package acl;

import dao.ProductionDao;
import entities.ProductionEntity;
import shortages.ProductionOutputs;
import shortages.ProductionOutputsRepository;

import java.time.LocalDate;
import java.util.List;

public class ProductionOutputsMediator implements ProductionOutputsRepository {
    private final ProductionDao productionDao;

    public ProductionOutputsMediator(ProductionDao productionDao) {
        this.productionDao = productionDao;
    }

    @Override
    public ProductionOutputs getOutputs(String productRefNo, LocalDate today) {
        List<ProductionEntity> productions = productionDao.findFromTime(productRefNo, today.atStartOfDay());
        var outputs = new ProductionOutputs(productions);
        return outputs;
    }
}
