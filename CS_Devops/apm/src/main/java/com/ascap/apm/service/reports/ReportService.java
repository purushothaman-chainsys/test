package com.ascap.apm.service.reports;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportListSearch;
import com.ascap.apm.vob.reports.ScheduleStatus;
import com.ascap.apm.vob.reports.ViewInstance;

public interface ReportService {

    public ReportListSearch getReportList(ReportListSearch reportSearchVOB)
        throws PrepSystemException, PrepFunctionalException;

    public Report getreportInstanceList(Report reportVOB) throws PrepSystemException, PrepFunctionalException;

    public Report getReportArchiveList(Report reportVOB)throws PrepSystemException, PrepFunctionalException;
    
    public ScheduleStatus returnReportInstance(ScheduleStatus instanceVOB)throws PrepSystemException, PrepFunctionalException;

    public Report validateReport(Report reportVOB) throws PrepFunctionalException, PrepSystemException;

    public Report getReportRequestList(Report report) throws PrepFunctionalException, PrepSystemException;

    public Report getReportScheduleInfo(Report report) throws PrepFunctionalException, PrepSystemException;

    public Report showInputParameters(Report reportVOB) throws PrepSystemException, PrepFunctionalException;

    public Report executeReport(Report reportVOB) throws PrepSystemException, PrepFunctionalException;

    public void updateReportRequestStatus(Report report) throws PrepFunctionalException, PrepSystemException;
 
  	public ViewInstance getUserForInstance(ViewInstance viewInstanceVOB)throws PrepSystemException, PrepFunctionalException;

    public ViewInstance cancelReport(ViewInstance viewInstanceVOB)throws PrepSystemException, PrepFunctionalException;
}
