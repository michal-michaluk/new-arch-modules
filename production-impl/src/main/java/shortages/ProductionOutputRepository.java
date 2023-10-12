package shortages;

import dao.ProductionDao;
import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.List;

public class ProductionOutputRepository {
    private final ProductionDao productionDao;

    public ProductionOutputRepository(ProductionDao productionDao) {
        this.productionDao = productionDao;
    }

    public ProductionOutput getOutput(String productRefNo, LocalDate today) {
        List<ProductionEntity> productions = productionDao.findFromTime(productRefNo, today.atStartOfDay());
        ProductionOutput outputs = new ProductionOutput(productions);
        return outputs;
    }
}
