package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class EOCallLetterConfig extends BaseVOB {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String suppCode;
    private String callLetter;
    private String callLetterSuffix;
    private String callLetterFull;
    private String samplingCompletedFlag;


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EOCallLetterConfig [suppCode=");
        builder.append(suppCode);
        builder.append(", callLetter=");
        builder.append(callLetter);
        builder.append(", callLetterSuffix=");
        builder.append(callLetterSuffix);
        builder.append(", callLetterFull=");
        builder.append(callLetterFull);
        builder.append(", samplingCompletedFlag=");
        builder.append(samplingCompletedFlag);
        builder.append("]");
        return builder.toString();
    }


    public String getSuppCode() {
        return suppCode;
    }


    public void setSuppCode(String suppCode) {
        this.suppCode = suppCode;
    }


    public String getCallLetter() {
        return callLetter;
    }


    public void setCallLetter(String callLetter) {
        this.callLetter = callLetter;
    }


    public String getCallLetterSuffix() {
        return callLetterSuffix;
    }


    public void setCallLetterSuffix(String callLetterSuffix) {
        this.callLetterSuffix = callLetterSuffix;
    }


    public String getSamplingCompletedFlag() {
        return samplingCompletedFlag;
    }


    public void setSamplingCompletedFlag(String samplingCompletedFlag) {
        this.samplingCompletedFlag = samplingCompletedFlag;
    }


    public String getCallLetterFull() {
        return callLetterFull;
    }


    public void setCallLetterFull(String callLetterFull) {
        this.callLetterFull = callLetterFull;
    }

}
