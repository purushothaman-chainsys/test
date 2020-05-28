package com.ascap.apm.handler.usage;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;

public interface UsageHandler {

    public PREPContext deleteWorkPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getProgramPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext deleteProgramPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateAssignedUsers(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext searchProgramPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext searchWorkPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getWorkPerformance(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateProgramPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getPerformanceMusicUserInfo(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext addProgramPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getChannelList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateChannelList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getCatalogSamplingSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext deleteWorkPerformancesList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateWorkPerformancesBulk(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext addWorkPerformance(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateWorkPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getWorkPerformancesList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext addToMedleyWorkPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext removeFromMedleyWorkPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getCatalogManualMatchList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateAssignedUsersToWorkPerf(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateCatalogManualMatchList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getMedleyWorkInformation(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext cloneCatalogPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getApmSuppliers(PREPContext inputContext) throws PrepSystemException;

    public PREPContext getApmActiveSurveyQuarter(PREPContext inputContext) throws PrepSystemException;

    public PREPContext getEOFileList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getRollupThreshold(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext loadToAPM(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateFileInventory(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext loadToEO(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getLogRequestSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateLogRequestSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getSamplingSummary(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getApmPerfBulkRequestList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext seacrhFiles(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext seacrhParty(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getLogUsageSummary(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext insertLogUsageSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateLogUsageSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getExemptionList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext addExemption(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getExemption(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateExemption(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext deleteExemptions(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
    
    public PREPContext cancelSampling(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getSamplingDetails(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateSamplingSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext addSamplingRequest(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext bypassSampling(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext cloneWorkPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateApmPerfBulkRequestList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext deleteEOLearnedMatchList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getEOLearnedMatchList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext validateApmWorks(PREPContext inputWorkContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateEOLearnedMatchList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getEOLearnedMatchMedleyWorkInformation(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext addEOLearnedMatch(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getMultWorkIdSequence(PREPContext inputContext) throws PrepSystemException;

    public PREPContext updateEOLearnedMatchMultiple(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
    
    public PREPContext getApmLearnedMatchList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getApmLearnedMatch(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
    
    public PREPContext deleteApmLearnedMatchList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
    
    public PREPContext updateApmLearnedMatch(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
    
    public PREPContext getLearnedMatchMedleyWorkInformation(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
    
    public PREPContext updateApmLearnedMatchMultiple(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
    
    public PREPContext addApmLearnedMatch(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
    
    public PREPContext  loadFile2Usage (PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

}
