package com.ascap.apm.servicehelpers.reports;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.reports.Report;

public interface ReportServiceHelper {

    public String formatdisplayNumber(String value) throws PrepSystemException;

    public String formatInstaceDate(String value, int type) throws PrepFunctionalException;

    public String formatParameterName(String prompt) throws PrepFunctionalException;

    public String formatdisplayDate(String string) throws PrepSystemException;

    public boolean isParamRequired(String paramName) throws PrepSystemException;

    public String formatInDateParam(String value, int type) throws PrepFunctionalException;

    public String formatOutDateParam(String value, String type) throws PrepFunctionalException;

    public String getMailSubject(Report report) throws PrepSystemException;

    public String getMailMessage(Report report) throws PrepSystemException;

}
