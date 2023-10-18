package acl;

import entities.ShortageEntity;
import external.CurrentStock;
import services.impl.ShortageFinder;
import shortages.*;

import java.time.LocalDate;
import java.util.List;

public class ShortageACLFinder {


    private final ShortageFinder legacy;
    private final DemandsRepository demandDao;
    private final ProductionOutputsRepository productionDao;
    private boolean validateOnProductionToggle = false;

    public ShortageACLFinder(ShortageFinder legacy, DemandsRepository demandDao, ProductionOutputsRepository productionDao) {
        this.legacy = legacy;
        this.demandDao = demandDao;
        this.productionDao = productionDao;
    }

    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {
        List<ShortageEntity> legacyResult = legacy.findShortages(productRefNo, today, daysAhead, stock);
        if (validateOnProductionToggle) {
            ShortagePredictionService service = new ShortagePredictionService(
                    demandDao,
                    productionDao,
                    refNo -> stock.getLevel()
            );
            Shortages shortages = service.predict(productRefNo, DateRange.of(today, daysAhead));

            compareAndLogDiff(legacyResult, shortages.getEntites());
        }
        return legacyResult;
    }

    private void compareAndLogDiff(List<ShortageEntity> legacyResult, List<ShortageEntity> entites) {

    }

}
