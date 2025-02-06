package main.key_management;
import data_structures.key_management.*;
import main.Stock;

public class StockIdManager implements KeyManager<String, Stock>{
    @Override
    public String extractRawKey(Stock value) {
        return value.getId();
    }
}
