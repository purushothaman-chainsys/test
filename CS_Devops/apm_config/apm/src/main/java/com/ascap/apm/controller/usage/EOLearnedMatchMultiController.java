package com.ascap.apm.controller.usage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.ApmWork;
import com.ascap.apm.vob.usage.EOLearnedMatch;
import com.ascap.apm.vob.usage.EOLearnedMatchForm;
import com.ascap.apm.vob.usage.EOLearnedMatchList;
import com.ascap.apm.vob.usage.EOLearnedMatchListForm;
import com.ascap.apm.vob.usage.PerformanceSearch;

@Controller
public class EOLearnedMatchMultiController extends BaseUsageController {

    private static final String ERROR = "error";
    private static final String EO_LEARNED_MATCHED_LIST = "usage/eoLearnedMatchList";
    private static final String EO_LEARNED_MATCHED_DETAILS = "usage/eoLearnedMatchDetails";
    private static final String APM_LEARNED_MATCH_EO = "forward:/apmLearnedMatchEO";
    private static final String APM_LEARNED_MATCH_EO_PARAM = "forward:/apmLearnedMatchEO?navigationTypePar=CURR&operationTypePar=SEARCH";


    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/learnedMatchDetailsEO", method = { RequestMethod.GET, RequestMethod.POST })
    public String doSubmitEO(@ModelAttribute EOLearnedMatchForm eoLearnedMatchForm , HttpServletRequest request,
        HttpServletResponse response , Model model)
    {

        log.debug("eoLearnedMatchForm.operationType: " + eoLearnedMatchForm.getOperationType());
        if("CREATE_LEARNED_MATCH".equals(eoLearnedMatchForm.getOperationType())) {
            return createEOLearnedMatch(eoLearnedMatchForm, model, request, response);
        }
        if("CANCEL".equals(eoLearnedMatchForm.getOperationType())) {
            return APM_LEARNED_MATCH_EO_PARAM;
        }
        else if("UPDATE_LEARNED_MATCH".equals(eoLearnedMatchForm.getOperationType())) {      
            return updateEOLearnedMatchMultiple(eoLearnedMatchForm, model, request, response);
        }
        return EO_LEARNED_MATCHED_LIST;

    }

    @RequestMapping(value = "/apmLearnedMatchEO", method = { RequestMethod.GET, RequestMethod.POST })
    public String doSubmit(@ModelAttribute EOLearnedMatchListForm eoLearnedMatchListForm , HttpServletRequest request,
        HttpServletResponse response , Model model , @RequestParam(required = false) String navigationTypePar 
        , @RequestParam(required = false) String operationTypePar)
            throws Exception {

        log.debug("Entering doSubmit of EOLearnedMatchMultiController");

        log.debug("Form is instance of EOLearnedMatchListForm");
        log.debug("EOLearnedMatchListForm.operationType: " + eoLearnedMatchListForm.getOperationType());

        if(!ValidationUtils.isEmptyOrNull(navigationTypePar) )  {
            eoLearnedMatchListForm.setNavigationType(navigationTypePar);
        }
        if(!ValidationUtils.isEmptyOrNull(operationTypePar) )  {
            eoLearnedMatchListForm.setOperationType(operationTypePar);
        }

        if(ValidationUtils.isEmptyOrNull(eoLearnedMatchListForm.getOperationType()) )  {
            model.addAttribute("eoLearnedMatchListForm", eoLearnedMatchListForm);
            return EO_LEARNED_MATCHED_LIST;
        }
        else {			
            if("RETRIEVE".equals(eoLearnedMatchListForm.getOperationType())) {
                //return getEOLearnedMatch(mapping, form, request, response);
            }
            else if("UPDATE".equals(eoLearnedMatchListForm.getOperationType())) {
                return updateEOLearnedMatch(eoLearnedMatchListForm, model, request, response);
            }
            else if("DELETE".equals(eoLearnedMatchListForm.getOperationType())) {
                return deleteEOLearnedMatchList(eoLearnedMatchListForm, model, request, response);
            }
            else if("ADD".equals(eoLearnedMatchListForm.getOperationType())) {
                model.addAttribute("eoLearnedMatchForm", new EOLearnedMatchForm());
                return EO_LEARNED_MATCHED_DETAILS;
            }

            else if("MEDLEY_NEW".equals(eoLearnedMatchListForm.getOperationType())
                || "MEDLEY_RETRIEVE".equals(eoLearnedMatchListForm.getOperationType()) ) {
                return buildMedleyGroups(eoLearnedMatchListForm, model, request, response);    
            }

            else if("SEARCH".equals(eoLearnedMatchListForm.getOperationType()) || ValidationUtils.isEmptyOrNull(eoLearnedMatchListForm.getOperationType())
                || "CANCEL".equals(eoLearnedMatchListForm.getOperationType())
                ) {
                return getEOLearnedMatchList(eoLearnedMatchListForm, model , request, response, null);
            }
        }

        log.debug("Exiting doSubmit of EOLearnedMatchMultiController");
        return EO_LEARNED_MATCHED_LIST;
    }

    private String deleteEOLearnedMatchList(EOLearnedMatchListForm apmFileListForm,
        Model model, HttpServletRequest request,
        HttpServletResponse response ) throws Exception {

        log.debug("Entering deleteEOLearnedMatchList in EOLearnedMatchMultiController");

        String forwardKey = EO_LEARNED_MATCHED_LIST;

        EOLearnedMatchList apmFileList = new EOLearnedMatchList();

        try {

            BeanUtils.copyProperties(apmFileList, apmFileListForm);

            List<Object> outValueObjects =null;

            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmFileList);

            PREPContext outputContext = null;

            outputContext = usageHandler.deleteEOLearnedMatchList(inputContext);
            apmFileList = null;
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                apmFileList = (EOLearnedMatchList) outValueObjects.iterator().next();
                model.addAttribute("systemmessage", getMessage("message.apm.learnedmatch.delete.success"));
                apmFileListForm.setOperationType("SEARCH");
                return APM_LEARNED_MATCH_EO_PARAM;
            }
            else {
                apmFileListForm.setSelectedIndex(null);
                if(model.containsAttribute("systemmessage")) {
                    model.addAttribute("systemmessage", getMessage("message.apm.learnedmatch.delete.success"));
                }
                log.debug("Exiting deleteEOLearnedMatchList of ApmFileListMultiController");
                return APM_LEARNED_MATCH_EO;
            }
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmPerfBulkRequestAction :" + pse);
            log.error("Error Caught in ApmPerfBulkRequestAction  :" , pse);
            model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
            forwardKey = EO_LEARNED_MATCHED_LIST;
        } catch( PrepFunctionalException pfex){
            log.debug("Error Caught in ApmPerfBulkRequestAction  :" + pfex);
            log.warn("Functional Exception in ApmPerfBulkRequestAction  :" + pfex);
            model.addAttribute("systemerror", getMessage(pfex.getErrorKey()));
            forwardKey= EO_LEARNED_MATCHED_LIST; 
        } catch (Exception ex) {
            log.debug("Error Caught in ApmPerfBulkRequestAction  :" + ex);
            log.error("Exception caught in ApmPerfBulkRequestAction  :doSubmit() method", ex);
            model.addAttribute("systemerror", getMessage("system.error"));
            forwardKey= EO_LEARNED_MATCHED_LIST;
        } 

        if(!model.containsAttribute("sytemerror")) {
            model.addAttribute("systemmessage", getMessage("message.apm.learnedmatch.delete.success"));
        }

        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting deleteEOLearnedMatchList of ApmFileListMultiController");
        model.addAttribute("eoLearnedMatchListForm", apmFileListForm);
        return forwardKey;
    }

    private String getEOLearnedMatchList(EOLearnedMatchListForm eoLearnedMatchListForm ,Model model ,
        HttpServletRequest request, HttpServletResponse response, Map<String, String> errorWorkIds)
            throws Exception {

        log.debug("Entering getEOLearnedMatchList in EOLearnedMatchMultiController");
        String forwardKey = null;

        if(ValidationUtils.isEmptyOrNull(eoLearnedMatchListForm.getFilterMatchTypeCode())) {
            model.addAttribute("eoLearnedMatchListForm", eoLearnedMatchListForm);
            return EO_LEARNED_MATCHED_LIST;
        }

        String[] selectedIndex = eoLearnedMatchListForm.getSelectedIndex();

        //Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        EOLearnedMatchList apmLearnedMatchList = null;

        log.debug("eoLearnedMatchListForm.navigationType() " + eoLearnedMatchListForm.getNavigationType());

        if (eoLearnedMatchListForm.getNavigationType() == null || "FIRST".equalsIgnoreCase(eoLearnedMatchListForm.getNavigationType()) || "".equalsIgnoreCase(eoLearnedMatchListForm.getNavigationType().trim())) {
            apmLearnedMatchList = new EOLearnedMatchList();
            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            eoLearnedMatchListForm.setUserPreferences(userPreferences);
            BeanUtils.copyProperties(apmLearnedMatchList, eoLearnedMatchListForm);			

        } else {
            if(inputContext.getUserSessionState().getSearch() !=null){
                try {
                    apmLearnedMatchList = (EOLearnedMatchList) inputContext.getUserSessionState().getSearch().getAlternateSearch();
                }
                catch(Exception e) {}

                if(apmLearnedMatchList==null){
                    apmLearnedMatchList = new EOLearnedMatchList();
                }
                apmLearnedMatchList.setNavigationType(eoLearnedMatchListForm.getNavigationType());

            }else{
                inputContext.getUserSessionState().setSearch(new PerformanceSearch());
                apmLearnedMatchList = new EOLearnedMatchList();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                eoLearnedMatchListForm.setUserPreferences(userPreferences);
                BeanUtils.copyProperties(apmLearnedMatchList, eoLearnedMatchListForm);
            }
        }
        PerformanceSearch performanceSearch=null;
        List<Object> outValueObjects =null;
        inputContext.addInputValueObject(apmLearnedMatchList);
        try {
            outputContext = usageHandler.getEOLearnedMatchList(inputContext);
            apmLearnedMatchList = null;
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                apmLearnedMatchList = (EOLearnedMatchList) outValueObjects.iterator().next();

                if(errorWorkIds != null && errorWorkIds.size() > 0) {
                    List<Object> lmCol = apmLearnedMatchList.getSearchResults();
                    if(lmCol != null && lmCol.size() > 0) {
                        Iterator<Object> itr = lmCol.iterator();
                        EOLearnedMatch lm = null;
                        while(itr.hasNext()) {
                            lm = (EOLearnedMatch) itr.next();
                            if(errorWorkIds.containsKey(lm.getLearnedMatchId())) {
                                lm.setInvalidWorkId("Y");
                                lm.setWorkId(errorWorkIds.get(lm.getLearnedMatchId()));
                            }
                        }
                    }
                }

                BeanUtils.copyProperties(eoLearnedMatchListForm, apmLearnedMatchList);

                // reset selected check boxes in case of error
                if(errorWorkIds != null && errorWorkIds.size() > 0) {
                    eoLearnedMatchListForm.setSelectedIndex(selectedIndex);
                }
                else {
                    eoLearnedMatchListForm.setSelectedIndex(null);
                }
                eoLearnedMatchListForm.setOperationType("SEARCH");
                apmLearnedMatchList.setSearchResults(null);
                apmLearnedMatchList.setNavigationType(null);

                if(inputContext.getUserSessionState().getSearch() == null){
                    performanceSearch= new PerformanceSearch();	
                    performanceSearch.setAlternateSearch(apmLearnedMatchList);
                    inputContext.getUserSessionState().setSearch(performanceSearch);
                }else{
                    inputContext.getUserSessionState().getSearch().setAlternateSearch(apmLearnedMatchList);
                }
            } else {
                forwardKey = EO_LEARNED_MATCHED_LIST;
            }
            // set the UserSessionState objec to the session.
            setUserSessionState(request, inputContext.getUserSessionState());
            forwardKey = EO_LEARNED_MATCHED_LIST;
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in EOLearnedMatchMultiController :" + pse);
            log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);

            model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
            forwardKey = ERROR;
        } catch( PrepFunctionalException pfex){
            log.debug("Error Caught in EOLearnedMatchMultiController  :" + pfex);
            log.warn("Functional Exception in EOLearnedMatchMultiController  :" + pfex);
            model.addAttribute("systemerror", getMessage(pfex.getErrorKey()));
            forwardKey= ERROR; 
        } catch (Exception ex) {
            log.debug("Error Caught in EOLearnedMatchMultiController  :" + ex);
            log.error("Exception caught in EOLearnedMatchMultiController  :doSubmit() method", ex);
            model.addAttribute("systemerror", getMessage("system.error"));
            forwardKey= ERROR;
        } 

        log.debug("Exiting getEOLearnedMatchList in EOLearnedMatchMultiController");
        model.addAttribute("eoLearnedMatchListForm", eoLearnedMatchListForm);
        return forwardKey;
    }

    private String  updateEOLearnedMatch(EOLearnedMatchListForm apmFileListForm , Model model,
        HttpServletRequest request, HttpServletResponse response) {
        log.debug("Entering updateEOLearnedMatch in  EOLearnedMatchMultiController");
        String forwardKey = EO_LEARNED_MATCHED_LIST;
        EOLearnedMatchList apmFileList = new EOLearnedMatchList();
        List<ApmWork> workCol = new ArrayList<ApmWork>();

        try {

            BeanUtils.copyProperties(apmFileList, apmFileListForm);

            List<Object> col = new ArrayList<>();
            String[] lmIdWrkIdStr = apmFileListForm.getLmIdWorkIdStr();

            EOLearnedMatch apmFile = null;
            ApmWork apmWork = null;

            Map<String, String> idWorkIdMap = new HashMap<String, String> ();


            int i=0;
            for (String index : lmIdWrkIdStr) {

                if(ValidationUtils.isEmptyOrNull(index)) {
                    continue;
                }
                i++;
                apmFile = new EOLearnedMatch();
                apmWork = new ApmWork();

                apmFile.setLearnedMatchId(index.substring(0, index.indexOf("##")));
                apmFile.setWorkId(index.substring(index.indexOf("##")+2));
                col.add(apmFile);			

                idWorkIdMap.put(apmFile.getLearnedMatchId(), apmFile.getWorkId());

                apmWork.setWorkId(apmFile.getWorkId());

                workCol.add(apmWork);
                log.debug("Added object index " + i + " EOLearnedMatch: " +apmFile);
            }
            apmFileList.setSearchResults(col);
            Map<String, String> errorIdList = new HashMap<String, String> ();
            PREPContext inputWorkContext = getPREPContext(request);
            PREPContext outputWorkContext = null;
            inputWorkContext.addInputValueObject(workCol);
            List<ApmWork> outCol=null;

            List<String> errorWorkIds = new ArrayList<String>();

            try {
                boolean workIdErrors = false;
                outputWorkContext = usageHandler.validateApmWorks(inputWorkContext);				
                List<Object> outWorkValueObjects = outputWorkContext.getOutputValueObjects();

                if ((outWorkValueObjects != null) && (outWorkValueObjects.size() > 0)) {
                    outCol = (List<ApmWork> ) outWorkValueObjects.iterator().next();

                    if(outCol != null && outCol.size() > 0) {
                        for(ApmWork outWork : outCol) {
                            log.debug("Work: " + outWork);
                            if(!"Y".equals(outWork.getValidFlag())) {
                                errorWorkIds.add(outWork.getWorkId());
                                model.addAttribute("systemerror", getMessage("error.apm.workID.error.notfound", outWork.getWorkId()));
                                workIdErrors = true;
                                this.getKey(idWorkIdMap, outWork.getWorkId(), errorIdList);
                            }
                        }		
                    }
                }

                if ((outWorkValueObjects == null) || (outWorkValueObjects.size() <= 0) || workIdErrors) {
                    if(!model.containsAttribute("systemerror")) {
                        model.addAttribute("systemerror", getMessage("error.apm.workID.error.notfound"));
                    }
                    apmFileListForm.setNavigationType("CURR");
                    return this.getEOLearnedMatchList(apmFileListForm, model , request, response, errorIdList);
                }
            }
            catch (PrepSystemException pse) {
                log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);					
                model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
                forwardKey = ERROR;
            } 
            catch (PrepFunctionalException pse) {
                log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);					
                model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
                forwardKey = ERROR;
            }  
            catch (Exception ex) {
                log.error("Exception caught in EOLearnedMatchMultiController  :doSubmit() method", ex);
                model.addAttribute("systemerror", getMessage("system.error"));
                forwardKey= ERROR;
            }

            List<Object> outValueObjects =null;
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmFileList);

            PREPContext outputContext = null;
            outputContext = usageHandler.updateEOLearnedMatchList(inputContext);
            apmFileList = null;
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                apmFileList = (EOLearnedMatchList) outValueObjects.iterator().next();
            }
            else {
                apmFileListForm.setSelectedIndex(null);
                if(!model.containsAttribute("systemerror")) {
                    model.addAttribute("systemmessage", getMessage("message.apm.learnedmatch.update.success"));
                }

                if(true) {
                    return APM_LEARNED_MATCH_EO;
                }
            }
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmPerfBulkRequestAction :" + pse);
            log.error("Error Caught in ApmPerfBulkRequestAction  :" , pse);
            model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
            forwardKey = EO_LEARNED_MATCHED_LIST;
        } catch( PrepFunctionalException pfex){
            log.debug("Error Caught in ApmPerfBulkRequestAction  :" + pfex);
            log.warn("Functional Exception in ApmPerfBulkRequestAction  :" + pfex);
            model.addAttribute("systemerror", getMessage(pfex.getErrorKey()));
            forwardKey= ERROR; 
        } catch (Exception ex) {
            log.debug("Error Caught in ApmPerfBulkRequestAction  :" + ex);
            log.error("Exception caught in ApmPerfBulkRequestAction  :doSubmit() method", ex);
            model.addAttribute("systemerror", getMessage("system.error"));
            forwardKey= EO_LEARNED_MATCHED_LIST;
        } 

        if(!model.containsAttribute("systemerror")) {
            model.addAttribute("systemmessage", getMessage("message.apm.learnedmatch.update.success"));
            return APM_LEARNED_MATCH_EO_PARAM;
        }

        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting updateEOLearnedMatch of EOLearnedMatchMultiController");
        model.addAttribute("eoLearnedMatchForm", apmFileListForm);
        return forwardKey;
    }

    private Map<String, String>  getKey(Map<String, String> inputMap, String value, Map<String, String> outList) {
        Iterator<Entry<String, String>> it = inputMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>)it.next();
            if(value.equals((String) pair.getValue())) {
                outList.put((String) pair.getKey(), (String) pair.getValue());
            }
        }
        return outList;
    }

    private String buildMedleyGroups(EOLearnedMatchListForm eoLearnedMatchListForm,
        Model model, HttpServletRequest request,
        HttpServletResponse response) throws Exception {

        log.debug("Entering buildMedleyGroups in EOLearnedMatchMultiController");

        String forwardKey = null;
        EOLearnedMatchForm apmLearnedMatchForm =  new EOLearnedMatchForm();
        String[] selectedIndex = eoLearnedMatchListForm.getSelectedIndex();

        String url = null;
        String multWorkIdValue = null;
        String workId = null;
        String selectedId = null;
        String supplierCode = null;

        for (String index : selectedIndex) {
            multWorkIdValue = eoLearnedMatchListForm.getMultWorkMultWorkId();
            workId = eoLearnedMatchListForm.getMultWorkWorkId();
            url = eoLearnedMatchListForm.getMultWorkUrl();
            selectedId = index;
            supplierCode = eoLearnedMatchListForm.getMultWorkSupplierCode();
        }

        log.debug("eoLearnedMatchListForm.getEoLearnedMatchType " + eoLearnedMatchListForm.getEoLearnedMatchType());
        log.debug("eoLearnedMatchListForm.getMatchType " + eoLearnedMatchListForm.getMatchType());
        log.debug("eoLearnedMatchListForm.getMultWorkSelectedId " + eoLearnedMatchListForm.getMultWorkSelectedId());
        log.debug("eoLearnedMatchListForm.getMultWorkUrl " + eoLearnedMatchListForm.getMultWorkUrl());
        log.debug("eoLearnedMatchListForm.getMultWorkWorkId " + eoLearnedMatchListForm.getMultWorkWorkId());
        log.debug("eoLearnedMatchListForm.getMultWorkSelectedId " + eoLearnedMatchListForm.getMultWorkSelectedId());


        if("MEDLEY_NEW".equals(eoLearnedMatchListForm.getOperationType())) {
            apmLearnedMatchForm.setOperationType("EDIT");
            apmLearnedMatchForm.setMatchType(eoLearnedMatchListForm.getEoLearnedMatchType());
            apmLearnedMatchForm.setMatchIdValue(UsageConstants.YOUTUBE_URL_PREFIX+url);
            if("SUPPLIERID".equals(eoLearnedMatchListForm.getEoLearnedMatchType())) {
                apmLearnedMatchForm.setMatchIdValue(url);
                apmLearnedMatchForm.setSupplierCode(supplierCode);
            }
            List<String> workList = new ArrayList<String>();
            workList.add(workId);
            apmLearnedMatchForm.setWorkIdCollection(workList);

            return EO_LEARNED_MATCHED_DETAILS;
        }

        EOLearnedMatchList apmLearnedMatchList = new EOLearnedMatchList();
        apmLearnedMatchList.setEoLearnedMatchType(eoLearnedMatchListForm.getEoLearnedMatchType());

        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        EOLearnedMatch apmLearnedMatch = null;

        List<Object> outValueObjects =null;
        apmLearnedMatchList.setMultWorkMultWorkId(multWorkIdValue);
        inputContext.addInputValueObject(apmLearnedMatchList);
        try {
            outputContext = usageHandler.getEOLearnedMatchMedleyWorkInformation(inputContext);
            apmLearnedMatch = null;
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                apmLearnedMatch = (EOLearnedMatch) outValueObjects.iterator().next();

                log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxx before " + apmLearnedMatch.getMatchIdValue());
                if("URL".equals(eoLearnedMatchListForm.getEoLearnedMatchType())) {
                    apmLearnedMatch.setMatchIdValue(UsageConstants.YOUTUBE_URL_PREFIX+apmLearnedMatch.getMatchIdValue());
                }
                else {
                    apmLearnedMatch.setMatchIdValue(apmLearnedMatch.getMatchIdValue());
                }
                log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxx after " + apmLearnedMatch.getMatchIdValue());

                BeanUtils.copyProperties(apmLearnedMatchForm, apmLearnedMatch);
                apmLearnedMatchForm.setMatchType(eoLearnedMatchListForm.getEoLearnedMatchType());
                apmLearnedMatchForm.setOperationType("EDIT");
            } else {
                forwardKey = EO_LEARNED_MATCHED_DETAILS;
            }
            forwardKey = EO_LEARNED_MATCHED_LIST;
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmLearnedMatchMultiController :" + pse);
            log.error("Error Caught in ApmLearnedMatchMultiController  :" , pse);
            model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
            forwardKey = EO_LEARNED_MATCHED_LIST;
        } catch( PrepFunctionalException pfex){
            log.debug("Error Caught in ApmLearnedMatchMultiController  :" + pfex);
            log.warn("Functional Exception in ApmLearnedMatchMultiController  :" + pfex);
            model.addAttribute("systemerror", getMessage(pfex.getErrorKey()));
            forwardKey= EO_LEARNED_MATCHED_LIST; 
        } catch (Exception ex) {
            if(log.isDebugEnabled()){
                log.debug("Error Caught in ApmLearnedMatchMultiController  :" + ex);
                ex.printStackTrace();
            }
            log.error("Exception caught in ApmLearnedMatchMultiController  :doSubmit() method", ex);
            model.addAttribute("systemerror", getMessage("system.error"));
            forwardKey= ERROR;
        } 
        log.debug("Exiting buildMedleyGroups in EOLearnedMatchMultiController");
        model.addAttribute("eoLearnedMatchForm", apmLearnedMatchForm);
        return EO_LEARNED_MATCHED_DETAILS;
    }

    private String createEOLearnedMatch(EOLearnedMatchForm apmLearnedMatchForm, Model model ,HttpServletRequest request,
        HttpServletResponse response) {
        log.debug("Entering createEOLearnedMatch in EOLearnedMatchMultiController");
        String forwardKey = null;

        //Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        EOLearnedMatch apmLearnedMatch = new EOLearnedMatch();  

        List<Object> outValueObjects =null;
        String tempWorkId = apmLearnedMatchForm.getWorkId();
        if(apmLearnedMatchForm.getWorkIds() != null && apmLearnedMatchForm.getWorkIds().length > 0) {   

            ApmWork work = null; //new ApmWork();
            List<ApmWork> col = new ArrayList<ApmWork>();

            // Add work Ids to be validated into Collection to be sent to EJB
            log.debug("Work Ids in form size: " + apmLearnedMatchForm.getWorkIds().length);
            List<String> workList = new ArrayList<String>();
            for(String workId : apmLearnedMatchForm.getWorkIds()) {
                log.debug("workid + " + workId);
                workList.add(workId);
                try {
                    apmLearnedMatchForm.setWorkId(String.valueOf(Long.parseLong(workId)));
                    tempWorkId = workId;
                }
                catch(Exception e) {
                    model.addAttribute("systemerror", getMessage("error.apm.workID.error.notfound", tempWorkId));
                    model.addAttribute("eoLearnedMatchForm", apmLearnedMatchForm);
                    return EO_LEARNED_MATCHED_DETAILS;
                }

                work = new ApmWork();
                work.setWorkId(workId);
                col.add(work);
            }

            PREPContext inputWorkContext = getPREPContext(request);
            PREPContext outputWorkContext = null;

            inputWorkContext.addInputValueObject(col);
            List<ApmWork> outCol=null;
            apmLearnedMatchForm.setWorkIdCollection(workList);

            try {

                outputWorkContext = usageHandler.validateApmWorks(inputWorkContext);                
                List<Object> outWorkValueObjects = outputWorkContext.getOutputValueObjects();

                if ((outWorkValueObjects != null) && (outWorkValueObjects.size() > 0)) {
                    outCol = (List<ApmWork> ) outWorkValueObjects.iterator().next();

                    if(outCol != null && outCol.size() > 0) {
                        for(ApmWork outWork : outCol) {
                            log.debug("Work: " + outWork);
                            if(!"Y".equals(outWork.getValidFlag())) {
                                model.addAttribute("systemerror",  getMessage("error.apm.workID.error.notfound", outWork.getWorkId()));
                            }
                        }       
                    }
                    if(model.getAttribute("systemerror") != null) {
                        model.addAttribute("eoLearnedMatchForm", apmLearnedMatchForm);
                        return EO_LEARNED_MATCHED_DETAILS;
                    }
                }
            }
            catch (PrepSystemException pse) {
                log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);                    
                model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
                forwardKey = EO_LEARNED_MATCHED_DETAILS;
            } 
            catch (PrepFunctionalException pse) {
                log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);                    
                model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
                forwardKey = EO_LEARNED_MATCHED_DETAILS;
            }  
            catch (Exception ex) {
                log.error("Exception caught in EOLearnedMatchMultiController  :doWork() method", ex);
                model.addAttribute("systemerror", getMessage("system.error"));
                forwardKey= EO_LEARNED_MATCHED_DETAILS;
            }
        }

        String multWorkId = null;

        if(apmLearnedMatchForm.getWorkIds() != null && apmLearnedMatchForm.getWorkIds().length > 1 & "SUPPLIERID".equals(apmLearnedMatchForm.getMatchType())) {

            PREPContext inputMultWorkContext = getPREPContext(request);
            PREPContext outputMultWorkContext = null;
            try {
                outputMultWorkContext = usageHandler.getMultWorkIdSequence(inputMultWorkContext);
                List<Object> outSequenceValueObjects = outputMultWorkContext.getOutputValueObjects();
                if ((outSequenceValueObjects != null) && (outSequenceValueObjects.size() > 0)) {
                    multWorkId = (String) outSequenceValueObjects.iterator().next();
                    log.debug("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMult work id "+multWorkId );
                }
            }
            catch (PrepSystemException pse) {
                log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);                    
                model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
                forwardKey = EO_LEARNED_MATCHED_DETAILS;
            }
            catch (Exception pse) {
                log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);
                model.addAttribute("systemerror", getMessage("system.error"));
                forwardKey= EO_LEARNED_MATCHED_DETAILS;
            }
        }

        try {
            BeanUtils.copyProperties(apmLearnedMatch, apmLearnedMatchForm);
            apmLearnedMatch.setMultWorkId(multWorkId);
            log.debug("OOOOOOOOOOOOOOOOOOOOOObject " + apmLearnedMatch);
            inputContext.addInputValueObject(apmLearnedMatch);
            outputContext = usageHandler.addEOLearnedMatch(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                apmLearnedMatch = (EOLearnedMatch) outValueObjects.iterator().next();
                BeanUtils.copyProperties(apmLearnedMatchForm, apmLearnedMatch);             
            }
            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        }
        catch (PrepSystemException pse) {
            log.error("Error Caught in ApmPerfBulkRequestAction  :" , pse);                 
            model.addAttribute("systemerror",  getMessage(pse.getErrorKey()));
            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        } catch( PrepFunctionalException pfex){
            log.warn("Functional Exception in EOLearnedMatchMultiController  :" + pfex);
            if(pfex.getErrorKey().equals("error.apm.learnedmatch.invalid.workID")) {
                String [] dyanValues = pfex.getDynaValues();
                for(int i=0;i<dyanValues.length;i++) {                          
                    if("NOT_FOUND".endsWith(dyanValues[i])) {
                        model.addAttribute("systemerror",  getMessage("error.apm.workID.error.notfound", tempWorkId));
                    }
                    else if("INF".endsWith(dyanValues[i])) {
                        model.addAttribute("systemerror",  getMessage("error.apm.workID.error.inf", tempWorkId));
                    }
                    else if("PUB_DOM".endsWith(dyanValues[i])) {
                        model.addAttribute("systemerror",  getMessage("error.apm.workID.error.pubdomain", tempWorkId));
                    }
                    else if("WRK_GRD_VAL".endsWith(dyanValues[i])) {
                        model.addAttribute("systemerror",  getMessage("error.apm.workID.error.arraignpercentage", tempWorkId));
                    }
                }
            }
            else if(pfex.getErrorKey().equals("error.apm.learnedmatch.exists")) {
                log.debug("in error condition");
                String [] dyanValues = pfex.getDynaValues();
                for(int i=0;i<dyanValues.length;i++) {
                    model.addAttribute("systemerror",  getMessage("error.apm.learnedmatch.exists", dyanValues[i]));
                }
            }

            else if(pfex.getErrorKey().equals("error.apm.learnedmatch.industryid.invalid")) {
                String [] dyanValues = pfex.getDynaValues();
                for(int i=0;i<dyanValues.length;i++) {
                    model.addAttribute("systemerror",  getMessage("error.apm.learnedmatch.industryid.invalid", dyanValues[i]));
                }
            }
            else {
                model.addAttribute("systemerror",  getMessage(pfex.getErrorKey()));
            }
            forwardKey= EO_LEARNED_MATCHED_DETAILS; 
        } catch (Exception ex) {
            log.error("Exception caught in EOLearnedMatchMultiController  :doWork() method", ex);
            model.addAttribute("systemerror",  getMessage("system.error"));
            forwardKey= EO_LEARNED_MATCHED_DETAILS;
        }
        if(!model.containsAttribute("systemerror")) {
            model.addAttribute("systemmessage",  getMessage("message.apm.learnedmatch.create.success"));
            model.addAttribute("eoLearnedMatchListForm", new EOLearnedMatchListForm());
            return EO_LEARNED_MATCHED_LIST;
        }

        log.debug("Exiting createEOLearnedMatch in EOLearnedMatchMultiController");

        model.addAttribute("eoLearnedMatchForm", apmLearnedMatchForm);
        return forwardKey;
    }

    private String updateEOLearnedMatchMultiple(EOLearnedMatchForm apmLearnedMatchForm, Model model, HttpServletRequest request,
        HttpServletResponse response) {

        log.debug("Entering updateEOLearnedMatchMultiple in EOLearnedMatchMultiController");

        String forwardKey = null;

        log.debug("mult work id " + apmLearnedMatchForm.getMultWorkId());
        log.debug("getLearnedMatchId " + apmLearnedMatchForm.getLearnedMatchId());
        log.debug("getMatchIdValue " + apmLearnedMatchForm.getMatchIdValue());
        log.debug("getMatchType " + apmLearnedMatchForm.getMatchType());
        log.debug("getSupplierCode " + apmLearnedMatchForm.getSupplierCode());

        String inputMultWorkId = apmLearnedMatchForm.getMultWorkId();
        String inputMatchType = apmLearnedMatchForm.getMatchType();


        //Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        EOLearnedMatch apmLearnedMatch = new EOLearnedMatch();      
        List<String> workList = new ArrayList<String>();

        inputContext = getPREPContext(request);
        outputContext = null;

        List<Object> outValueObjects =null;
        String tempWorkId = apmLearnedMatchForm.getWorkId();
        if(apmLearnedMatchForm.getWorkIds() != null && apmLearnedMatchForm.getWorkIds().length > 0) {   

            ApmWork work = null; 
            List<ApmWork> col = new ArrayList<ApmWork>();

            // Add work Ids to be validated into Collection to be sent to EJB
            log.debug("yyyyyyyyyyyyyyyyyyyyyyyyyy form size: " + apmLearnedMatchForm.getWorkIds().length);
            for(String workId : apmLearnedMatchForm.getWorkIds()) {
                workList.add(workId);
                try {
                    apmLearnedMatchForm.setWorkId(String.valueOf(Long.parseLong(workId)));
                    tempWorkId = workId;
                }
                catch(Exception e) {
                    model.addAttribute("systemerror", getMessage("error.apm.workID.error.notfound", tempWorkId));
                    model.addAttribute("eoLearnedMatchForm", apmLearnedMatchForm);
                    return EO_LEARNED_MATCHED_DETAILS;
                }

                log.debug("AAAAAAAAAAAAAAAAAADDing work to be validated " + workId);
                work = new ApmWork();
                work.setWorkId(workId);
                col.add(work);
            }

            PREPContext inputWorkContext = getPREPContext(request);
            PREPContext outputWorkContext = null;

            inputWorkContext.addInputValueObject(col);
            List<ApmWork> outCol=null;
            apmLearnedMatchForm.setWorkIdCollection(workList);
            apmLearnedMatchForm.setOperationType("EDIT");

            try {

                outputWorkContext = usageHandler.validateApmWorks(inputWorkContext);                
                List<Object> outWorkValueObjects = outputWorkContext.getOutputValueObjects();

                if ((outWorkValueObjects != null) && (outWorkValueObjects.size() > 0)) {
                    outCol = (List<ApmWork> ) outWorkValueObjects.iterator().next();

                    if(outCol != null && outCol.size() > 0) {
                        for(ApmWork outWork : outCol) {
                            log.debug("Work: " + outWork);
                            if(!"Y".equals(outWork.getValidFlag())) {
                                model.addAttribute("systemerror", getMessage("error.apm.workID.error.notfound", outWork.getWorkId()));
                            }
                        }       
                    }
                    if(model.getAttribute("systemerror") != null) {
                        model.addAttribute("eoLearnedMatchForm", apmLearnedMatchForm);
                        return EO_LEARNED_MATCHED_DETAILS;
                    }
                }
            }
            catch (PrepSystemException pse) {
                log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);                    
                model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
                forwardKey = EO_LEARNED_MATCHED_DETAILS;
            } 
            catch (PrepFunctionalException pse) {
                log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);                    
                model.addAttribute("systemerror", getMessage(getMessage(pse.getErrorKey())));
                forwardKey = EO_LEARNED_MATCHED_DETAILS;
            }  
            catch (Exception ex) {
                log.error("Exception caught in EOLearnedMatchMultiController  :doSubmitEO() method", ex);
                model.addAttribute("systemerror",getMessage("system.error"));
                forwardKey= EO_LEARNED_MATCHED_DETAILS;
            }
        }

        List<String> medleyCloneCounts = new ArrayList<String>();
        if(apmLearnedMatchForm.getWorkIds() != null) {
            String[] wrkArray = apmLearnedMatchForm.getWorkIds();
            String[] medleyCloneCountsArray = apmLearnedMatchForm.getMedleyCloneCounts();
            for(String w : wrkArray) {
                if(!ValidationUtils.isEmptyOrNull(w) && !workList.contains(w)) {
                    workList.add(w);
                }
            }

            for(String cc : medleyCloneCountsArray) {
                if(!ValidationUtils.isEmptyOrNull(cc)) {
                    medleyCloneCounts.add(cc);
                }
            }
            apmLearnedMatchForm.setWorkIdCollection(workList);
            apmLearnedMatchForm.setCloneCountsCollection(medleyCloneCounts);
        }

        if(workList.size() == 1) {
            tempWorkId = workList.get(0);
        }

        if(ValidationUtils.isEmptyOrNull(inputMultWorkId) && "SUPPLIERID".equals(inputMatchType) && workList.size() > 1) {
            PREPContext inputMultWorkContext = getPREPContext(request);
            PREPContext outputMultWorkContext = null;

            try {
                outputMultWorkContext = usageHandler.getMultWorkIdSequence(inputMultWorkContext);
                List<Object> outSequenceValueObjects = outputMultWorkContext.getOutputValueObjects();
                if ((outSequenceValueObjects != null) && (outSequenceValueObjects.size() > 0)) {
                    inputMultWorkId = (String) outSequenceValueObjects.iterator().next();
                    log.debug("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMult work id "+inputMultWorkId );
                }
            }
            catch (PrepSystemException pse) {
                log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);                    
                model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
                forwardKey = EO_LEARNED_MATCHED_DETAILS;
            }
            catch (Exception pse) {
                log.error("Error Caught in EOLearnedMatchMultiController  :" , pse);
                model.addAttribute("systemerror", getMessage("system.error"));
                forwardKey= EO_LEARNED_MATCHED_DETAILS;
            }
        }

        try {
            BeanUtils.copyProperties(apmLearnedMatch, apmLearnedMatchForm);
            apmLearnedMatch.setMultWorkId(inputMultWorkId);
            inputContext.addInputValueObject(apmLearnedMatch);
            outputContext = usageHandler.updateEOLearnedMatchMultiple(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                apmLearnedMatch = (EOLearnedMatch) outValueObjects.iterator().next();
                if(apmLearnedMatch.getWorkIdErrors() != null && apmLearnedMatch.getWorkIdErrors().size() > 0) {
                    apmLearnedMatchForm.setOperationType("EDIT");

                    Iterator<String> itr = apmLearnedMatch.getWorkIdErrors().iterator();
                    String errorMsg = null;
                    while(itr.hasNext()) {
                        errorMsg = (String) itr.next();
                        model.addAttribute("systemerror", getMessage("field.errors.placeholder", errorMsg));
                    }      
                    model.addAttribute("eoLearnedMatchForm", apmLearnedMatchForm);
                    return EO_LEARNED_MATCHED_DETAILS;
                }
                BeanUtils.copyProperties(apmLearnedMatchForm, apmLearnedMatch);             
            }

            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        }
        catch (PrepSystemException pse) {
            log.error("Error Caught in ApmPerfBulkRequestAction  :" , pse);                 
            model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        } catch( PrepFunctionalException pfex){
            log.warn("Functional Exception in ApmPerfBulkRequestAction  :" + pfex);
            if(pfex.getErrorKey().equals("error.apm.learnedmatch.invalid.workID")) {
                String [] dyanValues = pfex.getDynaValues();
                for(int i=0;i<dyanValues.length;i++) {                          
                    if("NOT_FOUND".endsWith(dyanValues[i])) {
                        model.addAttribute("systemerror", getMessage("error.apm.workID.error.notfound", tempWorkId));
                    }
                    else if("INF".endsWith(dyanValues[i])) {
                        model.addAttribute("systemerror", getMessage("error.apm.workID.error.inf", tempWorkId));
                    }
                    else if("PUB_DOM".endsWith(dyanValues[i])) {
                        model.addAttribute("systemerror", getMessage("error.apm.workID.error.pubdomain", tempWorkId));
                    }
                    else if("WRK_GRD_VAL".endsWith(dyanValues[i])) {
                        model.addAttribute("systemerror", getMessage("error.apm.workID.error.arraignpercentage", tempWorkId));
                    }
                }
            }
            else if(pfex.getErrorKey().equals("error.apm.learnedmatch.exists")) {
                String [] dyanValues = pfex.getDynaValues();
                for(int i=0;i<dyanValues.length;i++) {          
                    model.addAttribute("systemerror", getMessage("error.apm.learnedmatch.exists", dyanValues[i]));
                }
            }
            else {
                model.addAttribute("systemerror", getMessage(pfex.getErrorKey()));
            }
            forwardKey= EO_LEARNED_MATCHED_DETAILS; 
        } catch (Exception ex) {
            log.error("Exception caught in ApmPerfBulkRequestAction  :doSubmitEO() method", ex);
            model.addAttribute("systemerror", getMessage("system.error"));
            forwardKey= EO_LEARNED_MATCHED_DETAILS;
        }
        if(!model.containsAttribute("systemerror")) {
            model.addAttribute("systemmessage", getMessage("message.apm.learnedmatch.multiplematch.update.success"));
            forwardKey = APM_LEARNED_MATCH_EO_PARAM;
        }
        else {
            apmLearnedMatchForm.setOperationType("EDIT");
        }

        log.debug("Exiting updateEOLearnedMatchMultiple in EOLearnedMatchMultiController");

        model.addAttribute("eoLearnedMatchForm", apmLearnedMatchForm);
        return forwardKey;
    }
}
