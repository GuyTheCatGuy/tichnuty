import data_structures.efficient_doubly_linked_list.EfficientDoublyLinkedList;
import data_structures.two_three_tree.TwoThreeTree;
import main.key_management.FloatString;
import main.key_management.NodeStockPriceIdManager;
import main.key_management.StockIdManager;
import main.key_management.UpdateTimestampExtractor;
import main.*;

public class StockManager {

    private StockIdManager idKeyManager;
    private NodeStockPriceIdManager priceIdKeyManager;
    private TwoThreeTree<String, Stock> stockTree;
    private EfficientDoublyLinkedList<FloatString, Stock> stockList;


    public StockManager() {
        Stock.TimestampManager = new UpdateTimestampExtractor();
        this.idKeyManager = new StockIdManager();
        this.priceIdKeyManager = new NodeStockPriceIdManager();
    }

    // 1. Initialize the system
    public void initStocks() {
        this.stockTree = new TwoThreeTree<String, Stock>(this.idKeyManager);
        this.stockList = new EfficientDoublyLinkedList<FloatString, Stock>(this.priceIdKeyManager);
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
        if(price <= 0) {
          throw new IllegalArgumentException("Price must be positive");
        }
        if(timestamp <= 0) {
            throw new IllegalArgumentException("timestamp must be positive"); 
        }
        if(this.stockTree.search(stockId) != null) {
          throw new IllegalArgumentException("Stock already exists");
        }
        Stock stock = new Stock(stockId, new StockUpdate(price, timestamp));
        this.stockTree.insert(stock);

        this.stockList.insert(stock);

    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
        Stock stock = this.stockTree.search(stockId);
        if(stock == null) {
          throw new IllegalArgumentException("Can't delete nonexistent stock");
        }
        this.stockTree.delete(stockId);
        this.stockList.delete(this.priceIdKeyManager.extractRawKey(stock));
    }

    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        if(priceDifference == 0) {
            throw new IllegalArgumentException("Price difference must be nonzero");
        }
        Stock stock = this.stockTree.search(stockId);
        if(stock == null) {
            throw new IllegalArgumentException("Stock not found");
        }
        FloatString oldKey = this.priceIdKeyManager.extractRawKey(stock);
        stock.updatePrice(new StockUpdate(priceDifference, timestamp));
        this.stockList.reassignKey(oldKey);
    }

    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
        Stock stock = this.stockTree.search(stockId);
        if(stock == null) {
            throw new IllegalArgumentException("Stock not found");
        }
        return stock.getPrice();
    }

    // 6. Remove a specific timestamp from a stock's history
    public void removeStockTimestamp(String stockId, long timestamp) {
        Stock stock = this.stockTree.search(stockId);
        if(stock == null) {
            throw new IllegalArgumentException("Stock not found");
        }
        stock.RemoveUpdate(timestamp);
    }

    // 7. Get the amount of stocks in a given price range
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        FloatString lowerDummy, upperDummy;
        lowerDummy = new FloatString(price1, this.stockTree.getMin().getId());
        upperDummy = new FloatString(price2, this.stockTree.getMax().getId());
       return this.stockList.countValuesInRange(lowerDummy, upperDummy);

    }

    // 8. Get a list of stock IDs within a given price range
    public String[] getStocksInPriceRange(Float price1, Float price2) {
        int size = this.getAmountStocksInPriceRange(price1, price2);
        Stock[] stocksInRange = this.stockList.getValuesFromBound( new FloatString(price1,
                this.stockTree.getMin().getId()),new Stock[size]);


        int len = stocksInRange.length;
        String[] stockIds = new String[len];
        for (int i = 0; i < len; i++) {
            stockIds[i] = stocksInRange[i].getId();
        }
        return stockIds;
    }

}


