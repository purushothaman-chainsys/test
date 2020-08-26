package com.ascap.apm.controller.usage;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.exception.BaseException;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.CatalogSamplingSummary;

@Controller
public class CatalogSamplingMultiController extends BaseUsageController {

    private static final String SYSTEMERROR = "systemerror";

    private static final String SEARCHRESULTS = "usage/catalogSamplingSummary";

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/catalogSamplingSummary", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView returnPage(
        @ModelAttribute("catalogSamplingSummary") CatalogSamplingSummary catalogSamplingSummary, ModelAndView view,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = "";
        log.debug("Entering doWork of CatalogSamplingMultiAction");
        if (catalogSamplingSummary.getOperationType() == null)
            log.debug("::null");

        log.debug("Exiting doWork of CatalogSamplingMultiAction forward: CATALOGSUMMARY");
        viewName = getCatalogSamplingSummary(catalogSamplingSummary, request, view);
        view.setViewName(viewName);

        return view;

    }

    private String getCatalogSamplingSummary(CatalogSamplingSummary catalogSamplingSummary, HttpServletRequest request,
        ModelAndView view) {
        log.debug("Entering getCatalogSamplingSummary in SamplingDetailMultiAction");
        String forwardKey = "";
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;
        inputContext.addInputValueObject(catalogSamplingSummary);
        try {
            outputContext = usageHandler.getCatalogSamplingSummary(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && !outValueObjects.isEmpty()) {
                catalogSamplingSummary = (CatalogSamplingSummary) outValueObjects.iterator().next();
                view.getModel().put("catalogSamplingSummary", catalogSamplingSummary);
            }
            view.getModel().put("catalogSamplingSummary", catalogSamplingSummary);
            forwardKey = SEARCHRESULTS;
        } catch (PrepSystemException pse) {
            log.error("Error Caught in CatalogSamplingDetailMultiAction  :", pse);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
            forwardKey = SEARCHRESULTS;
        } catch (PrepFunctionalException pfex) {
            log.error("Functional Exception in CatalogSamplingDetailMultiAction  :" + pfex);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pfex.getErrorKey(), null, Locale.getDefault()));
            forwardKey = SEARCHRESULTS;
        } catch (Exception ex) {
            log.error("Exception caught in CatalogSamplingDetailMultiAction  :doWork() method", ex);
            view.getModel().put(SYSTEMERROR,
                messageSource.getMessage(((BaseException) ex).getErrorKey(), null, Locale.getDefault()));
            forwardKey = SEARCHRESULTS;
        }
        log.debug("Exiting getCatalogSamplingSummary in CatalogSamplingDetailMultiAction");
        return forwardKey;
    }
}
