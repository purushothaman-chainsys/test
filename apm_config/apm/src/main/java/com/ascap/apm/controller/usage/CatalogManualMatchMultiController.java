package com.ascap.apm.controller.usage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.ApmPerformanceBulkRequest;
import com.ascap.apm.vob.usage.ApmPerformanceBulkRequestList;
import com.ascap.apm.vob.usage.PerformanceSearch;

@Controller
public class CatalogManualMatchMultiController extends BaseUsageController {

    private static final String CATALOGMANUALMATCH = "/usage/catalogManualMatch";

    private static final String MEDLEY = "/usage/apmMedleyDetailsCatalog";

    private static final String SYSTEMMESSAGE = "systemmessage";

    private static final String SYSTEMERROR = "systemerror";

    private static final String DELETESUCCESS = "message.apm.workmatch.delete.success";

    private static final String UNDELETESUCCESS = "message.apm.workmatch.undelete.success";

    private static final String UNMATCHSUCCESS = "message.apm.workmatch.unmatch.success";

    private static final String UPDATESUCCESS = "message.apm.workmatch.update.success";

    /**
     * Constructor
     */
    public CatalogManualMatchMultiController() {
        super();
    }

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/catalogManualMatchRequest", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView submitPreferences(
        @ModelAttribute("apmPerformanceBulkRequestList") ApmPerformanceBulkRequestList apmPerformanceBulkRequestList,
        HttpServletRequest request, HttpServletResponse response, ModelAndView view, BindingResult bindingResult)
            throws PrepFunctionalException, PrepSystemException  {

        Enumeration<String> e = request.getParameterNames();
        String viewName = "";
        String val = "";
        int totalparams = 0;
        while (e.hasMoreElements()) {
            val = e.nextElement();
            String[] numvalues = request.getParameterValues(val);
            log.debug("param : " + val + "   count " + numvalues.length);
            totalparams += numvalues.length;
        }
        log.debug("TOTAL REQUEST PARAMETERS : " + totalparams);

        if ("UPDATE".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = updateBulkRequest(apmPerformanceBulkRequestList, request, view, bindingResult);
        } else if ("DELETE".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = updateBulkRequest(apmPerformanceBulkRequestList, request, view, bindingResult);
        } else if ("UNMATCH".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = updateBulkRequest(apmPerformanceBulkRequestList, request, view, bindingResult);
        } else if ("UNDELETE".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = updateBulkRequest(apmPerformanceBulkRequestList, request, view, bindingResult);
        } else if ("SEARCH".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = getBulkRequestData(apmPerformanceBulkRequestList, request, view);
        } else if ("MEDLEY_UPDATE".equals(apmPerformanceBulkRequestList.getOperationType())
            || "MEDLEY_ADD".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = medelyBulkRequest(apmPerformanceBulkRequestList, request, view, bindingResult);
        } else if ("MEDLEY_RETRIEVE".equals(apmPerformanceBulkRequestList.getOperationType())
            || "MEDLEY_UNMATCH".equals(apmPerformanceBulkRequestList.getOperationType())
            || "MEDLEY_NEW".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = buildMedleyGroups(apmPerformanceBulkRequestList, request, view);
        } else if ("ASSIGN".equals(apmPerformanceBulkRequestList.getOperationType())
            || "UNASSIGN".equals(apmPerformanceBulkRequestList.getOperationType())) {
            viewName = assignBulkRequest(apmPerformanceBulkRequestList, request, view, bindingResult);
        } else {
            viewName = CATALOGMANUALMATCH;
        }
        view.setViewName(viewName);

        return view;

    }

    private String getBulkRequestData(ApmPerformanceBulkRequestList apmPerformanceBulkRequestListForm,
        HttpServletRequest request, ModelAndView view) throws PrepFunctionalException, PrepSystemException {

        if (log.isDebugEnabled()) {
            log.debug("Entering getBulkRequestData in CatalogManualMatchMultiController");
        }

        String forwardKey = null;

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        ApmPerformanceBulkRequestList apmPerformanceBulkReqList = null;

        if (apmPerformanceBulkRequestListForm.getNavigationType() == null
            || "FIRST".equalsIgnoreCase(apmPerformanceBulkRequestListForm.getNavigationType())
            || "".equalsIgnoreCase(apmPerformanceBulkRequestListForm.getNavigationType().trim())) {

            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            apmPerformanceBulkRequestListForm.setUserPreferences(userPreferences);

        } else {
            if (inputContext.getUserSessionState().getSearch() != null) {
                apmPerformanceBulkReqList =
                    (ApmPerformanceBulkRequestList) inputContext.getUserSessionState().getSearch().getAlternateSearch();

                if (apmPerformanceBulkReqList == null) {
                    apmPerformanceBulkReqList = new ApmPerformanceBulkRequestList();
                }
                apmPerformanceBulkReqList.setNavigationType(apmPerformanceBulkRequestListForm.getNavigationType());

            } else {
                inputContext.getUserSessionState().setSearch(new PerformanceSearch());
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmPerformanceBulkRequestListForm.setUserPreferences(userPreferences);

            }
        }

        PerformanceSearch performanceSearch = null;

        List<Object> outValueObjects = null;

        inputContext.addInputValueObject(apmPerformanceBulkRequestListForm);
        try {
            outputContext = usageHandler.getCatalogManualMatchList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && !(outValueObjects.isEmpty())) {
                apmPerformanceBulkReqList = (ApmPerformanceBulkRequestList) outValueObjects.iterator().next();
                apmPerformanceBulkReqList.setSelectedIndex(null);
                view.getModel().put("apmPerformanceBulkRequestList", apmPerformanceBulkReqList);
                apmPerformanceBulkReqList.setNavigationType(null);

                if (inputContext.getUserSessionState().getSearch() == null) {
                    performanceSearch = new PerformanceSearch();
                    performanceSearch.setAlternateSearch(apmPerformanceBulkReqList);
                    inputContext.getUserSessionState().setSearch(performanceSearch);
                } else {
                    inputContext.getUserSessionState().getSearch().setAlternateSearch(apmPerformanceBulkReqList);
                }
            }
            // set the UserSessionState objec to the session.
            setUserSessionState(request, inputContext.getUserSessionState());
            forwardKey = CATALOGMANUALMATCH;
        } catch (PrepSystemException pse) {
            if (log.isDebugEnabled()) {
                log.debug("Error Caught in CatalogManualMatchMultiController :" + pse);
                log.error(pse.getMessage());
            }
            log.error("Error Caught in CatalogManualMatchMultiController  :", pse);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        } catch (PrepFunctionalException pfex) {
            if (log.isDebugEnabled()) {
                log.debug("Error Caught in CatalogManualMatchMultiController  :" + pfex);
                log.error(pfex.getMessage());
            }
            log.warn("Functional Exception in CatalogManualMatchMultiController  :" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getErrorKey(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        } catch (Exception ex) {
            if (log.isDebugEnabled()) {
                log.debug("Error Caught in CatalogManualMatchMultiController  :" + ex);
                log.error(ex.getMessage());
            }
            log.error("Exception caught in CatalogManualMatchMultiController  :getBulkRequestData() method", ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(ex.getMessage(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting getBulkRequestData in CatalogManualMatchMultiController");
        }

        return forwardKey;

    }

    private String assignBulkRequest(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList,
        HttpServletRequest request, ModelAndView view, BindingResult bindingResult) {

        String forwardKey = null;
        int apmRequestCount = 0;
        ApmPerformanceBulkRequestList apmFileList = new ApmPerformanceBulkRequestList();
        try {

            List<Object> col = new ArrayList<>();
            int apmRequestSize = apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().size();
            if (apmRequestSize > 0) {
                apmRequestCount = apmRequestSize - 1;
            }
            String[] selectedIndex = apmPerformanceBulkRequestList.getSelectedIndex();
            String supplierCode =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmRequestCount).getSupplierCode();
            String workTitle =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmRequestCount).getWorkTitle();
            String performers =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmRequestCount).getPerformerName();
            String writerNames =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmRequestCount).getWriterName();

            ApmPerformanceBulkRequest apmFile = null;

            for (String index : selectedIndex) {
                apmFile = new ApmPerformanceBulkRequest();
                apmFile.setSupplierCode(supplierCode);
                apmFile.setWorkTitle(workTitle);
                apmFile.setPerformerName(performers);
                apmFile.setWriterName(writerNames);
                if ("ASSIGN".equals(apmPerformanceBulkRequestList.getOperationType())) {
                    apmFile.setAssignedToUser(apmPerformanceBulkRequestList.getAssignUser());
                }

                col.add(apmFile);
            }
            apmPerformanceBulkRequestList.setSearchResults(col);
            List<Object> outValueObjects = null;
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmPerformanceBulkRequestList);
            PREPContext outputContext = null;

            outputContext = usageHandler.updateAssignedUsersToWorkPerf(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmFileList = (ApmPerformanceBulkRequestList) outValueObjects.iterator().next();

                if (apmFileList.getInvalidGroups() != null && !apmFileList.getInvalidGroups().isEmpty()) {
                } else {
                    apmPerformanceBulkRequestList.setOperationType("SEARCH");
                    apmPerformanceBulkRequestList.setAssignUser(null);
                    return getBulkRequestData(apmPerformanceBulkRequestList, request, view);
                }
            } else {
                apmPerformanceBulkRequestList.setOperationType("SEARCH");
                apmPerformanceBulkRequestList.setAssignUser(null);
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmPerformanceBulkRequestList.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
                    * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
                if (!bindingResult.hasErrors()) {
                    view.getModel().put(SYSTEMMESSAGE,
                        messageSource.getMessage(UPDATESUCCESS, new Object[] {"catalog Assign"}, Locale.getDefault()));
                }

                return getBulkRequestData(apmPerformanceBulkRequestList, request, view);
            }
            forwardKey = CATALOGMANUALMATCH;
        } catch (PrepSystemException pse) {
            if (log.isDebugEnabled()) {
                log.debug("Error Caught in CatalogManualMatchMultiController :" + pse);
                log.error(pse.getMessage());
            }
            log.error("Error Caught in CatalogManualMatchMultiController  :", pse);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getMessage(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        } catch (PrepFunctionalException pfex) {
            if (log.isDebugEnabled()) {
                log.debug("Error Caught in CatalogManualMatchMultiController  :" + pfex);
                log.error(pfex.getMessage());
            }
            log.warn("Functional Exception in CatalogManualMatchMultiController  :" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getMessage(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        } catch (Exception ex) {
            if (log.isDebugEnabled()) {
                log.debug("Error Caught in CatalogManualMatchMultiController  :" + ex);
                log.error(ex.getMessage());
            }
            log.error("Exception caught in CatalogManualMatchMultiController  :assignBulkRequest() method", ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(ex.getMessage(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        }

        if (!bindingResult.hasErrors()) {
            view.getModel().put(SYSTEMMESSAGE,
                messageSource.getMessage(UPDATESUCCESS, new Object[] {"catalog Assign"}, Locale.getDefault()));
        }

        if (log.isDebugEnabled()) {
            log.debug("forwardKey is " + forwardKey);
            log.debug("Exiting updateFileList of ApmFileListMultiAction");
        }

        return forwardKey;

    }

    private String updateBulkRequest(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList,
        HttpServletRequest request, ModelAndView view, BindingResult bindingResult)
        throws PrepSystemException, PrepFunctionalException {

        String forwardKey = CATALOGMANUALMATCH;
        int apmRequestSize = 0;
        int apmIndexCount = 0;
        ApmPerformanceBulkRequestList apmFileList = null;
        String requestTypeCode = "DELETE".equals(apmPerformanceBulkRequestList.getOperationType())
            ? UsageConstants.BULK_REQUEST_TYPE_DELETE : UsageConstants.BULK_REQUEST_TYPE_UPDATE;
        if ("UNMATCH".equals(apmPerformanceBulkRequestList.getOperationType())) {
            requestTypeCode = UsageConstants.BULK_REQUEST_TYPE_UNMATCH;
        }
        if ("UNDELETE".equals(apmPerformanceBulkRequestList.getOperationType())) {
            requestTypeCode = UsageConstants.BULK_REQUEST_TYPE_UNDELETE;
        }

        try {
            apmPerformanceBulkRequestList.setRequestTypeCode(requestTypeCode);

            List<Object> col = new ArrayList<>();
            Collection<ApmPerformanceBulkRequest> tempCol = new ArrayList<>();

            apmRequestSize = apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().size();
            if (apmRequestSize > 0) {
                apmIndexCount = apmRequestSize - 1;
            }
            String[] selectedIndex = apmPerformanceBulkRequestList.getSelectedIndex();
            String[] workId = apmPerformanceBulkRequestList.getWorkId();
            String supplierCode =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getSupplierCode();
            String workTitle =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getWorkTitle();
            String performers =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getPerformerName();
            String writerNames =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getWriterName();
            String workPerfCount =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getWorkPerfCount();
            String playCount =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getPlayCount();
            String manualMatchUserId =
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getManualMatchUserId();
            String[] groupingSupplierCode = apmPerformanceBulkRequestList.getGroupingSubpplierCode();
            String[] originalWorkId =
                apmPerformanceBulkRequestList.getOriginalWorkId();
            String[] assignedToUser =
                apmPerformanceBulkRequestList.getAssignedToUser();
            ApmPerformanceBulkRequest apmFile = null;
            String originalWorkIds =null;
            for (String index : selectedIndex) {
                int indexNum = Integer.parseInt(index);
                apmFile = new ApmPerformanceBulkRequest();
                if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                }
                if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(requestTypeCode)) {
                } else if (UsageConstants.BULK_REQUEST_TYPE_UPDATE.equals(requestTypeCode)) {

                    if (log.isDebugEnabled()) {
                        log.debug("Work ID " + workId[indexNum]);
                    }
                    apmFile.setWorkId(String.valueOf(Long.parseLong(workId[indexNum])));
                } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                    apmFile.setWorkId(null);
                }
                apmFile.setSupplierCode(supplierCode);
                apmFile.setWorkTitle(workTitle);
                apmFile.setPerformerName(performers);
                apmFile.setWriterName(writerNames);
                apmFile.setWorkPerfCount(workPerfCount);
                apmFile.setPlayCount(playCount);
                apmFile.setManualMatchUserId(manualMatchUserId);
                apmFile.setUserId(apmPerformanceBulkRequestList.getUserId());
                if(originalWorkId.length >0) {
                apmFile.setOriginalWorkId(originalWorkId[indexNum]);
                }else {
                    apmFile.setOriginalWorkId(originalWorkIds);  
                }
                if(assignedToUser.length >0) {
                apmFile.setAssignedToUser(assignedToUser[indexNum]);
                }
                if (!ValidationUtils.isEmptyOrNull(groupingSupplierCode[indexNum])) {
                    apmFile.setSupplierCode(groupingSupplierCode[indexNum]);
                }
                col.add(apmFile);
                apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().size();

                if (workId.length > 0 && originalWorkId.length>0 ) {
                    if (!originalWorkId[indexNum].equals(workId[indexNum])) {
                        tempCol.add(apmFile);
                    }
                }

                log.debug("Added object index " + indexNum + " ApmPerformanceBulkRequest: " + apmFile);
            }
            apmPerformanceBulkRequestList.setSearchResults(col);
            List<Object> outValueObjects = null;
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmPerformanceBulkRequestList);
            PREPContext outputContext = null;
            outputContext = usageHandler.updateCatalogManualMatchList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();

            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmFileList = (ApmPerformanceBulkRequestList) outValueObjects.iterator().next();

                if (apmFileList.getInvalidGroups() != null && !apmFileList.getInvalidGroups().isEmpty()) {
                    apmPerformanceBulkRequestList =
                        populateForm(apmPerformanceBulkRequestList, apmFileList.getInvalidGroups(), request);

                    view.getModel().put("apmPerformanceBulkRequestList", apmPerformanceBulkRequestList);

                    Iterator<Object> itr = apmFileList.getInvalidGroups().iterator();
                    ApmPerformanceBulkRequest errApmPerformanceBulkRequest = null;
                    while (itr.hasNext()) {
                        errApmPerformanceBulkRequest = (ApmPerformanceBulkRequest) itr.next();
                        List<Object> colInner = errApmPerformanceBulkRequest.getWorkIdErrors();
                        if (colInner != null && !colInner.isEmpty()) {
                            Iterator<Object> ir = colInner.iterator();
                            String errString = "";
                            while (ir.hasNext()) {
                                errString = (String) ir.next();
                                log.debug(errString);
                                view.getModel().put(SYSTEMERROR, messageSource.getMessage("field.errors.placeholder",
                                    new Object[] {"Catalog Match"}, Locale.getDefault()));
                            }
                        }
                    }
                    forwardKey = CATALOGMANUALMATCH;
                    return forwardKey;

                } else {
                    apmPerformanceBulkRequestList.setOperationType("SEARCH");
                    UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                    apmPerformanceBulkRequestList.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
                        * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
                    if (!bindingResult.hasErrors()) {
                        if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                            view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(DELETESUCCESS,
                                new Object[] {"catalog Delete"}, Locale.getDefault()));
                        } // undelete condition
                        else if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(requestTypeCode)) {
                            // if invalid header found add error message else success message
                            if ("Y".equalsIgnoreCase(apmFileList.getInvalidUndeleteHeaderExists())) {
                                view.getModel().put(SYSTEMERROR,
                                    messageSource.getMessage("error.apm.workmatch.undelete.invalidheader",
                                        new Object[] {"catalog UnDelete"}, Locale.getDefault()));
                                view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(UNDELETESUCCESS,
                                    new Object[] {"catalog UnDelete"}, Locale.getDefault()));
                            }
                        } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                            view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(UNMATCHSUCCESS,
                                new Object[] {"catalog UnMatch"}, Locale.getDefault()));
                        } else {
                            view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(UPDATESUCCESS,
                                new Object[] {"catalog Update"}, Locale.getDefault()));
                        }
                    }
                    if (true) {
                        return getBulkRequestData(apmPerformanceBulkRequestList, request, view);
                    }
                }
            } else {
                apmPerformanceBulkRequestList.setOperationType("SEARCH");
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmPerformanceBulkRequestList.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
                    * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
                if (!bindingResult.hasErrors()) {
                    if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                        view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(DELETESUCCESS,
                            new Object[] {"Performance Header"}, Locale.getDefault()));
                    } else if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(requestTypeCode)) {
                        view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(UNDELETESUCCESS,
                            new Object[] {"Performance Header"}, Locale.getDefault()));
                    } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                        view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(UNMATCHSUCCESS,
                            new Object[] {"Performance Header"}, Locale.getDefault()));
                    } else {
                        view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(UPDATESUCCESS,
                            new Object[] {"Performance Header"}, Locale.getDefault()));
                    }
                }

                if (true) {
                    return getBulkRequestData(apmPerformanceBulkRequestList, request, view);
                }
            }

        } catch (PrepSystemException pse) {
            log.debug("Error Caught in CatalogManualMatchMultiController :" + pse);
            log.error(pse.getMessage());
            log.error("Error Caught in CatalogManualMatchMultiController  :", pse);

            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        } catch (PrepFunctionalException pfex) {

            log.debug("Error Caught in CatalogManualMatchMultiController  :" + pfex);
            log.error(pfex.getMessage());
            log.warn("Functional Exception in CatalogManualMatchMultiController  :" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getErrorKey(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        } catch (Exception ex) {
            log.debug("Error Caught in CatalogManualMatchMultiController  :" + ex);
            log.error(ex.getMessage());

            log.error("Exception caught in CatalogManualMatchMultiController  :updateBulkRequest() method", ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(ex.getMessage(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        }
        if (!bindingResult.hasErrors()) {
            if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                view.getModel().put(SYSTEMMESSAGE,
                    messageSource.getMessage(DELETESUCCESS, new Object[] {"catalog update"}, Locale.getDefault()));
            } else if (UsageConstants.BULK_REQUEST_TYPE_UNDELETE.equals(requestTypeCode)) {
                view.getModel().put(SYSTEMMESSAGE,
                    messageSource.getMessage(UNDELETESUCCESS, new Object[] {"catalog update"}, Locale.getDefault()));
            } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                view.getModel().put(SYSTEMMESSAGE,
                    messageSource.getMessage(UNMATCHSUCCESS, new Object[] {"catalog update"}, Locale.getDefault()));
            } else {
                view.getModel().put(SYSTEMMESSAGE,
                    messageSource.getMessage(UPDATESUCCESS, new Object[] {"catalog update"}, Locale.getDefault()));
            }
        }

        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting updateFileList of ApmFileListMultiAction");

        return forwardKey;
    }

    private ApmPerformanceBulkRequestList populateForm(ApmPerformanceBulkRequestList apmFileListForm,
        List<Object> list, HttpServletRequest request) {

        UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
        apmFileListForm.setUserPreferences(userPreferences);

        apmFileListForm.setNumberOfRecordsFound(Integer.parseInt(request.getParameter("tempNoOfResults")));
        apmFileListForm.setPageNumber(request.getParameter("tempCurPageNr"));
        apmFileListForm.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
            * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));

        apmFileListForm.setOperationType("SEARCH");
        int apmIndexCount = 0;
        int apmRequestSize = apmFileListForm.getApmPerformanceBulkRequest().size();
        if (apmRequestSize > 0) {
            apmIndexCount = apmRequestSize - 1;
        }

        String[] workId = apmFileListForm.getWorkId();
        String supplierCode = apmFileListForm.getApmPerformanceBulkRequest().get(apmIndexCount).getSupplierCode();
        String workTitle = apmFileListForm.getApmPerformanceBulkRequest().get(apmIndexCount).getWorkTitle();
        String performers = apmFileListForm.getApmPerformanceBulkRequest().get(apmIndexCount).getPerformerName();
        String writerName = apmFileListForm.getApmPerformanceBulkRequest().get(apmIndexCount).getWriterName();
        String workPerfCount = apmFileListForm.getApmPerformanceBulkRequest().get(apmIndexCount).getWorkPerfCount();
        String playCount = apmFileListForm.getApmPerformanceBulkRequest().get(apmIndexCount).getPlayCount();
        String manualMatchUserId =
            apmFileListForm.getApmPerformanceBulkRequest().get(apmIndexCount).getManualMatchUserId();
        String[] multWorkIds = apmFileListForm.getMultWorkId();

        String priority = apmFileListForm.getApmPerformanceBulkRequest().get(apmIndexCount).getPriority();
        String[] assignedToUser = apmFileListForm.getAssignedToUser();
        List<Object> col = new ArrayList<>();
        ApmPerformanceBulkRequest pmPerformanceBulkRequest = null;
        String multWorkIdValue = null;
        for (int indexNum = 0; indexNum < workPerfCount.length(); indexNum++) {
            pmPerformanceBulkRequest = new ApmPerformanceBulkRequest();

            if (workId.length > 0) {
                pmPerformanceBulkRequest.setWorkId(workId[indexNum]);

                if (hasWorkIdErrors(workId[indexNum], list)) {
                    pmPerformanceBulkRequest.setInvalidWorkId("Y");
                }
            }
            pmPerformanceBulkRequest.setSupplierCode(supplierCode);
            pmPerformanceBulkRequest.setWorkTitle(workTitle);
            pmPerformanceBulkRequest.setPerformerName(performers);
            pmPerformanceBulkRequest.setWriterName(writerName);
            pmPerformanceBulkRequest.setWorkPerfCount(workPerfCount);
            pmPerformanceBulkRequest.setPlayCount(playCount);
            pmPerformanceBulkRequest.setManualMatchUserId(manualMatchUserId);
            if(multWorkIds.length>0) {
            pmPerformanceBulkRequest.setMultWorkId(multWorkIds[indexNum]);
            }else {
                pmPerformanceBulkRequest.setMultWorkId(multWorkIdValue);
            }
            if(assignedToUser.length >0) {
                pmPerformanceBulkRequest.setAssignedToUser(assignedToUser[indexNum]);
            }
            pmPerformanceBulkRequest.setPriority(priority);
            col.add(pmPerformanceBulkRequest);
        }

        apmFileListForm.setSearchResults(col);
        return apmFileListForm;
    }

    private boolean hasWorkIdErrors(String workId, List<Object> errorCol) {
        boolean workIdError = false;

        if (errorCol != null) {
            Iterator<Object> itr = errorCol.iterator();
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

    private String buildMedleyGroups(ApmPerformanceBulkRequestList apmPerformanceBulkRequestList,
        HttpServletRequest request, ModelAndView view) throws PrepSystemException, PrepFunctionalException {

            log.debug("Entering buildMedleyGroups in CatalogManualMatchMultiController");

        String forwardKey = null;
        int apmIndexCount = 0;
        int apmRequestSize = apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().size();
        if (apmRequestSize > 0) {
            apmIndexCount = apmRequestSize - 1;
        }
        String operationType = apmPerformanceBulkRequestList.getOperationType();

        String[] multWorkId =
            apmPerformanceBulkRequestList.getMultWorkId();
        String[] selectedIndex = apmPerformanceBulkRequestList.getSelectedIndex();
        String supplierCodeArr =
            apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getSupplierCode();
        String workTitleArr =
            apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getWorkTitle();
        String performerNameArr =
            apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getPerformerName();
        String writerNameArr =
            apmPerformanceBulkRequestList.getApmPerformanceBulkRequest().get(apmIndexCount).getWriterName();

        String supplierCode = null;
        String performerName = null;
        String workTitle = null;
        String writerName = null;

        String multWorkIdValue = null;
        for (String index : selectedIndex) {
            log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxx " + index);
            int indexNum = Integer.parseInt(index);
            if (multWorkId.length>0) {
            multWorkIdValue = multWorkId[indexNum];
            }
            performerName = performerNameArr;
            workTitle = workTitleArr;
            supplierCode = supplierCodeArr;
            writerName = writerNameArr;
        }
        apmPerformanceBulkRequestList.setMedleySupplierCode(supplierCode);
        apmPerformanceBulkRequestList.setMedleyMultiWorkId(multWorkIdValue);
        apmPerformanceBulkRequestList.setMedleyWorkTitle(workTitle);
        apmPerformanceBulkRequestList.setMedleyPerformerName(performerName);
        apmPerformanceBulkRequestList.setMedleyWriterName(writerName);
        apmPerformanceBulkRequestList = populateForm(apmPerformanceBulkRequestList, new ArrayList(), request);
        apmPerformanceBulkRequestList.setOperationType(operationType);
        view.getModel().put("apmPerformanceBulkRequestList", apmPerformanceBulkRequestList);

        if ("MEDLEY_NEW".equals(apmPerformanceBulkRequestList.getOperationType())) {
            forwardKey = MEDLEY;
            return forwardKey;
        }

        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        if (apmPerformanceBulkRequestList.getNavigationType() == null
            || "FIRST".equalsIgnoreCase(apmPerformanceBulkRequestList.getNavigationType())
            || "".equalsIgnoreCase(apmPerformanceBulkRequestList.getNavigationType().trim())) {

            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            apmPerformanceBulkRequestList.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
                * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
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

        List<Object> outValueObjects = null;
        apmPerformanceBulkRequestList.setMedleyMultiWorkId(multWorkIdValue);
        inputContext.addInputValueObject(apmPerformanceBulkRequestList);
        try {
            outputContext = usageHandler.getMedleyWorkInformation(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmPerformanceBulkRequestList = (ApmPerformanceBulkRequestList) outValueObjects.iterator().next();
                apmPerformanceBulkRequestList.setMedleyWorkIds(apmPerformanceBulkRequestList.getMedleyWorkIds());
                apmPerformanceBulkRequestList
                    .setMedleyCloneCounts(apmPerformanceBulkRequestList.getMedleyCloneCounts());
                apmPerformanceBulkRequestList.setMedelyView("Y");
                apmPerformanceBulkRequestList.setMedleySupplierCode(supplierCode);
                apmPerformanceBulkRequestList.setMedleyWorkTitle(workTitle);
                apmPerformanceBulkRequestList.setMedleyPerformerName(performerName);
                apmPerformanceBulkRequestList.setMedleyWriterName(writerName);
                apmPerformanceBulkRequestList
                    .setMedleyOriginalExists(apmPerformanceBulkRequestList.getMedleyOriginalExists());

                ArrayList<String> originalMedleyWorkList = new ArrayList<>();
                if (apmPerformanceBulkRequestList.getMedleyWorkIds() != null
                    && apmPerformanceBulkRequestList.getMedleyWorkIds().length > 0) {
                    String[] medleyOrigWorks = apmPerformanceBulkRequestList.getMedleyWorkIds();
                    for (String x : medleyOrigWorks) {
                        originalMedleyWorkList.add(x);
                    }
                }
                apmPerformanceBulkRequestList.setMedleyOriginalWorkList(originalMedleyWorkList);

                view.getModel().put("apmPerformanceBulkRequestListForm", apmPerformanceBulkRequestList);
                apmPerformanceBulkRequestList.setNavigationType(null);
            }
            setUserSessionState(request, inputContext.getUserSessionState());
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in CatalogManualMatchMultiController :" + pse);
            log.error(pse.getMessage());
            log.error("Error Caught in CatalogManualMatchMultiController  :", pse);

            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getMessage(), null, Locale.getDefault()));
        } catch (PrepFunctionalException pfex) {
            log.debug("Error Caught in CatalogManualMatchMultiController  :" + pfex);
            log.error(pfex.getMessage());
            log.warn("Functional Exception in CatalogManualMatchMultiController  :" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getMessage(), null, Locale.getDefault()));
        } catch (Exception ex) {
            if (log.isDebugEnabled()) {
                log.debug("Error Caught in CatalogManualMatchMultiController  :" + ex);
                log.error(ex.getMessage());

            }
            log.error("Exception caught in CatalogManualMatchMultiController  :buildMedleyGroups() method", ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(ex.getMessage(), null, Locale.getDefault()));
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting buildMedleyGroups in CatalogManualMatchMultiController");
        }

        return MEDLEY;
    }

    private String medelyBulkRequest(ApmPerformanceBulkRequestList apmFileList, HttpServletRequest request,
        ModelAndView view, BindingResult binding)throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering private medelyBulkRequest method in CatalogManualMatchMultiAction");
        String forwardKey = CATALOGMANUALMATCH;

        log.debug("getMedleyPerformerName" + apmFileList.getMedleyPerformerName());
        log.debug("getMedleySupplierCode" + apmFileList.getMedleySupplierCode());
        log.debug("getMedleyWorkTitle" + apmFileList.getMedleyWorkTitle());

        String[] medelyWorkIdArr = apmFileList.getMedleyWorkIds();
        String[] cloneCountsArr = apmFileList.getMedleyCloneCounts();
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
        String[] medelyWorkOrigIndicator = apmFileList.getMedleyWorkOriginalInd();
        if (medelyWorkOrigIndicator != null && medelyWorkOrigIndicator.length > 0) {
            for (int i = 0; i < medelyWorkOrigIndicator.length; i++) {
                log.debug("medely Original Indocator " + medelyWorkOrigIndicator[i]);
                if (medelyWorkIdArr != null && medelyWorkIdArr.length > 0 && "Y".equals(medelyWorkOrigIndicator[i])) {
                        medleyOriginalWorks.add(medelyWorkIdArr[i]);
                }
            }
        }
        ApmPerformanceBulkRequestList apmFileRequestList = new ApmPerformanceBulkRequestList();
        String requestTypeCode = "DELETE".equals(apmFileList.getOperationType())
            ? UsageConstants.BULK_REQUEST_TYPE_DELETE : UsageConstants.BULK_REQUEST_TYPE_UPDATE;
        if ("UNMATCH".equals(apmFileList.getOperationType())) {
            requestTypeCode = UsageConstants.BULK_REQUEST_TYPE_UNMATCH;
        }
        try {
            
            BeanUtils.copyProperties(apmFileRequestList, apmFileList);
            if(apmFileRequestList.getMedleyWorkIds()==null) {
                apmFileRequestList.setMedleyWorkIds(new String[0]);
            }
            apmFileRequestList.setRequestTypeCode(requestTypeCode);
            List<Object> outValueObjects = null;
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(apmFileRequestList);
            PREPContext outputContext = null;

            outputContext = usageHandler.cloneCatalogPerformances(inputContext);
            apmFileRequestList = null;
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmFileRequestList = (ApmPerformanceBulkRequestList) outValueObjects.iterator().next();

                if (apmFileRequestList.getInvalidGroups() != null && !apmFileRequestList.getInvalidGroups().isEmpty()) {
                    String opType = apmFileList.getOperationType();
                    apmFileList = populateForm(apmFileList, apmFileList.getInvalidGroups(), request);
                    if ("MEDLEY_ADD".equals(opType)) {
                        apmFileList.setOperationType("MEDLEY_RETRIEVE");
                    }
                    apmFileList.setMedleyOriginalWorkList(medleyOriginalWorks);
                    view.getModel().put("apmPerformanceBulkRequestListForm", apmFileList);
                    Iterator<Object> itr = apmFileRequestList.getInvalidGroups().iterator();
                    ApmPerformanceBulkRequest errApmPerformanceBulkRequest = null;
                    while (itr.hasNext()) {
                        errApmPerformanceBulkRequest = (ApmPerformanceBulkRequest) itr.next();
                        List<Object> colInner = errApmPerformanceBulkRequest.getWorkIdErrors();
                        if (colInner != null && !colInner.isEmpty()) {
                            Iterator<Object> ir = colInner.iterator();
                            String errString = "";
                            while (ir.hasNext()) {
                                errString = (String) ir.next();
                                log.debug(errString);
                                view.getModel().put(SYSTEMERROR, messageSource.getMessage("field.errors.placeholder",
                                    new Object[] {errString}, Locale.getDefault()));
                            }
                        }

                    }
                    forwardKey = MEDLEY;
                    return forwardKey;

                } else {
                    view.getModel().put(SYSTEMMESSAGE,
                        messageSource.getMessage(UPDATESUCCESS, new Object[] {"Medley success"}, Locale.getDefault()));
                    apmFileList.setOperationType("SEARCH");
                    apmFileList.setMedelyView("N");
                    return getBulkRequestData(apmFileList, request, view);
                }
            } else {
                apmFileList.setOperationType("SEARCH");
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmFileList.setIndex((Integer.parseInt(request.getParameter("tempCurPageNr")) - 1)
                    * (userPreferences == null ? 100 : userPreferences.getNofSrchRsltsPerPage()));
                if (!binding.hasErrors()) {
                    if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                        view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(DELETESUCCESS,
                            new Object[] {"Medley success"}, Locale.getDefault()));
                    } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                        view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(UNMATCHSUCCESS,
                            new Object[] {"Medley success"}, Locale.getDefault()));
                    } else {
                        view.getModel().put(SYSTEMMESSAGE, messageSource.getMessage(UPDATESUCCESS,
                            new Object[] {"Medley success"}, Locale.getDefault()));
                    }
                }

                if (true) {
                    return getBulkRequestData(apmFileList, request, view);
                }
            }
           
        } catch (PrepSystemException pse) {
            if (log.isDebugEnabled()) {
                log.debug("Error Caught in CatalogManualMatchMultiController :" + pse);
                log.error(pse.getMessage());
            }
            log.error("Error Caught in CatalogManualMatchMultiController  :", pse);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getMessage(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        } catch (PrepFunctionalException pfex) {
            if (log.isDebugEnabled()) {
                log.debug("Error Caught in CatalogManualMatchMultiController  :" + pfex);
                log.error(pfex.getMessage());
            }
            log.warn("Functional Exception in CatalogManualMatchMultiController  :" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getMessage(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        } catch (Exception ex) {
            if (log.isDebugEnabled()) {
                log.debug("Error Caught in CatalogManualMatchMultiController  :" + ex);
                log.error(ex.getMessage());
            }
            log.error("Exception caught in CatalogManualMatchMultiController  :medelyBulkRequest() method", ex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(ex.getMessage(), null, Locale.getDefault()));
            forwardKey = CATALOGMANUALMATCH;
        }

        if (!binding.hasErrors()) {
            if (UsageConstants.BULK_REQUEST_TYPE_DELETE.equals(requestTypeCode)) {
                view.getModel().put(SYSTEMMESSAGE,
                    messageSource.getMessage(DELETESUCCESS, new Object[] {"Medley delete"}, Locale.getDefault()));
            } else if (UsageConstants.BULK_REQUEST_TYPE_UNMATCH.equals(requestTypeCode)) {
                view.getModel().put(SYSTEMMESSAGE,
                    messageSource.getMessage(UNMATCHSUCCESS, new Object[] {"Medley unmatch"}, Locale.getDefault()));
            } else {
                view.getModel().put(SYSTEMMESSAGE,
                    messageSource.getMessage(UPDATESUCCESS, new Object[] {"Medley success"}, Locale.getDefault()));
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("forwardKey is " + forwardKey);
            log.debug("Exiting medelyBulkRequest of catalogManualMatchMultiController");
        }
        return forwardKey;

    }
}
