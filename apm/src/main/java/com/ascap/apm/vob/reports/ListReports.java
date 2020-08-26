package com.ascap.apm.vob.reports;

import java.util.ArrayList;
import java.util.List;
import com.ascap.apm.vob.BaseSearchVOB;

/**
 * Form bean for a Struts application.
 * @version 	1.0
 * @author
 */
public class ListReports extends BaseSearchVOB {

    /**
     * 
     */
    private static final long serialVersionUID = -2147455663835715909L;
    private List<Object> reports;
	private List<Object> parameters = new ArrayList<>();
	private String selectedReportID;
	private String actionSubmit;
	private List<Object> statusList;
	private String moduleName;

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
	public List<Object> getReports() {
		return reports;
	}

	/**
	 * Sets the reports.
	 * @param reports The reports to set
	 */
	public void setReports(List<Object> reports) {
		this.reports = reports;
	}

	/**
	 * Returns the parameters.
	 * @return List
	 */
	public List<Object> getParameters() {
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
	public void setParameters(List<Object> parameters) {
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
	public List<Object> getStatusList() {
		return statusList;
	}

	/**
	 * Sets the statusList.
	 * @param statusList The statusList to set
	 */
	public void setStatusList(List<Object> statusList) {
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
