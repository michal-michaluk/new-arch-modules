package shortages;

import java.time.LocalDate;

public class ShortagePrediction {
    private final String productRefNo;
    private final DateRange dates;
    private final ProductionOutput outputs;
    private final Demands demandsPerDay;
    private final long stock;

    public ShortagePrediction(String productRefNo, DateRange dates, ProductionOutput outputs, Demands demandsPerDay, long stock) {
        this.productRefNo = productRefNo;
        this.dates = dates;
        this.outputs = outputs;
        this.demandsPerDay = demandsPerDay;
        this.stock = stock;
    }

    public Shortage predict() {
        long level = stock;
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
            long endOfDayLevel = level + produced - demand.getDemand();
            level = Math.max(endOfDayLevel, 0);
        }
        return gap.build();
    }
}
