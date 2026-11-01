package hai704i.tp2.hotel.depot.interfaces;

import hai704i.tp2.hotel.metier.model.Reservation;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationDepot {
    void enregistrer(Reservation reservation);

    Optional<Reservation> trouver(String agence, String idDemande);

    boolean chambreOccupee(String chambreId, LocalDate arrivee, LocalDate depart);
}