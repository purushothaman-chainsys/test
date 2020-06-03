package com.ascap.apm.common.utils;



import com.ascap.apm.common.utils.constants.PrepConstants;



/**
 * @author mzbamidi
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PrepUtils {
	
	private PrepUtils() {
		
	}
	
	/**
	 * Method getStatus.
	 * @param statusCode
	 * @return String
	 */
	
	public static String getStatus(int statusCode){
		
		String status="";
		switch(statusCode){
			
			case 1:
			   status=PrepConstants.REPORT_STATUS_SUCCESS;
			   break;
			case 3:
			   status=PrepConstants.REPORT_STATUS_FAIL;
			   break;
			case 8:
			   status=PrepConstants.REPORT_STATUS_PAUSED;
			   break;
			case 9:
			   status=PrepConstants.REPORT_STATUS_PENDING;
			   break;
			case 0:
			   status=PrepConstants.REPORT_STATUS_RUNNING;
			   break;
			default :
	        status=PrepConstants.REPORT_STATUS_UNKNOWN;
		}	   
       return status;
	}
	
	public static final String breakCSVList(String inputStr){
		if(ValidationUtils.isEmptyOrNull(inputStr)){
			return inputStr;
		}
		
		return inputStr.replace(",", ", ");
	}
}
