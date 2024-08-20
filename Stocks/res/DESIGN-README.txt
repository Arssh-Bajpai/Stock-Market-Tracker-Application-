This covers the design of Assignment 5: Stocks (Part 2).

The program follows a Model-view-controller design pattern. 

Controller:
IController
This is the interface which shows all the operations a Controller can do.

ModelImplController 
This is the controller of the program for all the commands relating to the Stock class.

PortfolioImplController
This is the controller for all the commands relating to the Portfolio class.


Model:
IModel
This is the interface which shows all the operations that the Model does.

IModelMacro
This is the interface that defines exciting a macro on a IModel.

IModelMacroImpl
This class implements IModelWithMacro and is a decorator for an IModel.

IModelWithMacro
This interface extends IModdel, it adds a method execute.

ModelImpl
This is the implemented model which the program directly uses. The controller calls methods that are in this class.

ModelImplMock
This is a mock version of the model used for testing purposes.

PerformanceOverTime
This is an interface has a method visualize, which shows how a stock or a portfolio has performed over a period of time.

PerformanceOverTimeImpl
This class implements PerformanceOverTime. This class directly computes and shows how a stock or a portfolio has performed over a period of time.

PortfolioCurStock
This class represents a PortfolioCurStock. A PortfolioCurStock is made up of a ticker (String), shares (double), and a sharePrice(double).

PortfolioMacroImpl
This class implements Portfolios. The PortfolioImplController calls this to execute commands relating to the Portfolio. 

Portfolios:
This is interface represents a Portfolio.

PortfolioStockLog
This class represents a PortfolioStockLog. It contains a name (String), ticker (String), date (Date), and shares (double).

Stock:
This class represents a stock. A stock is made up of a ticker (String), timestamp (ticker), the price when the stock market opens (double), the highest price of a day (double), the lowest price of a day (low), and the price of a stock when the stock market closes (double).

View:
Viewer
The viewer initializes the program and runs it.

ChangesLog:

GUIController: 
An additional controller class designed to explicitly handle the GUIs functionality and take user inputs and send to the GUIViewer

IView: a new view interface with methods designed explicitly for the GUI viewer portion so it can better delegate methods between the controller and itself.

GUIViewer: A Viewer class that implements the IView interface and sends logic between the GUI and the Controller itself to create communicate and share logic.

TextViewer: The original View method renamed for better clarification.
