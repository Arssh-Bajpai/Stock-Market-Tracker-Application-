package model;

/**
 * Fully implements a macro into the IModels inter.
 */
public interface IModelWithMacro extends IModel {
  /**
   * Executes the macro.
   *
   * @param m      the model that the macros are being run through and executed on.
   * @param method the method to execute.
   * @param params the parameters for the method.
   */
  Object execute(IModelMacro m, String method, Object... params);
}
