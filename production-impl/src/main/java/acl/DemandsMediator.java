package acl;

import dao.DemandDao;
import entities.DemandEntity;
import shortages.Demands;
import shortages.DemandsRepository;

import java.time.LocalDate;
import java.util.List;

public class DemandsMediator implements DemandsRepository {

    private final DemandDao demandDao;

    public DemandsMediator(DemandDao demandDao) {
        this.demandDao = demandDao;
    }

    @Override
    public Demands getDemands(String productRefNo, LocalDate today) {
        List<DemandEntity> demands = demandDao.findFrom(today.atStartOfDay(), productRefNo);
        var demandsPerDay = new Demands(demands);
        return demandsPerDay;
    }
}
