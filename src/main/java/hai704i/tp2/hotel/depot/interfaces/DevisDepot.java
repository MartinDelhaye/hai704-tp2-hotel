package hai704i.tp2.hotel.depot.interfaces;

import hai704i.tp2.hotel.metier.model.Devis;
import java.util.Optional;

public interface DevisDepot {
    void enregistrer(Devis devis);
    Optional<Devis> trouver(String id);
}