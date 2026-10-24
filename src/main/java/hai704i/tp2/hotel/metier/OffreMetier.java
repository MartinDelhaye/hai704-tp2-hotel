package hai704i.tp2.hotel.metier;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OffreMetier(
        String id,
        String hotel,
        String chambre,
        int etoiles,
        int capacite,
        LocalDate arrivee,
        LocalDate depart,
        BigDecimal prixTotal
) {
}