package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of the performance over time interface.
 */
public class PerformanceOverTimeImpl implements PerformanceOverTime {

  /**
   * constructs an object to run macros through.
   */
  public PerformanceOverTimeImpl() {
    //constructs an empty Performance over time object mainly for testing.
  }

  @Override
  public String visualize(String name, String start, String end) throws IllegalArgumentException {
    if (!isValid(start, end)) {
      throw new IllegalArgumentException("End date is before Start date");
    }
    try {
      StringBuilder span = new StringBuilder();
      span.append("Performance of portfolio ").append(name).append(" from ")
              .append(start).append(" to ").append(end);
      span.append(System.lineSeparator());
      span.append(System.lineSeparator());
      return getSpan(name, getPeriod(start, end), start, span);
    } catch (ParseException e) {
      System.out.println("Invalid Dates");
      throw new IllegalArgumentException("Invalid date format", e);
    }
  }

  //checks if the period being operated on is a valid parameter input
  private boolean isValid(String start, String end) {
    SimpleDateFormat buyDate = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date starts = buyDate.parse(start);
      Date ends = buyDate.parse(end);
      return (ends.before(starts));
    } catch (ParseException e) {
      System.out.println("Invalid Dates");
    }
    return true;
  }

  //determines the timescale by getting the number of days between the start and end and choosing
  //scale based on having between 5-30 intervals per chart.
  private String getPeriod(String start, String end) throws ParseException {
    SimpleDateFormat buyDate = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    long days = 0;
    try {
      days = (buyDate.parse(end).getTime() - buyDate.parse(start).getTime()) / 86400000;
    } catch (ParseException e) {
      throw new IllegalStateException("Invalid date entry");
    }
    long period = 0;
    if (days <= 30) { //the time period will be by day
      period = days;
      return "days:" + period;
    } else if (days <= 210) {  //the time period will be by week
      period = days / 7;
      return "week:" + period;
    } else if (days <= 420) { //the time period will be bi-weekly
      period = days / 14;
      return "bi-weekly:" + period;
    } else if (days <= 900) { //the time period will be monthly
      period = days / 30;
      return "monthly:" + period;
    } else if (days >= 1825) { //the time period will be by year
      period = days / 365;
      return "year:" + period;
    } else { //the time period will be yearly-quarters
      period = days / 90;
      return "quarterly:" + period;
    }
  }

  //iterates timescale by timescale and appending the data to a Stringbuilder which is then
  //returned.
  private String getSpan(String name, String period, String start, StringBuilder span)
          throws ParseException {
    String time = period.substring(0, period.indexOf(":"));
    int interval = Integer.parseInt(period.substring(period.indexOf(":") + 1));

    Portfolios macro = new PortfolioMacroImpl();
    SimpleDateFormat buyDate = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate = buyDate.parse(start);

    Calendar cal = Calendar.getInstance();
    cal.setTime(startDate);

    double scale = 0;

    switch (time) {
      case "days":
        scale = getScaler(name, start, 50);
        for (int i = 0; i < interval; i++) {
          String formattedDate = String.format("%02d/%02d/%d",
                  cal.get(Calendar.MONTH) + 1,
                  cal.get(Calendar.DAY_OF_MONTH),
                  cal.get(Calendar.YEAR));
          span.append(formattedDate + ": ");
          span.append(asterisks(name, buyDate.format(cal.getTime()), scale));
          span.append(System.lineSeparator());
          cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        span.append(System.lineSeparator());
        span.append("Scale: * = " + scale);
        break;

      case "week":
        scale = getScaler(name, start, 50);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        for (int i = 0; i < interval; i++) {
          String formattedDate = String.format("%02d/%02d/%d",
                  cal.get(Calendar.MONTH) + 1,
                  cal.get(Calendar.DAY_OF_MONTH),
                  cal.get(Calendar.YEAR));
          span.append(formattedDate + ": ");
          span.append(asterisks(name, buyDate.format(cal.getTime()), scale));
          span.append(System.lineSeparator());
          cal.add(Calendar.DAY_OF_YEAR, 7);
        }
        span.append(System.lineSeparator());
        span.append("Scale: * = " + scale);
        break;

      case "bi-weekly":
        scale = getScaler(name, start, 50);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        for (int i = 0; i < interval; i++) {
          String formattedDate = String.format("%02d/%02d/%d",
                  cal.get(Calendar.MONTH) + 1,
                  cal.get(Calendar.DAY_OF_MONTH),
                  cal.get(Calendar.YEAR));
          span.append(formattedDate + ": ");
          span.append(asterisks(name, buyDate.format(cal.getTime()), scale));
          span.append(System.lineSeparator());
          cal.add(Calendar.WEEK_OF_YEAR, 2);
        }
        span.append(System.lineSeparator());
        span.append("Scale: * = " + scale);
        break;

      case "monthly":
        scale = getScaler(name, start, 35);
        int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
        while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)
                == Calendar.SUNDAY) {
          cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        span.append(monthString(cal.get(Calendar.MONTH))).append(" ")
                .append(cal.get(Calendar.YEAR)).append(": ");
        span.append(asterisks(name, buyDate.format(cal.getTime()), scale));
        span.append(System.lineSeparator());
        cal.add(Calendar.MONTH, 1);

        for (int i = 1; i < interval; i++) {
          lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
          cal.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
          while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                  cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
          }
          span.append(monthString(cal.get(Calendar.MONTH))).append(" ")
                  .append(cal.get(Calendar.YEAR)).append(": ");
          span.append(asterisks(name, buyDate.format(cal.getTime()), scale));
          span.append(System.lineSeparator());
          cal.add(Calendar.MONTH, 1);
        }
        span.append(System.lineSeparator());
        span.append("Scale: * = " + scale);
        break;

      case "year":
        scale = getScaler(name, start, 10);
        for (int i = 0; i < interval; i++) {
          int lastDayOfYear = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
          cal.set(Calendar.DAY_OF_YEAR, lastDayOfYear);
          while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                  cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
          }
          span.append(cal.get(Calendar.YEAR)).append(": ");
          span.append(asterisks(name, buyDate.format(cal.getTime()), scale));
          span.append(System.lineSeparator());
          cal.add(Calendar.YEAR, 1);
        }
        span.append(System.lineSeparator());
        span.append("Scale: * = ").append(scale);
        break;

      case "quarterly":
        int initialMonth = cal.get(Calendar.MONTH);
        for (int i = 0; i < interval; i++) {
          cal.set(Calendar.MONTH, initialMonth + i * 3);
          int lastDayOfQuarter = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
          cal.set(Calendar.DAY_OF_MONTH, lastDayOfQuarter);
          while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                  cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
          }
          span.append(monthString(cal.get(Calendar.MONTH))).append(" ")
                  .append(cal.get(Calendar.YEAR)).append(": ");
          span.append(asterisks(name, buyDate.format(cal.getTime()), scale));
          span.append(System.lineSeparator());
        }
        span.append(System.lineSeparator());
        span.append("Scale: * = " + scale);
        break;
      default:
        System.out.println("Bad input");
        break;
    }
    System.out.println(span);
    return span.toString();
  }

  //gets the scale of each asterisk dependant on the timescale
  // and the value of the portfolio
  // itself.
  private double getScaler(String name, String date, int aster) throws IllegalArgumentException {
    Portfolios macro = new PortfolioMacroImpl();
    double scale = macro.portfolioValue(name, date) / aster;
    if (scale == 0) {
      throw new IllegalArgumentException("For best results please enter a " +
              "starting date that is valid with data and value");
    }
    return scale;
  }

  //appends the asterisks and assigns the value to them.
  private String asterisks(String name, String date, double scale) {
    Portfolios macro = new PortfolioMacroImpl();
    double portfolioValue = macro.portfolioValue(name, date);
    StringBuilder output = new StringBuilder();
    int maxAsterisks = 200;
    double asteriskCount = portfolioValue / scale;
    for (int i = 0; i < Math.min(asteriskCount, maxAsterisks); i++) {
      output.append("*");
    }
    if (output.length() == 0) {
      output.append("Stock Offices Closed No Data");
    }

    return output.toString();
  }


  //called by the calendar object to return an int from MONTH_OF_THE_YEAR into an actual hyphen
  //for the month.
  private String monthString(int month) {
    switch (month) {
      case 0:
        return "Jan";
      case 1:
        return "Feb";
      case 2:
        return "Mar";
      case 3:
        return "Apr";
      case 4:
        return "May";
      case 5:
        return "Jun";
      case 6:
        return "Jul";
      case 7:
        return "Aug";
      case 8:
        return "Sep";
      case 9:
        return "Oct";
      case 10:
        return "Nov";
      case 11:
        return "Dec";
      default:
        throw new IllegalArgumentException("Invalid month");
    }
  }

  @Override
  public Object execute(String method, Object... params) {
    String result = "";
    if (method.equals("visualize")) {
      if (params.length != 3) {
        throw new IllegalArgumentException("visualize method requires exactly 3 parameters: " +
                "portfolio name, start date, and end date.");
      }
      try {
        String portfolioName = (String) params[0];
        String startDate = (String) params[1];
        String endDate = (String) params[2];
        result = visualize(portfolioName, startDate, endDate);
      } catch (IllegalArgumentException e) {
        System.out.println("Failed to execute visualize: " + e.getMessage());
      }
    } else {
      throw new IllegalArgumentException("Unknown method: " + method);
    }
    return result;
  }
}
