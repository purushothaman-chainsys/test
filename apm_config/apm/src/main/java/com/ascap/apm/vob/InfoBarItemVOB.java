package com.ascap.apm.vob;

/**
 * InfoBarItem is VOB to hold a string Description and string value.
 * 
 * @author shyam_narayana
 */
public class InfoBarItemVOB extends BaseVOB {

    private static final long serialVersionUID = 853993190641058848L;

    private String fieldDescription;

    private String fieldValue;

    public InfoBarItemVOB() {
        super();
    }

    public InfoBarItemVOB(String fieldDescription, String fieldValue) {
        this.fieldDescription = fieldDescription;
        this.fieldValue = fieldValue;
    }

    /**
     * Returns the fieldDescription.
     * 
     * @return String
     */
    public String getFieldDescription() {
        return fieldDescription;
    }

    /**
     * Returns the fieldValue.
     * 
     * @return String
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * Sets the fieldDescription.
     * 
     * @param fieldDescription The fieldDescription to set
     */
    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    /**
     * Sets the fieldValue.
     * 
     * @param fieldValue The fieldValue to set
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String toString() {
        StringBuilder outStrBuff;
        outStrBuff = new StringBuilder("InfoBarItemVOB{");
        outStrBuff.append("fieldDescription='").append(this.fieldDescription).append("', fieldValue='")
            .append(this.fieldValue).append("'}");
        return (outStrBuff.toString());
    }
}
