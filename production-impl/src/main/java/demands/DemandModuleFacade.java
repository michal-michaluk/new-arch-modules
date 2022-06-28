package demands;

import dao.DemandDao;
import enums.DeliverySchema;

import java.time.LocalDate;
import java.util.List;

public class DemandModuleFacade {

    private final DemandDao dao;

    public DemandModuleFacade(DemandDao dao) {
        this.dao = dao;
    }

    public List<DemandRecord> findFrom(String productRefNo, LocalDate start) {
        return dao.findFrom(start.atStartOfDay(), productRefNo).stream()
                .map(entity -> new DemandRecord(
                        entity.getDay(),
                        entity.getLevel(),
                        entity.getDeliverySchema()
                )).toList();
    }

    public record DemandRecord(LocalDate date, long demand, DeliverySchema schema) {
    }
}
