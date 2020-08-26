package com.ascap.apm.vob.usage;

import java.util.List;

import com.ascap.apm.vob.BaseVOB;

public class ApmActiveSurveyQuarter extends BaseVOB {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    private String startDate;
    private String endDate;
    private String activeFlag;
    private String activeSurveyQuarter;
    private List<?> censusSuppliers;


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ApmActiveSurveyQuarter [startDate=");
        builder.append(startDate);
        builder.append(", endDate=");
        builder.append(endDate);
        builder.append(", activeFlag=");
        builder.append(activeFlag);
        builder.append(", activeSurveyQuarter=");
        builder.append(activeSurveyQuarter);
        builder.append("]");
        return builder.toString();
    }


    public String getStartDate() {
        return startDate;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getEndDate() {
        return endDate;
    }


    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getActiveFlag() {
        return activeFlag;
    }


    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }


    public String getActiveSurveyQuarter() {
        return activeSurveyQuarter;
    }


    public void setActiveSurveyQuarter(String activeSurveyQuarter) {
        this.activeSurveyQuarter = activeSurveyQuarter;
    }


    public List<?> getCensusSuppliers() {
        return censusSuppliers;
    }


    public void setCensusSuppliers(List<?> censusSuppliers) {
        this.censusSuppliers = censusSuppliers;
    }

}
