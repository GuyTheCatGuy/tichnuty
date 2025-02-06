package main.key_management;
import data_structures.key_management.*;
import main.StockUpdate;

public class UpdateTimestampExtractor implements KeyManager<Long, StockUpdate>{
    @Override
    public Long extractRawKey(StockUpdate value) {
        return value.getTimestamp();
    }
}
