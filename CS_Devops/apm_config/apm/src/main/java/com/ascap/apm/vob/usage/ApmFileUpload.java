package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;
import java.io.File;

public class ApmFileUpload extends BaseVOB {

	private static final long serialVersionUID = -2200015875338685521L;
	private File radioFile;
	String fileName;
	String userId = null;
	String fileStatus;
	String fileType;
	String uploadDate;
	String targetYearQuarter;
	String fileId = null;
	String filePath = null;
	String fileOrigPath = null;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ fileName=");
		builder.append(fileName);
		builder.append("[ userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

	public String getTargetYearQuarter() {
		return targetYearQuarter;
	}

	public void setTargetYearQuarter(String targetYearQuarter) {
		this.targetYearQuarter = targetYearQuarter;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileOrigPath() {
		return fileOrigPath;
	}

	public void setFileOrigPath(String fileOrigPath) {
		this.fileOrigPath = fileOrigPath;
	}

	public File getRadioFile() {
		return radioFile;
	}

	public void setRadioFile(File radioFile) {
		this.radioFile = radioFile;
	}

}
