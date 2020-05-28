package com.ascap.apm.controller.usage;

import java.util.ArrayList;
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
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.ApmChannel;
import com.ascap.apm.vob.usage.ApmChannelList;

@Controller
public class ApmChannelMultiController extends BaseUsageController {

    private static final String APM_CHANNEL_LIST = "usage/apmChannelList";

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/channelList", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView apmChannelMultiOperation(@ModelAttribute("apmChannelList") ApmChannelList apmChannelList,
        HttpServletRequest request, HttpServletResponse response, ModelAndView view, BindingResult bindingResult)
        throws Exception {
        String viewName = "";
        log.debug("Entering apmChannelMultiAction method in ApmChannelMultiController");
        if (apmChannelList.getOperationType() != null) {
            if ("LIST".equals(apmChannelList.getOperationType())) {
                viewName = getChannelList(apmChannelList, request, view);
                view.setViewName(viewName);
                return view;
            }
            if ("UPDATE".equals(apmChannelList.getOperationType())
                || "ASSIGN".equals(apmChannelList.getOperationType())) {
                viewName = updateChannelList(apmChannelList, request, response, bindingResult, view);
                view.setViewName(viewName);
                return view;
            }
        } else {
            viewName = getChannelList(apmChannelList, request, view);
            view.setViewName(viewName);
            return view;
        }
        log.debug("Exiting apmChannelMultiAction method in ApmChannelMultiController");
        return null;
    }

    private String getChannelList(ApmChannelList apmChannelList, HttpServletRequest request, ModelAndView view)
        throws Exception {

        log.debug("Entering getChannelList method in ApmChannelMultiController");
        ApmChannelList outputApmChannelList = new ApmChannelList();
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;

        String forwardKey = APM_CHANNEL_LIST;
        inputContext.addInputValueObject(apmChannelList);

        try {
            outputContext = usageHandler.getChannelList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();
            if (outValueObjects != null && outValueObjects.size() > 0) {
                outputApmChannelList = (ApmChannelList) outValueObjects.iterator().next();
                BeanUtils.copyProperties(apmChannelList, outputApmChannelList);
                view.getModel().put("apmChannelList", apmChannelList);
            } else {
                return forwardKey;
            }
        } catch (PrepFunctionalException pe) {
            log.error("PrepFunctionalException Caught in ApmChannelMultiController  :", pe);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pe.getErrorKey(), null, Locale.getDefault()));
            forwardKey = APM_CHANNEL_LIST;
        } catch (PrepSystemException pe) {
            log.error("PrepSystemException Caught in ApmChannelMultiController  :", pe);
            view.getModel().put(SYSTEMERROR, messageSource.getMessage(pe.getErrorKey(), null, Locale.getDefault()));
            forwardKey = APM_CHANNEL_LIST;
        } catch (Exception pe) {
            log.error("Exception Caught in ApmChannelMultiController  :", pe);
            view.getModel().put(SYSTEMERROR, SYSTEMERROR);
            forwardKey = APM_CHANNEL_LIST;
        }
        log.debug("Exiting getChannelList method in ApmChannelMultiController");
        return forwardKey;
    }

    private String updateChannelList(ApmChannelList apmChannelList, HttpServletRequest request,
        HttpServletResponse response, BindingResult bindingResult, ModelAndView view) throws Exception {

        log.debug("Entering updateChannelList method in ApmChannelMultiController");
        ApmChannelList newApmChannelList = new ApmChannelList();
        PREPContext inputContext = getPREPContext(request);
        ApmChannel apmChannel = null;
        List<Object> channelCol = new ArrayList<>();
        String[] selectedIndexes = apmChannelList.getSelectedIndex();
        String[] selectedIds = apmChannelList.getSelectedIds();
        String[] newChannelTypes = apmChannelList.getNewChannelType();

        if (selectedIndexes != null && selectedIndexes.length > 0) {
            for (int i = 0; i < selectedIndexes.length; i++) {
                log.debug("Slected Index: " + selectedIndexes[i]);
                log.debug("Slected Id: " + selectedIds[Integer.parseInt(selectedIndexes[i])]);
                log.debug("Slected Type: " + newChannelTypes[Integer.parseInt(selectedIndexes[i])]);

                apmChannel = new ApmChannel();
                apmChannel.setChannelId(selectedIds[Integer.parseInt(selectedIndexes[i])]);
                apmChannel.setChannelType(newChannelTypes[Integer.parseInt(selectedIndexes[i])]);
                apmChannel.setUserId(apmChannelList.getUserId());
                channelCol.add(apmChannel);
            }
        } else {
            log.debug("Slected Index is null or empty: ");
        }
        newApmChannelList.setSearchResults(channelCol);
        newApmChannelList.setOperationType(apmChannelList.getOperationType());
        newApmChannelList.setFilterSupplierCode(apmChannelList.getFilterSupplierCode());
        newApmChannelList.setUserId(apmChannelList.getUserId());
        inputContext.addInputValueObject(newApmChannelList);
        ApmChannelList outputApmChannelList = new ApmChannelList();
        try {
            usageHandler.updateChannelList(inputContext);
            outputApmChannelList.setFilterSupplierCode(apmChannelList.getFilterSupplierCode());
            outputApmChannelList.setFilterChannelType(apmChannelList.getFilterChannelType());
        } catch (PrepFunctionalException pe) {
            log.error("ERROR:: PrepFunctionalException: " + pe);
            view.getModel().put(SYSTEMERROR,
                messageSource.getMessage("error.usage.common.exception", null, Locale.getDefault()));
        } catch (PrepSystemException pe) {
            log.error("ERROR:: PrepSystemException: " + pe);
            view.getModel().put(SYSTEMERROR,
                messageSource.getMessage("error.usage.common.exception", null, Locale.getDefault()));
        }
        if (!bindingResult.hasErrors()) {
            view.getModel().put("systemmessage",
                messageSource.getMessage("message.apm.channels.update.success", null, Locale.getDefault()));
        }
        log.debug("Exiting updateChannelList method in ApmChannelMultiController");
        return getChannelList(outputApmChannelList, request, view);
    }

}
