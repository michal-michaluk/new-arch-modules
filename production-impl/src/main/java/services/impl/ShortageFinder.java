package services.impl;

import entities.ShortageEntity;
import external.CurrentStock;
import shortages.*;

import java.time.LocalDate;
import java.util.List;

public class ShortageFinder {

    private final DemandsRepository demandDao;
    private final ProductionOutputsRepository productionDao;

    public ShortageFinder(DemandsRepository demandDao, ProductionOutputsRepository productionDao) {
        this.demandDao = demandDao;
        this.productionDao = productionDao;
    }

    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {

        DateRange dates = DateRange.of(today, daysAhead);
        ProductionOutputs outputs = productionDao.get(productRefNo, today);
        Demands demandsPerDay = demandDao.get(productRefNo, today);

        long level = stock.getLevel();

        ShortagePrediction prediction = new ShortagePrediction(productRefNo, dates, outputs, demandsPerDay, level);
        Shortages shortages = prediction.predict();
        return shortages.getEntites();
    }

}
