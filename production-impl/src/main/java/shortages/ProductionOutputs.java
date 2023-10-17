package shortages;

import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.*;

public class ProductionOutputs {
    private final Map<LocalDate, List<ProductionEntity>> outputs;

    public ProductionOutputs(List<ProductionEntity> productions) {
        Map<LocalDate, List<ProductionEntity>> outputs = new HashMap<>();
        for (ProductionEntity production : productions) {
            if (!outputs.containsKey(production.getStart().toLocalDate())) {
                outputs.put(production.getStart().toLocalDate(), new ArrayList<>());
            }
            outputs.get(production.getStart().toLocalDate()).add(production);
        }
        this.outputs = Collections.unmodifiableMap(outputs);
    }

    public long getOutput(LocalDate day) {
        return outputs.get(day)
                .stream()
                .mapToLong(ProductionEntity::getOutput)
                .sum();
    }
}
