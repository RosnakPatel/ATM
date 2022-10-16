// Import necessary classes
import java.util.Date;

public class Transaction {

    // Variable declaration
    private double amount; // The amount transacted
    private Date timestamp; // The time and date of transaction
    private String message; // The message related to the transaction
    private Account accountUsed; // The account used for the transactions

    /**
     * Create new Transaction object
     * @param amount the amount used in the transaction
     * @param accountUsed the account used in the transaction
     */
    public Transaction(double amount, Account accountUsed) {
        this.amount = amount;
        this.accountUsed = accountUsed;
        this.timestamp = new Date();
        this.message = "";
    }

    /**
     * Create new Transaction object
     * @param amount the amount used in the transaction
     * @param accountUsed the account used in the transaction
     * @param message the message for the transaction
     */
    public Transaction(double amount, Account accountUsed, String message) {
        // Call the two-argument constructor first
        this(amount, accountUsed);

        // Instantiate the message
        this.message = message;
    }

    /**
     * Return the amount of the transaction
     * @return The amount of the transaction
     */
    public double getAmount() {
        // Return the amount of the transaction
        return amount;
    }

    /**
     * Get the transaction details and print them formatted correctly
     */
    public void getTransactionDetails() {
        // Variable declaration
        String transDetails;

        // Depending on the amount, format the details differently
        if (amount >= 0) {
            transDetails = String.format("$%.2f: %s: %s", amount, timestamp.toString(), message);
        }
        else {
            transDetails = String.format("-$%.2f: %s: %s", Math.abs(amount), timestamp.toString(), message);
        }

        // Output the details
        System.out.println(transDetails);
    }
}
