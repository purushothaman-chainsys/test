package com.ascap.apm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Raju_Ayanampudi To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
@Controller
public class ForceAuditController extends BaseController {

    /**
     * Constructor for ForceAuditController.
     */
    public ForceAuditController() {
        super();
    }

    /**
     * @see com.ascap.apm.controller#logout(HttpServletRequest)
     */
    @RequestMapping(value = "/forceAudit")
    public String logout(@RequestParam String forceTargetLink,String junction, HttpServletRequest request) throws Exception {
        log.debug("Entering logout method in ForceAuditController");
        // Create input and output contexts
        String forwardKey = null;
        if (request.getParameter("forceTargetLink") != null
            && request.getParameter("forceTargetLink").equalsIgnoreCase("logout")) {
            request.getSession().setAttribute("junction", junction);
            log.debug("**** junction: " + junction);
        }
        forwardKey = "logoutTemp";
        log.debug("Exiting logout method in ForceAuditController");
        log.debug("**** forwardKey: " + forwardKey);
        return forwardKey;
    }
}
