package main;
import data_structures.*;
import data_structures.key_management.InfiniteKey;
import data_structures.two_three_tree.TwoThreeTree;
import main.key_management.UpdateTimestampExtractor;

public class Stock {
    private String stockId;
    private Float price;
    private Long creationTimestamp;
    private TwoThreeTree<Long, StockUpdate> tree;
    static UpdateTimestampExtractor TimestampManager = new UpdateTimestampExtractor();

    public Stock(String stockId, StockUpdate initializationPrice) {
        if(initializationPrice.getTimestamp() < 0 || initializationPrice.getDifference() <= 0) {
            throw new IllegalArgumentException();
        }
        this.stockId = stockId;
        this.price = 0.0F;
        this.creationTimestamp = initializationPrice.getTimestamp();
        this.tree = new TwoThreeTree<Long, StockUpdate>(Stock.TimestampManager);
        this.tree.insert(initializationPrice);
        this.price += initializationPrice.getDifference();
    }

    public String getId() {
        return this.stockId;
    }

    public Float getPrice() {
        return this.price;
    }


    public void updatePrice(StockUpdate update) {
        if(update.getDifference() == 0) {
            throw new IllegalArgumentException();
        }
        this.tree.insert(update);
        this.price += update.getDifference();
    }

    public void RemoveUpdate(Long updateTimestamp) {
        if(updateTimestamp.equals(this.creationTimestamp)) {
            throw new IllegalArgumentException();
        }
        Float badDifference = this.tree.search(updateTimestamp).getDifference();
        this.tree.delete(updateTimestamp);
        this.price -= badDifference;
    }

    @Override
    public String toString() {
        return this.stockId + "with price" + this.price;
    }
}
