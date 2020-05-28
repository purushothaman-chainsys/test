package com.ascap.apm.vob.usage;

import java.text.DecimalFormat;

import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.vob.BaseVOB;

public class EOFile extends BaseVOB {

    private static final long serialVersionUID = -7824779078077161713L;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    private String fileId = null;
    private String fileName = null;
    private String origFileName = null;
    private String zipFileName = null;
    private String fileFrom = null;
    private String fileTo = null;
    private String emailFrom = null;
    private String emailTo = null;
    private String emailDate = null;
    private String emailSubject = null;
    private String emailBody = null;
    private String fileTransferMethod = null;
    private String updateDate = null;
    private String createDate = null;
    private String statusCode = null;
    private String eoLoadFlg = null;
    private String apmLoadFlg = null;
    private String perfQuarter = null;
    private String distQuarter = null;
    private String SuppCode = null;
    private String apmSuppCode = null;
    private String loadStartTime = null;
    private String loadEndTime = null;
    private String fileRejectCount = null;
    private String fileRejectCountFormatted = null;
    private String fileRowCount = null;
    private String fileRowCountFormatted = null;
    private String isdEOLoadFlg = null;
    private String fileNumRejectCount = null;
    private String eoSuppFormatFlag = null;	
    private String apmSuppFormatFlag = null;
    private String samplingCompletedFlag = null;
    private String batchId = null;
    private String rollupRowCount = null;
    private String rollupRowCountFormatted = null;
    private String playCount = null;
    private String playCountFormatted = null;
    private String donotProcessFlag = null;


    private String errorCorrectedStatus = null;
    private String fileRejectCountNew = null;
    private String fileVldRejectcount = null;

    private String eoBatchID = null;
    private String lastRollupCount = null;
    private String perfPeriod = null;
    private String supplierType = null;
    private String frequency = null;

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
    public String getOrigFileName() {
        return origFileName;
    }
    public void setOrigFileName(String origFileName) {
        this.origFileName = origFileName;
    }
    public String getZipFileName() {
        return zipFileName;
    }
    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }
    public String getFileFrom() {
        return fileFrom;
    }
    public void setFileFrom(String fileFrom) {
        this.fileFrom = fileFrom;
    }
    public String getFileTo() {
        return fileTo;
    }
    public void setFileTo(String fileTo) {
        this.fileTo = fileTo;
    }
    public String getEmailFrom() {
        return emailFrom;
    }
    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }
    public String getEmailTo() {
        return emailTo;
    }
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }
    public String getEmailDate() {
        return emailDate;
    }
    public void setEmailDate(String emailDate) {
        this.emailDate = emailDate;
    }
    public String getEmailSubject() {
        return emailSubject;
    }
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }
    public String getEmailBody() {
        return emailBody;
    }
    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
    public String getFileTransferMethod() {
        return fileTransferMethod;
    }
    public void setFileTransferMethod(String fileTransferMethod) {
        this.fileTransferMethod = fileTransferMethod;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    public String getEoLoadFlg() {
        return eoLoadFlg;
    }
    public void setEoLoadFlg(String eoLoadFlg) {
        this.eoLoadFlg = eoLoadFlg;
    }
    public String getApmLoadFlg() {
        return apmLoadFlg;
    }
    public void setApmLoadFlg(String apmLoadFlg) {
        this.apmLoadFlg = apmLoadFlg;
    }
    public String getPerfQuarter() {
        return perfQuarter;
    }
    public void setPerfQuarter(String perfQuarter) {
        this.perfQuarter = perfQuarter;
    }
    public String getSuppCode() {
        return SuppCode;
    }
    public void setSuppCode(String suppCode) {
        SuppCode = suppCode;
    }
    public String getLoadStartTime() {
        return loadStartTime;
    }
    public void setLoadStartTime(String loadStartTime) {
        this.loadStartTime = loadStartTime;
    }
    public String getLoadEndTime() {
        return loadEndTime;
    }
    public void setLoadEndTime(String loadEndTime) {
        this.loadEndTime = loadEndTime;
    }
    public String getFileRejectCount() {
        return fileRejectCount;
    }
    public void setFileRejectCount(String fileRejectCount) {
        this.fileRejectCount = fileRejectCount;
        if(!ValidationUtils.isEmptyOrNull(fileRejectCount)) {
            String formattedValue = "";
            try {
                formattedValue = decimalFormat.format(Long.parseLong(fileRejectCount));
            }
            catch(Exception ignored) {this.setFileRejectCountFormatted(fileRejectCount); }
            this.setFileRejectCountFormatted(formattedValue);
        }
    }
    public String getFileRowCount() {
        return fileRowCount;
    }
    public void setFileRowCount(String fileRowCount) {
        this.fileRowCount = fileRowCount;
        if(!ValidationUtils.isEmptyOrNull(fileRowCount)) {
            String formattedValue = "";
            try {
                formattedValue = decimalFormat.format(Long.parseLong(fileRowCount));
            }
            catch(Exception ignored) {this.setFileRejectCountFormatted(fileRowCount); }
            this.setFileRowCountFormatted(formattedValue);
        }
    }
    public String getIsdEOLoadFlg() {
        return isdEOLoadFlg;
    }
    public void setIsdEOLoadFlg(String isdEOLoadFlg) {
        this.isdEOLoadFlg = isdEOLoadFlg;
    }
    public String getFileNumRejectCount() {
        return fileNumRejectCount;
    }
    public void setFileNumRejectCount(String fileNumRejectCount) {
        this.fileNumRejectCount = fileNumRejectCount;
    }
    public String getEoSuppFormatFlag() {
        return eoSuppFormatFlag;
    }
    public void setEoSuppFormatFlag(String eoSuppFormatFlag) {
        this.eoSuppFormatFlag = eoSuppFormatFlag;
    }
    public String getApmSuppFormatFlag() {
        return apmSuppFormatFlag;
    }
    public void setApmSuppFormatFlag(String apmSuppFormatFlag) {
        this.apmSuppFormatFlag = apmSuppFormatFlag;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EOFile [fileId=");
        builder.append(fileId);
        builder.append(", fileName=");
        builder.append(fileName);
        builder.append(", origFileName=");
        builder.append(origFileName);
        builder.append(", zipFileName=");
        builder.append(zipFileName);
        builder.append(", fileFrom=");
        builder.append(fileFrom);
        builder.append(", fileTo=");
        builder.append(fileTo);
        builder.append(", emailFrom=");
        builder.append(emailFrom);
        builder.append(", emailTo=");
        builder.append(emailTo);
        builder.append(", emailDate=");
        builder.append(emailDate);
        builder.append(", emailSubject=");
        builder.append(emailSubject);
        builder.append(", emailBody=");
        builder.append(emailBody);
        builder.append(", fileTransferMethod=");
        builder.append(fileTransferMethod);
        builder.append(", updateDate=");
        builder.append(updateDate);
        builder.append(", createDate=");
        builder.append(createDate);
        builder.append(", statusCode=");
        builder.append(statusCode);
        builder.append(", eoLoadFlg=");
        builder.append(eoLoadFlg);
        builder.append(", apmLoadFlg=");
        builder.append(apmLoadFlg);
        builder.append(", perfQuarter=");
        builder.append(perfQuarter); 
        builder.append(", perfPeriod=");
        builder.append(perfPeriod); 
        builder.append(", distQuarter=");
        builder.append(distQuarter);
        builder.append(", SuppCode=");
        builder.append(SuppCode);
        builder.append(", apmSuppCode=");
        builder.append(apmSuppCode);
        builder.append(", loadStartTime=");
        builder.append(loadStartTime);
        builder.append(", loadEndTime=");
        builder.append(loadEndTime);
        builder.append(", fileRejectCount=");
        builder.append(fileRejectCount);
        builder.append(", fileRejectCountFormatted=");
        builder.append(fileRejectCountFormatted);
        builder.append(", fileRowCount=");
        builder.append(fileRowCount);
        builder.append(", fileRowCountFormatted=");
        builder.append(fileRowCountFormatted);
        builder.append(", isdEOLoadFlg=");
        builder.append(isdEOLoadFlg);
        builder.append(", fileNumRejectCount=");
        builder.append(fileNumRejectCount);
        builder.append(", eoSuppFormatFlag=");
        builder.append(eoSuppFormatFlag);
        builder.append(", apmSuppFormatFlag=");
        builder.append(apmSuppFormatFlag);
        builder.append(", samplingCompletedFlag=");
        builder.append(samplingCompletedFlag);
        builder.append(", batchId=");
        builder.append(batchId);
        builder.append(", rollupRowCount=");
        builder.append(rollupRowCount);
        builder.append(", rollupRowCountFormatted=");
        builder.append(rollupRowCountFormatted);
        builder.append(", playCount=");
        builder.append(playCount);
        builder.append(", playCountFormatted=");
        builder.append(playCountFormatted);
        builder.append(", donotProcessFlag=");
        builder.append(donotProcessFlag);
        builder.append(", errorCorrectedStatus=");
        builder.append(errorCorrectedStatus);
        builder.append(", fileRejectCountNew=");
        builder.append(fileRejectCountNew);
        builder.append(", fileVldRejectcount=");
        builder.append(fileVldRejectcount);
        builder.append(", eoBatchID=");
        builder.append(eoBatchID);
        builder.append(", lastRollupCount=");
        builder.append(lastRollupCount);
        builder.append("]");
        return builder.toString();
    }
    public String getBatchId() {
        return batchId;
    }
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    public String getRollupRowCount() {
        return rollupRowCount;
    }
    public void setRollupRowCount(String rollupRowCount) {
        this.rollupRowCount = rollupRowCount;
        if(!ValidationUtils.isEmptyOrNull(rollupRowCount)) {
            String formattedValue = "";
            try {
                formattedValue = decimalFormat.format(Long.parseLong(rollupRowCount));
            }
            catch(Exception ignored) {this.setRollupRowCountFormatted(rollupRowCount); }
            this.setRollupRowCountFormatted(formattedValue);
        }
    }
    public String getPlayCount() {
        return playCount;
    }
    public void setPlayCount(String playCount) {
        this.playCount = playCount;
        if(!ValidationUtils.isEmptyOrNull(playCount)) {
            String formattedValue = "";
            try {
                formattedValue = decimalFormat.format(Long.parseLong(playCount));
            }
            catch(Exception ignored) {this.setPlayCountFormatted(playCount); }
            this.setPlayCountFormatted(formattedValue);
        }

    }
    public String getDonotProcessFlag() {
        return donotProcessFlag;
    }
    public void setDonotProcessFlag(String donotProcessFlag) {
        this.donotProcessFlag = donotProcessFlag;
    }
    public String getApmSuppCode() {
        return apmSuppCode;
    }
    public void setApmSuppCode(String apmSuppCode) {
        this.apmSuppCode = apmSuppCode;
    }
    public String getSamplingCompletedFlag() {
        return samplingCompletedFlag;
    }
    public void setSamplingCompletedFlag(String samplingCompletedFlag) {
        this.samplingCompletedFlag = samplingCompletedFlag;
    }
    public String getFileRejectCountFormatted() {
        return fileRejectCountFormatted;
    }
    public void setFileRejectCountFormatted(String fileRejectCountFormatted) {
        this.fileRejectCountFormatted = fileRejectCountFormatted;
    }
    public String getFileRowCountFormatted() {
        return fileRowCountFormatted;
    }
    public void setFileRowCountFormatted(String fileRowCountFormatted) {
        this.fileRowCountFormatted = fileRowCountFormatted;
    }
    public String getRollupRowCountFormatted() {
        return rollupRowCountFormatted;
    }
    public void setRollupRowCountFormatted(String rollupRowCountFormatted) {
        this.rollupRowCountFormatted = rollupRowCountFormatted;
    }
    public String getPlayCountFormatted() {
        return playCountFormatted;
    }
    public void setPlayCountFormatted(String playCountFormatted) {
        this.playCountFormatted = playCountFormatted;
    }
    public String getErrorCorrectedStatus() {
        return errorCorrectedStatus;
    }
    public void setErrorCorrectedStatus(String errorCorrectedStatus) {
        this.errorCorrectedStatus = errorCorrectedStatus;
    }
    public String getFileRejectCountNew() {
        return fileRejectCountNew;
    }
    public void setFileRejectCountNew(String fileRejectCountNew) {
        this.fileRejectCountNew = fileRejectCountNew;

        if(!ValidationUtils.isEmptyOrNull(fileRejectCountNew)) {
            String formattedValue = "";
            try {
                formattedValue = decimalFormat.format(Long.parseLong(fileRejectCountNew));
                this.fileRejectCountNew = formattedValue;
            }
            catch(Exception ignored) {this.fileRejectCountNew = fileRejectCountNew;} 
        }

    }



    public String getFileVldRejectcount() {
        return fileVldRejectcount;
    }
    public void setFileVldRejectcount(String fileVldRejectcount) {
        this.fileVldRejectcount = fileVldRejectcount;

        if(!ValidationUtils.isEmptyOrNull(fileVldRejectcount)) {
            String formattedValue = "";
            try {
                formattedValue = decimalFormat.format(Long.parseLong(fileVldRejectcount));
                this.fileVldRejectcount = formattedValue;
            }
            catch(Exception ignored) {this.fileVldRejectcount = fileVldRejectcount;} 
        }

    }
    public String getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
    public String getEoBatchID() {
        return eoBatchID;
    }
    public void setEoBatchID(String eoBatchID) {
        this.eoBatchID = eoBatchID;
    }
    public String getLastRollupCount() {
        return lastRollupCount;
    }
    public void setLastRollupCount(String lastRollupCount) {
        this.lastRollupCount = lastRollupCount;
    }
    public String getDistQuarter() {
        return distQuarter;
    }
    public void setDistQuarter(String distQuarter) {
        this.distQuarter = distQuarter;
    }
    public String getPerfPeriod() {
        return perfPeriod;
    }
    public void setPerfPeriod(String perfPeriod) {
        this.perfPeriod = perfPeriod;
    }
    public String getSupplierType() {
        return supplierType;
    }
    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }
    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }



}
