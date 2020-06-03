package com.ascap.apm.servicehelpers.reports;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportListSearch;
import com.ascap.apm.vob.reports.ScheduleStatus;
import com.ascap.apm.vob.reports.ViewInstance;

public interface ReportCommonService {

    public ReportListSearch getReportsList(ReportListSearch reportSearchVOB) throws PrepSystemException, PrepFunctionalException;

    public Report getInstanceStatus(Report reportVOB) throws PrepSystemException, PrepFunctionalException;

    public Report retreiveUserOnlineInstances(Report reportVOB) throws PrepSystemException, PrepFunctionalException;

    public void returnReportInstanceToArchive(ScheduleStatus sStatus)throws PrepSystemException, PrepFunctionalException ;

    public Report retreiveUserArchiveInstances(Report reportVOB)throws PrepSystemException, PrepFunctionalException ;
    
    public ScheduleStatus retrieveInstanceDetails(ScheduleStatus instanceVOB)throws PrepSystemException, PrepFunctionalException;

    public Report getReportParameters(Report reportVob) throws PrepSystemException, PrepFunctionalException;
    
    public Report executeReport(Report report) throws PrepSystemException, PrepFunctionalException;
	
	public ViewInstance deletePendingInstance(ViewInstance viewInstanceVOB)throws PrepSystemException, PrepFunctionalException;
    
}
