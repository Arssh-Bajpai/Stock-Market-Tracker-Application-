import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import controller.PortfolioImplController;
import model.IModelMacroImpl;
import model.IModelWithMacro;
import model.ModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * test for the Portfolio Controller.
 */
public class PortfolioImplControllerTest {
  private Reader readable;
  private StringWriter appendable;
  private PortfolioImplController controller;
  private IModelWithMacro model;

  @Before
  public void initialize() {
    model = new IModelMacroImpl(new ModelImpl());
    readable = new StringReader("");
    appendable = new StringWriter();
    controller = new PortfolioImplController(model, readable, appendable);
  }

  @Test
  public void testControl() {
    initialize();

    // Test 'q' (quit)
    readable = new StringReader("q");
    controller = new PortfolioImplController(model, readable, appendable);
    controller.control();
    assertEquals("Welcome to the Portfolio manager program\n" +
            "Supported user instructions are: \n" +
            "Create-Portfolios: creates a new Portfolio \n" +
            "Buy-Shares: buy shares for an existing portfolio\n" +
            "Sell-Shares: sell shares for an existing portfolio\n" +
            "Portfolio-Value: Get the total value of an existingportfolio\n" +
            "Composition: Get the total composition of an existingportfolio\n" +
            "Distribution: Get the value distribution of an existingportfolio\n" +
            "Rebalance: rebalance an existing portfolios values to weights\n" +
            "Price-Over-Time: gets the price over a time period of an existing portofolio\n" +
            "menu: (Print supported instruction list)\n" +
            "q or quit: (quit the program) \n" +
            "Type command: \n" +
            "Thank you for using this program", appendable.toString());
  }

  @Test
  public void testWelcomeMessage() {
    initialize();
    controller.welcomeMessage();
    assertEquals("Welcome to the Portfolio manager program\n" +
            "Supported user instructions are: \n" +
            "Create-Portfolios: creates a new Portfolio \n" +
            "Buy-Shares: buy shares for an existing portfolio\n" +
            "Sell-Shares: sell shares for an existing portfolio\n" +
            "Portfolio-Value: Get the total value of an existingportfolio\n" +
            "Composition: Get the total composition of an existingportfolio\n" +
            "Distribution: Get the value distribution of an existingportfolio\n" +
            "Rebalance: rebalance an existing portfolios values to weights\n" +
            "Price-Over-Time: gets the price over a time period of an existing portofolio\n" +
            "menu: (Print supported instruction list)\n" +
            "q or quit: (quit the program) ", appendable.toString());
  }

  @Test
  public void testFarewellMessage() {
    initialize();
    controller.farewellMessage();
    assertEquals("\nThank you for using this program", appendable.toString());
  }
}