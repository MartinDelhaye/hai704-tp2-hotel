package hai704i.tp2.hotel.metier;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CritereRecherche(
        String ville,
        LocalDate arrivee,
        LocalDate depart,
        int personnes,
        int etoilesMin,
        BigDecimal prixMax
) {
}