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
        ProductionOutputs outputs = production.get(productRefNo, range.start());
        Demands demandsPerDay = demands.get(productRefNo, range.start());
        long warehouseStock = warehouse.getCurrentStockLevel(productRefNo);

        ShortagePrediction prediction = new ShortagePrediction(
                productRefNo,
                range,
                outputs,
                demandsPerDay,
                warehouseStock);
        return prediction.predict();
    }
}
