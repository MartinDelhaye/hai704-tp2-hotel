package hai704i.tp2.hotel;

import hai704i.tp2.hotel.depot.memoire.AgenceDepotMemoire;
import hai704i.tp2.hotel.depot.memoire.ChambreDepotMemoire;
import hai704i.tp2.hotel.depot.memoire.DevisDepotMemoire;
import hai704i.tp2.hotel.depot.memoire.ReservationDepotMemoire;
import hai704i.tp2.hotel.metier.HotelService;
import hai704i.tp2.hotel.webService.HotelPortImpl;
import jakarta.xml.ws.Endpoint;

public class ServeurHotel {
    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8081;
        String adresse = "http://localhost:" + port + "/hotel";

        HotelService service = new HotelService(
                new ChambreDepotMemoire(),
                new AgenceDepotMemoire(),
                new DevisDepotMemoire(),
                new ReservationDepotMemoire()
        );
        Endpoint.publish(adresse, new HotelPortImpl(service));
        System.out.println("Hôtel publié : " + adresse + "?wsdl");
    }
}