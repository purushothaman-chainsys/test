package com.ascap.apm.controller.customtags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.utils.constants.SecurityConstants;
import com.ascap.apm.controller.utils.SecurityUtils;

/**
 * @author Raju_Ayanampudi
 *
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class SecureTag extends BodyTagSupport {

	private static final long serialVersionUID = 4696906542293977070L;

	protected static LogHelper log = new LogHelper("SecureTag");

	protected String type = null;

	protected String name = null;

	protected String hiddenValue = null;

	protected boolean hasPermission = false;

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			hasPermission = checkPermission(name);
		} catch (Exception ex) {
			log.error("Exception in doStartTag method in SecureTag: ", ex);
			throw new JspTagException("SimpleTag: " + ex.getMessage());
		}
		return (EVAL_BODY_BUFFERED);
	}

	/**
	 * @see javax.servlet.jsp.tagext.IterationTag#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {
		if (bodyContent != null) {
			try {
				JspWriter out = this.getPreviousOut();
				String content = this.getBodyContent().getString().trim();
				StringBuilder outputString = new StringBuilder(content);
				buildButtonTypeContent(outputString, content);
				buildAnchorTypeContent(outputString, content);
				buildReportTypeContent(outputString, content);
				buildMenuTypeContent(outputString, content);
				buildHideTypeContent(outputString);
				buildSelectTypeContent(outputString, content);
				buildMultiBoxTypeContent(outputString, content);
				buildCheckBoxTypeContent(outputString, content);
				buildTextTypeContent(outputString, content);
				buildTextAreaTypeContent(outputString, content);
				out.print(outputString);
				bodyContent.clearBody();

			} catch (IOException e) {
				log.error("IOException in doAfterBody method in SecureTag: ", e);
			}
		}
		return (SKIP_BODY);
	}

	private void buildTextAreaTypeContent(StringBuilder outputString, String content) {
		if (type != null && type.trim().equalsIgnoreCase(SecurityConstants.TEXTAREA_TYPE) && !hasPermission) {
			// If doesn't have execution permission,
			int strIndex = content.toUpperCase().indexOf("<TEXTAREA ");
			strIndex = strIndex + "<TEXTAREA ".length();
			if (strIndex == -1) {
				strIndex = content.toUpperCase().indexOf("<HTML:TEXTAREA ");
				strIndex = strIndex + "<HTML:TEXTAREA ".length();
			}
			char[] str = "readonly=\"true\" ".toCharArray();
			outputString.insert(strIndex, str);

		}
	}

	private void buildTextTypeContent(StringBuilder outputString, String content) {
		if (type != null && type.trim().equalsIgnoreCase(SecurityConstants.TEXT_TYPE) && !hasPermission) {
			// If doesn't have execution permission,
			int strIndex = content.toUpperCase().indexOf("<INPUT TYPE=\"TEXT\" ");
			strIndex = strIndex + "<INPUT TYPE=\"TEXT\" ".length();
			if (strIndex == -1) {
				strIndex = content.toUpperCase().indexOf("<HTML:TEXT ");
				strIndex = strIndex + "<HTML:TEXT ".length();
			}
			char[] str = "readonly=\"true\" ".toCharArray();
			outputString.insert(strIndex, str);

		}
	}

	private void buildCheckBoxTypeContent(StringBuilder outputString, String content) {
		if (type != null && type.trim().equalsIgnoreCase(SecurityConstants.CHECKBOX_TYPE) && !hasPermission) {
			// If doesn't have execution permission,
			int strIndex = content.toUpperCase().indexOf("<SELECT ");
			strIndex = strIndex + "<SELECT ".length();
			if (strIndex == -1) {
				strIndex = content.toUpperCase().indexOf("<HTML:CHECKBOX ");
				strIndex = strIndex + "<HTML:CHECKBOX ".length();
			}

			char[] str = "disabled=\"true\" ".toCharArray();

			int index = content.indexOf("name");
			int strIndexOf = content.indexOf('\"', index);
			int endIndex = content.indexOf('\"', strIndexOf + 1);
			String propertyName = content.substring(strIndexOf + 1, endIndex);

			outputString.insert(strIndex, str);
			outputString
					.append(" <input type=\"hidden\" name=\"" + propertyName + "\" value=\"" + hiddenValue + "\" />");

		}
	}

	private void buildMultiBoxTypeContent(StringBuilder outputString, String content) {
		if (type != null && type.trim().equalsIgnoreCase(SecurityConstants.MULTIBOX_TYPE) && !hasPermission) {
			// If doesn't have execution permission,
			int strIndex = content.toUpperCase().indexOf("<SELECT ");
			strIndex = strIndex + "<SELECT ".length();
			if (strIndex == -1) {
				strIndex = content.toUpperCase().indexOf("<HTML:MULTIBOX ");
				strIndex = strIndex + "<HTML:MULTIBOX ".length();
			}

			char[] str = "disabled=\"true\" ".toCharArray();

			outputString.insert(strIndex, str);

		}
	}

	private void buildSelectTypeContent(StringBuilder outputString, String content) {
		if (type != null && type.trim().equalsIgnoreCase(SecurityConstants.SELECT_TYPE) && !hasPermission) {
			// If doesn't have execution permission,
			int strIndex = content.toUpperCase().indexOf("<SELECT ");
			strIndex = strIndex + "<SELECT ".length();
			if (strIndex == -1) {
				strIndex = content.toUpperCase().indexOf("<HTML:SELECT ");
				strIndex = strIndex + "<HTML:SELECT ".length();
			}
			char[] str = "disabled=\"true\" ".toCharArray();

			int index = content.indexOf("property");
			int strIndexOf = content.indexOf('\"', index);
			int endIndex = content.indexOf('\"', strIndexOf + 1);
			String propertyName = content.substring(strIndexOf + 1, endIndex);

			outputString.insert(strIndex, str);

			outputString
					.append(" <input type=\"hidden\" name=\"" + propertyName + "\" value=\"" + hiddenValue + "\" />");

		}
	}

	private void buildHideTypeContent(StringBuilder outputString) {
		String content = null;
		if (type != null && type.trim().equals(SecurityConstants.HIDE_TYPE) && !hasPermission) {
			// If doesn't have execution permission,
			// Hides the component if doesn't have the permissions.
			content = "<!-- " + outputString + " -->";
			outputString.append(content);
		}
	}

	private void buildMenuTypeContent(StringBuilder outputString, String content) {
		if (type != null && type.trim().equals(SecurityConstants.MENU_TYPE) && !hasPermission) {

			int strIndex = content.indexOf("loadFrames");
			int endIndex = content.indexOf('\"', strIndex);
			outputString.replace(strIndex, endIndex, "");
		}
	}

	private void buildReportTypeContent(StringBuilder outputString, String content) {
		if (type != null && (type.trim().equals(SecurityConstants.SHOW_REPORT_TYPE)
				|| type.trim().equals(SecurityConstants.READ_REPORT_TYPE)
				|| type.trim().equals(SecurityConstants.EXECUTE_REPORT_TYPE)
				|| type.trim().equals(SecurityConstants.EXPORT_REPORT_TYPE))) {

			boolean reportPermission = hasReportPermission(type.trim());

			if (!reportPermission) {
				// Checks for the view permissions
				if (type.trim().equals(SecurityConstants.SHOW_REPORT_TYPE)) {
					// Hides the component if doesn't have the view permissions.
					content = "<!-- " + outputString + " -->";
					outputString.append(content);
				} else {
					// If has the view permissions but not the execution permissions, removes the
					// anchor.
					int idx = (content.toLowerCase()).indexOf("<a");
					int strIndex = (content.toLowerCase()).indexOf('>', idx);
					int endIndex = (content.toLowerCase()).indexOf("</a>");
					content = content.substring(strIndex + 1, endIndex);
					outputString.append(content);
				}
			}
		}

	}

	private void buildAnchorTypeContent(StringBuilder outputString, String content) {
		if (type != null && type.trim().equals(SecurityConstants.ANCHOR_TYPE) && !hasPermission) {
			// If has the view permissions but not the execution permissions, removes the
			// anchor.

			int idx = (content.toLowerCase()).indexOf("<a");
			int strIndex = (content.toLowerCase()).indexOf('>', idx);
			int endIndex = (content.toLowerCase()).indexOf("</a>");
			content = content.substring(strIndex + 1, endIndex);
			outputString.append(content);
		}
	}

	private void buildButtonTypeContent(StringBuilder outputString, String content) {
		if (type != null && type.trim().equals(SecurityConstants.BUTTON_TYPE) && !hasPermission) {

			// If has the view permissions but not the execution permissions, makes it
			// readonly.
			int i = content.indexOf("/>");
			if (i == -1) {
				i = content.indexOf('>');
			}
			char[] str = " disabled=\"true\" ".toCharArray();
			outputString.insert(i, str);
		}

	}

	protected boolean checkPermission(String objectName) {
		UserProfile userProfile = (UserProfile) ((HttpServletRequest) this.pageContext.getRequest()).getSession()
				.getAttribute(UserProfile.HTTP_SESSION_KEY);
		if (objectName == null || objectName.equals("")) {
			objectName = name;
		}
		return SecurityUtils.hasPermission(userProfile, objectName);
	}

	private boolean hasReportPermission(String type) {
		UserProfile userProfile = (UserProfile) ((HttpServletRequest) this.pageContext.getRequest()).getSession()
				.getAttribute(UserProfile.HTTP_SESSION_KEY);
		return SecurityUtils.hasPermission(userProfile, name, type);
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	@Override
	public int doEndTag() {
		return EVAL_PAGE;
	}

	/**
	 * Returns the type.
	 * 
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
