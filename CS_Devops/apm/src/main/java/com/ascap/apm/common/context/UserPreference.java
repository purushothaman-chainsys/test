package com.ascap.apm.common.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;

import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
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

    
    private int nofSrchRsltsPerPage;

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
    
    public boolean validate(HttpServletRequest request,MessageSource messageSource) {
        boolean flag =false;
        List<String> errors=new ArrayList<>();
        try{
            int searchResultsThreshold = Integer.parseInt(PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.MAX_SEARCHRESULTS_THRESHOLD));
            int recordsPerPageThreshold = Integer.parseInt(PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.MAX_NUMBER_OF_RESULTS_PER_PAGE));
            if(this.maxSearchResults <= 0   || this.maxSearchResults> searchResultsThreshold){                 
                errors.add(messageSource.getMessage("ps.common.title.maxSearchResults.exceedlimit", new Object[] {searchResultsThreshold}, Locale.getDefault()));
                flag =true;
            }
            
            if(this.nofSrchRsltsPerPage <= 0   || this.nofSrchRsltsPerPage > recordsPerPageThreshold){                  
                errors.add(messageSource.getMessage("ps.common.title.noOfSrchRsltsPerPage.exceedlimit", new Object[] {recordsPerPageThreshold}, Locale.getDefault()));
                flag =true;
            }
            request.setAttribute("systemerrorlist", errors);
        }catch(Exception e){       
            e.printStackTrace();
        }
        return flag;
    }
}
