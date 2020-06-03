package com.ascap.apm.controller.customtags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.controller.utils.SecurityUtils;

/**
 * @author Raju_Ayanampudi
 *
 */
public class HasPermissionTag extends ConditionalTagSupport{
	
	private static final long serialVersionUID = 3616195377538460277L;

	protected static LogHelper log = new LogHelper("HasPermissionTag");
	
	protected String name = null;		
	protected String type = null;
	private String userId = null;
	protected String value = null;
	

    /**
     * Generate the required input tag.
     * <p>
     * Support for indexed property since Struts 1.1
     */
	@Override
	public boolean condition() {
	    boolean hasPermission = hasPermission();	
	    boolean condition = false;
	    if(hasPermission && this.value.equalsIgnoreCase("Y"))	condition = true;
	    else if(!hasPermission && this.value.equalsIgnoreCase("N"))	condition = true;
	    else condition = false;
	    return condition;
	}

	private boolean hasPermission() {
		UserProfile userProfile = (UserProfile) ((HttpServletRequest)this.pageContext.getRequest()).getSession().getAttribute(UserProfile.HTTP_SESSION_KEY);
		return SecurityUtils.hasPermission(userProfile, name, type);
	}

	/**
	 * Returns the userId.
	 * @return String
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the userId.
	 * @param userId The userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
	
	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
