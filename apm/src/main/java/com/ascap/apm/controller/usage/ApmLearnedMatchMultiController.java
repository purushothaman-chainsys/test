package com.ascap.apm.controller.usage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.ApmLearnedMatch;
import com.ascap.apm.vob.usage.ApmLearnedMatchList;
import com.ascap.apm.vob.usage.PerformanceSearch;

@Controller
@RequestMapping("/usage")
public class ApmLearnedMatchMultiController extends BaseUsageController {

    private static final String APMLEARNEDMATCHLIST = "/usage/apmLearnedMatchList";

    private static final String APMLEARNEDMATCHDETAILS = "/usage/apmLearnedMatchDetails";

    private static final String SYSTEM_ERROR = "systemerror";

    private static final String APMLEARNEDMATCH_LIST = "apmLearnedMatchList";

    private static final String SEARCH = "SEARCH";

    private static final String ERROR_MSG = "Error Caught in ApmLearnedMatchMultiController  :";

    private static final String LIST = "forward:/usage/apmLearnedMatch?navigationTypePar=CURR&operationTypePar=SEARCH";

    private static final String APMLEARNEDMATCHLISTFORM = "apmLearnedMatchListForm";

    private static final String APMLEARNEDMATCHFORM = "apmLearnedMatchForm";

    private static final String FIELD_ERRORS_PLACEHOLDER = "us.field.errors.placeholder";

    private static final String ERROR_APM_WORKID_ERROR_NOTFOUND = "us.error.apm.workID.error.notfound";

    private static final String ERROR_APM_LEARNEDMATCH_EXISTS = "us.error.apm.learnedmatch.exists";

    private static final String FUNC_EXECEPTION = "Functional Exception in ApmLearnedMatchMultiController  :";

    private static final String EXECEPTION =
        "Exception caught in ApmLearnedMatchMultiController  :apmLearnedMatchMultiOperation() method";

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/apmLearnedMatch")
    public ModelAndView apmLearnedMatchMultiOperation(
        @ModelAttribute("apmLearnedMatchList") ApmLearnedMatchList apmLearnedMatchList, HttpServletRequest request,
        HttpServletResponse response, ModelAndView view, BindingResult bindingResult,
        @RequestParam(required = false) String navigationTypePar,
        @RequestParam(required = false) String operationTypePar) throws Exception {
        String viewName = "";
        log.debug("Entering apmLearnedMatchMultiOperation of ApmLearnedMatchMultiController");
        log.debug("apmLearnedMatchList.operationType: " + apmLearnedMatchList.getOperationType());
        if (!ValidationUtils.isEmptyOrNull(navigationTypePar)) {
            apmLearnedMatchList.setNavigationType(navigationTypePar);
        }
        if (!ValidationUtils.isEmptyOrNull(operationTypePar)) {
            apmLearnedMatchList.setOperationType(operationTypePar);
        }
        if (ValidationUtils.isEmptyOrNull(apmLearnedMatchList.getOperationType())) {
            viewName = "usage/apmLearnedMatchList";
        } else {
            if ("UPDATE".equals(apmLearnedMatchList.getOperationType())) {
                viewName = updateApmLearnedMatch(apmLearnedMatchList, request, view);
            } else if ("DELETE".equals(apmLearnedMatchList.getOperationType())) {
                viewName = deleteApmLearnedMatchList(apmLearnedMatchList, request, view);
            } else if ("ADD".equals(apmLearnedMatchList.getOperationType())) {
                view.getModel().put(APMLEARNEDMATCHFORM, new ApmLearnedMatch());
                viewName = APMLEARNEDMATCHDETAILS;
            } else if ("MEDLEY_RETRIEVE".equals(apmLearnedMatchList.getOperationType())
                || "MEDLEY_NEW".equals(apmLearnedMatchList.getOperationType())) {
                viewName = buildMedleyGroups(apmLearnedMatchList, request, view);
            } else if (SEARCH.equals(apmLearnedMatchList.getOperationType())
                || ValidationUtils.isEmptyOrNull(apmLearnedMatchList.getOperationType())
                || "CANCEL".equals(apmLearnedMatchList.getOperationType())) {
                viewName = getApmLearnedMatchList(apmLearnedMatchList, request, view);
            }
        }
        log.debug("Exiting apmLearnedMatchMultiOperation of ApmLearnedMatchMultiController");

        view.setViewName(viewName);
        return view;
    }

    @RequestMapping(value = "/apmLearnedMatchDetails")
    public ModelAndView apmLearnedMatchAdd(@ModelAttribute("apmLearnedMatch") ApmLearnedMatch apmLearnedMatch,
        HttpServletRequest request, HttpServletResponse response, ModelAndView view, BindingResult bindingResult)
        throws Exception {
        String viewName = "";
        log.debug("Entering apmLearnedMatchMultiOperation of ApmLearnedMatchMultiController");
        log.debug("apmLearnedMatchList.operationType: " + apmLearnedMatch.getOperationType());
        if ("CREATE_LEARNED_MATCH".equals(apmLearnedMatch.getOperationType())) {
            viewName = createApmLearnedMatch(apmLearnedMatch, request, view);
        }
        if ("CANCEL".equals(apmLearnedMatch.getOperationType())) {
            viewName = LIST;
        } else if ("UPDATE_LEARNED_MATCH".equals(apmLearnedMatch.getOperationType())) {
            viewName = updateApmLearnedMatchMultiple(apmLearnedMatch, request, view);
        }
        log.debug("Exiting apmLearnedMatchAdd of ApmLearnedMatchMultiController");
        view.setViewName(viewName);
        return view;
    }

    private String getApmLearnedMatchList(ApmLearnedMatchList apmLearnedMatchListForm, HttpServletRequest request,
        ModelAndView view) throws Exception {

        log.debug("Entering getApmLearnedMatchList in ApmLearnedMatchMultiController");
        String forwardKey = null;

        String tempWorkId = apmLearnedMatchListForm.getFilterWorkId();
        if (!ValidationUtils.isEmptyOrNull(apmLearnedMatchListForm.getFilterWorkId())) {
            try {
                apmLearnedMatchListForm
                    .setFilterWorkId(String.valueOf(Long.parseLong(apmLearnedMatchListForm.getFilterWorkId())));
            } catch (Exception e) {
                view.getModel().put(SYSTEMMESSAGE,
                    getMessage("us.error.apm.archives.workid.nonnumber", tempWorkId));
                return APMLEARNEDMATCHLIST;
            }
        }

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        ApmLearnedMatchList apmLearnedMatchList = null;

        log.debug("apmLearnedMatchListForm.getNavigationType() " + apmLearnedMatchListForm.getNavigationType());

        if (apmLearnedMatchListForm.getNavigationType() == null
            || "FIRST".equalsIgnoreCase(apmLearnedMatchListForm.getNavigationType())
            || "".equalsIgnoreCase(apmLearnedMatchListForm.getNavigationType().trim())) {

            apmLearnedMatchList = new ApmLearnedMatchList();

            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            apmLearnedMatchListForm.setUserPreferences(userPreferences);
            BeanUtils.copyProperties(apmLearnedMatchList, apmLearnedMatchListForm);

        } else {
            if (inputContext.getUserSessionState().getSearch() != null) {
                try {
                    apmLearnedMatchList =
                        (ApmLearnedMatchList) inputContext.getUserSessionState().getSearch().getAlternateSearch();
                } catch (Exception e) {
                    // TO DO
                }
                if (apmLearnedMatchList == null) {
                    apmLearnedMatchList = new ApmLearnedMatchList();
                }
                apmLearnedMatchList.setNavigationType(apmLearnedMatchListForm.getNavigationType());

            } else {
                inputContext.getUserSessionState().setSearch(new PerformanceSearch());
                apmLearnedMatchList = new ApmLearnedMatchList();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmLearnedMatchListForm.setUserPreferences(userPreferences);

                BeanUtils.copyProperties(apmLearnedMatchList, apmLearnedMatchListForm);

            }
        }

        PerformanceSearch performanceSearch = null;

        List<Object> outValueObjects = null;

        inputContext.addInputValueObject(apmLearnedMatchList);
        try {
            outputContext = usageHandler.getApmLearnedMatchList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmLearnedMatchList = (ApmLearnedMatchList) outValueObjects.iterator().next();

                apmLearnedMatchList.setSelectedIndex(null);
                view.getModel().put(APMLEARNEDMATCH_LIST, apmLearnedMatchList);
                apmLearnedMatchList.setNavigationType(null);
                if (inputContext.getUserSessionState().getSearch() == null) {
                    performanceSearch = new PerformanceSearch();
                    performanceSearch.setAlternateSearch(apmLearnedMatchList);
                    inputContext.getUserSessionState().setSearch(performanceSearch);
                } else {
                    inputContext.getUserSessionState().getSearch().setAlternateSearch(apmLearnedMatchList);
                }
            }
            // set the UserSessionState object to the session.
            setUserSessionState(request, inputContext.getUserSessionState());
            forwardKey = APMLEARNEDMATCHLIST;
        } catch (PrepSystemException pse) {
            log.debug(ERROR_MSG + pse);
            log.error(ERROR_MSG, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = APMLEARNEDMATCHLIST;
        } catch (PrepFunctionalException pfex) {
            log.debug(ERROR_MSG + pfex);
            log.warn(FUNC_EXECEPTION + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = APMLEARNEDMATCHLIST;
        } catch (Exception ex) {
            log.debug(ERROR_MSG + ex);
            log.error(EXECEPTION, ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = APMLEARNEDMATCHLIST;
        }

        log.debug("Exiting getApmLearnedMatchList in ApmLearnedMatchMultiController");

        return forwardKey;

    }

    private String deleteApmLearnedMatchList(ApmLearnedMatchList apmFileListForm, HttpServletRequest request,
        ModelAndView view) {
        log.debug("Entering deleteApmLearnedMatchList in ApmLearnedMatchMultiController");
        String forwardKey = APMLEARNEDMATCHLIST;
        ApmLearnedMatchList apmFileList = new ApmLearnedMatchList();
        String deleteMessage = "us.message.apm.learnedmatch.delete.success";
        try {

            BeanUtils.copyProperties(apmFileList, apmFileListForm);

            List<Object> col = new ArrayList<>();
            String[] selectedIndex = apmFileListForm.getSelectedIndex();
            String[] workId = apmFileListForm.getWorkId();
            String[] supplierCode = apmFileListForm.getSupplierCode();
            String[] workTitle = apmFileListForm.getWorkTitle();
            String[] performers = apmFileListForm.getPerformerName();
            String[] writerNames = apmFileListForm.getWriterName();
            String[] originalWorkId = apmFileListForm.getOriginalWorkId();
            String[] leanredDeleteFlag = apmFileListForm.getLearnedDeleteFlag();
            String[] multWorkId = apmFileListForm.getMultWorkId();
            String[] lmType = apmFileListForm.getLmType();

            ApmLearnedMatch apmFile = null;
            for (String index : selectedIndex) {
                int indexNum = Integer.parseInt(index);
                apmFile = new ApmLearnedMatch();
                if (!ValidationUtils.isEmptyOrNull(workId[indexNum])) {
                    apmFile.setWorkId(String.valueOf(Long.parseLong(workId[indexNum])));
                }
                apmFile.setOriginalWorkId(originalWorkId[indexNum]);
                apmFile.setSupplierCode(supplierCode[indexNum]);
                apmFile.setWorkTitle(workTitle[indexNum]);
                apmFile.setPerformerName(performers[indexNum]);
                apmFile.setWriterName(writerNames[indexNum]);
                apmFile.setLearnedDeleteFlag(leanredDeleteFlag[indexNum]);
                apmFile.setMultWorkId(multWorkId[indexNum]);
                apmFile.setLmType(lmType[indexNum]);
                apmFile.setUserId(apmFileListForm.getUserId());

                col.add(apmFile);

                log.debug("Added object index " + indexNum + " ApmLearnedMatch: " + apmFile);
            }
            apmFileList.setSearchResults(col);

            List<Object> outValueObjects = null;

            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmFileList);

            PREPContext outputContext = null;

            outputContext = usageHandler.deleteApmLearnedMatchList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmFileList = (ApmLearnedMatchList) outValueObjects.iterator().next();
                if (apmFileList.getInvalidGroups() != null && !apmFileList.getInvalidGroups().isEmpty()) {
                } else {
                    apmFileListForm.setOperationType(SEARCH);
                    apmFileListForm.setNavigationType("CURR");
                    return LIST;
                }
            } else {
                apmFileListForm.setSelectedIndex(null);
                view.getModel().put(SYSTEMMESSAGE, getMessage(deleteMessage));
                log.debug("Exiting deleteApmLearnedMatchList of ApmLearnedMatchMultiController");
                return LIST;
            }
        } catch (PrepSystemException pse) {
            log.debug(ERROR_MSG + pse);
            log.error(ERROR_MSG, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = APMLEARNEDMATCHLIST;
        } catch (PrepFunctionalException pfex) {
            log.debug(ERROR_MSG + pfex);
            log.warn(FUNC_EXECEPTION + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = APMLEARNEDMATCHLIST;
        } catch (Exception ex) {
            log.debug(ERROR_MSG + ex);
            log.error(EXECEPTION, ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = APMLEARNEDMATCHLIST;
        }

        view.getModel().put(SYSTEMMESSAGE, getMessage(deleteMessage));

        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting deleteApmLearnedMatchList of ApmLearnedMatchMultiController");
        return forwardKey;

    }

    private String updateApmLearnedMatch(ApmLearnedMatchList apmFileListForm, HttpServletRequest request,
        ModelAndView view) {
        String forwardKey = APMLEARNEDMATCHLIST;

        ApmLearnedMatchList apmFileList = new ApmLearnedMatchList();
        try {

            BeanUtils.copyProperties(apmFileList, apmFileListForm);

            List<Object> col = new ArrayList<>();
            String[] selectedIndex = apmFileListForm.getSelectedIndex();
            String[] workId = apmFileListForm.getWorkId();
            String[] supplierCode = apmFileListForm.getSupplierCode();
            String[] workTitle = apmFileListForm.getWorkTitle();
            String[] performers = apmFileListForm.getPerformerName();
            String[] originalWorkId = apmFileListForm.getOriginalWorkId();
            String[] leanredDeleteFlag = apmFileListForm.getLearnedDeleteFlag();
            String[] writerName = apmFileListForm.getWriterName();
            String[] lmType = apmFileListForm.getLmType();
            ApmLearnedMatch apmFile = null;
            for (String index : selectedIndex) {
                int indexNum = Integer.parseInt(index);
                apmFile = new ApmLearnedMatch();
                apmFile.setWorkId(String.valueOf(Long.parseLong(workId[indexNum])));
                apmFile.setOriginalWorkId(originalWorkId[indexNum]);
                apmFile.setSupplierCode(supplierCode[indexNum]);
                apmFile.setWorkTitle(workTitle[indexNum]);
                apmFile.setPerformerName(performers[indexNum]);
                apmFile.setLearnedDeleteFlag(leanredDeleteFlag[indexNum]);
                apmFile.setWriterName(writerName[indexNum]);
                apmFile.setUserId(apmFileListForm.getUserId());
                apmFile.setLmType(lmType[indexNum]);
                col.add(apmFile);

                log.debug("Added object index " + indexNum + " ApmLearnedMatch: " + apmFile);
            }
            apmFileList.setSearchResults(col);

            List<Object> outValueObjects = null;

            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmFileList);

            PREPContext outputContext = null;

            outputContext = usageHandler.updateApmLearnedMatch(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmFileList = (ApmLearnedMatchList) outValueObjects.iterator().next();

                if (apmFileList.getInvalidGroups() != null && !apmFileList.getInvalidGroups().isEmpty()) {
                    apmFileListForm = populateForm(apmFileListForm, apmFileList.getInvalidGroups(), request);

                    request.setAttribute(APMLEARNEDMATCHLISTFORM, apmFileListForm);

                    Iterator<Object> itr = apmFileList.getInvalidGroups().iterator();
                    ApmLearnedMatch errApmPerformanceBulkRequest = null;
                    while (itr.hasNext()) {
                        errApmPerformanceBulkRequest = (ApmLearnedMatch) itr.next();
                        List<Object> colInner = errApmPerformanceBulkRequest.getWorkIdErrors();
                        if (colInner != null && !colInner.isEmpty()) {
                            Iterator<Object> ir = colInner.iterator();
                            String errString = "";
                            while (ir.hasNext()) {
                                errString = (String) ir.next();
                                view.getModel().put(SYSTEMERROR,
                                    getMessage(FIELD_ERRORS_PLACEHOLDER, errString));
                            }
                        }

                    }
                    return APMLEARNEDMATCHLIST;

                } else {
                    apmFileListForm.setOperationType(SEARCH);
                    apmFileListForm.setNavigationType("CURR");
                    request.setAttribute(APMLEARNEDMATCHLISTFORM, apmFileListForm);
                    return LIST;
                }
            } else {
                apmFileListForm.setSelectedIndex(null);
                if (!view.getModel().containsKey(SYSTEMERROR)) {
                    view.getModel().put(SYSTEMMESSAGE, getMessage("us.message.apm.learnedmatch.update.success"));
                    return LIST;
                }
            }
        } catch (PrepSystemException pse) {
            log.debug(ERROR_MSG + pse);
            log.error(ERROR_MSG, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = APMLEARNEDMATCHLIST;
        } catch (PrepFunctionalException pfex) {
            log.debug(ERROR_MSG + pfex);
            log.warn(FUNC_EXECEPTION + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = APMLEARNEDMATCHLIST;
        } catch (Exception ex) {
            log.debug(ERROR_MSG + ex);
            log.error(EXECEPTION, ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = APMLEARNEDMATCHLIST;
        }

        view.getModel().put(SYSTEMMESSAGE, getMessage("us.message.apm.learnedmatch.update.success"));
        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting updateApmLearnedMatch of ApmLearnedMatchMultiController");

        return forwardKey;

    }

    private String buildMedleyGroups(ApmLearnedMatchList apmLearnedMatchListForm, HttpServletRequest request,
        ModelAndView view) throws Exception {

        log.debug("Entering buildMedleyGroups in ApmLearnedMatchMultiController");

        String forwardKey = null;

        ApmLearnedMatch apmLearnedMatchForm = new ApmLearnedMatch();

        String[] multWorkId = apmLearnedMatchListForm.getMultWorkId();
        String[] selectedIndex = apmLearnedMatchListForm.getSelectedIndex();
        String[] supplierCodeArr = apmLearnedMatchListForm.getSupplierCode();
        String[] workTitleArr = apmLearnedMatchListForm.getWorkTitle();
        String[] performerNameArr = apmLearnedMatchListForm.getPerformerName();
        String[] writerNameArr = apmLearnedMatchListForm.getWriterName();
        String[] workIdArr = apmLearnedMatchListForm.getWorkId();
        String[] cloneCountArr = apmLearnedMatchListForm.getCloneCount();
        String[] lmTypeArr = apmLearnedMatchListForm.getLmType();

        String supplierCode = null;
        String performerName = null;
        String workTitle = null;
        String workId = null;
        String writerName = null;
        String cloneCount = null;
        String lmType = null;

        String multWorkIdValue = null;
        for (String index : selectedIndex) {
            int indexNum = Integer.parseInt(index);
            multWorkIdValue = multWorkId[indexNum];
            performerName = performerNameArr[indexNum];
            writerName = writerNameArr[indexNum];
            workTitle = workTitleArr[indexNum];
            supplierCode = supplierCodeArr[indexNum];
            workId = workIdArr[indexNum];
            cloneCount = cloneCountArr[indexNum];
            lmType = lmTypeArr[indexNum];
        }

        apmLearnedMatchListForm.setMedleyMultiWorkId(multWorkIdValue);

        if ("MEDLEY_NEW".equals(apmLearnedMatchListForm.getOperationType())) {
            apmLearnedMatchForm.setApmMatchType("N");
            apmLearnedMatchForm.setOperationType("EDIT");
            apmLearnedMatchForm.setSupplierCode(supplierCode);
            apmLearnedMatchForm.setWorkTitle(workTitle);
            apmLearnedMatchForm.setPerformerName(performerName);
            apmLearnedMatchForm.setWriterName(writerName);
            apmLearnedMatchForm.setLmType(lmType);
            List<String> workList = new ArrayList<>();
            workList.add(workId);
            String[] ccCounts = new String[1];
            ccCounts[0] = cloneCount;
            apmLearnedMatchForm.setWorkIdCollection(workList);
            apmLearnedMatchForm.setMedleyCloneCounts(ccCounts);
            view.getModel().put(APMLEARNEDMATCHFORM, apmLearnedMatchForm);
            request.setAttribute(APMLEARNEDMATCHFORM, apmLearnedMatchForm);
            return APMLEARNEDMATCHDETAILS;
        }

        ApmLearnedMatchList apmLearnedMatchList = new ApmLearnedMatchList();

        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        ApmLearnedMatch apmLearnedMatch = null;

        List<Object> outValueObjects = null;

        String learnedMatchType = null;

        // multWorkIdValue
        if (ValidationUtils.isEmptyOrNull(performerName)) {
            learnedMatchType = "APM_LEARNED_MATCH_WTR";
            lmType = "WTR";
            apmLearnedMatchList.setLearnedMatchType(learnedMatchType);
        } else {
            learnedMatchType = "APM_LEARNED_MATCH";
            lmType = "PFR";
            apmLearnedMatchList.setLearnedMatchType(learnedMatchType);
        }
        apmLearnedMatchList.setMedleyMultiWorkId(multWorkIdValue);
        inputContext.addInputValueObject(apmLearnedMatchList);
        try {
            outputContext = usageHandler.getLearnedMatchMedleyWorkInformation(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmLearnedMatch = (ApmLearnedMatch) outValueObjects.iterator().next();
                BeanUtils.copyProperties(apmLearnedMatchForm, apmLearnedMatch);
                apmLearnedMatchForm.setLearnedMatchType(learnedMatchType);
                apmLearnedMatchForm.setLmType(lmType);
                apmLearnedMatchForm.setOperationType("EDIT");
                request.setAttribute(APMLEARNEDMATCHFORM, apmLearnedMatchForm);
            }
        } catch (PrepSystemException pse) {
            log.debug(ERROR_MSG + pse);
            log.error(ERROR_MSG, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
        } catch (PrepFunctionalException pfex) {
            log.debug(ERROR_MSG + pfex);
            log.warn(FUNC_EXECEPTION + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
        } catch (Exception ex) {
            log.debug(ERROR_MSG + ex);
            log.error(EXECEPTION, ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
        }

        log.debug("Exiting buildMedleyGroups in ApmLearnedMatchMultiController");
        view.getModel().put(APMLEARNEDMATCHFORM, apmLearnedMatchForm);
        forwardKey = APMLEARNEDMATCHDETAILS;
        return forwardKey;
    }

    private ApmLearnedMatchList populateForm(ApmLearnedMatchList apmFileListForm, List<Object> invalidGroups,
        HttpServletRequest request) {

        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
        apmFileListForm.setUserPreferences(userPreferences);

        apmFileListForm.setNumberOfRecordsFound(Integer.parseInt(request.getParameter("tempNoOfResults")));
        apmFileListForm.setPageNumber(request.getParameter("tempCurPageNr"));
        apmFileListForm.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
            * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));

        apmFileListForm.setOperationType(SEARCH);

        String[] workId = apmFileListForm.getWorkId();
        String[] supplierCode = apmFileListForm.getSupplierCode();
        String[] workTitle = apmFileListForm.getWorkTitle();
        String[] performers = apmFileListForm.getPerformerName();
        String[] learnDeleteFlags = apmFileListForm.getLearnedDeleteFlag();
        String[] originalWorkIds = apmFileListForm.getOriginalWorkId();
        String[] writerNames = apmFileListForm.getWriterName();
        String[] lmTypes = apmFileListForm.getLmType();
        String[] multWorkIds = apmFileListForm.getMultWorkId();

        List<Object> col = new ArrayList<>();
        ApmLearnedMatch pmPerformanceBulkRequest = null;

        for (int indexNum = 0; indexNum < workTitle.length; indexNum++) {
            pmPerformanceBulkRequest = new ApmLearnedMatch();
            pmPerformanceBulkRequest.setWorkId(workId[indexNum]);

            if (hasWorkIdErrors(workId[indexNum], invalidGroups)) {
                pmPerformanceBulkRequest.setInvalidWorkId("Y");
            }

            pmPerformanceBulkRequest.setSupplierCode(supplierCode[indexNum]);
            pmPerformanceBulkRequest.setWorkTitle(workTitle[indexNum]);
            pmPerformanceBulkRequest.setPerformerName(performers[indexNum]);
            pmPerformanceBulkRequest.setLearnedDeleteFlag(learnDeleteFlags[indexNum]);
            pmPerformanceBulkRequest.setOriginalWorkId(originalWorkIds[indexNum]);
            pmPerformanceBulkRequest.setWriterName(writerNames[indexNum]);
            pmPerformanceBulkRequest.setLmType(lmTypes[indexNum]);
            pmPerformanceBulkRequest.setMultWorkId(multWorkIds[indexNum]);
            if ("Y".equals(learnDeleteFlags[indexNum])) {
                pmPerformanceBulkRequest.setLearnType("Delete");
            } else {
                pmPerformanceBulkRequest.setLearnType("Match");
            }
            col.add(pmPerformanceBulkRequest);
        }

        apmFileListForm.setSearchResults(col);
        return apmFileListForm;
    }

    private boolean hasWorkIdErrors(String workId, List<Object> errorCol) {
        boolean workIdError = false;

        if (errorCol != null) {
            Iterator<Object> itr = errorCol.iterator();
            ApmLearnedMatch apmPerformanceBulkRequest = null;
            while (itr.hasNext()) {
                apmPerformanceBulkRequest = (ApmLearnedMatch) itr.next();
                if (apmPerformanceBulkRequest.getWorkId().equals(workId)) {
                    return true;
                }
            }
        }

        return workIdError;
    }

    private String createApmLearnedMatch(ApmLearnedMatch apmLearnedMatchForm, HttpServletRequest request,
        ModelAndView view) {
        log.debug("Entering createApmLearnedMatch in ApmLearnedMatchMultiController");

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        ApmLearnedMatch apmLearnedMatch = new ApmLearnedMatch();
        List<String> workList = new ArrayList<>();
        List<String> medleyCloneCounts = new ArrayList<>();
        String forwardKey = null;

        List<Object> outValueObjects = null;
        String tempWorkId = apmLearnedMatchForm.getWorkId();
        if (!ValidationUtils.isEmptyOrNull(apmLearnedMatchForm.getWorkId())) {
            try {
                tempWorkId = apmLearnedMatchForm.getWorkId();
                apmLearnedMatchForm.setWorkId(String.valueOf(Long.parseLong(tempWorkId)));
            } catch (Exception e) {
                view.getModel().put(SYSTEMERROR,
                    getMessage(ERROR_APM_WORKID_ERROR_NOTFOUND, tempWorkId));
                return APMLEARNEDMATCHDETAILS;
            }
        }

        if (apmLearnedMatchForm.getWorkIds() != null) {
            log.debug("Work Id Length::::::::::::::::::::::: " + apmLearnedMatchForm.getWorkIds().length);
            String[] wrkArray = apmLearnedMatchForm.getWorkIds();
            String[] medleyCloneCountsArray = apmLearnedMatchForm.getMedleyCloneCounts();
            for (String w : wrkArray) {
                log.debug("work id:" + w + ":");
                if (!ValidationUtils.isEmptyOrNull(w) && !workList.contains(w)) {
                    log.debug("Adding work Id " + w);
                    workList.add(w);
                }
            }
            for (String cc : medleyCloneCountsArray) {
                log.debug("Clone Count :" + cc + ":");
                if (!ValidationUtils.isEmptyOrNull(cc)) {
                    log.debug("Adding Clone Count: " + cc);
                    medleyCloneCounts.add(cc);
                }
            }
            apmLearnedMatchForm.setWorkIdCollection(workList);
            apmLearnedMatchForm.setCloneCountsCollection(medleyCloneCounts);
        }

        if (workList.size() == 1) {
            tempWorkId = workList.get(0);
        }

        try {
            BeanUtils.copyProperties(apmLearnedMatch, apmLearnedMatchForm);
            inputContext.addInputValueObject(apmLearnedMatch);

            outputContext = usageHandler.addApmLearnedMatch(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && !(outValueObjects.isEmpty())) {
                apmLearnedMatch = (ApmLearnedMatch) outValueObjects.iterator().next();

                if (apmLearnedMatch.getWorkIdErrors() != null && !apmLearnedMatch.getWorkIdErrors().isEmpty()) {

                    request.setAttribute(APMLEARNEDMATCHFORM, apmLearnedMatchForm);

                    Iterator<Object> itr = apmLearnedMatch.getWorkIdErrors().iterator();
                    String errorMsg = null;
                    while (itr.hasNext()) {
                        errorMsg = (String) itr.next();
                        view.getModel().put(SYSTEMERROR, getMessage(FIELD_ERRORS_PLACEHOLDER, errorMsg));
                    }
                    return APMLEARNEDMATCHDETAILS;

                }

                BeanUtils.copyProperties(apmLearnedMatchForm, apmLearnedMatch);
            }

            request.setAttribute(APMLEARNEDMATCHFORM, apmLearnedMatchForm);
            forwardKey = APMLEARNEDMATCHDETAILS;
        } catch (PrepSystemException pse) {
            log.error(ERROR_MSG, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = APMLEARNEDMATCHDETAILS;
        } catch (PrepFunctionalException pfex) {
            log.warn(FUNC_EXECEPTION + pfex);
            if (pfex.getErrorKey().equals("error.apm.learnedmatch.invalid.workID")) {
                String[] dyanValues = pfex.getDynaValues();
                for (int i = 0; i < dyanValues.length; i++) {
                    if ("NOT_FOUND".endsWith(dyanValues[i])) {
                        view.getModel().put(SYSTEMERROR,
                            getMessage(ERROR_APM_WORKID_ERROR_NOTFOUND, tempWorkId));
                    } else if ("INF".endsWith(dyanValues[i])) {
                        view.getModel().put(SYSTEMERROR,
                            getMessage("us.error.apm.workID.error.inf", tempWorkId));
                    } else if ("PUB_DOM".endsWith(dyanValues[i])) {
                        view.getModel().put(SYSTEMERROR,
                            getMessage("us.error.apm.workID.error.pubdomain", tempWorkId));
                    } else if ("WRK_GRD_VAL".endsWith(dyanValues[i])) {
                        view.getModel().put(SYSTEMERROR,
                            getMessage("us.error.apm.workID.error.arraignpercentage", tempWorkId));
                    }
                }
            } else if (pfex.getErrorKey().equals(ERROR_APM_LEARNEDMATCH_EXISTS)) {
                log.debug("in error condition");
                String[] dyanValues = pfex.getDynaValues();
                for (int i = 0; i < dyanValues.length; i++) {
                    view.getModel().put(SYSTEMERROR,
                        getMessage(ERROR_APM_LEARNEDMATCH_EXISTS, dyanValues[i]));
                }
            }
            //
            else {
                view.getModel().put(SYSTEMERROR, pfex.getErrorKey());
            }
            view.getModel().put(APMLEARNEDMATCHFORM, apmLearnedMatchForm);
            forwardKey = APMLEARNEDMATCHDETAILS;
        } catch (Exception ex) {
            log.error(EXECEPTION, ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = APMLEARNEDMATCHDETAILS;
        }
        if (!view.getModel().containsKey(SYSTEMERROR)) {
            view.getModel().put(SYSTEMMESSAGE, getMessage("us.message.apm.learnedmatch.create.success"));
            forwardKey = LIST;
        }
        log.debug("Exiting createApmLearnedMatch in ApmLearnedMatchMultiController");

        return forwardKey;

    }

    private String updateApmLearnedMatchMultiple(ApmLearnedMatch apmLearnedMatchForm, HttpServletRequest request,
        ModelAndView view) {
        log.debug("Entering updateApmLearnedMatchMultiple in ApmLearnedMatchMultiController");

        String forwardKey = null;

        log.debug("mult work id " + apmLearnedMatchForm.getMultWorkId());
        log.debug("getLearnedMatchType " + apmLearnedMatchForm.getLearnedMatchType());
        log.debug("getWriterName " + apmLearnedMatchForm.getWriterName());
        log.debug("getPerformerName " + apmLearnedMatchForm.getPerformerName());
        log.debug("getSupplierCode " + apmLearnedMatchForm.getSupplierCode());

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        ApmLearnedMatch apmLearnedMatch = new ApmLearnedMatch();
        List<String> workList = new ArrayList<>();
        List<String> medleyCloneCounts = new ArrayList<>();

        List<Object> outValueObjects = null;
        String tempWorkId = apmLearnedMatchForm.getWorkId();
        if (!ValidationUtils.isEmptyOrNull(apmLearnedMatchForm.getWorkId())) {
            try {
                apmLearnedMatchForm.setWorkId(String.valueOf(Long.parseLong(apmLearnedMatchForm.getWorkId())));
                tempWorkId = apmLearnedMatchForm.getWorkId();
            } catch (Exception e) {
                view.getModel().put(SYSTEMERROR,
                    getMessage(ERROR_APM_WORKID_ERROR_NOTFOUND, tempWorkId));
                return APMLEARNEDMATCHDETAILS;
            }
        }

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

            String learnedMatchType = null;

            // multWorkIdValue
            if (ValidationUtils.isEmptyOrNull(apmLearnedMatchForm.getPerformerName())) {
                learnedMatchType = "APM_LEARNED_MATCH_WTR";
                apmLearnedMatchForm.setLearnedMatchType(learnedMatchType);
            } else {
                learnedMatchType = "APM_LEARNED_MATCH";
                apmLearnedMatchForm.setLearnedMatchType(learnedMatchType);
            }

        }

        if (workList.size() == 1) {
            tempWorkId = workList.get(0);
        }

        try {
            BeanUtils.copyProperties(apmLearnedMatch, apmLearnedMatchForm);
            inputContext.addInputValueObject(apmLearnedMatch);

            outputContext = usageHandler.updateApmLearnedMatchMultiple(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmLearnedMatch = (ApmLearnedMatch) outValueObjects.iterator().next();
                if (apmLearnedMatch.getWorkIdErrors() != null && !apmLearnedMatch.getWorkIdErrors().isEmpty()) {
                    apmLearnedMatchForm.setOperationType("EDIT");
                    request.setAttribute(APMLEARNEDMATCHFORM, apmLearnedMatchForm);

                    Iterator<Object> itr = apmLearnedMatch.getWorkIdErrors().iterator();
                    String errorMsg = null;
                    while (itr.hasNext()) {
                        errorMsg = (String) itr.next();
                        view.getModel().put(SYSTEMERROR, getMessage(FIELD_ERRORS_PLACEHOLDER, errorMsg));
                    }
                    return APMLEARNEDMATCHDETAILS;
                }

                request.setAttribute(APMLEARNEDMATCHFORM, apmLearnedMatchForm);
                forwardKey = APMLEARNEDMATCHDETAILS;
            }
        } catch (PrepSystemException pse) {
            log.error(ERROR_MSG, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = APMLEARNEDMATCHDETAILS;
        } catch (PrepFunctionalException pfex) {
            log.warn(FUNC_EXECEPTION + pfex);
            if (pfex.getErrorKey().equals("error.apm.learnedmatch.invalid.workID")) {
                String[] dyanValues = pfex.getDynaValues();
                for (int i = 0; i < dyanValues.length; i++) {
                    if ("NOT_FOUND".endsWith(dyanValues[i])) {
                        view.getModel().put(SYSTEMERROR,
                            getMessage(ERROR_APM_WORKID_ERROR_NOTFOUND, tempWorkId));
                    } else if ("INF".endsWith(dyanValues[i])) {
                        view.getModel().put(SYSTEMERROR,
                            getMessage("us.error.apm.workID.error.inf", tempWorkId));
                    } else if ("PUB_DOM".endsWith(dyanValues[i])) {
                        view.getModel().put(SYSTEMERROR,
                            getMessage("us.error.apm.workID.error.pubdomain", tempWorkId));
                    } else if ("WRK_GRD_VAL".endsWith(dyanValues[i])) {
                        view.getModel().put(SYSTEMERROR,
                            getMessage("us.error.apm.workID.error.arraignpercentage", tempWorkId));
                    }
                }
            } else if (pfex.getErrorKey().equals(ERROR_APM_LEARNEDMATCH_EXISTS)) {
                String[] dyanValues = pfex.getDynaValues();
                for (int i = 0; i < dyanValues.length; i++) {
                    view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey(), dyanValues[i]));
                }
            }
            //
            else {
                view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            }
            forwardKey = APMLEARNEDMATCHDETAILS;
        } catch (Exception ex) {
            log.error(EXECEPTION, ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = APMLEARNEDMATCHDETAILS;
        }
        if (!view.getModel().containsKey(SYSTEMERROR)) {
            view.getModel().put(SYSTEMMESSAGE, getMessage("us.message.apm.learnedmatch.multiplematch.update.success"));
            forwardKey = LIST;
        } else {
            apmLearnedMatchForm.setOperationType("EDIT");
        }

        log.debug("Exiting updateApmLearnedMatchMultiple in ApmLearnedMatchMultiController");

        return forwardKey;

    }

}
