package shortages;

import java.time.LocalDate;

public interface DemandsRepository {
    Demands getDemands(String productRefNo, LocalDate today);
}
