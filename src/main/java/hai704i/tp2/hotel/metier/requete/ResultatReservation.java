package hai704i.tp2.hotel.metier.requete;

import hai704i.tp2.hotel.metier.model.Reservation;

public record ResultatReservation(
        Reservation reservation,
        boolean rejeu
) {
}