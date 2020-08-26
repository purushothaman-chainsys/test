package com.ascap.apm.vob.reports;

import java.io.Serializable;

/**
 * @author mzbamidi
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ScheduleStatus implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = -2775798685552072256L;
    private String reportName;
	private String instanceID;
	private String status;
	private int statusCode;
	private String startDate;
	private String endDate;
	private String runBy;
	private String description;
	private String inputParams;
	private String requestedDate;
	private String returnedEndDate;
	
	private boolean adminExportFlag;
	
	
	public String toString(){
		return ("\n****ScheduleStatus >> \n" +
				"****reportName       >> " + reportName + "\n" +
				"****instanceID       >> " + instanceID + "\n" +
				"****status           >> " + status + "\n" +
				"****adminExportFlag  >> " + adminExportFlag + "\n" +
				"****runBy            >> " + runBy + "\n"	+
				"****startDate            >> " + startDate + "\n" +
				"****endDate            >> " + endDate + "\n" +
				"****description            >> " + description + "\n" +
				"****requestedDate            >> " + requestedDate + "\n" +
				"****returnedEndDate            >> " + returnedEndDate + "\n"
		);				
		
	}

	
	/**
	 * Returns the description.
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the instanceID.
	 * @return String
	 */
	public String getInstanceID() {
		return instanceID;
	}

	/**
	 * Returns the reportName.
	 * @return String
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * Returns the runBy.
	 * @return String
	 */
	public String getRunBy() {
		return runBy;
	}

	

	/**
	 * Returns the status.
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	

	/**
	 * Sets the description.
	 * @param description The description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the instanceID.
	 * @param instanceID The instanceID to set
	 */
	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}

	/**
	 * Sets the reportName.
	 * @param reportName The reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * Sets the runBy.
	 * @param runBy The runBy to set
	 */
	public void setRunBy(String runBy) {
		this.runBy = runBy;
	}

	
	/**
	 * Sets the status.
	 * @param status The status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns the endDate.
	 * @return String
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Returns the startDate.
	 * @return String
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Sets the endDate.
	 * @param endDate The endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * Sets the startDate.
	 * @param startDate The startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the statusCode.
	 * @return int
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the statusCode.
	 * @param statusCode The statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Returns the inputParams.
	 * @return String
	 */
	public String getInputParams() {
		return inputParams;
	}

	/**
	 * Sets the inputParams.
	 * @param inputParams The inputParams to set
	 */
	public void setInputParams(String inputParams) {
		this.inputParams = inputParams;
	}
	
	/**
	 * Returns the requestedDate.
	 * @return String
	 */
	public String getRequestedDate() {
		return requestedDate;
	}

	/**
	 * Sets the requestedDate.
	 * @param requestedDate The requestedDate to set
	 */
	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

    
    /**
     * @return Returns the returnedEndDate.
     */
    public String getReturnedEndDate() {
        return returnedEndDate;
    }
    /**
     * @param returnedEndDate The returnedEndDate to set.
     */
    public void setReturnedEndDate(String returnedEndDate) {
        this.returnedEndDate = returnedEndDate;
    }
	/**
	 * @return Returns the adminExportFlag.
	 */
	public boolean isAdminExportFlag() {
		return adminExportFlag;
	}
	/**
	 * @param adminExportFlag The adminExportFlag to set.
	 */
	public void setAdminExportFlag(boolean adminExportFlag) {
		this.adminExportFlag = adminExportFlag;
	}
}
