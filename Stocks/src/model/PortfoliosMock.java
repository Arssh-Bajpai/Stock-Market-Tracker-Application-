import java.util.ArrayList;
import java.util.List;

import model.IModel;
import model.IModelMacroImpl;
import model.ModelImpl;
import model.Portfolios;

/**
 * The Mock class of the Portfolios interface used for testing.
 */
public class PortfoliosMock implements Portfolios {
  public List<String> portfolios = new ArrayList<>();

  public PortfoliosMock() {
    //used to create an object for testing and object pass through.
  }

  @Override
  public void createPortfolios(String name) {
    portfolios.add(name);
  }

  @Override
  public void buyShares(String portfolioName, String ticker, String date, double shares) {
    // Mock implementation
  }

  @Override
  public void sellShares(String portfolioName, String ticker, String date, double shares) {
    // Mock implementation
  }

  @Override
  public double portfolioValue(String portfolioName, String date) {
    return 1000.0; // Mock return value
  }

  @Override
  public String composition(String portfolioName, String date) {
    return "Composition details"; // Mock return value
  }

  @Override
  public String distribution(String portfolioName, String date) {
    return "Distribution details"; // Mock return value
  }

  @Override
  public String rebalance(String portfolioName, String date, ArrayList<Integer> weights) {
    return "Rebalanced"; // Mock return value
  }

  @Override
  public Object execute(String str, Object... params) {
    IModel model = new IModelMacroImpl(new ModelImpl());
    return "((IModelMacroImpl) model).execute(m, str, params);";
  }
}