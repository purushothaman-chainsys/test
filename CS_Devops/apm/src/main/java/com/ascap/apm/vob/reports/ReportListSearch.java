package com.ascap.apm.vob.reports;

import java.util.ArrayList;
import java.util.List;

import com.ascap.apm.vob.BaseSearchVOB;

/**
 * @author vzayyadevara To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class ReportListSearch extends BaseSearchVOB {

    /**
     * 
     */
    private static final long serialVersionUID = 5017231993142451806L;

    private List<Object> reports;

    private List<Object> parameters = new ArrayList<>();

    private String selectedReportID;

    private String actionSubmit;

    private List<Object> statusList;

    private String moduleName;

    /**
     * Returns the actionSubmit.
     * 
     * @return String
     */
    public String getActionSubmit() {
        return actionSubmit;
    }

    /**
     * Returns the parameters.
     * 
     * @return List
     */
    public List<Object> getParameters() {
        return parameters;
    }

    /**
     * Returns the reports.
     * 
     * @return ArrayList
     */
    public List<Object> getReports() {
        return reports;
    }

    /**
     * Sets the reports.
     * 
     * @param reports The reports to set
     */
    public void setReports(List<Object> reports) {
        this.reports = reports;
    }

    /**
     * Returns the selectedReportID.
     * 
     * @return String
     */
    public String getSelectedReportID() {
        return selectedReportID;
    }

    /**
     * Returns the statusList.
     * 
     * @return List
     */
    public List<Object> getStatusList() {
        return statusList;
    }

    /**
     * Sets the actionSubmit.
     * 
     * @param actionSubmit The actionSubmit to set
     */
    public void setActionSubmit(String actionSubmit) {
        this.actionSubmit = actionSubmit;
    }

    /**
     * Sets the moduleName.
     * 
     * @param moduleName The moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Sets the parameters.
     * 
     * @param parameters The parameters to set
     */
    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * Sets the selectedReportID.
     * 
     * @param selectedReportID The selectedReportID to set
     */
    public void setSelectedReportID(String selectedReportID) {
        this.selectedReportID = selectedReportID;
    }

    /**
     * Sets the statusList.
     * 
     * @param statusList The statusList to set
     */
    public void setStatusList(List<Object> statusList) {
        this.statusList = statusList;
    }

    /**
     * Returns the moduleName.
     * 
     * @return String
     */
    public String getModuleName() {
        return moduleName;
    }

}
