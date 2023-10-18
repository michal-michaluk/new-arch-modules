package acl;

import entities.ShortageEntity;
import external.CurrentStock;
import shortages.Shortages;

import java.time.LocalDate;
import java.util.List;

public class ShortageACLFinder {

    private final DemandsACLRepository demands;
    private final ProductionOutputsACLRepository productions;

    public ShortageACLFinder(DemandsACLRepository demands, ProductionOutputsACLRepository productions) {
        this.demands = demands;
        this.productions = productions;
    }

    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {
        // execute module primary port (service)
        Shortages shortages = null;
        return shortages.getEntites();
    }

}
