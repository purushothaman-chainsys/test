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
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.LogUsageSummary;

@Controller
@RequestMapping("/usage")
public class LogUsageMultiController extends BaseUsageController {

    private static final String INSERT = "INSERT";

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/logusage", method = {RequestMethod.GET, RequestMethod.POST})
    public String doSubmit(@ModelAttribute LogUsageSummary logUsage, HttpServletRequest request,
        HttpServletResponse response, Model model) throws Exception {

        log.debug("Entering doSubmit of LogUsageMultiController");

        if (logUsage.getOperationType() == null)
            log.debug("::null");
        else
            log.debug("Operation Type" + logUsage.getOperationType());

        if (INSERT.equals(logUsage.getOperationType())) {
            return insertLogUsage(logUsage, model, request);
        }
        if ("UPDATE".equals(logUsage.getOperationType())) {
            return updateLogUsage(logUsage, model, request);
        }
        if ("DELETE".equals(logUsage.getOperationType())) {
            return updateLogUsage(logUsage, model, request);
        }
        if ("ADDROW".equals(logUsage.getOperationType())) {
            return insertLogUsage(logUsage, model, request);
        } else {
            log.debug("Exiting doSubmit of LogUsageMultiController forward: LOGREQUESTSUMMARY");
            return updateLogUsage(logUsage, model, request);
        }
    }

    private String insertLogUsage(LogUsageSummary logUsage, Model model, HttpServletRequest request) {

        log.debug("Entering insertLogUsage in LogUsageMultiController");

        String forwardKey = null;

        String filterSupplierCode = logUsage.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logUsage.getFiltertargetSurveyYearQuarter();
        String filterStaus = logUsage.getFilterStaus();
        int logRequestPg = logUsage.getLogRequestPg();
        String operationType = logUsage.getOperationType();
        String headerid = logUsage.getHeaderid();
        String callLetter = logUsage.getCallLetter();
        String startDate = logUsage.getStartDate();
        String endDate = logUsage.getEndDate();
        String accountNumber = logUsage.getAccountNumber();

        log.debug(
            "Headerid: " + headerid + " filterSupplierCode: " + filterSupplierCode + " filterTargetSurveyYearQuarter: "
                + filterTargetSurveyYearQuarter + " filterStaus: " + filterStaus + " operationType: " + operationType
                + " currentPg: " + logUsage.getCurrentPg() + " logRequestPg: " + logRequestPg);

        // pagination
        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
        int searchResultsPerPg = userPreferences.getNofSrchRsltsPerPage();
        logUsage.setSearchResultsPerPg(searchResultsPerPg);

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        LogUsageSummary inputFormSummary = new LogUsageSummary();
        LogUsageSummary outputFormSummary = new LogUsageSummary();

        List<Object> outValueObjects = null;
        inputContext.addInputValueObject(inputFormSummary);
        try {
            BeanUtils.copyProperties(inputFormSummary, logUsage);
            outputContext = usageHandler.insertLogUsageSummary(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outputFormSummary = (LogUsageSummary) outValueObjects.iterator().next();

                // copy summary object to form
                BeanUtils.copyProperties(logUsage, outputFormSummary);

                // reset selections and set filters
                logUsage.setHeaderid(headerid);
                logUsage.setCallLetter(callLetter);
                logUsage.setStartDate(startDate);
                logUsage.setEndDate(endDate);
                logUsage.setAccountNumber(accountNumber);
                logUsage.setFiltertargetSurveyYearQuarter(filterTargetSurveyYearQuarter);
                logUsage.setFilterSupplierCode(filterSupplierCode);
                logUsage.setFilterStaus(filterStaus);
                logUsage.setLogRequestPg(logRequestPg);
                model.addAttribute("logUsage", logUsage);
            }
            forwardKey = "usage/logUsageSummary";
        } catch (PrepSystemException pse) {
            log.error("Error Caught in LogUsageMultiController  :", pse);
            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (PrepFunctionalException pfex) {
            log.error("Functional Exception in LogUsageMultiController  :" + pfex);
            model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (Exception ex) {
            log.error("Exception caught in LogUsageMultiController  :doSubmit() method", ex);
            model.addAttribute(SYSTEMERROR, getMessage("system.error"));
            forwardKey = ERRORPAGE;
        }
        if (operationType.equalsIgnoreCase(INSERT) && !model.containsAttribute(SYSTEMERROR)) {
            model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.logusage.insert.success"));
        }
        log.debug("Exiting insertLogUsage in LogUsageMultiController");
        return forwardKey;
    }

    private String updateLogUsage(LogUsageSummary logUsage, Model model, HttpServletRequest request) {

        String forwardKey = null;
        log.debug("Entering updateLogUsage in LogUsageMultiController");

        String[] selections = logUsage.getSelections();
        String filterSupplierCode = logUsage.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logUsage.getFiltertargetSurveyYearQuarter();
        String filterStaus = logUsage.getFilterStaus();
        int logRequestPg = logUsage.getLogRequestPg();
        String operationType = logUsage.getOperationType();
        String headerid = logUsage.getHeaderid();
        String callLetter = logUsage.getCallLetter();
        String startDate = logUsage.getStartDate();
        String endDate = logUsage.getEndDate();
        String accountNumber = logUsage.getAccountNumber();

        log.debug("Headerid: " + headerid + " filterSupplierCode: " + filterSupplierCode
            + " filterTargetSurveyYearQuarter: " + filterTargetSurveyYearQuarter + " filterStaus: " + filterStaus
            + " operationType: " + operationType + " currentPg: " + logRequestPg);

        // pagination
        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
        int searchResultsPerPg = userPreferences.getNofSrchRsltsPerPage();
        logUsage.setSearchResultsPerPg(searchResultsPerPg);

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        LogUsageSummary inputFormSummary = new LogUsageSummary();
        LogUsageSummary outputFormSummary = new LogUsageSummary();

        List<Object> outValueObjects = null;
        inputContext.addInputValueObject(inputFormSummary);
        try {
            BeanUtils.copyProperties(inputFormSummary, logUsage);
            outputContext = usageHandler.updateLogUsageSummary(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outputFormSummary = (LogUsageSummary) outValueObjects.iterator().next();

                // copy summary object to form
                BeanUtils.copyProperties(logUsage, outputFormSummary);

                // reset selections and set filters
                logUsage.setHeaderid(headerid);
                logUsage.setCallLetter(callLetter);
                logUsage.setStartDate(startDate);
                logUsage.setEndDate(endDate);
                logUsage.setAccountNumber(accountNumber);
                logUsage.setFiltertargetSurveyYearQuarter(filterTargetSurveyYearQuarter);
                logUsage.setFilterSupplierCode(filterSupplierCode);
                logUsage.setFilterStaus(filterStaus);
                logUsage.setLogRequestPg(logRequestPg);
                logUsage.setFirstPg(outputFormSummary.getFirstPg());
                logUsage.setLastPg(outputFormSummary.getLastPg());
                logUsage.setTotalPg(outputFormSummary.getTotalPg());
                logUsage.setSelections(selections);
                model.addAttribute("logUsage", logUsage);
            }
            forwardKey = "usage/logUsageSummary";
        } catch (PrepSystemException pse) {
            log.error("Error Caught in LogUsageMultiController  :", pse);
            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (PrepFunctionalException pfex) {
            log.error("Functional Exception in LogUsageMultiController  :" + pfex);
            model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (Exception ex) {
            log.error("Exception caught in LogUsageMultiController  :doSubmit() method", ex);
            model.addAttribute(SYSTEMERROR, getMessage(SYSTEMERROR));
            forwardKey = ERRORPAGE;
        }

        if (operationType.equalsIgnoreCase(INSERT) && !model.containsAttribute(SYSTEMERROR)) {
            model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.logusage.insert.success"));
        }
        if (operationType.equalsIgnoreCase("UPDATE") && !model.containsAttribute(SYSTEMERROR)) {
            model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.logrequest.update.success"));
        }

        if (operationType.equalsIgnoreCase("DELETE") && !model.containsAttribute(SYSTEMERROR)) {
            model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.logrequest.delete.success"));
        }
        log.debug("Exiting updateLogUsage in LogUsageMultiController");
        return forwardKey;
    }
}
