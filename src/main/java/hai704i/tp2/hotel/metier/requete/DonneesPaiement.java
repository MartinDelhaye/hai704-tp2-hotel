package hai704i.tp2.hotel.metier.requete;

public record DonneesPaiement(
        String numeroCarte,
        int moisExpiration,
        int anneeExpiration,
        String cvc) {
    @Override
    public String toString() {
        return "DonneesPaiement[masquées]";
    }
}