package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class Exemption extends BaseVOB {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String exemptionId;
    private String exemptionType;
    private String exemptionValue;
    private String operationType;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Exemption [exemptionId=");
        builder.append(exemptionId);
        builder.append(", exemptionType=");
        builder.append(exemptionType);
        builder.append(", exemptionValue=");
        builder.append(exemptionValue);
        builder.append("]");
        return builder.toString();
    }

    public String getExemptionId() {
        return exemptionId;
    }

    public void setExemptionId(String exemptionId) {
        this.exemptionId = exemptionId;
    }

    public String getExemptionType() {
        return exemptionType;
    }

    public void setExemptionType(String exemptionType) {
        this.exemptionType = exemptionType;
    }

    public String getExemptionValue() {
        return exemptionValue;
    }

    public void setExemptionValue(String exemptionValue) {
        this.exemptionValue = exemptionValue;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
    
    

}
