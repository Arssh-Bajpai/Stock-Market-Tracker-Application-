package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.IModelWithMacro;
import model.PerformanceOverTimeImpl;
import model.PortfolioMacroImpl;

/**
 * Controller for the PortfolioImpl class.
 */
public class PortfolioImplController implements IController {
  private Readable readable;
  private Appendable appendable;
  private IModelWithMacro model;

  /**
   * Constructor for the PortfolioImplController.
   *
   * @param model      the current model being controlled and run through.
   * @param readable   the readable that takes in interface input.
   * @param appendable the appendable that takes the user input.
   */
  public PortfolioImplController(IModelWithMacro model, Readable readable, Appendable appendable) {
    if ((model == null) || (readable == null) || (appendable == null)) {
      throw new IllegalArgumentException("model, readable, or appendable is null");
    }
    this.model = model;
    this.appendable = appendable;
    this.readable = readable;
  }

  @Override
  public void control() throws IllegalStateException {
    Scanner sc = new Scanner(readable);
    boolean quit = false;
    this.welcomeMessage();

    while (!quit && sc.hasNext()) {
      writeMessage(System.lineSeparator() + "Type command: ");
      String command = sc.next();
      if (command.equals("quit") || command.equals("q")) {
        quit = true;
      } else {
        userCommands(command, sc, model);
      }
    }
    this.farewellMessage();
  }

  private void userCommands(String command, Scanner sc, IModelWithMacro model) {
    String name;
    String ticker;
    String date;
    String startDate;
    String endDate;
    double shares;
    int weight;
    sc.nextLine();
    switch (command) {
      case "Create-Portfolios":
        try {
          writeMessage("Enter name of the to be created portfolio, " +
                  "no special characters or numbers: ");
          name = sc.nextLine();
          writeMessage("Creating Portfolio..."
                  + System.lineSeparator());
          model.execute(new PortfolioMacroImpl(), "createPortfolios", name);
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "Buy-Shares":
        try {
          writeMessage("Enter name of portfolio to Buy in:  ");
          name = sc.nextLine();
          writeMessage("Enter valid ticker stock symbol: ");
          ticker = sc.nextLine();
          writeMessage("Enter a date in YYYY-MM-DD: ");
          date = sc.nextLine();
          writeMessage("Enter number of shares to buy please add a trailing .0 if an Integer: ");
          shares = sc.nextDouble();
          sc.nextLine();
          writeMessage("Buying " + shares + " Shares of " + ticker
                  + System.lineSeparator());
          model.execute(new PortfolioMacroImpl(), "buyShares", name, ticker, date, shares);
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "Sell-Shares":
        try {
          writeMessage("Enter name of portfolio to Sell out:  ");
          name = sc.nextLine();
          writeMessage("Enter valid ticker stock symbol: ");
          ticker = sc.nextLine();
          writeMessage("Enter a date in YYYY-MM-DD: ");
          date = sc.nextLine();
          writeMessage("Enter number of shares to sell please add a trailing .0 if an Integer: ");
          shares = sc.nextDouble();
          sc.nextLine();
          writeMessage("Selling " + shares + " Shares of " + ticker + System.lineSeparator());
          model.execute(new PortfolioMacroImpl(), "sellShares", name, ticker, date, shares);
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "Portfolio-Value":
        try {
          writeMessage("Enter the name of the portfolio you would like to evaluate: ");
          name = sc.nextLine();
          writeMessage("Enter the date you would like it to be evaluated on in YYYY-MM-DD: ");
          date = sc.nextLine();
          double value = (double) model.execute(new PortfolioMacroImpl(),
                  "portfolioValue", name, date);
          writeMessage("$" + value + System.lineSeparator());
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "Composition":
        try {
          writeMessage("Enter the name of the portfolio you would like to compose: ");
          name = sc.nextLine();
          writeMessage("Enter the date you would like it to be composed on in YYYY-MM-DD: ");
          date = sc.nextLine();
          String composition = (String) model.execute(new PortfolioMacroImpl(),
                  "composition", name, date);
          writeMessage(composition + System.lineSeparator());
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "Distribution":
        try {
          writeMessage("Enter the name of the portfolio you would like to distribute: ");
          name = sc.nextLine();
          writeMessage("Enter the date you would like it to be distributed on in YYYY-MM-DD: ");
          date = sc.nextLine();
          String distribution = (String) model.execute(new PortfolioMacroImpl(),
                  "distribution", name, date);
          writeMessage(distribution + System.lineSeparator());
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "Rebalance":
        try {
          ArrayList<Integer> weights = new ArrayList<>();
          writeMessage("Enter the name of the portfolio you would like to rebalance: ");
          name = sc.nextLine();
          writeMessage("Enter the date you would like it to be rebalanced in YYYY-MM-DD: ");
          date = sc.nextLine();
          boolean stop = false;
          writeMessage(System.lineSeparator());
          writeMessage("Please Enter a valid Integer weight for these stocks"
                  + System.lineSeparator()
                  + "with each weight corresponding to the stocks order"
                  + System.lineSeparator() + "These weights must be integer values " +
                  "and add up to a total of 100" + System.lineSeparator() +
                  "do not include any special symbols such as % in your weights enter 'done'" +
                  "when your finished adding weights and press enter after every weight " +
                  "to add another");
          while (!stop) {
            writeMessage("Enter the weight: ");
            try {
              String input = sc.nextLine();
              if (input.equalsIgnoreCase("done")) {
                stop = true;
              } else {
                weight = Integer.parseInt(input);
                weights.add(weight);
              }
            } catch (NumberFormatException e) {
              writeMessage("Please enter a valid integer weight." + System.lineSeparator());
            }
          }
          String result = (String) model.execute(new PortfolioMacroImpl(),
                  "rebalance", name, date, weights);
          writeMessage(result + System.lineSeparator());
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "Price-Over-Time":
        try {
          writeMessage("Enter the name of the portfolio you would like to visualize: ");
          name = sc.nextLine();
          writeMessage("Enter the Start date in YYYY-MM-DD: ");
          startDate = sc.nextLine();
          writeMessage("Enter the End date in YYYY-MM-DD: ");
          endDate = sc.nextLine();
          String result = (String) model.execute(new PerformanceOverTimeImpl(),
                  "visualize", name, startDate, endDate);
          writeMessage(result + System.lineSeparator());
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "menu":
        printOptions();
        break;
      default:
        writeMessage("Undefined instruction: " + command + System.lineSeparator());
    }
  }

  @Override
  public void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void printOptions() throws IllegalStateException {
    writeMessage("Supported user instructions are: " + System.lineSeparator());
    writeMessage("Create-Portfolios: creates a new Portfolio "
            + System.lineSeparator());
    writeMessage("Buy-Shares: buy shares for an existing portfolio"
            + System.lineSeparator());
    writeMessage("Sell-Shares: sell shares for an existing portfolio"
            + System.lineSeparator());
    writeMessage("Portfolio-Value: Get the total value of an existing" +
            "portfolio" + System.lineSeparator());
    writeMessage("Composition: Get the total composition of an existing" +
            "portfolio" + System.lineSeparator());
    writeMessage("Distribution: Get the value distribution of an existing" +
            "portfolio" + System.lineSeparator());
    writeMessage("Rebalance: rebalance an existing portfolios values to weights" +
            "" + System.lineSeparator());
    writeMessage("Price-Over-Time: gets the price over a time period of an existing portofolio" +
            "" + System.lineSeparator());
    writeMessage("menu: (Print supported " +
            "instruction list)" + System.lineSeparator());
    writeMessage("q or quit: (quit the program) ");
  }

  @Override
  public void welcomeMessage() throws IllegalStateException {
    writeMessage("Welcome to the Portfolio manager program" + System.lineSeparator());
    printOptions();
  }

  @Override
  public void farewellMessage() throws IllegalStateException {
    writeMessage(System.lineSeparator() + "Thank you for using this program");
  }
}