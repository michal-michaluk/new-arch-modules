package shortages;

public interface WarehouseRepository {
    long getStock(String productRefNo);
}
