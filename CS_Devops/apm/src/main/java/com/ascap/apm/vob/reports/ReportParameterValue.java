/*
 * Created on Nov 26, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ascap.apm.vob.reports;

import com.ascap.apm.vob.BaseSearchVOB;

/**
 * @author jason_emeric
 * @company ASCAP
 * 
 * Created on Nov 26, 2007, 10:31:10 AM
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportParameterValue extends BaseSearchVOB {
	
	/**
     * 
     */
    private static final long serialVersionUID = -7212849913063834690L;


    public String toString() {
		return ("****ReportRequestParameter>> \n" +
				"****rptPrmValSelId        >> " + rptPrmValSelId + "\n" +
				"****rptPrmVal             >> " + rptPrmVal + "\n" +
				"****rptPrmSelId           >> " + rptPrmSelId + "\n"
				
		);}
		
	//private String reportParamValId;
	//private String reportParamVal;
	
	private long rptPrmValSelId;
	private long rptPrmSelId;
	private String rptPrmVal;

	
	/**
	 * @return Returns the rptPrmSelId.
	 */
	public long getRptPrmSelId() {
		return rptPrmSelId;
	}
	/**
	 * @param rptPrmSelId The rptPrmSelId to set.
	 */
	public void setRptPrmSelId(long rptPrmSelId) {
		this.rptPrmSelId = rptPrmSelId;
	}
	/**
	 * @return Returns the rptPrmVal.
	 */
	public String getRptPrmVal() {
		return rptPrmVal;
	}
	/**
	 * @param rptPrmVal The rptPrmVal to set.
	 */
	public void setRptPrmVal(String rptPrmVal) {
		this.rptPrmVal = rptPrmVal;
	}
	/**
	 * @return Returns the rptPrmValSelId.
	 */
	public long getRptPrmValSelId() {
		return rptPrmValSelId;
	}
	/**
	 * @param rptPrmValSelId The rptPrmValSelId to set.
	 */
	public void setRptPrmValSelId(long rptPrmValSelId) {
		this.rptPrmValSelId = rptPrmValSelId;
	}
}
