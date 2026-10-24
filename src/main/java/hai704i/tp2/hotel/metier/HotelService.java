package hai704i.tp2.hotel.metier;

import hai704i.tp2.hotel.depot.ChambreDepot;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HotelService {

    private final ChambreDepot depot;

    public HotelService(ChambreDepot depot) {
        this.depot = depot;
    }

    public List<OffreMetier> chercher(CritereRecherche critereRecherche) throws DemandeInvalideException {
        valider(critereRecherche);
        long nuits = ChronoUnit.DAYS.between(critereRecherche.arrivee(), critereRecherche.depart());

        List<OffreMetier> offres = new ArrayList<>();
        for (Chambre chambre : depot.toutesLesChambres()) {
            if (!chambre.ville().equalsIgnoreCase(critereRecherche.ville())) continue;
            if (chambre.capacite() < critereRecherche.personnes()) continue;
            if (chambre.etoiles() < critereRecherche.etoilesMin()) continue;

            BigDecimal prixTotalNuits = chambre.prixNuit().multiply(BigDecimal.valueOf(nuits));
            if (critereRecherche.prixMax() != null && prixTotalNuits.compareTo(critereRecherche.prixMax()) > 0)
                continue;

            offres.add(
                    new OffreMetier(
                            "OF-" + chambre.id(),
                            chambre.hotel(),
                            chambre.nom(),
                            chambre.etoiles(),
                            chambre.capacite(),
                            critereRecherche.arrivee(),
                            critereRecherche.depart(),
                            prixTotalNuits
                    )
            );
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
}