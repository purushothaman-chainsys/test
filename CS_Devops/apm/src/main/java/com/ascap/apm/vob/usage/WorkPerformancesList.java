package com.ascap.apm.vob.usage;

import java.util.List;

import com.ascap.apm.vob.BaseSearchVOB;

/**
 * Represents the List of Work Performances a ProgramPerformance can Have.
 * @author Jaya Shyam Narayana Lingamchetty
 * @version $Revision:   1.2  $ $Date:   Feb 06 2008 18:41:20  $
 */
public class WorkPerformancesList extends BaseSearchVOB {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2225219104970107760L;
	private String programPerformanceId=null;
	private String versionNumber=null;
	
	private String[] selectedIds=null;
	private String[] selectedUseTypeCodes=null;
	private String[] originalUseTypeCodes=null;
	private String actionSelected=null;
	
	private ProgramPerformance programPerformance=null;
	
	private List<Object> invalidWorkPerformances = null;
	private String deleteType;
	
	private String selectedWorkPerformanceId=null;
	private String selectedPerformanceVersionNumber =null;
	
	/**
	 * Returns the actionSelected.
	 * @return String
	 */
	public String getActionSelected() {
		return actionSelected;
	}

	/**
	 * Returns the programPerformanceId.
	 * @return String
	 */
	public String getProgramPerformanceId() {
		return programPerformanceId;
	}

	/**
	 * Returns the selectedIds.
	 * @return String[]
	 */
	public String[] getSelectedIds() {
		return selectedIds;
	}

	/**
	 * Returns the versionNumber.
	 * @return String
	 */
	public String getVersionNumber() {
		return versionNumber;
	}

	/**
	 * Sets the actionSelected.
	 * @param actionSelected The actionSelected to set
	 */
	public void setActionSelected(String actionSelected) {
		this.actionSelected = actionSelected;
	}

	/**
	 * Sets the programPerformanceId.
	 * @param programPerformanceId The programPerformanceId to set
	 */
	public void setProgramPerformanceId(String programPerformanceId) {
		this.programPerformanceId = programPerformanceId;
	}

	/**
	 * Sets the selectedIds.
	 * @param selectedIds The selectedIds to set
	 */
	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	/**
	 * Sets the versionNumber.
	 * @param versionNumber The versionNumber to set
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * Returns the programPerformance.
	 * @return ProgramPerformance
	 */
	public ProgramPerformance getProgramPerformance() {
		return programPerformance;
	}

	/**
	 * Sets the programPerformance.
	 * @param programPerformance The programPerformance to set
	 */
	public void setProgramPerformance(ProgramPerformance programPerformance) {
		this.programPerformance = programPerformance;
	}

	/**
	 * Returns the invalidWorkPerformances.
	 * @return Collection
	 */
	public List<Object> getInvalidWorkPerformances() {
		return invalidWorkPerformances;
	}

	/**
	 * Sets the invalidWorkPerformances.
	 * @param invalidWorkPerformances The invalidWorkPerformances to set
	 */
	public void setInvalidWorkPerformances(List<Object> invalidWorkPerformances) {
		this.invalidWorkPerformances = invalidWorkPerformances;
	}

public String toString() {
	StringBuffer outStrBuff;
	outStrBuff = new StringBuffer();
	outStrBuff
		.append(super.toString())
		.append("com.ascap.apm.common.vob.usage.WorkPerformancesList {")
		.append("programPerformance= '")
		.append(programPerformance)
		.append("', ")
		.append("actionSelected= '")
		.append(actionSelected)
		.append("', ")
		.append("selectedIds= '")
		.append(selectedIds)
		.append("', ")
		.append("versionNumber= '")
		.append(versionNumber)
		.append("', ")
		.append("programPerformanceId= '")
		.append(programPerformanceId)
		.append(", ")
		.append("'}");
	return (outStrBuff.toString());
}

public String getDeleteType() {
	return deleteType;
}

public void setDeleteType(String deleteType) {
	this.deleteType = deleteType;
}

public String[] getSelectedUseTypeCodes() {
	return selectedUseTypeCodes;
}

public void setSelectedUseTypeCodes(String[] selectedUseTypeCodes) {
	this.selectedUseTypeCodes = selectedUseTypeCodes;
}

public String[] getOriginalUseTypeCodes() {
	return originalUseTypeCodes;
}

public void setOriginalUseTypeCodes(String[] originalUseTypeCodes) {
	this.originalUseTypeCodes = originalUseTypeCodes;
}

public String getSelectedWorkPerformanceId() {
	return selectedWorkPerformanceId;
}

public void setSelectedWorkPerformanceId(String selectedWorkPerformanceId) {
	this.selectedWorkPerformanceId = selectedWorkPerformanceId;
}

public String getSelectedPerformanceVersionNumber() {
	return selectedPerformanceVersionNumber;
}

public void setSelectedPerformanceVersionNumber(String selectedPerformanceVersionNumber) {
	this.selectedPerformanceVersionNumber = selectedPerformanceVersionNumber;
}


}
