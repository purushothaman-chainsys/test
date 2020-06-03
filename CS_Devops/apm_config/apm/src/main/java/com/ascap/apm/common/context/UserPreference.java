package com.ascap.apm.common.context;

import javax.validation.constraints.Min;

import com.ascap.apm.vob.BaseVOB;

/**
 * Used for holding User Preferences.
 * 
 * @author Jaya Shyam Narayana Lingamchetty
 * @internal Please add members if you require more.
 */
public class UserPreference extends BaseVOB {

    private static final long serialVersionUID = 528118969556931821L;

    private String defaultPage;

    
    @Min(value = 1, message= "{ps.common.title.pageSearchResults}")
    private int nofSrchRsltsPerPage;

    @Min(value = 1, message= "ps.common.title.maxSearchResults")
    private int maxSearchResults;

    private String themeTypeCode;

    private String randomThemeTypeCode;

    private String userId = null;

    private String emailReminder = null;

    private String sortOrder;

    private String backgroundThemeFlag;

    private String enableCookies;

    /**
     * Returns the defaultPage.
     * 
     * @return String
     */
    public String getDefaultPage() {
        return defaultPage;
    }

    /**
     * Returns the srchRsltsPerPage.
     * 
     * @return int
     */
    public int getNofSrchRsltsPerPage() {
        return nofSrchRsltsPerPage;
    }

    /**
     * Sets the defaultPage.
     * 
     * @param defaultPage The defaultPage to set
     */
    public void setDefaultPage(String defaultPage) {
        this.defaultPage = defaultPage;
    }

    /**
     * Sets the srchRsltsPerPage.
     * 
     * @param srchRsltsPerPage The srchRsltsPerPage to set
     */
    public void setNofSrchRsltsPerPage(int nofSrchRsltsPerPage) {
        this.nofSrchRsltsPerPage = nofSrchRsltsPerPage;
    }

    /**
     * @internal please update the toString() when you add new members
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder outStrBuff = new StringBuilder();
        outStrBuff.append("UserPreference{").append("defaultPage='").append(this.defaultPage)
            .append("', nofSrchRsltsPerPage='").append(this.nofSrchRsltsPerPage).append("', themeTypeCode='")
            .append(this.themeTypeCode).append("'}");
        return outStrBuff.toString();
    }

    /**
     * Returns the emailReminder.
     * 
     * @return String
     */
    public String getEmailReminder() {
        return emailReminder;
    }

    /**
     * Returns the sortOrder.
     * 
     * @return String
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * Returns the userId.
     * 
     * @return String
     */
    @Override
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the emailReminder.
     * 
     * @param emailReminder The emailReminder to set
     */
    public void setEmailReminder(String emailReminder) {
        this.emailReminder = emailReminder;
    }

    /**
     * Sets the sortOrder.
     * 
     * @param sortOrder The sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Sets the userId.
     * 
     * @param userId The userId to set
     */
    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return Returns the maxSearchResults.
     */
    public int getMaxSearchResults() {
        return maxSearchResults;
    }

    /**
     * @param maxSearchResults The maxSearchResults to set.
     */
    public void setMaxSearchResults(int maxSearchResults) {
        this.maxSearchResults = maxSearchResults;
    }

    public String getRandomThemeTypeCode() {
        return randomThemeTypeCode;
    }

    public void setRandomThemeTypeCode(String randomThemeTypeCode) {
        this.randomThemeTypeCode = randomThemeTypeCode;
    }

    public String getThemeTypeCode() {
        return themeTypeCode;
    }

    public void setThemeTypeCode(String themeTypeCode) {
        this.themeTypeCode = themeTypeCode;
    }

    public String getBackgroundThemeFlag() {
        return backgroundThemeFlag;
    }

    public void setBackgroundThemeFlag(String backgroundThemeFlag) {
        this.backgroundThemeFlag = backgroundThemeFlag;
    }

    public String getEnableCookies() {
        return enableCookies;
    }

    public void setEnableCookies(String enableCookies) {
        this.enableCookies = enableCookies;
    }
}
