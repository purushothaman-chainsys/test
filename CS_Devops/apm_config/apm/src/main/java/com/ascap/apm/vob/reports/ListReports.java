package com.ascap.apm.vob.reports;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.vob.BaseSearchVOB;
import com.ascap.apm.vob.reports.Parameter;

/**
 * Form bean for a Struts application.
 * @version 	1.0
 * @author
 */
public class ListReports extends BaseSearchVOB {

	private List reports;
	private List parameters = new ArrayList();
	private String selectedReportID;
	private String actionSubmit;
	private List statusList;
	private String moduleName;
	private String operationFlag=null;

	/**
	* Constructor
	*/
	public ListReports() {

		super();

	}
	
	/**
	 * Returns the reports.
	 * @return ArrayList
	 */
	public List getReports() {
		return reports;
	}

	/**
	 * Sets the reports.
	 * @param reports The reports to set
	 */
	public void setReports(List reports) {
		this.reports = reports;
	}

	/**
	 * Returns the parameters.
	 * @return List
	 */
	public List getParameters() {
		return parameters;
	}

	/**
	 * Returns the selectedReportID.
	 * @return String
	 */
	public String getSelectedReportID() {
		return selectedReportID;
	}

	/**
	 * Sets the parameters.
	 * @param parameters The parameters to set
	 */
	public void setParameters(List parameters) {
		this.parameters = parameters;
	}

	/**
	 * Sets the selectedReportID.
	 * @param selectedReportID The selectedReportID to set
	 */
	public void setSelectedReportID(String selectedReportID) {
		this.selectedReportID = selectedReportID;
	}

	/**
	 * Returns the actionSubmit.
	 * @return String
	 */
	public String getActionSubmit() {
		return actionSubmit;
	}

	/**
	 * Sets the actionSubmit.
	 * @param actionSubmit The actionSubmit to set
	 */
	public void setActionSubmit(String actionSubmit) {
		this.actionSubmit = actionSubmit;
	}

	/**
	 * Returns the statusList.
	 * @return List
	 */
	public List getStatusList() {
		return statusList;
	}

	/**
	 * Sets the statusList.
	 * @param statusList The statusList to set
	 */
	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	/**
	 * Returns the moduleName.
	 * @return String
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * Sets the moduleName.
	 * @param moduleName The moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * Method getOperationFlag.
	 * @return String
	 */
	public String getOperationFlag() {
		return null;
	}

	/**
	 * Sets the operationFlag.
	 * @param operationFlag The operationFlag to set
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	
	/**
	 * Returns the currentPageNumber.
	 * @return int
	 */
	public int getTotalPages() {
		
		
		if(super.getTotalPages()<=0)
		{ 
			return super.getTotalPages()+1;	
		}else{
			return super.getTotalPages();
		}
		
	}

}
