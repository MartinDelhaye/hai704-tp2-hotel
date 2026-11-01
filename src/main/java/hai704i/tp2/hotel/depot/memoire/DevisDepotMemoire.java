package hai704i.tp2.hotel.depot.memoire;

import hai704i.tp2.hotel.depot.interfaces.DevisDepot;
import hai704i.tp2.hotel.metier.model.Devis;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DevisDepotMemoire implements DevisDepot {
    private final Map<String, Devis> devis = new ConcurrentHashMap<>();

    @Override
    public void enregistrer(Devis devis) {
        this.devis.put(devis.id(), devis);
    }

    @Override
    public Optional<Devis> trouver(String id) {
        return Optional.ofNullable(devis.get(id));
    }
}