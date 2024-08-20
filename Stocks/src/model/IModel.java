package model;

import java.util.ArrayList;

/**
 * This interface represents all the operations to be offered by the Model.
 */
public interface IModel {

  /**
   * This method examines the gain or loss of a stock over a specified period.
   */
  public double getReturns(String start, String end);

  /**
   * This method allows examines the x-day moving average of a
   * stock for a specified date and a specified value of x.
   */
  public double movingAverage(String date, int days);

  /**
   * This method determines which days are x-day crossovers
   * for a specified stock over a specified date range and a specified value of x.
   */
  public ArrayList<String> crossovers(String start, String end, int days);
}
