package hai704i.tp2.hotel;

import hai704i.tp2.hotel.webService.HotelPortImpl;
import jakarta.xml.ws.Endpoint;

public class ServeurHotel {
    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8081;
        String adresse = "http://localhost:" + port + "/hotel";
        Endpoint.publish(adresse, new HotelPortImpl());
        System.out.println("Hôtel publié : " + adresse + "?wsdl");
    }
}