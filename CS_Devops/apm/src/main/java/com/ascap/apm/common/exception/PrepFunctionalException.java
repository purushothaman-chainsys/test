package com.ascap.apm.common.exception;

/**
 * @author Pratap Sanikommu
 * @version $Revision: 1.0 $ $Date: Jul 21 2003 13:51:12 $ Prep Functional Exception for Base Functional Exceptions
 */
public class PrepFunctionalException extends BaseException {

    private static final long serialVersionUID = 4556970698652531493L;

    public PrepFunctionalException(String errorKey) {
        super(errorKey);
    }

    public PrepFunctionalException(String errorKey, String[] dynamicValues) {
        super(errorKey, dynamicValues);
    }
}
