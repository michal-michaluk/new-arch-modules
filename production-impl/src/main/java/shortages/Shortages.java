package shortages;

import entities.ShortageEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shortages {

    public static Shortages.Builder builder(String productRefNo) {
        return new Builder(productRefNo);
    }

    public static class Builder {
        private final String productRefNo;
        private final List<ShortageEntity> shortages = new ArrayList<>();

        public Builder(String productRefNo) {
            this.productRefNo = productRefNo;
        }

        public void add(LocalDate day, long levelOnDelivery) {
            ShortageEntity entity = new ShortageEntity();
            entity.setRefNo(productRefNo);
            entity.setFound(LocalDate.now());
            entity.setAtDay(day);
            entity.setMissing(-levelOnDelivery);
            shortages.add(entity);
        }

        public List<ShortageEntity> build() {
            return Collections.unmodifiableList(shortages);
        }
    }
}
