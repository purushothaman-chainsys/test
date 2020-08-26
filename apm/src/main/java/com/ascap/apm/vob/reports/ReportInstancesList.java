package com.ascap.apm.vob.reports;

import java.util.List;


import com.ascap.apm.vob.BaseSearchVOB;

/**
 * @author mzbamidi
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ReportInstancesList extends BaseSearchVOB {
    
    /**
     * 
     */
    private static final long serialVersionUID = 5008598902095051829L;
    private String reportName;
    private String reportID;
    private String description;
    private List<Object> parameters = null;
    private List<Object> statusList;
    private List<Object> inputs;
    private String radioValue;  
    private String reportType;
    
    private boolean adminRunnableFlag;
    
    private boolean adminExportFlag;
   

    /**
     * Returns the description.
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the inputs.
     * @return List
     */
    public List<Object> getInputs() {
        return inputs;
    }

    /**
     * Returns the parameters.
     * @return List
     */
    public List<Object> getParameters() {
        return parameters;
    }

    /**
     * Returns the radioValue.
     * @return String
     */
    public String getRadioValue() {
        return radioValue;
    }

    /**
     * Returns the reportID.
     * @return String
     */
    public String getReportID() {
        return reportID;
    }

    /**
     * Returns the reportName.
     * @return String
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * Returns the reportType.
     * @return String
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Returns the statusList.
     * @return List
     */
    public List<Object> getStatusList() {
        return statusList;
    }

    /**
     * Sets the description.
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the inputs.
     * @param inputs The inputs to set
     */
    public void setInputs(List<Object> inputs) {
        this.inputs = inputs;
    }

    /**
     * Sets the parameters.
     * @param parameters The parameters to set
     */
    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * Sets the radioValue.
     * @param radioValue The radioValue to set
     */
    public void setRadioValue(String radioValue) {
        this.radioValue = radioValue;
    }

    /**
     * Sets the reportID.
     * @param reportID The reportID to set
     */
    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    /**
     * Sets the reportName.
     * @param reportName The reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * Sets the reportType.
     * @param reportType The reportType to set
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * Sets the statusList.
     * @param statusList The statusList to set
     */
    public void setStatusList(List<Object> statusList) {
        this.statusList = statusList;
    }

    /**
     * @return Returns the adminRunnableFlag.
     */
    public boolean isAdminRunnableFlag() {
        return adminRunnableFlag;
    }
    /**
     * @param adminRunnableFlag The adminRunnableFlag to set.
     */
    public void setAdminRunnableFlag(boolean adminRunnableFlag) {
        this.adminRunnableFlag = adminRunnableFlag;
    }
    /**
     * @return Returns the adminExportFlag.
     */
    public boolean isAdminExportFlag() {
        return adminExportFlag;
    }
    /**
     * @param adminExportFlag The adminExportFlag to set.
     */
    public void setAdminExportFlag(boolean adminExportFlag) {
        this.adminExportFlag = adminExportFlag;
    }
}
