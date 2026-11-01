package hai704i.tp2.hotel.metier.exception;

public class AgenceNonAuthentifieeException extends Exception {
    public AgenceNonAuthentifieeException() {
        super("authentification refusée");
    }
}