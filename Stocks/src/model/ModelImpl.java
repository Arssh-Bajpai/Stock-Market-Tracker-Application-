package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents the implemented model for a model.Stock.
 */
public class ModelImpl implements IModel {
  private ArrayList<Stock> stocks;
  private String ticker;

  /**
   * constructor the implementation of the Model implemntation.
   *
   * @param ticker name of the stock.
   */
  public ModelImpl(String ticker) {
    this.stocks = new ArrayList<>();
    this.ticker = ticker;
    try {
      connectURL(ticker);
    } catch (IllegalStateException e) {
      defaultFile();
    }
  }

  /**
   * Default constructor for the ModelImpl.
   */
  public ModelImpl() {
    this.ticker = "none";
    this.stocks = new ArrayList<>();
  }

  private void connectURL(String ticker) {
    String apiKey = "RATYZJGMVMNFCAOF";
    URL url;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("The Alphavantage API has either changed or no longer works.");
    }

    try (InputStream in = url.openStream()) {
      StringBuilder output = new StringBuilder();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      String[] lines = output.toString().split("\n");
      ArrayList<String> fileLines = new ArrayList<>(Arrays.asList(lines));
      fileLines.remove(0);
      for (String s : fileLines) {
        try {
          stocks.add(new Stock(s));
        } catch (ArrayIndexOutOfBoundsException e) {
          defaultFile();
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + ticker);
    }
  }

  private void defaultFile() {
    System.out.println("Cannot find stock or API is down please enjoy our local data of " +
            "the GOOG stock");
    this.ticker = "GOOG";
    try (BufferedReader file = new BufferedReader(new FileReader("GOOG.csv"))) {
      file.readLine();
      String line;
      while ((line = file.readLine()) != null) {
        stocks.add(new Stock(line));
      }
    } catch (FileNotFoundException e) {
      System.out.println("Error: Default file does not exist");
    } catch (IOException e) {
      System.out.println("Error reading default file");
    }
  }

  @Override
  public double getReturns(String start, String end) throws IllegalArgumentException {
    Stock startStock = null;
    Stock endStock = null;
    for (Stock s : stocks) {
      String time = s.getTimestamp();
      if (time.equals(start)) {
        startStock = s;
      } else if (time.equals(end)) {
        endStock = s;
      }
    }
    if (startStock == null || endStock == null) {
      throw new IllegalArgumentException("Please enter valid dates in the correct format");
    }
    return endStock.getClose() - startStock.getClose();
  }

  @Override
  public double movingAverage(String date, int days) throws IllegalArgumentException {
    double total = 0;
    int period = 0;
    if (!validDate(date)) {
      throw new IllegalArgumentException("invalid date entered");
    }
    for (int i = 0; i < stocks.size(); i++) {
      String time = stocks.get(i).getTimestamp();
      if (time.equals(date)) {
        for (int a = i; a > (i - days); a--) {
          if (a == 0) {
            total = total + stocks.get(a).getClose();
            period = period + 1;
            return Math.round((total / period) * 100.0) / 100.0;
          } else {
            period = period + 1;
            total = total + stocks.get(a).getClose();
          }
        }
      }
    }
    return Math.round((total / period) * 100.0) / 100.0;
  }


  public boolean validDate(String date) {
    return stocks.stream().anyMatch(s -> s.getTimestamp().equals(date));
  }

  private boolean validPeriod(String start, String end) {
    return stocks.stream().anyMatch(s -> s.getTimestamp().equals(start)) &&
            stocks.stream().anyMatch(s -> s.getTimestamp().equals(end));
  }

  @Override
  public ArrayList<String> crossovers(String start, String end, int days)
          throws IllegalArgumentException {
    ArrayList<String> crossovers = new ArrayList<>();
    if (!validPeriod(start, end)) {
      throw new IllegalArgumentException("Enter valid date range in correct format");
    }
    for (int i = 0; i < stocks.size(); i++) {
      String time = stocks.get(i).getTimestamp();
      if (time.equals(start)) {
        for (int a = i; a >= 0; a--) {
          if (stocks.get(a).getClose() > movingAverage(stocks.get(a).getTimestamp(), days)) {
            if (!crossovers.contains(stocks.get(a).getTimestamp())) {
              crossovers.add(stocks.get(a).getTimestamp());
            }
          }
          if (stocks.get(a).getTimestamp().equals(end)) {
            break;
          }
        }
      }
    }
    return crossovers;
  }

  public ArrayList<Stock> getStocks() {
    return stocks;
  }
}