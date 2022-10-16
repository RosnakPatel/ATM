// Import necessary classes
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;

public class EncryptPin {

    // Variable declaration and initialization
    private SecretKey key; // The key for the encryption
    private final int KEY_SIZE = 128; // The size of the key for the encryption
    private final int DATA_LENGTH = 128; // The length of the data
    private Cipher encryptionCipher; // The cipher used for the encryption

    /**
     * Initialize the key
     * @return the key to encrypt and decrypt
     * @throws Exception If instance "AES" not found
     */
    public SecretKey init() throws Exception {
        // Generate the key and assign it to the variable key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); // Generate the key for AES
        keyGenerator.init(KEY_SIZE); // Initialize the keyGenerator with the key size
        key = keyGenerator.generateKey(); // Assign the generated key to key
        return key;
    }

    /**
     * Initialize the key if key is already found
     * @param key1 The key
     */
    public void init1(SecretKey key1) {
        key = key1; // Assign the inputted key to key
    }

    /**
     * Encrypt a String
     * @param data The string being encrypted
     * @return The encrypted string
     * @throws Exception If instance "AES/GCM/NoPadding not found
     */
    public String encrypt(String data) throws Exception {
        // Variable declaration and initialization
        byte[] dataInBytes = data.getBytes(); // Get the bytes of the message
        byte[] encryptedBytes; // Declare encryptedBytes

        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding"); // Get the cipher's instance
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key); // Initialize the cipher and pass the key as an argument
        encryptedBytes = encryptionCipher.doFinal(dataInBytes); // Encrypt the data in the form of bytes and instantiate encryptedBytes
        return encode(encryptedBytes); // Return the encrypted data as a String by calling the encode method
    }

    /**
     * Encode the bytes to string
     * @param data The data in bytes
     * @return The data in string
     */
    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data); // Convert the data to String
    }

    /**
     * Decrypt a string
     * @param encryptedData The encrypted data
     * @return The decrypted data
     * @throws Exception If instance "AES/GCM/NoPadding not found
     */
    public String decrypt(String encryptedData) throws Exception {
        // Variable declaration and initialization
        byte[] dataInBytes = decode(encryptedData); // Get the bytes of the message
        byte[] decryptedBytes;  // Declare decryptedBytes

        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding"); // Get the cipher's instance
        GCMParameterSpec spec = new GCMParameterSpec(DATA_LENGTH, encryptionCipher.getIV()); // The decryption cipher
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec); // Initialize the decryption cipher
        decryptedBytes = decryptionCipher.doFinal(dataInBytes); // Decrypt the data which is in bytes and instantiate decryptedBytes
        return new String(decryptedBytes); // Return the decrypted data as a String by calling the decode method
    }

    /**
     * Convert data from string to bytes
     * @param data The data in string
     * @return The data in bytes
     */
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

}
