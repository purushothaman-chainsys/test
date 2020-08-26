package com.ascap.apm.common.utils;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import com.ascap.apm.common.helpers.LogHelper;

/**
 * @author Pratap Sanikommu
 * @version $Revision: 1.0 $ $Date: Jul 15 2003 18:10:26 $ Property File for PREP Application. Loads the property file
 *          and is available for the rest of the application to use thru the access method.
 */
public class MessageProperties implements Serializable {

    private static final long serialVersionUID = -3073448345378271721L;

    private static MessageProperties messageProperties = null;

    private LogHelper log = new LogHelper(this.getClass().getName());

    private ResourceBundle resource = null;

    private String propFileName;

    /**
     * MessageProperties constructor to Set the propFileName and Load the properties.
     * 
     * @param propFileName1 The propFileName to set
     */
    private MessageProperties(String propFileName) {
        this.propFileName = propFileName;
        load();
    }

    private void load() {
        // load from the file
        if (propFileName != null && !propFileName.equalsIgnoreCase("")) {
            try {
                resource = ResourceBundle.getBundle(propFileName, new Locale("en", "US"));
                log.debug("The Resource file " + propFileName + " loaded");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * Sets the propFileName and Loads the properties.
     * 
     * @param propFileName The propFileName to set
     */
    @SuppressWarnings("unused")
    private void setPropFileName(String propFileName) {
        this.propFileName = propFileName;
    }

    /**
     * Returns the propFileName.
     * 
     * @return String
     */
    public String getPropFileName() {
        return propFileName;
    }

    /**
     * Returns the value for the keyValue property name This will be the only method on this object used by the entire
     * application The keyvalue is what is defined in the ErrorMessages properties file
     * 
     * @return String the value specified on the file
     */
    public String getProperty(String keyValue) {
        if (resource != null)
            return resource.getString(keyValue);

        return null; // invalid key or no properties found
    }

    /**
     * Returns the dynamic value for the keyValue property name by replacing each %d with corresponding element of
     * dynamicValues array This will be the only method on this object used by the entire application The keyvalue is
     * what is defined in the ErrorMessages properties file
     * 
     * @return String the value specified on the file
     */
    public String getProperty(String keyValue, String[] dynamicValues) {
        String keyMessage = null;
        if (resource != null)
            keyMessage = resource.getString(keyValue);
        if (keyMessage != null && dynamicValues != null && dynamicValues.length > 0) {
            keyMessage = replaceSubstring(keyMessage, "%d", dynamicValues);

        }

        return keyMessage; // invalid key or no properties found
    }

    private String replaceSubstring(String str, String pattern, String[] replace) {
        int slen = str.length();
        int plen = pattern.length();
        int rlen = replace.length;
        int s = 0;
        int e = 0;
        int i = 0;
        StringBuilder result = new StringBuilder(slen * 2);
        char[] chars = new char[slen];

        while ((e = str.indexOf(pattern, s)) >= 0) {
            str.getChars(s, e, chars, 0);
            if (i < rlen)
                result.append(chars, 0, e - s).append(replace[i]);
            else
                result.append(chars, 0, e - s).append("Unknown");
            i++;
            s = e + plen;
        }
        str.getChars(s, slen, chars, 0);
        result.append(chars, 0, slen - s);
        return result.toString().trim();
    }

    /**
     * Returns the existing instance for the given file name if available. Otherwise Returns the new instance for the
     * given file name
     * 
     * @return MessageProperties object
     */
    public static synchronized MessageProperties getInstance(String propFileName) {
        if (messageProperties != null) {
            if (messageProperties.resource == null || propFileName == null
                || !propFileName.equals(messageProperties.propFileName)) {
                messageProperties.propFileName = propFileName;
                messageProperties.load();
            }
            return messageProperties;
        } else {
            messageProperties = new MessageProperties(propFileName);
            return messageProperties;
        }
    }

}
