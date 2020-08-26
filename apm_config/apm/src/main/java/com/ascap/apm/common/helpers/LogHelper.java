package com.ascap.apm.common.helpers;

//java imports
import java.io.*;

//log4j imports
import org.apache.log4j.*;

import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;

/**
 * LogHelper is Helper Class. The code bases should use this class and not log4j directly. This hides the underlying logging
 * implementation from the rest of the app. Adapted from webspehere design recommendations.
 *
 * @author Pratap Sanikommu
 * @version $Revision:   1.6  $ $Date:   Aug 25 2010 16:28:34  $
 */

public class LogHelper implements Serializable {

    private static final long serialVersionUID = -1576218334398487065L;

    private static boolean initialized = false;

	private static Logger logger = null;

    /**
     * This creates a new Logger instance for a any component
     *
     * @param component Class reference to the calling class
     */
    public LogHelper(String className) {
        super(); //to be consistent

        //Initialize
        if ( !initialized ) {
            init();
        }

        //register
        logger = Logger.getLogger(className);

    }
    
    public void reLoadLog(String className) {
        
        //re-initialize        
        initialized = false;
        
        init();
        
        //register
        logger = Logger.getLogger(className);

    }


    private static synchronized void init() {
        if ( !initialized ) {
            //initialize
            //hard coding the path for now, needs to be in classpath
            //PropertyConfigurator.configureAndWatch("C:/Sudhar/Projects/Prep/prep_log4j.properties", 60000);
            String propConfig = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.LOG4J_PROP_CONFIGURATOR);
            //PropertyConfigurator.configure("C:/prep/PrepEAR/prep_log4j.properties");
            PropertyConfigurator.configure(propConfig);
            initialized = true;
        }
    }

    public void info(Object o) {
        logger.info(o);
    }

    public void info(Object o, Exception e) {
        logger.info(o, e);
    }

    public void debug(Object o) {
        if ( isDebugEnabled() )
            logger.debug(o);
    }

    public void debug(Object o, Exception e) {
        if ( isDebugEnabled() )
            logger.debug(o, e);
    }

    public void error(Object o) {
        logger.error(o);
    }

    public void error(Object o, Exception e) {
        logger.error(o, e);
    }

    public void warn(Object o) {
        logger.warn(o);
    }

    public void warn(Object o, Exception e) {
        logger.warn(o, e);
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public void putMDC(String key,Object obj) {
    	NDC.push("NDC:"+ key + obj);
        MDC.put(key,obj);
    }
    public void removeMDC(String key) {
    	NDC.pop();
    	NDC.remove();
        MDC.remove(key);
    }
}