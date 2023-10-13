package acl;

import entities.ShortageEntity;
import external.CurrentStock;
import shortages.*;

import java.time.LocalDate;
import java.util.List;

public class ShortageFinderACL {

    private final DemandsRepository demandRepository;
    private final ProductionOutputRepository productionRepository;

    public ShortageFinderACL(DemandsRepository demandRepository, ProductionOutputRepository productionRepository) {
        this.demandRepository = demandRepository;
        this.productionRepository = productionRepository;
    }

    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {


        ShortagePredictionService service = new ShortagePredictionService(
                demandRepository,
                productionRepository,
                productRefNo1 -> stock.getLevel()
        );

        Shortage shortage = service.predict(productRefNo, DateRange.of(today, daysAhead));

        return shortage.toEntities();
    }

}
