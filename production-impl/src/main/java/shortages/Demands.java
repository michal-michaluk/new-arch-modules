package shortages;

import enums.DeliverySchema;

import java.time.LocalDate;
import java.util.Map;

public class Demands {
    private static final DailyDemand NO_DEMAND = new DailyDemand(0, DeliverySchema.tillEndOfDay);

    private final Map<LocalDate, DailyDemand> demands;

    public Demands(Map<LocalDate, DailyDemand> demands) {
        this.demands = demands;
    }

    public DailyDemand get(LocalDate day) {
        return demands.getOrDefault(day, NO_DEMAND);
    }

    public record DailyDemand(long demand, DeliverySchema schema) {

        public long getLevel() {
            return demand;
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            return switch (schema) {
                case atDayStart -> level - demand;
                case tillEndOfDay -> level - demand + produced;
                case null, default -> {
                    if (schema == DeliverySchema.every3hours) {
                        // TODO WTF ?? we need to rewrite that app :/
                        throw new UnsupportedOperationException();
                    } else {
                        // TODO implement other variants
                        throw new UnsupportedOperationException();
                    }
                }
            };
        }
    }
}
