package com.ascap.apm.controller.customtags;

import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.utils.constants.SecurityConstants;
import com.ascap.apm.common.utils.constants.UIWidgetConstants;

/**
 * @author Raju_Ayanampudi
 */
public class UIWidgetHelper {

	protected static LogHelper log = new LogHelper("UIWidgetHelper");

	private static final String ANCHORSTART = "<a href=\"javascript:void(0)\" ";
	private static final String BUTTONSTART = "<button ";
	private static final String ICONSTART = "<a href=\"javascript:void(0)\" ";
	private static final String ANCHOREND = "</a>";
	private static final String BUTTONEND = "</button>";
	private static final String ICONEND = "</a>";
	
	private static final String STYLE_WITH_NO_ICON = "ui-button-text-only";
	private static final String STYLE_WITH_ICON = "ui-button-text-icon-primary";

	private static final String STYLE_VIEW_ANCHOR_ENABLED = "ui-button " + STYLE_WITH_ICON + " ui-widget ui-state-default ui-corner-all";
	private static final String STYLE_VIEW_ANCHOR_DISABLED = STYLE_VIEW_ANCHOR_ENABLED + " ui-state-disabled";

	private static final String STYLE_VIEW_ICON_ONLY_ENABLED = "ui-button ui-button-icon-only ui-widget ui-state-default ui-corner-all";
	private static final String STYLE_VIEW_ICON_ONLY_DISABLED = "ui-button ui-button-icon-only ui-widget ui-state-disabled ui-corner-all";

	private static final String TEXT1 = "class=\"";

	private static final String TEXT2 = "\" ";
	// idPrefixEnabled
	private static final String TEXT4 = " > ";
	private static final String TEXT5 = "<span class=\"ui-button-icon-primary ui-icon ";
	// ui-icon-search
	private static final String TEXT6 = "\"></span>";
	
	private static final String TEXT7 = "<span class=\"ui-button-text\" nowrap >";
	// label
	private static final String TEXT8 = "</span>";

	private static final String IDPREFIXENABLED = "id=\"";

	private static final String IDPREFIXDISABLED = "id=\"_nolink";

	private static final String DISABLEDTRUE = " disabled = \"true\" ";

	private static final String TYPE_SUBMIT = " type = \"submit\" ";

	public static StringBuilder getwidget(boolean hasPermission, String disabled, String type, String id, String label, String title, String bodyContentText) {

		boolean isBUTTON = false;
		boolean isICON = false;
		boolean isANCHOR = false;
		StringBuilder outputString = new StringBuilder();
		boolean disableWidget = false;

		if (disabled != null && disabled.equalsIgnoreCase("TRUE")) {
			disableWidget = true;
		} else {
			if (hasPermission == false) {
				disableWidget = true;
			}
		}

		if (type != null && type.trim().equals(SecurityConstants.BUTTON_TYPE)) {
			isBUTTON = true;
		} else if (type != null
				&& type.trim().equals(SecurityConstants.ANCHOR_TYPE)) {
			isANCHOR = true;
		} else if (type != null
				&& type.trim().equals(SecurityConstants.ICON_TYPE)) {
			isICON = true;
		}

		if (isBUTTON) {
			outputString.append(BUTTONSTART);
			outputString.append(TYPE_SUBMIT);
		} else if (isANCHOR) {
			outputString.append(ANCHORSTART);
		} else if (isICON) {
			outputString.append(ICONSTART);
		}

		outputString.append(TEXT1);
		if (disableWidget) {
			if (isBUTTON || isANCHOR) {
				outputString.append(STYLE_VIEW_ANCHOR_DISABLED);
			} else if (isICON) {
				outputString.append(STYLE_VIEW_ICON_ONLY_DISABLED);
			}
		} else {
			if (isBUTTON || isANCHOR) {
				outputString.append(STYLE_VIEW_ANCHOR_ENABLED);
			} else if (isICON) {
				outputString.append(STYLE_VIEW_ICON_ONLY_ENABLED);
			}
		}
		outputString.append(TEXT2);

		if (disableWidget) {
			outputString.append(DISABLEDTRUE);
			outputString.append(IDPREFIXDISABLED);
		} else {
			outputString.append(IDPREFIXENABLED);
		}

		outputString.append(id).append("\" ");
		if (title != null && !title.trim().equals("")) {
			outputString.append(" title=\"" + title + "\" ");
		}
		if(!disableWidget){
			if(bodyContentText != null){
				outputString.append(bodyContentText);
			}
		}
		outputString.append(TEXT4);
		
		if(UIWidgetConstants.prepIcons.get(id) != null && ((String)UIWidgetConstants.prepIcons.get(id)).trim().equals("")){
			int startIndex = outputString.indexOf(STYLE_WITH_ICON);
			if(startIndex != -1){
				outputString.replace(startIndex, (startIndex + STYLE_WITH_ICON.length()), STYLE_WITH_NO_ICON);
			}
		}else{
			outputString.append(TEXT5);
			outputString.append(UIWidgetConstants.prepIcons.get(id));
			outputString.append(TEXT6);
		}
		outputString.append(TEXT7);
		if (isBUTTON || isANCHOR) {
			if (label != null && !label.equals("")) {
				outputString.append(label);
			}
		} else if (isICON) {
			outputString.append("&nbsp;");
		}

		outputString.append(TEXT8);

		if (isBUTTON) {
			outputString.append(BUTTONEND);
		} else if (isANCHOR) {
			outputString.append(ANCHOREND);			
		} else if (isICON) {
			outputString.append(ICONEND);
		}
		
		// This below code is to handle DoubleClick issue
		if((isANCHOR)&& (
				outputString.indexOf(UIWidgetConstants.ID_SUBMIT) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_UPDATE) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_DELETE) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_UNMATCH) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_ADD) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_BULK_AMEND) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_GRANT_AWARD_APPLICATION) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_GRANT_AWARD_PAYMENT) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_DECLINE_AWARD_APPLICATION) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_ROLLUP_AND_LOAD_APM) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_PROCESS) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_DO_NOT_PROCESS) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_DECLINE) != -1
				|| outputString.indexOf(UIWidgetConstants.ID_CONTINUE) != -1
				)){
			if(!(outputString.toString().toLowerCase().indexOf("onclick") != -1)) {
				int idx = (outputString.toString().toLowerCase()).indexOf("<a");	
				if(idx != -1){
					outputString.insert(idx+2, " onClick=\"javascript:checkSub(this);\"" );
				}	
			}else{
				int idx = (outputString.toString().toLowerCase()).indexOf("onclick");		
				if(idx != -1){
					int strIndex = outputString.toString().indexOf("\"", idx);	
					outputString.insert(strIndex+1, "javascript:checkSub(this);" );	
				}
			}		
		}		
		return outputString;
	}
}
