package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class CatalogSamplingRequest extends BaseVOB {

	private static final long serialVersionUID = -5843533138102707886L;
	private String samplingRequestId;
	private String statusCode; 
	private String processStartTime; 
	private String callLetter; 
	private String targetSurveyYearQuarter; 
	private String loadStatus; 
	private String sampleCount; 
	private String samplesRequested; 
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SamplingRequest [samplingRequestId=");
		builder.append(samplingRequestId);
		builder.append(", statusCode=");
		builder.append(statusCode); 
		builder.append(", processStartTime=");
		builder.append(processStartTime); 
		builder.append(", callLetter=");
		builder.append(callLetter); 
		builder.append(", targetSurveyYearQuarter=");
		builder.append(targetSurveyYearQuarter); 
		builder.append(", loadStatus=");
		builder.append(loadStatus); 
		builder.append("]");
		return builder.toString();
	}
	

	public String getSamplingRequestId() {
		return samplingRequestId;
	}


	public void setSamplingRequestId(String samplingRequestId) {
		this.samplingRequestId = samplingRequestId;
	}


	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	 


	public String getProcessStartTime() {
		return processStartTime;
	}


	public void setProcessStartTime(String processStartTime) {
		this.processStartTime = processStartTime;
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

 
	
	public String getLoadStatus() {
		return loadStatus;
	}

	public void setLoadStatus(
			String loadStatus) {
		this.loadStatus = loadStatus;
	}


	public String getSampleCount() {
		return sampleCount;
	}


	public void setSampleCount(String sampleCount) {
		this.sampleCount = sampleCount;
	}


	public String getSamplesRequested() {
		return samplesRequested;
	}


	public void setSamplesRequested(String samplesRequested) {
		this.samplesRequested = samplesRequested;
	}
	
	 
}
