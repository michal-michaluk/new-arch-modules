package shortages;

import dao.ProductionDao;
import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

public class ProductionOutputsRepository {
    private final ProductionDao productionDao;

    public ProductionOutputsRepository(ProductionDao productionDao) {
        this.productionDao = productionDao;
    }

    public ProductionOutputs get(String productRefNo, LocalDate start) {
        List<ProductionEntity> productions = productionDao.findFromTime(productRefNo, start.atStartOfDay());
        Map<LocalDate, Long> grouped = productions.stream().collect(groupingBy(
                e -> e.getStart().toLocalDate(),
                summingLong(ProductionEntity::getOutput))
        );
        return new ProductionOutputs(grouped);
    }
}
