package hai704i.tp2.hotel.webService;

import java.util.List;

import hai704i.tp2.hotel.webService.dto.*;
import hai704i.tp2.hotel.webService.dto.recherche.DemandeRecherche;
import hai704i.tp2.hotel.webService.dto.recherche.Offre;
import hai704i.tp2.hotel.webService.dto.reservation.DemandeReservation;
import hai704i.tp2.hotel.webService.fault.*;
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
    List<Offre> chercherOffres(@WebParam(name = "demande") DemandeRecherche demande)
            throws AgenceInconnueFaultException, DemandeInvalideFaultException;

    @WebMethod(operationName = "reserver")
    @WebResult(name = "confirmation")
    Confirmation reserver(@WebParam(name = "demande") DemandeReservation demande)
            throws AgenceNonAuthentifieeFaultException, DemandeInvalideFaultException, PaiementRefuseFaultException, OffreIndisponibleFaultException;
}