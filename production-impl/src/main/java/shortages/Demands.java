package shortages;

import entities.DemandEntity;
import enums.DeliverySchema;
import tools.Util;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demands {

    private final Map<LocalDate, DemandEntity> demandsPerDay = new HashMap<>();

    public Demands(List<DemandEntity> demands) {
        for (DemandEntity demand : demands) {
            demandsPerDay.put(demand.getDay(), demand);
        }
    }

    public DailyDemand get(LocalDate day) {
        if (demandsPerDay.containsKey(day)) {
            return new DailyDemand(demandsPerDay.get(day));
        }
        return null;
    }

    public static class DailyDemand {

        private final DemandEntity demand;

        public DailyDemand(DemandEntity demand) {
            this.demand = demand;
        }

        public long getLevel() {
            return Util.getLevel(demand);
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            long levelOnDelivery;
            if (hasDeliverySchema(DeliverySchema.atDayStart)) {
                levelOnDelivery = level - getLevel();
            } else if (hasDeliverySchema(DeliverySchema.tillEndOfDay)) {
                levelOnDelivery = level - getLevel() + produced;
            } else if (hasDeliverySchema(DeliverySchema.every3hours)) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
            return levelOnDelivery;
        }


        public long calculateEndOfDayLevel(long level, long produced) {
            return level + produced - getLevel();
        }

        private boolean hasDeliverySchema(DeliverySchema schema) {
            return Util.getDeliverySchema(demand) == schema;
        }
    }
}
