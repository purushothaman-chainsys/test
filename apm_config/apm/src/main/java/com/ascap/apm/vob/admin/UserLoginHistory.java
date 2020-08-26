package com.ascap.apm.vob.admin;

import com.ascap.apm.vob.BaseVOB;

/**
 * Holds the Login History ID. enables more information to be captured and tracked about a Login Session.
 * 
 * @author Jaya Shyam Narayana Lingamchetty
 * @version $Revision: 1.3 $ $Date: Jun 03 2011 11:45:00 $
 */
public class UserLoginHistory extends BaseVOB {

    private static final long serialVersionUID = 6030883718470690930L;

    private String userLoginHistoryId;

    private String userName;

    private String attemptCode;

    private String sessionKey;

    private String sessionStatusCode;

    private String accessTypeCode;

    private String userAgent;

    private String historyTypeCode;

    public String toString() {
        StringBuilder outStrBuff;
        outStrBuff = new StringBuilder();
        outStrBuff.append("com.ascap.apm.common.vob.admin.UserLoginHistory {").append("userAgent= '").append(userAgent)
            .append("', ").append("accessTypeCode= '").append(accessTypeCode).append("', ")
            .append("sessionStatusCode= '").append(sessionStatusCode).append("', ")
            // .append("sessionKey= '").append(sessionKey).append("', ")
            .append("attemptCode= '").append(attemptCode).append("', ").append("userName= '").append(userName)
            .append("', ").append("historyTypeCode= '").append(historyTypeCode).append("', ")
            .append("userLoginHistoryId= '").append(userLoginHistoryId).append("'}");
        return (outStrBuff.toString());
    }

    /**
     * Returns the attemptCode.
     * 
     * @return String
     */
    public String getAttemptCode() {
        return attemptCode;
    }

    /**
     * Returns the userName.
     * 
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the attemptCode.
     * 
     * @param attemptCode The attemptCode to set
     */
    public void setAttemptCode(String attemptCode) {
        this.attemptCode = attemptCode;
    }

    /**
     * Sets the userName.
     * 
     * @param userName The userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return Returns the accessTypeCode.
     */
    public String getAccessTypeCode() {
        return this.accessTypeCode;
    }

    /**
     * @param accessTypeCode The accessTypeCode to set.
     */
    public void setAccessTypeCode(String accessTypeCode) {
        this.accessTypeCode = accessTypeCode;
    }

    /**
     * @return Returns the sessionKey.
     */
    public String getSessionKey() {
        return this.sessionKey;
    }

    /**
     * @param sessionKey The sessionKey to set.
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * @return Returns the sessionStatusCode.
     */
    public String getSessionStatusCode() {
        return this.sessionStatusCode;
    }

    /**
     * @param sessionStatusCode The sessionStatusCode to set.
     */
    public void setSessionStatusCode(String sessionStatusCode) {
        this.sessionStatusCode = sessionStatusCode;
    }

    /**
     * @return Returns the userAgent.
     */
    public String getUserAgent() {
        return this.userAgent;
    }

    /**
     * @param userAgent The userAgent to set.
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * @return Returns the userLoginHistoryId.
     */
    public String getUserLoginHistoryId() {
        return this.userLoginHistoryId;
    }

    /**
     * @param userLoginHistoryId The userLoginHistoryId to set.
     */
    public void setUserLoginHistoryId(String userLoginHistoryId) {
        this.userLoginHistoryId = userLoginHistoryId;
    }

    /**
     * @return Returns the historyTypeCode.
     */
    public String getHistoryTypeCode() {
        return historyTypeCode;
    }

    /**
     * @param historyTypeCode The historyTypeCode to set.
     */
    public void setHistoryTypeCode(String historyTypeCode) {
        this.historyTypeCode = historyTypeCode;
    }
}
