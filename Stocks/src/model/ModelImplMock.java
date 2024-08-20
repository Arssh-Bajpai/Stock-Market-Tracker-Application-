package model;

import java.util.ArrayList;

/**
 * Mock test for the ModelIMpl class.
 */
public class ModelImplMock implements IModel {

  private ArrayList<Stock> stocks;

  /**
   * constructor for the mock that initalizes the mock.
   *
   * @param ticker the name for the ticker stock.
   */
  public ModelImplMock(String ticker) {
    String tickers = ticker;
    this.stocks = new ArrayList<>();
    mockStockData();
  }

  private void mockStockData() {
    Stock stock1 = new Stock("2024-06-01,100.00,105.00,95.00,100.00");
    Stock stock2 = new Stock("2024-06-06,150.00,155.00,145.00,150.00");
    stocks.add(stock1);
    stocks.add(stock2);
  }

  @Override
  public double getReturns(String start, String end) throws IllegalArgumentException {
    return 50.00;
  }

  @Override
  public double movingAverage(String date, int days) throws IllegalArgumentException {
    return 200.00;
  }

  @Override
  public ArrayList<String> crossovers(String start, String end, int days)
          throws IllegalArgumentException {
    ArrayList<String> dummyCrossovers = new ArrayList<>();
    dummyCrossovers.add("2024-06-06");
    return dummyCrossovers;
  }

}