package acl;

import entities.ShortageEntity;
import external.CurrentStock;
import shortages.DateRange;
import shortages.ShortagePredictionService;
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

        ShortagePredictionService service = new ShortagePredictionService(demands, productions, productRefNo1 -> stock.getLevel());
        Shortages shortages = service.predict(productRefNo, DateRange.of(today, daysAhead));

        return shortages.getEntites();
    }

}
