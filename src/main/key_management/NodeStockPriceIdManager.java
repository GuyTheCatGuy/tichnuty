package main.key_management;
import data_structures.efficient_doubly_linked_list.DoubleNode;
import data_structures.key_management.*;
import main.Stock;

public class NodeStockPriceIdManager implements KeyManager<FloatString, DoubleNode<Stock>>{
    @Override
    public FloatString extractRawKey(DoubleNode<Stock> value) {
        return(this.extractRawKey(value.getValue()));
    }

    public FloatString extractRawKey(Stock value) {
        return new FloatString(value.getPrice(), value.getId());
    }
}
