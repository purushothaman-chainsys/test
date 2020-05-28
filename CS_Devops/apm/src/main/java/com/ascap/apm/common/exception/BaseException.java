package com.ascap.apm.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.ascap.apm.common.helpers.ExceptionIdHelper;
import com.ascap.apm.common.utils.MessageProperties;

/**
 * @author Pratap Sanikommu
 * @version $Revision: 1.1 $ $Date: Jan 24 2006 12:42:36 $ Base Exception class to be extended by all Exception classes
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = 6641908405592634437L;

    protected boolean isLogged = false;

    protected final String exceptionId;

    protected String errorKey;

    protected final Exception exception;

    private final String inputContextMessage;

    private final String outputContextMessage;

    private final String[] dynaValues;

    public BaseException(final String errorKey) {
        super(getErrorMessage(errorKey));
        this.errorKey = errorKey;
        isLogged = false;
        exceptionId = ExceptionIdHelper.getExceptionId();
        this.exception = null;
        this.inputContextMessage = null;
        this.outputContextMessage = null;
        this.dynaValues = null;
    }

    public BaseException(String errorKey, String[] dynamicValues) {
        super(getErrorMessage(errorKey, dynamicValues));
        this.errorKey = errorKey;
        isLogged = false;
        exceptionId = ExceptionIdHelper.getExceptionId();
        dynaValues = dynamicValues;
        this.exception = null;
        this.inputContextMessage = null;
        this.outputContextMessage = null;
    }

    public BaseException(final Exception exception) {
        super((exception == null ? "Unknown" : exception.toString()));
        exception.printStackTrace();
        this.exception = exception;
        isLogged = false;
        exceptionId = ExceptionIdHelper.getExceptionId();
        this.errorKey = null;
        this.inputContextMessage = null;
        this.outputContextMessage = null;
        this.dynaValues = null;
    }

    public BaseException(String errorKey, final Exception exception) {
        super(getErrorMessage(errorKey) + (char) 13 + (char) 10 + "Exception Root Cause : "
            + (exception == null ? "Unkown" : exception.toString()));
        exception.printStackTrace();
        this.exception = exception;
        this.errorKey = errorKey;
        isLogged = false;
        exceptionId = ExceptionIdHelper.getExceptionId();
        this.inputContextMessage = null;
        this.outputContextMessage = null;
        this.dynaValues = null;
    }

    public BaseException(String errorKey, String[] dynamicValues, final Exception exception) {
        super(getErrorMessage(errorKey, dynamicValues) + (char) 13 + (char) 10 + "Exception Root Cause : "
            + (exception == null ? "Unkown" : exception.toString()));
        exception.printStackTrace();
        this.exception = exception;
        this.errorKey = errorKey;
        isLogged = false;
        exceptionId = ExceptionIdHelper.getExceptionId();
        this.inputContextMessage = null;
        this.outputContextMessage = null;
        this.dynaValues = null;
    }

    /**
     * Method getErrorMessage to get assigned message for a key from property file.
     * 
     * @param errorKey
     * @return errorMessage
     */
    private static String getErrorMessage(String errorKey) {
        String errorMessage = errorKey;
        MessageProperties messageProps = MessageProperties.getInstance("ErrorMessages");
        String errorMessageTmp = messageProps.getProperty(errorKey);
        if (errorMessageTmp != null)
            errorMessage = errorMessageTmp;
        return errorMessage;
    }

    /**
     * Method getErrorMessage to get assigned message for a key from property file.
     * 
     * @param errorKey
     * @param dynamicValues[]
     * @return errorMessage
     */
    private static String getErrorMessage(String errorKey, String[] dynamicValues) {
        String errorMessage = errorKey;
        MessageProperties messageProps = MessageProperties.getInstance("ErrorMessages");
        String errorMessageTmp = messageProps.getProperty(errorKey, dynamicValues);
        if (errorMessageTmp != null)
            errorMessage = errorMessageTmp;
        return errorMessage;
    }

    public void setLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public String getExceptionId() {
        return exceptionId;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void printFullStackTrace() {
        super.printStackTrace();

        Exception curexc = this.exception;

        while (curexc != null) {
            curexc.printStackTrace();

            if (curexc instanceof BaseException) {
                curexc = ((BaseException) curexc).exception;
            } else {
                curexc = null;
            }

        }
        this.printStackTrace();
    }

    public void printFullStackTrace(PrintStream s) {
        super.printStackTrace(s);
        s.flush();
        if (this.exception != null) {
            this.exception.printStackTrace(s);
        }
        this.printStackTrace(s);
    }

    public void printFullStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        s.flush();
        if (this.exception != null) {
            this.exception.printStackTrace(s);
        }
        this.printStackTrace(s);
    }

    /**
     * Returns the inputContextMessage.
     * 
     * @return String
     */
    public String getInputContextMessage() {
        return inputContextMessage;
    }

    /**
     * Returns the outputContextMessage.
     * 
     * @return String
     */
    public String getOutputContextMessage() {
        return outputContextMessage;
    }

    public String[] getDynaValues() {
        return dynaValues;
    }

    
}
