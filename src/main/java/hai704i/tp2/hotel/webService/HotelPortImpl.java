package hai704i.tp2.hotel.webService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.jws.WebService;

@WebService(
        endpointInterface = "hai704i.tp2.hotel.webService.HotelPort",
        serviceName = "HotelService",
        portName = "HotelSoapPort",
        targetNamespace = "http://hai704i.hotel.fr/"
)
public class HotelPortImpl implements HotelPort {

    @Override
    public List<Offre> chercherOffres(DemandeRecherche d) {
        List<Offre> res = new ArrayList<>();
        res.add(offre("OF-118", "Double vue port", 4, 2, "89.00", d));
        res.add(offre("OF-204", "Familiale", 3, 4, "124.50", d));
        return res;
    }

    private Offre offre(String id, String chambre, int etoiles, int cap, String prix,
                        DemandeRecherche d) {
        Offre o = new Offre();
        o.setId(id);
        o.setHotel("Hôtel des Arènes");
        o.setChambre(chambre);
        o.setEtoiles(etoiles);
        o.setCapacite(cap);
        o.setArrivee(d.getArrivee());
        o.setDepart(d.getDepart());
        o.setPrixTotal(new BigDecimal(prix));
        return o;
    }
}