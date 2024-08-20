import controller.GUIController;
import viewer.GUIViewer;
import viewer.IView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class GUIControllerTest {

  private Robot robot;
  private IView viewer;
  private GUIController controller;

  public static void main(String[] args) throws Exception {
    GUIControllerTest test = new GUIControllerTest();
    //test.setUp();
    //test.testHandleCreation();
//    test.testHandleBuyAction();
//    test.testHandleSellAction();
//    test.testHandleValueAction();
//    test.testHandleCompositionAction();
  }

//  public void setUp() throws Exception {
//    robot = new Robot();
//    viewer = new GUIViewer();
//    controller = new GUIController(viewer);
//    viewer.setListener(controller);
//    SwingUtilities.invokeLater(() -> viewer.display());
//
//
//    robot.waitForIdle();
//    Thread.sleep(2000);
//  }

//  public void testHandleCreation() throws InterruptedException {
//    enterText(((GUIViewer) viewer).portfolioNameField, "TestPortfolio");
//    clickButton(((GUIViewer) viewer).createButton);
//
//    Thread.sleep(1000);
//
//    assertTrue(((GUIViewer) viewer).outputArea.getText().contains("Creating portfolio " +
//            "named TestPortfolio"));
//  }
//
//  public void testHandleBuyAction() throws InterruptedException {
//    enterText(((GUIViewer) viewer).portfolioNameField, "TestPortfolio");
//    enterText(((GUIViewer) viewer).tickerSymbolField, "AAPL");
//    enterText(((GUIViewer) viewer).dateField, "2024-06-20");
//    enterText(((GUIViewer) viewer).sharesField, "10");
//    clickButton(((GUIViewer) viewer).buyButton);
//
//    Thread.sleep(1000);
//
//    assertTrue(((GUIViewer) viewer).outputArea.getText().contains("Buying 10.0 shares of" +
//            " AAPL in portfolio TestPortfolio"));
//  }
//
//  public void testHandleSellAction() throws InterruptedException {
//    enterText(((GUIViewer) viewer).portfolioNameField, "TestPortfolio");
//    enterText(((GUIViewer) viewer).tickerSymbolField, "AAPL");
//    enterText(((GUIViewer) viewer).dateField, "2024-06-20");
//    enterText(((GUIViewer) viewer).sharesField, "5");
//    clickButton(((GUIViewer) viewer).sellButton);
//
//    Thread.sleep(1000);
//
//    assertTrue(((GUIViewer) viewer).outputArea.getText().contains("Selling 5.0 shares of " +
//            "AAPL from " + "portfolio TestPortfolio"));
//  }
//
//  public void testHandleValueAction() throws InterruptedException {
//    enterText(((GUIViewer) viewer).portfolioNameField, "TestPortfolio");
//    enterText(((GUIViewer) viewer).dateField, "2024-06-20");
//    clickButton(((GUIViewer) viewer).valueButton);
//
//    Thread.sleep(1000);
//
//    assertTrue(((GUIViewer) viewer).outputArea.getText().contains("Calculating total value of " +
//            "portfolio TestPortfolio"));
//  }
//
//  public void testHandleCompositionAction() throws InterruptedException {
//    enterText(((GUIViewer) viewer).portfolioNameField, "TestPortfolio");
//    enterText(((GUIViewer) viewer).dateField, "2024-06-20");
//    clickButton(((GUIViewer) viewer).compositionButton);
//
//    Thread.sleep(1000);
//
//    assertTrue(((GUIViewer) viewer).outputArea.getText().contains("Showing composition of" +
//            " portfolio TestPortfolio"));
//  }

  private void enterText(JTextField textField, String text) throws InterruptedException {
    textField.requestFocus();
    Thread.sleep(500);

    for (char c : text.toCharArray()) {
      int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
      robot.keyPress(keyCode);
      robot.keyRelease(keyCode);
      Thread.sleep(100);
    }
  }

  private void clickButton(JButton button) {
    Point point = button.getLocationOnScreen();
    robot.mouseMove(point.x + button.getWidth() / 2, point.y + button.getHeight() / 2);
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
  }

  private void assertTrue(boolean condition) {
    if (!condition) {
      throw new AssertionError("Test failed");
    }
  }
}