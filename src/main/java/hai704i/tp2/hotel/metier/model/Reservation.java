package hai704i.tp2.hotel.metier.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Reservation(
        String reference,
        String idDemande,
        String idDevis,
        String agence,
        String chambreId,
        String hotel,
        String chambre,
        LocalDate arrivee,
        LocalDate depart,
        BigDecimal prixTotal,
        String nom, String prenom
) {
}