import org.junit.Test;

import java.util.ArrayList;

import model.ModelImplMock;

import static org.junit.Assert.assertEquals;

/**
 * testing the Model class and uses the mock for the model to test inputs.
 */
public class ModelImplMockTest {

  @Test
  public void testGetReturns() {
    ModelImplMock model = new ModelImplMock("AAPL");
    double returns = model.getReturns("2024-06-01", "2024-06-06");
    assertEquals(50.00, returns, 0.001);
  }

  @Test
  public void testMovingAverage() {
    ModelImplMock model = new ModelImplMock("AAPL");
    double movingAverage = model.movingAverage("2024-06-06", 5);
    assertEquals(200.00, movingAverage, 0.001);
  }

  @Test
  public void testCrossovers() {
    ModelImplMock model = new ModelImplMock("AAPL");
    ArrayList<String> crossovers = model.crossovers("2024-06-01", "2024-06-06", 5);
    assertEquals(1, crossovers.size());
    assertEquals("2024-06-06", crossovers.get(0));
  }



}