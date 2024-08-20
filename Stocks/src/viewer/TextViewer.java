package viewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.IController;
import controller.ModelImplController;
import controller.PortfolioImplController;
import model.IModel;
import model.IModelMacroImpl;
import model.IModelWithMacro;
import model.ModelImpl;

/**
 * The viewer class for the program and model.
 */
public class TextViewer {

  /**
   * Main method for the stock program that initializes the program and runs it.
   *
   * @param args the args in the program.
   */
  public static void main(String[] args) {
    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
    try {
      while (true) {
        System.out.println("Which Program interface would you like to use? Stocks/Portfolios");
        String program = userInput.readLine();
        if (program.equalsIgnoreCase("Stocks")) {
          runStocksProgram(userInput);
          break;
        } else if (program.equalsIgnoreCase("Portfolios")) {
          runPortfoliosProgram(userInput);
          break;
        } else {
          System.out.println("Invalid option. Please enter 'Stocks' or 'Portfolios'.");
        }
      }
    } catch (IOException e) {
      System.err.println("An error occurred while reading input: " + e.getMessage());
    } finally {
      try {
        userInput.close();
      } catch (IOException e) {
        System.err.println("Failed to close input reader: " + e.getMessage());
      }
    }
  }

  private static void runStocksProgram(BufferedReader userInput) {
    try {
      System.out.println("Enter the name of the ticker you would like to use");
      String ticker = userInput.readLine();
      IModelWithMacro model = new IModelMacroImpl(new ModelImpl(ticker));
      runController(new ModelImplController(model, userInput, System.out));
    } catch (IOException e) {
      System.out.println("Invalid ticker");
    }
  }

  private static void runPortfoliosProgram(BufferedReader userInput) {
    IModel m = new ModelImpl();
    IModelWithMacro model = new IModelMacroImpl(m);
    runController(new PortfolioImplController(model, userInput, System.out));
  }

  private static void runController(IController controller) {
    controller.control();
  }
}