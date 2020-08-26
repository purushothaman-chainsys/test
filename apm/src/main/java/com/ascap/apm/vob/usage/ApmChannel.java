package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

public class ApmChannel extends BaseVOB {

	private static final long serialVersionUID = -3884895606693246469L;

	private String suppCode = null;
	private String channelName = null;
	private String channelType = null;
	private String channelDescription = null;
	private String channelId = null;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApmChannel [suppCode=");
		builder.append(suppCode);
		builder.append(", channelName=");
		builder.append(channelName);
		builder.append(", channelType=");
		builder.append(channelType);
		builder.append(", channelDescription=");
		builder.append(channelDescription);
		builder.append(", channelId=");
		builder.append(channelId);
		builder.append("]");
		return builder.toString();
	}

	public String getSuppCode() {
		return suppCode;
	}

	public void setSuppCode(String suppCode) {
		this.suppCode = suppCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelDescription() {
		return channelDescription;
	}

	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

}
