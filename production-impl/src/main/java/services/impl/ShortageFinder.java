package services.impl;

import entities.ShortageEntity;
import external.CurrentStock;
import shortages.*;

import java.time.LocalDate;
import java.util.List;

public class ShortageFinder {

    private final DemandsRepository demandRepository;
    private final ProductionOutputRepository productionRepository;

    public ShortageFinder(DemandsRepository demandRepository, ProductionOutputRepository productionRepository) {
        this.demandRepository = demandRepository;
        this.productionRepository = productionRepository;
    }

    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {
        DateRange dates = DateRange.of(today, daysAhead);
        ProductionOutput outputs = productionRepository.getOutput(productRefNo, today);
        Demands demandsPerDay = demandRepository.getDemands(productRefNo, today);
        long level = stock.getLevel();

        ShortagePrediction prediction = new ShortagePrediction(productRefNo, dates, outputs, demandsPerDay, level);

        Shortage.Builder gap = prediction.predict();
        return gap.build();
    }

}
