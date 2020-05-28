package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class SamplingRequest extends BaseVOB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5843533138102707886L;
	private String samplingRequestId;
	private String statusCode;
	private String samplingStatusCode;
	private String statusDescription;
	private String processStartTime;
	private String processEndTime;
	private String callLetter;
	private String callLetterSuffix;
	private String targetSurveyYearQuarter;
	private String stepCode;
	private String stepDescription;
	private String playCountRanges;
	private String message;
	private String numberOfPerformancesToBeSampled;
	private String loadStatus;
	private String manMatchIndicator;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SamplingRequest [samplingRequestId=");
		builder.append(samplingRequestId);
		builder.append(", statusCode=");
		builder.append(statusCode);
		builder.append(", samplingStatusCode=");
		builder.append(samplingStatusCode);
		builder.append(", statusDescription=");
		builder.append(statusDescription);
		builder.append(", processStartTime=");
		builder.append(processStartTime);
		builder.append(", processEndTime=");
		builder.append(processEndTime);
		builder.append(", callLetter=");
		builder.append(callLetter);
		builder.append(", callLetterSuffix=");
		builder.append(callLetterSuffix);
		builder.append(", targetSurveyYearQuarter=");
		builder.append(targetSurveyYearQuarter);
		builder.append(", stepCode=");
		builder.append(stepCode);
		builder.append(", stepDescription=");
		builder.append(stepDescription);
		builder.append(", playCountRanges=");
		builder.append(playCountRanges);
		builder.append(", message=");
		builder.append(message);
		builder.append(", numberOfPerformancesToBeSampled=");
		builder.append(numberOfPerformancesToBeSampled);
		builder.append(", loadStatus=");
		builder.append(loadStatus);
		builder.append(", manMatchIndicator=");
		builder.append(manMatchIndicator);
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


	public String getStatusDescription() {
		return statusDescription;
	}


	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}


	public String getProcessStartTime() {
		return processStartTime;
	}


	public void setProcessStartTime(String processStartTime) {
		this.processStartTime = processStartTime;
	}


	public String getProcessEndTime() {
		return processEndTime;
	}


	public void setProcessEndTime(String processEndTime) {
		this.processEndTime = processEndTime;
	}


	public String getCallLetter() {
		return callLetter;
	}


	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}


	public String getCallLetterSuffix() {
		return callLetterSuffix;
	}


	public void setCallLetterSuffix(String callLetterSuffix) {
		this.callLetterSuffix = callLetterSuffix;
	}


	public String getTargetSurveyYearQuarter() {
		return targetSurveyYearQuarter;
	}


	public void setTargetSurveyYearQuarter(String targetSurveyYearQuarter) {
		this.targetSurveyYearQuarter = targetSurveyYearQuarter;
	}



	public String getStepCode() {
		return stepCode;
	}


	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}


	public String getPlayCountRanges() {
		return playCountRanges;
	}


	public void setPlayCountRanges(String playCountRanges) {
		this.playCountRanges = playCountRanges;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getSamplingStatusCode() {
		return samplingStatusCode;
	}


	public void setSamplingStatusCode(String samplingStatusCode) {
		this.samplingStatusCode = samplingStatusCode;
	}


	public String getStepDescription() {
		return stepDescription;
	}


	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}


	public String getNumberOfPerformancesToBeSampled() {
		return numberOfPerformancesToBeSampled;
	}


	public void setNumberOfPerformancesToBeSampled(
			String numberOfPerformancesToBeSampled) {
		this.numberOfPerformancesToBeSampled = numberOfPerformancesToBeSampled;
	}
	
	
	public String getLoadStatus() {
		return loadStatus;
	}

	public void setLoadStatus(
			String loadStatus) {
		this.loadStatus = loadStatus;
	}
	
	public String getManMatchIndicator() {
		return manMatchIndicator;
	}

	public void setManMatchIndicator(
			String manMatchIndicator) {
		this.manMatchIndicator = manMatchIndicator;
	}
}
