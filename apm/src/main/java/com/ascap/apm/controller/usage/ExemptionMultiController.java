package com.ascap.apm.controller.usage;

import java.util.ArrayList;
import java.util.List;

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
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.Exemption;
import com.ascap.apm.vob.usage.ExemptionList;
import com.ascap.apm.vob.usage.PerformanceSearch;

@Controller
@RequestMapping("/usage")
public class ExemptionMultiController extends BaseUsageController {

    private static final String EXEMPTION_LIST = "usage/exemptionList";

    private static final String EXEMPTION_DETAILS = "usage/exemptionDetails";

    private static final String SYSTEM_ERROR = "system.error";

    private static final String EXEMPTION = "exemption";

    private static final String RETRIEVE = "RETRIEVE";

    private static final String PREPFUNCTIONALEXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER =
        "PrepFunctionalException Caught in ExemptionMultiController  :";

    private static final String PREPSYSTEMEXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER =
        "PrepSystemException Caught in ExemptionMultiController  :";

    private static final String EXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER =
        "Exception Caught in ExemptionMultiController  :";

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = {"/exemptionList", "/exemptionMaitain"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView exemptionMultiOperation(@ModelAttribute("exemptionList") ExemptionList exemptionList,
        @ModelAttribute("exemption") Exemption exemption, HttpServletRequest request, HttpServletResponse response,
        ModelAndView view, BindingResult bindingResult) throws Exception {

        String viewName = "";
        log.debug("Entering exemptionMultiOperation method in ExemptionMultiController");
        try {
            String operationType = null;
            operationType = exemptionList.getOperationType();
            log.debug("Form : ExemptionListForm operationType: " + operationType);
            if (ValidationUtils.isEmptyOrNull(operationType)) {
                viewName = EXEMPTION_LIST;
                view.setViewName(viewName);
                return view;
            } else if ("SEARCH".equals(operationType)) {
                viewName = getExemptionList(exemptionList, request, view);
                view.setViewName(viewName);
                return view;
            } else if ("ADD_NEW_EXEMPTION".equals(operationType)) {
                viewName = EXEMPTION_DETAILS;
                view.setViewName(viewName);
                return view;
            } else if ("RETRIEVE_EXEMPTION".equals(operationType)) {
                viewName = getExemption(exemptionList, request, view);
                view.setViewName(viewName);
                return view;
            } else if ("DELETE".equals(operationType)) {
                viewName = deleteExemptions(exemptionList, request, view);
                view.setViewName(viewName);
                return view;
            } else if ("CREATE".equals(operationType)) {
                viewName = addExemption(exemption, request, view);
                view.setViewName(viewName);
                return view;
            } else if ("UPDATE".equals(operationType)) {
                viewName = updateExemption(exemption, request, view);
                view.setViewName(viewName);
                return view;
            } else if ("CANCEL".equals(operationType)) {
                exemptionList.setNavigationType("CURR");
                viewName = getExemptionList(exemptionList, request, view);
                view.setViewName(viewName);
                return view;
            } else {
                log.debug("Invalid operation type..");
            }
        } catch (Exception e) {
            log.error("Error in exemptionMultiOperation() method");
            viewName = EXEMPTION_LIST;
            view.setViewName(viewName);
            return view;
        }
        log.debug("Exiting exemptionMultiOperation method in ExemptionMultiController ");
        viewName = EXEMPTION_LIST;
        view.setViewName(viewName);
        return view;
    }

    private String getExemptionList(ExemptionList exemptionList, HttpServletRequest request, ModelAndView view)
        throws Exception {

        log.debug("Entering getExemptionList method in ExemptionMultiController");
        String forwardKey = EXEMPTION_LIST;
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        ExemptionList newExemptionList = null;
        if (exemptionList.getNavigationType() == null || "FIRST".equalsIgnoreCase(exemptionList.getNavigationType())
            || "".equalsIgnoreCase(exemptionList.getNavigationType().trim())) {
            newExemptionList = new ExemptionList();
            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            exemptionList.setUserPreferences(userPreferences);
            BeanUtils.copyProperties(newExemptionList, exemptionList);
            log.debug("Setting preferences done getNofSrchRsltsPerPage " + userPreferences.getNofSrchRsltsPerPage());
            newExemptionList.setDeviceType("MANOJ DEVICE");
        } else {
            if (inputContext.getUserSessionState().getSearch() != null) {
                newExemptionList = (ExemptionList) inputContext.getUserSessionState().getSearch().getAlternateSearch();
                if (newExemptionList == null) {
                    newExemptionList = new ExemptionList();
                }
                newExemptionList.setNavigationType(exemptionList.getNavigationType());
            } else {
                inputContext.getUserSessionState().setSearch(new PerformanceSearch());
                newExemptionList = new ExemptionList();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                exemptionList.setUserPreferences(userPreferences);
                BeanUtils.copyProperties(newExemptionList, exemptionList);
            }
        }
        if (ValidationUtils.isEmptyOrNull(newExemptionList.getFilterExemptionType())) {
            log.debug("Filter Exemption Type missing.. returning..");
            return forwardKey;
        }
        PerformanceSearch performanceSearch = null;
        List<Object> outValueObjects = null;
        inputContext.addInputValueObject(newExemptionList);
        try {
            outputContext = usageHandler.getExemptionList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                newExemptionList = (ExemptionList) outValueObjects.iterator().next();
                newExemptionList.setSearchResults(newExemptionList.getSearchResults());
                newExemptionList.setOperationType(null);
                view.getModel().put("exemptionList", newExemptionList);
            }
            if (inputContext.getUserSessionState().getSearch() == null) {
                performanceSearch = new PerformanceSearch();
                performanceSearch.setAlternateSearch(newExemptionList);
                inputContext.getUserSessionState().setSearch(performanceSearch);
            } else {
                inputContext.getUserSessionState().getSearch().setAlternateSearch(newExemptionList);
            }
            forwardKey = EXEMPTION_LIST;
        } catch (PrepFunctionalException pe) {
            log.error(PREPFUNCTIONALEXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER, pe);
            view.getModel().put(SYSTEMERROR, getMessage(pe.getErrorKey()));
            forwardKey = EXEMPTION_LIST;
        } catch (PrepSystemException pe) {
            log.error(PREPSYSTEMEXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER, pe);
            view.getModel().put(SYSTEMERROR, getMessage(pe.getErrorKey()));
            forwardKey = EXEMPTION_LIST;
        } catch (Exception pe) {
            log.error(EXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER, pe);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = EXEMPTION_LIST;
        }
        log.debug("Exiting getExemptionList method in ExemptionMultiController");
        return forwardKey;
    }

    private String addExemption(Exemption exemption, HttpServletRequest request, ModelAndView view) throws Exception {

        log.debug("Entering addExemption method in ExemptionMultiController");
        String forwardKey = "";

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;
        try {
            inputContext.addInputValueObject(exemption);
            outputContext = usageHandler.addExemption(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                exemption = (Exemption) outValueObjects.iterator().next();
            }
            view.getModel().put(EXEMPTION, exemption);
            forwardKey = EXEMPTION_DETAILS;
        } catch (PrepSystemException pse) {
            log.error("PrepSystemException in ExemptionMultiController  :", pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = EXEMPTION_DETAILS;
        } catch (PrepFunctionalException pfex) {
            log.warn("PrepFunctionalException in ExemptionMultiController  :" + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = EXEMPTION_DETAILS;
        } catch (Exception ex) {
            log.error("Exception caught in ExemptionMultiController ", ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = EXEMPTION_DETAILS;
        }
        if (!view.getModel().containsKey(SYSTEMMESSAGE) && !view.getModel().containsKey(SYSTEMERROR)) {
            view.getModel().put(SYSTEMMESSAGE, getMessage("us.message.apm.exemption.create.success"));
        }
        if (log.isDebugEnabled()) {
            log.debug("Exiting addExemption method in ExemptionMultiController");
        }
        return forwardKey;
    }

    private String getExemption(ExemptionList exemptionList, HttpServletRequest request, ModelAndView view)
        throws Exception {

        log.debug("Entering getExemption method in ExemptionMultiController");
        String forwardKey = EXEMPTION_DETAILS;
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        String selectedId = exemptionList.getSelectedId();
        Exemption exemption = new Exemption();
        exemption.setExemptionId(selectedId);
        inputContext.addInputValueObject(exemption);
        List<Object> outValueObjects = null;
        try {
            outputContext = usageHandler.getExemption(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                exemption = (Exemption) outValueObjects.iterator().next();
                BeanUtils.copyProperties(exemption, exemption);
                exemption.setOperationType(RETRIEVE);
                view.getModel().put(EXEMPTION, exemption);
            }
            forwardKey = EXEMPTION_DETAILS;
        } catch (PrepFunctionalException pe) {
            log.error(PREPFUNCTIONALEXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER, pe);
            view.getModel().put(SYSTEMERROR, getMessage(pe.getErrorKey()));
            exemption.setOperationType(RETRIEVE);
            forwardKey = EXEMPTION_DETAILS;
        } catch (PrepSystemException pe) {
            log.error(PREPSYSTEMEXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER, pe);
            view.getModel().put(SYSTEMERROR, getMessage(pe.getErrorKey()));
            exemption.setOperationType(RETRIEVE);
            forwardKey = EXEMPTION_DETAILS;
        } catch (Exception pe) {
            log.error(EXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER, pe);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            exemption.setOperationType(RETRIEVE);
            forwardKey = EXEMPTION_DETAILS;
        }
        view.getModel().put(EXEMPTION, exemption);
        log.debug("Exiting getExemptions method in ExemptionMultiController");
        return forwardKey;
    }

    private String updateExemption(Exemption exemption, HttpServletRequest request, ModelAndView view)
        throws Exception {

        log.debug("Entering updateExemption method in ExemptionMultiController");
        String forwardKey = EXEMPTION_DETAILS;

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;
        try {
            inputContext.addInputValueObject(exemption);
            outputContext = usageHandler.updateExemption(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                exemption = (Exemption) outValueObjects.iterator().next();
            }
            forwardKey = EXEMPTION_DETAILS;
        } catch (PrepSystemException pse) {
            log.error("PrepSystemException in ExemptionMultiController  :", pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = EXEMPTION_DETAILS;
            exemption.setOperationType(RETRIEVE);
        } catch (PrepFunctionalException pfex) {
            log.warn("PrepFunctionalException in ExemptionMultiController  :" + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = EXEMPTION_DETAILS;
            exemption.setOperationType(RETRIEVE);
        } catch (Exception ex) {
            log.error("Exception caught in ExemptionMultiController ", ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = EXEMPTION_DETAILS;
            exemption.setOperationType(RETRIEVE);
        }
        if (!view.getModel().containsKey(SYSTEMMESSAGE) && !view.getModel().containsKey(SYSTEMERROR)) {
            view.getModel().put(SYSTEMMESSAGE, getMessage("us.message.apm.exemption.update.success"));
        }
        exemption.setOperationType(RETRIEVE);
        view.getModel().put(EXEMPTION, exemption);
        log.debug("Exiting updateExemption method in ExemptionMultiController");
        return forwardKey;
    }

    private String deleteExemptions(ExemptionList exemptionList, HttpServletRequest request, ModelAndView view)
        throws Exception {

        log.debug("Entering deleteExemptions method in ExemptionMultiController");
        String forwardKey = "";
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        if (exemptionList.getNavigationType() == null || "FIRST".equalsIgnoreCase(exemptionList.getNavigationType())
            || "".equalsIgnoreCase(exemptionList.getNavigationType().trim())) {
            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            exemptionList.setUserPreferences(userPreferences);
            log.debug("Setting preferences done getNofSrchRsltsPerPage " + userPreferences.getNofSrchRsltsPerPage());
            exemptionList.setDeviceType("MANOJ DEVICE");
        } else {
            if (inputContext.getUserSessionState().getSearch() != null) {
                exemptionList = (ExemptionList) inputContext.getUserSessionState().getSearch().getAlternateSearch();
            } else {
                inputContext.getUserSessionState().setSearch(new PerformanceSearch());
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                exemptionList.setUserPreferences(userPreferences);
            }
        }
        String[] selectedIds = exemptionList.getSelectedIndex();
        Exemption ex = null;
        List<Object> col = new ArrayList<>();
        if (selectedIds != null && selectedIds.length > 0) {
            for (String exemptionId : selectedIds) {
                ex = new Exemption();
                ex.setExemptionId(exemptionId);
                col.add(ex);
            }
        }
        exemptionList.setSearchResults(col);
        PerformanceSearch performanceSearch = null;
        List<Object> outValueObjects = null;
        inputContext.addInputValueObject(exemptionList);
        try {
            outputContext = usageHandler.deleteExemptions(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                exemptionList = (ExemptionList) outValueObjects.iterator().next();
                exemptionList.setOperationType(null);
                view.getModel().put("exemptionList", exemptionList);
            }
            if (inputContext.getUserSessionState().getSearch() == null) {
                performanceSearch = new PerformanceSearch();
                performanceSearch.setAlternateSearch(exemptionList);
            } else {
                inputContext.getUserSessionState().getSearch().setAlternateSearch(exemptionList);
            }
            forwardKey = EXEMPTION_LIST;
        } catch (PrepFunctionalException pe) {
            log.error(PREPFUNCTIONALEXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER, pe);
            view.getModel().put(SYSTEMERROR, getMessage(pe.getErrorKey()));
            forwardKey = EXEMPTION_LIST;
        } catch (PrepSystemException pe) {
            log.error(PREPSYSTEMEXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER, pe);
            view.getModel().put(SYSTEMERROR, getMessage(pe.getErrorKey()));
            forwardKey = EXEMPTION_LIST;
        } catch (Exception pe) {
            log.error(EXCEPTION_CAUGHT_EXEMPTIONMULTICONTROLLER, pe);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = EXEMPTION_LIST;
        }
        log.debug("Exiting deleteExemptions method in ExemptionMultiController");
        return forwardKey;
    }

}
