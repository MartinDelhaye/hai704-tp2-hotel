package hai704i.tp2.hotel.metier.model;

public record EmpreinteSecret(byte[] sel, byte[] empreinte) {
    @Override
    public String toString() {
        return "EmpreinteSecret[masquée]";
    }
}