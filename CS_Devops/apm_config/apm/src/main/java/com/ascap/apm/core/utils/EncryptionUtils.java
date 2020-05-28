/*
 * Copyright(c) 2013 Chain-Sys Corporation Inc.
 * Duplication or distribution of this code in part or in whole by any media
 * without the express written permission of Chain-Sys Corporation or its agents is
 * strictly prohibited.
 *
 * REVISION         DATE            NAME     DESCRIPTION
 * 511.101       Oct 19, 2013       MDR      Initial Code
 */
package com.ascap.apm.core.utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ascap.apm.common.exception.BaseException;
import com.ascap.apm.common.exception.PrepSystemException;

/**
 * The Class StringEncrypter.
 */
public final class EncryptionUtils {

    private EncryptionUtils() {

    }

    /**
     * Encrypt.
     *
     * @param plainString the plain string
     * @return the string
     * @throws BaseException the base framework exception
     */
    public static String encrypt(String plainString) throws PrepSystemException {
        return encrypt(plainString, EncryptionType.DESede);
    }

    /**
     * Encrypt.
     *
     * @param plainString the plain string
     * @param encKey the enc key
     * @return the string
     * @throws BaseException the base framework exception
     */
    public static String encrypt(String plainString, String encKey) throws PrepSystemException {
        return encrypt(plainString, encKey, EncryptionType.DESede);
    }

    /**
     * Encrypt.
     *
     * @param plainString the plain string
     * @param encType the enc type
     * @return the string
     * @throws BaseException the base framework exception
     */
    public static String encrypt(String plainString, EncryptionType encType) throws PrepSystemException {
        return encrypt(plainString, "ASCAPAPM", encType);
    }

    /**
     * Encrypt.
     *
     * @param plainString the plain string
     * @param encKey the enc key
     * @param encType the enc type
     * @return the string
     * @throws BaseException the base framework exception
     */
    public static String encrypt(String plainString, String encKey, EncryptionType encType) throws PrepSystemException {
        try {
            return CipherUtils.encrypt(plainString, encKey, encType.name());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException
            | IllegalBlockSizeException | BadPaddingException e) {
            throw new PrepSystemException(e);
        }
    }

    /**
     * Decrypt.
     *
     * @param encryptedString the encrypted string
     * @return the string
     * @throws BaseException the base framework exception
     */
    public static String decrypt(String encryptedString) throws PrepSystemException {
        return decrypt(encryptedString, EncryptionType.DESede);
    }

    /**
     * Decrypt.
     *
     * @param encryptedString the encrypted string
     * @param decKey the dec key
     * @return the string
     * @throws BaseException the base framework exception
     */
    public static String decrypt(String encryptedString, String decKey) throws PrepSystemException {
        return decrypt(encryptedString, decKey, EncryptionType.DESede);
    }

    /**
     * Decrypt.
     *
     * @param encryptedString the encrypted string
     * @param encType the enc type
     * @return the string
     * @throws BaseException the base framework exception
     */
    public static String decrypt(String encryptedString, EncryptionType encType) throws PrepSystemException {
        return decrypt(encryptedString, "ASCAPAPM", encType);
    }

    /**
     * Decrypt.
     *
     * @param encryptedString the encrypted string
     * @param decKey the dec key
     * @param encType the enc type
     * @return the string
     * @throws BaseException the base framework exception
     */
    public static String decrypt(String encryptedString, String decKey, EncryptionType encType)
        throws PrepSystemException {
        try {
            return CipherUtils.decrypt(encryptedString, decKey, encType.name());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException
            | IllegalBlockSizeException | BadPaddingException | IOException e) {
            throw new PrepSystemException(e);
        }
    }
}
