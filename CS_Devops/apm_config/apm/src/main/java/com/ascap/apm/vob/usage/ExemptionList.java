package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseSearchVOB;

public class ExemptionList extends BaseSearchVOB {

    /**
     * 
     */
    private static final long serialVersionUID = 7424793369563228994L;
    
    private String filterExemptionType;
    private String filterExemptionValue;
    
    private String operationType;
    
    private String[] selectedIndex;
    private String selectedId;
    
    public String getFilterExemptionType() {
        return filterExemptionType;
    }
    public void setFilterExemptionType(String filterExemptionType) {
        this.filterExemptionType = filterExemptionType;
    }
    public String getFilterExemptionValue() {
        return filterExemptionValue;
    }
    public void setFilterExemptionValue(String filterExemptionValue) {
        this.filterExemptionValue = filterExemptionValue;
    }
    public String getOperationType() {
        return operationType;
    }
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
    public String[] getSelectedIndex() {
        return selectedIndex;
    }
    public void setSelectedIndex(String[] selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
    public String getSelectedId() {
        return selectedId;
    }
    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }

}
