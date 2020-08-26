package com.ascap.apm.controller.customtags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.springframework.web.servlet.tags.form.InputTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.ascap.apm.common.utils.constants.LookupTableConstants;

/**
 * @author Raju_Ayanampudi
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LookupTextTag extends InputTag {
	
	private static final long serialVersionUID = 8549553016889834594L;
	
	protected String lookupName;
	

	/**
	 * Returns the lookupName.
	 * @return String
	 */
	public String getLookupName() {
		return lookupName;
	}

	/**
	 * Sets the lookupName.
	 * @param lookupName The lookupName to set
	 */
	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		try {
			if(this.lookupName != null){				
				if(lookupName.equalsIgnoreCase(LookupTableConstants.LICENSETYPE_CODE)){
					this.setMaxlength(LookupTableConstants.LICENSETYPE_LENGTH);					
				}
				else if(lookupName.equalsIgnoreCase(LookupTableConstants.MUSICUSERTYPE_CODE)){
					this.setMaxlength(LookupTableConstants.MUSICUSERTYPE_LENGTH);					
				}
				else if(lookupName.equalsIgnoreCase(LookupTableConstants.RADIOFEATURETYPE_CODE)){
					this.setMaxlength(LookupTableConstants.RADIOFEATURETYPE_LENGTH);					
				}
				else if(lookupName.equalsIgnoreCase(LookupTableConstants.SAMPLETYPE_CODE)){
					this.setMaxlength(LookupTableConstants.SAMPLETYPE_LENGTH);					
				}
				else if(lookupName.equalsIgnoreCase(LookupTableConstants.SURVEYTYPE_CODE)){
					this.setMaxlength(LookupTableConstants.SURVEYTYPE_LENGTH);					
				}
				else if(lookupName.equalsIgnoreCase(LookupTableConstants.USETYPE_CODE)){
					this.setMaxlength(LookupTableConstants.USETYPE_LENGTH);					
				}
				else if(lookupName.equalsIgnoreCase(LookupTableConstants.CERMONIALAWARDCATEGORY_CODE)){
					this.setMaxlength(LookupTableConstants.CERMONIALAWARDCATEGORY_LENGTH);					
				}
				else if(lookupName.equalsIgnoreCase(LookupTableConstants.CERMONIALAWARDGENRE_CODE)){
					this.setMaxlength(LookupTableConstants.CERMONIALAWARDGENRE_LENGTH);					
				}
				else if(lookupName.equalsIgnoreCase(LookupTableConstants.CERMONIALAWARDTITLE_CODE)){
					this.setMaxlength(LookupTableConstants.CERMONIALAWARDTITLE_LENGTH);					
				}
				else if(lookupName.equalsIgnoreCase(LookupTableConstants.EXPLTTYPE_CODE)){
					this.setMaxlength(LookupTableConstants.EXPLTTYPE_LENGTH);					
				}
				
			}
			return super.writeTagContent(tagWriter);
		} catch (Exception ex) {
			throw new JspTagException("LookupTextTag: " + ex.getMessage());
		}
	}
}
