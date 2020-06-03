package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class ApmFile extends BaseVOB {

	private static final long serialVersionUID = 1L;
	private String fileId;
	private String fileType;
	private String fileName;
	private String originalFileName;
	private String processDate;
	private String supplierId;
	private String headerCount;
	private String detailCount;
	private String fileStatus;
	private String supplierCode;
	private String targetYearQuarter;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApmFile [fileId=");
		builder.append(fileId);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", originalFileName=");
		builder.append(originalFileName);
		builder.append(", processDate=");
		builder.append(processDate);
		builder.append(", supplierId=");
		builder.append(supplierId);
		builder.append(", headerCount=");
		builder.append(headerCount);
		builder.append(", detailCount=");
		builder.append(detailCount);
		builder.append(", fileStatus=");
		builder.append(fileStatus);
		builder.append(", supplierCode=");
		builder.append(supplierCode);
		builder.append(", targetYearQuarter=");
		builder.append(targetYearQuarter);
		builder.append("]");
		return builder.toString();
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getHeaderCount() {
		return headerCount;
	}

	public void setHeaderCount(String headerCount) {
		this.headerCount = headerCount;
	}

	public String getDetailCount() {
		return detailCount;
	}

	public void setDetailCount(String detailCount) {
		this.detailCount = detailCount;
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

	public String getTargetYearQuarter() {
		return targetYearQuarter;
	}

	public void setTargetYearQuarter(String targetYearQuarter) {
		this.targetYearQuarter = targetYearQuarter;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
