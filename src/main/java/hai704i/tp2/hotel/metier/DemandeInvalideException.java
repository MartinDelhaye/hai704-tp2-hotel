package hai704i.tp2.hotel.metier;

public class DemandeInvalideException extends Exception {
    public DemandeInvalideException(
            String message
    ) {
        super(message);
    }
}