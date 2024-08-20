package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.IModelMacroImpl;
import model.IModelWithMacro;
import model.ModelImpl;
import model.PortfolioMacroImpl;
import viewer.GUIViewer;

/**
 * the Controller for the GUI Viewer usability, This gets user input and sends them to the Viewer
 * for display.
 */
public class GUIController implements ActionListener {
  private GUIViewer viewer;
  private IModelWithMacro model;

  /**
   * constructs the GUIController object to push through it.
   *
   * @param viewer takes in the viewer to use its methods and display.
   */
  public GUIController(GUIViewer viewer) {
    this.viewer = viewer;
    this.model = new IModelMacroImpl(new ModelImpl());
    viewer.displayOutput("Welcome to the Stock Manager Application Please Enter " +
            "Valid inputs so you " +
            "can buy,");
    viewer.displayOutput("sell, or view your portfolios or even create new portfolios!");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    switch (command) {
      case "Buy":
        handleBuyAction();
        break;
      case "Sell":
        handleSellAction();
        break;
      case "Value":
        handleValueAction();
        break;
      case "Composition":
        handleCompositionAction();
        break;
      case "Create":
        handleCreation();
        break;
      default:
        break;
    }
  }

  private void handleCreation() {
    viewer.clearOutput();
    String portfolios = viewer.getCreatedPortfolioName();
    try {
      if (portfolios.equals("") || portfolios == null || portfolios.contains(".")) {
        throw new IllegalArgumentException();
      }
      viewer.displayOutput("Creating portfoiolio named " + portfolios);
      model.execute(new PortfolioMacroImpl(), "createPortfolios", portfolios);
    } catch (NumberFormatException ex) {
      viewer.displayOutput("Invalid Name");
    } catch (IllegalArgumentException e) {
      viewer.displayOutput("Please enter a valid portfolio name");
    }
  }

  private void handleBuyAction() {
    viewer.clearOutput();
    String name = viewer.getSelectedPortfolio();
    String ticker = viewer.getTickerSymbol();
    String date = viewer.getSelectedDate();
    String sharesStr = viewer.getInputString();
    try {
      double shares = Double.parseDouble(sharesStr);
      viewer.displayOutput("Buying " + shares + " shares of " + ticker + " in portfolio " + name);
      model.execute(new PortfolioMacroImpl(), "buyShares", name, ticker, date, shares);
    } catch (NumberFormatException ex) {
      viewer.displayOutput("Invalid share amount entered.");
    } catch (IllegalArgumentException e) {
      viewer.displayOutput("Invalid share amount entered.");
    }
  }

  private void handleSellAction() {
    viewer.clearOutput();
    String name = viewer.getSelectedPortfolio();
    String ticker = viewer.getTickerSymbol();
    String date = viewer.getSelectedDate();
    String sharesStr = viewer.getInputString();
    try {
      double shares = Double.parseDouble(sharesStr);
      viewer.displayOutput("Selling " + shares + " shares of " + ticker +
              " from portfolio " + name);
      model.execute(new PortfolioMacroImpl(), "sellShares", name, ticker, date, shares);
    } catch (NumberFormatException ex) {
      viewer.displayOutput("Invalid share amount entered.");
    } catch (IllegalArgumentException e) {
      viewer.displayOutput("Invalid share amount entered.");
    }
  }

  private void handleValueAction() {
    viewer.clearOutput();
    String name = viewer.getSelectedPortfolio();
    String date = viewer.getSelectedDate();
    try {
      viewer.displayOutput("Calculating total value of portfolio " + name);
      viewer.displayOutput("value: " + model.execute(new PortfolioMacroImpl(),
              "portfolioValue", name, date) + "$");
    } catch (IllegalArgumentException e) {
      viewer.displayOutput("Invalid Parameters");
    }
  }

  private void handleCompositionAction() {
    viewer.clearOutput();
    String name = viewer.getSelectedPortfolio();
    String date = viewer.getSelectedDate();
    try {
      viewer.displayOutput("Showing composition of portfolio " + name);
      viewer.displayOutput("" + model.execute(new PortfolioMacroImpl(),
              "composition", name, date));
    } catch (IllegalArgumentException e) {
      viewer.displayOutput("Invalid Parameters");
    }
  }
}