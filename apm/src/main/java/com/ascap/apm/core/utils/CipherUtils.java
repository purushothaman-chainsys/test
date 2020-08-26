package com.ascap.apm.core.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

/**
 * The Class CipherUtils.
 */
public final class CipherUtils {

    /** The Constant DESEDE_ENCRYPTION_SCHEME. */
    protected static final String DESEDE_ENCRYPTION_SCHEME = "DESede";

    /** The Constant DES_ENCRYPTION_SCHEME. */
    protected static final String DES_ENCRYPTION_SCHEME = "DES";

    private CipherUtils() {

    }

    /**
     * Gets the secret key.
     *
     * @param key the key
     * @param encScheme the enc scheme
     * @return the secret key
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws NoSuchPaddingException the no such padding exception
     * @throws InvalidKeySpecException the invalid key spec exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     * @throws InvalidKeyException the invalid key exception
     */
    private static SecretKey getSecretKey(String key, String encScheme)
        throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {

        key = key + key;
        byte[] keyAsBytes = key.getBytes(StandardCharsets.UTF_8);
        KeySpec keySpec = null;
        SecretKeyFactory keyFactory = null;
        if (encScheme.equals(DESEDE_ENCRYPTION_SCHEME))
            keySpec = new DESedeKeySpec(keyAsBytes);
        else if (encScheme.equals(DES_ENCRYPTION_SCHEME))
            keySpec = new DESKeySpec(keyAsBytes);
        else
            throw new IllegalArgumentException("Encryption scheme not supported: " + encScheme);

        keyFactory = SecretKeyFactory.getInstance(encScheme);
        return keyFactory.generateSecret(keySpec);

    }

    /**
     * Encrypt.
     *
     * @param plainString the plain string
     * @param key the key
     * @param encScheme the enc scheme
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws NoSuchPaddingException the no such padding exception
     * @throws InvalidKeyException the invalid key exception
     * @throws InvalidKeySpecException the invalid key spec exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     * @throws IllegalBlockSizeException the illegal block size exception
     * @throws BadPaddingException the bad padding exception
     */
    protected static String encrypt(String plainString, String key, String encScheme)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException,
        IllegalBlockSizeException, BadPaddingException {

        if (plainString == null || plainString.trim().length() == 0)
            throw new IllegalArgumentException("Unencrypted String was null or empty");

        String encrypted = null;
        key = key + key;
        Cipher cipher = Cipher.getInstance(encScheme);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key, encScheme));
        byte[] cleartext = plainString.getBytes(StandardCharsets.UTF_8);
        byte[] ciphertext = cipher.doFinal(cleartext);

        encrypted = Base64.getEncoder().encodeToString(ciphertext);
        String lineDelimiter = System.getProperty("line.separator");
        encrypted = encrypted.replaceAll(lineDelimiter, "");
        return encrypted;
    }

    /**
     * Decrypt.
     *
     * @param encryptedString the encrypted string
     * @param key the key
     * @param encScheme the enc scheme
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws NoSuchPaddingException the no such padding exception
     * @throws InvalidKeyException the invalid key exception
     * @throws InvalidKeySpecException the invalid key spec exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws IllegalBlockSizeException the illegal block size exception
     * @throws BadPaddingException the bad padding exception
     */
    protected static String decrypt(String encryptedString, String key, String encScheme)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException,
        IOException, IllegalBlockSizeException, BadPaddingException {
        String lineDelimiter = System.getProperty("line.separator");
        encryptedString = encryptedString.replaceAll(lineDelimiter, "");
        key = key + key;
        Cipher cipher = Cipher.getInstance(encScheme);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key, encScheme));

        byte[] cleartext = Base64.getDecoder().decode(encryptedString);
        byte[] ciphertext = cipher.doFinal(cleartext);

        return bytes2String(ciphertext);
    }

    /**
     * Bytes 2 string.
     *
     * @param bytes the bytes
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    private static String bytes2String(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
