package com.ascap.apm.controller.usage;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.DebugHelperUtils;
import com.ascap.apm.common.utils.UsageCommonValidations;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.constants.Constants;
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.PerformanceSearch;
import com.ascap.apm.vob.usage.ProgramPerformance;
import com.ascap.apm.vob.usage.WeightsAndRulesApplied;
import com.ascap.apm.vob.usage.WorkPerformance;
import com.ascap.apm.vob.usage.WorkPerformancesList;

/**
 * Handles the Work Performance related Action Requests.
 * 
 * @author Manoj Puli
 * @version $Revision: 1.23 $ $Date: Nov 13 2009 11:49:36 $
 */
@Controller
public class WorkPerformanceMultiController extends BaseUsageController {

    private static final String USHOMESEARCH = "/usage/usHomeSearch";

    private static final String USWORKPERFORMANCE = "/usage/usWorkPerformance";

    private static final String USWORKPERFORMANCESSEARCHRESULTS = "/usage/usWorkPerformancesSearchResults";

    private static final String WORK_PERFORMANCE = "Work Performance";

    private static final String SYSTEMERROR = "systemerror";

    private static final String SYSTEMMESSAGE = "systemmessage";

    private static final String SYSTEM_ERROR = "system.error";

    private static final String ACTIONSELECTED = "actionSelected";

    private static final String RETRIEVE_WORK_PERFORMANCES_LIST = "RETRIEVE_WORK_PERFORMANCES_LIST";

    private static final String RETRIEVE_MODE = "RETRIEVE_MODE";

    private static final String WORKPERFORMANCEFORM = "workPerformanceForm";

    private static final String WORKPERFORMANCE = "workPerformance";

    private static final String PERFORMANCESEARCHFORM = "performanceSearchForm";

    private static final String PERFORMANCESEARCH = "performanceSearch";

    private static final String WORKPERFORMANCE_CONTROLLER = "In WorkPerformanceMultiController :";

    public WorkPerformanceMultiController() {
        super();
    }

    @Autowired
    private UsageHandler usageHandler;

    /**
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @see com.ascap.apm.web.actions.BaseAction#doWork(ActionMapping, ActionForm, HttpServletRequest,
     *      HttpServletResponse)
     */
    @RequestMapping(value = {"/workPerformancesListMultiAction", "/workPerformanceMultiAction",
    "/workPerformanceAction"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView onSubmit(@ModelAttribute("performanceSearch") PerformanceSearch performanceSearch,
        @ModelAttribute("workPerformance") WorkPerformance workPerformance, HttpServletRequest request,
        HttpServletResponse response, ModelAndView view, BindingResult bindingResult)
        throws PrepSystemException, PrepFunctionalException, IllegalAccessException, InvocationTargetException {
        log.debug("Entering onSubmit() method in WorkPerformanceMultiController");

        // Required vobs and Objects
        String actionSelected = null;
        String viewName = "";

        actionSelected = request.getParameter(ACTIONSELECTED);
        log.debug("WorkPerformanceMultiController.onSubmit() actionSelected :" + actionSelected);

        if (actionSelected != null) {
            if ("DELETE_WORK_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                viewName = deleteWorkPerformance(performanceSearch, request, view);
            } else if (RETRIEVE_WORK_PERFORMANCES_LIST.equalsIgnoreCase(actionSelected)) {
                viewName = retrieveWorkPerformancesList(performanceSearch, request, view);
            } else if ("ADD_NEW_WORK_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                viewName = addNewWorkPerformance(performanceSearch, request, view);
            } else if ("ADD_TO_MEDLEY_WORK_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                viewName = addToMedleyWorkPerformance(performanceSearch, request, view);
            } else if ("REMOVE_FROM_MEDLEY_WORK_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                viewName = removeFromMedleyWorkPerformance(performanceSearch, request, view);
            } else if ("RETRIEVE_WORK_PERFORMANCES_LIST_FROM_DETAILS".equalsIgnoreCase(actionSelected)) {
                viewName = retrieveWorkPerformancesList(performanceSearch, request, view);
            } else if ("UPDATE_WORK_PERFORMANCES_MULT".equalsIgnoreCase(actionSelected)) {
                viewName = updateWorkPerformancesBulk(performanceSearch, request, view);
            } else if ("CREATE_WORK_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                viewName = createWorkPerformance(workPerformance, request, view);
            } else if ("UPDATE_WORK_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                viewName = updateWorkPerformance(workPerformance, request, view);
            } else {
                viewName = USWORKPERFORMANCESSEARCHRESULTS;
            }
        } else {
            viewName = USWORKPERFORMANCESSEARCHRESULTS;
        }
        view.setViewName(viewName);
        return view;
    }

    public String deleteWorkPerformance(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view)
        throws PrepSystemException, PrepFunctionalException, IllegalAccessException, InvocationTargetException {

        log.debug("Entering deleteWorkPerformance LIST method in WorkPerformanceMultiController");

        String headerIdCurrent = null;
        String headerIdNext = null;
        String headerIdPrev = null;
        String headerIdCurrentRowNum = null;

        try {
            log.debug("casting to WorkPerformancesListForm ");

            if (performanceSearch != null) {
                headerIdCurrent = performanceSearch.getHeaderIdCurrent();
                headerIdNext = performanceSearch.getHeaderIdNext();
                headerIdPrev = performanceSearch.getHeaderIdPrev();
                headerIdCurrentRowNum = performanceSearch.getHeaderIdCurrentRowNum();
            }

            log.debug("IN dddddddddddddddddddddd deleteWorkPerformance " + headerIdCurrent + " " + headerIdNext + " "
                + headerIdPrev + " " + headerIdCurrentRowNum);

        } catch (Exception e) {
            // log.debug("casting to WorkPerformanceForm ");
            // workPerformanceForm = (WorkPerformanceForm) form;
            // performanceSearch = new PerformanceSearchForm();
            // performanceSearch.setUserId(workPerformanceForm.getUserId());
            // performanceSearch.setSelectedIds(new String[]{workPerformanceForm.getWorkPerformanceId()});
            // performanceSearch.setProgramPerformanceId(workPerformanceForm.getPerformanceHeaderId());
        }

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        PerformanceSearch outWorkPerformancesListForm = null;
        WorkPerformancesList outWorkPerformancesList = null;

        WorkPerformancesList workPerformancesListToDelete = null;

        String forwardKey = "";

        String headerId = performanceSearch.getHeaderIdForWorkPerf();
        String deleteType = "";
        String error = "Error Caught in WorkPerformanceMultiController.deleteWorkPerformance:";

        try {
            // Create WorkPerformance VOB and Set data from Action Form
            workPerformancesListToDelete = new WorkPerformancesList();

            log.debug("ProgramPerformanceId :" + performanceSearch.getProgramPerformanceId());
            log.debug("HeaderIdForWorkPerf :" + performanceSearch.getHeaderIdForWorkPerf());
            log.debug("DeleteType :" + performanceSearch.getDeleteType());

            deleteType = performanceSearch.getDeleteType();

            workPerformancesListToDelete.setSelectedIds(performanceSearch.getSelectedIds());
            if (performanceSearch.getProgramPerformanceId() == null) {
                log.debug("ProgramPerformanceId " + performanceSearch.getHeaderIdForWorkPerf());
                workPerformancesListToDelete.setProgramPerformanceId(performanceSearch.getHeaderIdForWorkPerf());
            } else {
                workPerformancesListToDelete.setProgramPerformanceId(performanceSearch.getProgramPerformanceId());
            }
            workPerformancesListToDelete.setUserId(performanceSearch.getUserId());
            workPerformancesListToDelete.setDeleteType(performanceSearch.getDeleteType());

            if (!UsageCommonValidations.isValidLongCollection(performanceSearch.getSelectedIds())) {
                view.getModel().put(SYSTEMERROR, messageSource.getMessage("error.usage.generic.required.delete",
                    new Object[] {WORK_PERFORMANCE}, Locale.getDefault()));
                return retrieveWorkPerformancesList(performanceSearch, request, view);
            }

            log.debug("Entering deleteWorkPerformance LIST method in WorkPerformanceMultiController USERID is "
                + workPerformancesListToDelete.getUserId());
            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(workPerformancesListToDelete);

            // Call CreateAgreement service
            outputContext = usageHandler.deleteWorkPerformancesList(inputContext);

        } catch (PrepSystemException pse) {
            log.debug(error + pse);
            log.error(error, pse);

            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        } catch (PrepFunctionalException pfex) {
            log.debug(error + pfex);
            log.warn("Functional Exception in WorkPerformanceMultiController.deleteWorkPerformance:" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getErrorKey(), null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        } catch (Exception ex) {
            log.debug(error + ex);
            log.error("Exception caught WorkPerformanceMultiController.deleteWorkPerformance method", ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(SYSTEM_ERROR, null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        }

        if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {

            outWorkPerformancesListForm = new PerformanceSearch();

            List<Object> outValueObjects = outputContext.getOutputValueObjects();

            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outWorkPerformancesList = (WorkPerformancesList) outValueObjects.iterator().next();
                BeanUtils.copyProperties(outWorkPerformancesListForm, outWorkPerformancesList);
                if (outWorkPerformancesList != null && outWorkPerformancesList.getSearchResults() != null) {
                    outWorkPerformancesListForm
                        .setWorkPerfCount("" + outWorkPerformancesList.getSearchResults().size());
                }
                outWorkPerformancesListForm.setOnlineEditable("Y");
            }

            outWorkPerformancesListForm.setActionSelected(RETRIEVE_WORK_PERFORMANCES_LIST);
            outWorkPerformancesListForm.setHeaderIdForWorkPerf(headerId);
            outWorkPerformancesListForm.setSelectedIds(null);

            outWorkPerformancesListForm.setHeaderIdCurrent(headerIdCurrent);
            outWorkPerformancesListForm.setHeaderIdNext(headerIdNext);
            outWorkPerformancesListForm.setHeaderIdPrev(headerIdPrev);
            outWorkPerformancesListForm.setHeaderIdCurrentRowNum(headerIdCurrentRowNum);

            request.setAttribute(PERFORMANCESEARCHFORM, outWorkPerformancesListForm);
            view.getModel().put(PERFORMANCESEARCH, outWorkPerformancesListForm);

            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;

            if (UsageConstants.WRK_PERF_DELETE_TYPE.equals(deleteType)) {
                view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.usage.workperf.move.success",
                    new Object[] {WORK_PERFORMANCE}, Locale.getDefault()));
            } else {
                view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.usage.generic.success.deleted",
                    new Object[] {WORK_PERFORMANCE}, Locale.getDefault()));
            }
        }
        log.debug("Exiting deleteWorkPerformance method in WorkPerformanceMultiController");

        return forwardKey;
    }

    public String updateWorkPerformancesBulk(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view)
        throws PrepSystemException, PrepFunctionalException, IllegalAccessException, InvocationTargetException {

        log.debug("Entering updateWorkPerformancesBulk LIST method in WorkPerformanceMultiController");

        String headerIdCurrent = null;
        String headerIdNext = null;
        String headerIdPrev = null;
        String headerIdCurrentRowNum = null;
        String error = "Error Caught in WorkPerformanceMultiController.updateWorkPerformancesBulk:";

        if (performanceSearch != null) {
            headerIdCurrent = performanceSearch.getHeaderIdCurrent();
            headerIdNext = performanceSearch.getHeaderIdNext();
            headerIdPrev = performanceSearch.getHeaderIdPrev();
            headerIdCurrentRowNum = performanceSearch.getHeaderIdCurrentRowNum();
        }

        log.debug("IN dddddddddddddddddddddd deleteWorkPerformance " + headerIdCurrent + " " + headerIdNext + " "
            + headerIdPrev + " " + headerIdCurrentRowNum);

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        PerformanceSearch outWorkPerformancesListForm = null;
        WorkPerformancesList outWorkPerformancesList = null;

        WorkPerformancesList workPerformancesListToUpdate = null;

        // Create ActionErrors and ActionForward objects

        String forwardKey = "";

        String headerId = performanceSearch.getHeaderIdForWorkPerf();

        try {

            workPerformancesListToUpdate = new WorkPerformancesList();

            log.debug("ProgramPerformanceId :" + performanceSearch.getProgramPerformanceId());
            log.debug("HeaderIdForWorkPerf :" + performanceSearch.getHeaderIdForWorkPerf());

            String[] selectedIds = performanceSearch.getSelectedIds();

            List<Object> workPerfCol = new ArrayList<>();
            WorkPerformance wp = null;

            if (selectedIds != null && selectedIds.length > 0) {
                String submittedValue = null;
                String apmWorkPerfId = null;
                String useTypeCode = null;

                for (int i = 0; i < selectedIds.length; i++) {
                    submittedValue = selectedIds[i];
                    if (ValidationUtils.isEmptyOrNull(submittedValue)) {
                        continue;
                    }

                    StringTokenizer st = new StringTokenizer(submittedValue, "-_-");
                    if (st.countTokens() == 2) {
                        apmWorkPerfId = st.nextToken();
                        useTypeCode = st.nextToken();
                    }

                    if (!ValidationUtils.isEmptyOrNull(apmWorkPerfId) && !ValidationUtils.isEmptyOrNull(useTypeCode)) {

                        wp = new WorkPerformance();
                        wp.setWorkPerformanceId(apmWorkPerfId);
                        wp.setUseTypeCode(useTypeCode);

                        log.debug("WWWWWWWWWWWWWW  " + wp.getWorkPerformanceId());
                        log.debug("WWWWWWWWWWWWWW  " + wp.getUseTypeCode());

                        if (performanceSearch.getProgramPerformanceId() == null) {
                            wp.setPerformanceHeaderId(performanceSearch.getHeaderIdForWorkPerf());
                        } else {
                            wp.setPerformanceHeaderId(performanceSearch.getProgramPerformanceId());
                        }
                        wp.setUserId(performanceSearch.getUserId());
                        workPerfCol.add(wp);
                    }

                }
            }

            workPerformancesListToUpdate.setSearchResults(workPerfCol);

            if (performanceSearch.getProgramPerformanceId() == null) {
                log.debug("ProgramPerformanceId " + performanceSearch.getHeaderIdForWorkPerf());
                workPerformancesListToUpdate.setProgramPerformanceId(performanceSearch.getHeaderIdForWorkPerf());
            } else {
                workPerformancesListToUpdate.setProgramPerformanceId(performanceSearch.getProgramPerformanceId());
            }
            workPerformancesListToUpdate.setUserId(performanceSearch.getUserId());

            log.debug("Entering update multiple work perfs  LIST method in WorkPerformanceMultiController USERID is "
                + workPerformancesListToUpdate.getUserId());
            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(workPerformancesListToUpdate);

            // Call CreateAgreement service
            outputContext = usageHandler.updateWorkPerformancesBulk(inputContext);

        } catch (PrepSystemException pse) {
            log.debug(error + pse);
            log.error(error, pse);

            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        } catch (PrepFunctionalException pfex) {
            log.debug(error + pfex);
            log.warn("Functional Exception in WorkPerformanceMultiController.updateWorkPerformancesBulk:" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getErrorKey(), null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        } catch (Exception ex) {
            log.debug(error + ex);
            log.error("Exception caught WorkPerformanceMultiController.updateWorkPerformancesBulk", ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(SYSTEM_ERROR, null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        }

        if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {

            outWorkPerformancesListForm = new PerformanceSearch();

            List<Object> outValueObjects = outputContext.getOutputValueObjects();

            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outWorkPerformancesList = (WorkPerformancesList) outValueObjects.iterator().next();
                BeanUtils.copyProperties(outWorkPerformancesListForm, outWorkPerformancesList);
                if (outWorkPerformancesList != null && outWorkPerformancesList.getSearchResults() != null) {
                    outWorkPerformancesListForm
                        .setWorkPerfCount("" + outWorkPerformancesList.getSearchResults().size());
                }
                outWorkPerformancesListForm.setOnlineEditable("Y");
            }

            outWorkPerformancesListForm.setActionSelected(RETRIEVE_WORK_PERFORMANCES_LIST);
            outWorkPerformancesListForm.setHeaderIdForWorkPerf(headerId);
            outWorkPerformancesListForm.setSelectedIds(null);

            outWorkPerformancesListForm.setHeaderIdCurrent(headerIdCurrent);
            outWorkPerformancesListForm.setHeaderIdNext(headerIdNext);
            outWorkPerformancesListForm.setHeaderIdPrev(headerIdPrev);
            outWorkPerformancesListForm.setHeaderIdCurrentRowNum(headerIdCurrentRowNum);

            request.setAttribute(PERFORMANCESEARCHFORM, outWorkPerformancesListForm);
            view.getModel().put(PERFORMANCESEARCH, outWorkPerformancesListForm);
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
            view.getModel().put(SYSTEMMESSAGE,
                messageSource.getMessage("message.apm.workperf.mult.update.success", null, Locale.getDefault()));

        }

        log.debug("Exiting updateWorkPerformancesBulk method in WorkPerformanceMultiController");

        return forwardKey;
    }

    /**
     * Method createWorkPerformance.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param bindingResult
     * @param response
     * @return ActionForward
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws Exception
     */

    public String createWorkPerformance(WorkPerformance workPerformance, HttpServletRequest request, ModelAndView view)
        throws PrepSystemException, PrepFunctionalException, IllegalAccessException, InvocationTargetException {

        log.debug("Entering createWorkPerformance method in WorkPerformanceMultiController");

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        WorkPerformance outWorkPerformanceForm = null;
        WorkPerformance outWorkPerformance = null;

        String forwardKey = "";
        String error = "Error Caught in WorkPerformanceMultiController.createWorkPerformance:";

        // FOR TEST ONLY
        workPerformance.setVersionNumber("0");
        workPerformance.setIsCurrentVersion("Y");
        // FOR TEST ONLY

        try {
            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(workPerformance);

            // Call CreateAgreement service
            outputContext = usageHandler.addWorkPerformance(inputContext);

        } catch (PrepSystemException pse) {
            log.debug(error + pse);
            log.error(error, pse);

            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCE;
        } catch (PrepFunctionalException pfex) {
            log.debug(error + pfex);
            log.warn("Functional Exception in WorkPerformanceMultiController.createWorkPerformance:" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getErrorKey(), null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCE;
        } catch (Exception ex) {
            log.debug(error + ex);
            log.error(error, ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(SYSTEM_ERROR, null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCE;
        }

        if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {

            outWorkPerformanceForm = new WorkPerformance();

            List<Object> outValueObjects = outputContext.getOutputValueObjects();

            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outWorkPerformance = (WorkPerformance) outValueObjects.iterator().next();
                BeanUtils.copyProperties(outWorkPerformanceForm, outWorkPerformance);

                log.debug(DebugHelperUtils.debugCollections("Perf Messages in Action",
                    outWorkPerformance.getErrorsAndWarnings()));
                outWorkPerformanceForm.setErrorsAndWarnings(outWorkPerformance.getErrorsAndWarnings());
            }

            outWorkPerformanceForm.setOperationMode(RETRIEVE_MODE);
            request.setAttribute(WORKPERFORMANCEFORM, outWorkPerformanceForm);
            view.getModel().put(WORKPERFORMANCE, outWorkPerformanceForm);
            log.debug(DebugHelperUtils.debugCollections("Perf Messages in Action",
                outWorkPerformanceForm.getErrorsAndWarnings()));
            view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.usage.generic.success.created",
                new Object[] {WORK_PERFORMANCE}, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCE;
        }
        log.debug("Exiting createWorkPerformance method in WorkPerformanceMultiController");

        return forwardKey;
    }

    /**
     * Method updateWorkPerformance.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param bindingResult
     * @param response
     * @return ActionForward
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws Exception
     */
    public String updateWorkPerformance(WorkPerformance workPerformance, HttpServletRequest request, ModelAndView view)
        throws PrepSystemException, PrepFunctionalException, IllegalAccessException, InvocationTargetException {
        log.debug("Entering updateWorkPerformance method in WorkPerformanceMultiController");

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        WorkPerformance outWorkPerformanceForm = null;
        WorkPerformance outWorkPerformance = null;

        String forwardKey = "";
        String error = "Error Caught in WorkPerformanceMultiController.updateWorkPerformance:";
        try {
            log.debug("Wwwwwwwwwwwwwwwwwwwwwwwwwwork workPerformancevob workId = " + workPerformance.getWorkId());

            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(workPerformance);

            // Call CreateAgreement service
            outputContext = usageHandler.updateWorkPerformance(inputContext);

        } catch (PrepSystemException pse) {
            log.debug(error + pse);
            log.error(error, pse);

            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCE;
        } catch (PrepFunctionalException pfex) {
            log.debug(error + pfex);
            log.warn("Functional Exception in WorkPerformanceMultiController.updateWorkPerformance:" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getErrorKey(), null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCE;
        } catch (Exception ex) {
            log.debug(error + ex);
            log.error("Exception caught WorkPerformanceMultiController.updateWorkPerformance method", ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(SYSTEM_ERROR, null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCE;
        }

        if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {

            outWorkPerformanceForm = new WorkPerformance();

            List<Object> outValueObjects = outputContext.getOutputValueObjects();

            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outWorkPerformance = (WorkPerformance) outValueObjects.iterator().next();
                BeanUtils.copyProperties(outWorkPerformanceForm, outWorkPerformance);

                if (outWorkPerformance != null) {
                    outWorkPerformanceForm.setDbWorkTitle(outWorkPerformance.getWorkTitle());
                    outWorkPerformanceForm.setDbPerformerName(outWorkPerformance.getPerformerName());
                    outWorkPerformanceForm.setErrorFlag(outWorkPerformance.getErrorFlag());
                }

            }

            outWorkPerformanceForm.setOperationMode(RETRIEVE_MODE);
            request.setAttribute(WORKPERFORMANCEFORM, outWorkPerformanceForm);
            view.getModel().put(WORKPERFORMANCE, outWorkPerformanceForm);
            view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.usage.generic.success.modified",
                new Object[] {WORK_PERFORMANCE}, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCE;
        }

        log.debug("Exiting updateWorkPerformance method in WorkPerformanceMultiController");

        return forwardKey;
    }

    /**
     * Method retrieveWorkPerformancesList.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param bindingResult
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public String retrieveWorkPerformancesList(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering retrieveWorkPerformancesList method in WorkPerformanceMultiController");

        String forwardKey = null;
        String contextId = null;
        int contextFromModule;
        WorkPerformancesList workPerformancesListVOB = null;
        PerformanceSearch outWorkPerformancesListForm = null;
        WorkPerformancesList outWorkPerformancesListVob = null;
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        contextId = getPREPContext(request).getUserSessionState().getId();
        contextFromModule = getPREPContext(request).getUserSessionState().getModuleName();

        log.debug("CONTEXT Info Id: '" + contextId + "', From Module: '" + contextFromModule);
        try {
            if (performanceSearch.getSelectedIds() != null) {
                log.debug("MMMMMMMMMMMMMMMMMMMMMMmanoj Selected IDs " + performanceSearch.getSelectedIds().length);
            } else {
                log.debug("MMMMMMMMMMMMMMMMMMMMMMmanoj Selected IDs is nulllllllllllllllll ");
            }
            if (performanceSearch.getHeaderIdForWorkPerf() == null) {
                log.debug(
                    "redirecting to normal usagehomepage from retrieveWorkPerformancesList contextId is null in session and form");
                return USHOMESEARCH;
            }

            workPerformancesListVOB = new WorkPerformancesList();

            String programPerformanceId = performanceSearch.getHeaderIdForWorkPerf();

            workPerformancesListVOB.setProgramPerformanceId(performanceSearch.getHeaderIdForWorkPerf());

            log.debug("MMMMMMMMMMMMMMMMMMMMANOJ workPerformancesListForm.getProgramPerformanceId "
                + performanceSearch.getProgramPerformanceId());
            log.debug("MMMMMMMMMMMMMMMMMMMMANOJ workPerformancesListForm.getHeaderIdForWorkPerf "
                + performanceSearch.getHeaderIdForWorkPerf());

            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(workPerformancesListVOB);
            outputContext = new PREPContext();
            try {
                // Call SearchAgreement service
                outputContext = usageHandler.getWorkPerformancesList(inputContext);
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                view.getModel().put(SYSTEMERROR,
                    messageSource.getMessage(dae.getErrorKey(), null, Locale.getDefault()));
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                view.getModel().put(SYSTEMERROR,
                    messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
            }

            outWorkPerformancesListForm = new PerformanceSearch();
            if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {

                List<Object> outValueObjects = outputContext.getOutputValueObjects();

                outWorkPerformancesListVob = workPerformancesListVOB;

                if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                    outWorkPerformancesListVob = (WorkPerformancesList) outValueObjects.iterator().next();
                    log.debug("outValueObjects is not null in WORKPERFORMANCE Multi Action " + DebugHelperUtils
                        .debugCollections(WORKPERFORMANCE_CONTROLLER, outWorkPerformancesListVob.getSearchResults()));
                }

                // Copy the the search results output to the Form, remove the search results
                // List<Object> from the searchVOb and set it to the session.
                BeanUtils.copyProperties(outWorkPerformancesListForm, outWorkPerformancesListVob);

                outWorkPerformancesListVob.setSearchResults(null);

                outWorkPerformancesListVob.setNavigationType(null);
                if (inputContext.getUserSessionState().getSearch() == null) {
                    outWorkPerformancesListVob = new WorkPerformancesList();
                    outWorkPerformancesListVob.setAlternateSearch(outWorkPerformancesListVob);
                    outWorkPerformancesListVob.setProgramPerformanceId(programPerformanceId);
                } else {
                    outWorkPerformancesListVob.setProgramPerformanceId(programPerformanceId);
                    inputContext.getUserSessionState().getSearch().setAlternateSearch(outWorkPerformancesListVob);
                }

                log.debug("Work Performances List :" + outWorkPerformancesListVob);
                // programPerformanceId
                outWorkPerformancesListForm.setHeaderIdForWorkPerf(programPerformanceId);
                outWorkPerformancesListForm.setActionSelected(RETRIEVE_WORK_PERFORMANCES_LIST);
                outWorkPerformancesListForm.setSelectedIds(performanceSearch.getSelectedIds());
                request.setAttribute("workPerformancesListForm", outWorkPerformancesListForm);

                log.debug("outValueObjects is not null in WORKPERFORMANCE Multi Action FORM " + DebugHelperUtils
                    .debugCollections(WORKPERFORMANCE_CONTROLLER, outWorkPerformancesListForm.getSearchResults()));

                forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
            } else {
                List<Object> outValueObjects = outputContext.getOutputValueObjects();

                outWorkPerformancesListVob = workPerformancesListVOB;

                if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                    outWorkPerformancesListVob = (WorkPerformancesList) outValueObjects.iterator().next();
                    log.debug("outValueObjects is not null in WORKPERFORMANCE Multi Action " + DebugHelperUtils
                        .debugCollections(WORKPERFORMANCE_CONTROLLER, outWorkPerformancesListVob.getSearchResults()));
                }

                // Copy the the search results output to the Form, remove the search results
                // collection from the searchVOb and set it to the session.
                BeanUtils.copyProperties(outWorkPerformancesListForm, outWorkPerformancesListVob);

                outWorkPerformancesListVob.setSearchResults(null);

                log.debug("Work Performances List :" + outWorkPerformancesListVob);

                outWorkPerformancesListForm.setHeaderIdForWorkPerf(programPerformanceId);
                outWorkPerformancesListForm.setActionSelected(RETRIEVE_WORK_PERFORMANCES_LIST);
                outWorkPerformancesListForm.setSelectedIds(performanceSearch.getSelectedIds());
                request.setAttribute(PERFORMANCESEARCHFORM, outWorkPerformancesListForm);

                log.debug("outValueObjects is not null in WORKPERFORMANCE Multi Action FORM " + DebugHelperUtils
                    .debugCollections(WORKPERFORMANCE_CONTROLLER, outWorkPerformancesListForm.getSearchResults()));
                forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
            }

        } catch (Exception e) {
            log.error("Error in retrieveWorkPerformancesList method in WorkPerformanceMultiController" + e);
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        }
        log.debug("Exiting retrieveWorkPerformancesList method in WorkPerformanceMultiController");

        // Forwards the control to SearchResults page.
        return forwardKey;
    }

    /**
     * Method retrieveWorkPerformance.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param bindingResult
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public String retrieveWorkPerformance(WorkPerformancesList workPerformancesList, HttpServletRequest request,
        ModelAndView view) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering retrieveWorkPerformance method in WorkPerformanceMultiController");

        String forwardKey = null;
        WorkPerformance workPerformance = null;
        WorkPerformance outWorkPerformanceVob = null;
        WeightsAndRulesApplied weightsAndRulesApplied = null;

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        try {
            if (workPerformancesList == null) {
                return USHOMESEARCH;
            } else if (workPerformancesList.getSelectedWorkPerformanceId() == null) {
                log.debug(
                    "redirecting to normal usagehomepage from retrieveWorkPerformance contextId is null in session and form");
                return USHOMESEARCH;
            }

            workPerformance = new WorkPerformance();

            workPerformance.setWorkPerformanceId(workPerformancesList.getSelectedWorkPerformanceId());
            workPerformance.setVersionNumber(workPerformancesList.getSelectedPerformanceVersionNumber());

            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(workPerformance);
            outputContext = new PREPContext();
            try {
                // Call SearchAgreement service
                outputContext = usageHandler.getWorkPerformance(inputContext);
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                view.getModel().put(SYSTEMERROR,
                    messageSource.getMessage(dae.getErrorKey(), null, Locale.getDefault()));
                forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                view.getModel().put(SYSTEMERROR,
                    messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
                forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
            }

            if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {

                List<Object> outValueObjects = outputContext.getOutputValueObjects();

                outWorkPerformanceVob = workPerformance;

                if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                    outWorkPerformanceVob = (WorkPerformance) outValueObjects.iterator().next();
                    log.debug(
                        "outValueObjects is not null in RETRIEVE_WORK_PERFORMANCE in WorkPerformanceMultiController "
                            + outWorkPerformanceVob);
                }

                weightsAndRulesApplied = outWorkPerformanceVob.getWeightsAndRulesApplied();
                log.debug("Inside WorkPerformanceMultiController.retrieveWorkPerformance() Work Performance Retrieved :"
                    + outWorkPerformanceVob);
                log.debug("Inside WorkPerformanceMultiControllerretrieveWorkPerformance() Weights and Rules Applied:"
                    + weightsAndRulesApplied);

                outWorkPerformanceVob.setOperationMode(RETRIEVE_MODE);
                request.setAttribute(WORKPERFORMANCEFORM, outWorkPerformanceVob);

                forwardKey = USWORKPERFORMANCE;
            }

        } catch (Exception e) {
            log.error("Error in retrieveWorkPerformance method in WorkPerformanceMultiController" + e);
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        }
        log.debug("Exiting retrieveWorkPerformance method in WorkPerformanceMultiController");

        // Forwards the control to SearchResults page.
        return forwardKey;
    }

    /**
     * Method addNewWorkPerformance.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param bindingResult
     * @param response
     * @return ActionForward
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws Exception
     */
    public String addNewWorkPerformance(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view)
        throws PrepSystemException, PrepFunctionalException, IllegalAccessException, InvocationTargetException {
        log.debug("Entering addNewWorkPerformance method in WorkPerformanceMultiController");
        String forwardKey = null;
        WorkPerformance outWorkPerformance = null;
        ProgramPerformance programPerformance = null;
        ProgramPerformance outprogramPerformance = null;

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        List<Object> outValueObjects = null;
        String id = null;
        id = inputContext.getUserSessionState().getId();

        if (id != null) {
            // Set the id to the UserSessionState object.

            inputContext.getUserSessionState().setId(id);
            inputContext.getUserSessionState().setModuleName(Constants.USAGE_MODULE);

            setUserSessionState(request, inputContext.getUserSessionState());

            programPerformance = new ProgramPerformance();

            programPerformance.setPerformanceHeaderId(id);

            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(programPerformance);

            try {
                // Call RetrieveAgreement service
                outputContext = usageHandler.getProgramPerformance(inputContext);

            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                view.getModel().put(SYSTEMERROR,
                    messageSource.getMessage(dae.getErrorKey(), null, Locale.getDefault()));
                forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                view.getModel().put(SYSTEMERROR,
                    messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
                forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
            }
        }

        if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {

            outValueObjects = outputContext.getOutputValueObjects();

            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outWorkPerformance = new WorkPerformance();

                outprogramPerformance = (ProgramPerformance) outValueObjects.iterator().next();

                BeanUtils.copyProperties(outWorkPerformance, outprogramPerformance);
                outWorkPerformance.setPerformanceHeaderId(outprogramPerformance.getPerformanceHeaderId());
                outWorkPerformance.setSourceSystem(outprogramPerformance.getSourceSystem());
                outWorkPerformance.setOnlineHeaderEditable(outprogramPerformance.getOnlineEditable());
                outWorkPerformance.setOperationMode(null);
                outWorkPerformance.setErrorsAndWarnings(null);
                outWorkPerformance.setVersionNumber(null);
                outWorkPerformance.setPerformerName(null);
            }

            request.setAttribute(WORKPERFORMANCEFORM, outWorkPerformance);
            view.getModel().put(WORKPERFORMANCE, outWorkPerformance);
            // Forwards the control to usProgramPerformance page
            forwardKey = USWORKPERFORMANCE;

        }

        log.debug("Exiting retrieveProgramPerformance method in UsageSearchMultiAction");
        return forwardKey;
    }

    /**
     * Method addToMedleyWorkPerformance.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param bindingResult
     * @param response
     * @return ActionForward
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws Exception
     */
    public String addToMedleyWorkPerformance(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view)
        throws PrepSystemException, PrepFunctionalException, IllegalAccessException, InvocationTargetException {

        log.debug("Entering addToMedleyWorkPerformance LIST method in WorkPerformanceMultiController");

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        PerformanceSearch outWorkPerformancesListForm = null;
        WorkPerformancesList outWorkPerformancesList = null;

        WorkPerformancesList workPerformancesListForAddToMedley = null;

        // Create ActionErrors and ActionForward objects
        String forwardKey = "";
        String programPerformanceId = null;
        String error = "Error Caught in WorkPerformanceMultiController.addToMedleyWorkPerformance:";
        try {
            workPerformancesListForAddToMedley = new WorkPerformancesList();
            workPerformancesListForAddToMedley.setSelectedIds(performanceSearch.getSelectedIds());
            if (performanceSearch.getSelectedIds() == null) {
                view.getModel().put(SYSTEMERROR, messageSource
                    .getMessage("error.usage.addToMedley.nofWorkPerformancesInsufficient", null, Locale.getDefault()));
                return retrieveWorkPerformancesList(performanceSearch, request, view);
            } else {
                if (performanceSearch.getSelectedIds().length < 2) {
                    view.getModel().put(SYSTEMERROR, messageSource.getMessage(
                        "error.usage.addToMedley.nofWorkPerformancesInsufficient", null, Locale.getDefault()));
                    return retrieveWorkPerformancesList(performanceSearch, request, view);
                }
            }
            programPerformanceId = performanceSearch.getHeaderIdForWorkPerf();
            workPerformancesListForAddToMedley.setProgramPerformanceId(performanceSearch.getHeaderIdForWorkPerf());
            workPerformancesListForAddToMedley.setUserId(performanceSearch.getUserId());

            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(workPerformancesListForAddToMedley);

            // Call CreateAgreement service
            outputContext = usageHandler.addToMedleyWorkPerformance(inputContext);

        } catch (PrepSystemException pse) {
            log.debug(error + pse);
            log.error(error, pse);

            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
        } catch (PrepFunctionalException pfex) {
            log.debug(error + pfex);
            log.warn("Functional Exception in WorkPerformanceMultiController.addToMedleyWorkPerformance:" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getErrorKey(), null, Locale.getDefault()));
        } catch (Exception ex) {
            log.debug(error + ex);
            log.error("Exception caught WorkPerformanceMultiController.addToMedleyWorkPerformance method", ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(SYSTEM_ERROR, null, Locale.getDefault()));
        }

        if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {

            outWorkPerformancesListForm = new PerformanceSearch();

            List<Object> outValueObjects = outputContext.getOutputValueObjects();

            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outWorkPerformancesList = (WorkPerformancesList) outValueObjects.iterator().next();
                BeanUtils.copyProperties(outWorkPerformancesListForm, outWorkPerformancesList);

                if (outWorkPerformancesList != null && outWorkPerformancesList.getSearchResults() != null) {
                    outWorkPerformancesListForm
                        .setWorkPerfCount("" + outWorkPerformancesList.getSearchResults().size());
                }
            }

            outWorkPerformancesListForm.setOnlineEditable(performanceSearch.getOnlineEditable());
            outWorkPerformancesListForm.setHeaderIdForWorkPerf(programPerformanceId);
            outWorkPerformancesListForm.setActionSelected(RETRIEVE_WORK_PERFORMANCES_LIST);
            outWorkPerformancesListForm.setSelectedIds(null);
            request.setAttribute(PERFORMANCESEARCHFORM, outWorkPerformancesListForm);
            view.getModel().put(PERFORMANCESEARCH, outWorkPerformancesListForm);
            view.getModel().put(SYSTEMMESSAGE,
                messageSource.getMessage("audit.usage.workPerformance.medley.created", null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        } else {
            return retrieveWorkPerformancesList(performanceSearch, request, view);
        }
        log.debug("Exiting addToMedleyWorkPerformance method in WorkPerformanceMultiController");

        return forwardKey;
    }

    /**
     * Method removeFromMedleyWorkPerformance.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param bindingResult
     * @param response
     * @return ActionForward
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws Exception
     */
    public String removeFromMedleyWorkPerformance(PerformanceSearch performanceSearch, HttpServletRequest request,
        ModelAndView view)
        throws PrepSystemException, PrepFunctionalException, IllegalAccessException, InvocationTargetException {

        log.debug("Entering removeFromMedleyWorkPerformance LIST method in WorkPerformanceMultiController");

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        PerformanceSearch outWorkPerformancesListForm = null;
        WorkPerformancesList outWorkPerformancesList = null;

        WorkPerformancesList workPerformancesListForRemoveFromMedley = null;
        String forwardKey = "";
        String programPerformanceId = performanceSearch.getHeaderIdForWorkPerf();

        String error = "Error Caught in WorkPerformanceMultiController.removeFromMedleyWorkPerformance:";

        try {
            workPerformancesListForRemoveFromMedley = new WorkPerformancesList();
            workPerformancesListForRemoveFromMedley.setSelectedIds(performanceSearch.getSelectedIds());
            workPerformancesListForRemoveFromMedley.setProgramPerformanceId(performanceSearch.getHeaderIdForWorkPerf());
            workPerformancesListForRemoveFromMedley.setUserId(performanceSearch.getUserId());

            if (performanceSearch.getSelectedIds() == null) {
                view.getModel().put(SYSTEMERROR, messageSource.getMessage(
                    "error.usage.removeFromMedley.nofWorkPerformancesInsufficient", null, Locale.getDefault()));
                return retrieveWorkPerformancesList(performanceSearch, request, view);
            } else {
                if (performanceSearch.getSelectedIds().length == 0) {
                    view.getModel().put(SYSTEMERROR, messageSource.getMessage(
                        "error.usage.removeFromMedley.nofWorkPerformancesInsufficient", null, Locale.getDefault()));
                    return retrieveWorkPerformancesList(performanceSearch, request, view);
                }
            }

            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(workPerformancesListForRemoveFromMedley);

            // Call CreateAgreement service
            outputContext = usageHandler.removeFromMedleyWorkPerformance(inputContext);

        } catch (PrepSystemException pse) {
            log.debug(error + pse);
            log.error(error, pse);

            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
        } catch (PrepFunctionalException pfex) {
            log.debug(error + pfex);
            log.warn("Functional Exception in WorkPerformanceMultiController.removeFromMedleyWorkPerformance:" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getErrorKey(), null, Locale.getDefault()));
        } catch (Exception ex) {
            log.debug(error + ex);
            log.error("Exception caught WorkPerformanceMultiController.removeFromMedleyWorkPerformance method", ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(SYSTEM_ERROR, null, Locale.getDefault()));
        }

        if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {

            outWorkPerformancesListForm = new PerformanceSearch();

            List<Object> outValueObjects = outputContext.getOutputValueObjects();

            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outWorkPerformancesList = (WorkPerformancesList) outValueObjects.iterator().next();
                BeanUtils.copyProperties(outWorkPerformancesListForm, outWorkPerformancesList);

                if (outWorkPerformancesList != null && outWorkPerformancesList.getSearchResults() != null) {
                    outWorkPerformancesListForm
                        .setWorkPerfCount("" + outWorkPerformancesList.getSearchResults().size());
                }
            }

            outWorkPerformancesListForm.setOnlineEditable(performanceSearch.getOnlineEditable());
            outWorkPerformancesListForm.setHeaderIdForWorkPerf(programPerformanceId);
            outWorkPerformancesListForm.setActionSelected(RETRIEVE_WORK_PERFORMANCES_LIST);
            outWorkPerformancesListForm.setSelectedIds(null);
            request.setAttribute(PERFORMANCESEARCHFORM, outWorkPerformancesListForm);
            view.getModel().put(PERFORMANCESEARCH, outWorkPerformancesListForm);
            view.getModel().put(SYSTEMMESSAGE,
                messageSource.getMessage("audit.usage.workPerformance.medley.removed", null, Locale.getDefault()));
            forwardKey = USWORKPERFORMANCESSEARCHRESULTS;
        } else {
            return retrieveWorkPerformancesList(performanceSearch, request, view);
        }
        log.debug("Exiting removeFromMedleyWorkPerformance method in WorkPerformanceMultiController");
        return forwardKey;
    }

}
