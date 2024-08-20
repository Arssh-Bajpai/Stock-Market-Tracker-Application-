import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import model.PortfolioMacroImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * testing the Portfolio Macros methods.
 */
public class PortfolioMacroImplTest {


  private final String testDirectory = "/Users/arsshbajpai/Desktop/OOD/Stocks/Portfolios";
  private final String testPortfolioName = "TestPortfolio";
  private PortfolioMacroImpl portfolio;

  @Before
  public void setUp() throws IOException {
    portfolio = new PortfolioMacroImpl();
  }


  @Test //tests that the buy Shares method succesfully logs the buy into the json file.
  public void testBuyShares_validInputs() {
    String name = "TestPortfolio";
    String ticker = "AAPL";
    String validDate = "2024-05-28";
    double shares = 15.0;
    try {
      portfolio.buyShares(name, ticker, validDate, shares);
    } catch (IllegalArgumentException e) {
      fail("buyShares should not throw an exception with valid inputs");
    }
  }

  @Test //tests the buy shares method with invalid dates
  public void testBuyShares_invalidDate() {
    String name = "TestPortfolio";
    String ticker = "AAPL";
    String invalidDate = "2024-13-01";
    double shares = 10.0;
    try {
      portfolio.buyShares(name, ticker, invalidDate, shares);
      fail("buyShares should throw IllegalArgumentException for invalid date");
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid date or nonnegative shares or a valid file", e.getMessage());
    }
  }

  @Test //tests the buy shares method for negative shares input
  public void testBuyShares_negativeShares() {
    String name = "TestPortfolio";
    String ticker = "AAPL";
    String validDate = "2024-06-01";
    double negativeShares = -10.0;
    try {
      portfolio.buyShares(name, ticker, validDate, negativeShares);
      fail("buyShares should throw IllegalArgumentException for negative shares");
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid date or nonnegative shares or a valid file", e.getMessage());
    }
  }

  @Test
  public void testBuyShares_wrongFileName() {
    String wrongPortfolioName = "NonExistingPortfolio";
    String ticker = "AAPL";
    String validDate = "2024-06-01";
    double shares = 10.0;
    try {
      portfolio.buyShares(wrongPortfolioName, ticker, validDate, shares);
      fail("buyShares should throw IllegalArgumentException for non-existing portfolio");
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid date or nonnegative shares or a valid file", e.getMessage());
    }
  }

  @Test //tests the sell share method when theirs enough shares to sell and valid inputs
  public void testSellShares_validInputs() {
    String name = "TestPortfolio";
    String ticker = "AAPL";
    String validDate = "2024-05-29";
    double shares = 5.0;
    try {
      portfolio.buyShares(name, ticker, validDate, shares);
      portfolio.sellShares(name, ticker, validDate, shares);
    } catch (IllegalArgumentException e) {
      fail("sellShares should not throw an exception with valid inputs");
    }
  }

  @Test //test sell shares with a wrong date
  public void testSellShares_invalidDate() {
    String name = "TestPortfolio";
    String ticker = "AAPL";
    String invalidDate = "2024-13-01";
    double shares = 5.0;
    try {
      portfolio.sellShares(name, ticker, invalidDate, shares);
      fail("sellShares should throw IllegalArgumentException for invalid date");
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid date or nonnegative shares or a valid file", e.getMessage());
    }
  }

  @Test //test sell shares with negative shares
  public void testSellShares_negativeShares() {
    String name = "TestPortfolio";
    String ticker = "AAPL";
    String validDate = "2024-06-01";
    double negativeShares = -5.0;
    try {
      portfolio.sellShares(name, ticker, validDate, negativeShares);
      fail("sellShares should throw IllegalArgumentException for negative shares");
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid date or nonnegative shares or a valid file", e.getMessage());
    }
  }

  @Test //tests the sell share methods when theirs not enough shares
  public void testSellShares_notEnoughShares() {
    String name = "TestPortfolio";
    String ticker = "AAPL";
    String validDate = "2024-05-29";
    double shares = 100.0;
    try {
      portfolio.sellShares(name, ticker, validDate, shares);
      fail("sellShares should throw IllegalArgumentException for not enough shares");
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough shares to sell", e.getMessage());
    }
  }

  @Test //tests the portfolio Value method for valid inputs
  public void testPortfolioValue_validInputs() {
    String name = "TestPortfolio";
    String validDate = "2024-05-28";
    try {
      double value = portfolio.portfolioValue(name, validDate);
    } catch (IllegalArgumentException e) {
      fail("portfolioValue should not throw IllegalArgumentException with valid inputs");
    }
  }

  @Test //tests the portfolio Value method for invalid Date
  public void testPortfolioValue_invalidDate() {
    String name = "TestPortfolio";
    String invalidDate = "2024-13-01";
    try {
      double value = portfolio.portfolioValue(name, invalidDate);
      fail("portfolioValue should throw ParseException for invalid date");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Date", e.getMessage());
    }
  }

  @Test //tests the portfoliovalue method for invalid file name
  public void testPortfolioValue_invalidPortfolioName() {
    String invalidPortfolioName = "InvalidPortfolio";
    String dateStr = "2024-06-01";
    try {
      portfolio.portfolioValue(invalidPortfolioName, dateStr);
      fail("Expected IllegalArgumentException for invalid portfolio name");
    } catch (IllegalArgumentException e) {
      System.out.println("passed with invalid input");
    }
  }

  @Test //tests if the composition method is valid
  public void testComposition_valid() {
    String portfolioName = "Retirement";
    String dateStr = "2024-05-29";
    String expectedComposition = "GOOG : 83.51 : 14814.674\n" +
            "AAPL : 58.69 : 11168.120099999998\n" +
            "AMZN : 61.22 : 11143.2644\n";
    assertEquals(expectedComposition, portfolio.composition(portfolioName, dateStr));
  }

  //tests invalid dates for th Composition method.
  @Test(expected = IllegalArgumentException.class)
  public void testComposition_invalidDate() {
    String portfolioName = "TestPortfolio";
    String invalidDateStr = "2024/06/01";
    portfolio.composition(portfolioName, invalidDateStr);
  }

  //tests nonexistant file names for the composition method.
  @Test(expected = IllegalArgumentException.class)
  public void testComposition_nonexistentFile() {
    String nonexistentPortfolioName = "NonexistentPortfolio";
    String dateStr = "2024-06-01";
    portfolio.composition(nonexistentPortfolioName, dateStr);
  }

  @Test
  public void testDistribution_valid() {
    String portfolioName = "Retirement";
    String dateStr = "2024-05-29";
    String expectedDistribution = "GOOG : 83.51 : 14814.674\n" +
            "AAPL : 58.69 : 11168.120099999998\n" +
            "AMZN : 61.22 : 11143.2644\n" +
            "Total Value: 37126.06";
    assertEquals(expectedDistribution, portfolio.distribution(portfolioName, dateStr));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDistribution_invalidDate() {
    String portfolioName = "TestPortfolio";
    String invalidDateStr = "2024/06/01"; // Invalid date format
    portfolio.distribution(portfolioName, invalidDateStr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDistribution_nonexistentFile() {
    String nonexistentPortfolioName = "NonexistentPortfolio";
    String dateStr = "2024-06-01";
    portfolio.distribution(nonexistentPortfolioName, dateStr);
  }


  @Test //valid inputs for the rebalance method
  public void testRebalance_valid() {
    String portfolioName = "TestPortfolio";
    String dateStr = "2024-05-29";
    ArrayList<Integer> weights = new ArrayList<>(Arrays.asList(100));
    String expectedOutput = "AAPL : 5.0 : 951.45\n" +
            "Total Value: 951.45";
    assertEquals(expectedOutput, portfolio.rebalance(portfolioName, dateStr, weights));
  }

  @Test(expected = IllegalArgumentException.class) //invalid weights for the rebalance method
  public void testRebalance_invalidWeightsSum() {
    String portfolioName = "TestPortfolio";
    String dateStr = "2024-05-29";
    ArrayList<Integer> weights = new ArrayList<>(Arrays.asList(50, 40)); // Weights don't sum to 100
    portfolio.rebalance(portfolioName, dateStr, weights);
  }

  @Test(expected = IllegalArgumentException.class) //too many weights in the paramter
  // for rebalance method
  public void testRebalance_invalidNumberOfWeights() {
    String portfolioName = "TestPortfolio";
    String dateStr = "2024-06-01";
    ArrayList<Integer> weights = new ArrayList<>(Arrays.asList(50, 30, 20));
    // More weights than stocks
    portfolio.rebalance(portfolioName, dateStr, weights);
  }

  @Test(expected = IllegalArgumentException.class) //tests the rebalance for invalid dates
  public void testRebalance_invalidDate() {
    String portfolioName = "TestPortfolio";
    String invalidDateStr = "2024/06/01";
    ArrayList<Integer> weights = new ArrayList<>(Arrays.asList(50, 50));
    portfolio.rebalance(portfolioName, invalidDateStr, weights);
  }

  @Test(expected = IllegalArgumentException.class) //tests for invalid or nonexistant files
  public void testRebalance_nonexistentFile() {
    String nonexistentPortfolioName = "NonexistentPortfolio";
    String dateStr = "2024-06-01";
    ArrayList<Integer> weights = new ArrayList<>(Arrays.asList(50, 50));
    portfolio.rebalance(nonexistentPortfolioName, dateStr, weights);
  }

  @Test(expected = IllegalArgumentException.class) //tests rebalances for future dates
  public void testRebalance_futureDate() {
    String portfolioName = "TestPortfolio";
    String futureDateStr = "2025-06-01";
    ArrayList<Integer> weights = new ArrayList<>(Arrays.asList(50, 50));
    portfolio.rebalance(portfolioName, futureDateStr, weights);
  }

}