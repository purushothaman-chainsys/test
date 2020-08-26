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
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.ApmWork;
import com.ascap.apm.vob.usage.EOLearnedMatch;
import com.ascap.apm.vob.usage.EOLearnedMatchList;
import com.ascap.apm.vob.usage.PerformanceSearch;

@Controller
@RequestMapping("/usage")
public class EOLearnedMatchMultiController extends BaseUsageController {

    private static final String EO_LEARNED_MATCHED_LIST = "usage/eoLearnedMatchList";

    private static final String EO_LEARNED_MATCHED_DETAILS = "usage/eoLearnedMatchDetails";

    private static final String APM_LEARNED_MATCH_EO = "forward:/usage/apmLearnedMatchEO";

    private static final String APM_LEARNED_MATCH_EO_PARAM =
        "forward:/usage/apmLearnedMatchEO?navigationTypePar=CURR&operationTypePar=SEARCH";

    private static final String EOLEARNEDMATCHLIST = "eoLearnedMatchList";

    private static final String EOLEARNEDMATCH = "eoLearnedMatch";

    private static final String SEARCH = "SEARCH";

    private static final String US_MESSAGE_APM_LEARNEDMATCH_DELETE_SUCCESS =
        "us.message.apm.learnedmatch.delete.success";

    private static final String ERROR_CAUGHT_APMPERFBULKREQUESTCONTROLLER =
        "Error Caught in ApmPerfBulkRequestController  :";

    private static final String FUNCTIONAL_EXCEPTION_APMPERFBULKREQUESTCONTROLLER =
        "Functional Exception in ApmPerfBulkRequestController  :";

    private static final String SYSTEM_ERROR = "system.error";

    private static final String ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER =
        "Error Caught in EOLearnedMatchMultiController  :";

    private static final String US_ERROR_APM_WORKID_ERROR_NOTFOUND = "us.error.apm.workID.error.notfound";

    private static final String SUPPLIERID = "SUPPLIERID";

    private static final String WORK = "Work: ";

    private static final String ERROR_CAUGHT_APMLEARNEDMATCHMULTICONTROLLER =
        "Error Caught in ApmLearnedMatchMultiController  :";

    private static final String US_ERROR_APM_LEARNEDMATCH_EXISTS = "us.error.apm.learnedmatch.exists";

    private static final String SYSTEM_ERROR_LIST = "systemerrorlist";

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/learnedMatchDetailsEO", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView doSubmitEO(@ModelAttribute EOLearnedMatch eoLearnedMatch, HttpServletRequest request,
        HttpServletResponse response, Model model) {
        String viewName = EO_LEARNED_MATCHED_LIST;
        log.debug("eoLearnedMatchForm.operationType: " + eoLearnedMatch.getOperationType());
        if ("CREATE_LEARNED_MATCH".equals(eoLearnedMatch.getOperationType())) {
            viewName = createEOLearnedMatch(eoLearnedMatch, model, request);
        }
        if ("CANCEL".equals(eoLearnedMatch.getOperationType())) {
            viewName = APM_LEARNED_MATCH_EO_PARAM;
        } else if ("UPDATE_LEARNED_MATCH".equals(eoLearnedMatch.getOperationType())) {
            viewName = updateEOLearnedMatchMultiple(eoLearnedMatch, model, request);
        }
        return new ModelAndView(viewName);

    }

    @RequestMapping(value = "/apmLearnedMatchEO", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView doSubmit(@ModelAttribute("eoLearnedMatchList") EOLearnedMatchList eoLearnedMatchList,
        HttpServletRequest request, HttpServletResponse response, Model model,
        @RequestParam(required = false) String navigationTypePar,
        @RequestParam(required = false) String operationTypePar) throws Exception {
        String viewName = EO_LEARNED_MATCHED_LIST;
        log.debug("Entering doSubmit of EOLearnedMatchMultiController");

        log.debug("Form is instance of EOLearnedMatchList");
        log.debug("EOLearnedMatchListForm.operationType: " + eoLearnedMatchList.getOperationType());

        if (!ValidationUtils.isEmptyOrNull(navigationTypePar)) {
            eoLearnedMatchList.setNavigationType(navigationTypePar);
        }
        if (!ValidationUtils.isEmptyOrNull(operationTypePar)) {
            eoLearnedMatchList.setOperationType(operationTypePar);
        }

        if (ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getOperationType())) {
            model.addAttribute(EOLEARNEDMATCHLIST, eoLearnedMatchList);
        } else {
            if ("UPDATE".equals(eoLearnedMatchList.getOperationType())) {
                viewName = updateEOLearnedMatch(eoLearnedMatchList, model, request);
            } else if ("DELETE".equals(eoLearnedMatchList.getOperationType())) {
                viewName = deleteEOLearnedMatchList(eoLearnedMatchList, model, request);
            } else if ("ADD".equals(eoLearnedMatchList.getOperationType())) {
                model.addAttribute(EOLEARNEDMATCH, new EOLearnedMatch());
                viewName = EO_LEARNED_MATCHED_DETAILS;
            }

            else if ("MEDLEY_NEW".equals(eoLearnedMatchList.getOperationType())
                || "MEDLEY_RETRIEVE".equals(eoLearnedMatchList.getOperationType())) {
                viewName = buildMedleyGroups(eoLearnedMatchList, model, request);
            }

            else if (SEARCH.equals(eoLearnedMatchList.getOperationType())
                || ValidationUtils.isEmptyOrNull(eoLearnedMatchList.getOperationType())
                || "CANCEL".equals(eoLearnedMatchList.getOperationType())) {
                viewName = getEOLearnedMatchList(eoLearnedMatchList, model, request, null);
            }
        }

        log.debug("Exiting doSubmit of EOLearnedMatchMultiController");
        return new ModelAndView(viewName);
    }

    private String deleteEOLearnedMatchList(EOLearnedMatchList apmFileListForm, Model model, HttpServletRequest request)
        throws Exception {

        log.debug("Entering deleteEOLearnedMatchList in EOLearnedMatchMultiController");

        String forwardKey = "";

        EOLearnedMatchList apmFileList = new EOLearnedMatchList();

        try {

            BeanUtils.copyProperties(apmFileList, apmFileListForm);

            List<Object> outValueObjects = null;

            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmFileList);

            PREPContext outputContext = null;

            outputContext = usageHandler.deleteEOLearnedMatchList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmFileList = (EOLearnedMatchList) outValueObjects.iterator().next();
                model.addAttribute(SYSTEMMESSAGE, getMessage(US_MESSAGE_APM_LEARNEDMATCH_DELETE_SUCCESS));
                apmFileListForm.setOperationType(SEARCH);
                return APM_LEARNED_MATCH_EO_PARAM;
            } else {
                apmFileListForm.setSelectedIndex(null);
                if (model.containsAttribute(SYSTEMMESSAGE)) {
                    model.addAttribute(SYSTEMMESSAGE, getMessage(US_MESSAGE_APM_LEARNEDMATCH_DELETE_SUCCESS));
                }
                log.debug("Exiting deleteEOLearnedMatchList of ApmFileListMultiController");
                return APM_LEARNED_MATCH_EO;
            }
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmPerfBulkRequestAction :" + pse);
            log.error(ERROR_CAUGHT_APMPERFBULKREQUESTCONTROLLER, pse);
            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = EO_LEARNED_MATCHED_LIST;
        } catch (PrepFunctionalException pfex) {
            log.debug(ERROR_CAUGHT_APMPERFBULKREQUESTCONTROLLER + pfex);
            log.warn(FUNCTIONAL_EXCEPTION_APMPERFBULKREQUESTCONTROLLER + pfex);
            model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = EO_LEARNED_MATCHED_LIST;
        } catch (Exception ex) {
            log.debug(ERROR_CAUGHT_APMPERFBULKREQUESTCONTROLLER + ex);
            log.error("Exception caught in ApmPerfBulkRequestAction  :doSubmit() method", ex);
            model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = EO_LEARNED_MATCHED_LIST;
        }

        if (!model.containsAttribute("sytemerror")) {
            model.addAttribute(SYSTEMMESSAGE, getMessage(US_MESSAGE_APM_LEARNEDMATCH_DELETE_SUCCESS));
        }

        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting deleteEOLearnedMatchList of ApmFileListMultiController");
        model.addAttribute(EOLEARNEDMATCHLIST, apmFileListForm);
        return forwardKey;
    }

    private String getEOLearnedMatchList(EOLearnedMatchList eoLearnedMatchListForm, Model model,
        HttpServletRequest request, Map<String, String> errorWorkIds) throws Exception {

        log.debug("Entering getEOLearnedMatchList in EOLearnedMatchMultiController");
        String forwardKey = null;

        if (ValidationUtils.isEmptyOrNull(eoLearnedMatchListForm.getFilterMatchTypeCode())) {
            model.addAttribute(EOLEARNEDMATCHLIST, eoLearnedMatchListForm);
            return EO_LEARNED_MATCHED_LIST;
        }

        String[] selectedIndex = eoLearnedMatchListForm.getSelectedIndex();

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        EOLearnedMatchList apmLearnedMatchList = null;

        log.debug("eoLearnedMatchListForm.navigationType() " + eoLearnedMatchListForm.getNavigationType());

        if (eoLearnedMatchListForm.getNavigationType() == null
            || "FIRST".equalsIgnoreCase(eoLearnedMatchListForm.getNavigationType())
            || "".equalsIgnoreCase(eoLearnedMatchListForm.getNavigationType().trim())) {
            apmLearnedMatchList = new EOLearnedMatchList();
            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            eoLearnedMatchListForm.setUserPreferences(userPreferences);
            BeanUtils.copyProperties(apmLearnedMatchList, eoLearnedMatchListForm);

        } else {
            if (inputContext.getUserSessionState().getSearch() != null) {
                try {
                    apmLearnedMatchList =
                        (EOLearnedMatchList) inputContext.getUserSessionState().getSearch().getAlternateSearch();
                } catch (Exception e) {
                    log.debug(e.getMessage());
                }

                if (apmLearnedMatchList == null) {
                    apmLearnedMatchList = new EOLearnedMatchList();
                }
                apmLearnedMatchList.setNavigationType(eoLearnedMatchListForm.getNavigationType());

            } else {
                inputContext.getUserSessionState().setSearch(new PerformanceSearch());
                apmLearnedMatchList = new EOLearnedMatchList();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                eoLearnedMatchListForm.setUserPreferences(userPreferences);
                BeanUtils.copyProperties(apmLearnedMatchList, eoLearnedMatchListForm);
            }
        }
        PerformanceSearch performanceSearch = null;
        List<Object> outValueObjects = null;
        inputContext.addInputValueObject(apmLearnedMatchList);
        try {
            outputContext = usageHandler.getEOLearnedMatchList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmLearnedMatchList = (EOLearnedMatchList) outValueObjects.iterator().next();

                if (errorWorkIds != null && errorWorkIds.size() > 0) {
                    List<Object> lmCol = apmLearnedMatchList.getSearchResults();
                    if (lmCol != null && !lmCol.isEmpty()) {
                        Iterator<Object> itr = lmCol.iterator();
                        EOLearnedMatch lm = null;
                        while (itr.hasNext()) {
                            lm = (EOLearnedMatch) itr.next();
                            if (errorWorkIds.containsKey(lm.getLearnedMatchId())) {
                                lm.setInvalidWorkId("Y");
                                lm.setWorkId(errorWorkIds.get(lm.getLearnedMatchId()));
                            }
                        }
                    }
                }

                // reset selected check boxes in case of error
                if (errorWorkIds != null && errorWorkIds.size() > 0) {
                    apmLearnedMatchList.setSelectedIndex(selectedIndex);
                } else {
                    apmLearnedMatchList.setSelectedIndex(null);
                }
                apmLearnedMatchList.setOperationType(SEARCH);

                model.addAttribute(EOLEARNEDMATCHLIST, apmLearnedMatchList);
                apmLearnedMatchList.setNavigationType(null);

                if (inputContext.getUserSessionState().getSearch() == null) {
                    performanceSearch = new PerformanceSearch();
                    performanceSearch.setAlternateSearch(apmLearnedMatchList);
                    inputContext.getUserSessionState().setSearch(performanceSearch);
                } else {
                    inputContext.getUserSessionState().getSearch().setAlternateSearch(apmLearnedMatchList);
                }
            }
            // set the UserSessionState objec to the session.
            setUserSessionState(request, inputContext.getUserSessionState());
            forwardKey = EO_LEARNED_MATCHED_LIST;
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in EOLearnedMatchMultiController :" + pse);
            log.error(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER, pse);

            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (PrepFunctionalException pfex) {
            log.debug(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER + pfex);
            log.warn("Functional Exception in EOLearnedMatchMultiController  :" + pfex);
            model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (Exception ex) {
            log.debug(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER + ex);
            log.error("Exception caught in EOLearnedMatchMultiController  :doSubmit() method", ex);
            model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = ERRORPAGE;
        }

        log.debug("Exiting getEOLearnedMatchList in EOLearnedMatchMultiController");
        return forwardKey;
    }

    @SuppressWarnings("unchecked")
	private String updateEOLearnedMatch(EOLearnedMatchList apmFileListForm, Model model, HttpServletRequest request) {
        log.debug("Entering updateEOLearnedMatch in  EOLearnedMatchMultiController");
        String forwardKey = EO_LEARNED_MATCHED_LIST;
        EOLearnedMatchList apmFileList = new EOLearnedMatchList();
        List<ApmWork> workCol = new ArrayList<>();
        List<String> errorList = new ArrayList<>();

        try {

            BeanUtils.copyProperties(apmFileList, apmFileListForm);

            List<Object> col = new ArrayList<>();
            String[] lmIdWrkIdStr = apmFileListForm.getLmIdWorkIdStr();

            EOLearnedMatch apmFile = null;
            ApmWork apmWork = null;

            Map<String, String> idWorkIdMap = new HashMap<>();

            int i = 0;
            for (String index : lmIdWrkIdStr) {

                if (ValidationUtils.isEmptyOrNull(index)) {
                    continue;
                }
                i++;
                apmFile = new EOLearnedMatch();
                apmWork = new ApmWork();

                apmFile.setLearnedMatchId(index.substring(0, index.indexOf("##")));
                apmFile.setWorkId(index.substring(index.indexOf("##") + 2));
                col.add(apmFile);

                idWorkIdMap.put(apmFile.getLearnedMatchId(), apmFile.getWorkId());

                apmWork.setWorkId(apmFile.getWorkId());

                workCol.add(apmWork);
                log.debug("Added object index " + i + " EOLearnedMatch: " + apmFile);
            }
            apmFileList.setSearchResults(col);
            Map<String, String> errorIdList = new HashMap<>();
            PREPContext inputWorkContext = getPREPContext(request);
            PREPContext outputWorkContext = null;
            inputWorkContext.addInputValueObject(workCol);
            List<Object> outCol = null;

            List<String> errorWorkIds = new ArrayList<>();

            try {
                boolean workIdErrors = false;
                outputWorkContext = usageHandler.validateApmWorks(inputWorkContext);
                List<Object> outWorkValueObjects = outputWorkContext.getOutputValueObjects();

                if ((outWorkValueObjects != null) && (!outWorkValueObjects.isEmpty())) {
                    outCol = (List<Object>) outWorkValueObjects.iterator().next();

                    if (outCol != null && !outCol.isEmpty()) {
                        for (Object outWork : outCol) {

                            log.debug(WORK + outWork);
                            if (!"Y".equals(((ApmWork) outWork).getValidFlag())) {
                                errorWorkIds.add(((ApmWork) outWork).getWorkId());
                                errorList.add(
                                    getMessage(US_ERROR_APM_WORKID_ERROR_NOTFOUND, ((ApmWork) outWork).getWorkId()));
                                workIdErrors = true;
                                this.getKey(idWorkIdMap, ((ApmWork) outWork).getWorkId(), errorIdList);
                            }
                        }
                    }
                    model.addAttribute(SYSTEM_ERROR_LIST, errorList);
                }

                if ((outWorkValueObjects == null) || (outWorkValueObjects.isEmpty()) || workIdErrors) {
                    if (errorList.isEmpty()) {
                        errorList.add(getMessage(US_ERROR_APM_WORKID_ERROR_NOTFOUND));
                    }
                    apmFileListForm.setNavigationType("CURR");
                    return this.getEOLearnedMatchList(apmFileListForm, model, request, errorIdList);
                }
                model.addAttribute(SYSTEM_ERROR_LIST, errorList);
            } catch (PrepSystemException | PrepFunctionalException pse) {
                log.error(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER, pse);
                model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
                forwardKey = ERRORPAGE;
            } catch (Exception ex) {
                log.error("Exception caught in EOLearnedMatchMultiController  :doSubmit() method", ex);
                model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
                forwardKey = ERRORPAGE;
            }

            List<Object> outValueObjects = null;
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmFileList);
            apmFileList =null;
            PREPContext outputContext = null;
            outputContext = usageHandler.updateEOLearnedMatchList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmFileList = (EOLearnedMatchList) outValueObjects.iterator().next();
            } else {
                apmFileListForm.setSelectedIndex(null);
                if (errorList.isEmpty() && !model.containsAttribute(SYSTEMERROR)) {
                    model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.learnedmatch.update.success"));
                }

                if (true) {
                    return APM_LEARNED_MATCH_EO;
                }
            }
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmPerfBulkRequestAction :" + pse);
            log.error(ERROR_CAUGHT_APMPERFBULKREQUESTCONTROLLER, pse);
            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = EO_LEARNED_MATCHED_LIST;
        } catch (PrepFunctionalException pfex) {
            log.debug(ERROR_CAUGHT_APMPERFBULKREQUESTCONTROLLER + pfex);
            log.warn(FUNCTIONAL_EXCEPTION_APMPERFBULKREQUESTCONTROLLER + pfex);
            model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = ERRORPAGE;
        } catch (Exception ex) {
            log.debug(ERROR_CAUGHT_APMPERFBULKREQUESTCONTROLLER + ex);
            log.error("Exception caught in ApmPerfBulkRequestAction  :doSubmit() method", ex);
            model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = EO_LEARNED_MATCHED_LIST;
        }

        if (errorList.isEmpty() && !model.containsAttribute(SYSTEMERROR)) {
            model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.learnedmatch.update.success"));
            model.addAttribute(SYSTEM_ERROR_LIST, errorList);
            return APM_LEARNED_MATCH_EO_PARAM;
        }

        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting updateEOLearnedMatch of EOLearnedMatchMultiController");
        model.addAttribute(SYSTEM_ERROR_LIST, errorList);
        model.addAttribute(EOLEARNEDMATCH, apmFileList);
        return forwardKey;
    }

    private Map<String, String> getKey(Map<String, String> inputMap, String value, Map<String, String> outList) {
        Iterator<Entry<String, String>> it = inputMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
            if (value.equals((String) pair.getValue())) {
                outList.put((String) pair.getKey(), (String) pair.getValue());
            }
        }
        return outList;
    }

    private String buildMedleyGroups(EOLearnedMatchList eoLearnedMatchListForm, Model model, HttpServletRequest request)
        throws Exception {

        log.debug("Entering buildMedleyGroups in EOLearnedMatchMultiController");

        EOLearnedMatch apmLearnedMatchForm = new EOLearnedMatch();

        String url = null;
        String multWorkIdValue = null;
        String workId = null;
        String supplierCode = null;

        multWorkIdValue = eoLearnedMatchListForm.getMultWorkMultWorkId();
        workId = eoLearnedMatchListForm.getMultWorkWorkId();
        url = eoLearnedMatchListForm.getMultWorkUrl();
        supplierCode = eoLearnedMatchListForm.getMultWorkSupplierCode();

        log.debug("eoLearnedMatchList.getEoLearnedMatchType " + eoLearnedMatchListForm.getEoLearnedMatchType());
        log.debug("eoLearnedMatchList.getMatchType " + eoLearnedMatchListForm.getMatchType());
        log.debug("eoLearnedMatchList.getMultWorkSelectedId " + eoLearnedMatchListForm.getMultWorkSelectedId());
        log.debug("eoLearnedMatchList.getMultWorkUrl " + eoLearnedMatchListForm.getMultWorkUrl());
        log.debug("eoLearnedMatchList.getMultWorkWorkId " + eoLearnedMatchListForm.getMultWorkWorkId());
        log.debug("eoLearnedMatchList.getMultWorkSelectedId " + eoLearnedMatchListForm.getMultWorkSelectedId());

        if ("MEDLEY_NEW".equals(eoLearnedMatchListForm.getOperationType())) {
            apmLearnedMatchForm.setOperationType("EDIT");
            apmLearnedMatchForm.setMatchType(eoLearnedMatchListForm.getEoLearnedMatchType());
            apmLearnedMatchForm.setMatchIdValue(UsageConstants.YOUTUBE_URL_PREFIX + url);
            if (SUPPLIERID.equals(eoLearnedMatchListForm.getEoLearnedMatchType())) {
                apmLearnedMatchForm.setMatchIdValue(url);
                apmLearnedMatchForm.setSupplierCode(supplierCode);
            }
            List<String> workList = new ArrayList<>();
            workList.add(workId);
            apmLearnedMatchForm.setWorkIdCollection(workList);
            model.addAttribute(EOLEARNEDMATCH, apmLearnedMatchForm);
            return EO_LEARNED_MATCHED_DETAILS;
        }

        EOLearnedMatchList apmLearnedMatchList = new EOLearnedMatchList();
        apmLearnedMatchList.setEoLearnedMatchType(eoLearnedMatchListForm.getEoLearnedMatchType());

        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        EOLearnedMatch apmLearnedMatch = null;

        List<Object> outValueObjects = null;
        apmLearnedMatchList.setMultWorkMultWorkId(multWorkIdValue);
        inputContext.addInputValueObject(apmLearnedMatchList);
        try {
            outputContext = usageHandler.getEOLearnedMatchMedleyWorkInformation(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmLearnedMatch = (EOLearnedMatch) outValueObjects.iterator().next();

                log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxx before " + apmLearnedMatch.getMatchIdValue());
                if ("URL".equals(eoLearnedMatchListForm.getEoLearnedMatchType())) {
                    apmLearnedMatch
                        .setMatchIdValue(UsageConstants.YOUTUBE_URL_PREFIX + apmLearnedMatch.getMatchIdValue());
                } else {
                    apmLearnedMatch.setMatchIdValue(apmLearnedMatch.getMatchIdValue());
                }
                log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxx after " + apmLearnedMatch.getMatchIdValue());

                BeanUtils.copyProperties(apmLearnedMatchForm, apmLearnedMatch);
                apmLearnedMatchForm.setMatchType(eoLearnedMatchListForm.getEoLearnedMatchType());
                apmLearnedMatchForm.setOperationType("EDIT");
            }
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmLearnedMatchMultiController :" + pse);
            log.error(ERROR_CAUGHT_APMLEARNEDMATCHMULTICONTROLLER, pse);
            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
        } catch (PrepFunctionalException pfex) {
            log.debug(ERROR_CAUGHT_APMLEARNEDMATCHMULTICONTROLLER + pfex);
            log.warn("Functional Exception in ApmLearnedMatchMultiController  :" + pfex);
            model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
        } catch (Exception ex) {
            if (log.isDebugEnabled()) {
                log.debug(ERROR_CAUGHT_APMLEARNEDMATCHMULTICONTROLLER + ex);
            }
            log.error("Exception caught in ApmLearnedMatchMultiController  :doSubmit() method", ex);
            model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
        }
        log.debug("Exiting buildMedleyGroups in EOLearnedMatchMultiController");
        model.addAttribute(EOLEARNEDMATCH, apmLearnedMatchForm);
        return EO_LEARNED_MATCHED_DETAILS;
    }

    @SuppressWarnings("unchecked")
    private String createEOLearnedMatch(EOLearnedMatch apmLearnedMatchForm, Model model, HttpServletRequest request) {
        log.debug("Entering createEOLearnedMatch in EOLearnedMatchMultiController");
        String forwardKey = null;

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        EOLearnedMatch apmLearnedMatch = new EOLearnedMatch();

        List<Object> outValueObjects = null;
        String tempWorkId = apmLearnedMatchForm.getWorkId();
        if (apmLearnedMatchForm.getWorkIds() != null && apmLearnedMatchForm.getWorkIds().length > 0) {

            ApmWork work = null;
            List<ApmWork> col = new ArrayList<>();

            // Add work Ids to be validated into Collection to be sent to EJB
            log.debug("Work Ids in form size: " + apmLearnedMatchForm.getWorkIds().length);
            List<String> workList = new ArrayList<>();
            for (String workId : apmLearnedMatchForm.getWorkIds()) {
                log.debug("workid + " + workId);
                workList.add(workId);
                try {
                    apmLearnedMatchForm.setWorkId(String.valueOf(Long.parseLong(workId)));
                    tempWorkId = workId;
                } catch (Exception e) {
                    model.addAttribute(SYSTEMERROR, getMessage(US_ERROR_APM_WORKID_ERROR_NOTFOUND, tempWorkId));
                    model.addAttribute(EOLEARNEDMATCH, apmLearnedMatchForm);
                    return EO_LEARNED_MATCHED_DETAILS;
                }

                work = new ApmWork();
                work.setWorkId(workId);
                col.add(work);
            }

            PREPContext inputWorkContext = getPREPContext(request);
            PREPContext outputWorkContext = null;

            inputWorkContext.addInputValueObject(col);
            List<Object> outCol = null;
            apmLearnedMatchForm.setWorkIdCollection(workList);

            try {

                outputWorkContext = usageHandler.validateApmWorks(inputWorkContext);
                List<Object> outWorkValueObjects = outputWorkContext.getOutputValueObjects();

                if ((outWorkValueObjects != null) && (!outWorkValueObjects.isEmpty())) {
                    outCol = (List<Object>) outWorkValueObjects.iterator().next();

                    if (outCol != null && !outCol.isEmpty()) {
                        for (Object outWork : outCol) {
                            log.debug(WORK + outWork);
                            if (!"Y".equals(((ApmWork) outWork).getValidFlag())) {
                                model.addAttribute(SYSTEMERROR,
                                    getMessage(US_ERROR_APM_WORKID_ERROR_NOTFOUND, ((ApmWork) outWork).getWorkId()));
                            }
                        }
                    }
                    if (model.getAttribute(SYSTEMERROR) != null) {
                        model.addAttribute(EOLEARNEDMATCH, apmLearnedMatchForm);
                        return EO_LEARNED_MATCHED_DETAILS;
                    }
                }
            } catch (PrepSystemException | PrepFunctionalException pse) {
                log.error(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER, pse);
                model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            } catch (Exception ex) {
                log.error("Exception caught in EOLearnedMatchMultiController  :createEOLearnedMatch() method", ex);
                model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            }
        }

        String multWorkId = null;

        if (apmLearnedMatchForm.getWorkIds() != null && apmLearnedMatchForm.getWorkIds().length > 1
            && SUPPLIERID.equals(apmLearnedMatchForm.getMatchType())) {

            PREPContext inputMultWorkContext = getPREPContext(request);
            PREPContext outputMultWorkContext = null;
            try {
                outputMultWorkContext = usageHandler.getMultWorkIdSequence(inputMultWorkContext);
                List<Object> outSequenceValueObjects = outputMultWorkContext.getOutputValueObjects();
                if ((outSequenceValueObjects != null) && !outSequenceValueObjects.isEmpty()) {
                    multWorkId = (String) outSequenceValueObjects.iterator().next();
                    log.debug("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMult work id " + multWorkId);
                }
            } catch (PrepSystemException pse) {
                log.error(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER, pse);
                model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            } catch (Exception pse) {
                log.error(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER, pse);
                model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            }
        }

        try {
            BeanUtils.copyProperties(apmLearnedMatch, apmLearnedMatchForm);
            apmLearnedMatch.setMultWorkId(multWorkId);
            log.debug("OOOOOOOOOOOOOOOOOOOOOObject " + apmLearnedMatch);
            inputContext.addInputValueObject(apmLearnedMatch);
            outputContext = usageHandler.addEOLearnedMatch(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && !outValueObjects.isEmpty()) {
                apmLearnedMatch = (EOLearnedMatch) outValueObjects.iterator().next();
                BeanUtils.copyProperties(apmLearnedMatchForm, apmLearnedMatch);
            }
            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        } catch (PrepSystemException pse) {
            log.error(ERROR_CAUGHT_APMPERFBULKREQUESTCONTROLLER, pse);
            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        } catch (PrepFunctionalException pfex) {
            log.warn("Functional Exception in EOLearnedMatchMultiController  :" + pfex);
            if (pfex.getErrorKey().equals("error.apm.learnedmatch.invalid.workID")) {
                String[] dyanValues = pfex.getDynaValues();
                for (int i = 0; i < dyanValues.length; i++) {
                    if ("NOT_FOUND".endsWith(dyanValues[i])) {
                        model.addAttribute(SYSTEMERROR, getMessage(US_ERROR_APM_WORKID_ERROR_NOTFOUND, tempWorkId));
                    } else if ("INF".endsWith(dyanValues[i])) {
                        model.addAttribute(SYSTEMERROR, getMessage("us.error.apm.workID.error.inf", tempWorkId));
                    } else if ("PUB_DOM".endsWith(dyanValues[i])) {
                        model.addAttribute(SYSTEMERROR, getMessage("us.error.apm.workID.error.pubdomain", tempWorkId));
                    } else if ("WRK_GRD_VAL".endsWith(dyanValues[i])) {
                        model.addAttribute(SYSTEMERROR,
                            getMessage("us.error.apm.workID.error.arraignpercentage", tempWorkId));
                    }
                }
            } else if (pfex.getErrorKey().equals(US_ERROR_APM_LEARNEDMATCH_EXISTS)) {
                log.debug("in error condition");
                String[] dyanValues = pfex.getDynaValues();
                for (int i = 0; i < dyanValues.length; i++) {
                    model.addAttribute(SYSTEMERROR, getMessage(US_ERROR_APM_LEARNEDMATCH_EXISTS, dyanValues[i]));
                }
            }

            else if (pfex.getErrorKey().equals("us.error.apm.learnedmatch.industryid.invalid")) {
                String[] dyanValues = pfex.getDynaValues();
                for (int i = 0; i < dyanValues.length; i++) {
                    model.addAttribute(SYSTEMERROR,
                        getMessage("us.error.apm.learnedmatch.industryid.invalid", dyanValues[i]));
                }
            } else {
                model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            }
            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        } catch (Exception ex) {
            log.error("Exception caught in EOLearnedMatchMultiController  :createEOLearnedMatch() method", ex);
            model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        }
        if (!model.containsAttribute(SYSTEMERROR)) {
            model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.learnedmatch.create.success"));
            model.addAttribute(EOLEARNEDMATCHLIST, new EOLearnedMatchList());
            return EO_LEARNED_MATCHED_LIST;
        }

        log.debug("Exiting createEOLearnedMatch in EOLearnedMatchMultiController");

        model.addAttribute(EOLEARNEDMATCH, apmLearnedMatchForm);
        return forwardKey;
    }

    @SuppressWarnings("unchecked")
    private String updateEOLearnedMatchMultiple(EOLearnedMatch apmLearnedMatchForm, Model model,
        HttpServletRequest request) {

        log.debug("Entering updateEOLearnedMatchMultiple in EOLearnedMatchMultiController");

        String forwardKey = null;

        log.debug("mult work id " + apmLearnedMatchForm.getMultWorkId());
        log.debug("getLearnedMatchId " + apmLearnedMatchForm.getLearnedMatchId());
        log.debug("getMatchIdValue " + apmLearnedMatchForm.getMatchIdValue());
        log.debug("getMatchType " + apmLearnedMatchForm.getMatchType());
        log.debug("getSupplierCode " + apmLearnedMatchForm.getSupplierCode());

        String inputMultWorkId = apmLearnedMatchForm.getMultWorkId();
        String inputMatchType = apmLearnedMatchForm.getMatchType();

        // Create input and output contexts
        PREPContext inputContext = null;
        PREPContext outputContext = null;
        EOLearnedMatch apmLearnedMatch = new EOLearnedMatch();
        List<String> workList = new ArrayList<>();

        inputContext = getPREPContext(request);

        List<Object> outValueObjects = null;
        String tempWorkId = apmLearnedMatchForm.getWorkId();
        if (apmLearnedMatchForm.getWorkIds() != null && apmLearnedMatchForm.getWorkIds().length > 0) {

            ApmWork work = null;
            List<ApmWork> col = new ArrayList<>();

            // Add work Ids to be validated into Collection to be sent to EJB
            log.debug("yyyyyyyyyyyyyyyyyyyyyyyyyy form size: " + apmLearnedMatchForm.getWorkIds().length);
            for (String workId : apmLearnedMatchForm.getWorkIds()) {
                workList.add(workId);
                try {
                    apmLearnedMatchForm.setWorkId(String.valueOf(Long.parseLong(workId)));
                    tempWorkId = workId;
                } catch (Exception e) {
                    model.addAttribute(SYSTEMERROR, getMessage(US_ERROR_APM_WORKID_ERROR_NOTFOUND, tempWorkId));
                    model.addAttribute(EOLEARNEDMATCH, apmLearnedMatchForm);
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
            List<Object> outCol = null;
            apmLearnedMatchForm.setWorkIdCollection(workList);
            apmLearnedMatchForm.setOperationType("EDIT");

            try {

                outputWorkContext = usageHandler.validateApmWorks(inputWorkContext);
                List<Object> outWorkValueObjects = outputWorkContext.getOutputValueObjects();

                if ((outWorkValueObjects != null) && !outWorkValueObjects.isEmpty()) {
                    outCol = (List<Object>) outWorkValueObjects.iterator().next();

                    if (outCol != null && !outCol.isEmpty()) {
                        for (Object outWork : outCol) {
                            log.debug(WORK + outWork);
                            if (!"Y".equals(((ApmWork) outWork).getValidFlag())) {
                                model.addAttribute(SYSTEMERROR,
                                    getMessage(US_ERROR_APM_WORKID_ERROR_NOTFOUND, ((ApmWork) outWork).getWorkId()));
                            }
                        }
                    }
                    if (model.getAttribute(SYSTEMERROR) != null) {
                        model.addAttribute(EOLEARNEDMATCH, apmLearnedMatchForm);
                        return EO_LEARNED_MATCHED_DETAILS;
                    }
                }
            } catch (PrepSystemException pse) {
                log.error(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER, pse);
                model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            } catch (PrepFunctionalException pse) {
                log.error(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER, pse);
                model.addAttribute(SYSTEMERROR, getMessage(getMessage(pse.getErrorKey())));
            } catch (Exception ex) {
                log.error("Exception caught in EOLearnedMatchMultiController  :doSubmitEO() method", ex);
                model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            }
        }

        List<String> medleyCloneCounts = new ArrayList<>();
        if (apmLearnedMatchForm.getWorkIds() != null) {
            String[] wrkArray = apmLearnedMatchForm.getWorkIds();
            String[] medleyCloneCountsArray = apmLearnedMatchForm.getMedleyCloneCounts();
            for (String w : wrkArray) {
                if (!ValidationUtils.isEmptyOrNull(w) && !workList.contains(w)) {
                    workList.add(w);
                }
            }

            for (String cc : medleyCloneCountsArray) {
                if (!ValidationUtils.isEmptyOrNull(cc)) {
                    medleyCloneCounts.add(cc);
                }
            }
            apmLearnedMatchForm.setWorkIdCollection(workList);
            apmLearnedMatchForm.setCloneCountsCollection(medleyCloneCounts);
        }

        if (workList.size() == 1) {
            tempWorkId = workList.get(0);
        }

        if (ValidationUtils.isEmptyOrNull(inputMultWorkId) && SUPPLIERID.equals(inputMatchType)
            && workList.size() > 1) {
            PREPContext inputMultWorkContext = getPREPContext(request);
            PREPContext outputMultWorkContext = null;

            try {
                outputMultWorkContext = usageHandler.getMultWorkIdSequence(inputMultWorkContext);
                List<Object> outSequenceValueObjects = outputMultWorkContext.getOutputValueObjects();
                if ((outSequenceValueObjects != null) && (!outSequenceValueObjects.isEmpty())) {
                    inputMultWorkId = (String) outSequenceValueObjects.iterator().next();
                    log.debug("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMult work id " + inputMultWorkId);
                }
            } catch (PrepSystemException pse) {
                log.error(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER, pse);
                model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            } catch (Exception pse) {
                log.error(ERROR_CAUGHT_EOLEARNEDMATCHMULTICONTROLLER, pse);
                model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            }
        }

        try {
            BeanUtils.copyProperties(apmLearnedMatch, apmLearnedMatchForm);
            apmLearnedMatch.setMultWorkId(inputMultWorkId);
            inputContext.addInputValueObject(apmLearnedMatch);
            outputContext = usageHandler.updateEOLearnedMatchMultiple(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmLearnedMatch = (EOLearnedMatch) outValueObjects.iterator().next();
                if (apmLearnedMatch.getWorkIdErrors() != null && !apmLearnedMatch.getWorkIdErrors().isEmpty()) {
                    apmLearnedMatchForm.setOperationType("EDIT");

                    Iterator<String> itr = apmLearnedMatch.getWorkIdErrors().iterator();
                    String errorMsg = null;
                    while (itr.hasNext()) {
                        errorMsg = itr.next();
                        model.addAttribute(SYSTEMERROR, getMessage("us.field.errors.placeholder", errorMsg));
                    }
                    model.addAttribute(EOLEARNEDMATCH, apmLearnedMatchForm);
                    return EO_LEARNED_MATCHED_DETAILS;
                }
                BeanUtils.copyProperties(apmLearnedMatchForm, apmLearnedMatch);
            }

            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        } catch (PrepSystemException pse) {
            log.error(ERROR_CAUGHT_APMPERFBULKREQUESTCONTROLLER, pse);
            model.addAttribute(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        } catch (PrepFunctionalException pfex) {
            log.warn(FUNCTIONAL_EXCEPTION_APMPERFBULKREQUESTCONTROLLER + pfex);
            if (pfex.getErrorKey().equals("error.apm.learnedmatch.invalid.workID")) {
                String[] dyanValues = pfex.getDynaValues();
                for (int i = 0; i < dyanValues.length; i++) {
                    if ("NOT_FOUND".endsWith(dyanValues[i])) {
                        model.addAttribute(SYSTEMERROR, getMessage(US_ERROR_APM_WORKID_ERROR_NOTFOUND, tempWorkId));
                    } else if ("INF".endsWith(dyanValues[i])) {
                        model.addAttribute(SYSTEMERROR, getMessage("us.error.apm.workID.error.inf", tempWorkId));
                    } else if ("PUB_DOM".endsWith(dyanValues[i])) {
                        model.addAttribute(SYSTEMERROR, getMessage("us.error.apm.workID.error.pubdomain", tempWorkId));
                    } else if ("WRK_GRD_VAL".endsWith(dyanValues[i])) {
                        model.addAttribute(SYSTEMERROR,
                            getMessage("us.error.apm.workID.error.arraignpercentage", tempWorkId));
                    }
                }
            } else if (pfex.getErrorKey().equals(US_ERROR_APM_LEARNEDMATCH_EXISTS)) {
                String[] dyanValues = pfex.getDynaValues();
                for (int i = 0; i < dyanValues.length; i++) {
                    model.addAttribute(SYSTEMERROR, getMessage(US_ERROR_APM_LEARNEDMATCH_EXISTS, dyanValues[i]));
                }
            } else {
                model.addAttribute(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            }
            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        } catch (Exception ex) {
            log.error("Exception caught in ApmPerfBulkRequestAction  :doSubmitEO() method", ex);
            model.addAttribute(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = EO_LEARNED_MATCHED_DETAILS;
        }
        if (!model.containsAttribute(SYSTEMERROR)) {
            model.addAttribute(SYSTEMMESSAGE, getMessage("us.message.apm.learnedmatch.multiplematch.update.success"));
            forwardKey = APM_LEARNED_MATCH_EO_PARAM;
        } else {
            apmLearnedMatchForm.setOperationType("EDIT");
        }

        log.debug("Exiting updateEOLearnedMatchMultiple in EOLearnedMatchMultiController");

        model.addAttribute(EOLEARNEDMATCH, apmLearnedMatchForm);
        return forwardKey;
    }
}
