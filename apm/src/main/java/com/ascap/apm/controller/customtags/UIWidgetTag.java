package com.ascap.apm.controller.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import com.ascap.apm.common.helpers.LogHelper;

/**
 * @author Raju_Ayanampudi
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class UIWidgetTag extends SecureTag {

	private static final long serialVersionUID = 2912384135283613625L;

	protected static LogHelper log = new LogHelper("UiWidgetTag");

	protected String label = null;

	protected String title = null;

	protected String disabled = null;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			hasPermission = true;
		} catch (Exception ex) {
			log.error("Exception in doStartTag method in UIWidgetTag: ", ex);
			throw new JspTagException("SimpleTag: " + ex.getMessage());
		}
		return (EVAL_BODY_BUFFERED);
	}

	/**
	 * @see javax.servlet.jsp.tagext.IterationTag#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {

		try {
			JspWriter out = this.getPreviousOut();
			String bodyContentText = this.getBodyContent().getString().trim();

			StringBuilder outputString = UIWidgetHelper.getwidget(hasPermission, disabled, type, id, label, title,
					bodyContentText);

			out.print(outputString);

			bodyContent.clearBody();

		} catch (IOException e) {
			log.error("IOException in doAfterBody method in UIWidgetInquiryTag: ", e);
		}

		return (SKIP_BODY);
	}
}
