package hai704i.tp2.hotel.depot.interfaces;

import hai704i.tp2.hotel.metier.model.EmpreinteSecret;
import java.util.Optional;

public interface AgenceDepot {
    boolean estPartenaire(String idAgence);
    Optional<EmpreinteSecret> empreinteSecret(String idAgence);
}