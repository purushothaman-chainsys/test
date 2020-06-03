package com.ascap.apm.servicehelpers.reports;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.reports.ReportListSearch;

public interface ReportsHelper {
    
    public ReportListSearch getReportsList(ReportListSearch reportListSearch) throws PrepSystemException, PrepFunctionalException ;
    
    
    
}
