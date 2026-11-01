package hai704i.tp2.hotel.metier.exception;

public class AgenceInconnueException extends Exception {
    public AgenceInconnueException(String agence) {
        super("agence " + agence + " non partenaire");
    }
}