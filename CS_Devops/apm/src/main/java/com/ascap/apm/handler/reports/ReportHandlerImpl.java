package com.ascap.apm.handler.reports;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.database.reports.Connection;
import com.ascap.apm.handler.BaseHandler;
import com.ascap.apm.service.reports.ReportService;
import com.ascap.apm.servicehelpers.reports.ReportExportHelper;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportInstancesList;
import com.ascap.apm.vob.reports.ReportListSearch;
import com.ascap.apm.vob.reports.ScheduleStatus;
import com.ascap.apm.vob.reports.ViewInstance;
import com.crystaldecisions.report.web.viewer.CrystalReportViewer;
import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.managedreports.IReportSourceFactory;
import com.crystaldecisions.sdk.occa.report.reportsource.IReportSource;


@Service("reportHandler")
@Transactional(value = "ascapTxManager")
public class ReportHandlerImpl extends BaseHandler implements ReportHandler {

    private static final long serialVersionUID = 3260668370321869797L;
    
    private static final String INFOSTORE = "InfoStore";

    @Autowired
    private ReportService reportService;
    
    @Autowired
    private ReportExportHelper reportExportHelper;

    public PREPContext setReportListPagination(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        PREPContext outputContext = new PREPContext();
        PREPContext inputContextNew = new PREPContext();
        try {
            List<Object> listOfReports = null;
            List<Object> inputValueObjects = inputContext.getInputValueObjects();
            ReportListSearch reportSearchVOB = null;
            HttpServletRequest request = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                Iterator<Object> iterator = inputValueObjects.iterator();
                while (iterator.hasNext()) {
                    Object object = iterator.next();
                    if (object instanceof HttpServletRequest) {
                        request = (HttpServletRequest) object;
                    } else {
                        reportSearchVOB = (ReportListSearch) object;
                    }
                }
                inputContextNew.addInputValueObject(reportSearchVOB);
                outputContext = getReportList(inputContextNew);

                List<Object> outValueObjects = outputContext.getOutputValueObjects();

                if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                    reportSearchVOB = (ReportListSearch) outValueObjects.iterator().next();
                }
                listOfReports = reportSearchVOB.getReports();
                List<Object> filteredList = getTAMFilteredReportsList(listOfReports, request);
                int listsize = filteredList.size();
                reportSearchVOB.setNumberOfRecordsFound(filteredList.size());
                if (listsize != 0) {
                    int fromIndex = reportSearchVOB.getFromIndex() - 1;
                    int toIndex = reportSearchVOB.getToIndex() - 1;
                    if (fromIndex >= listsize) {
                        fromIndex = listsize - 1;
                    }
                    if (toIndex > listsize) {
                        toIndex = listsize;
                    }
                    List<Object> sublist = new ArrayList<>();
                    for (int i = fromIndex; i < toIndex; i++) {
                        sublist.add(filteredList.get(i));
                    }
                    reportSearchVOB.setSearchResults(sublist);
                    reportSearchVOB.setReports(sublist);
                }
                outputContext.addOutputValueObject(reportSearchVOB);
            }
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (PrepFunctionalException pfe) {
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.basereport.pagination.exception", errorStrings);
        }
        return outputContext;
    }

    public PREPContext getReportList(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException {

            log.debug("Entering getReportList method in ReportHandleImpl");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            ReportListSearch reportListSearchVOB = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                reportListSearchVOB = (ReportListSearch) inputValueObjects.iterator().next();
            }
            reportListSearchVOB = reportService.getReportList(reportListSearchVOB);
            outputContext.addOutputValueObject(reportListSearchVOB);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }
            log.debug("Exiting getReportList method in ReportHandleImpl");
        return outputContext;
    }

    private List<Object> getTAMFilteredReportsList(List<Object> listOfReports, HttpServletRequest request) {
        List<Object> filteredList = new ArrayList<>();
        UserProfile userProfile = (UserProfile) request.getSession().getAttribute(UserProfile.HTTP_SESSION_KEY);
        if (!userProfile.isSecurityEnabled()) {
            if (log.isDebugEnabled()) {
                log.debug(
                    "BaseReportController - getTAMFilteredReportsList() method - security disabled. returning null.");
            }
            return filteredList;
        }
        for (int i = 0; i < listOfReports.size(); i++) {
            filteredList.add(listOfReports.get(i));
        }
        return filteredList;
    }

    public PREPContext setReportInstacePagination(PREPContext inputContext)
        throws PrepFunctionalException, PrepSystemException {
        List<Object> inputValueObjectsForID = inputContext.getInputValueObjects();
        Report insReport = new Report();
        ReportInstancesList reportInstancesVOB = null;
        PREPContext outputContext = null;
        PREPContext outputContextNew = new PREPContext();
        if (inputValueObjectsForID != null && !inputValueObjectsForID.isEmpty()) {
            reportInstancesVOB = (ReportInstancesList) inputValueObjectsForID.iterator().next();
        }
        insReport.setReportName(reportInstancesVOB.getReportName());
        try {
            PREPContext inputContextNew = new PREPContext();
            inputContextNew.addInputValueObject(insReport);
            outputContext = getreportInstanceList(inputContextNew);
            Report report = null;
            List<Object> outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                report = (Report) outValueObjects.iterator().next();
            }
            int listsize = report.getStatusList().size();
            log.debug("listReturnedFrom:" + listsize);
            reportInstancesVOB.setNumberOfRecordsFound(listsize);
            reportInstancesVOB.setAdminRunnableFlag(report.isAdminRunnableFlag());
            if (listsize != 0) {
                int fromIndex = reportInstancesVOB.getFromIndex() - 1;
                int toIndex = reportInstancesVOB.getToIndex() - 1;
                log.debug("From:" + fromIndex + "---To:" + toIndex);
                if (fromIndex >= listsize) {
                    fromIndex = listsize - 1;
                }
                if (toIndex > listsize) {
                    toIndex = listsize;
                }
                List<Object> sublist = new ArrayList<>();
                for (int i = fromIndex; i < toIndex; i++) {
                    sublist.add(report.getStatusList().get(i));
                }
                log.debug(sublist);
                reportInstancesVOB.setSearchResults(sublist);
                reportInstancesVOB.setStatusList(sublist);
            }
            outputContextNew.addOutputValueObject(reportInstancesVOB);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (PrepFunctionalException pfe) {
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
        }
        return outputContextNew;
    }

    /**
     * @see com.ascap.apm.controller.reports.ReportControllerLocalInterface#getreportInstanceList(PREPContext)
     */

    public PREPContext getreportInstanceList(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getreportInstanceList method in ReportsHandlerImpl");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            Report reportVOB = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                reportVOB = (Report) inputValueObjects.iterator().next();
            }
            reportVOB = reportService.getreportInstanceList(reportVOB);
            outputContext.addOutputValueObject(reportVOB);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("Exiting getreportInstancelist method in ReportsHandlerImpl");
        return outputContext;

    }
    
    /**
     * @see com.ascap.apm.controller.reports.ReportControllerLocalInterface#viewInstance(viewInstanceVob)
     */
    public ViewInstance viewInstance(ViewInstance viewInstanceVob) throws PrepSystemException, PrepFunctionalException {
        String reportInstanceId = viewInstanceVob.getReportID();
        String export = viewInstanceVob.getExportType();
        String reportName = viewInstanceVob.getReportName();
        IInfoStore iStore = null;
        IInfoObjects infoObjects = null;
        IInfoObject infoObject = null;
        Object returnInstance = null;
        RandomAccessFile fileHandle = null;
        IReportSourceFactory factoryPS = null;
        IEnterpriseSession es = null;

            log.debug("Start of viewInstance()");

        try {
            String query = "SELECT SI_PROGID FROM CI_INFOOBJECTS WHERE SI_ID='" + reportInstanceId + "'";

            infoObjects = getInfoObjects(query);
            if(infoObjects!=null && !infoObjects.isEmpty()){
                infoObject = (IInfoObject) infoObjects.get(0);
            }
            try{
                es = Connection.getEnterPriseSession();
                iStore = (IInfoStore) es.getService("", INFOSTORE);
                factoryPS = (IReportSourceFactory) es.getService("PSReportFactory");
            } catch (Exception e){
                es = Connection.forceGetEnterpriseSession();
                iStore = (IInfoStore) es.getService("", INFOSTORE);
                factoryPS = (IReportSourceFactory) es.getService("PSReportFactory");
            }
            IReportSource reportSource = factoryPS.openReportSource((infoObject), Locale.ENGLISH);
                if (export.equalsIgnoreCase("rpt")) {
                    CrystalReportViewer viewer = new CrystalReportViewer();
                    viewer.setReportSourceClassFactoryName("com.crystaldecisions.sdk.occa.report.application.reportsourcefactory.PSReportSourceFactory");
                    viewer.setReportSource(reportSource);
                    viewer.setName("View Report");
                    returnInstance = viewer;
                } else if (PrepConstants.REPORT_EXPORT_FORMAT_CSV.equalsIgnoreCase(export) || PrepConstants.REPORT_EXPORT_FORMAT_PDF.equalsIgnoreCase(export)) {
                    fileHandle = reportExportHelper.exportReport(es, reportInstanceId, reportName, viewInstanceVob.getXmlFilePath(), iStore, export);
                } else {
                        log.debug("Unknow format");
                    returnInstance = null;
                }
        } catch (SDKException sdke) {
            log.error(sdke.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = sdke.getMessage();
            throw new PrepSystemException("error.report.sdk.viewInstance.exception", errorStrings);
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.viewInstance.exception", errorStrings);
        }
        viewInstanceVob.setInstanceObj(returnInstance);
        viewInstanceVob.setExportFileHandle(fileHandle);
            log.debug("End of viewInstance(String reportInstanceId, String export)");
        return viewInstanceVob;
    }
    
    public IInfoObjects getInfoObjects(String query) throws PrepSystemException {
        IInfoObjects infoObjects = null;
        IInfoStore iStore = null;
        log.debug("Start of getInfoObjects(String query)");
        try {
                log.debug("getInfoObjects()- Before gettting Istore");
            iStore = getIstore();
            if (iStore != null) {
                infoObjects = iStore.query(query);
                    log.debug("getInfoObjects()- After gettting Istore");
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
                String[] errorStrings= new String[1];
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
                String[] errorStrings= new String[1];
                errorStrings[0] = ie.getMessage();
                throw ie;
            }
        }
            log.debug("End of getIstore()");
        return iStore;
    }
 public PREPContext setReportArchivePagination(PREPContext inputContext)
        throws PrepFunctionalException, PrepSystemException {

        List<Object> inputValueObjectsForID = inputContext.getInputValueObjects();
        Report insReport = new Report();
        ReportInstancesList reportInstancesVOB = null;
        PREPContext outputContext = null;
        PREPContext outputContextNew = new PREPContext();
        if (inputValueObjectsForID != null && !inputValueObjectsForID.isEmpty()) {
            reportInstancesVOB = (ReportInstancesList) inputValueObjectsForID.iterator().next();
        }
        insReport.setReportName(reportInstancesVOB.getReportName());
        try {
            PREPContext inputContextNew = new PREPContext();
            inputContextNew.addInputValueObject(insReport);
            outputContext = getReportArchiveList(inputContextNew);
            Report report = null;
            List<Object> outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                report = (Report) outValueObjects.iterator().next();
            }
            int listsize = report.getStatusList().size();
            log.debug("Archive - listReturnedFrom:" + listsize);
            reportInstancesVOB.setNumberOfRecordsFound(listsize);
            if (listsize == 0) {// clear old list
                reportInstancesVOB.setSearchResults(report.getStatusList());
            }
            if (listsize != 0) {
                int fromIndex = reportInstancesVOB.getFromIndex() - 1;
                int toIndex = reportInstancesVOB.getToIndex() - 1;
               log.debug("From:" + fromIndex + "---To:" + toIndex);
                if (fromIndex >= listsize) {
                    fromIndex = listsize - 1;
                }
                if (toIndex > listsize) {
                    toIndex = listsize;
                }
                List<Object> sublist = new ArrayList<>();
                for (int i = fromIndex; i < toIndex; i++) {
                    sublist.add(report.getStatusList().get(i));
                }
                reportInstancesVOB.setSearchResults(sublist);
                reportInstancesVOB.setStatusList(sublist);
            }
            outputContextNew.addOutputValueObject(reportInstancesVOB);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (PrepFunctionalException pfe) {
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            String[] errorStrings= new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.basereport.pagination.exception", errorStrings);
        }
        return outputContextNew;
    }

    /**
     * @see com.ascap.apm.controller.reports.ReportControllerLocalInterface#getReportArchiveList(PREPContext)
     */
    public PREPContext getReportArchiveList(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getReportArchiveList method in ReportsHandlerImpl");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            Report reportVOB = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                reportVOB = (Report) inputValueObjects.iterator().next();
            }
            reportVOB = reportService.getReportArchiveList(reportVOB);
            outputContext.addOutputValueObject(reportVOB);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }
        log.debug("Exiting getReportArchiveList method in ReportsHandlerImpl");
        return outputContext;
    }
    /**
     * @see com.ascap.apm.controller.reports.ReportControllerLocalInterface#returnReportInstance(PREPContext)
     */
    public PREPContext returnReportInstance(PREPContext prepCtx)
            throws PrepSystemException, PrepFunctionalException {
            log.debug("Entering returnReportInstance method in ReportsHandlerImpl");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            ScheduleStatus instanceVOB = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                instanceVOB = (ScheduleStatus) inputValueObjects.iterator().next();
            }
            instanceVOB = reportService.returnReportInstance(instanceVOB);
            outputContext.addOutputValueObject(instanceVOB);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting returnReportInstance method in ReportsHandlerImpl");
        }
        return outputContext;
    }
    
    public PREPContext validateReport(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering validateReport method in ReportHandlerImpl");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            Report reportVOB = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                reportVOB = (Report) inputValueObjects.iterator().next();
            }
            reportVOB = reportService.validateReport(reportVOB);
            outputContext.addOutputValueObject(reportVOB);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }
        log.debug("Exiting validateReport method in ReportHandlerImpl");
        return outputContext;
    }

    @Override
    public PREPContext getReportRequestList(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getReportRequestList method in ReportHandlerImpl");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            Report report = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                report = (Report) inputValueObjects.iterator().next();
            }
            report = reportService.getReportRequestList(report);
            outputContext.addOutputValueObject(report);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }
        log.debug("Exiting getReportRequestList method in ReportHandlerImpl");
        return outputContext;

    }

    @Override
    public PREPContext getReportScheduleInfo(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getReportScheduleInfo method in getReportScheduleInfo method");
        PREPContext outputContext = new PREPContext();
        Report outReport = null;
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            Report report = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                report = (Report) inputValueObjects.iterator().next();
            }
            outReport = reportService.getReportScheduleInfo(report);
            outputContext.addOutputValueObject(outReport);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }
        log.debug("Exiting getReportScheduleInfo method in getReportScheduleInfo method");
        return outputContext;

    }

    @Override
    public PREPContext showInputParameters(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering showInputParameters method in ReportHandlerImpl");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getOutputValueObjects();
            Report reportVOB = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                reportVOB = (Report) inputValueObjects.iterator().next();
            }
            reportVOB = reportService.showInputParameters(reportVOB);
            outputContext.addOutputValueObject(reportVOB);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }
        log.debug("Exiting showInputParameters method in ReportHandlerImpl");
        return outputContext;
    }

    @Override
    public PREPContext executeReport(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering executeReport method in ReportHandlerImpl");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            Report reportVOB = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                reportVOB = (Report) inputValueObjects.iterator().next();
            }
            reportVOB = reportService.executeReport(reportVOB);
            outputContext.addOutputValueObject(reportVOB);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception", e);
        }
        log.debug("Exiting executeReport method in ReportHandlerImpl");
        return outputContext;
    }

    @Override
    public PREPContext updateReportRequestStatus(PREPContext prepCtx)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateReportRequestStatus method in ReportHandlerImpl");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            Report report = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                report = (Report) inputValueObjects.iterator().next();
            }
            reportService.updateReportRequestStatus(report);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }
        log.debug("Exiting updateReportRequestStatus method in ReportHandlerImpl");
        return outputContext;

    }
 public PREPContext getUserForInstance(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getUserForInstance method in ReportsHandlerImpl");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            ViewInstance viewInstanceVOB = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                viewInstanceVOB = (ViewInstance) inputValueObjects.iterator().next();
            }
            viewInstanceVOB = reportService.getUserForInstance(viewInstanceVOB);
            outputContext.addOutputValueObject(viewInstanceVOB);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }
            log.debug("Exiting getUserForInstance method in ReportsHandlerImpl");
        return outputContext;

    }
    
    /**
     * @see com.ascap.apm.controller.reports.ReportControllerLocalInterface#executeReport(PREPContext)
     */
    public PREPContext cancelReport(PREPContext prepCtx)
            throws PrepSystemException, PrepFunctionalException {
            log.debug("Entering cancelReport method in ReportHandlerImpl");

        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            ViewInstance viewInstanceVOB = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                viewInstanceVOB = (ViewInstance) inputValueObjects.iterator()
                        .next();
            }
            viewInstanceVOB = reportService.cancelReport(viewInstanceVOB);
            outputContext.addOutputValueObject(viewInstanceVOB);
        } catch (PrepSystemException pse) {
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }
            log.debug("Exiting cancelReport method in ReportHandlerImpl");
        return outputContext;
    }

}
