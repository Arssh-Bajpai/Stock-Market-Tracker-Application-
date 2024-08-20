package model;

/**
 * This class represents a stock.
 */
public class Stock {
  private String ticker;
  private String timestamp;
  private double open;
  private double high;
  private double low;
  private double close;

  /**
   * This contructs a stock.
   */
  public Stock(String stocks) {
    String[] stock = stocks.split(",");
    this.timestamp = stock[0];
    this.open = Double.parseDouble(stock[1]);
    this.high = Double.parseDouble(stock[2]);
    this.low = Double.parseDouble(stock[3]);
    this.close = Double.parseDouble(stock[4]);
  }

  /**
   * This method gets the ticker of the stock.
   */
  public String getTicker() {
    return ticker;
  }

  /**
   * This method gets the timestamps of the stock.
   */
  public String getTimestamp() {
    return timestamp;
  }

  /**
   * This method gets the opening price of the stock.
   */
  public double getOpen() {
    return open;
  }

  /**
   * This method gets the highest price of the stock.
   */
  public double getHigh() {
    return high;
  }

  /**
   * This method gets the lowest price of the stock.
   */
  public double getLow() {
    return low;
  }

  /**
   * This method gets the closing price of the stock.
   */
  public double getClose() {
    return close;
  }

}
