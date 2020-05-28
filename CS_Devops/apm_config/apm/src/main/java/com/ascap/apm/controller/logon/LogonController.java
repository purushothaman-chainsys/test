package com.ascap.apm.controller.logon;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.context.UserSessionState;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.constants.Constants;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.common.utils.constants.UIWidgetConstants;
import com.ascap.apm.controller.BaseController;
import com.ascap.apm.controller.utils.HtmlSelectOption;
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

    @GetMapping("/authenticateLogon")
    public ModelAndView logon(HttpServletRequest request, HttpServletResponse response, ModelAndView view,
        BindingResult bindingResult) {
        String userTheme = "";
        log.debug("Entering logon method in LogonController");
        // Temp variable to turn on/off the security (Call to TAM). Set to false in the "LogonController" if needs turn
        // off.
        boolean isSecurityEnabled = false;
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        String userId = request.getHeader("iv-user");
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
                    userTheme = getUserTheme(request);
                    view.setViewName("redirect:/usageHomeSearch?performanceSearchType=CRITERIA&&theme=" + userTheme);
                }
            }
        } catch (PrepSystemException pse) {
            log.error(messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
            view.getModel().put("systemerror", messageSource.getMessage(pse.getErrorKey(), null, Locale.getDefault()));
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

    private String getUserTheme(HttpServletRequest request) {
        String theme = UIWidgetConstants.DEFAULT_THEME_TYPE_CODE;
        UserSessionState userSessionState =
            (UserSessionState) request.getSession().getAttribute(UserSessionState.HTTP_SESSION_KEY);
        if (userSessionState != null) {
            UserPreference userPreference = userSessionState.getUserPreference();
            if (userPreference != null) {
                theme = userPreference.getThemeTypeCode();
                if (theme == null || "".equals(theme)) {
                    theme = UIWidgetConstants.DEFAULT_THEME_TYPE_CODE;
                }
                if (PrepConstants.THEME_RANDOM.equals(theme)) {
                    theme = userPreference.getRandomThemeTypeCode();
                }
            }
        }

        String themePath = HtmlSelectOption.getLookUpTable("ThemePaths", theme);
        if (themePath == null || "".equals(themePath)) {
            themePath = UIWidgetConstants.DEFAULT_THEME_TYPE_DES;
        }
        return themePath;
    }

}
