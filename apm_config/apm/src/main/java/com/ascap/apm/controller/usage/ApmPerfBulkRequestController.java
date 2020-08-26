package com.ascap.apm.controller.usage;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.ApmPerformanceBulkRequest;
import com.ascap.apm.vob.usage.ApmPerformanceBulkRequestList;
import com.ascap.apm.vob.usage.PerformanceSearch;

@RestController
public class ApmPerfBulkRequestController extends BaseUsageController {

    private static final String SYSTEMERROR = "systemerror";

    private static final String SYSTEMMESSAGE = "systemmessage";

    private static final String JSPURL = "usage/apmPerfBulkRequest";

    private static final String JSPURLMADLEY = "usage/apmMedleyDetails";

    public ApmPerfBulkRequestController() {
        super();
    }

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/apmPerfBulkRequest", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchWork(
        @ModelAttribute("apmPerformanceBulkRequestList") ApmPerformanceBulkRequestList apmPerformanceBulkRequestList,
        HttpServletRequest request, HttpServletResponse response, ModelAndView view, BindingResult bindingResult)
        throws Exception {
        Enumeration<String> e = request.getParameterNames();
        String val = "";
        String viewName = "";
        int totalparams = 0;
        while (e.hasMoreElements()) {
            val = e.nextElement();
            String[] numvalues = request.getParameterValues(val);
            log.debug("param : " + val + "   count " + numvalues.length);
            totalparams += numvalues.length;
        }
        log.debug("TOTAL REQUEST PARAMETERS : " + totalparams);
        if ("SEARCH".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = getBulkRequestData(apmPerformanceBulkRequestList, request, view);
        } else if ("MEDLEY_UPDATE".equals(apmPerformanceBulkRequestList.getOperationType())
            || "MEDLEY_ADD".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = medelyBulkRequest(apmPerformanceBulkRequestList, request, view);
        } else if ("MEDLEY_RETRIEVE".equals(apmPerformanceBulkRequestList.getOperationType())
            || "MEDLEY_UNMATCH".equals(apmPerformanceBulkRequestList.getOperationType())
            || "MEDLEY_NEW".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = buildMedleyGroups(apmPerformanceBulkRequestList, request, view);
        } else if ("ASSIGN".equals(apmPerformanceBulkRequestList.getOperationType())
            || "UNASSIGN".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = assignBulkRequest(apmPerformanceBulkRequestList, request, view);
        } else if ("UNMATCH".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = updateBulkRequest(apmPerformanceBulkRequestList, request, view);
        } else if ("UNDELETE".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = updateBulkRequest(apmPerformanceBulkRequestList, request, view);
        } else if ("DELETE".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = updateBulkRequest(apmPerformanceBulkRequestList, request, view);
        } else if ("UPDATE".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = updateBulkRequest(apmPerformanceBulkRequestList, request, view);
        } else {
            view.setViewName(JSPURL);
            return view;
        }
        view.setViewName(viewName);
        return view;
    }

    private String getBulkRequestData(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList,
        HttpServletRequest request, ModelAndView view) throws Exception {
        log.debug("Entering getBulkRequestData in ApmPerfBulkRequestController");
        String forwardKey = "";
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        if (apmPerformanceBulkRequestList.getNavigationType() == null
            || "FIRST".equalsIgnoreCase(apmPerformanceBulkRequestList.getNavigationType())
            || "".equalsIgnoreCase(apmPerformanceBulkRequestList.getNavigationType().trim())) {
            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            apmPerformanceBulkRequestList.setUserPreferences(userPreferences);
        } else {
            if (inputContext.getUserSessionState().getSearch() != null) {
                apmPerformanceBulkRequestList =
                    (ApmPerformanceBulkRequestList) inputContext.getUserSessionState().getSearch().getAlternateSearch();
                if (apmPerformanceBulkRequestList == null) {
                    apmPerformanceBulkRequestList = new ApmPerformanceBulkRequestList();
                }
                apmPerformanceBulkRequestList.setNavigationType(apmPerformanceBulkRequestList.getNavigationType());
            } else {
                inputContext.getUserSessionState().setSearch(new PerformanceSearch());
                apmPerformanceBulkRequestList = new ApmPerformanceBulkRequestList();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmPerformanceBulkRequestList.setUserPreferences(userPreferences);
            }
        }
        PerformanceSearch performanceSearch = null;
        List<Object> outValueObjects = null;
        inputContext.addInputValueObject(apmPerformanceBulkRequestList);
        try {
            outputContext = usageHandler.getApmPerfBulkRequestList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && !outValueObjects.isEmpty()) {
                apmPerformanceBulkRequestList = (ApmPerformanceBulkRequestList) outValueObjects.iterator().next();
                if (inputContext.getUserSessionState().getSearch() == null) {
                    performanceSearch = new PerformanceSearch();
                    performanceSearch.setAlternateSearch(apmPerformanceBulkRequestList);
                    inputContext.getUserSessionState().setSearch(performanceSearch);
                } else {
                    inputContext.getUserSessionState().getSearch().setAlternateSearch(apmPerformanceBulkRequestList);
                }
            } else {
                forwardKey = JSPURL;
            }
            view.getModel().put("apmPerformanceBulkRequestList", apmPerformanceBulkRequestList);
            setUserSessionState(request, inputContext.getUserSessionState());
            forwardKey = JSPURL;
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmPerfBulkRequestController :" + pse);
            log.error(pse.getMessage());
            log.error("Error Caught in ApmPerfBulkRequestController  :", pse);
            view.getModel().put(SYSTEMERROR, pse.getErrorKey());
        } catch (PrepFunctionalException pfex) {
            log.debug("Error Caught in ApmPerfBulkRequestController  :" + pfex);
            log.error(pfex.getMessage());
            log.warn("Functional Exception in ApmPerfBulkRequestController  :" + pfex);
            view.getModel().put(SYSTEMERROR, pfex.getErrorKey());
        } catch (Exception ex) {
            log.debug("Error Caught in ApmPerfBulkRequestController  :" + ex);
            log.error(ex.getMessage());
            log.error("Exception caught in ApmPerfBulkRequestController  :doWork() method", ex);
            view.getModel().put(SYSTEMERROR, ex.getMessage());
        }
        log.debug("Exiting getBulkRequestData in ApmPerfBulkRequestController");
        return forwardKey;
    }

    private String buildMedleyGroups(ApmPerformanceBulkRequestList apmPerformanceBulkRequestListForm,
        HttpServletRequest request, ModelAndView view) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering buildMedleyGroups in ApmPerformanceBulkRequestController");
        }
        String forwardKey = "";
        String operationType = apmPerformanceBulkRequestListForm.getOperationType();
        String[] multWorkId = apmPerformanceBulkRequestListForm.getMultWorkId();
        String[] selectedIndex = apmPerformanceBulkRequestListForm.getSelectedIndex();
        String[] supplierCodeArr = apmPerformanceBulkRequestListForm.getSupplierCode();
        String[] workTitleArr = apmPerformanceBulkRequestListForm.getWorkTitle();
        String[] performerNameArr = apmPerformanceBulkRequestListForm.getPerformerName();
        String[] writerNameArr = apmPerformanceBulkRequestListForm.getWriterName();
        String supplierCode = null;
        String performerName = null;
        String workTitle = null;
        String writerName = null;
        String writerExists = apmPerformanceBulkRequestListForm.getWriterExists();
        String multWorkIdValue = null;
        for (String index : selectedIndex) {
            int indexNum = Integer.parseInt(index);
            multWorkIdValue = multWorkId[indexNum];
            performerName = performerNameArr[indexNum];
            workTitle = workTitleArr[indexNum];
            supplierCode = supplierCodeArr[indexNum];
            writerName = writerNameArr[indexNum];
        }
        apmPerformanceBulkRequestListForm.setMedleySupplierCode(supplierCode);
        apmPerformanceBulkRequestListForm.setMedleyMultiWorkId(multWorkIdValue);
        apmPerformanceBulkRequestListForm.setMedleyWorkTitle(workTitle);
        apmPerformanceBulkRequestListForm.setMedleyPerformerName(performerName);
        apmPerformanceBulkRequestListForm.setMedleyWriterName(writerName);
        apmPerformanceBulkRequestListForm =
            populateForm(apmPerformanceBulkRequestListForm, new ArrayList<>(), request, "1");
        apmPerformanceBulkRequestListForm.setOperationType(operationType);
        request.setAttribute("apmPerformanceBulkRequestListForm", apmPerformanceBulkRequestListForm);
        if ("MEDLEY_NEW".equals(apmPerformanceBulkRequestListForm.getOperationType())) {
            return JSPURLMADLEY;
        }
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        ApmPerformanceBulkRequestList apmPerformanceBulkRequestList = null;
        if (apmPerformanceBulkRequestListForm.getNavigationType() == null
            || "FIRST".equalsIgnoreCase(apmPerformanceBulkRequestListForm.getNavigationType())
            || "".equalsIgnoreCase(apmPerformanceBulkRequestListForm.getNavigationType().trim())) {
            apmPerformanceBulkRequestList = new ApmPerformanceBulkRequestList();
            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            apmPerformanceBulkRequestListForm.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
                * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
            apmPerformanceBulkRequestListForm.setUserPreferences(userPreferences);
            BeanUtils.copyProperties(apmPerformanceBulkRequestList, apmPerformanceBulkRequestListForm);
        } else {
            if (inputContext.getUserSessionState().getSearch() != null) {
                apmPerformanceBulkRequestList =
                    (ApmPerformanceBulkRequestList) inputContext.getUserSessionState().getSearch().getAlternateSearch();
                if (apmPerformanceBulkRequestList == null) {
                    apmPerformanceBulkRequestList = new ApmPerformanceBulkRequestList();
                }
                apmPerformanceBulkRequestList.setNavigationType(apmPerformanceBulkRequestListForm.getNavigationType());
            } else {
                inputContext.getUserSessionState().setSearch(new PerformanceSearch());
                apmPerformanceBulkRequestList = new ApmPerformanceBulkRequestList();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmPerformanceBulkRequestListForm.setUserPreferences(userPreferences);
                BeanUtils.copyProperties(apmPerformanceBulkRequestList, apmPerformanceBulkRequestListForm);
            }
        }
        PerformanceSearch performanceSearch = null;
        List<Object> outValueObjects = null;
        apmPerformanceBulkRequestList.setMedleyMultiWorkId(multWorkIdValue);
        inputContext.addInputValueObject(apmPerformanceBulkRequestList);
        try {
            outputContext = usageHandler.getMedleyWorkInformation(inputContext);
            apmPerformanceBulkRequestList = null;
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && !(outValueObjects.isEmpty())) {
                apmPerformanceBulkRequestList = (ApmPerformanceBulkRequestList) outValueObjects.iterator().next();
                apmPerformanceBulkRequestListForm.setMedleyWorkIds(apmPerformanceBulkRequestList.getMedleyWorkIds());
                apmPerformanceBulkRequestListForm
                    .setMedleyCloneCounts(apmPerformanceBulkRequestList.getMedleyCloneCounts());
                apmPerformanceBulkRequestListForm.setMedelyView("Y");
                apmPerformanceBulkRequestListForm.setMedleySupplierCode(supplierCode);
                apmPerformanceBulkRequestListForm.setMedleyWorkTitle(workTitle);
                apmPerformanceBulkRequestListForm.setMedleyPerformerName(performerName);
                apmPerformanceBulkRequestListForm.setMedleyWriterName(writerName);
                apmPerformanceBulkRequestListForm
                    .setMedleyOriginalExists(apmPerformanceBulkRequestList.getMedleyOriginalExists());
                ArrayList<String> originalMedleyWorkList = new ArrayList<>();
                if (apmPerformanceBulkRequestList.getMedleyWorkIds() != null
                    && apmPerformanceBulkRequestList.getMedleyWorkIds().length > 0) {
                    String[] medleyOrigWorks = apmPerformanceBulkRequestList.getMedleyWorkIds();
                    for (String x : medleyOrigWorks) {
                        originalMedleyWorkList.add(x);
                    }
                }
                apmPerformanceBulkRequestListForm.setMedleyOriginalWorkList(originalMedleyWorkList);
                request.setAttribute("apmPerformanceBulkRequestListForm", apmPerformanceBulkRequestListForm);
                view.getModel().put("apmPerformanceBulkRequestList", apmPerformanceBulkRequestListForm);
            } else {
                forwardKey = JSPURL;
            }
            view.getModel().put("apmPerformanceBulkRequestList", apmPerformanceBulkRequestListForm);
            setUserSessionState(request, inputContext.getUserSessionState());
            forwardKey = JSPURL;
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmPerfBulkRequestController :" + pse);
            log.error(pse.getMessage());
            log.error("Error Caught in ApmPerfBulkRequestController  :", pse);
            view.getModel().put(SYSTEMERROR, pse.getErrorKey());
            forwardKey = JSPURL;
        } catch (PrepFunctionalException pfex) {
            log.debug("Error Caught in ApmPerfBulkRequestController  :" + pfex);
            log.error(pfex.getMessage());
            log.warn("Functional Exception in ApmPerfBulkRequestController  :" + pfex);
            view.getModel().put(SYSTEMERROR, pfex.getErrorKey());
            forwardKey = JSPURL;
        } catch (Exception ex) {
            log.debug("Error Caught in ApmPerfBulkRequestController  :" + ex);
            log.error(ex.getMessage());
            log.error("Exception caught in ApmPerfBulkRequestController :doWork() method", ex);
            view.getModel().put(SYSTEMERROR, ex.getMessage());
            forwardKey = JSPURL;
        }
        log.debug("Exiting buildMedleyGroups in ApmPerfBulkRequestController");
        return forwardKey = JSPURLMADLEY;
    }

    private ApmPerformanceBulkRequestList populateForm(ApmPerformanceBulkRequestList apmFileList,
        List<Object> invalidGroups, HttpServletRequest request, String curPageNumber) {
        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
        apmFileList.setUserPreferences(userPreferences);
        apmFileList.setNumberOfRecordsFound(Integer.parseInt(request.getParameter("tempNoOfResults")));
        apmFileList.setPageNumber(request.getParameter("tempCurPageNr"));
        apmFileList.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
            * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
        apmFileList.setOperationType("SEARCH");
        String[] workId = apmFileList.getWorkId();
        String[] supplierCode = apmFileList.getSupplierCode();
        String[] workTitle = apmFileList.getWorkTitle();
        String[] performers = apmFileList.getPerformerName();
        String[] writerName = apmFileList.getWriterName();
        String[] workPerfCount = apmFileList.getWorkPerfCount();
        String[] playCount = apmFileList.getPlayCount();
        String[] manualMatchUserId = apmFileList.getManualMatchUserId();
        String[] estimatedDollarAmounts = apmFileList.getEstimatedDollarAmount();
        String[] estimatedDollarFlags = apmFileList.getEstimatedDollarFlag();
        String[] multWorkIds = apmFileList.getMultWorkId();
        String[] assignedToUser = apmFileList.getAssignedToUser();
        List<Object> col = new ArrayList<>();
        ApmPerformanceBulkRequest pmPerformanceBulkRequest = null;
        for (int indexNum = 0; indexNum < workPerfCount.length; indexNum++) {
            pmPerformanceBulkRequest = new ApmPerformanceBulkRequest();
            pmPerformanceBulkRequest.setWorkId(workId[indexNum]);
            if (hasWorkIdErrors(workId[indexNum], invalidGroups)) {
                pmPerformanceBulkRequest.setInvalidWorkId("Y");
            }
            pmPerformanceBulkRequest.setSupplierCode(supplierCode[indexNum]);
            pmPerformanceBulkRequest.setWorkTitle(workTitle[indexNum]);
            pmPerformanceBulkRequest.setPerformerName(performers[indexNum]);
            pmPerformanceBulkRequest.setWriterName(writerName[indexNum]);
            pmPerformanceBulkRequest.setWorkPerfCount(workPerfCount[indexNum]);
            pmPerformanceBulkRequest.setPlayCount(playCount[indexNum]);
            pmPerformanceBulkRequest.setManualMatchUserId(manualMatchUserId[indexNum]);
            pmPerformanceBulkRequest.setEstimatedDollarAmount(estimatedDollarAmounts[indexNum]);
            pmPerformanceBulkRequest.setEstimatedDollarFlag(estimatedDollarFlags[indexNum]);
            pmPerformanceBulkRequest.setMultWorkId(multWorkIds[indexNum]);
            pmPerformanceBulkRequest.setAssignedToUser(assignedToUser[indexNum]);
            col.add(pmPerformanceBulkRequest);
        }
        apmFileList.setSearchResults(col);
        return apmFileList;
    }

    private boolean hasWorkIdErrors(String workId, List<Object> errorCol) {
        boolean workIdError = false;
        if (errorCol != null) {
            Iterator<?> itr = errorCol.iterator();
            ApmPerformanceBulkRequest apmPerformanceBulkRequest = null;
            while (itr.hasNext()) {
                apmPerformanceBulkRequest = (ApmPerformanceBulkRequest) itr.next();
                if (apmPerformanceBulkRequest.getWorkId().equals(workId)) {
                    return true;
                }
            }
        }
        return workIdError;
    }

    private String medelyBulkRequest(ApmPerformanceBulkRequestList apmFileListForm, HttpServletRequest request,
        ModelAndView view) throws Exception {
        log.debug("Entering private medelyBulkRequest method in ApmPerfBulkRequestController");
        String forwardKey = JSPURL;
        log.debug("getMedleyPerformerName" + apmFileListForm.getMedleyPerformerName());
        log.debug("getMedleySupplierCode" + apmFileListForm.getMedleySupplierCode());
        log.debug("getMedleyWorkTitle" + apmFileListForm.getMedleyWorkTitle());
        String[] medelyWorkIdArr = apmFileListForm.getMedleyWorkIds();
        String[] cloneCountsArr = apmFileListForm.getMedleyCloneCounts();
        if (medelyWorkIdArr != null && medelyWorkIdArr.length > 0) {
            for (String s : medelyWorkIdArr) {
                log.debug("medely work id " + s);
            }
        }
        if (cloneCountsArr != null && cloneCountsArr.length > 0) {
            for (int i = 0; i < cloneCountsArr.length; i++) {
                log.debug("medely clone counts " + cloneCountsArr[i]);
                if (ValidationUtils.isEmptyOrNull(cloneCountsArr[i])) {
                    cloneCountsArr[i] = "1";
                }
            }
        }
        List<String> medleyOriginalWorks = new ArrayList<>();
        String[] medelyWorkOrigIndicator = apmFileListForm.getMedleyWorkOriginalInd();
        if (medelyWorkOrigIndicator != null && medelyWorkOrigIndicator.length > 0) {
            for (int i = 0; i < medelyWorkOrigIndicator.length; i++) {
                log.debug("medely Original Indocator " + medelyWorkOrigIndicator[i]);
                if (medelyWorkIdArr != null && medelyWorkIdArr.length > 0) {
                    if ("Y".equals(medelyWorkOrigIndicator[i])) {
                        medleyOriginalWorks.add(medelyWorkIdArr[i]);
                    }
                }
            }
        }
        ApmPerformanceBulkRequestList apmFileList = new ApmPerformanceBulkRequestList();
        String requestTypeCode = "DELETE".equals(apmFileListForm.getOperationType())
            ? UsageConstants.BULK_REQUEST_TYPE_DELETE : UsageConstants.BULK_REQUEST_TYPE_UPDATE;
        if ("UNMATCH".equals(apmFileListForm.getOperationType())) {
            requestTypeCode = UsageConstants.BULK_REQUEST_TYPE_UNMATCH;
        }
        try {
            BeanUtils.copyProperties(apmFileList, apmFileListForm);
            apmFileList.setRequestTypeCode(requestTypeCode);
            BeanUtils.copyProperties(apmFileList, apmFileListForm);
            List<Object> outValueObjects = null;
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmFileList);
            PREPContext outputContext = null;
            outputContext = usageHandler.cloneWorkPerformances(inputContext);
            apmFileList = null;
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && !(outValueObjects.isEmpty())) {
                apmFileList = (ApmPerformanceBulkRequestList) outValueObjects.iterator().next();
                if (apmFileList.getInvalidGroups() != null && !apmFileList.getInvalidGroups().isEmpty()) {
                    String opType = apmFileListForm.getOperationType();
                    apmFileListForm = populateForm(apmFileListForm, apmFileList.getInvalidGroups(), request,
                        apmFileList.getPageNumber());
                    if ("MEDLEY_ADD".equals(opType)) {
                        apmFileListForm.setOperationType("MEDLEY_RETRIEVE");
                    }
                    apmFileListForm.setMedleyOriginalWorkList(medleyOriginalWorks);
                    request.setAttribute("apmPerformanceBulkRequestListForm", apmFileListForm);
                    Iterator<?> itr = apmFileList.getInvalidGroups().iterator();
                    ApmPerformanceBulkRequest errApmPerformanceBulkRequest = null;
                    while (itr.hasNext()) {
                        errApmPerformanceBulkRequest = (ApmPerformanceBulkRequest) itr.next();
                        List<Object> colInner = errApmPerformanceBulkRequest.getWorkIdErrors();
                        if (colInner != null && !colInner.isEmpty()) {
                            Iterator<?> ir = colInner.iterator();
                            String errString = "";
                            while (ir.hasNext()) {
                                errString = (String) ir.next();
                                view.getModel().put(SYSTEMERROR, messageSource.getMessage("field.errors.placeholder",
                                    new Object[] {}, Locale.getDefault()));
                            }
                        }

                    }
                    return forwardKey = JSPURLMADLEY;
                } else {
                    view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.apm.workmatch.update.success",
                        new Object[] {}, Locale.getDefault()));
                    apmFileListForm.setOperationType("SEARCH");
                    apmFileListForm.setMedelyView("N");
                    return getBulkRequestData(apmFileListForm, request, view);
                }
            } else {
                apmFileListForm.setOperationType("SEARCH");
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmFileListForm.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
                    * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
                if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {
                    if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                        view.getModel().put(SYSTEMMESSAGE, messageSource
                            .getMessage("message.apm.workmatch.delete.success", new Object[] {}, Locale.getDefault()));
                    } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                        view.getModel().put(SYSTEMMESSAGE, messageSource
                            .getMessage("message.apm.workmatch.unmatch.success", new Object[] {}, Locale.getDefault()));
                    } else {
                        view.getModel().put(SYSTEMMESSAGE, messageSource
                            .getMessage("message.apm.workmatch.update.success", new Object[] {}, Locale.getDefault()));
                    }
                }

                if (true) {
                    return getBulkRequestData(apmFileListForm, request, view);
                }
            }
            forwardKey = JSPURL;
        } catch (PrepSystemException pse) {

            log.debug("Error Caught in ApmPerfBulkRequestController :" + pse);
            log.error(pse.getMessage());

            log.error("Error Caught in ApmPerfBulkRequestController  :", pse);

            view.getModel().put(SYSTEMERROR, pse.getErrorKey());
            forwardKey = JSPURL;
        } catch (PrepFunctionalException pfex) {
            log.debug("Error Caught in ApmPerfBulkRequestController  :" + pfex);
            log.error(pfex.getMessage());
            log.warn("Functional Exception in ApmPerfBulkRequestController  :" + pfex);
            view.getModel().put(SYSTEMERROR, pfex.getErrorKey());
            forwardKey = JSPURL;
        } catch (Exception ex) {
            log.debug("Error Caught in ApmPerfBulkRequestController :" + ex);
            log.error(ex.getMessage());
            log.error("Exception caught in ApmPerfBulkRequestController  :doWork() method", ex);
            view.getModel().put(SYSTEMERROR, ex.getMessage());
            forwardKey = JSPURL;
        }
        if (!view.getModel().containsKey(SYSTEMERROR)) {
            if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.apm.workmatch.delete.success",
                    new Object[] {}, Locale.getDefault()));
            } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.apm.workmatch.unmatch.success",
                    new Object[] {}, Locale.getDefault()));
            } else {
                view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.apm.workmatch.update.success",
                    new Object[] {}, Locale.getDefault()));
            }
        }
        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting medelyBulkRequest of ApmFileListMultiController");
        return forwardKey;

    }

    private String assignBulkRequest(ApmPerformanceBulkRequestList apmFileListForm, HttpServletRequest request,
        ModelAndView view) {
        String forwardKey = JSPURL;
        ApmPerformanceBulkRequestList apmFileList = new ApmPerformanceBulkRequestList();
        try {
            BeanUtils.copyProperties(apmFileList, apmFileListForm);
            List<Object> col = new ArrayList<>();
            List<Object> tempCol = new ArrayList<>();
            String[] selectedIndex = apmFileListForm.getSelectedIndex();
            String[] workId = apmFileListForm.getWorkId();
            String[] supplierCode = apmFileListForm.getSupplierCode();
            String[] workTitle = apmFileListForm.getWorkTitle();
            String[] performers = apmFileListForm.getPerformerName();
            String[] writerNames = apmFileListForm.getWriterName();
            ApmPerformanceBulkRequest apmFile = null;
            for (String index : selectedIndex) {
                int indexNum = Integer.parseInt(index);
                apmFile = new ApmPerformanceBulkRequest();
                apmFile.setSupplierCode(supplierCode[indexNum]);
                apmFile.setWorkTitle(workTitle[indexNum]);
                apmFile.setPerformerName(performers[indexNum]);
                apmFile.setWriterName(writerNames[indexNum]);
                if ("ASSIGN".equals(apmFileListForm.getOperationType())) {
                    apmFile.setAssignedToUser(apmFileListForm.getAssignUser());
                }
                col.add(apmFile);
            }
            apmFileList.setSearchResults(col);
            List<Object> outValueObjects = null;
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmFileList);
            PREPContext outputContext = null;
            outputContext = usageHandler.updateAssignedUsersToWorkPerf(inputContext);
            apmFileList = null;
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && !(outValueObjects.isEmpty())) {
                apmFileList = (ApmPerformanceBulkRequestList) outValueObjects.iterator().next();
                if (apmFileList.getInvalidGroups() != null && !apmFileList.getInvalidGroups().isEmpty()) {
                } else {
                    apmFileListForm.setOperationType("SEARCH");
                    apmFileListForm.setAssignUser(null);
                    return getBulkRequestData(apmFileListForm, request, view);
                }
            } else {
                apmFileListForm.setOperationType("SEARCH");
                apmFileListForm.setAssignUser(null);
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmFileListForm.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
                    * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
                if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {
                    view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.apm.workmatch.update.success",
                        new Object[] {}, Locale.getDefault()));
                }
                return getBulkRequestData(apmFileListForm, request, view);
            }
            forwardKey = JSPURL;
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmPerfBulkRequestController :" + pse);
            log.error(pse.getMessage());
            log.error("Error Caught in ApmPerfBulkRequestController  :", pse);
            view.getModel().put(SYSTEMERROR, pse.getErrorKey());
            forwardKey = JSPURL;
        } catch (PrepFunctionalException pfex) {
            log.debug("Error Caught in ApmPerfBulkRequestController  :" + pfex);
            log.warn("Functional Exception in ApmPerfBulkRequestController  :" + pfex);
            log.error(pfex.getMessage());
            view.getModel().put(SYSTEMERROR, pfex.getErrorKey());
            forwardKey = JSPURL;
        } catch (Exception ex) {
            log.debug("Error Caught in ApmPerfBulkRequestController  :" + ex);
            log.error(ex.getMessage());
            log.error("Exception caught in ApmPerfBulkRequestController  :doWork() method", ex);
            view.getModel().put(SYSTEMERROR,
                messageSource.getMessage(SYSTEMERROR, new Object[] {}, Locale.getDefault()));
            forwardKey = JSPURL;
        }
        if (!view.getModel().containsKey(SYSTEMERROR)) {
            view.getModel().put(SYSTEMMESSAGE,
                messageSource.getMessage("message.apm.workmatch.update.success", new Object[] {}, Locale.getDefault()));
        }

        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting updateFileList of ApmFileListMultiController");
        return forwardKey;
    }

    private String updateBulkRequest(ApmPerformanceBulkRequestList apmFileListForm, HttpServletRequest request,
        ModelAndView view) {
        String forwardKey = JSPURL;
        ApmPerformanceBulkRequestList apmFileList = new ApmPerformanceBulkRequestList();
        String requestTypeCode = "DELETE".equals(apmFileListForm.getOperationType())
            ? UsageConstants.BULK_REQUEST_TYPE_DELETE : UsageConstants.BULK_REQUEST_TYPE_UPDATE;
        if ("UNMATCH".equals(apmFileListForm.getOperationType())) {
            requestTypeCode = UsageConstants.BULK_REQUEST_TYPE_UNMATCH;
        }
        if ("UNDELETE".equals(apmFileListForm.getOperationType())) {
            requestTypeCode = UsageConstants.BULK_REQUEST_TYPE_UNDELETE;
        }
        try {
            BeanUtils.copyProperties(apmFileList, apmFileListForm);
            apmFileList.setRequestTypeCode(requestTypeCode);
            List<Object> col = new ArrayList<>();
            List<Object> tempCol = new ArrayList<>();
            String[] selectedIndex = apmFileListForm.getSelectedIndex();
            String[] workId = apmFileListForm.getWorkId();
            String[] supplierCode = apmFileListForm.getSupplierCode();
            String[] workTitle = apmFileListForm.getWorkTitle();
            String[] performers = apmFileListForm.getPerformerName();
            String[] writerNames = apmFileListForm.getWriterName();
            String[] workPerfCount = apmFileListForm.getWorkPerfCount();
            String[] playCount = apmFileListForm.getPlayCount();
            String[] manualMatchUserId = apmFileListForm.getManualMatchUserId();
            String[] groupingSupplierCode = apmFileListForm.getGroupingSubpplierCode();
            String[] originalWorkId = apmFileListForm.getOriginalWorkId();
            String[] assignedToUser = apmFileListForm.getAssignedToUser();
            ApmPerformanceBulkRequest apmFile = null;
            for (String index : selectedIndex) {
                int indexNum = Integer.parseInt(index);
                apmFile = new ApmPerformanceBulkRequest();
                if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                }
                if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(requestTypeCode)) {
                } else if (UsageConstants.BULK_REQUEST_TYPE_UPDATE.equals(requestTypeCode)) {
                    log.debug("Work ID " + workId[indexNum]);
                    apmFile.setWorkId(String.valueOf(Long.parseLong(workId[indexNum])));
                } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                    apmFile.setWorkId(null);
                }
                apmFile.setSupplierCode(supplierCode[indexNum]);
                apmFile.setWorkTitle(workTitle[indexNum]);
                apmFile.setPerformerName(performers[indexNum]);
                apmFile.setWriterName(writerNames[indexNum]);
                apmFile.setWorkPerfCount(workPerfCount[indexNum]);
                apmFile.setPlayCount(playCount[indexNum]);
                apmFile.setManualMatchUserId(manualMatchUserId[indexNum]);
                apmFile.setUserId(apmFileListForm.getUserId());
                apmFile.setOriginalWorkId(originalWorkId[indexNum]);
                apmFile.setAssignedToUser(assignedToUser[indexNum]);
                if (!ValidationUtils.isEmptyOrNull(groupingSupplierCode[indexNum])) {
                    apmFile.setSupplierCode(groupingSupplierCode[indexNum]);
                }
                col.add(apmFile);

                if (!originalWorkId[indexNum].equals(workId[indexNum])) {
                    tempCol.add(apmFile);
                }

                log.debug("Added object index " + indexNum + " ApmPerformanceBulkRequest: " + apmFile);
            }
            apmFileList.setSearchResults(col);
            List<Object> outValueObjects = null;

            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmFileList);

            PREPContext outputContext = null;
            outputContext = usageHandler.updateApmPerfBulkRequestList(inputContext);
            apmFileList = null;
            outValueObjects = outputContext.getOutputValueObjects();

            if ((outValueObjects != null) && !(outValueObjects.isEmpty())) {
                apmFileList = (ApmPerformanceBulkRequestList) outValueObjects.iterator().next();
                if (apmFileList.getInvalidGroups() != null && !apmFileList.getInvalidGroups().isEmpty()) {
                    apmFileListForm = populateForm(apmFileListForm, apmFileList.getInvalidGroups(), request,
                        apmFileList.getPageNumber());
                    request.setAttribute("apmPerformanceBulkRequestListForm", apmFileListForm);
                    Iterator<?> itr = apmFileList.getInvalidGroups().iterator();
                    ApmPerformanceBulkRequest errApmPerformanceBulkRequest = null;
                    while (itr.hasNext()) {
                        errApmPerformanceBulkRequest = (ApmPerformanceBulkRequest) itr.next();
                        List<Object> colInner = errApmPerformanceBulkRequest.getWorkIdErrors();
                        if (colInner != null && !colInner.isEmpty()) {
                            Iterator<?> ir = colInner.iterator();
                            String errString = "";
                            while (ir.hasNext()) {
                                errString = (String) ir.next();
                                view.getModel().put(SYSTEMERROR, messageSource.getMessage("field.errors.placeholder",
                                    new Object[] {}, Locale.getDefault()));
                            }
                        }
                    }
                    return JSPURL;
                } else {
                    apmFileListForm.setOperationType("SEARCH");
                    UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                    apmFileListForm.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
                        * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
                    if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {
                        if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                            view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(
                                "message.apm.workmatch.delete.success", new Object[] {}, Locale.getDefault()));
                        } else if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(requestTypeCode)) {
                            if ("Y".equalsIgnoreCase(apmFileList.getInvalidUndeleteHeaderExists()))
                                view.getModel().put(SYSTEMERROR,
                                    messageSource.getMessage(("error.apm.workmatch.undelete.invalidheader"),
                                        new Object[] {}, Locale.getDefault()));
                            view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(
                                "message.apm.workmatch.undelete.success", new Object[] {}, Locale.getDefault()));
                        } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                            view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(
                                "message.apm.workmatch.unmatch.success", new Object[] {}, Locale.getDefault()));
                        } else {
                            view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(
                                "message.apm.workmatch.update.success", new Object[] {}, Locale.getDefault()));
                        }
                    }

                    if (true) {
                        return getBulkRequestData(apmFileListForm, request, view);
                    }
                }
            } else {
                apmFileListForm.setOperationType("SEARCH");
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmFileListForm.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
                    * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
                if (!view.getModel().containsKey(SYSTEMERROR) && outputContext != null) {
                    if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                        view.getModel().put(SYSTEMMESSAGE, messageSource
                            .getMessage("message.apm.workmatch.delete.success", new Object[] {}, Locale.getDefault()));
                    }

                    else if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(requestTypeCode)) {
                        view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(
                            "message.apm.workmatch.undelete.success", new Object[] {}, Locale.getDefault()));

                    } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                        view.getModel().put(SYSTEMMESSAGE, messageSource
                            .getMessage("message.apm.workmatch.unmatch.success", new Object[] {}, Locale.getDefault()));
                    } else {
                        view.getModel().put(SYSTEMMESSAGE, messageSource
                            .getMessage("message.apm.workmatch.update.success", new Object[] {}, Locale.getDefault()));
                    }
                }

                if (true) {
                    return getBulkRequestData(apmFileListForm, request, view);
                }
            }
            forwardKey = JSPURL;
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmPerfBulkRequestController :" + pse);
            log.error(pse.getMessage());
            log.error("Error Caught in ApmPerfBulkRequestController  :", pse);
            view.getModel().put(SYSTEMERROR, pse.getErrorKey());
            forwardKey = JSPURL;
        } catch (PrepFunctionalException pfex) {
            log.debug("Error Caught in ApmPerfBulkRequestController  :" + pfex);
            log.error(pfex.getMessage());
            log.warn("Functional Exception in ApmPerfBulkRequestController  :" + pfex);
            view.getModel().put(SYSTEMERROR, pfex.getErrorKey());
            forwardKey = JSPURL;
        } catch (Exception ex) {
            log.debug("Error Caught in ApmPerfBulkRequestController  :" + ex);
            log.error(ex.getMessage());
            log.error("Exception caught in ApmPerfBulkRequestController  :doWork() method", ex);
            view.getModel().put(SYSTEMERROR,
                messageSource.getMessage(SYSTEMERROR, new Object[] {}, Locale.getDefault()));
            forwardKey = JSPURL;
        }
        if (!view.getModel().containsKey(SYSTEMERROR)) {
            if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.apm.workmatch.delete.success",
                    new Object[] {}, Locale.getDefault()));
            } else if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(requestTypeCode)) {
                view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.apm.workmatch.undelete.success",
                    new Object[] {}, Locale.getDefault()));
            } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.apm.workmatch.unmatch.success",
                    new Object[] {}, Locale.getDefault()));
            } else {
                view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage("message.apm.workmatch.update.success",
                    new Object[] {}, Locale.getDefault()));
            }
        }
        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting updateFileList of ApmFileListMultiController");

        return forwardKey;

    }
}
