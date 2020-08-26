package com.ascap.apm.common.context;

import java.util.ArrayList;
import java.util.List;

import com.ascap.apm.common.utils.DebugHelperUtils;
import com.ascap.apm.vob.BaseVOB;

/**
 * PREP application context object This contains run time information on user session data, application session data
 * 
 * @author Sudhar_Krishnamachar
 * @author Pratap Sanikommu
 * @version $Revision: 1.2 $ $Date: Jan 24 2006 12:39:10 $
 */
public class PREPContext extends BaseVOB {

    private static final long serialVersionUID = 7340925151171436995L;

    // ApplicationState object
    private ApplicationState applicationState;

    // UserSession State object
    private UserSessionState userSessionState;

    // UserProfile object
    private UserProfile userProfile;

    // List<Serializable> of Input VOBS
    private List<Object> inputValueObjects;

    // List<Serializable> of Output VOBS
    private List<Object> outputValueObjects;

    // List<Serializable> of Output errors
    private List<Object> outputErrorObjects;

    /**
     * Constructor
     */
    public PREPContext() {
        init();
    }

    /**
     * init method for initialization.
     */
    private void init() {
        inputValueObjects = new ArrayList<>();
        outputValueObjects = new ArrayList<>();
        outputErrorObjects = new ArrayList<>();
    }

    /**
     * String representation of this object REMEMBER TO UPDATE THIS METHOD EVERYTIME AN ATTRIBUTE IS ADDED
     */
    public String toString() {

        try {

            StringBuilder userProfileStringBuffer = new StringBuilder();

            userProfileStringBuffer.append("PREPContext : ");
            userProfileStringBuffer.append(applicationState.toString());
            userProfileStringBuffer.append(userSessionState.toString());
            userProfileStringBuffer.append(userProfile.toString());

            /** <!-- Start ascapdevel changes --> **/
            userProfileStringBuffer.append("<br>");
            userProfileStringBuffer
                .append(DebugHelperUtils.debugCollections("InputValueObjects: ", this.inputValueObjects));
            userProfileStringBuffer.append("<br>");
            userProfileStringBuffer
                .append(DebugHelperUtils.debugCollections("OutputValueObjects: ", this.outputValueObjects));
            /** <!-- Start ascapdevel changes --> **/

            return userProfileStringBuffer.toString();

        } catch (Exception e) {
            return "Error Unable to Construct PREPContext Object String";
        }

    }

    /**
     * Returns the applicationState.
     * 
     * @return ApplicationState
     */
    public ApplicationState getApplicationState() {
        return applicationState;
    }

    /**
     * Returns the userProfile.
     * 
     * @return UserProfile
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Returns the userSessionState.
     * 
     * @return UserSessionState
     */
    public UserSessionState getUserSessionState() {
        return userSessionState;
    }

    /**
     * Sets the applicationState.
     * 
     * @param applicationState The applicationState to set
     */
    public void setApplicationState(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    /**
     * Sets the userProfile.
     * 
     * @param userProfile The userProfile to set
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Sets the userSessionState.
     * 
     * @param userSessionState The userSessionState to set
     */
    public void setUserSessionState(UserSessionState userSessionState) {
        this.userSessionState = userSessionState;
    }

    /**
     * Returns the inputValueObjects.
     * 
     * @return Collection
     */
    public List<Object> getInputValueObjects() {
        return inputValueObjects;
    }

    /**
     * Returns the outputErrorObjects.
     * 
     * @return Collection
     */
    public List<Object> getOutputErrorObjects() {
        return outputErrorObjects;
    }

    /**
     * Returns the outputValueObjects.
     * 
     * @return Collection
     */
    public List<Object> getOutputValueObjects() {
        return outputValueObjects;
    }

    /**
     * Sets the inputValueObjects.
     * 
     * @param inputValueObjects The inputValueObjects to set
     */
    public void setInputValueObjects(List<Object> inputValueObjects) {
        this.inputValueObjects.addAll(inputValueObjects);
    }

    /**
     * Sets the outputErrorObjects.
     * 
     * @param outputErrorObjects The outputErrorObjects to set
     */
    public void setOutputErrorObjects(List<Object> outputErrorObjects) {
        this.outputErrorObjects.addAll(outputErrorObjects);
    }

    /**
     * Sets the outputValueObjects.
     * 
     * @param outputValueObjects The outputValueObjects to set
     */
    public void setOutputValueObjects(List<Object> outputValueObjects) {
        this.outputValueObjects.addAll(outputValueObjects);
    }

    /**
     * Adds the inputValueObject.
     * 
     * @param inputValueObject The inputValueObject to add
     */
    public void addInputValueObject(Object obj) {
        this.inputValueObjects.add(obj);
    }

    /**
     * Adds the outputErrorObject.
     * 
     * @param outputErrorObject The outputErrorObject to add
     */
    public void addOutputErrorObject(Object obj) {
        this.outputErrorObjects.add(obj);
    }

    /**
     * Adds the outputValueObject.
     * 
     * @param outputValueObject The outputValueObject to add
     */
    public void addOutputValueObject(Object obj) {
        this.outputValueObjects.add(obj);
    }

}
