package com.ascap.apm.common.exception.cache;

import com.ascap.apm.common.exception.PrepSystemException;

/**
 * @author Sudhar_Krishnamachar Used by cache classes to indicate thet cahce is locked and no change can be performed on
 *         the cache data
 */
public class CacheLockedException extends PrepSystemException {

    private static final long serialVersionUID = 3565234202697477413L;

    /**
     * Constructor for CacheLockedException.
     * 
     * @param errorKey
     */
    public CacheLockedException(String errorKey) {
        super(errorKey);
    }

    /**
     * Constructor for CacheLockedException.
     * 
     * @param errorKey
     * @param dynamicValues
     */
    public CacheLockedException(String errorKey, String[] dynamicValues) {
        super(errorKey, dynamicValues);
    }

    /**
     * Constructor for CacheLockedException.
     * 
     * @param errorKey
     * @param dynamicValues
     * @param exception
     */
    public CacheLockedException(String errorKey, String[] dynamicValues, Exception exception) {
        super(errorKey, dynamicValues, exception);
    }

}
