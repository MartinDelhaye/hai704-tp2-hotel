package hai704i.tp2.hotel.metier;

public class AgenceInconnueException extends Exception {
    public AgenceInconnueException(String agence) {
        super("agence " + agence + " non partenaire");
    }
}