package com.ascap.apm.common.exception.cache;

import com.ascap.apm.common.exception.PrepSystemException;

/**
 * @author Sudhar_Krishnamachar To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class InvalidCacheException extends PrepSystemException {

    private static final long serialVersionUID = -9170928602724117452L;

    /**
     * Constructor for InvalidCacheException.
     * 
     * @param errorKey
     */
    public InvalidCacheException(String errorKey) {
        super(errorKey);
    }

    /**
     * Constructor for InvalidCacheException.
     * 
     * @param errorKey
     * @param dynamicValues
     */
    public InvalidCacheException(String errorKey, String[] dynamicValues) {
        super(errorKey, dynamicValues);
    }

    /**
     * Constructor for InvalidCacheException.
     * 
     * @param errorKey
     * @param dynamicValues
     * @param exception
     */
    public InvalidCacheException(String errorKey, String[] dynamicValues, Exception exception) {
        super(errorKey, dynamicValues, exception);
    }

}
