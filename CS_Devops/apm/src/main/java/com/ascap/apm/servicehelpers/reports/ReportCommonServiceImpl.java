package com.ascap.apm.servicehelpers.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.PrepUtils;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.cache.PrepKeyValueObject;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
import com.ascap.apm.common.utils.constants.ReportConstants;
import com.ascap.apm.database.reports.Connection;
import com.ascap.apm.service.reports.ReportRESTService;
import com.ascap.apm.vob.reports.Parameter;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportListSearch;
import com.ascap.apm.vob.reports.ScheduleInfo;
import com.ascap.apm.vob.reports.ScheduleStatus;
import com.ascap.apm.vob.reports.ViewInstance;
import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.occa.infostore.IDestination;
import com.crystaldecisions.sdk.occa.infostore.IDestinationPlugin;
import com.crystaldecisions.sdk.occa.infostore.IDestinations;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.infostore.INotifications;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportLogon;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameter;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterSingleValue;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterValue;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportProcessingInfo;
import com.crystaldecisions.sdk.plugin.desktop.report.IReport;
import com.crystaldecisions.sdk.plugin.destination.smtp.ISMTPOptions;
import com.crystaldecisions.sdk.properties.IProperties;
import com.crystaldecisions.sdk.properties.IProperty;
import com.crystaldecisions.sdk.properties.ISDKList;

/**
 * ReportCommonServiceImpl.java interacts with Crystal Enterprise server to exceute the requested serives
 * 
 * @author Murali Bamidi (mzbamidi@kanbay.com) Revision History:
 *         ------------------------------------------------------------------------ Modified by Comments Date/Time
 *         ------------------------------------------------------------------------ Raja Rao Modified 01-09-2006
 *         ------------------------------------------------------------------------
 */
@Service("reportCommonService")
public class ReportCommonServiceImpl implements ReportCommonService {

    protected LogHelper log = new LogHelper(this.getClass().getName());

    private static final String QUERY = "query::";

    private static final String SI_SUBMITTER = "SI_SUBMITTER";

    private static final String INFOSTORE = "InfoStore";

    @Autowired
    private ReportServiceHelper reportServiceHelper;

    /**
     * Method getInstanceStatus.
     * 
     * @param reportId
     * @return Report
     */
    @SuppressWarnings("rawtypes")
    public Report getInstanceStatus(Report insReport) throws PrepSystemException, PrepFunctionalException {
        ArrayList<Object> statusList = new ArrayList<>();
        IInfoObjects infoObjects = null;
        IInfoObject infoObject = null;
        Report report = null;
        report = insReport;
        log.debug("Start of getInstanceStatus()");
        try {
            String query =
                "Select SI_ID,SI_NAME,SI_STARTTIME,SI_ENDTIME,SI_CREATION_TIME,SI_SCHEDULE_STATUS,SI_KIND,SI_PROCESSINFO,SI_SCHEDULEINFO from CI_INFOOBJECTS Where SI_PROGID IN ('CrystalEnterprise.Report', 'CrystalEnterprise.Webi') AND SI_INSTANCE=1 And SI_NAME='"
                    + insReport.getReportName().trim() + "' ORDER BY SI_ID DESC";
            log.debug(QUERY + query);
            infoObjects = getInfoObjects(query);
            if (infoObjects != null && !infoObjects.isEmpty()) {
                report.setReportName(((IInfoObject) infoObjects.get(0)).getTitle());
            }
            log.debug("size::" + infoObjects.size());
            for (int i = 0; i < infoObjects.size(); i++) {
                String reportKind = "";
                ScheduleStatus statusObj = new ScheduleStatus();
                infoObject = (IInfoObject) infoObjects.get(i);
                statusObj.setInstanceID(infoObject.getID() + "");
                statusObj.setReportName(infoObject.getTitle());
                statusObj.setStatusCode(infoObject.getSchedulingInfo().getStatus());
                statusObj.setStatus(PrepUtils.getStatus(infoObject.getSchedulingInfo().getStatus()));
                IProperty endTimeprop = infoObject.properties().getProperty("SI_ENDTIME");
                IProperty creationTimeProp = infoObject.properties().getProperty("SI_CREATION_TIME");
                IProperty kind = infoObject.properties().getProperty("SI_KIND");
                String runby = "";
                if (infoObject.getSchedulingInfo().properties().getProperty(SI_SUBMITTER) != null) {
                    runby = (String) (infoObject.getSchedulingInfo().properties().getProperty(SI_SUBMITTER).getValue());
                }
                if (endTimeprop != null) {
                    log.debug("SI_ENDTIME:" + endTimeprop.getValue() + "xxxxxxxxxxxx" + endTimeprop.toString());
                    statusObj.setEndDate(reportServiceHelper.formatdisplayDate(endTimeprop.toString()));
                } else {
                    statusObj.setEndDate("");
                }
                if (creationTimeProp != null) {
                    log.debug("SI_CREATION_TIME:" + creationTimeProp.getValue() + "YYYYYYYYYYYYYYYYYYY"
                        + creationTimeProp.toString());
                    statusObj.setRequestedDate(reportServiceHelper.formatdisplayDate(creationTimeProp.toString()));

                } else {
                    statusObj.setRequestedDate("");
                }

                if (kind != null) {
                    reportKind = kind.getValue().toString();
                }
                statusObj.setRunBy(runby);
                log.debug("runBY::" + runby);
                if (ReportConstants.REPORT_KIND_WEBI.equalsIgnoreCase(reportKind)) {
                    StringBuilder params = new StringBuilder();
                    IProperties prompts = (IProperties) infoObject.getProcessingInfo().properties()
                        .getProperty("SI_WEBI_PROMPTS").getValue();
                    int numPrompts = ((Integer) prompts.getProperty("SI_TOTAL").getValue()).intValue();
                    log.debug(
                        "Total Prompts #############################" + numPrompts + "################" + reportKind);
                    if (numPrompts > 0) {
                        for (int nPromptNameIndex = 1; nPromptNameIndex <= numPrompts; nPromptNameIndex++) {
                            Integer propNum = Integer.valueOf(nPromptNameIndex);
                            IProperties prompt = (IProperties) prompts.getProperty(propNum).getValue();
                            String promptName = prompt.getProperty("SI_NAME").getValue().toString();
                            IProperties promptValue = (IProperties) prompt.getProperty("SI_VALUES").getValue();
                            String strTotalValue = promptValue.getProperty("SI_TOTAL").getValue().toString();
                            Integer intTotalValue = Integer.valueOf(strTotalValue);
                            int numPromptValues = (intTotalValue).intValue();
                            StringBuilder promptDataValues = new StringBuilder();
                            for (int nPromptValueIndex = 1; nPromptValueIndex <= numPromptValues; nPromptValueIndex++) {
                                Integer propvalueNum = Integer.valueOf(nPromptValueIndex);
                                String strpropvalueNum = propvalueNum.toString();
                                String singlePromptVal = promptValue.getProperty(strpropvalueNum).getValue().toString();
                                if (nPromptValueIndex == 1)
                                    promptDataValues.append(singlePromptVal);
                                else
                                    promptDataValues.append(promptDataValues + ";" + singlePromptVal);
                            }
                            if (!ValidationUtils.isEmptyOrNull(promptName)) {
                                params.append(promptName);
                                params.append('\r');
                            }
                            if (!ValidationUtils.isEmptyOrNull(promptDataValues.toString())) {
                                params.append(promptDataValues.toString());
                                params.append('\r');
                            }
                            log.debug("###########" + promptName + "@@@@@@@@@@" + promptDataValues.toString());
                        }
                    }
                    statusObj.setInputParams(params.toString());
                } else {
                    IReportProcessingInfo theReport = (IReportProcessingInfo) infoObject;
                    List parameters = theReport.getReportParameters();
                    StringBuilder params = new StringBuilder();
                    String value = "";
                    int type = -1;
                    for (int j = 0; j < parameters.size(); j++) {
                        IReportParameter pf = (IReportParameter) parameters.get(j);
                        if (pf.getPrompt() != null && !pf.getPrompt().equals("")) {
                            params.append(pf.getPrompt());
                            params.append('\r');
                        }
                        if (!pf.isRangeValueSupported()) {
                            IReportParameterValue val = null;
                            if (!pf.getCurrentValues().isEmpty()) {
                                val = (IReportParameterValue) pf.getCurrentValues().get(0);
                            }
                            type = pf.getValueType();
                            if (val != null && !val.isNull()) {
                                if (type == IReportParameter.ReportVariableValueType.DATE_TIME
                                    || type == IReportParameter.ReportVariableValueType.TIME
                                    || type == IReportParameter.ReportVariableValueType.DATE
                                    || type == IReportParameter.ReportVariableValueType.STRING) {
                                    value = val.getParameterFieldValue(type).computeText();
                                    params.append(reportServiceHelper.formatInstaceDate(value, type));
                                } else if (type == IReportParameter.ReportVariableValueType.NUMBER) {
                                    value = val.getParameterFieldValue(type).displayText(Locale.US);
                                    Double doubleVal = Double.valueOf(reportServiceHelper.formatdisplayNumber(value));
                                    long longval = doubleVal.longValue();
                                    log.debug("long:" + longval);
                                    double doubleval =
                                        Double.parseDouble(reportServiceHelper.formatdisplayNumber(value));
                                    if (doubleval == longval) {
                                        params.append(longval);
                                    } else {
                                        params.append(reportServiceHelper.formatdisplayNumber(value));
                                    }
                                }
                                params.append('\r');
                            } else {
                                params.append('\r');
                            }
                        }
                    }
                    statusObj.setInputParams(params.toString());
                }
                statusList.add(statusObj);
            }
            report.setStatusList(statusList);
            log.debug("End of getInstanceStatus():");
        } catch (SDKException sdke) {
            log.error(sdke.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = sdke.getMessage();
            throw new PrepSystemException("error.report.sdk.instanceStatus.exception", errorStrings);
        } catch (PrepFunctionalException pfe) {
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.instanceStatus.exception", errorStrings);
        }
        return report;
    }

    /**
     * Method getInfoObjects.
     * 
     * @param query
     * @return IInfoObjects
     */
    public IInfoObjects getInfoObjects(String query) throws PrepSystemException {
        IInfoObjects infoObjects = null;
        IInfoStore iStore = null;
        log.debug("Start of getInfoObjects(String query)");
        try {
            log.debug("getInfoObjects()- Before gettting Istore");
            iStore = getIstore();
            if (iStore != null) {
                infoObjects = iStore.query(query);
            }
        } catch (SDKException ske) {
            log.error("getInfoObjects()-In SDK Exception");
            try {
                Connection.logoff();
                iStore = getIstore();
                if (iStore != null) {
                    infoObjects = iStore.query(query);
                }
            } catch (Exception se) {
                log.error("getInfoObjects()-Inside Exception in  SDK Exception");
                log.error(se.getMessage());
                String[] errorStrings = new String[1];
                errorStrings[0] = se.getMessage();
                throw new PrepSystemException("error.report.common.infoobject.exception", errorStrings);
            }
        } catch (Exception e) {
            log.error("getInfoObjects()-Inside Exception block:  " + e.getMessage());
            try {
                Connection.logoff();
                iStore = getIstore();
                if (iStore != null) {
                    infoObjects = iStore.query(query);
                }
            } catch (Exception se) {
                log.error("getInfoObjects()-Inside Exception in Exception block:  " + se.getMessage());
                log.error(se.getMessage());
                String[] errorStrings = new String[1];
                errorStrings[0] = se.getMessage();
                throw new PrepSystemException("error.report.common.infoobject.exception", errorStrings);
            }
        }
        log.debug("End of getInfoObjects(String query)");
        return infoObjects;
    }

    /**
     * Method getIstore.
     * 
     * @return IInfoStore
     */

    public IInfoStore getIstore() throws Exception {
        log.debug("Start of getIstore()");
        IInfoStore iStore = null;
        IEnterpriseSession eSession = null;
        try {
            log.debug("getIstore()-Before session");
            eSession = Connection.getEnterPriseSession();
            if (eSession != null) {
                iStore = (IInfoStore) eSession.getService("", INFOSTORE);
                log.debug("getIstore()-after session");
            }
        } catch (SDKException ske) {
            log.error("getIstore()-In SDK Exception");
            try {
                eSession = Connection.forceGetEnterpriseSession();
                if (eSession != null) {
                    iStore = (IInfoStore) eSession.getService("", INFOSTORE);
                }
            } catch (Exception se) {
                log.error("getIstore()-Inside Exception block in SDKException");
                log.error(se.getMessage());
                String[] errorStrings = new String[1];
                errorStrings[0] = se.getMessage();
                throw new PrepSystemException("error.report.common.infostore.exception", errorStrings);
            }

        } catch (Exception e) {
            log.error("getIstore()-Inside Exception block");
            try {
                eSession = Connection.forceGetEnterpriseSession();
                if (eSession != null) {
                    iStore = (IInfoStore) eSession.getService("", INFOSTORE);
                }
            } catch (Exception ie) {
                log.error("getIstore()-Inside Exception block in Exception");
                log.error(ie.getMessage());
                String[] errorStrings = new String[1];
                errorStrings[0] = ie.getMessage();
                throw ie;
            }
        }
        log.debug("End of getIstore()");
        return iStore;
    }

    /**
     * Method getReportsList.
     * 
     * @param moduleName
     * @return ArrayList
     */

    @SuppressWarnings({"rawtypes", "unchecked"})
    public ReportListSearch getReportsList(ReportListSearch reportListSearch)
        throws PrepSystemException, PrepFunctionalException {
        String moduleName = reportListSearch.getModuleName();
        ArrayList reportsList = new ArrayList();
        IInfoObjects oInfoObjects = null;
        IInfoObject infoObject = null;
        String queryFolder = null;
        String queryReport = null;
        log.debug("Strat of getReportsList()." + moduleName);
        try {
            queryReport =
                "Select SI_ID,SI_NAME,SI_DESCRIPTION,SI_PROGID,SI_INSTANCE, SI_KIND from CI_INFOOBJECTS Where SI_INSTANCE=0 And SI_KIND='CrystalReport' ORDER BY SI_NAME ASC";
            log.debug(queryReport);
            oInfoObjects = getInfoObjects(queryReport);
            try {
                if (oInfoObjects != null && !oInfoObjects.isEmpty()) {
                    for (Iterator iterator = oInfoObjects.iterator(); iterator.hasNext();) {
                        Report report = new Report();
                        infoObject = (IInfoObject) iterator.next();
                        if (!infoObject.isInstance()) {
                            report.setDescription(infoObject.getDescription());
                            report.setReportID(infoObject.getID() + "");
                            report.setReportName(infoObject.getTitle());
                            report.setReportType(infoObject.getKind());
                            reportsList.add(report);
                        }
                    }
                }
            } catch (Exception sdke) {
                log.error("@@@ReportList Exception 2@@@" + sdke.getMessage());
                Connection.logoff();
                getInfoObjects(queryFolder);
                queryReport =
                    "Select SI_ID,SI_NAME,SI_DESCRIPTION,SI_PROGID,SI_INSTANCE, SI_KIND from CI_INFOOBJECTS Where SI_INSTANCE=0 And SI_KIND='CrystalReport' ORDER BY SI_NAME ASC";
                oInfoObjects = getInfoObjects(queryReport);
                for (Iterator iterator = oInfoObjects.iterator(); iterator.hasNext();) {
                    Report report = new Report();
                    infoObject = (IInfoObject) iterator.next();
                    if (!infoObject.isInstance()) {
                        report.setDescription(infoObject.getDescription());
                        report.setReportID(infoObject.getID() + "");
                        report.setReportName(infoObject.getTitle());
                        report.setReportType(infoObject.getKind());
                        reportsList.add(report);
                    }
                }
            }

        } catch (SDKException sdke) {
            log.error("getReportsList():-SDK Exception  " + sdke.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = sdke.getMessage();
            throw new PrepSystemException("error.report.sdk.reportlist.exception", errorStrings);
        } catch (Exception e) {
            log.error("getReportsList():-Exception  " + e.getMessage());
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.reportlist.exception", errorStrings);
        }
        log.debug("End of getReportsList()");
        reportListSearch.setReports(reportsList);
        return reportListSearch;
    }

    /**
     * Method retreiveUserArchiveInstances.
     * 
     * @param insReport
     * @return Report
     */
    public Report retreiveUserArchiveInstances(Report insReport) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering ReportCommonServiceImpl - retreiveUserArchiveInstances method");
        log.debug("Input statusList size: " + insReport.getStatusList().size());
        log.debug("Max. # of Archive instances: " + insReport.getArchiveInstanceCnt());
        ArrayList<Object> newStatusList = new ArrayList<>();
        if (!insReport.getStatusList().isEmpty()) {
            Iterator<Object> statusListItr = insReport.getStatusList().iterator();
            int totalInstCnt = insReport.getUserInstanceCnt() + insReport.getArchiveInstanceCnt();
            if (insReport.getStatusList().size() > insReport.getUserInstanceCnt()) {
                int instCnt = 1;
                do {
                    ScheduleStatus statusObj = (ScheduleStatus) statusListItr.next();
                    // within range
                    if (instCnt > insReport.getUserInstanceCnt() && instCnt <= totalInstCnt
                        && (statusObj.getReturnedEndDate() == null
                            || statusObj.getReturnedEndDate().trim().equalsIgnoreCase(""))) {
                        newStatusList.add(statusObj);
                        log.debug("Adding instance: " + statusObj.getInstanceID() + " to the new Status List");
                    }
                    instCnt++;
                } while (statusListItr.hasNext());
            }
            insReport.setStatusList(newStatusList);// overwrite with new list
        }
        log.debug("new Status List size: " + insReport.getStatusList().size());
        log.debug("Exiting ReportCommonServiceImpl - retreiveUserArchiveInstances method");
        return insReport;
    }

    /**
     * Method retreiveUserOnlineInstances.
     * 
     * @param insReport
     * @return Report
     */
    public Report retreiveUserOnlineInstances(Report insReport) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering ReportCommonServiceImpl - retreiveUserOnlineInstances method");
        log.debug("Input statusList size: " + insReport.getStatusList().size());
        log.debug("Max. # of online instances: " + insReport.getUserInstanceCnt());
        ArrayList<Object> newStatusList = new ArrayList<>();
        if (insReport.getUserInstanceCnt() == 0) {
            insReport.setUserInstanceCnt(PrepConstants.DEFAULT_USR_INST_CNT);
        }
        if (insReport.getArchiveInstanceCnt() == 0) {
            insReport.setArchiveInstanceCnt(PrepConstants.DEFAULT_ARCH_INST_CNT);
        }
        if (!insReport.getStatusList().isEmpty()) {
            Iterator<Object> statusListItr = insReport.getStatusList().iterator();
            int instCnt = 0;
            do {
                ScheduleStatus statusObj = (ScheduleStatus) statusListItr.next();
                if (instCnt < insReport.getUserInstanceCnt() || (statusObj.getReturnedEndDate() != null
                    && !statusObj.getReturnedEndDate().trim().equalsIgnoreCase(""))) {
                    newStatusList.add(statusObj);
                    log.debug("Adding instance: " + statusObj.getInstanceID() + " to the new Status List");
                }
                instCnt++;
            } while (statusListItr.hasNext());
        }
        insReport.setStatusList(newStatusList);// overwrite with new list
        log.debug("new Status List size: " + insReport.getStatusList().size());
        log.debug("Exiting ReportCommonServiceImpl - retreiveUserOnlineInstances method");
        return insReport;
    }

    /**
     * Method getInstanceDetails.
     * 
     * @param scheduleStatus
     * @return ScheduleStatus
     */
    @SuppressWarnings("rawtypes")
    public ScheduleStatus retrieveInstanceDetails(ScheduleStatus scheduleStatus)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("ENTERING - ReportCommonServiceImpl - retrieveInstanceDetails");
        IInfoObjects infoObjects = null;
        IInfoObject infoObject = null;
        ScheduleStatus statusObj = null;
        log.debug("Start of getInstanceStatus()");
        try {
            String query =
                "Select SI_ID,SI_NAME,SI_STARTTIME,SI_ENDTIME,SI_CREATION_TIME,SI_SCHEDULE_STATUS,SI_PROCESSINFO,SI_SCHEDULEINFO from CI_INFOOBJECTS Where SI_PROGID IN ('CrystalEnterprise.Report') AND  SI_INSTANCE=1 "
                    + "AND SI_ID='" + scheduleStatus.getInstanceID().trim() + "'";

            log.debug(QUERY + query);
            infoObjects = getInfoObjects(query);
            log.debug("size::" + infoObjects.size());
            statusObj = new ScheduleStatus();
            infoObject = (IInfoObject) infoObjects.get(0);// should only be one instance returned
            statusObj.setInstanceID(infoObject.getID() + "");
            statusObj.setReportName(infoObject.getTitle());
            statusObj.setStatusCode(infoObject.getSchedulingInfo().getStatus());
            statusObj.setStatus(PrepUtils.getStatus(infoObject.getSchedulingInfo().getStatus()));
            IProperty endTimeprop = infoObject.properties().getProperty("SI_ENDTIME");
            IProperty creationTimeProp = infoObject.properties().getProperty("SI_CREATION_TIME");
            String submitterUser =
                (String) (infoObject.getSchedulingInfo().properties().getProperty(SI_SUBMITTER).getValue());
            if (endTimeprop != null) {
                log.debug("SI_ENDTIME:" + endTimeprop.getValue() + "xxxxxxxxxxxx" + endTimeprop.toString());
                statusObj.setEndDate(reportServiceHelper.formatdisplayDate(endTimeprop.toString()));
            } else {
                statusObj.setEndDate("");
            }

            if (creationTimeProp != null) {
                log.debug("SI_CREATION_TIME:" + creationTimeProp.getValue() + "YYYYYYYYYYYYYYYYYYYYYYYYYYY"
                    + creationTimeProp.toString());
                statusObj.setRequestedDate(reportServiceHelper.formatdisplayDate(creationTimeProp.toString()));

            } else {
                statusObj.setRequestedDate("");
            }
            statusObj.setRunBy(submitterUser);
            log.debug("runBY::" + submitterUser);

            IReportProcessingInfo theReport = (IReportProcessingInfo) infoObject;
            List parameters = theReport.getReportParameters();
            StringBuilder params = new StringBuilder();
            StringBuilder inparams = new StringBuilder();
            String value = "";
            String name = "";
            int type = -1;
            for (int j = 0; j < parameters.size(); j++) {
                IReportParameter pf = (IReportParameter) parameters.get(j);
                if (pf.getPrompt() == null || "".equalsIgnoreCase(pf.getPrompt())) {
                    name = reportServiceHelper.formatParameterName(pf.getParameterName());
                } else {
                    name = reportServiceHelper.formatParameterName(pf.getPrompt());
                }
                inparams.append(inparams + name + "\r");
                if (pf.getPrompt() != null && !pf.getPrompt().equals("")) {
                    params.append(pf.getPrompt());
                    params.append('\r');
                }

                if (!pf.isRangeValueSupported()) {
                    IReportParameterValue val = null;
                    if (!pf.getCurrentValues().isEmpty()) {
                        val = (IReportParameterValue) pf.getCurrentValues().get(0);
                    }
                    type = pf.getValueType();
                    if (val != null && !val.isNull()) {
                        if (type == 5 || type == 4 || type == 3 || type == 6) {
                            value = val.getParameterFieldValue(type).computeText();
                            params.append(reportServiceHelper.formatInstaceDate(value, type));
                        } else if (type == 0) {
                            value = val.getParameterFieldValue(type).displayText(Locale.US);
                            Double doubleVal = Double.valueOf(reportServiceHelper.formatdisplayNumber(value));
                            long longval = doubleVal.longValue();
                            double doubleval = Double.parseDouble(reportServiceHelper.formatdisplayNumber(value));
                            if (doubleval == longval) {
                                params.append(longval);
                            } else {
                                params.append(reportServiceHelper.formatdisplayNumber(value));
                            }
                        }
                        params.append('\r');
                        inparams.append(inparams + value + "\r");
                    } else {
                        params.append('\r');
                        inparams.append(inparams + "\r");
                    }
                }
            }
            statusObj.setInputParams(params.toString());

        } catch (SDKException sdke) {
            log.error(sdke.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = sdke.getMessage();
            throw new PrepSystemException("error.report.sdk.instanceStatus.exception", errorStrings);
        } catch (PrepFunctionalException pfe) {
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.instanceStatus.exception", errorStrings);
        }
        log.debug("EXITING - ReportCommonServiceImpl - retrieveInstanceDetails");

        return statusObj;

    }

    public void returnReportInstanceToArchive(ScheduleStatus reportInstance)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("ENTERING - ReportCommonServiceImpl - returnReportInstanceToArchive");

        IInfoStore iStore = null;
        int reportID = 0;
        try {
            IEnterpriseSession es = Connection.getSpecialEnterPriseSession();
            iStore = (IInfoStore) es.getService("", INFOSTORE);
            String rptQry =
                "Select SI_ID from CI_INFOOBJECTS Where SI_PROGID IN ('CrystalEnterprise.Report') AND  SI_INSTANCE=0 And SI_NAME='"
                    + reportInstance.getReportName() + "'";

            IInfoObjects reports = iStore.query(rptQry);

            if (!reports.isEmpty()) {
                IInfoObject report = (IInfoObject) reports.get(0);
                log.debug("The report id: " + report.getID());
                // save report id
                reportID = report.getID();
            }
            String qry = "Select * from CI_INFOOBJECTS Where SI_ID ='" + reportInstance.getInstanceID() + "'";

            IInfoObjects iObjects = iStore.query(qry);

            if (!iObjects.isEmpty()) {
                IInfoObject iObject = (IInfoObject) iObjects.get(0);
                log.debug("The returned info object from CMS id: " + iObject.getID());
                log.debug("Returning report instance: " + iObject.getID() + " to report id: " + reportID);
                // change its parent iD
                iObject.setParentID(reportID);
                iObject.save();

                iStore.commit(iObjects);
            }

        } catch (SDKException sdke) {
            log.error(sdke.getMessage());
            log.debug("ReportCommonServiceImpl - returnReportInstanceToArchive - exception 1 - " + sdke.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = sdke.getMessage();
            throw new PrepSystemException("error.report.sdk.returnReportInstanceToArchive.exception", errorStrings);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug("ReportCommonServiceImpl - returnReportInstanceToArchive - exception 2 - " + e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.returnReportInstanceToArchive.exception", errorStrings);

        }
        log.debug("EXITING - ReportCommonServiceImpl - returnReportInstanceToArchive");
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public Report getReportParameters(Report reportVob) throws PrepSystemException, PrepFunctionalException {
        String reportName = reportVob.getReportName();
        log.debug("Entering getReportParameters() in ReportCommonServiceImpl:" + reportName);

        List<Parameter> paramList = new ArrayList<>();
        IInfoObjects oInfoObjects = null;
        IReportParameter oReportParameter;
        Report report = reportVob;
        try {
            String query =
                "Select SI_ID, SI_NAME, SI_DESCRIPTION, SI_SPECIFIC_KIND, SI_EXTENDED_PROCESSINFO, SI_PROCESSINFO.SI_PROMPTS from CI_INFOOBJECTS Where SI_INSTANCE=0 And SI_NAME='"
                    + reportName.trim() + "'";
            log.debug(QUERY + query);
            oInfoObjects = getInfoObjects(query);

            IInfoObject oInfoObject = (IInfoObject) oInfoObjects.get(0);
            log.debug("######################### Size " + (oInfoObject.properties()).size());
            String reportKind = oInfoObject.getKind();

            report.setReportName(oInfoObject.getTitle());
            report.setReportID(oInfoObject.getID() + "");

            log.debug("Report ID " + report.getReportID());
            if (PrepConstants.REPORT_KIND_WEBI.equalsIgnoreCase(reportKind)) {
                ReportRESTService rrs = new ReportRESTService();
                paramList = rrs.getReportParameters(report.getReportID());
            } else {
                IReport oReport = (IReport) oInfoObject;
                List<IReportParameter> reportParamList = oReport.getReportParameters();

                for (int i = 0; i < reportParamList.size(); i++) {

                    oReportParameter = reportParamList.get(i);
                    Parameter param = new Parameter();
                    param.setParamID(i);
                    param.setParamName(reportServiceHelper.formatParameterName(oReportParameter.getParameterName()));
                    param.setParamLabel(reportServiceHelper.formatParameterName(oReportParameter.getPrompt()));
                    param.setParameterType(oReportParameter.getValueType() + "");
                    param.setDescreteValued(oReportParameter.isDiscreteValueSupported());
                    param.setRangeValued(oReportParameter.isRangeValueSupported());
                    param.setMultiValued(oReportParameter.isMultipleValuesEnabled());
                    param.setCustomValued(oReportParameter.isEditingDisallowed());
                    log.debug(
                        "@@@@@@@@@@@@@#################@@@@@@@@@@@@@@@@@@ " + oReportParameter.isEditingDisallowed());

                    if (oReportParameter.getPrompt() == null || "".equalsIgnoreCase(oReportParameter.getPrompt())) {
                        param.setRequired(reportServiceHelper.isParamRequired(oReportParameter.getParameterName()));
                    } else {
                        param.setRequired(reportServiceHelper.isParamRequired(oReportParameter.getPrompt()));
                    }

                    log.debug("parameter name::" + oReportParameter.getParameterName());
                    log.debug("parameter prompt::" + oReportParameter.getPrompt());
                    log.debug("parameter type::" + oReportParameter.getValueType());

                    log.debug("MANOJ oReportParameter.isDefaultValueSet()  " + oReportParameter.isDefaultValueSet());
                    log.debug("MANOJ oReportParameter.isCurrentValueSet()  " + oReportParameter.isCurrentValueSet());

                    if (oReportParameter.isDefaultValueSet()) {

                        String val = reportServiceHelper.formatInDateParam(
                            ((IReportParameterSingleValue) oReportParameter.getDefaultValues().get(0)).getValue(),
                            oReportParameter.getValueType());
                        param.setParameterValue(val);
                        if (oReportParameter.getDefaultValues().size() > 1) {
                            ArrayList<PrepKeyValueObject> defaultValueList = new ArrayList<>();
                            param.setDefaultList(true);

                            for (int j = 0; j < oReportParameter.getDefaultValues().size(); j++) {

                                // collect both objects
                                // if show description is true, show the parameters' descriptions\

                                log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX " + j + "     "
                                    + ((IReportParameterSingleValue) oReportParameter.getDefaultValues().get(j))
                                        .getValue());

                                PrepKeyValueObject rptCodeDescObject = new PrepKeyValueObject();
                                rptCodeDescObject
                                    .setKey(((IReportParameterSingleValue) oReportParameter.getDefaultValues().get(j))
                                        .getValue());// code
                                if (oReportParameter.isShowDescriptionOnlyEnabled()) {
                                    rptCodeDescObject.setValue(
                                        ((IReportParameterSingleValue) oReportParameter.getDefaultValues().get(j))
                                            .getDescription());// description
                                }
                                defaultValueList.add(rptCodeDescObject);

                            }
                            param.setMultipleValues(defaultValueList);
                        }

                    } else if (oReportParameter.isCurrentValueSet()) {
                        String val = reportServiceHelper.formatInDateParam(
                            ((IReportParameterSingleValue) oReportParameter.getCurrentValues().get(0)).getValue(),
                            oReportParameter.getValueType());
                        param.setParameterValue(val);

                        for (int j = 0; j < oReportParameter.getCurrentValues().size(); j++) {
                            log.debug("default vaue::"
                                + ((IReportParameterSingleValue) oReportParameter.getCurrentValues().get(j))
                                    .getValue());
                        }

                    }

                    paramList.add(param);

                }
            }

            report.setParameters(paramList);

        } catch (SDKException sdke) {
            log.error(sdke.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = sdke.getMessage();
            log.debug("SDKException in getReportParameters() in ReportCommonServiceImpl:" + sdke);
            throw new PrepSystemException("error.report.sdk.reportParameters.exception", errorStrings);
        } catch (PrepFunctionalException pfe) {
            log.debug("PrepFunctionalException in getReportParameters() in ReportCommonServiceImpl:" + pfe);
            throw pfe;
        } catch (PrepSystemException pse) {
            log.debug("PrepSystemException in getReportParameters() in ReportCommonServiceImpl:" + pse);
            throw pse;
        } catch (Exception e) {
            log.debug("Exception in getReportParameters() in ReportCommonServiceImpl:" + e);
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.reportParameters.exception", errorStrings);
        }

        log.debug("Exiting getReportParameters() in ReportCommonServiceImpl:" + reportName);
        return report;

    }

    @SuppressWarnings("rawtypes")
    @Override
    public Report executeReport(Report report) throws PrepSystemException, PrepFunctionalException {
        IInfoStore iStore = null;
        IReportParameter oReportParameter;
        IReportParameterSingleValue currentValue = null;
        // parameters added newly for schedule instance
        String instanceId = null;
        String scheduleStatus = null;
        ArrayList<Object> statusList = new ArrayList<>();
        log.debug("Start of excecuteReport()");

        try {

            String query =
                "Select * from CI_INFOOBJECTS Where SI_INSTANCE=0 And SI_NAME='" + report.getReportName() + "'";
            log.debug(QUERY + query);

            IInfoObjects oInfoObjects = getInfoObjects(query);
            IInfoObject oInfoObject = (IInfoObject) oInfoObjects.get(0);

            String reportKind = oInfoObject.getKind();
            report.setReportID("" + oInfoObject.getID());

            if (PrepConstants.REPORT_KIND_WEBI.equalsIgnoreCase(reportKind)) {
                ReportRESTService rrs = new ReportRESTService();
                rrs.scheduleReport(report.getReportID(), report.getReportName(),
                    (ArrayList<Parameter>) report.getParameters());
            } else {
                IReport oReport = (IReport) oInfoObject;
                ISDKList dbLogons = oReport.getReportLogons();
                IReportLogon dblogon = (IReportLogon) dbLogons.get(0);

                // set the database/datasource settings depending on Report Name
                String rptDbType = report.getDatabaseType();

                if (ValidationUtils.isEmptyOrNull(rptDbType)) {
                    rptDbType = PrepConstants.DATASOURCE_ORACLE;// default to oracle
                }

                if (PrepConstants.DATASOURCE_DW.equalsIgnoreCase(rptDbType)) {
                    setReportDWDBLogon(dblogon);
                } else if (PrepConstants.DATASOURCE_ORACLE.equalsIgnoreCase(rptDbType)) {
                    setReportDBLogon(dblogon);
                }

                iStore = getIstore();
                iStore.commit(oInfoObjects);

                List reportParamList = oReport.getReportParameters();

                for (int i = 0; i < reportParamList.size(); i++) {

                    oReportParameter = (IReportParameter) reportParamList.get(i);

                    Parameter param = report.getParameters().get(i);

                    String paramType = param.getParameterType();
                    String paramValue = param.getInputValue();

                    if (!oReportParameter.getCurrentValues().isEmpty()) {
                        oReportParameter.getCurrentValues().clear();

                    } else {
                        log.debug("default list is zero for:" + param.getParamName());
                    }

                    if (paramValue != null && paramValue.trim().length() > 0) {

                        log.debug("parameter is not a null value");
                        currentValue = oReportParameter.getCurrentValues().addSingleValue();

                        if (paramType.equalsIgnoreCase("0")) {

                            String paramStr = param.getInputValue();
                            if (paramStr != null && paramStr.length() != 0) {
                                currentValue.setValue(param.getInputValue().trim());
                            } else {
                                currentValue.setValue("");
                            }

                        } else {
                            currentValue.setValue(reportServiceHelper.formatOutDateParam(param.getInputValue(),
                                param.getParameterType()));
                        }

                    } else {

                        currentValue = oReportParameter.getCurrentValues().addSingleValue();
                        currentValue.setNull(true);

                    }

                }

                ISchedulingInfo schedInfo = oInfoObject.getSchedulingInfo();

                if (!report.getScheduleInfo().getScheduleNow().equalsIgnoreCase("T")) {
                    iStore = getIstore();
                    ScheduleStatus schedulevob = new ScheduleStatus();
                    sendEmailForReport(schedInfo, iStore, report);
                    synchronized (this) {
                        schedInfo.setRightNow(true);
                        iStore.schedule(oInfoObjects);
                        instanceId = oInfoObject.properties().getProperty("SI_NEW_JOB_ID").getValue().toString();
                        if (log.isDebugEnabled())
                            log.debug("SI_NEW_JOB_ID:" + instanceId);
                        schedulevob.setInstanceID(instanceId);
                        statusList.add(schedulevob);
                        report.setNewJobId(instanceId);
                        report.setStatusList(statusList);

                    }
                    // schedule at latter point of time
                } else {
                    ScheduleInfo scheduleinfo = report.getScheduleInfo();
                    String time = scheduleinfo.getScheduleTime();
                    String hours = time.substring(0, 2);
                    String mins = time.substring(3, 5);
                    String secs = "01";
                    int runHour = (Integer.valueOf(hours)).intValue();
                    int runMinute = (Integer.valueOf(mins)).intValue();
                    int runSecond = (Integer.valueOf(secs)).intValue();

                    Calendar scheduleTime = GregorianCalendar.getInstance();
                    scheduleTime.set(Calendar.HOUR_OF_DAY, runHour);
                    scheduleTime.set(Calendar.MINUTE, runMinute);
                    scheduleTime.set(Calendar.SECOND, runSecond);
                    Calendar requestedTime = GregorianCalendar.getInstance();
                    // if the requested time has crossed Todays schedule time,then schedule the report tomorrow at the
                    // specified schedule time
                    if (requestedTime.after(scheduleTime)) {
                        scheduleTime.add(Calendar.DAY_OF_MONTH, 1);
                        log.debug(scheduleTime.get(Calendar.DAY_OF_MONTH) + "/" + scheduleTime.get(Calendar.MONTH) + "/"
                            + scheduleTime.get(Calendar.YEAR) + "/" + scheduleTime.get(Calendar.HOUR_OF_DAY) + "/"
                            + scheduleTime.get(Calendar.MINUTE) + "/" + scheduleTime.get(Calendar.SECOND));
                    }
                    Date beginDate = scheduleTime.getTime();
                    schedInfo.setBeginDate(beginDate);
                    schedInfo.setType(0);
                    iStore = getIstore();
                    ScheduleStatus schedulevob = new ScheduleStatus();
                    sendEmailForReport(schedInfo, iStore, report);
                    synchronized (this) {
                        iStore.schedule(oInfoObjects);
                        instanceId = oInfoObject.properties().getProperty("SI_NEW_JOB_ID").getValue().toString();
                        if (log.isDebugEnabled())
                            log.debug("SI_NEW_JOB_ID:" + instanceId);
                        schedulevob.setInstanceID(instanceId);
                        schedulevob.setStatus(scheduleStatus);
                        statusList.add(schedulevob);
                        report.setNewJobId(instanceId);
                        report.setStatusList(statusList);
                    }
                }
            }
        } catch (SDKException sdke) {
            log.error(sdke.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = sdke.getMessage();
            throw new PrepSystemException("error.report.sdk.reportExecute.exception", errorStrings);
        } catch (PrepFunctionalException pfe) {
            throw pfe;
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.reportExecute.exception", errorStrings);
        }

        log.debug("**End of ecuteReport()**");
        return report;
    }

    @SuppressWarnings("unchecked")
    public void sendEmailForReport(ISchedulingInfo schedInfo, IInfoStore iStore, Report report)
        throws PrepSystemException {
        try {
            log.debug("**Start of  sendEmailForReport()**");
            // Get the Notifications from the SchedulingInfo Object
            INotifications notifications = schedInfo.getNotifications();
            // Get the Failure Notification object
            IDestinations failureDestination = notifications.getDestinationsOnFailure();
            // Adding a destination to the notification and then setting its options
            IDestination dest = failureDestination.add("");

            // Query the CMS database to return the SMTP Plugin for the report
            IInfoObjects smtpResults = iStore
                .query("SELECT * FROM CI_SYSTEMOBJECTS WHERE SI_PARENTID = 29 And SI_NAME = 'CrystalEnterprise.SMTP'");
            IDestinationPlugin destPlugin = (IDestinationPlugin) smtpResults.get(0);
            // Set SMTP Options
            ISMTPOptions smtpoptions = (ISMTPOptions) destPlugin.getScheduleOptions();
            smtpoptions
                .setSenderAddress(PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_USER)
                    + "_Reports@ascap.com");
            smtpoptions.setSubject(reportServiceHelper.getMailSubject(report));
            // Add the addresses to send the email to
            smtpoptions.getToAddresses().add(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.PREP_REPORTS_MAIL_TO));
            smtpoptions.setMessage(reportServiceHelper.getMailMessage(report));
            dest.setFromPlugin(destPlugin);
            log.debug("If the report fails an e-mail will be sent to"
                + PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.PREP_REPORTS_MAIL_TO));

            log.debug("**End of  sendEmailForReport()**");

        } catch (SDKException sdke) {
            log.error(sdke.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = sdke.getMessage();
            throw new PrepSystemException("error.report.sdk.sendEmail.exception", errorStrings);

        } catch (PrepSystemException pse) {
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.sendEmail.exception", errorStrings);

        }

    }

    private void setReportDBLogon(IReportLogon dblogon) throws Exception {
        if (dblogon.isCustomizable()) {
            dblogon.setOriginalDataSource(false);
            dblogon.setCustomServerName(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_DB_HOST));
            dblogon.setCustomUserName(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_DB_USER));
            dblogon.setCustomPassword(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_DB_PASSWORD));
            dblogon.setPromptOnDemandViewing(false);
            log.debug("*** Custom database enable***" + dblogon.isCustomizable());
        } else {
            dblogon.setOriginalDataSource(true);
            dblogon.setServerName(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_DB_HOST));
            dblogon
                .setUserName(PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_DB_USER));
            dblogon.setPassword(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_DB_PASSWORD));
            dblogon.setPromptOnDemandViewing(false);
            log.debug("*** Original database enable***" + dblogon.isOriginalDataSource());
        }
    }

    private void setReportDWDBLogon(IReportLogon dblogon) throws Exception {
        if (dblogon.isCustomizable()) {
            dblogon.setOriginalDataSource(false);
            dblogon.setCustomServerName(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.DW_DB_HOST));
            dblogon.setCustomUserName(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.DW_DB_USER));
            dblogon.setCustomPassword(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.DW_DB_PASSWORD));
            dblogon.setPromptOnDemandViewing(false);
            if (log.isDebugEnabled()) {
                log.debug("*** Custom DW database enable***" + dblogon.isCustomizable());
            }
        } else {
            dblogon.setOriginalDataSource(true);
            dblogon.setServerName(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.DW_DB_HOST));
            dblogon
                .setUserName(PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.DW_DB_USER));
            dblogon.setPassword(
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.DW_DB_PASSWORD));
            dblogon.setPromptOnDemandViewing(false);
            log.debug("*** Original DW database enable***" + dblogon.isOriginalDataSource());
        }

    }

    public ViewInstance deletePendingInstance(ViewInstance viewInstanceVOB)
        throws PrepSystemException, PrepFunctionalException {
        IInfoObject infoObject = null;
        IInfoStore iStore = null;
        IInfoObjects infoObjects = null;
        IInfoObjects statusInfoObjects = null;
        IInfoObject statusInfoObject = null;
        String status = null;
        String instanceID = viewInstanceVOB.getReportID();
        try {

            try {
                IEnterpriseSession es = Connection.getEnterPriseSession();
                iStore = (IInfoStore) es.getService("", INFOSTORE);
            } catch (Exception e) {
                IEnterpriseSession es = Connection.forceGetEnterpriseSession();
                iStore = (IInfoStore) es.getService("", INFOSTORE);
            }
            String querystatus =
                "Select SI_SCHEDUlE_STATUS from CI_INFOOBJECTS Where SI_PROGID IN ('CrystalEnterprise.PDF','CrystalEnterprise.Report') AND  SI_INSTANCE=1 And SI_ID="
                    + instanceID;
            statusInfoObjects = iStore.query(querystatus);
            if (statusInfoObjects != null && !statusInfoObjects.isEmpty()) {
                log.debug("statusInfoObjects size:" + statusInfoObjects.size());
                statusInfoObject = (IInfoObject) statusInfoObjects.get(0);
                status = statusInfoObject.properties().getProperty("SI_SCHEDUlE_STATUS").getValue().toString();
                log.debug("Status before deleting:" + status);
            }

            if (status != null && (status.equalsIgnoreCase("9") || status.equalsIgnoreCase("0"))) {

                String query =
                    "Select * from CI_INFOOBJECTS Where SI_PROGID IN ('CrystalEnterprise.PDF','CrystalEnterprise.Report')"
                        + " AND  SI_INSTANCE=1 And SI_ID='" + instanceID.trim() + "'";
                log.debug(QUERY + query);

                infoObjects = iStore.query(query);
                infoObject = (IInfoObject) infoObjects.get(0);
                infoObjects.delete(infoObject);
                iStore.commit(infoObjects);

            }
        } catch (SDKException sdke) {
            log.error(sdke.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = sdke.getMessage();
            throw new PrepSystemException("error.report.sdk.cancelInstance.exception", errorStrings);
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.cancelInstance.exception", errorStrings);
        }
        return viewInstanceVOB;

    }
}
