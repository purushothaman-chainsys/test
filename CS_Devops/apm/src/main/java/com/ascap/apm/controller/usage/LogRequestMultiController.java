package com.ascap.apm.controller.usage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.LogRequestSummary;
import com.ascap.apm.vob.usage.LogUsageSummary;

@Controller
@RequestMapping("/usage")
public class LogRequestMultiController extends BaseUsageController {

    private static final String SELECTEDLOGREQUESTS = "SelectedLogRequests: ";

    private static final String FILTERSUPPLIERCODE = "filterSupplierCode: ";

    private static final String SYSTEM_ERROR = "system.error";

    private static final String FILTERTARGETSURVEYYEARQUARTER = " filterTargetSurveyYearQuarter: ";

    private static final String FILTERSTAUS = " filterStaus: ";

    private static final String OPERATIONTYPE = " operationType";

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/logrequestSummary", method = {RequestMethod.GET, RequestMethod.POST})
    public String doSubmit(@ModelAttribute LogRequestSummary logRequestSummary, HttpServletRequest request,
        HttpServletResponse response, Model model) throws Exception {

        log.debug("Entering doSubmit of LogRequestMultiController");

        if (logRequestSummary.getOperationType() == null)
            log.debug("::null");

        if ("UPDATE".equals(logRequestSummary.getOperationType())) {
            return updateLogRequestSummary(logRequestSummary, model, request);
        }

        if ("DELETE".equals(logRequestSummary.getOperationType())) {
            return updateLogRequestSummary(logRequestSummary, model, request);
        }

        if ("RELEASE".equals(logRequestSummary.getOperationType())) {
            return updateLogRequestSummary(logRequestSummary, model, request);
        }

        if ("USAGE".equals(logRequestSummary.getOperationType())) {
            return goToUsage(logRequestSummary, model, request);
        } else {
            log.debug("Exiting doSubmit of LogRequestMultiController forward: LOGREQUESTSUMMARY");
            return getLogRequestSummary(logRequestSummary, model, request);
        }
    }

    private String getLogRequestSummary(LogRequestSummary logRequestSummary, Model model, HttpServletRequest request) {

        String forwardKey = null;
        String[] selectedIds = logRequestSummary.getSelectedLogRequest();
        if (selectedIds != null && selectedIds.length > 0) {
            for (String s : selectedIds) {
                log.debug(SELECTEDLOGREQUESTS + s);
            }
        }
        String filterSupplierCode = logRequestSummary.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logRequestSummary.getFiltertargetSurveyYearQuarter();
        String filterStaus = logRequestSummary.getFilterStaus();
        String operationtype = logRequestSummary.getOperationType();
        log.debug(FILTERSUPPLIERCODE + filterSupplierCode + "filterTargetSurveyYearQuarter: "
            + filterTargetSurveyYearQuarter + "filterStaus: " + filterStaus + " operationtype: " + operationtype);
        log.debug("currentPg: " + logRequestSummary.getCurrentPg() + " totalPg: " + logRequestSummary.getTotalPg());
        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
        int searchResultsPerPg = userPreferences.getNofSrchRsltsPerPage();
        logRequestSummary.setSearchResultsPerPg(searchResultsPerPg);
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        LogRequestSummary inputFormSummary = new LogRequestSummary();
        LogRequestSummary outputFormSummary = new LogRequestSummary();
        List<Object> outValueObjects = null;
        inputContext.addInputValueObject(inputFormSummary);
        try {
            BeanUtils.copyProperties(inputFormSummary, logRequestSummary);
            outputContext = usageHandler.getLogRequestSummary(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outputFormSummary = (LogRequestSummary) outValueObjects.iterator().next();
                // copy summary object to form
                BeanUtils.copyProperties(logRequestSummary, outputFormSummary);
                // reset selections and set filters
                logRequestSummary.setSelectedLogRequest(null);
                model.addAttribute("logRequestSummary", logRequestSummary);
            }
            forwardKey = "usage/logRequestSummary";
        } catch (PrepSystemException pse) {
            log.error("Error Caught in LogRequestMultiFunction  :", pse);
            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (PrepFunctionalException pfex) {
            log.error("Functional Exception in LogRequestMultiFunction  :" + pfex);
            model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (Exception ex) {
            log.error("Exception caught in LogRequestMultiFunction  :doSubmit() method", ex);
            model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = ERRORPAGE;
        }

        log.debug("Exiting getLogRequestSummary in LogRequestMultiController");
        return forwardKey;
    }

    private String updateLogRequestSummary(LogRequestSummary logRequestSummary, Model model,
        HttpServletRequest request) {

        log.debug("Entering updateLogRequestSummary in LogRequestMultiController");

        String forwardKey = null;

        String[] selectedIds = logRequestSummary.getSelectedLogRequest();

        if (selectedIds != null && selectedIds.length > 0) {
            for (String s : selectedIds) {
                log.debug(SELECTEDLOGREQUESTS + s);
            }
        }

        String filterSupplierCode = logRequestSummary.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logRequestSummary.getFiltertargetSurveyYearQuarter();
        String filterStaus = logRequestSummary.getFilterStaus();
        String operationType = logRequestSummary.getOperationType();

        log.debug(FILTERSUPPLIERCODE + filterSupplierCode + FILTERTARGETSURVEYYEARQUARTER
            + filterTargetSurveyYearQuarter + FILTERSTAUS + filterStaus + OPERATIONTYPE + operationType);

        // pagination
        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
        int searchResultsPerPg = userPreferences.getNofSrchRsltsPerPage();
        logRequestSummary.setSearchResultsPerPg(searchResultsPerPg);

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        LogRequestSummary inputFormSummary = new LogRequestSummary();
        LogRequestSummary outputFormSummary = new LogRequestSummary();

        List<Object> outValueObjects = null;

        inputContext.addInputValueObject(inputFormSummary);
        try {
            BeanUtils.copyProperties(inputFormSummary, logRequestSummary);

            outputContext = usageHandler.updateLogRequestSummary(inputContext);

            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outputFormSummary = (LogRequestSummary) outValueObjects.iterator().next();

                // copy summary object to form
                BeanUtils.copyProperties(logRequestSummary, outputFormSummary);

                // reset selections and set filters
                logRequestSummary.setSelectedLogRequest(null);
                model.addAttribute("logRequestSummary", logRequestSummary);
            }
            forwardKey = "usage/logRequestSummary";
        } catch (PrepSystemException pse) {
            log.error("Error Caught in LogRequestMultiFunction  :", pse);
            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (PrepFunctionalException pfex) {
            log.error("Functional Exception in LogRequestMultiFunction  :" + pfex);
            model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (Exception ex) {
            log.error("Exception caught in LogRequestMultiFunction  :updateLogRequestSummary() method", ex);
            model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = ERRORPAGE;
        }

        log.debug("Return to UI, filterSupplierCode: " + filterSupplierCode + FILTERTARGETSURVEYYEARQUARTER
            + filterTargetSurveyYearQuarter + FILTERSTAUS + filterStaus + OPERATIONTYPE + operationType);

        if (operationType.equalsIgnoreCase("UPDATE")) {
            log.debug("UPDATE message");
            if (model.getAttribute(SYSTEMERROR) == null) {
                log.debug("UPDATE message, error empty");
                model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.logrequest.update.success"));
            }
        }
        if (operationType.equalsIgnoreCase("RELEASE")) {
            log.debug("RELEASE message");
            if (model.getAttribute(SYSTEMERROR) == null) {
                log.debug("RELEASE message, error empty");
                model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.logrequest.release.success"));
            }
        }
        if (operationType.equalsIgnoreCase("DELETE")) {
            log.debug("DELETE message");
            if (model.getAttribute(SYSTEMERROR) == null) {
                log.debug("DELETE message, error empty");
                model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.logrequest.delete.success"));
            }
        }
        log.debug("Exiting updateLogRequestSummary in LogRequestMultiController");
        return forwardKey;
    }

    private String goToUsage(LogRequestSummary logRequestSummary, Model model, HttpServletRequest request) {

        log.debug("Entering goToUsage in LogRequestMultiController");

        String forwardKey = null;

        String[] selectedIds = logRequestSummary.getSelectedLogRequest();

        if (selectedIds != null && selectedIds.length > 0) {
            for (String s : selectedIds) {
                log.debug(SELECTEDLOGREQUESTS + s);
            }
        }

        String filterSupplierCode = logRequestSummary.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logRequestSummary.getFiltertargetSurveyYearQuarter();
        String filterStaus = logRequestSummary.getFilterStaus();
        String operationType = logRequestSummary.getOperationType();

        log.debug(FILTERSUPPLIERCODE + filterSupplierCode + FILTERTARGETSURVEYYEARQUARTER
            + filterTargetSurveyYearQuarter + FILTERSTAUS + filterStaus + OPERATIONTYPE + operationType);

        // pagination
        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
        int searchResultsPerPg = userPreferences.getNofSrchRsltsPerPage();
        logRequestSummary.setSearchResultsPerPg(searchResultsPerPg);

        String[] selections = logRequestSummary.getSelections();

        String[] tokens = null;
        String headerid = null;

        if (selections != null && selections.length > 0) {
            int i = 0;

            for (String index : selections) {

                if (ValidationUtils.isEmptyOrNull(index)) {
                    continue;
                }
                i++;

                log.debug("selectedStrs: " + i + " element: " + index);
                tokens = index.split("##", -1);
                log.debug("logReqId: " + tokens[0]);

                headerid = tokens[0];

                if (null != tokens[1])
                    log.debug("call letter: " + tokens[1]);

                if (null != tokens[1])
                    log.debug("start date: " + tokens[2]);

                if (null != tokens[2])
                    log.debug("end date: " + tokens[3]);

                if (null != tokens[3])
                    log.debug("account Number: " + tokens[4]);
            }
        }

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        LogUsageSummary inputFormSummary = new LogUsageSummary();
        LogUsageSummary outputFormSummary = new LogUsageSummary();

        List<Object> outValueObjects = null;

        inputContext.addInputValueObject(inputFormSummary);
        try {
            BeanUtils.copyProperties(inputFormSummary, logRequestSummary);

            outputContext = usageHandler.getLogUsageSummary(inputContext);

            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outputFormSummary = (LogUsageSummary) outValueObjects.iterator().next();

                // copy summary object to form
                LogUsageSummary f = new LogUsageSummary();
                BeanUtils.copyProperties(f, outputFormSummary);

                f.setHeaderid(headerid);
                f.setFiltertargetSurveyYearQuarter(filterTargetSurveyYearQuarter);
                f.setFilterSupplierCode(filterSupplierCode);
                f.setFilterStaus(filterStaus);
                f.setLogRequestPg(logRequestSummary.getCurrentPg());

                f.setFirstPg(outputFormSummary.getFirstPg());
                f.setLastPg(outputFormSummary.getLastPg());
                f.setTotalPg(outputFormSummary.getTotalPg());

                // reset selections and set filters
                log.debug("setting up values for form now: ");
                model.addAttribute("logUsage", f);
            }
            forwardKey = "usage/logUsageSummary";
        } catch (PrepSystemException pse) {
            log.error("Error Caught in LogRequestMultiController  :", pse);
            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (PrepFunctionalException pfex) {
            log.error("Functional Exception in LogRequestMultiController  :" + pfex);
            model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (Exception ex) {
            log.error("Exception caught in LogRequestMultiController  :doSumbit() method", ex);
            model.addAttribute(SYSTEM_ERROR, getMessage(SYSTEM_ERROR));
            forwardKey = ERRORPAGE;
        }
        log.debug("Exiting goToUsage in LogRequestMultiController");
        return forwardKey;
    }
}
