/*
 * Created on Nov 26, 2007
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
 * Created on Nov 26, 2007, 9:36:16 AM
 *
 */
public class ReportParameter extends BaseSearchVOB {

	
	/**
     * 
     */
    private static final long serialVersionUID = 3833306907261516703L;

    public String toString() {
		return ("****ReportRequestParameter >> \n" +
				"****rptPrmCde              >> " + rptPrmCde + "\n" +
				"****rptPrmName             >> " + rptPrmName + "\n" +
				"****rptPrmDatTypCde        >> " + rptPrmDatTypCde + "\n" +
				"****rptPrmDatTypName       >> " + rptPrmDatTypName + "\n" +
				"****rptPrmSelId            >> " + rptPrmSelId + "\n" +
				"****rptPrmValues           >> " + rptPrmValues + "\n" 
		);
		
		
		
	}	
	
	private long rptPrmSelId;
	private long rptReqId;
	private String rptPrmCde;
	private String rptPrmName;
	private String rptPrmDatTypCde;
	private String rptPrmDatTypName;
	private List<Object> rptPrmValues;	
	
	/**
	 * @return Returns the rptPrmCde.
	 */
	public String getRptPrmCde() {
		return rptPrmCde;
	}
	/**
	 * @param rptPrmCde The rptPrmCde to set.
	 */
	public void setRptPrmCde(String rptPrmCde) {
		this.rptPrmCde = rptPrmCde;
	}
	/**
	 * @return Returns the rptPrmDatTypCde.
	 */
	public String getRptPrmDatTypCde() {
		return rptPrmDatTypCde;
	}
	/**
	 * @param rptPrmDatTypCde The rptPrmDatTypCde to set.
	 */
	public void setRptPrmDatTypCde(String rptPrmDatTypCde) {
		this.rptPrmDatTypCde = rptPrmDatTypCde;
	}
	/**
	 * @return Returns the rptPrmDatTypName.
	 */
	public String getRptPrmDatTypName() {
		return rptPrmDatTypName;
	}
	/**
	 * @param rptPrmDatTypName The rptPrmDatTypName to set.
	 */
	public void setRptPrmDatTypName(String rptPrmDatTypName) {
		this.rptPrmDatTypName = rptPrmDatTypName;
	}
	/**
	 * @return Returns the rptPrmName.
	 */
	public String getRptPrmName() {
		return rptPrmName;
	}
	/**
	 * @param rptPrmName The rptPrmName to set.
	 */
	public void setRptPrmName(String rptPrmName) {
		this.rptPrmName = rptPrmName;
	}
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
	 * @return Returns the rptPrmValues.
	 */
	public List<Object> getRptPrmValues() {
		return rptPrmValues;
	}
	/**
	 * @param rptPrmValues The rptPrmValues to set.
	 */
	public void setRptPrmValues(List<Object> rptPrmValues) {
		this.rptPrmValues = rptPrmValues;
	}
	/**
	 * @return Returns the rptReqId.
	 */
	public long getRptReqId() {
		return rptReqId;
	}
	/**
	 * @param rptReqId The rptReqId to set.
	 */
	public void setRptReqId(long rptReqId) {
		this.rptReqId = rptReqId;
	}
}
