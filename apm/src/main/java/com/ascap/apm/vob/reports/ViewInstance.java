package com.ascap.apm.vob.reports;

import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * @author vzayyadevara
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ViewInstance implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = -3773406930047125574L;
    private String reportID;
	private Object instanceObj;
	private String exportType;
	private String  reportName;
	private String xmlFilePath;
	private String userID;
	private RandomAccessFile exportFileHandle;
	
	
	public String toString()
	{
		StringBuffer outStrBuff;
		outStrBuff = new StringBuffer();
		outStrBuff
			.append("com.ascap.prep.common.vob.reports.ViewInstance {")
			.append("\nreportID= '")
			.append(reportID)
			.append("', ")
			.append("\ninstanceObj= '")
			.append(instanceObj)
			.append("', ")
			.append("'}");
		return (outStrBuff.toString());
	}
	/**
	 * Returns the instanceObj.
	 * @return Object
	 */
	public Object getInstanceObj() {
		return instanceObj;
	}

	/**
	 * Returns the reportID.
	 * @return String
	 */
	public String getReportID() {
		return reportID;
	}

	/**
	 * Sets the instanceObj.
	 * @param instanceObj The instanceObj to set
	 */
	public void setInstanceObj(Object instanceObj) {
		this.instanceObj = instanceObj;
	}

	/**
	 * Sets the reportID.
	 * @param reportID The reportID to set
	 */
	public void setReportID(String reportID) {
		this.reportID = reportID;
	}






	/**
	 * Returns the exportType.
	 * @return String
	 */
	public String getExportType() {
		return exportType;
	}

	/**
	 * Sets the exportType.
	 * @param exportType The exportType to set
	 */
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	/**
	 * Returns the reportName.
	 * @return String
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * Sets the reportName.
	 * @param reportName The reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * Returns the xmlFilePath.
	 * @return String
	 */
	public String getXmlFilePath() {
		return xmlFilePath;
	}

	/**
	 * Sets the xmlFilePath.
	 * @param xmlFilePath The xmlFilePath to set
	 */
	public void setXmlFilePath(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	}
	
	/**
	 * Returns the userID.
	 * @return String
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * Sets the userID.
	 * @param userID The userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	/**
	 * @return the exportFileHandle
	 */
	public RandomAccessFile getExportFileHandle() {
		return exportFileHandle;
	}
	/**
	 * @param exportFileHandle the exportFileHandle to set
	 */
	public void setExportFileHandle(RandomAccessFile exportFileHandle) {
		this.exportFileHandle = exportFileHandle;
	}

}
