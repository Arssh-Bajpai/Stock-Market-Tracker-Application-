import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.ModelImplController;
import model.IModel;
import model.ModelImplMock;

import static org.junit.Assert.assertEquals;

/**
 * Test for the Model Controller.
 */
public class ModelImplControllerTest {
  private Readable readable;
  private StringBuilder appendable;
  private ModelImplController controller;
  private IModel model;

  @Before
  public void initialize() {
    model = new ModelImplMock("GOOG");
    readable = new StringReader("");
    appendable = new StringBuilder();
    controller = new ModelImplController(model, readable, appendable);
  }

  @Test
  public void testControl() {
    initialize();

    //test 'q'
    readable = new StringReader("q");
    controller = new ModelImplController(model, readable, appendable);
    controller.control();

    assertEquals("Welcome to the Stock checker program\n"
            + "Supported user instructions are: \n"
            + "Returns: get returns over a period of time using start and end dates \n"
            + "Moving-Average: get the moving avg at a date for a certain x number of days\n"
            + "Crossovers: Get which days are crossovers for a given x number of days within a "
            + "range\n"
            + "Portfolio: Create a new portfolio\n"
            + "Add-to-Portfolio: Add more stocks to an existing portfolio\n"
            + "Portfolio-Value: Get the value of a portfolio at a certain day\n"
            + "menu: (Print supported instruction list)\n"
            + "q or quit: (quit the program) \n"
            + "Type command: \n"
            + "Thank you for using this program", appendable.toString());


    //test 'Returns'
    initialize();
    Reader returns = new StringReader("Returns\n2023-01-01");
    controller = new ModelImplController(model, returns, appendable);
    controller.control();

    assertEquals("Welcome to the Stock checker program\n" +
            "Supported user instructions are: \n" +
            "Returns: get returns over a period of time using start and end dates \n" +
            "Moving-Average: get the moving avg at a date for a certain x number of days\n" +
            "Crossovers: Get which days are crossovers for a given x number of days within a " +
            "range\n" +
            "Portfolio: Create a new portfolio\n" +
            "Add-to-Portfolio: Add more stocks to an existing portfolio\n" +
            "Portfolio-Value: Get the value of a portfolio at a certain day\n" +
            "menu: (Print supported instruction list)\n" +
            "q or quit: (quit the program) \n" +
            "Type command: Returns\n" +
            "50.0\n" +
            "\n" +
            "Thank you for using this program", appendable.toString());

    //test 'menu'
    initialize();
    Reader menu = new StringReader("menu");
    controller = new ModelImplController(model, menu, appendable);
    controller.control();

    assertEquals("Welcome to the Stock checker program\n" +
            "Supported user instructions are: \n" +
            "Returns: get returns over a period of time using start and end dates \n" +
            "Moving-Average: get the moving avg at a date for a certain x number of days\n" +
            "Crossovers: Get which days are crossovers for a given x number of days within a " +
            "range\n" +
            "Portfolio: Create a new portfolio\n" +
            "Add-to-Portfolio: Add more stocks to an existing portfolio\n" +
            "Portfolio-Value: Get the value of a portfolio at a certain day\n" +
            "menu: (Print supported instruction list)\n" +
            "q or quit: (quit the program) \n" +
            "Type command: menu\n" +
            "Supported user instructions are: \n" +
            "Returns: get returns over a period of time using start and end dates \n" +
            "Moving-Average: get the moving avg at a date for a certain x number of days\n" +
            "Crossovers: Get which days are crossovers for a given x number of days within a " +
            "range\n" +
            "Portfolio: Create a new portfolio\n" +
            "Add-to-Portfolio: Add more stocks to an existing portfolio\n" +
            "Portfolio-Value: Get the value of a portfolio at a certain day\n" +
            "menu: (Print supported instruction list)\n" +
            "q or quit: (quit the program) \n" +
            "Thank you for using this program", appendable.toString());
  }

  @Test
  public void testWelcomeMessage() {
    initialize();
    controller.welcomeMessage();

    String result = appendable.toString();
    assertEquals("Welcome to the Stock checker program\n" +
            "Supported user instructions are: \n" +
            "Returns: get returns over a period of time using start and end dates \n" +
            "Moving-Average: get the moving avg at a date for a certain x number of days\n" +
            "Crossovers: Get which days are crossovers for a given x number of days within a " +
            "range\n" +
            "Portfolio: Create a new portfolio\n" +
            "Add-to-Portfolio: Add more stocks to an existing portfolio\n" +
            "Portfolio-Value: Get the value of a portfolio at a certain day\n" +
            "menu: (Print supported instruction list)\n" +
            "q or quit: (quit the program) ", result);
  }

  @Test
  public void testFarewellMessage() {
    initialize();
    controller.farewellMessage();

    String result = appendable.toString();
    assertEquals('\n' + "Thank you for using this program", result);
  }
}
