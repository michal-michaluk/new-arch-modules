package shortages;

import enums.DeliverySchema;

import java.time.LocalDate;
import java.util.Map;

public class Demands {
    private final Map<LocalDate, DailyDemand> demandsPerDay;

    public Demands(Map<LocalDate, DailyDemand> demands) {
        this.demandsPerDay = demands;
    }

    public DailyDemand get(LocalDate day) {
        return demandsPerDay.getOrDefault(day, null);
    }

    public static class DailyDemand {
        private final long demand;
        private final DeliverySchema schema;

        public DailyDemand(long demand, DeliverySchema schema) {
            this.demand = demand;
            this.schema = schema;
        }

        public boolean hasDeliverySchema(DeliverySchema schema) {
            return this.schema == schema;
        }

        public long getDemand() {
            return demand;
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            long levelOnDelivery;
            if (hasDeliverySchema(DeliverySchema.atDayStart)) {
                levelOnDelivery = level - getDemand();
            } else if (hasDeliverySchema(DeliverySchema.tillEndOfDay)) {
                levelOnDelivery = level - getDemand() + produced;
            } else if (hasDeliverySchema(DeliverySchema.every3hours)) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
            return levelOnDelivery;
        }
    }
}
