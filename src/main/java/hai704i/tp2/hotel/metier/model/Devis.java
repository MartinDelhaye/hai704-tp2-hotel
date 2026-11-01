package hai704i.tp2.hotel.metier.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record Devis(
        String id,
        String agence,
        String chambreId,
        String hotel,
        String chambre,
        int etoiles,
        int capacite,
        LocalDate arrivee,
        LocalDate depart,
        BigDecimal prixTotal,
        Instant expireLe
) {
}