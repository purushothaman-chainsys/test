package com.ascap.apm.vob.usage;

import java.util.Arrays;
import java.util.List;

import com.ascap.apm.vob.BaseSearchVOB;

public class ApmLearnedMatchList extends BaseSearchVOB {
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String filterSupplierCode;
	private String filterPerformerName;
	private String performerSearchType; 
	private String filterWorkTitle;
	private String filterWorkId;
	private String workTitleSearchType;
	private String filterResultViewType;
	private String filterMatchTypeCode;
	private String medleyMultiWorkId;
	private String learnedMatchType;

	private String[] selectedIds;
	private String operationType;
	private String selectdRowId;
	private String[] selectedIndex;
	
	private String[] workId;
    private String[] workTitle;
    private String[] supplierCode;
    private String[] performerName;
    private String[] writerName;
    private String[] originalWorkId;
    private String[] learnedDeleteFlag;
    private String[] multWorkId;
    private String[] lmType;
    private String[] cloneCount;
	

	private List<Object> invalidGroups;
	
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
	public String getFilterWorkTitle() {
		return filterWorkTitle;
	}
	public void setFilterWorkTitle(String filterWorkTitle) {
		this.filterWorkTitle = filterWorkTitle;
	}
	public String getFilterResultViewType() {
		return filterResultViewType;
	}
	public void setFilterResultViewType(String filterResultViewType) {
		this.filterResultViewType = filterResultViewType;
	}
	public String getFilterMatchTypeCode() {
		return filterMatchTypeCode;
	}
	public void setFilterMatchTypeCode(String filterMatchTypeCode) {
		this.filterMatchTypeCode = filterMatchTypeCode;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApmLearnedMatchList [filterSupplierCode=");
		builder.append(filterSupplierCode);
		builder.append(", filterPerformerName=");
		builder.append(filterPerformerName);
		builder.append(", performerSearchType=");
		builder.append(performerSearchType);
		builder.append(", filterWorkTitle=");
		builder.append(filterWorkTitle);
		builder.append(", filterWorkId=");
		builder.append(filterWorkId);
		builder.append(", workTitleSearchType=");
		builder.append(workTitleSearchType);
		builder.append(", filterResultViewType=");
		builder.append(filterResultViewType);
		builder.append(", filterMatchTypeCode=");
		builder.append(filterMatchTypeCode);
		builder.append(", selectedIds=");
		builder.append(Arrays.toString(selectedIds));
		builder.append(", operationType=");
		builder.append(operationType);
		builder.append("]");
		return builder.toString();
	}
	public String getPerformerSearchType() {
		return performerSearchType;
	}
	public void setPerformerSearchType(String performerSearchType) {
		this.performerSearchType = performerSearchType;
	}
	public String getWorkTitleSearchType() {
		return workTitleSearchType;
	}
	public void setWorkTitleSearchType(String workTitleSearchType) {
		this.workTitleSearchType = workTitleSearchType;
	}
	public String getFilterWorkId() {
		return filterWorkId;
	}
	public void setFilterWorkId(String filterWorkId) {
		this.filterWorkId = filterWorkId;
	}
	public List<Object> getInvalidGroups() {
		return invalidGroups;
	}
	public void setInvalidGroups(List<Object> invalidGroups) {
		this.invalidGroups = invalidGroups;
	}
	public String getMedleyMultiWorkId() {
		return medleyMultiWorkId;
	}
	public void setMedleyMultiWorkId(String medleyMultiWorkId) {
		this.medleyMultiWorkId = medleyMultiWorkId;
	}
	public String getLearnedMatchType() {
		return learnedMatchType;
	}
	public void setLearnedMatchType(String learnedMatchType) {
		this.learnedMatchType = learnedMatchType;
	}
    public String getSelectdRowId() {
        return selectdRowId;
    }
    public void setSelectdRowId(String selectdRowId) {
        this.selectdRowId = selectdRowId;
    }
    public String[] getSelectedIndex() {
        return selectedIndex;
    }
    public void setSelectedIndex(String[] selectedIndex) {
        this.selectedIndex = selectedIndex;
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
    
    public String[] getWriterName() {
        return writerName;
    }
    
    public void setWriterName(String[] writerName) {
        this.writerName = writerName;
    }
    
    public String[] getOriginalWorkId() {
        return originalWorkId;
    }
    
    public void setOriginalWorkId(String[] originalWorkId) {
        this.originalWorkId = originalWorkId;
    }
    
    public String[] getLearnedDeleteFlag() {
        return learnedDeleteFlag;
    }
    
    public void setLearnedDeleteFlag(String[] learnedDeleteFlag) {
        this.learnedDeleteFlag = learnedDeleteFlag;
    }
    
    public String[] getMultWorkId() {
        return multWorkId;
    }
    
    public void setMultWorkId(String[] multWorkId) {
        this.multWorkId = multWorkId;
    }
    
    public String[] getLmType() {
        return lmType;
    }
    
    public void setLmType(String[] lmType) {
        this.lmType = lmType;
    }
    public String[] getCloneCount() {
        return cloneCount;
    }
    public void setCloneCount(String[] cloneCount) {
        this.cloneCount = cloneCount;
    }
	
}
