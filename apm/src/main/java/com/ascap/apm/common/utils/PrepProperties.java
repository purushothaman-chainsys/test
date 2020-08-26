package com.ascap.apm.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Properties;

import com.ascap.apm.common.helpers.LogHelper;

/**
 * @author Sudhar Krishnamachary Property File for PREP Application. Loads the property file and is available for the
 *         rest of the application to use thru the access method. This is loaded from the struts startup plug in.
 *         Refactor considering that EJB layer could be deployed standalone and that this property file may be required.
 */
public class PrepProperties implements Serializable {

    private static final long serialVersionUID = -3967588603569022244L;

    private static LogHelper log = new LogHelper("PrepProperties");

    private static Properties properties = new Properties();

    private static String propFileName;

    /**
     * Method load. Added to be used from unit testing classes that may not have startup
     * 
     * @param propertyFileName
     * @throws FileNotFoundException
     */
    public static void load(String propertyFileName) throws FileNotFoundException {
        log.debug("loading from " + propertyFileName);
        setPropFileName(propertyFileName);
        load();
    }

    public static void load() throws FileNotFoundException {
        // load from the file
        if (propFileName != null && !propFileName.equalsIgnoreCase("")) {
            try {
                properties.load(new FileInputStream(propFileName));
                log.debug("loaded properties " + properties.toString());
            } catch (IOException e) {
                // to do
            }
        }
    }

    /**
     * Sets the propFileName.
     * 
     * @param propFileName The propFileName to set
     */
    public static void setPropFileName(String fileName) {
        propFileName = fileName;
    }

    /**
     * Returns the prepPopertyFileNameWithPath.
     * 
     * @return String
     */
    public static String getPropFileName() {
        return propFileName;
    }

    /**
     * Returns the value for the keyValue property name This will be the only method on this object used by the entire
     * application The keyvalue is what is defined in the prep properties file
     * 
     * @return String the value specified on the file
     */
    public static String getProperty(String keyValue) {
        if (properties != null)
            return properties.getProperty(keyValue);

        return null; // invalud key or no properties found
    }

    /**
     * Prints the list of all application specific properties to the print stream supplied
     * 
     * @return none
     */
    public static void printProperties(PrintStream printStream) {
        properties.list(printStream);
    }

    /**
     * In case the Property is loaded and is to be set
     * 
     * @param Properties prop
     * @return void
     */
    public static void setPrepProperty(Properties prop) {
        properties = prop;
    }

}
