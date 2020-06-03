package com.ascap.apm.controller.preferences;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.controller.BaseController;
import com.ascap.apm.handler.preferences.PreferencesHandler;

/**
 * @version 1.0
 * @author
 */
@RestController
@RequestMapping("/preferences")
public class PreferencesSubmitController extends BaseController {

    @Autowired
    private PreferencesHandler preferenceHandler;

    private static final String GETPREFERENCES = "/preferences/dsGetPreference";

    /**
     * Constructor
     */
    public PreferencesSubmitController() {
        super();
    }

    /**
     * @see com.ascap.apm.controller.preferenceSubmit()#(@ModelAttribute, BindingResult, Model, HttpServletRequest)
     */
    @PostMapping(value = "/submitPreferences")
    public ModelAndView preferenceSubmit(@ModelAttribute("userPreferences") UserPreference userPreferences,
        BindingResult results, ModelMap m, HttpServletRequest request) throws Exception {

        log.debug("Entering preferenceSubmit in PreferencesSubmitController");
        // Create input and output contexts

        if (userPreferences.validate(request, messageSource)) {
            m.addAttribute("systemerrorlist", request.getAttribute("systemerrorlist"));
            return new ModelAndView(GETPREFERENCES);
        }
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = new PREPContext();
        String forwardKey = "";

        UserPreference preferences = inputContext.getUserSessionState().getUserPreference();
        BeanUtils.copyProperties(preferences, userPreferences);

        // Set Input VOBS to inputContext
        inputContext.addInputValueObject(preferences);

        try {
            outputContext = preferenceHandler.submitPreferences(inputContext);
            List<Object> outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && !outValueObjects.isEmpty()) {
                preferences = (UserPreference) outValueObjects.iterator().next();
            }
            BeanUtils.copyProperties(userPreferences, preferences);
            m.addAttribute("userPreferences", userPreferences);
            forwardKey = GETPREFERENCES;
            inputContext.getUserSessionState().setUserPreference(preferences);

        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            m.addAttribute(SYSTEMERROR, pse.getErrorKey());
            forwardKey = ERRORPAGE;
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            m.addAttribute(SYSTEMERROR, pfe.getErrorKey());
            forwardKey = ERRORPAGE;
        } catch (Exception e) {
            log.error(e.getMessage());
            m.addAttribute(SYSTEMERROR, getMessage("ps.error.common.exception"));
            forwardKey = ERRORPAGE;
        }
        if (!m.containsAttribute(SYSTEMERROR)) {
            m.addAttribute(SYSTEMMESSAGE, getMessage("ps.preference.update.success"));
            forwardKey = GETPREFERENCES;
        }
        log.debug("Exiting preferenceSubmit in PreferencesSubmitController");
        return new ModelAndView(forwardKey);
    }

    
}
