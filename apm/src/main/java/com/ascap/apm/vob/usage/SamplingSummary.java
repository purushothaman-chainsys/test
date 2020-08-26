package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseSearchVOB;

public class SamplingSummary extends BaseSearchVOB {


	private static final long serialVersionUID = 6408237608809015279L;
	private String targetSurveyYearQuarter;
	private String hasActiveSamplingProcess;
	private String hasForeignAffiliationFlag;
	private String callLetter;
	private String stepNumber;
	private String numberOfPerformancesToBeSampled;
	
	private String operationType;
	private String[] playCountRanges;
	private String playCountRange;
	private String samplingRequestId;
	
	private String[] playCountRangeId;
	private String[] mergeIndicator;
	private String[] censusSampleIndicator;
	private String[] includeForeignAffFlag;
	
	private String[] selectStart;
	private String[] selectSkip;
	private String[] selectPercent;
	private String[] selectCount;
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SamplingSummary [targetSurveyYearQuarter=");
		builder.append(targetSurveyYearQuarter);
		builder.append(", hasActiveSamplingProcess=");
		builder.append(hasActiveSamplingProcess);
		builder.append(", hasForeignAffiliationFlag=");
		builder.append(hasForeignAffiliationFlag);
		builder.append(", callLetter=");
		builder.append(callLetter);
		builder.append(", stepNumber=");
		builder.append(stepNumber);
		builder.append(", numberOfPerformancesToBeSampled=");
		builder.append(numberOfPerformancesToBeSampled);
		builder.append("]");
		return builder.toString();
	}

	public String getTargetSurveyYearQuarter() {
		return targetSurveyYearQuarter;
	}

	public void setTargetSurveyYearQuarter(String targetSurveyYearQuarter) {
		this.targetSurveyYearQuarter = targetSurveyYearQuarter;
	}

	public String getHasActiveSamplingProcess() {
		return hasActiveSamplingProcess;
	}

	public void setHasActiveSamplingProcess(String hasActiveSamplingProcess) {
		this.hasActiveSamplingProcess = hasActiveSamplingProcess;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public String getNumberOfPerformancesToBeSampled() {
		return numberOfPerformancesToBeSampled;
	}

	public void setNumberOfPerformancesToBeSampled(
			String numberOfPerformancesToBeSampled) {
		this.numberOfPerformancesToBeSampled = numberOfPerformancesToBeSampled;
	}

	public String getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(String stepNumber) {
		this.stepNumber = stepNumber;
	}

	public String getHasForeignAffiliationFlag() {
		return hasForeignAffiliationFlag;
	}

	public void setHasForeignAffiliationFlag(String hasForeignAffiliationFlag) {
		this.hasForeignAffiliationFlag = hasForeignAffiliationFlag;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String[] getPlayCountRanges() {
		return playCountRanges;
	}

	public void setPlayCountRanges(String[] playCountRanges) {
		this.playCountRanges = playCountRanges;
	}

	public String getPlayCountRange() {
		return playCountRange;
	}

	public void setPlayCountRange(String playCountRange) {
		this.playCountRange = playCountRange;
	}

	public String getSamplingRequestId() {
		return samplingRequestId;
	}

	public void setSamplingRequestId(String samplingRequestId) {
		this.samplingRequestId = samplingRequestId;
	}

	public String[] getPlayCountRangeId() {
		return playCountRangeId;
	}

	public void setPlayCountRangeId(String[] playCountRangeId) {
		this.playCountRangeId = playCountRangeId;
	}

	public String[] getMergeIndicator() {
		return mergeIndicator;
	}

	public void setMergeIndicator(String[] mergeIndicator) {
		this.mergeIndicator = mergeIndicator;
	}

	public String[] getCensusSampleIndicator() {
		return censusSampleIndicator;
	}

	public void setCensusSampleIndicator(String[] censusSampleIndicator) {
		this.censusSampleIndicator = censusSampleIndicator;
	}

	public String[] getIncludeForeignAffFlag() {
		return includeForeignAffFlag;
	}

	public void setIncludeForeignAffFlag(String[] includeForeignAffFlag) {
		this.includeForeignAffFlag = includeForeignAffFlag;
	}

	public String[] getSelectStart() {
		return selectStart;
	}

	public void setSelectStart(String[] selectStart) {
		this.selectStart = selectStart;
	}

	public String[] getSelectSkip() {
		return selectSkip;
	}

	public void setSelectSkip(String[] selectSkip) {
		this.selectSkip = selectSkip;
	}

	public String[] getSelectPercent() {
		return selectPercent;
	}

	public void setSelectPercent(String[] selectPercent) {
		this.selectPercent = selectPercent;
	}

	public String[] getSelectCount() {
		return selectCount;
	}

	public void setSelectCount(String[] selectCount) {
		this.selectCount = selectCount;
	}
	
	
	
}
