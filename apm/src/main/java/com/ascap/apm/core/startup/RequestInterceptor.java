package com.ascap.apm.core.startup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import com.ascap.apm.vob.BaseSearchVOB;
import com.rsa.cryptoj.f.E;

/**
 * The Class TransactionInterceptor.
 */
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private MessageSource messageSource;

	/** The logger. */
	protected LogHelper log = new LogHelper(this.getClass().getName());

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

		if (!request.getRequestURI().contains("/logon") && !request.getRequestURI().contains("/authenticateLogon")) {
			log.debug("Entering preHandle method");
			String remoteHost = request.getRemoteHost();
			String remoteUser = "UnknownUser";

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
				log.debug("In preHandle Module Name Id : "
						+ (getPREPContext(request).getUserSessionState().getModuleName()));
			} catch (Exception e) {
				log.error(e);
			}

			log.debug("Entering execute method in preHandle");

		}
		return true;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("Inside postHandle....." + request.getRequestURI());

		if (!request.getRequestURI().contains("/logon") && !request.getRequestURI().contains("/authenticateLogon")) {
			List<E> beanObjectList = null;
			if (modelAndView != null) {
				beanObjectList = new ArrayList(modelAndView.getModel().values());

				for (Object beanObject : beanObjectList) {
					if (beanObject instanceof BaseSearchVOB) {
						BaseSearchVOB baseSearchVOB = (BaseSearchVOB) beanObject;
						if (baseSearchVOB.getNumberOfRecordsFoundbySearch() > getPREPContext(request)
								.getUserSessionState().getUserPreference().getMaxSearchResults()) {
						    if(!modelAndView.getModel().containsKey("systemerror")) {
							modelAndView.getModel().put("systemerror",
									messageSource.getMessage("errors.searchresults.exceededthreshold",
											new Object[] { baseSearchVOB.getNumberOfRecordsFoundbySearch() },
											Locale.getDefault()));
						    }
						}
						break;
					}
				}
			}
		}
	}

	public PREPContext getPREPContext(HttpServletRequest request) {
		PREPContext prepContext = new PREPContext();
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
			request.getSession().setAttribute(aKey, (Serializable) aValue);
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

}
