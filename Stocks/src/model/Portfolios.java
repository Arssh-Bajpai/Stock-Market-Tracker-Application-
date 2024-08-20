package model;

import java.util.ArrayList;

/**
 * the Portfolios interface all of the macros having to do with portfolios in one inter.
 */
public interface Portfolios extends IModelMacro {
  /**
   * creates a portfolio.
   *
   * @param name name of the portfolio.
   */
  public void createPortfolios(String name);

  /**
   * adds to a portfolio.
   *
   * @param name   name of the portfolio.
   * @param ticker name of the stock to buy.
   * @param date   date to buy.
   * @param shares number of shares to buy.
   */
  public void buyShares(String name, String ticker, String date, double shares);

  /**
   * the number of shares to sell from a portfolio.
   *
   * @param name   name of the portfolio to sell from.
   * @param ticker name of the stock to sell.
   * @param date   date to sell the shares.
   * @param shares number of shares to sell.
   */
  public void sellShares(String name, String ticker, String date, double shares);

  /**
   * gets the value of a portfolio on a given date.
   *
   * @param name name of the portfolio.
   * @param date date to get value.
   * @return the value of the portfolio.
   */
  public double portfolioValue(String name, String date);

  /**
   * what stocks and how many are held in a portfolio at a certain point in time.
   *
   * @param name name of the portfolio.
   * @param date date to get compostion.
   * @return the composition of the portfolio as a string.
   */
  public String composition(String name, String date);

  /**
   * gets the name value of each stock and its value and value of the total portfolio.
   *
   * @param name name of the portfolio
   * @param date date to get the evaluation.
   * @return the evaluation of the portfolio.
   */
  public String distribution(String name, String date);

  /**
   * Rebalances a portfolio to the right weights needed.
   *
   * @param name    name of the portfolio to rebalance.
   * @param date    date on which to get the value to rebalance.
   * @param weights weights of each stock to have rebalance.
   * @return a strin representation of the rebalanced portfolio.
   */
  public String rebalance(String name, String date, ArrayList<Integer> weights);
}
