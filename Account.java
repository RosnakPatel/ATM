// Import necessary classes
import java.util.ArrayList;

public class Account {

    // Variable declaration
    private String accountName; // The name of the account
    private double balance; // The balance of the account
    private String uuid; // The universal unique identified of the account
    private User holder; // The user who owns the account
    private ArrayList<Transaction> transactions; // The list of transactions made with this account

    /**
     * Account constructor
     * @param accountName the name of the account
     * @param holder the User that holds the account
     * @param bank the bank of which this account is a part of
     */

    public Account(String accountName, User holder, Bank bank) {
        // Set the account name and holder
        this.accountName = accountName;
        this.holder = holder;

        // Get new account UUID
        this.uuid = bank.getNewAccountUUID();

        // Initialize transactions
        this.transactions = new ArrayList<>();
    }

    /**
     * Get account uuid
     * @return the account uuid
     */
    public String getUUID() {
        // Return the account uuid
        return uuid;
    }

    /**
     * Get account name
     * @return The account name
     */
    public String getAccountName() {
        // Return the account name
        return accountName;
    }

    /**
     * Calculate the account balance
     * @return The account balance
     */
    public double getBalance() {
        balance = 0;

        // Calculate the account balance based on the transactions
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }

        // Return the calculated balance
        return balance;
    }

    /**
     * Print the transaction history for the account
     */
    public void getTransHistory() {
        // For each transaction, print the transaction details
        System.out.printf("Transaction history for %s (%s):\n", accountName, uuid);
        for (Transaction t : transactions) {
            t.getTransactionDetails();
        }
    }

    /**
     * Transfer funds from/to an account
     * @param amount The amount being transferred
     * @param message The message associated with the transfer
     */
    public void transFunds(double amount, String message) {
        Transaction transaction;

        transaction = new Transaction(amount, this, message);
        transactions.add(transaction);
        transaction.getTransactionDetails();
    }

}
