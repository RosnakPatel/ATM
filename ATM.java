// Import necessary classes
import java.util.Scanner;

public class ATM {

    // Instantiate Scanner
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // Variable declaration
        User currentUser;

        // Instantiate Bank
        Bank bank = new Bank("The Bank of Programmers");

        // Add a user to the bank
        User aUser = bank.addUser("John", "Doe", "1234");

//        // Ensure the entered pin is only 4 characters
//        do {
//            enteredPin = input.next(); // Assign the entered value to enteredPin
//        } while (enteredPin.length()!=4);

        // Add a chequing account
        Account chequingAccount = new Account("Chequing", aUser, bank);
        aUser.addAccount(chequingAccount);
        bank.addAccount(chequingAccount);

        // Loop until login is successful
        while (true) {

            // The user if successfully logged in
            ATM.generalMenuPrompt(bank);

        }
    }

    /**
     * The general menu prompt
     * @param bank The bank being used
     */
    public static void generalMenuPrompt(Bank bank) {
        // Variable declaration
        int choice;
        User currentUser;
        Admin currentAdmin;

        // Loop until valid choice
        do {
            // Prompt user for their choice
            System.out.println("What would you like to do today?\n1) User login\n2) Admin login\n3) Admin Registration" +
                    " (Select this if this program has just been ran)");
            choice = input.nextInt();

            // Invalid response
            if (choice < 1 || choice > 3) {
                System.out.println("Invalid response. Try again.");
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1 -> {
                // Login to user and print their user menu
                while (true) { // May not be necessary
                    currentUser = mainMenuPrompt(bank);
                    printUserMenu(currentUser);
                }
            }
            case 2 -> {
                currentAdmin = adminMenuPrompt(bank);
            }
            case 3 -> adminRegistration(bank);
        }
    }

    /**
     * Register a new admin
     * @param bank The admin's bank
     */
    public static void adminRegistration(Bank bank) {
        // Declare variables;
        String enteredKey;
        Admin a;
        String firstName;
        String lastName;
        String password;
        boolean valid;

        // Prompt admin for the bank key
        System.out.printf("Enter the key for %s (1234 for The Bank of Programmers)\n", bank.getBankName());
        enteredKey = input.next();

        // Check if the key is valid
        valid = bank.validateKey(enteredKey);

        // If the key is valid, add admin to bank admin list
        if (valid) {

            // Prompt admin for information
            System.out.println("Enter your first name: ");
            firstName = input.next();

            System.out.println("Enter your last name: ");
            lastName = input.next();

            System.out.println("Enter a secure password: ");
            password = input.next();

            // Create admin object
            a = new Admin(firstName, lastName, password, bank);

            // Log admin object in database
            bank.addAdmin(a);

            System.out.printf("\nAdmin registered successfully with uuid: %s\n\n", a.getUUID());
        }
        else {
            System.out.println("Registration failed");
        }

        // Call generalMenuPrompt
        generalMenuPrompt(bank);

    }

    /**
     * The admin login menu
     * @param bank The admin's bank
     * @return The admin object logged in
     */
    public static Admin adminMenuPrompt(Bank bank) {
        // Variable declaration
        String uuid;
        String password;
        Admin theAdmin;

        // Prompt user for their uuid/pin until it is associated with a valid user
        do {
            System.out.printf("\nHello administrator, how may we assist you today?", bank.getBankName());

            System.out.print("\nEnter your admin ID: ");
            uuid = input.next();
            System.out.print("Enter your password: ");
            password = input.next();

            // Get user that matches the uuid and pin
            theAdmin = bank.adminLogin(uuid, password);

            if (theAdmin == null) {
                System.out.println("Admin not found.");
            }

            else {
                System.out.println("\nLogin Success!\n");
            }

        } while (theAdmin == null);

        // Return the successfully logged-in admin
        return theAdmin;
    }


    /**
     * The menu login which prompts user to enter their uuid and pin until a correct one is found
     * @param bank The user's bank
     * @return The user that successfully logged in
     */
    public static User mainMenuPrompt(Bank bank) {
        // Variable declaration
        String uuid;
        String pin;
        User theUser;

        // Prompt user for their uuid/pin until it is associated with a valid user
        do {
            System.out.printf("Thank you for choosing %s, how may we assist you today?", bank.getBankName());

            System.out.print("\nEnter your user ID: ");
            uuid = input.next();
            System.out.print("Enter your pin: ");
            pin = input.next();

            // Get user that matches the uuid and pin
            theUser = bank.userLogin(uuid, pin);

            if (theUser == null) {
                System.out.println("User not found.");
            }

            else {
                System.out.println("\nLogin Success!\n");
            }

        } while (theUser == null);

        // Return the successfully logged-in user
        return theUser;
    }

    /**
     * Print the user menu
     * @param theUser The user
     */
    public static void printUserMenu(User theUser) {

        // Declare variables
        int choice = 0;

        // Print summary of accounts
        theUser.printAccountsSummary();

        // User menu
        do {
            System.out.printf("\nHello %s, select what you would like to do today: ", theUser.getName());
            System.out.println("\n\n1) Show account transaction history" +
                    "\n2) Withdraw money" +
                    "\n3) Deposit money" +
                    "\n4) Transfer money" +
                    "\n5) Quit");

            System.out.println("Enter choice: ");
            try {
                choice = input.nextInt();
            } catch (Exception e) {
                System.out.println("An error has occurred. Try again.");
            }

            if (choice < 1 || choice > 5) {
                System.out.println("That choice does not exist, please try again.\n\n");
            }

        } while (choice < 1 || choice > 5);

        // Run specific program with choice
        switch (choice) {
            case 1 -> ATM.showTransactionHistory(theUser);
            case 2 -> ATM.withdrawFunds(theUser);
            case 3 -> ATM.depositFunds(theUser);
            case 4 -> ATM.transferFunds(theUser);
            case 5 -> {
                System.out.printf("Farewell %s!", theUser.getName());
                System.exit(1);
            }
        }
        System.out.println("\n");
        ATM.printUserMenu(theUser);
    }

    /**
     * Show the transaction history of a specific account
     * @param theUser The user
     */
    public static void showTransactionHistory(User theUser) {
        // Variable declaration
        int accountChoice;

        String prompt = "Enter the number of the account you would like to see the transaction history of";

        // Prompt user to select an account
        accountChoice = ATM.selectAccount(theUser, prompt);

        // Print the transaction history
        theUser.printAccTransHistory(accountChoice);
    }

    /**
     * Transfer funds from one account to another
     * @param theUser the user
     */
    public static void transferFunds(User theUser) {
        // Variable declaration and initialization
        int senderChoice;
        int recipientChoice;
        String senderPrompt = "Select which account will send the funds";
        String recipientPrompt = "Select which account will receive the funds";
        double amount;
        String message;
        double maxTransfer;

        // Select the sender and recipient accounts
        senderChoice = ATM.selectAccount(theUser, senderPrompt);
        recipientChoice = ATM.selectAccount(theUser, recipientPrompt);

        // The maximum amount of money that can be transferred
        maxTransfer = theUser.getBalance(senderChoice);

        // Prompt user to input valid amount. If invalid, repeat
        do {
            System.out.println("How much money would you like to transfer ($1-$" + maxTransfer + ") :");
            amount = input.nextDouble();

            if (amount < 0 || amount > maxTransfer) {
                System.out.println("That is an invalid amount. Select a number between $1-$" + maxTransfer + ".");
            }

        } while (amount < 0 || amount > maxTransfer);

        // Prompt user for an optional message
        System.out.println("Enter a message associated with the transfer (enter -1 for none): ");
        message = input.nextLine();
        if (message.equalsIgnoreCase("-1")) {
            message = "";
        }

        // Transfer the funds
        theUser.transferFunds(senderChoice, recipientChoice, amount, message);
    }

    /**
     * Prompt user to select a valid account
     * @param theUser The user
     * @param prompt The prompt to ask the user
     * @return The selected account
     */
    public static int selectAccount(User theUser, String prompt) {
        // Variable declaration and initialization
        int accountChoice;
//        boolean validAccount = true;
        int accountSize = theUser.getAccountsSize();

        // Prompt user to enter valid account
        do {
            System.out.println(prompt + " (1-" + accountSize + "):");
            accountChoice = input.nextInt();
            if (accountChoice < 1 || accountChoice > accountSize) {
                System.out.printf("Invalid account number. Please try again." +
                        "Choose a number between 1-%i", accountSize);
            }
            else {
                System.out.printf("Account found: " + theUser.getAccountName(accountChoice-1) + "\n\n");
            }
        } while (accountChoice < 1 || accountChoice > accountSize);

        // Return the selected account index
        return accountChoice-1;
    }

    /**
     * Deposit funds into an account
     * @param theUser The user
     */
    public static void depositFunds(User theUser) {
        // Variable declaration and initialization
        int accountChoice;
        String prompt = "Select which account to deposit funds into";
        double amount;
        String message;

        // Prompt user to select an account
        accountChoice = ATM.selectAccount(theUser, prompt);

        // Prompt user to enter a valid amount to deposit
        do {
            System.out.println("How much money would you like to deposit into this account:");
            amount = input.nextDouble();

            if (amount < 0) {
                System.out.println("That is an invalid amount. Select a number above or equal to $1.");
            }

        } while (amount < 0);

        // Prompt user for an optional message
        System.out.println("Enter a message associated with the deposit (enter -1 for none): ");
        message = input.next();
        if (message.equalsIgnoreCase("-1")) {
            message = "";
        }

        // Deposit the funds into the account
        theUser.depositFunds(accountChoice, amount, message);
    }

    /**
     * Withdraw funds from an account
     * @param theUser The user
     */
    public static void withdrawFunds(User theUser) {
        // Variable declaration and initialization
        int accountChoice;
        String prompt = "Select which account to withdraw funds from";
        double amount;
        String message;
        double maxAmount;

        // Prompt user to select an account
        accountChoice = ATM.selectAccount(theUser, prompt);

        // If account has a zero or negative balance, cancel withdrawal
        if (theUser.getBalance(accountChoice) <= 0) {
            System.out.println("Insufficient funds.\n");
            ATM.printUserMenu(theUser);
        }

        // The maximum amount that can be withdrawn
        maxAmount = theUser.getBalance(accountChoice);

        // Prompt user to enter a valid amount of money to withdraw
        do {
            System.out.println("How much money would you like to withdraw from this account ($1-$" + maxAmount + ") :");
            amount = input.nextDouble();

            if (amount < 1 || amount > maxAmount) {
                System.out.println("That is an invalid amount. Select a number between $1-$" + maxAmount + ".");
            }

        } while (amount < 1 || amount > maxAmount);

        // Prompt user to enter an optional message
        System.out.println("Enter a message associated with the withdrawal (enter -1 for none): ");
        message = input.nextLine();
        if (message.equalsIgnoreCase("-1")) {
            message = "";
        }

        // Withdraw funds
        theUser.withdrawFunds(accountChoice, amount, message);
    }
}

