package com.ascap.apm.vob.reports;

import java.io.Serializable;

public class AdminReport implements Serializable {
	
	
	/**
     * 
     */
    private static final long serialVersionUID = -2835658497296432826L;
    private String reportName;
	private String scheduleLater;
	private String scheduleTime;
	private String storeProcedure;
	private String userID;
	private String delFlag;
	private String statusMessage;
	private int userInstanceCnt;
	private int archiveInstanceCnt;
	private int instMaxDaysCnt;
	private String rptMaxInstFlag;
	private String rptInstMaxDaysFlag;
	private boolean adminExportFlag;
	private boolean adminRunnableFlag;
	private String databaseType;

	
	/**
	 * Constructor for AdminReport.
	 */
	public AdminReport() {
		super();
		
	}
	public AdminReport(String reportName,
				  String scheduleLater,
				  String scheduleTime,
				  String storeProcedure){
	  this.reportName = reportName;
	  this.scheduleLater = scheduleLater;
	  this.scheduleTime = scheduleTime;
	  this.storeProcedure = storeProcedure;
	}			  		
	/**
	 * Returns the reportName.
	 * @return String
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * Returns the scheduleLater.
	 * @return String
	 */
	public String getScheduleLater() {
		return scheduleLater;
	}

	/**
	 * Returns the scheduleTime.
	 * @return List
	 */
	public String getScheduleTime() {
   		return scheduleTime;
	}

	/**
	 * Returns the storeProcedure.
	 * @return String
	 */
	public String getStoreProcedure() {
		return storeProcedure;
	}

	
	/**
	 * Sets the ReportName.
	 * @param ReportName The ReportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * Sets the scheduleLater.
	 * @param scheduleLater The scheduleLater to set
	 */
	public void setScheduleLater(String scheduleLater) {
		this.scheduleLater = scheduleLater;
	}

	/**
	 * Sets the scheduleTime.
	 * @param scheduleTime The scheduleTime to set
	 */
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	/**
	 * Sets the storeProcedure.
	 * @param storeProcedure The storeProcedure to set
	 */
	public void setStoreProcedure(String storeProcedure) {
		this.storeProcedure = storeProcedure;
	}

	
	/**
	 * Returns the userID.
	 * @return String
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * Sets the userID.
	 * @param userID The userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * Returns the delFlag.
	 * @return String
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * Sets the delFlag.
	 * @param delFlag The delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	/**
	 * Returns the statusMessage.
	 * @return String
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * Sets the statusMessage.
	 * @param statusMessage The statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

    /**
     * @return Returns the archiveInstanceCnt.
     */
    public int getArchiveInstanceCnt() {
        return archiveInstanceCnt;
    }
    /**
     * @param archiveInstanceCnt The archiveInstanceCnt to set.
     */
    public void setArchiveInstanceCnt(int archiveInstanceCnt) {
        this.archiveInstanceCnt = archiveInstanceCnt;
    }
    /**
     * @return Returns the userInstanceCnt.
     */
    public int getUserInstanceCnt() {
        return userInstanceCnt;
    }
    /**
     * @param userInstanceCnt The userInstanceCnt to set.
     */
    public void setUserInstanceCnt(int userInstanceCnt) {
        this.userInstanceCnt = userInstanceCnt;
    }
    /**
     * @return Returns the instMaxDaysCnt.
     */
    public int getInstMaxDaysCnt() {
        return instMaxDaysCnt;
    }
    /**
     * @param instMaxDaysCnt The instMaxDaysCnt to set.
     */
    public void setInstMaxDaysCnt(int instMaxDaysCnt) {
        this.instMaxDaysCnt = instMaxDaysCnt;
    }
    /**
     * @return Returns the rptMaxInstFlag.
     */
    public String getRptMaxInstFlag() {
        return rptMaxInstFlag;
    }
    /**
     * @param rptMaxInstFlag The rptMaxInstFlag to set.
     */
    public void setRptMaxInstFlag(String rptMaxInstFlag) {
        this.rptMaxInstFlag = rptMaxInstFlag;
    }
    /**
     * @return Returns the rptInstMaxDaysFlag.
     */
    public String getRptInstMaxDaysFlag() {
        return rptInstMaxDaysFlag;
    }
    /**
     * @param rptInstMaxDaysFlag The rptInstMaxDaysFlag to set.
     */
    public void setRptInstMaxDaysFlag(String rptInstMaxDaysFlag) {
        this.rptInstMaxDaysFlag = rptInstMaxDaysFlag;
    }
	/**
	 * @return Returns the adminExportFlag.
	 */
	public boolean isAdminExportFlag() {
		return adminExportFlag;
	}
	/**
	 * @param adminExportFlag The adminExportFlag to set.
	 */
	public void setAdminExportFlag(boolean adminExportFlag) {
		this.adminExportFlag = adminExportFlag;
	}
	/**
	 * @return Returns the adminRunnableFlag.
	 */
	public boolean isAdminRunnableFlag() {
		return adminRunnableFlag;
	}
	/**
	 * @param adminRunnableFlag The adminRunnableFlag to set.
	 */
	public void setAdminRunnableFlag(boolean adminRunnableFlag) {
		this.adminRunnableFlag = adminRunnableFlag;
	}
	public String getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
}
