public class BankAccount {

    // Stores the current bank account balance
    private double balance;

    // Parameterized constructor to create a bank account
    public BankAccount(double startingBalance) {
        if (!Double.isFinite(startingBalance) || startingBalance < 0) {
            throw new IllegalArgumentException("Starting balance must be $0.00 or more.");
        }
        balance = startingBalance;
    }

    // Method to retrieve the current balance for the account
    public double getBalance() {
        return balance;
    }

    // Method to deposit money into the bank account
    public void deposit(double amount) {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Deposit must be greater than $0.00.");
        }
        balance += amount;
    }

    // Method to withdraw money from the bank account
    public boolean withdraw(double amount) {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be greater than $0.00.");
        }

        if (amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }
}