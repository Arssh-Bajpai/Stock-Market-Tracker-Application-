package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.IModel;

/**
 * controller for the ModelImpl class.
 */
public class ModelImplController implements IController {
  private Readable readable;
  private Appendable appendable;
  private IModel model;

  /**
   * Constructor for the ModelImplController.
   *
   * @param model      the current model being controlled and run through.
   * @param readable   the readable that takes in interface input.
   * @param appendable the appendable that takes the the user input.
   */
  public ModelImplController(IModel model, Readable readable, Appendable appendable) {
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

  private void userCommands(String command, Scanner sc, IModel model) {
    ArrayList<String> stocks;
    String startStock;
    String endStock;
    String ticker;
    String more;
    String name;
    boolean stop;
    int days;

    sc.nextLine();

    switch (command) {
      case "Returns":
        try {
          writeMessage("Enter start date in YYYY-MM-DD format: ");
          startStock = sc.nextLine();
          writeMessage("Enter end date in YYYY-MM-DD format: ");
          endStock = sc.nextLine();
          writeMessage("Getting Returns (" + startStock + ", " + endStock + ")"
                  + System.lineSeparator());
          writeMessage(model.getReturns(startStock, endStock) + System.lineSeparator());
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "Moving-Average":
        try {
          writeMessage("Enter date in YYYY-MM-DD format: ");
          startStock = sc.nextLine();
          writeMessage("Enter x day period: ");
          days = sc.nextInt();
          sc.nextLine();  // Consume newline left-over
          writeMessage("Getting Moving Average (" + startStock + ", " + days + ")"
                  + System.lineSeparator());
          writeMessage(model.movingAverage(startStock, days) + System.lineSeparator());
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "Crossovers":
        try {
          writeMessage("Enter Start date in YYYY-MM-DD: ");
          startStock = sc.nextLine();
          writeMessage("Enter End date in YYYY-MM-DD: ");
          endStock = sc.nextLine();
          writeMessage("Enter x day period: ");
          days = sc.nextInt();
          sc.nextLine();  // Consume newline left-over
          writeMessage("Getting crossovers (" + startStock + ", " + endStock + ", "
                  + days + ")" + System.lineSeparator());
          writeMessage(model.crossovers(startStock, endStock, days) + System.lineSeparator());
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
    writeMessage("Returns: get returns over a period of time using start and end dates "
            + System.lineSeparator());
    writeMessage("Moving-Average: get the moving avg at a date for a certain x number of days"
            + System.lineSeparator());
    writeMessage("Crossovers: Get which days are crossovers for a given x number " +
            "of days within a range" + System.lineSeparator());
    writeMessage("menu: (Print supported " +
            "instruction list)" + System.lineSeparator());
    writeMessage("q or quit: (quit the program) ");
  }

  @Override
  public void welcomeMessage() throws IllegalStateException {
    writeMessage("Welcome to the Stock checker program" + System.lineSeparator());
    printOptions();
  }

  @Override
  public void farewellMessage() throws IllegalStateException {
    writeMessage(System.lineSeparator() + "Thank you for using this program");
  }
}