package shortages;

public class ShortagePredictionService {

    private final DemandsRepository demand;
    private final ProductionOutputRepository production;
    private final WarehouseRepository warehouse;

    public ShortagePredictionService(DemandsRepository demand, ProductionOutputRepository production, WarehouseRepository warehouse) {
        this.demand = demand;
        this.production = production;
        this.warehouse = warehouse;
    }

    public Shortage predict(String productRefNo, DateRange range) {
        ProductionOutput outputs = production.getOutput(productRefNo, range.start());
        Demands demandsPerDay = demand.getDemands(productRefNo, range.start());
        long stock = warehouse.getStock(productRefNo);
        ShortagePrediction prediction = new ShortagePrediction(productRefNo, range, outputs, demandsPerDay, stock);

        Shortage gap = prediction.predict();
        return gap;
    }
}
