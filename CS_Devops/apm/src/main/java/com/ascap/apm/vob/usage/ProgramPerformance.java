//Source file: C:\\Program Files\\Rational\\RUPBuilder\\PREPBuild\\com\\ascap\\prep\\common\\vob\\usage\\ProgramPerformance.java

package com.ascap.apm.vob.usage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;

import com.ascap.apm.common.utils.UsageCommonValidations;
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.vob.BaseMultiVOB;

/**
 * Holds Program Performance Fields
 * 
 * @author Manoj Puli
 * @version $Revision: 1.16 $ $Date: May 05 2011 12:34:24 $
 */

public class ProgramPerformance extends BaseMultiVOB {

    private static final long serialVersionUID = -4567480460995013712L;

    private String headerIdCurrent;

    private String headerIdNext;

    private String headerIdPrev;

    private String headerIdCurrentRowNum;

    private String performanceHeaderId = null;

    private String versionNumber = null;

    private String isCurrentVersion = null;

    private String isUsedByDistribution = null;

    private boolean isAnyAdjustmentTriggerCreated = false;

    private String sourceSystem = null;

    private String legacyProgramPerformanceId = null;

    private String legacyMusicUserId = null;

    private String legacyMusicUserIdTypeCode = null;

    private String supplierCode = null;

    private String supplierDes = null;

    private String proxyRequestId = null;

    // There are 3 properties now that have Music User Call Letter and Music User
    // Call Letter Suffix seperately as
    // musicUserCallLetterOnly, musicUserCallLetterSuffixOnly And both concatenated
    // in one property called
    // musicUserCallLtterSuffix.
    private String musicUserCallLetterOnly = null;

    private String musicUserCallLetterSuffixOnly = null;

    private String musicUserId = null;

    private String musicUserTypeCode = null;

    private String licenseTypeCode = null;

    private String performanceStartDate = null;

    private String performanceStartTime = null;

    private String performanceEndDate = null;

    private String performanceEndTime = null;

    private String programOverlapCode = null;

    private String duration = null;

    private String surveyTypeCode = null;

    private String sampleTypeCode = null;

    private String segmentNumber = null;

    private String numberOfHooks = null;

    private String programNumberOfHooks = null;

    private String performerName = null;

    private String seriesCode = null;

    private String seriesTitle = null;

    private String showProgramCode = null;

    private String programTitle = null;

    private String compensibilityStatusCode = null;

    private String compensibilityStatusDescription = null;

    private String programNumber = null;

    private String ascapPerProgramRevenueAmount = null;

    private String pprValue = null;

    private String payPerViewAmount = null;

    private String sreNumberOfPlays = null;

    private String sreVenue = null;

    private String featuredPerformer = null;

    private String playListFlag = null;

    private String headlineActIndicator = null;

    private String thirdPartyDistributedIndicator = null;

    private String numberOfConcerts = null;

    private String nationalityOfStreamingCompany = null;

    private String countryOfSourceISOId = null;

    private String countryOfDestinationISOId = null;

    private String pprFinalAdjustmentIndicator = null;

    private String artistRevenue = null;

    private String affiliatedSociety = null;

    private String internationalRevenueClass = null;

    private String maximumNumberPlays = null;

    private String totalNumberPlays = null;

    private String pointsCollected = null;

    private String totalNumberOfWorkPerformances = null;

    private String postingIndicator = null;

    private String affiliatedSocietyId = null;

    private String fileId = null;

    private String channelInd = null;

    // START Non UI fields
    private String scheduleId = null;

    private String scheduleSequenceNumber = null;

    private String programPerformanceActionCode = null;
    // END Non UI Fields

    // START Additional Information for Display in Search Results etc
    private String musicUserFirstName = null;

    private String musicUserLastName = null;

    private String musicUserFullName = null;

    // There are 3 properties now that have Music User Call Letter and Music User
    // Call Letter Suffix seperately as
    // musicUserCallLetterOnly, musicUserCallLetterSuffixOnly And both concatenated
    // in one property called
    // musicUserCallLtterSuffix.
    private String musicUserCallLetterSuffix = null;

    private String musicUserTypeDescription = null;

    private String licenseTypeDescription = null;

    private String surveyTypeDescription = null;

    private String sampleTypeDescription = null;

    private String sourceSystemDescription = null;
    // END Additional Information for Display in Search Results etc

    private List<Object> errorsAndWarnings = null;

    private String performanceDeleteFlag = null; // maybe required since versioning is effective

    private String errorFlag = null;

    private String isInvalidMusicUser = null;

    private String cloneType = null;

    // This is the distribution Id stamped on the performance Header record, this
    // would be the
    // latest Distribution in which the performance got processed. If the work
    // Performances got
    // processed in different distributions, The header would have the dis id that
    // got processed last.
    // This field is pretty much useless for any functional validations, this is
    // used only for display purposes.
    private String performanceHeaderDisId = null;

    private String processStatus;

    private String statusDate;

    private String targetYearQuarter;

    private String onlineEditable;

    private String classicalIndicator;

    private String supplierCodeModified = "N";

    private String assignedToUser;

    private String musicUserInformationMode;

    private String operationMode;

    private String actionSelected = null;

    private String ifFirstTimeClick = "N";

    private String[] errorCodes = null;

    private String[] errorTypes = null;

    private String[] errorMessages = null;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 20;
        StringBuilder builder = new StringBuilder();
        builder.append("ProgramPerformance [performanceHeaderId=");
        builder.append(performanceHeaderId);
        builder.append(", versionNumber=");
        builder.append(versionNumber);
        builder.append(", isCurrentVersion=");
        builder.append(isCurrentVersion);
        builder.append(", isUsedByDistribution=");
        builder.append(isUsedByDistribution);
        builder.append(", isAnyAdjustmentTriggerCreated=");
        builder.append(isAnyAdjustmentTriggerCreated);
        builder.append(", sourceSystem=");
        builder.append(sourceSystem);
        builder.append(", legacyProgramPerformanceId=");
        builder.append(legacyProgramPerformanceId);
        builder.append(", legacyMusicUserId=");
        builder.append(legacyMusicUserId);
        builder.append(", legacyMusicUserIdTypeCode=");
        builder.append(legacyMusicUserIdTypeCode);
        builder.append(", supplierCode=");
        builder.append(supplierCode);
        builder.append(", supplierDes=");
        builder.append(supplierDes);
        builder.append(", proxyRequestId=");
        builder.append(proxyRequestId);
        builder.append(", musicUserCallLetterOnly=");
        builder.append(musicUserCallLetterOnly);
        builder.append(", musicUserCallLetterSuffixOnly=");
        builder.append(musicUserCallLetterSuffixOnly);
        builder.append(", musicUserId=");
        builder.append(musicUserId);
        builder.append(", musicUserTypeCode=");
        builder.append(musicUserTypeCode);
        builder.append(", licenseTypeCode=");
        builder.append(licenseTypeCode);
        builder.append(", performanceStartDate=");
        builder.append(performanceStartDate);
        builder.append(", performanceStartTime=");
        builder.append(performanceStartTime);
        builder.append(", performanceEndDate=");
        builder.append(performanceEndDate);
        builder.append(", performanceEndTime=");
        builder.append(performanceEndTime);
        builder.append(", programOverlapCode=");
        builder.append(programOverlapCode);
        builder.append(", duration=");
        builder.append(duration);
        builder.append(", surveyTypeCode=");
        builder.append(surveyTypeCode);
        builder.append(", sampleTypeCode=");
        builder.append(sampleTypeCode);
        builder.append(", segmentNumber=");
        builder.append(segmentNumber);
        builder.append(", numberOfHooks=");
        builder.append(numberOfHooks);
        builder.append(", programNumberOfHooks=");
        builder.append(programNumberOfHooks);
        builder.append(", performerName=");
        builder.append(performerName);
        builder.append(", seriesCode=");
        builder.append(seriesCode);
        builder.append(", seriesTitle=");
        builder.append(seriesTitle);
        builder.append(", showProgramCode=");
        builder.append(showProgramCode);
        builder.append(", programTitle=");
        builder.append(programTitle);
        builder.append(", compensibilityStatusCode=");
        builder.append(compensibilityStatusCode);
        builder.append(", compensibilityStatusDescription=");
        builder.append(compensibilityStatusDescription);
        builder.append(", programNumber=");
        builder.append(programNumber);
        builder.append(", ascapPerProgramRevenueAmount=");
        builder.append(ascapPerProgramRevenueAmount);
        builder.append(", pprValue=");
        builder.append(pprValue);
        builder.append(", payPerViewAmount=");
        builder.append(payPerViewAmount);
        builder.append(", sreNumberOfPlays=");
        builder.append(sreNumberOfPlays);
        builder.append(", sreVenue=");
        builder.append(sreVenue);
        builder.append(", featuredPerformer=");
        builder.append(featuredPerformer);
        builder.append(", playListFlag=");
        builder.append(playListFlag);
        builder.append(", headlineActIndicator=");
        builder.append(headlineActIndicator);
        builder.append(", thirdPartyDistributedIndicator=");
        builder.append(thirdPartyDistributedIndicator);
        builder.append(", numberOfConcerts=");
        builder.append(numberOfConcerts);
        builder.append(", nationalityOfStreamingCompany=");
        builder.append(nationalityOfStreamingCompany);
        builder.append(", countryOfSourceISOId=");
        builder.append(countryOfSourceISOId);
        builder.append(", countryOfDestinationISOId=");
        builder.append(countryOfDestinationISOId);
        builder.append(", pprFinalAdjustmentIndicator=");
        builder.append(pprFinalAdjustmentIndicator);
        builder.append(", artistRevenue=");
        builder.append(artistRevenue);
        builder.append(", affiliatedSociety=");
        builder.append(affiliatedSociety);
        builder.append(", internationalRevenueClass=");
        builder.append(internationalRevenueClass);
        builder.append(", maximumNumberPlays=");
        builder.append(maximumNumberPlays);
        builder.append(", totalNumberPlays=");
        builder.append(totalNumberPlays);
        builder.append(", pointsCollected=");
        builder.append(pointsCollected);
        builder.append(", totalNumberOfWorkPerformances=");
        builder.append(totalNumberOfWorkPerformances);
        builder.append(", postingIndicator=");
        builder.append(postingIndicator);
        builder.append(", affiliatedSocietyId=");
        builder.append(affiliatedSocietyId);
        builder.append(", fileId=");
        builder.append(fileId);
        builder.append(", scheduleId=");
        builder.append(scheduleId);
        builder.append(", scheduleSequenceNumber=");
        builder.append(scheduleSequenceNumber);
        builder.append(", programPerformanceActionCode=");
        builder.append(programPerformanceActionCode);
        builder.append(", musicUserFirstName=");
        builder.append(musicUserFirstName);
        builder.append(", musicUserLastName=");
        builder.append(musicUserLastName);
        builder.append(", musicUserFullName=");
        builder.append(musicUserFullName);
        builder.append(", musicUserCallLetterSuffix=");
        builder.append(musicUserCallLetterSuffix);
        builder.append(", musicUserTypeDescription=");
        builder.append(musicUserTypeDescription);
        builder.append(", licenseTypeDescription=");
        builder.append(licenseTypeDescription);
        builder.append(", surveyTypeDescription=");
        builder.append(surveyTypeDescription);
        builder.append(", sampleTypeDescription=");
        builder.append(sampleTypeDescription);
        builder.append(", sourceSystemDescription=");
        builder.append(sourceSystemDescription);
        builder.append(", errorsAndWarnings=");
        builder.append(errorsAndWarnings != null ? toString(errorsAndWarnings, maxLen) : null);
        builder.append(", performanceDeleteFlag=");
        builder.append(performanceDeleteFlag);
        builder.append(", errorFlag=");
        builder.append(errorFlag);
        builder.append(", isInvalidMusicUser=");
        builder.append(isInvalidMusicUser);
        builder.append(", cloneType=");
        builder.append(cloneType);
        builder.append(", performanceHeaderDisId=");
        builder.append(performanceHeaderDisId);
        builder.append(", targetYearQuarter=");
        builder.append(targetYearQuarter);
        builder.append(", onlineEditable=");
        builder.append(onlineEditable);
        builder.append("]");
        return builder.toString();
    }

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0)
                builder.append(", ");
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Returns the affiliatedSociety.
     * 
     * @return String
     */
    public String getAffiliatedSociety() {
        return affiliatedSociety;
    }

    /**
     * Returns the affiliatedSocietyId.
     * 
     * @return String
     */
    public String getAffiliatedSocietyId() {
        return affiliatedSocietyId;
    }

    /**
     * Returns the artistRevenue.
     * 
     * @return String
     */
    public String getArtistRevenue() {
        return artistRevenue;
    }

    /**
     * Returns the ascapPerProgramRevenueAmount.
     * 
     * @return String
     */
    public String getAscapPerProgramRevenueAmount() {
        return ascapPerProgramRevenueAmount;
    }

    /**
     * Returns the countryOfDestinationISOId.
     * 
     * @return String
     */
    public String getCountryOfDestinationISOId() {
        return countryOfDestinationISOId;
    }

    /**
     * Returns the countryOfSourceISOId.
     * 
     * @return String
     */
    public String getCountryOfSourceISOId() {
        return countryOfSourceISOId;
    }

    /**
     * Returns the duration.
     * 
     * @return String
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Returns the featuredPerformer.
     * 
     * @return String
     */
    public String getFeaturedPerformer() {
        return featuredPerformer;
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
     * Returns the headlineActIndicator.
     * 
     * @return String
     */
    public String getHeadlineActIndicator() {
        return headlineActIndicator;
    }

    /**
     * Returns the internationalRevenueClass.
     * 
     * @return String
     */
    public String getInternationalRevenueClass() {
        return internationalRevenueClass;
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
     * Returns the legacyMusicUserId.
     * 
     * @return String
     */
    public String getLegacyMusicUserId() {
        return legacyMusicUserId;
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
     * Returns the maximumNumberPlays.
     * 
     * @return String
     */
    public String getMaximumNumberPlays() {
        return maximumNumberPlays;
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
     * @return Returns the musicUserCallLetterOnly.
     */
    public String getMusicUserCallLetterOnly() {
        return musicUserCallLetterOnly;
    }

    /**
     * @return Returns the musicUserCallLetterSuffixOnly.
     */
    public String getMusicUserCallLetterSuffixOnly() {
        return musicUserCallLetterSuffixOnly;
    }

    /**
     * @param musicUserCallLetterOnly The musicUserCallLetterOnly to set.
     */
    public void setMusicUserCallLetterOnly(String musicUserCallLetterOnly) {
        this.musicUserCallLetterOnly = musicUserCallLetterOnly;
    }

    /**
     * @param musicUserCallLetterSuffixOnly The musicUserCallLetterSuffixOnly to set.
     */
    public void setMusicUserCallLetterSuffixOnly(String musicUserCallLetterSuffixOnly) {
        this.musicUserCallLetterSuffixOnly = musicUserCallLetterSuffixOnly;
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
     * Returns the nationalityOfStreamingCompany.
     * 
     * @return String
     */
    public String getNationalityOfStreamingCompany() {
        return nationalityOfStreamingCompany;
    }

    /**
     * Returns the numberOfConcerts.
     * 
     * @return String
     */
    public String getNumberOfConcerts() {
        return numberOfConcerts;
    }

    /**
     * Returns the numberOfHooks.
     * 
     * @return String
     */
    public String getNumberOfHooks() {
        return numberOfHooks;
    }

    /**
     * Returns the payPerViewAmount.
     * 
     * @return String
     */
    public String getPayPerViewAmount() {
        return payPerViewAmount;
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
     * Returns the performanceHeaderId.
     * 
     * @return String
     */
    public String getPerformanceHeaderId() {
        return performanceHeaderId;
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
     * Returns the playListFlag.
     * 
     * @return String
     */
    public String getPlayListFlag() {
        return playListFlag;
    }

    /**
     * Returns the pointsCollected.
     * 
     * @return String
     */
    public String getPointsCollected() {
        return pointsCollected;
    }

    /**
     * Returns the postingIndicator.
     * 
     * @return String
     */
    public String getPostingIndicator() {
        return postingIndicator;
    }

    /**
     * Returns the pprFinalAdjustmentIndicator.
     * 
     * @return String
     */
    public String getPprFinalAdjustmentIndicator() {
        return pprFinalAdjustmentIndicator;
    }

    /**
     * Returns the pprValue.
     * 
     * @return String
     */
    public String getPprValue() {
        return pprValue;
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
     * Returns the programNumber.
     * 
     * @return String
     */
    public String getProgramNumber() {
        return programNumber;
    }

    /**
     * Returns the programNumberOfHooks.
     * 
     * @return String
     */
    public String getProgramNumberOfHooks() {
        return programNumberOfHooks;
    }

    /**
     * Returns the programOverlapCode.
     * 
     * @return String
     */
    public String getProgramOverlapCode() {
        return programOverlapCode;
    }

    /**
     * Returns the programPerformanceActionCode.
     * 
     * @return String
     */
    public String getProgramPerformanceActionCode() {
        return programPerformanceActionCode;
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
     * Returns the sampleTypeCode.
     * 
     * @return String
     */
    public String getSampleTypeCode() {
        return sampleTypeCode;
    }

    /**
     * Returns the scheduleId.
     * 
     * @return String
     */
    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * Returns the scheduleSequenceNumber.
     * 
     * @return String
     */
    public String getScheduleSequenceNumber() {
        return scheduleSequenceNumber;
    }

    /**
     * Returns the segmentNumber.
     * 
     * @return String
     */
    public String getSegmentNumber() {
        return segmentNumber;
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
     * Returns the sreNumberOfPlays.
     * 
     * @return String
     */
    public String getSreNumberOfPlays() {
        return sreNumberOfPlays;
    }

    /**
     * Returns the sreVenue.
     * 
     * @return String
     */
    public String getSreVenue() {
        return sreVenue;
    }

    /**
     * Returns the surveyTypeCode.
     * 
     * @return String
     */
    public String getSurveyTypeCode() {
        return surveyTypeCode;
    }

    /**
     * Returns the thirdPartyDistributedIndicator.
     * 
     * @return String
     */
    public String getThirdPartyDistributedIndicator() {
        return thirdPartyDistributedIndicator;
    }

    /**
     * Returns the totalNumberOfWorkPerformances.
     * 
     * @return String
     */
    public String getTotalNumberOfWorkPerformances() {
        return totalNumberOfWorkPerformances;
    }

    /**
     * Returns the totalNumberPlays.
     * 
     * @return String
     */
    public String getTotalNumberPlays() {
        return totalNumberPlays;
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
     * Sets the affiliatedSociety.
     * 
     * @param affiliatedSociety The affiliatedSociety to set
     */
    public void setAffiliatedSociety(String affiliatedSociety) {
        this.affiliatedSociety = affiliatedSociety;
    }

    /**
     * Sets the affiliatedSocietyId.
     * 
     * @param affiliatedSocietyId The affiliatedSocietyId to set
     */
    public void setAffiliatedSocietyId(String affiliatedSocietyId) {
        this.affiliatedSocietyId = affiliatedSocietyId;
    }

    /**
     * Sets the artistRevenue.
     * 
     * @param artistRevenue The artistRevenue to set
     */
    public void setArtistRevenue(String artistRevenue) {
        this.artistRevenue = artistRevenue;
    }

    /**
     * Sets the ascapPerProgramRevenueAmount.
     * 
     * @param ascapPerProgramRevenueAmount The ascapPerProgramRevenueAmount to set
     */
    public void setAscapPerProgramRevenueAmount(String ascapPerProgramRevenueAmount) {
        this.ascapPerProgramRevenueAmount = ascapPerProgramRevenueAmount;
    }

    /**
     * Sets the countryOfDestinationISOId.
     * 
     * @param countryOfDestinationISOId The countryOfDestinationISOId to set
     */
    public void setCountryOfDestinationISOId(String countryOfDestinationISOId) {
        this.countryOfDestinationISOId = countryOfDestinationISOId;
    }

    /**
     * Sets the countryOfSourceISOId.
     * 
     * @param countryOfSourceISOId The countryOfSourceISOId to set
     */
    public void setCountryOfSourceISOId(String countryOfSourceISOId) {
        this.countryOfSourceISOId = countryOfSourceISOId;
    }

    /**
     * Sets the duration.
     * 
     * @param duration The duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Sets the featuredPerformer.
     * 
     * @param featuredPerformer The featuredPerformer to set
     */
    public void setFeaturedPerformer(String featuredPerformer) {
        this.featuredPerformer = featuredPerformer;
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
     * Sets the headlineActIndicator.
     * 
     * @param headlineActIndicator The headlineActIndicator to set
     */
    public void setHeadlineActIndicator(String headlineActIndicator) {
        this.headlineActIndicator = headlineActIndicator;
    }

    /**
     * Sets the internationalRevenueClass.
     * 
     * @param internationalRevenueClass The internationalRevenueClass to set
     */
    public void setInternationalRevenueClass(String internationalRevenueClass) {
        this.internationalRevenueClass = internationalRevenueClass;
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
     * Sets the legacyMusicUserId.
     * 
     * @param legacyMusicUserId The legacyMusicUserId to set
     */
    public void setLegacyMusicUserId(String legacyMusicUserId) {
        this.legacyMusicUserId = legacyMusicUserId;
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
     * Sets the maximumNumberPlays.
     * 
     * @param maximumNumberPlays The maximumNumberPlays to set
     */
    public void setMaximumNumberPlays(String maximumNumberPlays) {
        this.maximumNumberPlays = maximumNumberPlays;
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
     * Sets the nationalityOfStreamingCompany.
     * 
     * @param nationalityOfStreamingCompany The nationalityOfStreamingCompany to set
     */
    public void setNationalityOfStreamingCompany(String nationalityOfStreamingCompany) {
        this.nationalityOfStreamingCompany = nationalityOfStreamingCompany;
    }

    /**
     * Sets the numberOfConcerts.
     * 
     * @param numberOfConcerts The numberOfConcerts to set
     */
    public void setNumberOfConcerts(String numberOfConcerts) {
        this.numberOfConcerts = numberOfConcerts;
    }

    /**
     * Sets the numberOfHooks.
     * 
     * @param numberOfHooks The numberOfHooks to set
     */
    public void setNumberOfHooks(String numberOfHooks) {
        this.numberOfHooks = numberOfHooks;
    }

    /**
     * Sets the payPerViewAmount.
     * 
     * @param payPerViewAmount The payPerViewAmount to set
     */
    public void setPayPerViewAmount(String payPerViewAmount) {
        this.payPerViewAmount = payPerViewAmount;
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
     * Sets the performanceHeaderId.
     * 
     * @param performanceHeaderId The performanceHeaderId to set
     */
    public void setPerformanceHeaderId(String performanceHeaderId) {
        this.performanceHeaderId = performanceHeaderId;
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
     * Sets the playListFlag.
     * 
     * @param playListFlag The playListFlag to set
     */
    public void setPlayListFlag(String playListFlag) {
        this.playListFlag = playListFlag;
    }

    /**
     * Sets the pointsCollected.
     * 
     * @param pointsCollected The pointsCollected to set
     */
    public void setPointsCollected(String pointsCollected) {
        this.pointsCollected = pointsCollected;
    }

    /**
     * Sets the postingIndicator.
     * 
     * @param postingIndicator The postingIndicator to set
     */
    public void setPostingIndicator(String postingIndicator) {
        this.postingIndicator = postingIndicator;
    }

    /**
     * Sets the pprFinalAdjustmentIndicator.
     * 
     * @param pprFinalAdjustmentIndicator The pprFinalAdjustmentIndicator to set
     */
    public void setPprFinalAdjustmentIndicator(String pprFinalAdjustmentIndicator) {
        this.pprFinalAdjustmentIndicator = pprFinalAdjustmentIndicator;
    }

    /**
     * Sets the pprValue.
     * 
     * @param pprValue The pprValue to set
     */
    public void setPprValue(String pprValue) {
        this.pprValue = pprValue;
    }

    /**
     * Sets the programCode.
     * 
     * @param programCode The programCode to set
     */
    public void setShowProgramCode(String programCode) {
        this.showProgramCode = programCode;
    }

    public void setProgramNumber(String programNumber) {
        this.programNumber = programNumber;
    }

    /**
     * Sets the programNumberOfHooks.
     * 
     * @param programNumberOfHooks The programNumberOfHooks to set
     */
    public void setProgramNumberOfHooks(String programNumberOfHooks) {
        this.programNumberOfHooks = programNumberOfHooks;
    }

    /**
     * Sets the programOverlapCode.
     * 
     * @param programOverlapCode The programOverlapCode to set
     */
    public void setProgramOverlapCode(String programOverlapCode) {
        this.programOverlapCode = programOverlapCode;
    }

    /**
     * Sets the programPerformanceActionCode.
     * 
     * @param programPerformanceActionCode The programPerformanceActionCode to set
     */
    public void setProgramPerformanceActionCode(String programPerformanceActionCode) {
        this.programPerformanceActionCode = programPerformanceActionCode;
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
     * Sets the sampleTypeCode.
     * 
     * @param sampleTypeCode The sampleTypeCode to set
     */
    public void setSampleTypeCode(String sampleTypeCode) {
        this.sampleTypeCode = sampleTypeCode;
    }

    /**
     * Sets the scheduleId.
     * 
     * @param scheduleId The scheduleId to set
     */
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * Sets the scheduleSequenceNumber.
     * 
     * @param scheduleSequenceNumber The scheduleSequenceNumber to set
     */
    public void setScheduleSequenceNumber(String scheduleSequenceNumber) {
        this.scheduleSequenceNumber = scheduleSequenceNumber;
    }

    /**
     * Sets the segmentNumber.
     * 
     * @param segmentNumber The segmentNumber to set
     */
    public void setSegmentNumber(String segmentNumber) {
        this.segmentNumber = segmentNumber;
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
     * Sets the sreNumberOfPlays.
     * 
     * @param sreNumberOfPlays The sreNumberOfPlays to set
     */
    public void setSreNumberOfPlays(String sreNumberOfPlays) {
        this.sreNumberOfPlays = sreNumberOfPlays;
    }

    /**
     * Sets the sreVenue.
     * 
     * @param sreVenue The sreVenue to set
     */
    public void setSreVenue(String sreVenue) {
        this.sreVenue = sreVenue;
    }

    /**
     * Sets the surveyTypeCode.
     * 
     * @param surveyTypeCode The surveyTypeCode to set
     */
    public void setSurveyTypeCode(String surveyTypeCode) {
        this.surveyTypeCode = surveyTypeCode;
    }

    /**
     * Sets the thirdPartyDistributedIndicator.
     * 
     * @param thirdPartyDistributedIndicator The thirdPartyDistributedIndicator to set
     */
    public void setThirdPartyDistributedIndicator(String thirdPartyDistributedIndicator) {
        this.thirdPartyDistributedIndicator = thirdPartyDistributedIndicator;
    }

    /**
     * Sets the totalNumberOfWorkPerformances.
     * 
     * @param totalNumberOfWorkPerformances The totalNumberOfWorkPerformances to set
     */
    public void setTotalNumberOfWorkPerformances(String totalNumberOfWorkPerformances) {
        this.totalNumberOfWorkPerformances = totalNumberOfWorkPerformances;
    }

    /**
     * Sets the totalNumberPlays.
     * 
     * @param totalNumberPlays The totalNumberPlays to set
     */
    public void setTotalNumberPlays(String totalNumberPlays) {
        this.totalNumberPlays = totalNumberPlays;
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
     * Returns the legacyMusicUserIdTypeCode.
     * 
     * @return String
     */
    public String getLegacyMusicUserIdTypeCode() {
        return legacyMusicUserIdTypeCode;
    }

    /**
     * Sets the legacyMusicUserIdTypeCode.
     * 
     * @param legacyMusicUserIdTypeCode The legacyMusicUserIdTypeCode to set
     */
    public void setLegacyMusicUserIdTypeCode(String legacyMusicUserIdTypeCode) {
        this.legacyMusicUserIdTypeCode = legacyMusicUserIdTypeCode;
    }

    /**
     * Returns the isUsedByDistribution.
     * 
     * @return String
     */
    public String getIsUsedByDistribution() {
        return isUsedByDistribution;
    }

    /**
     * Returns the sampleTypeDescription.
     * 
     * @return String
     */
    public String getSampleTypeDescription() {
        return sampleTypeDescription;
    }

    /**
     * Returns the surveyTypeDescription.
     * 
     * @return String
     */
    public String getSurveyTypeDescription() {
        return surveyTypeDescription;
    }

    /**
     * Sets the isUsedByDistribution.
     * 
     * @param isUsedByDistribution The isUsedByDistribution to set
     */
    public void setIsUsedByDistribution(String isUsedByDistribution) {
        this.isUsedByDistribution = isUsedByDistribution;
    }

    /**
     * Sets the sampleTypeDescription.
     * 
     * @param sampleTypeDescription The sampleTypeDescription to set
     */
    public void setSampleTypeDescription(String sampleTypeDescription) {
        this.sampleTypeDescription = sampleTypeDescription;
    }

    /**
     * Sets the surveyTypeDescription.
     * 
     * @param surveyTypeDescription The surveyTypeDescription to set
     */
    public void setSurveyTypeDescription(String surveyTypeDescription) {
        this.surveyTypeDescription = surveyTypeDescription;
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
     * Sets the errorsAndWarnings.
     * 
     * @param errorsAndWarnings The errorsAndWarnings to set
     */
    public void setErrorsAndWarnings(List<Object> errorsAndWarnings) {
        this.errorsAndWarnings = errorsAndWarnings;
    }

    /**
     * Returns the PerformanceDeleteFlag.
     * 
     * @return String
     */
    public String getPerformanceDeleteFlag() {
        return performanceDeleteFlag;
    }

    /**
     * Sets the performanceDeleteFlag.
     * 
     * @param deleteFlag The performanceDeleteFlag to set
     */
    public void setPerformanceDeleteFlag(String deleteFlag) {
        this.performanceDeleteFlag = deleteFlag;
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
     * Sets the errorFlag.
     * 
     * @param errorFlag The errorFlag to set
     */
    public void setErrorFlag(String errorFlag) {
        this.errorFlag = errorFlag;
    }

    /**
     * Returns the isInvalidMusicUser.
     * 
     * @return String
     */
    public String getIsInvalidMusicUser() {
        return isInvalidMusicUser;
    }

    /**
     * Sets the isInvalidMusicUser.
     * 
     * @param isInvalidMusicUser The isInvalidMusicUser to set
     */
    public void setIsInvalidMusicUser(String isInvalidMusicUser) {
        this.isInvalidMusicUser = isInvalidMusicUser;
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
     * @return Returns the cloneType.
     */
    public String getCloneType() {
        return this.cloneType;
    }

    /**
     * @param cloneType The cloneType to set.
     */
    public void setCloneType(String cloneType) {
        this.cloneType = cloneType;
    }

    /**
     * @return the performanceHeaderDisId
     */
    public String getPerformanceHeaderDisId() {
        return performanceHeaderDisId;
    }

    /**
     * @param performanceHeaderDisId the performanceHeaderDisId to set
     */
    public void setPerformanceHeaderDisId(String performanceHeaderDisId) {
        this.performanceHeaderDisId = performanceHeaderDisId;
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
     * @return the proxyRequestId
     */
    public String getProxyRequestId() {
        return proxyRequestId;
    }

    /**
     * @param proxyRequestId the proxyRequestId to set
     */
    public void setProxyRequestId(String proxyRequestId) {
        this.proxyRequestId = proxyRequestId;
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

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getTargetYearQuarter() {
        return targetYearQuarter;
    }

    public void setTargetYearQuarter(String targetYearQuarter) {
        this.targetYearQuarter = targetYearQuarter;
    }

    public String getOnlineEditable() {
        return onlineEditable;
    }

    public void setOnlineEditable(String onlineEditable) {
        this.onlineEditable = onlineEditable;
    }

    public String getClassicalIndicator() {
        return classicalIndicator;
    }

    public void setClassicalIndicator(String classicalIndicator) {
        this.classicalIndicator = classicalIndicator;
    }

    public String getSupplierCodeModified() {
        return supplierCodeModified;
    }

    public void setSupplierCodeModified(String supplierCodeModified) {
        this.supplierCodeModified = supplierCodeModified;
    }

    public String getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(String assignedToUser) {
        this.assignedToUser = assignedToUser;
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

    public String getChannelInd() {
        return channelInd;
    }

    public void setChannelInd(String channelInd) {
        this.channelInd = channelInd;
    }

    public String getMusicUserInformationMode() {
        return musicUserInformationMode;
    }

    public void setMusicUserInformationMode(String musicUserInformationMode) {
        this.musicUserInformationMode = musicUserInformationMode;
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

    public String getConstantAllwaysYES() {
        return "Y";
    }

    public String getIfFirstTimeClick() {
        return ifFirstTimeClick;
    }

    public void setIfFirstTimeClick(String ifFirstTimeClick) {
        this.ifFirstTimeClick = ifFirstTimeClick;
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

    public List<String> validate(HttpServletRequest request, MessageSource messageSources) {
        List<String> errorsColl = new ArrayList<>();

        String TIME24HOURS_PATTERN = "([0-1]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)";
        Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);

        if (!UsageConstants.SOURCE_SYSTEM_FOREIGN.equalsIgnoreCase(this.getSourceSystem())  && UsageCommonValidations.isEmptyOrNull(this.getPerformanceStartDate())) {
                errorsColl.add(messageSources.getMessage(
                    "error.usage.programPerformanceForm.required.performanceStartDate", null, Locale.getDefault()));
        }

        if (!UsageConstants.SOURCE_SYSTEM_FOREIGN.equalsIgnoreCase(this.getSourceSystem())) {
            if (UsageCommonValidations.isEmptyOrNull(this.getPerformanceStartTime())) {
                errorsColl.add(messageSources.getMessage(
                    "error.usage.programPerformanceForm.required.performanceStartTime", null, Locale.getDefault()));
            } else {
                Matcher matcher = pattern.matcher(this.getPerformanceStartTime());
                if (!matcher.matches()) {
                    errorsColl.add(
                        messageSources.getMessage("us.errors.time", new Object[] {"Start Time"}, Locale.getDefault()));
                }
            }
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getPerformanceEndDate())  && !UsageConstants.SOURCE_SYSTEM_FOREIGN.equalsIgnoreCase(this.getSourceSystem())) {
                errorsColl.add(messageSources.getMessage(
                    "error.usage.programPerformanceForm.required.performanceEndDate", null, Locale.getDefault()));
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getPerformanceEndTime())) {
            if (!UsageConstants.SOURCE_SYSTEM_FOREIGN.equalsIgnoreCase(this.getSourceSystem())) {
                errorsColl.add(messageSources.getMessage(
                    "error.usage.programPerformanceForm.required.performanceEndTime", null, Locale.getDefault()));
            }
        }

        else {
            Matcher matcher = pattern.matcher(this.getPerformanceEndTime());
            if (!matcher.matches()) {
                errorsColl
                    .add(messageSources.getMessage("us.errors.time", new Object[] {"End Time"}, Locale.getDefault()));
            }
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getMusicUserId()) && !UsageConstants.SOURCE_SYSTEM_FOREIGN.equalsIgnoreCase(this.getSourceSystem())) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.required.musicUserId", null,
                    Locale.getDefault()));
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getMusicUserId())) {
            try {
                Long.parseLong(this.getMusicUserId());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.invalid.musicUserId", null,
                    Locale.getDefault()));
            }
        }

        if (("CREATE_PROGRAM_PERFORMANCE".equalsIgnoreCase(this.actionSelected)
            || "UPDATE_PROGRAM_PERFORMANCE".equalsIgnoreCase(this.actionSelected))  && UsageCommonValidations.isEmptyOrNull(this.getSupplierCode()) ) {
                errorsColl.add(messageSources.getMessage("error.usage.performanceSearch.invalid.supplierCode", null,
                    Locale.getDefault()));
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getNumberOfHooks())) {
            try {
                Long.parseLong(this.getNumberOfHooks());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.invalid.numberOfHooks", null,
                    Locale.getDefault()));
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getProgramNumberOfHooks())) {
            try {
                Long.parseLong(this.getProgramNumberOfHooks());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.invalid.programNumberOfHooks",
                    null, Locale.getDefault()));
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getAscapPerProgramRevenueAmount())) {
            try {
                Double.parseDouble(this.getAscapPerProgramRevenueAmount());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage(
                    "error.usage.programPerformance.invalid.ascapPerProgramRevenueAmount", null, Locale.getDefault()));
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getPprValue())) {
            try {
                Double.parseDouble(this.getPprValue());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.invalid.pprValue", null,
                    Locale.getDefault()));
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getPayPerViewAmount())) {
            try {
                Double.parseDouble(this.getPayPerViewAmount());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.invalid.payPerViewAmount",
                    null, Locale.getDefault()));
            }
        }

          if (!UsageCommonValidations.isEmptyOrNull(this.getSreNumberOfPlays())) {
            try {
                Long.parseLong(this.getSreNumberOfPlays());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.invalid.sreNumberOfPlays",
                    null, Locale.getDefault()));
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getNumberOfConcerts())) {
            try {
                Long.parseLong(this.getNumberOfConcerts());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.invalid.numberOfConcerts",
                    null, Locale.getDefault()));
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getArtistRevenue())) {
            try {
                Double.parseDouble(this.getArtistRevenue());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.invalid.artistRevenue", null,
                    Locale.getDefault()));
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getTotalNumberPlays())) {
            try {
                Long.parseLong(this.getTotalNumberPlays());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.invalid.totalNumberPlays",
                    null, Locale.getDefault()));
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(this.getPointsCollected())) {
            try {
                Long.parseLong(this.getPointsCollected());
            } catch (NumberFormatException ne) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.invalid.pointsCollected", null,
                    Locale.getDefault()));
            }
        }

        if (UsageCommonValidations.isEmptyOrNull(this.getSeriesCode()) == false && UsageCommonValidations.isEmptyOrNull(this.getShowProgramCode())) {
                errorsColl.add(messageSources.getMessage("error.usage.programPerformance.required.programCode", null,
                    Locale.getDefault()));
        }

        if ("CREATE_PROGRAM_PERFORMANCE".equalsIgnoreCase(this.actionSelected)
            || "UPDATE_PROGRAM_PERFORMANCE".equalsIgnoreCase(this.actionSelected)
            || "UPDATE_MUSIC_USER_INFORMATION".equalsIgnoreCase(this.actionSelected)) {

            if(errorsColl !=null && !errorsColl.isEmpty()) {

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
        }
        return errorsColl;

    } 
}
