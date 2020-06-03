package com.ascap.apm.vob.admin;

import com.ascap.apm.vob.BaseVOB;

public class Group extends BaseVOB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3006268999045955122L;

	
	
	private String groupCode;
	private String description;

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the groupCode.
	 */
	public String getGroupCode() {
		return groupCode;
	}
	/**
	 * @param groupCode The groupCode to set.
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Group [groupCode=");
		builder.append(groupCode);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

}
