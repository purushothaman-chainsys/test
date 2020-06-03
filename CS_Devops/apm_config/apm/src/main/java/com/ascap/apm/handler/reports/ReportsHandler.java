package com.ascap.apm.handler.reports;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;

public interface ReportsHandler {

    public PREPContext setReportListPagination(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;
}
