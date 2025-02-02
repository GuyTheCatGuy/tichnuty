package main;
import data_structures.*;
import data_structures.efficient_doubly_linked_list.EfficientDoublyLinkedList;
import data_structures.key_management.InfiniteKey;
import data_structures.two_three_tree.TwoThreeNode;
import data_structures.two_three_tree.TwoThreeTree;
import main.key_management.FloatString;
import main.key_management.NodeStockPriceIdManager;
import main.key_management.StockIdManager;
import main.key_management.UpdateTimestampExtractor;

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
        Stock stock = new Stock(stockId, new StockUpdate(price, timestamp));
        this.stockTree.insert(stock);

        this.stockList.insert(stock);

    }

    // delete
    public void printList(){
        this.stockList.print();
    }
    //





    // 3. Remove a stock
    public void removeStock(String stockId) {
        Stock stock = this.stockTree.search(stockId);
        this.stockTree.delete(stockId);
        this.stockList.delete(this.priceIdKeyManager.extractRawKey(stock));
        System.out.println("searching for the removed in tree: " + this.stockTree.search(stockId));
        //System.out.println("searching for the removed in list: " + this.stockList.search(stockId));


    }

    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        Stock stock = this.stockTree.search(stockId);
        FloatString oldKey = this.priceIdKeyManager.extractRawKey(stock);
        stock.updatePrice(new StockUpdate(priceDifference, timestamp));
        this.stockList.reassignKey(oldKey);
    }

    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
        Stock stock = this.stockTree.search(stockId);
        return stock.getPrice();
    }

    //delete
    public void printTree(){
        this.stockTree.print();
    }

    public void printListTree() {
        this.stockList.printTree();
    }
    //

    // 6. Remove a specific timestamp from a stock's history
    public void removeStockTimestamp(String stockId, long timestamp) {
        Stock stock = this.stockTree.search(stockId);
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
            //delete
            System.out.println(stocksInRange[i].getPrice() + stocksInRange[i].getId());
            stockIds[i] = stocksInRange[i].getId();
        }
        return stockIds;
    }

}


