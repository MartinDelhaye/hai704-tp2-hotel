package hai704i.tp2.hotel.metier;

import java.math.BigDecimal;

public record Chambre(
        String id,
        String hotel,
        String nom,
        String ville,
        int etoiles,
        int capacite,
        BigDecimal prixNuit
) {
}