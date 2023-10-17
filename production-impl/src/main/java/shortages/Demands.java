package shortages;

import entities.DemandEntity;
import enums.DeliverySchema;
import tools.Util;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demands {
    private final Map<LocalDate, DemandEntity> demandsPerDay;

    public Demands(List<DemandEntity> demands) {
        Map<LocalDate, DemandEntity> demandsPerDay = new HashMap<>();
        for (DemandEntity demand : demands) {
            demandsPerDay.put(demand.getDay(), demand);
        }
        this.demandsPerDay = Collections.unmodifiableMap(demandsPerDay);
    }

    public DailyDemand get(LocalDate day) {
        if (demandsPerDay.containsKey(day)) {
            return new DailyDemand(
                    Util.getLevel(demandsPerDay.get(day)),
                    Util.getDeliverySchema(demandsPerDay.get(day))
            );
        } else {
            return new DailyDemand(0, DeliverySchema.tillEndOfDay);
        }
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
