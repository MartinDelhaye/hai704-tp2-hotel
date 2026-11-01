package hai704i.tp2.hotel.webService.fault;

import hai704i.tp2.hotel.webService.dto.FaultInfo;
import jakarta.xml.ws.WebFault;

@WebFault(
        name = "DemandeInvalideException",
        targetNamespace = "http://hai704i.hotel.fr/"
)
public class DemandeInvalideFaultException extends Exception {
    private final FaultInfo faultInfo;

    public DemandeInvalideFaultException(
            String message,
            FaultInfo faultInfo
    ) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public FaultInfo getFaultInfo() {
        return faultInfo;
    }
}