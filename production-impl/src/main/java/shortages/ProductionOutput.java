package shortages;

import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.*;

public class ProductionOutput {
    private final Map<LocalDate, List<ProductionEntity>> outputs;

    public ProductionOutput(List<ProductionEntity> productions) {
        Map<LocalDate, List<ProductionEntity>> outputs = new HashMap<>();
        for (ProductionEntity production : productions) {
            if (!outputs.containsKey(production.getStart().toLocalDate())) {
                outputs.put(production.getStart().toLocalDate(), new ArrayList<>());
            }
            outputs.get(production.getStart().toLocalDate()).add(production);
        }
        this.outputs = Collections.unmodifiableMap(outputs);
    }

    public long getOutputs(LocalDate day) {
        long produced = 0;
        for (ProductionEntity production : outputs.get(day)) {
            produced += production.getOutput();
        }
        return produced;
    }
}
