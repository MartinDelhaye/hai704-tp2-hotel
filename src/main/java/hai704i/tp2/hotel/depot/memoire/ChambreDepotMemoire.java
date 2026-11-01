package hai704i.tp2.hotel.depot.memoire;

import hai704i.tp2.hotel.depot.interfaces.ChambreDepot;
import hai704i.tp2.hotel.metier.model.Chambre;

import java.math.BigDecimal;
import java.util.List;

public class ChambreDepotMemoire implements ChambreDepot {
    private final List<Chambre> chambres;

    private ChambreDepotMemoire(List<Chambre> chambres) {
        this.chambres = List.copyOf(chambres);
    }

    public static ChambreDepotMemoire pourHotel(int numero) {
        return switch (numero) {
            case 1 -> new ChambreDepotMemoire(List.of(
                    new Chambre("CH-001", "Hôtel 1", "Premium", "Montpellier", 4, 2, new BigDecimal("99.99")),
                    new Chambre("CH-002", "Hôtel 1", "Familiale", "Montpellier", 3, 4, new BigDecimal("123.45")),
                    new Chambre("CH-003", "Hôtel 1", "Classique", "Montpellier", 2, 1, new BigDecimal("67.00"))));
            case 2 -> new ChambreDepotMemoire(List.of(
                    new Chambre("CH-101", "Hôtel 2", "Suite", "Montpellier", 5, 2, new BigDecimal("119.00")),
                    new Chambre("CH-102", "Hôtel 2", "Standard", "Montpellier", 3, 2, new BigDecimal("89.50")),
                    new Chambre("CH-103", "Hôtel 2", "Famille", "Montpellier", 4, 4, new BigDecimal("109.90")),
                    new Chambre("CH-104", "Hôtel 2", "Économique", "Montpellier", 2, 2, new BigDecimal("55.00"))));
            default -> throw new IllegalArgumentException("hôtel inconnu : " + numero + " (1 ou 2)");
        };
    }

    @Override
    public List<Chambre> toutesLesChambres() {
        return chambres;
    }
}