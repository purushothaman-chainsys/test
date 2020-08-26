package com.ascap.apm.vob.usage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;

import com.ascap.apm.common.exception.cache.InvalidCacheException;
import com.ascap.apm.common.exception.cache.KeyNotFoundException;
import com.ascap.apm.common.utils.DateUtils;
import com.ascap.apm.common.utils.UsageCommonValidations;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.cache.lookup.LookupTables;
import com.ascap.apm.common.utils.constants.PerformanceMusicUserUIGroups;
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.vob.BaseVOB;

/**
 * Work Performance VOB
 * 
 * @author Manoj Puli
 * @version $Revision: 1.17 $ $Date: Mar 10 2010 12:39:46 $
 */
public class WorkPerformance extends BaseVOB {

    private static final long serialVersionUID = 3395706967505088571L;

    private String performanceHeaderId = null;

    private String performanceHeaderVersionNumber = null;

    private String performanceHeaderIsCurrentVersion = null;

    private String workPerformanceId = null;

    private String versionNumber = null;

    private String isCurrentVersion = null;

    private String legacyProgramPerformanceId = null;

    private String legacyWorkPerformanceId = null;

    private String legacyMusicUserId = null;

    private String distributionId = null;

    private String distributionDate = null;

    private String runcontrollId = null;

    private String usageSelectionId = null;

    private String workSequenceNumber = null;

    private String cueSheetSequenceNumber = null;

    private String medleySequenceNumber = null;

    private String workTitle = null;

    private String workId = null;

    private String useTypeCode;

    private String useTypeDescription = null;

    private String workPerformanceDuration = null;

    private String durationDisplayString = null;

    private String performerName = null;

    private String librarySurveyIndicator = null;

    private String playCount = null;

    private String workPerformanceDurationInMinutes = null;// For SRE

    private String smcCode = null; // concatenated category sub category

    private String sreCategory = null;

    private String sreSubCategory = null;

    // Derived from lookup based on workPerformanceDuration
    private String sreDurationCode = null;

    private String srePointValue = null;

    private String instrumentationCode = null;

    private String instrumentalOrVocalIndicator = null;

    private String allocationGroupId = null;

    private String workPerformanceAdjustmentIndicator = null;

    private String copyrightArrangementPercentage = null;

    private String origAscapIntlIncmgDistDate = null;

    private String workPerformanceActionCode = null;

    private String nabtWriterRevenuePoolAmount = null;

    private String nabtPublisherRevenuePoolAmount = null;

    private String creditSymbol = null;

    private List<Object> errorsAndWarnings = null;

    private String workPerformanceDeleteFlag = null;// Mayberequired since version is done

    private String errorFlag = null;

    // START Display Only or Redundant fields from Program Performance for search or
    // for efficiency in Validations
    // Is used by distribution is from the performance Header
    private String isUsedByDistribution = null;

    private String musicUserId = null;

    private String musicUserFirstName = null;

    private String musicUserLastName = null;

    private String musicUserFullName = null;

    private String musicUserCallLetterSuffix = null;

    // Muser User Type
    private String musicUserTypeCode = null;

    private String musicUserTypeDescription = null;

    // Performance Date & Time
    private String performanceStartDate = null;

    private String performanceStartTime = null;

    private String performanceEndDate = null;

    private String performanceEndTime = null;

    // Series Code Series Title
    private String seriesCode = null;

    private String seriesTitle = null;

    // Program Code Program Title
    private String showProgramCode = null;

    private String programTitle = null;

    private String compensibilityStatusCode = null;

    private String compensibilityStatusDescription = null;

    private String programNumber = null;

    // License Type
    private String licenseTypeCode = null;

    private String licenseTypeDescription = null;

    // Used when Distribution uses for Search Results Only UI Ccolumns
    private String baseCredit = null;

    private String adjBaseCredit = null;

    private String finalDistrCredit = null;

    private String rfpCredit = null;

    private String tvpCredit = null;

    private String musicUserType = null;

    private String performanceDate = null;

    private String performanceDeleteFlag = null;
    // Used when Distribution uses for Search Results Only UI Ccolumns

    // Source System
    private String sourceSystem = null;

    private String sourceSystemDescription = null;

    private String supplierDes = null;

    private String procStatus = null;

    private String targetYearQuarter;

    private String matchTypeCode = null;

    private String matchTypeDes = null;

    private String fileId = null;

    private String providerId = null;

    private String supplierCode = null;

    private String statusDate;

    private String onlineHeaderEditable;

    private String onlineWorkPerfEditable;

    // Used for Work Object Merging and Processing Validations in Helper END

    private List<Object> workPerformanceShares = null;

    // This is for the Issue #932, If the WorkPerformance is
    // Distributed This object will hold the Weights and other factors applied
    // on it by the Distribution System.
    private WeightsAndRulesApplied weightsAndRulesApplied = null;

    private boolean isAnyAdjustmentTriggerCreated = false;

    private String apmSourceSongKey = null;

    private String apmWorkEffectiveStartDate = null;

    private String apmWorkEffectiveEndDate = null;

    private String apmLockIndicator = null;

    private String apmSendToManMatchIndicator = null;

    private String apmStatusDate = null;

    private String apmBatchId = null;

    private String apmEstimatedDollarVal = null;

    private String apmEstimatedDollarFound = null;

    private String apmChannel = null;

    private String apmAlbum = null;

    private String apmLabel = null;

    private String apmIsrc = null;

    private String apmCatalogNumber = null;

    private String apmCatalogYear = null;

    private String apmCompany = null;

    private String apmStation = null;

    private String apmSupplierId = null;

    private String apmPerfDate = null;

    private String apmPerfTime = null;

    private String apmClassical = null;

    private String detectionTime;

    private String libraryDiscTitle;

    private String libraryDiscId;

    private String confidenceLevel;

    private String writer;

    private String libraryName;

    private String libraryTrack;

    private String libraryTrackId;

    private String assignedToUser;

    private String performanceQuarter;

    private String performancePeriod;

    private String priority;

    private String operationMode = null;

    private String actionSelected = null;

    private String musicUserWeight = null;

    private String livePopEconomicComponent = null;

    private String livePopMusicDensityComponent = null;

    private String creditsPerSitePerPlay = null;

    private String pointsForTitle = null;

    private String useWeight = null;

    private String useWeightRuleId = null;

    private String creditSymbolForUseWeight = null;

    private String timeOfDayWeight = null;

    private String perSetCode = null;

    private String dbWorkTitle = null;

    private String dbPerformerName = null;

    private String programPerformanceHeaderId = null;

    private String[] errorCodes = null;

    private String[] errorTypes = null;

    private String[] errorMessages = null;

    private String musicUserInformationMode = null;

    @Override
    public String toString() {
        return "WorkPerformance [performanceHeaderId=" + performanceHeaderId + ", performanceHeaderVersionNumber="
            + performanceHeaderVersionNumber + ", performanceHeaderIsCurrentVersion="
            + performanceHeaderIsCurrentVersion + ", workPerformanceId=" + workPerformanceId + ", versionNumber="
            + versionNumber + ", isCurrentVersion=" + isCurrentVersion + ", legacyProgramPerformanceId="
            + legacyProgramPerformanceId + ", legacyWorkPerformanceId=" + legacyWorkPerformanceId
            + ", legacyMusicUserId=" + legacyMusicUserId + ", distributionId=" + distributionId + ", distributionDate="
            + distributionDate + ", runcontrollId=" + runcontrollId + ", usageSelectionId=" + usageSelectionId
            + ", workSequenceNumber=" + workSequenceNumber + ", cueSheetSequenceNumber=" + cueSheetSequenceNumber
            + ", medleySequenceNumber=" + medleySequenceNumber + ", workTitle=" + workTitle + ", workId=" + workId
            + ", useTypeCode=" + useTypeCode + ", useTypeDescription=" + useTypeDescription
            + ", workPerformanceDuration=" + workPerformanceDuration + ", durationDisplayString="
            + durationDisplayString + ", performerName=" + performerName + ", librarySurveyIndicator="
            + librarySurveyIndicator + ", playCount=" + playCount + ", workPerformanceDurationInMinutes="
            + workPerformanceDurationInMinutes + ", smcCode=" + smcCode + ", sreCategory=" + sreCategory
            + ", sreSubCategory=" + sreSubCategory + ", sreDurationCode=" + sreDurationCode + ", srePointValue="
            + srePointValue + ", instrumentationCode=" + instrumentationCode + ", instrumentalOrVocalIndicator="
            + instrumentalOrVocalIndicator + ", allocationGroupId=" + allocationGroupId
            + ", workPerformanceAdjustmentIndicator=" + workPerformanceAdjustmentIndicator
            + ", copyrightArrangementPercentage=" + copyrightArrangementPercentage + ", origAscapIntlIncmgDistDate="
            + origAscapIntlIncmgDistDate + ", workPerformanceActionCode=" + workPerformanceActionCode
            + ", nabtWriterRevenuePoolAmount=" + nabtWriterRevenuePoolAmount + ", nabtPublisherRevenuePoolAmount="
            + nabtPublisherRevenuePoolAmount + ", creditSymbol=" + creditSymbol + ", errorsAndWarnings="
            + errorsAndWarnings + ", workPerformanceDeleteFlag=" + workPerformanceDeleteFlag + ", errorFlag="
            + errorFlag + ", isUsedByDistribution=" + isUsedByDistribution + ", musicUserId=" + musicUserId
            + ", musicUserFirstName=" + musicUserFirstName + ", musicUserLastName=" + musicUserLastName
            + ", musicUserFullName=" + musicUserFullName + ", musicUserCallLetterSuffix=" + musicUserCallLetterSuffix
            + ", musicUserTypeCode=" + musicUserTypeCode + ", musicUserTypeDescription=" + musicUserTypeDescription
            + ", performanceStartDate=" + performanceStartDate + ", performanceStartTime=" + performanceStartTime
            + ", performanceEndDate=" + performanceEndDate + ", performanceEndTime=" + performanceEndTime
            + ", seriesCode=" + seriesCode + ", seriesTitle=" + seriesTitle + ", showProgramCode=" + showProgramCode
            + ", programTitle=" + programTitle + ", compensibilityStatusCode=" + compensibilityStatusCode
            + ", compensibilityStatusDescription=" + compensibilityStatusDescription + ", programNumber="
            + programNumber + ", licenseTypeCode=" + licenseTypeCode + ", licenseTypeDescription="
            + licenseTypeDescription + ", baseCredit=" + baseCredit + ", adjBaseCredit=" + adjBaseCredit
            + ", finalDistrCredit=" + finalDistrCredit + ", rfpCredit=" + rfpCredit + ", tvpCredit=" + tvpCredit
            + ", musicUserType=" + musicUserType + ", performanceDate=" + performanceDate + ", performanceDeleteFlag="
            + performanceDeleteFlag + ", sourceSystem=" + sourceSystem + ", sourceSystemDescription="
            + sourceSystemDescription + ", supplierDes=" + supplierDes + ", procStatus=" + procStatus
            + ", targetYearQuarter=" + targetYearQuarter + ", matchTypeCode=" + matchTypeCode + ", matchTypeDes="
            + matchTypeDes + ", fileId=" + fileId + ", providerId=" + providerId + ", supplierCode=" + supplierCode
            + ", statusDate=" + statusDate + ", onlineHeaderEditable=" + onlineHeaderEditable
            + ", onlineWorkPerfEditable=" + onlineWorkPerfEditable + ", workPerformanceShares=" + workPerformanceShares
            + ", weightsAndRulesApplied=" + weightsAndRulesApplied + ", isAnyAdjustmentTriggerCreated="
            + isAnyAdjustmentTriggerCreated + ", apmSourceSongKey=" + apmSourceSongKey + ", apmWorkEffectiveStartDate="
            + apmWorkEffectiveStartDate + ", apmWorkEffectiveEndDate=" + apmWorkEffectiveEndDate + ", apmLockIndicator="
            + apmLockIndicator + ", apmSendToManMatchIndicator=" + apmSendToManMatchIndicator + ", apmStatusDate="
            + apmStatusDate + ", apmBatchId=" + apmBatchId + ", apmEstimatedDollarVal=" + apmEstimatedDollarVal
            + ", apmEstimatedDollarFound=" + apmEstimatedDollarFound + ", apmChannel=" + apmChannel + ", apmAlbum="
            + apmAlbum + ", apmLabel=" + apmLabel + ", apmIsrc=" + apmIsrc + ", apmCatalogNumber=" + apmCatalogNumber
            + ", apmCatalogYear=" + apmCatalogYear + ", apmCompany=" + apmCompany + ", apmStation=" + apmStation
            + ", apmSupplierId=" + apmSupplierId + ", apmPerfDate=" + apmPerfDate + ", apmPerfTime=" + apmPerfTime
            + ", apmClassical=" + apmClassical + ", detectionTime=" + detectionTime + ", libraryDiscTitle="
            + libraryDiscTitle + ", libraryDiscId=" + libraryDiscId + ", confidenceLevel=" + confidenceLevel
            + ", writer=" + writer + ", libraryName=" + libraryName + ", libraryTrack=" + libraryTrack
            + ", libraryTrackId=" + libraryTrackId + ", assignedToUser=" + assignedToUser + ", performanceQuarter="
            + performanceQuarter + ", performancePeriod=" + performancePeriod + ", priority=" + priority + "]";
    }

    /**
     * Returns the allocationGroupId.
     * 
     * @return String
     */
    public String getAllocationGroupId() {
        return allocationGroupId;
    }

    /**
     * Returns the cueSheetSequenceNumber.
     * 
     * @return String
     */
    public String getCueSheetSequenceNumber() {
        return cueSheetSequenceNumber;
    }

    /**
     * Returns the distributionDate.
     * 
     * @return String
     */
    public String getDistributionDate() {
        return distributionDate;
    }

    /**
     * Returns the distributionId.
     * 
     * @return String
     */
    public String getDistributionId() {
        return distributionId;
    }

    /**
     * Returns the errorsAndWarnings.
     * 
     * @return Collection
     */
    public List<Object> getErrorsAndWarnings() {
        return errorsAndWarnings;
    }

    /**
     * Returns the instrumentalOrVocalIndicator.
     * 
     * @return String
     */
    public String getInstrumentalOrVocalIndicator() {
        return instrumentalOrVocalIndicator;
    }

    /**
     * Returns the instrumentationCode.
     * 
     * @return String
     */
    public String getInstrumentationCode() {
        return instrumentationCode;
    }

    /**
     * Returns the isCurrentVersion.
     * 
     * @return String
     */
    public String getIsCurrentVersion() {
        return isCurrentVersion;
    }

    /**
     * Returns the legacyProgramPerformanceId.
     * 
     * @return String
     */
    public String getLegacyProgramPerformanceId() {
        return legacyProgramPerformanceId;
    }

    /**
     * Returns the legacyWorkPerformanceId.
     * 
     * @return String
     */
    public String getLegacyWorkPerformanceId() {
        return legacyWorkPerformanceId;
    }

    /**
     * Returns the librarySurveyIndicator.
     * 
     * @return String
     */
    public String getLibrarySurveyIndicator() {
        return librarySurveyIndicator;
    }

    /**
     * Returns the licenseTypeCode.
     * 
     * @return String
     */
    public String getLicenseTypeCode() {
        return licenseTypeCode;
    }

    /**
     * Returns the licenseTypeDescription.
     * 
     * @return String
     */
    public String getLicenseTypeDescription() {
        return licenseTypeDescription;
    }

    /**
     * Returns the medleySequenceNumber.
     * 
     * @return String
     */
    public String getMedleySequenceNumber() {
        return medleySequenceNumber;
    }

    /**
     * Returns the musicUserCallLetterSuffix.
     * 
     * @return String
     */
    public String getMusicUserCallLetterSuffix() {
        return musicUserCallLetterSuffix;
    }

    /**
     * Returns the musicUserFirstName.
     * 
     * @return String
     */
    public String getMusicUserFirstName() {
        return musicUserFirstName;
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
     * Returns the musicUserLastName.
     * 
     * @return String
     */
    public String getMusicUserLastName() {
        return musicUserLastName;
    }

    /**
     * @return Returns the musicUserFullName.
     */
    public String getMusicUserFullName() {
        return musicUserFullName;
    }

    /**
     * @param musicUserFullName The musicUserFullName to set.
     */
    public void setMusicUserFullName(String musicUserFullName) {
        this.musicUserFullName = musicUserFullName;
    }

    /**
     * Returns the musicUserTypeCode.
     * 
     * @return String
     */
    public String getMusicUserTypeCode() {
        return musicUserTypeCode;
    }

    /**
     * Returns the musicUserTypeDescription.
     * 
     * @return String
     */
    public String getMusicUserTypeDescription() {
        return musicUserTypeDescription;
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
     * Returns the performerName.
     * 
     * @return String
     */
    public String getPerformerName() {
        return performerName;
    }

    /**
     * Returns the playCount.
     * 
     * @return String
     */
    public String getPlayCount() {
        return playCount;
    }

    /**
     * Returns the programCode.
     * 
     * @return String
     */
    public String getShowProgramCode() {
        return showProgramCode;
    }

    /**
     * Returns the programPerformanceHeaderId.
     * 
     * @return String
     */
    public String getPerformanceHeaderId() {
        return performanceHeaderId;
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
     * Returns the runcontrollId.
     * 
     * @return String
     */
    public String getRuncontrollId() {
        return runcontrollId;
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
     * Returns the sourceSystem.
     * 
     * @return String
     */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * Returns the sreCategory.
     * 
     * @return String
     */
    public String getSreCategory() {
        return sreCategory;
    }

    /**
     * Returns the sreDurationCode.
     * 
     * @return String
     */
    public String getSreDurationCode() {
        return sreDurationCode;
    }

    /**
     * Returns the srePointValue.
     * 
     * @return String
     */
    public String getSrePointValue() {
        return srePointValue;
    }

    /**
     * Returns the sreSubCategory.
     * 
     * @return String
     */
    public String getSreSubCategory() {
        return sreSubCategory;
    }

    /**
     * Returns the usageSelectionId.
     * 
     * @return String
     */
    public String getUsageSelectionId() {
        return usageSelectionId;
    }

    /**
     * Returns the useTypeCode.
     * 
     * @return String
     */
    public String getUseTypeCode() {
        return useTypeCode;
    }

    /**
     * Returns the useTypeDescription.
     * 
     * @return String
     */
    public String getUseTypeDescription() {
        return useTypeDescription;
    }

    /**
     * Returns the versionNumber.
     * 
     * @return String
     */
    public String getVersionNumber() {
        return versionNumber;
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
     * Returns the workPerformanceAdjustmentIndicator.
     * 
     * @return String
     */
    public String getWorkPerformanceAdjustmentIndicator() {
        return workPerformanceAdjustmentIndicator;
    }

    /**
     * Returns the workPerformanceDuration.
     * 
     * @return String
     */
    public String getWorkPerformanceDuration() {
        return workPerformanceDuration;
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
     * Returns the workSequenceNumber.
     * 
     * @return String
     */
    public String getWorkSequenceNumber() {
        return workSequenceNumber;
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
     * Sets the allocationGroupId.
     * 
     * @param allocationGroupId The allocationGroupId to set
     */
    public void setAllocationGroupId(String allocationGroupId) {
        this.allocationGroupId = allocationGroupId;
    }

    /**
     * Sets the cueSheetSequenceNumber.
     * 
     * @param cueSheetSequenceNumber The cueSheetSequenceNumber to set
     */
    public void setCueSheetSequenceNumber(String cueSheetSequenceNumber) {
        this.cueSheetSequenceNumber = cueSheetSequenceNumber;
    }

    /**
     * Sets the distributionDate.
     * 
     * @param distributionDate The distributionDate to set
     */
    public void setDistributionDate(String distributionDate) {
        this.distributionDate = distributionDate;
    }

    /**
     * Sets the distributionId.
     * 
     * @param distributionId The distributionId to set
     */
    public void setDistributionId(String distributionId) {
        this.distributionId = distributionId;
    }

    /**
     * Sets the errorsAndWarnings.
     * 
     * @param errorsAndWarnings The errorsAndWarnings to set
     */
    public void setErrorsAndWarnings(List<Object> errorsAndWarnings) {
        this.errorsAndWarnings = errorsAndWarnings;
    }

    /**
     * Sets the instrumentalOrVocalIndicator.
     * 
     * @param instrumentalOrVocalIndicator The instrumentalOrVocalIndicator to set
     */
    public void setInstrumentalOrVocalIndicator(String instrumentalOrVocalIndicator) {
        this.instrumentalOrVocalIndicator = instrumentalOrVocalIndicator;
    }

    /**
     * Sets the instrumentationCode.
     * 
     * @param instrumentationCode The instrumentationCode to set
     */
    public void setInstrumentationCode(String instrumentationCode) {
        this.instrumentationCode = instrumentationCode;
    }

    /**
     * Sets the isCurrentVersion.
     * 
     * @param isCurrentVersion The isCurrentVersion to set
     */
    public void setIsCurrentVersion(String isCurrentVersion) {
        this.isCurrentVersion = isCurrentVersion;
    }

    /**
     * Sets the legacyProgramPerformanceId.
     * 
     * @param legacyProgramPerformanceId The legacyProgramPerformanceId to set
     */
    public void setLegacyProgramPerformanceId(String legacyProgramPerformanceId) {
        this.legacyProgramPerformanceId = legacyProgramPerformanceId;
    }

    /**
     * Sets the legacyWorkPerformanceId.
     * 
     * @param legacyWorkPerformanceId The legacyWorkPerformanceId to set
     */
    public void setLegacyWorkPerformanceId(String legacyWorkPerformanceId) {
        this.legacyWorkPerformanceId = legacyWorkPerformanceId;
    }

    /**
     * Sets the librarySurveyIndicator.
     * 
     * @param librarySurveyIndicator The librarySurveyIndicator to set
     */
    public void setLibrarySurveyIndicator(String librarySurveyIndicator) {
        this.librarySurveyIndicator = librarySurveyIndicator;
    }

    /**
     * Sets the licenseTypeCode.
     * 
     * @param licenseTypeCode The licenseTypeCode to set
     */
    public void setLicenseTypeCode(String licenseTypeCode) {
        this.licenseTypeCode = licenseTypeCode;
    }

    /**
     * Sets the licenseTypeDescription.
     * 
     * @param licenseTypeDescription The licenseTypeDescription to set
     */
    public void setLicenseTypeDescription(String licenseTypeDescription) {
        this.licenseTypeDescription = licenseTypeDescription;
    }

    /**
     * Sets the medleySequenceNumber.
     * 
     * @param medleySequenceNumber The medleySequenceNumber to set
     */
    public void setMedleySequenceNumber(String medleySequenceNumber) {
        this.medleySequenceNumber = medleySequenceNumber;
    }

    /**
     * Sets the musicUserCallLetterSuffix.
     * 
     * @param musicUserCallLetterSuffix The musicUserCallLetterSuffix to set
     */
    public void setMusicUserCallLetterSuffix(String musicUserCallLetterSuffix) {
        this.musicUserCallLetterSuffix = musicUserCallLetterSuffix;
    }

    /**
     * Sets the musicUserFirstName.
     * 
     * @param musicUserFirstName The musicUserFirstName to set
     */
    public void setMusicUserFirstName(String musicUserFirstName) {
        this.musicUserFirstName = musicUserFirstName;
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
     * Sets the musicUserLastName.
     * 
     * @param musicUserLastName The musicUserLastName to set
     */
    public void setMusicUserLastName(String musicUserLastName) {
        this.musicUserLastName = musicUserLastName;
    }

    /**
     * Sets the musicUserTypeCode.
     * 
     * @param musicUserTypeCode The musicUserTypeCode to set
     */
    public void setMusicUserTypeCode(String musicUserTypeCode) {
        this.musicUserTypeCode = musicUserTypeCode;
    }

    /**
     * Sets the musicUserTypeDescription.
     * 
     * @param musicUserTypeDescription The musicUserTypeDescription to set
     */
    public void setMusicUserTypeDescription(String musicUserTypeDescription) {
        this.musicUserTypeDescription = musicUserTypeDescription;
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
     * Sets the performerName.
     * 
     * @param performerName The performerName to set
     */
    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    /**
     * Sets the playCount.
     * 
     * @param playCount The playCount to set
     */
    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    /**
     * Sets the programCode.
     * 
     * @param programCode The programCode to set
     */
    public void setShowProgramCode(String showProgramCode) {
        this.showProgramCode = showProgramCode;
    }

    /**
     * Sets the programPerformanceHeaderId.
     * 
     * @param programPerformanceId The programPerformanceHeaderId to set
     */
    public void setPerformanceHeaderId(String programPerformanceHeaderId) {
        this.performanceHeaderId = programPerformanceHeaderId;
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
     * Sets the runcontrollId.
     * 
     * @param runcontrollId The runcontrollId to set
     */
    public void setRuncontrollId(String runcontrollId) {
        this.runcontrollId = runcontrollId;
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
     * Sets the sourceSystem.
     * 
     * @param sourceSystem The sourceSystem to set
     */
    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    /**
     * Sets the sreCategory.
     * 
     * @param sreCategory The sreCategory to set
     */
    public void setSreCategory(String sreCategory) {
        this.sreCategory = sreCategory;
    }

    /**
     * Sets the sreDurationCode.
     * 
     * @param sreDurationCode The sreDurationCode to set
     */
    public void setSreDurationCode(String sreDurationCode) {
        this.sreDurationCode = sreDurationCode;
    }

    /**
     * Sets the srePointValue.
     * 
     * @param srePointValue The srePointValue to set
     */
    public void setSrePointValue(String srePointValue) {
        this.srePointValue = srePointValue;
    }

    /**
     * Sets the sreSubCategory.
     * 
     * @param sreSubCategory The sreSubCategory to set
     */
    public void setSreSubCategory(String sreSubCategory) {
        this.sreSubCategory = sreSubCategory;
    }

    /**
     * Sets the usageSelectionId.
     * 
     * @param usageSelectionId The usageSelectionId to set
     */
    public void setUsageSelectionId(String usageSelectionId) {
        this.usageSelectionId = usageSelectionId;
    }

    /**
     * Sets the useTypeCode.
     * 
     * @param useTypeCode The useTypeCode to set
     */
    public void setUseTypeCode(String useTypeCode) {
        this.useTypeCode = useTypeCode;
    }

    /**
     * Sets the useTypeDescription.
     * 
     * @param useTypeDescription The useTypeDescription to set
     */
    public void setUseTypeDescription(String useTypeDescription) {
        this.useTypeDescription = useTypeDescription;
    }

    /**
     * Sets the versionNumber.
     * 
     * @param versionNumber The versionNumber to set
     */
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
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
     * Sets the workPerformanceAdjustmentIndicator.
     * 
     * @param workPerformanceAdjustmentIndicator The workPerformanceAdjustmentIndicator to set
     */
    public void setWorkPerformanceAdjustmentIndicator(String workPerformanceAdjustmentIndicator) {
        this.workPerformanceAdjustmentIndicator = workPerformanceAdjustmentIndicator;
    }

    /**
     * Sets the workPerformanceDuration.
     * 
     * @param workPerformanceDuration The workPerformanceDuration to set
     */
    public void setWorkPerformanceDuration(String workPerformanceDuration) {
        this.workPerformanceDuration = workPerformanceDuration;
        try {
            this.durationDisplayString =
                DateUtils.getDurationString(Long.parseLong(workPerformanceDuration), DateUtils.DUR_UNIT_SECONDS);
        } catch (Exception e) {
            this.durationDisplayString = "";
        }
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
     * Sets the workSequenceNumber.
     * 
     * @param workSequenceNumber The workSequenceNumber to set
     */
    public void setWorkSequenceNumber(String workSequenceNumber) {
        this.workSequenceNumber = workSequenceNumber;
    }

    /**
     * Sets the workTitle.
     * 
     * @param workTitle The workTitle to set
     */
    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    /**
     * Returns the copyrightArrangementPercentage.
     * 
     * @return String
     */
    public String getCopyrightArrangementPercentage() {
        return copyrightArrangementPercentage;
    }

    /**
     * Returns the creditSymbol.
     * 
     * @return String
     */
    public String getCreditSymbol() {
        return creditSymbol;
    }

    /**
     * Returns the nabtPublisherRevenuePoolAmount.
     * 
     * @return String
     */
    public String getNabtPublisherRevenuePoolAmount() {
        return nabtPublisherRevenuePoolAmount;
    }

    /**
     * Returns the nabtWriterRevenuePoolAmount.
     * 
     * @return String
     */
    public String getNabtWriterRevenuePoolAmount() {
        return nabtWriterRevenuePoolAmount;
    }

    /**
     * Returns the workPerformanceActionCode.
     * 
     * @return String
     */
    public String getWorkPerformanceActionCode() {
        return workPerformanceActionCode;
    }

    /**
     * Sets the copyrightArrangementPercentage.
     * 
     * @param copyrightArrangementPercentage The copyrightArrangementPercentage to set
     */
    public void setCopyrightArrangementPercentage(String copyrightArrangementPercentage) {
        this.copyrightArrangementPercentage = copyrightArrangementPercentage;
    }

    /**
     * Sets the creditSymbol.
     * 
     * @param creditSymbol The creditSymbol to set
     */
    public void setCreditSymbol(String creditSymbol) {
        this.creditSymbol = creditSymbol;
    }

    /**
     * Sets the nabtPublisherRevenuePoolAmount.
     * 
     * @param nabtPublisherRevenuePoolAmount The nabtPublisherRevenuePoolAmount to set
     */
    public void setNabtPublisherRevenuePoolAmount(String nabtPublisherRevenuePoolAmount) {
        this.nabtPublisherRevenuePoolAmount = nabtPublisherRevenuePoolAmount;
    }

    /**
     * Sets the nabtWriterRevenuePoolAmount.
     * 
     * @param nabtWriterRevenuePoolAmount The nabtWriterRevenuePoolAmount to set
     */
    public void setNabtWriterRevenuePoolAmount(String nabtWriterRevenuePoolAmount) {
        this.nabtWriterRevenuePoolAmount = nabtWriterRevenuePoolAmount;
    }

    /**
     * Sets the workPerformanceActionCode.
     * 
     * @param workPerformanceActionCode The workPerformanceActionCode to set
     */
    public void setWorkPerformanceActionCode(String workPerformanceActionCode) {
        this.workPerformanceActionCode = workPerformanceActionCode;
    }

    /**
     * Returns the origAscapIntlIncmgDistDate.
     * 
     * @return String
     */
    public String getOrigAscapIntlIncmgDistDate() {
        return origAscapIntlIncmgDistDate;
    }

    /**
     * Sets the origAscapIntlIncmgDistDate.
     * 
     * @param origAscapIntlIncmgDistDate The origAscapIntlIncmgDistDate to set
     */
    public void setOrigAscapIntlIncmgDistDate(String origAscapIntlIncmgDistDate) {
        this.origAscapIntlIncmgDistDate = origAscapIntlIncmgDistDate;
    }

    /**
     * Returns the durationDisplayString.
     * 
     * @return String
     */
    public String getDurationDisplayString() {
        return durationDisplayString;
    }

    /**
     * Sets the durationDisplayString.
     * 
     * @param durationDisplayString The durationDisplayString to set
     */
    public void setDurationDisplayString(String durationDisplayString) {
        this.durationDisplayString = durationDisplayString;
    }

    /**
     * Returns the workPerformanceDeleteFlag.
     * 
     * @return String
     */
    public String getWorkPerformanceDeleteFlag() {
        return workPerformanceDeleteFlag;
    }

    /**
     * Sets the workPerformanceDeleteFlag.
     * 
     * @param deleteFlag The workPerformanceDeleteFlag to set
     */
    public void setWorkPerformanceDeleteFlag(String deleteFlag) {
        this.workPerformanceDeleteFlag = deleteFlag;
    }

    /**
     * Returns the programNumber.
     * 
     * @return String
     */
    public String getProgramNumber() {
        return programNumber;
    }

    /**
     * Sets the programNumber.
     * 
     * @param programNumber The programNumber to set
     */
    public void setProgramNumber(String programNumber) {
        this.programNumber = programNumber;
    }

    /**
     * Returns the adjBaseCredit.
     * 
     * @return String
     */
    public String getAdjBaseCredit() {
        return adjBaseCredit;
    }

    /**
     * Returns the baseCredit.
     * 
     * @return String
     */
    public String getBaseCredit() {
        return baseCredit;
    }

    /**
     * Returns the errorFlag.
     * 
     * @return String
     */
    public String getErrorFlag() {
        return errorFlag;
    }

    /**
     * Returns the finalDistrCredit.
     * 
     * @return String
     */
    public String getFinalDistrCredit() {
        return finalDistrCredit;
    }

    /**
     * Returns the musicUserType.
     * 
     * @return String
     */
    public String getMusicUserType() {
        return musicUserType;
    }

    /**
     * Returns the performanceDate.
     * 
     * @return String
     */
    public String getPerformanceDate() {
        return performanceDate;
    }

    /**
     * Returns the rfpCredit.
     * 
     * @return String
     */
    public String getRfpCredit() {
        return rfpCredit;
    }

    /**
     * Returns the tvpCredit.
     * 
     * @return String
     */
    public String getTvpCredit() {
        return tvpCredit;
    }

    /**
     * Sets the adjBaseCredit.
     * 
     * @param adjBaseCredit The adjBaseCredit to set
     */
    public void setAdjBaseCredit(String adjBaseCredit) {
        this.adjBaseCredit = adjBaseCredit;
    }

    /**
     * Sets the baseCredit.
     * 
     * @param baseCredit The baseCredit to set
     */
    public void setBaseCredit(String baseCredit) {
        this.baseCredit = baseCredit;
    }

    /**
     * Sets the errorFlag.
     * 
     * @param errorFlag The errorFlag to set
     */
    public void setErrorFlag(String errorFlag) {
        this.errorFlag = errorFlag;
    }

    /**
     * Sets the finalDistrCredit.
     * 
     * @param finalDistrCredit The finalDistrCredit to set
     */
    public void setFinalDistrCredit(String finalDistrCredit) {
        this.finalDistrCredit = finalDistrCredit;
    }

    /**
     * Sets the musicUserType.
     * 
     * @param musicUserType The musicUserType to set
     */
    public void setMusicUserType(String musicUserType) {
        this.musicUserType = musicUserType;
    }

    /**
     * Sets the performanceDate.
     * 
     * @param performanceDate The performanceDate to set
     */
    public void setPerformanceDate(String performanceDate) {
        this.performanceDate = performanceDate;
    }

    /**
     * Sets the rfpCredit.
     * 
     * @param rfpCredit The rfpCredit to set
     */
    public void setRfpCredit(String rfpCredit) {
        this.rfpCredit = rfpCredit;
    }

    /**
     * Sets the tvpCredit.
     * 
     * @param tvpCredit The tvpCredit to set
     */
    public void setTvpCredit(String tvpCredit) {
        this.tvpCredit = tvpCredit;
    }

    /**
     * Returns the workPerformanceShares.
     * 
     * @return Collection
     */
    public List<Object> getWorkPerformanceShares() {
        return workPerformanceShares;
    }

    /**
     * Sets the workPerformanceShares.
     * 
     * @param workPerformanceShares The workPerformanceShares to set
     */
    public void setWorkPerformanceShares(List<Object> workPerformanceShares) {
        this.workPerformanceShares = workPerformanceShares;
    }

    /**
     * Returns the weightsAndRulesApplied.
     * 
     * @return WeightsAndRulesApplied
     */
    public WeightsAndRulesApplied getWeightsAndRulesApplied() {
        return weightsAndRulesApplied;
    }

    /**
     * Sets the weightsAndRulesApplied.
     * 
     * @param weightsAndRulesApplied The weightsAndRulesApplied to set
     */
    public void setWeightsAndRulesApplied(WeightsAndRulesApplied weightsAndRulesApplied) {
        this.weightsAndRulesApplied = weightsAndRulesApplied;
    }

    /**
     * @return Returns the isAnyAdjustmentTriggerCreated.
     */
    public boolean getIsAnyAdjustmentTriggerCreated() {
        return isAnyAdjustmentTriggerCreated;
    }

    /**
     * @param isAnyAdjustmentTriggerCreated The isAnyAdjustmentTriggerCreated to set.
     */
    public void setIsAnyAdjustmentTriggerCreated(boolean isAnyAdjustmentTriggerCreated) {
        this.isAnyAdjustmentTriggerCreated = isAnyAdjustmentTriggerCreated;
    }

    /**
     * @return Returns the compensibilityStatusCode.
     */
    public String getCompensibilityStatusCode() {
        return compensibilityStatusCode;
    }

    /**
     * @param compensibilityStatusCode The compensibilityStatusCode to set.
     */
    public void setCompensibilityStatusCode(String compensibilityStatusCode) {
        this.compensibilityStatusCode = compensibilityStatusCode;
    }

    /**
     * @return Returns the compensibilityStatusDescription.
     */
    public String getCompensibilityStatusDescription() {
        return compensibilityStatusDescription;
    }

    /**
     * @param compensibilityStatusDescription The compensibilityStatusDescription to set.
     */
    public void setCompensibilityStatusDescription(String compensibilityStatusDescription) {
        this.compensibilityStatusDescription = compensibilityStatusDescription;
    }

    /**
     * @return Returns the performanceHeaderIsCurrentVersion.
     */
    public String getPerformanceHeaderIsCurrentVersion() {
        return performanceHeaderIsCurrentVersion;
    }

    /**
     * @param performanceHeaderIsCurrentVersion The performanceHeaderIsCurrentVersion to set.
     */
    public void setPerformanceHeaderIsCurrentVersion(String performanceHeaderIsCurrentVersion) {
        this.performanceHeaderIsCurrentVersion = performanceHeaderIsCurrentVersion;
    }

    /**
     * @return Returns the performanceHeaderVersionNumber.
     */
    public String getPerformanceHeaderVersionNumber() {
        return performanceHeaderVersionNumber;
    }

    /**
     * @param performanceHeaderVersionNumber The performanceHeaderVersionNumber to set.
     */
    public void setPerformanceHeaderVersionNumber(String performanceHeaderVersionNumber) {
        this.performanceHeaderVersionNumber = performanceHeaderVersionNumber;
    }

    /**
     * @return Returns the sourceSystemDescription.
     */
    public String getSourceSystemDescription() {
        return sourceSystemDescription;
    }

    /**
     * @param sourceSystemDescription The sourceSystemDescription to set.
     */
    public void setSourceSystemDescription(String sourceSystemDescription) {
        this.sourceSystemDescription = sourceSystemDescription;
    }

    /**
     * @return Returns the isUsedByDistribution.
     */
    public String getIsUsedByDistribution() {
        return isUsedByDistribution;
    }

    /**
     * @param isUsedByDistribution The isUsedByDistribution to set.
     */
    public void setIsUsedByDistribution(String isUsedByDistribution) {
        this.isUsedByDistribution = isUsedByDistribution;
    }

    /**
     * @return Returns the smcCode.
     */
    public String getSmcCode() {
        return smcCode;
    }

    /**
     * @param smcCode The smcCode to set.
     */
    public void setSmcCode(String smcCode) {
        this.smcCode = smcCode;
    }

    /**
     * @return Returns the workPerformanceDurationInMinutes.
     */
    public String getWorkPerformanceDurationInMinutes() {
        return workPerformanceDurationInMinutes;
    }

    /**
     * @param workPerformanceDurationInMinutes The workPerformanceDurationInMinutes to set.
     */
    public void setWorkPerformanceDurationInMinutes(String workPerformanceDurationInMinutes) {
        this.workPerformanceDurationInMinutes = workPerformanceDurationInMinutes;
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

    /**
     * @return the supplierDes
     */
    public String getSupplierDes() {
        return supplierDes;
    }

    /**
     * @param supplierDes the supplierDes to set
     */
    public void setSupplierDes(String supplierDes) {
        this.supplierDes = supplierDes;
    }

    /**
     * @return the performanceDeleteFlag
     */
    public String getPerformanceDeleteFlag() {
        return performanceDeleteFlag;
    }

    /**
     * @param performanceDeleteFlag the performanceDeleteFlag to set
     */
    public void setPerformanceDeleteFlag(String performanceDeleteFlag) {
        this.performanceDeleteFlag = performanceDeleteFlag;
    }

    public String getLegacyMusicUserId() {
        return legacyMusicUserId;
    }

    public void setLegacyMusicUserId(String legacyMusicUserId) {
        this.legacyMusicUserId = legacyMusicUserId;
    }

    public String getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }

    public String getTargetYearQuarter() {
        return targetYearQuarter;
    }

    public void setTargetYearQuarter(String targetYearQuarter) {
        this.targetYearQuarter = targetYearQuarter;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getMatchTypeCode() {
        return matchTypeCode;
    }

    public void setMatchTypeCode(String matchTypeCode) {
        this.matchTypeCode = matchTypeCode;
    }

    public String getMatchTypeDes() {
        return matchTypeDes;
    }

    public void setMatchTypeDes(String matchTypeDes) {
        this.matchTypeDes = matchTypeDes;
    }

    public String getOnlineHeaderEditable() {
        return onlineHeaderEditable;
    }

    public void setOnlineHeaderEditable(String onlineHeaderEditable) {
        this.onlineHeaderEditable = onlineHeaderEditable;
    }

    public String getOnlineWorkPerfEditable() {
        return onlineWorkPerfEditable;
    }

    public void setOnlineWorkPerfEditable(String onlineWorkPerfEditable) {
        this.onlineWorkPerfEditable = onlineWorkPerfEditable;
    }

    public String getApmSourceSongKey() {
        return apmSourceSongKey;
    }

    public void setApmSourceSongKey(String apmSourceSongKey) {
        this.apmSourceSongKey = apmSourceSongKey;
    }

    public String getApmWorkEffectiveStartDate() {
        return apmWorkEffectiveStartDate;
    }

    public void setApmWorkEffectiveStartDate(String apmWorkEffectiveStartDate) {
        this.apmWorkEffectiveStartDate = apmWorkEffectiveStartDate;
    }

    public String getApmWorkEffectiveEndDate() {
        return apmWorkEffectiveEndDate;
    }

    public void setApmWorkEffectiveEndDate(String apmWorkEffectiveEndDate) {
        this.apmWorkEffectiveEndDate = apmWorkEffectiveEndDate;
    }

    public String getApmLockIndicator() {
        return apmLockIndicator;
    }

    public void setApmLockIndicator(String apmLockIndicator) {
        this.apmLockIndicator = apmLockIndicator;
    }

    public String getApmSendToManMatchIndicator() {
        return apmSendToManMatchIndicator;
    }

    public void setApmSendToManMatchIndicator(String apmSendToManMatchIndicator) {
        this.apmSendToManMatchIndicator = apmSendToManMatchIndicator;
    }

    public String getApmStatusDate() {
        return apmStatusDate;
    }

    public void setApmStatusDate(String apmStatusDate) {
        this.apmStatusDate = apmStatusDate;
    }

    public String getApmBatchId() {
        return apmBatchId;
    }

    public void setApmBatchId(String apmBatchId) {
        this.apmBatchId = apmBatchId;
    }

    public String getApmEstimatedDollarVal() {
        return apmEstimatedDollarVal;
    }

    public void setApmEstimatedDollarVal(String apmEstimatedDollarVal) {
        this.apmEstimatedDollarVal = apmEstimatedDollarVal;
    }

    public String getApmEstimatedDollarFound() {
        return apmEstimatedDollarFound;
    }

    public void setApmEstimatedDollarFound(String apmEstimatedDollarFound) {
        this.apmEstimatedDollarFound = apmEstimatedDollarFound;
    }

    public String getApmChannel() {
        return apmChannel;
    }

    public void setApmChannel(String apmChannel) {
        this.apmChannel = apmChannel;
    }

    public String getApmAlbum() {
        return apmAlbum;
    }

    public void setApmAlbum(String apmAlbum) {
        this.apmAlbum = apmAlbum;
    }

    public String getApmLabel() {
        return apmLabel;
    }

    public void setApmLabel(String apmLabel) {
        this.apmLabel = apmLabel;
    }

    public String getApmIsrc() {
        return apmIsrc;
    }

    public void setApmIsrc(String apmIsrc) {
        this.apmIsrc = apmIsrc;
    }

    public String getApmCatalogNumber() {
        return apmCatalogNumber;
    }

    public void setApmCatalogNumber(String apmCatalogNumber) {
        this.apmCatalogNumber = apmCatalogNumber;
    }

    public String getApmCatalogYear() {
        return apmCatalogYear;
    }

    public void setApmCatalogYear(String apmCatalogYear) {
        this.apmCatalogYear = apmCatalogYear;
    }

    public String getApmCompany() {
        return apmCompany;
    }

    public void setApmCompany(String apmCompany) {
        this.apmCompany = apmCompany;
    }

    public String getApmStation() {
        return apmStation;
    }

    public void setApmStation(String apmStation) {
        this.apmStation = apmStation;
    }

    public String getApmSupplierId() {
        return apmSupplierId;
    }

    public void setApmSupplierId(String apmSupplierId) {
        this.apmSupplierId = apmSupplierId;
    }

    public String getApmPerfDate() {
        return apmPerfDate;
    }

    public void setApmPerfDate(String apmPerfDate) {
        this.apmPerfDate = apmPerfDate;
    }

    public String getApmPerfTime() {
        return apmPerfTime;
    }

    public void setApmPerfTime(String apmPerfTime) {
        this.apmPerfTime = apmPerfTime;
    }

    public String getApmClassical() {
        return apmClassical;
    }

    public void setApmClassical(String apmClassical) {
        this.apmClassical = apmClassical;
    }

    public String getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(String detectionTime) {
        this.detectionTime = detectionTime;
    }

    public String getLibraryDiscTitle() {
        return libraryDiscTitle;
    }

    public void setLibraryDiscTitle(String libraryDiscTitle) {
        this.libraryDiscTitle = libraryDiscTitle;
    }

    public String getLibraryDiscId() {
        return libraryDiscId;
    }

    public void setLibraryDiscId(String libraryDiscId) {
        this.libraryDiscId = libraryDiscId;
    }

    public String getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(String confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryTrack() {
        return libraryTrack;
    }

    public void setLibraryTrack(String libraryTrack) {
        this.libraryTrack = libraryTrack;
    }

    public String getLibraryTrackId() {
        return libraryTrackId;
    }

    public void setLibraryTrackId(String libraryTrackId) {
        this.libraryTrackId = libraryTrackId;
    }

    public String getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(String assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    public String getPerformanceQuarter() {
        return performanceQuarter;
    }

    public void setPerformanceQuarter(String performanceQuarter) {
        this.performanceQuarter = performanceQuarter;
    }

    public String getPerformancePeriod() {
        return performancePeriod;
    }

    public void setPerformancePeriod(String performancePeriod) {
        this.performancePeriod = performancePeriod;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getActionSelected() {
        return actionSelected;
    }

    public void setActionSelected(String actionSelected) {
        this.actionSelected = actionSelected;
    }

    public String getMusicUserWeight() {
        return musicUserWeight;
    }

    public void setMusicUserWeight(String musicUserWeight) {
        this.musicUserWeight = musicUserWeight;
    }

    public String getLivePopEconomicComponent() {
        return livePopEconomicComponent;
    }

    public void setLivePopEconomicComponent(String livePopEconomicComponent) {
        this.livePopEconomicComponent = livePopEconomicComponent;
    }

    public String getLivePopMusicDensityComponent() {
        return livePopMusicDensityComponent;
    }

    public void setLivePopMusicDensityComponent(String livePopMusicDensityComponent) {
        this.livePopMusicDensityComponent = livePopMusicDensityComponent;
    }

    public String getCreditsPerSitePerPlay() {
        return creditsPerSitePerPlay;
    }

    public void setCreditsPerSitePerPlay(String creditsPerSitePerPlay) {
        this.creditsPerSitePerPlay = creditsPerSitePerPlay;
    }

    public String getPointsForTitle() {
        return pointsForTitle;
    }

    public void setPointsForTitle(String pointsForTitle) {
        this.pointsForTitle = pointsForTitle;
    }

    public String getUseWeight() {
        return useWeight;
    }

    public void setUseWeight(String useWeight) {
        this.useWeight = useWeight;
    }

    public String getUseWeightRuleId() {
        return useWeightRuleId;
    }

    public void setUseWeightRuleId(String useWeightRuleId) {
        this.useWeightRuleId = useWeightRuleId;
    }

    public String getCreditSymbolForUseWeight() {
        return creditSymbolForUseWeight;
    }

    public void setCreditSymbolForUseWeight(String creditSymbolForUseWeight) {
        this.creditSymbolForUseWeight = creditSymbolForUseWeight;
    }

    public String getTimeOfDayWeight() {
        return timeOfDayWeight;
    }

    public void setTimeOfDayWeight(String timeOfDayWeight) {
        this.timeOfDayWeight = timeOfDayWeight;
    }

    public String getPerSetCode() {
        return perSetCode;
    }

    public void setPerSetCode(String perSetCode) {
        this.perSetCode = perSetCode;
    }

    public String getDbWorkTitle() {
        return dbWorkTitle;
    }

    public void setDbWorkTitle(String dbWorkTitle) {
        this.dbWorkTitle = dbWorkTitle;
    }

    public String getDbPerformerName() {
        return dbPerformerName;
    }

    public void setDbPerformerName(String dbPerformerName) {
        this.dbPerformerName = dbPerformerName;
    }

    public String getProgramPerformanceHeaderId() {
        return programPerformanceHeaderId;
    }

    public void setProgramPerformanceHeaderId(String programPerformanceHeaderId) {
        this.programPerformanceHeaderId = programPerformanceHeaderId;
    }

    public String[] getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(String[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    public String[] getErrorTypes() {
        return errorTypes;
    }

    public void setErrorTypes(String[] errorTypes) {
        this.errorTypes = errorTypes;
    }

    public String[] getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String[] errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getMusicUserInformationMode() {
        return musicUserInformationMode;
    }

    public void setMusicUserInformationMode(String musicUserInformationMode) {
        this.musicUserInformationMode = musicUserInformationMode;
    }

    public List<String> validate(MessageSource messageSources) {
        List<String> errorsColl = new ArrayList<>();
        if (UsageCommonValidations.areBothEqualIgnoreCase("ADD_NEW_WORK_PERFORMANCE", this.actionSelected)) {
            return errorsColl;
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getCueSheetSequenceNumber())) {
            // Nothing to do
        } else {
            try {
                Long.parseLong(this.getCueSheetSequenceNumber());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.workPerformance.invalid.cueSheetSequenceNumber",
                    null, Locale.getDefault()));
            }
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getWorkId())) {
            // Nothing to do
        } else {
            try {
                Long.parseLong(this.getWorkId());
            } catch (NumberFormatException ne) {
                errorsColl.add(
                    messageSources.getMessage("error.usage.workPerformance.invalid.workId", null, Locale.getDefault()));
            }
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getUseTypeCode())) {
            errorsColl.add(messageSources.getMessage("error.usage.workPerformance.required.useTypeCode", null,
                Locale.getDefault()));
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getPlayCount())) {
            // Nothing to do
        } else {
            try {
                Long.parseLong(this.getPlayCount());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.workPerformance.invalid.playCount", null,
                    Locale.getDefault()));
            }
        }

        if (PerformanceMusicUserUIGroups.isMusicUserTypeMemberOftheUIGroup(
            PerformanceMusicUserUIGroups.SRE_PERF_UI_GROUP, this.getMusicUserTypeCode())) {

            if (UsageCommonValidations.isEmptyOrNull(this.getWorkPerformanceDurationInMinutes())) {
                this.setWorkPerformanceDuration(null);
            } else {
                try {
                    Long.parseLong(this.getWorkPerformanceDurationInMinutes());
                    this.setWorkPerformanceDuration(
                        String.valueOf(Long.parseLong(this.getWorkPerformanceDurationInMinutes()) * 60));
                } catch (NumberFormatException ne) {
                    errorsColl.add(messageSources.getMessage(
                        "error.usage.workPerformance.invalid.workPerformanceDuration", null, Locale.getDefault()));
                    this.setWorkPerformanceDuration(null);
                }
            }

            if (UsageCommonValidations.isEmptyOrNull(this.getSmcCode())) {
                errorsColl.add(messageSources.getMessage("error.usage.workPerformance.required.smcCode", null,
                    Locale.getDefault()));
                this.setSreCategory(null);
                this.setSreSubCategory(null);
            } else {
                if (this.getSmcCode().length() == 1) {
                    if (UsageConstants.SRE_CATEGORY_CODE_A.equalsIgnoreCase(this.getSmcCode())) {
                        this.setSreCategory(this.getSmcCode());
                        this.setSreSubCategory(null);
                    } else {
                        // Should Never Happen
                        this.setSreCategory(null);
                        this.setSreSubCategory(null);
                    }
                } else if (this.getSmcCode().length() == 2) {
                    this.setSreCategory(this.getSmcCode().substring(0, 1));
                    this.setSreSubCategory(this.getSmcCode().substring(1));
                } else {
                    // should never happen
                    this.setSreCategory(null);
                    this.setSreSubCategory(null);
                }
            }
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getWorkPerformanceDuration())) {
            // Nothing to do
        } else {
            try {
                Long.parseLong(this.getWorkPerformanceDuration());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.workPerformance.invalid.workPerformanceDuration",
                    null, Locale.getDefault()));
            }
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getCopyrightArrangementPercentage())) {
            // Nothing to do
        } else {
            try {
                Double.parseDouble(this.getCopyrightArrangementPercentage());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage(
                    "error.usage.workPerformance.invalid.copyrightArrangementPercentage", null, Locale.getDefault()));
            }
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getNabtWriterRevenuePoolAmount())) {
            // Nothing to do
        } else {
            try {
                Double.parseDouble(this.getNabtWriterRevenuePoolAmount());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage(
                    "error.usage.workPerformance.invalid.nabtWriterRevenuePoolAmount", null, Locale.getDefault()));
            }
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getNabtPublisherRevenuePoolAmount())) {
            // Nothing to do
        } else {
            try {
                Double.parseDouble(this.getNabtPublisherRevenuePoolAmount());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage(
                    "error.usage.workPerformance.invalid.nabtPublisherRevenuePoolAmount", null, Locale.getDefault()));
            }
        }

        String originalWorkTitle = null;
        boolean doesTitleHasCue = false;

        Map<Object, Serializable> featuredUseTypesMap = null;

        try {
            featuredUseTypesMap = com.ascap.apm.common.utils.cache.lookup.LookupTables
                .getLookupTableItemsWithKeys("UseTypes_IS_FEATURED");
        } catch (InvalidCacheException e) {
            // Nothing can be done right now
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getWorkTitle())) {
            originalWorkTitle = this.getWorkTitle();

            originalWorkTitle = originalWorkTitle.toUpperCase();
            originalWorkTitle = originalWorkTitle.trim();
            this.setWorkTitle(originalWorkTitle);
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getPerformerName())) {
            this.setPerformerName(this.getPerformerName().toUpperCase().trim());
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getWriter())) {
            this.setWriter(this.getWriter().toUpperCase().trim());
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getDetectionTime())) {
            this.setDetectionTime(this.getDetectionTime().toUpperCase().trim());
        }

        String musicUserTypeCodePartOfALLgroup = null;
        if (!UsageCommonValidations.isEmptyOrNull(this.getMusicUserTypeCode())) {
            try {
                musicUserTypeCodePartOfALLgroup = LookupTables.getLookupTableItemDescription(
                    UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_ALL, this.getMusicUserTypeCode());
            } catch (InvalidCacheException | KeyNotFoundException e) {
                // Nothing to do
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(musicUserTypeCodePartOfALLgroup)
            && (featuredUseTypesMap != null && (!UsageCommonValidations.isEmptyOrNull(this.useTypeCode)))
            && (featuredUseTypesMap.containsKey(this.getUseTypeCode()))) {
            if (!UsageCommonValidations.isEmptyOrNull(this.getWorkTitle())) {
                originalWorkTitle = this.getWorkTitle();
                originalWorkTitle = originalWorkTitle.toUpperCase();
                originalWorkTitle = originalWorkTitle.trim();
                if (originalWorkTitle.endsWith(" CUE") || originalWorkTitle.endsWith(" CUES")) {
                    doesTitleHasCue = true;
                }
            }

            if (doesTitleHasCue) {
                errorsColl.add(messageSources.getMessage("error.usage.performance.businessRules.all.warning.20111",
                    null, Locale.getDefault()));
            }
        }

        if (UsageConstants.APM_MATCH_TYPE_PENDING.equals(this.getMatchTypeCode())
            && (!("2".equals(this.getErrorFlag()) || ValidationUtils.isEmptyOrNull(this.getDbWorkTitle())
                || ValidationUtils.isEmptyOrNull(this.getDbPerformerName())))) {
            errorsColl = new ArrayList<>();
            errorsColl.add(messageSources.getMessage("error.usage.workPerformance.update.invalid.pnedingbatchmatch",
                null, Locale.getDefault()));
        }

        if (errorsColl != null && !errorsColl.isEmpty()) {
            List<Object> errorCol = new ArrayList<>();
            PerformanceMessage pm = null;
            if (this.getErrorCodes() != null) {
                for (int i = 0; i < this.getErrorCodes().length; i++) {
                    pm = new PerformanceMessage();
                    pm.setMessageCategoryCode(this.getErrorTypes()[i]);
                    pm.setMessageCode(this.getErrorCodes()[i]);
                    pm.setMessageDescription(this.getErrorMessages()[i]);
                    errorCol.add(pm);
                }
            }
            this.setErrorsAndWarnings(errorCol);
        }
        return errorsColl;
    }

}
