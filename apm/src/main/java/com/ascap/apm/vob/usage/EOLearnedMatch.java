package com.ascap.apm.vob.usage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ascap.apm.vob.BaseVOB;

public class EOLearnedMatch extends BaseVOB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String workId;
	private String matchType;
	private String matchIdValue;
	private String learnedMatchId;
	private String supplierCode;
	private String multWorkId;
	
	private String invalidWorkId;
	private String[] workIds;
	private List<String> workIdCollection = new ArrayList<String>();
	private Collection<String> workIdErrors;
	
	
	private String[] medleyCloneCounts;
	private List<String> cloneCountsCollection = new ArrayList<String>();
	private String operationType;
	
	
    @Override
	public String toString() {
		return "EOLearnedMatch [workId=" + workId + ", matchType=" + matchType
				+ ", matchIdValue=" + matchIdValue + ", learnedMatchId="
				+ learnedMatchId + ", supplierCode=" + supplierCode
				+ ", multWorkId=" + multWorkId + ", invalidWorkId="
				+ invalidWorkId + ", workIds=" + Arrays.toString(workIds)
				+ ", workIdCollection=" + workIdCollection + ", workIdErrors="
				+ workIdErrors + ", medleyCloneCounts="
				+ Arrays.toString(medleyCloneCounts)
				+ ", cloneCountsCollection=" + cloneCountsCollection + "]";
	}

    
    public String getOperationType() {
        return operationType;
    }
    
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getLearnedMatchId() {
		return learnedMatchId;
	}
	public void setLearnedMatchId(String learnedMatchId) {
		this.learnedMatchId = learnedMatchId;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getInvalidWorkId() {
		return invalidWorkId;
	}
	public void setInvalidWorkId(String invalidWorkId) {
		this.invalidWorkId = invalidWorkId;
	}
	public String getMatchIdValue() {
		return matchIdValue;
	}

	public void setMatchIdValue(String matchIdValue) {
		this.matchIdValue = matchIdValue;
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
	public Collection<String> getWorkIdErrors() {
		return workIdErrors;
	}
	public void setWorkIdErrors(Collection<String> workIdErrors) {
		this.workIdErrors = workIdErrors;
	}
	public String[] getMedleyCloneCounts() {
		return medleyCloneCounts;
	}
	public void setMedleyCloneCounts(String[] medleyCloneCounts) {
		this.medleyCloneCounts = medleyCloneCounts;
	}
	public List<String> getCloneCountsCollection() {
		return cloneCountsCollection;
	}
	public void setCloneCountsCollection(List<String> cloneCountsCollection) {
		this.cloneCountsCollection = cloneCountsCollection;
	}

}
