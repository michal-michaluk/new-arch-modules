package shortages;

import java.time.LocalDate;

public interface ProductionOutputRepository {
    ProductionOutput getOutput(String productRefNo, LocalDate today);
}
