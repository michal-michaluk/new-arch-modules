package acl;

import dao.ProductionDao;
import entities.ProductionEntity;
import shortages.ProductionOutput;
import shortages.ProductionOutputRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductionOutputACLRepository implements ProductionOutputRepository {
    private final ProductionDao productionDao;

    public ProductionOutputACLRepository(ProductionDao productionDao) {
        this.productionDao = productionDao;
    }

    @Override
    public ProductionOutput getOutput(String productRefNo, LocalDate today) {
        List<ProductionEntity> productions = productionDao.findFromTime(productRefNo, today.atStartOfDay());
        Map<LocalDate, Long> outputs = productions.stream()
                .collect(Collectors.groupingBy(
                        production -> production.getStart().toLocalDate(),
                        Collectors.summingLong(ProductionEntity::getOutput)
                ));
        return new ProductionOutput(outputs);
    }
}
