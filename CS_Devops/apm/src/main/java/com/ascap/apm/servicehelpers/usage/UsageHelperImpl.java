package com.ascap.apm.servicehelpers.usage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.DebugHelperUtils;
import com.ascap.apm.common.utils.UsageCommonValidations;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.database.usage.UsageDAO;
import com.ascap.apm.servicehelpers.BaseHelper;
import com.ascap.apm.vob.usage.ApmActiveSurveyQuarter;
import com.ascap.apm.vob.usage.ApmChannelList;
import com.ascap.apm.vob.usage.ApmFileList;
import com.ascap.apm.vob.usage.ApmFileUpload;
import com.ascap.apm.vob.usage.ApmLearnedMatch;
import com.ascap.apm.vob.usage.ApmLearnedMatchList;
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
 * Usage Helper
 *
 * @author Manoj Puli
 * @version $Revision: 1.0 $ $Date: Sep 05 2012 12:34:30 $
 * @param <K>
 */
@Service("usageHelper")
public class UsageHelperImpl extends BaseHelper implements UsageHelper {

    private static final long serialVersionUID = -5228968601943091282L;

    private static final String ERROR_USAGE_PROGRAMPERFORMANCE_INVALID_MUSICUSERID =
        "error.usage.programPerformance.invalid.musicUserId";

    private static final String ERROR_COMMON_EXCEPTION = "us.error.common.exception";

    private static final String WORK_ID_ALREADY_VALIDATED = "Work Id already validated ";

    private static final String ERROR_APM_EXEMPTION_NON_NUMERIC = "us.error.apm.exemption.non.numeric";

    private static final String WRK_ID = "WRK_ID";

    @Autowired
    private UsageDAO usageDAO;

    /**
     * Method deleteWorkPerformances.
     * 
     * @param performanceSearch
     * @return PerformanceSearch
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public PerformanceSearch deleteWorkPerformances(PerformanceSearch performanceSearch) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - deleteWorkPerformances method");

        PerformanceSearch outPerformanceSearchVob = usageDAO.deleteWorkPerformances(performanceSearch);

        log.debug("Exiting UsageHelperImpl - deleteWorkPerformances method");
        return outPerformanceSearchVob;
    }

    /**
     * Method searchWorkPerformances.
     * 
     * @param performanceSearchVob
     * @return PerformanceSearch
     * @throws PrepSystemException
     */
    public PerformanceSearch searchWorkPerformances(PerformanceSearch performanceSearchVob) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - searchWorkPerformances method");

        PerformanceSearch outPerformanceSearchVob = null;

        outPerformanceSearchVob = usageDAO.searchWorkPerformances(performanceSearchVob);
        log.debug("Exiting UsageHelperImpl - searchWorkPerformances method");
        return outPerformanceSearchVob;
    }

    /**
     * Method getWorkPerformance.
     * 
     * @param workPerformanceId
     * @param versionNumber
     * @return WorkPerformance
     * @throws PrepSystemException
     */
    public WorkPerformance getWorkPerformance(long workPerformanceId, int versionNumber) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getWorkPerformance(long workPerformanceId, int versionNumber) method "
            + workPerformanceId + "," + versionNumber);
        WorkPerformance outWorkPerformanceVob = null;
        outWorkPerformanceVob = usageDAO.getWorkPerformance(workPerformanceId, Integer.toString(versionNumber));

        log.debug("Exiting UsageHelperImpl - getWorkPerformance(long workPerformanceId, int versionNumber) method");
        return outWorkPerformanceVob;
    }

    /**
     * Method getProgramPerformance.
     * 
     * @param programPerformanceId
     * @param versionNumber
     * @return ProgramPerformance
     * @throws PrepSystemException
     */
    public ProgramPerformance getProgramPerformance(long programPerformanceId, int versionNumber)
        throws PrepSystemException {
        log.debug(
            "Entering UsageHelperImpl - getProgramPerformance(long programPerformanceId, int versionNumber) method");

        ProgramPerformance outProgramPerformanceVob = null;

        outProgramPerformanceVob =
            usageDAO.getProgramPerformance(programPerformanceId, Integer.toString(versionNumber));

        log.debug(
            "Exiting UsageHelperImpl - getProgramPerformance(long programPerformanceId, int versionNumber) method");
        return outProgramPerformanceVob;
    }

    /**
     * Method getProgramPerformance.
     * 
     * @param programPerformanceId
     * @return ProgramPerformance
     * @throws PrepSystemException
     */
    public ProgramPerformance getProgramPerformance(long programPerformanceId) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getProgramPerformance method");
        ProgramPerformance outProgramPerformanceVob = null;
        outProgramPerformanceVob = usageDAO.getProgramPerformance(programPerformanceId);
        log.debug("Exiting UsageHelperImpl - getProgramPerformance method " + outProgramPerformanceVob);
        return outProgramPerformanceVob;
    }

    /**
     * Method deleteProgramPerformances.
     * 
     * @param performanceSearch
     * @return PerformanceSearch
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public PerformanceSearch deleteProgramPerformances(PerformanceSearch performanceSearch) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - deleteProgramPerformances method");
        usageDAO.deleteProgramPerformances(performanceSearch);
        log.debug("Exiting UsageHelperImpl - deleteProgramPerformances method");
        return performanceSearch;
    }

    /**
     * Method searchProgramPerformances.
     * 
     * @param performanceSearchVob
     * @return PerformanceSearch
     * @throws PrepSystemException
     */
    public PerformanceSearch searchProgramPerformances(PerformanceSearch performanceSearchVob)
        throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - searchProgramPerformances method");
        PerformanceSearch outPerformanceSearchVob = null;
        outPerformanceSearchVob = usageDAO.searchProgramPerformances(performanceSearchVob);
        log.debug("Exiting UsageHelperImpl - searchProgramPerformances method");
        return outPerformanceSearchVob;
    }

    public PerformanceSearch updateAssignedUsers(PerformanceSearch performanceSearch) throws PrepSystemException {
        log.debug("Entering updateAssignedUsers in UsageHelperImpl");
        log.debug("Calling dao interface usageDAO.updateAssignedUsers(performanceSearch);");
        performanceSearch = usageDAO.updateAssignedUsers(performanceSearch);
        log.debug("Exiting updateAssignedUsers in UsageHelperImpl");
        return performanceSearch;
    }

    /**
     * Method getWorkPerformance.
     * 
     * @param workPerformanceId
     * @return WorkPerformance
     * @throws PrepSystemException
     */
    public WorkPerformance getWorkPerformance(long workPerformanceId) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getWorkPerformance method " + workPerformanceId);
        WorkPerformance outWorkPerformanceVob = null;
        outWorkPerformanceVob = usageDAO.getWorkPerformance(workPerformanceId);
        log.debug("Exiting UsageHelperImpl - getWorkPerformance method");
        return outWorkPerformanceVob;
    }

    /**
     * Method updateProgramPerformance.
     * 
     * @param programPerformance
     * @return ProgramPerformance
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public ProgramPerformance updateProgramPerformance(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - updateProgramPerformance method :" + programPerformance);
        return this.updateProgramPerformanceBatch(programPerformance);

    }

    public ProgramPerformance updateProgramPerformanceBatch(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - updateProgramPerformanceBatch method :" + programPerformance);
        ProgramPerformance outProgramPerformanceVob = null;
        if (programPerformance == null) {
            log.debug(
                "UsageHelperImpl updateProgramPerformanceBatch : Performance object is NULL so returning without doing any thing, SHOULD NEVER HAPPEN IF WEB LAYER IS CORRECT:");
            return programPerformance;
        }
        if (UsageCommonValidations.isEmptyOrNull(programPerformance.getPerformanceHeaderId())) {
            log.debug(
                "UsageHelperImpl updateProgramPerformanceBatch : Performance Header Id is NULL so returning without doing any thing, SHOULD NEVER HAPPEN IF WEB LAYER IS CORRECT:");
            return programPerformance;
        }
        ProgramPerformance programPerformanceDB =
            usageDAO.getProgramPerformance(Long.parseLong(programPerformance.getPerformanceHeaderId()));

        if (programPerformanceDB != null && programPerformanceDB.getFileId() != null
            && "0".equals(programPerformanceDB.getFileId())
            && !programPerformanceDB.getSupplierCode().equals(programPerformance.getSupplierCode())) {
            programPerformance.setSupplierCodeModified("Y");
        }
        outProgramPerformanceVob = usageDAO.updateProgramPerformance(programPerformance);
        outProgramPerformanceVob.setUserId(programPerformance.getUserId());
        this.callPerformanceBatchValidate(outProgramPerformanceVob, UsageConstants.PERF_HDR_UPDATE);
        log.debug("Exiting UsageHelperImpl - updateProgramPerformanceBatch method");
        long currentProgramPerformanceId = 0;
        currentProgramPerformanceId = Long.parseLong(outProgramPerformanceVob.getPerformanceHeaderId());
        outProgramPerformanceVob = usageDAO.getProgramPerformance(currentProgramPerformanceId);

        return outProgramPerformanceVob;
    }

    public ProgramPerformance callPerformanceBatchValidate(ProgramPerformance programPerformanceVobToValidate,
        String actionType) throws PrepSystemException {
        log.debug("UsageHelperImpl callPerformanceBatchValidate PerformanceHeaderId: "
            + programPerformanceVobToValidate.getPerformanceHeaderId() + " actionType :" + actionType);

        usageDAO.callPerformanceBatchValidate(actionType, programPerformanceVobToValidate.getPerformanceHeaderId(),
            null, programPerformanceVobToValidate.getUserId());
        return programPerformanceVobToValidate;
    }

    /**
     * Method getPerformanceMusicUserInfo.
     * 
     * @param programPerformance
     * @return ProgramPerformance
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public ProgramPerformance getPerformanceMusicUserInfo(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering UsageHelperImpl - getPerformanceMusicUserInfo method :" + programPerformance);
        }

        ProgramPerformance outProgramPerformanceVob = null;
        String strIsValidMusicUserParty = null;
        if (programPerformance == null) {
            throw new PrepFunctionalException(ERROR_USAGE_PROGRAMPERFORMANCE_INVALID_MUSICUSERID);
        }
        if (UsageCommonValidations.isEmptyOrNull(programPerformance.getMusicUserId())) {
            throw new PrepFunctionalException("error.usage.programPerformance.required.musicUserId");
        } else {
            try {
                Long.parseLong(programPerformance.getMusicUserId());
                strIsValidMusicUserParty = usageDAO.getSingleOutputInputInformation(
                    UsageConstants.SDBINF_GET_IF_VALID_MUSIC_USER_PARTY, programPerformance.getMusicUserId());
                log.debug("MMMMMMMMMMMMMMMMMMMMMMMManoj strIsValidMusicUserParty " + strIsValidMusicUserParty);
                if (UsageCommonValidations.isEmptyOrNull(strIsValidMusicUserParty)
                    || (!UsageConstants.BOOLEAN_STRING_Y.equalsIgnoreCase(strIsValidMusicUserParty))) {

                    throw new PrepFunctionalException(ERROR_USAGE_PROGRAMPERFORMANCE_INVALID_MUSICUSERID);

                }
            } catch (NumberFormatException ne) {

                throw new PrepFunctionalException(ERROR_USAGE_PROGRAMPERFORMANCE_INVALID_MUSICUSERID);

            }
        }
        outProgramPerformanceVob = usageDAO.getPerformanceMusicUserInfo(programPerformance);
        log.debug("Exiting UsageHelperImpl - getPerformanceMusicUserInfo method :" + outProgramPerformanceVob);
        return outProgramPerformanceVob;
    }

    /**
     * Method addProgramPerformance.
     * 
     * @param programPerformance
     * @return ProgramPerformance
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public ProgramPerformance addProgramPerformance(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - addProgramPerformance method");
        return this.addProgramPerformanceBatch(programPerformance);

    }

    public ProgramPerformance addProgramPerformanceBatch(ProgramPerformance programPerformance)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - addProgramPerformanceBatch method");
        ProgramPerformance outProgramPerformanceVob = null;
        outProgramPerformanceVob = usageDAO.addProgramPerformance(programPerformance);
        outProgramPerformanceVob.setUserId(programPerformance.getUserId());
        this.callPerformanceBatchValidate(outProgramPerformanceVob, UsageConstants.PERF_HDR_ADD);
        long currentProgramPerformanceId = 0;
        currentProgramPerformanceId = Long.parseLong(outProgramPerformanceVob.getPerformanceHeaderId());
        outProgramPerformanceVob = usageDAO.getProgramPerformance(currentProgramPerformanceId);
        log.debug("Exiting UsageHelperImpl - addProgramPerformanceBatch method" + outProgramPerformanceVob);
        return outProgramPerformanceVob;
    }

    /**
     * Method getChannelList.
     * 
     * @param apmChannelList
     * @return ApmChannelList
     * @throws PrepSystemException
     */
    public ApmChannelList getChannelList(ApmChannelList apmChannelList) throws PrepSystemException {

        log.debug("Entering UsageHelperImpl - getChannelList method ");
        apmChannelList = usageDAO.getChannelList(apmChannelList);
        log.debug("Exiting UsageHelperImpl - getChannelList method ");
        return apmChannelList;
    }

    /**
     * Method updateChannelList.
     * 
     * @param apmChannelList
     * @return ApmChannelList
     * @throws PrepSystemException
     */
    public ApmChannelList updateChannelList(ApmChannelList apmChannelList) throws PrepSystemException {

        log.debug("Entering UsageHelperImpl - updateChannelList method ");
        apmChannelList = usageDAO.updateChannelList(apmChannelList);
        log.debug("Exiting UsageHelperImpl - updateChannelList method ");
        return apmChannelList;
    }

    public CatalogSamplingSummary getCatalogSamplingSummary(CatalogSamplingSummary samplingSummary)
        throws PrepSystemException {
        if (log.isDebugEnabled()) {
            log.debug("Entering UsageHelperImpl - getCatalogSamplingSummary method ");
        }
        CatalogSamplingSummary outSamplingDetails = usageDAO.getCatalogSamplingSummary(samplingSummary);

        if (log.isDebugEnabled()) {
            log.debug("Exiting UsageHelperImpl - getCatalogSamplingSummary method ");
        }
        return outSamplingDetails;
    }

    public WorkPerformancesList getWorkPerformancesList(WorkPerformancesList workPerformancesList)
        throws PrepSystemException {
        log.debug(
            "Entering UsageHelperImpl - getWorkPerformancesList(WorkPerformancesList workPerformancesList) method");
        WorkPerformancesList outWorkPerformancesListVob = null;
        outWorkPerformancesListVob = usageDAO.getWorkPerformancesList(workPerformancesList);
        log.debug(
            "Exiting UsageHelperImpl - getWorkPerformancesList(WorkPerformancesList workPerformancesList) method ");
        return outWorkPerformancesListVob;
    }

    public WorkPerformance updateWorkPerformance(WorkPerformance workPerformance) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - updateWorkPerformance method" + workPerformance);
        return this.updateWorkPerformanceBatch(workPerformance);
    }

    public WorkPerformance updateWorkPerformanceBatch(WorkPerformance workPerformance) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - updateWorkPerformanceBatch method" + workPerformance);
        log.debug(
            "Entering UsageHelperImpl - workPerformance.getMatchTypeCode() " + workPerformance.getMatchTypeCode());
        WorkPerformance outWorkPerformanceVob = null;
        WorkPerformance dbMergeWorkPerformance = null;

        dbMergeWorkPerformance = usageDAO.getWorkPerformance(Long.parseLong(workPerformance.getWorkPerformanceId()));

        if (!ValidationUtils.isEmptyOrNull(workPerformance.getWorkId())) {
            if (ValidationUtils.isEmptyOrNull(dbMergeWorkPerformance.getWorkId())
                || !workPerformance.getWorkId().equals(dbMergeWorkPerformance.getWorkId())) {
                workPerformance.setMatchTypeCode(UsageConstants.APM_MATCH_TYPE_MANUAL);
            }
        } else {
            if (!UsageConstants.APM_MATCH_TYPE_PENDING.equals(workPerformance.getMatchTypeCode())) {
                workPerformance.setMatchTypeCode(UsageConstants.APM_MATCH_TYPE_NO_MATCH);
            }
        }

        outWorkPerformanceVob = usageDAO.updateWorkPerformance(workPerformance);

        this.callWorkPerformanceBatchValidate(workPerformance, UsageConstants.WRK_PERF_UPDATE);

        outWorkPerformanceVob =
            usageDAO.getWorkPerformance(Long.parseLong(outWorkPerformanceVob.getWorkPerformanceId()));

        log.debug("Exiting UsageHelperImpl - updateWorkPerformanceBatch method");
        return outWorkPerformanceVob;
    }

    public WorkPerformance addWorkPerformance(WorkPerformance workPerformance) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - addWorkPerformance method");
        return this.addWorkPerformanceBatch(workPerformance);

    }

    public WorkPerformance addWorkPerformanceBatch(WorkPerformance workPerformance) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - addWorkPerformanceBatch method");
        WorkPerformance outWorkPerformanceVob = null;
        String currentHighestWorkSequenceNumber = null;
        String targetSurveyYearQtr = null;
        int nextWorkSequenceNumber = 0;
        String userId = null;

        long currentWorkPerformanceId = 0;
        ProgramPerformance programPerformanceForTotals;

        if (workPerformance == null) {
            return workPerformance;
        }

        currentHighestWorkSequenceNumber = usageDAO.getSingleOutputInputInformation(
            UsageConstants.SDBINF_GET_WORK_PERFORMANCE_HIGHEST_WORKSEQUENCE_NUMBER,
            workPerformance.getPerformanceHeaderId());

        targetSurveyYearQtr = usageDAO.getHeaderTargetSurveyYearQuarter(workPerformance.getPerformanceHeaderId());
        workPerformance.setTargetYearQuarter(targetSurveyYearQtr);

        userId = workPerformance.getUserId();
        nextWorkSequenceNumber = Integer.parseInt(currentHighestWorkSequenceNumber) + 1;

        workPerformance.setWorkSequenceNumber(String.valueOf(nextWorkSequenceNumber));

        outWorkPerformanceVob = usageDAO.addWorkPerformance(workPerformance);
        outWorkPerformanceVob.setUserId(userId);

        currentWorkPerformanceId = Long.parseLong(outWorkPerformanceVob.getWorkPerformanceId());

        this.callWorkPerformanceBatchValidate(outWorkPerformanceVob, UsageConstants.WRK_PERF_ADD);

        outWorkPerformanceVob = usageDAO.getWorkPerformance(currentWorkPerformanceId);
        programPerformanceForTotals =
            usageDAO.getProgramPerformance(Long.parseLong(outWorkPerformanceVob.getPerformanceHeaderId()));
        log.debug("UsageHelperImpl - addWorkPerformanceBatch method After ValidatePP :" + programPerformanceForTotals);
        copyMusicUserInfoToWorkPerf(outWorkPerformanceVob, programPerformanceForTotals);

        log.debug("Exiting UsageHelperImpl - addWorkPerformanceBatch method Messages"
            + DebugHelperUtils.debugCollections("Perf Messages HELPER", outWorkPerformanceVob.getErrorsAndWarnings()));
        return outWorkPerformanceVob;
    }

    private WorkPerformance copyMusicUserInfoToWorkPerf(WorkPerformance workPerfVob, ProgramPerformance perfHdrVob) {

        if (workPerfVob == null) {
            return workPerfVob;
        }

        if (perfHdrVob == null) {
            return workPerfVob;
        }

        workPerfVob.setMusicUserId(perfHdrVob.getMusicUserId());

        workPerfVob.setMusicUserTypeCode(perfHdrVob.getMusicUserTypeCode());
        workPerfVob.setMusicUserTypeDescription(perfHdrVob.getMusicUserTypeDescription());

        workPerfVob.setLicenseTypeCode(perfHdrVob.getLicenseTypeCode());
        workPerfVob.setLicenseTypeDescription(perfHdrVob.getLicenseTypeDescription());

        workPerfVob.setSourceSystem(perfHdrVob.getSourceSystem());

        workPerfVob.setMusicUserLastName(perfHdrVob.getMusicUserLastName());
        workPerfVob.setMusicUserFirstName(perfHdrVob.getMusicUserFirstName());
        workPerfVob.setMusicUserFullName(perfHdrVob.getMusicUserFullName());
        workPerfVob.setMusicUserCallLetterSuffix(perfHdrVob.getMusicUserCallLetterSuffix());

        return workPerfVob;
    }

    public WorkPerformancesList addToMedleyWorkPerformance(WorkPerformancesList workPerformancesList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug(
            "Entering WorkPerformancesList addToMedleyWorkPerformance(WorkPerformancesList workPerformancesList)  method in UsageHelperImpl XXXXXXXXXXXXXXXXXXXXXXXXXXxx");
        WorkPerformancesList outWorkPerformancesListVob = null;

        String[] selectedIds;
        String workPerformanceIdStr;
        long workPerformanceId;
        WorkPerformance workPerformanceObj;

        ProgramPerformance tempProgramPerformanceObj;

        String currentIsPartOfMedley;
        WorkPerformance currentOtherMedleyWorkPerformanceObj;
        List<Object> otherMedleyWorkPerformanceIdsColl;
        Iterator<Object> otherMedleyWorkPerformanceItr;
        String otherMedleyWorkPerformanceIdStr;
        long otherMedleyWorkPerformanceId;

        Collection<String> alreadyDoneMedleys = null;

        String maxMedleySequenceNumberStr;
        int runningMedleySequenceNumber = 1;
        String smallestWorkSequenceNumberStr;

        int totalIdsCount;

        boolean isMedleyDistributed = false;
        String medleyDistributedIdStr = null;

        List<Object> adjustmentsCollection = new ArrayList<>();

        Map<String, Collection<?>> validationLookups = null;

        if (workPerformancesList == null) {
            log.debug(
                "Program workPerformancesList is NULL IN WorkPerformancesList addToMedleyWorkPerformance in HELPER");
            return workPerformancesList;
        }

        if (workPerformancesList.getSelectedIds() == null) {
            log.debug(
                "Program workPerformancesList 2 is NULL IN WorkPerformancesList addToMedleyWorkPerformance in HELPER");

            throw new PrepFunctionalException("error.usage.addToMedley.nofWorkPerformancesInsufficient");
        }

        if (workPerformancesList.getSelectedIds().length <= 1) {
            log.debug(
                "Program workPerformancesList 3 is NULL IN WorkPerformancesList addToMedleyWorkPerformance in HELPER");
            throw new PrepFunctionalException("error.usage.addToMedley.nofWorkPerformancesInsufficient");
        }

        if (UsageCommonValidations.isEmptyOrNull(workPerformancesList.getProgramPerformanceId())) {
            log.debug("Program Performance Id is NULL IN WorkPerformancesList addToMedleyWorkPerformance in HELPER");
            return workPerformancesList;
        }
        alreadyDoneMedleys = new ArrayList<>();

        smallestWorkSequenceNumberStr = usageDAO.getMedleyRelatedInformation(
            UsageConstants.SDB_MEDLEYINFO_GET_WORK_PERFORMANCE_SMALLEST_WORKSEQUENCE_NUMBER,
            workPerformancesList.getSelectedIds(), "", "", "");
        maxMedleySequenceNumberStr = usageDAO.getMedleyRelatedInformation(
            UsageConstants.SDB_MEDLEYINFO_GET_WORK_PERFORMANCE_HIGHEST_MEDLEY_SEQUENCE_NUMBER,
            workPerformancesList.getSelectedIds(), smallestWorkSequenceNumberStr, "", "");
        medleyDistributedIdStr = usageDAO.getMedleyRelatedInformation(
            UsageConstants.SDB_MEDLEYINFO_GET_WORK_PERFORMANCE_HIGHEST_MEDLEY_SEQUENCE_NUMBER,
            workPerformancesList.getSelectedIds(), smallestWorkSequenceNumberStr, "", "");

        if (UsageCommonValidations.isEmptyOrNull(medleyDistributedIdStr)) {
            isMedleyDistributed = true;
        }

        runningMedleySequenceNumber = (Integer.parseInt(maxMedleySequenceNumberStr));

        log.debug(
            "ADD_TO_MEDLEY inside Helper what is the Running MedleySequence Number :" + runningMedleySequenceNumber);
        selectedIds = workPerformancesList.getSelectedIds();
        totalIdsCount = selectedIds.length;

        for (int currIndex = 0; currIndex < totalIdsCount; currIndex++) {
            try {

                workPerformanceIdStr = selectedIds[currIndex];
                workPerformanceId = Long.parseLong(workPerformanceIdStr);
                workPerformanceObj = usageDAO.getWorkPerformance(workPerformanceId);

                log.debug("ADD_TO_MEDLEY Inside Processing UsageHelperImpl.addToMedleyWorkPerformance "
                    + workPerformanceIdStr);

                if (this.isValueInCollection(alreadyDoneMedleys, workPerformanceIdStr)) {
                    log.debug("ADD_TO_MEDLEY Work Performance Id Exists in alreadyDoneMedleys so continue called"
                        + workPerformanceIdStr);
                    continue;
                }

                // checking if part of the same medley or not
                if (!UsageCommonValidations.areBothEqualIgnoreCase(workPerformanceObj.getWorkSequenceNumber(),
                    smallestWorkSequenceNumberStr)) {

                    // Check if the current Work Performance is a Medley if Yes then merge the whole
                    // medley
                    currentIsPartOfMedley =
                        usageDAO.getMedleyRelatedInformation(UsageConstants.SDB_MEDLEYINFO_GET_IF_PART_OF_MEDLEY,
                            selectedIds, workPerformanceObj.getWorkSequenceNumber(), workPerformanceIdStr,
                            workPerformanceObj.getPerformanceHeaderId());

                    if (!UsageCommonValidations.isEmptyOrNull(currentIsPartOfMedley)) {
                        otherMedleyWorkPerformanceIdsColl = usageDAO.getWorkPerformanceIdsOfMedley(
                            workPerformanceObj.getPerformanceHeaderId(), workPerformanceObj.getWorkSequenceNumber());

                        log.debug(DebugHelperUtils.debugCollections(
                            "ADD_TO_MEDLEY OTHER WORKPerformances of the Medley inside UsageHelperImpl.addToMedleyWorkPerformance()",
                            otherMedleyWorkPerformanceIdsColl));
                    } else {
                        otherMedleyWorkPerformanceIdsColl = null;
                    }

                    if (otherMedleyWorkPerformanceIdsColl != null) {
                        otherMedleyWorkPerformanceItr = otherMedleyWorkPerformanceIdsColl.iterator();

                        if (otherMedleyWorkPerformanceItr != null) {
                            while (otherMedleyWorkPerformanceItr.hasNext()) {

                                otherMedleyWorkPerformanceIdStr = (String) otherMedleyWorkPerformanceItr.next();

                                log.debug("ADD_TO_MEDLEY ADDING TO MEDLEY OTHER Work Performance id "
                                    + otherMedleyWorkPerformanceIdStr);
                                otherMedleyWorkPerformanceId = Long.parseLong(otherMedleyWorkPerformanceIdStr);
                                currentOtherMedleyWorkPerformanceObj =
                                    usageDAO.getWorkPerformance(otherMedleyWorkPerformanceId);

                                currentOtherMedleyWorkPerformanceObj
                                    .setWorkSequenceNumber(smallestWorkSequenceNumberStr);
                                runningMedleySequenceNumber++;
                                currentOtherMedleyWorkPerformanceObj
                                    .setMedleySequenceNumber(Integer.toString(runningMedleySequenceNumber));

                                if ((isMedleyDistributed) && (UsageCommonValidations
                                    .isEmptyOrNull(currentOtherMedleyWorkPerformanceObj.getDistributionId()))) {
                                    currentOtherMedleyWorkPerformanceObj.setDistributionId(medleyDistributedIdStr);

                                }

                                log.debug("ADD_TO_MEDLEY ADDING TO MEDLEY OTHER Work Performance Wih Sequence "
                                    + currentOtherMedleyWorkPerformanceObj.getWorkSequenceNumber() + ","
                                    + currentOtherMedleyWorkPerformanceObj.getMedleySequenceNumber());

                                if (validationLookups == null) {
                                    validationLookups = this.initializeValidationLookups();
                                }

                                this.updateWorkPerformance(currentOtherMedleyWorkPerformanceObj);

                                log.debug(
                                    "ADD_TO_MEDLEY SUCCESSFULL ADDING TO MEDLEY OTHER Work Performance Wih Sequence "
                                        + currentOtherMedleyWorkPerformanceObj.getWorkSequenceNumber() + ","
                                        + currentOtherMedleyWorkPerformanceObj.getMedleySequenceNumber());

                                alreadyDoneMedleys.add(otherMedleyWorkPerformanceIdStr);
                            }
                        }
                    } // Check for the Whole Medley for Add

                    workPerformanceObj.setWorkSequenceNumber(smallestWorkSequenceNumberStr);
                    runningMedleySequenceNumber++;
                    workPerformanceObj.setMedleySequenceNumber(Integer.toString(runningMedleySequenceNumber));

                    if ((isMedleyDistributed)
                        && (UsageCommonValidations.isEmptyOrNull(workPerformanceObj.getDistributionId()))) {
                        workPerformanceObj.setDistributionId(medleyDistributedIdStr);

                    }

                    if (validationLookups == null) {
                        validationLookups = this.initializeValidationLookups();
                    }
                    this.updateWorkPerformance(workPerformanceObj);

                } else {
                    // Both are same work sequence number but check the medley Seq and then put it

                    workPerformanceObj.setWorkSequenceNumber(smallestWorkSequenceNumberStr);
                    runningMedleySequenceNumber++;
                    workPerformanceObj.setMedleySequenceNumber(Integer.toString(runningMedleySequenceNumber));

                    if ((isMedleyDistributed)
                        && (UsageCommonValidations.isEmptyOrNull(workPerformanceObj.getDistributionId()))) {
                        workPerformanceObj.setDistributionId(medleyDistributedIdStr);
                    }

                    if (validationLookups == null) {
                        validationLookups = this.initializeValidationLookups();
                    }
                    this.updateWorkPerformance(workPerformanceObj);

                }
                alreadyDoneMedleys.add(workPerformanceIdStr);
            } catch (PrepSystemException pse) {
                throw pse;
            } catch (NumberFormatException nfe) {
                log.error(nfe);
            }
        }

        log.debug(
            "Exiting WorkPerformancesList addToMedleyWorkPerformance(WorkPerformancesList workPerformancesList)  method in UsageHelperImpl ");
        log.debug("Inside addToMedleyWorkPerformance of Helper Adjustment Triggers determined for Creating are :"
            + DebugHelperUtils.debugCollections(" Adjs in AddToMedley ", adjustmentsCollection));

        tempProgramPerformanceObj = new ProgramPerformance();
        tempProgramPerformanceObj.setPerformanceHeaderId(workPerformancesList.getProgramPerformanceId());
        tempProgramPerformanceObj.setUserId(workPerformancesList.getUserId());
        this.callPerformanceBatchValidate(tempProgramPerformanceObj, UsageConstants.ADD_TO_MEDLEY);

        return outWorkPerformancesListVob;
    }

    private boolean isValueInCollection(Collection<?> collectionOfValues, String valueToCheck) {
        if (UsageCommonValidations.isEmptyOrNull(valueToCheck)) {
            return false;
        }

        if (collectionOfValues == null) {
            return false;
        }

        return collectionOfValues.contains(valueToCheck);
    }

    private Map<String, Collection<?>> initializeValidationLookups() throws PrepSystemException {

        Map<String, Collection<?>> validationLookups = new HashMap<>();

        // For Business Rule Group Members
        this.populateSingleValidationLookupTable(validationLookups, UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_ALL);
        this.populateSingleValidationLookupTable(validationLookups,
            UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_DEFAULT_MUSIC_USER);
        this.populateSingleValidationLookupTable(validationLookups,
            UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_LIVE_POP_CONCERTS);
        this.populateSingleValidationLookupTable(validationLookups,
            UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_INTERNET_STREAMING);
        this.populateSingleValidationLookupTable(validationLookups,
            UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_INTERACTIVE);
        this.populateSingleValidationLookupTable(validationLookups,
            UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_GENERAL_BACKGROUND);
        this.populateSingleValidationLookupTable(validationLookups,
            UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_FOREGROUND);
        // Use types Collections
        this.populateSingleValidationLookupTable(validationLookups, UsageConstants.VLDN_LKUPS_KEY_USE_TYPE_ALL);
        this.populateSingleValidationLookupTable(validationLookups, UsageConstants.VLDN_LKUPS_KEY_USE_TYPE_FEATURED);
        this.populateSingleValidationLookupTable(validationLookups, UsageConstants.VLDN_LKUPS_KEY_USE_TYPE_LIBRARY);
        this.populateSingleValidationLookupTable(validationLookups, UsageConstants.VLDN_LKUPS_KEY_USE_TYPE_MEDLEY);
        return validationLookups;

    }

    private Map<String, Collection<?>> populateSingleValidationLookupTable(
        Map<String, Collection<?>> validationLookupsToPopulate, String validationLookupKey) throws PrepSystemException {
        if (UsageCommonValidations.isEmptyOrNull(validationLookupKey)) {
            log.error(
                "Should Never Happen Unless calling programs are erroroneously invoking this method with null key Inside UsageHelperImpl.populateSingleValidationLookupTable()");
            throw new PrepSystemException(ERROR_COMMON_EXCEPTION);
        }
        if (validationLookupsToPopulate == null) {
            validationLookupsToPopulate = new HashMap<>();
        }
        Collection<?> tempColl = null;
        if (true) {
            tempColl = usageDAO.getValidationLookup(validationLookupKey);
            if (tempColl == null) {
                tempColl = new ArrayList<>();
            }
            validationLookupsToPopulate.put(validationLookupKey, tempColl);

        }
        return validationLookupsToPopulate;

    }

    public WorkPerformancesList updateWorkPerformancesBulk(WorkPerformancesList workPerformancesList)
        throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - updateWorkPerformancesBulk method");
        workPerformancesList = usageDAO.updateWorkPerformancesBulk(workPerformancesList);
        log.debug("Exiting UsageHelperImpl - updateWorkPerformancesBulk method");
        return workPerformancesList;
    }

    public WorkPerformancesList removeFromMedleyWorkPerformance(WorkPerformancesList workPerformancesList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug(
            "Entering WorkPerformancesList removeFromMedleyWorkPerformance(WorkPerformancesList workPerformancesList)  method in UsageHelperImpl XXXXXXXXXXXXXXXXXXXXXXXXXXxx");
        WorkPerformancesList outWorkPerformancesListVob = null;
        String[] selectedIds;
        String workPerformanceIdStr;
        long workPerformanceId;
        WorkPerformance workPerformanceObj;
        ProgramPerformance tempProgramPerformanceObj;

        int nextWorkSequenceNumber;
        String currentHighestWorkSequenceNumber;
        String isPartOfMedley;

        int totalIdsCount;

        Map<String, Collection<?>> validationLookups = null;

        if (workPerformancesList == null) {
            log.debug(
                "Program workPerformancesList is NULL IN WorkPerformancesList removeFromMedleyWorkPerformance in HELPER");
            return workPerformancesList;
        }

        if (workPerformancesList.getSelectedIds() == null) {
            log.debug(
                "Program workPerformancesList 2 is NULL IN WorkPerformancesList removeFromMedleyWorkPerformance in HELPER");
            throw new PrepFunctionalException("error.usage.removeFromMedley.nofWorkPerformancesInsufficient");
        }

        if (workPerformancesList.getSelectedIds().length == 0) {
            log.debug(
                "Program workPerformancesList 3 is NULL IN WorkPerformancesList removeFromMedleyWorkPerformance in HELPER");
            throw new PrepFunctionalException("error.usage.removeFromMedley.nofWorkPerformancesInsufficient");
        }

        if (UsageCommonValidations.isEmptyOrNull(workPerformancesList.getProgramPerformanceId())) {
            log.debug(
                "Program Performance Id is NULL IN WorkPerformancesList removeFromMedleyWorkPerformance in HELPER");
            return workPerformancesList;
        }

        selectedIds = workPerformancesList.getSelectedIds();
        totalIdsCount = selectedIds.length;

        for (int currIndex = 0; currIndex < totalIdsCount; currIndex++) {
            try {
                workPerformanceIdStr = selectedIds[currIndex];
                workPerformanceId = Long.parseLong(workPerformanceIdStr);
                workPerformanceObj = usageDAO.getWorkPerformance(workPerformanceId);

                // checking if part of Part of a Medley or not
                isPartOfMedley =
                    usageDAO.getMedleyRelatedInformation(UsageConstants.SDB_MEDLEYINFO_GET_IF_PART_OF_MEDLEY,
                        selectedIds, workPerformanceObj.getWorkSequenceNumber(), workPerformanceIdStr,
                        workPerformanceObj.getPerformanceHeaderId());

                if (!UsageCommonValidations.isEmptyOrNull(isPartOfMedley)) {
                    currentHighestWorkSequenceNumber = usageDAO.getSingleOutputInputInformation(
                        UsageConstants.SDBINF_GET_WORK_PERFORMANCE_HIGHEST_WORKSEQUENCE_NUMBER,
                        workPerformanceObj.getPerformanceHeaderId());
                    nextWorkSequenceNumber = Integer.parseInt(currentHighestWorkSequenceNumber) + 1;
                    workPerformanceObj.setWorkSequenceNumber(String.valueOf(nextWorkSequenceNumber));
                    workPerformanceObj.setMedleySequenceNumber(null);

                    if (validationLookups == null) {
                        validationLookups = this.initializeValidationLookups();
                    }

                    this.updateWorkPerformance(workPerformanceObj);

                }

            } catch (PrepSystemException pse) {
                throw pse;
            }
        }

        tempProgramPerformanceObj = new ProgramPerformance();
        tempProgramPerformanceObj.setPerformanceHeaderId(workPerformancesList.getProgramPerformanceId());
        tempProgramPerformanceObj.setUserId(workPerformancesList.getUserId());
        this.callPerformanceBatchValidate(tempProgramPerformanceObj, UsageConstants.DELETE_FROM_MEDLEY);

        log.debug(
            "Exiting WorkPerformancesList removeFromMedleyWorkPerformance(WorkPerformancesList workPerformancesList)  method in UsageHelperImpl");
        return outWorkPerformancesListVob;
    }

    public void callWorkPerformanceBatchValidate(WorkPerformance programPerformanceVobToValidate, String actionType)
        throws PrepSystemException {
        log.debug("UsageDAO callWorkPerformanceBatchValidate WorkPerformanceId"
            + programPerformanceVobToValidate.getWorkPerformanceId() + " actionType :" + actionType);
        usageDAO.callPerformanceBatchValidate(actionType, null, programPerformanceVobToValidate.getWorkPerformanceId(),
            programPerformanceVobToValidate.getUserId());
    }

    /**
     * Method getCatalogManualMatchList.
     * 
     * @param apmPerformanceBulkRequestList
     * @return outApmPerformanceBulkRequestList
     * @throws PrepSystemException
     */
    public ApmPerformanceBulkRequestList getCatalogManualMatchList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getCatalogManualMatchList method");
        ApmPerformanceBulkRequestList outApmPerformanceBulkRequestList = null;
        outApmPerformanceBulkRequestList = usageDAO.getCatalogManualMatchList(apmPerformanceBulkRequestList);
        if (outApmPerformanceBulkRequestList.getNumberOfRecordsFound() == 0
            && outApmPerformanceBulkRequestList.getCurrentPageNumber() > 1) {
            apmPerformanceBulkRequestList.setNavigationType("LAST");
            apmPerformanceBulkRequestList
                .setCurrentPageNumber(outApmPerformanceBulkRequestList.getCurrentPageNumber() - 1);
            outApmPerformanceBulkRequestList = usageDAO.getCatalogManualMatchList(apmPerformanceBulkRequestList);
        }
        log.debug("Exiting UsageHelperImpl - getCatalogManualMatchList method");
        return outApmPerformanceBulkRequestList;
    }

    /**
     * Method updateAssignedUsersToWorkPerf.
     * 
     * @param apmPerformanceBulkRequestList
     * @return apmPerformanceBulkRequestList
     * @throws PrepSystemException
     */
    public ApmPerformanceBulkRequestList updateAssignedUsersToWorkPerf(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering updateAssignedUsersToWorkPerf in UsageHelperImpl");
        log.debug("Calling dao interface updateAssignedUsersToWorkPerf(apmPerformanceBulkRequestList); XYZ:  "
            + apmPerformanceBulkRequestList.getApmPerformanceBulkRequestListType());
        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getApmPerformanceBulkRequestListType())
            && "CATALOG".equals(apmPerformanceBulkRequestList.getApmPerformanceBulkRequestListType())) {
            apmPerformanceBulkRequestList = usageDAO.updateAssignedUsersToCatalogPerf(apmPerformanceBulkRequestList);
        } else {
            apmPerformanceBulkRequestList = usageDAO.updateAssignedUsersToWorkPerf(apmPerformanceBulkRequestList);
        }
        log.debug("Exiting updateAssignedUsersToWorkPerf in UsageHelperImpl");

        return apmPerformanceBulkRequestList;
    }

    /**
     * Method updateCatalogManualMatchList.
     * 
     * @param apmPerformanceBulkRequestList
     * @return outApmPerformanceBulkRequestList
     * @throws PrepSystemException
     */
    public ApmPerformanceBulkRequestList updateCatalogManualMatchList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        long t1 = System.currentTimeMillis();
        log.debug("Entering UsageHelperImpl - updateCatalogManualMatchList method ");

        ApmPerformanceBulkRequestList outApmPerformanceBulkRequestList = null;
        ApmPerformanceBulkRequestList updApmPerformanceBulkRequestList = apmPerformanceBulkRequestList;

        List<Object> updColl = new ArrayList<>();

        if (UsageConstants.BULK_REQUEST_TYPE_UPDATE.equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {

            Collection<String> checkedWorkIds = new ArrayList<>();
            List<Object> invalidColl = new ArrayList<>();

            Iterator<?> itr = apmPerformanceBulkRequestList.getSearchResults().iterator();
            ApmPerformanceBulkRequest groupRequest = null;
            ApmPerformanceBulkRequest dbApmPerformanceBulkRequest = null;

            List<Object> alreadyMatchedCol = new ArrayList<>();

            while (itr.hasNext()) {
                groupRequest = (ApmPerformanceBulkRequest) itr.next();
                dbApmPerformanceBulkRequest = usageDAO.getCatalogManualMatch(groupRequest);
                // Do not allow if the group is already updated (matched)

                if (dbApmPerformanceBulkRequest == null) {
                    updColl.add(groupRequest);
                } else {
                    alreadyMatchedCol.add("The Title for Work ID " + groupRequest.getWorkId()
                        + " has already been matched to a different Work ID");
                    groupRequest.setWorkIdErrors(alreadyMatchedCol);
                    invalidColl.add(groupRequest);
                }
                if ((ValidationUtils.isEmptyOrNull(groupRequest.getWorkId())
                    && !ValidationUtils.isEmptyOrNull(groupRequest.getOriginalWorkId()))
                    || (this.isValueInCollection(checkedWorkIds, groupRequest.getWorkId()))) {
                    continue;
                }
                List<Object> errorColl = usageDAO.validateWorkIdForBulkMatch(groupRequest);

                if (!errorColl.isEmpty()) {
                    groupRequest.setWorkIdErrors(errorColl);
                    invalidColl.add(groupRequest);
                }
                checkedWorkIds.add(groupRequest.getWorkId());
            }
            updApmPerformanceBulkRequestList.setSearchResults(updColl);
            if (!invalidColl.isEmpty()) {
                apmPerformanceBulkRequestList.setInvalidGroups(invalidColl);
                if (log.isDebugEnabled()) {
                    log.debug(
                        "Exiting UsageHelperImpl - updateCatalogManualMatchList method,  Found Work Id Errors, time elapsed = "
                            + (System.currentTimeMillis() - t1));
                }
                return apmPerformanceBulkRequestList;
            }
        }
        usageDAO.updateCatalogManualMatchList(updApmPerformanceBulkRequestList);
        outApmPerformanceBulkRequestList =
            apmPerformanceBulkRequestList(apmPerformanceBulkRequestList, usageDAO, updApmPerformanceBulkRequestList);

        log.debug("Exiting UsageHelperImpl - updateCatalogManualMatchList method, time elapsed = "
            + (System.currentTimeMillis() - t1));
        return outApmPerformanceBulkRequestList;
    }

    private ApmPerformanceBulkRequestList apmPerformanceBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList, UsageDAO usageDAO,
        ApmPerformanceBulkRequestList updApmPerformanceBulkRequestList) throws PrepSystemException {
        ApmPerformanceBulkRequestList outApmPerformanceBulkRequestList;
        if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {
            outApmPerformanceBulkRequestList = usageDAO.deleteLearnedMatch(apmPerformanceBulkRequestList);
        }
        // if request if undelete
        else if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {
            log.debug("Request code" + apmPerformanceBulkRequestList.getRequestTypeCode());

            outApmPerformanceBulkRequestList = usageDAO.undeleteLearnedMatch(apmPerformanceBulkRequestList);

            log.debug("Final InvalidUndeleteHeaderExists "
                + outApmPerformanceBulkRequestList.getInvalidUndeleteHeaderExists());
        } else {
            outApmPerformanceBulkRequestList = usageDAO.updateLearnedkMatch(updApmPerformanceBulkRequestList);
        }
        return outApmPerformanceBulkRequestList;
    }

    /**
     * Method cloneCatalogPerformances.
     * 
     * @param apmPerformanceBulkRequestList
     * @return apmPerformanceBulkRequestList
     * @throws PrepSystemException
     */
    public ApmPerformanceBulkRequestList cloneCatalogPerformances(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering cloneCatalogPerformances in UsageHelperImpl");

        if (apmPerformanceBulkRequestList == null) {
            return null;
        } else if (apmPerformanceBulkRequestList.getMedleyWorkIds() == null
            || apmPerformanceBulkRequestList.getMedleyWorkIds().length < 0) {
            log.debug("Input work Ids is invalid");
            return null;
        } else {
            log.debug("Input OBJ:  " + apmPerformanceBulkRequestList);
        }
        Collection<String> checkedWorkIds = new ArrayList<>();
        List<Object> invalidColl = new ArrayList<>();
        ArrayList<String> updColl = new ArrayList<>();
        ArrayList<String> updCloneCountColl = new ArrayList<>();

        ApmPerformanceBulkRequest groupRequest = null;

        String[] workIds = apmPerformanceBulkRequestList.getMedleyWorkIds();
        String[] cloneCounts = apmPerformanceBulkRequestList.getMedleyCloneCounts();

        ApmPerformanceBulkRequest dbApmPerformanceBulkRequest = null;
        ApmPerformanceBulkRequest tempApmPerformanceBulkRequest = new ApmPerformanceBulkRequest();

        tempApmPerformanceBulkRequest.setWorkId("-9999999999");
        tempApmPerformanceBulkRequest.setSupplierCode(apmPerformanceBulkRequestList.getMedleySupplierCode());
        tempApmPerformanceBulkRequest.setWorkTitle(apmPerformanceBulkRequestList.getMedleyWorkTitle());
        tempApmPerformanceBulkRequest.setPerformerName(apmPerformanceBulkRequestList.getMedleyPerformerName());
        tempApmPerformanceBulkRequest.setWriterName(apmPerformanceBulkRequestList.getMedleyWriterName());

        dbApmPerformanceBulkRequest = usageDAO.getCatalogManualMatch(tempApmPerformanceBulkRequest);

        List<Object> alreadyMatchedCol = new ArrayList<>();

        int rowNum = -1;

        for (String wId : workIds) {
            rowNum++;
            groupRequest = new ApmPerformanceBulkRequest();
            groupRequest.setWorkId(wId);

            if (this.isValueInCollection(checkedWorkIds, groupRequest.getWorkId())) {
                log.debug(WORK_ID_ALREADY_VALIDATED + groupRequest.getWorkId());
                continue;
            }

            if (dbApmPerformanceBulkRequest != null) {
                alreadyMatchedCol.add("The Title has already been matched to a different Work ID");
                groupRequest.setWorkIdErrors(alreadyMatchedCol);
                invalidColl.add(groupRequest);
            }

            List<Object> errorColl = usageDAO.validateWorkIdForBulkMatch(groupRequest);

            if (!errorColl.isEmpty()) {
                groupRequest.setWorkIdErrors(errorColl);
                invalidColl.add(groupRequest);
            } else {
                updColl.add(groupRequest.getWorkId());
                updCloneCountColl.add(cloneCounts[rowNum]);
            }
            checkedWorkIds.add(groupRequest.getWorkId());
        }

        if (!invalidColl.isEmpty()) {
            apmPerformanceBulkRequestList.setInvalidGroups(invalidColl);
            if (log.isDebugEnabled()) {
                log.debug("Exiting UsageHelperImpl - cloneCatalogPerformances method,  Found Work Id Errors");
            }
            return apmPerformanceBulkRequestList;
        }

        String[] validWorkIds = updColl.toArray(new String[updColl.size()]);
        String[] validCloneCounts = updCloneCountColl.toArray(new String[updCloneCountColl.size()]);
        apmPerformanceBulkRequestList.setMedleyWorkIds(validWorkIds);
        apmPerformanceBulkRequestList.setMedleyCloneCounts(validCloneCounts);

        apmPerformanceBulkRequestList = usageDAO.cloneCatalogPerformances(apmPerformanceBulkRequestList);
        log.debug("Exiting cloneCatalogPerformances in UsageHelperImpl");
        return apmPerformanceBulkRequestList;
    }

    public EOFileList loadToAPM(EOFileList eoFileList) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - loadToAPM method ");
        if (eoFileList != null) {
            log.debug("ActiveSurveyQuarterApm " + eoFileList.getActiveSurveyQuarterApm());
            log.debug("ActiveSurveyQuarterEo " + eoFileList.getActiveSurveyQuarterEo());
            log.debug("CurrentPerformanceQuarterEO " + eoFileList.getCurrentPerformanceQuarterEO());
        }

        List<?> fileList = eoFileList.getSearchResults();

        String rollupSumCnt = usageDAO.getSumRollupCnt(eoFileList);
        log.debug("Rollup Sum count " + rollupSumCnt);

        String rollupThreshold = eoFileList.getRollupThreshold();
        log.debug("Rollup threshold count " + rollupThreshold);

        Iterator<?> itr = fileList.iterator();
        EOFile eoFile = null;
        while (itr.hasNext()) {
            eoFile = (EOFile) itr.next();
            eoFile = usageDAO.getEOBatchControl(eoFile);

            String suppcode = eoFile.getSuppCode();
            String perfqtr = eoFile.getPerfQuarter();
            String perfperiod = eoFile.getPerfPeriod();

            String validationErrorExists = usageDAO.validationErrorExists(suppcode, perfqtr, perfperiod);

            if (validationErrorExists.equalsIgnoreCase("Y")) {
                String[] errorValues = new String[1];
                errorValues[0] = eoFile.getPerfQuarter();
                // Validation error still exists
                throw new PrepFunctionalException("us.error.apm.eoload.batchcontrol.hasCorrErrors", errorValues);
            }

            String supplierType = usageDAO.getSupplierType(suppcode);

            if (!ValidationUtils.isEmptyOrNull(eoFile.getBatchId())
                && !ValidationUtils.areBothEqualIgnoreCase(supplierType, "Catalog")) {
                String[] errorValues = new String[2];
                errorValues[0] = eoFile.getSuppCode();
                errorValues[1] = eoFile.getPerfQuarter();
                // Already rolled up
                throw new PrepFunctionalException("us.error.apm.eoload.batchcontrol.exists", errorValues);
            }

        }

        // threshold validation happens here
        try {
            Long.parseLong(rollupThreshold);
        } catch (Exception e) {
            throw new PrepFunctionalException("us.error.apm.eoload.rollup.threshold.invalid");
        }

        if (!"Y".equals(eoFileList.getLoadThresholdExceeded())
            && (Long.parseLong(rollupSumCnt) > Long.parseLong(rollupThreshold))) {
            throw new PrepFunctionalException("us.error.apm.eoload.rollup.gt.threshold");
        }

        eoFileList = usageDAO.loadToAPM(eoFileList);

        log.debug("Exiting UsageHelperImpl - loadToAPM method ");

        return eoFileList;
    }

    public List<EOSupplierFormat> getApmSuppliers() throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getApmSuppliers method ");
        List<EOSupplierFormat> col = usageDAO.getApmSuppliers();
        log.debug("Exiting UsageHelperImpl - getApmSuppliers method ");
        return col;
    }

    public ApmActiveSurveyQuarter getApmActiveSurveyQuarter() throws PrepSystemException {
        return usageDAO.getApmActiveSurveyQuarter();

    }

    public EOFileList getEOFileList(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getEOFileList method ");
        eoFileList = usageDAO.getEOFileList(eoFileList);
        log.debug("Exiting UsageHelperImpl - getEOFileList method ");
        return eoFileList;
    }

    public String getRollupThreshold() throws PrepSystemException {
        return usageDAO.getRollupThreshold();

    }

    public EOFileList updateFileInventory(EOFileList eoFileList) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - updateFileInventory method ");
        if (eoFileList != null) {
            log.debug("ActiveSurveyQuarterApm " + eoFileList.getActiveSurveyQuarterApm());
            log.debug("ActiveSurveyQuarterEo " + eoFileList.getActiveSurveyQuarterEo());
            log.debug("CurrentPerformanceQuarterEO " + eoFileList.getCurrentPerformanceQuarterEO());
        }

        List<?> fileList = eoFileList.getSearchResults();
        Iterator<?> itr = fileList.iterator();
        EOFile eoFile = null;
        while (itr.hasNext()) {
            eoFile = (EOFile) itr.next();
            eoFile = usageDAO.getEOBatchControl(eoFile);
            String suppcode = eoFile.getSuppCode();
            String perfqtr = eoFile.getPerfQuarter();

            String validationErrorExists = usageDAO.validationErrorExists(suppcode, perfqtr);

            if (validationErrorExists.equalsIgnoreCase("N")) {
                String[] errorValues = new String[1];
                errorValues[0] = eoFile.getPerfQuarter();
                // Validation error does not exists
                throw new PrepFunctionalException("us.error.apm.eoload.batchcontrol.noValidationErrorsToProcess",
                    errorValues);
            }

            String supplierType = usageDAO.getSupplierType(suppcode);

            if (!ValidationUtils.isEmptyOrNull(eoFile.getBatchId())
                && !ValidationUtils.areBothEqualIgnoreCase(supplierType, "Catalog")) {
                String[] errorValues = new String[2];
                errorValues[0] = eoFile.getSuppCode();
                errorValues[1] = eoFile.getPerfQuarter();
                // Already rolled up
                throw new PrepFunctionalException("us.error.apm.eoload.batchcontrol.exists", errorValues);
            }

        }

        eoFileList = usageDAO.updateFileInventory(eoFileList);

        log.debug("Exiting UsageHelperImpl - updateFileInventory method ");

        return eoFileList;
    }

    public EOFileList loadToEO(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - loadToEO method ");
        eoFileList = usageDAO.loadToEO(eoFileList);
        log.debug("Exiting UsageHelperImpl - loadToEO method ");
        return eoFileList;
    }

    public LogRequestSummary getLogRequestSummary(LogRequestSummary logRequestSummary) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getLogRequestSummary method ");
        LogRequestSummary outputLogRequestSummary = usageDAO.getLogRequestSummary(logRequestSummary);
        log.debug("Exiting UsageHelperImpl - getLogRequestSummary method ");
        return outputLogRequestSummary;
    }

    public LogRequestSummary updateLogRequestSummary(LogRequestSummary logRequestSummary) throws PrepSystemException {

        log.debug("Entering UsageHelperImpl - updateLogRequestSummary method ");
        LogRequestSummary outputLogRequestSummary = usageDAO.updateLogRequestSummary(logRequestSummary);
        log.debug("Exiting UsageHelperImpl - updateLogRequestSummary method ");
        return outputLogRequestSummary;
    }

    /**
     * Method getSamplingSummary.
     * 
     * @param samplingSummary
     * @return SamplingSummary
     * @throws PrepSystemException
     */
    public SamplingSummary getSamplingSummary(SamplingSummary samplingSummary) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getSamplingSummary method ");
        SamplingSummary outSamplingDetails = usageDAO.getSamplingSummary(samplingSummary);
        log.debug("Exiting UsageHelperImpl - getSamplingSummary method ");
        return outSamplingDetails;
    }

    public ApmPerformanceBulkRequestList getApmPerfBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getApmPerfBulkRequestList method ");
        ApmPerformanceBulkRequestList outApmPerformanceBulkRequestList = null;
        outApmPerformanceBulkRequestList = usageDAO.getApmPerfBulkRequestList(apmPerformanceBulkRequestList);
        if (outApmPerformanceBulkRequestList.getNumberOfRecordsFound() == 0
            && outApmPerformanceBulkRequestList.getCurrentPageNumber() > 1) {
            apmPerformanceBulkRequestList.setNavigationType("LAST");
            apmPerformanceBulkRequestList
                .setCurrentPageNumber(outApmPerformanceBulkRequestList.getCurrentPageNumber() - 1);
            outApmPerformanceBulkRequestList = usageDAO.getApmPerfBulkRequestList(apmPerformanceBulkRequestList);
        }
        log.debug("Exiting UsageHelperImpl - getApmPerfBulkRequestList method");
        return outApmPerformanceBulkRequestList;
    }

    public ApmFileList seacrhFiles(ApmFileList apmFileList) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - seacrhFiles method");
        ApmFileList outApmFileList = null;
        outApmFileList = usageDAO.seacrhFiles(apmFileList);
        log.debug("Exiting UsageHelperImpl - seacrhFiles method");
        return outApmFileList;
    }

    public MusicUserSearch searchMusicUser(MusicUserSearch musicUserSearch) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - searchMusicUser method");
        musicUserSearch = usageDAO.searchMusicUser(musicUserSearch);
        log.debug("Exiting UsageHelperImpl - searchMusicUser method");
        return musicUserSearch;
    }

    public LogUsageSummary getLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getLogUsageSummary method ");
        LogUsageSummary outputLogUsageSummary = usageDAO.getLogUsageSummary(logUsageSummary);
        log.debug("Exiting UsageHelperImpl - getLogUsageSummary method ");
        return outputLogUsageSummary;
    }

    public LogUsageSummary insertLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - insertLogUsageSummary method ");
        LogUsageSummary outputLogUsageSummary = usageDAO.insertLogUsageSummary(logUsageSummary);
        log.debug("Exiting UsageHelperImpl - insertLogUsageSummary method ");
        return outputLogUsageSummary;
    }

    public LogUsageSummary updateLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - updateLogUsageSummary method ");

        String operationtype = logUsageSummary.getOperationType();
        log.debug("operationtype: " + operationtype);
        LogUsageSummary outputLogUsageSummary = null;
        if (operationtype.equalsIgnoreCase("UPDATE"))
            outputLogUsageSummary = usageDAO.updateLogUsageSummary(logUsageSummary);
        else {
            if (operationtype.equalsIgnoreCase("DELETE"))
                outputLogUsageSummary = usageDAO.deleteLogUsageSummary(logUsageSummary);
            else {
                // for navigation
                outputLogUsageSummary = usageDAO.getLogUsageSummary(logUsageSummary);
            }
        }
        log.debug("Exiting UsageHelperImpl - updateLogUsageSummary method ");
        return outputLogUsageSummary;
    }

    /**
     * Method getExemptionList.
     * 
     * @param exemptionList
     * @return ExemptionList
     * @throws PrepSystemException
     */
    public ExemptionList getExemptionList(ExemptionList exemptionList) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getExemptionList method");
        exemptionList = usageDAO.getExemptionList(exemptionList);
        log.debug("Exiting UsageHelperImpl - getExemptionList method");
        return exemptionList;

    }

    /**
     * Method addExemption.
     * 
     * @param exemption
     * @return Exemption
     * @throws PrepSystemException
     */
    public Exemption addExemption(Exemption exemption) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - addExemption method");
        if (WRK_ID.equals(exemption.getExemptionType()) || "PTY_ID".equals(exemption.getExemptionType())
            || "PROVIDER_ID".equals(exemption.getExemptionType())) {
            if (exemption.getExemptionValue().length() > 15) {
                throw new PrepFunctionalException(ERROR_APM_EXEMPTION_NON_NUMERIC);
            }
            if (!ValidationUtils.isDigitsOnly(exemption.getExemptionValue())) {
                throw new PrepFunctionalException(ERROR_APM_EXEMPTION_NON_NUMERIC);
            }
        }
        if (WRK_ID.equals(exemption.getExemptionType()) && !usageDAO.isValidWorkId(exemption.getExemptionValue())) {
            throw new PrepFunctionalException("error.usage.workPerformance.invalid.workId");
        }
        Exemption dbExemption = usageDAO.getExemptionByValues(exemption);
        if (dbExemption != null) {
            throw new PrepFunctionalException("us.error.apm.exemption.exists");
        }
        exemption = usageDAO.addExemption(exemption);
        log.debug("Exiting UsageHelperImpl - addExemption method");
        return exemption;
    }

    /**
     * Method getExemption.
     * 
     * @param exemption
     * @return Exemption
     * @throws PrepSystemException
     */
    public Exemption getExemption(Exemption exemption) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getExemption method");
        exemption = usageDAO.getExemption(exemption);
        log.debug("Exiting UsageHelperImpl - getExemption method");
        return exemption;
    }

    /**
     * Method updateExemption.
     * 
     * @param exemption
     * @return Exemption
     * @throws PrepSystemException
     */
    public Exemption updateExemption(Exemption exemption) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - updateExemption method");
        if (WRK_ID.equals(exemption.getExemptionType()) || "PTY_ID".equals(exemption.getExemptionType())
            || "PROVIDER_ID".equals(exemption.getExemptionType())) {
            if (exemption.getExemptionValue().length() > 15) {
                throw new PrepFunctionalException(ERROR_APM_EXEMPTION_NON_NUMERIC);
            }
            if (!ValidationUtils.isDigitsOnly(exemption.getExemptionValue())) {
                throw new PrepFunctionalException(ERROR_APM_EXEMPTION_NON_NUMERIC);
            }
        }
        if (WRK_ID.equals(exemption.getExemptionType()) && (!usageDAO.isValidWorkId(exemption.getExemptionValue()))) {
            throw new PrepFunctionalException("error.usage.workPerformance.invalid.workId");
        }
        Exemption dbExemption = usageDAO.getExemptionByValues(exemption);
        if (dbExemption != null) {
            throw new PrepFunctionalException("us.error.apm.exemption.exists");
        }
        exemption = usageDAO.updateExemption(exemption);
        log.debug("Exiting UsageHelperImpl - updateExemption method");
        return exemption;
    }

    /**
     * Method deleteExemptions.
     * 
     * @param exemptionList
     * @return ExemptionList
     * @throws PrepSystemException
     */
    public ExemptionList deleteExemptions(ExemptionList exemptionList) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - deleteExemptions method");
        exemptionList = usageDAO.deleteExemptions(exemptionList);
        log.debug("Exiting UsageHelperImpl - deleteExemptions method");
        return exemptionList;
    }

    /**
     * Method cancelSampling.
     * 
     * @param samplingSummary
     * @return SamplingSummary
     * @throws PrepSystemException
     */
    public SamplingSummary cancelSampling(SamplingSummary samplingSummary) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - cancelSampling method ");
        samplingSummary = usageDAO.cancelSampling(samplingSummary);
        log.debug("Exiting UsageHelperImpl - cancelSampling method ");
        return samplingSummary;
    }

    /**
     * Method getSamplingDetails.
     * 
     * @param samplingSummary
     * @return SamplingSummary
     * @throws PrepSystemException
     */
    public SamplingSummary getSamplingDetails(SamplingSummary samplingSummary) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getSamplingDetails method ");
        SamplingSummary outSamplingDetails = usageDAO.getSamplingDetails(samplingSummary);
        log.debug("Exiting UsageHelperImpl - getSamplingDetails method ");
        return outSamplingDetails;
    }

    /**
     * Method updateSamplingSummary.
     * 
     * @param samplingRequest
     * @return SamplingRequest
     * @throws PrepSystemException
     */
    public SamplingRequest updateSamplingSummary(SamplingRequest samplingRequest) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - updateSamplingSummary method ");
        samplingRequest = usageDAO.updateSamplingSummary(samplingRequest);
        log.debug("Exiting UsageHelperImpl - updateSamplingSummary method ");
        return samplingRequest;
    }

    /**
     * Method addSamplingRequest.
     * 
     * @param samplingRequest
     * @return SamplingRequest
     * @throws PrepSystemException
     */
    public SamplingRequest addSamplingRequest(SamplingRequest samplingRequest) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - addSamplingRequest method ");
        samplingRequest = usageDAO.addSamplingRequest(samplingRequest);
        log.debug("Exiting UsageHelperImpl - addSamplingRequest method ");
        return samplingRequest;
    }

    /**
     * Method updateSamplingResults.
     * 
     * @param samplingSummary
     * @return SamplingSummary
     * @throws PrepSystemException
     */
    public SamplingSummary updateSamplingResults(SamplingSummary samplingSummary) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - updateSamplingResults method ");
        samplingSummary = usageDAO.updateSamplingResults(samplingSummary);
        log.debug("Exiting UsageHelperImpl - updateSamplingResults method ");
        return samplingSummary;
    }

    /**
     * Method bypassSampling.
     * 
     * @param samplingRequest
     * @return SamplingRequest
     * @throws PrepSystemException
     */
    public SamplingRequest bypassSampling(SamplingRequest samplingRequest) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - bypassSampling method ");
        samplingRequest = usageDAO.bypassSampling(samplingRequest);
        log.debug("Exiting UsageHelperImpl - bypassSampling method ");
        return samplingRequest;
    }

    public ApmPerformanceBulkRequestList
        cloneWorkPerformances(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering cloneWorkPerformances in UsageHelperImpl");

        if (apmPerformanceBulkRequestList == null) {
            return null;
        } else if (apmPerformanceBulkRequestList.getMedleyWorkIds() == null) {
            log.debug("Input work Ids is invalid");
            return null;
        } else {
            log.debug("Input OBJ:  " + apmPerformanceBulkRequestList);
        }
        List<Object> checkedWorkIds = new ArrayList<>();
        List<Object> invalidColl = new ArrayList<>();
        ArrayList<String> updColl = new ArrayList<>();
        ArrayList<String> updCloneCountColl = new ArrayList<>();
        ApmPerformanceBulkRequest groupRequest = null;
        String[] workIds = apmPerformanceBulkRequestList.getMedleyWorkIds();
        String[] cloneCounts = apmPerformanceBulkRequestList.getMedleyCloneCounts();
        ApmPerformanceBulkRequest dbApmPerformanceBulkRequest = null;
        ApmPerformanceBulkRequest tempApmPerformanceBulkRequest = new ApmPerformanceBulkRequest();
        tempApmPerformanceBulkRequest.setWorkId("-9999999999");
        tempApmPerformanceBulkRequest.setSupplierCode(apmPerformanceBulkRequestList.getMedleySupplierCode());
        tempApmPerformanceBulkRequest.setWorkTitle(apmPerformanceBulkRequestList.getMedleyWorkTitle());
        tempApmPerformanceBulkRequest.setPerformerName(apmPerformanceBulkRequestList.getMedleyPerformerName());
        tempApmPerformanceBulkRequest.setWriterName(apmPerformanceBulkRequestList.getMedleyWriterName());
        dbApmPerformanceBulkRequest = usageDAO.getApmPerfBulkRequest(tempApmPerformanceBulkRequest);
        List<Object> alreadyMatchedCol = new ArrayList<>();
        int rowNum = -1;
        for (String wId : workIds) {
            rowNum++;
            groupRequest = new ApmPerformanceBulkRequest();
            groupRequest.setWorkId(wId);
            if (this.isValueInCollection(checkedWorkIds, groupRequest.getWorkId())) {
                log.debug(WORK_ID_ALREADY_VALIDATED + groupRequest.getWorkId());
                continue;
            }
            if (dbApmPerformanceBulkRequest != null) {
                alreadyMatchedCol.add("The Title has already been matched to a different Work ID");
                groupRequest.setWorkIdErrors(alreadyMatchedCol);
                invalidColl.add(groupRequest);
            }
            if (!invalidColl.isEmpty()) {
                apmPerformanceBulkRequestList.setInvalidGroups(invalidColl);
                log.debug("Exiting UsageHelperImpl - cloneWorkPerformances method,  Found Work Id Errors");
                return apmPerformanceBulkRequestList;
            }
            List<Object> errorColl = usageDAO.validateWorkIdForBulkMatch(groupRequest);
            if (!errorColl.isEmpty()) {
                groupRequest.setWorkIdErrors(errorColl);
                invalidColl.add(groupRequest);
            } else {
                updColl.add(groupRequest.getWorkId());
                updCloneCountColl.add(cloneCounts[rowNum]);
            }
            checkedWorkIds.add(groupRequest.getWorkId());
        }

        if (!invalidColl.isEmpty()) {
            apmPerformanceBulkRequestList.setInvalidGroups(invalidColl);
            log.debug("Exiting UsageHelperImpl - cloneWorkPerformances method,  Found Work Id Errors");
            return apmPerformanceBulkRequestList;
        }

        String[] validWorkIds = updColl.toArray(new String[updColl.size()]);
        String[] validCloneCounts = updCloneCountColl.toArray(new String[updCloneCountColl.size()]);
        apmPerformanceBulkRequestList.setMedleyWorkIds(validWorkIds);
        apmPerformanceBulkRequestList.setMedleyCloneCounts(validCloneCounts);
        apmPerformanceBulkRequestList = usageDAO.cloneWorkPerformances(apmPerformanceBulkRequestList);
        log.debug("Exiting cloneWorkPerformances in UsageHelperImpl");
        return apmPerformanceBulkRequestList;
    }

    /**
     * Method getMedleyWorkInformation.
     * 
     * @param apmPerformanceBulkRequestList
     * @return apmPerformanceBulkRequestList
     * @throws PrepSystemException
     */
    public ApmPerformanceBulkRequestList getMedleyWorkInformation(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        apmPerformanceBulkRequestList = usageDAO.getMedleyWorkInformation(apmPerformanceBulkRequestList);
        return apmPerformanceBulkRequestList;

    }

    public ApmPerformanceBulkRequestList updateApmPerfBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        long t1 = System.currentTimeMillis();
        log.debug("Entering UsageHelperImpl - updateApmPerfBulkRequestList method ");
        ApmPerformanceBulkRequestList outApmPerformanceBulkRequestList = null;
        ApmPerformanceBulkRequestList updApmPerformanceBulkRequestList = apmPerformanceBulkRequestList;
        List<Object> updColl = new ArrayList<>();
        if (UsageConstants.BULK_REQUEST_TYPE_UPDATE.equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {
            List<String> checkedWorkIds = new ArrayList<>();
            List<Object> invalidColl = new ArrayList<>();
            Iterator<?> itr = apmPerformanceBulkRequestList.getSearchResults().iterator();
            ApmPerformanceBulkRequest groupRequest = null;
            ApmPerformanceBulkRequest dbApmPerformanceBulkRequest = null;
            List<Object> alreadyMatchedCol = new ArrayList<>();
            while (itr.hasNext()) {
                groupRequest = (ApmPerformanceBulkRequest) itr.next();
                dbApmPerformanceBulkRequest = usageDAO.getApmPerfBulkRequest(groupRequest);
                if (dbApmPerformanceBulkRequest == null) {
                    updColl.add(groupRequest);
                } else {
                    alreadyMatchedCol.add("The Title for Work ID " + groupRequest.getWorkId()
                        + " has already been matched to a different Work ID");
                    groupRequest.setWorkIdErrors(alreadyMatchedCol);
                    invalidColl.add(groupRequest);
                }
                if ((ValidationUtils.isEmptyOrNull(groupRequest.getWorkId())
                    && !ValidationUtils.isEmptyOrNull(groupRequest.getOriginalWorkId()))
                    || (this.isValueInCollection(checkedWorkIds, groupRequest.getWorkId()))) {
                    continue;
                }
                List<Object> errorColl = usageDAO.validateWorkIdForBulkMatch(groupRequest);
                if (!errorColl.isEmpty()) {
                    groupRequest.setWorkIdErrors(errorColl);
                    invalidColl.add(groupRequest);
                }
                checkedWorkIds.add(groupRequest.getWorkId());
            }
            updApmPerformanceBulkRequestList.setSearchResults(updColl);
            if (!invalidColl.isEmpty()) {
                apmPerformanceBulkRequestList.setInvalidGroups(invalidColl);
                if (log.isDebugEnabled()) {
                    log.debug(
                        "Exiting UsageHelperImpl - updateApmPerfBulkRequestList method,  Found Work Id Errors, time elapsed = "
                            + (System.currentTimeMillis() - t1));
                }
                return apmPerformanceBulkRequestList;
            }
        }

        usageDAO.updateApmPerfBulkRequestList(updApmPerformanceBulkRequestList);
        outApmPerformanceBulkRequestList =
            apmPerformanceBulkRequestList(apmPerformanceBulkRequestList, usageDAO, updApmPerformanceBulkRequestList);
        log.debug("Exiting UsageHelperImpl - updateApmPerfBulkRequestList method, time elapsed = "
            + (System.currentTimeMillis() - t1));
        return outApmPerformanceBulkRequestList;
    }

    public EOLearnedMatchList deleteEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList)
        throws PrepSystemException {
        log.debug("Entering deleteEOLearnedMatchList in UsageHelperImpl");
        eoLearnedMatchList = usageDAO.deleteEOLearnedMatchList(eoLearnedMatchList);
        log.debug("Exiting deleteEOLearnedMatchList in UsageHelperImpl");
        return eoLearnedMatchList;

    }

    public EOLearnedMatchList getEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList) throws PrepSystemException {
        log.debug("Entering getEOLearnedMatchList in UsageHelperImpl");
        eoLearnedMatchList = usageDAO.getEOLearnedMatchList(eoLearnedMatchList);
        log.debug("Exiting getEOLearnedMatchList in UsageHelperImpl");
        return eoLearnedMatchList;
    }

    public List<ApmWork> validateApmWorks(List<ApmWork> col) throws PrepSystemException {
        log.debug("Entering validateApmWorks in UsageHelperImpl");
        List<ApmWork> outCol = usageDAO.validateApmWorks(col);
        log.debug("Exiting validateApmWorks in UsageHelperImpl");
        return outCol;
    }

    public EOLearnedMatchList updateEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList)
        throws PrepSystemException {
        log.debug("Entering updateEOLearnedMatchList in UsageHelperImpl");
        eoLearnedMatchList = usageDAO.updateEOLearnedMatchList(eoLearnedMatchList);
        log.debug("Exiting updateEOLearnedMatchList in UsageHelperImpl");
        return eoLearnedMatchList;
    }

    public EOLearnedMatch getEOLearnedMatchMedleyWorkInformation(EOLearnedMatchList eoLearnedMatchList)
        throws PrepSystemException {
        log.debug("Entering getEOLearnedMatchMedleyWorkInformation in UsageHelperImpl");
        EOLearnedMatch eoLearnedMatch = usageDAO.getEOLearnedMatchMedleyWorkInformation(eoLearnedMatchList);
        log.debug("Exiting getEOLearnedMatchMedleyWorkInformation in UsageHelperImpl");
        return eoLearnedMatch;
    }

    public String getMultWorkIdSequence() throws PrepSystemException {
        log.debug("Entering getMultWorkIdSequence in UsageHelperImpl");
        String multWorkId = usageDAO.getSequence("MULT_WRK_ID");
        log.debug("Entering getMultWorkIdSequence in UsageHelperImpl");
        return multWorkId;
    }

    public EOLearnedMatch addEOLearnedMatch(EOLearnedMatch eoLearnedMatch)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addEOLearnedMatch in UsageHelperImpl");
        EOLearnedMatch dbLearnedMatch = usageDAO.getEOLearnedMatch(eoLearnedMatch);
        if (dbLearnedMatch != null) {
            String[] errorValues = new String[1];
            log.debug("Learned Match exists with workid " + dbLearnedMatch.getWorkId());
            errorValues[0] = dbLearnedMatch.getWorkId();
            throw new PrepFunctionalException("us.error.apm.learnedmatch.exists", errorValues);
        }

        if ("ISRC".equals(eoLearnedMatch.getMatchType()) || "ISWC".equals(eoLearnedMatch.getMatchType())) {
            String industryId =
                usageDAO.validateIndustryIds(eoLearnedMatch.getMatchType(), eoLearnedMatch.getMatchIdValue());
            if (ValidationUtils.isEmptyOrNull(industryId)) {
                throw new PrepFunctionalException("us.error.apm.learnedmatch.industryid.invalid",
                    new String[] {eoLearnedMatch.getMatchType()});
            }
        }
        eoLearnedMatch = usageDAO.addEOLearnedMatch(eoLearnedMatch);
        log.debug("Exiting addEOLearnedMatch in UsageHelperImpl");
        return eoLearnedMatch;
    }

    public EOLearnedMatch updateEOLearnedMatchMultiple(EOLearnedMatch eoLearnedMatch) throws PrepSystemException {
        long t1 = System.currentTimeMillis();
        log.debug("Entering UsageHelperImpl - updateEOApmLearnedMatchMultiple method ");
        if (eoLearnedMatch == null) {
            log.debug("Invalid Input Object..........");
            throw new PrepSystemException(ERROR_COMMON_EXCEPTION);
        }
        eoLearnedMatch = usageDAO.updateEOLearnedMatchMultiple(eoLearnedMatch);

        log.debug("Exiting UsageHelperImpl - updateEOApmLearnedMatchMultiple method, time elapsed = "
            + (System.currentTimeMillis() - t1));
        return eoLearnedMatch;
    }

    @Override
    public ApmLearnedMatch getApmLearnedMatch(ApmLearnedMatch apmLearnedMatch) throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getApmLearnedMatch method");
        ApmLearnedMatch outApmLearnedMatch = null;
        apmLearnedMatch.setRequestId(UsageConstants.APM_LEARNED_MATCH_REQ_TYP_GET);
        outApmLearnedMatch = usageDAO.getApmLearnedMatch(apmLearnedMatch);
        log.debug("Exiting UsageHelperImpl - getApmLearnedMatch method");
        return outApmLearnedMatch;
    }

    @Override
    public ApmLearnedMatchList getApmLearnedMatchList(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException {
        log.debug("Entering UsageHelperImpl - getApmLearnedMatchList method");
        ApmLearnedMatchList outApmLearnedMatchList = null;

        outApmLearnedMatchList = usageDAO.getApmLearnedMatchList(apmLearnedMatchList);
        if (outApmLearnedMatchList.getNumberOfRecordsFound() == 0
            && outApmLearnedMatchList.getCurrentPageNumber() > 1) {
            outApmLearnedMatchList.setNavigationType("LAST");
            outApmLearnedMatchList.setCurrentPageNumber(outApmLearnedMatchList.getCurrentPageNumber() - 1);
            outApmLearnedMatchList = usageDAO.getApmLearnedMatchList(apmLearnedMatchList);
        }

        log.debug("Exiting UsageHelperImpl - getApmLearnedMatchList method");
        return outApmLearnedMatchList;
    }

    @Override
    public ApmLearnedMatchList deleteApmLearnedMatchList(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - deleteApmLearnedMatchList method ");
        boolean medleyDelete = false;
        List<Object> col = apmLearnedMatchList.getSearchResults();
        if (col != null && !col.isEmpty()) {
            ApmLearnedMatch alm = null;
            Iterator<Object> itr = col.iterator();
            if (itr.hasNext()) {
                alm = (ApmLearnedMatch) itr.next();
                if (!ValidationUtils.isEmptyOrNull(alm.getMultWorkId())) {
                    log.debug("Setting for Multiple Delete medely mult work id  " + alm.getMultWorkId());
                    medleyDelete = true;
                }
            }
        }

        ApmLearnedMatchList outApmPerformanceBulkRequestList = null;
        if (medleyDelete) {
            outApmPerformanceBulkRequestList = usageDAO.deleteApmLearnedMatchListMultiple(apmLearnedMatchList);
        } else {
            outApmPerformanceBulkRequestList = usageDAO.deleteApmLearnedMatchList(apmLearnedMatchList);
        }

        log.debug("Exiting UsageHelperImpl - deleteApmLearnedMatchList method");
        return outApmPerformanceBulkRequestList;
    }

    @Override
    public ApmLearnedMatchList updateApmLearnedMatch(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException, PrepFunctionalException {
        long t1 = System.currentTimeMillis();
        log.debug("Entering UsageHelperImpl - updateApmLearnedMatch method ");
        ApmLearnedMatchList outApmPerformanceBulkRequestList = null;
        Collection<String> checkedWorkIds = new ArrayList<>();
        List<Object> invalidColl = new ArrayList<>();
        Iterator<Object> itr = apmLearnedMatchList.getSearchResults().iterator();
        ApmLearnedMatch groupRequest = null;
        while (itr.hasNext()) {
            groupRequest = (ApmLearnedMatch) itr.next();
            if (ValidationUtils.isEmptyOrNull(groupRequest.getWorkId())
                && !ValidationUtils.isEmptyOrNull(groupRequest.getOriginalWorkId())) {
                continue;
            }
            if (this.isValueInCollection(checkedWorkIds, groupRequest.getWorkId())) {
                log.debug(WORK_ID_ALREADY_VALIDATED + groupRequest.getWorkId());
                continue;
            }
            ApmPerformanceBulkRequest tempRequest = new ApmPerformanceBulkRequest();
            tempRequest.setWorkId(groupRequest.getWorkId());
            List<Object> errorColl = usageDAO.validateWorkIdForBulkMatch(tempRequest);
            if (!errorColl.isEmpty()) {
                groupRequest.setWorkIdErrors(errorColl);
                invalidColl.add(groupRequest);
            }
            checkedWorkIds.add(groupRequest.getWorkId());
        }

        if (!invalidColl.isEmpty()) {
            apmLearnedMatchList.setInvalidGroups(invalidColl);
            log.debug(
                "Exiting UsageHelperImpl - updateApmPerfBulkRequestList method,  Found Work Id Errors, time elapsed = "
                    + (System.currentTimeMillis() - t1));
            return apmLearnedMatchList;
        }
        outApmPerformanceBulkRequestList = usageDAO.updateApmLearnedMatchList(apmLearnedMatchList);
        log.debug("Exiting UsageHelperImpl - updateApmPerfBulkRequestList method, time elapsed = "
            + (System.currentTimeMillis() - t1));
        return outApmPerformanceBulkRequestList;
    }

    @Override
    public ApmLearnedMatch getLearnedMatchMedleyWorkInformation(ApmLearnedMatchList apmLearnedMatchList)
        throws PrepSystemException {
        return usageDAO.getLearnedMatchMedleyWorkInformation(apmLearnedMatchList);
    }

    @Override
    public ApmLearnedMatch updateApmLearnedMatchMultiple(ApmLearnedMatch apmLearnedMatch)
        throws PrepSystemException, PrepFunctionalException {
        long t1 = System.currentTimeMillis();
        log.debug("Entering UsageHelperImpl - updateApmLearnedMatchMultiple method ");
        if (apmLearnedMatch == null || ValidationUtils.isEmptyOrNull(apmLearnedMatch.getLearnedMatchType())) {
            log.debug("Cannot determine Learned Match Type, must be APM_LEARNED_MATCH or APM_LEARNED_MATCH_WTR");
            throw new PrepSystemException(ERROR_COMMON_EXCEPTION);
        }

        Collection<String> checkedWorkIds = new ArrayList<>();

        Iterator<String> itr = apmLearnedMatch.getWorkIdCollection().iterator();
        String workId = null;
        List<Object> allErrorColl = new ArrayList<>();
        while (itr.hasNext()) {
            workId = itr.next();

            if (this.isValueInCollection(checkedWorkIds, workId)) {
                log.debug(WORK_ID_ALREADY_VALIDATED + workId);
                continue;
            }

            ApmPerformanceBulkRequest tempRequest = new ApmPerformanceBulkRequest();

            tempRequest.setWorkId(workId);
            List<Object> errorColl = usageDAO.validateWorkIdForBulkMatch(tempRequest);

            if (!errorColl.isEmpty()) {
                Iterator<Object> itx = errorColl.iterator();
                while (itx.hasNext()) {
                    allErrorColl.add((String) itx.next());
                }
            }
            checkedWorkIds.add(workId);
        }

        if (!allErrorColl.isEmpty()) {
            apmLearnedMatch.setWorkIdErrors(allErrorColl);
            if (log.isDebugEnabled()) {
                log.debug(
                    "Exiting UsageHelperImpl - updateApmLearnedMatchMultiple method,  Found Work Id Errors, time elapsed = "
                        + (System.currentTimeMillis() - t1));
            }
            return apmLearnedMatch;
        }
        apmLearnedMatch = usageDAO.updateApmLearnedMatchMultiple(apmLearnedMatch);

        log.debug("Exiting UsageHelperImpl - updateApmLearnedMatchMultiple method, time elapsed = "
            + (System.currentTimeMillis() - t1));
        return apmLearnedMatch;
    }

    @Override
    public ApmLearnedMatch addApmLearnedMatch(ApmLearnedMatch apmLearnedMatch)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - addApmLearnedMatch method");
        List<String> workList = apmLearnedMatch.getWorkIdCollection();
        String[] cloneCounts = apmLearnedMatch.getMedleyCloneCounts();
        if (workList != null && workList.size() > 1) {
            return addApmLearnedMatchMultiple(apmLearnedMatch);
        }

        log.debug("Checking work Id exists for w:" + workList.size());
        apmLearnedMatch.setRequestId(UsageConstants.APM_LEARNED_MATCH_REQ_TYP_ADD);
        ApmLearnedMatch dbLearnedMatch = usageDAO.getApmLearnedMatch(apmLearnedMatch);
        if (dbLearnedMatch != null && "N".equals(dbLearnedMatch.getDeleteFlag())) {
            String[] workIds = new String[1];
            workIds[0] = dbLearnedMatch.getWorkId();
            if ("Y".equals(dbLearnedMatch.getLearnedDeleteFlag())) {
                throw new PrepFunctionalException("us.error.apm.learneddelete.exists");
            } else {
                throw new PrepFunctionalException("us.error.apm.learnedmatch.exists", workIds);
            }
        }

        if ("N".equals(apmLearnedMatch.getLearnedDeleteFlag())) {
            apmLearnedMatch.setWorkId(workList.get(0));
            apmLearnedMatch.setCloneCount(cloneCounts[0]);
            ApmWork work = usageDAO.validateWorkIdForLearnedMatch(apmLearnedMatch);

            ArrayList<String> errorList = new ArrayList<>();
            if (work == null) {
                errorList.add("NOT_FOUND");
            } else {
                if ("INF".equals(work.getWorkTypeCode())) {
                    errorList.add("INF");
                }
                if ("Y".equals(work.getPublicDomainIndicator())) {
                    errorList.add("PUB_DOM");
                }
                if (0 == work.getWorkGradingValue()) {
                    errorList.add("WRK_GRD_VAL");
                }
            }

            if (!errorList.isEmpty()) {
                String[] errorsArray = errorList.toArray(new String[errorList.size()]);
                throw new PrepFunctionalException("error.apm.learnedmatch.invalid.workID", errorsArray);
            }
        }

        ApmLearnedMatch outApmLearnedMatch = null;

        if (dbLearnedMatch != null && "Y".equals(dbLearnedMatch.getDeleteFlag())) {
            outApmLearnedMatch = usageDAO.updateApmLearnedMatch(apmLearnedMatch);
        } else {
            outApmLearnedMatch = usageDAO.addApmLearnedMatch(apmLearnedMatch);
        }

        log.debug("Exiting UsageHelperImpl - addApmLearnedMatch method");
        return outApmLearnedMatch;
    }

    public ApmLearnedMatch addApmLearnedMatchMultiple(ApmLearnedMatch apmLearnedMatch)
        throws PrepSystemException, PrepFunctionalException {
        long t1 = System.currentTimeMillis();
        log.debug("Entering UsageHelperImpl - addApmLearnedMatchMultiple method ");

        Collection<String> checkedWorkIds = new ArrayList<>();

        Iterator<String> itr = apmLearnedMatch.getWorkIdCollection().iterator();
        String workId = null;
        List<Object> allErrorColl = new ArrayList<>();
        while (itr.hasNext()) {
            workId = itr.next();

            if (this.isValueInCollection(checkedWorkIds, workId)) {
                log.debug(WORK_ID_ALREADY_VALIDATED + workId);
                continue;
            }

            ApmPerformanceBulkRequest tempRequest = new ApmPerformanceBulkRequest();

            tempRequest.setWorkId(workId);
            List<Object> errorColl = usageDAO.validateWorkIdForBulkMatch(tempRequest);

            if (!errorColl.isEmpty()) {
                Iterator<Object> itx = errorColl.iterator();
                while (itx.hasNext()) {
                    allErrorColl.add((String) itx.next());
                }
            }
            checkedWorkIds.add(workId);
        }

        if (!allErrorColl.isEmpty()) {
            apmLearnedMatch.setWorkIdErrors(allErrorColl);
            log.debug(
                "Exiting UsageHelperImpl - addApmLearnedMatchMultiple method,  Found Work Id Errors, time elapsed = "
                    + (System.currentTimeMillis() - t1));
            return apmLearnedMatch;
        }
        apmLearnedMatch.setRequestId(UsageConstants.APM_LEARNED_MATCH_REQ_TYP_ADD);
        ApmLearnedMatch dbLearnedMatch = usageDAO.getApmLearnedMatch(apmLearnedMatch);
        log.debug("dbLearnedMatch " + dbLearnedMatch);
        if (dbLearnedMatch != null && "N".equals(dbLearnedMatch.getDeleteFlag())) {
            String[] workIds = new String[1];
            workIds[0] = dbLearnedMatch.getWorkId();
            if ("Y".equals(dbLearnedMatch.getLearnedDeleteFlag())) {
                throw new PrepFunctionalException("us.error.apm.learneddelete.exists");
            } else {
                throw new PrepFunctionalException("us.error.apm.learnedmatch.exists", workIds);
            }
        }
        apmLearnedMatch = usageDAO.addApmLearnedMatchMultiple(apmLearnedMatch);

        log.debug("Exiting UsageHelperImpl - addApmLearnedMatchMultiple method, time elapsed = "
            + (System.currentTimeMillis() - t1));
        return apmLearnedMatch;
    }

    @Override
    public ApmFileUpload loadFile2Usage(ApmFileUpload userFileUploadList)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHelperImpl - loadFile2Avps method ");
        userFileUploadList = usageDAO.loadFile2Usage(userFileUploadList);
        if (log.isDebugEnabled()) {
            log.debug("Exiting UsageHelperImpl - loadFile2Avps method ");
        }
        return userFileUploadList;
    }
}
