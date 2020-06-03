package com.ascap.apm.service.usage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.service.BaseService;
import com.ascap.apm.servicehelpers.usage.UsageHelper;
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

/**
 * Session Bean implementation class UsageServiceImpl
 */
@Service("usageService")
public class UsageServiceImpl extends BaseService implements UsageService {

    private static final long serialVersionUID = 8525690557385216031L;

    @Autowired
    private UsageHelper usageHelper;

    /**
     * @see com.ascap.apm.service.usage.UsageService#deleteWorkPerformances(PerformanceSearch)
     */
    public PerformanceSearch deleteWorkPerformances(PerformanceSearch performanceSearch) throws PrepSystemException {
        log.debug(
            "Entering PerformanceSearch deleteWorkPerformances(PerformanceSearch performanceSearch) method in UsageServiceImpl");
        usageHelper.deleteWorkPerformances(performanceSearch);
        log.debug(
            "Exiting PerformanceSearch deleteWorkPerformances(PerformanceSearch performanceSearch) method in UsageServiceImpl");

        return performanceSearch;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#searchWorkPerformances(PerformanceSearch)
     */
    public PerformanceSearch searchWorkPerformances(PerformanceSearch performanceSearchVOB) throws PrepSystemException {
        log.debug("Entering searchWorkPerformances method in UsageServiceImpl");
        performanceSearchVOB = usageHelper.searchWorkPerformances(performanceSearchVOB);
        log.debug("Exiting searchWorkPerformances method in UsageServiceImpl");
        return performanceSearchVOB;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#getProgramPerformance(long, int)
     */
    public ProgramPerformance getProgramPerformance(long programPerformanceId, int versionNumber)
        throws PrepSystemException {
        log.debug(
            "Entering getProgramPerformance(long programPerformanceId, int versionNumber) method in UsageServiceImpl");
        ProgramPerformance outProgramPerformanceVob = null;
        outProgramPerformanceVob = usageHelper.getProgramPerformance(programPerformanceId, versionNumber);
        log.debug(
            "Exiting getProgramPerformance(long programPerformanceId, int versionNumber) method in UsageServiceImpl");
        return outProgramPerformanceVob;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#getProgramPerformance(long)
     */
    public ProgramPerformance getProgramPerformance(long programPerformanceId) throws PrepSystemException {
        log.debug("Entering getProgramPerformance(long programPerformanceId) method in UsageServiceImpl");
        ProgramPerformance outProgramPerformanceVob = null;
        outProgramPerformanceVob = usageHelper.getProgramPerformance(programPerformanceId);
        log.debug("Exiting getProgramPerformance(long programPerformanceId) method in UsageServiceImpl");
        return outProgramPerformanceVob;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#deleteProgramPerformances(PerformanceSearch)
     */
    public PerformanceSearch deleteProgramPerformances(PerformanceSearch performanceSearch) throws PrepSystemException {
        log.debug(
            "Entering PerformanceSearch deleteProgramPerformances(PerformanceSearch performanceSearch) method in UsageServiceImpl");
        usageHelper.deleteProgramPerformances(performanceSearch);
        log.debug(
            "Exiting PerformanceSearch deleteProgramPerformances(PerformanceSearch performanceSearch) method in UsageServiceImpl");
        return performanceSearch;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#searchProgramPerformances(PerformanceSearch)
     */
    public PerformanceSearch searchProgramPerformances(PerformanceSearch performanceSearchVOB)
        throws PrepSystemException {
        log.debug("Entering searchProgramPerformances method in UsageServiceImpl");
        performanceSearchVOB = usageHelper.searchProgramPerformances(performanceSearchVOB);
        log.debug("Exiting searchProgramPerformances method in UsageServiceImpl");
        return performanceSearchVOB;
    }

    public PerformanceSearch updateAssignedUsers(PerformanceSearch performanceSearch) throws PrepSystemException {
        log.debug(
            "Entering PerformanceSearch updateAssignedUsers(PerformanceSearch performanceSearch) method in UsageServiceImpl");
        usageHelper.updateAssignedUsers(performanceSearch);
        log.debug(
            "Exiting PerformanceSearch deleteProgramPerupdateAssignedUsersformances(PerformanceSearch performanceSearch) method in UsageServiceImpl");
        return performanceSearch;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#getWorkPerformance(long, int)
     */
    public WorkPerformance getWorkPerformance(long workPerformanceId, int versionNumber) throws PrepSystemException {
        log.debug("Entering getWorkPerformance(long workPerformanceId, int versionNumber) method in UsageServiceImpl");
        WorkPerformance outWorkPerformanceVob = null;
        outWorkPerformanceVob = usageHelper.getWorkPerformance(workPerformanceId, versionNumber);
        log.debug("Exiting getWorkPerformance(long workPerformanceId, int versionNumber) method in UsageServiceImpl");
        return outWorkPerformanceVob;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#getWorkPerformance(long)
     */
    public WorkPerformance getWorkPerformance(long workPerformanceId) throws PrepSystemException {
        log.debug("Entering getWorkPerformance(long workPerformanceId) method in UsageServiceImpl");
        WorkPerformance outWorkPerformanceVob = null;
        outWorkPerformanceVob = usageHelper.getWorkPerformance(workPerformanceId);
        log.debug("Exiting getWorkPerformance(long workPerformanceId) method in UsageServiceImpl");
        return outWorkPerformanceVob;
    }

    public ProgramPerformance updateProgramPerformance(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException {
        log.debug(
            "Entering ProgramPerformance updateProgramPerformance(ProgramPerformance programPerformance) method in UsageServiceImpl");
        ProgramPerformance outprogramPerformanceVob = null;
        outprogramPerformanceVob = usageHelper.updateProgramPerformance(programPerformance);
        log.debug(
            "Exiting ProgramPerformance updateProgramPerformance(ProgramPerformance programPerformance) method in UsageServiceImpl");
        return outprogramPerformanceVob;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageServiceLocalInterface#getPerformanceMusicUserInfo(ProgramPerformance)
     */
    public ProgramPerformance getPerformanceMusicUserInfo(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException {
        log.debug(
            "Entering ProgramPerformance getPerformanceMusicUserInfo(ProgramPerformance programPerformance) method in UsageServiceImpl");
        ProgramPerformance outprogramPerformanceVob = null;
        outprogramPerformanceVob = usageHelper.getPerformanceMusicUserInfo(programPerformance);
        log.debug(
            "Exiting ProgramPerformance getPerformanceMusicUserInfo(ProgramPerformance programPerformance) method in UsageServiceImpl");
        return outprogramPerformanceVob;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageServiceLocalInterface#addProgramPerformance(ProgramPerformance)
     */
    public ProgramPerformance addProgramPerformance(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addProgramPerformance(ProgramPerformance programPerformance) method in UsageServiceImpl");
        ProgramPerformance outprogramPerformanceVob = null;
        try {
            outprogramPerformanceVob = usageHelper.addProgramPerformance(programPerformance);
        } catch (PrepFunctionalException pfe) {
            throw pfe;
        } catch (PrepSystemException pse) {
            throw pse;
        }
        log.debug("Exiting addProgramPerformance(ProgramPerformance programPerformance) method in UsageServiceImpl");
        return outprogramPerformanceVob;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#getChannelList(ApmChannelList)
     */
    public ApmChannelList getChannelList(ApmChannelList apmChannelList)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering getChannelList(ApmChannelList apmChannelList) method in UsageServiceImpl");
        apmChannelList = usageHelper.getChannelList(apmChannelList);
        log.debug("Exiting getChannelList(ApmChannelList apmChannelList) method in UsageServiceImpl");
        return apmChannelList;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#updateChannelList(ApmChannelList)
     */
    public ApmChannelList updateChannelList(ApmChannelList apmChannelList)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering updateChannelList(ApmChannelList apmChannelList) method in UsageServiceImpl");
        apmChannelList = usageHelper.updateChannelList(apmChannelList);
        log.debug("Exiting updateChannelList(ApmChannelList apmChannelList) method in UsageServiceImpl ");
        return apmChannelList;
    }

    public CatalogSamplingSummary getCatalogSamplingSummary(CatalogSamplingSummary samplingSummary)
        throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering UsageServiceImpl - getCatalogSamplingSummary method ");
        }
        CatalogSamplingSummary outSamplingDetails = null;
        outSamplingDetails = usageHelper.getCatalogSamplingSummary(samplingSummary);

        if (log.isDebugEnabled()) {
            log.debug("Exiting UsageServiceImpl - getCatalogSamplingSummary method ");
        }
        return outSamplingDetails;
    }

    public WorkPerformancesList getWorkPerformancesList(WorkPerformancesList workPerformancesList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug(
            "Entering getWorkPerformancesList(WorkPerformancesList workPerformancesList) method in UsageServiceImpl");
        WorkPerformancesList outWorkPerformancesListVob = null;
        outWorkPerformancesListVob = usageHelper.getWorkPerformancesList(workPerformancesList);
        log.debug(
            "Exiting getWorkPerformancesList(WorkPerformancesList workPerformancesList) method in UsageServiceImpl");
        return outWorkPerformancesListVob;
    }

    public WorkPerformance updateWorkPerformance(WorkPerformance workPerformance)
        throws PrepSystemException, PrepFunctionalException {
        log.debug(
            "Entering WorkPerformance updateWorkPerformance(WorkPerformance workPerformance)  method in UsageServiceImpl");
        WorkPerformance outworkPerformanceVob = null;
        outworkPerformanceVob = usageHelper.updateWorkPerformance(workPerformance);
        log.debug(
            "Exiting WorkPerformance updateWorkPerformance(WorkPerformance workPerformance)  method in UsageServiceImpl");
        return outworkPerformanceVob;
    }

    public WorkPerformancesList updateWorkPerformancesBulk(WorkPerformancesList workPerformancesList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug(
            "Entering PerformanceSearch updateWorkPerformancesBulk(WorkPerformancesList workPerformancesList) method in UsageServiceImpl");
        usageHelper.updateWorkPerformancesBulk(workPerformancesList);
        log.debug(
            "Exiting PerformanceSearch updateWorkPerformancesBulk(WorkPerformancesList workPerformancesList) method in UsageServiceImpl");
        return workPerformancesList;
    }

    public WorkPerformancesList addToMedleyWorkPerformance(WorkPerformancesList workPerformancesList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug(
            "Entering WorkPerformancesList addToMedleyWorkPerformance(WorkPerformancesList workPerformancesList)  method in UsageServiceImpl");
        WorkPerformancesList outWorkPerformancesListVob = null;
        outWorkPerformancesListVob = usageHelper.addToMedleyWorkPerformance(workPerformancesList);
        log.debug(
            "Exiting WorkPerformancesList addToMedleyWorkPerformance(WorkPerformancesList workPerformancesList)  method in UsageServiceImpl");
        return outWorkPerformancesListVob;
    }

    public WorkPerformancesList removeFromMedleyWorkPerformance(WorkPerformancesList workPerformancesList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug(
            "Entering WorkPerformancesList removeFromMedleyWorkPerformance(WorkPerformancesList workPerformancesList)  method in UsageServiceImpl");
        WorkPerformancesList outWorkPerformancesListVob = null;
        outWorkPerformancesListVob = usageHelper.removeFromMedleyWorkPerformance(workPerformancesList);
        log.debug(
            "Exiting WorkPerformancesList removeFromMedleyWorkPerformance(WorkPerformancesList workPerformancesList)  method in UsageServiceImpl");
        return outWorkPerformancesListVob;
    }

    public WorkPerformance addWorkPerformance(WorkPerformance workPerformance)
        throws PrepSystemException, PrepFunctionalException {
        log.debug(
            "Entering WorkPerformance addWorkPerformance(WorkPerformance workPerformance) method in UsageServiceImpl");
        WorkPerformance outworkPerformanceVob = null;
        outworkPerformanceVob = usageHelper.addWorkPerformance(workPerformance);
        log.debug(
            "Exiting WorkPerformance addWorkPerformance(WorkPerformance workPerformance) method in UsageServiceImpl");
        return outworkPerformanceVob;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageServiceImpl#getCatalogManualMatchList(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList getCatalogManualMatchList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering getCatalogManualMatchList method in UsageServiceImpl");
        ApmPerformanceBulkRequestList outApmPerformanceBulkRequestListVob = null;
        outApmPerformanceBulkRequestListVob = usageHelper.getCatalogManualMatchList(apmPerformanceBulkRequestList);
        log.debug("Exiting getCatalogManualMatchList method in UsageServiceImpl");
        return outApmPerformanceBulkRequestListVob;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageServiceImpl#updateAssignedUsersToWorkPerf(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList updateAssignedUsersToWorkPerf(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering UsageServiceImpl - updateAssignedUsersToWorkPerf method ");
        apmPerformanceBulkRequestList = usageHelper.updateAssignedUsersToWorkPerf(apmPerformanceBulkRequestList);
        log.debug("Exiting UsageServiceImpl - updateAssignedUsersToWorkPerf method ");
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageServiceImpl#updateAssignedUsersToWorkPerf(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList updateCatalogManualMatchList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering ApmFileList updateCatalogManualMatchList method in UsageServiceImpl");
        ApmPerformanceBulkRequestList outApmPerformanceBulkRequestListVob = null;
        outApmPerformanceBulkRequestListVob = usageHelper.updateCatalogManualMatchList(apmPerformanceBulkRequestList);
        log.debug("Exiting ApmFileList updateCatalogManualMatchList method in UsageServiceImpl");

        return outApmPerformanceBulkRequestListVob;

    }

    /**
     * @see com.ascap.apm.service.usage.UsageServiceImpl#getMedleyWorkInformation(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList getMedleyWorkInformation(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering UsageServiceImpl - getMedleyWorkInformation method ");
        apmPerformanceBulkRequestList = usageHelper.getMedleyWorkInformation(apmPerformanceBulkRequestList);
        log.debug("Exiting UsageServiceImpl - getMedleyWorkInformation method ");
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageServiceImpl#cloneCatalogPerformances(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList cloneCatalogPerformances(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering UsageServiceImpl - cloneCatalogPerformances method ");
        apmPerformanceBulkRequestList = usageHelper.cloneCatalogPerformances(apmPerformanceBulkRequestList);
        log.debug("Exiting UsageServiceImpl - cloneCatalogPerformances method ");
        return apmPerformanceBulkRequestList;

    }

    public EOFileList loadToAPM(EOFileList eoFileList) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - loadToAPM method ");
        eoFileList = usageHelper.loadToAPM(eoFileList);
        log.debug("Exiting UsageServiceImpl - loadToAPM method ");
        return eoFileList;
    }

    public List<EOSupplierFormat> getApmSuppliers() throws PrepSystemException {
        log.debug("Entering UsageServiceImpl - getApmSuppliers method ");
        List<EOSupplierFormat> col = usageHelper.getApmSuppliers();
        log.debug("Exiting UsageServiceImpl - getApmSuppliers method ");
        return col;

    }

    public ApmActiveSurveyQuarter getApmActiveSurveyQuarter() throws PrepSystemException {
        log.debug("Entering UsageServiceImpl - ApmActiveSurveyQuarter method ");
        ApmActiveSurveyQuarter apmActiveSurveyQuarter = usageHelper.getApmActiveSurveyQuarter();
        log.debug("Exiting UsageServiceImpl - ApmActiveSurveyQuarter method ");
        return apmActiveSurveyQuarter;

    }

    public EOFileList getEOFileList(EOFileList eoFileList) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - getEOFileList method ");
        eoFileList = usageHelper.getEOFileList(eoFileList);
        log.debug("Exiting UsageServiceImpl - getEOFileList method ");
        return eoFileList;
    }

    public String getRollupThreshold() throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - getRollupThreshold method ");
        String rollupThreshold = usageHelper.getRollupThreshold();
        log.debug("Exiting UsageServiceImpl - getRollupThreshold method ");
        return rollupThreshold;

    }

    public EOFileList updateFileInventory(EOFileList eoFileList) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - updateFileInventory method ");
        eoFileList = usageHelper.updateFileInventory(eoFileList);
        log.debug("Exiting UsageServiceImpl - updateFileInventory method ");

        return eoFileList;
    }

    public EOFileList loadToEO(EOFileList eoFileList) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - loadToEO method ");
        eoFileList = usageHelper.loadToEO(eoFileList);
        log.debug("Exiting UsageServiceImpl - loadToEO method ");
        return eoFileList;
    }

    public LogRequestSummary getLogRequestSummary(LogRequestSummary inputLogRequestSummary)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - getLogRequestSummary method ");

        LogRequestSummary outputLogRequestSummary = null;
        outputLogRequestSummary = usageHelper.getLogRequestSummary(inputLogRequestSummary);
        log.debug("Exiting UsageServiceImpl - getLogRequestSummary method ");

        return outputLogRequestSummary;
    }

    public LogRequestSummary updateLogRequestSummary(LogRequestSummary inputLogRequestSummary)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - updateLogRequestSummary method ");

        LogRequestSummary outputLogRequestSummary = null;

        outputLogRequestSummary = usageHelper.updateLogRequestSummary(inputLogRequestSummary);

        log.debug("Exiting UsageServiceImpl - updateLogRequestSummary method ");

        return outputLogRequestSummary;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#getSamplingSummary(SamplingSummary)
     */
    public SamplingSummary getSamplingSummary(SamplingSummary samplingSummary)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering getSamplingSummary(SamplingSummary samplingSummary) method in UsageServiceImpl ");
        SamplingSummary outSamplingDetails = null;
        outSamplingDetails = usageHelper.getSamplingSummary(samplingSummary);
        log.debug("Exiting getSamplingSummary(SamplingSummary samplingSummary) method in UsageServiceImpl ");
        return outSamplingDetails;
    }

    public ApmPerformanceBulkRequestList getApmPerfBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering getApmPerfBulkRequestList method in UsageServiceImpl");
        ApmPerformanceBulkRequestList outApmPerformanceBulkRequestListVob = null;
        outApmPerformanceBulkRequestListVob = usageHelper.getApmPerfBulkRequestList(apmPerformanceBulkRequestList);
        log.debug("Exiting getApmPerfBulkRequestList method in UsageServiceImpl");
        return outApmPerformanceBulkRequestListVob;
    }

    public ApmFileList seacrhFiles(ApmFileList apmFileList) throws PrepSystemException {
        log.debug("Entering ApmFileList seacrhFiles(ApmFileList ApmFileList)  method in UsageServiceImpl");
        ApmFileList outSampleDatesListVob = null;
        outSampleDatesListVob = usageHelper.seacrhFiles(apmFileList);
        log.debug("Exiting ApmFileList seacrhFiles(ApmFileList ApmFileList)  method in UsageServiceImpl");
        return outSampleDatesListVob;
    }

    public MusicUserSearch searchMusicUser(MusicUserSearch musicUserSearch) throws PrepSystemException {
        log.debug("Entering searchMusicUser method in UsageServiceImpl");
        MusicUserSearch outMusicUserSearch = null;
        outMusicUserSearch = usageHelper.searchMusicUser(musicUserSearch);
        log.debug("Exiting searchMusicUser method in UsageServiceImpl");
        return outMusicUserSearch;
    }

    public LogUsageSummary getLogUsageSummary(LogUsageSummary inputLogUsageSummary)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering UsageServiceImpl - getLogUsageSummary method ");
        LogUsageSummary outputLogUsageSummary = null;
        outputLogUsageSummary = usageHelper.getLogUsageSummary(inputLogUsageSummary);
        log.debug("Exiting UsageServiceImpl - getLogUsageSummary method ");
        return outputLogUsageSummary;
    }

    public LogUsageSummary insertLogUsageSummary(LogUsageSummary inputLogUsageSummary)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - insertLogUsageSummary method ");
        LogUsageSummary outputLogUsageSummary = null;
        outputLogUsageSummary = usageHelper.insertLogUsageSummary(inputLogUsageSummary);
        log.debug("Exiting UsageServiceImpl - insertLogUsageSummary method ");
        return outputLogUsageSummary;
    }

    public LogUsageSummary updateLogUsageSummary(LogUsageSummary inputLogUsageSummary)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - updateLogUsageSummary method ");
        LogUsageSummary outputLogUsageSummary = null;
        outputLogUsageSummary = usageHelper.updateLogUsageSummary(inputLogUsageSummary);
        log.debug("Exiting UsageServiceImpl - updateLogUsageSummary method ");
        return outputLogUsageSummary;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#getExemptionList(exemptionList)
     */
    public ExemptionList getExemptionList(ExemptionList exemptionList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getExemptionList(ExemptionList exemptionList) method in UsageServiceImpl ");
        exemptionList = usageHelper.getExemptionList(exemptionList);
        log.debug("Exiting getExemptionList(ExemptionList exemptionList) method in UsageServiceImpl ");
        return exemptionList;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#addExemption(exemption)
     */
    public Exemption addExemption(Exemption exemption) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addExemption(Exemption exemption) method in UsageServiceImpl ");
        exemption = usageHelper.addExemption(exemption);
        log.debug("Exiting addExemption(Exemption exemption) method in UsageServiceImpl ");
        return exemption;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#getExemption(exemption)
     */
    public Exemption getExemption(Exemption exemption) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getExemption(Exemption exemption) method in UsageServiceImpl ");
        exemption = usageHelper.getExemption(exemption);
        log.debug("Exiting getExemption(Exemption exemption) method in UsageServiceImpl ");
        return exemption;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#updateExemption(exemption)
     */
    public Exemption updateExemption(Exemption exemption) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateExemption(Exemption exemption) method in UsageServiceImpl ");
        exemption = usageHelper.updateExemption(exemption);
        log.debug("Exiting updateExemption(Exemption exemption) method in UsageServiceImpl ");
        return exemption;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#deleteExemptions(exemption)
     */
    public ExemptionList deleteExemptions(ExemptionList exemptionList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering deleteExemptions(ExemptionList exemptionList) method in UsageServiceImpl ");
        exemptionList = usageHelper.deleteExemptions(exemptionList);
        log.debug("Exiting deleteExemptions(ExemptionList exemptionList) method in UsageServiceImpl ");
        return exemptionList;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#cancelSampling(samplingSummary)
     */
    public SamplingSummary cancelSampling(SamplingSummary samplingSummary)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering cancelSampling(SamplingSummary samplingSummary) method in UsageServiceImpl ");
        samplingSummary = usageHelper.cancelSampling(samplingSummary);
        log.debug("Exiting cancelSampling(SamplingSummary samplingSummary) method in UsageServiceImpl ");
        return samplingSummary;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#getSamplingDetails(samplingSummary)
     */
    public SamplingSummary getSamplingDetails(SamplingSummary samplingSummary)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getSamplingDetails(SamplingSummary samplingSummary) method in UsageServiceImpl ");
        SamplingSummary outSamplingDetails = null;
        outSamplingDetails = usageHelper.getSamplingDetails(samplingSummary);
        log.debug("Exiting getSamplingDetails(SamplingSummary samplingSummary) method in UsageServiceImpl ");
        return outSamplingDetails;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#updateSamplingSummary(samplingRequest)
     */
    public SamplingRequest updateSamplingSummary(SamplingRequest samplingRequest)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateSamplingSummary(SamplingRequest samplingRequest) method in UsageServiceImpl ");
        samplingRequest = usageHelper.updateSamplingSummary(samplingRequest);
        log.debug("Exiting updateSamplingSummary(SamplingRequest samplingRequest) method in UsageServiceImpl ");
        return samplingRequest;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#addSamplingRequest(samplingRequest)
     */
    public SamplingRequest addSamplingRequest(SamplingRequest samplingRequest)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addSamplingRequest(SamplingRequest samplingRequest) method in UsageServiceImpl ");
        samplingRequest = usageHelper.addSamplingRequest(samplingRequest);
        log.debug("Exiting addSamplingRequest(SamplingRequest samplingRequest) method in UsageServiceImpl ");
        return samplingRequest;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#updateSamplingResults(samplingRequest)
     */
    public SamplingSummary updateSamplingResults(SamplingSummary samplingSummary)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateSamplingResults(SamplingSummary samplingSummary) method in UsageServiceImpl ");
        samplingSummary = usageHelper.updateSamplingResults(samplingSummary);
        log.debug("Exiting updateSamplingResults(SamplingSummary samplingSummary) method in UsageServiceImpl ");
        return samplingSummary;
    }

    /**
     * @see com.ascap.apm.service.usage.UsageService#bypassSampling(samplingRequest)
     */
    public SamplingRequest bypassSampling(SamplingRequest samplingRequest)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering bypassSampling(SamplingRequest samplingRequest) method in UsageServiceImpl ");
        samplingRequest = usageHelper.bypassSampling(samplingRequest);
        log.debug("Exiting bypassSampling(SamplingRequest samplingRequest) method in UsageServiceImpl ");
        return samplingRequest;
    }

    public ApmPerformanceBulkRequestList
        cloneWorkPerformances(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList)
            throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - cloneWorkPerformances method ");
        apmPerformanceBulkRequestList = usageHelper.cloneWorkPerformances(apmPerformanceBulkRequestList);
        log.debug("Exiting UsageServiceImpl - cloneWorkPerformances method ");
        return apmPerformanceBulkRequestList;

    }

    public ApmPerformanceBulkRequestList updateApmPerfBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering ApmFileList updateApmPerfBulkRequestList method in UsageServiceImpl");
        ApmPerformanceBulkRequestList outApmPerformanceBulkRequestListVob = null;
        outApmPerformanceBulkRequestListVob = usageHelper.updateApmPerfBulkRequestList(apmPerformanceBulkRequestList);
        log.debug("Exiting ApmFileList getApmPerfBulkRequestList method in UsageServiceImpl");
        return outApmPerformanceBulkRequestListVob;
    }

    public EOLearnedMatchList deleteEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - deleteEOLearnedMatchList method ");
        eoLearnedMatchList = usageHelper.deleteEOLearnedMatchList(eoLearnedMatchList);
        log.debug("Exiting UsageServiceImpl - deleteEOLearnedMatchList method ");
        return eoLearnedMatchList;
    }

    public EOLearnedMatchList getEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - getEOLearnedMatchList method ");
        eoLearnedMatchList = usageHelper.getEOLearnedMatchList(eoLearnedMatchList);
        log.debug("Exiting UsageServiceImpl - getEOLearnedMatchList method ");
        return eoLearnedMatchList;
    }

    public List<ApmWork> validateApmWorks(List<ApmWork> col) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - validateApmWorks method ");
        List<ApmWork> outCol = usageHelper.validateApmWorks(col);
        log.debug("Exiting UsageServiceImpl - validateApmWorks method ");
        return outCol;
    }

    public EOLearnedMatchList updateEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - updateEOLearnedMatchList method ");
        eoLearnedMatchList = usageHelper.updateEOLearnedMatchList(eoLearnedMatchList);
        log.debug("Exiting UsageServiceImpl - updateEOLearnedMatchList method ");
        return eoLearnedMatchList;
    }

    public EOLearnedMatch getEOLearnedMatchMedleyWorkInformation(EOLearnedMatchList eoLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering UsageServiceImpl - getEOLearnedMatchMedleyWorkInformation method ");
        EOLearnedMatch eoLearnedMatch = usageHelper.getEOLearnedMatchMedleyWorkInformation(eoLearnedMatchList);
        log.debug("Exiting UsageServiceImpl - getEOLearnedMatchMedleyWorkInformation method ");
        return eoLearnedMatch;

    }

    public String getMultWorkIdSequence() throws PrepSystemException {
        log.debug("Entering UsageServiceImpl - getMultWorkIdSequence method ");
        String multWorkId = usageHelper.getMultWorkIdSequence();
        log.debug("Exiting UsageServiceImpl - getMultWorkIdSequence method ");
        return multWorkId;
    }

    public EOLearnedMatch addEOLearnedMatch(EOLearnedMatch eoLearnedMatch)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - addEOLearnedMatch method ");
        eoLearnedMatch = usageHelper.addEOLearnedMatch(eoLearnedMatch);
        log.debug("Exiting UsageServiceImpl - addEOLearnedMatch method ");
        return eoLearnedMatch;
    }

    public EOLearnedMatch updateEOLearnedMatchMultiple(EOLearnedMatch eoLearnedMatch)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - updateEOLearnedMatchMultiple method ");
        eoLearnedMatch = usageHelper.updateEOLearnedMatchMultiple(eoLearnedMatch);
        log.debug("Exiting UsageServiceImpl - updateEOLearnedMatchMultiple method ");
        return eoLearnedMatch;
    }

    @Override
    public ApmLearnedMatch getApmLearnedMatch(ApmLearnedMatch apmLearnedMatch) throws PrepSystemException {
        log.debug("Entering ApmFileList getApmLearnedMatch method in UsageServiceImpl");
        ApmLearnedMatch outApmLearnedMatch = null;
        outApmLearnedMatch = usageHelper.getApmLearnedMatch(apmLearnedMatch);
        log.debug("Exiting ApmFileList getApmLearnedMatch method in UsageServiceImpl");
        return outApmLearnedMatch;

    }

    @Override
    public ApmLearnedMatchList getApmLearnedMatchList(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException {
        log.debug("Entering ApmFileList getApmLearnedMatchList method in UsageServiceImpl");
        ApmLearnedMatchList outApmLearnedMatchList = null;
        outApmLearnedMatchList = usageHelper.getApmLearnedMatchList(apmLearnedMatchList);
        log.debug("Exiting ApmFileList getApmLearnedMatchList method in UsageServiceImpl");
        return outApmLearnedMatchList;

    }

    @Override
    public ApmLearnedMatchList deleteApmLearnedMatchList(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering deleteApmLearnedMatchList method in UsageServiceImpl");
        ApmLearnedMatchList outApmLearnedMatch = null;
        outApmLearnedMatch = usageHelper.deleteApmLearnedMatchList(apmLearnedMatchList);
        log.debug("Exiting deleteApmLearnedMatchList method in UsageServiceImpl");
        return outApmLearnedMatch;

    }

    @Override
    public ApmLearnedMatchList updateApmLearnedMatch(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateApmLearnedMatch method in UsageServiceImpl");
        ApmLearnedMatchList outApmLearnedMatch = null;
        outApmLearnedMatch = usageHelper.updateApmLearnedMatch(apmLearnedMatchList);
        log.debug("Exiting updateApmLearnedMatch method in UsageServiceImpl");
        return outApmLearnedMatch;

    }

    @Override
    public ApmLearnedMatch getLearnedMatchMedleyWorkInformation(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - getLearnedMatchMedleyWorkInformation method ");
        ApmLearnedMatch apmLearnedMatch = usageHelper.getLearnedMatchMedleyWorkInformation(apmLearnedMatchList);
        log.debug("Exiting UsageServiceImpl - getLearnedMatchMedleyWorkInformation method ");
        return apmLearnedMatch;

    }

    @Override
    public ApmLearnedMatch updateApmLearnedMatchMultiple(ApmLearnedMatch apmLearnedMatch)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - updateApmLearnedMatchMultiple method ");
        apmLearnedMatch = usageHelper.updateApmLearnedMatchMultiple(apmLearnedMatch);
        log.debug("Exiting UsageServiceImpl - updateApmLearnedMatchMultiple method ");
        return apmLearnedMatch;
    }

    @Override
    public ApmLearnedMatch addApmLearnedMatch(ApmLearnedMatch apmLearnedMatch)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addApmLearnedMatch method in UsageServiceImpl");
        ApmLearnedMatch outApmLearnedMatch = null;
        outApmLearnedMatch = usageHelper.addApmLearnedMatch(apmLearnedMatch);
        log.debug("Exiting addApmLearnedMatch method in UsageServiceImpl");
        return outApmLearnedMatch;

    }

    @Override
    public ApmFileUpload loadFile2Usage(ApmFileUpload userFileUpload)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageServiceImpl - loadFile2Avps method ");
        userFileUpload = usageHelper.loadFile2Usage(userFileUpload);
        log.debug("Exiting UsageServiceImpl - loadFile2Avps method ");
        return userFileUpload;
    }
}
