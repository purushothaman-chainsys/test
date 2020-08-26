package com.ascap.apm.service.reports;

import java.util.Calendar;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.DateUtils;
import com.ascap.apm.service.BaseService;
import com.ascap.apm.servicehelpers.reports.ReportCommonService;
import com.ascap.apm.servicehelpers.reports.ReportHelper;
import com.ascap.apm.vob.reports.AdminReport;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportListSearch;
import com.ascap.apm.vob.reports.ScheduleInfo;
import com.ascap.apm.vob.reports.ScheduleStatus;
import com.ascap.apm.vob.reports.ViewInstance;

/**
 * ReportService.java interacts with Crystal Enterprise server to exceute the requested serives
 * 
 * @author Murali Bamidi (mzbamidi@kanbay.com) Revision History:
 *         ------------------------------------------------------------------------ Modified by Comments Date/Time
 *         ------------------------------------------------------------------------ Raja Rao Modified 01-09-2006
 *         ------------------------------------------------------------------------
 */

@Service("reportService")
public class ReportServiceImpl extends BaseService implements ReportService {

    @Autowired
    private ReportHelper reportHelper;

    @Autowired
    private ReportCommonService reportCommonService;

    private static final long serialVersionUID = 8525690557385216031L;

    /**
     * Method getReportsList.
     * 
     * @param moduleName
     * @return ArrayList
     */

    public ReportListSearch getReportList(ReportListSearch reportSearchVOB)
        throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering ReportServiceImpl - getReportList method");
        }
        reportSearchVOB = reportCommonService.getReportsList(reportSearchVOB);
        if (log.isDebugEnabled()) {
            log.debug("Exiting ReportServiceImpl - getReportList method");
        }
        return reportSearchVOB;
    }

    /**
     * Method getreportInstanceList.
     * 
     * @param reportVOB
     * @return Report
     */
    public Report getreportInstanceList(Report reportVOB) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering ReportsServiceImpl - getreportInstanceList method");
        AdminReport tmp = new AdminReport();
        tmp.setReportName(reportVOB.getReportName());
        tmp = adminGetDefaultValues(tmp);
        reportVOB.setUserInstanceCnt(tmp.getUserInstanceCnt());
        reportVOB.setArchiveInstanceCnt(tmp.getArchiveInstanceCnt());
        reportVOB.setAdminRunnableFlag(tmp.isAdminRunnableFlag());
        reportVOB = reportCommonService.getInstanceStatus(reportVOB);
        reportVOB = retrieveInstanceDetails(reportVOB);
        reportVOB = reportCommonService.retreiveUserOnlineInstances(reportVOB);
        this.returnNeccessaryInstancesToArchive(reportVOB);
        reportVOB = reportCommonService.retreiveUserOnlineInstances(reportVOB);
        if (log.isDebugEnabled()) {
            log.debug("Exiting ReportsServiceImpl - getreportInstanceList method");
        }
        return reportVOB;
    }

    public AdminReport adminGetDefaultValues(AdminReport adminReport)
        throws PrepSystemException, PrepFunctionalException {
            log.debug("Entering ReportsServiceImpl - adminGetDefaultValues method");
        AdminReport reportDao = null;
        reportDao = reportHelper.adminGetDefaultValues(adminReport);
            log.debug("Exiting ReportsServiceImpl - adminGetDefaultValues method");
        return reportDao;
    }

    /**
     * Method retrieveInstanceDetails.
     * 
     * @param reportVOB
     */
    private Report retrieveInstanceDetails(Report reportVOB) throws PrepSystemException, PrepFunctionalException {
        reportVOB = reportHelper.retrieveInstanceDetails(reportVOB);
        return reportVOB;
    }

    /**
     * Method returnNeccessaryInstancesToArchive
     * 
     * @param reportVOB
     * @param Report
     */
    private Report returnNeccessaryInstancesToArchive(Report reportVOB)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering ReportsServiceImpl - returnNeccessaryInstancesToArchive method");
        if (!reportVOB.getStatusList().isEmpty()) {
            Iterator<Object> statusListItr = reportVOB.getStatusList().iterator();
            do {
                ScheduleStatus statusObj = (ScheduleStatus) statusListItr.next();
                checkIfReturnEndDateHasArrived(statusObj);
            } while (statusListItr.hasNext());
        }
        log.debug("Exiting ReportsServiceImpl - returnNeccessaryInstancesToArchive method");
        return reportVOB;
    }

    /**
     * Method checkIfReturnEndDateHasArrived
     * 
     * @param sStatus
     * @param ScheduleStatus
     */
    private ScheduleStatus checkIfReturnEndDateHasArrived(ScheduleStatus sStatus)
        throws PrepSystemException, PrepFunctionalException {
        if (sStatus.getReturnedEndDate() != null) {
            String currentDate = "";
            Calendar curCal = null;
            try {
                curCal = Calendar.getInstance();
                currentDate = DateUtils.getASCAPDateDisplayFormat(curCal);
                log.debug("checkIfReturnEndDateHasArrived - Current Date: " + currentDate);
            } catch (Exception e) {
                log.error(e.getMessage());
                String[] errorStrings = new String[1];
                errorStrings[0] = e.getMessage();
                throw new PrepSystemException("error.report.common.sql.exception", errorStrings);
            }
            Calendar retCal = DateUtils.getCalendar(sStatus.getReturnedEndDate());
            if (curCal.after(retCal)) {
                try {
                    reportCommonService.returnReportInstanceToArchive(sStatus);
                } catch (PrepSystemException pe) {
                    log.debug("Returning instance to report archives throw exception");
                    throw pe;
                }
                sStatus.setReturnedEndDate(null);
                sStatus = resetReportInstanceReturnEndDate(sStatus);
            }
        }
        return sStatus;
    }

    /**
     * Method resetReportInstanceReturnEndDate
     * 
     * @param sStatus
     * @param ScheduleStatus
     */
    public ScheduleStatus resetReportInstanceReturnEndDate(ScheduleStatus sStatus)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering ReportServiceImpl - resetReportInstanceReturnEndDate method");
        sStatus = reportHelper.resetReportInstanceReturnEndDate(sStatus);
        log.debug("Exiting ReportServiceImpl - resetReportInstanceReturnEndDate method");
        return sStatus;
    }

    /**
     * Method getReportArchiveList.
     * 
     * @param reportVOB
     * @return Report
     */
    public Report getReportArchiveList(Report reportVOB) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering ReportServiceImpl - getReportArchiveList method");
        // populate the reportVOB with userInstanceCnt and ArchiveInstanceCnt
        AdminReport tmp = new AdminReport();
        tmp.setReportName(reportVOB.getReportName());
        tmp = adminGetDefaultValues(tmp);
        reportVOB.setUserInstanceCnt(tmp.getUserInstanceCnt());
        reportVOB.setArchiveInstanceCnt(tmp.getArchiveInstanceCnt());
        // get list of instanceIDs from CMS
         reportVOB=reportCommonService.getInstanceStatus(reportVOB);
        // get all info about each instance, (e.g. 'returned End Date')
        reportVOB = retrieveInstanceDetails(reportVOB);
        // only will keep 'archive instances' in list
        reportVOB = reportCommonService.retreiveUserArchiveInstances(reportVOB);
        log.debug("Exiting ReportServiceImpl - getReportArchiveList method");
        return reportVOB;
    }
    
    /**
     * Method returnReportInstance.
     * 
     * @param instanceVOB
     * @return ScheduleStatus
     */
    public ScheduleStatus returnReportInstance(ScheduleStatus instanceVOB)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering ReportServiceImpl - returnReportInstance method");
         instanceVOB = reportCommonService.retrieveInstanceDetails(instanceVOB);
        instanceVOB = reportHelper.updateReportInstanceDetails(instanceVOB);
        log.debug("Exiting ReportServiceImpl - returnReportInstance method");
        return instanceVOB;
    }
    
    @Override
    public Report validateReport(Report reportVOB) throws PrepFunctionalException, PrepSystemException {
        Report outReport = null;
        log.debug("Entering ReportHelper Class - getValidate method");
        log.debug("report id passed::" + reportVOB.getReportID());
        log.debug("report name sending bach ::" + reportVOB.getReportName());
        outReport = reportHelper.getValidateReport(reportVOB);
        log.debug("Exiting ReportServiceImpl - validationReport method");
        return outReport;
    }

    @Override
    public Report getReportRequestList(Report report) throws PrepFunctionalException, PrepSystemException {
        log.debug("Entering ReportServiceImpl - getReportRequestList method");
        report = reportHelper.getReportRequestList(report);
        log.debug("Exiting ReportServiceImpl - getReportRequestList method");
        return report;
    }

    @Override
    public Report getReportScheduleInfo(Report report) throws PrepFunctionalException, PrepSystemException {
        ScheduleInfo scheduleInfo = null;
        Report outReport = null;
        log.debug("Entering ReportScheduleInfoServiceBean - getReportScheduleInfo method");
        log.debug("report id passed::" + report.getReportID());
        log.debug("report name sending bach ::" + report.getReportName());
        outReport = reportHelper.getScheduleInfoDetails(report);
        scheduleInfo = new ScheduleInfo();
        scheduleInfo.setReportId(report.getReportID());
        scheduleInfo.setReportName(report.getReportName());
        log.debug("Exiting ReportScheduleInfoServiceBean - getReportScheduleInfo method");
        return outReport;
    }

    @Override
    public Report showInputParameters(Report reportVOB) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering ReportServiceImpl - showInputParameters method");
        reportVOB = reportCommonService.getReportParameters(reportVOB);
        log.debug("Exiting ReportServiceImpl - showInputParameters method");
        return reportVOB;
    }

    @Override
    public Report executeReport(Report reportVOB) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering ReportServiceImpl - executeReport method");
        reportVOB.setDatabaseType(reportHelper.retrieveReportDatabaseType(reportVOB.getReportName()));
        reportVOB = reportCommonService.executeReport(reportVOB);
        insertInstanceDetails(reportVOB);
        log.debug("Exiting ReportServiceImpl - executeReport method");
        return reportVOB;

    }

    @Override
    public void updateReportRequestStatus(Report report) throws PrepFunctionalException, PrepSystemException {
        log.debug("Entering ReportServiceImpl - updateReportRequestStatus method");
        reportHelper.updateReportRequestStatus(report);
        log.debug("Exiting ReportServiceImpl - updateReportRequestStatus method");
    }
    
    private void insertInstanceDetails(Report reportVOB) throws PrepSystemException{
        reportHelper.insertInstanceDetails(reportVOB);
    }
	
	 /**
     * Method getUserForInstance.
     * 
     * @param viewInstanceVOB
     * @return ViewInstance
     */
    public ViewInstance getUserForInstance(ViewInstance viewInstanceVOB)
        throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering ReportsServiceImpl - getUserForInstance method");
        }
        viewInstanceVOB = reportHelper.getUserForInstance(viewInstanceVOB);
        log.debug("Exiting ReportsServiceImpl - getUserForInstance method");
        return viewInstanceVOB;
    }
    
    /**
     * Method cancelReport
     * @param reportVOB
     * @return Report
     */
    public ViewInstance cancelReport(ViewInstance viewInstanceVOB) throws PrepSystemException,PrepFunctionalException{
            log.debug("Entering ReportsServiceImpl - cancelReport method");
        
        viewInstanceVOB= reportCommonService.deletePendingInstance(viewInstanceVOB);
        deleteInstanceDetails(viewInstanceVOB);
            log.debug("Exiting ReportsServiceImpl - cancelReport method");
        return viewInstanceVOB;
    }

        /**
         * Method deleteInstanceDetails.
         * @param viewInstanceVOB
         */
        private void deleteInstanceDetails(ViewInstance viewInstanceVOB) throws PrepSystemException,PrepFunctionalException{
            log.debug("Entering ReportsServiceImpl - deleteInstanceDetails method");
            reportHelper.deleteInstanceDetails(viewInstanceVOB);
            
            
        }
}
