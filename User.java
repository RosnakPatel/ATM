// Import necessary classes
import javax.crypto.SecretKey;
import java.util.ArrayList;

public class User {

    // Variable declaration and initialization
    private String firstName; // The first name of the user
    private String lastName; // The last name of the user
    private String uuid; // The universal unique identifier of the user
    private String pin; // The encrypted pin code of the user
    private ArrayList<Account> accounts; // The accounts owned by the user
    private EncryptPin encryptPin = new EncryptPin(); // The encryption object
    private SecretKey key; // The key to encrypt/decrypt the pin
    private Bank bank; // The user's bank

    /**
     *
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param pin the user's encrypted pin
     * @param bank the user's bank
     */
    public User(String firstName, String lastName, String pin, Bank bank) {
        // Set user's name
        this.firstName = firstName;
        this.lastName = lastName;
        this.bank = bank;

        // Call register pin to encrypt pin and assign it to the user
        registerPin(pin);

        // Generate a uuid
        this.uuid = bank.getNewUserUUID();

        // Create the empty list of accounts
        this.accounts = new ArrayList<>();

        // Print registration message
        System.out.printf("New user %s, %s with UUID %s has been created!\n", firstName, lastName, this.uuid);
    }

    /**
     * Register the pin and assign it to the user object
     * @param enteredPin the decrypted pin entered
     */
    private void registerPin(String enteredPin) {
        // Error handling
        try {
            // Initialize the encryption cipher and get key
            key = encryptPin.init();

            // Encrypt the pin with the encryption cipher and assign the value to pin
            this.pin = encryptPin.encrypt(enteredPin);

        } catch (Exception e) {
            // Message if error encountered
            System.err.println("Error encountered.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Add account to user's account list
     * @param anAccount The account being added
     */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount); // Add account to accounts arrayList
    }

    /**
     * Return the user's UUID
     * @return the user's UUID
     */
    public String getUUID() {
        return uuid; // Return user's uuid
    }

    /**
     * Validate the entered pin with the actual pin
     * @param aPin the pin entered by the user
     * @return whether the pin was valid (true) or not (false)
     */
    public boolean validatePin(String aPin) {
        // Variable declaration
        String decryptedData = null;
        boolean valid;

        // Error handling
        try {
            encryptPin.init1(key); // Initialize the encryption algorithm with the key
            decryptedData = encryptPin.decrypt(this.pin); // Decrypt the encrypted pin

        } catch (Exception e) {
            // Message if error encountered
            System.out.println("Error encountered.");
            e.printStackTrace();
            System.exit(1);
        }

        // Check if the pin is valid
        if (decryptedData.equalsIgnoreCase(aPin)) {
            valid = true;
        }
        else {
            valid = false;
        }

        // Return whether the pin is valid or not
        return valid;
    }

    /**
     * Print each account name and balance
     */
    public void printAccountsSummary() {
        // Variable declaration
        String accSum;

        // Print the user's name followed by the name of each account and its balance
        System.out.printf("%s's Accounts:\n", firstName);
        for (int i = 0; i < accounts.size(); i++) {

            // Change format depending on positive or negative balance
            if (accounts.get(i).getBalance()>=0) {
                accSum = String.format("%.0f) %s (%s): $%.2f", (float)i+1, accounts.get(i).getAccountName(),
                        accounts.get(i).getUUID(), accounts.get(i).getBalance());
            }

            else {
                accSum = String.format("%.0f) %s (%s): -$%s", (float)i+1, accounts.get(i).getAccountName(),
                        accounts.get(i).getUUID(), Math.abs(accounts.get(i).getBalance()));
            }
            System.out.println(accSum);
        }
    }

    /**
     * Return the user's full name
     * @return the user's full name
     */
    public String getName() {
        // Variable declaration
        String name;

        // Concatenate the first and last names
        name = firstName + " " + lastName;

        // Return the full name
        return name;
    }

    /**
     * Return the number of accounts the user has
     * @return the number of accounts the user has
     */
    public int getAccountsSize() {
        // Return the number of accounts
        return accounts.size();
    }

    /**
     * Print the account transaction history
     * @param accIndex The account that needs its transaction history checked
     */
    public void printAccTransHistory(int accIndex) {
        // Access the account that needs to be checked and call the getTransHistory method
        accounts.get(accIndex).getTransHistory();
    }

    /**
     * Transfer funds from one account to another
     * @param sender The account that is sending the funds
     * @param receiver The account that is receiving the funds
     * @param amount The amount that is being transferred
     * @param message The message associated with the transfer
     */
    public void transferFunds(int sender, int receiver, double amount, String message) {
        // Withdraw funds from the sender accounts
        this.withdrawFunds(sender, amount, message);

        // Deposit funds to the receiver account
        this.depositFunds(receiver, amount, message);
    }

    /**
     * Return the balance of an account
     * @param acc The account that needs to be accessed
     * @return The balance of the account being accessed
     */
    public double getBalance(int acc) {
        // Variable declaration
        double balance;

        // Access the account and call the getBalance method
        balance = accounts.get(acc).getBalance();

        // Return the balance
        return balance;
    }

    /**
     * Deposit funds into an account
     * @param acc The account that funds are being deposited into
     * @param amount The amount being deposited
     * @param message The message associated with the deposit
     */
    public void depositFunds(int acc, double amount, String message) {
        // Access the account and call the transFunds method
        accounts.get(acc).transFunds(amount, message);
    }

    /**
     * Withdraw funds from an account
     * @param acc The account that funds are being withdrawn from
     * @param amount The amount being withdrawn
     * @param message The message associated with the withdrawal
     */
    public void withdrawFunds(int acc, double amount, String message) {
        // Access the account and call the transFunds method with a negative amount
        accounts.get(acc).transFunds(amount*-1, message);
    }

    /**
     * Return the name of the account
     * @param acc The account being accessed
     * @return The name of the account being accessed
     */
    public String getAccountName(int acc) {
        // Return the name of the account
        return accounts.get(acc).getAccountName();
    }
}