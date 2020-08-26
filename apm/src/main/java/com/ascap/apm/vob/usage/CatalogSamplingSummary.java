package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseSearchVOB;

public class CatalogSamplingSummary extends BaseSearchVOB {
	private static final long serialVersionUID = 6408237608809015278L;
	private String targetSurveyYearQuarter;
	private String hasActiveSamplingProcessOrInError; 
	private String callLetter;
	private String sampleCount; 
	private String operationType;
	private String samplingRequestId;
	
	
	 @Override
    public String toString() {
        return "CatalogSamplingSummary [targetSurveyYearQuarter=" + targetSurveyYearQuarter
            + ", hasActiveSamplingProcessOrInError=" + hasActiveSamplingProcessOrInError + ", callLetter=" + callLetter
            + ", sampleCount=" + sampleCount + ", operationType=" + operationType + ", samplingRequestId="
            + samplingRequestId + "]";
    }


    public String getHasActiveSamplingProcessOrInError() {
        return hasActiveSamplingProcessOrInError;
    }

    
    public void setHasActiveSamplingProcessOrInError(String hasActiveSamplingProcessOrInError) {
        this.hasActiveSamplingProcessOrInError = hasActiveSamplingProcessOrInError;
    }

    
    public String getSamplingRequestId() {
        return samplingRequestId;
    }

    
    public void setSamplingRequestId(String samplingRequestId) {
        this.samplingRequestId = samplingRequestId;
    }

    public String getTargetSurveyYearQuarter() {
		return targetSurveyYearQuarter;
	}

	public void setTargetSurveyYearQuarter(String targetSurveyYearQuarter) {
		this.targetSurveyYearQuarter = targetSurveyYearQuarter;
	}

	public String gethasActiveSamplingProcessOrInError() {
		return hasActiveSamplingProcessOrInError;
	}

	public void sethasActiveSamplingProcessOrInError(String hasActiveSamplingProcessOrInError) {
		this.hasActiveSamplingProcessOrInError = hasActiveSamplingProcessOrInError;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public String getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(String sampleCount) {
		this.sampleCount = sampleCount;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}
