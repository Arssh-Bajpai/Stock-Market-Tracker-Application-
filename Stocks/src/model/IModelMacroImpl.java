package model;

import java.util.ArrayList;

/**
 * IModelMacroImpl takes in the IModel class and wraps it to allow for macro additions.
 */
public class IModelMacroImpl implements IModelWithMacro {

  private final IModel model;

  /**
   * constructor for the Macro Implementation og Imodel.
   *
   * @param model original model to begin accepting macros.
   */
  public IModelMacroImpl(IModel model) {
    this.model = model;
  }

  @Override
  public double getReturns(String start, String end) throws IllegalArgumentException {
    return model.getReturns(start, end);
  }

  @Override
  public double movingAverage(String date, int days) throws IllegalArgumentException {
    return model.movingAverage(date, days);
  }


  @Override
  public ArrayList<String> crossovers(String start, String end, int days) {
    return model.crossovers(start, end, days);
  }

  @Override
  public Object execute(IModelMacro m, String method, Object... params) {
    return m.execute(method, params);
  }
}
