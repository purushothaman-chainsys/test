package com.ascap.apm.common.exception;

import java.sql.SQLException;

import com.ascap.apm.common.utils.constants.PrepConstants;

/**
 * @author Pratap Sanikommu
 * @version $Revision: 1.1 $ $Date: Aug 13 2009 18:17:24 $ Prep System Exception for all System Exceptions
 */
public class PrepSystemException extends BaseException {

    private static final long serialVersionUID = 430376010796170253L;

    public PrepSystemException(String errorKey) {
        super(errorKey);
    }

    public PrepSystemException(String errorKey, String[] dynamicValues) {
        super(errorKey, dynamicValues);
    }

    public PrepSystemException(Exception exception) {
        super(exception);
    }

    public PrepSystemException(String errorKey, Exception exception) {
        super(errorKey, exception);
        if (exception instanceof SQLException
            && PrepConstants.ORACLE_TIME_OUT_ERROR_CODE == ((SQLException) exception).getErrorCode()) {
            super.errorKey = PrepConstants.TIME_OUT_GENERIC_MESSAGE_KEY;
        }
    }

    public PrepSystemException(String errorKey, String[] dynamicValues, Exception exception) {
        super(errorKey, dynamicValues, exception);
    }
}
