package viewer;

/**
 * The runner to choose which Viewer and UI to use using commandLine arguments.
 */
public class Runner {
  /**
   * prompts the user to choose which UI to use and than launches it.
   *
   * @param args the args to start the program
   */
  public static void main(String[] args) {
    if (args.length > 0 && args[0].equals("-text")) {
      TextViewer.main(new String[]{});
    } else {
      GUIViewer.main(new String[]{});
    }
  }
}

