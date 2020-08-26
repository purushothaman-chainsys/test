package com.ascap.apm.vob.usage;

import java.util.List;

import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.vob.BaseSearchVOB;

/**
 * Used to Store Performance Search Properties.
 * 
 * @author Jaya Shyam Narayana Lingamchetty
 * @version $Revision: 1.8 $ $Date: May 21 2009 15:41:50 $
 */
public class PerformanceSearch extends BaseSearchVOB {

    private static final long serialVersionUID = 604350215330232191L;

    private String postedFlag = null;

    private String musicUserId = null;

    private String musicUserCallLetter = null;

    private String[] musicUserTypes = null;

    private String[] surveyTypes = null;

    private String[] sampleTypes = null;

    private String[] matchTypes = null;

    private String venue = null;

    private String fileId = null;

    private String programPerformanceId = null;

    private String[] legacySourceSystem = null;

    private String supplierCode = null;

    private String workPerformanceId = null;

    private String workPerformanceShareId = null;

    private String workId = null;

    private String performanceDistributionIndicator = null;

    private String distributionId = null;

    private String distributionTypeCode = null;

    private String distributionStartDate = null;

    private String distributionEndDate = null;

    private String batchId = null;

    private String startLegacyPerformanceId = null;

    private String endLegacyPerformanceId = null;

    private String startLegacyWorkPerformanceId = null;

    private String endLegacyWorkPerformanceId = null;

    private String startLegacyWorkPerformanceShareId = null;

    private String endLegacyWorkPerformanceShareId = null;

    private String workTitle = null;

    private String workTitleSearchType = null;

    private String performerSearchType = null;

    private String featuredPerformerName = null;

    private String performanceStartDate = null;

    private String performanceEndDate = null;

    private String performanceStartTime = null;

    private String performanceEndTime = null;

    private String seriesCode = null;

    private String seriesTitle = null;

    private String programCode = null;

    private String programTitle = null;

    private String cueSequenceNumber = null;

    private String[] compensibilityStatuses = null;

    private String scheduleId = null;

    private String scheduleSequenceNumber = null;

    private String shareOwnerPartyId = null;

    private String memberPartyNameId = null;

    private String memberPartyFullName = null;

    private String memberPartyFirstName = null;

    private String memberPartyLastName = null;

    private String[] useTypes = null;

    private String[] overlapCode = null;

    private String[] performanceErrorWarningCodes = null;

    private String[] licenseTypeCode = null;

    private String[] selectedIndexes = null;

    private String[] allPerformanceIds = null;

    private String[] allPerformanceVersionNumbers = null;

    private String[] allSourceSystems = null;

    private String[] allLegacyPerformanceHeaderIds = null;

    private String[] allPostingStatuses = null;

    private String searchResultsCountPolicy = UsageConstants.SEARCH_RESULTS_COUNT_POLICY_LIMIT_TOMAX;

    private long progressiveResultsCount = 0;

    private String oldNavigationType = null;

    private String progressiveEndReached = "N";

    private String searchDeleteFlagPolicy = UsageConstants.SEARCH_DELETE_FLAG_POLICY_EXCLUDE_DELETED_PERFORMANCES;

    private List<Object> executionProfilingMessages = null;

    private String performanceSearchType = null;

    private String actionSelected = null;

    private String[] selectedIds = null;

    private List<Object> invalidWorkPerformances = null;

    private List<Object> audits = null;

    private String supplierWorkCode = null;

    private ProgramPerformance programPerformance = null;

    private String headerIdForWorkPerf = null;

    private String programNumber;

    private String segmentNumber;

    private String headlineIndicator;

    private String setlistType;

    private String playCount;

    private String statusDateFrom;

    private String statusDateTo;

    private String targetYearQuarterFrom;

    private String targetYearQuarterTo;

    private String classicalIndicator;

    private String deleteType;

    private String assignedToUser;

    private String assignedToUserAll;

    private String assignType;

    private String showDeletedRows;

    private String headerIdCurrent;

    private String headerIdNext;

    private String headerIdPrev;

    private String headerIdCurrentRowNum;

    private String selectedProgramPerformanceId;

    private String selectedPerformanceVersionNumber;

    private String selectedRownNum;

    private String nextActionType;

    private String workPerfCount;

    private String selectedWorkPerformanceId = null;

    private String onlineEditable;

    private String useTypeCodeAll;

    /**
     * Returns the actionSelected.
     * 
     * @return String
     */
    public String getActionSelected() {
        return actionSelected;
    }

    /**
     * Returns the cueSequenceNumber.
     * 
     * @return String
     */
    public String getCueSequenceNumber() {
        return cueSequenceNumber;
    }

    /**
     * Returns the endLegacyPerformanceId.
     * 
     * @return String
     */
    public String getEndLegacyPerformanceId() {
        return endLegacyPerformanceId;
    }

    /**
     * Returns the featuredPerformerName.
     * 
     * @return String
     */
    public String getFeaturedPerformerName() {
        return featuredPerformerName;
    }

    /**
     * Returns the legacySourceSystem.
     * 
     * @return String[]
     */
    public String[] getLegacySourceSystem() {
        return legacySourceSystem;
    }

    /**
     * Returns the memberPartyNameId.
     * 
     * @return String
     */
    public String getMemberPartyNameId() {
        return memberPartyNameId;
    }

    /**
     * Returns the memberPartyFirstName.
     * 
     * @return String
     */
    public String getMemberPartyFirstName() {
        return memberPartyFirstName;
    }

    /**
     * Returns the memberPartyLastName.
     * 
     * @return String
     */
    public String getMemberPartyLastName() {
        return memberPartyLastName;
    }

    /**
     * Returns the musicUserCallLetter.
     * 
     * @return String
     */
    public String getMusicUserCallLetter() {
        return musicUserCallLetter;
    }

    /**
     * Returns the musicUserId.
     * 
     * @return String
     */
    public String getMusicUserId() {
        return musicUserId;
    }

    /**
     * Returns the musicUserTypes.
     * 
     * @return String[]
     */
    public String[] getMusicUserTypes() {
        return musicUserTypes;
    }

    /**
     * Returns the overlapCode.
     * 
     * @return String[]
     */
    public String[] getOverlapCode() {
        return overlapCode;
    }

    /**
     * Returns the performanceEndDate.
     * 
     * @return String
     */
    public String getPerformanceEndDate() {
        return performanceEndDate;
    }

    /**
     * Returns the performanceEndTime.
     * 
     * @return String
     */
    public String getPerformanceEndTime() {
        return performanceEndTime;
    }

    /**
     * Returns the performanceErrorWarningCodes.
     * 
     * @return String[]
     */
    public String[] getPerformanceErrorWarningCodes() {
        return performanceErrorWarningCodes;
    }

    /**
     * Returns the performanceStartDate.
     * 
     * @return String
     */
    public String getPerformanceStartDate() {
        return performanceStartDate;
    }

    /**
     * Returns the performanceStartTime.
     * 
     * @return String
     */
    public String getPerformanceStartTime() {
        return performanceStartTime;
    }

    /**
     * Returns the programCode.
     * 
     * @return String
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * Returns the programPerformanceId.
     * 
     * @return String
     */
    public String getProgramPerformanceId() {
        return programPerformanceId;
    }

    /**
     * Returns the programTitle.
     * 
     * @return String
     */
    public String getProgramTitle() {
        return programTitle;
    }

    /**
     * Returns the sampleTypes.
     * 
     * @return String[]
     */
    public String[] getSampleTypes() {
        return sampleTypes;
    }

    /**
     * Returns the selectedIds.
     * 
     * @return String[]
     */
    public String[] getSelectedIds() {
        return selectedIds;
    }

    /**
     * Returns the seriesCode.
     * 
     * @return String
     */
    public String getSeriesCode() {
        return seriesCode;
    }

    /**
     * Returns the seriesTitle.
     * 
     * @return String
     */
    public String getSeriesTitle() {
        return seriesTitle;
    }

    /**
     * Returns the startLegacyPerformanceId.
     * 
     * @return String
     */
    public String getStartLegacyPerformanceId() {
        return startLegacyPerformanceId;
    }

    /**
     * Returns the surveyTypes.
     * 
     * @return String[]
     */
    public String[] getSurveyTypes() {
        return surveyTypes;
    }

    /**
     * Returns the useTypes.
     * 
     * @return String[]
     */
    public String[] getUseTypes() {
        return useTypes;
    }

    /**
     * Returns the venue.
     * 
     * @return String
     */
    public String getVenue() {
        return venue;
    }

    /**
     * Returns the workId.
     * 
     * @return String
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * Returns the workPerformanceId.
     * 
     * @return String
     */
    public String getWorkPerformanceId() {
        return workPerformanceId;
    }

    /**
     * Returns the workTitle.
     * 
     * @return String
     */
    public String getWorkTitle() {
        return workTitle;
    }

    /**
     * Sets the actionSelected.
     * 
     * @param actionSelected The actionSelected to set
     */
    public void setActionSelected(String actionSelected) {
        this.actionSelected = actionSelected;
    }

    /**
     * Sets the cueSequenceNumber.
     * 
     * @param cueSequenceNumber The cueSequenceNumber to set
     */
    public void setCueSequenceNumber(String cueSequenceNumber) {
        this.cueSequenceNumber = cueSequenceNumber;
    }

    /**
     * Sets the endLegacyPerformanceId.
     * 
     * @param endLegacyPerformanceId The endLegacyPerformanceId to set
     */
    public void setEndLegacyPerformanceId(String endLegacyPerformanceId) {
        this.endLegacyPerformanceId = endLegacyPerformanceId;
    }

    /**
     * Sets the featuredPerformerName.
     * 
     * @param featuredPerformerName The featuredPerformerName to set
     */
    public void setFeaturedPerformerName(String featuredPerformerName) {
        this.featuredPerformerName = featuredPerformerName;
    }

    /**
     * Sets the legacySourceSystem.
     * 
     * @param legacySourceSystem The legacySourceSystem to set
     */
    public void setLegacySourceSystem(String[] legacySourceSystem) {
        this.legacySourceSystem = legacySourceSystem;
    }

    /**
     * Sets the memberPartyNameId.
     * 
     * @param memberPartyNameId The memberPartyId to set
     */
    public void setMemberPartyNameId(String memberPartyNameId) {
        this.memberPartyNameId = memberPartyNameId;
    }

    /**
     * Sets the memberPartyFirstName.
     * 
     * @param memberPartyFirstName The memberPartyFirstName to set
     */
    public void setMemberPartyFirstName(String memberPartyFirstName) {
        this.memberPartyFirstName = memberPartyFirstName;
    }

    /**
     * Sets the memberPartyLastName.
     * 
     * @param memberPartyLastName The memberPartyLastName to set
     */
    public void setMemberPartyLastName(String memberPartyLastName) {
        this.memberPartyLastName = memberPartyLastName;
    }

    /**
     * Sets the musicUserCallLetter.
     * 
     * @param musicUserCallLetter The musicUserCallLetter to set
     */
    public void setMusicUserCallLetter(String musicUserCallLetter) {
        this.musicUserCallLetter = musicUserCallLetter;
    }

    /**
     * Sets the musicUserId.
     * 
     * @param musicUserId The musicUserId to set
     */
    public void setMusicUserId(String musicUserId) {
        this.musicUserId = musicUserId;
    }

    /**
     * Sets the musicUserTypes.
     * 
     * @param musicUserTypes The musicUserTypes to set
     */
    public void setMusicUserTypes(String[] musicUserTypes) {
        this.musicUserTypes = musicUserTypes;
    }

    /**
     * Sets the overlapCode.
     * 
     * @param overlapCode The overlapCode to set
     */
    public void setOverlapCode(String[] overlapCode) {
        this.overlapCode = overlapCode;
    }

    /**
     * Sets the performanceEndDate.
     * 
     * @param performanceEndDate The performanceEndDate to set
     */
    public void setPerformanceEndDate(String performanceEndDate) {
        this.performanceEndDate = performanceEndDate;
    }

    /**
     * Sets the performanceEndTime.
     * 
     * @param performanceEndTime The performanceEndTime to set
     */
    public void setPerformanceEndTime(String performanceEndTime) {
        this.performanceEndTime = performanceEndTime;
    }

    /**
     * Sets the performanceErrorWarningCodes.
     * 
     * @param performanceErrorWarningCodes The performanceErrorWarningCodes to set
     */
    public void setPerformanceErrorWarningCodes(String[] performanceErrorWarningCodes) {
        this.performanceErrorWarningCodes = performanceErrorWarningCodes;
    }

    /**
     * Sets the performanceStartDate.
     * 
     * @param performanceStartDate The performanceStartDate to set
     */
    public void setPerformanceStartDate(String performanceStartDate) {
        this.performanceStartDate = performanceStartDate;
    }

    /**
     * Sets the performanceStartTime.
     * 
     * @param performanceStartTime The performanceStartTime to set
     */
    public void setPerformanceStartTime(String performanceStartTime) {
        this.performanceStartTime = performanceStartTime;
    }

    /**
     * Sets the programCode.
     * 
     * @param programCode The programCode to set
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    /**
     * Sets the programPerformanceId.
     * 
     * @param programPerformanceId The programPerformanceId to set
     */
    public void setProgramPerformanceId(String programPerformanceId) {
        this.programPerformanceId = programPerformanceId;
    }

    /**
     * Sets the programTitle.
     * 
     * @param programTitle The programTitle to set
     */
    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    /**
     * Sets the sampleTypes.
     * 
     * @param sampleTypes The sampleTypes to set
     */
    public void setSampleTypes(String[] sampleTypes) {
        this.sampleTypes = sampleTypes;
    }

    /**
     * Sets the selectedIds.
     * 
     * @param selectedIds The selectedIds to set
     */
    public void setSelectedIds(String[] selectedIds) {
        this.selectedIds = selectedIds;
    }

    /**
     * Sets the seriesCode.
     * 
     * @param seriesCode The seriesCode to set
     */
    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    /**
     * Sets the seriesTitle.
     * 
     * @param seriesTitle The seriesTitle to set
     */
    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    /**
     * Sets the startLegacyPerformanceId.
     * 
     * @param startLegacyPerformanceId The startLegacyPerformanceId to set
     */
    public void setStartLegacyPerformanceId(String startLegacyPerformanceId) {
        this.startLegacyPerformanceId = startLegacyPerformanceId;
    }

    /**
     * Sets the surveyTypes.
     * 
     * @param surveyTypes The surveyTypes to set
     */
    public void setSurveyTypes(String[] surveyTypes) {
        this.surveyTypes = surveyTypes;
    }

    /**
     * Sets the useTypes.
     * 
     * @param useTypes The useTypes to set
     */
    public void setUseTypes(String[] useTypes) {
        this.useTypes = useTypes;
    }

    /**
     * Sets the venue.
     * 
     * @param venue The venue to set
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * Sets the workId.
     * 
     * @param workId The workId to set
     */
    public void setWorkId(String workId) {
        this.workId = workId;
    }

    /**
     * Sets the workPerformanceId.
     * 
     * @param workPerformanceId The workPerformanceId to set
     */
    public void setWorkPerformanceId(String workPerformanceId) {
        this.workPerformanceId = workPerformanceId;
    }

    /**
     * Sets the workTitle.
     * 
     * @param workTitle The workTitle to set
     */
    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    @Override
    public String toString() {
        StringBuilder outStrBuff;
        outStrBuff = new StringBuilder();
        outStrBuff.append("com.ascap.apm.common.vob.usage.PerformanceSearch {").append("selectedIds= '")
            .append(selectedIds).append("', ").append("actionSelected= '").append(actionSelected).append("', ")
            .append("performanceErrorWarningCodes= '").append(performanceErrorWarningCodes).append("', ")
            .append("overlapCode= '").append(overlapCode).append("', ").append("useTypes= '").append(useTypes)
            .append("', ").append("cueSequenceNumber= '").append(cueSequenceNumber).append("', ")
            .append("memberPartyFirstName= '").append(memberPartyFirstName).append("', ")
            .append("memberPartyLastName= '").append(memberPartyLastName).append("', ").append("programTitle= '")
            .append(programTitle).append("', ").append("programCode= '").append(programCode).append("', ")
            .append("memberPartyNameId= '").append(memberPartyNameId).append("', ").append("seriesTitle= '")
            .append(seriesTitle).append("', ").append("seriesCode= '").append(seriesCode).append("', ")
            .append("performanceEndTime= '").append(performanceEndTime).append("', ").append("performanceStartTime= '")
            .append(performanceStartTime).append("', ").append("featuredPerformerName= '").append(featuredPerformerName)
            .append("', ").append("performanceEndDate= '").append(performanceEndDate).append("', ")
            .append("performanceStartDate= '").append(performanceStartDate).append("', ").append("workTitle= '")
            .append(workTitle).append("', ").append("endLegacyPerformanceId= '").append(endLegacyPerformanceId)
            .append("', ").append("startLegacyPerformanceId= '").append(startLegacyPerformanceId).append("', ")
            .append("workId= '").append(workId).append("', ").append("workPerformanceId= '").append(workPerformanceId)
            .append("', ").append("legacySourceSystem= '").append(legacySourceSystem).append("', ")
            .append("programPerformanceId= '").append(programPerformanceId).append("', ").append("venue= '")
            .append(venue).append("', ").append("sampleTypes= '").append(sampleTypes).append("', ")
            .append("surveyTypes= '").append(surveyTypes).append("', ").append("musicUserTypes= '")
            .append(musicUserTypes).append("', ").append("musicUserCallLetter= '").append(musicUserCallLetter)
            .append("', ").append("musicUserId= '").append(musicUserId).append("', ").append("performanceSearchType= '")
            .append(performanceSearchType).append("', ").append("showDeletedRows= '").append(showDeletedRows)
            .append("', ").append("'}");
        return (outStrBuff.toString());
    }

    /**
     * Returns the performanceSearchType.
     * 
     * @return String
     */
    public String getPerformanceSearchType() {
        return performanceSearchType;
    }

    /**
     * Sets the performanceSearchType.
     * 
     * @param performanceSearchType The performanceSearchType to set
     */
    public void setPerformanceSearchType(String performanceSearchType) {
        this.performanceSearchType = performanceSearchType;
    }

    /**
     * Returns the licenseTypeCode.
     * 
     * @return String[]
     */
    public String[] getLicenseTypeCode() {
        return licenseTypeCode;
    }

    /**
     * Sets the licenseTypeCode.
     * 
     * @param licenseTypeCode The licenseTypeCode to set
     */
    public void setLicenseTypeCode(String[] licenseTypeCode) {
        this.licenseTypeCode = licenseTypeCode;
    }

    /**
     * Returns the fileId.
     * 
     * @return String
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * Sets the fileId.
     * 
     * @param fileId The fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * Returns the invalidWorkPerformances.
     * 
     * @return Collection
     */
    public List<Object> getInvalidWorkPerformances() {
        return invalidWorkPerformances;
    }

    /**
     * Sets the invalidWorkPerformances.
     * 
     * @param invalidWorkPerformances The invalidWorkPerformances to set
     */
    public void setInvalidWorkPerformances(List<Object> invalidWorkPerformances) {
        this.invalidWorkPerformances = invalidWorkPerformances;
    }

    /**
     * @return Returns the endLegacyWorkPerformanceId.
     */
    public String getEndLegacyWorkPerformanceId() {
        return endLegacyWorkPerformanceId;
    }

    /**
     * @param endLegacyWorkPerformanceId The endLegacyWorkPerformanceId to set.
     */
    public void setEndLegacyWorkPerformanceId(String endLegacyWorkPerformanceId) {
        this.endLegacyWorkPerformanceId = endLegacyWorkPerformanceId;
    }

    /**
     * @return Returns the endLegacyWorkPerformanceShareId.
     */
    public String getEndLegacyWorkPerformanceShareId() {
        return endLegacyWorkPerformanceShareId;
    }

    /**
     * @param endLegacyWorkPerformanceShareId The endLegacyWorkPerformanceShareId to set.
     */
    public void setEndLegacyWorkPerformanceShareId(String endLegacyWorkPerformanceShareId) {
        this.endLegacyWorkPerformanceShareId = endLegacyWorkPerformanceShareId;
    }

    /**
     * @return Returns the startLegacyWorkPerformanceId.
     */
    public String getStartLegacyWorkPerformanceId() {
        return startLegacyWorkPerformanceId;
    }

    /**
     * @param startLegacyWorkPerformanceId The startLegacyWorkPerformanceId to set.
     */
    public void setStartLegacyWorkPerformanceId(String startLegacyWorkPerformanceId) {
        this.startLegacyWorkPerformanceId = startLegacyWorkPerformanceId;
    }

    /**
     * @return Returns the startLegacyWorkPerformanceShareId.
     */
    public String getStartLegacyWorkPerformanceShareId() {
        return startLegacyWorkPerformanceShareId;
    }

    /**
     * @param startLegacyWorkPerformanceShareId The startLegacyWorkPerformanceShareId to set.
     */
    public void setStartLegacyWorkPerformanceShareId(String startLegacyWorkPerformanceShareId) {
        this.startLegacyWorkPerformanceShareId = startLegacyWorkPerformanceShareId;
    }

    /**
     * @return Returns the workPerformanceShareId.
     */
    public String getWorkPerformanceShareId() {
        return workPerformanceShareId;
    }

    /**
     * @param workPerformanceShareId The workPerformanceShareId to set.
     */
    public void setWorkPerformanceShareId(String workPerformanceShareId) {
        this.workPerformanceShareId = workPerformanceShareId;
    }

    /**
     * @return Returns the batchId.
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * @param batchId The batchId to set.
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    /**
     * @return Returns the distributionId.
     */
    public String getDistributionId() {
        return distributionId;
    }

    /**
     * @param distributionId The distributionId to set.
     */
    public void setDistributionId(String distributionId) {
        this.distributionId = distributionId;
    }

    /**
     * @return Returns the compensibilityStatuses.
     */
    public String[] getCompensibilityStatuses() {
        return compensibilityStatuses;
    }

    /**
     * @param compensibilityStatuses The compensibilityStatuses to set.
     */
    public void setCompensibilityStatuses(String[] compensibilityStatuses) {
        this.compensibilityStatuses = compensibilityStatuses;
    }

    /**
     * @return Returns the performanceDistributionIndicator.
     */
    public String getPerformanceDistributionIndicator() {
        return performanceDistributionIndicator;
    }

    /**
     * @param performanceDistributionIndicator The performanceDistributionIndicator to set.
     */
    public void setPerformanceDistributionIndicator(String performanceDistributionIndicator) {
        this.performanceDistributionIndicator = performanceDistributionIndicator;
    }

    /**
     * @return Returns the shareOwnerPartyId.
     */
    public String getShareOwnerPartyId() {
        return shareOwnerPartyId;
    }

    /**
     * @param shareOwnerPartyId The shareOwnerPartyId to set.
     */
    public void setShareOwnerPartyId(String shareOwnerPartyId) {
        this.shareOwnerPartyId = shareOwnerPartyId;
    }

    /**
     * @return Returns the distributionTypeCode.
     */
    public String getDistributionTypeCode() {
        return distributionTypeCode;
    }

    /**
     * @param distributionTypeCode The distributionTypeCode to set.
     */
    public void setDistributionTypeCode(String distributionTypeCode) {
        this.distributionTypeCode = distributionTypeCode;
    }

    /**
     * @return Returns the distributionEndDate.
     */
    public String getDistributionEndDate() {
        return distributionEndDate;
    }

    /**
     * @param distributionEndDate The distributionEndDate to set.
     */
    public void setDistributionEndDate(String distributionEndDate) {
        this.distributionEndDate = distributionEndDate;
    }

    /**
     * @return Returns the distributionStartDate.
     */
    public String getDistributionStartDate() {
        return distributionStartDate;
    }

    /**
     * @param distributionStartDate The distributionStartDate to set.
     */
    public void setDistributionStartDate(String distributionStartDate) {
        this.distributionStartDate = distributionStartDate;
    }

    /**
     * @return Returns the searchResultsCountPolicy.
     */
    public String getSearchResultsCountPolicy() {
        return searchResultsCountPolicy;
    }

    /**
     * @param searchResultsCountPolicy The searchResultsCountPolicy to set.
     */
    public void setSearchResultsCountPolicy(String searchResultsCountPolicy) {
        this.searchResultsCountPolicy = searchResultsCountPolicy;
    }

    /**
     * @return Returns the progressiveResultsCount.
     */
    public long getProgressiveResultsCount() {
        return progressiveResultsCount;
    }

    /**
     * @param progressiveResultsCount The progressiveResultsCount to set.
     */
    public void setProgressiveResultsCount(long progressiveResultsCount) {
        this.progressiveResultsCount = progressiveResultsCount;
    }

    /*
     * (non-Javadoc)
     * @see com.ascap.apm.common.vob.BaseSearchVOB#setNavigationType(java.lang.String)
     */
    @Override
    public void setNavigationType(String navigationType) {
        this.oldNavigationType = navigationType;
        super.setNavigationType(navigationType);
    }

    /**
     * @return Returns the oldNavigationType.
     */
    public String getOldNavigationType() {
        return oldNavigationType;
    }

    /**
     * @return Returns the progressiveEndReached.
     */
    public String getProgressiveEndReached() {
        return progressiveEndReached;
    }

    /**
     * @param progressiveEndReached The progressiveEndReached to set.
     */
    public void setProgressiveEndReached(String progressiveEndReached) {
        this.progressiveEndReached = progressiveEndReached;
    }

    /**
     * @return Returns the executionProfilingMessages.
     */
    public List<Object> getExecutionProfilingMessages() {
        return executionProfilingMessages;
    }

    /**
     * @param executionProfilingMessages The executionProfilingMessages to set.
     */
    public void setExecutionProfilingMessages(List<Object> executionProfilingMessages) {
        this.executionProfilingMessages = executionProfilingMessages;
    }

    /**
     * @return Returns the memberPartyFullName.
     */
    public String getMemberPartyFullName() {
        return memberPartyFullName;
    }

    /**
     * @param memberPartyFullName The memberPartyFullName to set.
     */
    public void setMemberPartyFullName(String memberPartyFullName) {
        this.memberPartyFullName = memberPartyFullName;
    }

    /**
     * @return Returns the searchDeleteFlagPolicy.
     */
    public String getSearchDeleteFlagPolicy() {
        return searchDeleteFlagPolicy;
    }

    /**
     * @param searchDeleteFlagPolicy The searchDeleteFlagPolicy to set.
     */
    public void setSearchDeleteFlagPolicy(String searchDeleteFlagPolicy) {
        this.searchDeleteFlagPolicy = searchDeleteFlagPolicy;
    }

    /**
     * @return Returns the scheduleId.
     */
    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * @param scheduleId The scheduleId to set.
     */
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * @return Returns the scheduleSequenceNumber.
     */
    public String getScheduleSequenceNumber() {
        return scheduleSequenceNumber;
    }

    /**
     * @param scheduleSequenceNumber The scheduleSequenceNumber to set.
     */
    public void setScheduleSequenceNumber(String scheduleSequenceNumber) {
        this.scheduleSequenceNumber = scheduleSequenceNumber;
    }

    /**
     * @return the audits
     */
    public List<Object> getAudits() {
        return audits;
    }

    /**
     * @param audits the audits to set
     */
    public void setAudits(List<Object> audits) {
        this.audits = audits;
    }

    /**
     * @return the supplierCode
     */
    public String getSupplierCode() {
        return supplierCode;
    }

    /**
     * @param supplierCode the supplierCode to set
     */
    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierWorkCode() {
        return supplierWorkCode;
    }

    public void setSupplierWorkCode(String supplierWorkCode) {
        this.supplierWorkCode = supplierWorkCode;
    }

    public String getPostedFlag() {
        return postedFlag;
    }

    public void setPostedFlag(String postedFlag) {
        this.postedFlag = postedFlag;
    }

    public ProgramPerformance getProgramPerformance() {
        return programPerformance;
    }

    public void setProgramPerformance(ProgramPerformance programPerformance) {
        this.programPerformance = programPerformance;
    }

    public String getHeaderIdForWorkPerf() {
        return headerIdForWorkPerf;
    }

    public void setHeaderIdForWorkPerf(String headerIdForWorkPerf) {
        this.headerIdForWorkPerf = headerIdForWorkPerf;
    }

    public String getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(String segmentNumber) {
        this.segmentNumber = segmentNumber;
    }

    public String getHeadlineIndicator() {
        return headlineIndicator;
    }

    public void setHeadlineIndicator(String headlineIndicator) {
        this.headlineIndicator = headlineIndicator;
    }

    public String getSetlistType() {
        return setlistType;
    }

    public void setSetlistType(String setlistType) {
        this.setlistType = setlistType;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(String programNumber) {
        this.programNumber = programNumber;
    }

    public String getStatusDateFrom() {
        return statusDateFrom;
    }

    public void setStatusDateFrom(String statusDateFrom) {
        this.statusDateFrom = statusDateFrom;
    }

    public String getStatusDateTo() {
        return statusDateTo;
    }

    public void setStatusDateTo(String statusDateTo) {
        this.statusDateTo = statusDateTo;
    }

    public String getTargetYearQuarterFrom() {
        return targetYearQuarterFrom;
    }

    public void setTargetYearQuarterFrom(String targetYearQuarterFrom) {
        this.targetYearQuarterFrom = targetYearQuarterFrom;
    }

    public String getTargetYearQuarterTo() {
        return targetYearQuarterTo;
    }

    public void setTargetYearQuarterTo(String targetYearQuarterTo) {
        this.targetYearQuarterTo = targetYearQuarterTo;
    }

    public String[] getMatchTypes() {
        return matchTypes;
    }

    public void setMatchTypes(String[] matchTypes) {
        this.matchTypes = matchTypes;
    }

    public String getWorkTitleSearchType() {
        return workTitleSearchType;
    }

    public void setWorkTitleSearchType(String workTitleSearchType) {
        this.workTitleSearchType = workTitleSearchType;
    }

    public String getPerformerSearchType() {
        return performerSearchType;
    }

    public void setPerformerSearchType(String performerSearchType) {
        this.performerSearchType = performerSearchType;
    }

    public String getClassicalIndicator() {
        return classicalIndicator;
    }

    public void setClassicalIndicator(String classicalIndicator) {
        this.classicalIndicator = classicalIndicator;
    }

    public String getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }

    public String getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(String assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    public String getShowDeletedRows() {
        return showDeletedRows;
    }

    public void setShowDeletedRows(String showDeletedRows) {
        this.showDeletedRows = showDeletedRows;
    }

    public String getAssignedToUserAll() {
        return assignedToUserAll;
    }

    public void setAssignedToUserAll(String assignedToUserAll) {
        this.assignedToUserAll = assignedToUserAll;
    }

    public String getAssignType() {
        return assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType;
    }

    public String getHeaderIdCurrent() {
        return headerIdCurrent;
    }

    public void setHeaderIdCurrent(String headerIdCurrent) {
        this.headerIdCurrent = headerIdCurrent;
    }

    public String getHeaderIdNext() {
        return headerIdNext;
    }

    public void setHeaderIdNext(String headerIdNext) {
        this.headerIdNext = headerIdNext;
    }

    public String getHeaderIdPrev() {
        return headerIdPrev;
    }

    public void setHeaderIdPrev(String headerIdPrev) {
        this.headerIdPrev = headerIdPrev;
    }

    public String getHeaderIdCurrentRowNum() {
        return headerIdCurrentRowNum;
    }

    public void setHeaderIdCurrentRowNum(String headerIdCurrentRowNum) {
        this.headerIdCurrentRowNum = headerIdCurrentRowNum;
    }

    public String getSelectedProgramPerformanceId() {
        return selectedProgramPerformanceId;
    }

    public void setSelectedProgramPerformanceId(String selectedProgramPerformanceId) {
        this.selectedProgramPerformanceId = selectedProgramPerformanceId;
    }

    public String getSelectedPerformanceVersionNumber() {
        return selectedPerformanceVersionNumber;
    }

    public void setSelectedPerformanceVersionNumber(String selectedPerformanceVersionNumber) {
        this.selectedPerformanceVersionNumber = selectedPerformanceVersionNumber;
    }

    public String getSelectedRownNum() {
        return selectedRownNum;
    }

    public void setSelectedRownNum(String selectedRownNum) {
        this.selectedRownNum = selectedRownNum;
    }

    public String getNextActionType() {
        return nextActionType;
    }

    public void setNextActionType(String nextActionType) {
        this.nextActionType = nextActionType;
    }

    public String getWorkPerfCount() {
        return workPerfCount;
    }

    public void setWorkPerfCount(String workPerfCount) {
        this.workPerfCount = workPerfCount;
    }

    public String getSelectedWorkPerformanceId() {
        return selectedWorkPerformanceId;
    }

    public void setSelectedWorkPerformanceId(String selectedWorkPerformanceId) {
        this.selectedWorkPerformanceId = selectedWorkPerformanceId;
    }

    public String getOnlineEditable() {
        return onlineEditable;
    }

    public void setOnlineEditable(String onlineEditable) {
        this.onlineEditable = onlineEditable;
    }

    public String getUseTypeCodeAll() {
        return useTypeCodeAll;
    }

    public void setUseTypeCodeAll(String useTypeCodeAll) {
        this.useTypeCodeAll = useTypeCodeAll;
    }

    public String[] getSelectedIndexes() {
        return selectedIndexes;
    }

    public void setSelectedIndexes(String[] selectedIndexes) {
        this.selectedIndexes = selectedIndexes;
    }

    public String[] getAllPerformanceIds() {
        return allPerformanceIds;
    }

    public void setAllPerformanceIds(String[] allPerformanceIds) {
        this.allPerformanceIds = allPerformanceIds;
    }

    public String[] getAllPerformanceVersionNumbers() {
        return allPerformanceVersionNumbers;
    }

    public void setAllPerformanceVersionNumbers(String[] allPerformanceVersionNumbers) {
        this.allPerformanceVersionNumbers = allPerformanceVersionNumbers;
    }

    public String[] getAllSourceSystems() {
        return allSourceSystems;
    }

    public void setAllSourceSystems(String[] allSourceSystems) {
        this.allSourceSystems = allSourceSystems;
    }

    public String[] getAllLegacyPerformanceHeaderIds() {
        return allLegacyPerformanceHeaderIds;
    }

    public void setAllLegacyPerformanceHeaderIds(String[] allLegacyPerformanceHeaderIds) {
        this.allLegacyPerformanceHeaderIds = allLegacyPerformanceHeaderIds;
    }

    public String[] getAllPostingStatuses() {
        return allPostingStatuses;
    }

    public void setAllPostingStatuses(String[] allPostingStatuses) {
        this.allPostingStatuses = allPostingStatuses;
    }

}
