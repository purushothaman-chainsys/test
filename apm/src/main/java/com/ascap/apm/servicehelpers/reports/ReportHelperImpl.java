package com.ascap.apm.servicehelpers.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.database.reports.AdminReportDAO;
import com.ascap.apm.database.reports.ReportDAO;
import com.ascap.apm.servicehelpers.BaseHelper;
import com.ascap.apm.vob.reports.AdminReport;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportListSearch;
import com.ascap.apm.vob.reports.ReportRequest;
import com.ascap.apm.vob.reports.ScheduleStatus;
import com.ascap.apm.vob.reports.ViewInstance;

/**
 * @author mzbamidi To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */

@Service("reportHelper")
public class ReportHelperImpl extends BaseHelper implements ReportHelper {

    /**
     * 
     */
    private static final long serialVersionUID = 1415743172012863044L;

    @Autowired
    private ReportDAO reportDAO;

    @Autowired
    private AdminReportDAO adminReportDAO;

    /**
     * Method retrieveInstanceDetails.
     * 
     * @param reportVOB
     */
    public Report retrieveInstanceDetails(Report reportVOB) throws PrepSystemException {
        log.debug("Entering ReportHelperImpl - retrieveInstanceDetails method");
        reportVOB = reportDAO.retrieveInstanceDetails(reportVOB);
        log.debug("Exiting ReportHelperImpl - retrieveInstanceDetails method");
        return reportVOB;
    }

    public ScheduleStatus resetReportInstanceReturnEndDate(ScheduleStatus instanceDetails) throws PrepSystemException {
        log.debug("Entering ReportHelperImpl - resetReportInstanceReturnEndDate method");

        try {
            instanceDetails = reportDAO.resetReportInstanceReturnEndDate(instanceDetails);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("Exiting ReportHelperImpl - resetReportInstanceReturnEndDate method");
        return instanceDetails;

    }

    public Report getValidateReport(Report report) throws PrepSystemException {
        log.debug("Entering ReportHelperImpl - getValidateReport method");
        Report reportOut = reportDAO.getValidateReport(report);
        log.debug(
            "Exiting ReportHelperImpl - getValidateReport method::"/* + reportOut.getScheduleInfo().getScheduleNow() */);
        return reportOut;
    }

    public Report getScheduleInfoDetails(Report report) throws PrepSystemException {
        log.debug("Entering ReportHelperImpl - getScheduleInfoDetails method");
        Report reportOut = reportDAO.getReportScheduleInfoDetails(report);
        log.debug("Exiting ReportHelperImpl - getScheduleInfoDetails method::"
            + reportOut.getScheduleInfo().getScheduleNow());
        return reportOut;
    }

    public ViewInstance getUserForInstance(ViewInstance viewInstanceVOB) throws PrepSystemException {
        log.debug("Entering ReportHelperImpl - getUserForInstance method");
        viewInstanceVOB = reportDAO.getUserForInstance(viewInstanceVOB);
        log.debug("Exiting ReportHelperImpl - getUserForInstance method");
        return viewInstanceVOB;
    }

    public ReportRequest getReportRequestDetail(ReportRequest reportRequest) {
        log.debug("Entering ReportHelperImpl - getReportRequestDetail method");
        try {
            reportRequest = reportDAO.getReportRequestDetail(reportRequest);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("Exiting ReportHelperImpl - getReportRequestDetail method");
        return reportRequest;
    }

    public ScheduleStatus updateReportInstanceDetails(ScheduleStatus instanceDetails) {
        log.debug("Entering ReportHelperImpl - updateReportInstanceDetails method");
        try {
            instanceDetails = reportDAO.updateReportInstanceDetails(instanceDetails);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("Exiting ReportHelperImpl - updateReportInstanceDetails method");
        return instanceDetails;

    }

    /**
     * @param report
     * @return
     */
    public Report getReportRequestList(Report report) throws PrepSystemException {
        log.debug("Entering ReportHelperImpl - getReportRequestList method");
        try {
            report = reportDAO.getReportRequestList(report);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("Exiting ReportHelperImpl - getReportRequestList method");
        return report;
    }

    public AdminReport adminGetDefaultValues(AdminReport adminReport) throws PrepSystemException {
        log.debug("Exiting ReportHelperImpl - adminGetDefaultValues method");
        AdminReport reportDao = adminReportDAO.adminGetDefaultValues(adminReport);
        log.debug("Exiting ReportHelperImpl - adminGetDefaultValues method");
        return reportDao;
    }

    @Override
    public ReportListSearch getReportsList(ReportListSearch reportListSearch)
        throws PrepSystemException, PrepFunctionalException {
        return null;
    }

    public Report insertInstanceDetails(Report reportVOB) throws PrepSystemException {
        log.debug("Entering ReportHelperImpl - insertInstanceDetails method");
        reportVOB = reportDAO.insertInstanceDetails(reportVOB);
        log.debug("Exiting ReportHelperImpl - insertInstanceDetails method");
        return reportVOB;

    }

    public String retrieveReportDatabaseType(String reportName) throws PrepSystemException {
        log.debug("Entering ReportHelperImpl - retrieveReportDatabaseType method");
        String reportDbType = reportDAO.retrieveReportDatabaseType(reportName);
        log.debug("Exiting ReportHelperImpl - retrieveReportDatabaseType method");
        return reportDbType;

    }

    public void updateReportRequestStatus(Report report) {
        log.debug("Entering ReportHelperImpl - updateReportRequestStatus method");
        try {
            reportDAO.updateReportRequestStatus(report);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("Exiting ReportHelperImpl - updateReportRequestStatus method");
    }

    public ViewInstance deleteInstanceDetails(ViewInstance viewInstanceVOB) throws PrepSystemException {
        log.debug("Entering ReportHelperImpl - deleteInstanceDetails method");
        viewInstanceVOB = reportDAO.deleteInstanceDetails(viewInstanceVOB);
        log.debug("Exiting ReportHelperImpl - deleteInstanceDetails method");
        return viewInstanceVOB;

    }
}
