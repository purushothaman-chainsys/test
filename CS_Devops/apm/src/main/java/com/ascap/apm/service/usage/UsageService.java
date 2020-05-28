package com.ascap.apm.service.usage;

import java.util.List;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.usage.ApmActiveSurveyQuarter;
import com.ascap.apm.vob.usage.ApmChannelList;
import com.ascap.apm.vob.usage.ApmFileList;
import com.ascap.apm.vob.usage.ApmFileUpload;
import com.ascap.apm.vob.usage.ApmLearnedMatch;
import com.ascap.apm.vob.usage.ApmLearnedMatchList;
import com.ascap.apm.vob.usage.ApmPerformanceBulkRequestList;
import com.ascap.apm.vob.usage.ApmWork;
import com.ascap.apm.vob.usage.CatalogSamplingSummary;
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

public interface UsageService {

    public PerformanceSearch deleteWorkPerformances(PerformanceSearch performanceSearch) throws PrepSystemException;

    public PerformanceSearch searchWorkPerformances(PerformanceSearch performanceSearchVOB) throws PrepSystemException;

    public ProgramPerformance getProgramPerformance(long programPerformanceId, int versionNumber)
        throws PrepSystemException;

    public ProgramPerformance getProgramPerformance(long programPerformanceId) throws PrepSystemException;

    public PerformanceSearch deleteProgramPerformances(PerformanceSearch performanceSearch) throws PrepSystemException;

    public PerformanceSearch searchProgramPerformances(PerformanceSearch performanceSearchVOB)
        throws PrepSystemException;

    public PerformanceSearch updateAssignedUsers(PerformanceSearch performanceSearch) throws PrepSystemException;

    public WorkPerformance getWorkPerformance(long workPerformanceId, int versionNumber) throws PrepSystemException;

    public WorkPerformance getWorkPerformance(long workPerformanceId) throws PrepSystemException;

    public ProgramPerformance updateProgramPerformance(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException;

    public ProgramPerformance getPerformanceMusicUserInfo(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException;

    public ProgramPerformance addProgramPerformance(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException;

    public ApmChannelList getChannelList(ApmChannelList apmChannelList)
        throws PrepSystemException, PrepFunctionalException;

    public ApmChannelList updateChannelList(ApmChannelList apmChannelList)
        throws PrepSystemException, PrepFunctionalException;

    public CatalogSamplingSummary getCatalogSamplingSummary(CatalogSamplingSummary samplingSummary)
        throws PrepSystemException, PrepFunctionalException;

    public WorkPerformancesList getWorkPerformancesList(WorkPerformancesList workPerformancesList)
        throws PrepSystemException, PrepFunctionalException;

    public WorkPerformance updateWorkPerformance(WorkPerformance workPerformance)
        throws PrepSystemException, PrepFunctionalException;

    public WorkPerformancesList updateWorkPerformancesBulk(WorkPerformancesList workPerformancesList)
        throws PrepSystemException, PrepFunctionalException;

    public WorkPerformancesList addToMedleyWorkPerformance(WorkPerformancesList workPerformancesList)
        throws PrepSystemException, PrepFunctionalException;

    public WorkPerformancesList removeFromMedleyWorkPerformance(WorkPerformancesList workPerformancesList)
        throws PrepSystemException, PrepFunctionalException;

    public WorkPerformance addWorkPerformance(WorkPerformance workPerformance)
        throws PrepSystemException, PrepFunctionalException;

    public ApmPerformanceBulkRequestList getCatalogManualMatchList(ApmPerformanceBulkRequestList apmFileList)
        throws PrepSystemException;

    public ApmPerformanceBulkRequestList updateAssignedUsersToWorkPerf(ApmPerformanceBulkRequestList apmFileList)
        throws PrepSystemException;

    public ApmPerformanceBulkRequestList updateCatalogManualMatchList(ApmPerformanceBulkRequestList apmFileList)
        throws PrepSystemException;

    public ApmPerformanceBulkRequestList getMedleyWorkInformation(ApmPerformanceBulkRequestList apmFileList)
        throws PrepSystemException;

    public ApmPerformanceBulkRequestList cloneCatalogPerformances(ApmPerformanceBulkRequestList apmFileList)
        throws PrepSystemException;

    public EOFileList loadToAPM(EOFileList eoFileList) throws PrepSystemException, PrepFunctionalException;

    public EOFileList loadToEO(EOFileList eoFileList) throws PrepSystemException, PrepFunctionalException;

    public ApmActiveSurveyQuarter getApmActiveSurveyQuarter() throws PrepSystemException;

    public EOFileList getEOFileList(EOFileList eoFileList) throws PrepSystemException, PrepFunctionalException;

    public String getRollupThreshold() throws PrepSystemException, PrepFunctionalException;

    public EOFileList updateFileInventory(EOFileList eoFileList) throws PrepSystemException, PrepFunctionalException;

    public List<EOSupplierFormat> getApmSuppliers() throws PrepSystemException;

    public LogRequestSummary getLogRequestSummary(LogRequestSummary inputLogRequestSummary)
        throws PrepSystemException, PrepFunctionalException;

    public LogRequestSummary updateLogRequestSummary(LogRequestSummary inputLogRequestSummary)
        throws PrepSystemException, PrepFunctionalException;

    public SamplingSummary getSamplingSummary(SamplingSummary samplingSummary)
        throws PrepSystemException, PrepFunctionalException;

    public ApmPerformanceBulkRequestList getApmPerfBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public ApmFileList seacrhFiles(ApmFileList apmFileList) throws PrepSystemException;

    public MusicUserSearch searchMusicUser(MusicUserSearch musicUserSearch) throws PrepSystemException;

    public LogUsageSummary getLogUsageSummary(LogUsageSummary inputLogUsageSummary)
        throws PrepSystemException, PrepFunctionalException;

    public LogUsageSummary insertLogUsageSummary(LogUsageSummary inputLogUsageSummary)
        throws PrepSystemException, PrepFunctionalException;

    public LogUsageSummary updateLogUsageSummary(LogUsageSummary inputLogUsageSummary)
        throws PrepSystemException, PrepFunctionalException;

    public ExemptionList getExemptionList(ExemptionList exemptionList)
        throws PrepSystemException, PrepFunctionalException;

    public Exemption addExemption(Exemption exemption) throws PrepSystemException, PrepFunctionalException;

    public Exemption getExemption(Exemption exemption) throws PrepSystemException, PrepFunctionalException;

    public Exemption updateExemption(Exemption exemption) throws PrepSystemException, PrepFunctionalException;

    public ExemptionList deleteExemptions(ExemptionList exemptionList)
        throws PrepSystemException, PrepFunctionalException;

    public SamplingSummary cancelSampling(SamplingSummary samplingSummary)
        throws PrepSystemException, PrepFunctionalException;

    public SamplingSummary getSamplingDetails(SamplingSummary samplingSummary)
        throws PrepSystemException, PrepFunctionalException;

    public SamplingRequest updateSamplingSummary(SamplingRequest samplingRequest)
        throws PrepSystemException, PrepFunctionalException;

    public SamplingRequest addSamplingRequest(SamplingRequest samplingRequest)
        throws PrepSystemException, PrepFunctionalException;

    public SamplingSummary updateSamplingResults(SamplingSummary samplingSummary)
        throws PrepSystemException, PrepFunctionalException;

    public SamplingRequest bypassSampling(SamplingRequest samplingRequest)
        throws PrepSystemException, PrepFunctionalException;

    public ApmPerformanceBulkRequestList
        cloneWorkPerformances(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList)
            throws PrepSystemException, PrepFunctionalException;

    public ApmPerformanceBulkRequestList updateApmPerfBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException;

    public EOLearnedMatchList deleteEOLearnedMatchList(EOLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException;

    public EOLearnedMatchList getEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException;

    public List<ApmWork> validateApmWorks(List<ApmWork> col) throws PrepSystemException, PrepFunctionalException;

    public EOLearnedMatchList updateEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException;

    public EOLearnedMatch getEOLearnedMatchMedleyWorkInformation(EOLearnedMatchList eoLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException;

    public EOLearnedMatch updateEOLearnedMatchMultiple(EOLearnedMatch apmLearnedMatch)
        throws PrepSystemException, PrepFunctionalException;

    public EOLearnedMatch addEOLearnedMatch(EOLearnedMatch apmLearnedMatch)
        throws PrepSystemException, PrepFunctionalException;

    public String getMultWorkIdSequence() throws PrepSystemException;

    public ApmLearnedMatch getApmLearnedMatch(ApmLearnedMatch apmLearnedMatch) throws PrepSystemException;

    public ApmLearnedMatchList getApmLearnedMatchList(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException;

    public ApmLearnedMatchList deleteApmLearnedMatchList(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException;

    public ApmLearnedMatchList updateApmLearnedMatch(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException;

    public ApmLearnedMatch getLearnedMatchMedleyWorkInformation(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException;

    public ApmLearnedMatch updateApmLearnedMatchMultiple(ApmLearnedMatch apmLearnedMatch)
        throws PrepSystemException, PrepFunctionalException;

    public ApmLearnedMatch addApmLearnedMatch(ApmLearnedMatch apmLearnedMatch)
        throws PrepSystemException, PrepFunctionalException;

    public ApmFileUpload loadFile2Usage(ApmFileUpload userFileUpload)
        throws PrepSystemException, PrepFunctionalException;

}
