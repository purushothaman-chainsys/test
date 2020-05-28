package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseSearchVOB;

public class EOFileListForm extends BaseSearchVOB{

	private static final long serialVersionUID = 9165766374540217488L;
	
	
	/**
	 * 
	 */
	private String filterSupplierCode;
	private String filterFileStatus;

	private String filterPerformanceQuarter;
	private String filterPerfPeriod;
	private String filterDistributionQuarter;
	private String filterCompletedSupplierCode;
	private String operationType;
	
	private String[] selectedIndex = null;
	private String[] fileIds = null;
	private String[] perfQuarters = null;
	private String[] donotProcessFlags = null;
	private String[] distQuarters = null;
	private String[] perfPeriods = null;
	private String[] supplierTypes = null;
	
	private String perfQuartersAll;
	private String newReviewFiles;
	private String newReviewFilesFiltered;
	private String channelReviewFiles;
	private String rolllupReviewFiles;
	private String eoLoadFiles;
	private String apmLoadFiles;
	private String errorFiles;
	private String noProcessFiles;
	private String successFiles;
	private String successFilesByPeriod;

	private String activeSurveyQuarterApm;
	private String activeSurveyQuarterEo;
	private String currentPerformanceQuarterEO;
	private String currentPerformanceQuarterApm;
	
	private String loadThresholdExceeded;
	private String currentDistQuarterEO;
	
	
	
	
	
	public String getFilterSupplierCode() {
		return filterSupplierCode;
	}
	public void setFilterSupplierCode(String filterSupplierCode) {
		this.filterSupplierCode = filterSupplierCode;
	}
	public String getFilterFileStatus() {
		return filterFileStatus;
	}
	public void setFilterFileStatus(String filterFileStatus) {
		this.filterFileStatus = filterFileStatus;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String[] getFileIds() {
		return fileIds;
	}
	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}
	public String[] getPerfQuarters() {
		return perfQuarters;
	}
	public void setPerfQuarters(String[] perfQuarters) {
		this.perfQuarters = perfQuarters;
	}
	public String[] getSelectedIndex() {
		return selectedIndex;
	}
	public void setSelectedIndex(String[] selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	public String getNewReviewFiles() {
		return newReviewFiles;
	}
	public void setNewReviewFiles(String newReviewFiles) {
		this.newReviewFiles = newReviewFiles;
	}
	public String getChannelReviewFiles() {
		return channelReviewFiles;
	}
	public void setChannelReviewFiles(String channelReviewFiles) {
		this.channelReviewFiles = channelReviewFiles;
	}
	public String getRolllupReviewFiles() {
		return rolllupReviewFiles;
	}
	public void setRolllupReviewFiles(String rolllupReviewFiles) {
		this.rolllupReviewFiles = rolllupReviewFiles;
	}
	public String getEoLoadFiles() {
		return eoLoadFiles;
	}
	public void setEoLoadFiles(String eoLoadFiles) {
		this.eoLoadFiles = eoLoadFiles;
	}
	public String getApmLoadFiles() {
		return apmLoadFiles;
	}
	public void setApmLoadFiles(String apmLoadFiles) {
		this.apmLoadFiles = apmLoadFiles;
	}
	public String getErrorFiles() {
		return errorFiles;
	}
	public void setErrorFiles(String errorFiles) {
		this.errorFiles = errorFiles;
	}
	public String getNoProcessFiles() {
		return noProcessFiles;
	}
	public void setNoProcessFiles(String noProcessFiles) {
		this.noProcessFiles = noProcessFiles;
	}
	public String getSuccessFiles() {
		return successFiles;
	}
	public void setSuccessFiles(String successFiles) {
		this.successFiles = successFiles;
	}
	public String[] getDonotProcessFlags() {
		return donotProcessFlags;
	}
	public void setDonotProcessFlags(String[] donotProcessFlags) {
		this.donotProcessFlags = donotProcessFlags;
	}
	public String getActiveSurveyQuarterApm() {
		return activeSurveyQuarterApm;
	}
	public void setActiveSurveyQuarterApm(String activeSurveyQuarterApm) {
		this.activeSurveyQuarterApm = activeSurveyQuarterApm;
	}
	public String getActiveSurveyQuarterEo() {
		return activeSurveyQuarterEo;
	}
	public void setActiveSurveyQuarterEo(String activeSurveyQuarterEo) {
		this.activeSurveyQuarterEo = activeSurveyQuarterEo;
	}
	public String getCurrentPerformanceQuarterEO() {
		return currentPerformanceQuarterEO;
	}
	public void setCurrentPerformanceQuarterEO(String currentPerformanceQuarterEO) {
		this.currentPerformanceQuarterEO = currentPerformanceQuarterEO;
	}
	public String getCurrentPerformanceQuarterApm() {
		return currentPerformanceQuarterApm;
	}
	public void setCurrentPerformanceQuarterApm(String currentPerformanceQuarterApm) {
		this.currentPerformanceQuarterApm = currentPerformanceQuarterApm;
	}
	public String getPerfQuartersAll() {
		return perfQuartersAll;
	}
	public void setPerfQuartersAll(String perfQuartersAll) {
		this.perfQuartersAll = perfQuartersAll;
	}
	public String getNewReviewFilesFiltered() {
		return newReviewFilesFiltered;
	}
	public void setNewReviewFilesFiltered(String newReviewFilesFiltered) {
		this.newReviewFilesFiltered = newReviewFilesFiltered;
	}
	public String getLoadThresholdExceeded() {
		return loadThresholdExceeded;
	}
	public void setLoadThresholdExceeded(String loadThresholdExceeded) {
		this.loadThresholdExceeded = loadThresholdExceeded;
	}
	public String[] getDistQuarters() {
		return distQuarters;
	}
	public void setDistQuarters(String[] distQuarters) {
		this.distQuarters = distQuarters;
	}
	public String getFilterPerformanceQuarter() {
		return filterPerformanceQuarter;
	}
	public void setFilterPerformanceQuarter(String filterPerformanceQuarter) {
		this.filterPerformanceQuarter = filterPerformanceQuarter;
	}
	public String getFilterDistributionQuarter() {
		return filterDistributionQuarter;
	}
	public void setFilterDistributionQuarter(String filterDistributionQuarter) {
		this.filterDistributionQuarter = filterDistributionQuarter;
	}
	public String getSuccessFilesByPeriod() {
		return successFilesByPeriod;
	}
	public void setSuccessFilesByPeriod(String successFilesByPeriod) {
		this.successFilesByPeriod = successFilesByPeriod;
	}
	public String getFilterCompletedSupplierCode() {
		return filterCompletedSupplierCode;
	}
	public void setFilterCompletedSupplierCode(String filterCompletedSupplierCode) {
		this.filterCompletedSupplierCode = filterCompletedSupplierCode;
	}
	public String getCurrentDistQuarterEO() {
		return currentDistQuarterEO;
	}
	public void setCurrentDistQuarterEO(String currentDistQuarterEO) {
		this.currentDistQuarterEO = currentDistQuarterEO;
	}
	public String[] getPerfPeriods() {
		return perfPeriods;
	}
	public void setPerfPeriods(String[] perfPeriods) {
		this.perfPeriods = perfPeriods;
	}
	public String[] getSupplierTypes() {
		return supplierTypes;
	}
	public void setSupplierTypes(String[] supplierTypes) {
		this.supplierTypes = supplierTypes;
	}
	public String getFilterPerfPeriod() {
		return filterPerfPeriod;
	}
	public void setFilterPerfPeriod(String filterPerfPeriod) {
		this.filterPerfPeriod = filterPerfPeriod;
	}

}
