package com.ascap.apm.database.usage;

import java.util.List;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.usage.ApmActiveSurveyQuarter;
import com.ascap.apm.vob.usage.ApmChannelList;
import com.ascap.apm.vob.usage.ApmFileList;
import com.ascap.apm.vob.usage.ApmPerformanceBulkRequest;
import com.ascap.apm.vob.usage.ApmPerformanceBulkRequestList;
import com.ascap.apm.vob.usage.ApmWork;
import com.ascap.apm.vob.usage.CatalogSamplingSummary;
import com.ascap.apm.vob.usage.EOFile;
import com.ascap.apm.vob.usage.EOFileList;
import com.ascap.apm.vob.usage.EOLearnedMatch;
import com.ascap.apm.vob.usage.EOLearnedMatchList;
import com.ascap.apm.vob.usage.EOSupplierFormat;
import com.ascap.apm.vob.usage.Exemption;
import com.ascap.apm.vob.usage.ExemptionList;
import com.ascap.apm.vob.usage.LogRequestSummary;
import com.ascap.apm.vob.usage.LogUsageSummary;
import com.ascap.apm.vob.usage.MusicUserSearch;
import com.ascap.apm.vob.usage.PerformanceSearch;
import com.ascap.apm.vob.usage.ProgramPerformance;
import com.ascap.apm.vob.usage.SamplingRequest;
import com.ascap.apm.vob.usage.SamplingSummary;
import com.ascap.apm.vob.usage.WorkPerformance;
import com.ascap.apm.vob.usage.WorkPerformancesList;

/**
 * Usage DAOInterface.
 * 
 * @author Manoj Puli
 * @version $Revision: 1.36 $ $Date: May 05 2011 12:34:24 $
 */
public interface UsageDAO {

    public PerformanceSearch deleteWorkPerformances(PerformanceSearch performanceSearch) throws PrepSystemException;

    public PerformanceSearch searchWorkPerformances(PerformanceSearch performanceSearchCriteria)
        throws PrepSystemException;

    public WorkPerformance getWorkPerformance(long workPerformanceId, String versionNumber) throws PrepSystemException;

    public WorkPerformance getWorkPerformance(long workPerformanceId, String versionNumber, boolean ignoreDeleteFlag)
        throws PrepSystemException;

    public ProgramPerformance getProgramPerformance(long programPerformanceId, String versionNumber)
        throws PrepSystemException;

    public ProgramPerformance getProgramPerformance(long programPerformanceId) throws PrepSystemException;

    public PerformanceSearch deleteProgramPerformances(PerformanceSearch performanceSearch) throws PrepSystemException;

    public PerformanceSearch searchProgramPerformances(PerformanceSearch performanceSearchCriteria)
        throws PrepSystemException;

    public PerformanceSearch updateAssignedUsers(PerformanceSearch performanceSearch) throws PrepSystemException;

    public WorkPerformance getWorkPerformance(long workPerformanceId) throws PrepSystemException;

    public WorkPerformance getWorkPerformance(long workPerformanceId, boolean ignoreDeleteFlag)
        throws PrepSystemException;

    public ProgramPerformance updateProgramPerformance(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException;

    public void callPerformanceBatchValidate(String actionType, String perfHeaderId, String workPerfId, String userId)
        throws PrepSystemException;

    public ProgramPerformance getPerformanceMusicUserInfo(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException;

    public String getSingleOutputInputInformation(String informationType, String parameter) throws PrepSystemException;

    public ProgramPerformance addProgramPerformance(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException;

    public ApmChannelList getChannelList(ApmChannelList apmChannelList) throws PrepSystemException;

    public ApmChannelList updateChannelList(ApmChannelList apmChannelList) throws PrepSystemException;

    public CatalogSamplingSummary getCatalogSamplingSummary(CatalogSamplingSummary samplingSummary)
        throws PrepSystemException;

    public WorkPerformancesList getWorkPerformancesList(WorkPerformancesList workPerformancesList)
        throws PrepSystemException;

    public WorkPerformance updateWorkPerformance(WorkPerformance workPerformanceVob) throws PrepSystemException;

    public String getHeaderTargetSurveyYearQuarter(String performanceHeaderId) throws PrepSystemException;

    public WorkPerformance addWorkPerformance(WorkPerformance workPerformanceVob) throws PrepSystemException;

    public String getMedleyRelatedInformation(String informationType, String[] workPerformanceIds,
        String workSequenceNumber, String workPerformanceId, String performanceHeaderId) throws PrepSystemException;

    public List<Object> getWorkPerformanceIdsOfMedley(String programPerformanceId, String workSequenceNumber)
        throws PrepSystemException;

    public List<Object> getValidationLookup(String validationLookupKey) throws PrepSystemException;

    public WorkPerformancesList updateWorkPerformancesBulk(WorkPerformancesList workPerformancesList)
        throws PrepSystemException;

    public ApmPerformanceBulkRequestList updateCatalogManualMatchList(
        ApmPerformanceBulkRequestList updApmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmPerformanceBulkRequestList deleteLearnedMatch(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList)
        throws PrepSystemException;

    public ApmPerformanceBulkRequestList
        undeleteLearnedMatch(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmPerformanceBulkRequestList
        updateLearnedkMatch(ApmPerformanceBulkRequestList updApmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmPerformanceBulkRequestList getMedleyWorkInformation(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmPerformanceBulkRequestList cloneCatalogPerformances(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmPerformanceBulkRequest getCatalogManualMatch(ApmPerformanceBulkRequest groupRequest)
        throws PrepSystemException;

    public List<Object> validateWorkIdForBulkMatch(ApmPerformanceBulkRequest groupRequest) throws PrepSystemException;

    public ApmPerformanceBulkRequestList getCatalogManualMatchList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmPerformanceBulkRequestList updateAssignedUsersToCatalogPerf(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmPerformanceBulkRequestList updateAssignedUsersToWorkPerf(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public String getSumRollupCnt(EOFileList eoFileList) throws PrepSystemException;

    public EOFile getEOBatchControl(EOFile eoFile) throws PrepSystemException;

    public String validationErrorExists(String suppcode, String perfqtr, String perfperiod) throws PrepSystemException;

    public EOFileList loadToAPM(EOFileList eoFileList) throws PrepSystemException;

    public String getSupplierType(String suppcode) throws PrepSystemException;

    public List<EOSupplierFormat> getApmSuppliers() throws PrepSystemException;

    public ApmActiveSurveyQuarter getApmActiveSurveyQuarter() throws PrepSystemException;

    public String validationErrorExists(String suppcode, String perfqtr) throws PrepSystemException;

    public EOFileList updateFileInventory(EOFileList eoFileList) throws PrepSystemException;

    public EOFileList loadToEO(EOFileList eoFileList) throws PrepSystemException;

    public EOFileList getEOFileList(EOFileList eoFileList) throws PrepSystemException;

    public String getRollupThreshold() throws PrepSystemException;

    public LogRequestSummary getLogRequestSummary(LogRequestSummary logRequestSummary) throws PrepSystemException;

    public int getTotalSearchRows(LogRequestSummary logRequestSummary) throws PrepSystemException;

    public LogRequestSummary setNavigationInfo(LogRequestSummary logRequestSummary, String activeSurveyYearQuarter)
        throws PrepSystemException;

    public LogRequestSummary updateLogRequestSummary(LogRequestSummary logRequestSummary) throws PrepSystemException;

    public SamplingSummary getSamplingSummary(SamplingSummary samplingSummary) throws PrepSystemException;

    public ApmPerformanceBulkRequestList getApmPerfBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmFileList seacrhFiles(ApmFileList apmFileList) throws PrepSystemException;

    public MusicUserSearch searchMusicUser(MusicUserSearch musicUserSearch) throws PrepSystemException;

    public LogUsageSummary getLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException;

    public LogUsageSummary deleteLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException;

    public LogUsageSummary updateLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException;

    public LogUsageSummary insertLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException;

    public ExemptionList getExemptionList(ExemptionList exemptionList) throws PrepSystemException;

    public boolean isValidWorkId(String workId) throws PrepSystemException;

    public Exemption getExemptionByValues(Exemption exemption) throws PrepSystemException;

    public Exemption addExemption(Exemption exemption) throws PrepSystemException;

    public Exemption getExemption(Exemption exemption) throws PrepSystemException;

    public Exemption updateExemption(Exemption exemption) throws PrepSystemException;

    public ExemptionList deleteExemptions(ExemptionList exemptionList) throws PrepSystemException;

    public SamplingSummary cancelSampling(SamplingSummary samplingSummary) throws PrepSystemException;

    public SamplingSummary getSamplingDetails(SamplingSummary samplingSummary) throws PrepSystemException;

    public SamplingRequest updateSamplingSummary(SamplingRequest samplingRequest) throws PrepSystemException;

    public SamplingRequest addSamplingRequest(SamplingRequest samplingRequest) throws PrepSystemException;

    public SamplingSummary updateSamplingResults(SamplingSummary samplingSummary) throws PrepSystemException;

    public SamplingRequest bypassSampling(SamplingRequest samplingRequest) throws PrepSystemException;

    public ApmPerformanceBulkRequestList
        cloneWorkPerformances(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmPerformanceBulkRequestList updateApmPerfBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmPerformanceBulkRequest getApmPerfBulkRequest(ApmPerformanceBulkRequest apmPerformanceBulkRequest)
        throws PrepSystemException;
    
    public EOLearnedMatchList getEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList) throws PrepSystemException;

    public List<ApmWork> validateApmWorks(List<ApmWork> col) throws PrepSystemException;

    public EOLearnedMatchList updateEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList) throws PrepSystemException;

    public EOLearnedMatch getEOLearnedMatchMedleyWorkInformation(EOLearnedMatchList eoLearnedMatchList) throws PrepSystemException;

    public EOLearnedMatchList deleteEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList) throws PrepSystemException;

    public String validateIndustryIds(String matchType, String matchIdValue) throws PrepSystemException;

    public EOLearnedMatch getEOLearnedMatch(EOLearnedMatch eoLearnedMatch) throws PrepSystemException;

    public EOLearnedMatch updateEOLearnedMatchMultiple(EOLearnedMatch eoLearnedMatch) throws PrepSystemException;

    public EOLearnedMatch addEOLearnedMatch(EOLearnedMatch eoLearnedMatch) throws PrepSystemException;

    public String getSequence(String string) throws PrepSystemException;

}
