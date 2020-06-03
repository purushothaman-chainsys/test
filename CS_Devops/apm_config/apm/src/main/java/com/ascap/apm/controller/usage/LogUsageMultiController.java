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
import com.ascap.apm.vob.usage.LogUsageForm;
import com.ascap.apm.vob.usage.LogUsageSummary;

@Controller
public class LogUsageMultiController extends BaseUsageController {

    @Autowired
    private UsageHandler usageHandler;

    private static final String ERROR = "error";

    @RequestMapping(value = "/usage/logusage", method = { RequestMethod.GET, RequestMethod.POST })
    public String doSubmit(@ModelAttribute LogUsageForm logUsageForm , HttpServletRequest request,
        HttpServletResponse response , Model model)
            throws Exception {

        log.debug("Entering doSubmit of LogUsageMultiController");

        if (  logUsageForm.getOperationType() == null)
            log.debug("::null"); 
        else
            log.debug( "Operation Type" + logUsageForm.getOperationType());

        if("INSERT".equals(logUsageForm.getOperationType())) {
            return insertLogUsage(logUsageForm, model, request, response); 
        } 
        if("UPDATE".equals(logUsageForm.getOperationType())) {
            return updateLogUsage(logUsageForm, model, request, response); 
        } 
        if("DELETE".equals(logUsageForm.getOperationType())) {
            return updateLogUsage(logUsageForm, model, request, response); 
        } 
        if("ADDROW".equals(logUsageForm.getOperationType())) {
            return insertLogUsage(logUsageForm, model, request, response); 
        } 
        else { 
            log.debug("Exiting doSubmit of LogUsageMultiController forward: LOGREQUESTSUMMARY"); 
            return updateLogUsage(logUsageForm, model, request, response); 
        }	 
    }

    private String insertLogUsage(LogUsageForm logUsageForm, Model model,
        HttpServletRequest request , HttpServletResponse response) {

        log.debug("Entering insertLogUsage in LogUsageMultiController");

        String forwardKey = null;

        String filterSupplierCode = logUsageForm.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logUsageForm.getFiltertargetSurveyYearQuarter();
        String filterStaus = logUsageForm.getFilterStaus(); 
        int logRequestPg = logUsageForm.getLogRequestPg();
        String operationType = logUsageForm.getOperationType();  

        String headerid = logUsageForm.getHeaderid();
        String callLetter = logUsageForm.getCallLetter();
        String startDate = logUsageForm.getStartDate();
        String endDate = logUsageForm.getEndDate();
        String accountNumber = logUsageForm.getAccountNumber();

        log.debug("Headerid: "+headerid+" filterSupplierCode: "+filterSupplierCode + " filterTargetSurveyYearQuarter: "+ filterTargetSurveyYearQuarter 
            +" filterStaus: "+filterStaus + " operationType: " + operationType + " currentPg: " +logUsageForm.getCurrentPg()
            + " logRequestPg: " +logRequestPg);

        // pagination
        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference(); 
        int searchResultsPerPg = userPreferences.getNofSrchRsltsPerPage();
        logUsageForm.setSearchResultsPerPg(searchResultsPerPg);

        //Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        LogUsageSummary inputFormSummary = new LogUsageSummary();
        LogUsageSummary outputFormSummary = new LogUsageSummary();

        List<Object> outValueObjects =null;

        inputContext.addInputValueObject(inputFormSummary);
        try {
            BeanUtils.copyProperties(inputFormSummary, logUsageForm); 

            outputContext = usageHandler.insertLogUsageSummary(inputContext);

            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                outputFormSummary = (LogUsageSummary) outValueObjects.iterator().next();

                // copy summary object to form
                BeanUtils.copyProperties(logUsageForm, outputFormSummary); 

                // reset selections and set filters
                logUsageForm.setHeaderid(headerid);
                logUsageForm.setCallLetter(callLetter);
                logUsageForm.setStartDate(startDate);
                logUsageForm.setEndDate(endDate);
                logUsageForm.setAccountNumber(accountNumber); 

                logUsageForm.setFiltertargetSurveyYearQuarter(filterTargetSurveyYearQuarter);
                logUsageForm.setFilterSupplierCode(filterSupplierCode);
                logUsageForm.setFilterStaus(filterStaus);
                logUsageForm.setLogRequestPg(logRequestPg); 

                request.setAttribute("logUsageForm", logUsageForm);
            }			
            forwardKey = "usage/logUsageSummary";
        } 
        catch (PrepSystemException pse) {
            log.error("Error Caught in LogUsageMultiController  :" , pse);					
            model.addAttribute("systemerror", pse.getErrorKey());
            forwardKey = ERROR;
        } catch( PrepFunctionalException pfex){
            log.error("Functional Exception in LogUsageMultiController  :" + pfex);
            model.addAttribute("systemerror", pfex.getErrorKey());
            forwardKey= ERROR; 
        } catch (Exception ex) {
            log.error("Exception caught in LogUsageMultiController  :doSubmit() method", ex);
            model.addAttribute("systemerror", "system.error");
            forwardKey= ERROR;
        }

        if (operationType.equalsIgnoreCase("INSERT")) {  
            if(model.getAttribute("systemerror") != null) { 
                model.addAttribute("message", "message.apm.logusage.insert.success");
            }
        }
        log.debug("Exiting insertLogUsage in LogUsageMultiController");
        return forwardKey;
    }

    private String updateLogUsage(LogUsageForm logUsageForm, Model model,
        HttpServletRequest request , HttpServletResponse response) {

        String forwardKey = null;
        log.debug("Entering updateLogUsage in LogUsageMultiController");

        String[] selections = logUsageForm.getSelections(); 

        String filterSupplierCode = logUsageForm.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logUsageForm.getFiltertargetSurveyYearQuarter();
        String filterStaus = logUsageForm.getFilterStaus(); 
        int logRequestPg = logUsageForm.getLogRequestPg();
        String operationType = logUsageForm.getOperationType(); 

        String headerid = logUsageForm.getHeaderid(); 
        String callLetter = logUsageForm.getCallLetter();
        String startDate = logUsageForm.getStartDate();
        String endDate = logUsageForm.getEndDate();
        String accountNumber = logUsageForm.getAccountNumber();

        log.debug("Headerid: "+headerid+" filterSupplierCode: "+filterSupplierCode + " filterTargetSurveyYearQuarter: "+ filterTargetSurveyYearQuarter +" filterStaus: "+filterStaus 
            + " operationType: " + operationType+ " currentPg: " +String.valueOf(logRequestPg));

        // pagination
        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference(); 
        int searchResultsPerPg = userPreferences.getNofSrchRsltsPerPage();
        logUsageForm.setSearchResultsPerPg(searchResultsPerPg);


        //Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        LogUsageSummary inputFormSummary = new LogUsageSummary();
        LogUsageSummary outputFormSummary = new LogUsageSummary();

        List<Object> outValueObjects =null;

        inputContext.addInputValueObject(inputFormSummary);
        try {
            BeanUtils.copyProperties(inputFormSummary, logUsageForm); 

            outputContext = usageHandler.updateLogUsageSummary(inputContext);

            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                outputFormSummary = (LogUsageSummary) outValueObjects.iterator().next();

                // copy summary object to form
                BeanUtils.copyProperties(logUsageForm, outputFormSummary); 

                // reset selections and set filters
                logUsageForm.setHeaderid(headerid);
                logUsageForm.setCallLetter(callLetter);
                logUsageForm.setStartDate(startDate);
                logUsageForm.setEndDate(endDate);
                logUsageForm.setAccountNumber(accountNumber); 

                logUsageForm.setFiltertargetSurveyYearQuarter(filterTargetSurveyYearQuarter);
                logUsageForm.setFilterSupplierCode(filterSupplierCode);
                logUsageForm.setFilterStaus(filterStaus);
                logUsageForm.setLogRequestPg(logRequestPg);


                logUsageForm.setFirstPg(outputFormSummary.getFirstPg() );
                logUsageForm.setLastPg(outputFormSummary.getLastPg());
                logUsageForm.setTotalPg(outputFormSummary.getTotalPg());
                logUsageForm.setSelections(selections); 
                request.setAttribute("logUsageForm", logUsageForm);
            }			
            forwardKey = "usage/logUsageSummary";
        } 
        catch (PrepSystemException pse) {
            log.error("Error Caught in LogUsageMultiController  :" , pse);					
            model.addAttribute("systemerror", pse.getErrorKey());
            forwardKey = ERROR;
        } catch( PrepFunctionalException pfex){
            log.error("Functional Exception in LogUsageMultiController  :" + pfex);
            model.addAttribute("systemerror", pfex.getErrorKey());
            forwardKey= ERROR; 
        } catch (Exception ex) {
            log.error("Exception caught in LogUsageMultiController  :doWork() method", ex);
            model.addAttribute("systemerror", "systemerror");
            forwardKey= ERROR;
        } 

        if (operationType.equalsIgnoreCase("INSERT")) {  
            if(model.getAttribute("systemerror") != null) { 
                model.addAttribute("message", "message.apm.logusage.insert.success");
            }
        }
        if (operationType.equalsIgnoreCase("UPDATE")) {
            if(model.getAttribute("systemerror") != null) { 
                model.addAttribute("message", "message.apm.logrequest.update.success");
            }
        }

        if (operationType.equalsIgnoreCase("DELETE")) {
            if(model.getAttribute("systemerror") != null) { 
                model.addAttribute("message", "message.apm.logrequest.delete.success");
            }
        } 

        log.debug("Exiting updateLogUsage in LogUsageMultiController");

        return forwardKey;
    }
}
