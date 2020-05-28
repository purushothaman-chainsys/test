package com.ascap.apm.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import com.ascap.apm.common.context.ApplicationState;
import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.context.UserSessionState;
import com.ascap.apm.common.exception.cache.KeyNotFoundException;
import com.ascap.apm.common.exception.cache.httpsession.InvalidSessionObjectException;
import com.ascap.apm.common.helpers.LogHelper;

/**
 * @version 1.0
 * @author Sudhar Krishnamachary
 * @author Pratap Sanikommu
 * @version $Revision: 1.35 $ $Date: Jan 12 2011 13:22:56 $
 */
public abstract class BaseController implements MessageSourceAware  {

    @Autowired
    protected MessageSource messageSource;
    
    protected static final String SYSTEMERROR = "systemerror";
    protected static final String SYSTEMMESSAGE = "systemmessage";
    protected static final String ERRORPAGE = "error";
    
	protected LogHelper log = new LogHelper(this.getClass().getName());

	/**
	 * Constructor
	 */
	public BaseController() {
		super();
	}

	@Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        
    }
	
	public String getMessage(String key){
		return messageSource.getMessage(key, null, Locale.getDefault());
	}
	
	public String getMessage(String key , Object... args){
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

	/**
	 * Method setUserSessionState. This will be the only method on this object used
	 * by the entire application to set User Session State into HTTPSession
	 * 
	 * @param request
	 * @param userSessionState
	 */
	protected void setUserSessionState(HttpServletRequest request, UserSessionState userSessionState) {
		try {
			putObjectInHTTPSession(request, UserSessionState.HTTP_SESSION_KEY, userSessionState);
		} catch (InvalidSessionObjectException isoe) {
		    log.error(isoe.getMessage());
		}
	}

	/**
	 * Method setUserProfile. This will be the only method on this object used by
	 * the entire application to set User Profile into HTTPSession
	 * 
	 * @param request
	 * @param userProfile
	 */
	protected void setUserProfile(HttpServletRequest request, UserProfile userProfile) {
		try {
			putObjectInHTTPSession(request, UserProfile.HTTP_SESSION_KEY, userProfile);
		} catch (InvalidSessionObjectException isoe) {
		    log.error(isoe.getMessage());
		}
	}

	/**
	 * Method setApplicationState. This will be the only method on this object used
	 * by the entire application to set Application State into HTTPSession
	 * 
	 * @param request
	 * @param applicationState
	 */
	protected void setApplicationState(HttpServletRequest request, ApplicationState applicationState) {
		try {
			putObjectInHTTPSession(request, ApplicationState.HTTP_SESSION_KEY, applicationState);
		} catch (InvalidSessionObjectException isoe) {
		    log.error(isoe.getMessage());
		}
	}

	/**
	 * Method getObjectFromHTTPSession. This method will be used by all action class
	 * to get items from the HTTP Session
	 * 
	 * @param request
	 * @param aKey
	 * @return Object
	 * @throws KeyNotFoundException
	 */
	protected Object getObjectFromHTTPSession(HttpServletRequest request, String aKey) {
		return request.getSession().getAttribute(aKey);
	}

	/**
	 * Method putObjectInHTTPSession. This method will be used by all action class
	 * to put items into the HTTP Session
	 * 
	 * @param request
	 * @param aKey
	 * @param aValue
	 * @throws InvalidSessionObjectException
	 */
	protected void putObjectInHTTPSession(HttpServletRequest request, String aKey, Object aValue)
			throws InvalidSessionObjectException {
		if (!(aValue instanceof Object))
			throw new InvalidSessionObjectException("");
		if (log.isDebugEnabled())
			// log.debug("Putting Object In HTTPSession : Key = " + aKey + ", Value = " +
			request.getSession().setAttribute(aKey, (Object) aValue);
	}

	/**
	 * Method removeObjectFromHTTPSession. This method will be used by all action
	 * class to remove items from the HTTP Session
	 * 
	 * @param request
	 * @param aKey
	 */
	protected void removeObjectFromHTTPSession(HttpServletRequest request, String aKey) {
		request.getSession().removeAttribute(aKey);
	}

	/**
	 * Method getPREPContext. Used by subclass to get a new PREP Context This method
	 * initializes the PREP Context object with items from the HTTP Session.
	 * 
	 * @param request
	 * @return PREPContext
	 */
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

}
