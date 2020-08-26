package com.ascap.apm.common.exception.cache.httpsession;

import com.ascap.apm.common.exception.PrepFunctionalException;

/**
 * @author Sudhar_Krishnamachar To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class InvalidSessionObjectException extends PrepFunctionalException {

    private static final long serialVersionUID = -4147317568078788136L;

    /**
     * Constructor for InvalidSessionObjectException.
     * 
     * @param errorKey
     */
    public InvalidSessionObjectException(String errorKey) {
        super(errorKey);
    }

    /**
     * Constructor for InvalidSessionObjectException.
     * 
     * @param errorKey
     * @param dynamicValues
     */
    public InvalidSessionObjectException(String errorKey, String[] dynamicValues) {
        super(errorKey, dynamicValues);
    }

}
