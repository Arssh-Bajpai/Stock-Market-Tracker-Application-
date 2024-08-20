package model;

import java.util.Date;

/**
 * concrete data class that holds specific values of a stock so it can be read and translated into
 * json and allows for searching based on date.
 */
public class PortfolioStockLog {
  private String name;
  private String ticker;
  private Date date;
  private double shares;

  /**
   * constructor for this class that creates an object of it.
   *
   * @param name   name of the portfolio.
   * @param ticker name of the stock.
   * @param date   date the stock was purchased.
   * @param shares how many of the object was bought or sold.
   */
  public PortfolioStockLog(String name, String ticker, Date date, double shares) {
    this.name = name;
    this.ticker = ticker;
    this.date = date;
    this.shares = shares;
  }

  /**
   * gets the name of the portfolio.
   *
   * @return name of the portfolio.
   */
  public String getNames() {
    return name;
  }

  /**
   * gets the name of the stock.
   *
   * @return the name of the stock.
   */
  public String getTickers() {
    return ticker;
  }

  /**
   * gets the date of purchase or sale.
   *
   * @return the date of purchase or sale.
   */
  public Date getDate() {
    return date;
  }

  /**
   * number of shares sold or purchased of the stock.
   *
   * @return the number of shares altered.
   */
  public double getShares() {
    return shares;
  }
}
