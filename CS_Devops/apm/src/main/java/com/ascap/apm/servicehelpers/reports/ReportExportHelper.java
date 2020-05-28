package com.ascap.apm.servicehelpers.reports;

import java.io.RandomAccessFile;

import com.ascap.apm.common.exception.PrepSystemException;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;

public interface ReportExportHelper {
    
    public RandomAccessFile exportReport(IEnterpriseSession eSession, String instanceId, String reportName,
        String xmlPath, IInfoStore istore, String exportFormat) throws PrepSystemException;
    
}