package controller;


/**
 * interface for the controller to control the Model and handle user inputs.
 */
public interface IController {
  /**
   * controls the program by sending and initiating inputs.
   *
   * @throws IllegalStateException throws an exception if the state of the input is invalid.
   */
  public void control() throws IllegalStateException;

  /**
   * Writes messages to the user interface.
   *
   * @param message the message that has to be written.
   * @throws IllegalStateException throws an exception if an invalid argument or input is given.
   */
  public void writeMessage(String message) throws IllegalStateException;

  /**
   * The welcome message to start the program.
   *
   * @throws IllegalStateException throws an exception with an illegal state is caught.
   */
  public void welcomeMessage() throws IllegalStateException;

  /**
   * print all the options for actions to do in the program.
   *
   * @throws IllegalStateException throws an illegal state exception when an invalid inputs Caught.
   */
  public void printOptions() throws IllegalStateException;

  /**
   * The farewell message for the program.
   *
   * @throws IllegalStateException throws an exception if an invalid input occurs.
   */
  public void farewellMessage() throws IllegalStateException;
}
