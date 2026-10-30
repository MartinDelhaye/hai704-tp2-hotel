package hai704i.tp2.hotel.webService;

import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

@WebService(
        name = "HotelPort",
        targetNamespace = "http://hai704i.hotel.fr/"
)
public interface HotelPort {
    @WebMethod(operationName = "chercherOffres")
    @WebResult(name = "offre")
    List<Offre> chercherOffres(
            @WebParam(name = "demande") DemandeRecherche demande
    ) throws AgenceInconnueFaultException;
}