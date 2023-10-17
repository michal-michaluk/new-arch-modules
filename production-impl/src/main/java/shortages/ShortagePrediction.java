package shortages;

import java.time.LocalDate;

public class ShortagePrediction {
    private final String productRefNo;
    private final DateRange dates;
    private final ProductionOutputs outputs;
    private final Demands demands;
    private final long warehouseStock;

    public ShortagePrediction(String productRefNo, DateRange dates, ProductionOutputs outputs, Demands demands, long warehouseStock) {
        this.productRefNo = productRefNo;
        this.dates = dates;
        this.outputs = outputs;
        this.demands = demands;
        this.warehouseStock = warehouseStock;
    }

    public Shortages predict() {
        long level = warehouseStock;
        Shortages.Builder shortages = Shortages.builder(productRefNo);
        for (LocalDate day : dates) {
            long produced = outputs.getOutput(day);
            Demands.DailyDemand demand = demands.get(day);
            long levelOnDelivery = demand.calculateLevelOnDelivery(level, produced);

            if (levelOnDelivery < 0) {
                shortages.add(day, levelOnDelivery);
            }
            long endOfDayLevel = demand.calculateEndOfDayLevel(level, produced);
            level = Math.max(0, endOfDayLevel);
        }
        return shortages.build();
    }
}
