// Import necessary classes
import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts; // Create matrix, maybe 3D
    private String key;
    private ArrayList<Admin> admins;

    /**
     * Create new bank object
     * @param name the bank's name
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
        this.admins = new ArrayList<Admin>();
        key = "1234";
    }

    /**
     * Return the generated user uuid
     * @return the user universal unique identifier
     */
    public String getNewUserUUID() {

        // Declarations
        String uuid = "";
        Random rng = new Random();
        final int LEN = 8;
        boolean nonUnique = false;

        // Continue to loop until a unique UUID is found
        do {
            // Create UUID
            for (int i = 0; i <= LEN; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            //Make sure the created UUID is unique
            for (User u : users) {
                if (uuid.equalsIgnoreCase(u.getUUID())) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        // Return the uuid
        return uuid;
    }

    /**
     * Generate an account uuid
     * @return the account universal unique identifier
     */
    public String getNewAccountUUID() {

        // Declarations
        String uuid = "";
        Random rng = new Random();
        final int LEN = 16;
        boolean nonUnique = false;

        // Continue to loop until a unique UUID is found
        do {
            // Create UUID
            for (int i = 0; i <= LEN; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            //Make sure the created UUID is unique
            for (Account a : accounts) {
                if (uuid.equalsIgnoreCase(a.getUUID())) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        // Return the account uuid
        return uuid;
    }

    /**
     * Add account to the accounts list
     * @param anAccount The account being added
     */
    public void addAccount(Account anAccount) {
        // Add account to the accounts list
        this.accounts.add(anAccount);
    }

    /**
     * Create a user of the bank
     * @param firstName The user's first name
     * @param lastName The user's last name
     * @param pin the user's pin
     * @return the user object
     */
    public User addUser(String firstName, String lastName, String pin) {
        // Create a new User object and add it to the list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // Create a savings account
        Account newAccount = new Account("Savings", newUser, this);

        // Add to user and bank lists
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        // Return the newUser
        return newUser;
    }

    /**
     * Get user that matches the uuid and pin entered
     * @param uuid The user's uuid
     * @param pin The user's pin
     * @return the user if the uuid and pin match, else, null
     */
    public User userLogin(String uuid, String pin) {

        // Search through list of users
        for (User u : users) {

            // Check uuid is correct and pin
            if (uuid.equalsIgnoreCase(u.getUUID()) && u.validatePin(pin)) {
                return u;
            }
        }
        // If no user found
        return null;
    }

    /**
     * Get the bank name
     * @return the bank name
     */
    public String getBankName() {
        // Return the bank name
        return name;
    }

    /**
     * Validate the key entered by the admin
     * @param key The key entered by the admin
     * @return If the key was valid or not
     */
    public boolean validateKey(String key) {
        // Validate if the key the admin entered is the bank's key
        if (this.key.equalsIgnoreCase(key)) {
            return true;
        }

        return false;
    }

    /**
     * Add admin to admins array list
     * @param a The admin
     */
    public void addAdmin(Admin a) {
        // Add admin to admins arraylist
        admins.add(a);
    }

    /**
     * Admin login
     * @param uuid The uuid of the admin
     * @param password The password of the admin
     * @return The admin object
     */
    public Admin adminLogin(String uuid, String password) {

        // Search through list of admins
        for (Admin a : admins) {

            // Check uuid is correct and password
            if (uuid.equalsIgnoreCase(a.getUUID()) && a.validatePassword(password)) {
                return a;
            }
        }
        // If no user found
        return null;
    }

}
