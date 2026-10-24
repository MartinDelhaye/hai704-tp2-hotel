package hai704i.tp2.hotel.depot;

import hai704i.tp2.hotel.metier.Chambre;

import java.math.BigDecimal;
import java.util.List;

public class ChambreDepotMemoire implements ChambreDepot {

    private final List<Chambre> chambres = List.of(
            new Chambre(
                    "CH-001",
                    "Hôtel 1",
                    "Premium",
                    "Montpellier",
                    4,
                    2,
                    new BigDecimal("99.99")
            ),
            new Chambre("CH-002",
                    "Hôtel 1",
                    "Familiale",
                    "Montpellier",
                    3,
                    4,
                    new BigDecimal("123.45")
            ),
            new Chambre(
                    "CH-003",
                    "Hôtel 1",
                    "Classique",
                    "Montpellier",
                    2,
                    1,
                    new BigDecimal("67.00")
            )
    );

    @Override
    public List<Chambre> toutesLesChambres() {
        return chambres;
    }
}