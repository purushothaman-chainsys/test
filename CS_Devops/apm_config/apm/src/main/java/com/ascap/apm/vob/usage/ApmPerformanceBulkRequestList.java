package com.ascap.apm.vob.usage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ascap.apm.vob.BaseSearchVOB;

public class ApmPerformanceBulkRequestList extends BaseSearchVOB {

    /**
     * 
     */
    private static final long serialVersionUID = 8962877435676744655L;

    private String filterSupplierCode;

    private String filterPerformerName;

    private String filterWorkTitle;

    private String filterResultViewType;

    private String filterMatchStatusCode;

    private String filterAssignedToUser;

    private String filterMatchedByUser;

    private String workTitleSearchType;

    private String performerSearchType;

    private String[] selectedIds;

    private String operationType;

    private String medelyView;

    private String assignUser;

    private String[] medleyWorkOriginalInd;

    private String[] workId;

    private String[] workTitle;

    private String[] supplierCode;

    private String[] performerName;

    private String[] playCount;

    private String[] workPerfCount;

    private String[] originalWorkId;

    private String[] manualMatchUserId;

    private String requestTypeCode;

    private List<Object> invalidGroups;

    private String[] medleyWorkIds;

    private String[] medleyCloneCounts;

    private String medleySupplierCode;

    private String medleyWorkTitle;

    private String medleyPerformerName;

    private String medleyWriterName;

    private String medleyMultiWorkId;

    private String medleyOriginalExists;

    private String[] selectedIndex;

    private String[] writerName;

    private String[] groupingSubpplierCode;

    private String[] assignedToUser;

    private String[] priority;

    private String[] multWorkId;

    private List<String> medleyOriginalWorkList = new ArrayList<>();

    private String writerExists = "N";

    private String invalidUndeleteHeaderExists = "N";

    private String apmPerformanceBulkRequestListType;

    private List<ApmPerformanceBulkRequest> apmPerformanceBulkRequest;
    
    private String[] estimatedDollarAmount;
    
    private String[] estimatedDollarFlag;
    private String requestId;
    private String newWorkId;
  
    public String getNewWorkId() {
        return newWorkId;
    }
    
    public void setNewWorkId(String newWorkId) {
        this.newWorkId = newWorkId;
    }


    public String getRequestId() {
        return requestId;
    }

    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<ApmPerformanceBulkRequest> getApmPerformanceBulkRequest() {
        return apmPerformanceBulkRequest;
    }

    public void setApmPerformanceBulkRequest(List<ApmPerformanceBulkRequest> apmPerformanceBulkRequest) {
        this.apmPerformanceBulkRequest = apmPerformanceBulkRequest;
    }

    public String getFilterSupplierCode() {
        return filterSupplierCode;
    }

    public void setFilterSupplierCode(String filterSupplierCode) {
        this.filterSupplierCode = filterSupplierCode;
    }

    public String getFilterPerformerName() {
        return filterPerformerName;
    }

    public void setFilterPerformerName(String filterPerformerName) {
        this.filterPerformerName = filterPerformerName;
    }

    public String getFilterResultViewType() {
        return filterResultViewType;
    }

    public void setFilterResultViewType(String filterResultViewType) {
        this.filterResultViewType = filterResultViewType;
    }

    public String[] getSelectedIds() {
        return selectedIds;
    }

    public void setSelectedIds(String[] selectedIds) {
        this.selectedIds = selectedIds;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String[] getWorkId() {
        return workId;
    }

    public void setWorkId(String[] workId) {
        this.workId = workId;
    }

    public String[] getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String[] workTitle) {
        this.workTitle = workTitle;
    }

    public String[] getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String[] supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String[] getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String[] performerName) {
        this.performerName = performerName;
    }

    public String[] getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String[] playCount) {
        this.playCount = playCount;
    }

    public String[] getWorkPerfCount() {
        return workPerfCount;
    }

    public void setWorkPerfCount(String[] workPerfCount) {
        this.workPerfCount = workPerfCount;
    }

    public String getFilterWorkTitle() {
        return filterWorkTitle;
    }

    public void setFilterWorkTitle(String filterWorkTitle) {
        this.filterWorkTitle = filterWorkTitle;
    }

    public String getWorkTitleSearchType() {
        return workTitleSearchType;
    }

    public void setWorkTitleSearchType(String workTitleSearchType) {
        this.workTitleSearchType = workTitleSearchType;
    }

    public String getPerformerSearchType() {
        return performerSearchType;
    }

    public void setPerformerSearchType(String performerSearchType) {
        this.performerSearchType = performerSearchType;
    }

    public String getFilterMatchStatusCode() {
        return filterMatchStatusCode;
    }

    public void setFilterMatchStatusCode(String filterMatchStatusCode) {
        this.filterMatchStatusCode = filterMatchStatusCode;
    }

    public List<Object> getInvalidGroups() {
        return invalidGroups;
    }

    public void setInvalidGroups(List<Object> invalidGroups) {
        this.invalidGroups = invalidGroups;
    }

    public String[] getOriginalWorkId() {
        return originalWorkId;
    }

    public void setOriginalWorkId(String[] originalWorkId) {
        this.originalWorkId = originalWorkId;
    }

    public String[] getManualMatchUserId() {
        return manualMatchUserId;
    }

    public void setManualMatchUserId(String[] manualMatchUserId) {
        this.manualMatchUserId = manualMatchUserId;
    }

    public String getRequestTypeCode() {
        return requestTypeCode;
    }

    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
    }

    public String[] getMedleyWorkIds() {
        return medleyWorkIds;
    }

    public void setMedleyWorkIds(String[] medleyWorkIds) {
        this.medleyWorkIds = medleyWorkIds;
    }

    public String getMedleySupplierCode() {
        return medleySupplierCode;
    }

    public void setMedleySupplierCode(String medleySupplierCode) {
        this.medleySupplierCode = medleySupplierCode;
    }

    public String getMedleyWorkTitle() {
        return medleyWorkTitle;
    }

    public void setMedleyWorkTitle(String medleyWorkTitle) {
        this.medleyWorkTitle = medleyWorkTitle;
    }

    public String getMedleyPerformerName() {
        return medleyPerformerName;
    }

    public void setMedleyPerformerName(String medleyPerformerName) {
        this.medleyPerformerName = medleyPerformerName;
    }

    public String getFilterMatchedByUser() {
        return filterMatchedByUser;
    }

    public void setFilterMatchedByUser(String filterMatchedByUser) {
        this.filterMatchedByUser = filterMatchedByUser;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ApmPerformanceBulkRequestList [filterSupplierCode=");
        builder.append(filterSupplierCode);
        builder.append(", filterPerformerName=");
        builder.append(filterPerformerName);
        builder.append(", filterWorkTitle=");
        builder.append(filterWorkTitle);
        builder.append(", filterResultViewType=");
        builder.append(filterResultViewType);
        builder.append(", filterMatchStatusCode=");
        builder.append(filterMatchStatusCode);
        builder.append(", workTitleSearchType=");
        builder.append(workTitleSearchType);
        builder.append(", performerSearchType=");
        builder.append(performerSearchType);
        builder.append(", selectedIds=");
        builder.append(Arrays.toString(selectedIds));
        builder.append(", operationType=");
        builder.append(operationType);
        builder.append(", workId=");
        builder.append(Arrays.toString(workId));
        builder.append(", workTitle=");
        builder.append(Arrays.toString(workTitle));
        builder.append(", supplierCode=");
        builder.append(Arrays.toString(supplierCode));
        builder.append(", performerName=");
        builder.append(Arrays.toString(performerName));
        builder.append(", playCount=");
        builder.append(Arrays.toString(playCount));
        builder.append(", workPerfCount=");
        builder.append(Arrays.toString(workPerfCount));
        builder.append(", originalWorkId=");
        builder.append(Arrays.toString(originalWorkId));
        builder.append(", manualMatchUserId=");
        builder.append(Arrays.toString(manualMatchUserId));
        builder.append(", requestTypeCode=");
        builder.append(requestTypeCode);
        builder.append(", invalidGroups=");
        builder.append(invalidGroups);
        builder.append(", medleyWorkIds=");
        builder.append(Arrays.toString(medleyWorkIds));
        builder.append(", medleySupplierCode=");
        builder.append(medleySupplierCode);
        builder.append(", medleyWorkTitle=");
        builder.append(medleyWorkTitle);
        builder.append(", medleyPerformerName=");
        builder.append(medleyPerformerName);
        builder.append(", invalidUndeleteHeaderExists=");
        builder.append(invalidUndeleteHeaderExists);
        builder.append("]");
        return builder.toString();
    }

    public String getMedleyMultiWorkId() {
        return medleyMultiWorkId;
    }

    public void setMedleyMultiWorkId(String medleyMultiWorkId) {
        this.medleyMultiWorkId = medleyMultiWorkId;
    }

    public String getWriterExists() {
        return writerExists;
    }

    public void setWriterExists(String writerExists) {
        this.writerExists = writerExists;
    }

    public String getMedleyWriterName() {
        return medleyWriterName;
    }

    public void setMedleyWriterName(String medleyWriterName) {
        this.medleyWriterName = medleyWriterName;
    }

    public String[] getMedleyCloneCounts() {
        return medleyCloneCounts;
    }

    public void setMedleyCloneCounts(String[] medleyCloneCounts) {
        this.medleyCloneCounts = medleyCloneCounts;
    }

    public String getMedleyOriginalExists() {
        return medleyOriginalExists;
    }

    public void setMedleyOriginalExists(String medleyOriginalExists) {
        this.medleyOriginalExists = medleyOriginalExists;
    }

    public String getFilterAssignedToUser() {
        return filterAssignedToUser;
    }

    public void setFilterAssignedToUser(String filterAssignedToUser) {
        this.filterAssignedToUser = filterAssignedToUser;
    }

    public String getInvalidUndeleteHeaderExists() {
        return invalidUndeleteHeaderExists;
    }

    public void setInvalidUndeleteHeaderExists(String invalidUndeleteHeaderExists) {
        this.invalidUndeleteHeaderExists = invalidUndeleteHeaderExists;
    }

    public String getApmPerformanceBulkRequestListType() {
        return apmPerformanceBulkRequestListType;
    }

    public void setApmPerformanceBulkRequestListType(String apmPerformanceBulkRequestListType) {
        this.apmPerformanceBulkRequestListType = apmPerformanceBulkRequestListType;
    }

    public String[] getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(String[] assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    public String[] getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(String[] selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public String[] getWriterName() {
        return writerName;
    }

    public void setWriterName(String[] writerName) {
        this.writerName = writerName;
    }

    public String[] getGroupingSubpplierCode() {
        return groupingSubpplierCode;
    }

    public void setGroupingSubpplierCode(String[] groupingSubpplierCode) {
        this.groupingSubpplierCode = groupingSubpplierCode;
    }

    public String[] getPriority() {
        return priority;
    }

    public void setPriority(String[] priority) {
        this.priority = priority;
    }

    public String[] getMultWorkId() {
        return multWorkId;
    }

    public void setMultWorkId(String[] multWorkId) {
        this.multWorkId = multWorkId;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public List<String> getMedleyOriginalWorkList() {
        return medleyOriginalWorkList;
    }

    public void setMedleyOriginalWorkList(List<String> medleyOriginalWorkList) {
        this.medleyOriginalWorkList = medleyOriginalWorkList;
    }

    public String[] getMedleyWorkOriginalInd() {
        return medleyWorkOriginalInd;
    }

    public void setMedleyWorkOriginalInd(String[] medleyWorkOriginalInd) {
        this.medleyWorkOriginalInd = medleyWorkOriginalInd;
    }

    public String getMedelyView() {
        return medelyView;
    }

    public void setMedelyView(String medelyView) {
        this.medelyView = medelyView;
    }

    
    public String[] getEstimatedDollarAmount() {
        return estimatedDollarAmount;
    }

    
    public void setEstimatedDollarAmount(String[] estimatedDollarAmount) {
        this.estimatedDollarAmount = estimatedDollarAmount;
    }

    
    public String[] getEstimatedDollarFlag() {
        return estimatedDollarFlag;
    }

    
    public void setEstimatedDollarFlag(String[] estimatedDollarFlag) {
        this.estimatedDollarFlag = estimatedDollarFlag;
    }
    
}
