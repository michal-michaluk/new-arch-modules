package shortages;

import dao.ProductionDao;
import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.List;

public class ProductionOutputsRepository {
    private final ProductionDao productionDao;

    public ProductionOutputsRepository(ProductionDao productionDao) {
        this.productionDao = productionDao;
    }

    public ProductionOutputs get(String productRefNo, LocalDate today) {
        List<ProductionEntity> productions = productionDao.findFromTime(productRefNo, today.atStartOfDay());
        ProductionOutputs outputs = new ProductionOutputs(productions);
        return outputs;
    }
}