package hai704i.tp2.hotel.webService;

import hai704i.tp2.hotel.metier.*;
import jakarta.jws.WebService;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<Offre> chercherOffres(DemandeRecherche d) throws AgenceInconnueFaultException {
        try {
            CritereRecherche critere = (d == null) ? null : new CritereRecherche(
                    d.getAgence(), d.getVille(), versLocalDate(d.getArrivee()), versLocalDate(d.getDepart()),
                    d.getPersonnes(), d.getEtoilesMin(), d.getPrixMax());
            List<OffreMetier> offres = service.chercher(critere);
            List<Offre> res = new ArrayList<>();
            for (OffreMetier o : offres) res.add(versDto(o));
            return res;
        } catch (AgenceInconnueException e) {
            throw new AgenceInconnueFaultException(e.getMessage(), new FaultInfo(e.getMessage()));
        } catch (DemandeInvalideException e) {
            throw new IllegalArgumentException(e.getMessage());   // provisoire
        }
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

    private static Offre versDto(OffreMetier o) {
        Offre dto = new Offre();
        dto.setId(o.id());
        dto.setHotel(o.hotel());
        dto.setChambre(o.chambre());
        dto.setEtoiles(o.etoiles());
        dto.setCapacite(o.capacite());
        dto.setArrivee(versXml(o.arrivee()));
        dto.setDepart(versXml(o.depart()));
        dto.setPrixTotal(o.prixTotal());
        return dto;
    }
}