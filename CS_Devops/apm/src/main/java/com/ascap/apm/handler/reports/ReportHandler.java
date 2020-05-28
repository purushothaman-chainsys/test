package com.ascap.apm.handler.reports;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.reports.ViewInstance;

public interface ReportHandler {

    public PREPContext setReportListPagination(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext setReportInstacePagination(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public ViewInstance viewInstance(ViewInstance viewInstanceVob) throws PrepSystemException, PrepFunctionalException;

    public PREPContext setReportArchivePagination(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext returnReportInstance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext validateReport(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getReportRequestList(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException;

    public PREPContext getReportScheduleInfo(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException;

    public PREPContext showInputParameters(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException;

    public PREPContext executeReport(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException;

    public PREPContext updateReportRequestStatus(PREPContext prepCtx)
        throws PrepSystemException, PrepFunctionalException;

    public PREPContext getUserForInstance(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

    public PREPContext cancelReport(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;

}
