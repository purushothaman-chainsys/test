package com.ascap.apm.common.exception.cache;

import com.ascap.apm.common.exception.PrepSystemException;

/**
 * @author Sudhar_Krishnamachar Used by cache classes to indicate the key is not found in the cache
 */
public class KeyNotFoundException extends PrepSystemException {

    private static final long serialVersionUID = -4669666377130228317L;

    /**
     * Constructor for KeyNotFoundException.
     * 
     * @param errorKey
     */
    public KeyNotFoundException(String errorKey) {
        super(errorKey);
    }

    /**
     * Constructor for KeyNotFoundException.
     * 
     * @param errorKey
     * @param dynamicValues
     */
    public KeyNotFoundException(String errorKey, String[] dynamicValues) {
        super(errorKey, dynamicValues);
    }

    /**
     * Constructor for KeyNotFoundException.
     * 
     * @param errorKey
     * @param dynamicValues
     * @param exception
     */
    public KeyNotFoundException(String errorKey, String[] dynamicValues, Exception exception) {
        super(errorKey, dynamicValues, exception);
    }

}
