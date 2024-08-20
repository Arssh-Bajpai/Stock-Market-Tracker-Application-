import org.junit.Before;
import org.junit.Test;

import model.PerformanceOverTimeImpl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Performance over time testing methods.
 */
public class PerformanceOverTimeImplTest {
  private PerformanceOverTimeImpl performance;

  @Before
  public void setUp() {
    performance = new PerformanceOverTimeImpl();
  }

  @Test
  public void testVisualize_validDates() {
    String portfolioName = "Retirement";
    String startDate = "2024-04-26";
    String endDate = "2024-05-29";

    String result = performance.visualize(portfolioName, startDate, endDate);
    assertTrue(result.contains("Performance of portfolio " + portfolioName));
    assertTrue(result.contains("from " + startDate + " to " + endDate));
  }


  @Test
  public void testVisualize_invalidDateRange() {
    String portfolioName = "TestPortfolio";
    String startDate = "2024-06-01";
    String endDate = "2024-01-01"; // End date is before start date

    assertThrows(IllegalArgumentException.class, () -> {
      performance.visualize(portfolioName, startDate, endDate);
    });
  }

  @Test
  public void testVisualize_nonexistentPortfolio() {
    String portfolioName = "NonexistentPortfolio";
    String startDate = "2024-01-01";
    String endDate = "2024-06-01";

    assertThrows(IllegalArgumentException.class, () -> {
      performance.visualize(portfolioName, startDate, endDate);
    });
  }

}