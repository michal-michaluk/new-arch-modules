package acl;

import dao.DemandDao;
import entities.DemandEntity;
import shortages.Demands;
import tools.Util;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DemandsACLRepository {
    private final DemandDao demandDao;

    public DemandsACLRepository(DemandDao demandDao) {
        this.demandDao = demandDao;
    }


    public Demands get(String productRefNo, LocalDate start) {
        List<DemandEntity> demands = demandDao.findFrom(start.atStartOfDay(), productRefNo);
        var grouped = demands.stream()
                .collect(Collectors.toUnmodifiableMap(
                        DemandEntity::getDay,
                        demand -> new Demands.DailyDemand(
                                Util.getLevel(demand),
                                Util.getDeliverySchema(demand)
                        )));
        return new Demands(grouped);
    }
}
