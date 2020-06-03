/*
 * Created on Nov 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ascap.apm.vob.reports;

import java.util.List;

import com.ascap.apm.vob.BaseSearchVOB;

/**
 * @author jason_emeric
 * @company ASCAP
 * 
 * Created on Nov 16, 2007, 4:46:50 PM
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportRequest extends BaseSearchVOB {

	/**
     * 
     */
    private static final long serialVersionUID = -7018521125272978177L;



    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ("****ReportRequest   >> \n" +
				"****requestId       >> " + requestId + "\n" +
				"****userId          >> " + userId + "\n" +
				"****fname           >> " + fname + "\n" +
				"****lname           >> " + lname + "\n" +
				"****reportId        >> " + reportId + "\n" +
				"****reportDesc      >> " + reportDesc + "\n" +
				"****status          >> " + status + "\n" +				
				"****statusCode      >> " + statusCode + "\n" +
				"****reportReqDesc   >> " + reportReqDesc + "\n" + 
				"****reportReqCreDate>> " + reportReqCreDate + "\n"	+
				"****reportParams    >> " + reportParams + "\n" 
					
		);
		
		
		
	}
	
	private long requestId;
	private String userId;
	private String fname;
	private String lname;
	private String reportId;
	private String status;
	private String statusCode;
	private String reportReqDesc;
	private String reportDesc;
	private String reportReqCreDate;
	private List<Object> reportParams;
	

	
	/**
	 * @return Returns the reportId.
	 */
	public String getReportId() {
		return reportId;
	}
	/**
	 * @param reportId The reportId to set.
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
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
	 * @return Returns the reportParams.
	 */
	public List<Object> getReportParams() {
		return reportParams;
	}
	/**
	 * @param reportParams The reportParams to set.
	 */
	public void setReportParams(List<Object> reportParams) {
		this.reportParams = reportParams;
	}
	/**
	 * @return Returns the statusCode.
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode The statusCode to set.
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return Returns the reportDesc.
	 */
	public String getReportDesc() {
		return reportDesc;
	}
	/**
	 * @param reportDesc The reportDesc to set.
	 */
	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}
	/**
	 * @return Returns the reportReqDesc.
	 */
	public String getReportReqDesc() {
		return reportReqDesc;
	}
	/**
	 * @param reportReqDesc The reportReqDesc to set.
	 */
	public void setReportReqDesc(String reportReqDesc) {
		this.reportReqDesc = reportReqDesc;
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
	
}
