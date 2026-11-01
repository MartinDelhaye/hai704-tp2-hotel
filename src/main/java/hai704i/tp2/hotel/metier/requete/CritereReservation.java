package hai704i.tp2.hotel.metier.requete;

public record CritereReservation(
        String idDemande,
        String agence,
        String secretPresente,
        String idDevis,
        String nom,
        String prenom,
        DonneesPaiement paiement
) {
    @Override
    public String toString() {
        return "CritereReservation[idDemande=" + idDemande + ", agence=" + agence
                + ", idDevis=" + idDevis + "]";
    }
}