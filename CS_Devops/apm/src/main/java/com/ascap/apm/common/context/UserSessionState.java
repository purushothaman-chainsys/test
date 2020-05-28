package com.ascap.apm.common.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ascap.apm.common.utils.cache.PrepKeyValueObject;
import com.ascap.apm.common.utils.cache.lookup.LookupTableCache;
import com.ascap.apm.common.utils.cache.lookup.LookupTables;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.vob.BaseInfoBarVOB;
import com.ascap.apm.vob.BaseMultiVOB;
import com.ascap.apm.vob.BaseSearchVOB;
import com.ascap.apm.vob.BaseVOB;
import com.ascap.apm.vob.admin.PrepConfigParamList;

/**
 * @author Sudhar_Krishnamachar
 * @version $Revision: 1.0
 * @author Pratap Sanikommu
 * @version $Revision: 1.34 $ $Date: Sep 30 2010 13:34:06 $ UserSessionState - Session state object to be used through
 *          out the application
 */
public class UserSessionState extends BaseVOB {

    private static final long serialVersionUID = 1632148982014123616L;

    // http session key to be used by httpsession.setAttribute methods
    public static final String HTTP_SESSION_KEY = "PREP.UserSessionState";

    private UserPreference userPreference = null;

    private BaseInfoBarVOB infoBar = null;

    private BaseSearchVOB search = null;

    private BaseSearchVOB adjustmentsSearch = null;

    private BaseSearchVOB headerSearchCriteria = null;

    private BaseMultiVOB multiPage = null;

    private String id = null;

    private byte moduleName = -1;

    private boolean isModelling = false;

    private boolean isDeceased = false;

    private String membershipLevelCode = null;

    private String reportID = null;

    private String reportName = null;

    private PrepConfigParamList prepConfigParamList = null;

    private String deletedId = null;

    private String workMatchIndicator = null;

    // Does an Audit ID have to be generated for the next change that a user
    // makes
    // to a Mandate, Agreement, or Work. Defaults to true.
    private boolean auditIdGenReq = true;

    private String membershipStatusCode = null;

    private boolean triggerUpdateRequired = false;

    private BaseVOB paymentInfo = null;

    // the below fields are context party details
    // (Main Party in case of super parents chooses a child, the child info will be
    // in these fields)
    private String partyId = null;

    private String partyName = null;

    private String workId = null;

    private String workTitle = null;

    private String applicationLogged = "";

    private String partyTypeCode = null;

    private String partyRoleTypeCode = null;

    private String childLoggedPartyId = null;

    private String childLoggedPartyName = null;

    private String childLoggedPartyTypeCode = null;

    private String childLoggedUserName = null;

    private String applyToAllWorks = null;

    private String isCueSheetsEnabled = "Y";

    private boolean loggedAsSuperParent = false;

    private String superParentPartyId = null;

    private String pdSessionId = null;

    private boolean totalWorksBelowThreshold = false;

    private String headerIdCurrent = null;

    private String headerIdPrev = null;

    private String headerIdNext = null;

    private String headerIdCurrentRowNum = null;

    public String getPdSessionId() {
        return pdSessionId;
    }

    public void setPdSessionId(String pdSessionId) {
        this.pdSessionId = pdSessionId;
    }

    /**
     * String representation of this object REMEMBER TO UPDATE THIS METHOD EVERYTIME AN ATTRIBUTE IS ADDED
     */
    public String toString() {
        try {

            final String APPEND_BREAK = "', <br>";

            StringBuilder outStrBuff = new StringBuilder();
            outStrBuff.append("com.ascap.apm.common.context.UserSessionState {").append("\npartyId= '").append(partyId)
                .append(APPEND_BREAK).append("\npartyTypeCode= '").append(partyTypeCode).append(APPEND_BREAK)
                .append("\npartyName= '").append(partyName).append(APPEND_BREAK).append("\nloggedAsSuperParent= '")
                .append(loggedAsSuperParent).append(APPEND_BREAK).append("\nchildLoggedUserName= '")
                .append(childLoggedUserName).append(APPEND_BREAK).append("\nchildLoggedPartyTypeCode= '")
                .append(childLoggedPartyTypeCode).append(APPEND_BREAK).append("\nchildLoggedPartyName= '")
                .append(childLoggedPartyName).append(APPEND_BREAK).append("\nchildLoggedPartyId= '")
                .append(childLoggedPartyId).append(APPEND_BREAK).append("\napplicationLogged= '")
                .append(applicationLogged).append(APPEND_BREAK).append("\npaymentInfo= '").append(paymentInfo)
                .append(APPEND_BREAK).append("\ntriggerUpdateRequired= '").append(triggerUpdateRequired)
                .append(APPEND_BREAK).append("\nmembershipStatusCode= '").append(membershipStatusCode)
                .append(APPEND_BREAK).append("\nauditIdGenReq= '").append(auditIdGenReq).append(APPEND_BREAK)
                .append("\nworkMatchIndicator= '").append(workMatchIndicator).append(APPEND_BREAK)
                .append("\ndeletedId= '").append(deletedId).append(APPEND_BREAK).append("\nprepConfigParamList= '")
                .append(prepConfigParamList).append(APPEND_BREAK).append("\nreportName= '").append(reportName)
                .append(APPEND_BREAK).append("\nreportID= '").append(reportID).append(APPEND_BREAK)
                .append("\nmembershipLevelCode= '").append(membershipLevelCode).append(APPEND_BREAK)
                .append("\nisDeceased= '").append(isDeceased).append(APPEND_BREAK).append("\nisModelling= '")
                .append(isModelling).append(APPEND_BREAK).append("\nmoduleName= '").append(moduleName)
                .append(APPEND_BREAK).append("\nid= '").append(id).append(APPEND_BREAK).append("\nmultiPage= '")
                .append(multiPage).append(APPEND_BREAK).append("\nadjustmentsSearch= '").append(adjustmentsSearch)
                .append(APPEND_BREAK).append("\nsearch= '").append(search).append(APPEND_BREAK).append("\ninfoBar= '")
                .append(infoBar).append(APPEND_BREAK).append("\nuserPreference= '").append(userPreference)
                .append(APPEND_BREAK).append("\nisCueSheetsEnabled= '").append(isCueSheetsEnabled).append(APPEND_BREAK)
                .append("\nHTTP_SESSION_KEY= '").append(HTTP_SESSION_KEY).append(APPEND_BREAK).append("'}");

            return (outStrBuff.toString());

        } catch (Exception e) {
            return "Error Unable to Construct UserSessionState Object String";
        }
    }

    /**
     * @return Returns the loggedAsSuperParent.
     */
    public boolean isLoggedAsSuperParent() {
        return loggedAsSuperParent;
    }

    /**
     * @param loggedAsSuperParent The loggedAsSuperParent to set.
     */
    public void setLoggedAsSuperParent(boolean loggedAsSuperParent) {
        this.loggedAsSuperParent = loggedAsSuperParent;
    }

    /**
     * Returns the id.
     * 
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the infoBar.
     * 
     * @return BaseInfoBarVOB
     */
    public BaseInfoBarVOB getInfoBar() {
        return infoBar;
    }

    /**
     * Returns the multiPage.
     * 
     * @return BaseMultiVOB
     */
    public BaseMultiVOB getMultiPage() {
        return multiPage;
    }

    /**
     * Returns the search.
     * 
     * @return BaseSearchVOB
     */
    public BaseSearchVOB getSearch() {
        return search;
    }

    /**
     * Returns the userPreference.
     * 
     * @return UserPreference
     */
    public UserPreference getUserPreference() {
        return userPreference;
    }

    /**
     * Sets the id.
     * 
     * @param id The id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the infoBar.
     * 
     * @param infoBar The infoBar to set
     */
    public void setInfoBar(BaseInfoBarVOB infoBar) {
        this.infoBar = infoBar;
    }

    /**
     * Sets the multiPage.
     * 
     * @param multiPage The multiPage to set
     */
    public void setMultiPage(BaseMultiVOB multiPage) {
        this.multiPage = multiPage;
    }

    /**
     * Sets the search.
     * 
     * @param search The search to set
     */
    public void setSearch(BaseSearchVOB search) {
        this.search = search;
    }

    /**
     * Sets the userPreference.
     * 
     * @param userPreference The userPreference to set
     */
    public void setUserPreference(UserPreference userPreference) {
        // code to select random themes.
        int randomThemeIndex;
        LookupTableCache xLkpCache;
        List<Serializable> availableThemes = new ArrayList<>();
        try {
            if (userPreference == null || userPreference.getThemeTypeCode() == null
                || userPreference.getThemeTypeCode().trim().equals("")) {
                userPreference.setThemeTypeCode(PrepConstants.THEME_DEFAULT);
            } else if (PrepConstants.THEME_RANDOM.equals(userPreference.getThemeTypeCode())) {
                xLkpCache = LookupTables.getLookupTable("ThemePaths");
                availableThemes = xLkpCache.getOriginalDBOrderedItemsWithKeys();
                while (true) {
                    randomThemeIndex = (new Random().nextInt() * 1000) % availableThemes.size();
                    userPreference
                        .setRandomThemeTypeCode(((PrepKeyValueObject) availableThemes.get(randomThemeIndex)).getKey());
                    // to make sure the THEME_RANDOM is not selected
                    if (!PrepConstants.THEME_RANDOM.equals(userPreference.getRandomThemeTypeCode())) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            userPreference.setThemeTypeCode(PrepConstants.THEME_DEFAULT);
        }

        this.userPreference = userPreference;
    }

    /**
     * Returns the partyTypeCode.
     * 
     * @return String
     */
    public String getPartyTypeCode() {
        return partyTypeCode;
    }

    /**
     * Sets the partyTypeCode.
     * 
     * @param partyTypeCode The partyTypeCode to set
     */
    public void setPartyTypeCode(String partyTypeCode) {
        this.partyTypeCode = partyTypeCode;
    }

    /**
     * Returns the hTTP_SESSION_KEY.
     * 
     * @return String
     */
    public static String getHttpSessionKey() {
        return HTTP_SESSION_KEY;
    }

    /**
     * Returns the moduleName.
     * 
     * @return byte
     */
    public byte getModuleName() {
        return moduleName;
    }

    /**
     * Sets the moduleName.
     * 
     * @param moduleName The moduleName to set
     */
    public void setModuleName(byte moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Returns the isModelling.
     * 
     * @return boolean
     */
    public boolean isModelling() {
        return isModelling;
    }

    /**
     * Sets the isModelling.
     * 
     * @param isModelling The isModelling to set
     */
    @Override
    public void setIsModelling(boolean isModelling) {
        this.isModelling = isModelling;
    }

    /**
     * Returns the isDeceased.
     * 
     * @return boolean
     */
    public boolean isDeceased() {
        return isDeceased;
    }

    /**
     * Sets the isDeceased.
     * 
     * @param isDeceased The isDeceased to set
     */
    public void setIsDeceased(boolean isDeceased) {
        this.isDeceased = isDeceased;
    }

    /**
     * Returns the membershipLevelCode.
     * 
     * @return String
     */
    public String getMembershipLevelCode() {
        return membershipLevelCode;
    }

    /**
     * Sets the membershipLevelCode.
     * 
     * @param membershipLevelCode The membershipLevelCode to set
     */
    public void setMembershipLevelCode(String membershipLevelCode) {
        this.membershipLevelCode = membershipLevelCode;
    }

    /**
     * Returns the auditIdGenReq.
     * 
     * @return boolean
     */
    public boolean isAuditIdGenReq() {
        return auditIdGenReq;
    }

    /**
     * Sets the auditIdGenReq.
     * 
     * @param auditIdGenReq The auditIdGenReq to set
     */
    public void setAuditIdGenReq(boolean auditIdGenReq) {
        this.auditIdGenReq = auditIdGenReq;
    }

    /**
     * Returns the reportID.
     * 
     * @return String
     */
    public String getReportID() {
        return reportID;
    }

    /**
     * Sets the reportID.
     * 
     * @param reportID The reportID to set
     */
    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    /**
     * Returns the reportName.
     * 
     * @return String
     */
    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * Returns the prepConfigParamList.
     * 
     * @return prepConfigParamList
     */
    public PrepConfigParamList getPrepConfigParamList() {
        return prepConfigParamList;
    }

    /**
     * Sets the prepConfigParamList.
     * 
     * @param prepConfigParamList The prepConfigParamList to set
     */
    public void setPrepConfigParamList(PrepConfigParamList prepConfigParamList) {
        this.prepConfigParamList = prepConfigParamList;
    }

    /**
     * Returns the deleted Id.
     * 
     * @return String
     */
    public String getDeletedId() {
        return deletedId;
    }

    /**
     * Sets the deleted Id.
     * 
     * @param id The id to set
     */
    public void setDeletedId(String deletedId) {
        this.deletedId = deletedId;
    }

    /**
     * Returns the membershipStatusCode.
     * 
     * @return String
     */
    public String getMembershipStatusCode() {
        return membershipStatusCode;
    }

    /**
     * Sets the membershipStatusCode.
     * 
     * @param membershipStatusCode The membershipStatusCode to set
     */
    public void setMembershipStatusCode(String membershipStatusCode) {
        this.membershipStatusCode = membershipStatusCode;
    }

    /**
     * Returns the partyId.
     * 
     * @return String
     */
    public String getPartyId() {
        return partyId;
    }

    /**
     * Sets the partyId.
     * 
     * @param partyId The partyId to set
     */
    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    /**
     * Returns the triggerUpdateRequired.
     * 
     * @return boolean
     */
    public boolean isTriggerUpdateRequired() {
        return triggerUpdateRequired;
    }

    /**
     * Sets the triggerUpdateRequired.
     * 
     * @param triggerUpdateRequired The triggerUpdateRequired to set
     */
    public void setTriggerUpdateRequired(boolean triggerUpdateRequired) {
        this.triggerUpdateRequired = triggerUpdateRequired;
    }

    /**
     * @return Returns the adjustmentsSearch.
     */
    public BaseSearchVOB getAdjustmentsSearch() {
        return adjustmentsSearch;
    }

    /**
     * @param adjustmentsSearch The adjustmentsSearch to set.
     */
    public void setAdjustmentsSearch(BaseSearchVOB adjustmentsSearch) {
        this.adjustmentsSearch = adjustmentsSearch;
    }

    /**
     * @return Returns the workMatchIndicator.
     */
    public String getWorkMatchIndicator() {
        return workMatchIndicator;
    }

    /**
     * @param workMatchIndicator The workMatchIndicator to set.
     */
    public void setWorkMatchIndicator(String workMatchIndicator) {
        this.workMatchIndicator = workMatchIndicator;
    }

    /**
     * @return Returns the paymentInfo.
     */
    public BaseVOB getPaymentInfo() {
        return paymentInfo;
    }

    /**
     * @param paymentInfo The paymentInfo to set.
     */
    public void setPaymentInfo(BaseVOB paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    /**
     * @return Returns the applicationLogged.
     */
    public String getApplicationLogged() {
        return applicationLogged;
    }

    /**
     * @param applicationLogged The applicationLogged to set.
     */
    public void setApplicationLogged(String applicationLogged) {
        this.applicationLogged = applicationLogged;
    }

    /**
     * @return Returns the childLoggedPartyId.
     */
    public String getChildLoggedPartyId() {
        return childLoggedPartyId;
    }

    /**
     * @param childLoggedPartyId The childLoggedPartyId to set.
     */
    public void setChildLoggedPartyId(String childLoggedPartyId) {
        this.childLoggedPartyId = childLoggedPartyId;
    }

    /**
     * @return Returns the childLoggedPartyName.
     */
    public String getChildLoggedPartyName() {
        return childLoggedPartyName;
    }

    /**
     * @param childLoggedPartyName The childLoggedPartyName to set.
     */
    public void setChildLoggedPartyName(String childLoggedPartyName) {
        this.childLoggedPartyName = childLoggedPartyName;
    }

    /**
     * @return Returns the childLoggedPartyTypeCode.
     */
    public String getChildLoggedPartyTypeCode() {
        return childLoggedPartyTypeCode;
    }

    /**
     * @param childLoggedPartyTypeCode The childLoggedPartyTypeCode to set.
     */
    public void setChildLoggedPartyTypeCode(String childLoggedPartyTypeCode) {
        this.childLoggedPartyTypeCode = childLoggedPartyTypeCode;
    }

    /**
     * @return Returns the childLoggedUserName.
     */
    public String getChildLoggedUserName() {
        return childLoggedUserName;
    }

    /**
     * @param childLoggedUserName The childLoggedUserName to set.
     */
    public void setChildLoggedUserName(String childLoggedUserName) {
        this.childLoggedUserName = childLoggedUserName;
    }

    /**
     * @return Returns the partyName.
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * @param partyName The partyName to set.
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * @return Returns the workTitle.
     */
    public String getWorkTitle() {
        return workTitle;
    }

    /**
     * @param workTitle The workTitle to set.
     */
    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    /**
     * @return Returns the applyToAllWorks.
     */
    public String getApplyToAllWorks() {
        return applyToAllWorks;
    }

    /**
     * @param applyToAllWorks The applyToAllWorks to set.
     */
    public void setApplyToAllWorks(String applyToAllWorks) {
        this.applyToAllWorks = applyToAllWorks;
    }

    /**
     * @return Returns the workId.
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * @param workId The workId to set.
     */
    public void setWorkId(String workId) {
        this.workId = workId;
    }

    /**
     * @return Returns the partyRoleTypeCode.
     */
    public String getPartyRoleTypeCode() {
        return partyRoleTypeCode;
    }

    /**
     * @param partyRoleTypeCode The partyRoleTypeCode to set.
     */
    public void setPartyRoleTypeCode(String partyRoleTypeCode) {
        this.partyRoleTypeCode = partyRoleTypeCode;
    }

    /**
     * @return Returns the isCueSheetsEnabled.
     */
    public String getIsCueSheetsEnabled() {
        return isCueSheetsEnabled;
    }

    /**
     * @param isCueSheetsEnabled The isCueSheetsEnabled to set.
     */
    public void setIsCueSheetsEnabled(String isCueSheetsEnabled) {
        this.isCueSheetsEnabled = isCueSheetsEnabled;
    }

    public String getSuperParentPartyId() {
        return superParentPartyId;
    }

    public void setSuperParentPartyId(String superParentPartyId) {
        this.superParentPartyId = superParentPartyId;
    }

    public boolean isTotalWorksBelowThreshold() {
        return totalWorksBelowThreshold;
    }

    public void setTotalWorksBelowThreshold(boolean totalWorksBelowThreshold) {
        this.totalWorksBelowThreshold = totalWorksBelowThreshold;
    }

    public BaseSearchVOB getHeaderSearchCriteria() {
        return headerSearchCriteria;
    }

    public void setHeaderSearchCriteria(BaseSearchVOB headerSearchCriteria) {
        this.headerSearchCriteria = headerSearchCriteria;
    }

    public String getHeaderIdCurrent() {
        return headerIdCurrent;
    }

    public void setHeaderIdCurrent(String headerIdCurrent) {
        this.headerIdCurrent = headerIdCurrent;
    }

    public String getHeaderIdPrev() {
        return headerIdPrev;
    }

    public void setHeaderIdPrev(String headerIdPrev) {
        this.headerIdPrev = headerIdPrev;
    }

    public String getHeaderIdNext() {
        return headerIdNext;
    }

    public void setHeaderIdNext(String headerIdNext) {
        this.headerIdNext = headerIdNext;
    }

    public String getHeaderIdCurrentRowNum() {
        return headerIdCurrentRowNum;
    }

    public void setHeaderIdCurrentRowNum(String headerIdCurrentRowNum) {
        this.headerIdCurrentRowNum = headerIdCurrentRowNum;
    }
}
