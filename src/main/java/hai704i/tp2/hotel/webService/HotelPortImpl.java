package hai704i.tp2.hotel.webService;

import hai704i.tp2.hotel.metier.*;
import hai704i.tp2.hotel.metier.exception.*;
import hai704i.tp2.hotel.metier.model.Devis;
import hai704i.tp2.hotel.metier.model.Reservation;
import hai704i.tp2.hotel.metier.requete.CritereRecherche;
import hai704i.tp2.hotel.metier.requete.CritereReservation;
import hai704i.tp2.hotel.metier.requete.DonneesPaiement;
import hai704i.tp2.hotel.metier.requete.ResultatReservation;
import hai704i.tp2.hotel.webService.dto.*;
import hai704i.tp2.hotel.webService.dto.recherche.DemandeRecherche;
import hai704i.tp2.hotel.webService.dto.recherche.Offre;
import hai704i.tp2.hotel.webService.dto.reservation.DemandeReservation;
import hai704i.tp2.hotel.webService.dto.reservation.IdentificationAgence;
import hai704i.tp2.hotel.webService.dto.reservation.Paiement;
import hai704i.tp2.hotel.webService.dto.reservation.Voyageur;
import hai704i.tp2.hotel.webService.fault.*;
import jakarta.jws.WebService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

@WebService(
        endpointInterface = "hai704i.tp2.hotel.webService.HotelPort",
        serviceName = "HotelService",
        portName = "HotelSoapPort",
        targetNamespace = "http://hai704i.hotel.fr/")
public class HotelPortImpl implements HotelPort {

    private final HotelService service;

    public HotelPortImpl(HotelService service) {
        this.service = service;
    }

    @Override
    public List<Offre> chercherOffres(DemandeRecherche d)
            throws AgenceInconnueFaultException, DemandeInvalideFaultException {
        try {
            CritereRecherche critere = (d == null) ? null : new CritereRecherche(
                    d.getAgence(), d.getVille(), versLocalDate(d.getArrivee()), versLocalDate(d.getDepart()),
                    d.getPersonnes(), d.getEtoilesMin(), d.getPrixMax());
            List<Offre> res = new ArrayList<>();
            for (Devis o : service.chercher(critere)) res.add(versDto(o));
            return res;
        } catch (AgenceInconnueException e) {
            throw new AgenceInconnueFaultException(e.getMessage(), new FaultInfo(e.getMessage()));
        } catch (DemandeInvalideException e) {
            throw new DemandeInvalideFaultException(e.getMessage(), new FaultInfo(e.getMessage()));
        }
    }

    @Override
    public Confirmation reserver(DemandeReservation d)
            throws AgenceNonAuthentifieeFaultException, DemandeInvalideFaultException,
            PaiementRefuseFaultException, OffreIndisponibleFaultException {
        try {
            return versConfirmation(service.reserver(versCritere(d)));
        } catch (AgenceNonAuthentifieeException e) {
            throw new AgenceNonAuthentifieeFaultException(e.getMessage(), new FaultInfo(e.getMessage()));
        } catch (DemandeInvalideException e) {
            throw new DemandeInvalideFaultException(e.getMessage(), new FaultInfo(e.getMessage()));
        } catch (PaiementRefuseException e) {
            throw new PaiementRefuseFaultException(e.getMessage(), new FaultInfo(e.getMessage()));
        } catch (OffreIndisponibleException e) {
            throw new OffreIndisponibleFaultException(e.getMessage(), new FaultInfo(e.getMessage()));
        }
    }

    private static CritereReservation versCritere(DemandeReservation d) {
        if (d == null) return null;
        IdentificationAgence id = d.getIdentification();
        Voyageur v = d.getVoyageur();
        Paiement p = d.getPaiement();
        DonneesPaiement paiement = (p == null) ? null : new DonneesPaiement(
                p.getNumeroCarte(), p.getMoisExpiration(), p.getAnneeExpiration(), p.getCvc());
        return new CritereReservation(d.getIdDemande(),
                id == null ? null : id.getIdAgence(), id == null ? null : id.getSecret(),
                d.getIdOffre(),
                v == null ? null : v.getNom(), v == null ? null : v.getPrenom(), paiement);
    }

    private static LocalDate versLocalDate(XMLGregorianCalendar x) {
        return x == null ? null : LocalDate.of(
                x.getYear(),
                x.getMonth(),
                x.getDay()
        );
    }

    private static XMLGregorianCalendar versXml(LocalDate d) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                    d.getYear(),
                    d.getMonthValue(),
                    d.getDayOfMonth(),
                    javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
            );
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Offre versDto(Devis devis) {
        Offre dto = new Offre();
        dto.setId(devis.id());
        dto.setHotel(devis.hotel());
        dto.setChambre(devis.chambre());
        dto.setEtoiles(devis.etoiles());
        dto.setCapacite(devis.capacite());
        dto.setArrivee(versXml(devis.arrivee()));
        dto.setDepart(versXml(devis.depart()));
        dto.setPrixTotal(devis.prixTotal());
        dto.setExpireLe(versXml(devis.expireLe()));
        return dto;
    }

    private static XMLGregorianCalendar versXml(Instant instant) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    GregorianCalendar.from(instant.atZone(ZoneOffset.UTC)));
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Confirmation versConfirmation(ResultatReservation res) {
        Reservation reservation = res.reservation();
        Confirmation confirmation = new Confirmation();
        confirmation.setReferenceReservation(reservation.reference());
        confirmation.setIdOffre(reservation.idDevis());
        confirmation.setHotel(reservation.hotel());
        confirmation.setChambre(reservation.chambre());
        confirmation.setArrivee(versXml(reservation.arrivee()));
        confirmation.setDepart(versXml(reservation.depart()));
        confirmation.setPrixTotal(reservation.prixTotal());
        confirmation.setRejeu(res.rejeu());
        return confirmation;
    }
}