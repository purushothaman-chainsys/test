package com.ascap.apm.controller.usage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.DebugHelperUtils;
import com.ascap.apm.common.utils.UsageCommonValidations;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.constants.Constants;
import com.ascap.apm.controller.utils.HtmlSelectOption;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.BaseSearchVOB;
import com.ascap.apm.vob.InfoBarItemVOB;
import com.ascap.apm.vob.usage.PerformanceSearch;
import com.ascap.apm.vob.usage.ProgramPerformance;
import com.ascap.apm.vob.usage.UsageInfoBar;
import com.ascap.apm.vob.usage.WorkPerformance;

/**
 * Handles the Home Search of Program Performances, Work Performances Searches.
 * 
 * @author Manoj_Puli
 */
@Controller
@RequestMapping("/usage")
public class UsageSearchMultiController extends BaseUsageController {

    private static final String USPROGRAMPERFORMANCESSEARCHRESULTS = "/usage/usProgramPerformancesSearchResults";

    private static final String USPROGRAMPERFORMANCE = "/usage/usProgramPerformance";

    private static final String USHOMESEARCH = "/usage/usHomeSearch";

    private static final String USWORKPERFORMANCE = "/usage/usWorkPerformance";

    private static final String USWORKPERFORMANCESSEARCHRESULTS = "/usage/usWorkPerformancesSearchResults";

    private static final String ERROR_USAGE_GENERIC_REQUIRED_DELETE = "error.usage.generic.required.delete";

    private static final String SYSTEMERROR = "systemerror";

    private static final String SYSTEMMESSAGE = "systemmessage";

    private static final String RETRIEVE_WORK_PERFORMANCES_LIST = "RETRIEVE_WORK_PERFORMANCES_LIST";

    private static final String SEARCH_PROGRAM_PERFORMANCES = "SEARCH_PROGRAM_PERFORMANCES";

    private static final String PERFORMANCESEARCH = "performanceSearch";

    private static final String PROGRAMPERFORMANCE = "programPerformance";

    private static final String WORKPERFORMANCE = "workPerformance";

    private static final String PERFORMANCE_HEADER = "Performance Header";

    private static final String LOG_ERRORMESSAGE = "Error Caught in deleteProgramPerformance :";

    private static final String MESSAGE_USAGE_GENERIC_SUCCESS_UPDATED = "message.usage.generic.success.updated";

    private static final String MESSAGE_USAGE_GENERIC_SUCCESS_DELETED = "message.usage.generic.success.deleted";

    private static final String ACTIONTYPE = "actionType";

    private static final String FORWARDKEY = "forwardKey is";

    private static final String APM_PERF_HEADER_ID = "APM Perf Header ID";

    private static final String MUSIC_USER_ID = "Music User ID";

    private static final String LICENSE_TYPE = "License Type";

    private static final String SYSTEM_ERROR = "system.error";

    /**
     * Constructor
     */
    public UsageSearchMultiController() {
        super();
    }

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/usageHomeSearch", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView usageSearchMultiOperation(
        @ModelAttribute("performanceSearch") PerformanceSearch performanceSearch, HttpServletRequest request,
        HttpServletResponse response, ModelAndView view, BindingResult bindingResult) {
        log.debug("Entering usageSearchMultiOperation method in UsageSearchMultiController");
        String actionSelected = null;
        String viewName = "";
        try {
            actionSelected = performanceSearch.getActionSelected();

            log.debug("request.getParameter(actionType) :" + request.getParameter(ACTIONTYPE));
            log.debug("actionSelected :" + actionSelected);
            log.debug("navigationType :" + performanceSearch.getNavigationType());
            log.debug("performanceSearchType :" + performanceSearch.getPerformanceSearchType());

            if (performanceSearch.getNavigationType() != null
                && performanceSearch.getNavigationType().equals(Constants.BACK_TO_SEARCH_CRITERIA)) {
                viewName = backToSearchCriteria(view, request);
                view.setViewName(viewName);
                return view;
            }
            if (request.getParameter(ACTIONTYPE) != null && "CRITERIA".equals(request.getParameter(ACTIONTYPE))) {
                view.setViewName(USHOMESEARCH);
                return view;
            }
            if (actionSelected != null) {
                if (SEARCH_PROGRAM_PERFORMANCES.equalsIgnoreCase(actionSelected)) {
                    viewName = searchProgramPerformances(performanceSearch, request, view);
                } else if ("RETRIEVE_PROGRAM_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                    viewName = retrieveProgramPerformance(performanceSearch, request, view, bindingResult);
                } else if ("DELETE_PROGRAM_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                    viewName = deleteProgramPerformance(performanceSearch, request, view);
                } else if ("UPDATE_ASSIGNED_USERS".equalsIgnoreCase(actionSelected)) {
                    log.debug("UPDATING ASSINGED USERSSS..................................");
                    viewName = updateAssignedUsers(performanceSearch, request, view);
                } else if ("ADD_NEW_PROGRAM_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                    getPREPContext(request).getUserSessionState().setId(null);
                    setUserSessionState(request, getPREPContext(request).getUserSessionState());
                    view.getModel().put(PROGRAMPERFORMANCE, new ProgramPerformance());
                    viewName = USPROGRAMPERFORMANCE;
                } else if ("SEARCH_WORK_PERFORMANCES".equalsIgnoreCase(actionSelected)) {
                    viewName = searchWorkPerformances(performanceSearch, request, view, true);
                } else if (RETRIEVE_WORK_PERFORMANCES_LIST.equalsIgnoreCase(actionSelected)) {
                    if (performanceSearch.getSelectedProgramPerformanceId() != null
                        && !"".equals(performanceSearch.getSelectedProgramPerformanceId())) {
                        performanceSearch.setProgramPerformanceId(performanceSearch.getSelectedProgramPerformanceId());
                    }
                    if (performanceSearch.getProgramPerformanceId() == null
                        || "".equals(performanceSearch.getProgramPerformanceId())) {
                        performanceSearch
                            .setProgramPerformanceId(getPREPContext(request).getUserSessionState().getId());
                    }
                    viewName = searchWorkPerformances(performanceSearch, request, view, false);
                } else if ("RETRIEVE_WORK_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                    viewName = retrieveWorkPerformance(performanceSearch, request, view, bindingResult);
                } else if ("DELETE_WORK_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                    viewName = deleteWorkPerformance(performanceSearch, request, view);
                } else if ("ADD_NEW_WORK_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                    return null;
                } else {
                    viewName = searchProgramPerformances(performanceSearch, request, view);
                }
            } else {
                if ("PROGRAM_PERFORMANCE".equalsIgnoreCase(performanceSearch.getPerformanceSearchType())
                    || performanceSearch.getPerformanceSearchType() == null) {
                    viewName = searchProgramPerformances(performanceSearch, request, view);
                } else if ("WORK_PERFORMANCE".equalsIgnoreCase(performanceSearch.getPerformanceSearchType())) {
                    viewName = searchWorkPerformances(performanceSearch, request, view, true);
                } else {
                    viewName = USHOMESEARCH;
                }
            }
        } catch (Exception e) {
            log.error("Error in searchProgramPerformances method in UsageSearchMultiController" + e);
        }
        view.setViewName(viewName);
        return view;
    }

    /**
     * Searches with the Search Criteria for Program Performances
     */

    public String searchProgramPerformances(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view) throws PrepFunctionalException, PrepSystemException {
        log.debug("Entering searchProgramPerformances method in UsageSearchMultiController");
        String forwardKey = null;
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        try {
            PerformanceSearch outPerformanceSearchVob = null;
            PerformanceSearch performanceSearchVOB = null;

            // If it's a new search criteria, it creates a new SearchVOB and copies all the
            // properties.
            // If it is not a new search ie., pagination then gets the SearchVOB from the
            // session and set only
            // navigationType.
            if ((performanceSearch.getBackToSearchResults() == null) && (performanceSearch.getNavigationType() == null
                || performanceSearch.getNavigationType().trim().equals(""))) {
                performanceSearchVOB = new PerformanceSearch();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                performanceSearch.setUserPreferences(userPreferences);
                BeanUtils.copyProperties(performanceSearchVOB, performanceSearch);
            } else {
                performanceSearchVOB = (PerformanceSearch) inputContext.getUserSessionState().getSearch();
                performanceSearchVOB.setNavigationType(performanceSearch.getNavigationType());
                if ("WORK_PERFORMANCE".equalsIgnoreCase(performanceSearchVOB.getPerformanceSearchType())) {
                    return searchWorkPerformances(performanceSearch, request, view, true);
                }
            }

            // Set Input VOBS to inputContext
            // VERY VERY IMPORTANT:::: Set HeaderIdCurrentRowNum to zero , else only 3 rows
            // will be returned
            performanceSearchVOB.setHeaderIdCurrentRowNum(String.valueOf(0));
            inputContext.addInputValueObject(performanceSearchVOB);
            outputContext = new PREPContext();
            try {
                outputContext = usageHandler.searchProgramPerformances(inputContext);
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                view.getModel().put(SYSTEMERROR, getMessage(dae.getErrorKey()));
                forwardKey = USPROGRAMPERFORMANCE;
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
                forwardKey = USHOMESEARCH;
            } catch (Exception e) {
                view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            }

            if (UsageCommonValidations.isEmptyOrNull(forwardKey)) {
                List<Object> outValueObjects = outputContext.getOutputValueObjects();
                outPerformanceSearchVob = performanceSearchVOB;
                if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                    outPerformanceSearchVob = (PerformanceSearch) outValueObjects.iterator().next();
                }

                // Copy the the search results output to the Form, remove the search results
                // collection from the
                // searchVOb and set it to the session.
                inputContext.getUserSessionState().setSearch(outPerformanceSearchVob);
                inputContext.getUserSessionState().setHeaderSearchCriteria(outPerformanceSearchVob);

                log.debug("Program Performance Search Results :" + outPerformanceSearchVob);
                if (performanceSearch.getNavigationType() == null) {
                    // Info bar settings
                    // Prepare the infobar collection (only when it is fresh search), set it to the
                    // UsageInfoBar VOB and
                    // add to the session.
                    // If it comes from different module through context search, need to create new
                    // UsageInfoBar object
                    UsageInfoBar usageInfoBar = null;
                    try {
                        usageInfoBar = (UsageInfoBar) inputContext.getUserSessionState().getInfoBar();
                    } catch (ClassCastException ce) {
                        usageInfoBar = new UsageInfoBar();
                    }
                    if (usageInfoBar == null) {
                        usageInfoBar = new UsageInfoBar();
                    }
                    usageInfoBar.setContextSearchCriteriaInfo(getUsageSearchInfoBarList(outPerformanceSearchVob));
                    inputContext.getUserSessionState().setInfoBar(usageInfoBar);
                    log.debug("INFO IN SESSION Message Set" + DebugHelperUtils.debugCollections("INFO BAR",
                        ((UsageInfoBar) inputContext.getUserSessionState().getInfoBar())
                            .getContextSearchCriteriaInfo()));
                }
                setUserSessionState(request, inputContext.getUserSessionState());
                view.getModel().put(PERFORMANCESEARCH, outPerformanceSearchVob);
                forwardKey = USPROGRAMPERFORMANCESSEARCHRESULTS;
            }
        } catch (Exception e) {
            log.error("Error in searchProgramPerformances method in UsageSearchMultiController" + e);
            forwardKey = USHOMESEARCH;
        }
        log.debug("Exiting searchProgramPerformances method in UsageSearchMultiController");
        return forwardKey;
    }

    public String retrieveProgramPerformance(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view, BindingResult bindingResult) throws PrepFunctionalException, PrepSystemException {
        log.debug("Entering retrieveProgramPerformance method in UsageSearchMultiController");
        String forwardKey = null;
        ProgramPerformance outprogramPerformance = null;
        ProgramPerformance programPerformance = null;
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;
        // Gets the checked Agreement ID to get the details or Get the ID which is in
        // the SessionState object
        log.debug("Program Performance Id from the PerformanceSearchForm is: "
            + performanceSearch.getSelectedProgramPerformanceId());
        String id = performanceSearch.getSelectedProgramPerformanceId();
        String performanceVersionNumber = performanceSearch.getSelectedPerformanceVersionNumber();
        if (id == null) {
            id = inputContext.getUserSessionState().getId();
        }
        if (id != null) {
            // Set the id to the UserSessionState object.
            inputContext.getUserSessionState().setId(id);
            inputContext.getUserSessionState().setModuleName(Constants.USAGE_MODULE);
            setUserSessionState(request, inputContext.getUserSessionState());
            programPerformance = new ProgramPerformance();
            programPerformance.setPerformanceHeaderId(id);
            if (UsageCommonValidations.isEmptyOrNull(performanceVersionNumber)) {
                programPerformance.setVersionNumber(performanceVersionNumber);
            }
            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(programPerformance);
            try {
                outputContext = usageHandler.getProgramPerformance(inputContext);
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                view.getModel().put(SYSTEMERROR, getMessage(dae.getErrorKey()));
                forwardKey = USPROGRAMPERFORMANCE;
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
                forwardKey = USHOMESEARCH;
            }
        }
        if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outprogramPerformance = (ProgramPerformance) outValueObjects.iterator().next();
            }
            if (outprogramPerformance != null) {
                outprogramPerformance.setMusicUserInformationMode("PERFORMANCE_MUSIC_USER_INFO_FIXED_MODE");
                outprogramPerformance.setOperationMode("RETRIEVE_MODE");
            }
            // Info bar settings
            // Prepare the infobar collection (only when it is fresh search), set it to the
            // UsageInfoBar VOB and add to
            // the session.
            // If it comes from different module through context search, need to create new
            // UsageInfoBar object
            UsageInfoBar usageInfoBar = null;
            try {
                usageInfoBar = (UsageInfoBar) inputContext.getUserSessionState().getInfoBar();
            } catch (ClassCastException ce) {
                usageInfoBar = new UsageInfoBar();
            }
            if (usageInfoBar == null) {
                usageInfoBar = new UsageInfoBar();
            }
            usageInfoBar.setProgramPerformanceInfo(this.getProgramPerformanceInfoBarList(outprogramPerformance));
            inputContext.getUserSessionState().setInfoBar(usageInfoBar);
            // Forwards the control to usProgramPerformance page
            view.getModel().put(PROGRAMPERFORMANCE, outprogramPerformance);
            forwardKey = USPROGRAMPERFORMANCE;
        }
        log.debug("Exiting retrieveProgramPerformance method in UsageSearchMultiController");
        return forwardKey;
    }

    public String deleteProgramPerformance(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view) {
        log.debug("Entering deleteProgramPerformance() of UsageSearchMultiController");
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;
        PerformanceSearch outperformanceSearch = null;
        // Setting the UserPreferences. This will be changed later.
        UserPreference userPreference = null;
        userPreference = inputContext.getUserSessionState().getUserPreference();
        performanceSearch.setUserPreferences(userPreference);
        String forwardKey = null;
        if (!UsageCommonValidations.isValidLongCollection(performanceSearch.getSelectedIds())) {
            view.getModel().put(SYSTEMERROR,
                getMessage(ERROR_USAGE_GENERIC_REQUIRED_DELETE, PERFORMANCE_HEADER));
            performanceSearch.setActionSelected(SEARCH_PROGRAM_PERFORMANCES);
            performanceSearch.setBackToSearchResults("TRUE");
            try {
                return (this.searchProgramPerformances(performanceSearch, request, view));
            } catch (PrepFunctionalException | PrepSystemException e) {
                log.error(e.getMessage());
            }
        }
        // Set Input VOBS to inputContext
        inputContext.addInputValueObject(performanceSearch);
        try {
            outputContext = usageHandler.deleteProgramPerformances(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outperformanceSearch = (PerformanceSearch) outValueObjects.iterator().next();
            }
            inputContext.getUserSessionState().setSearch(outperformanceSearch);
            view.getModel().put(PERFORMANCESEARCH, outperformanceSearch);
            view.getModel().put(SYSTEMMESSAGE,
                getMessage(MESSAGE_USAGE_GENERIC_SUCCESS_DELETED, PERFORMANCE_HEADER));
            // set the UserSessionState objec to the session.
            setUserSessionState(request, inputContext.getUserSessionState());
            forwardKey = USPROGRAMPERFORMANCESSEARCHRESULTS;
        } catch (PrepSystemException pse) {
            log.debug(LOG_ERRORMESSAGE + pse);
            log.error(LOG_ERRORMESSAGE, pse);
            view.getModel().put(SYSTEMERROR, pse.getErrorKey());
            forwardKey = USHOMESEARCH;
        } catch (PrepFunctionalException pfex) {
            log.debug(LOG_ERRORMESSAGE + pfex);
            log.warn("Functional " + LOG_ERRORMESSAGE + pfex);
            view.getModel().put(SYSTEMERROR, pfex.getErrorKey());
            forwardKey = USHOMESEARCH;
        } catch (Exception ex) {
            log.debug(LOG_ERRORMESSAGE + ex);
            log.error(LOG_ERRORMESSAGE + "usageSearchMultiOperation() method", ex);
            view.getModel().put(SYSTEMERROR, ex.getMessage());
            forwardKey = USHOMESEARCH;
        }
        log.debug(FORWARDKEY + forwardKey);
        log.debug("Exiting deleteProgramPerformance() of UsageSearchMultiController");
        return forwardKey;
    }

    public String updateAssignedUsers(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view) {
        log.debug("Entering updateAssignedUsers() of UsageSearchMultiController");
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;
        PerformanceSearch outperformanceSearch = null;
        // Setting the UserPreferences. This will be changed later.
        UserPreference userPreference = null;
        userPreference = inputContext.getUserSessionState().getUserPreference();
        performanceSearch.setUserPreferences(userPreference);
        String forwardKey = null;

        log.debug("Assignment Type :::::::::::::::::::::::::: " + performanceSearch.getAssignType());

        if (!UsageCommonValidations.isValidLongCollection(performanceSearch.getSelectedIds())) {
            view.getModel().put(SYSTEMERROR,
                getMessage("error.usage.generic.required.update", PERFORMANCE_HEADER));
            performanceSearch.setActionSelected(SEARCH_PROGRAM_PERFORMANCES);
            performanceSearch.setBackToSearchResults("TRUE");
            try {
                return (this.searchProgramPerformances(performanceSearch, request, view));
            } catch (PrepFunctionalException | PrepSystemException e) {
                log.error(e.getMessage());
            }
        }
        // Set Input VOBS to inputContext
        inputContext.addInputValueObject(performanceSearch);
        try {
            outputContext = usageHandler.updateAssignedUsers(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outperformanceSearch = (PerformanceSearch) outValueObjects.iterator().next();
            }
            inputContext.getUserSessionState().setSearch(outperformanceSearch);
            view.getModel().put(PERFORMANCESEARCH, outperformanceSearch);
            view.getModel().put(SYSTEMMESSAGE,
                getMessage(MESSAGE_USAGE_GENERIC_SUCCESS_UPDATED, PERFORMANCE_HEADER));
            // set the UserSessionState objec to the session.
            setUserSessionState(request, inputContext.getUserSessionState());
            forwardKey = USPROGRAMPERFORMANCESSEARCHRESULTS;
        } catch (PrepSystemException pse) {
            log.debug(LOG_ERRORMESSAGE + pse);
            log.error(LOG_ERRORMESSAGE, pse);
            view.getModel().put(SYSTEMERROR, pse.getErrorKey());
            forwardKey = USHOMESEARCH;
        } catch (PrepFunctionalException pfex) {
            log.debug(LOG_ERRORMESSAGE + pfex);
            log.warn(LOG_ERRORMESSAGE + pfex);
            view.getModel().put(SYSTEMERROR, pfex.getErrorKey());
            forwardKey = USHOMESEARCH;
        } catch (Exception ex) {
            log.debug(LOG_ERRORMESSAGE + ex);
            log.error(LOG_ERRORMESSAGE, ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = USHOMESEARCH;
        }
        log.debug(FORWARDKEY + forwardKey);
        log.debug("Exiting deleteProgramPerformance() of UsageSearchMultiController");
        return forwardKey;
    }

    public String searchWorkPerformances(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view, boolean isSearch) throws PrepFunctionalException, PrepSystemException {
        log.debug("Entering searchWorkPerformances method in UsageSearchMultiController");
        String forwardKey = null;
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        PREPContext inputContextCriteria = getPREPContext(request);
        PREPContext outputContextCriteria = null;
        String tempPerfHdrId = null;
        String headerIdCurrent = null;
        String headerIdNext = null;
        String headerIdPrev = null;
        String headerIdCurrentRowNum = null;
        PerformanceSearch outPerformanceSearchVob = null;
        PerformanceSearch performanceSearchVOB = null;
        try {
            tempPerfHdrId = performanceSearch.getProgramPerformanceId();
            // If it's a new search criteria, it creates a new SearchVOB and copies all the
            // properties.
            // If it is not a new search ie., pagination then gets the SearchVOB from the
            // session and set only
            // navigationType.
            if ((performanceSearch.getBackToSearchResults() == null) && (performanceSearch.getNavigationType() == null
                || performanceSearch.getNavigationType().trim().equals(""))) {
                performanceSearchVOB = new PerformanceSearch();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                performanceSearch.setUserPreferences(userPreferences);
                BeanUtils.copyProperties(performanceSearchVOB, performanceSearch);
            } else {
                performanceSearchVOB = (PerformanceSearch) inputContext.getUserSessionState().getSearch();
                performanceSearchVOB.setNavigationType(performanceSearch.getNavigationType());
            }
            String showDeletedRows = "N";
            if (RETRIEVE_WORK_PERFORMANCES_LIST.equals(performanceSearchVOB.getActionSelected())) {
                performanceSearchVOB = new PerformanceSearch();
                performanceSearchVOB.setProgramPerformanceId(performanceSearch.getProgramPerformanceId());
                showDeletedRows = performanceSearch.getShowDeletedRows();
                performanceSearchVOB.setShowDeletedRows(performanceSearch.getShowDeletedRows());
                performanceSearchVOB.setActionSelected(RETRIEVE_WORK_PERFORMANCES_LIST);
            }
            PerformanceSearch performanceSearchCriteria =
                (PerformanceSearch) inputContext.getUserSessionState().getHeaderSearchCriteria();
            if (performanceSearchCriteria != null) {
                String selectedRowNum = performanceSearch.getSelectedRownNum();
                log.debug("Selected rownum from Header List screen.. " + selectedRowNum);
                performanceSearchCriteria.setHeaderIdCurrent(tempPerfHdrId);
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                performanceSearchCriteria.setUserPreferences(userPreferences);
                if (!ValidationUtils.isEmptyOrNull(performanceSearch.getHeaderIdCurrentRowNum())
                    || !ValidationUtils.isEmptyOrNull(selectedRowNum)) {
                    int rowNum = 0;
                    try {
                        if (!ValidationUtils.isEmptyOrNull(performanceSearch.getHeaderIdCurrentRowNum())) {
                            rowNum = Integer.parseInt(performanceSearch.getHeaderIdCurrentRowNum());
                        }
                        if (!ValidationUtils.isEmptyOrNull(selectedRowNum)) {
                            rowNum = Integer.parseInt(selectedRowNum);
                        }
                    } catch (Exception e) {
                        log.error("Number Format EXCEPTION Occured.. Resetting Rownum value to zero." + e);
                        rowNum = 0;
                    }
                    if ("PREV".equals(performanceSearch.getNextActionType())) {
                        rowNum = rowNum - 1;
                    } else if ("NEXT".equals(performanceSearch.getNextActionType())) {
                        rowNum = rowNum + 1;
                    }
                    performanceSearchCriteria.setHeaderIdCurrentRowNum(String.valueOf(rowNum));
                }
                inputContextCriteria.addInputValueObject(performanceSearchCriteria);
                outputContextCriteria = new PREPContext();
                try {
                    // Call SearchAgreement service
                    outputContextCriteria = usageHandler.searchProgramPerformances(inputContextCriteria);
                    List<Object> outValueObjectsCriteria = outputContextCriteria.getOutputValueObjects();
                    PerformanceSearch outPerformanceSearchCriteriaVob = null;
                    if ((outValueObjectsCriteria != null) && (!outValueObjectsCriteria.isEmpty())) {
                        outPerformanceSearchCriteriaVob = (PerformanceSearch) outValueObjectsCriteria.iterator().next();
                        if (outPerformanceSearchCriteriaVob != null) {
                            List<Object> results = outPerformanceSearchCriteriaVob.getSearchResults();
                            ProgramPerformance programPerformance = null;
                            if (results != null && !results.isEmpty()) {
                                Iterator<Object> itr = results.iterator();
                                while (itr.hasNext()) {
                                    programPerformance = (ProgramPerformance) itr.next();
                                    if (programPerformance != null
                                        && programPerformance.getPerformanceHeaderId().equals(tempPerfHdrId)) {
                                        headerIdCurrent = programPerformance.getHeaderIdCurrent();
                                        headerIdPrev = programPerformance.getHeaderIdPrev();
                                        headerIdNext = programPerformance.getHeaderIdNext();
                                        headerIdCurrentRowNum = programPerformance.getHeaderIdCurrentRowNum();
                                    }
                                }
                            }
                        }

                        inputContext.getUserSessionState().setHeaderIdCurrent(headerIdCurrent);
                        inputContext.getUserSessionState().setHeaderIdPrev(headerIdPrev);
                        inputContext.getUserSessionState().setHeaderIdNext(headerIdNext);
                        inputContext.getUserSessionState().setHeaderIdCurrentRowNum(headerIdCurrentRowNum);
                    }

                } catch (PrepFunctionalException dae) {
                    log.warn(dae.getMessage());
                    view.getModel().put(SYSTEMERROR, getMessage(dae.getErrorKey()));
                    forwardKey = USPROGRAMPERFORMANCE;
                } catch (PrepSystemException pse) {
                    log.error(pse.getMessage());
                    view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
                    forwardKey = USHOMESEARCH;
                }
            }
            // Set Input VOBS to inputContext
            if (isSearch) {
                performanceSearchVOB.setPaginationOverrideRequired("N");
            } else {
                inputContext.getUserSessionState().setId(performanceSearch.getProgramPerformanceId());
                performanceSearchVOB.setPaginationOverrideRequired("Y");
            }

            inputContext.addInputValueObject(performanceSearchVOB);
            outputContext = new PREPContext();
            try {
                outputContext = usageHandler.searchWorkPerformances(inputContext);
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                view.getModel().put(SYSTEMERROR, getMessage(dae.getErrorKey()));
                forwardKey = USPROGRAMPERFORMANCE;
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
                forwardKey = USHOMESEARCH;
            }
            if (UsageCommonValidations.isEmptyOrNull(forwardKey)) {
                List<Object> outValueObjects = outputContext.getOutputValueObjects();
                outPerformanceSearchVob = performanceSearchVOB;
                if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                    outPerformanceSearchVob = (PerformanceSearch) outValueObjects.iterator().next();
                }
                // collection from the
                // searchVOb and set it to the session.
                if (isSearch) {
                    inputContext.getUserSessionState().setSearch(outPerformanceSearchVob);
                } else {
                    int workPerfCount = 0;
                    if (outPerformanceSearchVob != null && outPerformanceSearchVob.getSearchResults() != null) {
                        workPerfCount = outPerformanceSearchVob.getSearchResults().size();
                    }
                    log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxx" + showDeletedRows);
                    outPerformanceSearchVob.setShowDeletedRows(showDeletedRows);
                    outPerformanceSearchVob.setWorkPerfCount(String.valueOf(workPerfCount));
                }

                // Info bar settings
                // Prepare the infobar collection (only when it is fresh search), set it to the
                // UsageSearchInfoBar VOB
                // and add to the session.
                if (outPerformanceSearchVob.getNavigationType() == null) {
                    // Info bar settings
                    // Prepare the infobar collection (only when it is fresh search), set it to the
                    // UsageInfoBar VOB and
                    // add to the session.
                    // If it comes from different module through context search, need to create new
                    // UsageInfoBar object
                    UsageInfoBar usageInfoBar = null;
                    try {
                        usageInfoBar = (UsageInfoBar) inputContext.getUserSessionState().getInfoBar();
                    } catch (ClassCastException ce) {
                        usageInfoBar = new UsageInfoBar();
                    }
                    if (usageInfoBar == null) {
                        usageInfoBar = new UsageInfoBar();
                    }
                    if (isSearch) {
                        usageInfoBar.setContextSearchCriteriaInfo(getUsageSearchInfoBarList(outPerformanceSearchVob));
                    }
                    if (outPerformanceSearchVob.getProgramPerformance() != null) {
                        usageInfoBar.setProgramPerformanceInfo(
                            this.getProgramPerformanceInfoBarList(outPerformanceSearchVob.getProgramPerformance()));
                    }
                    inputContext.getUserSessionState().setInfoBar(usageInfoBar);

                    log.debug("INFO IN SESSION Message Set" + DebugHelperUtils.debugCollections("INFO BAR",
                        ((UsageInfoBar) inputContext.getUserSessionState().getInfoBar())
                            .getContextSearchCriteriaInfo()));
                }
                if (outPerformanceSearchVob.getProgramPerformance() != null) {
                    UsageInfoBar usageInfoBar = null;
                    try {
                        usageInfoBar = (UsageInfoBar) inputContext.getUserSessionState().getInfoBar();
                    } catch (ClassCastException ce) {
                        usageInfoBar = new UsageInfoBar();
                    }
                    if (usageInfoBar == null) {
                        usageInfoBar = new UsageInfoBar();
                    }
                    if (isSearch) {
                        usageInfoBar.setContextSearchCriteriaInfo(getUsageSearchInfoBarList(outPerformanceSearchVob));
                    }

                    log.debug("Online Editable " + outPerformanceSearchVob.getProgramPerformance().getOnlineEditable());
                    outPerformanceSearchVob
                        .setOnlineEditable(outPerformanceSearchVob.getProgramPerformance().getOnlineEditable());
                    usageInfoBar.setProgramPerformanceInfo(
                        this.getProgramPerformanceInfoBarList(outPerformanceSearchVob.getProgramPerformance()));

                    inputContext.getUserSessionState().setInfoBar(usageInfoBar);
                }

                outPerformanceSearchVob.setHeaderIdCurrent(headerIdCurrent);
                outPerformanceSearchVob.setHeaderIdCurrentRowNum(headerIdCurrentRowNum);
                outPerformanceSearchVob.setHeaderIdNext(headerIdNext);
                outPerformanceSearchVob.setHeaderIdPrev(headerIdPrev);

                
                view.getModel().put(PERFORMANCESEARCH, outPerformanceSearchVob);
                // set the UserSessionState objec to the session.       
                setUserSessionState(request, inputContext.getUserSessionState());
                forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
            }
        } catch (Exception e) {
            log.error("Error in searchWorkPerformances method in UsageSearchMultiController" + e);
            forwardKey = USHOMESEARCH;
        }
        log.debug("Exiting searchWorkPerformances method in UsageSearchMultiController");
        // Forwards the control to SearchResults page.
        return forwardKey;

    }

    public String retrieveWorkPerformance(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view, BindingResult bindingResult) throws PrepFunctionalException, PrepSystemException {
        log.debug("Entering retrieveWorkPerformance method in PerformanceSearchMultiAction");
        String forwardKey = null;
        WorkPerformance outworkPerformance = null;
        WorkPerformance workPerformance = null;
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;
        // Gets the checked Agreement ID to get the details or Get the ID which is in
        // the SessionState object
        log.debug("Work Performance Id from the PerformanceSearchForm is: "
            + performanceSearch.getSelectedWorkPerformanceId());
        String id = performanceSearch.getSelectedWorkPerformanceId();
        String performanceVersionNumber = performanceSearch.getSelectedPerformanceVersionNumber();

        try {
            workPerformance = new WorkPerformance();
            workPerformance.setWorkPerformanceId(id);
            workPerformance.setVersionNumber(performanceVersionNumber);
            inputContext.addInputValueObject(workPerformance);
            outputContext = usageHandler.getWorkPerformance(inputContext);
        } catch (PrepFunctionalException dae) {
            log.warn(dae.getMessage());
            view.getModel().put(SYSTEMERROR, getMessage(dae.getErrorKey()));
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        }

        if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outworkPerformance = (WorkPerformance) outValueObjects.iterator().next();
            }
            if (outworkPerformance != null) {
                outworkPerformance.setOperationMode("RETRIEVE_MODE");
                view.getModel().put(WORKPERFORMANCE, outworkPerformance);
            }

            // Info bar settings
            // Prepare the infobar collection (only when it is fresh search), set it to the
            // UsageInfoBar VOB and add to the session.
            // If it comes from different module through context search, need to create new
            // UsageInfoBar object
            UsageInfoBar usageInfoBar = null;
            try {
                usageInfoBar = (UsageInfoBar) inputContext.getUserSessionState().getInfoBar();
            } catch (ClassCastException ce) {
                usageInfoBar = new UsageInfoBar();
            }
            if (usageInfoBar == null) {
                usageInfoBar = new UsageInfoBar();
            }

            if (outworkPerformance != null) {
                inputContext.getUserSessionState().setId(outworkPerformance.getPerformanceHeaderId());
            }
            inputContext.getUserSessionState().setModuleName(Constants.USAGE_MODULE);

            usageInfoBar.setProgramPerformanceInfo(this.getWorkPerformanceInfoBarList(outworkPerformance));
            inputContext.getUserSessionState().setInfoBar(usageInfoBar);

            // Forwards the control to usWorkPerformance page
            forwardKey = USWORKPERFORMANCE;

        }
        log.debug("Exiting retrieveWorkPerformance method in UsageSearchMultiController");
        return forwardKey;
    }

    public String deleteWorkPerformance(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view) {
        log.debug("Entering deleteWorkPerformance() of UsageSearchMultiController");

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;
        PerformanceSearch outperformanceSearch = null;
        // Setting the UserPreferences. This will be changed later.
        UserPreference userPreference = null;
        userPreference = inputContext.getUserSessionState().getUserPreference();
        performanceSearch.setUserPreferences(userPreference);
        // Create ActionErrors and ActionForward objects
        String forwardKey = null;

        if (!UsageCommonValidations.isValidLongCollection(performanceSearch.getSelectedIds())) {
            view.getModel().put(SYSTEMMESSAGE,
                getMessage(ERROR_USAGE_GENERIC_REQUIRED_DELETE, "Work Performance"));
            performanceSearch.setActionSelected("SEARCH_WORK_PERFORMANCES");
            performanceSearch.setBackToSearchResults("TRUE");
            request.setAttribute(PERFORMANCESEARCH, performanceSearch);
            try {
                return (this.searchWorkPerformances(performanceSearch, request, view, true));
            } catch (PrepFunctionalException | PrepSystemException e) {
                log.error(e.getMessage());
            }
        }

        // Set Input VOBS to inputContext
        inputContext.addInputValueObject(performanceSearch);
        try {
            outputContext = usageHandler.deleteWorkPerformances(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outperformanceSearch = (PerformanceSearch) outValueObjects.iterator().next();
                request.setAttribute(PERFORMANCESEARCH, outperformanceSearch);
                outperformanceSearch.setSearchResults(null);
                inputContext.getUserSessionState().setSearch(outperformanceSearch);
            }
            view.getModel().put(SYSTEMMESSAGE,
                getMessage(MESSAGE_USAGE_GENERIC_SUCCESS_DELETED, "Work Performance"));
            // set the UserSessionState objec to the session.
            setUserSessionState(request, inputContext.getUserSessionState());
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        } catch (PrepSystemException pse) {
            log.debug(LOG_ERRORMESSAGE + pse);
            log.error(LOG_ERRORMESSAGE, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = USHOMESEARCH;
        } catch (PrepFunctionalException pfex) {
            log.debug("Error Caught in deleteWorkPerformance :" + pfex);
            log.warn("Functional Exception in deleteWorkPerformance :" + pfex);
            view.getModel().put(SYSTEMERROR, pfex.getErrorKey());
            forwardKey = USHOMESEARCH;
        } catch (Exception ex) {
            log.debug(LOG_ERRORMESSAGE + ex);
            log.error("Exception caught in deleteWorkPerformance method", ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = USHOMESEARCH;
        }
        log.debug(FORWARDKEY + forwardKey);
        log.debug("Exiting deleteWorkPerformance() of UsageSearchMultiController");
        return forwardKey;
    }

    private List<Object> getProgramPerformanceInfoBarList(ProgramPerformance programPerformanceVob) {
        List<Object> outcoll = null;
        if (programPerformanceVob == null) {
            return outcoll;
        }
        String startDate;
        String startTime;
        String endDate;
        String endTime;
        startDate = programPerformanceVob.getPerformanceStartDate() == null ? ""
            : programPerformanceVob.getPerformanceStartDate();
        startTime = programPerformanceVob.getPerformanceStartTime() == null ? ""
            : programPerformanceVob.getPerformanceStartTime();
        endDate =
            programPerformanceVob.getPerformanceEndDate() == null ? "" : programPerformanceVob.getPerformanceEndDate();
        endTime =
            programPerformanceVob.getPerformanceEndTime() == null ? "" : programPerformanceVob.getPerformanceEndTime();
        outcoll = new ArrayList<>();
        StringBuilder musicUserType = new StringBuilder();
        outcoll.add(new InfoBarItemVOB(APM_PERF_HEADER_ID, programPerformanceVob.getPerformanceHeaderId()));
        outcoll.add(new InfoBarItemVOB(MUSIC_USER_ID, programPerformanceVob.getMusicUserId()));
        if (programPerformanceVob.getMusicUserTypeCode() != null)
            musicUserType.append(programPerformanceVob.getMusicUserTypeCode());
        musicUserType.append(" - ");
        if (programPerformanceVob.getMusicUserTypeDescription() != null)
            musicUserType.append(programPerformanceVob.getMusicUserTypeDescription());

        outcoll.add(new InfoBarItemVOB("Music User Type", musicUserType.toString()));
        outcoll.add(new InfoBarItemVOB(LICENSE_TYPE, programPerformanceVob.getLicenseTypeDescription()));
        outcoll.add(new InfoBarItemVOB("Call Letter", programPerformanceVob.getMusicUserCallLetterSuffix() == null ? ""
            : programPerformanceVob.getMusicUserCallLetterSuffix()));
        outcoll.add(new InfoBarItemVOB("Program Start Date/Time", startDate + " " + startTime));
        outcoll.add(new InfoBarItemVOB("Program End Date/Time", endDate + " " + endTime));
        outcoll.add(new InfoBarItemVOB("Target Dist", programPerformanceVob.getTargetYearQuarter()));
        return outcoll;
    }

    private List<Object> getWorkPerformanceInfoBarList(WorkPerformance workPerformanceVob) {
        List<Object> outcoll = null;
        if (workPerformanceVob == null) {
            return outcoll;
        }
        String startDate;
        String startTime;
        String endDate;
        String endTime;
        startDate =
            workPerformanceVob.getPerformanceStartDate() == null ? "" : workPerformanceVob.getPerformanceStartDate();
        startTime =
            workPerformanceVob.getPerformanceStartTime() == null ? "" : workPerformanceVob.getPerformanceStartTime();
        endDate = workPerformanceVob.getPerformanceEndDate() == null ? "" : workPerformanceVob.getPerformanceEndDate();
        endTime = workPerformanceVob.getPerformanceEndTime() == null ? "" : workPerformanceVob.getPerformanceEndTime();
        StringBuilder musicUserType = new StringBuilder();
        outcoll = new ArrayList<>();
        outcoll.add(new InfoBarItemVOB(APM_PERF_HEADER_ID, workPerformanceVob.getPerformanceHeaderId()));
        outcoll.add(new InfoBarItemVOB(MUSIC_USER_ID, workPerformanceVob.getMusicUserId()));

        if (workPerformanceVob.getMusicUserTypeCode() != null)
            musicUserType.append(workPerformanceVob.getMusicUserTypeCode());
        musicUserType.append(" - ");
        if (workPerformanceVob.getMusicUserTypeDescription() != null)
            musicUserType.append(workPerformanceVob.getMusicUserTypeDescription());

        outcoll.add(new InfoBarItemVOB("Music User Type", musicUserType.toString()));
        outcoll.add(new InfoBarItemVOB(LICENSE_TYPE, workPerformanceVob.getLicenseTypeDescription()));
        outcoll.add(new InfoBarItemVOB("Call Letter",
            workPerformanceVob.getMusicUserFullName() == null ? "" : workPerformanceVob.getMusicUserFullName()));
        outcoll.add(new InfoBarItemVOB("Program Start Date/Time", startDate + " " + startTime));
        outcoll.add(new InfoBarItemVOB("Program End Date/Time", endDate + " " + endTime));
        outcoll.add(new InfoBarItemVOB("Target Dist", workPerformanceVob.getTargetYearQuarter()));
        return outcoll;
    }

    private List<Object> getUsageSearchInfoBarList(PerformanceSearch searchVob) {
        List<Object> outcoll = null;
        // Check if PerformanceSearch is null if null just return the empty collection
        if (searchVob == null) {
            return outcoll;
        }
        outcoll = new ArrayList<>();

        if (StringUtils.isNotEmpty(searchVob.getProgramPerformanceId())) {
            outcoll.add(new InfoBarItemVOB(APM_PERF_HEADER_ID, searchVob.getProgramPerformanceId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getWorkPerformanceId())) {
            outcoll.add(new InfoBarItemVOB("APM Work Perf ID", searchVob.getWorkPerformanceId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getWorkPerformanceShareId())) {
            outcoll.add(new InfoBarItemVOB("Work Performance Share ID", searchVob.getWorkPerformanceShareId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getStartLegacyPerformanceId())) {
            outcoll.add(new InfoBarItemVOB("Legacy Performance Header ID", searchVob.getStartLegacyPerformanceId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getStartLegacyWorkPerformanceId())) {
            outcoll.add(new InfoBarItemVOB("Legacy Work Performance ID", searchVob.getStartLegacyWorkPerformanceId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getStartLegacyWorkPerformanceShareId())) {
            outcoll.add(new InfoBarItemVOB("Legacy Work Performance Share ID",
                searchVob.getStartLegacyWorkPerformanceShareId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getPerformanceStartDate())) {
            outcoll.add(new InfoBarItemVOB("Start Date From", searchVob.getPerformanceStartDate()));
        }

        if (StringUtils.isNotEmpty(searchVob.getPerformanceStartTime())) {
            outcoll.add(new InfoBarItemVOB("Start Time From", searchVob.getPerformanceStartTime()));
        }

        if (StringUtils.isNotEmpty(searchVob.getPerformanceEndDate())) {
            outcoll.add(new InfoBarItemVOB("Start Date To", searchVob.getPerformanceEndDate()));
        }

        if (StringUtils.isNotEmpty(searchVob.getPerformanceEndTime())) {
            outcoll.add(new InfoBarItemVOB("Start Time To", searchVob.getPerformanceEndTime()));
        }

        if (searchVob.getOverlapCode() != null && searchVob.getOverlapCode().length > 0) {
            outcoll.add(new InfoBarItemVOB("Program Overlap Code", StringUtils.join(searchVob.getOverlapCode(), ", ")));
        }

        if (searchVob.getLegacySourceSystem() != null && searchVob.getLegacySourceSystem().length > 0) {
            outcoll.add(
                new InfoBarItemVOB("Legacy Source System", StringUtils.join(searchVob.getLegacySourceSystem(), ", ")));
        }

        if (StringUtils.isNotEmpty(searchVob.getBatchId())) {
            outcoll.add(new InfoBarItemVOB("Batch ID", searchVob.getBatchId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getMusicUserId())) {
            outcoll.add(new InfoBarItemVOB(MUSIC_USER_ID, searchVob.getMusicUserId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getMusicUserCallLetter())) {
            outcoll.add(new InfoBarItemVOB("Supplier Call Letter", searchVob.getMusicUserCallLetter()));
        }

        if (searchVob.getMusicUserTypes() != null && searchVob.getMusicUserTypes().length > 0) {
            outcoll.add(new InfoBarItemVOB("Music User Types", StringUtils.join(searchVob.getMusicUserTypes(), ", ")));
        }
        if (searchVob.getSurveyTypes() != null && searchVob.getSurveyTypes().length > 0) {
            outcoll.add(new InfoBarItemVOB("Survey Types", StringUtils.join(searchVob.getSurveyTypes(), ", ")));
        }

        if (searchVob.getSampleTypes() != null && searchVob.getSampleTypes().length > 0) {
            outcoll.add(new InfoBarItemVOB("Sample Types", StringUtils.join(searchVob.getSampleTypes(), ", ")));
        }

        if (StringUtils.isNotEmpty(searchVob.getWorkId())) {
            outcoll.add(new InfoBarItemVOB("Work ID", searchVob.getWorkId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getWorkTitle())) {
            outcoll.add(new InfoBarItemVOB("Work Title", searchVob.getWorkTitle()));
        }

        if (searchVob.getUseTypes() != null && searchVob.getUseTypes().length > 0) {
            outcoll.add(new InfoBarItemVOB("Use Types", StringUtils.join(searchVob.getUseTypes(), ", ")));
        }

        if (StringUtils.isNotEmpty(searchVob.getFeaturedPerformerName())) {
            outcoll.add(new InfoBarItemVOB("Performer", searchVob.getFeaturedPerformerName()));
        }

        if (StringUtils.isNotEmpty(searchVob.getSupplierWorkCode())) {
            outcoll.add(new InfoBarItemVOB("Supplier Work Code", searchVob.getSupplierWorkCode()));
        }

        if (StringUtils.isNotEmpty(searchVob.getVenue())) {
            outcoll.add(new InfoBarItemVOB("Venue", searchVob.getVenue()));
        }

        if (StringUtils.isNotEmpty(searchVob.getFileId())) {
            outcoll.add(new InfoBarItemVOB("File ID", searchVob.getFileId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getShareOwnerPartyId())) {
            outcoll.add(new InfoBarItemVOB("Owner Party ID", searchVob.getShareOwnerPartyId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getMemberPartyNameId())) {
            outcoll.add(new InfoBarItemVOB("Owner Party Name ID", searchVob.getMemberPartyNameId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getMemberPartyFirstName())) {
            outcoll.add(new InfoBarItemVOB("Member Party First Name", searchVob.getMemberPartyFirstName()));
        }

        if (StringUtils.isNotEmpty(searchVob.getMemberPartyLastName())) {
            outcoll.add(new InfoBarItemVOB("Member Party Last Name", searchVob.getMemberPartyLastName()));
        }

        if (StringUtils.isNotEmpty(searchVob.getSupplierCode())) {
            outcoll.add(new InfoBarItemVOB("Supplier",
                HtmlSelectOption.getLookUpTable("UsageSuppliers", searchVob.getSupplierCode())));
        }

        if (StringUtils.isNotEmpty(searchVob.getPostedFlag())) {
            outcoll.add(new InfoBarItemVOB("Posted", searchVob.getPostedFlag()));
        }

        if (StringUtils.isNotEmpty(searchVob.getScheduleId())) {
            outcoll.add(new InfoBarItemVOB("Schedule ID", searchVob.getScheduleId()));
        }

        if (StringUtils.isNotEmpty(searchVob.getScheduleSequenceNumber())) {
            outcoll.add(new InfoBarItemVOB("Schedule Sequence #", searchVob.getScheduleSequenceNumber()));
        }

        if (StringUtils.isNotEmpty(searchVob.getProgramNumber())) {
            outcoll.add(new InfoBarItemVOB("Program Number", searchVob.getProgramNumber()));
        }

        if (StringUtils.isNotEmpty(searchVob.getSegmentNumber())) {
            outcoll.add(new InfoBarItemVOB("Segment Number", searchVob.getSegmentNumber()));
        }

        if (StringUtils.isNotEmpty(searchVob.getStatusDateFrom())) {
            outcoll.add(new InfoBarItemVOB("Status Date From", searchVob.getStatusDateFrom()));
        }
        if (StringUtils.isNotEmpty(searchVob.getStatusDateTo())) {
            outcoll.add(new InfoBarItemVOB("Status Date To", searchVob.getStatusDateTo()));
        }
        if (searchVob.getLicenseTypeCode() != null && searchVob.getLicenseTypeCode().length > 0) {
            outcoll.add(new InfoBarItemVOB(LICENSE_TYPE, StringUtils.join(searchVob.getLicenseTypeCode(), ", ")));
        }

        if (StringUtils.isNotEmpty(searchVob.getHeadlineIndicator())) {
            outcoll.add(new InfoBarItemVOB("Headline Indicator", searchVob.getHeadlineIndicator()));
        }
        if (StringUtils.isNotEmpty(searchVob.getSetlistType())) {
            outcoll.add(new InfoBarItemVOB("Set List Type", searchVob.getSetlistType()));
        }
        if (StringUtils.isNotEmpty(searchVob.getPlayCount())) {
            outcoll.add(new InfoBarItemVOB("Play Count Above", searchVob.getPlayCount()));
        }

        if (StringUtils.isNotEmpty(searchVob.getTargetYearQuarterFrom())) {
            outcoll.add(new InfoBarItemVOB("Target Dist From", searchVob.getTargetYearQuarterFrom()));
        }
        if (StringUtils.isNotEmpty(searchVob.getTargetYearQuarterTo())) {
            outcoll.add(new InfoBarItemVOB("Target Dist To", searchVob.getTargetYearQuarterTo()));
        }
        if (StringUtils.isNotEmpty(searchVob.getClassicalIndicator())) {
            outcoll.add(new InfoBarItemVOB("Classical Ind", searchVob.getClassicalIndicator()));
        }
        if (searchVob.getMatchTypes() != null && searchVob.getMatchTypes().length > 0) {
            outcoll.add(new InfoBarItemVOB("Match Type", StringUtils.join(searchVob.getMatchTypes(), ", ")));
        }

        if (searchVob.getPerformanceErrorWarningCodes() != null
            && searchVob.getPerformanceErrorWarningCodes().length > 0) {
            outcoll.add(new InfoBarItemVOB("Error/Warning Code",
                StringUtils.join(searchVob.getPerformanceErrorWarningCodes(), ", ")));
        }
        return outcoll;
    }

    public String backToSearchCriteria(ModelAndView view, HttpServletRequest request) {
        log.debug("Entering backToSearchCriteria method in UsageSearchMultiController");
        BaseSearchVOB baseSearchVOB = getPREPContext(request).getUserSessionState().getSearch();
        PerformanceSearch performanceSearchVO = (PerformanceSearch) baseSearchVOB;
        if (baseSearchVOB == null)
            performanceSearchVO = new PerformanceSearch();
        view.getModel().put(PERFORMANCESEARCH, performanceSearchVO);
        log.debug("Exiting backToSearchCriteria method in UsageSearchMultiController");
        return USHOMESEARCH;
    }

}
