package acl;

import dao.DemandDao;
import entities.DemandEntity;
import shortages.Demands;
import shortages.DemandsRepository;
import tools.Util;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class DemandsMediator implements DemandsRepository {

    private final DemandDao demandDao;

    public DemandsMediator(DemandDao demandDao) {
        this.demandDao = demandDao;
    }

    @Override
    public Demands getDemands(String productRefNo, LocalDate today) {
        return new Demands(
                demandDao.findFrom(today.atStartOfDay(), productRefNo).stream()
                        .collect(Collectors.toUnmodifiableMap(
                                DemandEntity::getDay,
                                demand -> new Demands.DailyDemand(
                                        Util.getLevel(demand),
                                        Util.getDeliverySchema(demand)
                                )
                        )));
    }
}
