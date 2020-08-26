package com.ascap.apm.common.exception.logon;

import com.ascap.apm.common.exception.PrepFunctionalException;

/**
 * @author Raju Ayanampudi
 * @version $Revision: 1.0 $ $Date: Jul 27 2004 12:22:36 $ This Exception is generated when the specified Agreement is
 *          not found
 */

public class LogonException extends PrepFunctionalException {

    private static final long serialVersionUID = -3665940098554966651L;

    public LogonException(String errorKey) {
        super(errorKey);
    }

    /**
     * @see com.ascap.apm.common.exception.BaseException#BaseException(String, String[])
     */
    public LogonException(String errorKey, String[] dynamicValues) {
        super(errorKey, dynamicValues);
    }
}
