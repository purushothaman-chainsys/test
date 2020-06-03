package com.ascap.apm.service.report;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.reports.ReportListSearch;

public interface ReportsService {

    public ReportListSearch getReportsList(ReportListSearch reportListSearch)
        throws PrepSystemException, PrepFunctionalException;

}
