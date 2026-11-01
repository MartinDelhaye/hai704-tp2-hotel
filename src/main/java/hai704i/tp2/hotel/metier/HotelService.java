package hai704i.tp2.hotel.metier;

import hai704i.tp2.hotel.depot.interfaces.AgenceDepot;
import hai704i.tp2.hotel.depot.interfaces.ChambreDepot;
import hai704i.tp2.hotel.depot.interfaces.DevisDepot;
import hai704i.tp2.hotel.depot.interfaces.ReservationDepot;
import hai704i.tp2.hotel.metier.exception.*;
import hai704i.tp2.hotel.metier.model.Chambre;
import hai704i.tp2.hotel.metier.model.Devis;
import hai704i.tp2.hotel.metier.model.EmpreinteSecret;
import hai704i.tp2.hotel.metier.model.Reservation;
import hai704i.tp2.hotel.metier.requete.CritereRecherche;
import hai704i.tp2.hotel.metier.requete.CritereReservation;
import hai704i.tp2.hotel.metier.requete.DonneesPaiement;
import hai704i.tp2.hotel.metier.requete.ResultatReservation;
import hai704i.tp2.hotel.metier.securite.Hachage;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HotelService {

    private final ChambreDepot chambreDepot;
    private final AgenceDepot agenceDepot;
    private final DevisDepot devisDepot;
    private static final Duration VALIDITE_DEVIS = Duration.ofMinutes(15);
    private final ReservationDepot reservationDepot;
    private static final EmpreinteSecret EMPREINTE_FACTICE = Hachage.deriver("agence-inconnue");

    public HotelService(
            ChambreDepot chambreDepot,
            AgenceDepot agenceDepot,
            DevisDepot devisDepot,
            ReservationDepot reservationDepot
    ) {
        this.chambreDepot = chambreDepot;
        this.agenceDepot = agenceDepot;
        this.devisDepot = devisDepot;
        this.reservationDepot = reservationDepot;
    }

    public List<Devis> chercher(CritereRecherche critereRecherche) throws DemandeInvalideException, AgenceInconnueException {
        if (!agenceDepot.estPartenaire(critereRecherche.agence())) {
            throw new AgenceInconnueException(critereRecherche.agence());
        }
        valider(critereRecherche);
        long nuits = ChronoUnit.DAYS.between(critereRecherche.arrivee(), critereRecherche.depart());

        List<Devis> offres = new ArrayList<>();
        for (Chambre chambre : chambreDepot.toutesLesChambres()) {
            if (!chambre.ville().equalsIgnoreCase(critereRecherche.ville())) continue;
            if (chambre.capacite() < critereRecherche.personnes()) continue;
            if (chambre.etoiles() < critereRecherche.etoilesMin()) continue;
            if (reservationDepot.chambreOccupee(chambre.id(), critereRecherche.arrivee(), critereRecherche.depart()))
                continue;

            BigDecimal prixTotalNuits = chambre.prixNuit().multiply(BigDecimal.valueOf(nuits));
            if (critereRecherche.prixMax() != null && prixTotalNuits.compareTo(critereRecherche.prixMax()) > 0)
                continue;

            Devis devis = new Devis(
                    "DV-" + UUID.randomUUID(),
                    critereRecherche.agence(),
                    chambre.id(), chambre.hotel(), chambre.nom(),
                    chambre.etoiles(), chambre.capacite(),
                    critereRecherche.arrivee(), critereRecherche.depart(),
                    prixTotalNuits,
                    Instant.now().plus(VALIDITE_DEVIS));
            devisDepot.enregistrer(devis);
            offres.add(devis);
        }
        return offres;
    }

    private void valider(CritereRecherche c) throws DemandeInvalideException {
        if (c == null) throw new DemandeInvalideException("demande absente");
        if (c.ville() == null || c.ville().isBlank()) throw new DemandeInvalideException("ville obligatoire");
        if (c.arrivee() == null || c.depart() == null) throw new DemandeInvalideException("dates obligatoires");
        if (!c.depart().isAfter(c.arrivee())) throw new DemandeInvalideException("le départ doit suivre l'arrivée");
        if (c.personnes() < 1) throw new DemandeInvalideException("au moins une personne");
    }


    public ResultatReservation reserver(CritereReservation c)
            throws DemandeInvalideException, AgenceNonAuthentifieeException,
            PaiementRefuseException, OffreIndisponibleException {
        if (c == null) throw new DemandeInvalideException("demande absente");
        authentifier(c.agence(), c.secretPresente());
        if (vide(c.idDemande())) throw new DemandeInvalideException("identifiant de demande obligatoire");
        if (vide(c.idDevis())) throw new DemandeInvalideException("identifiant d'offre obligatoire");
        if (vide(c.nom()) || vide(c.prenom())) throw new DemandeInvalideException("nom et prénom obligatoires");
        if (c.paiement() == null) throw new DemandeInvalideException("paiement obligatoire");
        return reserverSousVerrou(c);
    }

    private void authentifier(String agence, String secretPresente) throws AgenceNonAuthentifieeException {
        Optional<EmpreinteSecret> rangee = agenceDepot.empreinteSecret(agence);
        boolean valide = Hachage.verifier(secretPresente, rangee.orElse(EMPREINTE_FACTICE));
        if (rangee.isEmpty() || !valide) throw new AgenceNonAuthentifieeException();
    }

    private synchronized ResultatReservation reserverSousVerrou(CritereReservation c)
            throws DemandeInvalideException, PaiementRefuseException, OffreIndisponibleException {
        Optional<Reservation> existante = reservationDepot.trouver(c.agence(), c.idDemande());
        if (existante.isPresent()) {
            if (!existante.get().idDevis().equals(c.idDevis())) {
                throw new DemandeInvalideException("identifiant de demande déjà utilisé pour une autre offre");
            }
            return new ResultatReservation(existante.get(), true);
        }

        verifierPaiement(c.paiement());

        Devis devis = devisDepot.trouver(c.idDevis())
                .filter(d -> d.agence().equals(c.agence()))
                .orElseThrow(() -> new OffreIndisponibleException("offre " + c.idDevis() + " inconnue"));
        if (Instant.now().isAfter(devis.expireLe())) {
            throw new OffreIndisponibleException("offre " + c.idDevis() + " expirée");
        }
        if (reservationDepot.chambreOccupee(devis.chambreId(), devis.arrivee(), devis.depart())) {
            throw new OffreIndisponibleException("offre " + c.idDevis() + " plus disponible");
        }

        String numero = c.paiement().numeroCarte();
        Reservation r = new Reservation("RES-" + UUID.randomUUID(), c.idDemande(), devis.id(),
                c.agence(), devis.chambreId(), devis.hotel(), devis.chambre(),
                devis.arrivee(), devis.depart(), devis.prixTotal(), c.nom(), c.prenom(),
                numero.substring(numero.length() - 4));
        reservationDepot.enregistrer(r);
        return new ResultatReservation(r, false);
    }

    private void verifierPaiement(DonneesPaiement p) throws PaiementRefuseException {
        if (p.numeroCarte() == null || !p.numeroCarte().matches("\\d{13,19}"))
            throw new PaiementRefuseException("numéro de carte invalide (13 à 19 chiffres)");
        if (p.moisExpiration() < 1 || p.moisExpiration() > 12)
            throw new PaiementRefuseException("mois d'expiration invalide");
        if (p.anneeExpiration() < 1000 || p.anneeExpiration() > 9999)
            throw new PaiementRefuseException("année d'expiration invalide");
        if (YearMonth.of(p.anneeExpiration(), p.moisExpiration()).isBefore(YearMonth.now()))
            throw new PaiementRefuseException("carte expirée");
        if (p.cvc() == null || !p.cvc().matches("\\d{3,4}"))
            throw new PaiementRefuseException("cryptogramme invalide");
    }

    private static boolean vide(String s) {
        return s == null || s.isBlank();
    }
}