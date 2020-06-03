package com.ascap.apm.core.startup;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ascap.apm.common.context.ApplicationState;
import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.context.UserSessionState;
import com.ascap.apm.common.exception.cache.httpsession.InvalidSessionObjectException;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.utils.constants.Constants;
import com.ascap.apm.vob.BaseSearchVOB;

/**
 * The Class TransactionInterceptor.
 */
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter implements MessageSourceAware {

	/** The logger. */
	protected LogHelper log = new LogHelper(this.getClass().getName());

	@Autowired
	protected MessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(
	 * javax.servlet.http. HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering execute method in BaseAction");
		}

		// add Mapped logging context with remote host name and remote user name to
		// logger
		String remoteHost = request.getRemoteHost();

		String remoteUser = "UnknownUser";
		boolean isActionForm = false;
		boolean isValidatorForm = false;
		boolean isModeling = false;

		try {
			if (getPREPContext(request).getUserProfile() != null
					&& getPREPContext(request).getUserProfile().getUserId() != null) {
				remoteUser = getPREPContext(request).getUserProfile().getUserId();

			} else if (request.getParameter("userId") != null) {
				remoteUser = request.getParameter("userId");
			} else {
				response.sendRedirect("/logout");
			}

			// this adds the MDC variable USER with username as value
			log.putMDC("HOST_USER", remoteHost + ":" + "<" + remoteUser + ">");

			// this is to set the link as modelling environment
			log.debug("In BaseAction Module Name Id : "
					+ (getPREPContext(request).getUserSessionState().getModuleName()));
			byte moduleId = -1;
			if (getPREPContext(request).getUserSessionState() != null) {
				String disMod = request.getParameter("isModelling");
				if (disMod != null && disMod.equalsIgnoreCase("yes")) {
					getPREPContext(request).getUserSessionState().setModuleName(Constants.MODELING_MODULE);
				} else if (disMod != null && disMod.equalsIgnoreCase("no")
						&& getPREPContext(request).getUserSessionState().getModuleName() == Constants.MODELING_MODULE) {
					getPREPContext(request).getUserSessionState().setModuleName(Constants.DISTRIBUTION_MODULE);
				}
				moduleId = getPREPContext(request).getUserSessionState().getModuleName();
			}
			if (moduleId == Constants.MODELING_MODULE) {
				isModeling = true;
			} else {
				isModeling = false;
			}

			/*
			 * if (form != null && form instanceof BaseActionForm) { isActionForm = true; }
			 * else if (form != null && form instanceof BaseValidatorForm) { isValidatorForm
			 * = true; } if (isActionForm) { BaseActionForm aForm = (BaseActionForm) form;
			 * aForm.setUserId(remoteUser); if (request.getSession() != null &&
			 * request.getSession().getAttribute(UserSessionState.HTTP_SESSION_KEY) != null)
			 * { aForm.setApplicationLoggedFrom(((com.ascap.prep.common.context.
			 * UserSessionState) request
			 * .getSession().getAttribute(UserSessionState.HTTP_SESSION_KEY)).
			 * getApplicationLogged()); } if (isModeling) { aForm.setIsModelling(true); }
			 * form = (ActionForm) aForm; } else if (isValidatorForm) { BaseValidatorForm
			 * vForm = (BaseValidatorForm) form; vForm.setUserId(remoteUser); if
			 * (request.getSession() != null &&
			 * request.getSession().getAttribute(UserSessionState.HTTP_SESSION_KEY) != null)
			 * { vForm.setApplicationLoggedFrom(((com.ascap.prep.common.context.
			 * UserSessionState) request
			 * .getSession().getAttribute(UserSessionState.HTTP_SESSION_KEY)).
			 * getApplicationLogged()); } if (isModeling) { vForm.setIsModelling(true); }
			 * form = (ActionForm) vForm; }
			 */
			// Discussion
		} catch (Exception e) {
			log.error(e);
		}

		log.info("Inside preHandle....." + request.getRequestURI());
		log.debug("Entering execute method in BaseAction");

		/*
		 * ActionErrors errors = (ActionErrors) request.getAttribute(Globals.ERROR_KEY);
		 * if (errors == null) { errors = new ActionErrors(); }
		 */

		try {
			// call authorize
			/*
			 * if (!authenticateUser(request, response)) { // report back to the UI saying
			 * user has no access to this errors.add("user", new
			 * ActionMessage("errors.authenticationfailed")); saveErrors(request, errors);
			 * // Forward control to the appropriate 'failure' URI (change name // as
			 * desired) forward = mapping.findForward("error_authenticationfailed"); }
			 * //call authorize if (!authorizeUser(request, response)) { // report back to
			 * the UI saying invalid user errors.add("user", new
			 * ActionMessage("errors.authorizationfailed")); saveErrors(request, errors); //
			 * Forward control to the appropriate 'failure' URI (change name // as desired)
			 * forward = mapping.findForward("error_authorizationfailed"); }
			 */ // Discussion Not Required

			if (log.isDebugEnabled()) {
				log.debug("BaseAction isTriggerUpdateRequired: "
						+ getPREPContext(request).getUserSessionState().isTriggerUpdateRequired());
			}

			// If Logged in user has unedited Adjustment Triggers, forward to
			// Adjusment Module and triggers screen.
			/*
			 * if (!(form instanceof
			 * com.ascap.prep.web.forms.adjustments.AdjustmentsTriggerListForm) && !(form
			 * instanceof com.ascap.prep.web.forms.adjustments.AdjustmentsTriggerForm) &&
			 * !(form instanceof com.ascap.prep.web.forms.admin.UserProfileForm)) { if
			 * (getPREPContext(request).getUserSessionState().isTriggerUpdateRequired()) {
			 * if
			 * (!(AdminConstants.LOGOUT_ACTION_PATH).equalsIgnoreCase(request.getPathInfo())
			 * ) { if (log.isDebugEnabled()) {
			 * log.debug("Forwarding to uneditedAdjustmentTriggers from BaseAction."); }
			 * return mapping.findForward("uneditedAdjustmentTriggers"); } else { if
			 * (log.isDebugEnabled()) {
			 * log.debug("No Adjustment Forward Required .. Action requested is logout."); }
			 * } } else { if (log.isDebugEnabled()) { log.
			 * debug("No Adjustment Forward Required .. isTriggerUpdateRequired is false");
			 * } } } else { if (log.isDebugEnabled()) {
			 * log.debug("No Adjustment Forward Required "); } }
			 */

			// This below logic takes you Back to Search Criteria screen
			/*
			 * if(form instanceof BaseSearchForm &&
			 * ((BaseSearchForm)form).getNavigationType() != null &&
			 * ((BaseSearchForm)form).getNavigationType().equals(Constants.
			 * BACK_TO_SEARCH_CRITERIA)) { try{ return backToSearchCriteria(mapping, form,
			 * request, response); }catch(Exception e){ e.printStackTrace(); } }
			 */ // Need to Analyze.
		} catch (Exception e) {
			log.debug("Exception Occured : " + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(
	 * javax.servlet.http. HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
		log.info("\n\n\n\n\nInside postHandle....." + request.getRequestURI());
		ModelAndView modelAndView = new ModelAndView();
		Enumeration<String> myEnum = request.getAttributeNames();
		while (myEnum.hasMoreElements()) {
			String attributeName = (String) myEnum.nextElement();
			if (request.getAttribute(attributeName) instanceof BaseSearchVOB) {
				BaseSearchVOB baseSearchVOB = (BaseSearchVOB) request.getAttribute(attributeName);
				if (baseSearchVOB.getNumberOfRecordsFoundbySearch() > getPREPContext(request).getUserSessionState()
						.getUserPreference().getMaxSearchResults()) {
					modelAndView.getModel().put("systemerror",
							messageSource.getMessage("errors.searchresults.exceededthreshold",
									new Object[] { baseSearchVOB.getNumberOfRecordsFoundbySearch() },
									Locale.getDefault()));
				}
				break;
			}
		}

		/*
		 * if (request.getAttribute("viewName") != null) {
		 * modelAndView.setViewName((String)request.getAttribute("viewName"));
		 * request.setAttribute("viewName", null); }
		 */
	}

	public boolean authenticateUser(HttpServletRequest request, HttpServletResponse response) {
		// use whatever method to AUTHENTICATE the user
		// check if already AUTHENTICATED and let the user go if so
		return true;
	}

	/**
	 * PREP framework method to authenticate the user can be used in conjuntion with
	 * the application security model Can also be made abstract and force derived
	 * actions to implement security AUTHORIZATION is checking if the user has
	 * premissions to make this specifc request
	 */
	public boolean authorizeUser(HttpServletRequest request, HttpServletResponse response) {
		// use whatever method to AUTHORIZE the user
		return true;
	}

	public PREPContext getPREPContext(HttpServletRequest request) {
		PREPContext prepContext = new PREPContext();
		HttpSession httpSession = request.getSession();
		// get user profile
		UserProfile userProfile = (UserProfile) getObjectFromHTTPSession(request, UserProfile.HTTP_SESSION_KEY);
		// get application state
		ApplicationState applicationState = (ApplicationState) getObjectFromHTTPSession(request,
				ApplicationState.HTTP_SESSION_KEY);
		// get user session state
		UserSessionState userSessionState = (UserSessionState) getObjectFromHTTPSession(request,
				UserSessionState.HTTP_SESSION_KEY);

		// Begin - This is simulation of login.Eventually going to be removed.
		if (userProfile == null) {
			userProfile = new UserProfile();
			setUserProfile(request, userProfile);
		}
		if (applicationState == null) {
			applicationState = new ApplicationState();
			setApplicationState(request, applicationState);
		}
		if (userSessionState == null) {
			UserPreference userPreference = new UserPreference();
			userPreference.setNofSrchRsltsPerPage(100);
			userSessionState = new UserSessionState();
			userSessionState.setUserPreference(userPreference);
			setUserSessionState(request, userSessionState);
		}
		// End - This is simulation of login.Eventually going to be removed.

		// set user profile
		prepContext.setUserProfile(userProfile);
		// set application state
		prepContext.setApplicationState(applicationState);
		// set user session state
		prepContext.setUserSessionState(userSessionState);

		return prepContext;
	}

	protected void setUserSessionState(HttpServletRequest request, UserSessionState userSessionState) {
		try {
			putObjectInHTTPSession(request, UserSessionState.HTTP_SESSION_KEY, userSessionState);
		} catch (InvalidSessionObjectException isoe) {
		}
	}

	protected void setApplicationState(HttpServletRequest request, ApplicationState applicationState) {
		try {
			putObjectInHTTPSession(request, ApplicationState.HTTP_SESSION_KEY, applicationState);
		} catch (InvalidSessionObjectException isoe) {
		}
	}

	protected void putObjectInHTTPSession(HttpServletRequest request, String aKey, Object aValue)
			throws InvalidSessionObjectException {
		if (!(aValue instanceof Serializable))
			throw new InvalidSessionObjectException("");
		if (log.isDebugEnabled())
			// log.debug("Putting Object In HTTPSession : Key = " + aKey + ", Value = " +
			// aValue.toString());
			request.getSession().setAttribute(aKey, aValue);
	}

	protected void setUserProfile(HttpServletRequest request, UserProfile userProfile) {
		try {
			putObjectInHTTPSession(request, UserProfile.HTTP_SESSION_KEY, userProfile);
		} catch (InvalidSessionObjectException isoe) {
		}
	}

	protected Object getObjectFromHTTPSession(HttpServletRequest request, String aKey) {
		return request.getSession().getAttribute(aKey);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;

	}
}
