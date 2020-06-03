package com.ascap.apm.controller.logon;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.context.UserSessionState;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.constants.Constants;
import com.ascap.apm.controller.BaseController;
import com.ascap.apm.controller.utils.ApplicationAuthenticator;
import com.ascap.apm.handler.logon.LogonHandler;
import com.ascap.apm.vob.admin.UserLoginHistory;

/**
 * @author Pratap Sanikommu
 * @version $Revision: 1.33 $ $Date: Oct 27 2010 16:37:30 $ DisRunAddAction - Action
 */
@Controller
public class LogonController extends BaseController {

    private static final String ERRORS = "Errors";

    public LogonController() {
        super();
    }

    @Autowired
    protected LogonHandler logonHandler;

    @GetMapping("/logon")
    public ModelAndView getLogon(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        view.setViewName("login");
        return view;
    }

    @GetMapping("/authenticateLogon")
    public ModelAndView authenticateLogon(HttpServletRequest request, HttpServletResponse response, ModelAndView view,
        BindingResult bindingResult) {
        log.debug("Entering logon method in LogonController");
        String userId = null;
        HttpSession session = request.getSession();
        if (request.getParameter("code") != null && request.getParameter("state") != null
            && request.getParameter("state").equals(session.getAttribute("azstate"))) {
            ApplicationAuthenticator helper = new ApplicationAuthenticator();
            try {
                String tokenJson = helper.getAccessToken(request.getParameter("code"));
                String accessToken = null;
                JSONObject jsObj = new JSONObject(tokenJson);
                accessToken = (String) jsObj.get("access_token");
                if (accessToken != null) {
                    String userInfo = helper.getUserInfo(accessToken);
                    JSONObject userObj = new JSONObject(userInfo);
                    userId = (String) userObj.get("preferred_username");
                    log.debug("Cognito User ---> " + userId);
                }
            } catch (JSONException | IOException exe) {
                log.debug(exe);
            }
        }

        boolean isSecurityEnabled = true;
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        if (userId == null) {
            log.debug("*********************** Inside PREPWEB LogonController.logon() userId is Determined NULL");
            userId = "dev_azam_shaik";
        }
        log.debug("**** UserId : " + userId);
        UserProfile profile = new UserProfile();
        profile.setUserId(userId);
        profile.setIsSecurityEnabled(isSecurityEnabled);
        log.debug("**************************************************************** User-Agent :"
            + request.getHeader("User-Agent"));
        if (request.getSession(false) != null) {
            log.debug("**************************************************************** Session ID"
                + request.getSession(false).getId());
        }
        UserLoginHistory userloginHistory = null;
        userloginHistory = new UserLoginHistory();
        if (request.getSession(false) != null) {
            userloginHistory.setSessionKey(request.getSession(false).getId());
        }
        userloginHistory.setUserAgent(request.getHeader("User-Agent"));
        userloginHistory.setUserName(userId);
        profile.setLoginSessionInfo(userloginHistory);
        // Set Input VOBS to inputContext
        inputContext.addInputValueObject(profile);
        try {
            outputContext = logonHandler.authenticateLogon(inputContext);
            if (!(bindingResult.hasErrors())) {
                List<Object> outValueObjects = outputContext.getOutputValueObjects();
                if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                    UserProfile userProfile = (UserProfile) outValueObjects.iterator().next();
                    if (userProfile == null) {
                        log.debug("Profile is null");
                        view.setViewName(ERRORS);
                    }
                    log.debug("*** userProfile.getGroups() *** " + userProfile.getGroups());
                    inputContext.setUserProfile(userProfile);
                    UserSessionState userSessionState = new UserSessionState();
                    userSessionState.setApplicationLogged(Constants.APPLICATION_LOGGED_PREP);
                    userSessionState.setUserId(userProfile.getUserId());
                    userSessionState.setUserPreference(userProfile.getUserPreference());
                    inputContext.setUserSessionState(userSessionState);
                    setUserSessionState(request, inputContext.getUserSessionState());
                    setUserProfile(request, inputContext.getUserProfile());
                    view.setViewName("redirect:/usage/usageHomeSearch?performanceSearchType=CRITERIA");
                }
            }
        } catch (PrepSystemException pse) {
            log.error(getMessage(pse.getErrorKey()));
            view.getModel().put("systemerror", getMessage(pse.getErrorKey()));
            view.setViewName(ERRORS);
        } catch (Exception e) {
            log.error(e.getMessage());
            view.getModel().put("systemerror", e.getMessage());
            view.setViewName(ERRORS);
        }

        log.debug("Exiting logon method in LogonController");
        log.debug("***********View Name>> " + view.getViewName());
        return view;
    }
}
