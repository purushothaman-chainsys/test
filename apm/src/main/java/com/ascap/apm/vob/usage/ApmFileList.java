package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseSearchVOB;

public class ApmFileList extends BaseSearchVOB {

	private static final long serialVersionUID = 1L;
	private String fileType;
	private String fileStatus;
	private String supplierCode;
	private String[] fileId;
	private String[] targetYearQuarter;
	private String[] selectedIndex;
	private String operationType;

	public String[] getTargetYearQuarter() {
		return targetYearQuarter;
	}

	public void setTargetYearQuarter(String[] targetYearQuarter) {
		this.targetYearQuarter = targetYearQuarter;
	}

	public String[] getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(String[] selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String[] getFileId() {
		return fileId;
	}

	public void setFileId(String[] fileId) {
		this.fileId = fileId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

}
