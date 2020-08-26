package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class SamplingResult extends BaseVOB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2317059994064266189L;
	
	
	private String playCountRangeId;
	private String callLetter;
	private String callLetterSuffix;
	private String targetSurveyYearQuarter;
	private String rangeStart;
	private String rangeEnd;
	private String totalMatchedPerfCount;
	private String totalMatchedPlayCount;
	private String totalUnMatchedPerfCount;
	private String totalUnMatchedPlayCount;
	private String totalUnMatchedForeignPerfCount;
	private String totalUnMatchedForeignPlayCount;
	private String includeForeignAffFlag;
	private String selectPercent;
	private String selectCount;
	private String selectStart;
	private String selectSkip;
	private String censusSampleIndicator;
	private String mergeIndicator;
	private String processedFlag;
	
	
	
	
	
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SamplingResult [playCountRangeId=");
		builder.append(playCountRangeId);
		builder.append(", callLetter=");
		builder.append(callLetter);
		builder.append(", callLetterSuffix=");
		builder.append(callLetterSuffix);
		builder.append(", targetSurveyYearQuarter=");
		builder.append(targetSurveyYearQuarter);
		builder.append(", rangeStart=");
		builder.append(rangeStart);
		builder.append(", rangeEnd=");
		builder.append(rangeEnd);
		builder.append(", totalMatchedPerfCount=");
		builder.append(totalMatchedPerfCount);
		builder.append(", totalMatchedPlayCount=");
		builder.append(totalMatchedPlayCount);
		builder.append(", totalUnMatchedPerfCount=");
		builder.append(totalUnMatchedPerfCount);
		builder.append(", totalUnMatchedPlayCount=");
		builder.append(totalUnMatchedPlayCount);
		builder.append(", totalUnMatchedForeignPerfCount=");
		builder.append(totalUnMatchedForeignPerfCount);
		builder.append(", totalUnMatchedForeignPlayCount=");
		builder.append(totalUnMatchedForeignPlayCount);
		builder.append(", includeForeignAffFlag=");
		builder.append(includeForeignAffFlag);
		builder.append(", selectPercent=");
		builder.append(selectPercent);
		builder.append(", selectCount=");
		builder.append(selectCount);
		builder.append(", selectStart=");
		builder.append(selectStart);
		builder.append(", selectSkip=");
		builder.append(selectSkip);
		builder.append(", censusSampleIndicator=");
		builder.append(censusSampleIndicator);
		builder.append(", mergeIndicator=");
		builder.append(mergeIndicator);
		builder.append(", processedFlag=");
		builder.append(processedFlag);
		builder.append("]");
		return builder.toString();
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








	public String getRangeStart() {
		return rangeStart;
	}








	public void setRangeStart(String rangeStart) {
		this.rangeStart = rangeStart;
	}








	public String getRangeEnd() {
		return rangeEnd;
	}








	public void setRangeEnd(String rangeEnd) {
		this.rangeEnd = rangeEnd;
	}








	public String getTotalMatchedPerfCount() {
		return totalMatchedPerfCount;
	}








	public void setTotalMatchedPerfCount(String totalMatchedPerfCount) {
		this.totalMatchedPerfCount = totalMatchedPerfCount;
	}








	public String getTotalMatchedPlayCount() {
		return totalMatchedPlayCount;
	}








	public void setTotalMatchedPlayCount(String totalMatchedPlayCount) {
		this.totalMatchedPlayCount = totalMatchedPlayCount;
	}








	public String getTotalUnMatchedPerfCount() {
		return totalUnMatchedPerfCount;
	}








	public void setTotalUnMatchedPerfCount(String totalUnMatchedPerfCount) {
		this.totalUnMatchedPerfCount = totalUnMatchedPerfCount;
	}








	public String getTotalUnMatchedPlayCount() {
		return totalUnMatchedPlayCount;
	}








	public void setTotalUnMatchedPlayCount(String totalUnMatchedPlayCount) {
		this.totalUnMatchedPlayCount = totalUnMatchedPlayCount;
	}








	public String getTotalUnMatchedForeignPerfCount() {
		return totalUnMatchedForeignPerfCount;
	}








	public void setTotalUnMatchedForeignPerfCount(
			String totalUnMatchedForeignPerfCount) {
		this.totalUnMatchedForeignPerfCount = totalUnMatchedForeignPerfCount;
	}








	public String getTotalUnMatchedForeignPlayCount() {
		return totalUnMatchedForeignPlayCount;
	}








	public void setTotalUnMatchedForeignPlayCount(
			String totalUnMatchedForeignPlayCount) {
		this.totalUnMatchedForeignPlayCount = totalUnMatchedForeignPlayCount;
	}








	public String getIncludeForeignAffFlag() {
		return includeForeignAffFlag;
	}








	public void setIncludeForeignAffFlag(String includeForeignAffFlag) {
		this.includeForeignAffFlag = includeForeignAffFlag;
	}








	public String getSelectPercent() {
		return selectPercent;
	}








	public void setSelectPercent(String selectPercent) {
		this.selectPercent = selectPercent;
	}








	public String getSelectCount() {
		return selectCount;
	}








	public void setSelectCount(String selectCount) {
		this.selectCount = selectCount;
	}








	public String getSelectStart() {
		return selectStart;
	}








	public void setSelectStart(String selectStart) {
		this.selectStart = selectStart;
	}








	public String getSelectSkip() {
		return selectSkip;
	}








	public void setSelectSkip(String selectSkip) {
		this.selectSkip = selectSkip;
	}








	public String getCensusSampleIndicator() {
		return censusSampleIndicator;
	}








	public void setCensusSampleIndicator(String censusSampleIndicator) {
		this.censusSampleIndicator = censusSampleIndicator;
	}








	public String getMergeIndicator() {
		return mergeIndicator;
	}








	public void setMergeIndicator(String mergeIndicator) {
		this.mergeIndicator = mergeIndicator;
	}








	public String getProcessedFlag() {
		return processedFlag;
	}








	public void setProcessedFlag(String processedFlag) {
		this.processedFlag = processedFlag;
	}








	public String getPlayCountRangeId() {
		return playCountRangeId;
	}








	public void setPlayCountRangeId(String playCountRangeId) {
		this.playCountRangeId = playCountRangeId;
	}

}
