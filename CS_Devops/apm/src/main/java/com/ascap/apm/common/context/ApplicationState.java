package com.ascap.apm.common.context;

import com.ascap.apm.vob.BaseVOB;

/**
 * @author Sudhar_Krishnamachar To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class ApplicationState extends BaseVOB {

    private static final long serialVersionUID = 1976053374821584779L;

    // http session key to be used by httpsession.setAttribute methods
    public static final String HTTP_SESSION_KEY = "PREP.ApplicationState";

    // add a class for each module's user session state

    /**
     * String representation of this object REMEMBER TO UPDATE THIS METHOD EVERYTIME AN ATTRIBUTE IS ADDED
     */
    public String toString() {

        try {

            StringBuilder userProfileStringBuffer = new StringBuilder();

            userProfileStringBuffer.append("");

            return userProfileStringBuffer.toString();

        } catch (Exception e) {
            return "Error Unable to Construct ApplicationState Object String";
        }

    }

}
