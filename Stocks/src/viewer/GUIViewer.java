package viewer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;

import controller.GUIController;

import java.awt.BorderLayout;

/**
 * The viewer class for the GUI this views and displays the GUI.
 * and holds values and gets inputs from the controller.
 */
public class GUIViewer implements IView {

  private JLabel selectedDateLabel;
  private JLabel selectedPortfolioLabel;
  private JTextField inputField;
  private JTextField tickerTextField;
  private JTextField createPortfolioField;
  private JButton createPortfolioButton;
  private JButton dateButton;
  private JButton portfolioButton;
  private JButton enterSharesButton;
  private JTextArea outputTextArea;
  private JButton buyButton;
  private JButton sellButton;
  private JButton valueButton;
  private JButton composeButton;
  private JFrame frame;

  /**
   * The GUIViewer this constructs a GUIViewer object to allow it to be instantiated and pushed.
   * through the Controlller as well as create all of the visual aspects of the GUI.
   */
  public GUIViewer() {
    frame = new JFrame("Swing Application Framework");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);

    JMenuBar menuBar = createMenuBar();
    frame.setJMenuBar(menuBar);

    MainPanel mainPanel = new MainPanel();
    frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

    JLabel statusBar = new JLabel("Status: Ready");
    statusBar.setBorder(BorderFactory.createEtchedBorder());
    frame.getContentPane().add(statusBar, BorderLayout.SOUTH);

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    selectedDateLabel = new JLabel("Select Date");
    northPanel.add(selectedDateLabel);

    selectedPortfolioLabel = new JLabel("Select Portfolio");
    northPanel.add(selectedPortfolioLabel);

    JLabel shareAmountLabel = new JLabel("Share Amount: ");
    northPanel.add(shareAmountLabel);

    JLabel tickerLabel = new JLabel("Enter Ticker: ");
    northPanel.add(tickerLabel);

    tickerTextField = new JTextField(10);
    northPanel.add(tickerTextField);

    frame.getContentPane().add(northPanel, BorderLayout.NORTH);

    dateButton = new JButton("Select Date");
    dateButton.setActionCommand("Select Date");
    dateButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame dateFrame = createDateDropdownFrame();
        dateFrame.setVisible(true);
      }
    });
    frame.getContentPane().add(dateButton, BorderLayout.WEST);

    portfolioButton = new JButton("Select Portfolio");
    portfolioButton.setActionCommand("Select Portfolio");
    portfolioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame portfolioFrame = createPortfolioDropdownFrame();
        portfolioFrame.setVisible(true);
      }
    });
    frame.getContentPane().add(portfolioButton, BorderLayout.EAST);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    inputField = new JTextField(10);
    bottomPanel.add(inputField);

    enterSharesButton = new JButton("Enter Share Amount");
    enterSharesButton.setActionCommand("Enter Share Amount");
    enterSharesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String shareAmountText = inputField.getText();
        try {
          double shareAmount = Double.parseDouble(shareAmountText);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(frame, "Please enter a valid numeric " +
                  "share amount.");
        }
      }
    });
    bottomPanel.add(enterSharesButton);

    frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

    frame.setVisible(true);

    // Initialize controller
    GUIController controller = new GUIController(this);
    setListener(controller);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new GUIViewer().display());
  }

  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    // Exit button
    JButton exitButton = new JButton("Exit");
    exitButton.addActionListener(e -> System.exit(0));
    menuBar.add(exitButton);

    // Functions buttons
    buyButton = new JButton("Buy");
    buyButton.setActionCommand("Buy");

    sellButton = new JButton("Sell");
    sellButton.setActionCommand("Sell");
    valueButton = new JButton("Value");
    valueButton.setActionCommand("Value");
    composeButton = new JButton("Composition");
    composeButton.setActionCommand("Composition");

    menuBar.add(buyButton);
    menuBar.add(sellButton);
    menuBar.add(valueButton);
    menuBar.add(composeButton);
    createPortfolioField = new JTextField(15);
    createPortfolioButton = new JButton("Create");
    createPortfolioButton.setActionCommand("Create");
    String portfolioName = createPortfolioField.getText().trim();
    menuBar.add(createPortfolioField);
    menuBar.add(createPortfolioButton);

    return menuBar;
  }

  private JFrame createDateDropdownFrame() {
    JFrame dateFrame = new JFrame("Select Date");
    dateFrame.setSize(300, 150);
    dateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel panel = new JPanel();
    JLabel label = new JLabel("Select Date:");
    JComboBox<String> dateComboBox = createComboBox();

    dateComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selectedDate = (String) dateComboBox.getSelectedItem();
        selectedDateLabel.setText("Selected Date: " + selectedDate);
      }
    });

    panel.add(label);
    panel.add(dateComboBox);
    dateFrame.add(panel);

    return dateFrame;
  }

  private JComboBox<String> createComboBox() {
    JComboBox<String> comboBox = new JComboBox<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(new Date());

    while (!calendar.getTime().before(getOldestDate())) {
      Date date = calendar.getTime();
      comboBox.addItem(sdf.format(date));
      calendar.add(Calendar.DATE, -1);
    }

    return comboBox;
  }

  private Date getOldestDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 1993);
    calendar.set(Calendar.MONTH, Calendar.JANUARY);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }

  private JFrame createPortfolioDropdownFrame() {
    JFrame portfolioFrame = new JFrame("Select Portfolio");
    portfolioFrame.setSize(300, 150);
    portfolioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel panel = new JPanel();
    JLabel label = new JLabel("Select Portfolio:");
    JComboBox<String> portfolioComboBox = createPortfolioComboBox();

    portfolioComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selectedPortfolio = (String) portfolioComboBox.getSelectedItem();
        selectedPortfolioLabel.setText("Selected Portfolio: " + selectedPortfolio);
      }
    });

    panel.add(label);
    panel.add(portfolioComboBox);
    portfolioFrame.add(panel);

    return portfolioFrame;
  }

  private JComboBox<String> createPortfolioComboBox() {
    JComboBox<String> comboBox = new JComboBox<>();
    return loadDirectory(comboBox, "/Users/arsshbajpai/Desktop/OOD/Stocks/Portfolios");
  }

  private JComboBox<String> loadDirectory(JComboBox<String> comboBox, String directoryPath) {
    comboBox.removeAllItems();

    File directory = new File(directoryPath);
    File[] files = directory.listFiles();

    if (files != null) {
      for (File file : files) {
        if (file.isFile()) {
          comboBox.addItem(file.getName());
        }
      }
    }
    return comboBox;
  }

  @Override
  public void display() {
    //allows the display of the GUI.
  }

  @Override
  public void setEchoOutput(String s) {
    outputTextArea.setText(s);
  }

  @Override
  public String getInputString() {
    return inputField.getText();
  }

  @Override
  public void clearInputString() {
    inputField.setText("");
  }

  @Override
  public void setListener(ActionListener listener) {
    createPortfolioButton.addActionListener(listener);
    dateButton.addActionListener(listener);
    portfolioButton.addActionListener(listener);
    enterSharesButton.addActionListener(listener);
    buyButton.addActionListener(listener);
    sellButton.addActionListener(listener);
    valueButton.addActionListener(listener);
    composeButton.addActionListener(listener);
  }

  @Override
  public String getTickerSymbol() {
    return tickerTextField.getText();
  }

  @Override
  public String getSelectedPortfolio() {
    String file = selectedPortfolioLabel.getText().replace("Selected Portfolio: ",
            "");
    String fileNoExt = "";
    if (file.contains(".json")) {
      fileNoExt = file.substring(0, file.indexOf("."));
    } else {
      fileNoExt = file;
    }
    return fileNoExt;
  }

  @Override
  public String getSelectedDate() {
    return selectedDateLabel.getText().replace("Selected Date: ", "");
  }

  @Override
  public void displayOutput(String message) {
    outputTextArea.append(message + "\n");
  }

  // Getter method for created portfolio name
  @Override
  public String getCreatedPortfolioName() {
    return createPortfolioField.getText();
  }


  // Method to clear the output text area
  @Override
  public void clearOutput() {
    outputTextArea.setText("");
  }

  /**
   * class to hold all panel creation methods and uses.
   */
  public class MainPanel extends JPanel {
    private JTextArea textArea;

    /**
     * method to create the Panel that the user uses.
     */
    public MainPanel() {
      setLayout(new BorderLayout());
      textArea = new JTextArea();
      outputTextArea = textArea;
      JScrollPane scrollPane = new JScrollPane(textArea);
      add(scrollPane, BorderLayout.CENTER);
    }
  }
}
