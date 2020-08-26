package com.ascap.apm.controller.preferences;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.controller.BaseController;

/**
 * @version 1.0
 * @author
 */
@Controller
@RequestMapping("/preferences")
public class PreferencesGetController extends BaseController {

    private static final String GETPREFERENCES = "preferences/dsGetPreference";

    /**
     * Constructor
     */
    public PreferencesGetController() {

        super();
    }

    /**
     * @see com.ascap.apm.controller.getPreferences()#(@ModelAttribute, BindingResult, Model, HttpServletRequest)
     */
    @RequestMapping(value = "/getPreferences")
    public String getPreferences(@ModelAttribute("userPreferences") UserPreference userPreferences, Model m,
        HttpServletRequest request) throws Exception {
        log.debug("Entering getPreferences in PreferencesGetController");
        // Create input and output contexts
        try {
        PREPContext inputContext = getPREPContext(request);
        userPreferences = inputContext.getUserSessionState().getUserPreference();
        m.addAttribute("userPreferences", userPreferences);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return ERRORPAGE;
        }
        log.debug("Exiting getPreferences in PreferencesGetController");
        return GETPREFERENCES;
    }
}
