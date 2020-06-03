package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class ApmWork extends BaseVOB {
	
	/**
     * 
     */
    private static final long serialVersionUID = -8911528385605496715L;
    /**
	 * 
	 */
	private String workId;
	private String workTypeCode;
	private String publicDomainIndicator;
	private long workGradingValue;
	private String validFlag;
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApmWork [workId=");
		builder.append(workId);
		builder.append(", workTypeCode=");
		builder.append(workTypeCode);
		builder.append(", publicDomainIndicator=");
		builder.append(publicDomainIndicator);
		builder.append(", workGradingValue=");
		builder.append(workGradingValue);
		builder.append(", validFlag=");
		builder.append(validFlag);
		builder.append("]");
		return builder.toString();
	}



	public String getWorkId() {
		return workId;
	}



	public void setWorkId(String workId) {
		this.workId = workId;
	}



	public String getPublicDomainIndicator() {
		return publicDomainIndicator;
	}



	public void setPublicDomainIndicator(String publicDomainIndicator) {
		this.publicDomainIndicator = publicDomainIndicator;
	}



	public String getWorkTypeCode() {
		return workTypeCode;
	}



	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}



	public long getWorkGradingValue() {
		return workGradingValue;
	}



	public void setWorkGradingValue(long workGradingValue) {
		this.workGradingValue = workGradingValue;
	}



	public String getValidFlag() {
		return validFlag;
	}



	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}




}
