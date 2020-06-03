package com.ascap.apm.vob.reports;

import java.io.Serializable;

/**
 * @author mzbamidi
 *
 * To change this generated comment edit the template variable "type comment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ScheduleInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6044037403738412708L;
    private String reportId;
	private String reportName;
	private String scheduleNow;
	private String scheduleTime;
	

	/**
	 * Returns the reportId.
	 * @return String
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * Returns the reportName.
	 * @return String
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * Returns the scheduleNow.
	 * @return String
	 */
	public String getScheduleNow() {
		return scheduleNow;
	}

	/**
	 * Returns the scheduleTime.
	 * @return String
	 */
	public String getScheduleTime() {
		return scheduleTime;
	}

	/**
	 * Sets the reportId.
	 * @param reportId The reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * Sets the reportName.
	 * @param reportName The reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * Sets the scheduleNow.
	 * @param scheduleNow The scheduleNow to set
	 */
	public void setScheduleNow(String scheduleNow) {
		this.scheduleNow = scheduleNow;
	}

	/**
	 * Sets the scheduleTime.
	 * @param scheduleTime The scheduleTime to set
	 */
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

}
