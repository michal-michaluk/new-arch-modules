package acl;

import dao.ProductionDao;
import entities.ProductionEntity;
import shortages.ProductionOutputs;
import shortages.ProductionOutputsRepository;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class ProductionOutputsMediator implements ProductionOutputsRepository {
    private final ProductionDao productionDao;

    public ProductionOutputsMediator(ProductionDao productionDao) {
        this.productionDao = productionDao;
    }

    @Override
    public ProductionOutputs getOutputs(String productRefNo, LocalDate today) {
        return new ProductionOutputs(
                productionDao.findFromTime(productRefNo, today.atStartOfDay())
                        .stream().collect(Collectors.groupingBy(
                                production -> production.getStart().toLocalDate(),
                                Collectors.summingLong(ProductionEntity::getOutput)
                        )));
    }
}
