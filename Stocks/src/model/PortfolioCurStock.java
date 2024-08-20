package model;

/**
 * a data class designed to hold the current value of a stock to quickly send the data between
 * objects.
 */
public class PortfolioCurStock {
  private String ticker;
  private double shares;
  private double sharePrice;

  /**
   * constructor for this object to create it.
   *
   * @param ticker     the name of the stock.
   * @param shares     the number of shares this stock has.
   * @param sharePrice the price of each share of this stock at a given point.
   */
  public PortfolioCurStock(String ticker, double shares, double sharePrice) {
    this.ticker = ticker;
    this.shares = shares;
    this.sharePrice = sharePrice;
  }

  /**
   * gets stock name.
   *
   * @return the ticker of the stock.
   */
  public String getStockName() {
    return ticker;
  }

  /**
   * gets the number of shares of a stock.
   *
   * @return the number of shares of a stock.
   */
  public double getShares() {
    return shares;
  }

  /**
   * gets the share price of a stock.
   *
   * @return gives the share price.
   */
  public double getSharePrice() {
    return sharePrice;
  }
}
