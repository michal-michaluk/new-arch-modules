package acl;

import demands.DemandModuleFacade;
import shortages.Demands;
import shortages.DemandsRepository;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class DemandsMediator implements DemandsRepository {

    private final DemandModuleFacade facade;

    public DemandsMediator(DemandModuleFacade facade) {
        this.facade = facade;
    }

    @Override
    public Demands getDemands(String productRefNo, LocalDate today) {
        return new Demands(
                facade.findFrom(productRefNo, today).stream()
                        .collect(Collectors.toUnmodifiableMap(
                                DemandModuleFacade.DemandRecord::date,
                                demand -> new Demands.DailyDemand(
                                        demand.demand(),
                                        demand.schema()
                                )
                        )));
    }
}
