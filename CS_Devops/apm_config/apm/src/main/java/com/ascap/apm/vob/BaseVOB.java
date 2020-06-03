package com.ascap.apm.vob;

import java.io.Serializable;

/**
 * @author Sudhar_Krishnamachar To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public abstract class BaseVOB implements Serializable {

    private static final long serialVersionUID = 5994448939314105825L;

    /**
     * All Value Objects/beans MUST implement this method for easy debugging and also for easy intrepreation of the
     * object return a valid string constructed out of all the meaningful attributes in the VOB Check out the
     * UserProfile object for a sample
     */
    public abstract String toString();

    private String userId;

    private boolean isModelling;

    private String applicationLoggedFrom;

    private String deviceType;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Returns the userId.
     * 
     * @return String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the userId.
     * 
     * @param userId The userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the isModelling.
     * 
     * @return boolean
     */
    public boolean getIsModelling() {
        return isModelling;
    }

    /**
     * Sets the isModelling.
     * 
     * @param isModelling The isModelling to set
     */
    public void setIsModelling(boolean isModelling) {
        this.isModelling = isModelling;
    }

    /**
     * @return Returns the applicationLoggedFrom.
     */
    public String getApplicationLoggedFrom() {
        return applicationLoggedFrom;
    }

    /**
     * @param applicationLoggedFrom The applicationLoggedFrom to set.
     */
    public void setApplicationLoggedFrom(String applicationLoggedFrom) {
        this.applicationLoggedFrom = applicationLoggedFrom;
    }
}
