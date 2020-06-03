package com.ascap.apm.controller.usage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.DebugHelperUtils;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.PerformanceSearch;
import com.ascap.apm.vob.usage.*;

@Controller
@RequestMapping("/usage")
public class ApmFileListMultiController extends BaseUsageController {

    private static final String APMFILELIST = "usage/apmFileList";

    private static final String SYSTEMERROR = "systemerror";

    private static final String APMFILEUPLOAD = "usage/apmFileUpload";

    private static final String APMFILELIST_MULTICONTROLLER = "Error Caught in ApmFileListMultiController  :";

    public ApmFileListMultiController() {
        super();

    }

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/apmFiles", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView fileList(@ModelAttribute("ApmFileList") ApmFileList apmFileList, HttpServletRequest request,
        HttpServletResponse response, ModelAndView view) throws Exception {
        String viewName = "";
        if ("UPLOAD_FILE".equals(apmFileList.getOperationType())) {
            view.getModel().put("apmFileUpload", new ApmFileUpload());
            viewName = APMFILEUPLOAD;
            view.setViewName(viewName);
            return view;
        }
        if ("SEARCH_FILES".equals(apmFileList.getOperationType())) {
            getFileList(apmFileList, request, view);
        }
        viewName = getFileList(apmFileList, request, view);
        view.setViewName(viewName);
        return view;
    }

    private String getFileList(ApmFileList apmFilesLists, HttpServletRequest request, ModelAndView view)
        throws Exception {
        String forwardKey = null;
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        ApmFileList apmFileList = null;
        if (apmFilesLists.getNavigationType() == null || "FIRST".equalsIgnoreCase(apmFilesLists.getNavigationType())
           || "".equalsIgnoreCase(apmFilesLists.getNavigationType().trim())) {
            apmFileList = new ApmFileList();
            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            apmFilesLists.setUserPreferences(userPreferences);
            BeanUtils.copyProperties(apmFileList, apmFilesLists);
        } else {
            if (inputContext.getUserSessionState().getSearch() != null) {
                apmFileList = (ApmFileList) inputContext.getUserSessionState().getSearch().getAlternateSearch();
                if (apmFileList == null) {
                    apmFileList = new ApmFileList();
                }
                apmFileList.setNavigationType(apmFilesLists.getNavigationType());
            } else {
                inputContext.getUserSessionState().setSearch(new PerformanceSearch());
                apmFileList = new ApmFileList();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                apmFilesLists.setUserPreferences(userPreferences);
                BeanUtils.copyProperties(apmFileList, apmFilesLists);
            }
        }
        PerformanceSearch performanceSearch = null;
        List<Object> outValueObjects = null;
        inputContext.addInputValueObject(apmFileList);
        try {
            if (log.isDebugEnabled()) {
                log.debug("calling  US_file list_LIST in ApmFileMultiController...");
            }
            outputContext = usageHandler.seacrhFiles(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                apmFileList = (ApmFileList) outValueObjects.iterator().next();
                
                apmFileList.setBaseFlag(true);
                BeanUtils.copyProperties(apmFilesLists, apmFileList);
                apmFileList.setBaseFlag(false);
                
                log.debug(DebugHelperUtils.debugCollections("surveyDatesList", apmFileList.getSearchResults()));
                view.getModel().put("apmFileList", apmFilesLists);
                apmFileList.setNavigationType(null);
                if (inputContext.getUserSessionState().getSearch() == null) {
                    performanceSearch = new PerformanceSearch();
                    performanceSearch.setAlternateSearch(apmFileList);
                } else {
                    inputContext.getUserSessionState().getSearch().setAlternateSearch(apmFileList);
                }
            } 
            // set the UserSessionState object to the session.
            setUserSessionState(request, inputContext.getUserSessionState());
            forwardKey = APMFILELIST;
        } catch (PrepSystemException pse) {
            log.debug("Error Caught in ApmFileListMultiController :" + pse);
            log.error(pse.getMessage());
            log.error(APMFILELIST_MULTICONTROLLER, pse);
            view.getModel().put(SYSTEMERROR, pse.getErrorKey());
            forwardKey = "error";
        } catch (PrepFunctionalException pfex) {
            log.debug(APMFILELIST_MULTICONTROLLER + pfex);
            log.error(pfex.getMessage());
            log.warn("Functional Exception in ApmFileListMultiController  :" + pfex);
            view.getModel().put(SYSTEMERROR, pfex.getErrorKey());
            forwardKey = APMFILELIST;
        } catch (Exception ex) {
            log.debug(APMFILELIST_MULTICONTROLLER + ex);
            log.error("Exception caught in ApmFileListMultiController  :fileList() method", ex);
            view.getModel().put(SYSTEMERROR, getMessage("system.error"));
            forwardKey = APMFILELIST;
        }
        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting getFileList of ApmFileListMultiController");
        return forwardKey;
    }

}
