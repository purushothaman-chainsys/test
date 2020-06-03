package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class LogSummaryRequest extends BaseVOB {

	/**
	 * 
	 */
	 
	private static final long serialVersionUID = -5843533138102707886L; 
	
	private String targetSurveyYearQuarter; 
	
	private String callLetter;   
	private String startDate;
	private String endDate;
	private String location;
	private String state;
	private String accountNumber;
	private String logMode;
	private String format;
	private String dateReceived;
	private String logComments;
	private String notes;
	private String status;

	private String logRequestId;
	
	private String startDateDummy;
	private String endDateDummy;
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(); 
		builder.append(", callLetter=");
		builder.append(callLetter); 
		builder.append(", targetSurveyYearQuarter=");
		builder.append(targetSurveyYearQuarter);  
		builder.append("]");
		return builder.toString();
	}
	
 

	public String getCallLetter() {
		return callLetter;
	}


	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}


	 


	public String getTargetSurveyYearQuarter() {
		return targetSurveyYearQuarter;
	}


	public void setTargetSurveyYearQuarter(String targetSurveyYearQuarter) {
		this.targetSurveyYearQuarter = targetSurveyYearQuarter;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public String getAccountNumber() {
		return accountNumber;
	}



	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getLogMode() {
		return logMode;
	}



	public void setLogMode(String logMode) {
		this.logMode = logMode;
	}



	public String getFormat() {
		return format;
	}



	public void setFormat(String format) {
		this.format = format;
	}



	public String getDateReceived() {
		return dateReceived;
	}



	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}



	public String getLogComments() {
		return logComments;
	}



	public void setLogComments(String logComments) {
		this.logComments = logComments;
	}



	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		this.notes = notes;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getLogRequestId() {
		return logRequestId;
	}



	public void setLogRequestId(String logRequestId) {
		this.logRequestId = logRequestId;
	}



	public String getStartDateDummy() {
		return startDateDummy;
	}



	public void setStartDateDummy(String startDateDummy) {
		this.startDateDummy = startDateDummy;
	}



	public String getEndDateDummy() {
		return endDateDummy;
	}



	public void setEndDateDummy(String endDateDummy) {
		this.endDateDummy = endDateDummy;
	}

 
	 
	 
}
