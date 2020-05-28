package com.ascap.apm.database.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.constants.AdminReportQueries;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.database.BaseDAO;
import com.ascap.apm.vob.reports.AdminReport;

/**
 * @author vzayyadevara To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
@Repository("AdminReportDAOImpl")
public class AdminReportDAOImpl extends BaseDAO implements AdminReportDAO {

    /**
     * Constructor for AdminReportDAOImpl.
     */
    public AdminReportDAOImpl() {
        super();
    }

    public static final String ADMINUPDATEINPUTVALUES_METHOD_REPORT = "adminUpdateInputValues method-Report ";

    public static final String ERROR_REPORT_COMMON_NULLPOINTEREXCEPTION = "error.report.common.NullpointerException";

    public static final String ERROR_REPORT_COMMON_NUMBERFORMATEXCEPTION = "error.report.common.NumberFormatException";

    public static final String ERROR_REPORT_SQL_SQLEXCEPTION = "error.report.sql.sqlexception";

    public static final String ERROR_REPORT_COMMON_INDEXOUTOFBOUNDEXCEPTION =
        "error.report.common.IndexOutOfBoundsException";

    @Autowired
    private JdbcTemplate ascapJdbcTemplate;

    /**
     * @see com.ascap.apm.common.AdminReportDAOImpl.reports.AdminReportDAOInterface#adminUpdateAllTables(List, String)
     */
    public List<Object> adminUpdateAllTables(List<Object> ceList, String userID) throws PrepSystemException {
        log.debug("Entering AdminReportDAOImpl - updateReportAdminTables method ");
        List<Object> ceNewReports = new ArrayList<>();
        try {
            if ((ceList != null) && (!ceList.isEmpty())) {
                ceNewReports = adminDeleteFromScheduleInfoTable(ceList, userID);
                adminDeleteFromValidationTable(ceList, userID);
            }
        } catch (NullPointerException ne) {
            log.debug("NullpointerException : " + ne);
            throw new PrepSystemException(ERROR_REPORT_COMMON_NULLPOINTEREXCEPTION, ne);
        } catch (NumberFormatException nf) {
            log.debug("NumberFormatException : " + nf);
            throw new PrepSystemException(ERROR_REPORT_COMMON_NUMBERFORMATEXCEPTION, nf);
        }
        log.debug("Entering AdminReportDAOImpl - updateReportAdminTables method ");
        return ceNewReports;
    }

    /**
     * Method adminDeleteFromValidationTable.
     * 
     * @param adminReportSearchVOB
     */
    private void adminDeleteFromValidationTable(List<Object> ceReports, String userID) throws PrepSystemException {
        log.debug("Entering AdminReportDAOImpl - adminDeleteFromValidationTable method ");
        List<Object> validationTableList = new ArrayList<>();
        List<Object> filteredList = null;
        try {
            log.debug("adminDeleteFromValidationTable method-Datasource Connection success  ");
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(AdminReportQueries.ADMIN_REPORT_SELECT_All_VALIDATE_INFO);
            while (rs.next()) {
                validationTableList.add(rs.getString(1));
            }
            log.debug("No Of reports in rpt_vldtn_inf table Before filtering: " + validationTableList.size());
            if ((ceReports != null) && (!ceReports.isEmpty())) {
                filteredList = subtract(validationTableList, ceReports);
                log.debug("No Of reports in rpt_vldtn_inf After filtering: " + filteredList.size());
                if (!filteredList.isEmpty()) {
                    for (Iterator<?> it = filteredList.iterator(); it.hasNext();) {
                        String updateReport = it.next().toString().trim();
                        ArrayList<Object> params = new ArrayList<>();
                        params.add(userID);
                        params.add(updateReport);
                        int rowcount = ascapJdbcTemplate.update(AdminReportQueries.ADMIN_REPORT_UPDATE_VALIDATE_INFO,
                            params.toArray());
                        if (rowcount != 0) {
                            log.debug(updateReport
                                + " SUCCESSFULLY updated in rpt_vldtn_inf table with DEL_FL='Y' and rowcount:"
                                + rowcount);
                            log.debug(
                                updateReport + " NOT successfully updated in rpt_vldtn_inf table with DEL_FL='Y'");
                        }
                    }
                }
            }

        } catch (DataAccessException se) {
            log.debug("SQLException in adminDeleteFromValidationTable method : " + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);
        } catch (NullPointerException ne) {
            log.debug("NullpointerException in adminDeleteFromValidationTable method: " + ne);
            throw new PrepSystemException(ERROR_REPORT_COMMON_NULLPOINTEREXCEPTION, ne);
        } catch (NumberFormatException nf) {
            log.debug("NumberFormatException in adminDeleteFromValidationTable method: " + nf);
            throw new PrepSystemException(ERROR_REPORT_COMMON_NUMBERFORMATEXCEPTION, nf);
        } catch (IndexOutOfBoundsException ibe) {
            log.debug("IndexOutOfBoundsException in  adminDeleteFromValidationTablemethod:: " + ibe);
            throw new PrepSystemException(ERROR_REPORT_COMMON_INDEXOUTOFBOUNDEXCEPTION, ibe);
        }
    }

    private List<Object> subtract(List<Object> fromlist, List<Object> givenlist) throws PrepSystemException {
        try {
            if ((fromlist != null) && (!fromlist.isEmpty()) && (givenlist != null) && (!givenlist.isEmpty())) {
                for (Iterator<?> it1 = givenlist.iterator(); it1.hasNext();) {
                    fromlist.remove(it1.next());
                }
            }
        } catch (IndexOutOfBoundsException ibe) {
            log.debug("IndexOutOfBoundsException in  subtract Method:: " + ibe);
            throw new PrepSystemException(ERROR_REPORT_COMMON_INDEXOUTOFBOUNDEXCEPTION, ibe);

        }
        return fromlist;
    }

    /**
     * Method adminDeleteFromScheduleInfoTable.
     * 
     * @param ceList
     * @param userID
     * @throws PrepSystemException
     */
    public List<Object> adminDeleteFromScheduleInfoTable(List<Object> ceList, String userID)
        throws PrepSystemException {
        log.debug("Entering ReportDAOImpl - adminDeleteFromScheduleInfoTable method ");
        List<Object> ceNewReports = null;
        List<Object> scheduleTableList = new ArrayList<>();
        List<Object> filteredList = null;
        try {
            log.debug("adminDeleteFromValidationTable method-Datasource Connection success  ");
            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(AdminReportQueries.ADMIN_REPORT_SELECT_SCHEDULE_INFO);
            while (rs.next()) {
                scheduleTableList.add(rs.getString(1));
            }
            log.debug("No Of reports in rpt_sch_inf table Before filtering: " + scheduleTableList.size());
            if ((ceList != null) && (!ceList.isEmpty())) {
                filteredList = subtract(scheduleTableList, ceList);
                log.debug("No Of reports in rpt_sch_inf table After filtering: " + filteredList.size());
                if ((!filteredList.isEmpty())) {
                    for (Iterator<?> it = filteredList.iterator(); it.hasNext();) {
                        String updateReport = it.next().toString().trim();
                        ArrayList<Object> params = new ArrayList<>();
                        params.add(userID);
                        params.add(updateReport);
                        int rowcount = ascapJdbcTemplate.update(AdminReportQueries.ADMIN_REPORT_UPDATE_SCHEDULE_INFO,
                            params.toArray());
                        if (rowcount != 0) {
                            log.debug(updateReport
                                + " SUCCESSFULLY updated in rpt_sch_inf table with DEL_FL='Y' and rowcount:"
                                + rowcount);
                            log.debug(updateReport + " NOT successfully updated in rpt_sch_inf table with DEL_FL='Y'");
                        }

                    }
                }
            }

        } catch (DataAccessException se) {
            log.debug("SQLException in adminDeleteFromScheduleInfo Table : " + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);

        } catch (NullPointerException ne) {
            log.debug("NullpointerException in adminDeleteFromScheduleInfo Table: " + ne);
            throw new PrepSystemException(ERROR_REPORT_COMMON_NULLPOINTEREXCEPTION, ne);

        } catch (NumberFormatException nf) {
            log.debug("NumberFormatException in adminDeleteFromScheduleInfo Table: " + nf);
            throw new PrepSystemException(ERROR_REPORT_COMMON_NUMBERFORMATEXCEPTION, nf);

        } catch (IndexOutOfBoundsException ibe) {
            log.debug("IndexOutOfBoundsException in  adminDeleteFromScheduleInfo:: " + ibe);
            throw new PrepSystemException(ERROR_REPORT_COMMON_INDEXOUTOFBOUNDEXCEPTION, ibe);

        }
        return ceNewReports;
    }

    /**
     * @see com.ascap.apm.common.AdminReportDAOImpl.reports.AdminReportDAO#adminGetDefaultValues(AdminReport)
     */
    public AdminReport adminGetDefaultValues(AdminReport adminReport) throws PrepSystemException {
        log.debug("Entering AdminReportDAOImpl - adminGetDefaultValues ");
        AdminReport adminValReport = adminReport;
        String getReportName = adminReport.getReportName();
        try {
            log.debug("adminGetDefaultValues method-Datasource Connection success  ");
            ArrayList<Object> params = new ArrayList<>();
            params.add(getReportName);
            SqlRowSet rs =
                ascapJdbcTemplate.queryForRowSet(AdminReportQueries.ADMIN_SELECT_SCHEDULE_INFO, params.toArray());
            if (rs.next()) {
                adminValReport.setScheduleLater(rs.getString(1));
                adminValReport.setScheduleTime(rs.getString(2));
                adminValReport.setUserInstanceCnt(rs.getInt(3));
                adminValReport.setArchiveInstanceCnt(rs.getInt(4));
                adminValReport.setInstMaxDaysCnt(rs.getInt(5));
                adminValReport.setRptMaxInstFlag(rs.getString(6));
                adminValReport.setRptInstMaxDaysFlag(rs.getString(7));
                if ("Y".equalsIgnoreCase(rs.getString(8))) {
                    adminValReport.setAdminRunnableFlag(true);
                }
                if ("Y".equalsIgnoreCase(rs.getString(9))) {
                    adminValReport.setAdminExportFlag(true);
                }
                adminValReport.setDatabaseType(rs.getString(10));
                log.debug("****adminValReport.getUserInstanceCnt***** " + adminValReport.getUserInstanceCnt());
                log.debug("****adminValReport.getArchiveInstanceCnt***** " + adminValReport.getArchiveInstanceCnt());
                log.debug("****adminValReport.getInstMaxDaysCnt***** " + adminValReport.getInstMaxDaysCnt());
                log.debug("adminGetDefaultValues method- result set retrieved from rpt_sch_inf");
            } else {
                adminValReport.setScheduleLater(PrepConstants.REPORT_FALSE);
                adminValReport.setScheduleTime("");
                log.debug("adminGetDefaultValues method-result set NOT retrieved from rpt_sch_inf");
            }
            ArrayList<Object> param1 = new ArrayList<>();
            param1.add(getReportName);
            rs = ascapJdbcTemplate.queryForRowSet(AdminReportQueries.ADMIN_SELECT_VALIDATE_INFO, param1.toArray());
            if (rs.next()) {
                adminValReport.setStoreProcedure(rs.getString(1));
                adminValReport.setDelFlag(rs.getString(2));
                log.debug("adminGetDefaultValues method- result set retrieved from rpt_vldtn_inf");
            } else {
                adminValReport.setStoreProcedure("");
                adminValReport.setDelFlag(PrepConstants.DELETE_FLAG_YES);
                log.debug("adminGetDefaultValues method-result set NOT retrieved from rpt_vldtn_inf");
            }

        } catch (DataAccessException se) {
            log.debug("SQLException in adminGetDefaultValues: " + se);
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, se);

        } catch (NullPointerException ne) {
            log.debug("NullpointerException in adminGetDefaultValues: " + ne);
            throw new PrepSystemException(ERROR_REPORT_COMMON_NULLPOINTEREXCEPTION, ne);

        } catch (NumberFormatException nf) {
            log.debug("NumberFormatException in adminGetDefaultValues: " + nf);
            throw new PrepSystemException(ERROR_REPORT_COMMON_NUMBERFORMATEXCEPTION, nf);

        }
        log.debug("Exiting AdminReportDAOImpl - adminGetDefaultValues ");
        return adminValReport;
    }

    /**
     * @see com.ascap.apm.common.AdminReportDAOImpl.reports.AdminReportDAOInterface#adminUpdateInputValues(AdminReport)
     */
    public AdminReport adminUpdateInputValues(AdminReport adminReport) throws PrepSystemException {
        log.debug("Entering AdminReportDAOImpl - adminUpdateInputValues method ");
        log.debug("ReportName: " + adminReport.getReportName());
        log.debug("runLater: " + adminReport.getScheduleLater());
        log.debug("runTime: " + adminReport.getScheduleTime());
        log.debug("UserInstanceCnt: " + adminReport.getUserInstanceCnt());
        log.debug("ArchiveInstanceCnt: " + adminReport.getArchiveInstanceCnt());
        log.debug("InstMaxDaysCnt: " + adminReport.getInstMaxDaysCnt());
        log.debug("RptMaxInstFlag: " + adminReport.getRptMaxInstFlag());
        log.debug("RptInstMaxDaysFlag: " + adminReport.getRptInstMaxDaysFlag());
        log.debug("UserID: " + adminReport.getUserID());
        log.debug("StoreProcedure: " + adminReport.getStoreProcedure().toLowerCase());
        log.debug("DelFlag: " + adminReport.getDelFlag());
        log.debug("StatusMessage: " + adminReport.getStatusMessage());
        log.debug("isAdminExportFlag: " + adminReport.isAdminExportFlag());
        log.debug("isAdminRunnableFlag: " + adminReport.isAdminRunnableFlag());
        AdminReport adminValReport = adminReport;
        String reportName = adminValReport.getReportName();
        String runLater = adminValReport.getScheduleLater();
        String runTime = adminValReport.getScheduleTime();
        String procName = adminValReport.getStoreProcedure().toLowerCase();
        String userID = adminValReport.getUserID();
        String delFlag = adminValReport.getDelFlag();
        boolean vldtnEntryExists = false;
        boolean schEntryExists = false;
        int vldtnStatus = 0;
        int schStatus = 0;
        log.debug("adminUpdateInputValues method-Datasource Connection success  ");
        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(reportName);
            SqlRowSet rs =
                ascapJdbcTemplate.queryForRowSet(AdminReportQueries.ADMIN_SELECT_VALIDATE_INFO, params.toArray());
            if (rs.next()) {
                log.debug("validation entry exists in the database");
                vldtnEntryExists = true;
            }
            if (vldtnEntryExists) {
                log.debug("adminUpdateInputValues method-" + reportName
                    + " exists in rpt_vldtn_inf. So UPDATING the record ");
                ArrayList<Object> params1 = new ArrayList<>();
                params1.add(adminReport.getUserID());
                if (delFlag.equalsIgnoreCase(PrepConstants.DELETE_FLAG_NO)) {
                    params1.add("N");
                } else {
                    params1.add("Y");
                }
                params1.add(reportName);
                vldtnStatus =
                    ascapJdbcTemplate.update(AdminReportQueries.ADMIN_UPDATE_VALIDATE_REPORT, params1.toArray());
                if (vldtnStatus != 0) {
                    log.debug(
                        ADMINUPDATEINPUTVALUES_METHOD_REPORT + reportName + " UPDATED in rpt_vldtn_inf " + vldtnStatus);
                }
            } else {
                log.debug(ADMINUPDATEINPUTVALUES_METHOD_REPORT + reportName
                    + " DOES NOT exists in rpt_vldtn_inf.So INSERTING the record ");
                if (delFlag.equalsIgnoreCase(PrepConstants.DELETE_FLAG_NO)) {
                    ArrayList<Object> params2 = new ArrayList<>();
                    params2.add(reportName);
                    params2.add(procName);
                    params2.add(userID);
                    params2.add("");
                    params.add(null);
                    vldtnStatus =
                        ascapJdbcTemplate.update(AdminReportQueries.ADMIN_INSERT_VALIDATE_INFO, params2.toArray());
                    if (vldtnStatus != 0) {
                        log.debug("adminUpdateInputValues method-Report" + reportName + " INSERTED in rpt_vldtn_inf"
                            + vldtnStatus);
                    }
                }
            }
            ArrayList<Object> params3 = new ArrayList<>();
            params3.add(reportName);
            rs = ascapJdbcTemplate.queryForRowSet(AdminReportQueries.ADMIN_SELECT_SCHEDULE_INFO, params3.toArray());
            if (rs.next()) {
                log.debug("validation entry exists in the database");
                schEntryExists = true;
            }
            log.debug("schEntryExists?:: " + schEntryExists);
            if (schEntryExists) {
                log.debug(
                    "adminUpdateInputValues method-" + reportName + " exists in rpt_sch_inf.So UPDATING the record");
                ArrayList<Object> params4 = new ArrayList<>();
                params4.add(runLater);
                params4.add(runTime);
                params4.add(userID);
                params4.add(adminValReport.getUserInstanceCnt());
                params4.add(adminValReport.getArchiveInstanceCnt());
                params4.add(adminValReport.getInstMaxDaysCnt());
                params4.add(adminValReport.getRptMaxInstFlag());
                params4.add(adminValReport.getRptInstMaxDaysFlag());

                if (adminValReport.isAdminRunnableFlag()) {
                    params4.add(PrepConstants.DELETE_FLAG_YES);
                } else {
                    params4.add(PrepConstants.DELETE_FLAG_NO);
                }

                if (adminValReport.isAdminExportFlag()) {
                    params4.add(PrepConstants.DELETE_FLAG_YES);
                } else {
                    params4.add(PrepConstants.DELETE_FLAG_NO);
                }

                if (ValidationUtils.isEmptyOrNull(adminValReport.getDatabaseType())) {
                    adminValReport.setDatabaseType(PrepConstants.DATASOURCE_ORACLE);
                }
                params4.add(adminValReport.getDatabaseType());

                params4.add(reportName);

                schStatus = ascapJdbcTemplate.update(AdminReportQueries.ADMIN_UPDATE_SCHEDUE_INFO, params4.toArray());
                if (schStatus != 0) {
                    log.debug(ADMINUPDATEINPUTVALUES_METHOD_REPORT + reportName
                        + " UPDATED in rpt_sch_inf; rows updated: " + schStatus);
                }
            } else {
                log.debug(ADMINUPDATEINPUTVALUES_METHOD_REPORT + reportName
                    + " DOES NOT exists in rpt_sch_inf.So INSERTING the record ");
                ArrayList<Object> params5 = new ArrayList<>();
                params5.add(reportName);
                params5.add(runLater);
                params5.add(runTime);
                params5.add(adminValReport.getUserInstanceCnt());
                params5.add(adminValReport.getArchiveInstanceCnt());
                params5.add(adminValReport.getInstMaxDaysCnt());
                params5.add(adminValReport.getRptMaxInstFlag());
                params5.add(adminValReport.getRptInstMaxDaysFlag());
                params5.add(userID);
                params5.add("");
                params5.add(null);
                if (adminValReport.isAdminExportFlag()) {
                    params5.add(PrepConstants.DELETE_FLAG_YES);
                } else {
                    params5.add(PrepConstants.DELETE_FLAG_NO);
                }
                if (adminValReport.isAdminRunnableFlag()) {
                    params5.add(PrepConstants.DELETE_FLAG_YES);
                } else {
                    params5.add(PrepConstants.DELETE_FLAG_NO);
                }

                if (ValidationUtils.isEmptyOrNull(adminValReport.getDatabaseType())) {
                    adminValReport.setDatabaseType(PrepConstants.DATASOURCE_ORACLE);
                }
                params5.add(adminValReport.getDatabaseType());

                schStatus = ascapJdbcTemplate.update(AdminReportQueries.ADMIN_INSERT_SCHEDULE_INFO, params.toArray());
                if (schStatus != 0) {
                    log.debug(
                        "adminUpdateInputValues method-Report" + reportName + " INSERTED in rpt_sch_inf" + schStatus);
                }
            }
            if (schStatus != 0 && vldtnStatus != 0) {
                adminValReport.setStatusMessage(reportName + " " + PrepConstants.ADMIN_STATUS_SUCCESS);
            }
        } catch (DataAccessException e) {
            log.debug("SQLException in adminUpdateInputValues: " + e);
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_REPORT_SQL_SQLEXCEPTION, e);
        } catch (NullPointerException ne) {
            log.debug("NullpointerException : " + ne);
            log.error(ne.getMessage());
            throw new PrepSystemException(ERROR_REPORT_COMMON_NULLPOINTEREXCEPTION, ne);
        } catch (NumberFormatException nf) {
            log.debug("NumberFormatException : " + nf);
            throw new PrepSystemException(ERROR_REPORT_COMMON_NUMBERFORMATEXCEPTION, nf);
        }
        log.debug("Exiting AdminReportDAOImpl - adminUpdateInputValues");
        return adminValReport;
    }

}
