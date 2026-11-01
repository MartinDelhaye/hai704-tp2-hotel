package hai704i.tp2.hotel.webService.dto.reservation;

public class IdentificationAgence {
    private String idAgence;
    private String secret;

    public IdentificationAgence() {
    }

    public String getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(String idAgence) {
        this.idAgence = idAgence;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}