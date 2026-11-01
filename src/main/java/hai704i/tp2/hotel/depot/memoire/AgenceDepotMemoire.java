package hai704i.tp2.hotel.depot.memoire;

import hai704i.tp2.hotel.depot.interfaces.AgenceDepot;
import hai704i.tp2.hotel.metier.model.EmpreinteSecret;
import hai704i.tp2.hotel.metier.securite.Hachage;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class AgenceDepotMemoire implements AgenceDepot {
    private final Map<String, EmpreinteSecret> agences = Map.of(
            "AG-01", Hachage.depuisTexte("68f548416c57f5678b72cf773b281a96:7b7587abeed54aa15d05d19fc2eb86f4673f090c0021cc2d5a42dbc6970b2ad2"),
            "AG-02", Hachage.depuisTexte("059697d5a2c6af6543db9ba3798f377e:babb82b66ff160246be2de10f2c3b35d13f2724e91c45fc22db30933891fd6dd"),
            "AG-03", Hachage.depuisTexte("abaef5f9e580e9f7e41da1605e661d16:8324902ade6ad30e6f9a372ccd5fc441f7df662738e8d6cf27af4b57f0ea79c2")
    );
    private final Map<String, BigDecimal> coefficients;

    private AgenceDepotMemoire(Map<String, BigDecimal> coefficients) {
        this.coefficients = coefficients;
    }
    public static AgenceDepotMemoire pourHotel(int numero) {
        return switch (numero) {
            case 1 -> new AgenceDepotMemoire(Map.of(
                    "AG-01", new BigDecimal("1.00"),
                    "AG-02", new BigDecimal("0.90"),
                    "AG-03", new BigDecimal("1.10")));
            case 2 -> new AgenceDepotMemoire(Map.of(
                    "AG-01", new BigDecimal("0.95"),
                    "AG-02", new BigDecimal("1.05"),
                    "AG-03", new BigDecimal("1.00")));
            default -> throw new IllegalArgumentException("hôtel inconnu : " + numero + " (1 ou 2)");
        };
    }

    @Override
    public BigDecimal coefficientTarif(String idAgence) {
        return coefficients.getOrDefault(idAgence, BigDecimal.ONE);
    }

    @Override
    public boolean estPartenaire(String idAgence) {
        return idAgence != null && agences.containsKey(idAgence);
    }

    @Override
    public Optional<EmpreinteSecret> empreinteSecret(String idAgence) {
        return idAgence == null ? Optional.empty() : Optional.ofNullable(agences.get(idAgence));
    }
}