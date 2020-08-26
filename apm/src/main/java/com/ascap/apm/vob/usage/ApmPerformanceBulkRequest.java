package com.ascap.apm.vob.usage;

import java.util.List;

import com.ascap.apm.vob.BaseVOB;

public class ApmPerformanceBulkRequest extends BaseVOB {

    /**
     * 
     */
    private static final long serialVersionUID = 7101624741081708275L;

    private String requestId;

    private String requestGroupId;

    private String workId;

    private String newWorkId;

    private String originalWorkId;

    private String workTitle;

    private String supplierCode;

    private String providerId;

    private String performerName;

    private String writerName;

    private String playCount;

    private String workPerfCount;

    private String statusCode;

    private String manualMatchUserId;

    private String deleteFlag;

    private String estimatedDollarAmount;

    private String estimatedDollarFlag;

    private List<Object> workIdErrors;

    private String requestTypeCode;

    private String multWorkId;

    private String assignedToUser;

    private String invalidWorkId = "N";

    private String priority;
   private String workTestimatedDollarAmountitle;
   
   

    
public String getWorkTestimatedDollarAmountitle() {
    return workTestimatedDollarAmountitle;
}


public void setWorkTestimatedDollarAmountitle(String workTestimatedDollarAmountitle) {
    this.workTestimatedDollarAmountitle = workTestimatedDollarAmountitle;
}

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestGroupId() {
        return requestGroupId;
    }

    public void setRequestGroupId(String requestGroupId) {
        this.requestGroupId = requestGroupId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
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

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getWorkPerfCount() {
        return workPerfCount;
    }

    public void setWorkPerfCount(String workPerfCount) {
        this.workPerfCount = workPerfCount;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "ApmPerformanceBulkRequest [requestId=" + requestId + ", requestGroupId=" + requestGroupId + ", workId="
            + workId + ", newWorkId=" + newWorkId + ", originalWorkId=" + originalWorkId + ", workTitle=" + workTitle
            + ", supplierCode=" + supplierCode + ", providerId=" + providerId + ", performerName=" + performerName
            + ", writerName=" + writerName + ", playCount=" + playCount + ", workPerfCount=" + workPerfCount
            + ", statusCode=" + statusCode + ", manualMatchUserId=" + manualMatchUserId + ", deleteFlag=" + deleteFlag
            + ", estimatedDollarAmount=" + estimatedDollarAmount + ", estimatedDollarFlag=" + estimatedDollarFlag
            + ", workIdErrors=" + workIdErrors + ", requestTypeCode=" + requestTypeCode + ", multWorkId=" + multWorkId
            + ", assignedToUser=" + assignedToUser + ", invalidWorkId=" + invalidWorkId + ", priority=" + priority
            + "]";
    }

    public String getOriginalWorkId() {
        return originalWorkId;
    }

    public void setOriginalWorkId(String originalWorkId) {
        this.originalWorkId = originalWorkId;
    }

    public String getManualMatchUserId() {
        return manualMatchUserId;
    }

    public void setManualMatchUserId(String manualMatchUserId) {
        this.manualMatchUserId = manualMatchUserId;
    }

    public List<Object> getWorkIdErrors() {
        return workIdErrors;
    }

    public void setWorkIdErrors(List<Object> workIdErrors) {
        this.workIdErrors = workIdErrors;
    }

    public String getInvalidWorkId() {
        return invalidWorkId;
    }

    public void setInvalidWorkId(String invalidWorkId) {
        this.invalidWorkId = invalidWorkId;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getNewWorkId() {
        return newWorkId;
    }

    public void setNewWorkId(String newWorkId) {
        this.newWorkId = newWorkId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getEstimatedDollarAmount() {
        return estimatedDollarAmount;
    }

    public void setEstimatedDollarAmount(String estimatedDollarAmount) {
        this.estimatedDollarAmount = estimatedDollarAmount;
    }

    public String getEstimatedDollarFlag() {
        return estimatedDollarFlag;
    }

    public void setEstimatedDollarFlag(String estimatedDollarFlag) {
        this.estimatedDollarFlag = estimatedDollarFlag;
    }

    public String getRequestTypeCode() {
        return requestTypeCode;
    }

    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
    }

    public String getMultWorkId() {
        return multWorkId;
    }

    public void setMultWorkId(String multWorkId) {
        this.multWorkId = multWorkId;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(String assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
