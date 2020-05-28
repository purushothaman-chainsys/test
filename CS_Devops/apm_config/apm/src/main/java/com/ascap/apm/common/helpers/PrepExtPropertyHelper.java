package com.ascap.apm.common.helpers;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.ascap.apm.common.utils.constants.PrepConstants;

/**
 * @author Naveen_Ramappa To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class PrepExtPropertyHelper {

    private static Properties prepProp = null;

    private static final String PROPERTY_FILE = "prepconstants.properties";

    static {
        prepProp = loadProperties(PROPERTY_FILE);
    }

    private PrepExtPropertyHelper() {
        
    }

    private static Properties loadProperties(String name) {
        if (name == null)
            throw new IllegalArgumentException("null input: property file name");
        Properties result = null;
        try {
            result = PropertiesLoaderUtils.loadProperties(new FileSystemResource(PrepConstants.CONFIG_PATH+name));
        } catch (IOException e) {
            result = null;
        } 

        if (result == null) {
            throw new IllegalArgumentException("could not load [" + name + "]" + " as classloader resource");
        }
        return result;
    }

    public URL getResourcePath(String resName) {
        return Thread.currentThread().getContextClassLoader().getResource(resName);
    }

    public String getPropertyValue(String name) {
        if (name != null) {
            return prepProp.getProperty(name.trim());
        }
        return name;
    }

    public static PrepExtPropertyHelper getInstance() {
        return new PrepExtPropertyHelper();
    }
}
