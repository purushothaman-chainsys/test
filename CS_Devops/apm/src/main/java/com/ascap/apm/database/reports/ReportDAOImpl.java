package com.ascap.apm.database.reports;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.DateUtils;
import com.ascap.apm.common.utils.constants.Constants;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
import com.ascap.apm.common.utils.constants.ReportQueries;
import com.ascap.apm.database.BaseDAO;
import com.ascap.apm.vob.reports.Parameter;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportParameter;
import com.ascap.apm.vob.reports.ReportParameterValue;
import com.ascap.apm.vob.reports.ReportRequest;
import com.ascap.apm.vob.reports.ScheduleInfo;
import com.ascap.apm.vob.reports.ScheduleStatus;
import com.ascap.apm.vob.reports.ViewInstance;

/**
 * @author mzbamidi To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */

@Repository("ReportDAOImpl")
public class ReportDAOImpl extends BaseDAO implements ReportDAO {

    public static final String REPORT_PARAM_VALUD_ID = "Report Param Valud Id :";

    public static final String ERROR_REPORT_SQL_SQLEXCEPTION = "error.report.sql.sqlexception";

    public static final String SQL_EXCEPTION = "SQLException : ";
    
    public static final String ERROR_REPORT_COMMON_SQL_EXCEPTION = "error.report.common.sql.exception";
    
    public static final String FNAME = "FNAME";

    public static final String LNAME = "LNAME";

    public static final String RPT_PRM_SEL_ID = "RPT_PRM_SEL_ID";

    public static final String RPT_PRM_VAL = "RPT_PRM_VAL";

    /**
     * Constructor for ReportDAOImpl.
     */
    public ReportDAOImpl() {
        super();
    }

    @Autowired
    private JdbcTemplate ascapJdbcTemplate;

    public Report retrieveInstanceDetails(Report report) throws PrepSystemException {
            log.debug("Entering ReportDAOImpl - retrieveInstanceDetails method ");

        ArrayList<Object> cmsInstanceIDs = new ArrayList<>();
        List<Object> statusList = report.getStatusList();
        ListIterator<Object> listIterator = statusList.listIterator();
        List<Object> modifiedList = new ArrayList<>();

        String reportName = report.getReportName();
        try {
            while (listIterator.hasNext()) {
                ScheduleStatus sStatus = (ScheduleStatus) listIterator.next();
                cmsInstanceIDs.add(sStatus.getInstanceID().trim());
                long instanceID = Long.parseLong(sStatus.getInstanceID());
                log.debug("**** jason report name>> " + report.getReportName());
                log.debug("**** jason instanceID>> " + instanceID);
                ArrayList<Object> params = new ArrayList<>();
                params.add(report.getReportName());
                params.add(report.getReportName());
                params.add(instanceID);
                SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_INSTANCE_DETAILS, params.toArray());
                if (rs.next()) {
                    long dbinstanceID = rs.getLong(1);
                    String dbrptname = rs.getString(2);
                    String dbuserID = rs.getString(3);
                    java.sql.Date dbDate = rs.getDate(4);
                    String reqdateStr = null;
                    try {
                        if (dbDate != null) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(dbDate);
                            reqdateStr = DateUtils.getASCAPDateDisplayFormat(cal);
                            log.debug("Request Date: " + reqdateStr);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        String[] errorStrings = new String[1];
                        errorStrings[0] = e.getMessage();
                        throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, errorStrings);
                    }
                    java.sql.Date returnDate = rs.getDate(5);
                    String retdateStr = null;
                    log.debug("returnDate: " + returnDate);
                    Calendar retCal = null;
                    if (returnDate != null) {
                        try {
                            retCal = Calendar.getInstance();
                            retCal.setTime(returnDate);
                            retdateStr = DateUtils.getASCAPDateDisplayFormat(retCal);
                            log.debug("Returned EndDate: " + retdateStr);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            String errorStrings[] = new String[1];
                            errorStrings[0] = e.getMessage();
                            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, errorStrings);
                        }
                    }
                    String dbinstanceIDStr = String.valueOf(dbinstanceID);
                    if (dbinstanceIDStr.equalsIgnoreCase(sStatus.getInstanceID())) {
                        sStatus.setRunBy(dbuserID);
                        sStatus.setReturnedEndDate(retdateStr);
                        sStatus.setReportName(dbrptname);
                    }
                    if (PrepConstants.DELETE_FLAG_YES.equalsIgnoreCase(rs.getString(6))) {
                        sStatus.setAdminExportFlag(true);
                    }
                }
                modifiedList.add(sStatus);
            }
            report.setStatusList(modifiedList);
            ArrayList<Object> dbInstanceIDs = retrieveAllInstanceIDsFromDB(reportName);
            log.debug("DB size: " + dbInstanceIDs.size() + " - IDs: " + dbInstanceIDs.toString());
            log.debug("CMS size: " + cmsInstanceIDs.size() + " - IDs: " + cmsInstanceIDs.toString());
            ArrayList<Object> tmp = compareLists(dbInstanceIDs, cmsInstanceIDs, "DB_IDS_NOT_FOUND_IN_CMS");
            Iterator<?> tmpIterator = tmp.iterator();
            while (tmpIterator.hasNext()) {
                String val = (String) tmpIterator.next();
                log.debug("Instance id: " + val.trim()
                    + " was deleted from Crystal Server; deleting instance from database.");
                deleteInstanceDetails(val);
            }
        } catch (Exception se) {
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting ReportDAOImpl - retrieveScheduleInfoDetails method " + report);
        return report;
    }

    private ArrayList<Object> retrieveAllInstanceIDsFromDB(String reportName) throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - retrieveAllInstanceIDsFromDB method ");
        ArrayList<Object> dbInstances = new ArrayList<>();
        log.debug("Report Name:: " + reportName);
        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(reportName);
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_ALL_DB_INSTANCES, params.toArray());
            while (rs.next()) {
                dbInstances.add(("" + rs.getLong(1)).trim());
            }
        } catch (Exception se) {
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting ReportDAOImpl - retrieveAllInstanceIDsFromDB method ");
        return dbInstances;

    }

    private void deleteInstanceDetails(String instanceId) throws PrepSystemException {
        try {
            log.debug("Entering deleteInstanceDetails - ReportDAOImpl");
            ArrayList<Object> params = new ArrayList<>();
            params.add(
                (PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_SPL_USER)).trim());
            params.add(Long.parseLong(instanceId));
            ascapJdbcTemplate.update(ReportQueries.REPORT_DELETE_INSTANCE_DETAILS, params.toArray());
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting deleteInstanceDetails - ReportDAOImpl");
    }

    private ArrayList<Object> compareLists(ArrayList<Object> db, ArrayList<Object> cms, String listType) {
        ArrayList<Object> dbIDsFoundInCMS = new ArrayList<>();
        ArrayList<Object> dbIDsNotFoundInCMS = new ArrayList<>();
        ArrayList<Object> cmsIDsNotFoundInDB = new ArrayList<>();
        ArrayList<Object> returnList = new ArrayList<>();
        if (!db.isEmpty()) {
            for (int i = 0; i < db.size(); i++) {
                if (cms.contains(db.get(i))) {
                    dbIDsFoundInCMS.add(db.get(i));
                } else {
                    dbIDsNotFoundInCMS.add(db.get(i));
                }
            }
        }
        if (!cms.isEmpty()) {
            for (int i = 0; i < cms.size(); i++) {
                if (!dbIDsFoundInCMS.contains(cms.get(i))) {
                    cmsIDsNotFoundInDB.add(cms.get(i));
                }
            }
        }

        if (listType.equalsIgnoreCase("DB_IDS_FOUND_IN_CMS")) {
            returnList = dbIDsFoundInCMS;
        } else if (listType.equalsIgnoreCase("DB_IDS_NOT_FOUND_IN_CMS")) {
            returnList = dbIDsNotFoundInCMS;
        } else if (listType.equalsIgnoreCase("CMS_IDS_NOT_FOUND_DB")) {
            returnList = cmsIDsNotFoundInDB;
        } else {
            return returnList;
        }
        log.debug("dbIDsFoundInCMS ==> size: " + dbIDsFoundInCMS.size() + " - " + dbIDsFoundInCMS.toString());
        log.debug("dbIDsNotFoundInCMS ==> size: " + dbIDsNotFoundInCMS.size() + " - " + dbIDsNotFoundInCMS.toString());
        log.debug("cmsIDsNotFoundInDB ==> size: " + cmsIDsNotFoundInDB.size() + " - " + cmsIDsNotFoundInDB.toString());
        return returnList;
    }

    public ScheduleStatus updateReportInstanceDetails(ScheduleStatus instanceDetails) throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - updateReportInstanceDetails method ");
        Report tmpReport = new Report();
        if (!this.isReportInstanceInDB(instanceDetails.getInstanceID())) {
            ArrayList<Object> newInstances = new ArrayList<>();
            newInstances.add(instanceDetails);
            tmpReport.setUserId(instanceDetails.getRunBy());
            tmpReport.setReportName(instanceDetails.getReportName());
            tmpReport.setRequestedDate(instanceDetails.getRequestedDate());
            tmpReport.setStatusList(newInstances);
            this.insertInstanceDetails(tmpReport);
        }
        String retEndDate = null;
        java.sql.Date sqldate = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            log.debug("Current Date: " + DateUtils.getASCAPDateDisplayFormat(cal));
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt((PrepExtPropertyHelper.getInstance()
                .getPropertyValue(PrepPropertiesConstants.CE_ACTIVATED_RPT_RTN_DAYS)).trim()));
            retEndDate = DateUtils.getASCAPDateDisplayFormat(cal);
            log.debug("Returned End Date: " + retEndDate);
            sqldate = new java.sql.Date(cal.getTime().getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException(ERROR_REPORT_COMMON_SQL_EXCEPTION, errorStrings);
        }
        instanceDetails.setReturnedEndDate(retEndDate);
        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(sqldate);
            params.add(instanceDetails.getInstanceID());
            ascapJdbcTemplate.update(ReportQueries.UPDATE_REPORT_INSTANCE_DETAILS,params.toArray());
        } catch (Exception se) {
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }

        log.debug("Exiting ReportDAOImpl - updateReportInstanceDetails method ");
        return instanceDetails;
    }

    private boolean isReportInstanceInDB(String instanceID) throws PrepSystemException {
        log.debug("Entering isReportInstanceInDB method in ReportDAOImpl ");
        boolean result = false;
        long rptInstId = 0;
        if (instanceID == null || "".equals(instanceID)) {
            log.debug("Inside   private method isReportInstanceInDB(Connection con, instanceID passed is" + instanceID);
            return false;
        }
        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(instanceID);
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_RPT_INST_ID, params.toArray());
            if (!rs.isAfterLast() && !rs.isBeforeFirst()) {
                return false;
            }
            if (rs.next()) {
                rptInstId = rs.getLong("RPT_INST_ID");
                log.debug("Report Instance Id found: " + rptInstId);
                result = true;
            }
            if (rptInstId == 0) {
                return false;
            }

        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se.getMessage());
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting isReportInstanceInDB method in ReportDAOImpl ");
        return result;
    }

    public Report insertInstanceDetails(Report report) throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - insertInstanceDetails method ");
        String requestedDate = report.getRequestedDate();
        DateUtils dateUtils = new DateUtils();
        java.sql.Date sqlRequestedDate = null;
        if (requestedDate != null) {
            java.util.Date date = dateUtils.stringToDate(requestedDate);
            sqlRequestedDate = new java.sql.Date(date.getTime());
        }
        List<Object> statuslist = report.getStatusList();
        ScheduleStatus statusobj = (ScheduleStatus) statuslist.get(0);
        String instanceID = statusobj.getInstanceID();
        String userID = report.getUserId();
        String reportName = report.getReportName();
        long partyID = report.getPartyId();
        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(Long.parseLong(instanceID));
            params.add(reportName);
            params.add(userID);
            params.add(sqlRequestedDate);
            params.add(partyID);
            params.add(userID);
            params.add("");
            ascapJdbcTemplate.update(ReportQueries.REPORT_INSERT_INSTANCE_DETAILS, params.toArray());
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting ReportDAOImpl - insertInstanceDetails method ");
        return report;
    }
    
    public ReportRequest getReportRequestDetail(ReportRequest reportRequest) throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - getReportRequestDetail method " + reportRequest);

        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(reportRequest.getRequestId());
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_REPORT_REQUEST_DETAIL, params.toArray());
            ReportParameterValue reportParamaterValueVob = null;
            ReportParameter reportRequestParamaterVob = new ReportParameter();
            ReportRequest reportRequestVob = reportRequest;
            List<Object> reportRequestParamValList = new ArrayList<>();
            List<Object> reportRequestParamList = new ArrayList<>();
            String previousReportParamId = null;

            int i = 0;
            while (rs.next()) {

                if (i == 0) {
                    reportRequestVob.setRequestId(rs.getLong("RPT_REQ_ID"));
                    reportRequestVob.setUserId(rs.getString("USR_ID"));
                    reportRequestVob.setFname(rs.getString(FNAME));
                    reportRequestVob.setLname(rs.getString(LNAME));
                    reportRequestVob.setReportId(rs.getString("RPT_ID"));
                    reportRequestVob.setStatusCode(rs.getString("STA_CDE"));
                    reportRequestVob.setStatus(rs.getString("REP_REQ_STA_NA"));
                    reportRequestVob.setReportReqDesc(rs.getString("DES"));
                    reportRequestVob.setReportReqCreDate(rs.getString("CRE_DT"));
                }

                if (previousReportParamId == null) {
                    previousReportParamId = rs.getString(RPT_PRM_SEL_ID);
                    reportRequestParamaterVob = new ReportParameter();
                    reportRequestParamaterVob.setRptPrmSelId(Long.parseLong(rs.getString(RPT_PRM_SEL_ID)));
                    reportRequestParamaterVob.setRptPrmCde(rs.getString("RPT_PRM_CDE"));
                    reportRequestParamaterVob.setRptPrmDatTypCde(rs.getString("RPT_PRM_DAT_TYP_CDE"));
                    reportRequestParamaterVob.setRptPrmDatTypName(rs.getString("REP_REQ_DATA_TYP_NA"));
                    reportRequestParamaterVob.setRptPrmName(rs.getString("REP_PRM_NA"));
                    reportParamaterValueVob = new ReportParameterValue();
                    reportParamaterValueVob.setRptPrmSelId(Long.parseLong(rs.getString(RPT_PRM_SEL_ID)));
                    reportParamaterValueVob.setRptPrmVal(rs.getString(RPT_PRM_VAL));
                    reportParamaterValueVob.setRptPrmValSelId(Long.parseLong(rs.getString(REPORT_PARAM_VALUD_ID)));
                    reportRequestParamList.add(reportRequestParamaterVob);
                    reportRequestParamValList.add(reportParamaterValueVob);

                } else if (previousReportParamId.equalsIgnoreCase(rs.getString(RPT_PRM_SEL_ID))) {
                    reportParamaterValueVob = new ReportParameterValue();
                    reportParamaterValueVob.setRptPrmSelId(Long.parseLong(rs.getString(RPT_PRM_SEL_ID)));
                    reportParamaterValueVob.setRptPrmVal(rs.getString(RPT_PRM_VAL));
                    reportParamaterValueVob.setRptPrmValSelId(Long.parseLong(rs.getString("RPT_PRM_VAL_SEL_ID")));
                    reportRequestParamValList.add(reportParamaterValueVob);

                } else if (!previousReportParamId.equalsIgnoreCase(rs.getString(RPT_PRM_SEL_ID))) {
                    previousReportParamId = rs.getString(RPT_PRM_SEL_ID);
                    reportRequestParamaterVob.setRptPrmValues(reportRequestParamValList);
                    reportRequestParamValList = new ArrayList<>();
                    reportRequestParamaterVob = new ReportParameter();
                    reportRequestParamaterVob.setRptPrmSelId(Long.parseLong(rs.getString(RPT_PRM_SEL_ID)));
                    reportRequestParamaterVob.setRptPrmCde(rs.getString("RPT_PRM_CDE"));
                    reportRequestParamaterVob.setRptPrmDatTypCde(rs.getString("RPT_PRM_DAT_TYP_CDE"));
                    reportRequestParamaterVob.setRptPrmDatTypName(rs.getString("REP_REQ_DATA_TYP_NA"));
                    reportRequestParamaterVob.setRptPrmName(rs.getString("REP_PRM_NA"));
                    reportParamaterValueVob = new ReportParameterValue();
                    reportParamaterValueVob.setRptPrmSelId(Long.parseLong(rs.getString(RPT_PRM_SEL_ID)));
                    reportParamaterValueVob.setRptPrmVal(rs.getString(RPT_PRM_VAL));
                    reportParamaterValueVob.setRptPrmValSelId(Long.parseLong(rs.getString("RPT_PRM_VAL_SEL_ID")));
                    reportRequestParamList.add(reportRequestParamaterVob);
                    reportRequestParamValList.add(reportParamaterValueVob);
                }

                i++;
            }
            reportRequestParamaterVob.setRptPrmValues(reportRequestParamValList);
            reportRequest.setReportParams(reportRequestParamList);

        } catch (Exception se) {
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting ReportDAOImpl - getReportRequestDetail method " + reportRequest);
        return reportRequest;
    }

    public ViewInstance getUserForInstance(ViewInstance viewInstaceVOB) throws PrepSystemException {
        log.debug("Entering getUserForInstance  method in ReportDAOImpl:");
        try {
            String reportName = viewInstaceVOB.getReportName();
            String instanceID = viewInstaceVOB.getReportID();
            ArrayList<Object> params = new ArrayList<>();
            params.add(reportName);
            params.add(Long.parseLong(instanceID));
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_USER_INSTANCE_DETAILS, params.toArray());
            while (rs.next()) {
                viewInstaceVOB.setUserID(rs.getString(1));
            }
        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);

        }
        log.debug("Exiting getUserForInstance  method in ReportDAOImpl:");
        return viewInstaceVOB;

    }

    public Report getReportScheduleInfoDetails(Report report) throws PrepSystemException {
        log.debug("Entering  getReportScheduleInfoDetails method in ReportDAOImpl:");
        Report outReport = null;
        outReport = this.retrieveScheduleInfoDetails(report);
        log.debug("Exiting  getReportScheduleInfoDetails method in ReportDAOImpl:");
        return outReport;
    }

    public Report retrieveScheduleInfoDetails(Report report) throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - retrieveScheduleInfoDetails method ");
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        String reportName = report.getReportName();
        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(reportName);
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_SCHEDULE_INFO_DETAILS, params.toArray());
            if (rs.next()) {
                scheduleInfo.setScheduleNow(rs.getString(3));
                scheduleInfo.setScheduleTime(rs.getString(4));
            } else {
                log.debug("Report Not found in scheduleinfo table.So setting default values");
                scheduleInfo.setScheduleNow(PrepConstants.REPORT_SCHEDULE_NOW);
                scheduleInfo.setScheduleTime(PrepConstants.REPORT_SCHEDULE_TIME);
            }
            report.setScheduleInfo(scheduleInfo);
        } catch (Exception se) {
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting ReportDAOImpl - retrieveScheduleInfoDetails method ");
        return report;
    }

    public ScheduleStatus resetReportInstanceReturnEndDate(ScheduleStatus instanceDetails) throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - resetReportInstanceReturnEndDate method ");

        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(java.sql.Types.DATE);
            params.add(instanceDetails.getInstanceID());
            ascapJdbcTemplate.update(ReportQueries.UPDATE_REPORT_INSTANCE_DETAILS, params.toArray());
        } catch (Exception se) {
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting ReportDAOImpl - resetReportInstanceReturnEndDate method ");
        return instanceDetails;

    }

    public Report getValidateReport(Report report) throws PrepSystemException {
        log.debug("Entering retrieveValidateDetails  method in ReportDAOImpl:");
        Report outReport = null;
        try {
            outReport = this.retrieveValidateDetails(report);
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, errorStrings);
        }
        log.debug("Exiting  retrieveValidateDetails method in ReportDAOImpl:");
        return outReport;
    }

    public Report retrieveValidateDetails(Report report) throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - retrieveValidateDetails method ");
        int size = report.getParameters().size();
        String storedprocName = null;
        List<Object> errors = new ArrayList<>();
        List<SqlParameter> sqlParameters = new ArrayList<>();
        String outParam = null;
        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(report.getReportName());
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_VALIDATE_INFO_DETAILS, params.toArray());
            if (rs.next()) {
                storedprocName = rs.getString(1);
            } else {
                log.debug(report.getReportName() + "doesnot exist in rpt_vldtn_inf for executing busniess validation");
                report.setBusinessErrors(null);
                return report;
            }
            if (storedprocName != null && !"".equals(storedprocName)) {
                try {
                    ArrayList<Object> params1 = new ArrayList<>();
                    params1.add(storedprocName.toUpperCase());
                    rs = ascapJdbcTemplate.queryForRowSet(ReportQueries.CHECK_STORED_PROC_IN_DATABASE,
                        params1.toArray());
                } catch (Exception sqle) {
                    log.debug("SQL excpetion occured while looking into database for Stroed procedure " + sqle);
                    throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, sqle);
                }
                if (rs.next()) {
                    StringBuilder paramArgs = new StringBuilder();
                    for (int i = 0; i < size; i++) {
                        paramArgs.append("?,");
                    }
                    paramArgs.append("?");
                    StringBuilder store = new StringBuilder();
                    store.append("call ");
                    store.append(storedprocName.trim());
                    String callablename = "{" + store.toString().trim() + "(" + paramArgs.toString().trim() + ")}";
                    outParam = getProcedureParams(storedprocName, sqlParameters);
                    Map<String, Object> resultMap = ascapJdbcTemplate.call(new CallableStatementCreator() {

                        @Override
                        public CallableStatement createCallableStatement(Connection con) throws SQLException {
                            CallableStatement cstmt = con.prepareCall(callablename);
                            for (int i = 0; i < size; i++) {
                                String parameterValue = (report.getParameters().get(i)).getInputValue();
                                String parameterType = (report.getParameters().get(i)).getParameterType();
                                if (parameterType != null) {
                                    if (parameterType.equalsIgnoreCase(PrepConstants.TYPE_STRING)) {
                                        cstmt.setString(i + 1, parameterValue);
                                    } else if (parameterType.equalsIgnoreCase(PrepConstants.TYPE_DATETIME)) {
                                        try {
                                            if (!parameterValue.equals("") && parameterValue.length() != 0) {
                                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                                java.util.Date date = sdf.parse(parameterValue);
                                                java.sql.Date sqldate = new java.sql.Date(date.getTime());
                                                cstmt.setDate(i + 1, sqldate);
                                            } else {
                                                cstmt.setDate(i + 1, null);
                                            }

                                        } catch (Exception e) {
                                            log.error(e.getMessage());
                                            String[] errorStrings = new String[1];
                                            errorStrings[0] = e.getMessage();
                                            throw new SQLException();
                                        }
                                    } else if (parameterType.equalsIgnoreCase(PrepConstants.TYPE_DATE)) {
                                        try {
                                            if (!parameterValue.equals("") && parameterValue.length() != 0) {
                                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                                java.util.Date date = sdf.parse(parameterValue);
                                                java.sql.Date sqldate = new java.sql.Date(date.getTime());
                                                cstmt.setDate(i + 1, sqldate);
                                            } else {
                                                cstmt.setDate(i + 1, null);
                                            }
                                        } catch (Exception e) {
                                            log.error(e.getMessage());
                                            String[] errorStrings = new String[1];
                                            errorStrings[0] = e.getMessage();
                                            throw new SQLException();
                                        }
                                    } else if (PrepConstants.TYPE_NUMBER.equalsIgnoreCase(parameterType)) {
                                        if (!parameterValue.equals("") && parameterValue.length() != 0) {
                                            cstmt.setDouble(i + 1, Double.parseDouble(parameterValue));
                                        } else {
                                            cstmt.setString(i + 1, null);
                                        }
                                    } else if (PrepConstants.TYPE_CURRENCY.equalsIgnoreCase(parameterType)) {
                                        if (!parameterValue.equals("") && parameterValue.length() != 0) {
                                            cstmt.setDouble(i + 1, Double.parseDouble(parameterValue));
                                        } else {
                                            cstmt.setString(i + 1, null);
                                        }
                                    }
                                }
                            }
                            cstmt.registerOutParameter(size + 1, Types.VARCHAR);
                            return cstmt;
                        }
                    }, sqlParameters);
                    if (resultMap.get(outParam) != null) {
                        String errorString = (String) resultMap.get(outParam);
                        int index = 0;
                        int indexTemp = 0;
                        String token = "";
                        while (indexTemp != -1) {
                            indexTemp = errorString.indexOf("#~#", index);
                            if (indexTemp != -1) {
                                token = errorString.substring(index, indexTemp).trim();
                                if (!token.equalsIgnoreCase("no errors") && !token.equals("")) {
                                    errors.add(token);
                                }
                                index = indexTemp + 3;

                            } else {
                                token = errorString.substring(index, errorString.length()).trim();
                                if (!token.equalsIgnoreCase("no errors") && !token.equals("")) {
                                    errors.add(token);
                                }
                            }

                        }
                    } else {
                        log.debug("SQL Exception in executing the stored procedure for busniess validation");
                        throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION);
                    }
                    report.setBusinessErrors(errors);
                } else {
                    log.debug("Stored procedure for busniess validation NOT Present in DATABASE for"
                        + report.getReportName());
                    String errorMsg = "Error!!Stored Procedure " + storedprocName
                        + " not created in database for the report " + report.getReportName();
                    errors.add(errorMsg);
                    report.setBusinessErrors(errors);
                    return report;
                }
            } else {
                log.debug("Stored procedure Name is Null or empty in rpt_vldnt_inf for" + report.getReportName());
                report.setBusinessErrors(null);
                throw new PrepSystemException(
                    "Stored procedure Name is Null or empty in Table rpt_vldnt_inf for" + report.getReportName());
            }

        } catch (Exception se) {
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);

        }
        log.debug("Exiting ReportDAOImpl - retrieveValidateDetails method");
        return report;
    }
    
    private String getProcedureParams(String procedureName, List<SqlParameter> sqlParameters) throws Exception {
        String outParam = null;
        Connection connection = null;
        ResultSet resultSet = null;
        DatabaseMetaData databaseMetaData = null;
        try {
            connection = ascapJdbcTemplate.getDataSource().getConnection();
            databaseMetaData = connection.getMetaData();
            resultSet = databaseMetaData.getProcedureColumns(connection.getCatalog(),
                "PAPM", procedureName, "%");
            while (resultSet.next()) {
                if (DatabaseMetaData.procedureColumnOut == resultSet.getInt("COLUMN_TYPE")) {
                    outParam = resultSet.getString("COLUMN_NAME");
                    sqlParameters.add(new SqlOutParameter(outParam, resultSet.getInt("DATA_TYPE")));
                } else {
                    sqlParameters.add(new SqlParameter(resultSet.getInt("DATA_TYPE")));
                }
            }
        } catch (Exception se) {
            log.debug(SQL_EXCEPTION + se);se.getMessage();
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        } finally {
            if(resultSet != null)
                resultSet.close();
            if(connection != null)
                connection.close();
        }
        return outParam;
    }
    
    public Report getReportRequestList(Report report) throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - getReportRequestList method " + report);

        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(report.getUserId());
            params.add(report.getReportName());
            SqlRowSet rs =
                ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_USER_REPORT_REQUEST_LIST_COUNT, params.toArray());
            while (rs.next()) {
                report.setNumberOfRecordsFound(rs.getInt(1));
            }
            ArrayList<Object> params1 = new ArrayList<>();
            params1.add(report.getUserId());
            rs = ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_USERS_NAME, params.toArray());
            while (rs.next()) {
                report.setFname(rs.getString(FNAME));
                report.setLname(rs.getString(LNAME));
            }
            ArrayList<Object> params2 = new ArrayList<>();
            params2.add(report.getUserId());
            params2.add(report.getReportName());
            params2.add(report.getFromIndex());
            params2.add(report.getToIndex() - 1);

            rs = ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_USER_REPORT_REQUEST_LIST, params2.toArray());
            ArrayList<Object> reportRequestList = new ArrayList<>();

            ReportRequest reportRequestVob = new ReportRequest();
            while (rs.next()) {
                reportRequestVob.setRequestId(rs.getLong("RPT_REQ_ID"));
                reportRequestVob.setUserId(rs.getString("USR_ID"));
                reportRequestVob.setReportId(rs.getString("RPT_ID"));
                reportRequestVob.setStatus(rs.getString("STA_NA"));
                reportRequestVob.setStatusCode(rs.getString("STA_CDE"));
                reportRequestVob.setReportReqDesc(rs.getString("DES"));
                reportRequestVob.setReportReqCreDate(rs.getString("CRE_DT"));
                reportRequestVob.setFname(rs.getString(FNAME));
                reportRequestVob.setLname(rs.getString(LNAME));
                report.setFname(rs.getString(FNAME));
                report.setLname(rs.getString(LNAME));
                reportRequestList.add(reportRequestVob);
                reportRequestVob = new ReportRequest();
            }
            report.setSearchResults(reportRequestList);
        } catch (Exception se) {
            log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }
        log.debug("Exiting ReportDAOImpl - getReportRequestList method " + report);
        return report;
    }
    
    public void updateReportRequestStatus(Report report) throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - updateReportRequestStatus method " + report);
        List<Object> params = new ArrayList<>();
        try {
            params.add(Constants.RPT_STA_PEND);
            params.add(report.getUserId());
            List<Parameter> paramList = report.getParameters();
            Iterator<Parameter> itr = paramList.iterator();
            String repReqId = null;
            Parameter repParam = null;
            while (itr.hasNext()) {
                repParam = itr.next();
            }
            if (repParam != null) {
                repReqId = repParam.getInputValue();
            }
            if (repReqId == null || "".equalsIgnoreCase(repReqId)) {
                return;
            } else {
                params.add(Long.parseLong(repReqId));
            }
            ascapJdbcTemplate.update(ReportQueries.UPDATE_REPORT_REQUEST_STATUS, params.toArray());
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException(ERROR_REPORT_COMMON_SQL_EXCEPTION, errorStrings);
        }
        log.debug("Exiting ReportDAOImpl - updateReportRequestStatus method " + report);
    }

    public String retrieveReportDatabaseType(String reportName) throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - retrieveReportDatabaseType method ");
        String datasourceType = null;
        List<Object> params = new ArrayList<>();
        try {
            params.add(reportName);
            SqlRowSet rs =
                ascapJdbcTemplate.queryForRowSet(ReportQueries.GET_REPORT_DATABASE_TYPE, params.toArray());
            if (rs.next()) {
                datasourceType = rs.getString(1);
            } else {
                log.debug(reportName + " does not exist in RPT_SCH_INF");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException(ERROR_REPORT_COMMON_SQL_EXCEPTION, errorStrings);
        }
        log.debug("Exiting ReportDAOImpl - retrieveReportDatabaseType method ");
        return datasourceType;

    }
	
	
    public ViewInstance deleteInstanceDetails(ViewInstance viewInstaceVOB) throws PrepSystemException {

        try {
                log.debug("Entering deleteInstanceDetails - ReportDAOImpl");
            String instanceID = viewInstaceVOB.getReportID();
            String userid = viewInstaceVOB.getUserID();
            ArrayList<Object> params=new ArrayList<>();
            params.add(userid);
            params.add(Long.parseLong(instanceID));
            
            ascapJdbcTemplate.update(ReportQueries.REPORT_DELETE_INSTANCE_DETAILS,params.toArray());
        } catch (Exception se) {
            log.error(se.getMessage());
                log.debug(SQL_EXCEPTION + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        }
            log.debug("Exiting deleteInstanceDetails - ReportDAOImpl");
            return viewInstaceVOB;
    }
    
}
