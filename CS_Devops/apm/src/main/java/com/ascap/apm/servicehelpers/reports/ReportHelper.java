package com.ascap.apm.servicehelpers.reports;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.reports.AdminReport;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportListSearch;
import com.ascap.apm.vob.reports.ReportRequest;
import com.ascap.apm.vob.reports.ScheduleStatus;
import com.ascap.apm.vob.reports.ViewInstance;

public interface ReportHelper {
    
    public ReportListSearch getReportsList(ReportListSearch reportListSearch) throws PrepSystemException, PrepFunctionalException ;

    public AdminReport adminGetDefaultValues(AdminReport adminReport) throws PrepSystemException, PrepFunctionalException ;

    public Report retrieveInstanceDetails(Report reportVOB)throws PrepSystemException, PrepFunctionalException;

    public ScheduleStatus resetReportInstanceReturnEndDate(ScheduleStatus sStatus)throws PrepSystemException, PrepFunctionalException;

    public Report getValidateReport(Report reportVOB)throws PrepSystemException, PrepFunctionalException;

    public Report getScheduleInfoDetails(Report report)throws PrepSystemException, PrepFunctionalException;

    public ViewInstance getUserForInstance(ViewInstance viewInstanceVOB)throws PrepSystemException, PrepFunctionalException;

    public ReportRequest getReportRequestDetail(ReportRequest reportRequest)throws PrepSystemException, PrepFunctionalException;

    public ScheduleStatus updateReportInstanceDetails(ScheduleStatus instanceVOB)throws PrepSystemException, PrepFunctionalException;

    public Report getReportRequestList(Report report)throws PrepSystemException, PrepFunctionalException;
    
    public String retrieveReportDatabaseType(String reportName) throws PrepSystemException;
    
    public void updateReportRequestStatus(Report report);
    
    public Report insertInstanceDetails(Report reportVOB) throws PrepSystemException;
    
	public ViewInstance deleteInstanceDetails(ViewInstance viewInstanceVOB)throws PrepSystemException, PrepFunctionalException;
}
