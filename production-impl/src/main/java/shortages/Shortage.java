package shortages;

import entities.ShortageEntity;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Shortage {
    public static Builder builder(String productRefNo) {
        return new Builder(productRefNo);
    }

    public static class Builder {
        private final String productRefNo;
        private final List<ShortageEntity> gap = new LinkedList<>();

        public Builder(String productRefNo) {
            this.productRefNo = productRefNo;
        }

        public void add(LocalDate day, long levelOnDelivery) {
            ShortageEntity entity = new ShortageEntity();
            entity.setRefNo(productRefNo);
            entity.setFound(LocalDate.now());
            entity.setAtDay(day);
            entity.setMissing(Math.abs(levelOnDelivery));
            gap.add(entity);
        }

        public List<ShortageEntity> build() {
            return gap;
        }
    }
}
