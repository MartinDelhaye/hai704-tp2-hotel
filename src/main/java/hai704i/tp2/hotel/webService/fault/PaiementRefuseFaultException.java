package hai704i.tp2.hotel.webService.fault;

import hai704i.tp2.hotel.webService.dto.FaultInfo;
import jakarta.xml.ws.WebFault;

@WebFault(
        name = "PaiementRefuseException",
        targetNamespace = "http://hai704i.hotel.fr/"
)
public class PaiementRefuseFaultException extends Exception {
    private final FaultInfo faultInfo;

    public PaiementRefuseFaultException(
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