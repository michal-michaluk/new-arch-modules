package shortages;

public class ShortagePredictionService {

    private final DemandsRepository demands;
    private final ProductionOutputsRepository production;
    private final WarehouseStockRepository warehouse;

    public ShortagePredictionService(DemandsRepository demands, ProductionOutputsRepository production, WarehouseStockRepository warehouse) {
        this.demands = demands;
        this.production = production;
        this.warehouse = warehouse;
    }

    public Shortages predict(String productRefNo, DateRange range) {
        var prediction = new ShortagePrediction(
                productRefNo,
                range,
                production.get(productRefNo, range.start()),
                demands.get(productRefNo, range.start()),
                warehouse.getCurrentStockLevel(productRefNo));

        return prediction.predict();
    }
}
