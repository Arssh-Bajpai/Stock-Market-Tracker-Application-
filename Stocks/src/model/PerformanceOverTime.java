package model;

/**
 * Performnance over time interface macro, the inter for the macro to display a table of asterisks
 * depicting performance over a period of time.
 */
public interface PerformanceOverTime extends IModelMacro {
  /**
   * visualizes the Performance over time of a portfolio.
   *
   * @param name  name of the portfolio to analyze.
   * @param start start date to analyze.
   * @param end   end date to analyze.
   */
  public String visualize(String name, String start, String end) throws IllegalArgumentException;
}
