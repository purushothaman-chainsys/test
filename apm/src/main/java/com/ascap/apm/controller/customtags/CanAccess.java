package com.ascap.apm.controller.customtags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * @author Saravanan_Sengottaiyan
 *
 * This tag handler class will decide whether to hyperlink a given text
 * or not based on the user authorization.
 */

public class CanAccess extends TagSupport {

	private static final long serialVersionUID = 3111401837641249021L;
	
	private String href = null;
	private String anchortext = null;
	private String enableflag = null;
	
	private boolean isAuthorized = true;

	@Override
	public int doStartTag() throws JspTagException {


		try {
			JspWriter out = pageContext.getOut();

			if (isAuthorized) {
				if (enableflag != null && !enableflag.equals("false")) {
					out.println("<a href=\"" + href + "\">" + anchortext + "</a>");
				} else {
					out.println(anchortext);					
				}
			} else {
				out.println(anchortext);
			}

		} catch (Exception ex) {
			throw new JspTagException(ex.getMessage());
		}

		return SKIP_BODY;
	}

	/**
	 * doEndTag is called by the JSP container when the tag is closed
	 */
	@Override
	public int doEndTag() throws JspTagException {
		return EVAL_PAGE;
	}

	/**
	 * Returns the enableflag.
	 * @return String
	 */
	public String getEnableflag() {
		return enableflag;
	}

	/**
	 * Returns the href.
	 * @return String
	 */
	public String getHref() {
		return href;
	}

	/**
	 * Sets the enableflag.
	 * @param enableflag The enableflag to set
	 */
	public void setEnableflag(String enableflag) {
		this.enableflag = enableflag;
	}

	/**
	 * Sets the href.
	 * @param href The href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * Returns the anchortext.
	 * @return String
	 */
	public String getAnchortext() {
		return anchortext;
	}

	/**
	 * Sets the anchortext.
	 * @param anchortext The anchortext to set
	 */
	public void setAnchortext(String anchortext) {
		this.anchortext = anchortext;
	}

}