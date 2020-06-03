package com.ascap.apm.controller.usage;

import java.util.ArrayList;
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
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.SamplingRequest;
import com.ascap.apm.vob.usage.SamplingResult;
import com.ascap.apm.vob.usage.SamplingSummary;

@Controller
@RequestMapping("/usage")
public class SamplingDetailMultiController extends BaseUsageController {

    private static final String SAMPLING_SUMMARY = "usage/samplingSummary";

    private static final String SAMPLING_DETAILS = "usage/samplingDetails";

    private static final String OPERATION_TYPE_SUMMARY = "redirect:/samplingSummary?operationType=SUMMARY";

    private static final String SYSTEM_ERROR = "system.error";

    private static final String ERROR_CAUGHT_SAMPLINGDETAILMULTICONTROLLER =
        "Error Caught in SamplingDetailMultiController  :";

    private static final String FUNCTIONALEXCEPTION_SAMPLINGDETAILMULTICONTROLLER =
        "Functional Exception in SamplingDetailMultiController  :";

    private static final String EXCEPTION_CAUGHT_SAMPLINGDETAILMULTICONTROLLER =
        "Exception caught in SamplingDetailMultiController ";

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/samplingSummary", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView samplingDetailMultiOperation(@ModelAttribute("samplingSummary") SamplingSummary samplingSummary,
        HttpServletRequest request, HttpServletResponse response, ModelAndView view)
        throws Exception {
        String viewName = "";
        log.debug("Entering samplingDetailMultiOperation method in SamplingDetailMultiController");
        if ("RETRIEVE".equals(samplingSummary.getOperationType())) {
        } else if ("DETAILS".equals(samplingSummary.getOperationType()) || "DETAILS2".equals(samplingSummary.getOperationType())) {
            viewName = getSamplingDetails(samplingSummary, request, view);
            view.setViewName(viewName);
            return view;
        } else if ("ADD".equals(samplingSummary.getOperationType())) {
            viewName = updateSamplingDetails(samplingSummary, request, view);
            view.setViewName(viewName);
            return view;
        } else if ("ADD2".equals(samplingSummary.getOperationType())) {
            viewName = updateSamplingDetailsL2(samplingSummary, request, view);
            view.setViewName(viewName);
            return view;
        } else if ("ADD3".equals(samplingSummary.getOperationType())) {
            viewName = updateSamplingDetailsL3(samplingSummary, request, view);
            view.setViewName(viewName);
            return view;
        } else if ("BYPASSSAMP".equals(samplingSummary.getOperationType())) {
            viewName = updateSamplingDetailsL4(samplingSummary, request, view);
            view.setViewName(viewName);
            return view;
        } else if ("SUMMARY".equals(samplingSummary.getOperationType())) {
            viewName = getSamplingSummary(samplingSummary, request, view);
            view.setViewName(viewName);
            return view;
        } else if ("CANCEL".equals(samplingSummary.getOperationType())) {
            viewName = cancelSampling(samplingSummary, request, view);
            view.setViewName(viewName);
            return view;
        } else {
            log.debug("Exiting samplingDetailMultiOperation method in SamplingDetailMultiController");
            viewName = getSamplingSummary(samplingSummary, request, view);
            view.setViewName(viewName);
            return view;
        }
        viewName = SAMPLING_SUMMARY;
        view.setViewName(viewName);
        return view;
    }

    private String getSamplingSummary(SamplingSummary samplingSummary, HttpServletRequest request, ModelAndView view) {

        log.debug("Entering getSamplingSummary method in SamplingDetailMultiController");
        String forwardKey = null;

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        SamplingSummary outSamplingDetails = null;
        List<Object> outValueObjects = null;
        inputContext.addInputValueObject(samplingSummary);
        try {
            outputContext = usageHandler.getSamplingSummary(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outSamplingDetails = (SamplingSummary) outValueObjects.iterator().next();
                view.getModel().put("samplingSummary", outSamplingDetails);
            }
            forwardKey = SAMPLING_SUMMARY;
        } catch (PrepSystemException pse) {
            log.error(ERROR_CAUGHT_SAMPLINGDETAILMULTICONTROLLER, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (PrepFunctionalException pfex) {
            log.error(FUNCTIONALEXCEPTION_SAMPLINGDETAILMULTICONTROLLER + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (Exception ex) {
            log.error("Exception caught in SamplingDetailMultiController  :samplingDetailMultiOperation() method", ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = SAMPLING_SUMMARY;
        }
        log.debug("Exiting getSamplingSummary method in SamplingDetailMultiController");
        return forwardKey;
    }

    private String cancelSampling(SamplingSummary samplingSummary, HttpServletRequest request, ModelAndView view) {

        log.debug("Entering cancelSampling method in SamplingDetailMultiController");
        String forwardKey = null;
        SamplingSummary newSamplingSummary = new SamplingSummary();
        newSamplingSummary.setCallLetter(samplingSummary.getCallLetter());
        newSamplingSummary.setTargetSurveyYearQuarter(samplingSummary.getTargetSurveyYearQuarter());
        newSamplingSummary.setUserId(samplingSummary.getUserId());
        try {
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(newSamplingSummary);
            usageHandler.cancelSampling(inputContext);
            forwardKey = OPERATION_TYPE_SUMMARY;
        } catch (PrepSystemException pse) {
            log.error(ERROR_CAUGHT_SAMPLINGDETAILMULTICONTROLLER, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (PrepFunctionalException pfex) {
            log.error(FUNCTIONALEXCEPTION_SAMPLINGDETAILMULTICONTROLLER + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (Exception ex) {
            log.error("Exception caught in SamplingDetailMultiController :", ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = SAMPLING_SUMMARY;
        }
        log.debug("Exiting cancelSampling method in SamplingDetailMultiController");
        return forwardKey;
    }

    private String getSamplingDetails(SamplingSummary samplingSummary, HttpServletRequest request, ModelAndView view) {

        log.debug("Entering getSamplingDetails method in SamplingDetailMultiController ");
        String forwardKey = null;
        String operationType = samplingSummary.getOperationType();
        log.debug("Operation Type " + operationType);

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        SamplingSummary newSamplingSummary = new SamplingSummary();
        SamplingSummary outSamplingDetails = new SamplingSummary();
        List<Object> outValueObjects = null;
        newSamplingSummary.setTargetSurveyYearQuarter(samplingSummary.getTargetSurveyYearQuarter());
        newSamplingSummary.setCallLetter(samplingSummary.getCallLetter());
        inputContext.addInputValueObject(newSamplingSummary);
        try {
            outputContext = usageHandler.getSamplingDetails(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                outSamplingDetails = (SamplingSummary) outValueObjects.iterator().next();
                BeanUtils.copyProperties(samplingSummary, outSamplingDetails);
                if ("DETAILS2".equals(operationType)) {
                    samplingSummary.setStepNumber("L2");
                } else {
                    samplingSummary.setStepNumber("L1");
                }
                view.getModel().put("samplingSummary", samplingSummary);
            }
            forwardKey = SAMPLING_DETAILS;
        } catch (PrepSystemException pse) {
            log.error(ERROR_CAUGHT_SAMPLINGDETAILMULTICONTROLLER, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = SAMPLING_DETAILS;
        } catch (PrepFunctionalException pfex) {
            log.error(FUNCTIONALEXCEPTION_SAMPLINGDETAILMULTICONTROLLER + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = SAMPLING_DETAILS;
        } catch (Exception ex) {
            log.error(EXCEPTION_CAUGHT_SAMPLINGDETAILMULTICONTROLLER, ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = SAMPLING_DETAILS;
        }
        log.debug("Exiting getSamplingDetails method in SamplingDetailMultiController");
        return forwardKey;
    }

    private String updateSamplingDetails(SamplingSummary samplingSummary, HttpServletRequest request, ModelAndView view) {

        log.debug("Entering updateSamplingDetails method in SamplingDetailMultiController");
        String forwardKey = null;
        SamplingRequest samplingRequest = new SamplingRequest();
        samplingRequest.setUserId(samplingSummary.getUserId());
        samplingRequest.setCallLetter(samplingSummary.getCallLetter());
        samplingRequest.setPlayCountRanges(samplingSummary.getPlayCountRange());
        samplingRequest.setSamplingRequestId(samplingSummary.getSamplingRequestId());
        samplingRequest.setTargetSurveyYearQuarter(samplingSummary.getTargetSurveyYearQuarter());
        samplingRequest.setStepCode("L1");

        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);

        inputContext.addInputValueObject(samplingRequest);
        try {
            usageHandler.updateSamplingSummary(inputContext);
            forwardKey = OPERATION_TYPE_SUMMARY;
        } catch (PrepSystemException pse) {
            log.error(ERROR_CAUGHT_SAMPLINGDETAILMULTICONTROLLER, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (PrepFunctionalException pfex) {
            log.error(FUNCTIONALEXCEPTION_SAMPLINGDETAILMULTICONTROLLER + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (Exception ex) {
            log.error("Exception caught in SamplingDetailMultiController", ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = SAMPLING_SUMMARY;
        }
        log.debug("Exiting updateSamplingDetails method in SamplingDetailMultiController");
        return forwardKey;
    }

    private String updateSamplingDetailsL2(SamplingSummary samplingSummary, HttpServletRequest request,
        ModelAndView view) {

        log.debug("Entering updateSamplingDetailsL2 method in SamplingDetailMultiController");
        String forwardKey = null;
        try {
            String[] playCntRangeIds = samplingSummary.getPlayCountRangeId();
            String[] mergeIndicator = samplingSummary.getMergeIndicator();
            String[] censusSampleIndicator = samplingSummary.getCensusSampleIndicator();
            String[] includeForeignAffFlag = samplingSummary.getIncludeForeignAffFlag();
            SamplingResult sr = null;
            List<Object> col = new ArrayList<>();
            if (playCntRangeIds != null && playCntRangeIds.length > 0) {
                for (int i = 0; i < playCntRangeIds.length; i++) {
                    sr = new SamplingResult();
                    sr.setPlayCountRangeId(playCntRangeIds[i]);
                    sr.setMergeIndicator(mergeIndicator[i]);
                    sr.setCensusSampleIndicator(censusSampleIndicator[i]);
                    sr.setIncludeForeignAffFlag(includeForeignAffFlag[i]);
                    col.add(sr);
                }
            }
            samplingSummary.setSearchResults(col);
            samplingSummary.setStepNumber("L2");

            // Create input and output contexts
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(samplingSummary);
            usageHandler.addSamplingRequest(inputContext);
            forwardKey = OPERATION_TYPE_SUMMARY;
        } catch (PrepSystemException pse) {
            log.error(ERROR_CAUGHT_SAMPLINGDETAILMULTICONTROLLER, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (PrepFunctionalException pfex) {
            log.error(FUNCTIONALEXCEPTION_SAMPLINGDETAILMULTICONTROLLER + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (Exception ex) {
            log.error(EXCEPTION_CAUGHT_SAMPLINGDETAILMULTICONTROLLER, ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = SAMPLING_SUMMARY;
        }
        log.debug("Exiting updateSamplingDetailsL2 method in SamplingDetailMultiController");
        return forwardKey;
    }

    private String updateSamplingDetailsL3(SamplingSummary samplingSummary, HttpServletRequest request,
        ModelAndView view) {

        log.debug("Entering updateSamplingDetailsL3 method in SamplingDetailMultiController");
        String forwardKey = null;
        try {
            String[] playCntRangeIds = samplingSummary.getPlayCountRangeId();
            String[] selectPercent = samplingSummary.getSelectPercent();
            String[] selectCount = samplingSummary.getSelectCount();
            String[] selectStart = samplingSummary.getSelectStart();
            String[] selectSkip = samplingSummary.getSelectSkip();
            String[] censusSampleIndicator = samplingSummary.getCensusSampleIndicator();
            SamplingResult sr = null;
            List<Object> col = new ArrayList<>();
            if (playCntRangeIds != null && playCntRangeIds.length > 0) {
                for (int i = 0; i < playCntRangeIds.length; i++) {
                    if ("C".equals(censusSampleIndicator[i])) {
                        continue;
                    }
                    sr = new SamplingResult();
                    sr.setPlayCountRangeId(playCntRangeIds[i]);
                    sr.setSelectPercent(selectPercent[i]);
                    sr.setSelectCount(selectCount[i]);
                    sr.setSelectStart(selectStart[i]);
                    sr.setSelectSkip(selectSkip[i]);
                    col.add(sr);
                }
            }
            samplingSummary.setSearchResults(col);
            samplingSummary.setStepNumber("L3");

            // Create input and output contexts
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(samplingSummary);
            usageHandler.addSamplingRequest(inputContext);
            forwardKey = OPERATION_TYPE_SUMMARY;
        } catch (PrepSystemException pse) {
            log.error(ERROR_CAUGHT_SAMPLINGDETAILMULTICONTROLLER, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (PrepFunctionalException pfex) {
            log.error(FUNCTIONALEXCEPTION_SAMPLINGDETAILMULTICONTROLLER + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (Exception ex) {
            log.error(EXCEPTION_CAUGHT_SAMPLINGDETAILMULTICONTROLLER, ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = SAMPLING_SUMMARY;
        }
        log.debug("Exiting updateSamplingDetailsL3 method in SamplingDetailMultiController");
        return forwardKey;
    }

    private String updateSamplingDetailsL4(SamplingSummary samplingSummary, HttpServletRequest request,
        ModelAndView view) {

        log.debug("Entering updateSamplingDetailsL4 method in SamplingDetailMultiController");
        String forwardKey = null;
        try {
            String[] playCntRangeIds = samplingSummary.getPlayCountRangeId();
            String[] mergeIndicator = samplingSummary.getMergeIndicator();
            String[] censusSampleIndicator = samplingSummary.getCensusSampleIndicator();
            String[] includeForeignAffFlag = samplingSummary.getIncludeForeignAffFlag();
            SamplingResult sr = null;
            List<Object> col = new ArrayList<>();
            if (playCntRangeIds != null && playCntRangeIds.length > 0) {
                for (int i = 0; i < playCntRangeIds.length; i++) {
                    sr = new SamplingResult();
                    sr.setPlayCountRangeId(playCntRangeIds[i]);
                    sr.setMergeIndicator(mergeIndicator[i]);
                    sr.setCensusSampleIndicator(censusSampleIndicator[i]);
                    sr.setIncludeForeignAffFlag(includeForeignAffFlag[i]);
                    col.add(sr);
                }
            }
            samplingSummary.setSearchResults(col);
            samplingSummary.setStepNumber("L4");

            // Create input and output contexts
            PREPContext inputContext = getPREPContext(request);
            inputContext.addInputValueObject(samplingSummary);
            usageHandler.bypassSampling(inputContext);
            forwardKey = OPERATION_TYPE_SUMMARY;
        } catch (PrepSystemException pse) {
            log.error(ERROR_CAUGHT_SAMPLINGDETAILMULTICONTROLLER, pse);
            view.getModel().put(SYSTEMERROR, getMessage(pse.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (PrepFunctionalException pfex) {
            log.error(FUNCTIONALEXCEPTION_SAMPLINGDETAILMULTICONTROLLER + pfex);
            view.getModel().put(SYSTEMERROR, getMessage(pfex.getErrorKey()));
            forwardKey = SAMPLING_SUMMARY;
        } catch (Exception ex) {
            log.error("Exception caught in SamplingDetailMultiController :", ex);
            view.getModel().put(SYSTEMERROR, getMessage(SYSTEM_ERROR));
            forwardKey = SAMPLING_SUMMARY;
        }
        log.debug("Exiting updateSamplingDetailsL4 method in SamplingDetailMultiController");
        return forwardKey;
    }

}
