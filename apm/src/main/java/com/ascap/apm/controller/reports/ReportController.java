package com.ascap.apm.controller.reports;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.context.UserSessionState;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.constants.Constants;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.database.reports.Connection;
import com.ascap.apm.handler.reports.ReportHandler;
import com.ascap.apm.vob.reports.Parameter;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportInstancesList;
import com.ascap.apm.vob.reports.ReportRequestList;
import com.ascap.apm.vob.reports.ScheduleInfo;
import com.ascap.apm.vob.reports.ScheduleStatus;
import com.ascap.apm.vob.reports.ViewInstance;
import com.crystaldecisions.report.web.viewer.CrystalReportViewer;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;

@Controller
@RequestMapping("/report")
@SessionAttributes("report")
public class ReportController extends BaseReportController   {

    private static final String REPORTERROR = "/reports/reportErrors";

    private static final String VIEWARCHIVES = "/reports/reportArchiveInstanceStatus";

    private static final String REPORTS_REPORTINSTANCESTATUS = "/reports/reportInstanceStatus";

    private static final String SYSTEM_ERROR = "systemerror";

    private static final String VIEWINSTANCEINFO = "/reports/viewInstanceInfo";

    private static final String REPORT_ERROR = "ap.report.error";

    private static final String INPUTPARAMETERS = "/reports/inputParameters";

    private static final String REPORTREQUESTLIST = "/reports/reportRequestList";

    private static final String DESCRIPTION = "description";

    private static final String INSTANCEID = "instanceId";

    private static final String REPORTNAME = "reportName";

    private static final String REPORT = "report";

    /**
     * Constructor for ReportController.
     */
    public ReportController() {
        super();
    }

    @ModelAttribute("report")
    public Report setReportForm() {
        return new Report();
    }

    @Autowired
    private ReportHandler reportHandler;

    @RequestMapping(value = "/apmreport")
    public ModelAndView reportDetails(@ModelAttribute("report") Report report, HttpServletRequest request,
        HttpServletResponse response, ModelAndView view) throws Exception {
        log.debug("Entering ReportController - reportDetails method");
        String doAction = request.getParameter("doAction");
        String viewName = "";

        if (doAction != null && doAction.equalsIgnoreCase(PrepConstants.REPORT_VIEW_REPORT_INSTANCE)) {
            viewName = getInstancesList(report, request, view);
        } else if (doAction != null && doAction.equalsIgnoreCase(PrepConstants.REPORT_VIEW)) {
            viewName = viewReport(report, request, response, view);
        } else if (doAction != null && doAction.equalsIgnoreCase(PrepConstants.REPORT_VIEW_ARCHIVES)) {
            viewName = getArchiveInstancesList(report, request, view);
        } else if (doAction != null && doAction.equalsIgnoreCase(PrepConstants.RETURN_REPORT)) {
            viewName = returnReport(report, request, view);
        } else if (doAction != null && doAction.equalsIgnoreCase(PrepConstants.REPORT_SHOWINPUTS)) {
            viewName = showInputParameters(report, request, response, view);
        } else if (doAction != null && doAction.equalsIgnoreCase(PrepConstants.REPORT_EXECUTE)) {
            viewName = validateReport(report, request, response, view);
        } else if (doAction != null && doAction.equalsIgnoreCase(PrepConstants.REPORT_CANCEL_INSTANCE)) {
            viewName = cancelReport(report, request, view, response);
        }
        view.setViewName(viewName);
        return view;
    }

    public String getInstancesList(Report reportForm, HttpServletRequest request, ModelAndView view)
        throws PrepFunctionalException, PrepSystemException {
        log.debug("Entering ReportController - getInstancesList method");
        String reportDesc = request.getParameter(DESCRIPTION);
        if (!"".equalsIgnoreCase(reportDesc) && reportDesc != null) {
            reportForm.setReportDesc(reportDesc);
        }
        List<Object> outputValueObjects = null;
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = new PREPContext();
        String forwardKey = "";
        ReportInstancesList reportInstancesVOB = null;
        Report insReport = new Report();
        insReport.setReportName(reportForm.getReportName());
        try {
            if ((reportForm.getBackToSearchResults() == null)
                && (reportForm.getNavigationType() == null || reportForm.getNavigationType().trim().equals(""))) {
                reportInstancesVOB = new ReportInstancesList();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                reportForm.setUserPreferences(userPreferences);
                reportForm.setIndex(0);
                BeanUtils.copyProperties(reportInstancesVOB, reportForm);
            } else {
                reportInstancesVOB = (ReportInstancesList) inputContext.getUserSessionState().getSearch();
                if (reportInstancesVOB != null) {
                    reportInstancesVOB.setNavigationType(reportForm.getNavigationType());
                    reportInstancesVOB.setReportName(reportForm.getReportName());
                    if (reportInstancesVOB.getUserPreferences() == null) {
                        UserPreference userPreferences =
                            getPREPContext(request).getUserSessionState().getUserPreference();
                        reportInstancesVOB.setUserPreferences(userPreferences);
                    }
                } else {
                    reportInstancesVOB = new ReportInstancesList();
                    reportInstancesVOB.setNavigationType(null);
                    reportInstancesVOB.setReportName(reportForm.getReportName());
                    UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                    reportInstancesVOB.setUserPreferences(userPreferences);
                }
            }
            inputContext.addInputValueObject(reportInstancesVOB);
            try {
                outputContext = reportHandler.setReportInstacePagination(inputContext);
            } catch (PrepFunctionalException pfe) {
                log.warn(pfe.getMessage());
                view.getModel().put(SYSTEMMESSAGE, pfe.getMessage());
            } catch (PrepSystemException e) {
                view.getModel().put(SYSTEM_ERROR, e.getMessage());
            }
            outputValueObjects = outputContext.getOutputValueObjects();
            if (outputValueObjects != null && !outputValueObjects.isEmpty()) {
                reportInstancesVOB = (ReportInstancesList) outputValueObjects.iterator().next();
            }
            reportInstancesVOB.setBaseFlag(true);
            BeanUtils.copyProperties(reportForm, reportInstancesVOB);
            reportInstancesVOB.setBaseFlag(false);
            List<Object> list = reportInstancesVOB.getSearchResults();
            reportForm.setStatusList(list);
            reportForm.setReportName(reportInstancesVOB.getReportName());
            reportInstancesVOB.setSearchResults(null);
            inputContext.getUserSessionState().setSearch(reportInstancesVOB);
            request.setAttribute(REPORT, reportForm);
            request.getSession().setAttribute(REPORT, reportForm);
            setUserSessionState(request, inputContext.getUserSessionState());
            view.getModel().put(REPORT, reportForm);
            reportForm.setParameters(null);
        } catch (Exception e) {
            log.error("Exception in ReportController-Instance list");
            view.getModel().put(SYSTEM_ERROR, e.getMessage());
        }
        forwardKey = REPORTS_REPORTINSTANCESTATUS;
        log.debug("Exiting ReportController - getInstancesList method");
        return forwardKey;
    }

    public String viewReport(Report report, HttpServletRequest request, HttpServletResponse response, ModelAndView view)
        throws Exception {

        log.debug("Entering ReportController - viewReport method");

        // ***** Create input and output contexts *****
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = new PREPContext();
        String forwardKey = null;

        String instanceId = request.getParameter(INSTANCEID);
        String export = request.getParameter("export");
        String reportName = report.getReportName();
        try {
            ViewInstance viewInstanceVOB = new ViewInstance();
            viewInstanceVOB.setReportID(instanceId);
            viewInstanceVOB.setReportName(reportName);
            if (export.equalsIgnoreCase("rpt")) {
                viewInstanceVOB.setExportType(PrepConstants.REPORT_EXPORT_FORMAT_RPT);
            } else {
                viewInstanceVOB.setExportType(PrepConstants.REPORT_EXPORT_FORMAT_CSV);
                String contextPath = request.getServletContext().getRealPath("/");
                int endPoint = contextPath.indexOf("webapp");
                String rootPath = contextPath.substring(0, endPoint);
                StringBuilder buffer = new StringBuilder(contextPath);
                int pos = 0;
                for (int i = 0; i < contextPath.length(); i++) {
                    pos = contextPath.indexOf('\\', pos);
                    if (pos >= 0) {
                        buffer.replace(pos, pos + 1, "/");
                    }
                    ++pos;
                }
                rootPath = buffer.substring(0, endPoint);
                String xmlPath = rootPath + "resources/reports/";
                viewInstanceVOB.setXmlFilePath(xmlPath);
            }
            inputContext.addInputValueObject(viewInstanceVOB);
            List<Object> outputValueObjects = null;
            // call the service
            try {
                viewInstanceVOB = reportHandler.viewInstance(viewInstanceVOB);
                outputContext.addOutputValueObject(viewInstanceVOB);
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                view.getModel().put(SYSTEMMESSAGE, dae.getMessage());
                forwardKey = REPORTERROR;
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                view.getModel().put(SYSTEM_ERROR, pse.getMessage());
                forwardKey = REPORTERROR;
            }
            if (!view.getModel().containsKey(SYSTEMMESSAGE) && !view.getModel().containsKey(SYSTEM_ERROR)) {
                outputValueObjects = outputContext.getOutputValueObjects();
                Object contextobject = null;
                if (outputValueObjects != null && !outputValueObjects.isEmpty()) {
                    contextobject = outputValueObjects.iterator().next();
                }
                ViewInstance viewInstanceVob = (ViewInstance) contextobject;
                Object instanceobject = viewInstanceVob.getInstanceObj();
                if (instanceobject instanceof CrystalReportViewer) {
                    CrystalReportViewer crsytalobj = (CrystalReportViewer) instanceobject;
                    IEnterpriseSession es = null;
                    try {
                        es = Connection.getEnterPriseSession();
                    } catch (Exception e) {
                        es = Connection.forceGetEnterpriseSession();
                    }
                    request.setAttribute("boEnterpriseSession", es);
                    request.setAttribute("boReportSource", crsytalobj.getReportSource());
                    request.setAttribute(INSTANCEID, instanceId);
                    request.setAttribute("id", instanceobject);
                    forwardKey = VIEWINSTANCEINFO;
                } else {

                    RandomAccessFile fileHandle = viewInstanceVob.getExportFileHandle();
                    FileChannel inChannel = fileHandle.getChannel();
                    try {
                        response.reset();
                        response.setContentType("text/plain");
                        response.setHeader("Content-disposition",
                            "attachment;filename=" + report.getReportName() + ".csv");
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        while (inChannel.read(buffer) > 0) {
                            buffer.flip();
                            for (int i = 0; i < buffer.limit(); i++) {
                                response.getWriter().write(buffer.get());
                            }
                            buffer.clear();
                        }
                        response.getWriter().flush();
                        response.getWriter().close();
                        response.flushBuffer();
                        forwardKey = null;
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    } finally {
                        if (fileHandle != null) {
                            fileHandle.setLength(0);
                            inChannel.close();
                            fileHandle.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error in viewReport method in ReportController" + e.getMessage());
            view.getModel().put(SYSTEM_ERROR, e.getMessage());
            forwardKey = REPORTERROR;
        }
        log.debug("Exiting ReportController - viewReport method " + forwardKey);
        return forwardKey;
    }

    public String getArchiveInstancesList(Report report, HttpServletRequest request, ModelAndView view)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering ReportController - getArchiveInstancesList method");
        // added for MR Issue 1676
        String reportDesc = request.getParameter(DESCRIPTION);
        // added for MR Issue 1676
        if (!"".equalsIgnoreCase(reportDesc) && reportDesc != null) {
            report.setReportDesc(reportDesc);
        }
        List<Object> outputValueObjects = null;
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = new PREPContext();
        ReportInstancesList reportInstancesVOB = null;
        Report insReport = new Report();
        insReport.setReportName(report.getReportName());
        try {
            // If it's a new search criteria, it creates a new SearchVOB and copies all the properties.
            // If it is not a new search ie., pagination then gets the SearchVOB from the session and set only
            // navigationType.
            if ((report.getBackToSearchResults() == null)
                && (report.getNavigationType() == null || report.getNavigationType().trim().equals(""))) {
                reportInstancesVOB = new ReportInstancesList();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                report.setUserPreferences(userPreferences);
                // need to clear index as to not carry page value from previous forms
                report.setIndex(0);
                BeanUtils.copyProperties(reportInstancesVOB, report);
            } else {
                reportInstancesVOB = (ReportInstancesList) inputContext.getUserSessionState().getSearch();
                if (reportInstancesVOB != null) {
                    reportInstancesVOB.setNavigationType(report.getNavigationType());
                    reportInstancesVOB.setReportName(report.getReportName());
                    if (reportInstancesVOB.getUserPreferences() == null) {
                        UserPreference userPreferences =
                            getPREPContext(request).getUserSessionState().getUserPreference();
                        reportInstancesVOB.setUserPreferences(userPreferences);
                    }
                } else {
                    reportInstancesVOB = new ReportInstancesList();
                    reportInstancesVOB.setNavigationType(null);
                    reportInstancesVOB.setReportName(report.getReportName());
                    UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                    reportInstancesVOB.setUserPreferences(userPreferences);
                }
            }
            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(reportInstancesVOB);
            try {
                outputContext = reportHandler.setReportArchivePagination(inputContext);
            } catch (PrepFunctionalException pfe) {
                log.warn(pfe.getMessage());
                view.getModel().put(SYSTEM_ERROR, getMessage(pfe.getErrorKey()));
            } catch (PrepSystemException e) {
                view.getModel().put(SYSTEM_ERROR, getMessage(e.getErrorKey()));
            }
            outputValueObjects = outputContext.getOutputValueObjects();
            if (outputValueObjects != null && !outputValueObjects.isEmpty()) {
                reportInstancesVOB = (ReportInstancesList) outputValueObjects.iterator().next();
            }

            if (reportInstancesVOB.getStatusList() != null) {
                log.debug("Report Archive list returned: " + reportInstancesVOB.getStatusList().size());
            }

            // Copy the the search results output to the Form, remove the search results collection from the searchVOb
            // and set it to the session.
            BeanUtils.copyProperties(report, reportInstancesVOB);
            List<Object> list = reportInstancesVOB.getSearchResults();
            report.setStatusList(list);
            report.setReportName(reportInstancesVOB.getReportName());
            // make the search results null
            reportInstancesVOB.setSearchResults(null);
            inputContext.getUserSessionState().setSearch(reportInstancesVOB);
            view.getModel().put(REPORT, report);
            request.getSession().setAttribute(REPORT, report);
            // set the UserSessionState objec to the session.
            setUserSessionState(request, inputContext.getUserSessionState());
            report.setParameters(null);
        } catch (Exception e) {
            log.error("Exception in ReportController-Instance list");
            view.getModel().put(SYSTEM_ERROR, getMessage(e.getMessage()));
        }
        log.debug("Exiting ReportController - getArchiveInstancesList method");

        return VIEWARCHIVES;
    }

    public String returnReport(Report report, HttpServletRequest request, ModelAndView view)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering returnReport method in ReportController");
        // ***** Create input and output contexts *****
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = new PREPContext();
        String instanceId = request.getParameter(INSTANCEID);
        String reportName = request.getParameter(REPORTNAME);

        try {
            ScheduleStatus instanceVob = new ScheduleStatus();
            instanceVob.setInstanceID(instanceId);
            instanceVob.setReportName(reportName);
            inputContext.addInputValueObject(instanceVob);
            try {
                outputContext = reportHandler.returnReportInstance(inputContext);
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                view.getModel().put(SYSTEMMESSAGE, getMessage(dae.getErrorKey()));
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                view.getModel().put(SYSTEM_ERROR, getMessage(pse.getErrorKey()));
            }
            if (!view.getModel().containsKey(SYSTEMERROR)) {
                List<Object> outputValueObjects = outputContext.getOutputValueObjects();
                Object object = null;
                if (outputValueObjects != null && !outputValueObjects.isEmpty()) {
                    object = outputValueObjects.iterator().next();
                }
                instanceVob = (ScheduleStatus) object;
                log.debug("ReportController - Returned End Date: " + instanceVob.getReturnedEndDate());
            }

        } catch (Exception e) {
            log.error("Error in returnReport method in ReportController" + e);
        }

        log.debug("Exiting returnReport method in ReportController");
        return getInstancesList(report, request, view);

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private String validateReport(Report reportForm, HttpServletRequest request, HttpServletResponse response,
        ModelAndView view) {
        // ***** Create input and output contexts *****
        log.debug("Entering ReportController - validateReport method");
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = new PREPContext();
        String forwardKey = "";
        try {
            ArrayList customReportList = new ArrayList(Constants.getCustomReportList());
            request.setAttribute("errors", null);
            Report reportVob = (Report) request.getSession().getAttribute(reportForm.getReportName());

            log.debug("Entering validateReport - method2 in ReportController");
            // method for doing form validations

            if (!customReportList.contains(reportForm.getReportName())) {
                reportForm.validate(request, messageSource);
            }
            // if Form validation is done proceed with Business Validation
            if (request.getAttribute("systemerrorlist") == null) {
                List<Parameter> list = reportForm.getParameters();

                if (customReportList.contains(reportForm.getReportName())) {

                    String requestListId = request.getParameter("requestListId");
                    String requestDesc = request.getParameter("requestDesc");

                    Parameter param = null;

                    if (requestListId != null && !"".equalsIgnoreCase(requestListId)) {
                        ArrayList<Parameter> requestListList = new ArrayList<>();

                        param = new Parameter();
                        param.setParameterType(PrepConstants.TYPE_STRING);
                        param.setInputValue(requestDesc);

                        requestListList.add(param);

                        param = new Parameter();
                        param.setParameterType(PrepConstants.TYPE_STRING);
                        param.setInputValue(requestListId);

                        requestListList.add(param);

                        list = requestListList;
                    }
                    reportForm.setParameters(list);
                    reportForm.validate(request, messageSource);
                    reportVob = new Report();
                    reportVob.setReportName(reportForm.getReportName());
                    reportVob.setParameters(list);
                    inputContext.addInputValueObject(reportVob);

                } else {

                    reportVob.setParameters(list);
                    inputContext.addInputValueObject(reportVob);

                }

                List<Object> outputValueObjects = null;
                try {
                    outputContext = reportHandler.validateReport(inputContext);
                } catch (PrepFunctionalException dae) {
                    log.warn(dae.getMessage());
                    view.getModel().put(SYSTEMMESSAGE, getMessage(dae.getErrorKey()));
                } catch (PrepSystemException pse) {
                    log.error(pse.getMessage());
                    view.getModel().put(SYSTEM_ERROR, getMessage(REPORT_ERROR, pse.getMessage()));
                }

                outputValueObjects = outputContext.getOutputValueObjects();
                Object object = null;
                if (outputValueObjects != null && !outputValueObjects.isEmpty()) {
                    object = outputValueObjects.iterator().next();
                }
                reportVob = (Report) object;
                List<Object> bErrors = reportVob.getBusinessErrors();
                if (bErrors == null || bErrors.isEmpty()) {
                    // EXECUTE THE REPORT ONLY IF THE BUSINESS ERRORS ARE EMPTY
                    return executeReport(reportForm, request, response, view);
                } else {

                    request.setAttribute("berrors", bErrors);

                    forwardKey = INPUTPARAMETERS;

                    log.debug("forwardKey = " + forwardKey);
                }
            }
            // Errors in form validation
            else {
                forwardKey = INPUTPARAMETERS;
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            view.getModel().put(SYSTEM_ERROR, getMessage(REPORT_ERROR, e.getMessage()));
            forwardKey = REPORTERROR;

        }
        log.debug("Exiting validateReport in ReportController - validate condition");
        request.getSession().setAttribute(REPORT, reportForm);
        view.getModel().put(REPORT, reportForm);
        return forwardKey;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public String showInputParameters(Report reportForm, HttpServletRequest request, HttpServletResponse response,
        ModelAndView view) throws Exception {
        // ***** Create input and output contexts *****
        log.debug("Entering showInputParameters method in ReportController");

        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = new PREPContext();
        String forwardKey = "";

        if (reportForm.getNavigationType() == null || "".equalsIgnoreCase(reportForm.getNavigationType())) {
            reportForm.setIndex(0);
            reportForm.setPage(1);
        }

        String reportID = request.getParameter("RID");
        String reportName = request.getParameter(REPORTNAME);
        String reportDesc = request.getParameter(DESCRIPTION);
        if (!"".equalsIgnoreCase(reportDesc) && reportDesc != null) {
            reportForm.setReportDesc(reportDesc);
        }
        if (!"".equalsIgnoreCase(reportID) && reportID != null) {
            reportForm.setReportID(reportID);
        }
        reportForm.setReportDesc(reportDesc);
        reportForm.setReportName(reportName);
        reportForm.setUserId(reportForm.getUserId());
        Report reportVob = new Report();
        BeanUtils.copyProperties(reportVob, reportForm);
        reportVob.setNavigationType(reportForm.getNavigationType());

        try {
            try {
                ArrayList customReportList = new ArrayList(Constants.getCustomReportList());
                inputContext.addInputValueObject(reportVob);
                // if custom report and LS-RP01
                if (customReportList.contains(reportName) && Constants.LRS_REPORT_01.equalsIgnoreCase(reportName)) {
                    outputContext = reportHandler.getReportRequestList(inputContext);
                } else {
                    outputContext = reportHandler.getReportScheduleInfo(inputContext);
                    outputContext = reportHandler.showInputParameters(outputContext);
                }

            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                view.getModel().put(SYSTEMMESSAGE, getMessage(REPORT_ERROR, dae.getMessage()));
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                view.getModel().put(SYSTEM_ERROR, getMessage(REPORT_ERROR, pse.getMessage()));
            } catch (Exception e) {
                log.error(e.getMessage());
                view.getModel().put(SYSTEM_ERROR, getMessage(REPORT_ERROR, e.getMessage()));
            }
            if (!view.getModel().containsKey(SYSTEM_ERROR)) {
                List<Object> outputValueObjects = outputContext.getOutputValueObjects();
                Object object = null;
                if (outputValueObjects != null && !outputValueObjects.isEmpty()) {
                    object = outputValueObjects.iterator().next();
                }

                ArrayList customReportList = new ArrayList(Constants.getCustomReportList());
                // if custom report and LR-RP01
                if (customReportList.contains(reportName) && Constants.LRS_REPORT_01.equalsIgnoreCase(reportName)) {

                    reportVob = (Report) object;

                    ReportRequestList reportRequestListForm = new ReportRequestList();

                    BeanUtils.copyProperties(reportRequestListForm, reportVob);
                    reportRequestListForm.setReportRequestList(new ArrayList(reportVob.getSearchResults()));

                    reportRequestListForm.setReportDesc(reportDesc);
                    reportRequestListForm.setReportName(reportName);

                    request.getSession().setAttribute("reportRequestListForm", reportRequestListForm);

                    forwardKey = REPORTREQUESTLIST;
                } else {
                    reportVob = (Report) object;
                    reportForm.setParameters(reportVob.getParameters());
                    reportForm.setReportName(reportVob.getReportName());
                    request.getSession().setAttribute(reportVob.getReportName(), reportVob);
                    reportForm.setReportID(reportVob.getReportID());
                    reportForm.setReportName(reportVob.getReportName());
                    request.getSession().setAttribute(REPORT, reportForm);
                    if (reportVob.getScheduleInfo().getScheduleNow() != null) {
                        if (!reportVob.getScheduleInfo().getScheduleNow().equalsIgnoreCase("T")) {
                            reportForm.setReportType("now");
                        } else {
                            reportForm.setReportType("later");
                        }
                    } else {
                        reportForm.setReportType("later");
                    }
                    forwardKey = INPUTPARAMETERS;
                }

            } else {
                forwardKey = REPORTERROR;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            view.getModel().put(SYSTEM_ERROR, getMessage(REPORT_ERROR, e.getMessage()));
            forwardKey = REPORTERROR;
        }
        log.debug("Exiting showInputParameters in ReportController");
        view.getModel().put(REPORT, reportForm);
        return forwardKey;
    }

    public String executeReport(Report reportForm, HttpServletRequest request, HttpServletResponse response,
        ModelAndView view) throws Exception {
        // ***** Create input and output contexts *****
        log.debug("Entering executeReport method in ReportController");
        PREPContext inputContext = getPREPContext(request);
        String forwardKey = null;
        reportForm.setNavigationType("");
        reportForm.setPage(1);
        reportForm.setIndex(0);
        log.debug("*******reportForm>> " + reportForm.getReportName());
        // ****code added to set userinfo for canceling pending instances***
        UserSessionState userSessionState = inputContext.getUserSessionState();
        String partyId = userSessionState.getId();
        String userId = userSessionState.getUserId();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String currentdate = sdf.format(new java.util.Date());
        try {
            reportForm.setStatusList(null);
            Report reportVob = (Report) request.getSession().getAttribute(reportForm.getReportName());
            List<Parameter> list = reportForm.getParameters();
            List<Object> customReportList = new ArrayList<>(Constants.getCustomReportList());
            if (reportVob == null && customReportList.contains(reportForm.getReportName())
                && Constants.LRS_REPORT_01.equalsIgnoreCase(reportForm.getReportName())) {

                reportVob = new Report();
                BeanUtils.copyProperties(reportVob, reportForm);
                ScheduleInfo si = new ScheduleInfo();
                si.setScheduleNow(PrepConstants.REPORT_SCHEDULE_NOW);
                reportVob.setScheduleInfo(si);
                reportVob.setReportName(reportForm.getReportName());
            }
            reportVob.setParameters(list);
            String executeNow = reportForm.getRadioValue();
            // ******code for cancelling the instance set in the input value object*****
            reportVob.setRequestedDate(currentdate);
            reportVob.setUserId(userId);
            if (partyId != null && !"".equals(partyId)) {
                reportVob.setPartyId(Long.parseLong(partyId));

            }
            // ***** code ended for cancelling the instance****
            log.debug("scheduleList created::" + reportVob.getScheduleInfo().getScheduleNow());
            // For Schedule Latter
            if (reportVob.getScheduleInfo().getScheduleNow().equalsIgnoreCase(PrepConstants.REPORT_SCHEDULE_LATTER)
                || "1".equalsIgnoreCase(executeNow)) {
                if (log.isDebugEnabled()) {
                    log.debug("scheduling the report for later execution");
                }
                reportVob.getScheduleInfo().setScheduleNow(PrepConstants.REPORT_SCHEDULE_LATTER);
                inputContext.addInputValueObject(reportVob);
                // call service to execute the Report Latter
                try {
                    reportHandler.executeReport(inputContext);
                } catch (PrepFunctionalException dae) {
                    log.warn(dae.getMessage());
                    view.getModel().put(SYSTEMMESSAGE, getMessage(REPORT_ERROR, dae.getMessage()));
                } catch (PrepSystemException pse) {
                    log.error(pse.getMessage());
                    view.getModel().put(SYSTEM_ERROR, getMessage(REPORT_ERROR, pse.getMessage()));
                }
            }
            // For schedule a report NOW
            else {
                log.debug("Scheduling  report to execute now ");
                try {
                    inputContext.addInputValueObject(reportVob);
                    reportHandler.executeReport(inputContext);
                } catch (PrepFunctionalException dae) {
                    log.warn(dae.getMessage());
                    view.getModel().put(SYSTEMMESSAGE, getMessage(REPORT_ERROR, dae.getMessage()));
                } catch (PrepSystemException pse) {
                    log.error(pse.getMessage());
                    view.getModel().put(SYSTEM_ERROR, getMessage(REPORT_ERROR, pse.getErrorKey()));
                }
            }

            // update custom report status
            if (customReportList.contains(reportForm.getReportName())
                && Constants.LRS_REPORT_01.equalsIgnoreCase(reportForm.getReportName())) {

                inputContext.addInputValueObject(reportVob);
                reportHandler.updateReportRequestStatus(inputContext);
            }

            if (!view.getModel().containsKey(SYSTEM_ERROR)) {
                try {
                    reportForm.setBackToSearchResults(null);
                    reportForm.setNavigationType(null);
                    return getInstancesList(reportForm, request, view);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    view.getModel().put(SYSTEM_ERROR, getMessage(REPORT_ERROR, e.getMessage()));
                    forwardKey = REPORTERROR;
                }
            } else {
                forwardKey = REPORTERROR;
            }
        } catch (Exception e) {
            log.error("Error in executeReport method in ReportController" + e.getMessage());
            view.getModel().put(SYSTEM_ERROR, getMessage(REPORT_ERROR, e.getMessage()));
            forwardKey = REPORTERROR;
        }
        log.debug("Exiting executeReport in ReportController");
        return forwardKey;
    }

    /**
     * Method cancelReport.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    private String cancelReport(Report report, HttpServletRequest request, ModelAndView view,
        HttpServletResponse response) throws PrepSystemException, PrepFunctionalException {
        // ***** Create input and output contexts *****
        log.debug("Entering cancelReport ");
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = new PREPContext();
        String forwardKey = null;
        String instanceId = request.getParameter(INSTANCEID);
        ViewInstance viewInstanceVOB = new ViewInstance();
        viewInstanceVOB.setReportID(instanceId);
        viewInstanceVOB.setReportName(report.getReportName());
        inputContext.addInputValueObject(viewInstanceVOB);
        UserSessionState userSessionState = inputContext.getUserSessionState();
        String userID = null;
        String currentuserID = userSessionState.getUserId();
        viewInstanceVOB.setUserID(currentuserID);
        Map<String, String> errors = new HashMap<>();
        /***** Create input and output contexts *****/
        String reportError = "reportError";
        try {
            outputContext = reportHandler.getUserForInstance(inputContext);
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            view.getModel().put(SYSTEMMESSAGE, getMessage(pfe.getErrorKey()));

        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            view.getModel().put(SYSTEM_ERROR, getMessage(pse.getErrorKey()));
        } catch (Exception e) {
            log.error("Error in execute method in ReportController");
            view.getModel().put(SYSTEM_ERROR, getMessage(e.getMessage()));
        }
        List<Object> outputValueObjectsFromDB = outputContext.getOutputValueObjects();
        if (outputValueObjectsFromDB != null && !outputValueObjectsFromDB.isEmpty()) {
            viewInstanceVOB = (ViewInstance) outputValueObjectsFromDB.iterator().next();
            userID = viewInstanceVOB.getUserID();
        }
        if (userID.equalsIgnoreCase(currentuserID)) {

            try {
                // call service to execute the Report Latter
                try {
                    reportHandler.cancelReport(inputContext);
                } catch (PrepFunctionalException dae) {
                    log.warn(dae.getMessage());
                    view.getModel().put(SYSTEMMESSAGE, getMessage(dae.getErrorKey()));
                } catch (PrepSystemException pse) {
                    view.getModel().put(SYSTEM_ERROR, getMessage(pse.getErrorKey()));
                } catch (Exception e) {
                    view.getModel().put(SYSTEM_ERROR, getMessage(e.getMessage()));
                }
                if (!view.getModel().containsKey(SYSTEMERROR)) {
                    log.debug("in ReportController after executing");
                    try {
                        report.setBackToSearchResults(null);
                        report.setNavigationType(null);
                        return getInstancesList(report, request, view);
                    } catch (PrepFunctionalException dae) {
                        log.warn(dae.getMessage());
                        view.getModel().put(SYSTEMMESSAGE, getMessage(dae.getErrorKey()));
                        forwardKey = reportError;
                    } catch (PrepSystemException pse) {
                        log.error(pse.getMessage());
                        view.getModel().put(SYSTEM_ERROR, getMessage(pse.getErrorKey()));
                        forwardKey = reportError;
                    }
                } else {
                    forwardKey = reportError;
                }
            } catch (Exception e) {
                log.error("Error in executeReport method in ReportController");
                view.getModel().put(SYSTEM_ERROR, getMessage(e.getMessage()));
                forwardKey = reportError;
            }
        } else {
            errors.put("cancel.error", getMessage("cancel.error") + currentuserID);
            request.setAttribute("errors", errors);
            report.setBackToSearchResults(null);
            report.setNavigationType(null);
            try {
                return getInstancesList(report, request, view);
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                view.getModel().put(SYSTEM_ERROR, getMessage(dae.getErrorKey()));
                forwardKey = reportError;
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                view.getModel().put(SYSTEM_ERROR, getMessage(pse.getErrorKey()));
                forwardKey = reportError;
            } catch (Exception e) {
                log.error("Exception in cancelReport method in ReportController");
                view.getModel().put(SYSTEM_ERROR, getMessage(e.getMessage()));
                forwardKey = reportError;
            }
        }
        log.debug("Exiting CancelReport in ReportController ");
        return forwardKey;
    }
}
