/*
 * Created on Nov 19, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ascap.apm.vob.reports;

import java.util.List;

/**
 * @author jason_emeric
 * @company ASCAP
 * 
 * Created on Nov 19, 2007, 9:15:01 AM
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportRequestList extends Report {
	
	/**
     * 
     */
    private static final long serialVersionUID = -6558941503754281207L;



    public String toString() {
		// TODO Auto-generated method stub
		return ("****ReportRequest>> \n" +
				"****requestId>> " + requestId + "\n" +
				"****userId>> " + userId + "\n" +
				"****userId>> " + fname + "\n" +
				"****userId>> " + lname + "\n" +
				"****reportId>> " + super.getReportID() + "\n" +
				"****status>> " + status + "\n" +
				"****reportReqCreDate>> " + reportReqCreDate + "\n" +
				"****desc>> " + reportDesc + "\n"
		
		
		);
		
		
		
	}
	
	private long requestId;
	private String userId;
	private String fname;
	private String lname;
	private List<Object> reportRequestList;
	private String status;
	private String reportDesc;
	private String reportReqCreDate;
	
	

	/**
	 * @return Returns the desc.
	 */
	public String getReportDesc() {
		return reportDesc;
	}
	/**
	 * @param desc The desc to set.
	 */
	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}
	
	/**
	 * @return Returns the requestId.
	 */
	public long getRequestId() {
		return requestId;
	}
	/**
	 * @param requestId The requestId to set.
	 */
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return Returns the reportRequestList.
	 */
	public List<Object> getReportRequestList() {
		return reportRequestList;
	}
	/**
	 * @param reportRequestList The reportRequestList to set.
	 */
	public void setReportRequestList(List<Object> reportRequestList) {
		this.reportRequestList = reportRequestList;
	}
	/**
	 * @return Returns the fname.
	 */
	public String getFname() {
		return fname;
	}
	/**
	 * @param fname The fname to set.
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	/**
	 * @return Returns the lname.
	 */
	public String getLname() {
		return lname;
	}
	/**
	 * @param lname The lname to set.
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	/**
	 * @return Returns the reportReqCreDate.
	 */
	public String getReportReqCreDate() {
		return reportReqCreDate;
	}
	/**
	 * @param reportReqCreDate The reportReqCreDate to set.
	 */
	public void setReportReqCreDate(String reportReqCreDate) {
		this.reportReqCreDate = reportReqCreDate;
	}
}
