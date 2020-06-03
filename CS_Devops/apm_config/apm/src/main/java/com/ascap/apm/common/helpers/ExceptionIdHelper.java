package com.ascap.apm.common.helpers;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ExceptionIdHelper is Helper Class. The code bases should use this class to generate exception ID. Adapted from
 * webspehere design recommendations.
 *
 * @author Pratap Sanikommu
 * @version $Revision: 1.0 $ $Date: Jul 15 2003 18:10:14 $
 */

public class ExceptionIdHelper implements Serializable {

    private static final long serialVersionUID = 60800261054990441L;

    public static synchronized String getExceptionId() {
        String exceptionId = null;
        try {
            exceptionId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ue) {
            exceptionId = "UnknownHost";
        }

        exceptionId = exceptionId + System.currentTimeMillis();

        return exceptionId;
    }
}
