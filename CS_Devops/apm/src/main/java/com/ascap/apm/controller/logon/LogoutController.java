package com.ascap.apm.controller.logon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.exception.logon.LogonException;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.constants.AdminConstants;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
import com.ascap.apm.controller.BaseController;
import com.ascap.apm.handler.logon.LogonHandler;

/**
 * @author Pratap Sanikommu
 * @version $Revision: 1.6 $ $Date: Oct 27 2010 14:38:10
 */
@Controller
public class LogoutController extends BaseController {

    @Autowired
    private LogonHandler logonHandler;

    public LogoutController() {
        super();
    }
    @RequestMapping(value = "/logout")
    public String logout(@RequestParam  String junction,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        log.debug("Entering logout method in LogoutController");
        PREPContext inputContext = getPREPContext(request);
        UserProfile userProfile = null;
        HttpSession session = request.getSession();
        try{
            if (session != null) {
                userProfile = (UserProfile) session.getAttribute(UserProfile.HTTP_SESSION_KEY);
                if (userProfile != null && userProfile.getLoginSessionInfo() != null) {
                    userProfile.getLoginSessionInfo()
                        .setSessionStatusCode(AdminConstants.LOGIN_HST_SESSION_STATUS_LOGGEDOUT);
                    try {
                        inputContext.addInputValueObject(userProfile);
                        logonHandler.logout(inputContext);
                    } catch (LogonException dae) {
                        log.warn(dae.getMessage());
                    } catch (PrepFunctionalException dae) {
                        log.warn(dae.getMessage());
                        model.addAttribute("systemmessage", dae.getErrorKey());
                    } catch (PrepSystemException pse) {
                        log.error(pse.getMessage());
                        model.addAttribute("systemerror", pse.getErrorKey());
                    }
                } // login session info not null
                // Invalidate the Session
                session.invalidate();
            }
            log.debug("Loged Out");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // logoutWebSeal();
        String LOGOUT_PATH = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.PREP_URL);
        log.debug("Exiting logout method in LogoutController");
        response.sendRedirect(LOGOUT_PATH + "/pkmslogout?junction=" + junction);
        return null;
    }
}
