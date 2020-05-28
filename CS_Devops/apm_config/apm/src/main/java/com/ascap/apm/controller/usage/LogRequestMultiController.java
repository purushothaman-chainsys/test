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
import com.ascap.apm.vob.usage.LogRequestSummaryForm;
import com.ascap.apm.vob.usage.LogUsageForm;
import com.ascap.apm.vob.usage.LogUsageSummary;

@Controller
public class LogRequestMultiController extends BaseUsageController {

    private static final String ERROR = "error";
    private static final String MESSAGE = "message";

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/usage/logrequestSummary", method = { RequestMethod.GET, RequestMethod.POST })
    public String doSubmit(@ModelAttribute LogRequestSummaryForm logRequestSummaryForm , HttpServletRequest request,
        HttpServletResponse response , Model model)
            throws Exception {

        log.debug("Entering doSubmit of LogRequestMultiController");

        if (  logRequestSummaryForm.getOperationType() == null)
            log.debug("::null"); 

        if("UPDATE".equals(logRequestSummaryForm.getOperationType())) {
            return updateLogRequestSummary(logRequestSummaryForm ,model , request, response); 
        } 

        if("DELETE".equals(logRequestSummaryForm.getOperationType())) {
            return updateLogRequestSummary(logRequestSummaryForm , model , request, response); 
        } 

        if("RELEASE".equals(logRequestSummaryForm.getOperationType())) {
            return updateLogRequestSummary(logRequestSummaryForm , model , request, response); 
        } 

        if("USAGE".equals(logRequestSummaryForm.getOperationType())) {
            return goToUsage(logRequestSummaryForm , model , request, response);
        } 
        else { 
            log.debug("Exiting doSubmit of LogRequestMultiController forward: LOGREQUESTSUMMARY"); 
            return getLogRequestSummary(logRequestSummaryForm , model , request, response); 
        }	
    }

    private String getLogRequestSummary(LogRequestSummaryForm logRequestSummaryForm , Model model , HttpServletRequest request,
        HttpServletResponse response) {

        String forwardKey = null;

        String[] selectedIds = logRequestSummaryForm.getSelectedLogRequest();

        if (selectedIds != null && selectedIds.length > 0 ) {
            for (String s: selectedIds) {   
                log.debug("SelectedLogRequests: "+s);
            }
        }

        String filterSupplierCode = logRequestSummaryForm.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logRequestSummaryForm.getFiltertargetSurveyYearQuarter();
        String filterStaus = logRequestSummaryForm.getFilterStaus(); 
        String operationtype = logRequestSummaryForm.getOperationType();

        log.debug("filterSupplierCode: "+filterSupplierCode + "filterTargetSurveyYearQuarter: "+filterTargetSurveyYearQuarter +"filterStaus: "+filterStaus+" operationtype: "+operationtype);
        log.debug("currentPg: "+logRequestSummaryForm.getCurrentPg()+" totalPg: "+logRequestSummaryForm.getTotalPg());

        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference(); 
        int searchResultsPerPg = userPreferences.getNofSrchRsltsPerPage();
        logRequestSummaryForm.setSearchResultsPerPg(searchResultsPerPg);

        //Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        LogRequestSummary inputFormSummary = new LogRequestSummary();
        LogRequestSummary outputFormSummary = new LogRequestSummary();

        List<Object> outValueObjects =null;

        inputContext.addInputValueObject(inputFormSummary);
        try {
            BeanUtils.copyProperties(inputFormSummary, logRequestSummaryForm); 

            outputContext = usageHandler.getLogRequestSummary(inputContext);

            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                outputFormSummary = (LogRequestSummary) outValueObjects.iterator().next();

                // copy summary object to form
                BeanUtils.copyProperties(logRequestSummaryForm, outputFormSummary); 

                // reset selections and set filters 
                logRequestSummaryForm.setSelectedLogRequest(null); 

                request.setAttribute("logRequestSummaryForm", logRequestSummaryForm);
            }			
            forwardKey = "usage/logRequestSummary";
        } 
        catch (PrepSystemException pse) {
            log.error("Error Caught in LogRequestMultiFunction  :" , pse);					
            model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
            forwardKey = ERROR;
        } catch( PrepFunctionalException pfex){
            log.error("Functional Exception in LogRequestMultiFunction  :" + pfex);
            model.addAttribute("systemerror", getMessage(pfex.getErrorKey()));
            forwardKey= ERROR; 
        } catch (Exception ex) {
            log.error("Exception caught in LogRequestMultiFunction  :doSubmit() method", ex);
            model.addAttribute("systemerror", getMessage("system.error"));
            forwardKey= ERROR;
        } 

        log.debug("Exiting getLogRequestSummary in LogRequestMultiController");
        return forwardKey;
    }

    private String  updateLogRequestSummary(LogRequestSummaryForm logRequestSummaryForm , Model model , HttpServletRequest request,
        HttpServletResponse response) {

        log.debug("Entering updateLogRequestSummary in LogRequestMultiController");

        String forwardKey = null;

        String[] selectedIds = logRequestSummaryForm.getSelectedLogRequest();

        if (selectedIds != null && selectedIds.length > 0 ) {
            for (String s: selectedIds) {   
                log.debug("SelectedLogRequests: "+s);
            }
        }

        String filterSupplierCode = logRequestSummaryForm.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logRequestSummaryForm.getFiltertargetSurveyYearQuarter();
        String filterStaus = logRequestSummaryForm.getFilterStaus(); 
        String operationType = logRequestSummaryForm.getOperationType();

        log.debug("filterSupplierCode: "+filterSupplierCode + " filterTargetSurveyYearQuarter: "+ filterTargetSurveyYearQuarter +" filterStaus: "+filterStaus + " operationType" + operationType);

        // pagination
        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference(); 
        int searchResultsPerPg = userPreferences.getNofSrchRsltsPerPage();
        logRequestSummaryForm.setSearchResultsPerPg(searchResultsPerPg);

        //Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        LogRequestSummary inputFormSummary = new LogRequestSummary();
        LogRequestSummary outputFormSummary = new LogRequestSummary();


        List<Object> outValueObjects =null;

        inputContext.addInputValueObject(inputFormSummary);
        try {
            BeanUtils.copyProperties(inputFormSummary, logRequestSummaryForm); 

            outputContext = usageHandler.updateLogRequestSummary(inputContext);

            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                outputFormSummary = (LogRequestSummary) outValueObjects.iterator().next();

                // copy summary object to form
                BeanUtils.copyProperties(logRequestSummaryForm, outputFormSummary); 

                // reset selections and set filters
                logRequestSummaryForm.setSelectedLogRequest(null);
                request.setAttribute("logRequestSummaryForm", logRequestSummaryForm);
            }			
            forwardKey = "usage/logRequestSummary";
        } 
        catch (PrepSystemException pse) {
            log.error("Error Caught in LogRequestMultiFunction  :" , pse);					
            model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
            forwardKey = ERROR;
        } catch( PrepFunctionalException pfex){
            log.error("Functional Exception in LogRequestMultiFunction  :" + pfex);
            model.addAttribute("systemerror", getMessage(pfex.getErrorKey()));
            forwardKey= ERROR; 
        } catch (Exception ex) {
            log.error("Exception caught in LogRequestMultiFunction  :doWork() method", ex);
            model.addAttribute("systemerror", getMessage("system.error"));
            forwardKey= ERROR;
        } 

        log.debug("Return to UI, filterSupplierCode: "+filterSupplierCode + " filterTargetSurveyYearQuarter: "+ filterTargetSurveyYearQuarter +" filterStaus: "+filterStaus + " operationType" + operationType);

        if (operationType.equalsIgnoreCase("UPDATE")) { 
            log.debug("UPDATE message");
            if(model.getAttribute("systemerror") != null) {
                log.debug("UPDATE message, error empty");
                model.addAttribute(MESSAGE, getMessage("message.apm.logrequest.update.success"));
            }
        }
        if (operationType.equalsIgnoreCase("RELEASE") ) {
            log.debug("RELEASE message");
            if(model.getAttribute("systemerror") != null) {
                log.debug("RELEASE message, error empty");
                model.addAttribute(MESSAGE, getMessage("message.apm.logrequest.release.success"));
            }
        }
        if (operationType.equalsIgnoreCase("DELETE")) { 
            log.debug("DELETE message");
            if(model.getAttribute("systemerror") != null) {
                log.debug("DELETE message, error empty");
                model.addAttribute(MESSAGE , getMessage("message.apm.logrequest.delete.success"));
            }
        }
        log.debug("Exiting updateLogRequestSummary in LogRequestMultiController");
        return forwardKey;
    }

    private String goToUsage(LogRequestSummaryForm logRequestSummaryForm , Model model , HttpServletRequest request,
        HttpServletResponse response) {


        log.debug("Entering goToUsage in LogRequestMultiAction");

        String forwardKey = null;

        String[] selectedIds = logRequestSummaryForm.getSelectedLogRequest();

        if (selectedIds != null && selectedIds.length > 0 ) {
            for (String s: selectedIds) {   
                log.debug("SelectedLogRequests: "+s);
            }
        } 

        String filterSupplierCode = logRequestSummaryForm.getFilterSupplierCode();
        String filterTargetSurveyYearQuarter = logRequestSummaryForm.getFiltertargetSurveyYearQuarter();
        String filterStaus = logRequestSummaryForm.getFilterStaus(); 
        String operationType = logRequestSummaryForm.getOperationType();

        log.debug("filterSupplierCode: "+filterSupplierCode + " filterTargetSurveyYearQuarter: "+ filterTargetSurveyYearQuarter +" filterStaus: "+filterStaus + " operationType" + operationType);

        // pagination
        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference(); 
        int searchResultsPerPg = userPreferences.getNofSrchRsltsPerPage();
        logRequestSummaryForm.setSearchResultsPerPg(searchResultsPerPg);

        String[] selections = logRequestSummaryForm.getSelections();

        String[] tokens =null;
        String headerid = null;

        if (selections != null && selections.length >0){
            int i=0;

            for (String index : selections) {

                if(ValidationUtils.isEmptyOrNull(index)) {
                    continue;
                }
                i++;

                log.debug("selectedStrs: "+ i + " element: "+index);
                tokens = index.split("##",-1);
                log.debug("logReqId: "+ tokens[0]);

                headerid = tokens[0];

                if (null != tokens[1] )
                    log.debug("call letter: "+ tokens[1]);

                if (null != tokens[1] )
                    log.debug("start date: "+ tokens[2]);

                if (null != tokens[2] )
                    log.debug("end date: "+ tokens[3]);

                if (null != tokens[3] )
                    log.debug("account Number: "+ tokens[4]); 
            }
        } 


        //Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null; 

        LogUsageSummary inputFormSummary = new LogUsageSummary();
        LogUsageSummary outputFormSummary = new LogUsageSummary(); 

        List<Object> outValueObjects =null;

        inputContext.addInputValueObject(inputFormSummary);
        try {
            BeanUtils.copyProperties(inputFormSummary, logRequestSummaryForm);  

            outputContext = usageHandler.getLogUsageSummary(inputContext);

            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                outputFormSummary = (LogUsageSummary) outValueObjects.iterator().next();

                // copy summary object to form
                LogUsageForm f = new LogUsageForm();
                BeanUtils.copyProperties(f, outputFormSummary); 

                f.setHeaderid(headerid); 
                f.setFiltertargetSurveyYearQuarter(filterTargetSurveyYearQuarter);
                f.setFilterSupplierCode(filterSupplierCode);
                f.setFilterStaus(filterStaus); 
                f.setLogRequestPg(logRequestSummaryForm.getCurrentPg()); 

                f.setFirstPg(outputFormSummary.getFirstPg() );
                f.setLastPg(outputFormSummary.getLastPg());
                f.setTotalPg(outputFormSummary.getTotalPg());


                // reset selections and set filters 
                log.debug("setting up values for form now: ");
                request.setAttribute("logUsageForm", f);
            }           
            forwardKey = "usage/logUsageSummary";
        } 
        catch (PrepSystemException pse) {
            log.error("Error Caught in LogRequestMultiController  :" , pse);                  
            model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
            forwardKey = ERROR;
        } catch( PrepFunctionalException pfex){
            log.error("Functional Exception in LogRequestMultiController  :" + pfex);
            model.addAttribute("systemerror", getMessage(pfex.getErrorKey()));
            forwardKey= ERROR; 
        } catch (Exception ex) {
            log.error("Exception caught in LogRequestMultiController  :doSumbit() method", ex);
            model.addAttribute("system.error", getMessage("system.error"));
            forwardKey= ERROR;
        } 
        log.debug("Exiting goToUsage in LogRequestMultiController");
        return forwardKey;
    }
}
