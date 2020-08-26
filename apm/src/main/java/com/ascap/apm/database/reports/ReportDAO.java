package com.ascap.apm.database.reports;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportRequest;
import com.ascap.apm.vob.reports.ScheduleStatus;
import com.ascap.apm.vob.reports.ViewInstance;

public interface ReportDAO {

    public Report retrieveInstanceDetails(Report reportVOB) throws PrepSystemException;

    public ScheduleStatus updateReportInstanceDetails(ScheduleStatus instanceDetails) throws PrepSystemException;

    public ReportRequest getReportRequestDetail(ReportRequest reportRequest) throws PrepSystemException;

    public ViewInstance getUserForInstance(ViewInstance viewInstanceVOB) throws PrepSystemException;

    public Report getReportScheduleInfoDetails(Report report) throws PrepSystemException;

    public ScheduleStatus resetReportInstanceReturnEndDate(ScheduleStatus instanceDetails) throws PrepSystemException;

    public Report getValidateReport(Report report) throws PrepSystemException;

    public Report getReportRequestList(Report report) throws PrepSystemException;
    
    public void updateReportRequestStatus(Report report)throws PrepSystemException;

    public String retrieveReportDatabaseType(String reportName) throws PrepSystemException;
    
    public Report insertInstanceDetails(Report report) throws PrepSystemException;
	
	  public ViewInstance deleteInstanceDetails(ViewInstance viewInstanceVOB)throws PrepSystemException;

}
