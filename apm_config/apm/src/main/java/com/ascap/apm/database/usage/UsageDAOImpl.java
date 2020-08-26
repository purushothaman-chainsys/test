package com.ascap.apm.database.usage;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.DebugHelperUtils;
import com.ascap.apm.common.utils.UsageCommonValidations;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.common.utils.constants.UsageQueries;
import com.ascap.apm.database.BaseDAO;
import com.ascap.apm.vob.usage.ApmActiveSurveyQuarter;
import com.ascap.apm.vob.usage.ApmChannel;
import com.ascap.apm.vob.usage.ApmChannelList;
import com.ascap.apm.vob.usage.ApmFile;
import com.ascap.apm.vob.usage.ApmFileList;
import com.ascap.apm.vob.usage.ApmPerformanceBulkRequest;
import com.ascap.apm.vob.usage.ApmPerformanceBulkRequestList;
import com.ascap.apm.vob.usage.ApmWork;
import com.ascap.apm.vob.usage.CatalogSamplingRequest;
import com.ascap.apm.vob.usage.CatalogSamplingSummary;
import com.ascap.apm.vob.usage.EOCallLetterConfig;
import com.ascap.apm.vob.usage.EOFile;
import com.ascap.apm.vob.usage.EOFileList;
import com.ascap.apm.vob.usage.EOLearnedMatch;
import com.ascap.apm.vob.usage.EOLearnedMatchList;
import com.ascap.apm.vob.usage.EOSupplierFormat;
import com.ascap.apm.vob.usage.Exemption;
import com.ascap.apm.vob.usage.ExemptionList;
import com.ascap.apm.vob.usage.LogRequestSummary;
import com.ascap.apm.vob.usage.LogSummaryRequest;
import com.ascap.apm.vob.usage.LogUsageRequest;
import com.ascap.apm.vob.usage.LogUsageSummary;
import com.ascap.apm.vob.usage.MusicUserParty;
import com.ascap.apm.vob.usage.MusicUserSearch;
import com.ascap.apm.vob.usage.PerformanceMessage;
import com.ascap.apm.vob.usage.PerformanceSearch;
import com.ascap.apm.vob.usage.ProgramPerformance;
import com.ascap.apm.vob.usage.SamplingRequest;
import com.ascap.apm.vob.usage.SamplingResult;
import com.ascap.apm.vob.usage.SamplingSummary;
import com.ascap.apm.vob.usage.WorkPerformance;
import com.ascap.apm.vob.usage.WorkPerformancesList;

/**
 * UsageDAOImpl Implementation.
 * 
 * @author Manoj_Puli
 * @version $Revision: 1.24 $ $Date: May 05 2011 12:34:22 $
 */
@Repository("UsageDAOImpl")
public class UsageDAOImpl extends BaseDAO implements UsageDAO {

    /**
     * Constructor for UsageDAOImpl.
     */
    public UsageDAOImpl() {
        super();
    }

    public static final String CURR_ROW_NUM = "CURR_ROW_NUM";

    public static final String LGY_MUS_USER_ID_LIKE = " A.LGY_MUS_USER_ID LIKE ? ";

    public static final String LIC_TYP_CDE_IN = " AND K.LIC_TYP_CDE IN ";

    public static final String ERROR_USAGE_SQL_SQLEXCEPTION = "error.usage.sql.sqlexception";

    public static final String APM_ARCHIVE_ID = "APM_ARCHIVE_ID";

    public static final String AND_WORK_ID_IN = " AND B.WRK_ID IN ";

    public static final String PROC_STATUS = "PROC_STATUS";

    public static final String AND_USE_TYP_IN = " AND B.USE_TYP IN ";

    public static final String YYYY_MM_DD = "','YYYY/MM/DD')";

    public static final String PERFORMANCESEARCH_IS = "performanceSearch is ";

    public static final String AND_TRUNC_STATUS_DATE_TO_DATE = " AND TRUNC(A.STATUS_DATE) >= TO_DATE( '";

    public static final String AND_MUS_USER_TYP_CDE_IN = " AND J.MUS_USER_TYP_CDE IN ";

    public static final String AND_SUR_TYP_ID_IN = " AND A.SUR_TYP_ID IN ";

    public static final String AND_SRC_SYS_IN = " AND A.SRC_SYS IN ";

    public static final String PROC_STATUS_NEW = " WHERE A.DEL_FL = 'N' AND A.PROC_STATUS <> 'NEW' ";

    public static final String GET_PROGRESSIVE_RESULTS_COUNT = ", getProgressiveResultsCount() =";

    public static final String TEMP_NUMBER_OF_RECORDS_FETCHEDNOW = ", tempNumberOfRecordsFetchedNow=";

    public static final String OR = " OR  ";

    public static final String AND_PROC_STATUS = " AND A.PROC_STATUS = 'PSTD' ";

    public static final String PROC_STATUS_PSTD = " AND A.PROC_STATUS <> 'PSTD' ";

    public static final String AND_B_APM_MATCH_TYP = " AND B.APM_MATCH_TYP IN ";

    public static final String TOTALPARAMETERS = "\n totalParameters=";

    public static final String APM_PERF_HDR_ID = "APM_PERF_HDR_ID";

    public static final String INTERSECT = " INTERSECT ";

    public static final String PFR_NA = "PFR_NA";

    public static final String ERR_STATUS = "ERR_STATUS";

    public static final String SRC_SYS = "SRC_SYS";

    public static final String PTY_ID = "PTY_ID";

    public static final String PTYNAFORMUS_FULL_NAME = "PTYNAFORMUS_FULL_NAME";

    public static final String LGY_MUS_USER_ID = "LGY_MUS_USER_ID";

    public static final String MUS_USER_TYP_CDE = "MUS_USER_TYP_CDE";

    public static final String MUSIC_USER_TYPE_DES = "MUSIC_USER_TYPE_DES";

    public static final String PGM_STT_DT = "PGM_STT_DT";

    public static final String PGM_STT_TM = "PGM_STT_TM";

    public static final String PGM_END_DT = "PGM_END_DT";

    public static final String PGM_END_TM = "PGM_END_TM";

    public static final String PGM_TTL = "PGM_TTL";

    public static final String SUPP_CODE = "SUPP_CODE";

    public static final String PGM_NUM = "PGM_NUM";

    public static final String LIC_TYP_CDE = "LIC_TYP_CDE";

    public static final String SAM_TYP_ID_IN = " AND A.SAM_TYP_ID IN ";

    public static final String PGM_OVRLP_CDE_IN = " AND A.PGM_OVRLP_CDE IN ";

    public static final String AND_ALL_ERRORS_USE_EWI_CDE = " AND all_errors.USE_EWI_CDE IN ";

    public static final String AND = " AND ( ";

    public static final String TGTSURVYEARQTR = "TGTSURVYEARQTR";

    public static final String LICENSE_TYPE_DES = "LICENSE_TYPE_DES";

    public static final String PGM_DUR = "PGM_DUR";

    public static final String SUR_TYP_ID = "SUR_TYP_ID";

    public static final String LGY_MUS_USER_TYP = "LGY_MUS_USER_TYP";

    public static final String SAM_TYP_ID = "SAM_TYP_ID";

    public static final String SEG_NUM = "SEG_NUM";

    public static final String SET_LIST_TYP = "SET_LIST_TYP";

    public static final String HDLNR_OPNR_IND = "HDLNR_OPNR_IND";

    public static final String TOUR_CNC_CNT = "TOUR_CNC_CNT";

    public static final String ART_REV = "ART_REV";

    public static final String TOTAL_NUM_PLAYS = "TOTAL_NUM_PLAYS";

    public static final String WRK_PERF_CNT = "WRK_PERF_CNT";

    public static final String EDITABLE = "EDITABLE";

    public static final String ASG_USR_ID = "ASG_USR_ID";

    public static final String PGM_OVRLP_CDE = "PGM_OVRLP_CDE";

    public static final String SQL_ERROR = "error.usage.sql.sqlexception";

    @Autowired
    private JdbcTemplate ascapJdbcTemplate;

    @Autowired
    public JdbcTemplate eoDSJdbcTemplate;

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#deleteWorkPerformances(PerformanceSearch)
     */
    public PerformanceSearch deleteWorkPerformances(PerformanceSearch performanceSearch) throws PrepSystemException {
        log.debug("Entering deleteWorkPerformances , Delete Type: " + performanceSearch.getDeleteType());

        try {
            if (UsageConstants.WRK_PERF_DELETE_TYPE.equals(performanceSearch.getDeleteType())) {
                // TO BE
                this.moveWorkPerformances(performanceSearch);
            } else {
                this.removeWorkPerformances(performanceSearch);
            }
        } catch (PrepSystemException se) {
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        return performanceSearch;
    }

    private void moveWorkPerformances(PerformanceSearch performanceSearch) throws PrepSystemException {

        log.debug("Enering moveWorkPerformances method in UsageDAOImpl");
        if (performanceSearch.getSelectedIds() == null || performanceSearch.getSelectedIds().length < 1) {
            log.warn("Invalid Selected IDs .. Returning");
        }

        StringBuilder workPerfIdStr = new StringBuilder();
        for (String workPerfId : performanceSearch.getSelectedIds()) {
            workPerfIdStr.append(workPerfId + ",");
            workPerfIdStr.append(",");
        }
        if (!ValidationUtils.isEmptyOrNull(workPerfIdStr.toString())) {
            workPerfIdStr.toString().substring(0, workPerfIdStr.toString().lastIndexOf(','));
            log.debug("calling Work Perf Remove for Batch  WorkId String " + workPerfIdStr);
        }
        List<Object> params = new ArrayList<>();
        try {
            params.add(workPerfIdStr.toString());
            params.add(performanceSearch.getUserId());
            ascapJdbcTemplate.update(UsageQueries.BATCH_APM_WORK_PERF_MOVE, params.toArray());
        } catch (Exception se) {
            log.error(se.getMessage());
            throw new PrepSystemException("error.batch.sql.sqlexception", se);

        }

        log.debug("Exiting moveWorkPerformances method in UsageDAOImpl");

    }

    /**
     * Method removeWorkPerformances.
     * 
     * @param con
     * @param performanceSearch
     * @return PerformanceSearch
     * @throws PrepSystemException
     */
    private PerformanceSearch removeWorkPerformances(PerformanceSearch performanceSearch) throws PrepSystemException {
        log.debug("Entering UsageDAOImpl - removeWorkPerformances method");
        log.debug(PERFORMANCESEARCH_IS + performanceSearch);

        if (performanceSearch == null) {
            return performanceSearch;
        }

        try {

            String[] performanceSearchIds = performanceSearch.getSelectedIds();

            if (performanceSearchIds != null && performanceSearchIds.length > 0) {
                String programPerformanceId = null;
                for (String workPerfId : performanceSearchIds) {

                    SqlRowSet rs =
                        ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_APM_PERF_HDR_ID_FOR_WRK_PERF, workPerfId);

                    if (rs.next()) {
                        programPerformanceId = rs.getString(APM_PERF_HDR_ID) + ",";
                    }

                    ascapJdbcTemplate.update(UsageQueries.DELETE_WORK_PERFORMANCE, performanceSearch.getUserId(),
                        workPerfId);

                    ascapJdbcTemplate.update(UsageQueries.DELETE_ALL_WORK_PERFORMANCE_MESSAGES, workPerfId);

                }
                if (programPerformanceId != null) {
                    programPerformanceId = programPerformanceId.substring(0, programPerformanceId.lastIndexOf(','));
                    log.debug("ProgramPerformanceId for Batch Validate " + programPerformanceId);
                    callPerformanceBatchValidate(UsageConstants.WRK_PERF_DELETE, programPerformanceId, null,
                        performanceSearch.getUserId());
                }
            }

        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }

        log.debug(PERFORMANCESEARCH_IS + performanceSearch.toString());
        log.debug("Exiting UsageDAOImpl - removeWorkPerformances method");
        return performanceSearch;

    }

    public void callPerformanceBatchValidate(String actionType, String perfHeaderId, String workPerfId, String userId)
        throws PrepSystemException {

        log.debug("Entering UsageDAOImpl - callPerformanceBatchValidate");
        log.debug("actionType : " + actionType);
        log.debug("perfHeaderId : " + perfHeaderId);
        log.debug("workPerfId : " + workPerfId);
        log.debug("userId : " + userId);

        try {

            ArrayList<Object> params = new ArrayList<>();
            params.add(actionType);
            if (UsageConstants.PERF_HDR_ADD.equals(actionType) || UsageConstants.PERF_HDR_UPDATE.equals(actionType)
                || UsageConstants.PERF_HDR_DELETE.equals(actionType)
                || UsageConstants.WRK_PERF_DELETE.equals(actionType) || UsageConstants.ADD_TO_MEDLEY.equals(actionType)
                || UsageConstants.DELETE_FROM_MEDLEY.equals(actionType)) {
                params.add(perfHeaderId);
                params.add(null);
            } else {
                params.add(null);
                params.add(workPerfId);
            }
            params.add(userId);
            ascapJdbcTemplate.update(UsageQueries.BATCH_APM_PERF_VALIDATE, params);
        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException("error.batch.sql.sqlexception", se);

        }
        log.debug("Exiting UsageDAOImpl - callPerformanceBatchValidate method");
    }

    public PerformanceSearch searchWorkPerformances(PerformanceSearch performanceSearchCriteria)
        throws PrepSystemException {
        log.debug("Entering UsageDAOImpl - searchWorkPerformances method vob is " + performanceSearchCriteria);
        boolean inContextOfHeader = false;
        if (performanceSearchCriteria != null
            && "RETRIEVE_WORK_PERFORMANCES_LIST".equals(performanceSearchCriteria.getActionSelected())) {
            ProgramPerformance programPerformance = null;
            if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getProgramPerformanceId())) {
                try {
                    long programPerformanceId = Long.parseLong(performanceSearchCriteria.getProgramPerformanceId());
                    programPerformance = this.retrieveBasicProgramPerformance(programPerformanceId);
                } catch (NumberFormatException ne) {
                    return performanceSearchCriteria;
                }
            }
            if ("Y".equals(performanceSearchCriteria.getShowDeletedRows())) {
                inContextOfHeader = true;
            }
            performanceSearchCriteria.setProgramPerformance(programPerformance);
            performanceSearchCriteria.setHeaderIdForWorkPerf(performanceSearchCriteria.getProgramPerformanceId());
        }

        List<Serializable> profileMessagesList = new ArrayList<>();

        if (log.isDebugEnabled()) {
            profileMessagesList.add("searchWorkPerformances Invoked :" + System.currentTimeMillis());
        }

        SqlRowSet rs = null;

        StringBuilder debugSearchStatement;
        StringBuilder searchWorkPerformancesSqlStr;
        StringBuilder searchWorkPerformancesWherePart;

        StringBuilder searchCountWorkPerformancesSqlStr;
        StringBuilder searchJOINSPart;

        boolean bothStartDateAndTimeareGiven = false;
        int totalParameters;
        java.util.ArrayList<String> searchWorkPerformancesValuesList;

        List<Object> workPerformanceCollection = new ArrayList<>();
        searchWorkPerformancesValuesList = new ArrayList<>();
        BitSet tableJoinDependencies = new BitSet(UsageQueries.BIT_MASK_SIZE);
        boolean isPerfHdrPredicate = true;
        boolean isWrkPerfPredicate = true;
        boolean isWrkPerfShrPredicate = false;
        boolean isCallLetterPredicate = false;
        boolean isMusicUserTypePredicate = false;
        boolean isMusicUserLicensePredicate = false;
        boolean isShareOwnerPrdeicate = false;
        boolean isUsageErrorForPerfHdrPredicate = false;
        boolean isUsageErrorForWrkPerfPredicate = false;
        searchWorkPerformancesSqlStr = new StringBuilder(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_PART1);
        searchCountWorkPerformancesSqlStr =
            new StringBuilder(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_COUNT_PART1);

        if ((!ValidationUtils.isEmptyOrNull(performanceSearchCriteria.getFeaturedPerformerName())
            && "CNTS".equals(performanceSearchCriteria.getPerformerSearchType()))
            || (!ValidationUtils.isEmptyOrNull(performanceSearchCriteria.getWorkTitle())
                && "CNTS".equals(performanceSearchCriteria.getWorkTitleSearchType()))) {
            log.debug("Main Search Contains Begins search found.............");
            searchWorkPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_CONTEXT_START);
            searchCountWorkPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_CONTEXT_START);
            int contextSearchNr = 0;
            if (!ValidationUtils.isEmptyOrNull(performanceSearchCriteria.getWorkTitle())
                && "CNTS".equals(performanceSearchCriteria.getWorkTitleSearchType())) {
                contextSearchNr++;
                searchWorkPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_TTL_CONTAINS_SEARCH);
                searchCountWorkPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_TTL_CONTAINS_SEARCH);
                searchWorkPerformancesValuesList.add(performanceSearchCriteria.getWorkTitle());
            }

            if (contextSearchNr > 0
                && (!ValidationUtils.isEmptyOrNull(performanceSearchCriteria.getFeaturedPerformerName())
                    && "CNTS".equals(performanceSearchCriteria.getPerformerSearchType()))) {
                searchWorkPerformancesSqlStr.append(INTERSECT);
                searchCountWorkPerformancesSqlStr.append(INTERSECT);
            }

            if (!ValidationUtils.isEmptyOrNull(performanceSearchCriteria.getFeaturedPerformerName())
                && "CNTS".equals(performanceSearchCriteria.getPerformerSearchType())) {
                searchCountWorkPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_PFR_CONTAINS_SEARCH);
                searchWorkPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_PFR_CONTAINS_SEARCH);
                searchWorkPerformancesValuesList.add(performanceSearchCriteria.getFeaturedPerformerName());
            }
            searchWorkPerformancesSqlStr.append(" )) ");
            searchCountWorkPerformancesSqlStr.append(" )) ");
        }
        searchCountWorkPerformancesSqlStr.append(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_COUNT_PART1_END);
        searchWorkPerformancesSqlStr.append(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_PART1_END);

        debugSearchStatement = new StringBuilder(PROC_STATUS_NEW);

        searchWorkPerformancesWherePart = new StringBuilder(PROC_STATUS_NEW);

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getProgramPerformanceId())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.APM_PERF_HDR_ID = ?");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getProgramPerformanceId());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.APM_PERF_HDR_ID = '")
                    .append(performanceSearchCriteria.getProgramPerformanceId()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPostedFlag())) {
            isPerfHdrPredicate = true;

            if ("Y".equals(performanceSearchCriteria.getPostedFlag())) {
                searchWorkPerformancesWherePart.append(AND_PROC_STATUS);
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(AND_PROC_STATUS);
                }
            } else {
                searchWorkPerformancesWherePart.append(PROC_STATUS_PSTD);
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(PROC_STATUS_PSTD);
                }
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceStartDate())
            || !UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceEndDate())
            || !UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceStartTime())
            || !UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceEndTime())) {
            bothStartDateAndTimeareGiven = true;
        }

        if (bothStartDateAndTimeareGiven) {

            isPerfHdrPredicate = true;

            if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceStartDate())) {
                searchWorkPerformancesWherePart.append(" AND A.PGM_STT_DT >= ? ");
                searchWorkPerformancesValuesList.add(performanceSearchCriteria.getPerformanceStartDate());

                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(" AND A.PGM_STT_DT >= '")
                        .append(performanceSearchCriteria.getPerformanceStartDate()).append("'");
                }
            }

            if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceEndDate())) {
                searchWorkPerformancesWherePart.append(" AND A.PGM_STT_DT <=  ?");
                searchWorkPerformancesValuesList.add(performanceSearchCriteria.getPerformanceEndDate());
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(" AND A.PGM_STT_DT <= '")
                        .append(performanceSearchCriteria.getPerformanceEndDate()).append("'");
                }
            }

            if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceStartTime())) {
                searchWorkPerformancesWherePart.append(" AND A.PGM_STT_TM >= ?");
                searchWorkPerformancesValuesList.add(performanceSearchCriteria.getPerformanceStartTime());
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(" AND A.PGM_STT_TM >= '")
                        .append(performanceSearchCriteria.getPerformanceStartTime() + "'");
                }
            }

            if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceEndTime())) {
                searchWorkPerformancesWherePart.append(" AND A.PGM_STT_TM <=  ? ");
                searchWorkPerformancesValuesList.add(performanceSearchCriteria.getPerformanceEndTime());
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(" AND A.PGM_STT_TM <= '")
                        .append(performanceSearchCriteria.getPerformanceEndTime()).append("'");
                }
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getMusicUserId())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.PTY_ID = ?  AND A.PTY_ID is not null ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getMusicUserId());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.PTY_ID = '").append(performanceSearchCriteria.getMusicUserId())
                    .append("'  AND A.PTY_ID is not null ");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getOverlapCode(), true)) {
            isPerfHdrPredicate = true;

            searchWorkPerformancesWherePart.append(PGM_OVRLP_CDE_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getOverlapCode())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(PGM_OVRLP_CDE_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getOverlapCode())).append(" ");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getSupplierCode())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.SUPP_CODE = ?");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getSupplierCode());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.SUPP_CODE = '").append(performanceSearchCriteria.getSupplierCode())
                    .append("'");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getSampleTypes(), true)) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(SAM_TYP_ID_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getSampleTypes())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(SAM_TYP_ID_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getSampleTypes())).append(" ");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getSurveyTypes(), true)) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(AND_SUR_TYP_ID_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getSurveyTypes())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_SUR_TYP_ID_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getSurveyTypes())).append(" ");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getScheduleSequenceNumber())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.SCH_SEQ_NUM = ?  AND A.SCH_SEQ_NUM is not null ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getScheduleSequenceNumber());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.SCH_SEQ_NUM = '")
                    .append(performanceSearchCriteria.getScheduleSequenceNumber())
                    .append("'  AND A.SCH_SEQ_NUM is not null ");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getLegacySourceSystem(),
            true)) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(AND_SRC_SYS_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getLegacySourceSystem())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_SRC_SYS_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getLegacySourceSystem())).append(" ");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getProgramNumber())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.PGM_NUM = ?");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getProgramNumber());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.PGM_NUM = '").append(performanceSearchCriteria.getProgramNumber())
                    .append("'");
            }
        }
        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getSegmentNumber())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.SEG_NUM = ?");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getSegmentNumber());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.SEG_NUM = '").append(performanceSearchCriteria.getSegmentNumber())
                    .append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getStatusDateFrom())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND TRUNC(A.STATUS_DATE) >= TO_DATE(?,'YYYY/MM/DD') ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getStatusDateFrom());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_TRUNC_STATUS_DATE_TO_DATE)
                    .append(performanceSearchCriteria.getStatusDateFrom()).append(YYYY_MM_DD);
            }
        }
        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getStatusDateTo())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND TRUNC(A.STATUS_DATE) <= TO_DATE(?,'YYYY/MM/DD') ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getStatusDateTo());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_TRUNC_STATUS_DATE_TO_DATE)
                    .append(performanceSearchCriteria.getStatusDateTo()).append(YYYY_MM_DD);
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getSetlistType())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.SET_LIST_TYP = ? ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getSetlistType());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.SET_LIST_TYP =  '")
                    .append(performanceSearchCriteria.getSetlistType()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getHeadlineIndicator())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.HDLNR_OPNR_IND = ? ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getHeadlineIndicator());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.HDLNR_OPNR_IND = '")
                    .append(performanceSearchCriteria.getHeadlineIndicator()).append("'");
            }
        }
        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getTargetYearQuarterFrom())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.TGTSURVYEARQTR >= ? ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getTargetYearQuarterFrom());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.TGTSURVYEARQTR >= '")
                    .append(performanceSearchCriteria.getTargetYearQuarterFrom()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getTargetYearQuarterTo())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.TGTSURVYEARQTR <= ? ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getTargetYearQuarterTo());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.TGTSURVYEARQTR <= '")
                    .append(performanceSearchCriteria.getTargetYearQuarterTo()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getFileId())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.APM_ARCHIVE_ID = ?");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getFileId().toUpperCase());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.APM_ARCHIVE_ID = '").append(performanceSearchCriteria.getFileId())
                    .append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getClassicalIndicator())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.CLASSICAL_IND = ? ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getClassicalIndicator());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.CLASSICAL_IND = '")
                    .append(performanceSearchCriteria.getClassicalIndicator()).append("'");
            }
        }
        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getAssignedToUser())) {
            isPerfHdrPredicate = true;
            searchWorkPerformancesWherePart.append(" AND A.ASG_USR_ID = ? ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getAssignedToUser());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.ASG_USR_ID = '")
                    .append(performanceSearchCriteria.getAssignedToUser()).append("'");
            }
        }
        String callLetters = performanceSearchCriteria.getMusicUserCallLetter();

        if (callLetters == null) {
            callLetters = "";
        }
        StringTokenizer st = new StringTokenizer(callLetters, ",");
        String callLetter = null;
        int tokensCount = st.countTokens();
        if (tokensCount > 0) {

            searchWorkPerformancesWherePart.append(AND);
            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND);
            }
        }
        while (st.hasMoreTokens()) {
            tokensCount--;
            callLetter = (String) st.nextElement();
            searchWorkPerformancesWherePart.append(LGY_MUS_USER_ID_LIKE);
            searchWorkPerformancesValuesList.add((callLetter.toUpperCase() + "%"));
            if (tokensCount > 0) {
                searchWorkPerformancesWherePart.append(OR);
            } else {
                searchWorkPerformancesWherePart.append(" ) ");
            }
            if (log.isDebugEnabled()) {
                debugSearchStatement.append(LGY_MUS_USER_ID_LIKE);
                if (tokensCount > 0) {
                    debugSearchStatement.append(OR);
                } else {
                    debugSearchStatement.append(" ) ");
                }
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getMusicUserTypes(),
            true)) {
            isMusicUserTypePredicate = true;

            searchWorkPerformancesWherePart.append(AND_MUS_USER_TYP_CDE_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getMusicUserTypes())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_MUS_USER_TYP_CDE_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getMusicUserTypes())).append(" ");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getLicenseTypeCode(),
            true)) {
            isMusicUserLicensePredicate = true;

            searchWorkPerformancesWherePart.append(LIC_TYP_CDE_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getLicenseTypeCode())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(LIC_TYP_CDE_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getLicenseTypeCode())).append(" ");
            }
        }
        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getWorkPerformanceId())) {
            isWrkPerfPredicate = true;
            searchWorkPerformancesWherePart.append(" AND B.APM_WRK_PERF_ID = ?");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getWorkPerformanceId());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND B.APM_WRK_PERF_ID = '")
                    .append(performanceSearchCriteria.getWorkPerformanceId()).append("'");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getUseTypes(), true)) {
            isWrkPerfPredicate = true;
            searchWorkPerformancesWherePart.append(AND_USE_TYP_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getUseTypes())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_USE_TYP_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getUseTypes())).append(" ");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getMatchTypes(), true)) {
            isWrkPerfPredicate = true;
            searchWorkPerformancesWherePart.append(AND_B_APM_MATCH_TYP)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getMatchTypes())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_B_APM_MATCH_TYP)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getMatchTypes())).append(" ");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getWorkId())) {
            isWrkPerfPredicate = true;

            searchWorkPerformancesWherePart.append(AND_WORK_ID_IN)
                .append(UsageQueries.DYNAMIC_SEARCH_CRITERIA_MERGED_WRK_IDS);
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getWorkId());
            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_WORK_ID_IN).append(UsageQueries.DYNAMIC_SEARCH_CRITERIA_MERGED_WRK_IDS)
                    .append("/* USE Work ID => ").append(performanceSearchCriteria.getWorkId()).append(" */ ");
            }

        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getWorkTitle())) {
            isWrkPerfPredicate = true;

            if ("CNTS".equals(performanceSearchCriteria.getWorkTitleSearchType())) {
                searchWorkPerformancesWherePart.append(
                    " AND B.WRK_TTL LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'TITLE', 'BGNS') FROM DUAL) ");
                searchWorkPerformancesValuesList.add(performanceSearchCriteria.getWorkTitle());
                if (log.isDebugEnabled()) {
                    debugSearchStatement
                        .append(" AND B.WRK_TTL LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM('"
                            + performanceSearchCriteria.getWorkTitle() + "', 'TITLE', 'BGNS') FROM DUAL) ");
                }
            }

        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getFeaturedPerformerName())) {
            isWrkPerfPredicate = true;

            if ("CNTS".equals(performanceSearchCriteria.getPerformerSearchType())) {
                // performanceSearchCriteria
            } else {
                searchWorkPerformancesWherePart.append(
                    " AND B.PFR_NA LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'NAME', 'BGNS') FROM DUAL) ");
                searchWorkPerformancesValuesList.add(performanceSearchCriteria.getFeaturedPerformerName());
                if (log.isDebugEnabled()) {
                    debugSearchStatement
                        .append(" AND B.PFR_NA LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM('"
                            + performanceSearchCriteria.getFeaturedPerformerName() + "', 'NAME', 'BGNS') FROM DUAL) ");
                }
            }

        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPlayCount())) {
            isWrkPerfPredicate = true;
            searchWorkPerformancesWherePart.append(" AND  LPAD(B.PLAY_CNT, 20 , '0') >= LPAD( ?, 20,'0') ");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getPlayCount());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND  LPAD(A.PLAY_CNT, 20 , '0') >= LPAD( '")
                    .append(performanceSearchCriteria.getPlayCount()).append("', 20,'0')");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getSupplierWorkCode())) {
            isWrkPerfPredicate = true;
            searchWorkPerformancesWherePart.append(" AND B.PROVIDER_ID = ?");
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getSupplierWorkCode());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND B.PROVIDER_ID = '")
                    .append(performanceSearchCriteria.getSupplierWorkCode()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getShareOwnerPartyId())) {
            isShareOwnerPrdeicate = true;

            searchWorkPerformancesWherePart.append("AND G.PTY_ID IN ")
                .append(UsageQueries.DYNAMIC_SEARCH_CRITERIA_MERGED_PARTY_IDS);
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getShareOwnerPartyId());
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getShareOwnerPartyId());
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getShareOwnerPartyId());
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getShareOwnerPartyId());
            searchWorkPerformancesValuesList.add(performanceSearchCriteria.getShareOwnerPartyId());
            if (log.isDebugEnabled()) {
                debugSearchStatement.append("AND G.PTY_ID in ")
                    .append(UsageQueries.DYNAMIC_SEARCH_CRITERIA_MERGED_PARTY_IDS).append("/* Use this Party Id => '")
                    .append(performanceSearchCriteria.getShareOwnerPartyId()).append("' */");
            }
        }

        if (!UsageCommonValidations
            .isCollectionEmptyOrNoElements(performanceSearchCriteria.getPerformanceErrorWarningCodes(), true)) {
            isUsageErrorForWrkPerfPredicate = true;

            searchWorkPerformancesWherePart.append(AND_ALL_ERRORS_USE_EWI_CDE)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getPerformanceErrorWarningCodes()));

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_ALL_ERRORS_USE_EWI_CDE)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getPerformanceErrorWarningCodes()));
            }

        }

        if (isPerfHdrPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_PERF_HDR);
        }
        if (isWrkPerfPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_WRK_PERF);
        }
        if (isWrkPerfShrPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_WRK_PERF_SHR);
        }
        if (isCallLetterPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_CALL_LETTER);
        }
        if (isMusicUserTypePredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_MUS_USER_TYP);
        }
        if (isMusicUserLicensePredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_MUS_USER_LIC);
        }
        if (isShareOwnerPrdeicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_SHR_OWNER_PTY);
        }
        if (isUsageErrorForPerfHdrPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_USAGE_ERROR_FOR_PERF_HDR);
        }
        if (isUsageErrorForWrkPerfPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_USAGE_ERROR_FOR_WRK_PERF);
        }

        searchJOINSPart = new StringBuilder();

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_A_PERF_HDR)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_PERF_HDR_A);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_B_WRK_PERF)) {
            if (inContextOfHeader) {
                searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_WRK_PERF_B_EVEN_DELETED);
            } else {
                searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_WRK_PERF_B);
            }

            if ((!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getWorkTitle())
                && "CNTS".equals(performanceSearchCriteria.getWorkTitleSearchType()))
                || (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getFeaturedPerformerName())
                    && "CNTS".equals(performanceSearchCriteria.getPerformerSearchType()))) {
                searchJOINSPart.append(" INNER JOIN ctx ON (b.apm_wrk_perf_id = ctx.apm_wrk_perf_id)");
            }
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_C_WRK_PERF_SHR)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_WRK_PERF_SHR_C);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_D_PTY_FOR_MUSICUSER)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_PTY_D);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_F_PTY_NA_FOR_CALLLETTER)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_CALL_LETTERS_F);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_J_MUS_USER_TYP_SEL)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_TYPE_J);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_K_MUS_USER_LIC)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_LICENSE_K);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_ALL_ERRORS_PERF_HDR)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_PERF_HDR_ALL_ERRORS);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_ALL_ERRORS_WRK_PERF)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_WRK_PERF_ALL_ERRORS);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_E_PTY_FOR_SHAREOWNERPARTY)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_SHAREOWNER_PARTYID_E);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_G_PTY_NA_FOR_SHAREOWNERPARTY)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_SHAREOWNER_PARTY_NA_ID_G);
        }

        log.debug("JOIN CLAUSE IS .......................................................................\n"
            + searchJOINSPart);
        log.debug(
            "searchWorkPerformancesWherePart .......................................................................\n"
                + searchWorkPerformancesWherePart);

        try {

            profileMessagesList.add("searchWorkPerformances Query Prepared :" + System.currentTimeMillis());

            int count = 0;
            StringBuilder debugSearchStatementJoin = null;
            if (UsageConstants.SEARCH_RESULTS_COUNT_POLICY_LIMIT_TOMAX
                .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())) {
                // Building WorkPerformances Search Count
                searchCountWorkPerformancesSqlStr.append(searchJOINSPart.toString());
                searchCountWorkPerformancesSqlStr.append(searchWorkPerformancesWherePart);
                searchCountWorkPerformancesSqlStr
                    .append(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_COUNT_LIMT_PART2);

                debugSearchStatementJoin = new StringBuilder(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_PART1);
                debugSearchStatementJoin.append(searchJOINSPart).append(debugSearchStatement);
                debugSearchStatement = debugSearchStatementJoin;
            } else if (UsageConstants.SEARCH_RESULTS_COUNT_POLICY_EXACT_COUNT
                .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())
                || UsageConstants.SEARCH_RESULTS_COUNT_POLICY_DONOT_COUNT
                    .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())) {
                // Building WorkPerformances Search Count
                searchCountWorkPerformancesSqlStr.append(searchJOINSPart.toString());
                searchCountWorkPerformancesSqlStr.append(searchWorkPerformancesWherePart);
                searchCountWorkPerformancesSqlStr.append(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_COUNT_PART2);

                debugSearchStatementJoin = new StringBuilder(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_PART1);
                debugSearchStatementJoin.append(searchJOINSPart).append(debugSearchStatement);
                debugSearchStatement = debugSearchStatementJoin;
            } else {
                log.debug(
                    "Should NEVER Happen !!! Search Results Count Policy is invalid ... performanceSearchCriteria.getSearchResultsCountPolicy() = "
                        + performanceSearchCriteria.getSearchResultsCountPolicy());
                // Building WorkPerformances Search Count
                searchCountWorkPerformancesSqlStr.append(searchJOINSPart.toString());
                searchCountWorkPerformancesSqlStr.append(searchWorkPerformancesWherePart);
                searchCountWorkPerformancesSqlStr.append(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_COUNT_PART2);

                debugSearchStatementJoin = new StringBuilder(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_PART1);
                debugSearchStatementJoin.append(searchJOINSPart).append(debugSearchStatement);
                debugSearchStatement = debugSearchStatementJoin;
            }

            log.debug("\n--ORIGINAL COUNT Generated Search WorkPerformances Count Search SQL String :\n"
                + searchCountWorkPerformancesSqlStr.toString());
            log.debug(
                "\n--Generated Search DEBUG SQL BEFORE EXECUTING COUNT String :\n" + debugSearchStatement.toString());
            if (UsageConstants.SEARCH_RESULTS_COUNT_POLICY_LIMIT_TOMAX
                .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())) {
                log.debug("EXECUTING QUERY FOR SEARCH_RESULTS_COUNT_POLICY_LIMIT_TOMAX: "
                    + searchCountWorkPerformancesSqlStr.toString());

                totalParameters = searchWorkPerformancesValuesList.size();
                Object[] params = new Object[totalParameters + 1];
                for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                    params[parameterId] = searchWorkPerformancesValuesList.get(parameterId);
                }

                params[totalParameters] = PrepExtPropertyHelper.getInstance()
                    .getPropertyValue(PrepPropertiesConstants.MAX_SEARCHRESULTS_THRESHOLD);

                log.debug("\n--EXECUTING COUNT Query (SEARCH_RESULTS_COUNT_POLICY_LIMIT_TOMAX):\n"
                    + searchCountWorkPerformancesSqlStr.toString() + TOTALPARAMETERS + totalParameters);

                if (log.isDebugEnabled()) {
                    profileMessagesList
                        .add("searchWorkPerformances Count Started TO MAX:" + System.currentTimeMillis());
                }

                rs = ascapJdbcTemplate.queryForRowSet(searchCountWorkPerformancesSqlStr.toString(), params);

                count = 0;

                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } else if (UsageConstants.SEARCH_RESULTS_COUNT_POLICY_EXACT_COUNT
                .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())) {
                log.debug("EXECUTING QUERY FOR SEARCH_RESULTS_COUNT_POLICY_EXACT_COUNT: "
                    + searchCountWorkPerformancesSqlStr.toString());

                totalParameters = searchWorkPerformancesValuesList.size();
                Object[] params = new Object[totalParameters];
                for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                    log.debug("Manoj WORK PERF Value settting " + parameterId + " : "
                        + searchWorkPerformancesValuesList.get(parameterId));
                    params[parameterId + 1] = searchWorkPerformancesValuesList.get(parameterId);
                }

                if (log.isDebugEnabled()) {
                    log.debug("\n--EXECUTING COUNT Query (SEARCH_RESULTS_COUNT_POLICY_EXACT_COUNT):\n"
                        + searchCountWorkPerformancesSqlStr.toString() + TOTALPARAMETERS + totalParameters);
                }

                if (log.isDebugEnabled()) {
                    profileMessagesList.add("searchWorkPerformances Count Started EXACT:" + System.currentTimeMillis());
                }

                rs = ascapJdbcTemplate.queryForRowSet(searchCountWorkPerformancesSqlStr.toString(), params);

                count = 0;

                if (rs.next()) {
                    count = rs.getInt(1);
                }

            } else if (UsageConstants.SEARCH_RESULTS_COUNT_POLICY_DONOT_COUNT
                .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())) {
                if ("Y".equalsIgnoreCase(performanceSearchCriteria.getProgressiveEndReached())) {
                    count = performanceSearchCriteria.getNumberOfRecordsFoundbySearch();
                } else {
                    count = Integer.parseInt(PrepExtPropertyHelper.getInstance()
                        .getPropertyValue(PrepPropertiesConstants.MAX_SEARCHRESULTS_THRESHOLD));
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(
                        "Should NEVER Happen !!! Search Results Count Policy is invalid assuming MAX Results Threshold as result count... performanceSearchCriteria.getSearchResultsCountPolicy() = "
                            + performanceSearchCriteria.getSearchResultsCountPolicy());
                }
                count = Integer.parseInt(PrepExtPropertyHelper.getInstance()
                    .getPropertyValue(PrepPropertiesConstants.MAX_SEARCHRESULTS_THRESHOLD));
            }

            if (log.isDebugEnabled()) {
                log.debug("\n--Search Results Count Policy" + performanceSearchCriteria.getSearchResultsCountPolicy()
                    + ", COUNT Determined :" + count);
            }

            performanceSearchCriteria.setNumberOfRecordsFound(count);

            if (log.isDebugEnabled()) {
                profileMessagesList.add("searchWorkPerformances Count Finished:" + System.currentTimeMillis());
            }

            searchWorkPerformancesSqlStr.append(searchJOINSPart.toString());
            searchWorkPerformancesSqlStr.append(searchWorkPerformancesWherePart);
            searchWorkPerformancesSqlStr.append(UsageQueries.DYNAMIC_SEARCH_WORK_PERFORMANCES_PART2);

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" ) ) a1 where rn between ")
                    .append(performanceSearchCriteria.getFromIndex()).append(" and ")
                    .append(performanceSearchCriteria.getToIndex() - 1).append(" ");
            }

            if (log.isDebugEnabled()) {
                log.debug("\n--ORIGINAL SEARCH Generated Search WorkPerformancesSearch SQL String :\n"
                    + searchWorkPerformancesSqlStr.toString());
                log.debug("\n--Generated Search DEBUG SQL String :\n" + debugSearchStatement.toString());
            }

            if (log.isDebugEnabled()) {
                log.debug("Manoj -- Search query  " + searchWorkPerformancesSqlStr.toString());
                log.debug("Total records found: " + count);
                log.debug("Getting records from Index " + performanceSearchCriteria.getFromIndex() + " To Index :"
                    + (performanceSearchCriteria.getToIndex() - 1));
            }
            // Adding From Index and To Index to the Values List
            searchWorkPerformancesValuesList.add(Integer.toString(performanceSearchCriteria.getToIndex() - 1)); // MAX
            // ROW
            searchWorkPerformancesValuesList.add(Integer.toString(performanceSearchCriteria.getFromIndex())); // MIN ROW

            totalParameters = searchWorkPerformancesValuesList.size();
            Object[] params = new Object[totalParameters];
            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                log.debug("Manoj -- Search Setting Parameters " + parameterId + "  value: "
                    + searchWorkPerformancesValuesList.get(parameterId));
                params[parameterId] = searchWorkPerformancesValuesList.get(parameterId);
            }

            if (log.isDebugEnabled()) {
                profileMessagesList.add("searchWorkPerformances Search Query Started:" + System.currentTimeMillis());
            }
            rs = ascapJdbcTemplate.queryForRowSet(searchWorkPerformancesSqlStr.toString(), params);

            if (log.isDebugEnabled()) {
                profileMessagesList.add("searchWorkPerformances Search Query Finished:" + System.currentTimeMillis());
            }

            WorkPerformance workPerformanceVob = null;

            long tempNumberOfRecordsFetchedNow = 0;

            log.debug(
                "Before Fetching Results getOldNavigationType() =" + performanceSearchCriteria.getOldNavigationType()
                    + GET_PROGRESSIVE_RESULTS_COUNT + performanceSearchCriteria.getProgressiveResultsCount()
                    + TEMP_NUMBER_OF_RECORDS_FETCHEDNOW + tempNumberOfRecordsFetchedNow);

            while (rs.next()) {
                tempNumberOfRecordsFetchedNow++;
                workPerformanceVob = new WorkPerformance();
                workPerformanceVob.setPerformanceHeaderId(rs.getString(APM_PERF_HDR_ID));
                workPerformanceVob.setWorkPerformanceId(rs.getString("APM_WRK_PERF_ID"));
                workPerformanceVob.setFileId(rs.getString(APM_ARCHIVE_ID));
                workPerformanceVob.setWorkSequenceNumber(rs.getString("WRK_SEQ_NR"));
                workPerformanceVob.setMedleySequenceNumber(rs.getString("MEDL_SEQ"));
                workPerformanceVob.setWorkTitle(rs.getString("WRK_TTL"));
                workPerformanceVob.setWorkId(rs.getString("WRK_ID"));
                workPerformanceVob.setUseTypeCode(rs.getString("USE_TYP"));
                workPerformanceVob.setUseTypeDescription(rs.getString("USE_TYPE_DES"));
                workPerformanceVob.setWorkPerformanceDuration(rs.getString("WRK_PERF_DUR"));
                workPerformanceVob.setPerformerName(rs.getString(PFR_NA));
                workPerformanceVob.setPlayCount(rs.getString("PLAY_CNT"));
                workPerformanceVob.setErrorFlag(rs.getString(ERR_STATUS));
                workPerformanceVob.setSourceSystem(rs.getString(SRC_SYS));
                workPerformanceVob.setMusicUserId(rs.getString(PTY_ID));
                workPerformanceVob.setMusicUserFullName(rs.getString(PTYNAFORMUS_FULL_NAME));
                workPerformanceVob.setMusicUserCallLetterSuffix(rs.getString(LGY_MUS_USER_ID));
                workPerformanceVob.setMusicUserTypeCode(rs.getString(MUS_USER_TYP_CDE));
                workPerformanceVob.setMusicUserTypeDescription(rs.getString(MUSIC_USER_TYPE_DES));
                workPerformanceVob.setPerformanceStartDate(rs.getString(PGM_STT_DT));
                workPerformanceVob.setPerformanceStartTime(rs.getString(PGM_STT_TM));
                workPerformanceVob.setPerformanceEndDate(rs.getString(PGM_END_DT));
                workPerformanceVob.setPerformanceEndTime(rs.getString(PGM_END_TM));
                workPerformanceVob.setProgramTitle(rs.getString(PGM_TTL));
                workPerformanceVob.setSupplierCode(rs.getString(SUPP_CODE));
                workPerformanceVob.setProgramNumber(rs.getString(PGM_NUM));
                workPerformanceVob.setLicenseTypeCode(rs.getString(LIC_TYP_CDE));
                workPerformanceVob.setOnlineHeaderEditable(rs.getString("HDR_EDITABLE"));
                workPerformanceVob.setOnlineWorkPerfEditable(rs.getString("WORK_PERF_EDITABLE"));
                workPerformanceVob.setWorkPerformanceDeleteFlag(rs.getString("DEL_FL"));
                workPerformanceVob.setDetectionTime(rs.getString("DETECTION_TM"));
                workPerformanceVob.setConfidenceLevel(rs.getString("CONFIDENCE_LVL"));
                workPerformanceVob.setWriter(rs.getString("WRITER"));
                workPerformanceVob.setLibraryDiscTitle(rs.getString("LIBRARY_DISC"));
                workPerformanceVob.setLibraryDiscId(rs.getString("LIBRARY_DISC_ID"));

                workPerformanceCollection.add(workPerformanceVob);
            }

            if ("NEXT".equalsIgnoreCase(performanceSearchCriteria.getOldNavigationType())
                || "FIRST".equalsIgnoreCase(performanceSearchCriteria.getOldNavigationType())
                || UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getOldNavigationType())
                || ("LAST".equalsIgnoreCase(performanceSearchCriteria.getOldNavigationType())
                    || ("PREV".equalsIgnoreCase(performanceSearchCriteria.getOldNavigationType())))) {
                long tempTotalResultsFound = 0;
                if (tempNumberOfRecordsFetchedNow != 0) {
                    tempTotalResultsFound =
                        (performanceSearchCriteria.getFromIndex() - 1) + tempNumberOfRecordsFetchedNow;
                    if (tempTotalResultsFound >= performanceSearchCriteria.getProgressiveResultsCount()) {
                        performanceSearchCriteria.setProgressiveResultsCount(tempTotalResultsFound);
                    }
                }
            }

            // For deciding if Progressive counts reached an end
            if (("NEXT".equalsIgnoreCase(performanceSearchCriteria.getOldNavigationType()))
                && (tempNumberOfRecordsFetchedNow == 0)) {
                performanceSearchCriteria.setProgressiveEndReached("Y");
                performanceSearchCriteria
                    .setNumberOfRecordsFound((int) performanceSearchCriteria.getProgressiveResultsCount());
            }

            log.debug("After Fetching Results and Resetting getOldNavigationType() ="
                + performanceSearchCriteria.getOldNavigationType() + GET_PROGRESSIVE_RESULTS_COUNT
                + performanceSearchCriteria.getProgressiveResultsCount() + TEMP_NUMBER_OF_RECORDS_FETCHEDNOW
                + tempNumberOfRecordsFetchedNow);

            performanceSearchCriteria.setSearchResults(workPerformanceCollection);

            if (log.isDebugEnabled()) {
                profileMessagesList
                    .add("searchWorkPerformances Finished Setting Results :" + System.currentTimeMillis());
            }

        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        } finally {
            performanceSearchCriteria.setExecutionProfilingMessages(profileMessagesList);
        }
        if (log.isDebugEnabled()) {
            profileMessagesList.add("searchWorkPerformances Exiting :" + System.currentTimeMillis());
        }
        performanceSearchCriteria.setExecutionProfilingMessages(profileMessagesList);

        return performanceSearchCriteria;
    }

    /**
     * Method buildInStringCriteria.
     * 
     * @param criteria
     * @return String
     */
    private String buildInStringCriteria(String[] criteriaIn) {
        String[] criteria;
        int totalLength = 0;
        StringBuilder outStrBuff;
        if (UsageCommonValidations.isCollectionEmptyOrNoElements(criteriaIn, true)) {
            return "";
        }
        criteria = UsageCommonValidations.removeEmptyElements(criteriaIn);
        totalLength = criteria.length;
        outStrBuff = new StringBuilder("(");
        for (int currIndex = 0; currIndex < totalLength; currIndex++) {
            outStrBuff.append(" '").append(escapForOracle(criteria[currIndex])).append("',");
        }
        outStrBuff.deleteCharAt(outStrBuff.length() - 1);
        outStrBuff.append(")");
        return outStrBuff.toString();
    }

    public static String escapForOracle(String s) {
        String retvalue = s;
        if (s.indexOf('\'') != -1) {
            StringBuilder hold = new StringBuilder();
            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if (c == '\'') {
                    hold.append("''");
                } else if (c == '?') {
                    // Nothing to append
                    hold.append("?");
                } else {
                    hold.append(c);
                }
            }
            retvalue = hold.toString();
        }
        return retvalue;
    }

    /*
     * (non-Javadoc)
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#getWorkPerformance( long, java.lang.String) //Removed
     * Del_flags following Issue 2341
     */
    public WorkPerformance getWorkPerformance(long workPerformanceId, String versionNumber) throws PrepSystemException {
        return getWorkPerformance(workPerformanceId, versionNumber, false);
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#getWorkPerformance(long, int,boolean)
     */
    public WorkPerformance getWorkPerformance(long workPerformanceId, String versionNumber, boolean ignoreDeleteFlag)
        throws PrepSystemException {
        log.debug(
            "Entering public getWorkPerformance(long workPerformanceId,int versionNumber, boolean ignoreDeleteFlag)  method in UsageDAOImpl ");
        WorkPerformance workPerformance = null;
        try {
            workPerformance = this.retriveCompleteWorkPerformance(workPerformanceId, versionNumber);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug(
            "Exiting public getWorkPerformance(long workPerformanceId,int versionNumber, boolean ignoreDeleteFlag)  method in UsageDAOImpl ");
        return workPerformance;
    }

    /**
     * Method retriveCompleteWorkPerformance.
     * 
     * @param con
     * @param workPerformanceId
     * @param versionNumber
     * @return WorkPerformance
     * @throws PrepSystemException
     */
    private WorkPerformance retriveCompleteWorkPerformance(long workPerformanceId, String versionNumber)
        throws PrepSystemException {
        log.debug(
            "Entering private WorkPerformance retriveCompleteWorkPerformance(Connection con, long workPerformance,int versionNumber, boolean ignoreDeleteFlag)  method in UsageDAOImpl ");
        WorkPerformance workPerformance = null;
        try {
            workPerformance = this.retrieveBasicWorkPerformance(workPerformanceId);
            if (workPerformance != null) {
                workPerformance.setErrorsAndWarnings(this.retrievePerformanceMessages(
                    UsageConstants.WORK_PERFORMANCE_TYPE, workPerformanceId, versionNumber));

                if (workPerformance.getErrorsAndWarnings() == null) {
                    workPerformance.setErrorsAndWarnings(new ArrayList<>());
                }

                workPerformance.setWorkPerformanceShares(new ArrayList<Object>());

            }
        } catch (Exception pse) {
            log.error(pse.getMessage());
        }

        log.debug(
            "Exiting private WorkPerformance retriveCompleteWorkPerformance(Connection con, long workPerformance,int versionNumber, boolean ignoreDeleteFlag) method in UsageDAOImpl ");

        return workPerformance;
    }

    /**
     * Method retrievePerformanceMessages.
     * 
     * @param con
     * @param performanceType
     * @param performanceId
     * @param versionNumber
     * @return Collection
     * @throws PrepSystemException
     */
    private List<Object> retrievePerformanceMessages(String performanceType, long performanceId, String versionNumber)
        throws PrepSystemException {
        log.debug("Entering getPerformanceMessages method in UsageDAOImpl " + performanceType + ","
            + Long.toString(performanceId) + "," + versionNumber);

        PerformanceMessage performanceMessageVob = null;
        List<Object> outPerfromanceMessageColl = new ArrayList<>();
        try {

            String qry = null;
            if (UsageConstants.PROGRAM_PERFORMANCE_TYPE.equalsIgnoreCase(performanceType)) {
                qry = UsageQueries.GET_PROGRAM_PERFORMANCE_MESSAGES;
            } else if (UsageConstants.WORK_PERFORMANCE_TYPE.equalsIgnoreCase(performanceType)) {
                qry = UsageQueries.GET_WORK_PERFORMANCE_MESSAGES;
            } else {
                return outPerfromanceMessageColl;
            }

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(qry, performanceId);
            while (rs.next()) {
                performanceMessageVob = new PerformanceMessage();
                performanceMessageVob.setPerformanceType(performanceType);
                performanceMessageVob.setPerformanceId(rs.getString("PERF_ID"));
                performanceMessageVob.setMessageCode(rs.getString("ERR_CDE"));
                performanceMessageVob.setMessageCategoryCode(rs.getString("ERR_CAT"));
                performanceMessageVob.setMessageDescription(rs.getString("MSG_DES"));

                outPerfromanceMessageColl.add(performanceMessageVob);
            }

        } catch (Exception se) {
            log.error(se.getMessage());
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }

        log.debug("Entering getPerformanceMessages method in UsageDAOImpl Messages :"
            + DebugHelperUtils.debugCollections("Error Messages Collection", outPerfromanceMessageColl));

        return outPerfromanceMessageColl;
    }

    /**
     * Method retrieveBasicWorkPerformance.
     * 
     * @param con
     * @param workPerformanceId
     * @param versionNumber
     * @return WorkPerformance
     * @throws PrepSystemException
     */
    private WorkPerformance retrieveBasicWorkPerformance(long workPerformanceId) throws PrepSystemException {
        log.debug("Entering retrieveBasicWorkPerformance method in UsageDAOImpl ");
        WorkPerformance workPerformanceVob = null;
        try {

            log.debug("Query GET_WORK_PERFORMANCE: " + UsageQueries.GET_WORK_PERFORMANCE);

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_WORK_PERFORMANCE, workPerformanceId);

            if (!rs.isAfterLast() && !rs.isBeforeFirst()) {
                return workPerformanceVob;
            }

            if (rs.next()) {
                workPerformanceVob = new WorkPerformance();

                workPerformanceVob.setPerformanceHeaderId(rs.getString(APM_PERF_HDR_ID));
                workPerformanceVob.setWorkPerformanceId(rs.getString("APM_WRK_PERF_ID"));
                workPerformanceVob.setWorkSequenceNumber(rs.getString("WRK_SEQ_NR"));
                workPerformanceVob.setMedleySequenceNumber(rs.getString("MEDL_SEQ"));
                workPerformanceVob.setWorkTitle(rs.getString("WRK_TTL"));
                workPerformanceVob.setWorkId(rs.getString("WRK_ID"));
                workPerformanceVob.setUseTypeCode(rs.getString("USE_TYP"));
                workPerformanceVob.setUseTypeDescription(rs.getString("USE_TYPE_DES"));
                workPerformanceVob.setWorkPerformanceDuration(rs.getString("WRK_PERF_DUR"));
                if (UsageCommonValidations.isValidLong(workPerformanceVob.getWorkPerformanceDuration())) {
                    workPerformanceVob.setWorkPerformanceDurationInMinutes(String.valueOf(
                        (long) Math.floor(Integer.parseInt(workPerformanceVob.getWorkPerformanceDuration()) / 60D)));
                }
                workPerformanceVob.setPerformerName(rs.getString(PFR_NA));
                workPerformanceVob.setPlayCount(rs.getString("PLAY_CNT"));
                workPerformanceVob.setErrorFlag(rs.getString(ERR_STATUS));
                workPerformanceVob.setSourceSystem(rs.getString(SRC_SYS));
                workPerformanceVob.setMusicUserId(rs.getString(PTY_ID));
                workPerformanceVob.setMusicUserLastName(rs.getString("NA"));
                workPerformanceVob.setMusicUserCallLetterSuffix(rs.getString(LGY_MUS_USER_ID));
                workPerformanceVob.setMusicUserFullName(rs.getString(PTYNAFORMUS_FULL_NAME));
                workPerformanceVob.setMusicUserTypeCode(rs.getString(MUS_USER_TYP_CDE));
                workPerformanceVob.setMusicUserTypeDescription(rs.getString(MUSIC_USER_TYPE_DES));
                workPerformanceVob.setPerformanceStartDate(rs.getString(PGM_STT_DT));
                workPerformanceVob.setPerformanceStartTime(rs.getString(PGM_STT_TM));
                workPerformanceVob.setPerformanceEndDate(rs.getString(PGM_END_DT));
                workPerformanceVob.setPerformanceEndTime(rs.getString(PGM_END_TM));
                workPerformanceVob.setProcStatus(rs.getString(PROC_STATUS));
                workPerformanceVob.setProgramTitle(rs.getString(PGM_TTL));
                workPerformanceVob.setProgramNumber(rs.getString(PGM_NUM));
                workPerformanceVob.setLicenseTypeCode(rs.getString(LIC_TYP_CDE));
                workPerformanceVob.setLicenseTypeDescription(rs.getString(LICENSE_TYPE_DES));
                workPerformanceVob.setSupplierCode(rs.getString(SUPP_CODE));
                workPerformanceVob.setTargetYearQuarter(rs.getString(TGTSURVYEARQTR));
                workPerformanceVob.setFileId(rs.getString(APM_ARCHIVE_ID));
                workPerformanceVob.setStatusDate(rs.getString("STATUS_DATE"));
                workPerformanceVob.setProviderId(rs.getString("PROVIDER_ID"));
                workPerformanceVob.setMatchTypeCode(rs.getString("APM_MATCH_TYP"));
                workPerformanceVob.setMatchTypeDes(rs.getString("MATCH_TYPE_DES"));
                workPerformanceVob.setSupplierCode(rs.getString(SUPP_CODE));
                workPerformanceVob.setOnlineHeaderEditable(rs.getString("HDR_EDITABLE"));
                workPerformanceVob.setOnlineWorkPerfEditable(rs.getString("WORK_PERF_EDITABLE"));
                workPerformanceVob.setDetectionTime(rs.getString("DETECTION_TM"));
                workPerformanceVob.setLibraryDiscTitle(rs.getString("LIBRARY_DISC"));
                workPerformanceVob.setLibraryDiscId(rs.getString("LIBRARY_DISC_ID"));
                workPerformanceVob.setWriter(rs.getString("WRITER"));
                workPerformanceVob.setConfidenceLevel(rs.getString("CONFIDENCE_LVL"));

                workPerformanceVob.setIsUsedByDistribution("");

            }

        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }

        log.debug("Entering retrieveBasicWorkPerformance method in UsageDAOImpl ");

        return workPerformanceVob;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#getProgramPerformance(long, int)
     */
    public ProgramPerformance getProgramPerformance(long programPerformanceId, String versionNumber)
        throws PrepSystemException {
        log.debug(
            "Entering public getProgramPerformance(long programPerformanceId,int versionNumber)  method in UsageDAOImpl ");

        ProgramPerformance programPerformance = null;

        try {
            programPerformance = this.retrieveCompleteProgramPerformance(programPerformanceId, versionNumber);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.debug(
            "Exiting public getProgramPerformance(long programPerformanceId,int versionNumber)  method in UsageDAOImpl ");

        return programPerformance;
    }

    /**
     * Method retrieveCompleteProgramPerformance.
     * 
     * @param con
     * @param programPerformanceId
     * @param versionNumber
     * @return ProgramPerformance
     * @throws PrepSystemException
     */
    private ProgramPerformance retrieveCompleteProgramPerformance(long programPerformanceId, String versionNumber)
        throws PrepSystemException {
        log.debug(
            "Entering private ProgramPerformance retriveCompleteProgramPerformance(Connection con, long programPerformance,int versionNumber)  method in UsageDAOImpl ");
        ProgramPerformance programPerformance = null;
        List<Object> errorColl = null;
        try {
            programPerformance = this.retrieveBasicProgramPerformance(programPerformanceId);
            if (programPerformance == null) {
                return programPerformance;
            }
            errorColl = this.retrievePerformanceMessages(UsageConstants.PROGRAM_PERFORMANCE_TYPE, programPerformanceId,
                versionNumber);
            programPerformance.setErrorsAndWarnings(errorColl);
            Iterator<Object> errorIterator = errorColl.iterator();
            PerformanceMessage performanceMessageVob = null;
            while (errorIterator.hasNext()) {
                performanceMessageVob = (PerformanceMessage) errorIterator.next();

                if (performanceMessageVob != null && performanceMessageVob.getMessageCode() != null
                    && (UsageConstants.MUSIC_USER_RELATED_ERRORS_TEMP
                        .containsKey(performanceMessageVob.getMessageCode()))) {
                    programPerformance.setIsInvalidMusicUser("Y");

                    if (log.isDebugEnabled()) {
                        log.debug("Found Music User Errors");
                    }

                    break;
                }

            }
        } catch (Exception pse) {
            // TO BE Handled exception
        }
        log.debug(
            "Exiting private ProgramPerformance retriveCompleteProgramPerformance(Connection con, long programPerformance,int versionNumber) method in UsageDAOImpl ");
        return programPerformance;
    }

    /**
     * Method retrieveBasicProgramPerformance.
     * 
     * @param con
     * @param programPerformanceId
     * @param versionNumber
     * @return ProgramPerformance
     * @throws PrepSystemException
     */
    private ProgramPerformance retrieveBasicProgramPerformance(long programPerformanceId) throws PrepSystemException {

        log.debug("Entering retrieveBasicProgramPerformance method in UsageDAOImpl ");

        ProgramPerformance programPerformanceVob = null;
        try {
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_PROGRAM_PERFORMANCE, programPerformanceId);
            log.debug("Inside retrieveBasicProgramPerformance about to execute query with params :programPerformanceId="
                + programPerformanceId + " " + UsageQueries.GET_PROGRAM_PERFORMANCE);
            if (!rs.isAfterLast() && !rs.isBeforeFirst()) {
                return programPerformanceVob;
            }
            if (rs.next()) {
                programPerformanceVob = new ProgramPerformance();
                programPerformanceVob.setPerformanceHeaderId(rs.getString(APM_PERF_HDR_ID));
                programPerformanceVob.setSourceSystem(rs.getString(SRC_SYS));
                programPerformanceVob.setLegacyMusicUserId(rs.getString(LGY_MUS_USER_ID));
                programPerformanceVob.setLegacyMusicUserIdTypeCode(rs.getString(LGY_MUS_USER_TYP));
                programPerformanceVob.setMusicUserId(rs.getString(PTY_ID));
                programPerformanceVob.setMusicUserTypeCode(rs.getString(MUS_USER_TYP_CDE));
                programPerformanceVob.setLicenseTypeCode(rs.getString(LIC_TYP_CDE));
                programPerformanceVob.setPerformanceStartDate(rs.getString(PGM_STT_DT));
                programPerformanceVob.setPerformanceStartTime(rs.getString(PGM_STT_TM));
                programPerformanceVob.setPerformanceEndDate(rs.getString(PGM_END_DT));
                programPerformanceVob.setPerformanceEndTime(rs.getString(PGM_END_TM));
                programPerformanceVob.setProgramOverlapCode(rs.getString(PGM_OVRLP_CDE));
                programPerformanceVob.setDuration(rs.getString(PGM_DUR));
                programPerformanceVob.setSurveyTypeCode(rs.getString(SUR_TYP_ID));
                programPerformanceVob.setSampleTypeCode(rs.getString(SAM_TYP_ID));
                programPerformanceVob.setSegmentNumber(rs.getString(SEG_NUM));
                programPerformanceVob.setProgramTitle(rs.getString(PGM_TTL));
                programPerformanceVob.setProgramNumber(rs.getString(PGM_NUM));
                programPerformanceVob.setPlayListFlag(rs.getString(SET_LIST_TYP));
                programPerformanceVob.setHeadlineActIndicator(rs.getString(HDLNR_OPNR_IND));
                programPerformanceVob.setNumberOfConcerts(rs.getString(TOUR_CNC_CNT));
                programPerformanceVob.setArtistRevenue(rs.getString(ART_REV));
                programPerformanceVob.setTotalNumberPlays(rs.getString(TOTAL_NUM_PLAYS));
                programPerformanceVob.setProcessStatus(rs.getString(PROC_STATUS));
                programPerformanceVob.setErrorFlag(rs.getString(ERR_STATUS));
                programPerformanceVob.setSupplierCode(rs.getString(SUPP_CODE));
                programPerformanceVob.setMusicUserLastName(rs.getString("NA"));
                programPerformanceVob.setMusicUserFullName(rs.getString(PTYNAFORMUS_FULL_NAME));
                programPerformanceVob.setMusicUserTypeDescription(rs.getString(MUSIC_USER_TYPE_DES));
                programPerformanceVob.setLicenseTypeDescription(rs.getString(LICENSE_TYPE_DES));
                programPerformanceVob.setSurveyTypeDescription(rs.getString("SURVEY_TYPE_DES"));
                programPerformanceVob.setSampleTypeDescription(rs.getString("SAMPLE_TYPE_DES"));
                programPerformanceVob.setTargetYearQuarter(rs.getString(TGTSURVYEARQTR));
                programPerformanceVob.setTotalNumberOfWorkPerformances(rs.getString(WRK_PERF_CNT));
                programPerformanceVob.setFileId(rs.getString(APM_ARCHIVE_ID));
                programPerformanceVob.setPerformerName(rs.getString(PFR_NA));
                programPerformanceVob.setOnlineEditable(rs.getString(EDITABLE));
                programPerformanceVob.setClassicalIndicator(rs.getString("CLASSICAL_IND"));
                programPerformanceVob.setAssignedToUser(rs.getString(ASG_USR_ID));
                programPerformanceVob.setChannelInd(rs.getString("CHANNEL_IND"));

                programPerformanceVob = this.retrievePerformanceMusicUserInfo(programPerformanceVob);
            }

        } catch (Exception se) {
            log.error(se.getMessage());
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }

        log.debug("Exiting retrieveBasicProgramPerformance method in UsageDAOImpl ");

        return programPerformanceVob;
    }

    /**
     * Method retrievePerformanceMusicUserInfo.
     * 
     * @param con
     * @param programPerformanceVob
     * @return ProgramPerformance
     * @throws PrepSystemException
     */
    private ProgramPerformance retrievePerformanceMusicUserInfo(ProgramPerformance programPerformanceVob)
        throws PrepSystemException {
        log.debug("Entering retrievePerformanceMusicUserInfo method in UsageDAOImpl " + programPerformanceVob);

        if (programPerformanceVob == null) {
            return programPerformanceVob;
        }
        try {
            SqlRowSet musicUserTypeRs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_MUSIC_USER_TYPE,
                programPerformanceVob.getMusicUserId(), programPerformanceVob.getPerformanceStartDate());
            if (musicUserTypeRs.next()) {
                programPerformanceVob.setMusicUserId(musicUserTypeRs.getString(PTY_ID));
                programPerformanceVob.setMusicUserTypeCode(musicUserTypeRs.getString(MUS_USER_TYP_CDE));
                programPerformanceVob.setMusicUserTypeDescription(musicUserTypeRs.getString("MUS_USER_TYP_DES"));
            }

            SqlRowSet musicUserLicenseRs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_MUSIC_USER_LICENSE,
                programPerformanceVob.getMusicUserId(), programPerformanceVob.getPerformanceStartDate());

            if (musicUserLicenseRs.next()) {
                programPerformanceVob.setLicenseTypeCode(musicUserLicenseRs.getString(LIC_TYP_CDE));
                programPerformanceVob.setLicenseTypeDescription(musicUserLicenseRs.getString("NA"));
            }

            SqlRowSet musicUserCallLetterRs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_MUSIC_USER_CALL_LETTER,
                programPerformanceVob.getMusicUserId(), programPerformanceVob.getPerformanceStartDate());

            if (musicUserCallLetterRs.next()) {
                programPerformanceVob
                    .setMusicUserCallLetterSuffix(musicUserCallLetterRs.getString("CALL_LETTER_N_SUFF"));
                programPerformanceVob.setMusicUserCallLetterOnly(musicUserCallLetterRs.getString("CALL_LETTER_ONLY"));
                programPerformanceVob
                    .setMusicUserCallLetterSuffixOnly(musicUserCallLetterRs.getString("CALL_LETTER_SUFF_ONLY"));
            }

            // Get Music User Full Name

            SqlRowSet musicUserFullNameRs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_MUSIC_USER_FULL_NAME,
                programPerformanceVob.getMusicUserId());

            // populating the vob
            if (musicUserFullNameRs.next()) {
                programPerformanceVob.setMusicUserLastName(musicUserFullNameRs.getString("NA"));
                programPerformanceVob.setMusicUserFullName(musicUserFullNameRs.getString("FULL_DISPLAY_NAME"));
            }

        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        log.debug("Exiting retrievePerformanceMusicUserInfo method in UsageDAOImpl " + programPerformanceVob);

        return programPerformanceVob;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#getWorkPerformance(long)
     */
    public ProgramPerformance getProgramPerformance(long programPerformanceId) throws PrepSystemException {
        log.debug("Entering public getProgramPerformance method in UsageDAOImpl with Only programPerformanceId");

        ProgramPerformance programPerformance = null;
        String versionNumberString = null;

        try {
            log.debug(
                "Inside public ProgramPerformance getProgramPerformance(long programPerformanceId) versionString is :"
                    + versionNumberString);
            programPerformance = this.retrieveCompleteProgramPerformance(programPerformanceId, versionNumberString);

        } catch (Exception e) {
            // TO BE Handled exception
        }

        log.debug("Exiting public getProgramPerformance method in UsageDAOImpl ");
        return programPerformance;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#deleteProgramPerformances(PerformanceSearch)
     */
    public PerformanceSearch deleteProgramPerformances(PerformanceSearch performanceSearch) throws PrepSystemException {

        try {
            if (performanceSearch == null) {
                return performanceSearch;
            }
            this.removeProgramPerformances(performanceSearch);
        } catch (Exception se) {
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        return performanceSearch;
    }

    /**
     * Method removeProgramPerformances.
     * 
     * @param con
     * @param performanceSearch
     * @return PerformanceSearch
     * @throws PrepSystemException
     */
    private PerformanceSearch removeProgramPerformances(PerformanceSearch performanceSearch)
        throws PrepSystemException {
        log.debug("Entering UsageDAOImpl - removeProgramPerformances method");
        log.debug(PERFORMANCESEARCH_IS + performanceSearch);

        if (performanceSearch == null) {
            return performanceSearch;
        }

        try {
            String[] performanceSearchIds = performanceSearch.getSelectedIds();
            if (performanceSearchIds == null) {
                return performanceSearch;
            }
            String errorsQry = UsageQueries.DELETE_ALL_PROGRAM_PERFORMANCE_MESSAGES;
            String workPerfQry = UsageQueries.DELETE_WORK_PERFORMANCE_FOR_HEADER;
            String headerQry = UsageQueries.DELETE_PROGRAM_PERFORMANCE;
            int updatedCount = 0;
            for (String headerId : performanceSearchIds) {
                updatedCount = ascapJdbcTemplate.update(headerQry, performanceSearch.getUserId(), headerId);
                log.debug("Deleting headerId APM_PERF_HDR_ID " + headerId + " Deleted count " + updatedCount);
                if (updatedCount > 0) {
                    ascapJdbcTemplate.update(errorsQry, headerId);

                    ascapJdbcTemplate.update(workPerfQry, performanceSearch.getUserId(), headerId);
                }
            }
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }
        log.debug(PERFORMANCESEARCH_IS + performanceSearch.toString());
        log.debug("Exiting UsageDAOImpl - removeProgramPerformances method");
        return performanceSearch;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#searchProgramPerformances(PerformanceSearch)
     */
    public PerformanceSearch searchProgramPerformances(PerformanceSearch performanceSearchCriteria)
        throws PrepSystemException {
        log.debug("Entering UsageDAOImpl - searchProgramPerformances method vob is " + performanceSearchCriteria);

        if (performanceSearchCriteria != null) {
            log.debug("HeaderIdCurrentRowNum " + performanceSearchCriteria.getHeaderIdCurrentRowNum());
        }
        ArrayList<Serializable> profileMessagesList = new ArrayList<>();
        if (log.isDebugEnabled()) {
            profileMessagesList.add("searchProgramPerformances Invoked :" + System.currentTimeMillis());
        }
        int headerIdRowNum = 0;
        try {
            if (performanceSearchCriteria.getHeaderIdCurrentRowNum() != null) {
                headerIdRowNum = Integer.parseInt(performanceSearchCriteria.getHeaderIdCurrentRowNum());
            }
        } catch (Exception e) {
            headerIdRowNum = 0;
        }

        StringBuilder debugSearchStatement;
        StringBuilder searchProgramPerformancesSqlStr;
        StringBuilder searchProgramPerformancesWherePart;
        StringBuilder searchCountProgramPerformancesSqlStr;
        StringBuilder searchJOINSPart;
        boolean bothStartDateAndTimeareGiven = false;
        int totalParameters;
        java.util.ArrayList<String> searchProgramPerformancesValuesList;

        List<Object> programPerformanceCollection = new ArrayList<>();

        BitSet tableJoinDependencies = new BitSet(UsageQueries.BIT_MASK_SIZE);

        boolean isPerfHdrPredicate = true;
        boolean isWrkPerfPredicate = false;
        boolean isWrkPerfShrPredicate = false;
        boolean isCallLetterPredicate = false;
        boolean isMusicUserTypePredicate = false;
        boolean isMusicUserLicensePredicate = false;
        boolean isShareOwnerPrdeicate = false;
        boolean isUsageErrorForPerfHdrPredicate = false;
        boolean isUsageErrorForWrkPerfPredicate = false;

        searchProgramPerformancesSqlStr = new StringBuilder(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_PART1);
        searchCountProgramPerformancesSqlStr =
            new StringBuilder(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_COUNT_PART1);
        searchProgramPerformancesValuesList = new ArrayList<>();

        if ((!ValidationUtils.isEmptyOrNull(performanceSearchCriteria.getFeaturedPerformerName())
            && "CNTS".equals(performanceSearchCriteria.getPerformerSearchType()))
            || (!ValidationUtils.isEmptyOrNull(performanceSearchCriteria.getWorkTitle())
                && "CNTS".equals(performanceSearchCriteria.getWorkTitleSearchType()))) {
            log.debug("Main Search Contains Begins search found.............");
            searchProgramPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_CONTEXT_START);
            searchCountProgramPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_CONTEXT_START);
            int contextSearchNr = 0;
            if (!ValidationUtils.isEmptyOrNull(performanceSearchCriteria.getWorkTitle())
                && "CNTS".equals(performanceSearchCriteria.getWorkTitleSearchType())) {
                contextSearchNr++;
                searchProgramPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_TTL_CONTAINS_SEARCH);
                searchCountProgramPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_TTL_CONTAINS_SEARCH);
                searchProgramPerformancesValuesList.add(performanceSearchCriteria.getWorkTitle());
            }

            if (contextSearchNr > 0
                && (!ValidationUtils.isEmptyOrNull(performanceSearchCriteria.getFeaturedPerformerName())
                    && "CNTS".equals(performanceSearchCriteria.getPerformerSearchType()))) {
                searchProgramPerformancesSqlStr.append(INTERSECT);
                searchCountProgramPerformancesSqlStr.append(INTERSECT);
            }

            if (!ValidationUtils.isEmptyOrNull(performanceSearchCriteria.getFeaturedPerformerName())
                && "CNTS".equals(performanceSearchCriteria.getPerformerSearchType())) {
                searchProgramPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_PFR_CONTAINS_SEARCH);
                searchCountProgramPerformancesSqlStr.append(UsageQueries.GET_MAIN_SEARCH_PFR_CONTAINS_SEARCH);
                searchProgramPerformancesValuesList.add(performanceSearchCriteria.getFeaturedPerformerName());
            }
            searchProgramPerformancesSqlStr.append(" )) ");
            searchCountProgramPerformancesSqlStr.append(" )) ");
        }
        searchCountProgramPerformancesSqlStr.append(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_COUNT_PART1_END);
        searchProgramPerformancesSqlStr.append(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_PART1_END);

        debugSearchStatement = new StringBuilder(PROC_STATUS_NEW);
        searchProgramPerformancesWherePart = new StringBuilder(PROC_STATUS_NEW);

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getProgramPerformanceId())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.APM_PERF_HDR_ID = ?");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getProgramPerformanceId());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.APM_PERF_HDR_ID = '")
                    .append(performanceSearchCriteria.getProgramPerformanceId()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPostedFlag())) {
            isPerfHdrPredicate = true;

            if ("Y".equals(performanceSearchCriteria.getPostedFlag())) {
                searchProgramPerformancesWherePart.append(AND_PROC_STATUS);
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(AND_PROC_STATUS);
                }
            } else {
                searchProgramPerformancesWherePart.append(PROC_STATUS_PSTD);
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(PROC_STATUS_PSTD);
                }
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceStartDate())
            || !UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceEndDate())
            || !UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceStartTime())
            || !UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceEndTime())) {
            bothStartDateAndTimeareGiven = true;
        }

        if (bothStartDateAndTimeareGiven) {

            isPerfHdrPredicate = true;

            if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceStartDate())) {
                searchProgramPerformancesWherePart.append(" AND A.PGM_STT_DT >= ?");
                searchProgramPerformancesValuesList.add(performanceSearchCriteria.getPerformanceStartDate());
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(" AND A.PGM_STT_DT >= '")
                        .append(performanceSearchCriteria.getPerformanceStartDate() + "'");
                }
            }

            if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceEndDate())) {
                searchProgramPerformancesWherePart.append(" AND A.PGM_STT_DT <=  ? ");
                searchProgramPerformancesValuesList.add(performanceSearchCriteria.getPerformanceEndDate());
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(" AND A.PGM_STT_DT <= '")
                        .append(performanceSearchCriteria.getPerformanceEndDate()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceStartTime())) {
                searchProgramPerformancesWherePart.append(" AND A.PGM_STT_TM >= ?");
                searchProgramPerformancesValuesList.add(performanceSearchCriteria.getPerformanceStartTime());
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(" AND A.PGM_STT_TM >= '")
                        .append(performanceSearchCriteria.getPerformanceStartTime() + "'");
                }
            }

            if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPerformanceEndTime())) {
                searchProgramPerformancesWherePart.append(" AND A.PGM_STT_TM <=  ? ");
                searchProgramPerformancesValuesList.add(performanceSearchCriteria.getPerformanceEndTime());
                if (log.isDebugEnabled()) {
                    debugSearchStatement.append(" AND A.PGM_STT_TM <= '")
                        .append(performanceSearchCriteria.getPerformanceEndTime()).append("'");
                }
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getMusicUserId())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.PTY_ID = ?  AND A.PTY_ID is not null ");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getMusicUserId());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.PTY_ID = '").append(performanceSearchCriteria.getMusicUserId())
                    .append("'  AND A.PTY_ID is not null ");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getOverlapCode(), true)) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(PGM_OVRLP_CDE_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getOverlapCode())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(PGM_OVRLP_CDE_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getOverlapCode())).append(" ");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getSupplierCode())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.SUPP_CODE = ?");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getSupplierCode());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.SUPP_CODE = '").append(performanceSearchCriteria.getSupplierCode())
                    .append("'");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getSampleTypes(), true)) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(SAM_TYP_ID_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getSampleTypes())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(SAM_TYP_ID_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getSampleTypes())).append(" ");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getSurveyTypes(), true)) {
            isPerfHdrPredicate = true;

            searchProgramPerformancesWherePart.append(AND_SUR_TYP_ID_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getSurveyTypes())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_SUR_TYP_ID_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getSurveyTypes())).append(" ");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getLegacySourceSystem(),
            true)) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(AND_SRC_SYS_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getLegacySourceSystem())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_SRC_SYS_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getLegacySourceSystem())).append(" ");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getFileId())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.APM_ARCHIVE_ID = ?");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getFileId().toUpperCase());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.APM_ARCHIVE_ID = '")
                    .append(performanceSearchCriteria.getFileId().toUpperCase()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getProgramNumber())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.PGM_NUM = ?");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getProgramNumber());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.PGM_NUM = '").append(performanceSearchCriteria.getProgramNumber())
                    .append("'");
            }
        }
        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getSegmentNumber())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.SEG_NUM = ?");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getSegmentNumber());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.SEG_NUM = '").append(performanceSearchCriteria.getSegmentNumber())
                    .append("'");
            }
        }
        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getStatusDateFrom())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND TRUNC(A.STATUS_DATE) >= TO_DATE(?,'YYYY/MM/DD') ");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getStatusDateFrom());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_TRUNC_STATUS_DATE_TO_DATE)
                    .append(performanceSearchCriteria.getStatusDateFrom()).append(YYYY_MM_DD);
            }
        }
        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getStatusDateTo())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND TRUNC(A.STATUS_DATE) <= TO_DATE(?,'YYYY/MM/DD') ");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getStatusDateTo());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_TRUNC_STATUS_DATE_TO_DATE)
                    .append(performanceSearchCriteria.getStatusDateTo()).append(YYYY_MM_DD);
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getSetlistType())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.SET_LIST_TYP = ? ");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getSetlistType());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.SET_LIST_TYP =  '")
                    .append(performanceSearchCriteria.getSetlistType()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getHeadlineIndicator())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.HDLNR_OPNR_IND = ? ");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getHeadlineIndicator());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.HDLNR_OPNR_IND = '")
                    .append(performanceSearchCriteria.getHeadlineIndicator()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getTargetYearQuarterFrom())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.TGTSURVYEARQTR >= ? ");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getTargetYearQuarterFrom());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.TGTSURVYEARQTR >= '")
                    .append(performanceSearchCriteria.getTargetYearQuarterFrom()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getTargetYearQuarterTo())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.TGTSURVYEARQTR <= ? ");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getTargetYearQuarterTo());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.TGTSURVYEARQTR <= '")
                    .append(performanceSearchCriteria.getTargetYearQuarterTo()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getClassicalIndicator())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.CLASSICAL_IND = ? ");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getClassicalIndicator());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.CLASSICAL_IND = '")
                    .append(performanceSearchCriteria.getClassicalIndicator()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getAssignedToUser())) {
            isPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(" AND A.ASG_USR_ID = ? ");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getAssignedToUser());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND A.ASG_USR_ID = '")
                    .append(performanceSearchCriteria.getAssignedToUser()).append("'");
            }
        }

        String callLetters = performanceSearchCriteria.getMusicUserCallLetter();

        if (callLetters == null) {
            callLetters = "";
        }

        StringTokenizer st = new StringTokenizer(callLetters, ",");
        String callLetter = null;

        int tokensCount = st.countTokens();

        if (tokensCount > 0) {
            isCallLetterPredicate = false;
            searchProgramPerformancesWherePart.append(AND);
            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND);
            }
        }

        while (st.hasMoreTokens()) {
            tokensCount--;
            callLetter = (String) st.nextElement();

            searchProgramPerformancesWherePart.append(LGY_MUS_USER_ID_LIKE);
            searchProgramPerformancesValuesList.add((callLetter.toUpperCase() + "%"));

            if (tokensCount > 0) {
                searchProgramPerformancesWherePart.append(OR);
            } else {
                searchProgramPerformancesWherePart.append(" ) ");
            }

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(LGY_MUS_USER_ID_LIKE);
                if (tokensCount > 0) {
                    debugSearchStatement.append(OR);
                } else {
                    debugSearchStatement.append(" ) ");
                }
            }
        }
        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getMusicUserTypes(),
            true)) {
            isMusicUserTypePredicate = true;
            searchProgramPerformancesWherePart.append(AND_MUS_USER_TYP_CDE_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getMusicUserTypes())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_MUS_USER_TYP_CDE_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getMusicUserTypes())).append(" ");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getLicenseTypeCode(),
            true)) {
            isMusicUserLicensePredicate = true;

            searchProgramPerformancesWherePart.append(LIC_TYP_CDE_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getLicenseTypeCode())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(LIC_TYP_CDE_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getLicenseTypeCode())).append(" ");
            }
        }
        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getWorkPerformanceId())) {
            isWrkPerfPredicate = true;
            searchProgramPerformancesWherePart.append(" AND B.APM_WRK_PERF_ID = ?");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getWorkPerformanceId());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND B.APM_WRK_PERF_ID = '")
                    .append(performanceSearchCriteria.getWorkPerformanceId()).append("'");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getWorkTitle())) {
            isWrkPerfPredicate = true;

            if ("CNTS".equals(performanceSearchCriteria.getWorkTitleSearchType())) {
                searchProgramPerformancesWherePart.append(
                    " AND B.WRK_TTL LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'TITLE', 'BGNS') FROM DUAL) ");
                searchProgramPerformancesValuesList.add(performanceSearchCriteria.getWorkTitle());
                if (log.isDebugEnabled()) {
                    debugSearchStatement
                        .append(" AND B.WRK_TTL LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM('"
                            + performanceSearchCriteria.getWorkTitle() + "', 'TITLE', 'BGNS') FROM DUAL) ");
                }
            }

        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getFeaturedPerformerName())) {
            isWrkPerfPredicate = true;

            if ("CNTS".equals(performanceSearchCriteria.getPerformerSearchType())) {
                searchProgramPerformancesWherePart.append(
                    " AND B.PFR_NA LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'NAME', 'BGNS') FROM DUAL) ");
                searchProgramPerformancesValuesList.add(performanceSearchCriteria.getFeaturedPerformerName());
                if (log.isDebugEnabled()) {
                    debugSearchStatement
                        .append(" AND B.PFR_NA LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM('"
                            + performanceSearchCriteria.getFeaturedPerformerName() + "', 'NAME', 'BGNS') FROM DUAL) ");
                }
            }

        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getPlayCount())) {
            isWrkPerfPredicate = true;
            searchProgramPerformancesWherePart.append(" AND  LPAD(B.PLAY_CNT, 20 , '0')  >= LPAD( ?, 20,'0') ");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getPlayCount());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND LPAD(B.PLAY_CNT, 20 , '0') >= LPAD( '")
                    .append(performanceSearchCriteria.getPlayCount()).append("', 20,'0')");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getSupplierWorkCode())) {
            isWrkPerfPredicate = true;
            searchProgramPerformancesWherePart.append(" AND B.PROVIDER_ID = ?");
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getSupplierWorkCode());

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" AND B.PROVIDER_ID = '")
                    .append(performanceSearchCriteria.getSupplierWorkCode()).append("'");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getUseTypes(), true)) {
            isWrkPerfPredicate = true;
            searchProgramPerformancesWherePart.append(AND_USE_TYP_IN)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getUseTypes())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_USE_TYP_IN)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getUseTypes())).append(" ");
            }
        }

        if (!UsageCommonValidations.isCollectionEmptyOrNoElements(performanceSearchCriteria.getMatchTypes(), true)) {
            isWrkPerfPredicate = true;
            searchProgramPerformancesWherePart.append(AND_B_APM_MATCH_TYP)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getMatchTypes())).append(" ");

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_B_APM_MATCH_TYP)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getMatchTypes())).append(" ");
            }
        }

        if (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getWorkId())) {
            isWrkPerfPredicate = true;
            searchProgramPerformancesWherePart.append(AND_WORK_ID_IN)
                .append(UsageQueries.DYNAMIC_SEARCH_CRITERIA_MERGED_WRK_IDS);
            searchProgramPerformancesValuesList.add(performanceSearchCriteria.getWorkId());
            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_WORK_ID_IN).append(UsageQueries.DYNAMIC_SEARCH_CRITERIA_MERGED_WRK_IDS)
                    .append("/* USE Work ID => ").append(performanceSearchCriteria.getWorkId()).append(" */ ");
            }

        }

        if (!UsageCommonValidations
            .isCollectionEmptyOrNoElements(performanceSearchCriteria.getPerformanceErrorWarningCodes(), true)) {
            isUsageErrorForPerfHdrPredicate = true;
            searchProgramPerformancesWherePart.append(AND_ALL_ERRORS_USE_EWI_CDE)
                .append(this.buildInStringCriteria(performanceSearchCriteria.getPerformanceErrorWarningCodes()));

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(AND_ALL_ERRORS_USE_EWI_CDE)
                    .append(this.buildInStringCriteria(performanceSearchCriteria.getPerformanceErrorWarningCodes()));
            }

        }

        if (isPerfHdrPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_PERF_HDR);
        }
        if (isWrkPerfPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_WRK_PERF);
        }
        if (isWrkPerfShrPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_WRK_PERF_SHR);
        }
        if (isCallLetterPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_CALL_LETTER);
        }
        if (isMusicUserTypePredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_MUS_USER_TYP);
        }
        if (isMusicUserLicensePredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_MUS_USER_LIC);
        }
        if (isShareOwnerPrdeicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_SHR_OWNER_PTY);
        }
        if (isUsageErrorForPerfHdrPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_USAGE_ERROR_FOR_PERF_HDR);
        }
        if (isUsageErrorForWrkPerfPredicate) {
            tableJoinDependencies.or(UsageQueries.JOIN_DEP_MASK_USAGE_ERROR_FOR_WRK_PERF);
        }

        searchJOINSPart = new StringBuilder();

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_A_PERF_HDR)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_PERF_HDR_A);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_B_WRK_PERF)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_WRK_PERF_B);
            if ((!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getWorkTitle())
                && "CNTS".equals(performanceSearchCriteria.getWorkTitleSearchType()))
                || (!UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getFeaturedPerformerName())
                    && "CNTS".equals(performanceSearchCriteria.getPerformerSearchType()))) {
                searchJOINSPart.append(" INNER JOIN ctx ON (b.apm_wrk_perf_id = ctx.apm_wrk_perf_id)");
            }
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_C_WRK_PERF_SHR)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_WRK_PERF_SHR_C);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_D_PTY_FOR_MUSICUSER)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_PTY_D);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_F_PTY_NA_FOR_CALLLETTER)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_CALL_LETTERS_F);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_J_MUS_USER_TYP_SEL)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_TYPE_J);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_K_MUS_USER_LIC)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_LICENSE_K);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_ALL_ERRORS_PERF_HDR)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_PERF_HDR_ALL_ERRORS);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_ALL_ERRORS_WRK_PERF)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_WRK_PERF_ALL_ERRORS);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_E_PTY_FOR_SHAREOWNERPARTY)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_SHAREOWNER_PARTYID_E);
        }

        if (tableJoinDependencies.get(UsageQueries.BIT_POSITION_G_PTY_NA_FOR_SHAREOWNERPARTY)) {
            searchJOINSPart.append(UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_SHAREOWNER_PARTY_NA_ID_G);
        }

        log.debug("JOIN CLAUSE IS .......................................................................\n"
            + searchJOINSPart);

        try {

            if (log.isDebugEnabled()) {
                profileMessagesList.add("searchProgramPerformances Query Prepared :" + System.currentTimeMillis());
            }

            int count = 0;
            StringBuilder debugSearchStatementJoin = null;

            if (UsageConstants.SEARCH_RESULTS_COUNT_POLICY_LIMIT_TOMAX
                .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())) {
                // Building ProgramPerformances Search Count
                searchCountProgramPerformancesSqlStr.append(searchJOINSPart.toString());
                searchCountProgramPerformancesSqlStr.append(searchProgramPerformancesWherePart);
                searchCountProgramPerformancesSqlStr
                    .append(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_COUNT_LIMT_PART2);

                debugSearchStatementJoin = new StringBuilder(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_PART1);
                debugSearchStatementJoin.append(searchJOINSPart).append(debugSearchStatement);
                debugSearchStatement = debugSearchStatementJoin;
            } else if (UsageConstants.SEARCH_RESULTS_COUNT_POLICY_EXACT_COUNT
                .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())
                || UsageConstants.SEARCH_RESULTS_COUNT_POLICY_DONOT_COUNT
                    .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())) {
                // Building ProgramPerformances Search Count
                searchCountProgramPerformancesSqlStr.append(searchJOINSPart.toString());
                searchCountProgramPerformancesSqlStr.append(searchProgramPerformancesWherePart);
                searchCountProgramPerformancesSqlStr
                    .append(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_COUNT_PART2);

                debugSearchStatementJoin = new StringBuilder(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_PART1);
                debugSearchStatementJoin.append(searchJOINSPart).append(debugSearchStatement);
                debugSearchStatement = debugSearchStatementJoin;
            } else {
                log.debug(
                    "Should NEVER Happen !!! Search Results Count Policy is invalid ... performanceSearchCriteria.getSearchResultsCountPolicy() = "
                        + performanceSearchCriteria.getSearchResultsCountPolicy());
                // Building ProgramPerformances Search Count
                searchCountProgramPerformancesSqlStr.append(searchJOINSPart.toString());
                searchCountProgramPerformancesSqlStr.append(searchProgramPerformancesWherePart);
                searchCountProgramPerformancesSqlStr
                    .append(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_COUNT_PART2);

                debugSearchStatementJoin = new StringBuilder(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_PART1);
                debugSearchStatementJoin.append(searchJOINSPart).append(debugSearchStatement);
                debugSearchStatement = debugSearchStatementJoin;
            }

            log.debug("\n--ORIGINAL COUNT Generated Search ProgramPerformances Count Search SQL String :\n"
                + searchCountProgramPerformancesSqlStr.toString());
            log.debug(
                "\n--Generated Search DEBUG SQL BEFORE EXECUTING COUNT String :\n" + debugSearchStatement.toString());

            if (UsageConstants.SEARCH_RESULTS_COUNT_POLICY_LIMIT_TOMAX
                .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())) {

                totalParameters = searchProgramPerformancesValuesList.size();
                ArrayList<Object> params = new ArrayList<>();
                for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                    params.add((String) searchProgramPerformancesValuesList.get(parameterId));
                }

                params.add(PrepExtPropertyHelper.getInstance()
                    .getPropertyValue(PrepPropertiesConstants.MAX_SEARCHRESULTS_THRESHOLD));

                log.debug("\n--EXECUTING COUNT Query (SEARCH_RESULTS_COUNT_POLICY_LIMIT_TOMAX):\n"
                    + searchCountProgramPerformancesSqlStr.toString() + TOTALPARAMETERS + totalParameters);

                profileMessagesList.add("searchProgramPerformances Count Started TO MAX:" + System.currentTimeMillis());

                SqlRowSet rs =
                    ascapJdbcTemplate.queryForRowSet(searchCountProgramPerformancesSqlStr.toString(), params.toArray());

                count = 0;

                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } else if (UsageConstants.SEARCH_RESULTS_COUNT_POLICY_EXACT_COUNT
                .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())) {
                log.debug("Manoj Executing Count Query: " + searchCountProgramPerformancesSqlStr.toString());

                totalParameters = searchProgramPerformancesValuesList.size();
                ArrayList<Object> params = new ArrayList<>();
                for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                    log.debug("Manoj Value settting " + parameterId + " : "
                        + searchProgramPerformancesValuesList.get(parameterId));
                    params.add((String) searchProgramPerformancesValuesList.get(parameterId));
                }

                log.debug("\n--EXECUTING COUNT Query (SEARCH_RESULTS_COUNT_POLICY_EXACT_COUNT):\n"
                    + searchCountProgramPerformancesSqlStr.toString() + TOTALPARAMETERS + totalParameters);

                if (log.isDebugEnabled()) {
                    profileMessagesList
                        .add("searchProgramPerformances Count Started EXACT:" + System.currentTimeMillis());
                }

                SqlRowSet rs =
                    ascapJdbcTemplate.queryForRowSet(searchCountProgramPerformancesSqlStr.toString(), params.toArray());

                count = 0;

                if (rs.next()) {
                    count = rs.getInt(1);
                }

            } else if (UsageConstants.SEARCH_RESULTS_COUNT_POLICY_DONOT_COUNT
                .equalsIgnoreCase(performanceSearchCriteria.getSearchResultsCountPolicy())) {
                if ("Y".equalsIgnoreCase(performanceSearchCriteria.getProgressiveEndReached())) {
                    count = performanceSearchCriteria.getNumberOfRecordsFoundbySearch();
                } else {
                    count = Integer.parseInt(PrepExtPropertyHelper.getInstance()
                        .getPropertyValue(PrepPropertiesConstants.MAX_SEARCHRESULTS_THRESHOLD));
                }
            } else {
                log.debug(
                    "Should NEVER Happen !!! Search Results Count Policy is invalid assuming MAX Results Threshold as result count... performanceSearchCriteria.getSearchResultsCountPolicy() = "
                        + performanceSearchCriteria.getSearchResultsCountPolicy());
                count = Integer.parseInt(PrepExtPropertyHelper.getInstance()
                    .getPropertyValue(PrepPropertiesConstants.MAX_SEARCHRESULTS_THRESHOLD));
            }

            log.debug("\n--Search Results Count Policy" + performanceSearchCriteria.getSearchResultsCountPolicy()
                + ", COUNT Determined :" + count);

            performanceSearchCriteria.setNumberOfRecordsFound(count);

            if (log.isDebugEnabled()) {
                profileMessagesList.add("searchProgramPerformances Count Finished:" + System.currentTimeMillis());
            }

            searchProgramPerformancesSqlStr.append(searchJOINSPart.toString());
            searchProgramPerformancesSqlStr.append(searchProgramPerformancesWherePart);
            searchProgramPerformancesSqlStr.append(UsageQueries.DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_PART2);

            if (log.isDebugEnabled()) {
                debugSearchStatement.append(" ) ) a1 where rn between ")
                    .append(performanceSearchCriteria.getFromIndex()).append(" and ")
                    .append(performanceSearchCriteria.getToIndex() - 1).append(" ");
            }

            log.debug("\n--ORIGINAL SEARCH Generated Search ProgramPerformancesSearch SQL String :\n"
                + searchProgramPerformancesSqlStr.toString());
            log.debug("\n--Generated Search DEBUG SQL String :\n" + debugSearchStatement.toString());

            log.debug("Total records found: " + count);
            log.debug("Getting records from Index " + performanceSearchCriteria.getFromIndex() + " To Index :"
                + (performanceSearchCriteria.getToIndex() - 1));
            // Adding From Index and To Index to the Values List

            if (headerIdRowNum > 0) {
                searchProgramPerformancesValuesList.add(Integer.toString(headerIdRowNum + 1)); // MAX ROW
                searchProgramPerformancesValuesList.add(Integer.toString(headerIdRowNum - 1)); // MIN ROW
            } else {
                searchProgramPerformancesValuesList.add(Integer.toString(performanceSearchCriteria.getToIndex() - 1)); // MAX
                // ROW
                searchProgramPerformancesValuesList.add(Integer.toString(performanceSearchCriteria.getFromIndex())); // MIN
                // ROW
            }

            totalParameters = searchProgramPerformancesValuesList.size();
            ArrayList<Object> params = new ArrayList<>();
            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                params.add((String) searchProgramPerformancesValuesList.get(parameterId));
            }

            if (log.isDebugEnabled()) {
                profileMessagesList.add("searchProgramPerformances Search Query Started:" + System.currentTimeMillis());
            }

            SqlRowSet rs =
                ascapJdbcTemplate.queryForRowSet(searchProgramPerformancesSqlStr.toString(), params.toArray());

            if (log.isDebugEnabled()) {
                profileMessagesList
                    .add("searchProgramPerformances Search Query Finished:" + System.currentTimeMillis());
            }

            ProgramPerformance programPerformanceVob = null;
            long tempNumberOfRecordsFetchedNow = 0;

            log.debug(
                "Before Fetching Results getOldNavigationType() =" + performanceSearchCriteria.getOldNavigationType()
                    + GET_PROGRESSIVE_RESULTS_COUNT + performanceSearchCriteria.getProgressiveResultsCount()
                    + TEMP_NUMBER_OF_RECORDS_FETCHEDNOW + tempNumberOfRecordsFetchedNow);

            while (rs.next()) {

                tempNumberOfRecordsFetchedNow++;
                programPerformanceVob = new ProgramPerformance();

                programPerformanceVob.setHeaderIdPrev(rs.getString("PREV_ID"));
                programPerformanceVob.setHeaderIdNext(rs.getString("NEXT_ID"));

                if (headerIdRowNum <= 0) {
                    programPerformanceVob.setHeaderIdCurrentRowNum(
                        "" + (headerIdRowNum + performanceSearchCriteria.getFromIndex() + rs.getInt(CURR_ROW_NUM) - 1));
                } else {
                    if ((headerIdRowNum + rs.getInt(CURR_ROW_NUM) - 2) <= 0) {
                        programPerformanceVob.setHeaderIdCurrentRowNum("" + (1));
                    } else {
                        programPerformanceVob
                            .setHeaderIdCurrentRowNum("" + (headerIdRowNum + rs.getInt(CURR_ROW_NUM) - 2));
                    }
                }

                programPerformanceVob.setHeaderIdCurrent(rs.getString(APM_PERF_HDR_ID));

                programPerformanceVob.setPerformanceHeaderId(rs.getString(APM_PERF_HDR_ID));
                programPerformanceVob.setSourceSystem(rs.getString(SRC_SYS));
                programPerformanceVob.setLegacyMusicUserId(rs.getString(LGY_MUS_USER_ID));
                programPerformanceVob.setLegacyMusicUserIdTypeCode(rs.getString(LGY_MUS_USER_TYP));
                programPerformanceVob.setMusicUserId(rs.getString(PTY_ID));
                programPerformanceVob.setMusicUserTypeCode(rs.getString(MUS_USER_TYP_CDE));
                programPerformanceVob.setLicenseTypeCode(rs.getString(LIC_TYP_CDE));
                programPerformanceVob.setPerformanceStartDate(rs.getString(PGM_STT_DT));
                programPerformanceVob.setPerformanceStartTime(rs.getString(PGM_STT_TM));
                programPerformanceVob.setPerformanceEndDate(rs.getString(PGM_END_DT));
                programPerformanceVob.setPerformanceEndTime(rs.getString(PGM_END_TM));
                programPerformanceVob.setProgramOverlapCode(rs.getString(PGM_OVRLP_CDE));
                programPerformanceVob.setDuration(rs.getString(PGM_DUR));
                programPerformanceVob.setSurveyTypeCode(rs.getString(SUR_TYP_ID));
                programPerformanceVob.setSampleTypeCode(rs.getString(SAM_TYP_ID));
                programPerformanceVob.setSegmentNumber(rs.getString(SEG_NUM));
                programPerformanceVob.setProgramTitle(rs.getString(PGM_TTL));
                programPerformanceVob.setProgramNumber(rs.getString(PGM_NUM));
                programPerformanceVob.setPlayListFlag(rs.getString(SET_LIST_TYP));
                programPerformanceVob.setHeadlineActIndicator(rs.getString(HDLNR_OPNR_IND));
                programPerformanceVob.setNumberOfConcerts(rs.getString(TOUR_CNC_CNT));
                programPerformanceVob.setArtistRevenue(rs.getString(ART_REV));
                programPerformanceVob.setErrorFlag(rs.getString(ERR_STATUS));
                programPerformanceVob.setProcessStatus(rs.getString(PROC_STATUS));
                programPerformanceVob.setStatusDate(rs.getString("STATUS_DATE"));
                programPerformanceVob.setSupplierCode(rs.getString(SUPP_CODE));
                programPerformanceVob.setMusicUserCallLetterSuffix(rs.getString("CALL_LETTER_N_SUFF"));
                programPerformanceVob.setMusicUserCallLetterOnly(rs.getString("CALL_LETTER_ONLY"));
                programPerformanceVob.setMusicUserCallLetterSuffixOnly(rs.getString("CALL_LETTER_SUFF_ONLY"));
                programPerformanceVob.setMusicUserTypeDescription(rs.getString(MUSIC_USER_TYPE_DES));
                programPerformanceVob.setTotalNumberOfWorkPerformances(rs.getString(WRK_PERF_CNT));
                programPerformanceVob.setTotalNumberPlays(rs.getString(TOTAL_NUM_PLAYS));
                programPerformanceVob.setOnlineEditable(rs.getString(EDITABLE));
                programPerformanceVob.setAssignedToUser(rs.getString(ASG_USR_ID));

                programPerformanceCollection.add(programPerformanceVob);
            }

            // For managing Progressive counts
            if ("NEXT".equalsIgnoreCase(performanceSearchCriteria.getOldNavigationType())
                || "FIRST".equalsIgnoreCase(performanceSearchCriteria.getOldNavigationType())
                || UsageCommonValidations.isEmptyOrNull(performanceSearchCriteria.getOldNavigationType())) {
                long tempTotalResultsFound = 0;
                if (tempNumberOfRecordsFetchedNow >= 0) {
                    tempTotalResultsFound =
                        (performanceSearchCriteria.getFromIndex() - 1) + tempNumberOfRecordsFetchedNow;
                    performanceSearchCriteria.setProgressiveResultsCount(tempTotalResultsFound);
                }
            } else if ("LAST".equalsIgnoreCase(performanceSearchCriteria.getOldNavigationType())
                || ("PREV".equalsIgnoreCase(performanceSearchCriteria.getOldNavigationType()))) {
                long tempTotalResultsFound = 0;
                if (tempNumberOfRecordsFetchedNow >= 0) {
                    tempTotalResultsFound =
                        (performanceSearchCriteria.getFromIndex() - 1) + tempNumberOfRecordsFetchedNow;
                    if (tempTotalResultsFound >= performanceSearchCriteria.getProgressiveResultsCount()) {
                        performanceSearchCriteria.setProgressiveResultsCount(tempTotalResultsFound);
                    }
                }
            }

            // For deciding if Progressive counts reached an end
            if (("NEXT".equalsIgnoreCase(performanceSearchCriteria.getOldNavigationType()))
                && (tempNumberOfRecordsFetchedNow == 0)) {
                performanceSearchCriteria.setProgressiveEndReached("Y");
                performanceSearchCriteria
                    .setNumberOfRecordsFound((int) performanceSearchCriteria.getProgressiveResultsCount());
            }

            log.debug("After Fetching Results and Resetting getOldNavigationType() ="
                + performanceSearchCriteria.getOldNavigationType() + GET_PROGRESSIVE_RESULTS_COUNT
                + performanceSearchCriteria.getProgressiveResultsCount() + TEMP_NUMBER_OF_RECORDS_FETCHEDNOW
                + tempNumberOfRecordsFetchedNow);

            performanceSearchCriteria.setSearchResults(programPerformanceCollection);

            if (log.isDebugEnabled()) {
                profileMessagesList
                    .add("searchProgramPerformances Finished Setting Results :" + System.currentTimeMillis());
            }

        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        } finally {
            performanceSearchCriteria.setExecutionProfilingMessages(profileMessagesList);
        }

        if (log.isDebugEnabled()) {
            profileMessagesList.add("searchProgramPerformances Exiting :" + System.currentTimeMillis());
        }

        performanceSearchCriteria.setExecutionProfilingMessages(profileMessagesList);

        return performanceSearchCriteria;

    }

    public PerformanceSearch updateAssignedUsers(PerformanceSearch performanceSearch) throws PrepSystemException {
        try {
            if (performanceSearch == null) {
                return performanceSearch;
            }
            this.updateAssignedUsersToHeaders(performanceSearch);
        } catch (Exception se) {
            log.error(se.getMessage());
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        return performanceSearch;

    }

    private PerformanceSearch updateAssignedUsersToHeaders(PerformanceSearch performanceSearch)
        throws PrepSystemException {
        log.debug("Entering UsageDAOImpl - updateAssignedUsersToHeaders method");
        log.debug(PERFORMANCESEARCH_IS + performanceSearch);

        if (performanceSearch == null) {
            return performanceSearch;
        }

        try {
            String[] performanceSearchIds = performanceSearch.getSelectedIds();
            if (performanceSearchIds == null) {
                return performanceSearch;
            }

            String qry =
                "UPDATE APM_PERF_HDR SET ASG_USR_ID = ?, UPD_DT = SYSDATE, UPD_ID = ? WHERE APM_PERF_HDR_ID = ? ";
            int updatedCount = 0;
            log.debug("Update value to : " + performanceSearch.getAssignedToUserAll());
            for (String headerId : performanceSearchIds) {
                updatedCount = 0;

                ArrayList<Object> params = new ArrayList<>();
                if ("ASSIGN".equals(performanceSearch.getAssignType())) {
                    params.add(performanceSearch.getAssignedToUserAll());
                } else if ("UNASSIGN".equals(performanceSearch.getAssignType())) {
                    params.add(null);
                } else {
                    params.add(null);
                }
                params.add(performanceSearch.getUserId());
                params.add(headerId);
                ascapJdbcTemplate.update(qry, params.toArray());
                log.debug("updated " + updatedCount + " headers to " + performanceSearch.getAssignedToUserAll());
            }
        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }
        log.debug(PERFORMANCESEARCH_IS + performanceSearch.toString());
        log.debug("Exiting UsageDAOImpl - updateAssignedUsersToHeaders method");
        return performanceSearch;
    }

    /*
     * (non-Javadoc)
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#getWorkPerformance( long) //Removed Del_flags
     * following Issue 2341
     */
    public WorkPerformance getWorkPerformance(long workPerformanceId) throws PrepSystemException {
        return getWorkPerformance(workPerformanceId, false);
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#getWorkPerformance(long)
     */
    public WorkPerformance getWorkPerformance(long workPerformanceId, boolean ignoreDeleteFlag)
        throws PrepSystemException {
        log.debug("Entering public getWorkPerformance method in UsageDAOImpl ");

        WorkPerformance workPerformance = null;
        String versionNumberString = null;
        try {
            workPerformance = this.retriveCompleteWorkPerformance(workPerformanceId, versionNumberString);

        } catch (Exception e) {
            log.debug(e);
        }
        log.debug("Exiting public getWorkPerformance method in UsageDAOImpl ");
        return workPerformance;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#updateProgramPerformance(ProgramPerformance)
     */
    public ProgramPerformance updateProgramPerformance(ProgramPerformance programPerformanceVob)
        throws PrepSystemException {
        try {
            if (programPerformanceVob == null) {
                return programPerformanceVob;
            }
            this.updateBasicProgramPerformance(programPerformanceVob);

            if ("Y".equals(programPerformanceVob.getSupplierCodeModified())) {
                this.updateWorkPerformanceList(programPerformanceVob);
            }

        } catch (PrepSystemException se) {
            log.error(se.getMessage());
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        return programPerformanceVob;
    }

    /**
     * Method updateBasicProgramPerformance.
     * 
     * @param con
     * @param programPerformanceVob
     * @return ProgramPerformance
     * @throws PrepSystemException
     */
    private ProgramPerformance updateBasicProgramPerformance(ProgramPerformance programPerformanceVob)
        throws PrepSystemException {

        log.debug(
            "Entering private ProgramPerformance updateBasicProgramPerformance(Connection con, ProgramPerformance programPerformanceVob) of UsageDAOImpl Performance Vob received is :"
                + programPerformanceVob);

        StringBuilder updateProgramPerformanceSqlStr;

        StringBuilder updateProgramPerformanceColumnsPart;
        StringBuilder updateProgramPerformanceWherePart;

        StringBuilder debugFinalUpdateProgramPerformanceSql = null;
        StringBuilder debugUpdateProgramPerformanceSqlColumnsPart = new StringBuilder();

        java.util.ArrayList<String> programPerformanceValuesList;

        updateProgramPerformanceSqlStr = new StringBuilder(UsageQueries.DYNAMIC_UPDATE_PERF_HDR_TBL);

        updateProgramPerformanceColumnsPart = new StringBuilder(" ");
        updateProgramPerformanceWherePart = new StringBuilder(" WHERE ");

        if (log.isDebugEnabled()) {
            debugUpdateProgramPerformanceSqlColumnsPart = new StringBuilder(UsageQueries.DYNAMIC_UPDATE_PERF_HDR_TBL);
            debugUpdateProgramPerformanceSqlColumnsPart.append(" ");
        }

        try {

            programPerformanceValuesList = new ArrayList<>();

            updateProgramPerformanceColumnsPart.append(" SET UPD_DT = SYSDATE ");

            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(" SET UPD_DT = SYSDATE ");
            }

            updateProgramPerformanceColumnsPart.append(", TGTSURVYEARQTR = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getTargetYearQuarter());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", TGTSURVYEARQTR =   '")
                    .append(programPerformanceVob.getTargetYearQuarter()).append("'");
            }

            updateProgramPerformanceColumnsPart.append(", SRC_SYS = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getSourceSystem());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", SRC_SYS =   '")
                    .append(programPerformanceVob.getSourceSystem()).append("'");
            }

            updateProgramPerformanceColumnsPart.append(", PTY_ID = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getMusicUserId());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", PTY_ID =   '")
                    .append(programPerformanceVob.getMusicUserId()).append("'");
            }
            updateProgramPerformanceColumnsPart.append(", PGM_STT_DT = ? ");
            programPerformanceValuesList.add(programPerformanceVob.getPerformanceStartDate());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", PGM_STT_DT = '")
                    .append(programPerformanceVob.getPerformanceStartDate()).append("' ");
            }
            updateProgramPerformanceColumnsPart.append(", PGM_STT_TM = ? ");
            programPerformanceValuesList.add(programPerformanceVob.getPerformanceStartTime());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", PGM_STT_TM =  '")
                    .append(programPerformanceVob.getPerformanceStartTime() + "' ");
            }
            updateProgramPerformanceColumnsPart.append(", PGM_END_DT = ? ");
            programPerformanceValuesList.add(programPerformanceVob.getPerformanceEndDate());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", PGM_END_DT = '")
                    .append(programPerformanceVob.getPerformanceEndDate()).append("' ");
            }
            updateProgramPerformanceColumnsPart.append(", PGM_END_TM = ? ");
            programPerformanceValuesList.add(programPerformanceVob.getPerformanceEndTime());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", PGM_END_TM = '")
                    .append(programPerformanceVob.getPerformanceEndTime() + "' ");
            }
            updateProgramPerformanceColumnsPart.append(", SUPP_CODE = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getSupplierCode());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", SUPP_CODE =   '")
                    .append(programPerformanceVob.getSupplierCode()).append("'");
            }
            updateProgramPerformanceColumnsPart.append(", PGM_OVRLP_CDE = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getProgramOverlapCode());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", PGM_OVRLP_CDE =   '")
                    .append(programPerformanceVob.getProgramOverlapCode()).append("'");
            }
            updateProgramPerformanceColumnsPart.append(", PGM_DUR = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getDuration());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", PGM_DUR =   '")
                    .append(programPerformanceVob.getDuration()).append("'");
            }
            updateProgramPerformanceColumnsPart.append(", SUR_TYP_ID = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getSurveyTypeCode());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", SUR_TYP_ID =   '")
                    .append(programPerformanceVob.getSurveyTypeCode()).append("'");
            }
            updateProgramPerformanceColumnsPart.append(", SAM_TYP_ID = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getSampleTypeCode());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", SAM_TYP_ID =   '")
                    .append(programPerformanceVob.getSampleTypeCode()).append("'");
            }
            updateProgramPerformanceColumnsPart.append(", SEG_NUM = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getSegmentNumber());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", SEG_NUM =   '")
                    .append(programPerformanceVob.getSegmentNumber()).append("'");
            }

            updateProgramPerformanceColumnsPart.append(", PGM_TTL = UPPER(?) ");

            programPerformanceValuesList.add(programPerformanceVob.getProgramTitle());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", PGM_TTL = UPPER('")
                    .append(programPerformanceVob.getProgramTitle()).append("')");
            }
            updateProgramPerformanceColumnsPart.append(", PGM_NUM = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getProgramNumber());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", PGM_NUM =   '")
                    .append(programPerformanceVob.getProgramNumber()).append("'");
            }
            updateProgramPerformanceColumnsPart.append(", SET_LIST_TYP = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getPlayListFlag());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", SET_LIST_TYP =   '")
                    .append(programPerformanceVob.getPlayListFlag()).append("'");
            }
            updateProgramPerformanceColumnsPart.append(", HDLNR_OPNR_IND = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getHeadlineActIndicator());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", HDLNR_OPNR_IND =   '")
                    .append(programPerformanceVob.getHeadlineActIndicator()).append("'");
            }
            updateProgramPerformanceColumnsPart.append(", TOUR_CNC_CNT = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getNumberOfConcerts());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", TOUR_CNC_CNT =   '")
                    .append(programPerformanceVob.getNumberOfConcerts()).append("'");
            }
            updateProgramPerformanceColumnsPart.append(", TOTAL_NUM_PLAYS = ? ");

            programPerformanceValuesList.add(programPerformanceVob.getTotalNumberPlays());
            if (log.isDebugEnabled()) {
                debugUpdateProgramPerformanceSqlColumnsPart.append(", TOTAL_NUM_PLAYS =   '")
                    .append(programPerformanceVob.getTotalNumberPlays()).append("'");
            }

            updateProgramPerformanceColumnsPart.append(", PFR_NA = UPPER(?) ");
            programPerformanceValuesList.add(programPerformanceVob.getPerformerName());

            updateProgramPerformanceColumnsPart.append(", ART_REV = ? ");
            programPerformanceValuesList.add(programPerformanceVob.getArtistRevenue());

            updateProgramPerformanceColumnsPart.append(", CLASSICAL_IND = ? ");
            programPerformanceValuesList.add(programPerformanceVob.getClassicalIndicator());

            updateProgramPerformanceColumnsPart.append(", ASG_USR_ID = ? ");
            programPerformanceValuesList.add(programPerformanceVob.getAssignedToUser());

            if (programPerformanceVob.getUserId() != null) {
                updateProgramPerformanceColumnsPart.append(", UPD_ID = ? ");

                programPerformanceValuesList.add(programPerformanceVob.getUserId());

                if (log.isDebugEnabled()) {
                    debugUpdateProgramPerformanceSqlColumnsPart.append(", UPD_ID =   '")
                        .append(programPerformanceVob.getUserId()).append("'");
                }
            }

            updateProgramPerformanceWherePart.append(" APM_PERF_HDR_ID = ? ");
            programPerformanceValuesList.add(programPerformanceVob.getPerformanceHeaderId());

            updateProgramPerformanceSqlStr.append(updateProgramPerformanceColumnsPart)
                .append(updateProgramPerformanceWherePart);

            if (log.isDebugEnabled()) {
                log.debug("Generated Dynamic SQL Query :" + updateProgramPerformanceSqlStr);
                debugFinalUpdateProgramPerformanceSql =
                    new StringBuilder(debugUpdateProgramPerformanceSqlColumnsPart.toString());
                debugFinalUpdateProgramPerformanceSql.append(updateProgramPerformanceWherePart)
                    .append(" APM_PERF_HDR_ID = '").append(programPerformanceVob.getPerformanceHeaderId()).append("'");

                log.debug("FINAL DEBUG SQL :" + debugFinalUpdateProgramPerformanceSql.toString());
            }

            int totalParameters;
            totalParameters = programPerformanceValuesList.size();
            log.debug("Inside UsageDAOImpl.updateBasicProgramPerformance : Number of Parameters Found ");
            String currentValue;
            ArrayList<Object> params = new ArrayList<>();
            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                currentValue = programPerformanceValuesList.get(parameterId);
                params.add(currentValue);
            }
            ascapJdbcTemplate.update(updateProgramPerformanceSqlStr.toString(), params.toArray());
            log.debug("UPDATE BASIC PROGRAM PERFORMANCE SUCCESSFULL :" + programPerformanceVob);
        } catch (Exception se) {
            log.debug(se.getMessage());
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }
        return programPerformanceVob;
    }

    private ProgramPerformance updateWorkPerformanceList(ProgramPerformance programPerformanceVob)
        throws PrepSystemException {

        log.debug("Entering updateWorkPerformanceList in UsageDAOImpl programPerformanceVob.userId"
            + programPerformanceVob.getUserId());

        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(programPerformanceVob.getSupplierCode());
            params.add(programPerformanceVob.getUserId());
            params.add(programPerformanceVob.getPerformanceHeaderId());

            ascapJdbcTemplate.update(UsageQueries.UPDATE_WRK_PERF_LIST_SUPP_CODE, params);

        } catch (Exception se) {
            log.debug(se.getMessage());
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }

        log.debug("Exiting updateWorkPerformanceList in UsageDAOImpl");
        return (programPerformanceVob);
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#getPerformanceMusicUserInfo(ProgramPerformance)
     */
    public ProgramPerformance getPerformanceMusicUserInfo(ProgramPerformance programPerformanceVob)
        throws PrepSystemException {
        try {
            if (programPerformanceVob == null) {
                return programPerformanceVob;
            }
            this.retrievePerformanceMusicUserInfo(programPerformanceVob);
        } catch (PrepSystemException se) {
            log.error(se.getMessage());
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }
        return programPerformanceVob;

    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOInterface#addProgramPerformance(ProgramPerformance)
     */
    public ProgramPerformance addProgramPerformance(ProgramPerformance programPerformanceVob)
        throws PrepSystemException {

        try {
            if (programPerformanceVob == null) {
                return programPerformanceVob;
            }
            this.createBasicProgramPerformance(programPerformanceVob);

        } catch (Exception se) {
            log.debug(se.getMessage());
            throw new PrepSystemException(SQL_ERROR, se);

        }
        return programPerformanceVob;
    }

    /**
     * Method createBasicProgramPerformance.
     * 
     * @param con
     * @param programPerformanceVob
     * @return ProgramPerformance
     * @throws PrepSystemException
     */
    private ProgramPerformance createBasicProgramPerformance(ProgramPerformance programPerformanceVob)
        throws PrepSystemException {

        log.debug(
            "Entering private ProgramPerformance createBasicProgramPerformance(ProgramPerformance programPerformanceVob) of UsageDAOImpl Performance Vob received is :"
                + programPerformanceVob);

        StringBuilder insertProgramPerformanceSqlStr;

        StringBuilder insertProgramPerformanceColumnsPart;
        StringBuilder insertProgramPerformanceValuesPart;

        StringBuilder debugFinalInsertProgramPerformanceSql = null;
        StringBuilder debugInsertProgramPerformanceSqlColumnsPart = null;
        StringBuilder debugInsertProgramPerformanceSqlValuesPart = null;

        ArrayList<String> programPerformanceValuesList;

        // SequenceNumbers
        String programPerformanceHeaderId;

        insertProgramPerformanceSqlStr = new StringBuilder(UsageQueries.DYNAMIC_INSERT_PERF_HDR_TBL);
        insertProgramPerformanceColumnsPart = new StringBuilder(" (");
        insertProgramPerformanceValuesPart = new StringBuilder(" VALUES(");

        if (log.isDebugEnabled()) {
            debugInsertProgramPerformanceSqlColumnsPart = new StringBuilder(UsageQueries.DYNAMIC_INSERT_PERF_HDR_TBL);
            debugInsertProgramPerformanceSqlColumnsPart.append(" (");
            debugInsertProgramPerformanceSqlValuesPart = new StringBuilder(" VALUES(");
        }

        try {
            programPerformanceHeaderId = this.getSequence(UsageQueries.SEQUENCE_PERF_HDR_ID);
            programPerformanceVob.setPerformanceHeaderId(programPerformanceHeaderId);

            programPerformanceValuesList = new ArrayList<>();

            insertProgramPerformanceColumnsPart.append(" APM_PERF_HDR_ID ");
            insertProgramPerformanceValuesPart.append("?");
            programPerformanceValuesList.add(programPerformanceHeaderId);

            if (log.isDebugEnabled()) {
                debugInsertProgramPerformanceSqlColumnsPart.append(" APM_PERF_HDR_ID ");
                debugInsertProgramPerformanceSqlValuesPart.append(" '")
                    .append(programPerformanceVob.getPerformanceHeaderId()).append("' ");
            }

            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getSourceSystem())) {
                insertProgramPerformanceColumnsPart.append(", SRC_SYS");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getSourceSystem());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", SRC_SYS");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getSourceSystem()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getLegacyMusicUserId())) {
                insertProgramPerformanceColumnsPart.append(", LGY_MUS_USER_ID");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getLegacyMusicUserId());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", LGY_MUS_USER_ID");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getLegacyMusicUserId()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getLegacyMusicUserIdTypeCode())) {
                insertProgramPerformanceColumnsPart.append(", LGY_MUS_USER_TYP");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getLegacyMusicUserIdTypeCode());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", LGY_MUS_USER_TYP");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getLegacyMusicUserIdTypeCode()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getMusicUserId())) {
                insertProgramPerformanceColumnsPart.append(", PTY_ID");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getMusicUserId());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", PTY_ID");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getMusicUserId()).append("'");
                }
            }

            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getPerformanceStartDate())) {
                insertProgramPerformanceColumnsPart.append(", PGM_STT_DT");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getPerformanceStartDate());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", PGM_STT_DT");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getPerformanceStartDate()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getPerformanceStartTime())) {
                insertProgramPerformanceColumnsPart.append(", PGM_STT_TM");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getPerformanceStartTime());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", PGM_STT_TM");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getPerformanceStartTime()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getPerformanceEndDate())) {
                insertProgramPerformanceColumnsPart.append(", PGM_END_DT");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getPerformanceEndDate());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", PGM_END_DT");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getPerformanceEndDate()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getPerformanceEndTime())) {
                insertProgramPerformanceColumnsPart.append(", PGM_END_TM");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getPerformanceEndTime());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", PGM_END_TM");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getPerformanceEndTime()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getSupplierCode())) {
                insertProgramPerformanceColumnsPart.append(", SUPP_CODE");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getSupplierCode());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", SUPP_CODE");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getSupplierCode()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getProgramOverlapCode())) {
                insertProgramPerformanceColumnsPart.append(", PGM_OVRLP_CDE");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getProgramOverlapCode());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", PGM_OVRLP_CDE");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getProgramOverlapCode()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getDuration())) {
                insertProgramPerformanceColumnsPart.append(", PGM_DUR");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getDuration());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", PGM_DUR");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '").append(programPerformanceVob.getDuration())
                        .append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getSurveyTypeCode())) {
                insertProgramPerformanceColumnsPart.append(", SUR_TYP_ID");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getSurveyTypeCode());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", SUR_TYP_ID");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getSurveyTypeCode()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getSampleTypeCode())) {
                insertProgramPerformanceColumnsPart.append(", SAM_TYP_ID");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getSampleTypeCode());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", SAM_TYP_ID");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getSampleTypeCode()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getSegmentNumber())) {
                insertProgramPerformanceColumnsPart.append(", SEG_NUM");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getSegmentNumber());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", SEG_NUM");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getSegmentNumber()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getProgramTitle())) {
                insertProgramPerformanceColumnsPart.append(", PGM_TTL");
                insertProgramPerformanceValuesPart.append(", UPPER(?)");
                programPerformanceValuesList.add(programPerformanceVob.getProgramTitle());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", PGM_TTL");
                    debugInsertProgramPerformanceSqlValuesPart.append(", UPPER('")
                        .append(programPerformanceVob.getProgramTitle()).append("')");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getProgramNumber())) {
                insertProgramPerformanceColumnsPart.append(", PGM_NUM");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getProgramNumber());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", PGM_NUM");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getProgramNumber()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getPlayListFlag())) {
                insertProgramPerformanceColumnsPart.append(", SET_LIST_TYP");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getPlayListFlag());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", SET_LIST_TYP");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getPlayListFlag()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getHeadlineActIndicator())) {
                insertProgramPerformanceColumnsPart.append(", HDLNR_OPNR_IND");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getHeadlineActIndicator());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", HDLNR_OPNR_IND");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getHeadlineActIndicator()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getThirdPartyDistributedIndicator())) {
                insertProgramPerformanceColumnsPart.append(", THRD_PTY_DIS_ID");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getThirdPartyDistributedIndicator());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", THRD_PTY_DIS_ID");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getThirdPartyDistributedIndicator()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getNumberOfConcerts())) {
                insertProgramPerformanceColumnsPart.append(", TOUR_CNC_CNT");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getNumberOfConcerts());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", TOUR_CNC_CNT");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getNumberOfConcerts()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getMaximumNumberPlays())) {
                insertProgramPerformanceColumnsPart.append(", TOTAL_NUM_PLAYS");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getMaximumNumberPlays());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", TOTAL_NUM_PLAYS");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getMaximumNumberPlays()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getPerformerName())) {
                insertProgramPerformanceColumnsPart.append(", PFR_NA");
                insertProgramPerformanceValuesPart.append(", UPPER(?)");
                programPerformanceValuesList.add(programPerformanceVob.getPerformerName());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", PFR_NA");
                    debugInsertProgramPerformanceSqlValuesPart.append(", UPPER('")
                        .append(programPerformanceVob.getPerformerName()).append("')");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getArtistRevenue())) {
                insertProgramPerformanceColumnsPart.append(", ART_REV");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getArtistRevenue());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", ART_REV");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getArtistRevenue()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getClassicalIndicator())) {
                insertProgramPerformanceColumnsPart.append(", CLASSICAL_IND");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getClassicalIndicator());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", CLASSICAL_IND");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getClassicalIndicator()).append("'");
                }
            }
            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getTargetYearQuarter())) {
                insertProgramPerformanceColumnsPart.append(", TGTSURVYEARQTR");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getTargetYearQuarter());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", TGTSURVYEARQTR");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getTargetYearQuarter()).append("'");
                }
            }

            if (!UsageCommonValidations.isEmptyOrNull(programPerformanceVob.getAssignedToUser())) {
                insertProgramPerformanceColumnsPart.append(", ASG_USR_ID");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getAssignedToUser());
                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", ASG_USR_ID");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '")
                        .append(programPerformanceVob.getAssignedToUser()).append("'");
                }
            }

            // Fill in Auditing Fields and end the SQL Parts
            if (programPerformanceVob.getUserId() != null) {
                insertProgramPerformanceColumnsPart.append(", CRE_ID");
                insertProgramPerformanceValuesPart.append(", ?");
                programPerformanceValuesList.add(programPerformanceVob.getUserId());

                if (log.isDebugEnabled()) {
                    debugInsertProgramPerformanceSqlColumnsPart.append(", CRE_ID");
                    debugInsertProgramPerformanceSqlValuesPart.append(", '").append(programPerformanceVob.getUserId())
                        .append("'");
                }
            }

            insertProgramPerformanceColumnsPart.append(", PROC_STATUS");
            insertProgramPerformanceValuesPart.append(", '" + UsageConstants.PENDING_BATCH_VALIDATIONS + "'");

            insertProgramPerformanceColumnsPart.append(", ERR_STATUS");
            insertProgramPerformanceValuesPart.append(", 2");

            insertProgramPerformanceColumnsPart.append(", APM_ARCHIVE_ID");
            insertProgramPerformanceValuesPart.append(", 0");

            insertProgramPerformanceColumnsPart.append(", CRE_DT");
            insertProgramPerformanceValuesPart.append(", SYSDATE");
            // END fill in Auditing Fields and end the SQL Parts

            insertProgramPerformanceColumnsPart.append(") ");
            insertProgramPerformanceValuesPart.append(") ");

            if (log.isDebugEnabled()) {
                debugInsertProgramPerformanceSqlColumnsPart.append(", CRE_ID ) ");
                debugInsertProgramPerformanceSqlValuesPart.append(", SYSDATE ) ");
            }

            // Makeing SQL String
            insertProgramPerformanceSqlStr.append(insertProgramPerformanceColumnsPart)
                .append(insertProgramPerformanceValuesPart);

            if (log.isDebugEnabled()) {
                log.debug("Generated Dynamic SQL Query :" + insertProgramPerformanceSqlStr);
                debugFinalInsertProgramPerformanceSql =
                    new StringBuilder(debugInsertProgramPerformanceSqlColumnsPart.toString());
                debugFinalInsertProgramPerformanceSql.append(debugInsertProgramPerformanceSqlValuesPart);
                log.debug("FINAL DEBUG SQL :" + debugFinalInsertProgramPerformanceSql.toString());
            }

            int totalParameters;
            totalParameters = programPerformanceValuesList.size();
            ArrayList<Object> params = new ArrayList<>();
            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                params.add((String) programPerformanceValuesList.get(parameterId));
            }

            ascapJdbcTemplate.update(insertProgramPerformanceSqlStr.toString(), params.toArray());

            log.debug("CREATE BASIC PROGRAM PERFORMANCE SUCCESSFULL :" + programPerformanceVob);

        } catch (Exception se) {
            log.debug(se.getMessage());
            throw new PrepSystemException(SQL_ERROR, se);
        }
        return programPerformanceVob;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#getChannelList(apmChannelList)
     */
    public ApmChannelList getChannelList(ApmChannelList apmChannelList) throws PrepSystemException {

        log.debug("Entering public getChannelList method in UsageDAOImpl");
        StringBuilder sb = new StringBuilder(UsageQueries.GET_CHANNEL_LIST);
        StringBuilder channelSuppliersQry = new StringBuilder(UsageQueries.GET_CHANNEL_SUPPLIERS);
        StringBuilder channelTypeCountQry = new StringBuilder(UsageQueries.GET_CHANNEL_TYP_COUNTS);
        ApmChannel apmChannel = new ApmChannel();
        List<Object> col = new ArrayList<>();
        List<Object> channelSuppliers = new ArrayList<>();

        try {

            if (!ValidationUtils.isEmptyOrNull(apmChannelList.getFilterSupplierCode())) {
                sb.append(" AND SuppCode = ? ");
            }

            if (!ValidationUtils.isEmptyOrNull(apmChannelList.getFilterChannelType())) {
                if ("CL".equals(apmChannelList.getFilterChannelType())) {
                    sb.append(" AND ClassicalInd = 'Y' ");
                } else if ("NM".equals(apmChannelList.getFilterChannelType())) {
                    sb.append(" AND NonMusicInd = 'Y' ");
                } else if ("NC".equals(apmChannelList.getFilterChannelType())) {
                    sb.append(" AND NonClassicalInd = 'Y' ");
                } else if ("UA".equals(apmChannelList.getFilterChannelType())) {
                    sb.append(" AND ClassicalInd = 'N' AND NonMusicInd = 'N' AND NonClassicalInd = 'N' ");
                }
            }
            sb.append(" ORDER BY Channel ");

            log.debug("Executing Query SUPP: " + channelSuppliersQry.toString());

            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(channelSuppliersQry.toString());

            while (rs.next()) {
                channelSuppliers.add(rs.getString(1));
            }
            apmChannelList.setChannelSuppliers(channelSuppliers);

            if (!ValidationUtils.isEmptyOrNull(apmChannelList.getFilterSupplierCode())) {
                channelTypeCountQry.append(" AND SuppCode = ? ");
            } else {
                log.debug("Supplier Filter is empty..  Returning empty list");
                return apmChannelList;
            }
            log.debug("Executing Query COUNT: " + channelTypeCountQry.toString());
            ArrayList<Object> params = new ArrayList<>();
            if (!ValidationUtils.isEmptyOrNull(apmChannelList.getFilterSupplierCode())) {
                params.add(apmChannelList.getFilterSupplierCode());
            }
            rs = eoDSJdbcTemplate.queryForRowSet(channelTypeCountQry.toString(), params.toArray());

            while (rs.next()) {
                apmChannelList.setCountClassical(rs.getString("CLASSICAL_COUNT"));
                apmChannelList.setCountNonClassical(rs.getString("NONCLASSICAL_COUNT"));
                apmChannelList.setCountNonMusical(rs.getString("NONMUSIC_COUNT"));
                apmChannelList.setCountUnassigned(rs.getString("UNASSIGNED_COUNT"));
            }

            log.debug("Executing Query : " + sb.toString());

            ArrayList<Object> sbParams = new ArrayList<>();
            if (!ValidationUtils.isEmptyOrNull(apmChannelList.getFilterSupplierCode())) {
                sbParams.add(apmChannelList.getFilterSupplierCode());
            }

            rs = eoDSJdbcTemplate.queryForRowSet(sb.toString(), sbParams.toArray());

            while (rs.next()) {
                apmChannel = new ApmChannel();
                apmChannel.setChannelId(rs.getString("ID"));
                apmChannel.setSuppCode(rs.getString("SuppCode"));
                apmChannel.setChannelName(rs.getString("Channel"));
                apmChannel.setChannelDescription(rs.getString("Description"));
                if ("Y".equalsIgnoreCase(rs.getString("ClassicalInd"))) {
                    apmChannel.setChannelType("CL");
                } else if ("Y".equalsIgnoreCase(rs.getString("NonMusicInd"))) {
                    apmChannel.setChannelType("NM");
                } else if ("Y".equalsIgnoreCase(rs.getString("NonClassicalInd"))) {
                    apmChannel.setChannelType("NC");
                } else {
                    apmChannel.setChannelType("UA");
                }

                col.add(apmChannel);
            }
            apmChannelList.setSearchResults(col);

        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting UsageDAOImpl - getChannelList method ");

        return apmChannelList;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#updateChannelList(apmChannelList)
     */
    public ApmChannelList updateChannelList(ApmChannelList apmChannelList) throws PrepSystemException {

        log.debug("Entering public updateChannelList method in UsageDAOImpl");

        if (apmChannelList == null) {
            log.debug("Input Channel List is null.. Returning....");
            return apmChannelList;
        }

        if (apmChannelList.getSearchResults() == null) {
            log.debug("Input Channel List is null.. Returning....");
            return apmChannelList;
        }

        log.debug("Update Channel size " + apmChannelList.getSearchResults().size());
        log.debug("Update Channel Supplier " + apmChannelList.getFilterSupplierCode());
        log.debug("Update Channel Operation Type " + apmChannelList.getOperationType());

        ApmChannel apmChannel = null;
        Iterator<Object> itr = apmChannelList.getSearchResults().iterator();

        try {

            if ("ASSIGN".equals(apmChannelList.getOperationType())) {
                StringBuilder sqlStr = new StringBuilder(UsageQueries.UPDATE_CHANNELS_ASSIGN_NON_CLASSICAL);
                eoDSJdbcTemplate.update(sqlStr.toString(),
                    new Object[] {"Y", apmChannelList.getUserId(), apmChannelList.getFilterSupplierCode()});
            } else {
                StringBuilder sqlStr = new StringBuilder(UsageQueries.UPDATE_CHANNELS);
                List<Object[]> parametersList = new ArrayList<Object[]>();
                while (itr.hasNext()) {
                    apmChannel = (ApmChannel) itr.next();
                    Object[] params = new Object[5];
                    if ("CL".equals(apmChannel.getChannelType())) {
                        params[0] = "Y";
                        params[1] = "N";
                        params[2] = "N";
                    } else if ("NC".equals(apmChannel.getChannelType())) {
                        params[0] = "N";
                        params[1] = "Y";
                        params[2] = "N";
                    } else if ("NM".equals(apmChannel.getChannelType())) {
                        params[0] = "N";
                        params[1] = "N";
                        params[2] = "Y";
                    } else {
                        params[0] = "N";
                        params[1] = "N";
                        params[2] = "N";
                    }

                    params[3] = apmChannel.getUserId();
                    params[4] = apmChannel.getChannelId();
                    parametersList.add(params);
                }
                eoDSJdbcTemplate.batchUpdate(sqlStr.toString(), parametersList);
            }
        } catch (Exception se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        log.debug("Exiting UsageDAOImpl - updateChannelList method ");

        return apmChannelList;
    }

    private String getActiveSurveyYearQuarter() {
        String activeSurveyYearQuarter = null;

        try {

            SqlRowSet sequenceRs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_ACTIVE_SURVEY_YEAR_QTR);

            while (sequenceRs.next()) {
                activeSurveyYearQuarter = sequenceRs.getString("TGTSURVYEARQTR");
            }
        } finally {
        }
        log.debug("Active Survey Year Quarter : " + activeSurveyYearQuarter);
        return (activeSurveyYearQuarter);
    }

    public CatalogSamplingSummary getCatalogSamplingSummary(CatalogSamplingSummary samplingSummary)
        throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - getCatalogSamplingSummary method ");
        StringBuilder sb = new StringBuilder(UsageQueries.GET_CATALOG_SAMPLING_SUMMARY_LIST);
        CatalogSamplingSummary catalogSamplingSummary = new CatalogSamplingSummary();
        CatalogSamplingRequest samplingRequest;
        List<Object> colSamplingRequest = new ArrayList<>();
        try {
            ascapJdbcTemplate.execute(UsageQueries.POPULATE_SAMP_REQ_CAT);

            String activeSurveyYearQuarter = getActiveSurveyYearQuarter();

            String hasActiveSamplingProcessOrInError = "N";

            log.debug("Executing Query : " + sb.toString());

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(sb.toString());

            while (rs.next()) {
                samplingRequest = new CatalogSamplingRequest();
                samplingRequest.setSamplingRequestId(rs.getString("RequestId"));
                samplingRequest.setCallLetter(rs.getString("Supplier"));
                samplingRequest.setProcessStartTime(rs.getString("Period"));
                samplingRequest.setLoadStatus(rs.getString("LoadStatus"));
                samplingRequest.setStatusCode(rs.getString("SamplingStatus"));
                samplingRequest.setSampleCount("1000");
                samplingRequest.setSamplesRequested("1000");
                colSamplingRequest.add(samplingRequest);
                if ("PR".equals(samplingRequest.getStatusCode()) || "IQ".equals(samplingRequest.getStatusCode())
                    || "BP".equals(samplingRequest.getStatusCode()) || "AB".equals(samplingRequest.getStatusCode())) {
                    hasActiveSamplingProcessOrInError = "Y";
                }
            }
            catalogSamplingSummary.setSearchResults(colSamplingRequest);
            catalogSamplingSummary.setTargetSurveyYearQuarter(activeSurveyYearQuarter);
            catalogSamplingSummary.sethasActiveSamplingProcessOrInError(hasActiveSamplingProcessOrInError);
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se.getMessage());
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting UsageDAOImpl - getCatalogSamplingSummary method ");
        return catalogSamplingSummary;
    }

    public WorkPerformancesList getWorkPerformancesList(WorkPerformancesList workPerformancesList)
        throws PrepSystemException {
        log.debug("Entering UsageDAOImpl.getWorkperformancesList() with :" + workPerformancesList);
        long programPerformanceId = 0;
        ProgramPerformance programPerformance = null;
        String versionNumberString = null;
        try {
            if (workPerformancesList == null) {
                return workPerformancesList;
            }

            if (!UsageCommonValidations.isEmptyOrNull(workPerformancesList.getProgramPerformanceId())) {
                try {
                    programPerformanceId = Long.parseLong(workPerformancesList.getProgramPerformanceId());
                    versionNumberString = workPerformancesList.getVersionNumber();
                    programPerformance =
                        this.retrieveBasicProgramPerformance(programPerformanceId, versionNumberString);
                } catch (NumberFormatException ne) {
                    return workPerformancesList;
                }
            }

            workPerformancesList.setProgramPerformance(programPerformance);
            workPerformancesList.setSearchResults(this.retrieveWorkPerformances(programPerformanceId));

        } catch (PrepSystemException se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        log.debug("Exiting UsageDAOImpl.getWorkperformancesList() with :" + workPerformancesList);

        return workPerformancesList;
    }

    private List<Object> retrieveWorkPerformances(long programPerformanceId) throws PrepSystemException {
        log.debug(
            "Entering retrieveWorkPerformances(Connection con, long programPerformanceId) method in UsageDAOImpl ProgramPerformanceId :"
                + programPerformanceId);
        PerformanceSearch performanceSearch = new PerformanceSearch();
        performanceSearch.setPaginationOverrideRequired("Y");
        performanceSearch.setProgramPerformanceId("" + programPerformanceId);
        performanceSearch = this.searchWorkPerformances(performanceSearch);
        return performanceSearch.getSearchResults();

    }

    private ProgramPerformance retrieveBasicProgramPerformance(long programPerformanceId, String versionNumber)
        throws PrepSystemException {
        log.debug("Entering retrieveBasicProgramPerformance method in UsageDAOImpl ");
        ProgramPerformance programPerformanceVob = null;
        List<Object> params = new ArrayList<>();
        try {
            params.add(programPerformanceId);
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_PROGRAM_PERFORMANCE, params.toArray());

            log.debug("Inside retrieveBasicProgramPerformance about to execute query with params :programPerformanceId="
                + programPerformanceId + " " + UsageQueries.GET_PROGRAM_PERFORMANCE);
            if ((!rs.isAfterLast() && !rs.isBeforeFirst()) && rs.next()) {
                programPerformanceVob = new ProgramPerformance();
                programPerformanceVob.setPerformanceHeaderId(rs.getString(APM_PERF_HDR_ID));
                programPerformanceVob.setSourceSystem(rs.getString(SRC_SYS));
                programPerformanceVob.setLegacyMusicUserId(rs.getString(LGY_MUS_USER_ID));
                programPerformanceVob.setLegacyMusicUserIdTypeCode(rs.getString(LGY_MUS_USER_TYP));
                programPerformanceVob.setMusicUserId(rs.getString(PTY_ID));
                programPerformanceVob.setMusicUserTypeCode(rs.getString(MUS_USER_TYP_CDE));
                programPerformanceVob.setLicenseTypeCode(rs.getString(LIC_TYP_CDE));
                programPerformanceVob.setPerformanceStartDate(rs.getString(PGM_STT_DT));
                programPerformanceVob.setPerformanceStartTime(rs.getString(PGM_STT_TM));
                programPerformanceVob.setPerformanceEndDate(rs.getString(PGM_END_DT));
                programPerformanceVob.setPerformanceEndTime(rs.getString(PGM_END_TM));
                programPerformanceVob.setProgramOverlapCode(rs.getString(PGM_OVRLP_CDE));
                programPerformanceVob.setDuration(rs.getString(PGM_DUR));
                programPerformanceVob.setSurveyTypeCode(rs.getString(SUR_TYP_ID));
                programPerformanceVob.setSampleTypeCode(rs.getString(SAM_TYP_ID));
                programPerformanceVob.setSegmentNumber(rs.getString(SEG_NUM));
                programPerformanceVob.setProgramTitle(rs.getString(PGM_TTL));
                programPerformanceVob.setProgramNumber(rs.getString(PGM_NUM));
                programPerformanceVob.setPlayListFlag(rs.getString(SET_LIST_TYP));
                programPerformanceVob.setHeadlineActIndicator(rs.getString(HDLNR_OPNR_IND));
                programPerformanceVob.setNumberOfConcerts(rs.getString(TOUR_CNC_CNT));
                programPerformanceVob.setArtistRevenue(rs.getString(ART_REV));
                programPerformanceVob.setTotalNumberPlays(rs.getString(TOTAL_NUM_PLAYS));
                programPerformanceVob.setProcessStatus(rs.getString(PROC_STATUS));
                programPerformanceVob.setErrorFlag(rs.getString(ERR_STATUS));
                programPerformanceVob.setSupplierCode(rs.getString(SUPP_CODE));
                programPerformanceVob.setMusicUserLastName(rs.getString("NA"));
                programPerformanceVob.setMusicUserFullName(rs.getString(PTYNAFORMUS_FULL_NAME));
                programPerformanceVob.setMusicUserTypeDescription(rs.getString(MUSIC_USER_TYPE_DES));
                programPerformanceVob.setLicenseTypeDescription(rs.getString(LICENSE_TYPE_DES));
                programPerformanceVob.setSurveyTypeDescription(rs.getString("SURVEY_TYPE_DES"));
                programPerformanceVob.setSampleTypeDescription(rs.getString("SAMPLE_TYPE_DES"));
                programPerformanceVob.setTargetYearQuarter(rs.getString(TGTSURVYEARQTR));
                programPerformanceVob.setTotalNumberOfWorkPerformances(rs.getString(WRK_PERF_CNT));
                programPerformanceVob.setFileId(rs.getString(APM_ARCHIVE_ID));
                programPerformanceVob.setPerformerName(rs.getString(PFR_NA));
                programPerformanceVob.setOnlineEditable(rs.getString(EDITABLE));
                programPerformanceVob.setClassicalIndicator(rs.getString("CLASSICAL_IND"));
                programPerformanceVob.setAssignedToUser(rs.getString(ASG_USR_ID));
                programPerformanceVob.setChannelInd(rs.getString("CHANNEL_IND"));
                programPerformanceVob = this.retrievePerformanceMusicUserInfo(programPerformanceVob);
            }
        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting retrieveBasicProgramPerformance method in UsageDAOImpl ");
        return programPerformanceVob;
    }

    public WorkPerformance updateWorkPerformance(WorkPerformance workPerformanceVob) throws PrepSystemException {
        try {
            if (workPerformanceVob == null) {
                return workPerformanceVob;
            }
            this.updateBasicWorkPerformance(workPerformanceVob);
        } catch (PrepSystemException se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        return workPerformanceVob;
    }

    private WorkPerformance updateBasicWorkPerformance(WorkPerformance workPerformanceVob) throws PrepSystemException {

        log.debug(
            "Entering private WorkPerformance updateBasicWorkPerformance(Connection con, WorkPerformance workPerformanceVob) of UsageDAOImpl Performance Vob received is :"
                + workPerformanceVob);
        StringBuilder updateWorkPerformanceSqlStr;

        StringBuilder updateWorkPerformanceColumnsPart;
        StringBuilder updateWorkPerformanceWherePart;

        StringBuilder debugFinalUpdateWorkPerformanceSql = null;
        StringBuilder debugUpdateWorkPerformanceSqlColumnsPart = null;

        List<String> workPerformanceValuesList;

        updateWorkPerformanceSqlStr = new StringBuilder(UsageQueries.DYNAMIC_UPDATE_WRK_PERF_TBL);
        updateWorkPerformanceColumnsPart = new StringBuilder(" ");
        updateWorkPerformanceWherePart = new StringBuilder(" WHERE ");

        debugUpdateWorkPerformanceSqlColumnsPart = new StringBuilder(UsageQueries.DYNAMIC_UPDATE_WRK_PERF_TBL);
        debugUpdateWorkPerformanceSqlColumnsPart.append(" ");

        try {
            workPerformanceValuesList = new ArrayList<>();

            updateWorkPerformanceColumnsPart.append(" SET UPD_DT = SYSDATE ");

            debugUpdateWorkPerformanceSqlColumnsPart.append(" SET UPD_DT = SYSDATE ");

            updateWorkPerformanceColumnsPart.append(", WRK_SEQ_NR = ? ");

            workPerformanceValuesList.add(workPerformanceVob.getWorkSequenceNumber());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", WRK_SEQ_NR= '")
                .append(workPerformanceVob.getWorkSequenceNumber()).append("'");
            updateWorkPerformanceColumnsPart.append(", MEDL_SEQ = ? ");

            workPerformanceValuesList.add(workPerformanceVob.getMedleySequenceNumber());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", MEDL_SEQ='")
                .append(workPerformanceVob.getMedleySequenceNumber()).append("'");

            updateWorkPerformanceColumnsPart.append(", WRK_TTL =? ");

            workPerformanceValuesList.add(workPerformanceVob.getWorkTitle());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", WRK_TTL= '").append(workPerformanceVob.getWorkTitle())
                .append("')");

            updateWorkPerformanceColumnsPart.append(", WRK_ID = ? ");

            workPerformanceValuesList.add(workPerformanceVob.getWorkId());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", WRK_ID='").append(workPerformanceVob.getWorkId())
                .append("'");

            updateWorkPerformanceColumnsPart.append(", USE_TYP = ? ");

            workPerformanceValuesList.add(workPerformanceVob.getUseTypeCode());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", USE_TYP='").append(workPerformanceVob.getUseTypeCode())
                .append("'");
            updateWorkPerformanceColumnsPart.append(", WRK_PERF_DUR = ? ");

            workPerformanceValuesList.add(workPerformanceVob.getWorkPerformanceDuration());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", WRK_PERF_DUR='")
                .append(workPerformanceVob.getWorkPerformanceDuration()).append("'");
            updateWorkPerformanceColumnsPart.append(", PFR_NA = UPPER(?) ");

            workPerformanceValuesList.add(workPerformanceVob.getPerformerName());
            debugUpdateWorkPerformanceSqlColumnsPart.append(",PFR_NA= UPPER('")
                .append(workPerformanceVob.getPerformerName()).append("')");
            updateWorkPerformanceColumnsPart.append(", PLAY_CNT = ? ");

            workPerformanceValuesList.add(workPerformanceVob.getPlayCount());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", PLAY_CNT='").append(workPerformanceVob.getPlayCount())
                .append("'");

            updateWorkPerformanceColumnsPart.append(", APM_MATCH_TYP = ? ");

            workPerformanceValuesList.add(workPerformanceVob.getMatchTypeCode());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", APM_MATCH_TYP='")
                .append(workPerformanceVob.getMatchTypeCode()).append("'");

            updateWorkPerformanceColumnsPart.append(", DETECTION_TM = ? ");

            workPerformanceValuesList.add(workPerformanceVob.getDetectionTime());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", DETECTION_TM='")
                .append(workPerformanceVob.getDetectionTime()).append("'");

            updateWorkPerformanceColumnsPart.append(", LIBRARY_DISC = ? ");
            workPerformanceValuesList.add(workPerformanceVob.getLibraryDiscTitle());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", LIBRARY_DISC='")
                .append(workPerformanceVob.getLibraryDiscTitle()).append("'");

            updateWorkPerformanceColumnsPart.append(", LIBRARY_DISC_ID = ? ");
            workPerformanceValuesList.add(workPerformanceVob.getLibraryDiscId());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", LIBRARY_DISC_ID='")
                .append(workPerformanceVob.getLibraryDiscId()).append("'");

            updateWorkPerformanceColumnsPart.append(", WRITER = ? ");
            workPerformanceValuesList.add(workPerformanceVob.getWriter());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", WRITER='").append(workPerformanceVob.getWriter())
                .append("'");

            updateWorkPerformanceColumnsPart.append(", CONFIDENCE_LVL = ? ");
            workPerformanceValuesList.add(workPerformanceVob.getConfidenceLevel());
            debugUpdateWorkPerformanceSqlColumnsPart.append(", CONFIDENCE_LVL='")
                .append(workPerformanceVob.getConfidenceLevel()).append("'");

            if (workPerformanceVob.getUserId() != null) {
                updateWorkPerformanceColumnsPart.append(", UPD_ID = ? ");

                workPerformanceValuesList.add(workPerformanceVob.getUserId());

                debugUpdateWorkPerformanceSqlColumnsPart.append(", UPD_ID = ");
                debugUpdateWorkPerformanceSqlColumnsPart.append(" '").append(workPerformanceVob.getUserId())
                    .append("'");
            }

            updateWorkPerformanceColumnsPart.append(" ");
            updateWorkPerformanceWherePart.append(" APM_WRK_PERF_ID = ? ");
            workPerformanceValuesList.add(workPerformanceVob.getWorkPerformanceId());
            updateWorkPerformanceSqlStr.append(updateWorkPerformanceColumnsPart).append(updateWorkPerformanceWherePart);

            log.debug("Generated Dynamic SQL Query :" + updateWorkPerformanceSqlStr);
            debugFinalUpdateWorkPerformanceSql = new StringBuilder(debugUpdateWorkPerformanceSqlColumnsPart.toString());
            debugFinalUpdateWorkPerformanceSql.append("WHERE APM_WRK_PERF_ID = '")
                .append(workPerformanceVob.getWorkPerformanceId()).append("'");

            log.debug("FINAL DEBUG SQL :" + debugFinalUpdateWorkPerformanceSql.toString());

            int totalParameters;
            totalParameters = workPerformanceValuesList.size();

            ArrayList<Object> params = new ArrayList<>();
            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                params.add((String) workPerformanceValuesList.get(parameterId));
            }

            ascapJdbcTemplate.update(updateWorkPerformanceSqlStr.toString(), params.toArray());
            log.debug("UPDATE BASIC WORK PERFORMANCE SUCCESSFULL :" + workPerformanceVob);
        } catch (Exception se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }
        return workPerformanceVob;
    }

    public String getSingleOutputInputInformation(String informationType, String parameter) throws PrepSystemException {

        log.debug("Entering getSingleOutputInputInformation(" + informationType + "," + parameter + ")");

        // GET_WORK_PERFORMANCE_HIGHEST_WORKSEQUENCE_NUMBER

        String queryString = null;
        String outSequenceNo = "";
        boolean isTwo = false;

        if (UsageConstants.SDBINF_GET_WORK_PERFORMANCE_CURRENT_VERSION_NUMBER.equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_WORK_PERFORMANCE_CURRENT_VERSION_NUMBER;
        }

        if (UsageConstants.SDBINF_GET_WORK_PERFORMANCE_HIGHEST_VERSION_NUMBER.equalsIgnoreCase(informationType)) {
            // queryString = UsageQueries.GET_WORK_PERFORMANCE_HIGHEST_VERSION_NUMBER;
        }

        if (UsageConstants.SDBINF_GET_WORK_PERFORMANCE_HIGHEST_WORKSEQUENCE_NUMBER.equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_WORK_PERFORMANCE_HIGHEST_WORKSEQUENCE_NUMBER;
        }

        if (UsageConstants.SDB_MEDLEYINFO_GET_WORK_PERFORMANCE_HIGHEST_MEDLEY_SEQUENCE_NUMBER
            .equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_WORK_PERFORMANCE_HIGHEST_MEDLEY_SEQUENCE_NUMBER;
        }

        if (UsageConstants.SDBINF_GET_IF_WORK_EXIST.equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_IF_WORK_EXIST;
        }

        if (UsageConstants.SDBINF_GET_DURATION_SUM_OF_WORK_PERFORMANCES.equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_DURATION_SUM_OF_WORK_PERFORMANCES;
        }

        if (UsageConstants.SDBINF_GET_IF_VALID_SOC_CODE.equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_IF_VALID_SOC_CODE;
        }

        if (UsageConstants.SDBINF_GET_IF_VALID_INTERNATIONAL_REVENUE_CLASS.equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_IF_VALID_INTERNATIONAL_REVENUE_CLASS;
        }

        if (UsageConstants.SDBINF_GET_IF_VALID_MUSIC_USER_PARTY.equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_IF_VALID_MUSIC_USER_PARTY;
        }

        if (UsageConstants.SDBINF_GET_IF_PART_OF_BASE_CREDITS_CALCULATION_METHOD_FM1_MUZAK
            .equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_IF_PART_OF_BASE_CREDITS_CALCULATION_METHOD_FM1_MUZAK;
        }

        if (UsageConstants.SDBINF_GET_IF_PART_OF_BASE_CREDITS_CALCULATION_METHOD_MUSIC_CHOICE
            .equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_IF_PART_OF_BASE_CREDITS_CALCULATION_METHOD_MUSIC_CHOICE;
        }

        if (UsageConstants.SDBINF_GET_NOF_WORK_PERFORMANCES_IN_PERFORMACE_HEADER.equalsIgnoreCase(informationType)) {
            queryString = UsageQueries.GET_NOF_WORK_PERFORMANCES_IN_PERFORMACE_HEADER;
        }

        log.debug("Inside getSingleOutputInputInformation Query String determined for InformationType ="
            + informationType + "\n" + queryString);

        if (queryString == null) {
            return null;
        }

        try {

            log.debug("1111111111111111111111111111111111111 queryString :" + queryString);
            log.debug("1111111111111111111111111111111111111 parameter :" + parameter);

            ArrayList<Object> params = new ArrayList<>();
            params.add(parameter);
            if (isTwo) {
                params.add(parameter);
            }
            SqlRowSet sequenceRs = ascapJdbcTemplate.queryForRowSet(queryString, params.toArray());
            if (sequenceRs != null) {
                if (!sequenceRs.isAfterLast() && !sequenceRs.isBeforeFirst()) {
                    // No Results found
                } else {
                    while (sequenceRs.next()) {
                        log.debug("DAO RRRRRRRRRRRRRRRRRRRRRRReturning: " + sequenceRs.getString(1));
                        outSequenceNo = sequenceRs.getString(1);
                    }
                }
            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting getSingleOutputInputInformation(" + informationType + "," + parameter + ") output = "
            + outSequenceNo);

        return (outSequenceNo);
    }

    public String getHeaderTargetSurveyYearQuarter(String performanceHeaderId) throws PrepSystemException {
        log.debug("Entering  UsageDAOImpl - getHeaderTargetSurveyYearQuarter");
        if (ValidationUtils.isEmptyOrNull(performanceHeaderId)) {
            log.debug("Input performanceHeaderId is null or empty... returning null");
            return null;
        }
        String targetSurveyYearQuarter = null;
        try {
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_HDR_TGTSURVYEARQTR, performanceHeaderId);
            while (rs.next()) {
                targetSurveyYearQuarter = rs.getString(TGTSURVYEARQTR);
            }
        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting  UsageDAOImpl - getHeaderTargetSurveyYearQuarter");

        return targetSurveyYearQuarter;

    }

    public WorkPerformance addWorkPerformance(WorkPerformance workPerformanceVob) throws PrepSystemException {
        try {
            if (workPerformanceVob == null) {
                return workPerformanceVob;
            }
            this.createBasicWorkPerformance(workPerformanceVob);
        } catch (PrepSystemException se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        return workPerformanceVob;
    }

    private WorkPerformance createBasicWorkPerformance(WorkPerformance workPerformanceVob) throws PrepSystemException {

        log.debug(
            "Entering private WorkPerformance createBasicWorkPerformance(Connection con, WorkPerformance workPerformanceVob) of UsageDAOImpl Performance Vob received is :"
                + workPerformanceVob);

        StringBuilder insertWorkPerformanceSqlStr;

        StringBuilder insertWorkPerformanceColumnsPart;
        StringBuilder insertWorkPerformanceValuesPart;

        StringBuilder debugFinalInsertWorkPerformanceSql = null;
        StringBuilder debugInsertWorkPerformanceSqlColumnsPart = null;
        StringBuilder debugInsertWorkPerformanceSqlValuesPart = null;

        ArrayList<String> workPerformanceValuesList;

        // SequenceNumbers
        String workPerformanceId;

        insertWorkPerformanceSqlStr = new StringBuilder(UsageQueries.DYNAMIC_INSERT_WRK_PERF_TBL);
        insertWorkPerformanceColumnsPart = new StringBuilder(" (");
        insertWorkPerformanceValuesPart = new StringBuilder(" VALUES(");

        debugInsertWorkPerformanceSqlColumnsPart = new StringBuilder(UsageQueries.DYNAMIC_INSERT_WRK_PERF_TBL);
        debugInsertWorkPerformanceSqlColumnsPart.append(" (");
        debugInsertWorkPerformanceSqlValuesPart = new StringBuilder(" VALUES(");

        try {
            workPerformanceId = this.getSequence(UsageQueries.SEQUENCE_WRK_PERF_ID);

            workPerformanceVob.setWorkPerformanceId(workPerformanceId);

            workPerformanceValuesList = new ArrayList<>();

            insertWorkPerformanceColumnsPart.append(" APM_WRK_PERF_ID");
            insertWorkPerformanceValuesPart.append(" ?");
            workPerformanceValuesList.add(workPerformanceVob.getWorkPerformanceId());

            debugInsertWorkPerformanceSqlValuesPart.append(" '").append(workPerformanceVob.getWorkPerformanceId())
                .append("'");

            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getPerformanceHeaderId())) {
                insertWorkPerformanceColumnsPart.append(", APM_PERF_HDR_ID");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getPerformanceHeaderId());
                debugInsertWorkPerformanceSqlValuesPart.append(", '")
                    .append(workPerformanceVob.getPerformanceHeaderId()).append("'");
            }
            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getWorkSequenceNumber())) {
                insertWorkPerformanceColumnsPart.append(", WRK_SEQ_NR");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getWorkSequenceNumber());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getWorkSequenceNumber())
                    .append("'");
            }
            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getMedleySequenceNumber())) {
                insertWorkPerformanceColumnsPart.append(", MEDL_SEQ");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getMedleySequenceNumber());
                debugInsertWorkPerformanceSqlValuesPart.append(", '")
                    .append(workPerformanceVob.getMedleySequenceNumber()).append("'");
            }
            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getWorkTitle())) {
                insertWorkPerformanceColumnsPart.append(", WRK_TTL");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getWorkTitle());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getWorkTitle())
                    .append("')");
            }
            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getWorkId())) {
                insertWorkPerformanceColumnsPart.append(", WRK_ID");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getWorkId());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getWorkId())
                    .append("'");
            }
            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getUseTypeCode())) {
                insertWorkPerformanceColumnsPart.append(", USE_TYP");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getUseTypeCode());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getUseTypeCode())
                    .append("'");
            }
            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getWorkPerformanceDuration())) {
                insertWorkPerformanceColumnsPart.append(", WRK_PERF_DUR");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getWorkPerformanceDuration());
                debugInsertWorkPerformanceSqlValuesPart.append(", '")
                    .append(workPerformanceVob.getWorkPerformanceDuration()).append("'");
            }
            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getPerformerName())) {
                insertWorkPerformanceColumnsPart.append(", PFR_NA");
                insertWorkPerformanceValuesPart.append(", UPPER(?)");
                workPerformanceValuesList.add(workPerformanceVob.getPerformerName());
                debugInsertWorkPerformanceSqlValuesPart.append(", UPPER('")
                    .append(workPerformanceVob.getPerformerName()).append("')");
            }
            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getPlayCount())) {
                insertWorkPerformanceColumnsPart.append(", PLAY_CNT");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getPlayCount());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getPlayCount())
                    .append("'");
            }

            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getTargetYearQuarter())) {
                insertWorkPerformanceColumnsPart.append(", TGTSURVYEARQTR");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getTargetYearQuarter());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getTargetYearQuarter())
                    .append("'");
            }

            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getDetectionTime())) {
                insertWorkPerformanceColumnsPart.append(", DETECTION_TM");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getDetectionTime());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getDetectionTime())
                    .append("'");
            }

            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getLibraryDiscId())) {
                insertWorkPerformanceColumnsPart.append(", LIBRARY_DISC_ID");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getLibraryDiscId());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getLibraryDiscId())
                    .append("'");
            }

            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getLibraryDiscTitle())) {
                insertWorkPerformanceColumnsPart.append(", LIBRARY_DISC");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getLibraryDiscTitle());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getLibraryDiscTitle())
                    .append("'");
            }

            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getConfidenceLevel())) {
                insertWorkPerformanceColumnsPart.append(", CONFIDENCE_LVL");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getConfidenceLevel());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getConfidenceLevel())
                    .append("'");
            }

            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getWriter())) {
                insertWorkPerformanceColumnsPart.append(", WRITER");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getWriter());
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getWriter())
                    .append("'");
            }

            insertWorkPerformanceColumnsPart.append(", APM_MATCH_TYP");
            if (!UsageCommonValidations.isEmptyOrNull(workPerformanceVob.getWorkId())) {
                insertWorkPerformanceValuesPart.append(", '" + UsageConstants.APM_MATCH_TYPE_MANUAL + "'");
                debugInsertWorkPerformanceSqlValuesPart.append(", '" + UsageConstants.APM_MATCH_TYPE_MANUAL + "'");
            } else {
                insertWorkPerformanceValuesPart.append(", '" + UsageConstants.APM_MATCH_TYPE_NO_MATCH + "'");
                debugInsertWorkPerformanceSqlValuesPart.append(", '" + UsageConstants.APM_MATCH_TYPE_NO_MATCH + "'");
            }
            if (workPerformanceVob.getUserId() != null) {
                insertWorkPerformanceColumnsPart.append(", CRE_ID");
                insertWorkPerformanceValuesPart.append(", ?");
                workPerformanceValuesList.add(workPerformanceVob.getUserId());

                debugInsertWorkPerformanceSqlColumnsPart.append(", CRE_ID");
                debugInsertWorkPerformanceSqlValuesPart.append(", '").append(workPerformanceVob.getUserId())
                    .append("'");
            }

            insertWorkPerformanceColumnsPart.append(", SUPP_CODE");
            insertWorkPerformanceValuesPart.append(", (SELECT SUPP_CODE FROM APM_PERF_HDR WHERE APM_PERF_HDR_ID = ? )");
            workPerformanceValuesList.add(workPerformanceVob.getPerformanceHeaderId());

            insertWorkPerformanceColumnsPart.append(", APM_ARCHIVE_ID");
            insertWorkPerformanceValuesPart.append(", 0");

            insertWorkPerformanceColumnsPart.append(", DEL_FL");
            insertWorkPerformanceValuesPart.append(", 'N'");

            insertWorkPerformanceColumnsPart.append(", CRE_DT");
            insertWorkPerformanceValuesPart.append(", SYSDATE");

            insertWorkPerformanceColumnsPart.append(") ");
            insertWorkPerformanceValuesPart.append(") ");

            debugInsertWorkPerformanceSqlValuesPart.append(", SYSDATE ) ");

            // Makeing SQL String
            insertWorkPerformanceSqlStr.append(insertWorkPerformanceColumnsPart)
                .append(insertWorkPerformanceValuesPart);

            log.debug("Generated Dynamic SQL Query :" + insertWorkPerformanceSqlStr);
            debugFinalInsertWorkPerformanceSql = new StringBuilder(insertWorkPerformanceColumnsPart.toString());
            debugFinalInsertWorkPerformanceSql.append(debugInsertWorkPerformanceSqlValuesPart);
            log.debug("FINAL DEBUG SQL :" + debugFinalInsertWorkPerformanceSql.toString());

            int totalParameters;
            totalParameters = workPerformanceValuesList.size();

            ArrayList<Object> params = new ArrayList<>();
            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                params.add((String) workPerformanceValuesList.get(parameterId));
            }

            ascapJdbcTemplate.update(insertWorkPerformanceSqlStr.toString(), params.toArray());

            log.debug("CREATE BASIC WORK PERFORMANCE SUCCESSFULL :" + workPerformanceVob);

        } catch (Exception se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        return workPerformanceVob;
    }

    private String getSequence(String query , JdbcTemplate jdbcTemplate) throws PrepSystemException {
        String outSequenceNo = null;
        try {
            SqlRowSet sequenceRs = jdbcTemplate.queryForRowSet(query);
            if (sequenceRs != null) {
                if (!sequenceRs.isAfterLast() && !sequenceRs.isBeforeFirst()) {
                    // No Results found
                } else {
                    while (sequenceRs.next()) {
                        outSequenceNo = sequenceRs.getString(1);
                    }
                }
            }
        } catch (Exception se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }
        return (outSequenceNo);
    }

    private String buildInStringCriteriaForNumbers(String[] criteria) {
        int totalLength = 0;
        StringBuilder outStrBuff;

        if (criteria == null) {
            return "";
        }

        totalLength = criteria.length;
        if (totalLength == 0) {
            return "";
        }

        outStrBuff = new StringBuilder("(");
        for (int currIndex = 0; currIndex < totalLength; currIndex++) {
            if (UsageCommonValidations.isValidLong(criteria[currIndex])) {
                outStrBuff.append(" ").append(criteria[currIndex]).append(",");
            }
        }
        outStrBuff.deleteCharAt(outStrBuff.length() - 1);
        outStrBuff.append(")");
        return outStrBuff.toString();
    }

    public String getMedleyRelatedInformation(String informationType, String[] workPerformanceIds,
        String workSequenceNumber, String workPerformanceId, String performanceHeaderId) throws PrepSystemException {
        String queryString = null;
        String inString = null;
        String finQuery = null;
        String outSequenceNo = "";
        List<Object> params = new ArrayList<>();
        log.debug("Inside UsageDAOImpl.getMedleyRelatedInformation with values :" + informationType + ", workPerfIds="
            + this.buildInStringCriteriaForNumbers(workPerformanceIds) + ", WorkSequenceNr =" + workSequenceNumber
            + ", WorkPerfId=" + workPerformanceId + "ProgPerfId=" + performanceHeaderId);
        SqlRowSet sequenceRs = null;
        try {
            if ("GET_WORK_PERFORMANCE_SMALLEST_WORKSEQUENCE_NUMBER".equalsIgnoreCase(informationType)) {
                queryString = UsageQueries.GET_WORK_PERFORMANCE_SMALLEST_WORKSEQUENCE_NUMBER;
                inString = this.buildInStringCriteriaForNumbers(workPerformanceIds);
                finQuery = queryString + inString;
            }
            if ("GET_WORK_PERFORMANCE_HIGHEST_MEDLEY_SEQUENCE_NUMBER".equalsIgnoreCase(informationType)) {
                queryString = UsageQueries.GET_WORK_PERFORMANCE_HIGHEST_MEDLEY_SEQUENCE_NUMBER;
                inString = this.buildInStringCriteriaForNumbers(workPerformanceIds);
                finQuery = queryString + inString + " ) ";
                params.add(workSequenceNumber);
            }

            if ("GET_IF_PART_OF_MEDLEY".equalsIgnoreCase(informationType)) {
                queryString = UsageQueries.GET_IF_PART_OF_MEDLEY;
                finQuery = queryString;
                params.add(workSequenceNumber);
                params.add(performanceHeaderId);
                params.add(workPerformanceId);
            }

            if ("GET_IF_MEDLE_USE_TYPES_NOT_PART_OF_MEDLEY".equalsIgnoreCase(informationType)) {
                queryString = UsageQueries.GET_IF_MEDLEY_USE_TYPE_NOT_PART_OF_MEDLEY;
                finQuery = queryString;
                params.add(workSequenceNumber);
                params.add(performanceHeaderId);
                params.add(workPerformanceId);
            }

            if ("GET_IF_MEDLEY_BUT_SEQUENCE_NUMBER_MISSING".equalsIgnoreCase(informationType)) {
                queryString = UsageQueries.GET_IF_MEDLEY_BUT_SEQUENCE_NUMBER_MISSING;
                finQuery = queryString;
                params.add(performanceHeaderId);
                params.add(workPerformanceId);
            }

            if (queryString == null) {
                return null;
            }
            if (!params.isEmpty())
                sequenceRs = ascapJdbcTemplate.queryForRowSet(finQuery, params.toArray());
            else
                sequenceRs = ascapJdbcTemplate.queryForRowSet(finQuery);
            if (sequenceRs != null) {
                if (!sequenceRs.isAfterLast() && !sequenceRs.isBeforeFirst()) {
                    // No Results found
                } else {
                    if (sequenceRs.next()) {
                        outSequenceNo = sequenceRs.getString(1);
                    }
                }
            }

            log.debug("This is the output for 1st Execution of GET_IF_DUPLICATE_WRK_IN_MEDLEY '" + outSequenceNo + "'");

            if ("GET_IF_DUPLICATE_WRK_IN_MEDLEY".equalsIgnoreCase(informationType) && outSequenceNo.equals("")) {
                log.debug("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEexecuting query "
                    + UsageQueries.GET_IF_DUPLICATE_MERGE_WRK_IN_MEDLEY);
                queryString = UsageQueries.GET_IF_DUPLICATE_MERGE_WRK_IN_MEDLEY;

                params.add(workSequenceNumber);
                params.add(performanceHeaderId);
                params.add(workSequenceNumber);
                params.add(performanceHeaderId);
                params.add(workSequenceNumber);
                params.add(performanceHeaderId);
                params.add(workPerformanceId);
                params.add(workSequenceNumber);
                params.add(performanceHeaderId);
                params.add(workPerformanceId);

                log.debug("Executing for GET_IF_DUPLICATE_MERGE_WRK_IN_MEDLEY with the query \n" + queryString
                    + "\n with workSequenceNumber=" + workSequenceNumber + ", performanceHeaderId="
                    + performanceHeaderId + ",workPerformanceId=" + workPerformanceId);

                sequenceRs = ascapJdbcTemplate.queryForRowSet(queryString, params.toArray());
                if (sequenceRs != null) {
                    if (!sequenceRs.isAfterLast() && !sequenceRs.isBeforeFirst()) {
                        // No Results found
                    } else {
                        if (sequenceRs.next()) {
                            outSequenceNo = sequenceRs.getString(1);
                        }
                    }
                }
            }
        } catch (Exception se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }
        log.debug("Returning getMedleyRelatedInformation with out value " + outSequenceNo);
        return (outSequenceNo);
    }

    public List<Object> getWorkPerformanceIdsOfMedley(String programPerformanceId, String workSequenceNumber)
        throws PrepSystemException {
        log.debug("Entering getWorkPerformanceIdsOfMedley of UsageDAOImpl, programPerformanceId" + programPerformanceId
            + " workSequenceNumber:" + workSequenceNumber);
        String outSequenceNo = null;
        List<Object> workPerformanceIdsColl = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        try {
            params.add(programPerformanceId);
            params.add(workSequenceNumber);
            SqlRowSet sequenceRs =
                ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_WORK_PERFORMANCE_IDS_OF_MEDLEY, params);
            if (sequenceRs != null) {
                if (!sequenceRs.isAfterLast() && !sequenceRs.isBeforeFirst()) {
                    // No Results found
                } else {
                    while (sequenceRs.next()) {
                        outSequenceNo = sequenceRs.getString(1);
                        workPerformanceIdsColl.add(outSequenceNo);
                    }
                }
            }
        } catch (Exception se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }

        log.debug("Exiting getWorkPerformanceIdsOfMedley of UsageDAOImpl");
        return (workPerformanceIdsColl);
    }

    public List<Object> getValidationLookup(String validationLookupKey) throws PrepSystemException {
        if (log.isDebugEnabled()) {
            log.debug("Entering getValidationLookup in UsageDAOImpl validationLookupKey: " + validationLookupKey);
        }
        List<Object> outValidationLookupColl;
        outValidationLookupColl = new ArrayList<>();
        String currValidationLookupCode = null;
        String query = null;
        try {

            if (UsageConstants.VLDN_LKUPS_KEY_USE_TYPE_ALL.equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_USE_TYPES_ALL;
            } else if (UsageConstants.VLDN_LKUPS_KEY_USE_TYPE_FEATURED.equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_USE_TYPES_IS_FEATURED;
            } else if (UsageConstants.VLDN_LKUPS_KEY_USE_TYPE_MEDLEY.equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_USE_TYPES_IS_MEDLEY;
            } else if (UsageConstants.VLDN_LKUPS_KEY_USE_TYPE_LIBRARY.equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_USE_TYPES_IS_LIBRARY;
            } else if (UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_ALL.equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_US_RULE_GRP_MBRS_ALL;
            } else if (UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_DEFAULT_MUSIC_USER
                .equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_US_RULE_GRP_MBRS_DEFAULT_MUSIC_USER;
            } else if (UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_FOREGROUND.equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_US_RULE_GRP_MBRS_FOREGROUND;
            } else if (UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_GENERAL_BACKGROUND
                .equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_US_RULE_GRP_MBRS_GENERAL_BACKGROUND;
            } else if (UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_INTERACTIVE.equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_US_RULE_GRP_MBRS_INTERACTIVE;
            } else if (UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_INTERNET_STREAMING
                .equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_US_RULE_GRP_MBRS_INTERNET_STREAMING;
            } else if (UsageConstants.VLDN_LKUPS_KEY_US_RULE_GRP_LIVE_POP_CONCERTS
                .equalsIgnoreCase(validationLookupKey)) {
                query = UsageQueries.VLDN_LKUPS_US_RULE_GRP_MBRS_LIVE_POP_CONCERTS;
            }
            SqlRowSet validationLookupRs = ascapJdbcTemplate.queryForRowSet(query);
            if (!validationLookupRs.isAfterLast() && !validationLookupRs.isBeforeFirst()) {
                // No Results found
            } else {
                while (validationLookupRs.next()) {
                    currValidationLookupCode = validationLookupRs.getString(1);
                    outValidationLookupColl.add(currValidationLookupCode);
                }
            }
        } catch (Exception se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);

        }

        log.debug("Exiting getValidationLookup in UsageDAOImpl");
        return outValidationLookupColl;
    }

    public WorkPerformancesList updateWorkPerformancesBulk(WorkPerformancesList workPerformancesList)
        throws PrepSystemException {
        log.debug("Entering UsageDAOImpl - updateWorkPerformancesBulk method");

        if (workPerformancesList == null || workPerformancesList.getSearchResults() == null
            || workPerformancesList.getSearchResults().isEmpty()) {
            return workPerformancesList;
        }

        try {
            List<Object> workPerfColl = workPerformancesList.getSearchResults();

            if (workPerfColl != null && !workPerfColl.isEmpty()) {

                Iterator<Object> itr = workPerfColl.iterator();
                WorkPerformance wp = null;

                while (itr.hasNext()) {

                    wp = (WorkPerformance) itr.next();

                    log.debug("DAO wp.workperfid " + wp.getWorkPerformanceId());
                    log.debug("DAO getUseTypeCode " + wp.getUseTypeCode());
                    log.debug(" ");

                    List<Object> params = new ArrayList<>();
                    params.add(wp.getUseTypeCode());
                    params.add(wp.getUserId());
                    params.add(wp.getWorkPerformanceId());
                    ascapJdbcTemplate.update(UsageQueries.UPDATE_WORK_PERFORMANCE_USE_TYP, params.toArray());
                }
                if (wp != null && !ValidationUtils.isEmptyOrNull(wp.getWorkPerformanceId())) {
                    log.debug("Work Performance Id for Batch Validate " + wp.getWorkPerformanceId());
                    callPerformanceBatchValidate(UsageConstants.WRK_PERF_UPDATE, null, wp.getWorkPerformanceId(),
                        wp.getUserId());
                }
            }

        } catch (Exception se) {
            log.error(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }

        log.debug("Exiting UsageDAOImpl - updateWorkPerformancesBulk method");

        return workPerformancesList;

    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#getCatalogManualMatch(apmPerformanceBulkRequest)
     */
    public ApmPerformanceBulkRequest getCatalogManualMatch(ApmPerformanceBulkRequest apmPerformanceBulkRequest)
        throws PrepSystemException {

        log.debug(
            "Entering getCatalogManualMatch method in UsageDAOImpl getCatalogManualMatch " + apmPerformanceBulkRequest);

        ArrayList<String> searchValuesList = new ArrayList<>();
        StringBuilder sb = new StringBuilder(UsageQueries.GET_APM_PERF_BLK_REQUEST_CATALOG);
        ApmPerformanceBulkRequest outApmPerformanceBulkRequest = null;

        searchValuesList.add(apmPerformanceBulkRequest.getWorkId());

        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getSupplierCode())) {
            sb.append(" AND A.SUPP_CODE = ? ");
            searchValuesList.add(apmPerformanceBulkRequest.getSupplierCode());
        }
        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWorkTitle())) {
            sb.append(" AND A.WRK_TTL = ? ");
            searchValuesList.add(apmPerformanceBulkRequest.getWorkTitle());
        } else {
            sb.append(" AND A.WRK_TTL IS NULL ");
        }

        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
            sb.append(" AND A.PFR_NA = ? ");
            searchValuesList.add(apmPerformanceBulkRequest.getPerformerName());
        } else {
            sb.append(" AND A.PFR_NA IS NULL ");
        }
        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())) {
            sb.append(" AND A.WRITER = ? ");
            searchValuesList.add(apmPerformanceBulkRequest.getWriterName());
        } else {
            sb.append(" AND A.WRITER IS NULL ");
        }

        sb.append(" ORDER BY A.UPD_DT DESC ");

        int totalParameters;
        try {

            log.debug("Executing Query : " + sb.toString());
            totalParameters = searchValuesList.size();
            Object[] params = new Object[totalParameters];

            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                params[parameterId] = searchValuesList.get(parameterId);
            }

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(sb.toString(), params);

            if (rs.next()) {
                outApmPerformanceBulkRequest = new ApmPerformanceBulkRequest();
                outApmPerformanceBulkRequest.setSupplierCode(rs.getString("SUPP_CODE"));
                outApmPerformanceBulkRequest.setPerformerName(rs.getString("PFR_NA"));
                outApmPerformanceBulkRequest.setWorkTitle(rs.getString("WRK_TTL"));
                outApmPerformanceBulkRequest.setWorkId(rs.getString("WRK_ID"));
            }

        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        }
        log.debug("Exiting getCatalogManualMatch method in UsageDAOImpl ");

        return outApmPerformanceBulkRequest;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#validateWorkIdForBulkMatch(apmPerformanceBulkRequest)
     */
    public List<Object> validateWorkIdForBulkMatch(ApmPerformanceBulkRequest apmPerformanceBulkRequestList)
        throws PrepSystemException {

        log.debug("Entering validateWorkIdForBulkMatch(ApmPerformanceBulkRequest apmPerformanceBulkRequestList) + "
            + apmPerformanceBulkRequestList);

        List<Object> col = new ArrayList<>();
        try {

            if (!ValidationUtils.isValidLong(apmPerformanceBulkRequestList.getWorkId())) {
                col.add("Work ID " + apmPerformanceBulkRequestList.getWorkId() + " must be Numeric");
            } else {

                String qry =
                    "SELECT WRK_ID,  WRK_TYP_CDE, nvl(SUR_WRK_IND,'N') SUR_WRK_IND, nvl(POSS_MCH_IND,'N') POSS_MCH_IND, nvl(WRK_GRD_VAL,0) WRK_GRD_VAL, nvl(PUB_DOM_IND,'N') PUB_DOM_IND "
                        + "  FROM   mvw_wrk  WHERE  WRK_ID = ? ";

                SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(qry, apmPerformanceBulkRequestList.getWorkId());

                if (rs.next()) {
                    if ("INF".equals(rs.getString("WRK_TYP_CDE"))) {
                        col.add("Work ID " + apmPerformanceBulkRequestList.getWorkId() + " is an Informational Work");
                    }
                    if ("Y".equals(rs.getString("PUB_DOM_IND"))) {
                        col.add("Work ID " + apmPerformanceBulkRequestList.getWorkId() + " is a Public Domain Work");
                    }

                    if (0 == rs.getInt("WRK_GRD_VAL")) {
                        col.add("Work ID " + apmPerformanceBulkRequestList.getWorkId()
                            + " Copyright Arrangement Percentage of the Work is not Available");
                    }
                } else {
                    col.add("Work ID " + apmPerformanceBulkRequestList.getWorkId() + " is not a valid Work ID");
                }
            }

        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);

        }

        log.debug(
            "Exiting validateWorkIdForBulkMatch(ApmPerformanceBulkRequest apmPerformanceBulkRequestList) Errors size : "
                + col.size());
        return col;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#updateCatalogManualMatchList(apmPerformanceBulkRequestList)
     */

    public ApmPerformanceBulkRequestList updateCatalogManualMatchList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering UsageDAOImpl - updateCatalogManualMatchList method ");

        if (apmPerformanceBulkRequestList == null) {
            return apmPerformanceBulkRequestList;
        }

        Collection<Object> col = apmPerformanceBulkRequestList.getSearchResults();
        Iterator<Object> itr = null;

        if (col == null) {
            log.debug("Input Collection is null.. Returning.. null");
            return apmPerformanceBulkRequestList;
        } else {
            itr = col.iterator();
        }

        StringBuilder sqlStr = null;
        sqlStr = new StringBuilder(UsageQueries.INSERT_APM_PERF_BLK_REQ);

        try {

            String apmPerfBulkRequestId = null;
            String apmPerfBulkRequestGroupId = null;

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_SEQUENCE_APM_PERF_BLK_REQ_GRP_ID);

            if (rs.next()) {
                apmPerfBulkRequestGroupId = rs.getString(1);
            }

            ApmPerformanceBulkRequest apmFile = null;
            List<Object[]> parametersList = new ArrayList<>();

            while (itr.hasNext()) {

                rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_SEQUENCE_APM_PERF_BLK_REQ_ID);
                if (rs.next()) {
                    apmPerfBulkRequestId = rs.getString(1);
                }

                apmFile = (ApmPerformanceBulkRequest) itr.next();

                Object[] params = new Object[13];

                params[0] = apmPerfBulkRequestId;
                params[1] = apmPerfBulkRequestGroupId;
                params[2] = apmFile.getSupplierCode();
                params[3] = apmFile.getPerformerName();
                params[4] = apmFile.getWorkTitle();
                if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH
                    .equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {
                    params[5] = apmFile.getOriginalWorkId();
                } else {
                    params[5] = apmFile.getWorkId();
                }
                params[6] = apmFile.getWorkPerfCount();
                params[7] = apmFile.getPlayCount();
                params[8] = UsageConstants.BULK_REQUEST_STATUS_PENDING;
                params[9] = apmFile.getUserId();
                params[10] = apmPerformanceBulkRequestList.getRequestTypeCode();
                params[11] = apmFile.getWriterName();
                params[12] = null;

                parametersList.add(params);
            }

            ascapJdbcTemplate.batchUpdate(sqlStr.toString(), parametersList);

            Iterator<Object> itrN = col.iterator();

            while (itrN.hasNext()) {
                apmFile = (ApmPerformanceBulkRequest) itrN.next();

                apmFile.setRequestTypeCode(apmPerformanceBulkRequestList.getRequestTypeCode());
                updateCatalogManualMatch(apmFile);
            }

            ascapJdbcTemplate.update(UsageQueries.UPDATE_APM_PERF_BLK_REQ_CO, apmPerformanceBulkRequestList.getUserId(),
                apmPerfBulkRequestGroupId);

        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        }
        log.debug("Exiting UsageDAOImpl - updateCatalogManualMatchList method");

        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#updateCatalogManualMatch(apmPerformanceBulkRequest)
     */
    private ApmPerformanceBulkRequest updateCatalogManualMatch(ApmPerformanceBulkRequest apmPerformanceBulkRequest) {
        log.debug("Entering updateCatalogManualMatch method in UsageDAOImpl apmPerformanceBulkRequest "
            + apmPerformanceBulkRequest);

        StringBuilder sqlStr;
        StringBuilder columnsPart;
        ArrayList<String> valuesList = new ArrayList<>();
        sqlStr = new StringBuilder("UPDATE APM_CATALOG SET ");
        columnsPart = new StringBuilder("");
        columnsPart.append(" UPD_DT = SYSDATE ");
        columnsPart.append(", UPD_ID = ? ");
        valuesList.add(apmPerformanceBulkRequest.getUserId());

        if (UsageConstants.BULK_REQUEST_TYPE_UPDATE.equals(apmPerformanceBulkRequest.getRequestTypeCode())) {

            columnsPart.append(", MAN_MATCH_USR_ID = ? ");
            valuesList.add(apmPerformanceBulkRequest.getUserId());
            columnsPart.append(", WRK_ID = ? ");
            valuesList.add(apmPerformanceBulkRequest.getWorkId());
            columnsPart.append(", APM_MATCH_TYP = ? ");
            valuesList.add("MAN");
            columnsPart.append(", MAN_MATCH_DT = SYSDATE ");

        } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(apmPerformanceBulkRequest.getRequestTypeCode())) {
            columnsPart.append(", MAN_MATCH_USR_ID = NULL ");
            columnsPart.append(", MAN_MATCH_DT = NULL ");
            columnsPart.append(", WRK_ID = NULL ");
            columnsPart.append(", APM_MATCH_TYP = ? ");
            valuesList.add("NMT");
        }

        else if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(apmPerformanceBulkRequest.getRequestTypeCode())) {
            columnsPart.append(", DEL_FL = 'Y' ");
            columnsPart.append(", MAN_MATCH_USR_ID = ? ");
            columnsPart.append(", MAN_MATCH_DT = SYSDATE ");
            valuesList.add(apmPerformanceBulkRequest.getUserId());
            columnsPart.append(", DEL_RSN_CDE = ? ");
            valuesList.add("MAN_DEL");
            columnsPart.append(", APM_MATCH_TYP = ? ");
            valuesList.add("MAN");
        } else if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(apmPerformanceBulkRequest.getRequestTypeCode())) {
            columnsPart.append(", DEL_FL = 'N' ");
            columnsPart.append(", MAN_MATCH_USR_ID = NULL ");
            columnsPart.append(", MAN_MATCH_DT = NULL ");
            columnsPart.append(", WRK_ID = NULL ");
            columnsPart.append(", MULT_WRK_ID = NULL ");
            columnsPart.append(", APM_MATCH_TYP = ? ");
            valuesList.add("NMT");
            columnsPart.append(", DEL_RSN_CDE = NULL ");

        } else {
            log.debug("Invalid request type ... Returning......");
            return apmPerformanceBulkRequest;
        }

        if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(apmPerformanceBulkRequest.getRequestTypeCode())) {
            columnsPart.append(
                " WHERE DEL_FL = 'Y' AND CRE_ID <> 'MEDLEYADD' AND (MAN_MATCH_DT BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE+1)) AND SEND_MAN_MATCH = 'Y' AND WRK_TTL = ? ");
        } else {
            columnsPart.append(" WHERE DEL_FL = 'N' AND SEND_MAN_MATCH = 'Y' AND WRK_TTL = ? ");
        }

        if (UsageConstants.BULK_REQUEST_TYPE_UPDATE.equals(apmPerformanceBulkRequest.getRequestTypeCode())) {
            columnsPart.append(" AND WRK_ID IS NULL ");
        }

        valuesList.add(apmPerformanceBulkRequest.getWorkTitle());

        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getSupplierCode())) {
            columnsPart.append(" AND SUPP_CODE = ? ");
            valuesList.add(apmPerformanceBulkRequest.getSupplierCode());
        }

        if (ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
            columnsPart.append(" AND PFR_NA IS NULL");
            if (ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())) {
                columnsPart.append(" AND WRITER IS NULL ");
            } else {
                columnsPart.append(" AND WRITER = ? ");
                valuesList.add(apmPerformanceBulkRequest.getWriterName());
            }
        } else {
            columnsPart.append(" AND PFR_NA = ? ");
            valuesList.add(apmPerformanceBulkRequest.getPerformerName());
        }
        try {
            sqlStr.append(columnsPart);
            log.debug("Generated Dynamic SQL Query :" + sqlStr.toString());
            int totalParameters;
            totalParameters = valuesList.size();
            ArrayList<Object> params = new ArrayList<>();
            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                params.add((String) valuesList.get(parameterId));
                log.debug("param " + (parameterId + 1) + "  value " + ((String) valuesList.get(parameterId)));
            }
            ascapJdbcTemplate.update(sqlStr.toString(), params.toArray());
        } finally {
            log.debug("Exiting updateCatalogManualMatch method in UsageDAOImpl apmPerformanceBulkRequest "
                + apmPerformanceBulkRequest);
        }
        return apmPerformanceBulkRequest;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#deleteLearnedMatch(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList deleteLearnedMatch(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList)
        throws PrepSystemException {
        log.debug("Entering deleteLearnedMatch in UsageDAOImpl");

        Collection<Object> col = apmPerformanceBulkRequestList.getSearchResults();
        Iterator<Object> itr = null;

        if (col == null) {
            log.debug("Input Collection is null.. Returning.. null");
            return apmPerformanceBulkRequestList;
        } else {
            itr = col.iterator();
        }
        StringBuilder updateQuery = null;
        ApmPerformanceBulkRequest apmRequest = null;
        try {
            while (itr.hasNext()) {
                apmRequest = (ApmPerformanceBulkRequest) itr.next();

                log.debug("apmRequest::::::::::::::" + apmRequest);

                String matchType = UsageConstants.APM_LEARNED_MATCH_TYPE_PERFORMER;
                String performerOrWriterValue = null;

                if (apmRequest != null) {
                    performerOrWriterValue = apmRequest.getPerformerName();

                    if (ValidationUtils.isEmptyOrNull(apmRequest.getPerformerName())
                        && !ValidationUtils.isEmptyOrNull(apmRequest.getWriterName())) {
                        matchType = UsageConstants.APM_LEARNED_MATCH_TYPE_WRITER;
                        performerOrWriterValue = apmRequest.getWriterName();
                    }
                } else {
                    continue;
                }
                if (UsageConstants.APM_LEARNED_MATCH_TYPE_WRITER.equals(matchType)) {
                    updateQuery = new StringBuilder(UsageQueries.DELETE_LEARNED_MATCH_WTR);
                } else {
                    updateQuery = new StringBuilder(UsageQueries.DELETE_LEARNED_MATCH);
                }

                ArrayList<Object> params = new ArrayList<>();
                params.add(apmRequest.getUserId());
                params.add(performerOrWriterValue);
                params.add(apmRequest.getWorkTitle());
                params.add(apmRequest.getOriginalWorkId());

                ascapJdbcTemplate.update(updateQuery.toString(), params.toArray());
            }
        } catch (Exception se) {
            log.debug(se);
            log.error(se.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", se);

        } finally {
            log.debug("Exiting deleteLearnedMatch in UsageDAOImpl");
        }

        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#undeleteLearnedMatch(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList
        undeleteLearnedMatch(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering undeleteLearnedMatch in UsageDAOImpl");

        Collection<Object> col = apmPerformanceBulkRequestList.getSearchResults();
        Iterator<Object> itr = null;

        if (col == null) {
            log.debug("Input Collection is null.. Returning.. null");
            return apmPerformanceBulkRequestList;
        } else {
            itr = col.iterator();
        }

        ApmPerformanceBulkRequest apmRequest = null;
        try {
            while (itr.hasNext()) {
                apmRequest = (ApmPerformanceBulkRequest) itr.next();
                log.debug("apmRequest::::::::::::::" + apmRequest);
                String matchType = UsageConstants.APM_LEARNED_MATCH_TYPE_PERFORMER;
                String performerOrWriterValue = null;
                String supplierIsNull = null;

                if (apmRequest != null) {
                    performerOrWriterValue = apmRequest.getPerformerName();
                    if ((ValidationUtils.isEmptyOrNull(apmRequest.getWorkTitle())
                        || ValidationUtils.isEmptyOrNull(apmRequest.getPerformerName()))
                        && (ValidationUtils.isEmptyOrNull(apmRequest.getWorkTitle())
                            || ValidationUtils.isEmptyOrNull(apmRequest.getWriterName()))) {
                        log.debug(
                            "Inside undeleteLearnedMatch.. (Work Tiltle or Performer Name is null), and (Work Tiltle or Writer Name is null)so continuing for next group");
                        continue;
                    }
                    if (ValidationUtils.isEmptyOrNull(apmRequest.getPerformerName())
                        && !ValidationUtils.isEmptyOrNull(apmRequest.getWriterName())) {
                        matchType = UsageConstants.APM_LEARNED_MATCH_TYPE_WRITER;
                        performerOrWriterValue = apmRequest.getWriterName();
                    }
                } else {
                    continue;
                }
                if (ValidationUtils.isEmptyOrNull(apmRequest.getSupplierCode())) {
                    log.debug(" SUPP_CODE IS NULL ");
                    supplierIsNull = "Y";
                } else {
                    log.debug("SUPP_CODE " + apmRequest.getSupplierCode());
                    supplierIsNull = "N";
                }
                log.debug("matchType:" + matchType + " supplierIsNull:" + supplierIsNull);
                String learnedDeleteExists = null;

                // Build update query
                StringBuilder updateQuery = new StringBuilder(" ");

                // title / artist combination condition
                if (UsageConstants.APM_LEARNED_MATCH_TYPE_PERFORMER.equals(matchType)) {
                    // supplier exists
                    if (!supplierIsNull.equalsIgnoreCase("Y")) {

                        learnedDeleteExists = learnedDeleteExists(apmRequest.getSupplierCode(),
                            apmRequest.getWorkTitle(), performerOrWriterValue, "N");

                        // LM entry exists
                        if (learnedDeleteExists.equalsIgnoreCase("Y")) {
                            updateQuery =
                                new StringBuilder(UsageQueries.UNDELETE_LEARNED_MATCH + " AND SUPP_CODE = ? ");
                        }
                        // LM entry does not exists
                        else
                            updateQuery =
                                new StringBuilder(UsageQueries.UNDELETE_LEARNED_MATCH + " AND SUPP_CODE IS NULL ");
                    }
                    // supplier does not exists
                    else {
                        learnedDeleteExists =
                            learnedDeleteExists(apmRequest.getWorkTitle(), performerOrWriterValue, "N");

                        // LM entry exists
                        if (learnedDeleteExists.equalsIgnoreCase("Y")) {
                            updateQuery =
                                new StringBuilder(UsageQueries.UNDELETE_LEARNED_MATCH + " AND SUPP_CODE IS NULL ");
                        }
                        // LM entry does not exists
                        else
                            updateQuery = new StringBuilder(UsageQueries.UNDELETE_LEARNED_MATCH);
                    }
                }
                // title / writer combination condition
                else if (UsageConstants.APM_LEARNED_MATCH_TYPE_WRITER.equals(matchType)) {

                    // supplier exists
                    if (!supplierIsNull.equalsIgnoreCase("Y")) {
                        learnedDeleteExists = learnedDeleteExists(apmRequest.getSupplierCode(),
                            apmRequest.getWorkTitle(), performerOrWriterValue, "Y");

                        // LM entry exists
                        if (learnedDeleteExists.equalsIgnoreCase("Y")) {
                            updateQuery =
                                new StringBuilder(UsageQueries.UNDELETE_LEARNED_MATCH_WTR + " AND SUPP_CODE = ? ");
                        }
                        // LM entry does not exists
                        else
                            updateQuery =
                                new StringBuilder(UsageQueries.UNDELETE_LEARNED_MATCH_WTR + " AND SUPP_CODE IS NULL ");
                    }
                    // supplier does not exists
                    else {
                        learnedDeleteExists =
                            learnedDeleteExists(apmRequest.getWorkTitle(), performerOrWriterValue, "Y");
                        // LM entry exists
                        if (learnedDeleteExists.equalsIgnoreCase("Y")) {
                            updateQuery =
                                new StringBuilder(UsageQueries.UNDELETE_LEARNED_MATCH_WTR + " AND SUPP_CODE IS NULL ");
                        }
                        // LM entry does not exists
                        else
                            updateQuery = new StringBuilder(UsageQueries.UNDELETE_LEARNED_MATCH_WTR);
                    }
                }
                ArrayList<Object> params = new ArrayList<>();
                params.add(apmRequest.getWorkTitle());
                params.add(performerOrWriterValue);
                if (updateQuery.toString().contains("AND SUPP_CODE = ?")) {
                    params.add(apmRequest.getSupplierCode());
                }
                ascapJdbcTemplate.update(updateQuery.toString(), params.toArray());
            }
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        } finally {
            log.debug("Exiting undeleteLearnedMatch in UsageDAOImpl");
        }
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#learnedDeleteExists(title,artist,writerMatching)
     */
    private String learnedDeleteExists(String title, String artist, String writerMatching) throws PrepSystemException {
        log.debug("Entering  UsageDAOImpl - learnedDeleteExists method ");

        String result = "";
        String sql = UsageQueries.LEARNED_DEL_EXISTS + " AND SUPP_CODE IS NULL ";
        if (writerMatching.equalsIgnoreCase("Y")) {
            sql = UsageQueries.LEARNED_DEL_EXISTS_WTR + " AND SUPP_CODE IS NULL ";
        }
        log.debug("learnedDelete sql: " + sql);
        log.debug("title: " + title);
        log.debug("artist: " + artist);

        try {
            SqlRowSet sequenceRs = ascapJdbcTemplate.queryForRowSet(sql, title, artist);
            while (sequenceRs.next()) {
                result = sequenceRs.getString("learnedDeleteExists");
                log.debug("learnedDeleteExists: " + result);
            }
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        }
        log.debug("Exiting  UsageDAOImpl - learnedDeleteExists method ");

        return (result);
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#learnedDeleteExists(supplier,title,artist,writerMatching)
     */
    private String learnedDeleteExists(String supplier, String title, String artist, String writerMatching)
        throws PrepSystemException {
        log.debug("Entering  UsageDAOImpl - learnedDeleteExists method ");
        String result = "";
        String sql = UsageQueries.LEARNED_DEL_EXISTS + " AND SUPP_CODE = ? ";

        if (writerMatching.equalsIgnoreCase("Y")) {
            sql = UsageQueries.LEARNED_DEL_EXISTS_WTR + " AND SUPP_CODE = ? ";
        }
        try {
            SqlRowSet sequenceRs = ascapJdbcTemplate.queryForRowSet(sql, title, artist, supplier);
            while (sequenceRs.next()) {
                result = sequenceRs.getString("learnedDeleteExists");
                log.debug("learnedDeleteExists: " + result);
                log.debug("learnedDelete sql: " + sql);
            }
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        } finally {
            log.debug("Exiting  UsageDAOImpl - learnedDeleteExists method ");
        }
        return (result);
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#updateLearnedkMatch(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList
        updateLearnedkMatch(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering updateLearnedkMatch in UsageDAOImpl");

        Collection<Object> col = apmPerformanceBulkRequestList.getSearchResults();
        Iterator<Object> itr = null;

        if (col == null) {
            log.debug("Input Collection is null.. Returning.. null");
            return apmPerformanceBulkRequestList;
        } else {
            itr = col.iterator();
        }
        ApmPerformanceBulkRequest apmRequest = null;

        try {
            while (itr.hasNext()) {
                apmRequest = (ApmPerformanceBulkRequest) itr.next();
                log.debug("apmRequest::::::::::::::" + apmRequest);
                String matchType = UsageConstants.APM_LEARNED_MATCH_TYPE_PERFORMER;
                String performerOrWriterValue = null;

                if (apmRequest != null) {
                    performerOrWriterValue = apmRequest.getPerformerName();
                    if ((ValidationUtils.isEmptyOrNull(apmRequest.getWorkTitle())
                        || ValidationUtils.isEmptyOrNull(apmRequest.getPerformerName()))
                        && (ValidationUtils.isEmptyOrNull(apmRequest.getWorkTitle())
                            || ValidationUtils.isEmptyOrNull(apmRequest.getWriterName()))) {
                        log.debug(
                            "Inside updateLearnedkMatch.. (Work Tiltle or Performer Name is null), and (Work Tiltle or Writer Name is null)so continuing for next group");
                        continue;
                    }

                    if (ValidationUtils.isEmptyOrNull(apmRequest.getPerformerName())
                        && !ValidationUtils.isEmptyOrNull(apmRequest.getWriterName())) {
                        matchType = UsageConstants.APM_LEARNED_MATCH_TYPE_WRITER;
                        performerOrWriterValue = apmRequest.getWriterName();
                        // skip updating writer learned match if not permitted
                        if ("CATALOG".equals(apmPerformanceBulkRequestList.getApmPerformanceBulkRequestListType())) {
                            if (!writerMatchPermittedCatalog(apmRequest)) {
                                continue;
                            }
                        } else {
                            if (!writerMatchPermitted(apmRequest)) {
                                continue;
                            }
                        }
                    }
                } else {
                    continue;
                }
                int count = 0;
                if (count <= 0) {
                    StringBuilder updateQuery = null;
                    if ("UPD".equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {
                        updateQuery = new StringBuilder(UsageQueries.UPDATE_LEARNED_MATCH);
                        if (UsageConstants.APM_LEARNED_MATCH_TYPE_WRITER.equals(matchType)) {
                            updateQuery = new StringBuilder(UsageQueries.UPDATE_LEARNED_MATCH_WTR);
                        }
                        if (ValidationUtils.isEmptyOrNull(apmRequest.getSupplierCode())) {
                            updateQuery.append(" AND SUPP_CODE IS NULL ");
                        } else {
                            updateQuery.append(" AND SUPP_CODE = ? ");
                        }
                        ArrayList<Object> params = new ArrayList<>();
                        params.add(apmRequest.getUserId());
                        params.add(performerOrWriterValue);
                        params.add(apmRequest.getWorkTitle());
                        params.add(apmRequest.getWorkId());

                        if (!ValidationUtils.isEmptyOrNull(apmRequest.getSupplierCode())) {
                            params.add(apmRequest.getSupplierCode());
                        }
                        ascapJdbcTemplate.update(updateQuery.toString(), params.toArray());
                    }

                    updateQuery = new StringBuilder(UsageQueries.UPDATE_LEARNED_MATCH_VALID);
                    if (UsageConstants.APM_LEARNED_MATCH_TYPE_WRITER.equals(matchType)) {
                        updateQuery = new StringBuilder(UsageQueries.UPDATE_LEARNED_MATCH_VALID_WTR);
                    }

                    if (ValidationUtils.isEmptyOrNull(apmRequest.getSupplierCode())) {
                        updateQuery.append(" AND SUPP_CODE IS NULL ");
                    } else {
                        updateQuery.append(" AND SUPP_CODE = ? ");
                    }
                    if ("UPD".equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {

                        updateQuery.append(" AND WRK_ID = ? ");
                    } else {
                        updateQuery.append(" AND DEL_FL = 'N' ");
                    }
                    ArrayList<Object> params = new ArrayList<>();
                    params.add(apmRequest.getUserId());
                    params.add("UPD".equals(apmPerformanceBulkRequestList.getRequestTypeCode()) ? "N" : "Y");
                    params.add(performerOrWriterValue);
                    params.add(apmRequest.getWorkTitle());

                    if (!ValidationUtils.isEmptyOrNull(apmRequest.getSupplierCode())) {
                        params.add(apmRequest.getSupplierCode());
                        if ("UPD".equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {
                            params.add(apmRequest.getWorkId());
                        }
                    } else {
                        if ("UPD".equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {
                            params.add(apmRequest.getWorkId());
                        }
                    }

                    int validCount = ascapJdbcTemplate.update(updateQuery.toString(), params.toArray());
                    if (validCount <= 0) {
                        if (UsageConstants.APM_LEARNED_MATCH_TYPE_WRITER.equals(matchType)) {
                            updateQuery = new StringBuilder(UsageQueries.INSERT_LEARNED_MATCH_WTR);
                        } else {
                            updateQuery = new StringBuilder(UsageQueries.INSERT_LEARNED_MATCH);
                        }

                        params.clear();
                        params.add(ValidationUtils.isEmptyOrNull(apmRequest.getSupplierCode()) ? null
                            : apmRequest.getSupplierCode());
                        params.add(performerOrWriterValue);
                        params.add(apmRequest.getWorkTitle());
                        params.add("UPD".equals(apmPerformanceBulkRequestList.getRequestTypeCode())
                            ? apmRequest.getWorkId() : null);
                        params.add(apmRequest.getUserId());
                        params.add("UPD".equals(apmPerformanceBulkRequestList.getRequestTypeCode()) ? "N" : "Y");
                        ascapJdbcTemplate.update(updateQuery.toString(), params.toArray());
                    }
                }
            }
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        }
        log.debug("Exiting updateLearnedkMatch in UsageDAOImpl");
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#writerMatchPermitted(apmRequest)
     */
    private boolean writerMatchPermitted(ApmPerformanceBulkRequest apmRequest) throws PrepSystemException {

        boolean result = false;
        StringBuilder suppMatchTypeQuery = null;
        log.debug("Entering writerMatchPermitted");

        try {

            // if grouped by supplier
            if (!ValidationUtils.isEmptyOrNull(apmRequest.getSupplierCode())) {
                suppMatchTypeQuery = new StringBuilder(UsageQueries.GET_APM_LRN_MCH_TYPE_GRPBY);
                SqlRowSet rs =
                    ascapJdbcTemplate.queryForRowSet(suppMatchTypeQuery.toString(), apmRequest.getSupplierCode());
                // supplier is configured for writer matching
                if (rs.next()) {
                    result = true;
                    log.debug("Supplier: " + apmRequest.getSupplierCode() + " is configured for writer matching.");
                } else
                    log.debug("Supplier: " + apmRequest.getSupplierCode() + " is NOT configured for writer matching.");

            }
            // if not grouped by supplier
            else {
                // get suppliers from apm_wrk_perf
                suppMatchTypeQuery = new StringBuilder(UsageQueries.GET_APM_LRN_MCH_TYPE_NOGRPBY);
                String year = getActiveSurveyYearQuarter();
                ArrayList<Object> params = new ArrayList<>();
                params.add(year);
                params.add(apmRequest.getWorkTitle());
                params.add(apmRequest.getWriterName());

                SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(suppMatchTypeQuery.toString(), params);

                log.debug("NO GRP BY SUPPLIER - ActiveQtr: " + year + " Title: " + apmRequest.getWorkTitle()
                    + " Writer: " + apmRequest.getWriterName());

                if (rs.next()) {
                    // At lease one supplier is configured for writer matching
                    result = true;
                    log.debug(" At lease one supplier is configured for writer matching from the group: ");
                } else
                    log.debug(" No supplier is configured for writer matching from the group: ");

            }

        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);

        } finally {
            log.debug("Exiting writerMatchPermitted");
        }
        return result;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#writerMatchPermittedCatalog(apmRequest)
     */
    private boolean writerMatchPermittedCatalog(ApmPerformanceBulkRequest apmRequest) throws PrepSystemException {

        boolean result = false;
        StringBuilder suppMatchTypeQuery = null;
        log.debug("Entering writerMatchPermittedCatalog");

        try {
            // if grouped by supplier
            if (!ValidationUtils.isEmptyOrNull(apmRequest.getSupplierCode())) {
                suppMatchTypeQuery = new StringBuilder(UsageQueries.GET_APM_LRN_MCH_TYPE_GRPBY);
                SqlRowSet rs =
                    ascapJdbcTemplate.queryForRowSet(suppMatchTypeQuery.toString(), apmRequest.getSupplierCode());
                // supplier is configured for writer matching
                if (rs.next()) {
                    result = true;
                    log.debug("Supplier: " + apmRequest.getSupplierCode() + " is configured for writer matching.");
                } else
                    log.debug("Supplier: " + apmRequest.getSupplierCode() + " is NOT configured for writer matching.");

            }
            // if not grouped by supplier
            else {
                // get suppliers from apm_wrk_perf
                suppMatchTypeQuery = new StringBuilder(UsageQueries.GET_APM_LRN_MCH_TYPE_NOGRPBY_CATALOG);

                SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(suppMatchTypeQuery.toString(),
                    apmRequest.getWorkTitle(), apmRequest.getWriterName());
                log.debug("NO GRP BY SUPPLIER - Title: " + apmRequest.getWorkTitle() + " Writer: "
                    + apmRequest.getWriterName());
                if (rs.next()) {
                    // At lease one supplier is configured for writer matching
                    result = true;
                    log.debug(" At lease one supplier is configured for writer matching from the group: ");
                } else
                    log.debug(" No supplier is configured for writer matching from the group: ");
            }
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        } finally {
            log.debug("Exiting writerMatchPermittedCatalog");
        }

        return result;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#getMedleyWorkInformation(apmPerformanceBulkRequestList)
     */

    public ApmPerformanceBulkRequestList getMedleyWorkInformation(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering getMedleyWorkInformation in UsageDAOImpl "
            + apmPerformanceBulkRequestList.getApmPerformanceBulkRequestListType());

        ArrayList<String> wrkList = new ArrayList<>();
        String[] workIdArray = null;
        ArrayList<String> cloneCountList = new ArrayList<>();
        String[] cloneCountArray = null;
        String performerName = null;
        String supplierCode = null;
        String workTitle = null;
        String medleyOriginalExists = null;

        String tableName = "APM_WRK_PERF";
        if ("CATALOG".equals(apmPerformanceBulkRequestList.getApmPerformanceBulkRequestListType())) {
            tableName = "APM_CATALOG";
        }
        try {
            if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyMultiWorkId())) {
                int origCount = 1;

                SqlRowSet rsOrigCount = ascapJdbcTemplate.queryForRowSet("SELECT count(*) num_rows FROM " + tableName
                    + " WHERE DEL_FL = 'N' AND MULT_WRK_ID = ? AND CRE_ID <> 'MEDLEYADD'  and send_man_match = 'Y' and proc_status = 'BUSVLD'",
                    apmPerformanceBulkRequestList.getMedleyMultiWorkId());

                if (rsOrigCount.next()) {
                    origCount = rsOrigCount.getInt("num_rows");
                }
                String qry = " SELECT  WRK_ID_VLD, SUPP_CODE, WRK_TTL, PFR_NA, COUNT(1) NUM_ROWS  FROM " + tableName
                    + " WHERE MULT_WRK_ID = ? /*AND TGTSURVYEARQTR = ?*/  AND PROC_STATUS = 'BUSVLD' AND  SEND_MAN_MATCH = 'Y' AND LOCK_IND = 'N' AND DEL_FL = 'N' GROUP BY SUPP_CODE,  PFR_NA,   WRK_TTL,  WRK_ID, MAN_MATCH_USR_ID,DEL_FL,  MULT_WRK_ID ";
                SqlRowSet rs =
                    ascapJdbcTemplate.queryForRowSet(qry, apmPerformanceBulkRequestList.getMedleyMultiWorkId());
                while (rs.next()) {
                    if (!wrkList.contains(rs.getString("WRK_ID_VLD"))) {
                        wrkList.add(rs.getString("WRK_ID_VLD"));
                        cloneCountList.add("" + (rs.getInt("NUM_ROWS") / origCount));
                    }
                    supplierCode = rs.getString("SUPP_CODE");
                    performerName = rs.getString("PFR_NA");
                    workTitle = rs.getString("WRK_TTL");

                }
                workIdArray = wrkList.toArray(new String[wrkList.size()]);
                cloneCountArray = cloneCountList.toArray(new String[cloneCountList.size()]);
                String rsOrigQry = "SELECT " + tableName + "_ID FROM " + tableName
                    + " WHERE DEL_FL = 'N' AND MULT_WRK_ID = ? AND CRE_ID <> 'MEDLEYADD'  AND  SEND_MAN_MATCH = 'Y' ";

                SqlRowSet rsOrig =
                    ascapJdbcTemplate.queryForRowSet(rsOrigQry, apmPerformanceBulkRequestList.getMedleyMultiWorkId());
                if (rsOrig.next()) {
                    medleyOriginalExists = "Y";
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }

        apmPerformanceBulkRequestList.setMedleyWorkIds(workIdArray);
        apmPerformanceBulkRequestList.setMedleySupplierCode(supplierCode);
        apmPerformanceBulkRequestList.setMedleyPerformerName(performerName);
        apmPerformanceBulkRequestList.setMedleyWorkTitle(workTitle);
        apmPerformanceBulkRequestList.setMedleyWorkIds(workIdArray);
        apmPerformanceBulkRequestList.setMedleyCloneCounts(cloneCountArray);
        apmPerformanceBulkRequestList
            .setMedleyOriginalExists(medleyOriginalExists == null ? "N" : medleyOriginalExists);

        log.debug("Original Medley row exists " + medleyOriginalExists);

        log.debug("Exiting getMedleyWorkInformation in UsageDAOImpl");
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#cloneCatalogPerformances(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList cloneCatalogPerformances(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering cloneCatalogPerformances in UsageDAOImpl");
        if (apmPerformanceBulkRequestList == null) {
            return null;
        }
        log.debug("Operation Type " + apmPerformanceBulkRequestList.getOperationType());

        boolean fromUnmatch = false;

        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyMultiWorkId())) {

            if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getOperationType())
                && "MEDLEY_UPDATE".equals(apmPerformanceBulkRequestList.getOperationType())) {
                log.debug("Mult Work Id is not null forwarding to unmatch medeley "
                    + apmPerformanceBulkRequestList.getMedleyMultiWorkId());
                fromUnmatch = true;
                apmPerformanceBulkRequestList
                    .setApmPerformanceBulkRequestListType(UsageConstants.WRK_MATCH_TYPE_CATALOG);
                unmatchMedley(apmPerformanceBulkRequestList);
            } else {
                log.debug("Mult Work Id is not null forwarding to addToMedley "
                    + apmPerformanceBulkRequestList.getMedleyMultiWorkId());
                return addToMedleyCatalog(apmPerformanceBulkRequestList);
            }
        }
        String leanredMatchTableName = null;
        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWorkTitle())
            && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyPerformerName())) {

            leanredMatchTableName = "APM_LEARNED_MATCH";
        } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWorkTitle())
            && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWriterName())) {

            leanredMatchTableName = "APM_LEARNED_MATCH_WTR";
        } else {
            log.debug("Title and (Writer or Performer) is null...");
            return apmPerformanceBulkRequestList;
        }
        try {
            List<WorkPerformance> wpList =
                getOriginalCatalogPerformancesForMultiMatch(null, apmPerformanceBulkRequestList.getMedleySupplierCode(),
                    apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                    apmPerformanceBulkRequestList.getMedleyPerformerName(),
                    apmPerformanceBulkRequestList.getMedleyWriterName(), "MEDLEY_NEW");

            String[] medleyWrokIds = apmPerformanceBulkRequestList.getMedleyWorkIds();
            String[] medleyCloneCounts = apmPerformanceBulkRequestList.getMedleyCloneCounts();

            int numMedleyWorks = 0;
            if (medleyWrokIds != null) {
                log.debug("Medley Work ID Length: " + medleyWrokIds.length);
                numMedleyWorks = medleyWrokIds.length;
            } else {
                log.debug("Medley Work Id is null..");
            }

            String multiWorkId = apmPerformanceBulkRequestList.getMedleyMultiWorkId();
            if (ValidationUtils.isEmptyOrNull(multiWorkId) && numMedleyWorks > 1) {
                multiWorkId = this.getSequence("SELECT PAPM.MULT_WRK_ID.NEXTVAL FROM DUAL");
            } else if (fromUnmatch && numMedleyWorks <= 1) {
                multiWorkId = null;
            }

            List<WorkPerformance> newWPList = new ArrayList<>();

            for (WorkPerformance wp : wpList) {

                if (medleyWrokIds != null && medleyWrokIds.length > 0) {
                    int cloneCount = 1;
                    for (int i = 0; i < medleyWrokIds.length; i++) {
                        try {
                            cloneCount = Integer.parseInt(medleyCloneCounts[i]);
                        } catch (Exception e) {
                            log.error("Exception " + e.getMessage());
                            log.error("Resetting clone count to 1");
                            cloneCount = 1;
                        }
                        if (i == 0) {
                            String qryStr =
                                "UPDATE APM_CATALOG SET WRK_ID = ?, APM_MATCH_TYP = 'MAN', MAN_MATCH_USR_ID = ?, MULT_WRK_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE, MAN_MATCH_DT = SYSDATE WHERE APM_CATALOG_ID = ?";

                            ArrayList<Object> params = new ArrayList<>();
                            params.add(medleyWrokIds[0]);
                            params.add(apmPerformanceBulkRequestList.getUserId());
                            params.add(multiWorkId);
                            params.add(apmPerformanceBulkRequestList.getUserId());
                            params.add(wp.getWorkPerformanceId());
                            ascapJdbcTemplate.update(qryStr, params.toArray());
                            if (cloneCount > 1) {
                                for (int cCount = 1; cCount < cloneCount; cCount++) {
                                    WorkPerformance wpNew = new WorkPerformance();
                                    wpNew = copyWorkPerfVob(wpNew, wp);
                                    wpNew.setWorkId(medleyWrokIds[i]);
                                    wpNew.setUserId(apmPerformanceBulkRequestList.getUserId());
                                    newWPList.add(wpNew);
                                }
                            }
                        } else {
                            int updatedDeletedPerfCount = 0;
                            if (updatedDeletedPerfCount <= 0) {
                                WorkPerformance wpNew = new WorkPerformance();
                                wpNew = copyWorkPerfVob(wpNew, wp);
                                wpNew.setWorkId(medleyWrokIds[i]);
                                wpNew.setUserId(apmPerformanceBulkRequestList.getUserId());
                                newWPList.add(wpNew);
                            }
                            if (cloneCount > 1) {
                                for (int cCount = 1; cCount < cloneCount; cCount++) {
                                    WorkPerformance wpNew = new WorkPerformance();
                                    wpNew = copyWorkPerfVob(wpNew, wp);
                                    wpNew.setWorkId(medleyWrokIds[i]);
                                    wpNew.setUserId(apmPerformanceBulkRequestList.getUserId());
                                    newWPList.add(wpNew);
                                }
                            }
                        }
                    }
                }
                if (medleyWrokIds == null || medleyWrokIds.length == 0) {
                    if (wp != null && !ValidationUtils.isEmptyOrNull(wp.getWorkPerformanceId())) {
                        String qry =
                            "UPDATE APM_CATALOG SET MULT_WRK_ID = NULL, UPD_DT=SYSDATE, UPD_ID = ? WHERE APM_CATALOG_ID = ?";
                        log.debug("UPDATING ORIGINAL CATALOG PERF TO MULT_WRK_ID NULL BECAUSE THERE ARE NO WORK_IDs");
                        ascapJdbcTemplate.update(qry, apmPerformanceBulkRequestList.getUserId(),
                            wp.getWorkPerformanceId());
                    } else {
                        log.debug(
                            "Should not happen, either Original WP is null or it does not have a APM_WRK_PERF_ID ");
                    }
                }
            }
            this.addNewCatalogClonedWorkPerforance(newWPList, multiWorkId);
            if (medleyWrokIds != null && medleyWrokIds.length > 0) {
                for (int i = 0; i < medleyWrokIds.length; i++) {

                    // artist exists
                    if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyPerformerName()))
                        updateMedleyLeanredMatch(apmPerformanceBulkRequestList, multiWorkId,
                            apmPerformanceBulkRequestList.getMedleySupplierCode(),
                            apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                            apmPerformanceBulkRequestList.getMedleyPerformerName(),
                            apmPerformanceBulkRequestList.getMedleyWriterName(), medleyWrokIds[i],
                            medleyCloneCounts[i]);
                    // artist does not exists
                    else
                    // update LM only if writer match is permitted
                    if (writerMatchPermitted(apmPerformanceBulkRequestList)) {
                        updateMedleyLeanredMatch(apmPerformanceBulkRequestList, multiWorkId,
                            apmPerformanceBulkRequestList.getMedleySupplierCode(),
                            apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                            apmPerformanceBulkRequestList.getMedleyPerformerName(),
                            apmPerformanceBulkRequestList.getMedleyWriterName(), medleyWrokIds[i],
                            medleyCloneCounts[i]);
                    }
                }
            }
            if (!ValidationUtils.isEmptyOrNull(multiWorkId)) {
                String qry = null;
                if ("APM_LEARNED_MATCH".equals(leanredMatchTableName)) {
                    qry = UsageQueries.UNMATCH_MEDELY_LEANRED_MATCH_CATALOG;
                } else {
                    qry = UsageQueries.UNMATCH_MEDELY_LEANRED_MATCH_WTR_CATALOG;
                }
                int numDeleted =
                    ascapJdbcTemplate.update(qry, apmPerformanceBulkRequestList.getUserId(), multiWorkId, multiWorkId);
                log.debug("Number of Leanred matches deleted: " + numDeleted);
            }
            if (medleyWrokIds.length == 1) {
                ApmPerformanceBulkRequest apmRequest = new ApmPerformanceBulkRequest();
                apmRequest.setWorkId(apmPerformanceBulkRequestList.getMedleyWorkIds()[0]);
                apmRequest.setSupplierCode(apmPerformanceBulkRequestList.getMedleySupplierCode());
                apmRequest.setPerformerName(apmPerformanceBulkRequestList.getMedleyPerformerName());
                apmRequest.setWorkTitle(apmPerformanceBulkRequestList.getMedleyWorkTitle());
                apmRequest.setWriterName(apmPerformanceBulkRequestList.getMedleyWriterName());
                apmRequest.setRequestTypeCode("UPD");
                apmRequest.setUserId(apmPerformanceBulkRequestList.getUserId());
                this.addApmPerfBulkRequest(apmRequest);
            }
        } catch (SQLException se) {
            log.error(se.getMessage());
            log.debug(this.retrieveExceptions(se));
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        } finally {
            log.debug("Exiting cloneCatalogPerformances in UsageDAOImpl");
        }
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#unmatchMedley(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList unmatchMedley(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList)
        throws PrepSystemException {
        log.debug("Entering unmatchMedley in UsageDAOImpl");

        if (apmPerformanceBulkRequestList == null) {
            return null;
        } else {
            log.debug("Input OBJ:  " + apmPerformanceBulkRequestList);
            log.debug("Medely supp code for unmatch: " + apmPerformanceBulkRequestList.getMedleySupplierCode());
            log.debug("Request Type (WRK_PERF or CATALOG): "
                + apmPerformanceBulkRequestList.getApmPerformanceBulkRequestListType());
        }
        String tableName = "APM_WRK_PERF";
        if (UsageConstants.WRK_MATCH_TYPE_CATALOG
            .equals(apmPerformanceBulkRequestList.getApmPerformanceBulkRequestListType())) {
            log.debug("Catalog Unmatch");
            tableName = "APM_CATALOG";
        }
        try {

            String qry = " UPDATE " + tableName
                + " SET DEL_FL = 'Y', UPD_ID = ?, UPD_DT = SYSDATE, DEL_RSN_CDE = 'MULT_DEL' WHERE MULT_WRK_ID = ? AND CRE_ID = 'MEDLEYADD' AND DEL_FL = 'N' ";

            ascapJdbcTemplate.update(qry, apmPerformanceBulkRequestList.getUserId(),
                apmPerformanceBulkRequestList.getMedleyMultiWorkId());

            String qry1 = " UPDATE " + tableName
                + " SET WRK_ID = NULL , UPD_ID = ?, APM_MATCH_TYP = 'NMT', DEL_RSN_CDE = NULL, MAN_MATCH_USR_ID = NULL, MAN_MATCH_DT = NULL, APM_MATCH_DT = NULL, UPD_DT = SYSDATE WHERE MULT_WRK_ID = ? AND CRE_ID <> 'MEDLEYADD' AND DEL_FL = 'N' ";

            ascapJdbcTemplate.update(qry1, apmPerformanceBulkRequestList.getUserId(),
                apmPerformanceBulkRequestList.getMedleyMultiWorkId());

            String qry3 =
                " UPDATE APM_LEARNED_MATCH SET DEL_FL = 'Y', UPD_ID = ? , UPD_DT = SYSDATE WHERE MULT_WRK_ID = ? ";

            ascapJdbcTemplate.update(qry3, apmPerformanceBulkRequestList.getUserId(),
                apmPerformanceBulkRequestList.getMedleyMultiWorkId());

            String qry4 =
                " UPDATE APM_LEARNED_MATCH_WTR SET DEL_FL = 'Y', UPD_ID = ? , UPD_DT = SYSDATE WHERE MULT_WRK_ID = ? ";

            ascapJdbcTemplate.update(qry4, apmPerformanceBulkRequestList.getUserId(),
                apmPerformanceBulkRequestList.getMedleyMultiWorkId());

            if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyMultiWorkId())) {
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        } finally {
            log.debug("Exiting unmatchMedley in UsageDAOImpl");
        }
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#addToMedleyCatalog(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList addToMedleyCatalog(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList)
        throws PrepSystemException {
        log.debug("Entering addToMedleyCatalog in UsageDAOImpl");
        if (apmPerformanceBulkRequestList == null) {
            return null;
        } else {
            log.debug("Input OBJ:  " + apmPerformanceBulkRequestList);
        }
        String leanredMatchTableName = null;
        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWorkTitle())
            && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyPerformerName())) {
            leanredMatchTableName = "APM_LEARNED_MATCH";
        } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWorkTitle())
            && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWriterName())) {
            leanredMatchTableName = "APM_LEARNED_MATCH_WTR";
        } else {
            log.debug("Title and (Writer or Performer) is null...");
            return apmPerformanceBulkRequestList;
        }
        try {
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_MEDLEY_WORKIDS_CATALOG,
                apmPerformanceBulkRequestList.getMedleyMultiWorkId());
            List<String> existingMedleyWorkIds = new ArrayList<>();
            List<WorkPerformance> newWPList = new ArrayList<>();
            while (rs.next()) {
                existingMedleyWorkIds.add(rs.getString("WRK_ID_VLD"));
            }
            List<WorkPerformance> wpList =
                getOriginalCatalogPerformancesForMultiMatch(apmPerformanceBulkRequestList.getMedleyMultiWorkId(),
                    apmPerformanceBulkRequestList.getMedleySupplierCode(),
                    apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                    apmPerformanceBulkRequestList.getMedleyPerformerName(),
                    apmPerformanceBulkRequestList.getMedleyWriterName(), "MEDLEY_ADD");
            String[] medleyWrokIds = apmPerformanceBulkRequestList.getMedleyWorkIds();
            String[] medleyCloneCounts = apmPerformanceBulkRequestList.getMedleyCloneCounts();
            if (medleyWrokIds != null) {
                log.debug("Medley WORKD LENGTH:  " + medleyWrokIds.length);
            } else {
                log.debug("Meley WORKD LENGTH is null");
            }
            String multiWorkId = apmPerformanceBulkRequestList.getMedleyMultiWorkId();
            for (WorkPerformance wp : wpList) {
                if (medleyWrokIds != null && medleyWrokIds.length > 0) {
                    for (int i = 0; i < medleyWrokIds.length; i++) {
                        if (existingMedleyWorkIds.contains(medleyWrokIds[i])) {
                        } else {
                            int cloneCount = 1;
                            WorkPerformance wpNew = new WorkPerformance();
                            wpNew = copyWorkPerfVob(wpNew, wp);
                            wpNew.setWorkId(medleyWrokIds[i]);
                            wpNew.setUserId(apmPerformanceBulkRequestList.getUserId());
                            try {
                                cloneCount = Integer.parseInt(medleyCloneCounts[i]);
                            } catch (Exception e) {
                                log.error("Exception " + e.getMessage());
                                log.error("Resetting clone count to 1");
                                cloneCount = 1;
                            }
                            for (int x = 0; x < cloneCount; x++) {
                                newWPList.add(wpNew);
                            }
                        }
                    }
                }
            }
            this.addNewCatalogClonedWorkPerforance(newWPList, multiWorkId);
            if (medleyWrokIds != null && medleyWrokIds.length > 0) {
                for (int i = 0; i < medleyWrokIds.length; i++) {
                    if (!existingMedleyWorkIds.contains(medleyWrokIds[i])) {
                        // artist exists
                        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyPerformerName()))
                            updateMedleyLeanredMatch(apmPerformanceBulkRequestList, multiWorkId,
                                apmPerformanceBulkRequestList.getMedleySupplierCode(),
                                apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                                apmPerformanceBulkRequestList.getMedleyPerformerName(),
                                apmPerformanceBulkRequestList.getMedleyWriterName(), medleyWrokIds[i],
                                medleyCloneCounts[i]);
                        // artist does not exists
                        else
                        // update LM only if writer match is permitted
                        if (writerMatchPermitted(apmPerformanceBulkRequestList)) {
                            updateMedleyLeanredMatch(apmPerformanceBulkRequestList, multiWorkId,
                                apmPerformanceBulkRequestList.getMedleySupplierCode(),
                                apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                                apmPerformanceBulkRequestList.getMedleyPerformerName(),
                                apmPerformanceBulkRequestList.getMedleyWriterName(), medleyWrokIds[i],
                                medleyCloneCounts[i]);
                        }
                    }
                }
            }
            if (!ValidationUtils.isEmptyOrNull(multiWorkId)) {
                String qry = null;
                if ("APM_LEARNED_MATCH".equals(leanredMatchTableName)) {
                    qry = UsageQueries.UNMATCH_MEDELY_LEANRED_MATCH_CATALOG;
                } else {
                    qry = UsageQueries.UNMATCH_MEDELY_LEANRED_MATCH_WTR_CATALOG;
                }
                int numDeleted =
                    ascapJdbcTemplate.update(qry, apmPerformanceBulkRequestList.getUserId(), multiWorkId, multiWorkId);
                log.debug("Number of Leanred matches deleted " + numDeleted);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        } finally {
            log.debug("Exiting addToMedleyCatalog in UsageDAOImpl");
        }
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#getOriginalCatalogPerformancesForMultiMatch(multiWorkId,suppCode,workTitle,performerName,writerName,medleyMode)
     */
    private List<WorkPerformance> getOriginalCatalogPerformancesForMultiMatch(String multiWorkId, String suppCode,
        String workTitle, String performerName, String writerName, String medleyMode) throws PrepSystemException {
        log.debug(
            "Entering private getOriginalCatalogPerformancesForMultiMatch in UsageDAOImpl , medleyMode: " + medleyMode);

        WorkPerformance wp = null;
        List<WorkPerformance> list = new ArrayList<>();
        StringBuilder qry = null;
        ArrayList<String> params = null;

        if ("MEDLEY_NEW".equals(medleyMode)) {
            qry = new StringBuilder();
            params = new ArrayList<>();
            qry.append(UsageQueries.MEDLEY_GROUP_PERFS_ORIGINAL_COLUMNS_CATALOG);
            qry.append(UsageQueries.MEDLEY_GROUP_PERFS_START_CATALOG);
            if (!ValidationUtils.isEmptyOrNull(suppCode)) {
                qry.append(" AND A.SUPP_CODE = ? ");
                params.add(suppCode);
            }
            if (!ValidationUtils.isEmptyOrNull(workTitle)) {
                qry.append(" AND A.WRK_TTL = ? ");
                params.add(workTitle);
            } else {
                qry.append(" AND A.WRK_TTL is null ");
            }
            if (!ValidationUtils.isEmptyOrNull(performerName)) {
                qry.append(" AND A.PFR_NA = ? ");
                params.add(performerName);
            } else {
                qry.append(" AND A.PFR_NA is null ");
                if (!ValidationUtils.isEmptyOrNull(writerName)) {
                    qry.append(" AND A.WRITER = ? ");
                    params.add(writerName);
                } else {
                    qry.append(" AND A.WRITER IS NULL ");
                }
            }
            qry.append(UsageQueries.MEDLEY_GROUP_PERFS_END_CATALOG);
        } else if ("MEDLEY_ADD".equals(medleyMode)) {
            qry = new StringBuilder();
            params = new ArrayList<>();
            qry.append(UsageQueries.MEDLEY_GROUP_PERFS_ORIGINAL_COLUMNS_CATALOG);
            qry.append(UsageQueries.MEDLEY_GROUP_ORIGINAL_PERFS_START_CATALOG);
            params.add(multiWorkId);
        }
        try {
            int paramCount1 = params.size();
            String paramVal = null;
            ArrayList<Object> parameter = new ArrayList<>();
            for (int i = 0; i < paramCount1; i++) {
                paramVal = params.get(i);
                parameter.add(paramVal);
            }
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(qry.toString(), parameter.toArray());
            while (rs.next()) {
                wp = new WorkPerformance();
                wp.setWorkPerformanceId(rs.getString("APM_CATALOG_ID"));
                wp.setPlayCount(rs.getString("PLAY_CNT"));
                wp.setPerformerName(rs.getString("PFR_NA"));
                wp.setWorkTitle(rs.getString("WRK_TTL"));
                wp.setWorkId(rs.getString("WRK_ID"));
                wp.setProcStatus(rs.getString("PROC_STATUS"));
                wp.setProviderId(rs.getString("PROVIDER_ID"));
                wp.setErrorFlag(rs.getString("ERR_STATUS"));
                wp.setApmLockIndicator(rs.getString("LOCK_IND"));
                wp.setSupplierCode(rs.getString("SUPP_CODE"));
                wp.setApmSendToManMatchIndicator(rs.getString("SEND_MAN_MATCH"));
                wp.setApmStatusDate(rs.getString("STATUS_DATE"));
                wp.setPerformancePeriod(rs.getString("PERF_PERIOD"));
                wp.setPerformanceQuarter(rs.getString("PERF_QTR"));
                wp.setPriority(rs.getString("PRIORITY"));
                wp.setWriter(rs.getString("WRITER"));
                wp.setAssignedToUser(rs.getString("ASG_USR_ID"));
                list.add(wp);
                log.debug("WorkPerformance " + wp);
            }
            if (list != null) {
                log.debug("Original Performances size: " + list.size());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        } finally {
            log.debug("Exiting private getOriginalCatalogPerformancesForMultiMatch");
        }
        return list;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#copyWorkPerfVob(wpNew,wp)
     */
    private WorkPerformance copyWorkPerfVob(WorkPerformance wpNew, WorkPerformance wp) {

        if (wpNew == null) {
            return wpNew;
        }
        if (wp == null) {
            return wpNew;
        }
        wpNew.setPerformanceHeaderId(wp.getPerformanceHeaderId());
        wpNew.setPlayCount(wp.getPlayCount());
        wpNew.setPerformerName(wp.getPerformerName());
        wpNew.setWorkTitle(wp.getWorkTitle());
        wpNew.setUseTypeCode(wp.getUseTypeCode());
        wpNew.setProcStatus(wp.getProcStatus());
        wpNew.setProviderId(wp.getProviderId());
        wpNew.setErrorFlag(wp.getErrorFlag());
        wpNew.setApmLockIndicator(wp.getApmLockIndicator());
        wpNew.setInstrumentalOrVocalIndicator(wp.getInstrumentalOrVocalIndicator());
        wpNew.setSupplierCode(wp.getSupplierCode());
        wpNew.setApmSendToManMatchIndicator(wp.getApmSendToManMatchIndicator());
        wpNew.setApmEstimatedDollarVal(wp.getApmEstimatedDollarVal());
        wpNew.setApmEstimatedDollarFound(wp.getApmEstimatedDollarFound());
        wpNew.setTargetYearQuarter(wp.getTargetYearQuarter());
        wpNew.setApmSourceSongKey(wp.getApmSourceSongKey());
        wpNew.setApmWorkEffectiveEndDate(wp.getApmWorkEffectiveEndDate());
        wpNew.setApmWorkEffectiveStartDate(wp.getApmWorkEffectiveStartDate());
        wpNew.setWorkPerformanceDuration(wp.getWorkPerformanceDuration());
        wpNew.setApmAlbum(wp.getApmAlbum());
        wpNew.setApmBatchId(wp.getApmBatchId());
        wpNew.setApmCatalogNumber(wp.getApmCatalogNumber());
        wpNew.setApmCatalogYear(wp.getApmCatalogYear());
        wpNew.setApmChannel(wp.getApmChannel());
        wpNew.setApmClassical(wp.getApmClassical());
        wpNew.setApmCompany(wp.getApmCompany());
        wpNew.setApmIsrc(wp.getApmIsrc());
        wpNew.setApmLabel(wp.getApmLabel());
        wpNew.setApmPerfDate(wp.getApmPerfDate());
        wpNew.setApmPerfTime(wp.getApmPerfTime());
        wpNew.setApmStation(wp.getApmStation());
        wpNew.setApmStatusDate(wp.getApmStatusDate());
        wpNew.setApmSupplierId(wp.getApmSupplierId());
        wpNew.setDetectionTime(wp.getDetectionTime());
        wpNew.setLibraryName(wp.getLibraryName());
        wpNew.setLibraryDiscTitle(wp.getLibraryDiscTitle());
        wpNew.setLibraryDiscId(wp.getLibraryDiscId());
        wpNew.setLibraryTrack(wp.getLibraryTrack());
        wpNew.setLibraryTrackId(wp.getLibraryTrackId());
        wpNew.setWriter(wp.getWriter());
        wpNew.setConfidenceLevel(wp.getConfidenceLevel());
        wpNew.setAssignedToUser(wp.getAssignedToUser());
        wpNew.setPerformancePeriod(wp.getPerformancePeriod());
        wpNew.setPerformanceQuarter(wp.getPerformanceQuarter());
        wpNew.setPriority(wp.getPriority());
        return wpNew;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#copyWorkPerfVob(wpNew,wp)
     */
    private void addNewCatalogClonedWorkPerforance(List<WorkPerformance> wpList, String multiWorkId)
        throws PrepSystemException {
        log.debug("Entering private addNewCatalogClonedWorkPerforance with List in UsageDAOImpl");

        if (wpList == null || wpList.isEmpty()) {
            return;
        }
        try {
            String qryStr = " INSERT INTO PAPM.APM_CATALOG ( " + "   APM_ARCHIVE_ID, APM_CATALOG_ID, APM_MATCH_DT,  "
                + "   APM_MATCH_TYP, ASG_USR_ID, CRE_DT,  " + "   CRE_ID, DEL_FL,  " + "   ERR_STATUS, LOCK_IND,  "
                + "   MAN_MATCH_DT, MAN_MATCH_USR_ID, MULT_WRK_ID,  " + "   PERF_PERIOD, PERF_QTR, PFR_NA,  "
                + "   PLAY_CNT, PRIORITY, PROC_STATUS,  " + "   PROVIDER_ID, SEND_MAN_MATCH, STATUS_DATE,  "
                + "   SUPP_CODE,  " + "   WRITER, WRK_ID,   " + "   WRK_TTL) values ( "
                + " 0, PAPM.APM_CATALOG_ID.NEXTVAL, SYSDATE, " + // APM_ARCHIVE_ID, APM_CATALOG_ID, APM_MATCH_DT,
                " 'MAN', ?, SYSDATE, " + // APM_MATCH_TYP, ASG_USR_ID, CRE_DT,
                " ?, 'N', " + // CRE_ID, DEL_FL, DEL_RSN_CDE,
                " ?, ?, " + // ERR_STATUS, LOCK_IND,
                " SYSDATE, ?, ?, " + // MAN_MATCH_DT, MAN_MATCH_USR_ID, MULT_WRK_ID,
                " ?, ?, ?, " + // PERF_PERIOD, PERF_QTR, PFR_NA,
                " ?, ?, ?, " + // PLAY_CNT, PRIORITY, PROC_STATUS,
                " ?, ?, TO_DATE (?, 'MM/DD/YYYY HH24:MI:SS'), " + // PROVIDER_ID, SEND_MAN_MATCH, STATUS_DATE,
                " ?, " + // SUPP_CODE, UPD_DT, UPD_ID,
                " ?, ?, " + // WRITER, WRK_ID
                " ? ) "; // WRK_TTL

            log.debug("Executing Insert Work Perf query in cloneCatalogPerformances method " + qryStr);

            int insertedWps = 0;
            for (WorkPerformance wp : wpList) {
                log.debug("workId " + wp);
                ArrayList<Object> params = new ArrayList<>();
                params.add(wp.getAssignedToUser()); // assigned user
                params.add("MEDLEYADD"); // create id
                params.add(wp.getErrorFlag()); // error status
                params.add(wp.getApmLockIndicator()); // lock indicator
                params.add(wp.getUserId()); // man match user
                params.add(multiWorkId);
                params.add(wp.getPerformancePeriod()); // perf period
                params.add(wp.getPerformanceQuarter()); // perf quarter
                params.add(wp.getPerformerName());
                params.add(wp.getPlayCount()); // play count
                params.add(wp.getPriority()); // priiority
                params.add(wp.getProcStatus()); // proc sstatus
                params.add(wp.getProviderId()); // privider id
                params.add(wp.getApmSendToManMatchIndicator()); // send man match
                params.add(wp.getApmStatusDate()); // status date
                params.add(wp.getSupplierCode()); // SUPP CODE
                params.add(wp.getWriter()); // work id
                params.add(wp.getWorkId()); // work id
                params.add(wp.getWorkTitle()); // work title
                ascapJdbcTemplate.update(qryStr, params.toArray());
                insertedWps++;
            }
            log.debug("Inserted catalog Perfs " + insertedWps);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        } finally {
            log.debug("Exiting private addNewCatalogClonedWorkPerforance in UsageDAO");
        }
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#updateMedleyLeanredMatch(apmPerformanceBulkRequestList,multiWorkId)
     */
    private ApmPerformanceBulkRequestList updateMedleyLeanredMatch(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList, String multiWorkId, String supplierCode,
        String workTitle, String performerName, String writerName, String workId, String cloneCount)
        throws PrepSystemException {
        log.debug("Entering updateMedleyLeanredMatch in UsageDAO");

        boolean leanredMatchExists = false;
        String tableName = null;
        String fieldName = null;
        String fieldValue = null;
        if (!ValidationUtils.isEmptyOrNull(workTitle) && !ValidationUtils.isEmptyOrNull(performerName)) {
            tableName = " APM_LEARNED_MATCH ";
            fieldName = " PFR_NA ";
            fieldValue = performerName;
        } else if (!ValidationUtils.isEmptyOrNull(workTitle) && !ValidationUtils.isEmptyOrNull(writerName)) {
            tableName = " APM_LEARNED_MATCH_WTR ";
            fieldName = " WRITER ";
            fieldValue = writerName;
        } else {
            log.debug("Writer or Performer is null... returning");
            return apmPerformanceBulkRequestList;
        }
        String qry = "UPDATE " + tableName
            + " SET DEL_FL = 'N', LRN_DEL = 'N', UPD_ID = ?, UPD_DT=SYSDATE,  MULT_WRK_ID = ?, CLONE_CNT=?  WHERE "
            + fieldName + " = ? AND WRK_TTL = ? AND WRK_ID = ? ";

        try {
            if (!ValidationUtils.isEmptyOrNull(supplierCode)) {
                qry += " AND SUPP_CODE = ? ";
            } else {
                qry += " AND SUPP_CODE IS NULL ";
            }
            ArrayList<Object> params = new ArrayList<>();
            params.add(apmPerformanceBulkRequestList.getUserId());
            params.add(multiWorkId);
            params.add(cloneCount);
            params.add(fieldValue);
            params.add(workTitle);
            params.add(workId);
            if (!ValidationUtils.isEmptyOrNull(supplierCode)) {
                params.add(supplierCode);
            }
            int updatedCount = ascapJdbcTemplate.update(qry, params.toArray());
            if (updatedCount > 0) {
                leanredMatchExists = true;
            }
            if (!leanredMatchExists) {
                qry = " INSERT INTO " + tableName + " (SUPP_CODE, " + fieldName
                    + ", WRK_TTL, WRK_ID, CRE_ID, MULT_WRK_ID, LRN_DEL, APM_MATCH_TYP, DEL_FL, CRE_DT, CLONE_CNT) VALUES (?,?,?,?,?,?,'N','MAN','N',SYSDATE,?) ";
                ArrayList<Object> params1 = new ArrayList<>();
                params1.add(supplierCode);
                params1.add(fieldValue);
                params1.add(workTitle);
                params1.add(workId);
                params1.add(apmPerformanceBulkRequestList.getUserId());
                params1.add(multiWorkId);
                params1.add(cloneCount);
                ascapJdbcTemplate.update(qry, params1.toArray());
            }
        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        } finally {
            log.debug("Exiting updateMedleyLeanredMatch in UsageDAOImpl");
        }
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#writerMatchPermitted(apmPerformanceBulkRequestList)
     */
    private boolean writerMatchPermitted(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList)
        throws PrepSystemException {
        boolean result = false;
        StringBuilder suppMatchTypeQuery = null;
        log.debug("Entering Medley writerMatchPermitted");
        try {
            // if grouped by supplier
            if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleySupplierCode())) {
                suppMatchTypeQuery = new StringBuilder(UsageQueries.GET_APM_LRN_MCH_TYPE_GRPBY);
                SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(suppMatchTypeQuery.toString(),
                    apmPerformanceBulkRequestList.getMedleySupplierCode());
                // supplier is configured for writer matching
                if (rs.next()) {
                    result = true;
                    log.debug("Supplier: " + apmPerformanceBulkRequestList.getMedleySupplierCode()
                        + " is configured for writer matching.");
                } else
                    log.debug("Supplier: " + apmPerformanceBulkRequestList.getMedleySupplierCode()
                        + " is NOT configured for writer matching.");
            }
            // if not grouped by supplier
            else {
                ArrayList<Object> params = null;
                // get suppliers from apm_wrk_perf
                if ("CATALOG".equals(apmPerformanceBulkRequestList.getApmPerformanceBulkRequestListType())) {
                    suppMatchTypeQuery = new StringBuilder(UsageQueries.GET_APM_LRN_MCH_TYPE_NOGRPBY_CATALOG);
                    params = new ArrayList<>();
                    params.add(apmPerformanceBulkRequestList.getMedleyWorkTitle());
                    params.add(apmPerformanceBulkRequestList.getMedleyWriterName());
                } else {
                    suppMatchTypeQuery = new StringBuilder(UsageQueries.GET_APM_LRN_MCH_TYPE_NOGRPBY);
                    params = new ArrayList<>();
                    params.add(getActiveSurveyYearQuarter());
                    params.add(apmPerformanceBulkRequestList.getMedleyWorkTitle());
                    params.add(apmPerformanceBulkRequestList.getMedleyWriterName());
                }
                SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(suppMatchTypeQuery.toString(), params);
                log.debug("NO GRP BY SUPPLIER - " + " Title: " + apmPerformanceBulkRequestList.getMedleyWorkTitle()
                    + " Writer: " + apmPerformanceBulkRequestList.getMedleyWriterName());
                if (rs.next()) {
                    // At lease one supplier is configured for writer matching
                    result = true;
                    log.debug(" At lease one supplier is configured for writer matching from the group: ");
                } else
                    log.debug(" No supplier is configured for writer matching from the group: ");
            }

        } finally {
            log.debug("Exiting Medley writerMatchPermitted");
        }
        return result;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#addApmPerfBulkRequest(apmPerformanceBulkRequest)
     */
    private ApmPerformanceBulkRequest addApmPerfBulkRequest(ApmPerformanceBulkRequest apmPerformanceBulkRequest)
        throws PrepSystemException, SQLException {
        log.debug("Entering private addApmPerfBulkRequest in UsageDAO");
        StringBuilder sqlStr = null;
        sqlStr = new StringBuilder(UsageQueries.INSERT_APM_PERF_BLK_REQ_DUMMY);

        try {
            String apmPerfBulkRequestId = null;
            String apmPerfBulkRequestGroupId = null;
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_SEQUENCE_APM_PERF_BLK_REQ_GRP_ID);
            if (rs.next()) {
                apmPerfBulkRequestGroupId = rs.getString(1);
            }
            rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_SEQUENCE_APM_PERF_BLK_REQ_ID);
            if (rs.next()) {
                apmPerfBulkRequestId = rs.getString(1);
            }
            ArrayList<Object> params = new ArrayList<>();
            params.add(apmPerfBulkRequestId);
            params.add(apmPerfBulkRequestGroupId);
            params.add(apmPerformanceBulkRequest.getSupplierCode());
            params.add(apmPerformanceBulkRequest.getPerformerName());
            params.add(apmPerformanceBulkRequest.getWorkTitle());
            params.add(apmPerformanceBulkRequest.getWorkId());
            params.add(apmPerformanceBulkRequest.getWorkPerfCount());
            params.add(apmPerformanceBulkRequest.getPlayCount());
            params.add("CO");
            params.add(apmPerformanceBulkRequest.getUserId());
            params.add(apmPerformanceBulkRequest.getRequestTypeCode());
            params.add(apmPerformanceBulkRequest.getUserId());
            params.add(apmPerformanceBulkRequest.getWriterName());
            int updateCounts = ascapJdbcTemplate.update(sqlStr.toString(), params);
            log.debug("Inserted int apm_perf_blk_req " + updateCounts);
        } finally {
            log.debug("Exiting private addApmPerfBulkRequest in UsageDAO");
        }
        return apmPerformanceBulkRequest;
    }

    public String getSumRollupCnt(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getSumRollupCnt method ");

        String result = "";
        SqlRowSet rs = null;
        StringBuilder query = new StringBuilder(UsageQueries.GET_ROLLUP_SUM_CNT_PART1);

        List<?> fileList = eoFileList.getSearchResults();
        Iterator<?> itr = fileList.iterator();

        // parameter string
        String parameters = "";
        while (itr.hasNext()) {
            parameters = parameters + "?,";
        }
        parameters = parameters.substring(0, parameters.length() - 1);

        query.append(parameters).append(UsageQueries.GET_ROLLUP_SUM_CNT_PART2);

        int parameterId = 0;
        try {

            Object[] params = new Object[fileList.size()];
            Iterator itr1 = fileList.iterator();
            EOFile eo1 = null;
            while (itr1.hasNext()) {
                parameterId++;
                eo1 = (EOFile) itr1.next();
                params[parameterId] = eo1.getSuppCode();
            }

            rs = ascapJdbcTemplate.queryForRowSet(query.toString(), params);

            if (rs != null) {
                while (rs.next()) {
                    result = rs.getString("ROLLUPCNT");
                }
            }
        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }

        log.debug("Exiting  UsageDAO - getSumRollupCnt method ");

        return (result);
    }

    public EOFile getEOBatchControl(EOFile eoFile) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getEOBatchControl method ");

        if (eoFile == null || ValidationUtils.isEmptyOrNull(eoFile.getSuppCode())
            || ValidationUtils.isEmptyOrNull(eoFile.getPerfQuarter())) {
            log.debug("EOFile is empty.... Returning");
            return eoFile;
        }

        try {

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_BATCH_CONTROL, eoFile.getSuppCode(),
                eoFile.getPerfQuarter());

            if (rs.next()) {
                eoFile.setBatchId(rs.getString("BatchId"));
            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting  UsageDAO - getEOBatchControl method ");
        return eoFile;
    }

    public String validationErrorExists(String suppcode, String perfqtr) throws PrepSystemException {
        log.debug("Entering  UsageDAO - validationErrorExists method ");

        String result = "";

        try {

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.VALIDATION_ERROR_EXISTS, suppcode, perfqtr);

            if (rs != null) {

                while (rs.next()) {
                    result = rs.getString("ValidationError");
                    log.debug("Validation error exists " + result);
                }
            }
        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }

        log.debug("Exiting  UsageDAO - validationErrorExists method ");

        return (result);
    }

    public String getSupplierType(String suppcode) throws PrepSystemException {
        log.debug("Entering  UsageDAO - isCatalogSupplier method ");

        String result = "";

        try {

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_SUPP_TYPE, suppcode);

            if (rs != null) {

                while (rs.next()) {
                    result = rs.getString("SupplierType");
                    log.debug("SupplierType " + result);
                }
            }
        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }

        log.debug("Exiting  UsageDAO - isCatalogSupplier method ");

        return (result);
    }

    public List<EOSupplierFormat> getApmSuppliers() throws PrepSystemException {

        log.debug("Entering  UsageDAO - getApmSuppliers");

        EOSupplierFormat eoSupplierFormat = null;

        List<EOSupplierFormat> col = new ArrayList<>();

        try {

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_APM_SUPPLIERS);

            while (rs.next()) {
                eoSupplierFormat = new EOSupplierFormat();
                eoSupplierFormat.setApmSuppCode(rs.getString("SUPP_CODE"));
                col.add(eoSupplierFormat);
            }

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getApmSuppliers");

        return col;

    }

    public ApmActiveSurveyQuarter getApmActiveSurveyQuarter() throws PrepSystemException {

        log.debug("Entering  UsageDAO - getApmActiveSurveyQuarter");

        ApmActiveSurveyQuarter apmActiveSurveyQuarter = null;

        try {

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_ACTIVE_SUVERY_YEAR_QTR);
            while (rs.next()) {
                apmActiveSurveyQuarter = new ApmActiveSurveyQuarter();
                apmActiveSurveyQuarter.setStartDate(rs.getString("STT_DT"));
                apmActiveSurveyQuarter.setEndDate(rs.getString("END_DT"));
                apmActiveSurveyQuarter.setActiveSurveyQuarter(rs.getString("TGTSURVYEARQTR"));
                apmActiveSurveyQuarter.setActiveFlag(rs.getString("ACTIVE_FL"));
            }

            rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_ACTIVE_CENSUS_SUPPLIERS,
                new Object[] {apmActiveSurveyQuarter.getActiveSurveyQuarter()});

            ArrayList<EOCallLetterConfig> censusSuppliers = new ArrayList<>();
            EOCallLetterConfig eoCallLetterConfig = null;
            while (rs.next()) {
                eoCallLetterConfig = new EOCallLetterConfig();
                eoCallLetterConfig.setCallLetterFull(rs.getString("MUS_USER"));
                eoCallLetterConfig.setSamplingCompletedFlag(rs.getString("SAMP_COMP_FL"));
                censusSuppliers.add(eoCallLetterConfig);
            }
            apmActiveSurveyQuarter.setCensusSuppliers(censusSuppliers);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getApmActiveSurveyQuarter");

        return apmActiveSurveyQuarter;
    }

    public EOFileList getEOFileList(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getEOFileList method ");
        if (eoFileList != null) {
            log.debug("ActiveSurveyQuarterApm " + eoFileList.getActiveSurveyQuarterApm());
            log.debug("ActiveSurveyQuarterEo " + eoFileList.getActiveSurveyQuarterEo());
            log.debug("CurrentPerformanceQuarterEO " + eoFileList.getCurrentPerformanceQuarterEO());
        }

        StringBuilder sbFileInv = new StringBuilder(UsageQueries.GET_EO_FILE_SUMMARY_FILE_INV);
        StringBuilder sbBatchCntrlSuccessCnt =
            new StringBuilder(UsageQueries.GET_EO_FILE_SUMMARY_BATCH_CNTRL_SUCCESS_COUNT);
        StringBuilder sbBatchCntrl = new StringBuilder(UsageQueries.GET_EO_FILE_SUMMARY_BATCH_CNTRL);
        StringBuilder sbUAChannel = new StringBuilder(UsageQueries.GET_EO_FILE_UA_CHANNEL_COUNT);
        StringBuilder sbRollupReviewFiles = new StringBuilder(UsageQueries.GET_EO_FILE_LOAD_TO_APM_PEND);
        StringBuilder sbLoadToApm = new StringBuilder(UsageQueries.GET_EO_FILE_LOAD_TO_APM_PROG);

        try {

            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(sbFileInv.toString(), eoFileList.getStartDate(),
                eoFileList.getEndDate());

            int newFiles = 0;
            int errorFiles = 0;
            int uaChannelFiles = 0;
            int eoLoadFiles = 0;
            while (rs.next()) {

                if ("CR".equals(rs.getString("Status"))) {
                    eoLoadFiles += Integer.parseInt(rs.getString("FileCount"));
                } else if ("LR".equals(rs.getString("Status"))) {
                    eoLoadFiles += Integer.parseInt(rs.getString("FileCount"));
                } else if ("LD".equals(rs.getString("Status")) && "N".equals(rs.getString("LearnedMatch"))) {
                    eoLoadFiles += Integer.parseInt(rs.getString("FileCount"));
                } else if ("CA".equals(rs.getString("Status"))) {
                    errorFiles += Integer.parseInt(rs.getString("FileCount"));
                } else if ("LA".equals(rs.getString("Status"))) {
                    errorFiles += Integer.parseInt(rs.getString("FileCount"));
                } else if ("SA".equals(rs.getString("Status"))) {
                    errorFiles += Integer.parseInt(rs.getString("FileCount"));
                } else if ("NA".equals(rs.getString("Status"))) {
                    eoFileList.setNoProcessFiles(rs.getString("NoProcess_Count"));
                } else if (ValidationUtils.isEmptyOrNull(rs.getString("Status"))) {
                    newFiles += Integer.parseInt(rs.getString("FileCount"));
                }
            }
            eoFileList.setNewReviewFiles(String.valueOf(newFiles));
            eoFileList.setEoLoadFiles(String.valueOf(eoLoadFiles));

            rs = eoDSJdbcTemplate.queryForRowSet(sbBatchCntrlSuccessCnt.toString(),
                eoFileList.getActiveSurveyQuarterEo());
            String SuccessCnt = null;

            if (rs.next())
                SuccessCnt = rs.getString("SUCCESS_COUNT");
            eoFileList.setSuccessFiles(SuccessCnt);

            rs = eoDSJdbcTemplate.queryForRowSet(sbBatchCntrl.toString(), eoFileList.getActiveSurveyQuarterEo());
            while (rs.next()) {

                if ("LA".equals(rs.getString("Status"))) {
                    errorFiles += Integer.parseInt(rs.getString("FileCount"));
                } else if ("RA".equals(rs.getString("Status"))) {
                    errorFiles += Integer.parseInt(rs.getString("FileCount"));
                }
            }
            eoFileList.setErrorFiles(String.valueOf(errorFiles));

            rs = eoDSJdbcTemplate.queryForRowSet(sbUAChannel.toString());

            while (rs.next()) {
                uaChannelFiles += 1;
            }
            eoFileList.setChannelReviewFiles(String.valueOf(uaChannelFiles));

            int rollupReviewFiles = 0;
            rs = eoDSJdbcTemplate.queryForRowSet(sbRollupReviewFiles.toString());

            String prevSuppCode = "";
            String currSuppCode = "";
            String prevPerfQtr = "";
            String currPerfQtr = "";
            String prevPerfPeriod = "";
            String currPerfPeriod = "";
            while (rs.next()) {
                currSuppCode = rs.getString("SuppCode");
                currPerfQtr = rs.getString("PerfQtr");
                currPerfPeriod = rs.getString("PerfPeriod");
                if (rs.getString("SupplierType").equalsIgnoreCase("Catalog")) {
                    if (!currSuppCode.equals(prevSuppCode)
                        || (currSuppCode.equals(prevSuppCode) && !currPerfQtr.equals(prevPerfQtr))
                        || (currSuppCode.equals(prevSuppCode) && currPerfQtr.equals(prevPerfQtr)
                            && !currPerfPeriod.equals(prevPerfPeriod))) {
                        rollupReviewFiles += 1;
                    }
                } else if (!currSuppCode.equals(prevSuppCode)
                    || (currSuppCode.equals(prevSuppCode) && !currPerfQtr.equals(prevPerfQtr))) {
                    rollupReviewFiles += 1;
                }
                prevSuppCode = currSuppCode;
                prevPerfQtr = currPerfQtr;
                prevPerfPeriod = currPerfPeriod;
            }

            eoFileList.setRolllupReviewFiles(String.valueOf(rollupReviewFiles));

            int loadToAPMFiles = 0;
            rs = eoDSJdbcTemplate.queryForRowSet(sbLoadToApm.toString());
            prevSuppCode = "";
            currSuppCode = "";
            prevPerfQtr = "";
            currPerfQtr = "";
            prevPerfPeriod = "";
            currPerfPeriod = "";
            String prevBatchID = "";
            String currBatchID = "";
            while (rs.next()) {
                currSuppCode = rs.getString("SuppCode");
                currPerfQtr = rs.getString("PerfQtr");
                currBatchID = rs.getString("BatchID");
                currPerfPeriod = rs.getString("PerfPeriod");
                if (rs.getString("SupplierType").equalsIgnoreCase("Catalog")) {
                    if (!currSuppCode.equals(prevSuppCode)
                        || (currSuppCode.equals(prevSuppCode) && !currPerfQtr.equals(prevPerfQtr))
                        || !currBatchID.equals(prevBatchID) || !currPerfPeriod.equals(prevPerfPeriod)) {
                        loadToAPMFiles += 1;
                    }
                } else if (!currSuppCode.equals(prevSuppCode)
                    || (currSuppCode.equals(prevSuppCode) && !currPerfQtr.equals(prevPerfQtr))
                    || !currBatchID.equals(prevBatchID)) {
                    loadToAPMFiles += 1;
                }
                prevSuppCode = currSuppCode;
                prevPerfQtr = currPerfQtr;
                prevBatchID = currBatchID;
                prevPerfPeriod = currPerfPeriod;
            }
            eoFileList.setApmLoadFiles(String.valueOf(loadToAPMFiles));

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterFileStatus())) {
                if ("CHANNELS".equals(eoFileList.getFilterFileStatus())) {
                    eoFileList = getUnassingedChannelSuppliers(eoFileList);
                } else if ("LOAD2APM_PEND".equals(eoFileList.getFilterFileStatus())) {
                    eoFileList = getLoadToApmFiles(eoFileList);
                } else if ("LOAD2EO_PEND".equals(eoFileList.getFilterFileStatus())) {
                    eoFileList = getLoadToEoFiles(eoFileList);
                } else if ("LOAD2APM_PROG".equals(eoFileList.getFilterFileStatus())) {
                    eoFileList = getLoadToApmFilesInProgress(eoFileList);
                } else if ("ERROR_FILES".equals(eoFileList.getFilterFileStatus())) {
                    eoFileList = getFilesInError(eoFileList);
                } else if ("NOT_FOR_PROCESS".equals(eoFileList.getFilterFileStatus())) {
                    eoFileList = getFilesNotForProcess(eoFileList);
                } else if ("SUCCESS_FILES".equals(eoFileList.getFilterFileStatus())) {
                    eoFileList = getFilesInCompleted(eoFileList);
                } else if ("NEW".equals(eoFileList.getFilterFileStatus())) {
                    eoFileList = getNewEOFiles(eoFileList);
                } else {
                    eoFileList = getEOFileListDetails(eoFileList);
                }
            }
            if (eoFileList != null) {
                if (ValidationUtils.isEmptyOrNull(eoFileList.getNoProcessFiles())) {
                    eoFileList.setNoProcessFiles("0");
                }
                if (ValidationUtils.isEmptyOrNull(eoFileList.getSuccessFiles())) {
                    eoFileList.setSuccessFiles("0");
                }
            }

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getEOFileList method ");

        return eoFileList;
    }

    public EOFileList getEOFileListDetails(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getEOFileListDetails method ");

        StringBuilder sb = new StringBuilder(UsageQueries.GET_EO_FILE_LIST_START);

        List<Object> col = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();

        try {

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterSupplierCode())) {
                sb.append(" AND A.SuppCode = ? ");
                params.add(eoFileList.getFilterSupplierCode());
            }

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterFileStatus())) {
                if ("NEW".equals(eoFileList.getFilterFileStatus())) {
                    sb.append(" AND coalesce(A.Status,'') = '' ");
                } else {
                    sb.append(" AND A.Status = ? ");
                    params.add(eoFileList.getFilterFileStatus());
                }
            }

            sb.append(UsageQueries.GET_EO_FILE_LIST_END);
            sb.append(" order by rn ");

            if (eoFileList.getNavigationType() == null) {
                if (eoFileList.getNumberOfRecordsFound() <= 0) {
                    eoFileList.setNumberOfRecordsFound(10);
                } else {
                    eoFileList.setNumberOfRecordsFound(eoFileList.getNumberOfRecordsFound());
                }
            } else {
                if (eoFileList.getNumberOfRecordsFound() <= 0 && eoFileList.getCurrentPageNumber() > 1) {
                    eoFileList.setCurrentPageNumber(eoFileList.getCurrentPageNumber() - 1);
                    eoFileList.setNumberOfRecordsFound(10);
                } else {
                    eoFileList.setNumberOfRecordsFound(eoFileList.getNumberOfRecordsFound());
                }
            }

            int fromIndex = eoFileList.getFromIndex();
            int toIndex = eoFileList.getToIndexWithoutCount() - 1;
            log.debug("From Index: " + fromIndex);
            log.debug("To Index: " + toIndex);
            log.debug("eoFileList getCurrentPageNumber:" + eoFileList.getCurrentPageNumber());

            log.debug("Executing Query EO Files: " + sb.toString());

            int paramCount1 = params.size();
            String paramVal = null;
            ArrayList<Object> parameter = new ArrayList<>();
            for (int i = 0; i < paramCount1; i++) {
                paramVal = (String) params.get(i);
                parameter.add(paramVal);
            }

            parameter.add(fromIndex);
            parameter.add(toIndex);

            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(sb.toString(), params);

            EOFile eoFile = null;
            int totalRecordsFound = 0;
            while (rs.next()) {
                eoFile = new EOFile();
                eoFile.setFileId(rs.getString("FileId"));
                eoFile.setFileName(rs.getString("FileNa"));
                eoFile.setOrigFileName(rs.getString("OrigFileNa"));
                eoFile.setZipFileName(rs.getString("ZipFileNa"));
                eoFile.setFileFrom(rs.getString("FileFrom"));
                eoFile.setFileTo(rs.getString("FileTo"));
                eoFile.setEmailFrom(rs.getString("EmailFrom"));
                eoFile.setEmailTo(rs.getString("EmailTo"));
                eoFile.setEmailDate(rs.getString("EmailDate"));
                eoFile.setFileTransferMethod(rs.getString("FileTransMethod"));
                eoFile.setCreateDate(rs.getString("CreDate"));
                eoFile.setStatusCode(rs.getString("Status"));
                eoFile.setEoLoadFlg(rs.getString("EOLoadFlg"));
                eoFile.setApmLoadFlg(rs.getString("APMLoadFlg"));
                eoFile.setPerfQuarter(rs.getString("PerfQtr"));
                eoFile.setSuppCode(rs.getString("SuppCode"));
                eoFile.setLoadStartTime(rs.getString("LoadStartTime"));
                eoFile.setLoadEndTime(rs.getString("LoadEndTime"));
                eoFile.setFileRejectCount(rs.getString("FileRejectCount"));
                eoFile.setFileRowCount(rs.getString("FileRowCount"));
                eoFile.setFileNumRejectCount(rs.getString("FileVldRejectCount"));
                col.add(eoFile);
                totalRecordsFound = rs.getInt("totalrows");
            }

            eoFileList.setNumberOfRecordsFound(totalRecordsFound);
            eoFileList.setSearchResults(col);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getEOFileListDetails method ");

        return eoFileList;
    }

    private EOFileList getUnassingedChannelSuppliers(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getUnassingedChannelSuppliers method ");

        StringBuilder sb = new StringBuilder(UsageQueries.GET_EO_FILE_UA_CHANNEL_COUNT);

        List<Object> col = new ArrayList<>();

        try {

            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(sb.toString());

            EOSupplierFormat eoSupplierFormat = null;
            while (rs.next()) {
                eoSupplierFormat = new EOSupplierFormat();
                eoSupplierFormat.setSuppCode(rs.getString("SuppCode"));
                eoSupplierFormat.setUnassignedChannelCount(rs.getString("UACount"));
                col.add(eoSupplierFormat);
            }
            log.debug("Number of Suppliers with unassigned channels: " + col == null ? "0" : "" + col.size());
            eoFileList.setSearchResults(col);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getUnassingedChannelSuppliers method ");

        return eoFileList;
    }

    private EOFileList getLoadToApmFiles(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getLoadToApmFiles method ");

        StringBuilder sb = new StringBuilder(UsageQueries.GET_EO_FILE_LOAD_TO_APM_PEND);

        List<Object> col = new ArrayList<>();

        try {
            log.debug("Query : " + sb.toString());

            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(sb.toString());

            EOFile eoFile = null;
            String prevSuppCode = "";
            String currSuppCode = "";

            String prevPerfQtr = "";
            String currPerfQtr = "";

            String prevPerfPeriod = "";
            String currPerfPeriod = "";

            String supplierType = "";

            while (rs.next()) {

                currSuppCode = rs.getString("SuppCode");
                currPerfQtr = rs.getString("PerfQtr");
                currPerfPeriod = rs.getString("PerfPeriod");
                supplierType = rs.getString("SupplierType").toUpperCase();

                if (supplierType.equalsIgnoreCase("Catalog")) {

                    if (!currSuppCode.equals(prevSuppCode)
                        || (currSuppCode.equals(prevSuppCode) && !currPerfQtr.equals(prevPerfQtr))
                        || (currSuppCode.equals(prevSuppCode) && currPerfQtr.equals(prevPerfQtr)
                            && !currPerfPeriod.equals(prevPerfPeriod))) {
                        eoFile = new EOFile();
                        eoFile.setSuppCode(rs.getString("SuppCode"));
                        eoFile.setPerfQuarter(rs.getString("PerfQtr"));
                        eoFile.setPerfPeriod(currPerfPeriod);
                        eoFile.setLastRollupCount(rs.getString("ROLLUPCNT"));
                        eoFile.setSupplierType(supplierType);
                        col.add(eoFile);
                    }
                } else {

                    if (!currSuppCode.equals(prevSuppCode)
                        || (currSuppCode.equals(prevSuppCode) && !currPerfQtr.equals(prevPerfQtr))) {
                        eoFile = new EOFile();
                        eoFile.setSuppCode(rs.getString("SuppCode"));
                        eoFile.setPerfQuarter(rs.getString("PerfQtr"));
                        eoFile.setPerfPeriod(currPerfPeriod);
                        eoFile.setLastRollupCount(rs.getString("ROLLUPCNT"));
                        eoFile.setSupplierType(supplierType);
                        col.add(eoFile);
                    }
                }

                eoFile = new EOFile();
                eoFile.setSuppCode(rs.getString("SuppCode"));
                eoFile.setFileId(rs.getString("FileId"));
                eoFile.setFileName(rs.getString("FileNa"));
                eoFile.setFileRowCount(rs.getString("FileRowCount"));
                eoFile.setFileRejectCount(rs.getString("FileRejectCount"));
                eoFile.setErrorCorrectedStatus(rs.getString("errorCorrStatus"));
                eoFile.setFileRejectCountNew(rs.getString("FileRejectCountNew"));
                eoFile.setFileVldRejectcount(rs.getString("FileVldRejectCountNew"));
                col.add(eoFile);
                prevSuppCode = currSuppCode;
                prevPerfQtr = currPerfQtr;
                prevPerfPeriod = currPerfPeriod;
            }

            eoFileList.setSearchResults(col);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getLoadToApmFiles method ");

        return eoFileList;
    }

    private EOFileList getLoadToApmFilesInProgress(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getLoadToApmFilesInProgress method ");

        SqlRowSet rs = null;

        StringBuilder sb = new StringBuilder(UsageQueries.GET_EO_FILE_LOAD_TO_APM_PROG);

        List<Object> col = new ArrayList<>();

        try {

            rs = eoDSJdbcTemplate.queryForRowSet(sb.toString());

            EOFile eoFile = null;
            String prevSuppCode = "";
            String currSuppCode = "";
            String prevPerfQtr = "";
            String currPerfQtr = "";
            String prevPerfPeriod = "";
            String currPerfPeriod = "";
            String prevBatchID = "";
            String currBatchID = "";
            while (rs.next()) {

                currSuppCode = rs.getString("SuppCode");
                currPerfQtr = rs.getString("PerfQtr");
                currBatchID = rs.getString("BatchID");

                if (rs.getString("SupplierType").equalsIgnoreCase("Catalog")) {
                    if (!currSuppCode.equals(prevSuppCode)
                        || (currSuppCode.equals(prevSuppCode) && !currPerfQtr.equals(prevPerfQtr))
                        || !currBatchID.equals(prevBatchID) || !currPerfPeriod.equals(prevPerfPeriod)) {
                        eoFile = new EOFile();
                        eoFile.setSuppCode(rs.getString("SuppCode"));
                        eoFile.setBatchId(rs.getString("BatchID"));
                        eoFile.setStatusCode(rs.getString("Status"));
                        eoFile.setPerfQuarter(rs.getString("PerfQtr"));
                        eoFile.setPerfPeriod(rs.getString("PerfPeriod"));
                        col.add(eoFile);
                    }
                } else if (!currSuppCode.equals(prevSuppCode)
                    || (currSuppCode.equals(prevSuppCode) && !currPerfQtr.equals(prevPerfQtr))
                    || !currBatchID.equals(prevBatchID)) {
                    eoFile = new EOFile();
                    eoFile.setSuppCode(rs.getString("SuppCode"));
                    eoFile.setBatchId(rs.getString("BatchID"));
                    eoFile.setStatusCode(rs.getString("Status"));
                    eoFile.setPerfQuarter(rs.getString("PerfQtr"));
                    col.add(eoFile);
                }

                eoFile = new EOFile();
                eoFile.setSuppCode(rs.getString("SuppCode"));
                eoFile.setFileId(rs.getString("FileId"));
                eoFile.setFileName(rs.getString("FileNa"));
                col.add(eoFile);

                prevSuppCode = currSuppCode;
                prevPerfQtr = currPerfQtr;
                prevBatchID = currBatchID;
            }

            eoFileList.setSearchResults(col);

        } catch (Exception e) {

            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getLoadToApmFilesInProgress method ");

        return eoFileList;
    }

    private EOFileList getFilesInError(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getFilesInError method ");

        StringBuilder sb = new StringBuilder(UsageQueries.GET_EO_FILES_IN_ERROR);

        List<Object> col = new ArrayList<>();

        try {
            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(sb.toString());
            EOFile eoFile = null;
            while (rs.next()) {
                eoFile = new EOFile();
                eoFile.setSuppCode(rs.getString("SuppCode"));
                eoFile.setFileId(rs.getString("FileId"));
                eoFile.setFileName(rs.getString("FileNa"));
                eoFile.setBatchId(rs.getString("BatchId"));
                eoFile.setStatusCode(rs.getString("Status"));
                col.add(eoFile);
            }
            eoFileList.setSearchResults(col);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getFilesInError method ");

        return eoFileList;
    }

    private EOFileList getFilesNotForProcess(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getFilesNotForProcess method ");

        StringBuilder sb = new StringBuilder(UsageQueries.GET_EO_FILES_NOT_FOR_PROCESS);

        List<Object> col = new ArrayList<>();

        try {

            log.debug("Query: " + sb.toString());
            SqlRowSet rs =
                eoDSJdbcTemplate.queryForRowSet(sb.toString(), eoFileList.getStartDate(), eoFileList.getEndDate());
            EOFile eoFile = null;
            while (rs.next()) {
                eoFile = new EOFile();
                eoFile.setSuppCode(rs.getString("SuppCode"));
                eoFile.setFileId(rs.getString("FileId"));
                eoFile.setFileName(rs.getString("FileNa"));
                eoFile.setFileTo(rs.getString("FileTo"));
                eoFile.setCreateDate(rs.getString("CreDateFormatted"));
                eoFile.setUpdateDate(rs.getString("UpdDateFormatted"));
                col.add(eoFile);
            }
            eoFileList.setSearchResults(col);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getFilesInError method ");
        return eoFileList;
    }

    private EOFileList getFilesInCompleted(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getFilesInCompleted method ");

        if (eoFileList != null) {
            log.debug("Active Distribution Quarter EO " + eoFileList.getActiveSurveyQuarterEo());
        } else {
            log.debug("Input object is null... Returning");
            return eoFileList;
        }

        StringBuilder sb = new StringBuilder(UsageQueries.GET_EO_FILES_LOADED_SUCCESS_PART1.replace("a.TgtSurvYearQtr",
            "REPLACE(a.TgtSurvYearQtr,'Q','D') TgtSurvYearQtr"));

        ArrayList<String> params = new ArrayList<>();

        if (ValidationUtils.isEmptyOrNull(eoFileList.getFilterPerformanceQuarter())
            && ValidationUtils.isEmptyOrNull(eoFileList.getFilterPerfPeriod())
            && ValidationUtils.isEmptyOrNull(eoFileList.getFilterDistributionQuarter())
            && ValidationUtils.isEmptyOrNull(eoFileList.getFilterCompletedSupplierCode())) {
            sb = new StringBuilder(UsageQueries.GET_EO_FILES_LOADED_SUCCESS_DEFAULT);
            params.add(eoFileList.getActiveSurveyQuarterEo());
            eoFileList.setFilterDistributionQuarter(eoFileList.getActiveSurveyQuarterEo());
        } else {

            sb.append("and CASE WHEN b.SupplierType = 'Distribution'  ");

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterPerformanceQuarter())) {
                sb.append(" and PerfQtr = ? ");
                params.add(eoFileList.getFilterPerformanceQuarter());
            }

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterDistributionQuarter())) {
                sb.append(" and a.TgtSurvYearQtr = ? ");
                params.add(eoFileList.getFilterDistributionQuarter().replace('D', 'Q'));
            }

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterCompletedSupplierCode())) {
                sb.append(" and a.SuppCode = ? ");
                params.add(eoFileList.getFilterCompletedSupplierCode());
            }

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterPerfPeriod())) {
                sb.append(" and a.PerfPeriod = ? ");
                params.add(eoFileList.getFilterPerfPeriod());
            }

            sb.append("THEN 1 ");

            sb.append(" WHEN b.SupplierType = 'Catalog' ");

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterPerformanceQuarter())) {
                sb.append(" and PerfQtr = ? ");
                params.add(eoFileList.getFilterPerformanceQuarter());
            }

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterDistributionQuarter())) {
                sb.append(" and a.TgtSurvYearQtr = ? ");
                params.add(eoFileList.getFilterDistributionQuarter().replace('D', 'Q'));
            }

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterCompletedSupplierCode())) {
                sb.append(" and a.SuppCode = ? ");
                params.add(eoFileList.getFilterCompletedSupplierCode());
            }

            if (!ValidationUtils.isEmptyOrNull(eoFileList.getFilterPerfPeriod())) {
                sb.append(" and a.PerfPeriod = ? ");
                params.add(eoFileList.getFilterPerfPeriod());
            }

            sb.append(" THEN 1 ELSE 0 END = 1 ");
        }

        sb.append(UsageQueries.GET_EO_FILES_LOADED_SUCCESS_PART2);

        log.debug("Executing query " + sb.toString());

        eoFileList.setFilterDistributionQuarter(eoFileList.getFilterDistributionQuarter().replace('Q', 'D'));

        List<Object> col = new ArrayList<>();

        try {

            int paramCount1 = params.size();
            String paramVal = null;
            ArrayList<Object> parameter = new ArrayList<>();
            for (int i = 0; i < paramCount1; i++) {
                paramVal = (String) params.get(i);
                parameter.add(paramVal);
            }

            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(sb.toString(), parameter.toArray());
            EOFile eoFile = null;
            while (rs.next()) {
                eoFile = new EOFile();
                eoFile.setSuppCode(rs.getString("SuppCode"));
                eoFile.setCreateDate(rs.getString("UpdDtFormatted"));
                eoFile.setRollupRowCount(rs.getString("RowCnt"));
                eoFile.setPlayCount(rs.getString("PlayCnt"));
                eoFile.setPerfQuarter(rs.getString("PerfQtr"));
                eoFile.setDistQuarter(rs.getString("TgtSurvYearQtr"));
                eoFile.setPerfPeriod(rs.getString("PerfPeriod"));
                col.add(eoFile);
            }
            eoFileList.setSearchResults(col);
            eoFileList.setSuccessFilesByPeriod(String.valueOf(col.size()));

            rs = eoDSJdbcTemplate.queryForRowSet(UsageQueries.GET_EO_CALL_LETTER_CONFIGS);
            List<EOCallLetterConfig> callLetterConfgs = new ArrayList<>();
            EOCallLetterConfig eoCallLetterConfig = null;
            while (rs.next()) {
                eoCallLetterConfig = new EOCallLetterConfig();
                eoCallLetterConfig.setCallLetter(rs.getString("CalLtr"));
                eoCallLetterConfig.setSuppCode(rs.getString("SuppCode"));
                eoCallLetterConfig.setCallLetterFull(rs.getString("callLtrFull"));
                eoCallLetterConfig.setCallLetterSuffix(rs.getString("CalLtrSuf"));
                callLetterConfgs.add(eoCallLetterConfig);
            }
            eoFileList.setCallLetters(callLetterConfgs);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getFilesInCompleted method ");

        return eoFileList;
    }

    private EOFileList getLoadToEoFiles(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getLoadToEoFiles method ");

        StringBuilder sb = new StringBuilder(UsageQueries.GET_EO_FILE_LOAD_TO_EO_PEND);

        List<Object> col = new ArrayList<>();

        try {

            log.debug("Query " + sb.toString());
            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(sb.toString());
            EOFile eoFile = null;
            while (rs.next()) {
                eoFile = new EOFile();
                eoFile.setSuppCode(rs.getString("SuppCode"));
                eoFile.setFileId(rs.getString("FileId"));
                eoFile.setFileName(rs.getString("FileNa"));
                eoFile.setFileTo(rs.getString("FileTo"));
                eoFile.setCreateDate(rs.getString("CreDate"));
                eoFile.setStatusCode(rs.getString("Status"));
                if (!ValidationUtils.isEmptyOrNull(rs.getString("FormatSuppCode"))) {
                    eoFile.setEoSuppFormatFlag("Y");
                } else {
                    eoFile.setEoSuppFormatFlag("N");
                }
                col.add(eoFile);

            }

            eoFileList.setSearchResults(col);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting UsageDAO - getLoadToEoFiles method ");

        return eoFileList;
    }

    private EOFileList getNewEOFiles(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - getNewEOFiles method Filter Supplier: " + eoFileList.getFilterSupplierCode());

        StringBuilder sb = new StringBuilder(UsageQueries.GET_EO_FILES_NEW);
        if (eoFileList != null && !ValidationUtils.isEmptyOrNull(eoFileList.getFilterSupplierCode())) {
            sb.append(" and a.SuppCode = ? ");
        }
        sb.append(UsageQueries.GET_EO_FILES_NEW_END);
        List<Object> col = new ArrayList<>();

        log.debug("Executing Query: " + sb.toString());

        try {

            ArrayList<Object> params = new ArrayList<>();

            if (eoFileList != null && !ValidationUtils.isEmptyOrNull(eoFileList.getFilterSupplierCode())) {
                params.add(eoFileList.getFilterSupplierCode());
            }

            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(sb.toString(), params.toArray());

            EOFile eoFile = null;
            while (rs.next()) {
                eoFile = new EOFile();
                eoFile.setSuppCode(rs.getString("SuppCode"));
                eoFile.setFileId(rs.getString("FileId"));
                eoFile.setFileName(rs.getString("FileNa"));
                eoFile.setFileTo(rs.getString("FileTo"));
                eoFile.setCreateDate(rs.getString("CreDate"));
                eoFile.setPerfPeriod(rs.getString("PerfPeriod"));
                eoFile.setSupplierType(rs.getString("SupplierType"));
                eoFile.setFrequency(rs.getString("Frequency"));
                col.add(eoFile);
            }
            eoFileList.setSearchResults(col);
            eoFileList.setNewReviewFilesFiltered("" + col.size());

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting UsageDAO - getNewEOFiles method ");

        return eoFileList;
    }

    public EOFileList loadToAPM(EOFileList eoFileList) throws PrepSystemException {

        log.debug("Entering  UsageDAO - loadToAPM method ");

        if (eoFileList == null || eoFileList.getSearchResults() == null || eoFileList.getSearchResults().size() <= 0) {
            log.debug("Input List is empty.... Returning");
            return eoFileList;
        }

        String stmtStr = UsageQueries.INSERT_BATCH_CONTROL_SS;

        List<?> fileList = eoFileList.getSearchResults();
        Iterator<?> itr = fileList.iterator();
        EOFile eoFile = null;
        try {

            String suppDb = "";
            String supplierType = "";
            String datastageJobType = "";

            while (itr.hasNext()) {
                eoFile = (EOFile) itr.next();
                log.debug("Creating Batch Control for " + eoFile);

                SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_SUPP_INFO, eoFile.getSuppCode());

                while (rs.next()) {
                    suppDb = rs.getString("SuppDB");
                    supplierType = rs.getString("SupplierType").trim();
                    datastageJobType = rs.getString("ApmLdJob").trim();
                }

                log.debug("In loadToAPM, suppDb: " + suppDb + " SupplierType: " + supplierType + " PerfPeriod: "
                    + eoFile.getPerfPeriod() + " datastageJobType " + datastageJobType);

                if (ValidationUtils.isEmptyOrNull(suppDb)) {
                    log.error("Supp DB Not Found for supp Code " + eoFile.getSuppCode());
                    throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION);
                }

                if (ValidationUtils.isEmptyOrNull(supplierType) || ValidationUtils.isEmptyOrNull(datastageJobType)) {
                    log.error("supplierType or datastageJobType Not Found for supp Code " + eoFile.getSuppCode());
                    throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION);
                }

                if (supplierType.toUpperCase().equalsIgnoreCase("Catalog"))
                    stmtStr = stmtStr.replace("TgtSurvYearQtr", "PerfPeriod");

                ArrayList<Object> params = new ArrayList<>();

                params.add(eoFile.getSuppCode());
                params.add("RR");
                params.add(eoFileList.getUserId());

                if (supplierType.compareToIgnoreCase("Catalog") == 0
                    || datastageJobType.compareToIgnoreCase("eo_2_apm_generic_detail") == 0)
                    params.add(suppDb + ".dbo.Detail_" + eoFile.getPerfQuarter());
                else
                    params.add(suppDb + ".dbo.Rollup_" + eoFile.getPerfQuarter());

                params.add(eoFile.getPerfQuarter());

                if (supplierType.equalsIgnoreCase("Catalog"))
                    params.add(eoFile.getPerfPeriod());
                else
                    params.add(eoFile.getDistQuarter().replace('D', 'Q'));

                ascapJdbcTemplate.update(stmtStr, params);

                log.debug("Updated File " + eoFile.getFileId());

                String qry = null;

                if (supplierType.equalsIgnoreCase("Catalog"))
                    qry = UsageQueries.UPDATE_EO_FILE_INVENTORY_LOAD2APM + "and PerfPeriod = ?";
                else
                    qry = UsageQueries.UPDATE_EO_FILE_INVENTORY_LOAD2APM;

                params.clear();

                params.add(eoFileList.getUserId());
                params.add(eoFile.getSuppCode());
                params.add(eoFile.getPerfQuarter());

                if (supplierType.equalsIgnoreCase("Catalog"))
                    params.add(eoFile.getPerfPeriod());

                ascapJdbcTemplate.update(qry, params);
            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting  UsageDAO - loadToAPM method ");
        return eoFileList;
    }

    public String validationErrorExists(String suppcode, String perfqtr, String perfperiod) throws PrepSystemException {
        log.debug("Entering  UsageDAO - validationErrorExists method ");

        String result = "";

        try {

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.VALIDATION_ERROR_EXISTS_WITH_PERFPERIOD,
                suppcode, perfqtr, perfperiod);

            if (rs != null) {

                while (rs.next()) {
                    result = rs.getString("ValidationError");
                    log.debug("Validation error exists " + result);
                }
            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);

        }

        log.debug("Exiting  UsageDAO - validationErrorExists method ");

        return (result);
    }

    public String getRollupThreshold() throws PrepSystemException {

        log.debug("Entering getRollupThreshold method in UsageDAO ");
        String result = null;
        SqlRowSet rs = null;
        try {
            log.debug("getRollupThreshold 1");
            log.debug("getRollupThreshold 2");

            String a = "SELECT PARAM_VAL FROM APM_SYS_PARAM WHERE PARAM_NA = 'ROLLUP_THRESHOLD'";
            log.debug(a);
            rs = ascapJdbcTemplate.queryForRowSet(a);

            if (rs != null && rs.next()) {
                result = rs.getString("PARAM_VAL");
            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting getRollupThreshold method in UsageDAO ");

        return result;
    }

    public EOFileList updateFileInventory(EOFileList eoFileList) throws PrepSystemException {

        log.debug("Entering  UsageDAO - updateFileInventory method ");

        if (eoFileList == null || eoFileList.getSearchResults() == null || eoFileList.getSearchResults().size() <= 0) {
            log.debug("Input List is empty.... Returning");
            return eoFileList;
        }

        try {

            ascapJdbcTemplate.update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    List<?> fileList = eoFileList.getSearchResults();
                    Iterator<?> itr = fileList.iterator();
                    EOFile eoFile = null;

                    String stmtStr = UsageQueries.UPDATE_EO_FILEINVENTORY;
                    while (itr.hasNext()) {
                        eoFile = (EOFile) itr.next();
                        log.debug("eoFile " + eoFile);

                        if (!ValidationUtils.isEmptyOrNull(eoFile.getPerfPeriod())) {
                            stmtStr += " and  perfPeriod = ? ";
                        }
                    }
                    PreparedStatement pstmt = con.prepareStatement(stmtStr);
                    pstmt.setString(1, eoFile.getErrorCorrectedStatus());
                    pstmt.setString(2, eoFileList.getUserId());
                    pstmt.setString(3, eoFile.getPerfQuarter());
                    pstmt.setString(4, eoFile.getSuppCode());
                    if (!ValidationUtils.isEmptyOrNull(eoFile.getPerfPeriod())) {
                        pstmt.setString(5, eoFile.getPerfPeriod());
                    }

                    return pstmt;
                }
            });

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting  UsageDAO - updateFileInventory method ");
        return eoFileList;
    }

    public EOFileList loadToEO(EOFileList eoFileList) throws PrepSystemException {
        log.debug("Entering  UsageDAO - loadToEO method ");

        if (eoFileList == null || eoFileList.getSearchResults() == null || eoFileList.getSearchResults().size() <= 0) {
            log.debug("Input List is empty.... Returning");
            return eoFileList;
        }

        String stmtStr = UsageQueries.UPDATE_EO_FILE_INVENTORY_LOAD2EO;

        List<?> fileList = eoFileList.getSearchResults();
        Iterator<?> itr = fileList.iterator();
        EOFile eoFile = null;
        try {

            while (itr.hasNext()) {
                eoFile = (EOFile) itr.next();
                if ("Y".equals(eoFile.getDonotProcessFlag())) {
                    eoFile.setStatusCode("NA");
                    eoFile.setPerfQuarter(null);
                } else {
                    if (this.isSupplierFormatExists(eoFile)) {
                        eoFile.setStatusCode("CR");
                    } else {
                        eoFile.setStatusCode("SA");
                    }
                }

                final EOFile eoFile1 = eoFile;
                ascapJdbcTemplate.update(new PreparedStatementCreator() {

                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

                        PreparedStatement pstmt = con.prepareStatement(stmtStr);
                        pstmt.setString(1, eoFile1.getStatusCode());
                        pstmt.setString(2, eoFile1.getPerfQuarter());
                        pstmt.setString(3, eoFileList.getUserId());

                        log.debug("updating, fileid: " + eoFile1.getFileId() + " supplierType:"
                            + eoFile1.getSupplierType() + ": PerfPeriod: " + eoFile1.getPerfPeriod());
                        if ("CATALOG".equalsIgnoreCase(eoFile1.getSupplierType())) {
                            pstmt.setString(4, eoFile1.getPerfPeriod());
                        } else {
                            pstmt.setString(4, null);
                        }
                        pstmt.setString(5, eoFile1.getFileId());
                        return pstmt;
                    }
                });

                log.debug("Updated File " + eoFile1.getFileId());
            }

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting  UsageDAO - loadToEO method ");
        return eoFileList;
    }

    private boolean isSupplierFormatExists(EOFile eoFile) throws PrepSystemException {
        log.debug("Entering  UsageDAO - isSupplierFormatExists method ");

        String stmtStr = UsageQueries.IS_SUPPLIER_FORMAT_EXISTS;
        boolean suppFormatExists = false;
        try {

            log.debug("Updating EOFfile " + eoFile);

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(stmtStr, eoFile.getFileId());

            if (rs.next()) {
                suppFormatExists = true;
            }

            log.debug("SuppFormat Exists for File " + eoFile.getFileId() + " " + suppFormatExists);
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting  UsageDAO - isSupplierFormatExists method ");
        return suppFormatExists;
    }

    public LogRequestSummary getLogRequestSummary(LogRequestSummary logRequestSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAO - getLogRequestSummary method ");

        String activeSurveyYearQuarter = getActiveSurveyYearQuarter();
        log.debug("activeSurveyYearQuarter: " + activeSurveyYearQuarter);

        logRequestSummary = setNavigationInfo(logRequestSummary, activeSurveyYearQuarter);

        int fromIndex = logRequestSummary.getFromIdx();
        int toIndex = logRequestSummary.getToIdx();

        String filterSupplierCode = logRequestSummary.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logRequestSummary.getFiltertargetSurveyYearQuarter();
        String filterStaus = logRequestSummary.getFilterStaus();
        String operationType = logRequestSummary.getOperationType();

        log.debug("filterSupplierCode: " + filterSupplierCode + "filterTargetSurveyYearQuarter: "
            + filterTargetSurveyYearQuarter + "filterStaus: " + filterStaus + "operationType: " + operationType);

        String canDeleteLogRequest = canDeleteLogRequest(filterTargetSurveyYearQuarter);

        int i = 0;
        String[] param = null;
        int paramlength = 0;

        String query = UsageQueries.GET_LOGREQUEST_SUMMARY_LIST;

        if (operationType == null) {
            query += " AND A.TGTSURVYEARQTR = ? ";
        } else {
            if (!ValidationUtils.isEmptyOrNull(filterSupplierCode)) {
                query += " AND A.CALL = ? ";
                i++;
            }
            if (!ValidationUtils.isEmptyOrNull(filterTargetSurveyYearQuarter)) {
                query += " AND A.TGTSURVYEARQTR = ? ";
                i++;
            }
            if (!ValidationUtils.isEmptyOrNull(filterStaus)) {
                query += " AND A.LOG_STATUS = ? ";
                i++;
            }

            paramlength = i;
            i = 0;
            if (paramlength > 0) {
                param = new String[paramlength];

                if (!ValidationUtils.isEmptyOrNull(filterSupplierCode)) {
                    param[i] = filterSupplierCode.toUpperCase();
                    i++;
                }
                if (!ValidationUtils.isEmptyOrNull(filterTargetSurveyYearQuarter)) {
                    param[i] = filterTargetSurveyYearQuarter;
                    i++;
                }
                if (!ValidationUtils.isEmptyOrNull(filterStaus)) {
                    param[i] = filterStaus;
                    i++;
                }
            }

        }

        if (param != null && param.length > 0) {
            for (String s : param) {
                if (s != null)
                    log.debug("Param >>: " + s);
            }
        }

        query += UsageQueries.GET_LOGREQUEST_SUMMARY_LIST_ORDER_BY;

        StringBuilder sb = new StringBuilder(query);

        LogRequestSummary logRequestSummaryResult = new LogRequestSummary();
        LogSummaryRequest logSummaryRequest;

        List<Object> collectionLogSummaryRequest = new ArrayList<>();

        try {

            i = 1;
            log.debug("Executing Query : " + sb.toString());

            ArrayList<Object> params = new ArrayList<>();

            if (operationType == null) {
                params.add(activeSurveyYearQuarter);
                params.add(fromIndex);
                params.add(toIndex);

            } else {

                for (int a = 0; a < param.length; a++) {
                    params.add(param[a]);
                    log.debug("param: " + i + ": " + param[a]);
                    i++;
                }
                params.add(fromIndex);
                log.debug("param fromIndex: " + fromIndex);
                params.add(toIndex);
                log.debug("param toIndex: " + toIndex);
            }

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(sb.toString(), params.toArray());

            int searchResultRows = 0;

            while (rs.next()) {

                logSummaryRequest = new LogSummaryRequest();

                logSummaryRequest.setCallLetter(rs.getString("CallLetter"));
                logSummaryRequest.setStartDate(rs.getString("startDate"));
                logSummaryRequest.setEndDate(rs.getString("endDate"));

                logSummaryRequest.setStartDateDummy(rs.getString("startDate"));
                logSummaryRequest.setEndDateDummy(rs.getString("endDate"));

                logSummaryRequest.setLocation(rs.getString("location"));
                logSummaryRequest.setState(rs.getString("state"));
                logSummaryRequest.setAccountNumber(rs.getString("accountNumber"));
                logSummaryRequest.setLogMode(rs.getString("logMode"));
                logSummaryRequest.setFormat(rs.getString("format"));
                logSummaryRequest.setDateReceived(rs.getString("dateReceived"));
                logSummaryRequest.setLogComments(rs.getString("logComments"));
                logSummaryRequest.setNotes(rs.getString("notes"));
                logSummaryRequest.setStatus(rs.getString("status"));

                logSummaryRequest.setLogRequestId(rs.getString("logRequestId"));

                logSummaryRequest.setTargetSurveyYearQuarter(rs.getString("targetsurvyearqtr"));
                searchResultRows = Integer.parseInt(rs.getString("totalrows"));
                collectionLogSummaryRequest.add(logSummaryRequest);
            }

            logRequestSummaryResult.setSearchResults(collectionLogSummaryRequest);

            if (operationType == null) {
                logRequestSummaryResult.setFiltertargetSurveyYearQuarter(activeSurveyYearQuarter);
            }

            logRequestSummaryResult.setSearchResultRows(String.valueOf(searchResultRows));
            logRequestSummaryResult.setSearchRequested("Y");
            logRequestSummaryResult.setActiveqtr(activeSurveyYearQuarter);
            if (logRequestSummary.getNavigationType() == null) {
                if (logRequestSummary.getNumberOfRecordsFound() <= 0) {
                    logRequestSummaryResult.setNumberOfRecordsFound(10);
                } else {
                    logRequestSummaryResult.setNumberOfRecordsFound(logRequestSummary.getNumberOfRecordsFound());
                }
            } else {
                if (logRequestSummary.getNumberOfRecordsFound() <= 0 && logRequestSummary.getCurrentPageNumber() > 1) {
                    logRequestSummaryResult.setCurrentPageNumber(logRequestSummary.getCurrentPageNumber() - 1);
                    logRequestSummaryResult.setNumberOfRecordsFound(10);
                } else {
                    logRequestSummaryResult.setNumberOfRecordsFound(logRequestSummary.getNumberOfRecordsFound());
                }
            }

            log.debug("New values - currentPg: " + logRequestSummary.getCurrentPg() + " totalPg: "
                + logRequestSummary.getTotalPg() + " fromIndex: " + fromIndex + " toIndex: " + toIndex);

            logRequestSummaryResult.setNumberOfRecordsFound(searchResultRows);
            logRequestSummaryResult.setCurrentPg(logRequestSummary.getCurrentPg());
            logRequestSummaryResult.setTotalPg(logRequestSummary.getTotalPg());
            logRequestSummaryResult.setFirstPg(logRequestSummary.getFirstPg());
            logRequestSummaryResult.setLastPg(logRequestSummary.getLastPg());

            logRequestSummaryResult.setFilterSupplierCode(filterSupplierCode);
            logRequestSummaryResult.setFiltertargetSurveyYearQuarter(filterTargetSurveyYearQuarter);
            logRequestSummaryResult.setFilterStaus(filterStaus);
            logRequestSummaryResult.setFilterSupplierCodeOrig(logRequestSummary.getFilterSupplierCodeOrig());
            logRequestSummaryResult
                .setFiltertargetSurveyYearQuarterOrig(logRequestSummary.getFiltertargetSurveyYearQuarterOrig());
            logRequestSummaryResult.setFilterStausOrig(logRequestSummary.getFilterStausOrig());
            logRequestSummaryResult.setCanDeleteLogRequest(canDeleteLogRequest);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);

        }
        log.debug("Exiting UsageDAO - getLogRequestSummary method ");
        return logRequestSummaryResult;
    }

    public LogRequestSummary setNavigationInfo(LogRequestSummary logRequestSummary, String activeSurveyYearQuarter)
        throws PrepSystemException {

        log.debug("Entering  UsageDAO - setNavigationInfo method ");

        String operationType = logRequestSummary.getOperationType();
        String navigation = logRequestSummary.getNavigation();

        String filterSupplierCode = logRequestSummary.getFilterSupplierCode();
        String filtertargetSurveyYearQuarter = logRequestSummary.getFiltertargetSurveyYearQuarter();
        String filterStaus = logRequestSummary.getFilterStaus();
        String filterSupplierCodeOrig = logRequestSummary.getFilterSupplierCodeOrig();
        String filtertargetSurveyYearQuarterOrig = logRequestSummary.getFiltertargetSurveyYearQuarterOrig();
        String filterStausOrig = logRequestSummary.getFilterStausOrig();

        // set filter values
        // menu invoke, search, update, release, delete - here page should remain same
        if (UsageCommonValidations.isEmptyOrNull(navigation)) {
            // menu invoke
            if (UsageCommonValidations.isEmptyOrNull(operationType)) {
                logRequestSummary.setFiltertargetSurveyYearQuarter(activeSurveyYearQuarter);
                logRequestSummary.setFiltertargetSurveyYearQuarterOrig(activeSurveyYearQuarter);
                log.debug("00000000000");
            }
            // search, update, release, delete
            else {
                if ("SEARCH".equalsIgnoreCase(operationType) || "BACK".equalsIgnoreCase(operationType)) {

                    logRequestSummary.setFiltertargetSurveyYearQuarter(filtertargetSurveyYearQuarter);
                    logRequestSummary.setFilterSupplierCode(filterSupplierCode);
                    logRequestSummary.setFilterStaus(filterStaus);
                    logRequestSummary.setFiltertargetSurveyYearQuarterOrig(filtertargetSurveyYearQuarter);
                    logRequestSummary.setFilterSupplierCodeOrig(filterSupplierCode);
                    logRequestSummary.setFilterStausOrig(filterStaus);

                    log.debug("11111111111");
                } else {
                    logRequestSummary.setFiltertargetSurveyYearQuarter(filtertargetSurveyYearQuarterOrig);
                    logRequestSummary.setFilterSupplierCode(filterSupplierCodeOrig);
                    logRequestSummary.setFilterStaus(filterStausOrig);
                    logRequestSummary.setFiltertargetSurveyYearQuarterOrig(filtertargetSurveyYearQuarterOrig);
                    logRequestSummary.setFilterSupplierCodeOrig(filterSupplierCodeOrig);
                    logRequestSummary.setFilterStausOrig(filterStausOrig);

                    log.debug("22222222222222");
                }

            }
        }
        // page navigation - only page should change
        else {
            logRequestSummary.setFiltertargetSurveyYearQuarter(filtertargetSurveyYearQuarterOrig);
            logRequestSummary.setFilterSupplierCode(filterSupplierCodeOrig);
            logRequestSummary.setFilterStaus(filterStausOrig);
            logRequestSummary.setFiltertargetSurveyYearQuarterOrig(filtertargetSurveyYearQuarterOrig);
            logRequestSummary.setFilterSupplierCodeOrig(filterSupplierCodeOrig);
            logRequestSummary.setFilterStausOrig(filterStausOrig);

        }

        int currentPg = logRequestSummary.getCurrentPg();
        int totalPg = logRequestSummary.getTotalPg();
        int fromIdx = logRequestSummary.getFromIdx();
        int toIdx = logRequestSummary.getToIdx();
        int searchResultsPerPg = logRequestSummary.getSearchResultsPerPg();

        int totalSearchResultRows = getTotalSearchRows(logRequestSummary);

        log.debug("Before setting up of values - currentPg: " + currentPg + " totalPg: " + totalPg + " fromIdx: "
            + fromIdx + " toIdx: " + toIdx + " searchResultsPerPg: " + searchResultsPerPg + " totalSearchResultRows: "
            + totalSearchResultRows + " navigation: " + navigation + " operationType: " + operationType);

        if (totalSearchResultRows % searchResultsPerPg == 0)
            totalPg = (totalSearchResultRows / searchResultsPerPg);
        else
            totalPg = (totalSearchResultRows / searchResultsPerPg) + 1;

        // menu invoke, search, update, release, delete - here page should remain same
        if (UsageCommonValidations.isEmptyOrNull(navigation)) {
            // menu invoke
            if (UsageCommonValidations.isEmptyOrNull(operationType)) {
                currentPg = 1;
            }
            // search, update, release, delete
            else {
                if ("SEARCH".equalsIgnoreCase(operationType)) {
                    currentPg = 1;

                } else {
                }
                // no change in page or index
            }
        }
        // page navigation - only page should change
        else {

            if ("FIRST".equalsIgnoreCase(navigation)) {
                currentPg = 1;
                // log.debug(">4444444444");
            }
            if ("LAST".equalsIgnoreCase(navigation)) {
                if (totalSearchResultRows % searchResultsPerPg == 0)
                    currentPg = (totalSearchResultRows / searchResultsPerPg);
                else
                    currentPg = (totalSearchResultRows / searchResultsPerPg) + 1;
                // log.debug(">5555555555");
            }
            if ("NEXT".equalsIgnoreCase(navigation)) {
                if (currentPg < totalPg) {
                    currentPg = currentPg + 1;
                    // log.debug(">6666666666");
                }
                // log.debug(">7777777777777");
            }
            if ("PREV".equalsIgnoreCase(navigation)) {
                if (currentPg > 1) {
                    currentPg = currentPg - 1;
                    // log.debug(">88888888888888");
                }
                // log.debug(">99999999999");
            }

            if ("GOTOPAGE".equalsIgnoreCase(navigation)) {
                if (currentPg < totalPg)
                    currentPg = currentPg;
                else
                    currentPg = totalPg;
                // log.debug(">1010101010101010");
            }
        }

        log.debug("After setting up of values - currentPg: " + currentPg + " totalPg: " + totalPg + " fromIdx: "
            + fromIdx + " toIdx: " + toIdx + " searchResultsPerPg: " + searchResultsPerPg + " totalSearchResultRows: "
            + totalSearchResultRows);

        fromIdx = ((currentPg - 1) * searchResultsPerPg) + 1;
        toIdx = (fromIdx + searchResultsPerPg) - 1;

        logRequestSummary.setCurrentPg(currentPg);
        logRequestSummary.setTotalPg(totalPg);
        logRequestSummary.setFromIdx(fromIdx);
        logRequestSummary.setToIdx(toIdx);

        if (currentPg == 1)
            logRequestSummary.setFirstPg("Y");
        else
            logRequestSummary.setFirstPg("N");

        if (currentPg >= totalPg)
            logRequestSummary.setLastPg("Y");
        else
            logRequestSummary.setLastPg("N");

        log.debug("firstPg: " + logRequestSummary.getFirstPg() + " lastPg: " + logRequestSummary.getLastPg());

        log.debug("Entering  UsageDAO - setNavigationInfo method ");

        return logRequestSummary;

    }

    private String canDeleteLogRequest(String filterTargetSurveyYearQuarter) throws PrepSystemException {

        String canDeleteLogRequest = null;
        try {

            SqlRowSet sequenceRs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_LOGREQUEST_CAN_DELETE,
                new Object[] {Integer.parseInt(filterTargetSurveyYearQuarter)});

            while (sequenceRs.next()) {
                canDeleteLogRequest = sequenceRs.getString("Count");
            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);

        }
        log.debug("canDeleteLogRequest : " + canDeleteLogRequest);
        return (canDeleteLogRequest);
    }

    public int getTotalSearchRows(LogRequestSummary logRequestSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAO - getTotalSearchRows method ");

        String filterSupplierCode = logRequestSummary.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logRequestSummary.getFiltertargetSurveyYearQuarter();
        String filterStaus = logRequestSummary.getFilterStaus();
        String operationType = logRequestSummary.getOperationType();

        int fromIndex = 1;
        int toIndex = 10;

        int i = 0;
        String[] param = null;
        int paramlength = 0;

        String query = UsageQueries.GET_LOGREQUEST_SUMMARY_LIST;

        if (operationType == null) {
            query += " AND A.TGTSURVYEARQTR = ? ";
        } else {
            if (!ValidationUtils.isEmptyOrNull(filterSupplierCode)) {
                query += " AND A.CALL = ? ";
                i++;
            }
            if (!ValidationUtils.isEmptyOrNull(filterTargetSurveyYearQuarter)) {
                query += " AND A.TGTSURVYEARQTR = ? ";
                i++;
            }
            if (!ValidationUtils.isEmptyOrNull(filterStaus)) {
                query += " AND A.LOG_STATUS = ? ";
                i++;
            }

            paramlength = i;
            i = 0;
            if (paramlength > 0) {
                param = new String[paramlength];

                if (!ValidationUtils.isEmptyOrNull(filterSupplierCode)) {
                    param[i] = filterSupplierCode.toUpperCase();
                    i++;
                }
                if (!ValidationUtils.isEmptyOrNull(filterTargetSurveyYearQuarter)) {
                    param[i] = filterTargetSurveyYearQuarter;
                    i++;
                }
                if (!ValidationUtils.isEmptyOrNull(filterStaus)) {
                    param[i] = filterStaus;
                    i++;
                }
            }

        }

        query += UsageQueries.GET_LOGREQUEST_SUMMARY_LIST_ORDER_BY;

        StringBuilder sb = new StringBuilder(query);

        String activeSurveyYearQuarter = getActiveSurveyYearQuarter();
        int searchResultRows = 0;

        try {

            i = 1;

            ArrayList<Object> params = new ArrayList<>();

            if (operationType == null) {
                params.add(activeSurveyYearQuarter);
                params.add(fromIndex);
                params.add(toIndex);

            } else {

                for (int a = 0; a < param.length; a++) {
                    params.add(param[a]);
                    log.debug("param: " + i + ": " + param[a]);
                    i++;
                }
                params.add(fromIndex);
                params.add(toIndex);
            }

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(sb.toString(), params.toArray());

            if (rs.next()) {
                searchResultRows = Integer.parseInt(rs.getString("totalrows"));
            }

            log.debug("searchResultRows Found: " + searchResultRows);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getTotalSearchRows method ");

        return searchResultRows;
    }

    public LogRequestSummary updateLogRequestSummary(LogRequestSummary logRequestSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAO - updateLogRequestSummary method ");

        String filterSupplierCode = logRequestSummary.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logRequestSummary.getFiltertargetSurveyYearQuarter();
        String filterStaus = logRequestSummary.getFilterStaus();
        String operationType = logRequestSummary.getOperationType();

        log.debug("filterSupplierCode: " + filterSupplierCode + "filterTargetSurveyYearQuarter: "
            + filterTargetSurveyYearQuarter + "filterStaus: " + filterStaus + "operationType: " + operationType);

        String[] selections = logRequestSummary.getSelections();

        if (operationType.equalsIgnoreCase("UPDATE")) {
            updateLogRequestHdr(selections);
        }
        if (operationType.equalsIgnoreCase("DELETE")) {
            deleteLogRequestHdr(filterTargetSurveyYearQuarter);
        }
        if (operationType.equalsIgnoreCase("RELEASE")) {
            releaseLogRequest(selections);
        }

        logRequestSummary = getLogRequestSummary(logRequestSummary);
        return logRequestSummary;
    }

    private void updateLogRequestHdr(String[] selections) throws PrepSystemException {

        log.debug("Entering  UsageDAO - updateLogRequestHdr method ");

        String[] tokens = null;

        if (selections != null && selections.length > 0) {
            int i = 0;

            for (String index : selections) {

                if (ValidationUtils.isEmptyOrNull(index)) {
                    continue;
                }
                i++;

                log.debug("selectedStrs: " + i + " element: " + index);
                tokens = index.split("##", -1);
                log.debug("logReqId: " + tokens[0]);

                if (null != tokens[1])
                    log.debug("start date: " + tokens[1]);

                if (null != tokens[2])
                    log.debug("end date: " + tokens[2]);

                if (null != tokens[3])
                    log.debug("log notes: " + tokens[3]);

                if (null != tokens[4])
                    log.debug("Received Date: " + tokens[4]);

                try {
                    StringBuilder sqlStr = new StringBuilder(UsageQueries.UPDATE_LOGREQUEST_HDR);

                    ArrayList<Object> params = new ArrayList<>();
                    params.add(tokens[1]);
                    params.add(tokens[2]);
                    params.add(tokens[3]);
                    params.add(tokens[4]);
                    params.add(tokens[0]);

                    ascapJdbcTemplate.update(sqlStr.toString(), params.toArray());

                } catch (Exception e) {
                    log.debug(e);
                    throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
                }
            }
        }
        log.debug("Exiting  UsageDAO - updateLogRequestHdr method ");
    }

    private void deleteLogRequestHdr(String filterTargetSurveyYearQuarter) throws PrepSystemException {

        log.debug("Entering  UsageDAO - deleteLogRequestHdr method ");

        try {
            StringBuilder sqlStr = new StringBuilder(UsageQueries.DELETE_LOGREQUEST_HDR);

            ascapJdbcTemplate.update(sqlStr.toString(), new Object[] {Integer.parseInt(filterTargetSurveyYearQuarter)});

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting  UsageDAO - deleteLogRequestHdr method ");
    }

    private void releaseLogRequest(String[] selections) throws PrepSystemException {

        log.debug("Entering  UsageDAO - releaseLogRequest method ");

        String[] tokens = null;

        if (selections != null && selections.length > 0) {
            int i = 0;

            for (String index : selections) {

                if (ValidationUtils.isEmptyOrNull(index)) {
                    continue;
                }
                i++;

                log.debug("selectedStrs: " + i + " element: " + index);
                tokens = index.split("##", -1);
                log.debug("logReqId: " + tokens[0]);

                try {
                    StringBuilder sqlStr = new StringBuilder(UsageQueries.UPDATE_LOGREQUESTHDR_FOR_RELEASE);
                    ascapJdbcTemplate.update(sqlStr.toString(), tokens[0]);

                    StringBuilder sqlStrDtl = new StringBuilder(UsageQueries.UPDATE_LOGREQUESTDTL_FOR_RELEASE);
                    ascapJdbcTemplate.update(sqlStrDtl.toString(), tokens[0]);

                } catch (Exception e) {
                    log.debug(e);
                    throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
                }
            }

            log.debug("calling release log req proc ");
            try {
                ascapJdbcTemplate.execute(UsageQueries.CALL_RELEASE_LOGREQ_PROC);
            } catch (Exception e) {
                log.debug(e);
                throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
            }
        }
        log.debug("Exiting  UsageDAO - releaseLogRequest method ");
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#getCatalogManualMatchList(apmPerformanceBulkRequestList)
     */

    public ApmPerformanceBulkRequestList getCatalogManualMatchList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {

        log.debug("Entering UsageDAOImpl - getCatalogManualMatchList method ");
        if (apmPerformanceBulkRequestList == null) {
            log.debug("Input VOB is null... Returning null");
        }

        List<Object> col = new ArrayList<>();
        ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;

        ArrayList<String> params = new ArrayList<>();
        StringBuilder selectQry = new StringBuilder("");

        boolean contextSearchExists = false;

        try {

            selectQry = new StringBuilder(UsageQueries.GET_CATALOG_MATCH_SELECT);
            if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT_INITIAL_SPLR_COL);
            } else {
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT_INITIAL_NSPLR_COL);
            }
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT_INITIAL_COMMON_COL);
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_FROM);
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_PAREN_OPEN);

            selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT);
            if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT_INNER_SPLR_COL);
            }
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT_INNER_COMMON_COL);

            selectQry.append(UsageQueries.GET_CATALOG_MATCH_FROM);
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_PAREN_OPEN);

            if ((!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterPerformerName())
                && "CNTS".equals(apmPerformanceBulkRequestList.getPerformerSearchType()))
                || (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterWorkTitle())
                    && "CNTS".equals(apmPerformanceBulkRequestList.getWorkTitleSearchType()))) {
                contextSearchExists = true;
                log.debug("Contains Begins search found.............");
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_CONTEXT_START);
                int contextSearchNr = 0;
                if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterWorkTitle())
                    && "CNTS".equals(apmPerformanceBulkRequestList.getWorkTitleSearchType())) {
                    contextSearchNr++;
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_TTL_CONTAINS_SEARCH);
                    params.add(apmPerformanceBulkRequestList.getFilterWorkTitle());
                }
                if (contextSearchNr > 0
                    && (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterPerformerName())
                        && "CNTS".equals(apmPerformanceBulkRequestList.getPerformerSearchType()))) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_INTERSECT);
                }

                if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterPerformerName())
                    && "CNTS".equals(apmPerformanceBulkRequestList.getPerformerSearchType())) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_PFR_CONTAINS_SEARCH);
                    params.add(apmPerformanceBulkRequestList.getFilterPerformerName());
                }
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_CONTEXT_END);
            }
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT);
            if (!"DEL".equals(apmPerformanceBulkRequestList.getFilterMatchStatusCode())
                && !"MATCH".equals(apmPerformanceBulkRequestList.getFilterMatchStatusCode())) {
                selectQry.append(UsageQueries.ORACLE_INDEX_HINT_CATALOG);
            }

            if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT_INNER_INNER_SPLR_COL);
            }
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT_INNER_INNER_COMMON_COL);

            if (contextSearchExists) {
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_CONTEXT_JOIN);
            }
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_WHERE_CLAUSE);

            if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterSupplierCode())) {
                selectQry.append(" and A.SUPP_CODE = ? ");
                params.add(apmPerformanceBulkRequestList.getFilterSupplierCode());
            }

            if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterAssignedToUser())) {
                if ("NONE".equals(apmPerformanceBulkRequestList.getFilterAssignedToUser())) {
                    selectQry.append(" and A.ASG_USR_ID IS NULL ");
                } else {
                    selectQry.append(" and A.ASG_USR_ID = ? ");
                    params.add(apmPerformanceBulkRequestList.getFilterAssignedToUser());
                }
            }

            log.debug("filterMatchedByUser: " + apmPerformanceBulkRequestList.getFilterMatchedByUser());

            // MatchedBy filter
            if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterMatchedByUser())) {
                selectQry.append(" and A.MAN_MATCH_USR_ID = ? and A.MAN_MATCH_DT > trunc(sysdate) ");
                params.add(apmPerformanceBulkRequestList.getFilterMatchedByUser());
            }

            if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterPerformerName())
                && "BGNS".equals(apmPerformanceBulkRequestList.getPerformerSearchType())) {
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_PFR_BEGINS_SEARCH);
                params.add(apmPerformanceBulkRequestList.getFilterPerformerName());
            }

            if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterWorkTitle())
                && "BGNS".equals(apmPerformanceBulkRequestList.getWorkTitleSearchType())) {
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_TTL_BEGINS_SEARCH);
                params.add(apmPerformanceBulkRequestList.getFilterWorkTitle());
            }

            if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterMatchStatusCode())) {
                if ("DEL".equals(apmPerformanceBulkRequestList.getFilterMatchStatusCode())) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_MATCH_STATUS_DELETED);
                } else if ("MATCH".equals(apmPerformanceBulkRequestList.getFilterMatchStatusCode())) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_MATCH_STATUS_MATCHED);

                    // MatchedBy filter
                    if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterMatchedByUser())) {
                        selectQry.append(" and A.MAN_MATCH_USR_ID = ? and A.MAN_MATCH_DT > trunc(sysdate) ");
                        params.add(apmPerformanceBulkRequestList.getFilterMatchedByUser());
                    }
                } else if ("NMATCH".equals(apmPerformanceBulkRequestList.getFilterMatchStatusCode())) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_MATCH_STATUS_NOT_MATCHED);
                }

                else {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_MATCH_STATUS_NOT_MATCHED);
                }
                if (contextSearchExists) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_CONTEXT_WHERE_CLAUSE);
                }
            } else {
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_MATCH_STATUS_NOT_MATCHED);
                if (contextSearchExists) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_CONTEXT_WHERE_CLAUSE);
                }
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_UNION_ALL);
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT);
                if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT_INNER_INNER_SPLR_COL);
                }
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_SELECT_INNER_INNER_COMMON_COL);
                if (contextSearchExists) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_CONTEXT_JOIN);
                }
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_WHERE_CLAUSE);

                selectQry.append(UsageQueries.GET_CATALOG_MATCH_MATCH_STATUS_MATCHED_OR_DELETED);

                if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterSupplierCode())) {
                    selectQry.append(" and A.SUPP_CODE = ? ");
                    params.add(apmPerformanceBulkRequestList.getFilterSupplierCode());
                }
                if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterAssignedToUser())) {
                    if ("NONE".equals(apmPerformanceBulkRequestList.getFilterAssignedToUser())) {
                        selectQry.append(" and A.ASG_USR_ID IS NULL ");
                    } else {
                        selectQry.append(" and A.ASG_USR_ID = ? ");
                        params.add(apmPerformanceBulkRequestList.getFilterAssignedToUser());
                    }
                }
                // MatchedBy filter
                if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterMatchedByUser())) {
                    selectQry.append(" and A.MAN_MATCH_USR_ID = ? and A.MAN_MATCH_DT > trunc(sysdate) ");
                    params.add(apmPerformanceBulkRequestList.getFilterMatchedByUser());
                }
                if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterPerformerName())
                    && "BGNS".equals(apmPerformanceBulkRequestList.getPerformerSearchType())) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_PFR_BEGINS_SEARCH);
                    params.add(apmPerformanceBulkRequestList.getFilterPerformerName());
                }
                if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterWorkTitle())
                    && "BGNS".equals(apmPerformanceBulkRequestList.getWorkTitleSearchType())) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_TTL_BEGINS_SEARCH);
                    params.add(apmPerformanceBulkRequestList.getFilterWorkTitle());
                }
                if (contextSearchExists) {
                    selectQry.append(UsageQueries.GET_CATALOG_MATCH_CONTEXT_WHERE_CLAUSE);
                }
            }
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_PAREN_CLOSE);
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_GROUP_BY);
            if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                selectQry.append(UsageQueries.GET_CATALOG_MATCH_GROUP_BY_SPLR_COL);
            }
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_GROUP_BY_COMMON_COL);
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_PAREN_CLOSE);
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_ROWNUM_WHERE_CLAUSE);
            selectQry.append(UsageQueries.GET_CATALOG_MATCH_ORDER_CLAUSE);

            if (apmPerformanceBulkRequestList.getNavigationType() == null) {
                if (apmPerformanceBulkRequestList.getNumberOfRecordsFound() <= 0) {
                    apmPerformanceBulkRequestList.setNumberOfRecordsFound(10);
                } else {
                    apmPerformanceBulkRequestList
                        .setNumberOfRecordsFound(apmPerformanceBulkRequestList.getNumberOfRecordsFound());
                }
            } else {
                if (apmPerformanceBulkRequestList.getNumberOfRecordsFound() <= 0
                    && apmPerformanceBulkRequestList.getCurrentPageNumber() > 1) {
                    apmPerformanceBulkRequestList
                        .setCurrentPageNumber(apmPerformanceBulkRequestList.getCurrentPageNumber() - 1);
                    apmPerformanceBulkRequestList.setNumberOfRecordsFound(10);
                } else {
                    apmPerformanceBulkRequestList
                        .setNumberOfRecordsFound(apmPerformanceBulkRequestList.getNumberOfRecordsFound());
                }
            }
            int fromIndex = apmPerformanceBulkRequestList.getFromIndex();
            int toIndex = apmPerformanceBulkRequestList.getToIndexWithoutCount() - 1;
            log.debug("From Index: " + fromIndex);
            log.debug("To Index: " + toIndex);
            log.debug("apmPerformanceBulkRequestList getCurrentPageNumber:"
                + apmPerformanceBulkRequestList.getCurrentPageNumber());

            log.debug("Search Query:: \n" + selectQry);

            int paramCount1 = params.size();
            String paramVal = null;
            ArrayList<Object> params1 = new ArrayList<>();

            for (int i = 0; i < paramCount1; i++) {
                paramVal = params.get(i);
                params1.add(paramVal);
            }
            params1.add(fromIndex);
            params1.add(toIndex);
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(selectQry.toString(), params1.toArray());
            int totalRecordsFound = 0;
            boolean writerExists = false;
            while (rs.next()) {
                apmPerformanceBulkRequest = new ApmPerformanceBulkRequest();
                apmPerformanceBulkRequest.setSupplierCode(rs.getString("SUPP_CODE"));
                if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                    apmPerformanceBulkRequest.setProviderId(rs.getString("PROVIDER_ID"));
                }
                apmPerformanceBulkRequest.setPerformerName(rs.getString("PFR_NA"));
                apmPerformanceBulkRequest.setWorkTitle(rs.getString("WRK_TTL"));
                apmPerformanceBulkRequest.setWriterName(rs.getString("WRITER"));
                apmPerformanceBulkRequest.setWorkPerfCount(rs.getString("WRK_PERF_CNT"));
                apmPerformanceBulkRequest.setPlayCount(rs.getString("PLAY_CNT"));
                apmPerformanceBulkRequest.setOriginalWorkId(rs.getString("WRK_ID"));
                apmPerformanceBulkRequest.setWorkId(rs.getString("WRK_ID"));
                apmPerformanceBulkRequest.setManualMatchUserId(rs.getString("MAN_MATCH_USR_ID"));
                apmPerformanceBulkRequest.setDeleteFlag(rs.getString("DEL_FL"));
                apmPerformanceBulkRequest.setMultWorkId(rs.getString("MULT_WRK_ID"));
                apmPerformanceBulkRequest.setAssignedToUser(rs.getString("ASG_USR_ID"));
                apmPerformanceBulkRequest.setPriority(rs.getString("PRIORITY"));
                totalRecordsFound = rs.getInt("TOTALROWS");

                if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())) {
                    writerExists = true;
                }

                col.add(apmPerformanceBulkRequest);
            }
            apmPerformanceBulkRequestList.setNumberOfRecordsFound(totalRecordsFound);
            apmPerformanceBulkRequestList.setWriterExists(writerExists ? "Y" : "N");

        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);

        }

        apmPerformanceBulkRequestList.setSearchResults(col);

        log.debug("Exiting UsageDAOImpl - getCatalogManualMatchList method: No Of Results: " + col.size());
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#updateAssignedUsersToWorkPerf(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList updateAssignedUsersToWorkPerf(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {

        log.debug("Entering updateAssignedUsersToWorkPerf in UsageDAOImpl");
        if (apmPerformanceBulkRequestList == null || apmPerformanceBulkRequestList.getSearchResults() == null) {
            log.debug("Invalid Input Object...  Returning");
            return apmPerformanceBulkRequestList;
        }
        if (apmPerformanceBulkRequestList.getSearchResults().isEmpty()) {
            log.debug("Empty Input Collection...  Returning");
            return apmPerformanceBulkRequestList;
        }
        Collection<ApmPerformanceBulkRequest> noSuppWPNWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> noSuppNPWWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> noSuppNPNWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> noSuppWPWWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> suppWPNWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> suppNPWWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> suppNPNWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> suppWPWWCol = new ArrayList<>();

        if (!apmPerformanceBulkRequestList.getSearchResults().isEmpty()) {
            Iterator<Object> itrx = apmPerformanceBulkRequestList.getSearchResults().iterator();
            ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
            while (itrx.hasNext()) {
                apmPerformanceBulkRequest = (ApmPerformanceBulkRequest) itrx.next();
                if (ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getSupplierCode())) {
                    if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())) {
                        noSuppWPNWCol.add(apmPerformanceBulkRequest);
                    } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        noSuppNPWWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to noSuppNPWWCol");
                    } else if (ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        noSuppNPNWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to noSuppNPNWCol");
                    } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        noSuppWPWWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to noSuppNPNWCol");
                    }
                } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getSupplierCode())) {
                    if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())) {
                        suppWPNWCol.add(apmPerformanceBulkRequest);
                    } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        suppNPWWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to suppNPWWCol");
                    } else if (ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        suppNPNWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to suppNPNWCol");
                    } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        suppWPWWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to suppWPWWCol");
                    }
                }
            }
        }

        StringBuilder noSuppWPNW = new StringBuilder(
            "UPDATE APM_WRK_PERF A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.PFR_NA = ? AND A.WRITER IS NULL ");
        StringBuilder noSuppNPWW = new StringBuilder(
            "UPDATE APM_WRK_PERF A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.WRITER = ? AND A.PFR_NA IS NULL ");
        StringBuilder noSuppNPNW = new StringBuilder(
            "UPDATE APM_WRK_PERF A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.PFR_NA IS NULL AND A.WRITER IS NULL ");
        StringBuilder noSuppWPWW = new StringBuilder(
            "UPDATE APM_WRK_PERF A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.PFR_NA = ? ");

        StringBuilder suppWPNW = new StringBuilder(
            "UPDATE APM_WRK_PERF A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.PFR_NA = ? AND A.SUPP_CODE = ? AND A.WRITER IS NULL ");
        StringBuilder suppNPWW = new StringBuilder(
            "UPDATE APM_WRK_PERF A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.WRITER = ? AND A.SUPP_CODE = ? AND A.PFR_NA IS NULL ");
        StringBuilder suppNPNW = new StringBuilder(
            "UPDATE APM_WRK_PERF A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.SUPP_CODE = ? AND A.PFR_NA IS NULL AND A.WRITER IS NULL ");
        StringBuilder suppWPWW = new StringBuilder(
            "UPDATE APM_WRK_PERF A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.SUPP_CODE = ? AND A.PFR_NA = ? AND A.WRITER = ?  ");
        try {
            int targetSurvYearQtr = 0;
            String targetSurvYearQtrStr = "";
            try {
                targetSurvYearQtrStr = this.getActiveSurveyYearQuarter();
                targetSurvYearQtr = Integer.parseInt(targetSurvYearQtrStr);
            } catch (Exception e) {
                log.debug("ERROR:: Cannot convert active quarter to number " + targetSurvYearQtrStr);
                return apmPerformanceBulkRequestList;
            }

            if (!noSuppWPNWCol.isEmpty()) {
                log.debug("noSuppWPNWCol not empty " + noSuppWPNW.toString() + UsageQueries.ASSIGN_GROUP_PERFS_END);
                String qry = noSuppWPNW.toString() + UsageQueries.ASSIGN_GROUP_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = noSuppWPNWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[5];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getPerformerName();
                    params[4] = targetSurvYearQtr;

                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }

            if (!noSuppNPWWCol.isEmpty()) {
                log.debug("noSuppNPWWCol not empty");
                String qry = noSuppNPWW.toString() + UsageQueries.ASSIGN_GROUP_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = noSuppNPWWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[5];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getWriterName();
                    params[4] = targetSurvYearQtr;
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }

            if (!noSuppNPNWCol.isEmpty()) {
                log.debug("noSuppNPWWCol not empty");
                String qry = noSuppNPNW.toString() + UsageQueries.ASSIGN_GROUP_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = noSuppNPNWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {

                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[4];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = targetSurvYearQtr;
                    parametersList.add(params);
                    i++;

                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }
            if (!noSuppWPWWCol.isEmpty()) {
                log.debug("noSuppNPWWCol not empty");
                String qry = noSuppWPWW.toString() + UsageQueries.ASSIGN_GROUP_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = noSuppWPWWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[5];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getPerformerName();
                    params[4] = targetSurvYearQtr;
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }

            }
            if (!suppWPNWCol.isEmpty()) {
                log.debug("suppWPNWCol not empty " + suppWPNW.toString() + UsageQueries.ASSIGN_GROUP_PERFS_END);
                String qry = suppWPNW.toString() + UsageQueries.ASSIGN_GROUP_PERFS_END;

                Iterator<ApmPerformanceBulkRequest> itr = suppWPNWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {

                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[6];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getPerformerName();
                    params[4] = apmPerformanceBulkRequest.getSupplierCode();
                    params[5] = targetSurvYearQtr;

                    parametersList.add(params);
                    i++;

                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }

            if (!suppNPWWCol.isEmpty()) {
                log.debug("suppNPWWCol not empty");
                String qry = suppNPWW.toString() + UsageQueries.ASSIGN_GROUP_PERFS_END;

                Iterator<ApmPerformanceBulkRequest> itr = suppNPWWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();

                while (itr.hasNext()) {

                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[6];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getWriterName();
                    params[4] = apmPerformanceBulkRequest.getSupplierCode();
                    params[5] = targetSurvYearQtr;
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }

            if (!suppNPNWCol.isEmpty()) {
                log.debug("suppNPNWCol not empty");
                String qry = suppNPNW.toString() + UsageQueries.ASSIGN_GROUP_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = suppNPNWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[5];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getSupplierCode();
                    params[4] = targetSurvYearQtr;
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }

            if (!suppWPWWCol.isEmpty()) {
                log.debug("suppWPWWCol not empty");
                String qry = suppWPWW.toString() + UsageQueries.ASSIGN_GROUP_PERFS_END;

                Iterator<ApmPerformanceBulkRequest> itr = suppWPWWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[7];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getSupplierCode();
                    params[4] = apmPerformanceBulkRequest.getPerformerName();
                    params[5] = apmPerformanceBulkRequest.getWriterName();
                    params[6] = targetSurvYearQtr;
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }

            }
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);

        } finally {
            log.debug("Exting updateAssignedUsersToWorkPerf in UsageDAOImpl");
        }
        return apmPerformanceBulkRequestList;

    }

    /**
     * @see com.ascap.apm.common.database.usage.UsageDAOImpl#updateAssignedUsersToCatalogPerf(apmPerformanceBulkRequestList)
     */
    public ApmPerformanceBulkRequestList updateAssignedUsersToCatalogPerf(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {

        log.debug("Entering updateAssignedUsersToCatalogPerf in UsageDAOImpl");

        if (apmPerformanceBulkRequestList == null || apmPerformanceBulkRequestList.getSearchResults() == null) {
            log.debug("Invalid Input Object...  Returning");
            return apmPerformanceBulkRequestList;
        }

        if (apmPerformanceBulkRequestList.getSearchResults().isEmpty()) {
            log.debug("Empty Input Collection...  Returning");
            return apmPerformanceBulkRequestList;
        }
        Collection<ApmPerformanceBulkRequest> noSuppWPNWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> noSuppNPWWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> noSuppNPNWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> noSuppWPWWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> suppWPNWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> suppNPWWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> suppNPNWCol = new ArrayList<>();
        Collection<ApmPerformanceBulkRequest> suppWPWWCol = new ArrayList<>();
        if (!apmPerformanceBulkRequestList.getSearchResults().isEmpty()) {
            Iterator<Object> itrx = apmPerformanceBulkRequestList.getSearchResults().iterator();
            ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
            while (itrx.hasNext()) {
                apmPerformanceBulkRequest = (ApmPerformanceBulkRequest) itrx.next();

                if (ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getSupplierCode())) {

                    if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())) {
                        noSuppWPNWCol.add(apmPerformanceBulkRequest);
                    } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        noSuppNPWWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to noSuppNPWWCol");
                    } else if (ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        noSuppNPNWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to noSuppNPNWCol");
                    }

                    else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        noSuppWPWWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to noSuppNPNWCol");
                    }
                } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getSupplierCode())) {
                    if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())) {
                        suppWPNWCol.add(apmPerformanceBulkRequest);
                    } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        suppNPWWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to suppNPWWCol");
                    } else if (ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        suppNPNWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to suppNPNWCol");
                    } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())
                        && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
                        suppWPWWCol.add(apmPerformanceBulkRequest);
                        log.debug("Adding to suppWPWWCol");
                    }
                }
            }
        }
        StringBuilder noSuppWPNW = new StringBuilder(
            "UPDATE APM_CATALOG A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.PFR_NA = ? AND A.WRITER IS NULL ");
        StringBuilder noSuppNPWW = new StringBuilder(
            "UPDATE APM_CATALOG A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.WRITER = ? AND A.PFR_NA IS NULL ");
        StringBuilder noSuppNPNW = new StringBuilder(
            "UPDATE APM_CATALOG A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.PFR_NA IS NULL AND A.WRITER IS NULL ");
        StringBuilder noSuppWPWW = new StringBuilder(
            "UPDATE APM_CATALOG A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.PFR_NA = ? ");
        StringBuilder suppWPNW = new StringBuilder(
            "UPDATE APM_CATALOG A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.PFR_NA = ? AND A.SUPP_CODE = ? AND A.WRITER IS NULL ");
        StringBuilder suppNPWW = new StringBuilder(
            "UPDATE APM_CATALOG A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.WRITER = ? AND A.SUPP_CODE = ? AND A.PFR_NA IS NULL ");
        StringBuilder suppNPNW = new StringBuilder(
            "UPDATE APM_CATALOG A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.SUPP_CODE = ? AND A.PFR_NA IS NULL AND A.WRITER IS NULL ");
        StringBuilder suppWPWW = new StringBuilder(
            "UPDATE APM_CATALOG A SET ASG_USR_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.SUPP_CODE = ? AND A.PFR_NA = ? AND A.WRITER = ?  ");
        try {

            if (!noSuppWPNWCol.isEmpty()) {
                log.debug(
                    "noSuppWPNWCol not empty " + noSuppWPNW.toString() + UsageQueries.ASSIGN_GROUP_CATALOG_PERFS_END);
                String qry = noSuppWPNW.toString() + UsageQueries.ASSIGN_GROUP_CATALOG_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = noSuppWPNWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;

                List<Object[]> parametersList = new ArrayList<>();

                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[4];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getPerformerName();
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }

            if (!noSuppNPWWCol.isEmpty()) {
                log.debug("noSuppNPWWCol not empty");
                String qry = noSuppNPWW.toString() + UsageQueries.ASSIGN_GROUP_CATALOG_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = noSuppNPWWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[4];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getWriterName();
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }
            if (!noSuppNPNWCol.isEmpty()) {
                log.debug("noSuppNPWWCol not empty");
                String qry = noSuppNPNW.toString() + UsageQueries.ASSIGN_GROUP_CATALOG_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = noSuppNPNWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();

                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[3];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }
            if (!noSuppWPWWCol.isEmpty()) {
                log.debug("noSuppNPWWCol not empty");
                String qry = noSuppWPWW.toString() + UsageQueries.ASSIGN_GROUP_CATALOG_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = noSuppWPWWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[4];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getPerformerName();
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }
            if (!suppWPNWCol.isEmpty()) {
                log.debug("suppWPNWCol not empty " + suppWPNW.toString() + UsageQueries.ASSIGN_GROUP_CATALOG_PERFS_END);
                String qry = suppWPNW.toString() + UsageQueries.ASSIGN_GROUP_CATALOG_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = suppWPNWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[5];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getPerformerName();
                    params[4] = apmPerformanceBulkRequest.getSupplierCode();
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }
            if (!suppNPWWCol.isEmpty()) {
                log.debug("suppNPWWCol not empty");
                String qry = suppNPWW.toString() + UsageQueries.ASSIGN_GROUP_CATALOG_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = suppNPWWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {

                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[5];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getWriterName();
                    params[4] = apmPerformanceBulkRequest.getSupplierCode();
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }

            if (!suppNPNWCol.isEmpty()) {
                log.debug("suppNPNWCol not empty");
                String qry = suppNPNW.toString() + UsageQueries.ASSIGN_GROUP_CATALOG_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = suppNPNWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                Object[] params = null;
                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    params = new Object[4];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getSupplierCode();
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }
            if (!suppWPWWCol.isEmpty()) {
                log.debug("suppWPWWCol not empty");
                String qry = suppWPWW.toString() + UsageQueries.ASSIGN_GROUP_CATALOG_PERFS_END;
                Iterator<ApmPerformanceBulkRequest> itr = suppWPWWCol.iterator();
                ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
                int i = 0;
                List<Object[]> parametersList = new ArrayList<>();
                while (itr.hasNext()) {
                    apmPerformanceBulkRequest = itr.next();
                    Object[] params = new Object[6];
                    params[0] = apmPerformanceBulkRequest.getAssignedToUser();
                    params[1] = apmPerformanceBulkRequestList.getUserId();
                    params[2] = apmPerformanceBulkRequest.getWorkTitle();
                    params[3] = apmPerformanceBulkRequest.getSupplierCode();
                    params[4] = apmPerformanceBulkRequest.getPerformerName();
                    params[5] = apmPerformanceBulkRequest.getWriterName();
                    parametersList.add(params);
                    i++;
                }
                if (i > 0) {
                    ascapJdbcTemplate.batchUpdate(qry, parametersList);
                }
            }
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        }
        log.debug("Exting updateAssignedUsersToCatalogPerf in UsageDAOImpl");
        return apmPerformanceBulkRequestList;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#getActiveSurveyYearQuarter(ascapJdbcTemplate)
     */
    private String getActiveSurveyYearQuarter(JdbcTemplate ascapJdbcTemplate) throws PrepSystemException {

        String activeSurveyYearQuarter = null;
        try {
            SqlRowSet sequenceRs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_ACTIVE_SURVEY_YEAR_QTR);
            while (sequenceRs.next()) {
                activeSurveyYearQuarter = sequenceRs.getString("TGTSURVYEARQTR");
            }
        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }
        log.debug("Active Survey Year Quarter : " + activeSurveyYearQuarter);
        return (activeSurveyYearQuarter);
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#populateSamplingMusicUsers(ascapJdbcTemplate, activeSurveyYear,
     *      userId)
     */
    private void populateSamplingMusicUsers(JdbcTemplate asJdbcTemplate, String activeSurveyYear, String userId)
        throws PrepSystemException {

        log.debug("Entering UsageDAOImpl - populateSamplingMusicUsers method");
        try {
            SqlRowSet rs = asJdbcTemplate.queryForRowSet(UsageQueries.SAMPLING_MUS_USERS_FROM_MUSUSER_CNTRL,
                new Object[] {activeSurveyYear});

            while (rs.next()) {
                SqlRowSet rs1 = asJdbcTemplate.queryForRowSet(" SELECT SAMP_REQ_ID.NEXTVAL FROM DUAL");
                String id = "1";
                if (rs1.next()) {
                    id = rs1.getString(1);
                }
                ArrayList<Object> params1 = new ArrayList<>();
                params1.add(id);
                params1.add(rs.getString("MUS_USER"));
                params1.add(rs.getString("INIT_NUM_SETS"));
                params1.add(null);
                params1.add("PE");
                params1.add(activeSurveyYear);
                params1.add(userId);
                params1.add(null);
                asJdbcTemplate.update(UsageQueries.INSERT_SAMP_REQ, params1.toArray());

            }
            // insert MUS_USER_TYP_CDE for NIELSEN / Radio
            log.debug("Now populating MUS_USER_TYP_CDE");
            SqlRowSet rs2 = asJdbcTemplate.queryForRowSet(UsageQueries.SAMPLING_MUS_USERS_FROM_MVW_MUS_USER_TYP,
                new Object[] {activeSurveyYear});

            while (rs2.next()) {
                SqlRowSet rs3 = asJdbcTemplate.queryForRowSet(" SELECT SAMP_REQ_ID.NEXTVAL FROM DUAL");
                String id = "1";
                if (rs3.next()) {
                    id = rs3.getString(1);
                }
                ArrayList<Object> params2 = new ArrayList<>();
                params2.add(id);
                params2.add(rs2.getString("MUS_USER_TYP_CDE"));
                params2.add(rs2.getString("INIT_NUM_SETS"));
                params2.add(null);
                params2.add("PE");
                params2.add(activeSurveyYear);
                params2.add(userId);
                params2.add(null);
                params2.add("NIELSEN");
                params2.add("NIELSEN-" + rs2.getString("MUS_USER_TYP_CDE"));
                asJdbcTemplate.update(UsageQueries.INSERT_SAMP_REQ_MUS_USER_TYP_CDE, params2.toArray());
            }
        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting UsageDAOImpl - populateSamplingMusicUsers method");
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#getSamplingSummary(samplingSummary)
     */
    public SamplingSummary getSamplingSummary(SamplingSummary samplingSummary) throws PrepSystemException {

        log.debug("Entering public getSamplingSummary method in UsageDAOImpl");

        StringBuilder sb = new StringBuilder(UsageQueries.GET_SAMPLING_SUMMARY_LIST);
        SamplingSummary outSamplingDetails = new SamplingSummary();
        SamplingRequest samplingRequest;
        List<Object> col = new ArrayList<>();
        try {
            String activeSurveyYearQuarter = getActiveSurveyYearQuarter(ascapJdbcTemplate);
            populateSamplingMusicUsers(ascapJdbcTemplate, activeSurveyYearQuarter, samplingSummary.getUserId());
            String hasActiveSamplingProcess = "N";
            if (ValidationUtils.isEmptyOrNull(activeSurveyYearQuarter)) {
                log.error("Active Target Survey Year Quarter is NULL");
                return samplingSummary;
            }
            log.debug("Executing Query : " + sb.toString());
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(sb.toString(),
                new Object[] {activeSurveyYearQuarter, activeSurveyYearQuarter});

            String prevMusUser = "";
            String currMusUser = "";
            while (rs.next()) {
                currMusUser = rs.getString("MUS_USER");
                if (!currMusUser.equals(prevMusUser)) {
                    samplingRequest = new SamplingRequest();
                    samplingRequest.setCallLetter(rs.getString("MUS_USER"));
                    samplingRequest.setLoadStatus(rs.getString("LOAD_STA"));
                    samplingRequest.setManMatchIndicator(rs.getString("MAN_MATCH_IND"));
                    samplingRequest.setProcessStartTime(rs.getString("PRC_STT_TM"));
                    samplingRequest.setProcessEndTime(rs.getString("PRC_END_TM"));
                    samplingRequest.setStatusCode(rs.getString("STA_CDE"));
                    samplingRequest.setStatusDescription(rs.getString("STA_DESC"));
                    samplingRequest.setStepDescription(rs.getString("STEP_DESC"));
                    samplingRequest.setStepCode(rs.getString("STEP_CDE"));
                    samplingRequest.setSamplingRequestId(rs.getString("SAMP_REQ_ID"));
                    log.debug("request id: " + rs.getString("SAMP_REQ_ID") + " status: " + rs.getString("STA_CDE")
                        + " step: " + rs.getString("STEP_CDE"));

                    samplingRequest.setSamplingStatusCode("PE");

                    if ("CO".equals(samplingRequest.getStatusCode())) {
                        if ("L3".equals(samplingRequest.getStepCode())) {
                            samplingRequest.setSamplingStatusCode("CO");
                        } else {
                            samplingRequest.setSamplingStatusCode("PR");
                        }
                    } else {
                        if (!"PE".equals(samplingRequest.getStatusCode())) {
                            if ("CA".equals(samplingRequest.getStatusCode())) {
                                samplingRequest.setSamplingStatusCode("CA");
                            } else if ("AB".equals(samplingRequest.getStatusCode())) {
                                samplingRequest.setSamplingStatusCode("AB");
                            } else if ("BP".equals(samplingRequest.getStatusCode())) {
                                samplingRequest.setSamplingStatusCode("BP");
                            } else if ("BD".equals(samplingRequest.getStatusCode())) {
                                samplingRequest.setSamplingStatusCode("BD");
                            } else {
                                samplingRequest.setSamplingStatusCode("PR");
                            }
                        }
                    }
                    samplingRequest.setTargetSurveyYearQuarter(rs.getString("TGTSURVYEARQTR"));
                    samplingRequest.setPlayCountRanges(rs.getString("INIT_NUM_SETS"));
                    samplingRequest.setMessage(rs.getString("MSG"));

                    log.debug("StatusCode: " + samplingRequest.getStatusCode());
                    if (!"PE".equals(samplingRequest.getStatusCode()) && !"CO".equals(samplingRequest.getStatusCode())
                        && !"CA".equals(samplingRequest.getStatusCode())
                        && !"BD".equals(samplingRequest.getStatusCode())) {
                        hasActiveSamplingProcess = "Y";
                    }
                    if (("L1".equals(samplingRequest.getStepCode()) || "L2".equals(samplingRequest.getStepCode()))
                        && !"PE".equals(samplingRequest.getStatusCode())) {
                        hasActiveSamplingProcess = "Y";
                    }
                    col.add(samplingRequest);
                    prevMusUser = currMusUser;
                }
            }
            outSamplingDetails.setSearchResults(col);
            outSamplingDetails.setTargetSurveyYearQuarter(activeSurveyYearQuarter);
            outSamplingDetails.setHasActiveSamplingProcess(hasActiveSamplingProcess);

        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting UsageDAOImpl- getSamplingSummary method ");
        return outSamplingDetails;
    }

    public ApmPerformanceBulkRequestList getApmPerfBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering UsageDAOImpl - ApmPerformanceBulkRequestList method ");
        if (apmPerformanceBulkRequestList == null) {
            log.debug("Input VOB is null... Returning null");
        }
        log.debug("Input Criteria : Status: " + apmPerformanceBulkRequestList.getFilterMatchStatusCode() + ", PFR: "
            + apmPerformanceBulkRequestList.getFilterPerformerName() + ", SUPP: "
            + apmPerformanceBulkRequestList.getFilterSupplierCode() + ", TITLE:"
            + apmPerformanceBulkRequestList.getFilterWorkTitle());

        List<Object> col = new ArrayList<>();
        ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
        ArrayList<String> params = new ArrayList<>();
        StringBuilder selectQry = new StringBuilder("");
        boolean contextSearchExists = false;

        try {

            String activeSurveyYearQuarter = getActiveSurveyYearQuarter();
            if (ValidationUtils.isEmptyOrNull(activeSurveyYearQuarter)) {
                return apmPerformanceBulkRequestList;
            }
            selectQry = new StringBuilder(UsageQueries.GET_WRK_MATCH_SELECT);
            if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT_INITIAL_SPLR_COL);
            } else {
                selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT_INITIAL_NSPLR_COL);
            }
            selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT_INITIAL_COMMON_COL);
            selectQry.append(UsageQueries.GET_WRK_MATCH_FROM);
            selectQry.append(UsageQueries.GET_WRK_MATCH_PAREN_OPEN);
            selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT);
            if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT_INNER_SPLR_COL);
            }
            selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT_INNER_COMMON_COL);
            selectQry.append(UsageQueries.GET_WRK_MATCH_FROM);
            selectQry.append(UsageQueries.GET_WRK_MATCH_PAREN_OPEN);
            if ((!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterPerformerName())
                && "CNTS".equals(apmPerformanceBulkRequestList.getPerformerSearchType()))
                || (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterWorkTitle())
                    && "CNTS".equals(apmPerformanceBulkRequestList.getWorkTitleSearchType()))) {
                contextSearchExists = true;
                log.debug("Contains Begins search found.............");
                selectQry.append(UsageQueries.GET_WRK_MATCH_CONTEXT_START);
                int contextSearchNr = 0;
                if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterWorkTitle())
                    && "CNTS".equals(apmPerformanceBulkRequestList.getWorkTitleSearchType())) {
                    contextSearchNr++;
                    selectQry.append(UsageQueries.GET_WRK_MATCH_TTL_CONTAINS_SEARCH);
                    params.add(apmPerformanceBulkRequestList.getFilterWorkTitle());
                    selectQry.append(activeSurveyYearQuarter + " ");
                }
                if (contextSearchNr > 0
                    && (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterPerformerName())
                        && "CNTS".equals(apmPerformanceBulkRequestList.getPerformerSearchType()))) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_INTERSECT);
                }
                if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterPerformerName())
                    && "CNTS".equals(apmPerformanceBulkRequestList.getPerformerSearchType())) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_PFR_CONTAINS_SEARCH);
                    params.add(apmPerformanceBulkRequestList.getFilterPerformerName());
                    selectQry.append(activeSurveyYearQuarter + " ");
                }
                selectQry.append(UsageQueries.GET_WRK_MATCH_CONTEXT_END);
            }
            selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT);
            if (!"DEL".equals(apmPerformanceBulkRequestList.getFilterMatchStatusCode())
                && !"MATCH".equals(apmPerformanceBulkRequestList.getFilterMatchStatusCode())) {
                selectQry.append(UsageQueries.ORACLE_INDEX_HINT);
            }
            if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT_INNER_INNER_SPLR_COL);
            }
            selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT_INNER_INNER_COMMON_COL);
            if (contextSearchExists) {
                selectQry.append(UsageQueries.GET_WRK_MATCH_CONTEXT_JOIN);
            }
            selectQry.append(UsageQueries.GET_WRK_MATCH_WHERE_CLAUSE);
            selectQry.append(activeSurveyYearQuarter + " ");
            if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterSupplierCode())) {
                selectQry.append(" and A.SUPP_CODE = ? ");
                params.add(apmPerformanceBulkRequestList.getFilterSupplierCode());
            }
            if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterAssignedToUser())) {
                if ("NONE".equals(apmPerformanceBulkRequestList.getFilterAssignedToUser())) {
                    selectQry.append(" and A.ASG_USR_ID IS NULL ");
                } else {
                    selectQry.append(" and A.ASG_USR_ID = ? ");
                    params.add(apmPerformanceBulkRequestList.getFilterAssignedToUser());
                }
            }
            log.debug("filterMatchedByUser: " + apmPerformanceBulkRequestList.getFilterMatchedByUser());
            if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterMatchedByUser())) {
                selectQry.append(" and A.MAN_MATCH_USR_ID = ? and A.MAN_MATCH_DT > trunc(sysdate) ");
                params.add(apmPerformanceBulkRequestList.getFilterMatchedByUser());
            }
            if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterPerformerName())
                && "BGNS".equals(apmPerformanceBulkRequestList.getPerformerSearchType())) {
                selectQry.append(UsageQueries.GET_WRK_MATCH_PFR_BEGINS_SEARCH);
                params.add(apmPerformanceBulkRequestList.getFilterPerformerName());
            }
            if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterWorkTitle())
                && "BGNS".equals(apmPerformanceBulkRequestList.getWorkTitleSearchType())) {
                selectQry.append(UsageQueries.GET_WRK_MATCH_TTL_BEGINS_SEARCH);
                params.add(apmPerformanceBulkRequestList.getFilterWorkTitle());
            }
            if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterMatchStatusCode())) {
                if ("DEL".equals(apmPerformanceBulkRequestList.getFilterMatchStatusCode())) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_MATCH_STATUS_DELETED);
                } else if ("MATCH".equals(apmPerformanceBulkRequestList.getFilterMatchStatusCode())) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_MATCH_STATUS_MATCHED);
                    if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterMatchedByUser())) {
                        selectQry.append(" and A.MAN_MATCH_USR_ID = ? and A.MAN_MATCH_DT > trunc(sysdate) ");
                        params.add(apmPerformanceBulkRequestList.getFilterMatchedByUser());
                    }
                } else if ("NMATCH".equals(apmPerformanceBulkRequestList.getFilterMatchStatusCode())) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_MATCH_STATUS_NOT_MATCHED);
                } else {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_MATCH_STATUS_NOT_MATCHED);
                }
                if (contextSearchExists) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_CONTEXT_WHERE_CLAUSE);
                }
            } else {
                selectQry.append(UsageQueries.GET_WRK_MATCH_MATCH_STATUS_NOT_MATCHED);
                if (contextSearchExists) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_CONTEXT_WHERE_CLAUSE);
                }
                selectQry.append(UsageQueries.GET_WRK_MATCH_UNION_ALL);
                selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT);
                if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT_INNER_INNER_SPLR_COL);
                }
                selectQry.append(UsageQueries.GET_WRK_MATCH_SELECT_INNER_INNER_COMMON_COL);
                if (contextSearchExists) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_CONTEXT_JOIN);
                }
                selectQry.append(UsageQueries.GET_WRK_MATCH_WHERE_CLAUSE);
                selectQry.append(activeSurveyYearQuarter + " ");
                selectQry.append(UsageQueries.GET_WRK_MATCH_MATCH_STATUS_MATCHED_OR_DELETED);
                if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterSupplierCode())) {
                    selectQry.append(" and A.SUPP_CODE = ? ");
                    params.add(apmPerformanceBulkRequestList.getFilterSupplierCode());
                }
                if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterAssignedToUser())) {
                    if ("NONE".equals(apmPerformanceBulkRequestList.getFilterAssignedToUser())) {
                        selectQry.append(" and A.ASG_USR_ID IS NULL ");
                    } else {
                        selectQry.append(" and A.ASG_USR_ID = ? ");
                        params.add(apmPerformanceBulkRequestList.getFilterAssignedToUser());
                    }
                }
                if (!UsageCommonValidations.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterMatchedByUser())) {
                    selectQry.append(" and A.MAN_MATCH_USR_ID = ? and A.MAN_MATCH_DT > trunc(sysdate) ");
                    params.add(apmPerformanceBulkRequestList.getFilterMatchedByUser());
                }
                if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterPerformerName())
                    && "BGNS".equals(apmPerformanceBulkRequestList.getPerformerSearchType())) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_PFR_BEGINS_SEARCH);
                    params.add(apmPerformanceBulkRequestList.getFilterPerformerName());
                }
                if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getFilterWorkTitle())
                    && "BGNS".equals(apmPerformanceBulkRequestList.getWorkTitleSearchType())) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_TTL_BEGINS_SEARCH);
                    params.add(apmPerformanceBulkRequestList.getFilterWorkTitle());
                }
                if (contextSearchExists) {
                    selectQry.append(UsageQueries.GET_WRK_MATCH_CONTEXT_WHERE_CLAUSE);
                }
            }
            selectQry.append(UsageQueries.GET_WRK_MATCH_PAREN_CLOSE);
            selectQry.append(UsageQueries.GET_WRK_MATCH_GROUP_BY);
            if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                selectQry.append(UsageQueries.GET_WRK_MATCH_GROUP_BY_SPLR_COL);
            }
            selectQry.append(UsageQueries.GET_WRK_MATCH_GROUP_BY_COMMON_COL);
            selectQry.append(UsageQueries.GET_WRK_MATCH_PAREN_CLOSE);
            selectQry.append(UsageQueries.GET_WRK_MATCH_ROWNUM_WHERE_CLAUSE);
            selectQry.append(UsageQueries.GET_WRK_MATCH_ORDER_CLAUSE);
            if (apmPerformanceBulkRequestList.getNavigationType() == null) {
                if (apmPerformanceBulkRequestList.getNumberOfRecordsFound() <= 0) {
                    apmPerformanceBulkRequestList.setNumberOfRecordsFound(10);
                } else {
                    apmPerformanceBulkRequestList
                        .setNumberOfRecordsFound(apmPerformanceBulkRequestList.getNumberOfRecordsFound());
                }
            } else {
                if (apmPerformanceBulkRequestList.getNumberOfRecordsFound() <= 0
                    && apmPerformanceBulkRequestList.getCurrentPageNumber() > 1) {
                    apmPerformanceBulkRequestList
                        .setCurrentPageNumber(apmPerformanceBulkRequestList.getCurrentPageNumber() - 1);
                    apmPerformanceBulkRequestList.setNumberOfRecordsFound(10);
                } else {
                    apmPerformanceBulkRequestList
                        .setNumberOfRecordsFound(apmPerformanceBulkRequestList.getNumberOfRecordsFound());
                }
            }

            int fromIndex = apmPerformanceBulkRequestList.getFromIndex();
            int toIndex = apmPerformanceBulkRequestList.getToIndexWithoutCount() - 1;
            log.debug("From Index: " + fromIndex);
            log.debug("To Index: " + toIndex);
            log.debug("apmPerformanceBulkRequestList getCurrentPageNumber:"
                + apmPerformanceBulkRequestList.getCurrentPageNumber());

            log.debug("Search Query:hjajkdhajskhfsdjkahfjsakdfk: \n" + selectQry);

            int paramCount1 = params.size();
            ArrayList<Object> params1 = new ArrayList<>();
            for (int i = 0; i < paramCount1; i++) {
                params1.add(params.get(i));
            }
            params1.add(fromIndex);
            params1.add(toIndex);

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(selectQry.toString(), params1.toArray());

            int totalRecordsFound = 0;
            boolean writerExists = false;
            while (rs.next()) {
                apmPerformanceBulkRequest = new ApmPerformanceBulkRequest();
                apmPerformanceBulkRequest.setSupplierCode(rs.getString("SUPP_CODE"));
                if ("SPLR".equals(apmPerformanceBulkRequestList.getFilterResultViewType())) {
                    apmPerformanceBulkRequest.setProviderId(rs.getString("PROVIDER_ID"));
                }
                apmPerformanceBulkRequest.setPerformerName(rs.getString("PFR_NA"));
                apmPerformanceBulkRequest.setWorkTitle(rs.getString("WRK_TTL"));
                apmPerformanceBulkRequest.setWriterName(rs.getString("WRITER"));
                apmPerformanceBulkRequest.setWorkPerfCount(rs.getString("WRK_PERF_CNT"));
                apmPerformanceBulkRequest.setPlayCount(rs.getString("PLAY_CNT"));
                apmPerformanceBulkRequest.setOriginalWorkId(rs.getString("WRK_ID"));
                apmPerformanceBulkRequest.setWorkId(rs.getString("WRK_ID"));
                apmPerformanceBulkRequest.setManualMatchUserId(rs.getString("MAN_MATCH_USR_ID"));
                apmPerformanceBulkRequest.setDeleteFlag(rs.getString("DEL_FL"));
                apmPerformanceBulkRequest.setEstimatedDollarAmount(rs.getString("EST_DOL_AMT"));
                apmPerformanceBulkRequest.setEstimatedDollarFlag(rs.getString("EST_DLR_FOUND"));
                apmPerformanceBulkRequest.setMultWorkId(rs.getString("MULT_WRK_ID"));
                apmPerformanceBulkRequest.setAssignedToUser(rs.getString("ASG_USR_ID"));
                totalRecordsFound = rs.getInt("TOTALROWS");
                if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())) {
                    writerExists = true;
                }
                col.add(apmPerformanceBulkRequest);
            }
            apmPerformanceBulkRequestList.setNumberOfRecordsFound(totalRecordsFound);
            apmPerformanceBulkRequestList.setWriterExists(writerExists ? "Y" : "N");

        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException("error.usage.sql.exception", e);
        }
        apmPerformanceBulkRequestList.setSearchResults(col);
        log.debug("Exiting UsageDAOImpl - apmPerformanceBulkRequestList method: No Of Results: " + col.size());
        return apmPerformanceBulkRequestList;
    }

    public ApmFileList seacrhFiles(ApmFileList apmFileList) throws PrepSystemException {
        log.debug("Entering UsageDAO - seacrhFiles method " + apmFileList);
        String status = null;
        List<Object> sampleDateCollection = new ArrayList<>();
        ApmFile apmFile = null;
        StringBuilder selectQry;
        StringBuilder selectQryDistribution;
        StringBuilder selectQryRadio;
        StringBuilder searchFileWherePartDistribution;
        StringBuilder searchFileWherePartRadio;
        StringBuilder searchUNIONPart;
        StringBuilder debugSearchDistribution = new StringBuilder();
        StringBuilder debugSearchRadio = new StringBuilder();
        StringBuilder tempSelectQry = new StringBuilder();
        java.util.ArrayList<String> params = new ArrayList<>();
        java.util.ArrayList<String> paramsRadio = new ArrayList<>();
        java.util.ArrayList<String> paramsDist = new ArrayList<>();
        selectQry = new StringBuilder(UsageQueries.GET_APM_FILE_LIST_COMMON_SELECT_PART);
        selectQryDistribution = new StringBuilder(UsageQueries.GET_APM_FILE_LIST_DIST);
        selectQryRadio = new StringBuilder(UsageQueries.GET_APM_FILE_LIST_RADIO);
        searchFileWherePartDistribution = new StringBuilder(" WHERE A.DEL_FL = 'N'");
        searchFileWherePartRadio = new StringBuilder(" WHERE B.DEL_FL = 'N'");
        searchUNIONPart = new StringBuilder(" UNION ALL  ");
        if (!UsageCommonValidations.isEmptyOrNull(apmFileList.getSupplierCode())) {
            searchFileWherePartDistribution.append(" AND A.SUPP_CODE = ? ");
            searchFileWherePartRadio.append(" AND B.SUPP_CODE = ? ");
            paramsRadio.add(apmFileList.getSupplierCode());
            paramsDist.add(apmFileList.getSupplierCode());
            if (log.isDebugEnabled()) {
                debugSearchDistribution.append("  AND A.SUPP_CODE = ? ").append(apmFileList.getSupplierCode())
                    .append("'");
                debugSearchRadio.append(" AND AND B.SUPP_CODE = ? ").append(apmFileList.getSupplierCode()).append("'");
            }
        }
        if (!UsageCommonValidations.isEmptyOrNull(apmFileList.getFileStatus())) {
            searchFileWherePartDistribution.append(" AND A.STATUS = ? ");
            paramsDist.add(apmFileList.getFileStatus());
            searchFileWherePartRadio.append(" AND B.IMPORT_FILE_STATUS = ? ");
            paramsRadio.add(apmFileList.getFileStatus());
            if (log.isDebugEnabled()) {
                debugSearchDistribution.append("  AND A.STATUS = ? ").append(apmFileList.getFileStatus()).append("'");
                debugSearchRadio.append(" AND B.IMPORT_FILE_STATUS = ? ").append(apmFileList.getFileStatus())
                    .append("'");
            }
        }
        // This needs to be at end
        if (UsageCommonValidations.isEmptyOrNull(apmFileList.getFileType()) == false) {
            if (apmFileList.getFileType().equalsIgnoreCase(UsageConstants.FILE_TYPE_DISTRIBUTION)) {
                selectQry = selectQry.append(selectQryDistribution.append(searchFileWherePartDistribution));
                params = paramsDist;
            } else {
                searchFileWherePartRadio.append(" AND B.IMPORT_FILE_TYPE = ? ");
                paramsRadio.add(apmFileList.getFileType());
                // prepare query
                selectQry = selectQry.append(selectQryRadio.append(searchFileWherePartRadio));
                params = paramsRadio;
                if (log.isDebugEnabled()) {
                    debugSearchRadio.append(" AND B.IMPORT_FILE_TYPE = ? ").append(apmFileList.getFileType())
                        .append("'");
                }
            }
        } else {
            tempSelectQry = selectQryDistribution.append(searchFileWherePartDistribution).append(searchUNIONPart)
                .append(selectQryRadio.append(searchFileWherePartRadio));
            selectQry = selectQry.append(tempSelectQry);
            params.addAll(paramsDist);
            params.addAll(paramsRadio);
        }
        selectQry = selectQry.append(UsageQueries.GET_APM_FILE_LIST_COMMON_ROW_NBR_PART);
        if (apmFileList.getNavigationType() == null) {
            if (apmFileList.getNumberOfRecordsFound() <= 0) {
                apmFileList.setNumberOfRecordsFound(10);
            } else {
                apmFileList.setNumberOfRecordsFound(apmFileList.getNumberOfRecordsFound());
            }
        } else {
            if (apmFileList.getNumberOfRecordsFound() <= 0 && apmFileList.getCurrentPageNumber() > 1) {
                apmFileList.setCurrentPageNumber(apmFileList.getCurrentPageNumber() - 1);
                apmFileList.setNumberOfRecordsFound(10);
            } else {
                apmFileList.setNumberOfRecordsFound(apmFileList.getNumberOfRecordsFound());
            }
        }

        int fromIndex = apmFileList.getFromIndex();
        int toIndex = apmFileList.getToIndexWithoutCount() - 1;
        if (log.isDebugEnabled()) {
            log.debug("From Index: " + fromIndex);
            log.debug("To Index: " + toIndex);
            log.debug("getApmLearnedMatchList getCurrentPageNumber:" + apmFileList.getCurrentPageNumber()
                + "  param count " + params.size());
        }
        log.debug("Search Query \n" + selectQry);
        int paramCount1 = params.size();
        String paramVal = null;
        ArrayList<Object> params1 = new ArrayList<>();
        for (int i = 0; i < paramCount1; i++) {
            paramVal = params.get(i);
            params1.add(paramVal);
            log.debug(i + 1 + "  " + paramVal);
        }
        params1.add(fromIndex);
        params1.add(toIndex);
        SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(selectQry.toString(), params1.toArray());
        int totalRecordsFound = 0;
        while (rs.next()) {
            apmFile = new ApmFile();
            apmFile.setFileId(rs.getString("FILe_ID"));
            apmFile.setFileName(rs.getString("FILE_NAME"));
            apmFile.setFileType(rs.getString("FILe_TYPE"));
            apmFile.setFileStatus(rs.getString("STATUS"));
            apmFile.setDetailCount(rs.getString("CNT"));
            apmFile.setSupplierCode(rs.getString("SUPP_CODE"));
            apmFile.setProcessDate(rs.getString("PROCESS_DT"));
            apmFile.setTargetYearQuarter(rs.getString("TGTSURVYEARQTR"));
            totalRecordsFound = rs.getInt("TOTALROWS");
            sampleDateCollection.add(apmFile);
        }
        apmFileList.setNumberOfRecordsFound(totalRecordsFound);
        // set the search results into the search vob
        apmFileList.setSearchResults(sampleDateCollection);
        log.debug("Exiting UsageDAOImpl - seacrhFiles method  ");
        return apmFileList;
    }

    public MusicUserSearch searchMusicUser(MusicUserSearch musicUserSearch) throws PrepSystemException {
        log.debug("Entering UsageDAOImpl - searchMusicUser method ");

        List<Object> col = new ArrayList<>();
        MusicUserParty musicUserParty = null;
        ArrayList<String> params = new ArrayList<>();

        StringBuilder selectQry = new StringBuilder(UsageQueries.GET_MUS_USER_PTY_DATA_START);
        StringBuilder countQry = new StringBuilder(UsageQueries.GET_MUS_USER_PTY_COUNT_START);

        params.add(musicUserSearch.getFilterEffectiveDate());
        params.add(musicUserSearch.getFilterEffectiveDate());
        params.add(musicUserSearch.getFilterEffectiveDate());

        if (!UsageCommonValidations.isEmptyOrNull(musicUserSearch.getFilterCallLetter())) {
            selectQry.append(" and A.NA LIKE UPPER(?) ");
            countQry.append(" and A.NA LIKE UPPER(?) ");
            params.add(musicUserSearch.getFilterCallLetter() + "%");
        }

        if (!UsageCommonValidations.isEmptyOrNull(musicUserSearch.getFilterCallLetterSuffix())) {
            selectQry.append(" and A.CAL_LTR_SUF = ? ");
            countQry.append(" and A.CAL_LTR_SUF = ? ");
            params.add(musicUserSearch.getFilterCallLetterSuffix());
        }

        if (!UsageCommonValidations.isEmptyOrNull(musicUserSearch.getFilterMusicUserType())) {
            selectQry.append(" and B.MUS_USER_TYP_CDE = ? ");
            countQry.append(" and B.MUS_USER_TYP_CDE = ? ");
            params.add(musicUserSearch.getFilterMusicUserType());
        }

        if (!UsageCommonValidations.isEmptyOrNull(musicUserSearch.getFilterLicenseType())) {
            selectQry.append(" and C.LIC_TYP_CDE = ? ");
            countQry.append(" and C.LIC_TYP_CDE = ? ");
            params.add(musicUserSearch.getFilterLicenseType());
        }

        selectQry.append(UsageQueries.GET_MUS_USER_PTY_DATA_END);
        countQry.append(UsageQueries.GET_MUS_USER_PTY_COUNT_END);

        try {

            log.debug("Search Count Query " + countQry);
            int paramCount1 = params.size();
            ArrayList<Object> params1 = new ArrayList<>();
            for (int i = 0; i < paramCount1; i++) {
                params1.add(params.get(i));
            }
            log.debug("Executing Count Query " + countQry.toString());
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(countQry.toString(), params.toArray());

            if (rs.next()) {
                musicUserSearch.setNumberOfRecordsFound(rs.getInt(1));
            }

            log.debug("getNumberOfRecordsFound is " + musicUserSearch.getNumberOfRecordsFound());
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        }

        try {

            int fromIndex = musicUserSearch.getFromIndex();
            int toIndex = musicUserSearch.getToIndex() - 1;
            log.debug("From Index: " + fromIndex);
            log.debug("To Index: " + toIndex);
            log.debug("Search Query \n" + selectQry);

            int paramCount1 = params.size();
            ArrayList<Object> params1 = new ArrayList<>();
            for (int i = 0; i < paramCount1; i++) {
                params1.add(params.get(i));
            }
            params1.add(fromIndex);
            params1.add(toIndex);

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(selectQry.toString(), params1.toArray());
            while (rs.next()) {
                musicUserParty = new MusicUserParty();
                musicUserParty.setPartyId(rs.getString("PTY_ID"));
                musicUserParty.setCallLetter(rs.getString("NA"));
                musicUserParty.setCallLetterSuffix(rs.getString("CAL_LTR_SUF"));
                musicUserParty.setMusicUserTypeCode(rs.getString("MUS_USER_TYP_CDE"));
                musicUserParty.setLicenseType(rs.getString("LIC_TYP_CDE"));
                col.add(musicUserParty);
            }

        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        }
        musicUserSearch.setSearchResults(col);
        log.debug("Exiting UsageDAOImpl - searchMusicUser method ");
        return musicUserSearch;
    }

    public LogUsageSummary getLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - getLogUsageSummary method ");

        log.debug("navigationType: " + logUsageSummary.getNavigation() + " operationType: "
            + logUsageSummary.getOperationType());

        LogUsageSummary logUsageSummaryResult = new LogUsageSummary();

        String[] selections = logUsageSummary.getSelections();
        String[] tokens = null;
        String logRequestId = null;

        if (selections != null && selections.length > 0
            && ValidationUtils.isEmptyOrNull(logUsageSummary.getNavigation())
            && "USAGE".equalsIgnoreCase(logUsageSummary.getOperationType())) {
            int i = 0;

            for (String index : selections) {

                if (ValidationUtils.isEmptyOrNull(index)) {
                    continue;
                }
                i++;

                log.debug("selectedStrs: " + i + " element: " + index);
                tokens = index.split("##", -1);
                logRequestId = tokens[0];
            }
        } else
            logRequestId = logUsageSummary.getHeaderid();

        log.debug("logReqId / headerid: " + logRequestId);

        logUsageSummary = setNavigationInfo(logUsageSummary, logRequestId);

        int currentPg = logUsageSummary.getCurrentPg();
        int totalPg = logUsageSummary.getTotalPg();
        int fromIdx = logUsageSummary.getFromIdx();
        int toIdx = logUsageSummary.getToIdx();

        String sql = UsageQueries.GET_LOGHDR_INFO;
        StringBuilder strb = new StringBuilder(sql);

        try {

            log.debug("Executing Query : " + strb.toString());

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(strb.toString(), logRequestId);

            while (rs.next()) {

                logUsageSummaryResult.setCallLetter(rs.getString("CallLetter"));
                logUsageSummaryResult.setStartDate(rs.getString("StartDate"));
                logUsageSummaryResult.setEndDate(rs.getString("EndDate"));
                logUsageSummaryResult.setAccountNumber(rs.getString("AccountNumber"));

                log.debug("CallLetter: " + rs.getString("CallLetter") + " StartDate: " + rs.getString("StartDate")
                    + " EndDate: " + rs.getString("EndDate") + " AccountNumber: " + rs.getString("AccountNumber"));

            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        String query = UsageQueries.GET_LOGUSAGE_SUMMARY_LIST;

        StringBuilder sb = new StringBuilder(query);

        LogUsageRequest logSummaryRequest;

        List<Object> collectionLogUsageRequest = new ArrayList<>();

        try {

            log.debug("Executing Query: " + sb.toString());

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(sb.toString(), logRequestId, fromIdx, toIdx);

            String searchResultRows = null;

            while (rs.next()) {

                logSummaryRequest = new LogUsageRequest();

                logSummaryRequest.setWrkperfid(rs.getString("wrkperfid"));
                logSummaryRequest.setTitle(rs.getString("title"));
                logSummaryRequest.setArtist(rs.getString("artist"));
                logSummaryRequest.setUsetype(rs.getString("usetype"));
                logSummaryRequest.setDuration(rs.getString("duration"));
                logSummaryRequest.setPlays(rs.getString("plays"));
                logSummaryRequest.setInstvocal(rs.getString("instvocal"));
                logSummaryRequest.setWorkid(rs.getString("workid"));
                logSummaryRequest.setStatus(rs.getString("status"));
                logSummaryRequest.setReadonly(rs.getString("readonly"));

                searchResultRows = rs.getString("totalrows");

                collectionLogUsageRequest.add(logSummaryRequest);
            }

            logUsageSummaryResult.setSearchResults(collectionLogUsageRequest);

            logUsageSummaryResult.setSearchRequested("Y");
            logUsageSummaryResult.setSearchResultRows(searchResultRows);

            logUsageSummaryResult.setCurrentPg(currentPg);
            logUsageSummaryResult.setTotalPg(totalPg);
            logUsageSummaryResult.setFromIdx(fromIdx);
            logUsageSummaryResult.setToIdx(toIdx);
            logUsageSummaryResult.setFirstPg(logUsageSummary.getFirstPg());
            logUsageSummaryResult.setLastPg(logUsageSummary.getLastPg());
            logUsageSummaryResult.setTotalPg(logUsageSummary.getTotalPg());
            logUsageSummaryResult.setLogRequestPg(logUsageSummary.getLogRequestPg());

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAOImpl - getLogUsageSummary method ");

        return logUsageSummaryResult;
    }

    public LogUsageSummary setNavigationInfo(LogUsageSummary logUsageSummary, String headerid)
        throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - setNavigationInfo method ");

        String operationType = logUsageSummary.getOperationType();
        String navigation = logUsageSummary.getNavigation();

        int currentPg = logUsageSummary.getCurrentPg();
        int totalPg = logUsageSummary.getTotalPg();
        int fromIdx = logUsageSummary.getFromIdx();
        int toIdx = logUsageSummary.getToIdx();
        int searchResultsPerPg = logUsageSummary.getSearchResultsPerPg();

        int totalSearchResultRows = getTotalSearchRows(logUsageSummary, headerid);

        log.debug("Before setting up of values - currentPg: " + currentPg + " logRequestPg: "
            + logUsageSummary.getLogRequestPg() + " totalPg: " + totalPg + " fromIdx: " + fromIdx + " toIdx: " + toIdx
            + " searchResultsPerPg: " + searchResultsPerPg + " totalSearchResultRows: " + totalSearchResultRows
            + " navigation: " + navigation + " operationType: " + operationType);

        if (totalSearchResultRows % searchResultsPerPg == 0)
            totalPg = (totalSearchResultRows / searchResultsPerPg);
        else
            totalPg = (totalSearchResultRows / searchResultsPerPg) + 1;

        // menu invoke, search, update, release, delete - here page should remain same
        if (UsageCommonValidations.isEmptyOrNull(navigation)) {
            // menu invoke
            if (UsageCommonValidations.isEmptyOrNull(operationType) || "USAGE".equalsIgnoreCase(operationType)) {
                currentPg = 1;
                log.debug(">00000000000");
            }
            // search, update, release, delete
            else {
                // no change in page or index
                log.debug(">33333333333333");
            }
        }
        // page navigation - only page should change
        else {

            if ("FIRST".equalsIgnoreCase(navigation)) {
                currentPg = 1;
                log.debug(">4444444444");
            }
            if ("LAST".equalsIgnoreCase(navigation)) {
                if (totalSearchResultRows % searchResultsPerPg == 0)
                    currentPg = (totalSearchResultRows / searchResultsPerPg);
                else
                    currentPg = (totalSearchResultRows / searchResultsPerPg) + 1;
                log.debug(">5555555555");
            }
            if ("NEXT".equalsIgnoreCase(navigation)) {
                if (currentPg < totalPg) {
                    currentPg = currentPg + 1;
                    log.debug(">6666666666");
                }
                log.debug(">7777777777777");
            }
            if ("PREV".equalsIgnoreCase(navigation)) {
                if (currentPg > 1) {
                    currentPg = currentPg - 1;
                    log.debug(">88888888888888");
                }
                log.debug(">99999999999");
            }

            if ("GOTOPAGE".equalsIgnoreCase(navigation)) {
                if (currentPg < totalPg)
                    currentPg = currentPg;
                else
                    currentPg = totalPg;
                log.debug(">1010101010101010");
            }
        }

        if (currentPg > totalPg)
            currentPg = totalPg;

        fromIdx = ((currentPg - 1) * searchResultsPerPg) + 1;
        toIdx = (fromIdx + searchResultsPerPg) - 1;

        logUsageSummary.setCurrentPg(currentPg);
        logUsageSummary.setTotalPg(totalPg);
        logUsageSummary.setFromIdx(fromIdx);
        logUsageSummary.setToIdx(toIdx);

        log.debug("After setting up of values - currentPg: " + currentPg + " logRequestPg: "
            + logUsageSummary.getLogRequestPg() + " totalPg: " + totalPg + " fromIdx: " + fromIdx + " toIdx: " + toIdx
            + " searchResultsPerPg: " + searchResultsPerPg + " totalSearchResultRows: " + totalSearchResultRows
            + " navigation: " + navigation + " operationType: " + operationType);

        if (currentPg == 1)
            logUsageSummary.setFirstPg("Y");
        else
            logUsageSummary.setFirstPg("N");

        if (currentPg >= totalPg)
            logUsageSummary.setLastPg("Y");
        else
            logUsageSummary.setLastPg("N");

        log.debug("firstPg: " + logUsageSummary.getFirstPg() + " lastPg: " + logUsageSummary.getLastPg());

        log.debug("Exiting  UsageDAOImpl - setNavigationInfo method ");

        return logUsageSummary;

    }

    public int getTotalSearchRows(LogUsageSummary logUsageSummary, String headerid) throws PrepSystemException {

        log.debug("Entering  UsageDAO - getTotalSearchRows method ");

        int fromIndex = 1;
        int toIndex = 10;

        String query = UsageQueries.GET_LOGUSAGE_SUMMARY_LIST;

        StringBuilder sb = new StringBuilder(query);

        int searchResultRows = 0;

        try {

            SqlRowSet rs =
                ascapJdbcTemplate.queryForRowSet(sb.toString(), Integer.parseInt(headerid), fromIndex, toIndex);

            if (rs.next()) {
                searchResultRows = Integer.parseInt(rs.getString("totalrows"));
            }

            log.debug("searchResultRows Found: " + searchResultRows);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        log.debug("Exiting UsageDAO - getTotalSearchRows method ");

        return searchResultRows;
    }

    public LogUsageSummary insertLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - insertLogUsageSummary method ");

        String headerid = logUsageSummary.getHeaderid();

        log.debug("headerid : " + headerid);

        // insert usages
        insertLogUsage(logUsageSummary);

        logUsageSummary = getLogUsageSummary(logUsageSummary);
        return logUsageSummary;
    }

    public LogUsageSummary deleteLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - deleteLogUsageSummary method ");

        String headerid = logUsageSummary.getHeaderid();
        log.debug("headerid : " + headerid);

        // insert usages
        deleteLogUsage(logUsageSummary);

        logUsageSummary = getLogUsageSummary(logUsageSummary);
        log.debug("Exiting UsageDAOImpl - deleteLogUsageSummary method ");
        return logUsageSummary;
    }

    public void deleteLogUsage(LogUsageSummary logUsageSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - deleteLogUsage method ");
        String headerid = logUsageSummary.getHeaderid();
        log.debug("headerid : " + headerid);

        String[] selections = logUsageSummary.getSelections();

        if (selections != null && selections.length > 0) {
            int i = 0;

            for (String index : selections) {

                if (ValidationUtils.isEmptyOrNull(index)) {
                    continue;
                }
                i++;

                log.debug("selectedStrs: " + i + " element: " + index);

                String sql = null;

                sql = UsageQueries.DELETE_LOGUSAGE;

                StringBuilder strb = new StringBuilder(sql);
                log.debug("Executing query: " + strb);
                try {

                    ascapJdbcTemplate.update(strb.toString(), logUsageSummary.getUserId(), index);

                } catch (Exception e) {
                    log.debug(e);
                    throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
                }
            }
            validateLogs(logUsageSummary);
        }
        log.debug("Exiting  UsageDAOImpl - deleteLogUsage method ");
    }

    public void validateLogs(LogUsageSummary logUsageSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - validateLogs method ");

        String headerid = logUsageSummary.getHeaderid();
        String sql = null;

        int total = 0;
        int error = 0;
        int released = 0;
        String status = null;
        String count = null;
        String updateStatus = "";
        boolean updateLogRequest = false;

        sql = UsageQueries.GET_LOGUSAGE_STATUS_INFO;
        StringBuilder strb = new StringBuilder(sql);
        log.debug("Executing query: " + strb);
        try {

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(strb.toString(), logUsageSummary.getHeaderid());

            while (rs.next()) {
                status = rs.getString("Status");
                count = rs.getString("Count");

                if (status.equalsIgnoreCase("NEW") || status.equalsIgnoreCase("PND") || status.equalsIgnoreCase("RR")) {
                    total += Integer.parseInt(count);
                }

                if (status.equalsIgnoreCase("RD")) {
                    total += Integer.parseInt(count);
                    released += Integer.parseInt(count);
                }

                if (status.equalsIgnoreCase("RA") || status.equalsIgnoreCase("E")) {
                    total += Integer.parseInt(count);
                    error += Integer.parseInt(count);
                }
            }

            if (released == total && error == 0) {
                updateStatus = "RD";
                updateLogRequest = true;
            }
            if (released != total && error == 0) {
                updateStatus = "PND";
                updateLogRequest = true;
            }
            if (total == 0) {
                updateStatus = "NEW";
                updateLogRequest = true;
            }

            log.debug("Changing header status? " + updateLogRequest + " updateStatus: " + updateStatus + " total: "
                + total + " error: " + error + " released: " + released);

            // update logrequest status
            if (updateLogRequest) {
                String updateSQL = UsageQueries.UPDATE_LOGREQUESTHDR;
                StringBuilder sb = new StringBuilder(updateSQL);
                log.debug("update LogRequest query: " + strb);
                ascapJdbcTemplate.update(sb.toString(), updateStatus, headerid);
            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting  UsageDAOImpl - validateLogs method ");
    }

    public void insertLogUsage(LogUsageSummary logUsageSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - insertLogUsage method ");
        String headerid = logUsageSummary.getHeaderid();
        log.debug("headerid : " + headerid);

        String fileid = null;
        String tgtsurvyearqtr = null;
        String logStatus = null;

        String sql = UsageQueries.GET_LOGHDR_INFO;
        StringBuilder strb = new StringBuilder(sql);

        String[] titles = logUsageSummary.getInsTitles();
        String[] artists = logUsageSummary.getInsArtists();
        String[] durations = logUsageSummary.getInsDurations();
        String[] workids = logUsageSummary.getInsWorkids();
        String[] plays = logUsageSummary.getInsPlays();

        String[] usetypes = logUsageSummary.getUsetypes();
        String[] instvocals = logUsageSummary.getInstvocals();

        try {
            log.debug("Executing Query : " + strb.toString());

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(strb.toString(), headerid);

            if (rs.next()) {

                fileid = rs.getString("Fileid");
                tgtsurvyearqtr = rs.getString("Tgtsurvyearqtr");

                log.debug("fileid: " + fileid);

                if (titles != null && titles.length > 0) {
                    log.debug("Title Array lenth in DAO: " + titles.length);
                    for (int i = 0; i < titles.length; i++) {

                        logStatus = "PND";
                        rs = ascapJdbcTemplate.queryForRowSet(" SELECT LOG_REQ_WRK_PERF_ID.NEXTVAL FROM DUAL");
                        String id = "1";

                        if (rs.next()) {
                            id = rs.getString(1);
                        }

                        log.debug("iteration: " + i + " nextvel: " + id + " Title : " + titles[i] + " Arrtist: "
                            + artists[i] + " UseType: " + usetypes[i]);

                        ArrayList<Object> params = new ArrayList<>();
                        params.add(id);
                        params.add(fileid);
                        params.add(headerid);

                        if ("$######$######$".equals(durations[i]))
                            params.add("");
                        else
                            params.add(durations[i]);

                        if ("$######$######$".equals(plays[i]))
                            params.add("");
                        else
                            params.add(plays[i]);

                        if ("$######$######$".equals(artists[i])) {
                            params.add("");
                            logStatus = "E";
                        } else
                            params.add(artists[i]);

                        if ("$######$######$".equals(titles[i])) {
                            params.add("");
                            logStatus = "E";
                        } else
                            params.add(titles[i]);

                        if ("$######$######$".equals(workids[i]))
                            params.add("");
                        else
                            params.add(workids[i]);

                        params.add(usetypes[i]);
                        params.add(instvocals[i]);
                        params.add(logStatus);
                        params.add(tgtsurvyearqtr);
                        params.add("APM_UI");
                        params.add("N");

                        ascapJdbcTemplate.update(UsageQueries.INSERT_LOGUSAGE, params.toArray());

                        if ("E".equalsIgnoreCase(logStatus)) {
                            updateLogRequest(headerid, "E");
                        }

                        rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_VALIDATION_ERR, headerid);

                        int validationError = 0;

                        if (rs.next()) {
                            validationError = Integer.parseInt(rs.getString(1));
                        }

                        if ("PND".equalsIgnoreCase(logStatus) && validationError == 0) {
                            updateLogRequest(headerid, "PND");
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting  UsageDAOImpl - insertLogUsage method ");
    }

    public void updateLogRequest(String headerid, String logStatus) throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - updateLogRequest method ");

        String sql = "UPDATE PAPM.LOG_REQ_HDR SET LOG_STATUS = ? WHERE LOG_REQ_HDR_ID = ? ";
        StringBuilder strb = new StringBuilder(sql);

        try {

            ascapJdbcTemplate.update(strb.toString(), logStatus, headerid);
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting  UsageDAOImpl - updateLogRequest method ");
    }

    public LogUsageSummary updateLogUsageSummary(LogUsageSummary logUsageSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - updateLogUsageSummary method ");

        String headerid = logUsageSummary.getHeaderid();

        log.debug("headerid : " + headerid);

        // insert usages
        updateLogUsage(logUsageSummary);

        logUsageSummary = getLogUsageSummary(logUsageSummary);
        return logUsageSummary;
    }

    public void updateLogUsage(LogUsageSummary logUsageSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl - updateLogUsage method ");

        String headerid = logUsageSummary.getHeaderid();
        log.debug("headerid : " + headerid);

        String[] updWrkperfids = logUsageSummary.getUpdWrkperfids();
        String[] updTitles = logUsageSummary.getUpdTitles();
        String[] updArtists = logUsageSummary.getUpdArtists();
        String[] updWorkids = logUsageSummary.getUpdWorkids();
        String[] updDurations = logUsageSummary.getUpdDurations();
        String[] updPlays = logUsageSummary.getUpdPlays();
        String[] updUsetypes = logUsageSummary.getUpdUsetypes();
        String[] updInstvocals = logUsageSummary.getUpdInstvocals();

        if (updWrkperfids != null && updWrkperfids.length > 0) {
            log.debug("updWrkperfids Array lenth in MultiAction: " + updWrkperfids.length);
            for (int i = 0; i < updWrkperfids.length; i++) {
                log.debug("updWrkperfids: " + i + ": " + updWrkperfids[i] + " updTitles: " + updTitles[i].substring(12)
                    + " updArtists: " + updArtists[i].substring(12) + " updWorkids: " + updWorkids[i].substring(12)
                    + " updDurations: " + updDurations[i].substring(12) + " updPlays: " + updPlays[i].substring(12)
                    + " updUsetypes: " + updUsetypes[i] + " updInstvocals: " + updInstvocals[i]);
            }
        }

        if (updWrkperfids != null && updWrkperfids.length > 0) {

            for (int i = 0; i < updWrkperfids.length; i++) {

                String sql = null;
                sql = UsageQueries.UPDATE_LOGUSAGE_WITH_STATUS;

                StringBuilder strb = new StringBuilder(sql);
                log.debug("Executing query: " + strb);
                try {

                    List<Object> params = new ArrayList<>();
                    params.add(updTitles[i].substring(12));
                    params.add(updArtists[i].substring(12));
                    params.add(updWorkids[i].substring(12));
                    params.add(updDurations[i].substring(12));
                    params.add(updPlays[i].substring(12));
                    params.add(updUsetypes[i]);
                    params.add(updInstvocals[i]);
                    params.add(logUsageSummary.getUserId());

                    if (ValidationUtils.isEmptyOrNull(updTitles[i].substring(12))
                        || ValidationUtils.isEmptyOrNull(updArtists[i].substring(12))) {
                        params.add("E");
                        params.add(updWrkperfids[i]);
                    } else {
                        params.add("PND");
                        params.add(updWrkperfids[i]);
                    }

                    ascapJdbcTemplate.update(strb.toString(), params.toArray());

                    // update header
                    if (ValidationUtils.isEmptyOrNull(updTitles[i].substring(12))
                        || ValidationUtils.isEmptyOrNull(updArtists[i].substring(12))) {
                        String updateSQL = UsageQueries.UPDATE_LOGREQUESTHDR;
                        StringBuilder sb = new StringBuilder(updateSQL);
                        ascapJdbcTemplate.update(sb.toString(), "E", headerid);
                    }
                } catch (Exception e) {
                    log.debug(e);
                    throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
                }
            }
            validateLogs(logUsageSummary);
        }
        log.debug("Exiting  UsageDAOImpl - updateLogUsage method ");
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#getExemptionList(exemptionList)
     */
    public ExemptionList getExemptionList(ExemptionList exemptionList) throws PrepSystemException {
        log.debug("Entering UsageDAOImpl- getExemptionList method");
        Exemption exemption = null;
        List<Object> col = new ArrayList<>();
        ArrayList<String> params = null;
        try {
            StringBuilder qry = new StringBuilder(UsageQueries.GET_EXEMPTION_LIST_START);
            if (!ValidationUtils.isEmptyOrNull(exemptionList.getFilterExemptionType())) {
                if ("WRK_ID".equals(exemptionList.getFilterExemptionType())) {
                    qry.append(UsageQueries.GET_EXEMPTION_LIST_ORDER_BY_WRK_ID);
                } else if ("PTY_ID".equals(exemptionList.getFilterExemptionType())) {
                    qry.append(UsageQueries.GET_EXEMPTION_LIST_ORDER_BY_PTY_ID);
                } else if ("PROVIDER_ID".equals(exemptionList.getFilterExemptionType())) {
                    qry.append(UsageQueries.GET_EXEMPTION_LIST_ORDER_BY_PROVIDER_ID);
                } else {
                    qry.append(UsageQueries.GET_EXEMPTION_LIST_ORDER_BY_DISPLAY_VAL);
                }
            }
            qry.append(UsageQueries.GET_EXEMPTION_LIST_MIDDLE);
            params = new ArrayList<>();
            if (!ValidationUtils.isEmptyOrNull(exemptionList.getFilterExemptionType())) {
                if ("WRK_ID".equals(exemptionList.getFilterExemptionType())) {
                    qry.append(" AND A.WRK_ID IS NOT NULL ");
                    if (!ValidationUtils.isEmptyOrNull(exemptionList.getFilterExemptionValue())) {
                        qry.append(" AND A.DISPLAY_VAL = ? ");
                        params.add(exemptionList.getFilterExemptionValue());
                    }
                } else if ("PTY_ID".equals(exemptionList.getFilterExemptionType())) {
                    qry.append(" AND A.PTY_ID IS NOT NULL ");
                    if (!ValidationUtils.isEmptyOrNull(exemptionList.getFilterExemptionValue())) {
                        qry.append(" AND A.DISPLAY_VAL = ? ");
                        params.add(exemptionList.getFilterExemptionValue());
                    }
                } else if ("PROVIDER_ID".equals(exemptionList.getFilterExemptionType())) {
                    qry.append(" AND A.PROVIDER_ID IS NOT NULL ");
                    if (!ValidationUtils.isEmptyOrNull(exemptionList.getFilterExemptionValue())) {
                        qry.append(" AND A.DISPLAY_VAL = ? ");
                        params.add(exemptionList.getFilterExemptionValue());
                    }
                } else if ("WRK_TTL".equals(exemptionList.getFilterExemptionType())) {
                    qry.append(" AND A.WRK_TTL IS NOT NULL ");
                    if (!ValidationUtils.isEmptyOrNull(exemptionList.getFilterExemptionValue())) {
                        qry.append(
                            " AND CATSEARCH(A.DISPLAY_VAL, ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'TITLE', 'CNTS') FROM DUAL), '') > 0");
                        params.add(exemptionList.getFilterExemptionValue());
                    }
                } else if ("WRITER".equals(exemptionList.getFilterExemptionType())) {
                    qry.append(" AND A.WRITER IS NOT NULL ");
                    if (!ValidationUtils.isEmptyOrNull(exemptionList.getFilterExemptionValue())) {
                        qry.append(
                            " AND CATSEARCH(A.DISPLAY_VAL, ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'NAME', 'CNTS') FROM DUAL), '') > 0");
                        params.add(exemptionList.getFilterExemptionValue());
                    }
                } else if ("LIBRARY_DISC_ID".equals(exemptionList.getFilterExemptionType())) {
                    qry.append(" AND A.LIBRARY_DISC_ID IS NOT NULL ");
                    if (!ValidationUtils.isEmptyOrNull(exemptionList.getFilterExemptionValue())) {
                        qry.append(
                            " AND CATSEARCH(A.DISPLAY_VAL, ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'TITLE', 'CNTS') FROM DUAL), '') > 0");
                        params.add(exemptionList.getFilterExemptionValue());
                    }
                }
            }
            qry.append(UsageQueries.GET_EXEMPTION_LIST_END);
            if (exemptionList.getNavigationType() == null) {
                if (exemptionList.getNumberOfRecordsFound() <= 0) {
                    exemptionList.setNumberOfRecordsFound(10);
                } else {
                    exemptionList.setNumberOfRecordsFound(exemptionList.getNumberOfRecordsFound());
                }
            } else {
                if (exemptionList.getNumberOfRecordsFound() <= 0 && exemptionList.getCurrentPageNumber() > 1) {
                    exemptionList.setCurrentPageNumber(exemptionList.getCurrentPageNumber() - 1);
                    exemptionList.setNumberOfRecordsFound(10);
                } else {
                    exemptionList.setNumberOfRecordsFound(exemptionList.getNumberOfRecordsFound());
                }
            }
            int fromIndex = exemptionList.getFromIndex();
            int toIndex = exemptionList.getToIndexWithoutCount() - 1;
            ;
            log.debug("From Index: " + fromIndex);
            log.debug("To Index: " + toIndex);
            log.debug("Search Query \n" + qry.toString());
            int paramCount1 = params.size();
            String paramVal = null;
            ArrayList<Object> parameter = new ArrayList<>();
            for (int i = 0; i < paramCount1; i++) {
                paramVal = (String) params.get(i);
                parameter.add(paramVal);
            }
            parameter.add(fromIndex);
            parameter.add(toIndex);
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(qry.toString(), parameter.toArray());
            int totalRecordsFound = 0;
            while (rs.next()) {
                exemption = new Exemption();
                exemption.setExemptionId(rs.getString("SM_DEDUP_EXEMPTIONS_ID"));
                exemption.setExemptionType(rs.getString("EXEMPTION_TYPE"));
                exemption.setExemptionValue(rs.getString("DISPLAY_VAL"));
                totalRecordsFound = rs.getInt("TOTAL_ROWS");
                col.add(exemption);
            }
            exemptionList.setNumberOfRecordsFound(totalRecordsFound);
            exemptionList.setSearchResults(col);
        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exiting UsageDAOImpl- getExemptionList method ");
        return exemptionList;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#isValidWorkId(workId)
     */
    public boolean isValidWorkId(String workId) throws PrepSystemException {
        log.debug("Entering UsageDAOImpl- isValidWorkId method");
        boolean validWorkId = false;
        if (ValidationUtils.isEmptyOrNull(workId)) {
            log.debug("Input work id is null.. Exiting..");
            return validWorkId;
        }
        try {
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet("SELECT WRK_ID FROM MVW_WRK WHERE WRK_ID = ?", workId);
            if (rs.next()) {
                if (!ValidationUtils.isEmptyOrNull(rs.getString("WRK_ID"))) {
                    validWorkId = true;
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exting UsageDAOImpl- isValidWorkId method " + validWorkId);
        return validWorkId;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#getExemptionByValues(exemption)
     */
    public Exemption getExemptionByValues(Exemption exemption) throws PrepSystemException {
        log.debug("Entering UsageDAOImpl- getExemptionByValues method ");
        if (exemption == null) {
            log.debug("Invalid Input... Returning");
            return exemption;
        }
        StringBuilder qry = new StringBuilder(UsageQueries.GET_EXEMPTION_BY_VALUE);
        Exemption dbExemption = null;
        try {
            if ("WRK_ID".equals(exemption.getExemptionType())) {
                qry.append(" AND A.WRK_ID IS NOT NULL ");
            } else if ("PTY_ID".equals(exemption.getExemptionType())) {
                qry.append(" AND A.PTY_ID IS NOT NULL ");
            } else if ("PROVIDER".equals(exemption.getExemptionType())) {
                qry.append(" AND A.PROVIDER IS NOT NULL ");
            } else if ("WRK_TTL".equals(exemption.getExemptionType())) {
                qry.append(" AND A.WRK_TTL IS NOT NULL ");
            } else if ("WRITER".equals(exemption.getExemptionType())) {
                qry.append(" AND A.WRITER IS NOT NULL ");
            } else if ("LIBRARY_DISC_ID".equals(exemption.getExemptionType())) {
                qry.append(" AND A.LIBRARY_DISC_ID IS NOT NULL ");
            }
            qry.append(" AND A.DISPLAY_VAL = ? ");
            List<Object> param = new ArrayList<>();
            param.add(exemption.getExemptionValue());
            if (!ValidationUtils.isEmptyOrNull(exemption.getExemptionId())) {
                qry.append(" AND A.SM_DEDUP_EXEMPTIONS_ID <> ? ");
                param.add(exemption.getExemptionId());
            }
            log.debug("Get Exemption query: " + qry.toString());
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(qry.toString(), param.toArray());
            if (rs.next()) {
                dbExemption = new Exemption();
                dbExemption.setExemptionId(rs.getString("SM_DEDUP_EXEMPTIONS_ID"));
                dbExemption.setExemptionValue(rs.getString("DISPLAY_VAL"));
            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exting UsageDAOImpl- getExemptionByValues method ");
        return dbExemption;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#addExemption(exemption)
     */
    public Exemption addExemption(Exemption exemption) throws PrepSystemException {
        log.debug("Entering UsageDAOImpl- addExemption method ");
        if (exemption == null) {
            log.debug("Input vob is null.. Exiting..");
            return exemption;
        }
        StringBuilder sqlStr;
        StringBuilder columnsPart;
        StringBuilder valuesPart;
        String exemptionSequenceId = null;
        ArrayList<String> valuesList;
        sqlStr = new StringBuilder(UsageQueries.DYNAMIC_INSERT_EXEMPTION);
        columnsPart = new StringBuilder(" (");
        valuesPart = new StringBuilder(" VALUES(");
        try {
            exemptionSequenceId = this.getSequence(UsageQueries.SEQUENCE_SM_DEDUP_EXEMPTIONS_ID);
            valuesList = new ArrayList<>();
            columnsPart.append(" SM_DEDUP_EXEMPTIONS_ID");
            valuesPart.append(" ?");
            valuesList.add(exemptionSequenceId);
            String formattedStr = null;
            if (!UsageCommonValidations.isEmptyOrNull(exemption.getExemptionType())) {
                if ("PTY_ID".equals(exemption.getExemptionType())) {
                    columnsPart.append(", PTY_ID");
                    valuesPart.append(", ?");
                    valuesList.add(exemption.getExemptionValue());
                } else if ("WRK_ID".equals(exemption.getExemptionType())) {
                    columnsPart.append(", WRK_ID");
                    valuesPart.append(", ?");
                    valuesList.add(exemption.getExemptionValue());
                } else if ("PROVIDER_ID".equals(exemption.getExemptionType())) {
                    columnsPart.append(", PROVIDER_ID");
                    valuesPart.append(", ?");
                    valuesList.add(exemption.getExemptionValue());
                } else if ("WRK_TTL".equals(exemption.getExemptionType())) {
                    columnsPart.append(", WRK_TTL");
                    valuesPart.append(", ?");
                    formattedStr = "%" + exemption.getExemptionValue().replaceAll("\\s+", "%") + "%";
                    valuesList.add(formattedStr);
                } else if ("LIBRARY_DISC_ID".equals(exemption.getExemptionType())) {
                    columnsPart.append(", LIBRARY_DISC_ID");
                    valuesPart.append(", ?");
                    valuesList.add(exemption.getExemptionValue() + "%");
                } else if ("WRITER".equals(exemption.getExemptionType())) {
                    columnsPart.append(", WRITER");
                    valuesPart.append(", ?");
                    formattedStr = "%" + exemption.getExemptionValue().replaceAll("\\s+", "%") + "%";
                    valuesList.add(formattedStr);
                }
            }
            columnsPart.append(", DISPLAY_VAL");
            valuesPart.append(", ?");
            valuesList.add(exemption.getExemptionValue());
            columnsPart.append(", CRE_ID");
            valuesPart.append(", ?");
            valuesList.add(exemption.getUserId());
            columnsPart.append(", DEL_FL");
            valuesPart.append(", 'N'");
            columnsPart.append(", CRE_DT");
            valuesPart.append(", SYSDATE");
            columnsPart.append(") ");
            valuesPart.append(") ");
            sqlStr.append(columnsPart).append(valuesPart);
            log.debug("Generated Dynamic SQL Query :" + sqlStr.toString());
            int totalParameters;
            totalParameters = valuesList.size();
            List<Object> params = new ArrayList<>();
            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                params.add((String) valuesList.get(parameterId));
            }
            ascapJdbcTemplate.update(sqlStr.toString(), params.toArray());
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exiting UsageDAOImpl- addExemption method ");
        return exemption;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#getExemption(exemption)
     */
    public Exemption getExemption(Exemption exemption) throws PrepSystemException {

        log.debug("Entering UsageDAOImpl- getExemption method ");
        if (exemption == null || ValidationUtils.isEmptyOrNull(exemption.getExemptionId())) {
            log.debug("Invalid Input... Returning");
            return exemption;
        }
        try {
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_EXEMPTION, exemption.getExemptionId());
            if (rs.next()) {
                exemption.setExemptionId(rs.getString("SM_DEDUP_EXEMPTIONS_ID"));
                exemption.setExemptionValue(rs.getString("DISPLAY_VAL"));
                exemption.setExemptionType(rs.getString("EXEMPTION_TYPE"));
            }
        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exting UsageDAOImpl- getExemption method ");
        return exemption;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#updateExemption(exemption)
     */
    public Exemption updateExemption(Exemption exemption) throws PrepSystemException {
        log.debug("Entering UsageDAOImpl- updateExemption method " + exemption);
        if (exemption == null) {
            log.debug("Input vob is null.. Exiting..");
            return exemption;
        }
        StringBuilder sqlStr;
        StringBuilder columnsPart;
        ArrayList<String> valuesList;
        sqlStr = new StringBuilder(UsageQueries.DYNAMIC_UPDATE_EXEMPTION);
        columnsPart = new StringBuilder("");
        try {
            valuesList = new ArrayList<>();
            String formattedStr = null;
            if (!UsageCommonValidations.isEmptyOrNull(exemption.getExemptionType())) {
                if ("PTY_ID".equals(exemption.getExemptionType())) {
                    columnsPart.append(", PTY_ID = ? ");
                    valuesList.add(exemption.getExemptionValue());
                } else if ("WRK_ID".equals(exemption.getExemptionType())) {
                    columnsPart.append(", WRK_ID = ? ");
                    valuesList.add(exemption.getExemptionValue());
                } else if ("PROVIDER_ID".equals(exemption.getExemptionType())) {
                    columnsPart.append(", PROVIDER_ID = ? ");
                    valuesList.add(exemption.getExemptionValue());
                } else if ("WRK_TTL".equals(exemption.getExemptionType())) {
                    columnsPart.append(", WRK_TTL = ? ");
                    formattedStr = "%" + exemption.getExemptionValue().replaceAll("\\s+", "%") + "%";
                    valuesList.add(formattedStr);
                } else if ("LIBRARY_DISC_ID".equals(exemption.getExemptionType())) {
                    columnsPart.append(", LIBRARY_DISC_ID = ? ");
                    valuesList.add(exemption.getExemptionValue() + "%");
                } else if ("WRITER".equals(exemption.getExemptionType())) {
                    columnsPart.append(", WRITER = ? ");
                    formattedStr = "%" + exemption.getExemptionValue().replaceAll("\\s+", "%") + "%";
                    valuesList.add(formattedStr);
                }
            }
            columnsPart.append(", DISPLAY_VAL = ? ");
            valuesList.add(exemption.getExemptionValue());
            columnsPart.append(", UPD_ID = ? ");
            valuesList.add(exemption.getUserId());
            columnsPart.append(" WHERE SM_DEDUP_EXEMPTIONS_ID = ? ");
            valuesList.add(exemption.getExemptionId());
            sqlStr.append(columnsPart);
            log.debug("Generated Dynamic SQL Query :" + sqlStr.toString());
            int totalParameters;
            totalParameters = valuesList.size();
            List<Object> params = new ArrayList<>();
            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                params.add((String) valuesList.get(parameterId));
                log.debug("param " + (parameterId + 1) + "  value " + ((String) valuesList.get(parameterId)));
            }
            ascapJdbcTemplate.update(sqlStr.toString(), params.toArray());
        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exiting UsageDAOImpl- updateExemption method");
        return exemption;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#deleteExemptions(exemptionList)
     */
    public ExemptionList deleteExemptions(ExemptionList exemptionList) throws PrepSystemException {

        log.debug("Entering UsageDAOImpl- deleteExemptions method ");
        if (exemptionList == null || exemptionList.getSearchResults() == null) {
            log.debug("Invalid Input obect.. Returning..");
            return exemptionList;
        }
        try {
            List<Object> exemptsionsCol = exemptionList.getSearchResults();
            if (exemptsionsCol.size() > 0) {
                Iterator<Object> itr = exemptsionsCol.iterator();
                Exemption ex = null;
                List<Object[]> paramList = new ArrayList<>();
                while (itr.hasNext()) {
                    ex = (Exemption) itr.next();
                    Object[] params = new Object[2];
                    params[0] = exemptionList.getUserId();
                    params[1] = ex.getExemptionId();
                    paramList.add(params);
                }
                ascapJdbcTemplate.batchUpdate(
                    "UPDATE SM_DEDUP_EXEMPTIONS SET DEL_FL = 'Y', UPD_DT = SYSDATE, UPD_ID = ? WHERE SM_DEDUP_EXEMPTIONS_ID = ? ",
                    paramList);
            }
        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exting UsageDAOImpl- deleteExemptions method");
        return exemptionList;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#cancelSampling(samplingSummary)
     */
    public SamplingSummary cancelSampling(SamplingSummary samplingSummary) throws PrepSystemException {
        log.debug("Entering  UsageDAOImpl- cancelSampling method: " + samplingSummary);
        try {
            int count =
                ascapJdbcTemplate.update("DELETE FROM STG_PLAYCNT_RANGES WHERE MUS_USER = ? AND TGTSURVYEARQTR = ? ",
                    samplingSummary.getCallLetter(), samplingSummary.getTargetSurveyYearQuarter());
            log.debug("Deleted " + count + " rows from STG_PLAYCNT_RANGES table ");

            count = ascapJdbcTemplate.update(
                "DELETE FROM SAMP_REQ WHERE STEP_CDE <> 'L1' AND MUS_USER = ? AND TGTSURVYEARQTR = ? AND DEL_FL = 'N' ",
                samplingSummary.getCallLetter(), samplingSummary.getTargetSurveyYearQuarter());

            count = ascapJdbcTemplate.update(
                "UPDATE SAMP_REQ SET STEP_CDE = null, UPD_ID = ?, UPD_DT = SYSDATE, PRC_STT_TM = NULL, PRC_END_TM = NULL, STA_CDE = 'CA' WHERE STEP_CDE = 'L1' AND MUS_USER = ? AND TGTSURVYEARQTR = ?  AND DEL_FL = 'N' ",
                samplingSummary.getUserId(), samplingSummary.getCallLetter(),
                samplingSummary.getTargetSurveyYearQuarter());

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exiting UsageDAOImpl- cancelSampling method: " + samplingSummary);
        return samplingSummary;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#getSamplingDetails(samplingSummary)
     */
    public SamplingSummary getSamplingDetails(SamplingSummary samplingSummary) throws PrepSystemException {

        log.debug("Entering UsageDAOImpl- getSamplingDetails method: " + samplingSummary);
        StringBuilder sb = new StringBuilder(UsageQueries.GET_SAMPLING_DETAILS_LIST);
        SamplingResult samplingResult;
        List<Object> col = new ArrayList<>();
        try {
            String hasForeignAffiliationFlag = null;
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_MUSUSER_HAS_FRN_AFFL,
                samplingSummary.getCallLetter());
            log.debug("CallLetter:= " + samplingSummary.getCallLetter());
            if (rs.next()) {
                hasForeignAffiliationFlag = rs.getString("HAS_FRN_AFFL");
            }
            if (samplingSummary.getCallLetter().startsWith("NIELSEN"))
                hasForeignAffiliationFlag = "N";
            log.debug("Executing Query : " + sb.toString());
            rs = ascapJdbcTemplate.queryForRowSet(sb.toString(), samplingSummary.getCallLetter(),
                samplingSummary.getTargetSurveyYearQuarter());
            while (rs.next()) {
                samplingResult = new SamplingResult();
                samplingResult.setCallLetter(rs.getString("MUS_USER"));
                samplingResult.setTargetSurveyYearQuarter(rs.getString("TGTSURVYEARQTR"));
                samplingResult.setRangeStart(rs.getString("RANGE_START"));
                samplingResult.setRangeEnd(rs.getString("RANGE_END"));
                samplingResult.setTotalMatchedPerfCount(rs.getString("TOT_MATCHED_WP"));
                samplingResult.setTotalMatchedPlayCount(rs.getString("TOT_MATCHED_PLAYCNT"));
                samplingResult.setTotalUnMatchedPerfCount(rs.getString("TOT_UNMATCHED_WP"));
                samplingResult.setTotalUnMatchedPlayCount(rs.getString("TOT_UNMATCHED_PLAYCNT"));
                samplingResult.setSelectPercent(rs.getString("SELECT_PERCENT"));

                samplingResult.setSelectCount(rs.getString("SELECT_COUNT"));
                samplingResult.setSelectStart(rs.getString("SELECT_START"));
                samplingResult.setSelectSkip(rs.getString("SELECT_SKIP"));
                samplingResult.setCensusSampleIndicator(rs.getString("CENSUS_SAMPLE_IND"));
                samplingResult.setMergeIndicator(rs.getString("MERGE_IND"));

                samplingResult.setPlayCountRangeId(rs.getString("STG_PLAYCNT_RANGES_ID"));
                samplingResult.setProcessedFlag(rs.getString("PROCESSED"));
                samplingResult.setTotalUnMatchedForeignPerfCount(rs.getString("TOT_UNMATCHED_FRNAFL_WP"));
                samplingResult.setTotalUnMatchedForeignPlayCount(rs.getString("TOT_UNMATCHED_FRNAFL_PLAYCNT"));
                samplingResult.setIncludeForeignAffFlag(rs.getString("INCLUDE_FRNAFL_FLAG"));

                col.add(samplingResult);
            }
            samplingSummary.setHasForeignAffiliationFlag(hasForeignAffiliationFlag);
            samplingSummary.setSearchResults(col);

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exiting UsageDAOImpl- getSamplingDetails method ");
        return samplingSummary;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#updateSamplingSummary(samplingRequest)
     */
    public SamplingRequest updateSamplingSummary(SamplingRequest samplingRequest) throws PrepSystemException {

        log.debug("Entering UsageDAOImpl- updateSamplingSummary method : SamplingRequest: " + samplingRequest);
        try {
            if ("L1".equals(samplingRequest.getStepCode())) {
                String query =
                    "UPDATE SAMP_REQ SET STEP_CDE = ?, UPD_DT = SYSDATE, UPD_ID = ?, INIT_NUM_SETS = ?, STA_CDE = 'IQ' WHERE SAMP_REQ_ID = ? ";

                ascapJdbcTemplate.update(new PreparedStatementCreator() {

                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        // TODO Auto-generated method stub
                        PreparedStatement pstmt = con.prepareStatement(query);
                        pstmt.setString(1, samplingRequest.getStepCode());
                        pstmt.setString(2, samplingRequest.getUserId());
                        pstmt.setString(3, samplingRequest.getPlayCountRanges());
                        pstmt.setString(4, samplingRequest.getSamplingRequestId());
                        return pstmt;
                    }
                });
                log.debug("Calling callSamplingBatchRequest - SamplingRequestId:"
                    + samplingRequest.getSamplingRequestId() + " StepCode:" + samplingRequest.getStepCode());
            }
            this.callSamplingBatchRequest(ascapJdbcTemplate, samplingRequest.getSamplingRequestId(),
                samplingRequest.getStepCode());

        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exiting UsageDAOImpl- updateSamplingSummary method ");
        return samplingRequest;
    }

    private void callSamplingBatchRequest(JdbcTemplate ascapJdbcTemplate, String requestId, String stepCode)
        throws PrepSystemException {

        log.debug("Entering UsageDAOImpl- callSamplingBatchRequest method ");
        log.debug("requestId : " + requestId);
        log.debug("stepCode : " + stepCode);

        try {
            String query = UsageQueries.BATCH_APM_SAMPLING_L1_REQUEST;
            if ("L2".equals(stepCode)) {
                query = UsageQueries.BATCH_APM_SAMPLING_L2_REQUEST;
            } else if ("L3".equals(stepCode)) {
                log.debug("Calling Level 3 Request");
                query = UsageQueries.BATCH_APM_SAMPLING_L3_REQUEST;
            } else if ("L4".equals(stepCode)) {
                log.debug("Calling Level 4 - Bypass Request");
                query = UsageQueries.BATCH_APM_SAMPLING_L4_REQUEST;
            }
            ascapJdbcTemplate.update(query, requestId);
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException("error.batch.sql.sqlexception", e);
        }
        log.debug("Exiting UsageDAOImpl- callSamplingBatchRequest method ");
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#addSamplingRequest(samplingRequest)
     */
    public SamplingRequest addSamplingRequest(SamplingRequest samplingRequest) throws PrepSystemException {
        try {
            if (samplingRequest == null) {
                return samplingRequest;
            }
            String playCountRanges = samplingRequest.getPlayCountRanges();
            if ("L2".equals(samplingRequest.getStepCode()) || "L3".equals(samplingRequest.getStepCode())) {
                SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_SAMPLING_PLAY_CNT_RANGES,
                    samplingRequest.getCallLetter(), samplingRequest.getTargetSurveyYearQuarter());
                if (rs.next()) {
                    playCountRanges = rs.getString("INIT_NUM_SETS");
                }
                samplingRequest.setPlayCountRanges(playCountRanges);
            }
            this.createBasicSamplingRequest(ascapJdbcTemplate, samplingRequest);
        } catch (PrepSystemException se) {
            log.error(se);
            throw new PrepSystemException("error.usage.sql.sqlexception", se);
        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        return samplingRequest;
    }

    private SamplingRequest createBasicSamplingRequest(JdbcTemplate ascapJdbcTemplate, SamplingRequest samplingRequest)
        throws PrepSystemException {
        log.debug("Entering UsageDAOImpl- createBasicSamplingRequest method ");
        try {
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet("SELECT SAMP_REQ_ID.NEXTVAL FROM DUAL");
            String id = null;
            if (rs.next()) {
                id = rs.getString(1);
            }
            samplingRequest.setSamplingRequestId(id);
            log.debug("Calltr: : " + samplingRequest.getCallLetter());
            if (samplingRequest.getCallLetter().startsWith("NIELSEN")) {
                int updated = ascapJdbcTemplate.update(new PreparedStatementCreator() {

                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        // TODO Auto-generated method stub
                        PreparedStatement pstmt =
                            con.prepareStatement(UsageQueries.INSERT_SAMP_REQ_MUS_USER_TYP_CDE_GEN);
                        pstmt.setString(1, samplingRequest.getSamplingRequestId());
                        pstmt.setString(2, samplingRequest.getCallLetter());
                        pstmt.setString(3, samplingRequest.getPlayCountRanges());
                        pstmt.setString(4, samplingRequest.getStepCode());
                        pstmt.setString(5, "IQ");
                        pstmt.setString(6, samplingRequest.getTargetSurveyYearQuarter());
                        pstmt.setString(7, samplingRequest.getUserId());
                        pstmt.setString(8, samplingRequest.getNumberOfPerformancesToBeSampled());
                        pstmt.setString(9, "NIELSEN");
                        pstmt.setString(10, samplingRequest.getCallLetter().substring(8, 13));
                        return pstmt;
                    }
                });
                log.debug("MUS_USER_TYP_CDE: " + samplingRequest.getCallLetter().substring(8, 13));
            } else {
                int updated = ascapJdbcTemplate.update(new PreparedStatementCreator() {

                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pstmt = con.prepareStatement(UsageQueries.INSERT_SAMP_REQ);
                        pstmt.setString(1, samplingRequest.getSamplingRequestId());
                        pstmt.setString(2, samplingRequest.getCallLetter());
                        pstmt.setString(3, samplingRequest.getPlayCountRanges());
                        pstmt.setString(4, samplingRequest.getStepCode());
                        pstmt.setString(5, "IQ");
                        pstmt.setString(6, samplingRequest.getTargetSurveyYearQuarter());
                        pstmt.setString(7, samplingRequest.getUserId());
                        pstmt.setString(8, samplingRequest.getNumberOfPerformancesToBeSampled());
                        return pstmt;
                    }
                });
            }
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exiting UsageDAOImpl- createBasicSamplingRequest method ");
        return samplingRequest;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#updateSamplingResults(samplingSummary)
     */
    public SamplingSummary updateSamplingResults(SamplingSummary samplingSummary) throws PrepSystemException {

        log.debug("Entering  UsageDAOImpl- updateSamplingResults method : samplingSummary: " + samplingSummary);
        List<Object> col = samplingSummary.getSearchResults();
        Iterator<Object> itr = null;
        if (col == null) {
            log.debug("Input Collection is null.. Returning.. null");
            return samplingSummary;
        } else {
            itr = col.iterator();
        }
        StringBuilder sqlStr = null;
        sqlStr = new StringBuilder(UsageQueries.UPDATE_STG_PLAYCNT_RANGES);
        if ("L3".equals(samplingSummary.getStepNumber())) {
            sqlStr = new StringBuilder(UsageQueries.UPDATE_STG_PLAYCNT_RANGES_L2);
        }
        SamplingResult sr = null;
        try {
            List<Object[]> parametersList = new ArrayList<Object[]>();
            while (itr.hasNext()) {
                Object[] params = new Object[5];
                sr = (SamplingResult) itr.next();
                if ("L3".equals(samplingSummary.getStepNumber())) {
                    params[0] = sr.getSelectPercent();
                    params[1] = sr.getSelectCount();
                    params[2] = sr.getSelectStart();
                    params[3] = sr.getSelectSkip();
                    params[4] = sr.getPlayCountRangeId();
                } else {
                    params[0] = sr.getCensusSampleIndicator();
                    params[1] = sr.getMergeIndicator();
                    params[2] = sr.getIncludeForeignAffFlag();
                    params[3] = sr.getPlayCountRangeId();
                }
                parametersList.add(params);
            }
            ascapJdbcTemplate.batchUpdate(sqlStr.toString(), parametersList);
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exiting UsageDAOImpl- updateSamplingResults method ");
        return samplingSummary;
    }

    /**
     * @see com.ascap.apm.database.usage.UsageDAOImpl#bypassSampling(samplingRequest)
     */
    public SamplingRequest bypassSampling(SamplingRequest samplingRequest) throws PrepSystemException {

        log.debug("entering bypassSampling");
        log.debug("TargetSurveyYearQuarter " + samplingRequest.getTargetSurveyYearQuarter());
        log.debug("MUS_USER " + samplingRequest.getCallLetter());
        try {
            // get play count range
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_SAMPLING_PLAY_CNT_RANGES,
                samplingRequest.getCallLetter(), samplingRequest.getTargetSurveyYearQuarter());
            String playCountRanges = "";
            if (rs.next()) {
                playCountRanges = rs.getString("INIT_NUM_SETS");
            }
            samplingRequest.setPlayCountRanges(playCountRanges);

            // insert L4 record
            SqlRowSet dualRs = ascapJdbcTemplate.queryForRowSet("SELECT SAMP_REQ_ID.NEXTVAL FROM DUAL");
            String id = null;
            if (dualRs.next()) {
                id = dualRs.getString(1);
            }
            samplingRequest.setSamplingRequestId(id);
            if (samplingRequest.getCallLetter().startsWith("NIELSEN")) {
                ascapJdbcTemplate.update(new PreparedStatementCreator() {

                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        // TODO Auto-generated method stub
                        PreparedStatement pstmt =
                            con.prepareStatement(UsageQueries.INSERT_SAMP_REQ_MUS_USER_TYP_CDE_GEN);
                        pstmt.setString(1, samplingRequest.getSamplingRequestId());
                        pstmt.setString(2, samplingRequest.getCallLetter());
                        pstmt.setString(3, samplingRequest.getPlayCountRanges());
                        pstmt.setString(4, samplingRequest.getStepCode());
                        pstmt.setString(5, "IQ");
                        pstmt.setString(6, samplingRequest.getTargetSurveyYearQuarter());
                        pstmt.setString(7, samplingRequest.getUserId());
                        pstmt.setString(8, samplingRequest.getNumberOfPerformancesToBeSampled());
                        pstmt.setString(9, "NIELSEN");
                        pstmt.setString(10, samplingRequest.getCallLetter().substring(8, 13));
                        return pstmt;
                    }
                });
            } else {
                ascapJdbcTemplate.update(new PreparedStatementCreator() {

                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        // TODO Auto-generated method stub
                        PreparedStatement pstmt = con.prepareStatement(UsageQueries.INSERT_SAMP_REQ);

                        pstmt.setString(2, samplingRequest.getCallLetter());
                        pstmt.setString(3, samplingRequest.getPlayCountRanges());
                        pstmt.setString(4, samplingRequest.getStepCode());
                        pstmt.setString(5, "IQ");
                        pstmt.setString(6, samplingRequest.getTargetSurveyYearQuarter());
                        pstmt.setString(7, samplingRequest.getUserId());
                        pstmt.setString(8, samplingRequest.getNumberOfPerformancesToBeSampled());
                        return pstmt;
                    }
                });
            }
            // call L4 proc
            ascapJdbcTemplate.update(UsageQueries.BATCH_APM_SAMPLING_L4_REQUEST, id);
        } catch (Exception e) {
            log.error(e);
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("exiting bypassSampling");
        return samplingRequest;
    }

    public ApmPerformanceBulkRequestList
        cloneWorkPerformances(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering cloneWorkPerformances in UsageDAOImpl");
        if (apmPerformanceBulkRequestList == null) {
            return null;
        }
        log.debug("Operation Type " + apmPerformanceBulkRequestList.getOperationType());

        boolean fromUnmatch = false;

        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyMultiWorkId())) {

            if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getOperationType())
                && "MEDLEY_UPDATE".equals(apmPerformanceBulkRequestList.getOperationType())) {
                log.debug("Mult Work Id is not null forwarding to unmatch medeley "
                    + apmPerformanceBulkRequestList.getMedleyMultiWorkId());
                fromUnmatch = true;
                unmatchMedley(apmPerformanceBulkRequestList);
            } else {
                log.debug("Mult Work Id is not null forwarding to addToMedley "
                    + apmPerformanceBulkRequestList.getMedleyMultiWorkId());
                fromUnmatch = false;
                return addToMedley(apmPerformanceBulkRequestList);
            }
        }

        String leanredMatchTableName = null;

        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWorkTitle())
            && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyPerformerName())) {

            leanredMatchTableName = "APM_LEARNED_MATCH";
        } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWorkTitle())
            && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWriterName())) {

            leanredMatchTableName = "APM_LEARNED_MATCH_WTR";
        } else {
            log.debug("Title and (Writer or Performer) is null...");
            return apmPerformanceBulkRequestList;
        }

        try {
            String activeSurveyYearQuarter = getActiveSurveyYearQuarter();
            List<WorkPerformance> wpList = getOriginalPerformancesForMultiMatch(activeSurveyYearQuarter, null,
                apmPerformanceBulkRequestList.getMedleySupplierCode(),
                apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                apmPerformanceBulkRequestList.getMedleyPerformerName(),
                apmPerformanceBulkRequestList.getMedleyWriterName(), "MEDLEY_NEW");
            String[] medleyWrokIds = apmPerformanceBulkRequestList.getMedleyWorkIds();
            String[] medleyCloneCounts = apmPerformanceBulkRequestList.getMedleyCloneCounts();

            int numMedleyWorks = 0;
            if (medleyWrokIds != null) {
                log.debug("Medley Work ID Length: " + medleyWrokIds.length);
                numMedleyWorks = medleyWrokIds.length;
            } else {
                log.debug("Medley Work Id is null..");
            }

            String multiWorkId = apmPerformanceBulkRequestList.getMedleyMultiWorkId();
            if (ValidationUtils.isEmptyOrNull(multiWorkId) && numMedleyWorks > 1) {
                multiWorkId = this.getSequence("SELECT PAPM.MULT_WRK_ID.NEXTVAL FROM DUAL");
            } else if (fromUnmatch && numMedleyWorks <= 1) {
                multiWorkId = null;
            }

            List<Object> uniquePerfHeaderIds = new ArrayList<>();
            List<WorkPerformance> newWPList = new ArrayList<>();

            for (WorkPerformance wp : wpList) {
                if (!uniquePerfHeaderIds.contains(wp.getPerformanceHeaderId())) {
                    uniquePerfHeaderIds.add(wp.getPerformanceHeaderId());
                }
                if (medleyWrokIds != null && medleyWrokIds.length > 0) {
                    int cloneCount = 1;
                    for (int i = 0; i < medleyWrokIds.length; i++) {
                        try {
                            cloneCount = Integer.parseInt(medleyCloneCounts[i]);
                        } catch (Exception e) {
                            log.error("Exception " + e.getMessage());
                            log.error("Resetting clone count to 1");
                            cloneCount = 1;
                        }

                        if (i == 0) {

                            String qryStr =
                                "UPDATE APM_WRK_PERF SET WRK_ID = ?, APM_MATCH_TYP = 'MAN', MAN_MATCH_USR_ID = ?, MULT_WRK_ID = ?, UPD_ID = ?, UPD_DT = SYSDATE, MAN_MATCH_DT = SYSDATE WHERE APM_WRK_PERF_ID = ?";

                            ArrayList<Object> params = new ArrayList<>();

                            params.add(medleyWrokIds[0]);
                            params.add(apmPerformanceBulkRequestList.getUserId());
                            params.add(multiWorkId);
                            params.add(apmPerformanceBulkRequestList.getUserId());
                            params.add(wp.getWorkPerformanceId());
                            ascapJdbcTemplate.update(qryStr.toString(), params.toArray());

                            if (cloneCount > 1) {
                                for (int cCount = 1; cCount < cloneCount; cCount++) {
                                    WorkPerformance wpNew = new WorkPerformance();
                                    wpNew = copyWorkPerfVob(wpNew, wp);

                                    wpNew.setWorkId(medleyWrokIds[i]);
                                    wpNew.setUserId(apmPerformanceBulkRequestList.getUserId());

                                    newWPList.add(wpNew);
                                }
                            }

                        } else {

                            int updatedDeletedPerfCount = 0;
                            if (updatedDeletedPerfCount <= 0) {
                                WorkPerformance wpNew = new WorkPerformance();
                                wpNew = copyWorkPerfVob(wpNew, wp);
                                wpNew.setWorkId(medleyWrokIds[i]);
                                wpNew.setUserId(apmPerformanceBulkRequestList.getUserId());
                                newWPList.add(wpNew);
                            }
                            if (cloneCount > 1) {
                                for (int cCount = 1; cCount < cloneCount; cCount++) {
                                    WorkPerformance wpNew = new WorkPerformance();
                                    wpNew = copyWorkPerfVob(wpNew, wp);
                                    wpNew.setWorkId(medleyWrokIds[i]);
                                    wpNew.setUserId(apmPerformanceBulkRequestList.getUserId());
                                    newWPList.add(wpNew);
                                }
                            }
                        }
                    }

                }

                if (medleyWrokIds == null || medleyWrokIds.length == 0) {
                    if (wp != null && !ValidationUtils.isEmptyOrNull(wp.getWorkPerformanceId())) {
                        String qry =
                            "UPDATE APM_WRK_PERF SET MULT_WRK_ID = NULL, UPD_DT=SYSDATE, UPD_ID = ? WHERE APM_WRK_PERF_ID = ?";

                        log.debug("UPDATING ORIGINAL PERF TO MULT_WRK_ID NULL BECAUSE THERE ARE NO WORK_IDs");
                        ascapJdbcTemplate.update(qry, apmPerformanceBulkRequestList.getUserId(),
                            wp.getWorkPerformanceId());
                    } else {
                        log.debug(
                            "Should not happen, either Original WP is null or it does not have a APM_WRK_PERF_ID ");
                    }
                }
            }

            this.addNewClonedWorkPerforance(activeSurveyYearQuarter, newWPList, multiWorkId);
            this.updateHeaderCounts(uniquePerfHeaderIds, apmPerformanceBulkRequestList.getUserId());

            if (medleyWrokIds != null && medleyWrokIds.length > 0) {
                for (int i = 0; i < medleyWrokIds.length; i++) {

                    if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyPerformerName()))
                        updateMedleyLeanredMatch(apmPerformanceBulkRequestList, multiWorkId,
                            apmPerformanceBulkRequestList.getMedleySupplierCode(),
                            apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                            apmPerformanceBulkRequestList.getMedleyPerformerName(),
                            apmPerformanceBulkRequestList.getMedleyWriterName(), medleyWrokIds[i],
                            medleyCloneCounts[i]);
                    else if (writerMatchPermitted(apmPerformanceBulkRequestList)) {
                        updateMedleyLeanredMatch(apmPerformanceBulkRequestList, multiWorkId,
                            apmPerformanceBulkRequestList.getMedleySupplierCode(),
                            apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                            apmPerformanceBulkRequestList.getMedleyPerformerName(),
                            apmPerformanceBulkRequestList.getMedleyWriterName(), medleyWrokIds[i],
                            medleyCloneCounts[i]);
                    }

                }
            }

            if (!ValidationUtils.isEmptyOrNull(multiWorkId)) {
                String qry = null;
                if ("APM_LEARNED_MATCH".equals(leanredMatchTableName)) {
                    qry = UsageQueries.UNMATCH_MEDELY_LEANRED_MATCH;
                } else {
                    qry = UsageQueries.UNMATCH_MEDELY_LEANRED_MATCH_WTR;
                }
                int numDeleted =
                    ascapJdbcTemplate.update(qry, apmPerformanceBulkRequestList.getUserId(), multiWorkId, multiWorkId);
                log.debug("Number of Leanred matches deleted: " + numDeleted);
            }
            if (medleyWrokIds.length == 1) {
                ApmPerformanceBulkRequest apmRequest = new ApmPerformanceBulkRequest();
                apmRequest.setWorkId(apmPerformanceBulkRequestList.getMedleyWorkIds()[0]);
                apmRequest.setSupplierCode(apmPerformanceBulkRequestList.getMedleySupplierCode());
                apmRequest.setPerformerName(apmPerformanceBulkRequestList.getMedleyPerformerName());
                apmRequest.setWorkTitle(apmPerformanceBulkRequestList.getMedleyWorkTitle());
                apmRequest.setWriterName(apmPerformanceBulkRequestList.getMedleyWriterName());
                apmRequest.setRequestTypeCode("UPD");
                apmRequest.setUserId(apmPerformanceBulkRequestList.getUserId());
                this.addApmPerfBulkRequest(apmRequest);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exiting cloneWorkPerformances in UsageDAOImpl");
        return apmPerformanceBulkRequestList;
    }

    public ApmPerformanceBulkRequestList updateApmPerfBulkRequestList(
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList) throws PrepSystemException {
        log.debug("Entering UsageDAOImpl - updateApmPerfBulkRequestList method ");

        if (apmPerformanceBulkRequestList == null) {
            return apmPerformanceBulkRequestList;
        }

        String activeTargetSurveyYearQtr = null;

        List<Object> col = apmPerformanceBulkRequestList.getSearchResults();
        Iterator<?> itr = null;

        if (col == null) {
            log.debug("Input Collection is null.. Returning.. null");
            return apmPerformanceBulkRequestList;
        } else {
            itr = col.iterator();
        }

        StringBuilder sqlStr = null;
        sqlStr = new StringBuilder(UsageQueries.INSERT_APM_PERF_BLK_REQ);

        try {

            String apmPerfBulkRequestId = null;
            String apmPerfBulkRequestGroupId = null;

            activeTargetSurveyYearQtr = getActiveSurveyYearQuarter();

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_SEQUENCE_APM_PERF_BLK_REQ_GRP_ID);
            if (rs.next()) {
                apmPerfBulkRequestGroupId = rs.getString(1);
            }

            ApmPerformanceBulkRequest apmFile = null;
            String userId = null;
            List<Object[]> parametersList = new ArrayList<>();

            while (itr.hasNext()) {

                String qry = UsageQueries.GET_SEQUENCE_APM_PERF_BLK_REQ_ID;
                rs = ascapJdbcTemplate.queryForRowSet(qry);

                if (rs.next()) {
                    apmPerfBulkRequestId = rs.getString(1);
                }

                apmFile = (ApmPerformanceBulkRequest) itr.next();

                Object[] params = new Object[13];
                params[0] = apmPerfBulkRequestId;
                params[1] = apmPerfBulkRequestGroupId;
                params[2] = apmFile.getSupplierCode();
                params[3] = apmFile.getPerformerName();
                params[4] = apmFile.getWorkTitle();
                if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH
                    .equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {
                    params[5] = apmFile.getOriginalWorkId();
                } else {
                    params[5] = apmFile.getWorkId();
                }
                params[6] = apmFile.getWorkPerfCount();
                params[7] = apmFile.getPlayCount();
                params[8] = UsageConstants.BULK_REQUEST_STATUS_PENDING;
                params[9] = apmFile.getUserId();
                params[10] = apmPerformanceBulkRequestList.getRequestTypeCode();
                params[11] = apmFile.getWriterName();

                params[12] = activeTargetSurveyYearQtr;

                userId = apmFile.getUserId();
                parametersList.add(params);
            }

            ascapJdbcTemplate.batchUpdate(sqlStr.toString(), parametersList);

            log.debug("Calling Batch Process with groupId " + apmPerfBulkRequestGroupId + " and userId " + userId);
            this.callPerformanceBatchBulkUpdate(apmPerfBulkRequestGroupId, userId);

            if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(apmPerformanceBulkRequestList.getRequestTypeCode())) {
                String headerIsValid = this.validateHeaderForUndelete(apmPerfBulkRequestGroupId, userId);
                if ("Y".equals(headerIsValid)) {
                    apmPerformanceBulkRequestList.setInvalidUndeleteHeaderExists("Y");
                }
                log.debug("Invalid header? " + apmPerformanceBulkRequestList.getInvalidUndeleteHeaderExists());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);

        }

        log.debug("Exiting UsageDaoImpl - updateApmPerfBulkRequestList method");

        return apmPerformanceBulkRequestList;
    }

    private void callPerformanceBatchBulkUpdate(String groupId, String userId) throws PrepSystemException {

        log.debug("Entering UsageDAOImpl - callPerformanceBatchBulkUpdate");
        log.debug("groupId : " + groupId);
        log.debug("userId : " + userId);

        try {

            ascapJdbcTemplate.update(UsageQueries.BATCH_APM_PERF_BULK_REQUEST, groupId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException("error.batch.sql.sqlexception", e);

        }
        log.debug("Exiting UsageDAOImpl- callPerformanceBatchValidate method");

    }

    private String validateHeaderForUndelete(String groupId, String userId) throws PrepSystemException {

        String result = "N";

        log.debug("Entering UsageDAO - validateHeaderForUndelete");
        log.debug("groupId : " + groupId);
        log.debug("userId : " + userId);

        try {
            String supp_code = null;
            String pfr_na = null;
            String wrk_ttl = null;
            String wrk_id = null;
            String req_typ_cde = null;
            String writer = null;
            String TGTSURVYEARQTR = null;

            String sql = null;

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_APM_PERF_BLK_REQ_RECORD, groupId);

            while (rs.next()) {

                sql = UsageQueries.VALIDATE_HDR_FOR_UNDELETE;

                supp_code = rs.getString("supp_code");
                pfr_na = rs.getString("pfr_na");
                wrk_ttl = rs.getString("wrk_ttl");
                wrk_id = rs.getString("wrk_id");
                writer = rs.getString("writer");

                if (!UsageCommonValidations.isEmptyOrNull(supp_code)) {
                    sql = sql + " and SUPP_CODE = ? ";
                }
                if (!UsageCommonValidations.isEmptyOrNull(writer)) {
                    sql = sql + " and WRITER = ? ";
                }
                if (!UsageCommonValidations.isEmptyOrNull(wrk_ttl)) {
                    sql = sql + " and WRK_TTL = ? ";
                } else {
                    sql = sql + " AND WRK_TTL  IS NULL ";
                }
                if (!UsageCommonValidations.isEmptyOrNull(pfr_na)) {
                    sql = sql + " and PFR_NA = ? ";
                } else {
                    sql = sql + " AND PFR_NA  IS NULL ";
                }

                sql = sql + "  ) ";
                sql = sql + " and (proc_status not in ('BUSVLD','PBVLd') or del_fl = 'Y'  )  ";

                int i = 0;
                ArrayList<Object> params = new ArrayList<>();
                if (!UsageCommonValidations.isEmptyOrNull(supp_code)) {
                    i++;
                    params.add(supp_code);
                }
                if (!UsageCommonValidations.isEmptyOrNull(writer)) {
                    i++;
                    params.add(writer);
                }
                if (!UsageCommonValidations.isEmptyOrNull(wrk_ttl)) {
                    i++;
                    params.add(wrk_ttl);
                }
                if (!UsageCommonValidations.isEmptyOrNull(pfr_na)) {
                    i++;
                    params.add(pfr_na);
                }

                SqlRowSet rs2 = ascapJdbcTemplate.queryForRowSet(sql, params.toArray());

                if (rs2.next()) {
                    if ("Y".equalsIgnoreCase(rs2.getString("invalidHeaderExists"))) {
                        result = rs2.getString("invalidHeaderExists");
                        break;
                    }
                }

            }

        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }
        log.debug("Exiting UsageDAOImpl - validateHeaderForUndelete method");

        return result;

    }

    private List<WorkPerformance> getOriginalPerformancesForMultiMatch(String activeSurveyYearQuarter,
        String multiWorkId, String suppCode, String workTitle, String performerName, String writerName,
        String medleyMode) throws PrepSystemException {
        log.debug("Entering private getOriginalPerformancesForMultiMatch in UsageDAOImpl , medleyMode: " + medleyMode);

        WorkPerformance wp = null;
        List<WorkPerformance> list = new ArrayList<>();

        StringBuilder qry = null;
        ArrayList<String> params = null;

        if ("MEDLEY_NEW".equals(medleyMode)) {
            qry = new StringBuilder();
            params = new ArrayList<>();

            qry.append(UsageQueries.MEDLEY_GROUP_PERFS_ORIGINAL_COLUMNS);
            qry.append(UsageQueries.MEDLEY_GROUP_PERFS_START);
            if (!ValidationUtils.isEmptyOrNull(suppCode)) {
                qry.append(" AND A.SUPP_CODE = ? ");
                params.add(suppCode);
            }
            if (!ValidationUtils.isEmptyOrNull(workTitle)) {
                qry.append(" AND A.WRK_TTL = ? ");
                params.add(workTitle);
            } else {
                qry.append(" AND A.WRK_TTL is null ");
            }
            if (!ValidationUtils.isEmptyOrNull(performerName)) {
                qry.append(" AND A.PFR_NA = ? ");
                params.add(performerName);
            } else {
                qry.append(" AND A.PFR_NA is null ");
                if (!ValidationUtils.isEmptyOrNull(writerName)) {
                    qry.append(" AND A.WRITER = ? ");
                    params.add(writerName);
                } else {
                    qry.append(" AND A.WRITER IS NULL ");
                }

            }

            qry.append(UsageQueries.MEDLEY_GROUP_PERFS_END);
            params.add(activeSurveyYearQuarter);
        } else if ("MEDLEY_ADD".equals(medleyMode)) {
            qry = new StringBuilder();
            params = new ArrayList<>();
            qry.append(UsageQueries.MEDLEY_GROUP_PERFS_ORIGINAL_COLUMNS);
            qry.append(UsageQueries.MEDLEY_GROUP_ORIGINAL_PERFS_START);
            params.add(multiWorkId);
            params.add(activeSurveyYearQuarter);
        }

        try {

            int paramCount1 = params.size();
            ArrayList<Object> parameter = new ArrayList<>();

            for (int i = 0; i < paramCount1; i++) {
                parameter.add(params.get(i));
            }
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(qry.toString(), parameter.toArray());
            while (rs.next()) {
                wp = new WorkPerformance();
                wp.setWorkPerformanceId(rs.getString("APM_WRK_PERF_ID"));
                wp.setPerformanceHeaderId(rs.getString("APM_PERF_HDR_ID"));
                wp.setPlayCount(rs.getString("PLAY_CNT"));
                wp.setPerformerName(rs.getString("PFR_NA"));
                wp.setWorkTitle(rs.getString("WRK_TTL"));
                wp.setWorkId(rs.getString("WRK_ID"));
                wp.setUseTypeCode(rs.getString("USE_TYP"));
                wp.setProcStatus(rs.getString("PROC_STATUS"));
                wp.setProviderId(rs.getString("PROVIDER_ID"));
                wp.setErrorFlag(rs.getString("ERR_STATUS"));
                wp.setApmLockIndicator(rs.getString("LOCK_IND"));
                wp.setInstrumentalOrVocalIndicator(rs.getString("INST_VOCL"));
                wp.setSupplierCode(rs.getString("SUPP_CODE"));
                wp.setApmSendToManMatchIndicator(rs.getString("SEND_MAN_MATCH"));
                wp.setApmEstimatedDollarVal(rs.getString("ESTM_DOLLAR_VAL"));
                wp.setApmEstimatedDollarFound(rs.getString("ESTM_DLRVAL_FOUND"));
                wp.setTargetYearQuarter(rs.getString("TGTSURVYEARQTR"));
                wp.setApmSourceSongKey(rs.getString("APM_SOURCE_SONG_KEY"));
                wp.setApmWorkEffectiveEndDate(rs.getString("WRK_EFF_END_DT"));
                wp.setApmWorkEffectiveStartDate(rs.getString("WRK_EFF_STT_DT"));
                wp.setWorkPerformanceDuration(rs.getString("WRK_PERF_DUR"));
                wp.setApmStatusDate(rs.getString("STATUS_DATE"));
                wp.setDetectionTime(rs.getString("DETECTION_TM"));
                wp.setLibraryName(rs.getString("LIBRARY_NM"));
                wp.setLibraryDiscTitle(rs.getString("LIBRARY_DISC"));
                wp.setLibraryDiscId(rs.getString("LIBRARY_DISC_ID"));
                wp.setLibraryTrack(rs.getString("LIBRARY_TRACK"));
                wp.setLibraryTrackId(rs.getString("LIBRARY_TRACK_ID"));
                wp.setWriter(rs.getString("WRITER"));
                wp.setConfidenceLevel(rs.getString("CONFIDENCE_LVL"));
                wp.setAssignedToUser(rs.getString("ASG_USR_ID"));

                list.add(wp);
                log.debug("WWWWWWWWWWWWWWWWWWWWWP   " + wp);
            }
            if (list != null) {
                log.debug("Original Performances size: " + list.size());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);

        }
        log.debug("Exiting private getOriginalPerformancesForMultiMatch");
        return list;
    }

    public ApmPerformanceBulkRequestList addToMedley(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList)
        throws PrepSystemException {
        log.debug("Entering addToMedley in UsageDAOImpl");

        if (apmPerformanceBulkRequestList == null) {
            return null;
        } else {
            log.debug("Input OBJ:  " + apmPerformanceBulkRequestList);
        }

        String leanredMatchTableName = null;

        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWorkTitle())
            && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyPerformerName())) {

            leanredMatchTableName = "APM_LEARNED_MATCH";
        } else if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWorkTitle())
            && !ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyWriterName())) {

            leanredMatchTableName = "APM_LEARNED_MATCH_WTR";
        } else {
            log.debug("Title and (Writer or Performer) is null...");
            return apmPerformanceBulkRequestList;
        }

        try {

            String activeSurveyYearQuarter = getActiveSurveyYearQuarter();

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.GET_MEDLEY_WORKIDS,
                apmPerformanceBulkRequestList.getMedleyMultiWorkId());

            List<Object> existingMedleyWorkIds = new ArrayList<>();
            List<Object> uniquePerfHeaderIds = new ArrayList<>();
            List<WorkPerformance> newWPList = new ArrayList<>();

            while (rs.next()) {
                existingMedleyWorkIds.add(rs.getString("WRK_ID_VLD"));
            }

            List<WorkPerformance> wpList = getOriginalPerformancesForMultiMatch(activeSurveyYearQuarter,
                apmPerformanceBulkRequestList.getMedleyMultiWorkId(),
                apmPerformanceBulkRequestList.getMedleySupplierCode(),
                apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                apmPerformanceBulkRequestList.getMedleyPerformerName(),
                apmPerformanceBulkRequestList.getMedleyWriterName(), "MEDLEY_ADD");

            String[] medleyWrokIds = apmPerformanceBulkRequestList.getMedleyWorkIds();
            String[] medleyCloneCounts = apmPerformanceBulkRequestList.getMedleyCloneCounts();
            if (medleyWrokIds != null) {
                log.debug("Medley WORKD LENGTH:  " + medleyWrokIds.length);
            } else {
                log.debug("Meley WORKD LENGTH is null");
            }

            String multiWorkId = apmPerformanceBulkRequestList.getMedleyMultiWorkId();
            for (WorkPerformance wp : wpList) {
                if (!uniquePerfHeaderIds.contains(wp.getPerformanceHeaderId())) {
                    uniquePerfHeaderIds.add(wp.getPerformanceHeaderId());
                }
                if (medleyWrokIds != null && medleyWrokIds.length > 0) {
                    for (int i = 0; i < medleyWrokIds.length; i++) {
                        if (existingMedleyWorkIds.contains(medleyWrokIds[i])) {
                        } else {
                            int cloneCount = 1;
                            WorkPerformance wpNew = new WorkPerformance();
                            wpNew = copyWorkPerfVob(wpNew, wp);
                            wpNew.setWorkId(medleyWrokIds[i]);
                            wpNew.setUserId(apmPerformanceBulkRequestList.getUserId());

                            try {
                                cloneCount = Integer.parseInt(medleyCloneCounts[i]);
                            } catch (Exception e) {
                                log.error("Exception " + e.getMessage());
                                log.error("Resetting clone count to 1");
                                cloneCount = 1;
                            }

                            for (int x = 0; x < cloneCount; x++) {
                                newWPList.add(wpNew);
                            }
                        }
                    }
                }
            }

            this.addNewClonedWorkPerforance(activeSurveyYearQuarter, newWPList, multiWorkId);
            this.updateHeaderCounts(uniquePerfHeaderIds, apmPerformanceBulkRequestList.getUserId());

            if (medleyWrokIds != null && medleyWrokIds.length > 0) {
                for (int i = 0; i < medleyWrokIds.length; i++) {
                    if (!existingMedleyWorkIds.contains(medleyWrokIds[i])) {

                        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequestList.getMedleyPerformerName()))
                            updateMedleyLeanredMatch(apmPerformanceBulkRequestList, multiWorkId,
                                apmPerformanceBulkRequestList.getMedleySupplierCode(),
                                apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                                apmPerformanceBulkRequestList.getMedleyPerformerName(),
                                apmPerformanceBulkRequestList.getMedleyWriterName(), medleyWrokIds[i],
                                medleyCloneCounts[i]);
                        else if (writerMatchPermitted(apmPerformanceBulkRequestList)) {
                            updateMedleyLeanredMatch(apmPerformanceBulkRequestList, multiWorkId,
                                apmPerformanceBulkRequestList.getMedleySupplierCode(),
                                apmPerformanceBulkRequestList.getMedleyWorkTitle(),
                                apmPerformanceBulkRequestList.getMedleyPerformerName(),
                                apmPerformanceBulkRequestList.getMedleyWriterName(), medleyWrokIds[i],
                                medleyCloneCounts[i]);
                        }
                    }
                }
            }

            if (!ValidationUtils.isEmptyOrNull(multiWorkId)) {

                String qry = null;
                if ("APM_LEARNED_MATCH".equals(leanredMatchTableName)) {
                    qry = UsageQueries.UNMATCH_MEDELY_LEANRED_MATCH;
                } else {
                    qry = UsageQueries.UNMATCH_MEDELY_LEANRED_MATCH_WTR;
                }

                int numDeleted =
                    ascapJdbcTemplate.update(qry, apmPerformanceBulkRequestList.getUserId(), multiWorkId, multiWorkId);
                log.debug("Number of Leanred matches deleted " + numDeleted);
            }

        } catch (Exception e) {

            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);
        }

        log.debug("Exiting addToMedley in UsageDAOImpl");
        return apmPerformanceBulkRequestList;
    }

    private void addNewClonedWorkPerforance(String activeSurveyYearQuarter, List<WorkPerformance> wpList,
        String multiWorkId) throws PrepSystemException {
        log.debug("Entering private addNewClonedWorkPerforance with List in UsageDAO");

        if (wpList == null || !wpList.isEmpty()) {
            return;
        }
        try {
            String qryStr = "INSERT INTO PAPM.APM_WRK_PERF ( "
                + "   APM_PERF_HDR_ID, LINE_NR, APM_WRK_PERF_ID, APM_ARCHIVE_ID,  " + "   APM_MATCH_TYP,  PLAY_CNT,  "
                + "   MEDL_SEQ, PFR_NA, WRK_TTL,  " + "   WRK_ID, WRK_SEQ_NR, USE_TYP,  " + "   PROC_STATUS,   "
                + "   PROVIDER_ID, CRE_DT, CRE_ID,  " + "   ERR_STATUS,DEL_FL, LOCK_IND,  "
                + "   INST_VOCL, SUPP_CODE,   " + "    SEND_MAN_MATCH,  " + "    MAN_MATCH_DT, MAN_MATCH_USR_ID,  "
                + "   ESTM_DOLLAR_VAL, ESTM_DLRVAL_FOUND,  "
                + "   TGTSURVYEARQTR, MULT_WRK_ID, APM_SOURCE_SONG_KEY, WRK_EFF_STT_DT, WRK_EFF_END_DT, WRK_PERF_DUR, STATUS_DATE ,     DETECTION_TM,  LIBRARY_NM,  LIBRARY_DISC, LIBRARY_DISC_ID, LIBRARY_TRACK, LIBRARY_TRACK_ID, WRITER,  CONFIDENCE_LVL, APM_MATCH_DT, ASG_USR_ID )  "
                + " values ("
                + " ?, (select max(to_number(wrk_seq_nr))+1 from apm_wrk_perf where apm_perf_hdr_id =?), APM_WRK_PERF_ID.NEXTVAL, 0, 'MAN', ?, ?, ?, ?, ?, (select max(to_number(wrk_seq_nr))+1 from apm_wrk_perf where apm_perf_hdr_id =?), ?, ?, ?, SYSDATE, ?, ?, 'N', ?, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ? "
                + "  ,?,?,?,?, TO_DATE (?, 'MM/DD/YYYY HH24:MI:SS'), ?,?,?,?,?,?,?,?, SYSDATE,? ) ";

            log.debug("Executing Insert Work Perf query in clonePerformances method " + qryStr);

            int insertedWps = 0;
            for (WorkPerformance wp : wpList) {
                log.debug("workId " + wp);

                ArrayList<Object> params = new ArrayList<>();
                params.add(wp.getPerformanceHeaderId());
                params.add(wp.getPerformanceHeaderId());
                params.add(wp.getPlayCount());
                params.add(wp.getMedleySequenceNumber());
                params.add(wp.getPerformerName());
                params.add(wp.getWorkTitle());
                params.add(wp.getWorkId());
                params.add(wp.getPerformanceHeaderId());
                params.add(wp.getUseTypeCode());
                params.add(wp.getProcStatus());
                params.add(wp.getProviderId());
                params.add("MEDLEYADD");
                params.add(wp.getErrorFlag());
                params.add(wp.getApmLockIndicator());
                params.add(wp.getInstrumentalOrVocalIndicator());
                params.add(wp.getSupplierCode());
                params.add(wp.getApmSendToManMatchIndicator());
                params.add(wp.getUserId());
                params.add(wp.getApmEstimatedDollarVal());
                params.add(wp.getApmEstimatedDollarFound());
                params.add(activeSurveyYearQuarter);
                params.add(multiWorkId);
                params.add(wp.getApmSourceSongKey());
                params.add(wp.getApmWorkEffectiveStartDate());
                params.add(wp.getApmWorkEffectiveEndDate());
                params.add(wp.getWorkPerformanceDuration());
                params.add(wp.getApmStatusDate());
                params.add(wp.getDetectionTime());
                params.add(wp.getLibraryName());
                params.add(wp.getLibraryDiscTitle());
                params.add(wp.getLibraryDiscId());
                params.add(wp.getLibraryTrack());
                params.add(wp.getLibraryTrackId());
                params.add(wp.getWriter());
                params.add(wp.getConfidenceLevel());
                params.add(wp.getAssignedToUser());
                ascapJdbcTemplate.update(qryStr, params.toArray());
                insertedWps++;
            }

            log.debug("Inserted work Perfs " + insertedWps);

        }

        catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);

        }

        log.debug("Exiting private addNewClonedWorkPerforance in UsageDAOImpl");
    }

    private void updateHeaderCounts(List<Object> perfHeaderIdList, String userId) throws PrepSystemException {
        log.debug("Entering private updateHeaderCounts con, perfHeaderIdList, userid, in UsageDAOImpl");

        if (perfHeaderIdList == null || perfHeaderIdList.isEmpty()) {
            return;
        }

        try {

            String qryStr =
                " UPDATE APM_PERF_HDR SET WRK_PERF_CNT = (SELECT COUNT(A.APM_WRK_PERF_ID) CNT FROM APM_WRK_PERF A WHERE A.DEL_FL = 'N' AND A.APM_PERF_HDR_ID = ? ), TOTAL_NUM_PLAYS = (SELECT SUM(B.PLAY_CNT_VLD) TOT_PLAY_CNT FROM APM_WRK_PERF B WHERE B.DEL_FL = 'N' AND B.APM_PERF_HDR_ID = ? ), UPD_ID = ?, UPD_DT = SYSDATE WHERE APM_PERF_HDR_ID = ?  ";

            log.debug("Executing updateHeaderCounts, qry: " + qryStr);

            List<Object[]> parametersList = new ArrayList<>();

            for (Object perfHeaderId : perfHeaderIdList) {

                Object[] params = new Object[4];
                params[0] = perfHeaderId;
                params[1] = perfHeaderId;
                params[2] = userId;
                params[3] = perfHeaderId;

                parametersList.add(params);
            }

            int[] updaedCounts = ascapJdbcTemplate.batchUpdate(qryStr, parametersList);
            log.debug("Updated Num of Headers " + updaedCounts.length);

        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", e);

        }

        log.debug("Exiting private updateHeaderCounts in UsageDAOImpl");
    }

    public ApmPerformanceBulkRequest getApmPerfBulkRequest(ApmPerformanceBulkRequest apmPerformanceBulkRequest)
        throws PrepSystemException {

        log.debug("Entering getApmPerfBulkRequest method in UsageDAOImpl apmPerformanceBulkRequest "
            + apmPerformanceBulkRequest);

        ArrayList<String> searchValuesList = new ArrayList<>();
        StringBuilder sb = new StringBuilder(UsageQueries.GET_APM_PERF_BLK_REQUEST);
        ApmPerformanceBulkRequest outApmPerformanceBulkRequest = null;

        searchValuesList.add(apmPerformanceBulkRequest.getWorkId());

        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getSupplierCode())) {
            sb.append(" AND A.SUPP_CODE = ? ");
            searchValuesList.add(apmPerformanceBulkRequest.getSupplierCode());
        } else {
            sb.append(" AND A.SUPP_CODE IS NULL ");
        }

        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWorkTitle())) {
            sb.append(" AND A.WRK_TTL = ? ");
            searchValuesList.add(apmPerformanceBulkRequest.getWorkTitle());
        } else {
            sb.append(" AND A.WRK_TTL IS NULL ");
        }

        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getPerformerName())) {
            sb.append(" AND A.PFR_NA = ? ");
            searchValuesList.add(apmPerformanceBulkRequest.getPerformerName());
        } else {
            sb.append(" AND A.PFR_NA IS NULL ");
        }
        if (!ValidationUtils.isEmptyOrNull(apmPerformanceBulkRequest.getWriterName())) {
            sb.append(" AND A.WRITER = ? ");
            searchValuesList.add(apmPerformanceBulkRequest.getWriterName());
        } else {
            sb.append(" AND A.WRITER IS NULL ");
        }

        sb.append(" ORDER BY A.UPD_DT DESC ");

        int totalParameters;
        try {
            log.debug("Executing Query : " + sb.toString());
            totalParameters = searchValuesList.size();
            List<Object> params = new ArrayList<>();
            for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                params.add(searchValuesList.get(parameterId));
            }

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(sb.toString(), params.toArray());

            if (rs.next()) {
                outApmPerformanceBulkRequest = new ApmPerformanceBulkRequest();
                outApmPerformanceBulkRequest.setSupplierCode(rs.getString("SUPP_CODE"));
                outApmPerformanceBulkRequest.setPerformerName(rs.getString("PFR_NA"));
                outApmPerformanceBulkRequest.setWorkTitle(rs.getString("WRK_TTL"));
                outApmPerformanceBulkRequest.setWorkId(rs.getString("WRK_ID"));
            }

        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se.getMessage());
            throw new PrepSystemException("error.usage.sql.sqlexception", se);

        }
        log.debug("Exiting getApmPerfBulkRequest method in UsageDAOImpl ");

        return outApmPerformanceBulkRequest;
    }
    
    public EOLearnedMatchList deleteEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList)  throws PrepSystemException {

        log.debug("Entering deleteEOLearnedMatchList of UsageDAOImpl ");

        if (eoLearnedMatchList == null || UsageCommonValidations.isEmptyOrNull(eoLearnedMatchList.getEoLearnedMatchType())) {
            log.debug("Invalid input object or invalid match type to determine lm type");
            return eoLearnedMatchList;
        }
        if (eoLearnedMatchList == null || (eoLearnedMatchList.getMultWorkId() == null  && eoLearnedMatchList.getSelectedIndex() == null )) {
            log.debug("Invalid input object or Invalid select Id and invalid multWorkId");
            return eoLearnedMatchList;
        }

        if(eoLearnedMatchList.getMultWorkId() != null && eoLearnedMatchList.getMultWorkId().length > 0) {
            for(String x: eoLearnedMatchList.getMultWorkId()) {
                log.debug("DAO multworkId:   " + x);
            }
        }

        StringBuilder sqlStr = null;

        try {

            String sqlTableName = "";

            ArrayList<String> valuesList;
            if (!UsageCommonValidations.isEmptyOrNull(eoLearnedMatchList.getEoLearnedMatchType())) {
                if("ISRC".equals(eoLearnedMatchList.getEoLearnedMatchType())) {
                    sqlTableName = " IDMatching.dbo.LearnedMatchISRC ";
                }
                else if("ISWC".equals(eoLearnedMatchList.getEoLearnedMatchType())) {
                    sqlTableName = " IDMatching.dbo.LearnedMatchISWC ";
                }
                else if("URL".equals(eoLearnedMatchList.getEoLearnedMatchType())) {
                    sqlTableName = " IDMatching.dbo.LearnedMatchUrl ";
                }
                else if("SUPPLIERID".equals(eoLearnedMatchList.getEoLearnedMatchType())) {
                    sqlTableName = " IDMatching.dbo.LearnedMatchSuppId ";
                } 
                sqlStr = new StringBuilder("UPDATE " + sqlTableName + " SET DELFLAG = 'Y',  UpdID= ?, UpdDt =  GetDate() WHERE ID = ? and DelFlag = 'N' ");
            }

            String[] ids = null;
            if(eoLearnedMatchList.getMultWorkId() != null && eoLearnedMatchList.getMultWorkId().length > 0) {
                if("URL".equals(eoLearnedMatchList.getEoLearnedMatchType())) {
                    sqlStr = new StringBuilder("UPDATE IDMatching.dbo.LearnedMatchUrl SET DELFLAG = 'Y',  UpdID= ?, UpdDt =  GetDate() WHERE multWrkId = ? and DelFlag = 'N' ");
                }
                else {
                    sqlStr = new StringBuilder("UPDATE IDMatching.dbo.LearnedMatchSuppId SET DELFLAG = 'Y',  UpdID= ?, UpdDt =  GetDate() WHERE mult_Wrk_Id = ? and DelFlag = 'N' ");
                }
                ids = eoLearnedMatchList.getMultWorkId();
            }
            else {
                ids = eoLearnedMatchList.getSelectedIndex();
            }

            int numDeleted = 0;

            List<Object[]> parametersList = new ArrayList<Object[]>();

            for(String id : ids) {

                Object[] params = new Object[2];
                log.debug("Executing query " + sqlStr.toString());
                params[0] = eoLearnedMatchList.getUserId();
                params[1] = id;
                parametersList.add(params);
                numDeleted++;
            }

            if(numDeleted > 0) {
                eoDSJdbcTemplate.batchUpdate(sqlStr.toString(), parametersList);
            }
        }
        catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        } 
        log.debug("Exiting deleteEOLearnedMatchList of UsageDAOImpl"  );

        return eoLearnedMatchList;
    }

    public EOLearnedMatchList getEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList) throws PrepSystemException {
        log.debug("Entering getEOLearnedMatchList in UsageDAO");

        if(eoLearnedMatchList == null || eoLearnedMatchList.getFilterMatchTypeCode() == null) {
            log.debug("Invalid Input object... Retruning......");
        }

        log.debug("filter match type code " + eoLearnedMatchList.getFilterMatchTypeCode());

        EOLearnedMatch eoLearnedMatch = null;

        List<Object> col = new ArrayList<>();

        ArrayList<String> params = null; 
        params = new ArrayList<>();

        try {
            StringBuilder qry = new StringBuilder(UsageQueries.GET_EO_LEARNED_MATCH_LIST_START);

            if(!ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getFilterMatchTypeCode())) {
                if("ISRC".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_COLUMN_LIST_ISRC);
                }
                else if("ISWC".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_COLUMN_LIST_ISWC);
                }
                else if("SUPPLIERID".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_COLUMN_LIST_SUPPLIER_ID);
                }
                else if("URL".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_COLUMN_LIST_URL);
                }
                else {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_COLUMN_LIST_SUPPLIER_ID);  
                }               
            }

            qry.append(UsageQueries.GET_EO_LEARNED_MATCH_LIST_MIDDLE_START);

            if(!ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getFilterMatchTypeCode())) {
                if("ISRC".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_LIST_MIDDLE_COLUMN_ISRC);
                }
                else if("ISWC".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_LIST_MIDDLE_COLUMN_ISWC);
                }
                else if("URL".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_LIST_MIDDLE_COLUMN_URL);
                }
                else if("SUPPLIERID".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_LIST_MIDDLE_COLUMN_SUPPLIER_ID);
                }
                else {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_LIST_MIDDLE_COLUMN_SUPPLIER_ID);
                }               
            }

            qry.append(UsageQueries.GET_EO_LEARNED_MATCH_LIST_MIDDLE_END);

            if(!ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getFilterMatchTypeCode())) {
                if("ISRC".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_TABLE_NAME_ISRC);
                }
                else if("ISWC".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_TABLE_NAME_ISWC);
                }
                else if("URL".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_TABLE_NAME_URL);
                }
                else if("SUPPLIERID".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_TABLE_NAME_SUPPLIER_ID);
                }
                else {
                    qry.append(UsageQueries.GET_EO_LEARNED_MATCH_TABLE_NAME_SUPPLIER_ID);
                }               
            }

            if(!ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getFilterWorkId())) {              
                qry.append(" AND wrkid = ? ");
                params.add(eoLearnedMatchList.getFilterWorkId());
            }
            if(!ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getFilterSupplierCode()) && "SUPPLIERID".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {                
                qry.append(" AND suppcode = ? ");
                params.add(eoLearnedMatchList.getFilterSupplierCode());
            }

            if(!ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getFilterMultWorkId()) && "SUPPLIERID".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {              
                qry.append(" AND mult_wrk_id = ? ");
                params.add(eoLearnedMatchList.getFilterMultWorkId());
            }

            if(!ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getFilterMatchIdValue())) {
                if("ISRC".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(" AND isrc = ? ");
                }
                else if("ISWC".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(" AND iswc = ? ");
                }
                else if("URL".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(" AND url = ? ");
                }
                else if("SUPPLIERID".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    qry.append(" AND suppsongid = ? ");
                }
                else {
                    qry.append(" AND suppsongid = ? ");
                }   
                params.add(eoLearnedMatchList.getFilterMatchIdValue());
            }


            qry.append(UsageQueries.GET_EO_LEARNED_MATCH_LIST_END);


            if (eoLearnedMatchList.getNavigationType()==null) {
                if(eoLearnedMatchList.getNumberOfRecordsFound() <= 0 ) {
                    eoLearnedMatchList.setNumberOfRecordsFound(10); 
                }
                else {
                    eoLearnedMatchList.setNumberOfRecordsFound(eoLearnedMatchList.getNumberOfRecordsFound());
                }
            }
            else {
                if(eoLearnedMatchList.getNumberOfRecordsFound() <= 0 && eoLearnedMatchList.getCurrentPageNumber() > 1) {
                    eoLearnedMatchList.setCurrentPageNumber(eoLearnedMatchList.getCurrentPageNumber() - 1);
                    eoLearnedMatchList.setNumberOfRecordsFound(10);
                }
                else {
                    eoLearnedMatchList.setNumberOfRecordsFound(eoLearnedMatchList.getNumberOfRecordsFound());
                }
            }


            int fromIndex = eoLearnedMatchList.getFromIndex();
            int toIndex = eoLearnedMatchList.getToIndexWithoutCount()-1; //exemptionList.getToIndex()-1;
            log.debug("From Index: " + fromIndex);
            log.debug("To Index: " + toIndex);
            log.debug("Search Query \n"+ qry.toString());


            int paramCount1 = params.size();
            String paramVal = null;
            ArrayList<Object> parameter = new ArrayList<>();
            for (int i = 0; i < paramCount1; i++){
                paramVal = (String) params.get(i);
                parameter.add(paramVal);
            }
            parameter.add(fromIndex);
            parameter.add(toIndex);

            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(qry.toString(), parameter.toArray());

            int totalRecordsFound = 0;

            while(rs.next()) {
                eoLearnedMatch = new EOLearnedMatch();
                if("SUPPLIERID".equals(eoLearnedMatchList.getFilterMatchTypeCode())) {
                    eoLearnedMatch.setSupplierCode(rs.getString("SUPPCODE"));
                }
                eoLearnedMatch.setMatchType(rs.getString("MATCHTYPE"));
                eoLearnedMatch.setWorkId(rs.getString("WRKID"));
                eoLearnedMatch.setLearnedMatchId(rs.getString("ID"));
                eoLearnedMatch.setMultWorkId(rs.getString("MULTWRKID"));

                totalRecordsFound = rs.getInt("TOTAL_ROWS");

                col.add(eoLearnedMatch);

            }
            eoLearnedMatchList.setNumberOfRecordsFound(totalRecordsFound);

            eoLearnedMatchList.setSearchResults(col);

        }
        catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);

        }

        log.debug("Exiting getEOLearnedMatchList in UsageDAO");
        return eoLearnedMatchList;

    }

    public List<ApmWork> validateApmWorks(List<ApmWork> col) throws PrepSystemException {

        log.debug("Entering validateApmWorks method in UsageDAO ");         

        if(col == null || col.size() < 1) {
            log.debug("Nothing to validate.. Returning...");
        }

        List<ApmWork> outCol = new ArrayList<ApmWork>();

        try {

            for(ApmWork work : col) {
                SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(UsageQueries.VALIDATE_APM_WORKS , work.getWorkId());
                if(rs.next()) {
                    work.setValidFlag("Y");
                }
                outCol.add(work);
            }

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting validateApmWorks method in UsageDAOImpl ");          
        return outCol;
    }

    public EOLearnedMatchList updateEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchList)  throws PrepSystemException {
        log.debug("Entering updateEOLearnedMatchList in UsageDAOImpl");

        if(eoLearnedMatchList == null || eoLearnedMatchList.getSearchResults() == null || eoLearnedMatchList.getSearchResults().size() < 1) {
            log.debug("Invalid Input .. Returning");
            return eoLearnedMatchList;
        }
        if(ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getEoLearnedMatchType())) {
            log.debug("Invalid Learned Match type .. Returning");
            return eoLearnedMatchList;
        }

        log.debug("eolearnedmatch type " + eoLearnedMatchList.getEoLearnedMatchType() );
        String tableName = null;
        if("ISRC".equals(eoLearnedMatchList.getEoLearnedMatchType())) {
            tableName = "IDMatching.dbo.LearnedMatchISRC";
        }
        else if("ISWC".equals(eoLearnedMatchList.getEoLearnedMatchType())) {
            tableName = "IDMatching.dbo.LearnedMatchISWC";
        }
        else if("URL".equals(eoLearnedMatchList.getEoLearnedMatchType())) {
            tableName = "IDMatching.dbo.LearnedMatchUrl";
        }
        else {
            tableName = "IDMatching.dbo.LearnedMatchSuppId";            
        }

        List<Object> col = eoLearnedMatchList.getSearchResults();
        Iterator<Object> itr = col.iterator();

        try {

            EOLearnedMatch eoLearnedMatch = null;

            String qry = "UPDATE "+tableName+" SET WRKID =      ?, UPDID = ?, UPDDT = GetDate() WHERE ID = ?";

            List<Object[]> parametersList = new ArrayList<Object[]>();

            while(itr.hasNext()) {
                eoLearnedMatch = (EOLearnedMatch) itr.next();

                Object[] params = new Object[3];
                params[0] = eoLearnedMatch.getWorkId();
                params[1] = eoLearnedMatchList.getUserId();
                params[2] = eoLearnedMatch.getLearnedMatchId();
                parametersList.add(params);
            }
            eoDSJdbcTemplate.batchUpdate(qry, parametersList);
        }
        catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        } 
        log.debug("Exiting updateEOLearnedMatchList in UsageDAO");
        return eoLearnedMatchList;
    }


    public EOLearnedMatch getEOLearnedMatchMedleyWorkInformation(EOLearnedMatchList eoLearnedMatchList) throws PrepSystemException {

        log.debug("Entering getEOLearnedMatchMedleyWorkInformation in UsageDAOImpl: "  );

        EOLearnedMatch eoLearnedMatch = new EOLearnedMatch();
        if(eoLearnedMatchList == null  ) {
            log.debug("Input is null.......  Returning....");
            return eoLearnedMatch;
        }

        SqlRowSet rs = null;
        ArrayList<String> wrkList = new ArrayList<>();
        ArrayList<String> cloneCountList = new ArrayList<>();
        String [] workIdArray = null;
        String [] cloneCountArray = null;
        String url = null;
        String multWorkId = null;
        String supplierCode = null;
        try {
            log.debug("getMultWorkMultWorkId :: " +eoLearnedMatchList.getMultWorkMultWorkId());
            log.debug("getEoLearnedMatchType :: " +eoLearnedMatchList.getEoLearnedMatchType());
            log.debug("getFilterMatchTypeCode :: " +eoLearnedMatchList.getFilterMatchTypeCode());

            if(!ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getMultWorkMultWorkId())) {

                String qry = " SELECT ID, URL, WrkId, MultWrkId, null SupplierCode, null cloneCnt FROM IDMatching.dbo.LearnedMatchUrl where MultWrkId = ? and DelFlag = 'N' ";

                if("SUPPLIERID".equals(eoLearnedMatchList.getEoLearnedMatchType())) {
                    qry = " SELECT ID, suppsongid as url, WrkId, Mult_Wrk_Id as MultWrkId, SuppCode as SupplierCode, coalesce(clone_cnt,1) as cloneCnt FROM IDMatching.dbo.LearnedMatchSuppId where Mult_Wrk_Id = ? and DelFlag = 'N' ";
                }
                log.debug("query:: " + qry);
                rs = eoDSJdbcTemplate.queryForRowSet(qry, eoLearnedMatchList.getMultWorkMultWorkId());

                while(rs.next()) {
                    wrkList.add(rs.getString("WrkId"));
                    cloneCountList.add(rs.getString("cloneCnt"));
                    url = rs.getString("URL");
                    multWorkId = rs.getString("MultWrkId");
                    supplierCode = rs.getString("SupplierCode");
                }

                workIdArray = wrkList.toArray(new String[wrkList.size()]);
                cloneCountArray = cloneCountList.toArray(new String[cloneCountList.size()]);
            }
        }
        catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }

        eoLearnedMatch.setWorkIds(workIdArray);
        eoLearnedMatch.setWorkIdCollection(wrkList);

        eoLearnedMatch.setMedleyCloneCounts(cloneCountArray);
        eoLearnedMatch.setCloneCountsCollection(cloneCountList);

        eoLearnedMatch.setMatchIdValue(url);
        eoLearnedMatch.setMultWorkId(multWorkId);
        eoLearnedMatch.setSupplierCode(supplierCode);

        log.debug("Exiting getEOLearnedMatchMedleyWorkInformation in UsageDAO " + eoLearnedMatch);
        return eoLearnedMatch;
    }

    public EOLearnedMatch getEOLearnedMatch(EOLearnedMatch eoLearnedMatch)  throws PrepSystemException {
        log.debug("Entering getEOLearnedMatch of UsageDAO EOLearnedMatch :" + eoLearnedMatch);

        if(eoLearnedMatch == null || ValidationUtils.isEmptyOrNull(eoLearnedMatch.getMatchType())) {
            log.debug("Input object is null or invalid match type....Returning");
        }

        log.debug("eoLearnedMatch.getMatchType() " + eoLearnedMatch.getMatchType());

        String qry = null;
        EOLearnedMatch outEOLearnedMatch = null;

        if("ISWC".equals(eoLearnedMatch.getMatchType())) {
            qry = UsageQueries.GET_EO_LEARNED_MATCH_ISWC;
        }
        else if("ISRC".equals(eoLearnedMatch.getMatchType())) {
            qry = UsageQueries.GET_EO_LEARNED_MATCH_ISRC;
        }
        else if("URL".equals(eoLearnedMatch.getMatchType())) {
            qry = UsageQueries.GET_EO_LEARNED_MATCH_URL;
        }
        else {
            qry = UsageQueries.GET_EO_LEARNED_MATCH_SUPPLIER_ID;
        }

        try {

            ArrayList<Object> params = new ArrayList<>();
            if("URL".equals(eoLearnedMatch.getMatchType())) {
                params.add(eoLearnedMatch.getMatchIdValue().substring(eoLearnedMatch.getMatchIdValue().indexOf("=")+1).trim());
            }
            else {
                params.add(eoLearnedMatch.getMatchIdValue());
            }

            if(!"ISRC".equals(eoLearnedMatch.getMatchType()) && !"ISWC".equals(eoLearnedMatch.getMatchType()) && !"URL".equals(eoLearnedMatch.getMatchType()) ) {
                params.add(eoLearnedMatch.getSupplierCode());
            }

            SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(qry, params.toArray());

            if(rs.next()) {
                outEOLearnedMatch = new EOLearnedMatch();
                outEOLearnedMatch.setWorkId(rs.getString("wrkid"));
            }
        }
        catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);

        } 
        log.debug("Exiting getEOLearnedMatch of UsageDAO EOLearnedMatch :" + eoLearnedMatch);
        return outEOLearnedMatch;
    }

    public String validateIndustryIds(String idType, String idValue) throws PrepSystemException {
        log.debug("Entering validateIndustryIds method in UsageDAO.. Validating " + idType + " " + idValue);
        String result = null;
        SqlRowSet rs = null;    
        try {
            rs = eoDSJdbcTemplate.queryForRowSet("select FileInventory.dbo.ValidateIndustryIds(?,?)" , idType, idValue);
            if(rs.next()) {
                result = rs.getString(1);
            }

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting validateIndustryIds method in UsageDAO " + result);          

        return result;
    }

    public EOLearnedMatch addEOLearnedMatch(EOLearnedMatch eoLearnedMatch)
        throws PrepSystemException {

        log.debug("Entering addEOLearnedMatch of UsageDAO EOLearnedMatch :" + eoLearnedMatch);
        StringBuilder sqlStr = null;

        StringBuilder columnsPart;
        StringBuilder valuesPart;
        String multWorkId = null;

        try {

            if("URL".equals(eoLearnedMatch.getMatchType())) {
                if(eoLearnedMatch.getWorkIds().length > 1) {

                    SqlRowSet rs = eoDSJdbcTemplate.queryForRowSet(" SELECT ( MAX( COALESCE(multwrkid, 0)) + 1 ) AS SEQ FROM IDMatching.dbo.LearnedMatchUrl ");

                    if(rs.next()) {
                        multWorkId = rs.getString("SEQ");
                    }

                }
            }
            else if("SUPPLIERID".equals(eoLearnedMatch.getMatchType())) {
                multWorkId = eoLearnedMatch.getMultWorkId();
            }

            String[] cloneCounts = eoLearnedMatch.getMedleyCloneCounts();
            int i = 0;
            for (String workId : eoLearnedMatch.getWorkIds()) {

                String sqlTableName = "";
                //String seqId = "";
                ArrayList<String> valuesList;
                if (!UsageCommonValidations.isEmptyOrNull(eoLearnedMatch.getMatchType())) {
                    if("ISRC".equals(eoLearnedMatch.getMatchType())) {
                        sqlTableName = " IDMatching.dbo.LearnedMatchISRC ";
                    }
                    else if("ISWC".equals(eoLearnedMatch.getMatchType())) {
                        sqlTableName = " IDMatching.dbo.LearnedMatchISWC ";
                    }
                    else if("URL".equals(eoLearnedMatch.getMatchType())) {
                        sqlTableName = " IDMatching.dbo.LearnedMatchUrl ";
                    }
                    else if("SUPPLIERID".equals(eoLearnedMatch.getMatchType())) {
                        sqlTableName = " IDMatching.dbo.LearnedMatchSuppId ";
                    } 
                    sqlStr = new StringBuilder("INSERT INTO " + sqlTableName);
                }

                columnsPart = new StringBuilder(" (");
                valuesPart = new StringBuilder(" VALUES("); 

                valuesList = new ArrayList<>();

                columnsPart.append(" DelFlag");
                valuesPart.append(" ?");
                valuesList.add("N");

                if (!UsageCommonValidations.isEmptyOrNull(eoLearnedMatch.getSupplierCode())) {
                    columnsPart.append(", SUPPCODE");
                    valuesPart.append(", ?");
                    valuesList.add(eoLearnedMatch.getSupplierCode());
                }
                if (!UsageCommonValidations.isEmptyOrNull(workId)) {
                    columnsPart.append(", WRKID");
                    valuesPart.append(", ?");
                    valuesList.add(workId);
                }

                if (!UsageCommonValidations.isEmptyOrNull(multWorkId)) {
                    if("URL".equals(eoLearnedMatch.getMatchType())) {
                        columnsPart.append(", MULTWRKID ");
                    }
                    else if("SUPPLIERID".equals(eoLearnedMatch.getMatchType())) {
                        columnsPart.append(", MULT_WRK_ID ");
                    }

                    valuesPart.append(", ?");
                    valuesList.add(multWorkId);

                    if("SUPPLIERID".equals(eoLearnedMatch.getMatchType())) {
                        columnsPart.append(", CLONE_CNT");
                        valuesPart.append(", ?");
                        valuesList.add(cloneCounts[i]);
                    }
                }

                if (!UsageCommonValidations.isEmptyOrNull(eoLearnedMatch.getMatchType())) {             
                    columnsPart.append(", MATCHTYPE ");
                    valuesPart.append(", ?");
                    if("SUPPLIERID".equals(eoLearnedMatch.getMatchType())) {
                        valuesList.add("SuppId");
                    }
                    else {
                        valuesList.add(eoLearnedMatch.getMatchType());
                    }
                }

                if (!UsageCommonValidations.isEmptyOrNull(eoLearnedMatch.getMatchIdValue())) {
                    if (!UsageCommonValidations.isEmptyOrNull(eoLearnedMatch.getMatchType())) {
                        if("ISRC".equals(eoLearnedMatch.getMatchType())) {
                            columnsPart.append(", ISRC "); 
                            valuesPart.append(", FileInventory.dbo.ValidateIndustryIds('ISRC',?)");
                            valuesList.add(eoLearnedMatch.getMatchIdValue());
                        }
                        else if("ISWC".equals(eoLearnedMatch.getMatchType())) {
                            columnsPart.append(", ISWC ");
                            valuesPart.append(", FileInventory.dbo.ValidateIndustryIds('ISWC',?)");
                            valuesList.add(eoLearnedMatch.getMatchIdValue());
                        }
                        else if("URL".equals(eoLearnedMatch.getMatchType())) {
                            columnsPart.append(", URL ");
                            valuesPart.append(", ?");
                            valuesList.add(eoLearnedMatch.getMatchIdValue().substring(eoLearnedMatch.getMatchIdValue().indexOf("=")+1).trim());
                        } 
                        else if("SUPPLIERID".equals(eoLearnedMatch.getMatchType())) {
                            columnsPart.append(", SUPPSONGID ");
                            valuesPart.append(", ?");
                            valuesList.add(eoLearnedMatch.getMatchIdValue());
                        } 
                    }
                }
                if (eoLearnedMatch.getUserId() != null) {
                    columnsPart.append(", CREID");
                    valuesPart.append(", ?");
                    valuesList.add(eoLearnedMatch.getUserId());
                }
                if (eoLearnedMatch.getUserId() != null) {
                    columnsPart.append(", CREDT");
                    valuesPart.append(", GetDate()");
                }

                columnsPart.append(") ");
                valuesPart.append(") ");

                sqlStr.append(columnsPart).append(valuesPart);

                log.debug("Generated Dynamic SQL Query :" + sqlStr.toString());


                int totalParameters;
                totalParameters = valuesList.size();

                List<Object> params = new ArrayList<Object>();

                for (int parameterId = 0; parameterId < totalParameters; parameterId++) {
                    params.add(valuesList.get(parameterId));
                }
                eoDSJdbcTemplate.update(sqlStr.toString(), params.toArray());
                i++;
            }

        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);

        }
        log.debug("Exiting addApmLearnedMatch of UsageDAO");
        return eoLearnedMatch;
    }


    public EOLearnedMatch updateEOLearnedMatchMultiple(EOLearnedMatch eoLearnedMatch)  throws PrepSystemException {

        log.debug("Entering updateEOLearnedMatchMultiple method in UsageDAO apmLearnedMatch " + eoLearnedMatch);

        if(eoLearnedMatch == null || eoLearnedMatch.getWorkIdCollection() == null || eoLearnedMatch.getWorkIdCollection().size() <= 0) {            
            log.debug("Input is is null or work ids are null");
            return eoLearnedMatch;
        }
        List<String> workIdList = eoLearnedMatch.getWorkIdCollection();
        List<String> cloneCountsCollection = eoLearnedMatch.getCloneCountsCollection();
        String multWorkId = eoLearnedMatch.getMultWorkId();
        StringBuilder updateSoftDeleteAll= null; 
        StringBuilder updateQry = null; 
        StringBuilder insertQry = null;

        try {

            ArrayList<Object> params = new ArrayList<>();

            if("URL".equals(eoLearnedMatch.getMatchType())) {

                updateSoftDeleteAll = new StringBuilder("UPDATE IDMatching.dbo.LearnedMatchUrl SET DelFlag = 'Y', UpdId = ?, UpdDt = GETDATE(), multWrkId = NULL WHERE URL = ?  AND DelFlag = 'N' ");
                params.add(eoLearnedMatch.getUserId()); 
                params.add(eoLearnedMatch.getMatchIdValue().substring(eoLearnedMatch.getMatchIdValue().indexOf("=")+1).trim());
            }
            else if("SUPPLIERID".equals(eoLearnedMatch.getMatchType())) {

                updateSoftDeleteAll = new StringBuilder("UPDATE IDMatching.dbo.LearnedMatchSuppId SET DelFlag = 'Y', UpdId = ?, UpdDt = GETDATE(), mult_Wrk_Id = NULL, clone_cnt = null WHERE suppSongId = ?  AND DelFlag = 'N' AND SuppCode = ?");
                params.add(eoLearnedMatch.getUserId()); 
                params.add(eoLearnedMatch.getMatchIdValue());
                params.add(eoLearnedMatch.getSupplierCode());
            }

            int updCount  = eoDSJdbcTemplate.update(updateSoftDeleteAll.toString(), params.toArray());
            log.debug("Set DELFL Y to "+ updCount);

            if(workIdList.size() > 1 && ValidationUtils.isEmptyOrNull(multWorkId)) {
                log.debug("Getting sequeence   SELECT ( MAX( COALESCE(multwrkid, 0)) + 1 ) AS SEQ FROM IDMatching.dbo.LearnedMatchUrl ");
                multWorkId = this.getSequence(" SELECT ( MAX( COALESCE(multwrkid, 0)) + 1 ) AS SEQ FROM IDMatching.dbo.LearnedMatchUrl " , eoDSJdbcTemplate);
            }
            else if(workIdList.size() == 1) {
                multWorkId = null;
            }
            int i = -1;
            for(String w :  workIdList) {
                i++;
                log.debug("eoLearnedMatch " + eoLearnedMatch);
                updCount = 0;
                if(updCount <= 0) {

                    if("URL".equals(eoLearnedMatch.getMatchType())) {
                        log.debug("Inserting new row URL multi match for Work ID " + w);

                        insertQry =  new StringBuilder(" INSERT INTO IDMatching.dbo.LearnedMatchUrl (DelFlag, CreDt, URL, WrkId, MatchType, CreID,  MultWrkId) VALUES ('N', GETDATE(), ?,?,?,?,?) ");

                        params.clear();
                        params.add(eoLearnedMatch.getMatchIdValue().substring(eoLearnedMatch.getMatchIdValue().indexOf("=")+1).trim());
                        params.add(w);
                        params.add(eoLearnedMatch.getMatchType());
                        params.add(eoLearnedMatch.getUserId());
                        params.add(multWorkId);

                        eoDSJdbcTemplate.update(insertQry.toString(), params.toArray());
                    }
                    else if("SUPPLIERID".equals(eoLearnedMatch.getMatchType())) {
                        log.debug("Inserting new row SUPPID multi match for Work ID " + w);

                        insertQry =  new StringBuilder(" INSERT INTO IDMatching.dbo.LearnedMatchSuppId (DelFlag, CreDt, SuppSongId, WrkId, MatchType, CreID,  Mult_Wrk_Id, SuppCode, clone_cnt) VALUES ('N', GETDATE(), ?,?,?,?,?,?, ?) ");

                        params.clear();
                        params.add(eoLearnedMatch.getMatchIdValue());
                        params.add(w);
                        params.add("SuppId");
                        params.add(eoLearnedMatch.getUserId());
                        params.add(multWorkId);
                        params.add(eoLearnedMatch.getSupplierCode());
                        params.add(cloneCountsCollection.get(i));

                        eoDSJdbcTemplate.update(insertQry.toString(), params.toArray());
                    }
                }
            }
        } 
        catch(Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);
        }
        log.debug("Exiting updateEOLearnedMatchMultiple method in UsageDAO eoLearnedMatch " + eoLearnedMatch);
        return eoLearnedMatch;
    }

    public String getSequence(String sequence) throws PrepSystemException {

        log.debug("Entering public getSequence method in UsageDaoImpl " + sequence);
        String outSequenceNo = null;
        String query = null;

        if("MULT_WRK_ID".equals(sequence)) {
            query = "SELECT PAPM.MULT_WRK_ID.NEXTVAL FROM DUAL";
        }

        try {
            outSequenceNo = getSequence(query, eoDSJdbcTemplate);
        } catch (Exception e) {
            log.debug(e);
            throw new PrepSystemException(ERROR_USAGE_SQL_SQLEXCEPTION, e);

        } 
        log.debug("Exiting public getSequence method in UsageDaoImpl " + outSequenceNo);
        return (outSequenceNo);
    }
}
