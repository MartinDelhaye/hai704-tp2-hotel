package hai704i.tp2.hotel.webService;

public class FaultInfo {
    private String message;

    public FaultInfo() {
    }

    public FaultInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}