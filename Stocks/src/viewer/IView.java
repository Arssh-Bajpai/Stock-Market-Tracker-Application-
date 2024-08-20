package viewer;

import java.awt.event.ActionListener;

/**
 * The viewer interface a GUI focused interface model to help
 * communicate methods from the Viewer to the Controller.
 */
public interface IView {
  /**
   * displays the GUI.
   */
  void display();

  /**
   * displays the output from an action event in the text frame.
   *
   * @param s what to display
   */
  void setEchoOutput(String s);

  /**
   * gets the inputString from the Shares text box.
   *
   * @return the number of shares.
   */

  String getInputString();

  /**
   * clears the input string in the shares text box.
   */
  void clearInputString();

  /**
   * listens for action events.
   *
   * @param listener what to listen for.
   */
  void setListener(ActionListener listener);

  /**
   * gets the ticker inputted by the user.
   *
   * @return ticker symol inputted by user.
   */
  public String getTickerSymbol();

  /**
   * returns the chosen portfolio.
   *
   * @return the string name of the portfolio.
   */
  public String getSelectedPortfolio();

  /**
   * gets the selected date to view the portfolio.
   *
   * @return the date as a string.
   */
  public String getSelectedDate();

  /**
   * clears the output in the TextBox Viewer.
   */
  public void clearOutput();

  /**
   * displays the output from an action event.
   *
   * @param message the message to display.
   */
  public void displayOutput(String message);

  /**
   * gets the created Portfolio that the user inputted.
   *
   * @return the string name of the created portfolio.
   */
  public String getCreatedPortfolioName();
}