import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BankBalanceGUI extends JFrame implements ActionListener {

    private JPanel panel;
    private JPanel inputPanel;
    private JPanel buttonPanel;

    private JLabel messageLabel;

    private JTextField startingBalanceField;
    private JTextField amountField;

    private JButton setBalanceButton;
    private JButton showBalanceButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton exitButton;

    // Creates the BankAccount object
    private BankAccount bankAccount;

    // Constructor to create the GUI window
    public BankBalanceGUI() {

        // Build and set up the application's window
        setTitle("CSC372 - Bank Balance Application");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // Displays the final balance when the window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitApplication();
            }
        });

        // Create the main JPanel
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create the input JPanel
        inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        inputPanel.add(new JLabel("Starting Balance:"));
        startingBalanceField = new JTextField(10);
        inputPanel.add(startingBalanceField);

        inputPanel.add(new JLabel("Transaction Amount:"));
        amountField = new JTextField(10);
        inputPanel.add(amountField);

        // Create the button JPanel
        buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));

        setBalanceButton = new JButton("Set Balance");
        showBalanceButton = new JButton("Show Balance");
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        exitButton = new JButton("Exit");

        // Adds the buttons to the button JPanel
        buttonPanel.add(setBalanceButton);
        buttonPanel.add(showBalanceButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(exitButton);

        // Add ActionListeners to the buttons so they respond to the user actions
        setBalanceButton.addActionListener(this);
        showBalanceButton.addActionListener(this);
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Create a JPanel for the inputs and buttons
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.add(inputPanel);
        centerPanel.add(buttonPanel);

        // Create a starting or welcome message to display in the JPanel
        messageLabel = new JLabel("Your Account Balance Will Appear Here.");

        // Adds the components to the main JPanel
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(messageLabel, BorderLayout.SOUTH);

        // Adds the panel to the application window
        add(panel);

        // Makes the application window visible to the users
        setVisible(true);
    }

    // Method to respond to button actions
    @Override
    public void actionPerformed(ActionEvent event) {

        // Sets the starting account balance
        if (event.getSource() == setBalanceButton) {

            try {
                double startingBalance = Double.parseDouble(startingBalanceField.getText());

                bankAccount = new BankAccount(startingBalance);

                messageLabel.setText("Set Balance Button Was Selected. Starting Balance: $" +
                        String.format("%.2f", bankAccount.getBalance()));

            } catch (NumberFormatException e) {
                messageLabel.setText("Please Enter A Valid Starting Balance.");

            } catch (IllegalArgumentException e) {
                messageLabel.setText(e.getMessage());
            }
        }

        // Shows the current account balance
        else if (event.getSource() == showBalanceButton) {

            if (bankAccount == null) {
                messageLabel.setText("Please Set Your Starting Balance First.");
            } else {
                messageLabel.setText("Show Balance Button Selected. Current Balance: $" +
                        String.format("%.2f", bankAccount.getBalance()));
            }
        }

        // Deposits money into the bank account
        else if (event.getSource() == depositButton) {

            if (bankAccount == null) {
                messageLabel.setText("Please Set Your Starting Balance First.");
            } else {
                try {
                    double amount = Double.parseDouble(amountField.getText());

                    bankAccount.deposit(amount);

                    messageLabel.setText("Deposit Button Has Been Selected. New Balance: $" +
                            String.format("%.2f", bankAccount.getBalance()));

                } catch (NumberFormatException e) {
                    messageLabel.setText("Please Enter A Valid Deposit Amount.");

                } catch (IllegalArgumentException e) {
                    messageLabel.setText(e.getMessage());
                }
            }
        }

        // Withdraws money from the bank account
        else if (event.getSource() == withdrawButton) {

            if (bankAccount == null) {
                messageLabel.setText("Please Set Your Starting Balance First.");
            } else {
                try {
                    double amount = Double.parseDouble(amountField.getText());

                    boolean successful = bankAccount.withdraw(amount);

                    if (successful) {
                        messageLabel.setText("Withdraw Button Has Been Selected. New Balance: $" +
                                String.format("%.2f", bankAccount.getBalance()));
                    } else {
                        messageLabel.setText("Insufficient Funds For Withdrawal.");
                    }

                } catch (NumberFormatException e) {
                    messageLabel.setText("Please Enter A Valid Withdrawal Amount.");

                } catch (IllegalArgumentException e) {
                    messageLabel.setText(e.getMessage());
                }
            }
        }

        // Closes the application and displays the final account balance
        else if (event.getSource() == exitButton) {
            exitApplication();
        }
    }

    // Method to display the final balance before exiting
    private void exitApplication() {

        String finalMessage;

        if (bankAccount == null) {
            finalMessage = "No Bank Account Has Been Created.";
        } else {
            finalMessage = "Your Final Account Balance Is: $" +
                    String.format("%.2f", bankAccount.getBalance());
        }

        // Display the final balance in the JPanel
        messageLabel.setText(finalMessage);

        // Display the final balance in a message window
        JOptionPane.showMessageDialog(
                this,
                finalMessage,
                "Bank Account Summary",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Close the application
        dispose();
        System.exit(0);
    }

    // Main method to run the application
    public static void main(String[] args) {
        new BankBalanceGUI();
    }
}