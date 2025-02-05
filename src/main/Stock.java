package main;
import data_structures.two_three_tree.TwoThreeTree;
import main.key_management.UpdateTimestampExtractor;

public class Stock {
    private String stockId;
    private Float price;
    private TwoThreeTree<Long, StockUpdate> tree;
    public static UpdateTimestampExtractor TimestampManager = new UpdateTimestampExtractor();

    public Stock(String stockId, StockUpdate initializationPrice) {
        this.stockId = stockId;
        this.price = 0.0F;
        this.tree = new TwoThreeTree<Long, StockUpdate>(Stock.TimestampManager);
        this.updatePrice(initializationPrice);
    }

    public String getId() {
        return this.stockId;
    }

    public Float getPrice() {
        return this.price;
    }

    public void updatePrice(StockUpdate update) {
        this.tree.insert(update);
        this.price += update.getDifference();
    }

    public void RemoveUpdate(Long updateTimestamp) {
        StockUpdate update = this.tree.search(updateTimestamp);
        if(update == null) {
            throw new IllegalArgumentException("Stock update timestamp not found");
        }
        Float badDifference = update.getDifference();
        this.tree.delete(updateTimestamp);
        this.price -= badDifference;
    }
}
