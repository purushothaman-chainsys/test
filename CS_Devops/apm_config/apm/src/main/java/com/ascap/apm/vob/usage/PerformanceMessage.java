package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

/**
 * Error/Warning/Information on a Performance (either programPerformance or Work Performance)
 * 
 * @author Jaya Shyam Narayana Lingamchetty
 * @version $Revision: 1.1 $ $Date: May 18 2009 14:45:42 $
 */
public class PerformanceMessage extends BaseVOB {

    private static final long serialVersionUID = 4942263253288864718L;

    private String messageCode = null;

    private String messageDescription = null;

    private String messageCategoryCode = null;

    private String messageCategoryDescription = null;

    private String performanceType = null;

    private String performanceId = null;

    private String performanceVersion = null;

    private String performanceHeaderId = null;

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder outStrBuff;
        outStrBuff = new StringBuilder();
        outStrBuff.append("com.ascap.apm.common.vob.usage.PerformanceMessage {").append("messageCode= '")
            .append(messageCode).append("', ").append("messageDescription= '").append(messageDescription).append("', ")
            .append("messageCategoryCode= '").append(messageCategoryCode).append("', ")
            .append("messageCategoryDescription= '").append(messageCategoryDescription).append("', ")
            .append("performanceType= '").append(performanceType).append("', ").append("performanceId= '")
            .append(performanceId).append("', ").append("performanceVersion= '").append(performanceVersion)
            .append("', ").append("performanceHeaderId= '").append(performanceHeaderId).append("', ").append("'}");
        return (outStrBuff.toString());
    }

    /**
     * Returns the messageCategoryCode.
     * 
     * @return String
     */
    public String getMessageCategoryCode() {
        return messageCategoryCode;
    }

    /**
     * Returns the messageCategoryDescription.
     * 
     * @return String
     */
    public String getMessageCategoryDescription() {
        return messageCategoryDescription;
    }

    /**
     * Returns the messageCode.
     * 
     * @return String
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * Returns the messageDescription.
     * 
     * @return String
     */
    public String getMessageDescription() {
        return messageDescription;
    }

    /**
     * Returns the performanceId.
     * 
     * @return String
     */
    public String getPerformanceId() {
        return performanceId;
    }

    /**
     * Returns the performanceType.
     * 
     * @return String
     */
    public String getPerformanceType() {
        return performanceType;
    }

    /**
     * Returns the performanceVersion.
     * 
     * @return String
     */
    public String getPerformanceVersion() {
        return performanceVersion;
    }

    /**
     * Sets the messageCategoryCode.
     * 
     * @param messageCategoryCode The messageCategoryCode to set
     */
    public void setMessageCategoryCode(String messageCategoryCode) {
        this.messageCategoryCode = messageCategoryCode;
    }

    /**
     * Sets the messageCategoryDescription.
     * 
     * @param messageCategoryDescription The messageCategoryDescription to set
     */
    public void setMessageCategoryDescription(String messageCategoryDescription) {
        this.messageCategoryDescription = messageCategoryDescription;
    }

    /**
     * Sets the messageCode.
     * 
     * @param messageCode The messageCode to set
     */
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * Sets the messageDescription.
     * 
     * @param messageDescription The messageDescription to set
     */
    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    /**
     * Sets the performanceId.
     * 
     * @param performanceId The performanceId to set
     */
    public void setPerformanceId(String performanceId) {
        this.performanceId = performanceId;
    }

    /**
     * Sets the performanceType.
     * 
     * @param performanceType The performanceType to set
     */
    public void setPerformanceType(String performanceType) {
        this.performanceType = performanceType;
    }

    /**
     * Sets the performanceVersion.
     * 
     * @param performanceVersion The performanceVersion to set
     */
    public void setPerformanceVersion(String performanceVersion) {
        this.performanceVersion = performanceVersion;
    }

    /**
     * @return Returns the performanceHeaderId.
     */
    public String getPerformanceHeaderId() {
        return performanceHeaderId;
    }

    /**
     * @param performanceHeaderId The performanceHeaderId to set.
     */
    public void setPerformanceHeaderId(String performanceHeaderId) {
        this.performanceHeaderId = performanceHeaderId;
    }
}
