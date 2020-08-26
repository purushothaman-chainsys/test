package com.ascap.apm.common.exception.database.lookup;

import com.ascap.apm.common.exception.PrepSystemException;

/**
 * @author Sudhar_Krishnamachar To change this generated comment edit the template variable "type comment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class LookupTableLoadException extends PrepSystemException {

    private static final long serialVersionUID = -3736848890757945293L;

    /**
     * Constructor for LookupTableLoadException.
     * 
     * @param errorKey
     */
    public LookupTableLoadException(String errorKey) {
        super(errorKey);
    }

    /**
     * Constructor for LookupTableLoadException.
     * 
     * @param errorKey
     * @param dynamicValues
     */
    public LookupTableLoadException(String errorKey, String[] dynamicValues) {
        super(errorKey, dynamicValues);
    }

    /**
     * Constructor for LookupTableLoadException.
     * 
     * @param errorKey
     * @param dynamicValues
     * @param exception
     */
    public LookupTableLoadException(String errorKey, String[] dynamicValues, Exception exception) {
        super(errorKey, dynamicValues, exception);
    }

}
