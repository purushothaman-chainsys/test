package com.ascap.apm.controller.customtags;

import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.utils.constants.Constants;

/**
 * @author Raju_Ayanampudi
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class DisplayFormatTag extends BodyTagSupport {

	private static final long serialVersionUID = -4490358801274219165L;

	protected static LogHelper log = new LogHelper("DisplayFormatTag");

	protected String type = null;

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		return (EVAL_BODY_BUFFERED);
	}

	/**
	 * @see javax.servlet.jsp.tagext.IterationTag#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {
		if (bodyContent != null) {
			try {
				if (type != null && type.trim().equals(Constants.DISPLAY_FORMAT_TYPE_AMOUNT)) {
					JspWriter out = this.getPreviousOut();
					String content = this.getBodyContent().getString().trim();
					String formattedContent = formatContent(content);
					out.print(formattedContent);
					bodyContent.clearBody();

				}

				if (type != null && type.trim().equals(Constants.DISPLAY_FORMAT_TYPE_CREDITS)) {
					JspWriter out = this.getPreviousOut();
					String content = this.getBodyContent().getString().trim();
					String formattedContent = formatContent(content);
					out.print(formattedContent);
					bodyContent.clearBody();
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}

		return (SKIP_BODY);
	}

	private String formatContent(String content) {
		String formattedContent = null;
		try {
			DecimalFormat decimalFormat = new DecimalFormat("$#,###,###,##0.00");
			formattedContent = decimalFormat.format(Double.parseDouble(content));
		} catch (NumberFormatException nfe) {
			formattedContent = content;
		}
		return formattedContent;
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
}
