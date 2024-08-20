package model;

/**
 * the IModelMacro interface an interface to help implement macros into existing impls.
 */
public interface IModelMacro {
  /**
   * Executes macros given to it.
   *
   * @param method the macro to execute.
   * @param params the parameters for the method.
   */
  Object execute(String method, Object... params);
}

