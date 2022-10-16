import javax.crypto.SecretKey;

public class Admin {

    // Variable declaration
    private String firstName;
    private String lastName;
    private String uuid;
    private String password;
    private Bank bank;
    SecretKey eKey;
    private EncryptPin encryptPin = new EncryptPin();

    /**
     * Constructor
     * @param firstName The admin's first name
     * @param lastName The admin's last name
     * @param password The admin's password
     * @param bank The admin's bank
     */
    public Admin(String firstName, String lastName, String password, Bank bank) {
        // Variable declaration and initialization
        this.firstName = firstName;
        this.lastName = lastName;
        this.bank = bank;

        // Register the password
        registerPassword(password);

        // Generate uuid
        uuid = bank.getNewAccountUUID();
    }

    /**
     * Return the admin's uuid
     * @return The admin's uuid
     */
    public String getUUID() {
        // Return the admin's uuid
        return uuid;
    }

    /**
     * Validate the password
     * @param password The entered password
     * @return If the password is valid or not
     */
    public boolean validatePassword(String password) {
        // Variable declaration
        String decryptedData = null;
        boolean valid;

        // Error handling
        try {
            encryptPin.init1(eKey); // Initialize the encryption algorithm with the key
            decryptedData = encryptPin.decrypt(this.password); // Decrypt the encrypted pin

        } catch (Exception e) {
            // Message if error encountered
            System.out.println("Error encountered.");
            e.printStackTrace();
            System.exit(1);
        }

        // Check if the pin is valid
        if (decryptedData.equalsIgnoreCase(password)) {
            valid = true;
        }
        else {
            valid = false;
        }

        // Return whether the pin is valid or not
        return valid;
    }

    /**
     * Register the password
     * @param enteredPassword The password entered
     */
    private void registerPassword(String enteredPassword) {
        // Error handling
        try {
            // Initialize the encryption cipher and get key
            eKey = encryptPin.init();

            // Encrypt the pin with the encryption cipher and assign the value to pin
            this.password = encryptPin.encrypt(enteredPassword);

        } catch (Exception e) {
            // Message if error encountered
            System.err.println("Error encountered.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
