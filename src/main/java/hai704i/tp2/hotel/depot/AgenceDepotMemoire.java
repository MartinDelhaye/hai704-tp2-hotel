package hai704i.tp2.hotel.depot;

import java.util.Set;

public class AgenceDepotMemoire implements AgenceDepot {
    private final Set<String> partenaires = Set.of("AG-01", "AG-02", "AG-67");

    @Override
    public boolean estPartenaire(String idAgence) {
        return idAgence != null && partenaires.contains(idAgence);
    }
}