package services.impl;

import dao.DemandDao;
import dao.ProductionDao;
import entities.DemandEntity;
import entities.ProductionEntity;
import entities.ShortageEntity;
import external.CurrentStock;
import shortages.DateRange;
import shortages.Demands;
import shortages.ProductionOutput;
import shortages.Shortage;

import java.time.LocalDate;
import java.util.List;

public class ShortageFinder {

    private final DemandDao demandDao;
    private final ProductionDao productionDao;

    public ShortageFinder(DemandDao demandDao, ProductionDao productionDao) {
        this.demandDao = demandDao;
        this.productionDao = productionDao;
    }

    /**
     * Production at day of expected delivery is quite complex:
     * We are able to produce and deliver just in time at same day
     * but depending on delivery time or scheme of multiple deliveries,
     * we need to plan properly to have right amount of parts ready before delivery time.
     * <p/>
     * Typical schemas are:
     * <li>Delivery at prod day start</li>
     * <li>Delivery till prod day end</li>
     * <li>Delivery during specified shift</li>
     * <li>Multiple deliveries at specified times</li>
     * Schema changes the way how we calculate shortages.
     * Pick of schema depends on customer demand on daily basis and for each product differently.
     * Some customers includes that information in callof document,
     * other stick to single schema per product. By manual adjustments of demand,
     * customer always specifies desired delivery schema
     * (increase amount in scheduled transport or organize extra transport at given time)
     */
    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {
        List<ProductionEntity> productions = productionDao.findFromTime(productRefNo, today.atStartOfDay());
        List<DemandEntity> demands = demandDao.findFrom(today.atStartOfDay(), productRefNo);

        DateRange dates = DateRange.of(today, daysAhead);

        ProductionOutput outputs = new ProductionOutput(productions);
        Demands demandsPerDay = new Demands(demands);

        long level = stock.getLevel();

        Shortage.Builder gap = Shortage.builder(productRefNo);
        for (LocalDate day : dates) {
            Demands.DailyDemand demand = demandsPerDay.get(day);
            if (demand == null) {
                level += outputs.getOutputs(day);
                continue;
            }
            long produced = outputs.getOutputs(day);
            long levelOnDelivery = demand.calculateLevelOnDelivery(level, produced);

            if (levelOnDelivery < 0) {
                gap.add(day, levelOnDelivery);
            }
            long endOfDayLevel = level + produced - demand.getLevel();
            level = Math.max(endOfDayLevel, 0);
        }
        return gap.build();
    }

}
