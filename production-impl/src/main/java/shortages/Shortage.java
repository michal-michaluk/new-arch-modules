package shortages;

import entities.ShortageEntity;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Shortage {
    private final List<ShortageEntity> shortages;

    public Shortage(List<ShortageEntity> shortages) {
        this.shortages = shortages;
    }

    public static Builder builder(String productRefNo) {
        return new Builder(productRefNo);
    }

    public List<ShortageEntity> toEntities() {
        return shortages;
    }

    public static class Builder {
        private final String productRefNo;
        private final List<ShortageEntity> shortages = new LinkedList<>();

        public Builder(String productRefNo) {
            this.productRefNo = productRefNo;
        }

        public void add(LocalDate day, long levelOnDelivery) {
            ShortageEntity entity = new ShortageEntity();
            entity.setRefNo(productRefNo);
            entity.setFound(LocalDate.now());
            entity.setAtDay(day);
            entity.setMissing(Math.abs(levelOnDelivery));
            shortages.add(entity);
        }

        public Shortage build() {
            return new Shortage(shortages);
        }
    }
}
