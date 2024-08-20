package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Implementation of the Portfolio Macro and its methods.
 */
public class PortfolioMacroImpl implements Portfolios {
  //directory path where all the portflios are saved.
  private final String directory = "/Users/arsshbajpai/Desktop/OOD/Stocks/Portfolios";
  private ArrayList<PortfolioCurStock> portfolioState = new ArrayList<PortfolioCurStock>();

  //creates a data to append into a json object.
  private static void addData(StringBuilder stock, String key, Object val) {
    stock.append("\"").append(key).append("\":");
    if (val instanceof Date) {
      stock.append(val);
    } else if (val instanceof Double) {
      stock.append(val);
    } else if (val instanceof String) {
      stock.append("\"").append(val).append("\"");
    }
    stock.append(",");
  }

  //parses the data within a json object and returned into a usable data object of
  // PortfolioStockLog.
  private static PortfolioStockLog parseJsonLine(String line) throws ParseException {
    line = line.substring(1, line.length() - 1);
    String name = "";
    String ticker = "";
    Date date = null;
    double shares = 0;

    String[] keyValuePairs = line.split(",");
    for (String pair : keyValuePairs) {
      String[] entry = pair.split(":", 2);
      String key = entry[0].trim().replace("\"", "");
      String value = entry[1].trim();

      switch (key) {
        case "name":
          name = value.substring(1, value.length() - 1);
          break;
        case "stock":
          ticker = value.substring(1, value.length() - 1);
          break;
        case "date":
          SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
          date = dateFormat.parse(value);
          break;
        case "number":
          shares = Double.parseDouble(value);
          break;
        default:
          System.out.println("ineligible statement");
          break;
      }
    }
    return new PortfolioStockLog(name, ticker, date, shares);
  }

  //reads the lines of a json object and puts them all into an array to be parsed through and
  //analyzed
  private static String[] readJsonFile(String filePath) {
    ArrayList<String> lines = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines.toArray(new String[0]);
  }

  @Override
  public void createPortfolios(String name) throws IllegalArgumentException {
    String file = name + ".json";
    if (!fileExists(file)) {
      File newPortfolio = new File(directory, file);
      try (FileWriter writer = new FileWriter(newPortfolio)) {
        writer.write("");
        writer.close();
      } catch (IOException e) {
        System.out.println("This Portfolio already exists");
      }
    } else {
      throw new IllegalArgumentException("This file already exists");
    }
  }

  //validation method to check for IllegalArgument Throws involving file existence.
  public boolean fileExists(String name) {
    Path portfolioPath = Paths.get(directory, name);
    return Files.exists(portfolioPath);
  }

  @Override
  public void buyShares(String name, String ticker, String date, double shares) {
    ModelImpl model = new ModelImpl(ticker);
    if (model.validDate(date) && shares >= 0) {
      shareHandle(name, ticker, date, shares, true);
    } else {
      throw new IllegalArgumentException("Enter valid date or nonnegative shares or a valid file");
    }
  }

  @Override
  public void sellShares(String name, String ticker, String date, double shares) {
    ModelImpl model = new ModelImpl(ticker);
    if (model.validDate(date) && shares >= 0) {
      double availableShares = getAvailableShares(name, ticker, date);
      if (availableShares >= shares) {
        shareHandle(name, ticker, date, shares, false);
      } else {
        throw new IllegalArgumentException("Not enough shares to sell");
      }
    } else {
      throw new IllegalArgumentException("Enter valid date or nonnegative shares or a valid file");
    }
  }

  //gets the number of shares that are "available" so the user cant sell more than they have bought.
  private double getAvailableShares(String name, String ticker, String date) {
    String[] portfolio = readJsonFile(directory + "/" + name + ".json");
    ArrayList<PortfolioStockLog> stockLog = new ArrayList<>();
    SimpleDateFormat buyDate = new SimpleDateFormat("yyyy-MM-dd");
    Date parseDate = null;
    double totalShares = 0;

    try {
      parseDate = buyDate.parse(date);
      for (String line : portfolio) {
        PortfolioStockLog log = parseJsonLine(line);
        if (log.getTickers().equals(ticker) && !parseDate.before(log.getDate())) {
          totalShares += log.getShares();
        }
      }
    } catch (ParseException e) {
      System.out.println("problem parsing through JSON file");
    }
    return totalShares;
  }

  //handles logging the buying and selling of shares into the json portfolio.
  private void shareHandle(String name, String ticker, String date, double shares, boolean buys) {
    IModel model = new ModelImpl(ticker);
    StringBuilder bought = new StringBuilder();
    double buySell = shares;
    if (!buys) {
      buySell = buySell * -1;
    }
    SimpleDateFormat buyDate = new SimpleDateFormat("yyyy-MM-dd");
    if (fileExists(name + ".json")) {
      try {
        bought.append("{");
        addData(bought, "name", name);
        addData(bought, "stock", ticker);
        addData(bought, "date", buyDate.parse(date));
        addData(bought, "number", buySell);
        bought.append("}");
        FileWriter writer = new FileWriter(directory + "/" + name + ".json", true);
        writer.write(bought.toString());
        writer.write(System.lineSeparator());
        writer.close();
      } catch (IOException e) {
        System.out.println("The Shares being bought failed");
      } catch (ParseException e) {
        System.out.println("invalid buy date or format");
      }
    } else {
      throw new IllegalStateException();
    }
  }

  @Override
  public double portfolioValue(String name, String date) {
    String[] portfolio = readJsonFile(directory + "/" + name + ".json");
    ArrayList<PortfolioStockLog> stockLog = new ArrayList<PortfolioStockLog>();
    SimpleDateFormat buyDate = new SimpleDateFormat("yyyy-MM-dd");
    Date parseDate = null;
    double total = 0;
    if (!isValidDate(date)) {
      throw new IllegalArgumentException("Invalid Date");
    }
    try {
      parseDate = buyDate.parse(date);
      for (int i = 0; i < portfolio.length; i++) {
        stockLog.add(parseJsonLine(portfolio[i]));
      }
    } catch (ParseException e) {
      System.out.println("problem parsing through JSON file");
    }
    for (int a = 0; a < stockLog.size(); a++) {
      ModelImpl model = new ModelImpl(stockLog.get(a).getTickers());
      ArrayList<Stock> stocks = model.getStocks();
      for (Stock s : stocks) {
        if (s.getTimestamp().equals(date)) {
          if (parseDate.toInstant().isAfter(stockLog.get(a).getDate().toInstant())
                  || parseDate.toInstant().equals(stockLog.get(a).getDate().toInstant())) {
            total += s.getClose() * stockLog.get(a).getShares();
          }
        }
      }
    }
    return Math.round(total * 100.0) / 100.0;
  }

  /**
   * gets the validity of a date string.
   *
   * @param dateStr the date string being validated.
   * @return returns a boolean that shows whether the string is a valid input or not.
   */
  public boolean isValidDate(String dateStr) {
    return true;
  }

  @Override
  public String composition(String name, String date) {
    String[] portfolio = readJsonFile(directory + "/" + name + ".json");
    ArrayList<PortfolioStockLog> stockLog = new ArrayList<>();
    SimpleDateFormat buyDate = new SimpleDateFormat("yyyy-MM-dd");
    Date parseDate = null;
    if (!isValidDate(date)) {
      throw new IllegalArgumentException("Invalid Date or nonexistant file");
    }
    StringBuilder stock = new StringBuilder();
    double total = 0;
    try {
      parseDate = buyDate.parse(date);
      for (int i = 0; i < portfolio.length; i++) {
        stockLog.add(parseJsonLine(portfolio[i]));
      }
    } catch (ParseException e) {
      System.out.println("Problem parsing date: " + date);
      return "Invalid date format.";
    }

    for (int a = 0; a < stockLog.size(); a++) {
      PortfolioStockLog log = stockLog.get(a);
      if (!parseDate.before(log.getDate())) {
        ModelImpl model = new ModelImpl(log.getTickers());
        ArrayList<Stock> stocks = model.getStocks();
        for (Stock s : stocks) {
          if (s.getTimestamp().equals(date)) {
            double stockPriceOnDate = s.getClose();
            stock.append(log.getTickers() + ":" + log.getShares() + ":" +
                    (log.getShares() * stockPriceOnDate) + System.lineSeparator());
            break;
          }
        }
      }
    }
    compileStocks(stock.toString());
    return compileStocks(stock.toString());
  }

  //compiles the stocks together so that all the logging in the json object can be presented
  // cleanly.
  private String compileStocks(String stock) {
    StringBuilder ported = new StringBuilder();
    String[] stocks = stock.split("\n");
    HashSet<String> tickersUniq = new HashSet<>();
    ArrayList<PortfolioCurStock> stockLine = new ArrayList<>();
    for (int i = 0; i < stocks.length; i++) {
      String ticker = stocks[i].substring(0, 4);
      if (!tickersUniq.contains(ticker)) {
        double value = 0;
        double sharePrice = 0.0;
        for (int j = 0; j < stocks.length; j++) {
          if (stocks[j].substring(0, 4).equals(ticker)) {
            int firstCol = stocks[j].indexOf(":");
            int secCol = stocks[j].indexOf(":", firstCol + 1);
            value += Double.parseDouble(stocks[j].substring(firstCol + 1, secCol));
            sharePrice += Double.parseDouble(stocks[j].substring(secCol + 1));
          }
        }
        stockLine.add(new PortfolioCurStock(ticker, value, sharePrice));
        tickersUniq.add(ticker);
      }
    }
    for (PortfolioCurStock stockEntry : stockLine) {
      ported.append(stockEntry.getStockName() + " : " + stockEntry.getShares() + " : "
              + stockEntry.getSharePrice() + System.lineSeparator());
    }
    portfolioState = stockLine;
    return ported.toString();
  }

  @Override
  public String distribution(String name, String date) {
    double value = portfolioValue(name, date);
    String compose = composition(name, date);
    StringBuilder analysis = new StringBuilder();
    analysis.append(compose);
    analysis.append("Total Value: " + value);
    return analysis.toString();
  }

  @Override
  public String rebalance(String name, String date, ArrayList<Integer> weights) {
    String compose = composition(name, date);
    StringBuilder port = new StringBuilder();
    double val = portfolioValue(name, date);
    try (FileWriter fileWriter = new FileWriter(directory + "/" + name + ".json")) {
      fileWriter.write("");
      if (validWeights(weights, compose)) {
        for (int i = 0; i < portfolioState.size(); i++) {
          port.append(refactorPort(val, portfolioState.get(i), weights.get(i), name, date)
                  + System.lineSeparator());
        }
        port.append("Total Value: " + portfolioValue(name, date));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return port.toString();
  }

  //turns the percentage weights into decimals and gets the price of the stock and rebalances the
  //portfolio one at a time.
  private String refactorPort(double total, PortfolioCurStock stock, double weight,
                              String name, String date) {
    double weightedVal = weight / 100.0;
    //price the value of each stock after rebalance.
    double price = Math.round((total * weightedVal) * 100.0) / 100.0;
    double sharePrice = stock.getSharePrice() / stock.getShares();
    //actPrice the number of shares after rebalance
    double actPrice = Math.round((price / sharePrice) * 100.0) / 100.0;
    buyShares(name, stock.getStockName(), date, actPrice);
    return stock.getStockName() + " : " + actPrice + " : " + price;
  }

  //validates whether the weights are valid parameters in the rebalancing.
  private boolean validWeights(ArrayList<Integer> weights, String stocks) {
    double total = 0;
    String[] stock = stocks.split("\n");
    for (Integer weight : weights) {
      total += weight;
    }
    if (total != 100 || stock.length != weights.size()) {
      throw new IllegalArgumentException("The weights for this class don't add up to 100 " +
              "or a wrong number of weights were inputted");
    }
    return true;
  }

  @Override
  public Object execute(String method, Object... params) {
    switch (method) {
      case "createPortfolios":
        if (params.length == 1 && params[0] instanceof String) {
          createPortfolios((String) params[0]);
          return null;
        } else {
          throw new IllegalArgumentException("Invalid arguments for createPortfolios");
        }
      case "buyShares":
        if (params.length == 4 && params[0] instanceof String && params[1] instanceof String &&
                params[2] instanceof String && params[3] instanceof Double) {
          buyShares((String) params[0], (String) params[1], (String) params[2],
                  (Double) params[3]);
          return null;
        } else {
          throw new IllegalArgumentException("Invalid arguments for buyShares");
        }
      case "sellShares":
        if (params.length == 4 && params[0] instanceof String && params[1] instanceof String &&
                params[2] instanceof String && params[3] instanceof Double) {
          sellShares((String) params[0], (String) params[1], (String) params[2],
                  (Double) params[3]);
          return null;
        } else {
          throw new IllegalArgumentException("Invalid arguments for sellShares");
        }
      case "portfolioValue":
        if (params.length == 2 && params[0] instanceof String && params[1] instanceof String) {
          double value = portfolioValue((String) params[0], (String) params[1]);
          return value;
          //System.out.println("Portfolio Value: " + value);
        } else {
          throw new IllegalArgumentException("Invalid arguments for portfolioValue");
        }
      case "composition":
        if (params.length == 2 && params[0] instanceof String && params[1] instanceof String) {
          String composition = composition((String) params[0], (String) params[1]);
          return composition;
          //System.out.println("Composition: " + composition);
        } else {
          throw new IllegalArgumentException("Invalid arguments for composition");
        }
      case "distribution":
        if (params.length == 2 && params[0] instanceof String && params[1] instanceof String) {
          String distribution = distribution((String) params[0], (String) params[1]);
          return distribution;
          //System.out.println("Distribution: " + distribution);
        } else {
          throw new IllegalArgumentException("Invalid arguments for distribution");
        }
      case "rebalance":
        if (params.length == 3 && params[0] instanceof String && params[1] instanceof String &&
                params[2] instanceof ArrayList) {
          String rebalanceResult = rebalance((String) params[0], (String) params[1],
                  (ArrayList<Integer>) params[2]);
          return rebalanceResult;
          //System.out.println("Rebalance Result: " + rebalanceResult);
        } else {
          throw new IllegalArgumentException("Invalid arguments for rebalance");
        }
      default:
        throw new IllegalArgumentException("Invalid method name");
    }
  }
}