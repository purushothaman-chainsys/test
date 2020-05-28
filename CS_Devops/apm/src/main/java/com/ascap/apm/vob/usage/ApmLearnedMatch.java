package com.ascap.apm.vob.usage;

import java.util.ArrayList;
import java.util.List;

import com.ascap.apm.vob.BaseVOB;

public class ApmLearnedMatch extends BaseVOB {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String requestId;

    private String workId;

    private String cloneCount;

    private String originalWorkId;

    private String workTitle;

    private String supplierCode;

    private String performerName;

    private String apmMatchType;

    private String learnedDeleteFlag;

    private String learnedMatchType;

    private String learnType;

    private String deleteFlag;

    private String invalidWorkId;

    private String multWorkId;

    private String writerName;

    private String lmType;

    List<Object> workIdErrors;

    private List<String> workIdCollection = new ArrayList<>();

    private List<String> cloneCountsCollection = new ArrayList<>();

    private List<String> workCountCollection = new ArrayList<>();

    private String[] workIds;

    private String[] medleyCloneCounts;

    private String operationType;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ApmLearnedMatch [requestId=");
        builder.append(requestId);
        builder.append(", workId=");
        builder.append(workId);
        builder.append(", cloneCount=");
        builder.append(cloneCount);
        builder.append(", originalWorkId=");
        builder.append(originalWorkId);
        builder.append(", workTitle=");
        builder.append(workTitle);
        builder.append(", supplierCode=");
        builder.append(supplierCode);
        builder.append(", performerName=");
        builder.append(performerName);
        builder.append(", apmMatchType=");
        builder.append(apmMatchType);
        builder.append(", learnedDeleteFlag=");
        builder.append(learnedDeleteFlag);
        builder.append(", learnedMatchType=");
        builder.append(learnedMatchType);
        builder.append(", learnType=");
        builder.append(learnType);
        builder.append(", deleteFlag=");
        builder.append(deleteFlag);
        builder.append(", invalidWorkId=");
        builder.append(invalidWorkId);
        builder.append(", multWorkId=");
        builder.append(multWorkId);
        builder.append(", writerName=");
        builder.append(writerName);
        builder.append(", lmType=");
        builder.append(lmType);
        builder.append("]");
        return builder.toString();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getOriginalWorkId() {
        return originalWorkId;
    }

    public void setOriginalWorkId(String originalWorkId) {
        this.originalWorkId = originalWorkId;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public String getApmMatchType() {
        return apmMatchType;
    }

    public void setApmMatchType(String apmMatchType) {
        this.apmMatchType = apmMatchType;
    }

    public String getLearnedDeleteFlag() {
        return learnedDeleteFlag;
    }

    public void setLearnedDeleteFlag(String learnedDeleteFlag) {
        this.learnedDeleteFlag = learnedDeleteFlag;
    }

    public String getLearnType() {
        return learnType;
    }

    public void setLearnType(String learnType) {
        this.learnType = learnType;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public List<Object> getWorkIdErrors() {
        return workIdErrors;
    }

    public void setWorkIdErrors(List<Object> errorColl) {
        this.workIdErrors = errorColl;
    }

    public String getInvalidWorkId() {
        return invalidWorkId;
    }

    public void setInvalidWorkId(String invalidWorkId) {
        this.invalidWorkId = invalidWorkId;
    }

    public String getMultWorkId() {
        return multWorkId;
    }

    public void setMultWorkId(String multWorkId) {
        this.multWorkId = multWorkId;
    }

    public String[] getWorkIds() {
        return workIds;
    }

    public void setWorkIds(String[] workIds) {
        this.workIds = workIds;
    }

    public List<String> getWorkIdCollection() {
        return workIdCollection;
    }

    public void setWorkIdCollection(List<String> workIdCollection) {
        this.workIdCollection = workIdCollection;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public List<String> getCloneCountsCollection() {
        return cloneCountsCollection;
    }

    public void setCloneCountsCollection(List<String> cloneCountsCollection) {
        this.cloneCountsCollection = cloneCountsCollection;
    }

    public String[] getMedleyCloneCounts() {
        return medleyCloneCounts;
    }

    public void setMedleyCloneCounts(String[] medleyCloneCounts) {
        this.medleyCloneCounts = medleyCloneCounts;
    }

    public String getCloneCount() {
        return cloneCount;
    }

    public void setCloneCount(String cloneCount) {
        this.cloneCount = cloneCount;
    }

    public String getLearnedMatchType() {
        return learnedMatchType;
    }

    public void setLearnedMatchType(String learnedMatchType) {
        this.learnedMatchType = learnedMatchType;
    }

    public String getLmType() {
        return lmType;
    }

    public void setLmType(String lmType) {
        this.lmType = lmType;
    }

    public List<String> getWorkCountCollection() {
        return workCountCollection;
    }

    public void setWorkCountCollection(List<String> workCountCollection) {
        this.workCountCollection = workCountCollection;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

}
