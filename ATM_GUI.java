import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}

public class ATM_GUI extends JFrame implements ActionListener {
    private BankAccount account;
    private JTextField amountField;
    private JTextArea displayArea;
    private JButton checkButton, depositButton, withdrawButton, clearButton;

    public ATM_GUI() {
        account = new BankAccount(0);

        setTitle("ATM Interface");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        JLabel title = new JLabel("ATM MACHINE", JLabel.CENTER);
        title.setFont(new Font("Calibri", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        centerPanel.add(new JLabel("Enter amount:"));
        amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(100,50));
        centerPanel.add(amountField);

        checkButton = new JButton("Check Balance");
        checkButton.setBackground(Color.BLACK);
        checkButton.setForeground(Color.WHITE);
        depositButton = new JButton("Deposit");
        depositButton.setBackground(Color.BLACK);
        depositButton.setForeground(Color.WHITE);
        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBackground(Color.BLACK);
        withdrawButton.setForeground(Color.WHITE);
        clearButton = new JButton("Clear");
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.WHITE);

        centerPanel.add(checkButton);
        centerPanel.add(depositButton);
        centerPanel.add(withdrawButton);
        centerPanel.add(clearButton);

        add(centerPanel, BorderLayout.CENTER);

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Arial",Font.BOLD,15));
        displayArea.setEditable(false);
        displayArea.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        checkButton.addActionListener(this);
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        clearButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String input = amountField.getText().trim();
        double amount = 0;

        if (!input.isEmpty()) {
            try {
                amount = Double.parseDouble(input);
                if (amount < 0) {
                    displayArea.setText("Enter a positive amount.");
                    return;
                }
            } catch (NumberFormatException ex) {
                displayArea.setText("Invalid input. Enter a numeric value.");
                return;
            }
        }

        if (e.getSource() == checkButton) {
            displayArea.setText("Current Balance: ₹" + account.getBalance());
        } else if (e.getSource() == depositButton) {
            account.deposit(amount);
            displayArea.setText("Deposited ₹" + amount + "\nNew Balance: ₹" + account.getBalance());
        } else if (e.getSource() == withdrawButton) {
            if (account.withdraw(amount)) {
                displayArea.setText("Withdrawn ₹" + amount + "\nNew Balance: ₹" + account.getBalance());
            } else {
                displayArea.setText("Insufficient balance or invalid amount.");
            }
        } else if (e.getSource() == clearButton) {
            amountField.setText("");
            displayArea.setText("");
        }
    }

    public static void main(String[] args) {
        new ATM_GUI();
    }
}