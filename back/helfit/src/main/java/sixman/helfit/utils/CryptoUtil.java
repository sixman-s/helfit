package sixman.helfit.utils;

import lombok.Getter;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class CryptoUtil {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256;
    private static final int IV_SIZE = 16;
    private static final String SPLIT_CHARACTER = "|";

    @Getter
    private final SecretKey key;

    public CryptoUtil(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        this.key = new SecretKeySpec(decodedKey, ALGORITHM.split("/")[0]);
    }

    public CryptoUtil(SecretKey key) {
        this.key = key;
    }

    public CryptoUtil() throws NoSuchAlgorithmException {
        this(generateSymmetricKey());
    }

    public String encrypt(String plainText) throws GeneralSecurityException {
        byte[] iv = generateIV();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(plainText.getBytes());

        return Base64.getEncoder().encodeToString(iv) +
                   SPLIT_CHARACTER +
                   Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String cipherText) throws GeneralSecurityException {
        String[] parts = cipherText.split("\\" + SPLIT_CHARACTER);

        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] encrypted = Base64.getDecoder().decode(parts[1]);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted);
    }

    private static SecretKey generateSymmetricKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM.split("/")[0]);
        generator.init(KEY_SIZE);

        return generator.generateKey();
    }

    private static byte[] generateIV() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[IV_SIZE];
        secureRandom.nextBytes(iv);

        return iv;
    }
}
