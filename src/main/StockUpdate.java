package main;

public class StockUpdate {
    private final Float difference;
    private final Long timestamp;
    public StockUpdate(Float difference, Long timestamp) {
        this.difference = difference;
        this.timestamp = timestamp;
    }
    public Float getDifference() {
        return difference;
    }
    public Long getTimestamp() {
        return timestamp;
    }

}
