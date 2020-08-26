package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class EOSupplierFormat extends BaseVOB {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String suppCode;
    private String unassignedChannelCount;
    private String channelCount;
    private String apmSuppCode;



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EOSupplierFormat [suppCode=");
        builder.append(suppCode);
        builder.append(", unassignedChannelCount=");
        builder.append(unassignedChannelCount);
        builder.append(", channelCount=");
        builder.append(channelCount);
        builder.append(", apmSuppCode=");
        builder.append(apmSuppCode);
        builder.append("]");
        return builder.toString();
    }



    public String getSuppCode() {
        return suppCode;
    }



    public void setSuppCode(String suppCode) {
        this.suppCode = suppCode;
    }



    public String getUnassignedChannelCount() {
        return unassignedChannelCount;
    }



    public void setUnassignedChannelCount(String unassignedChannelCount) {
        this.unassignedChannelCount = unassignedChannelCount;
    }



    public String getChannelCount() {
        return channelCount;
    }



    public void setChannelCount(String channelCount) {
        this.channelCount = channelCount;
    }



    public String getApmSuppCode() {
        return apmSuppCode;
    }



    public void setApmSuppCode(String apmSuppCode) {
        this.apmSuppCode = apmSuppCode;
    }

}
