package hai704i.tp2.hotel.depot.memoire;

import hai704i.tp2.hotel.depot.interfaces.ReservationDepot;
import hai704i.tp2.hotel.metier.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReservationDepotMemoire implements ReservationDepot {
    private final List<Reservation> reservations = new CopyOnWriteArrayList<>();

    @Override
    public void enregistrer(Reservation r) {
        reservations.add(r);
    }

    @Override
    public Optional<Reservation> trouver(String agence, String idDemande) {
        return reservations.stream()
                .filter(r -> r.agence().equals(agence) && r.idDemande().equals(idDemande))
                .findFirst();
    }

    @Override
    public boolean chambreOccupee(String chambreId, LocalDate arrivee, LocalDate depart) {
        return reservations.stream().anyMatch(r -> r.chambreId().equals(chambreId)
                && r.arrivee().isBefore(depart) && arrivee.isBefore(r.depart()));
    }
}