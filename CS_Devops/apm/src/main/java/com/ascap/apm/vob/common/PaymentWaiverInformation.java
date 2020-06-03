package com.ascap.apm.vob.common;

import com.ascap.apm.vob.BaseVOB;

/**
 * @author Shyam_Narayana
 */
public class PaymentWaiverInformation extends BaseVOB {

    private static final long serialVersionUID = 6766097680580993867L;

    private String paymentWaiverInformationId = null; // PTY_CHG_SRV_FEE_WAIVER_ID

    private String ascapUserId = null; // ASCAP_USR_ID

    private String ascapUserFullName = null;

    private String ascapUserEmailAddress = null;

    private String waiverCode = null; // WAIVER_CDE

    private String comments = null; // COM

    private String waiverSetupDate = null;

    private String waiverDeleteFlag = null;

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder outStrBuff;
        outStrBuff = new StringBuilder();
        outStrBuff.append("com.ascap.apm.common.vob.common.PaymentWaiverInfomation {")
            .append("paymentWaiverInformationId= '").append(paymentWaiverInformationId).append("', ")
            .append("ascapUserId= '").append(ascapUserId).append("', ").append("ascapUserFullName= '")
            .append(ascapUserFullName).append("', ").append("waiverCode= '").append(waiverCode).append("', ")
            .append("waiverSetupDate= '").append(waiverSetupDate).append("', ").append("comments= '").append(comments)
            .append("'} ");
        return (outStrBuff.toString());
    }

    /**
     * Returns the ascapUserFullName.
     * 
     * @return String
     */
    public String getAscapUserFullName() {
        return ascapUserFullName;
    }

    /**
     * Returns the ascapUserId.
     * 
     * @return String
     */
    public String getAscapUserId() {
        return ascapUserId;
    }

    /**
     * Returns the comments.
     * 
     * @return String
     */
    public String getComments() {
        return comments;
    }

    /**
     * Returns the paymentWaiverInformationId.
     * 
     * @return String
     */
    public String getPaymentWaiverInformationId() {
        return paymentWaiverInformationId;
    }

    /**
     * Returns the waiverCode.
     * 
     * @return String
     */
    public String getWaiverCode() {
        return waiverCode;
    }

    /**
     * Sets the ascapUserFullName.
     * 
     * @param ascapUserFullName The ascapUserFullName to set
     */
    public void setAscapUserFullName(String ascapUserFullName) {
        this.ascapUserFullName = ascapUserFullName;
    }

    /**
     * Sets the ascapUserId.
     * 
     * @param ascapUserId The ascapUserId to set
     */
    public void setAscapUserId(String ascapUserId) {
        this.ascapUserId = ascapUserId;
    }

    /**
     * Sets the comments.
     * 
     * @param comments The comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Sets the paymentWaiverInformationId.
     * 
     * @param paymentWaiverInformationId The paymentWaiverInformationId to set
     */
    public void setPaymentWaiverInformationId(String paymentWaiverInformationId) {
        this.paymentWaiverInformationId = paymentWaiverInformationId;
    }

    /**
     * Sets the waiverCode.
     * 
     * @param waiverCode The waiverCode to set
     */
    public void setWaiverCode(String waiverCode) {
        this.waiverCode = waiverCode;
    }

    /**
     * Returns the waiverSetupDate.
     * 
     * @return String
     */
    public String getWaiverSetupDate() {
        return waiverSetupDate;
    }

    /**
     * Sets the waiverSetupDate.
     * 
     * @param waiverSetupDate The waiverSetupDate to set
     */
    public void setWaiverSetupDate(String waiverSetupDate) {
        this.waiverSetupDate = waiverSetupDate;
    }

    /**
     * Returns the waiverDeleteFlag.
     * 
     * @return String
     */
    public String getWaiverDeleteFlag() {
        return waiverDeleteFlag;
    }

    /**
     * Sets the waiverDeleteFlag.
     * 
     * @param waiverDeleteFlag The waiverDeleteFlag to set
     */
    public void setWaiverDeleteFlag(String waiverDeleteFlag) {
        this.waiverDeleteFlag = waiverDeleteFlag;
    }

    /**
     * Returns the ascapUserEmailAddress.
     * 
     * @return String
     */
    public String getAscapUserEmailAddress() {
        return ascapUserEmailAddress;
    }

    /**
     * Sets the ascapUserEmailAddress.
     * 
     * @param ascapUserEmailAddress The ascapUserEmailAddress to set
     */
    public void setAscapUserEmailAddress(String ascapUserEmailAddress) {
        this.ascapUserEmailAddress = ascapUserEmailAddress;
    }

}
