package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseSearchVOB;

/**
 * 
 * @author Manoj_Puli
 *
 */
public class MusicUserSearch extends BaseSearchVOB {

	private static final long serialVersionUID = -9142064500952725432L;

	private String operationType;
	
	private String filterCallLetter;
	private String filterCallLetterSuffix;
	private String filterLicenseType;
	private String filterMusicUserType;
	private String filterEffectiveDate;
	
	
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getFilterCallLetter() {
		return filterCallLetter;
	}
	public void setFilterCallLetter(String filterCallLetter) {
		this.filterCallLetter = filterCallLetter;
	}
	public String getFilterCallLetterSuffix() {
		return filterCallLetterSuffix;
	}
	public void setFilterCallLetterSuffix(String filterCallLetterSuffix) {
		this.filterCallLetterSuffix = filterCallLetterSuffix;
	}
	public String getFilterLicenseType() {
		return filterLicenseType;
	}
	public void setFilterLicenseType(String filterLicenseType) {
		this.filterLicenseType = filterLicenseType;
	}
	public String getFilterMusicUserType() {
		return filterMusicUserType;
	}
	public void setFilterMusicUserType(String filterMusicUserType) {
		this.filterMusicUserType = filterMusicUserType;
	}
	public String getFilterEffectiveDate() {
		return filterEffectiveDate;
	}
	public void setFilterEffectiveDate(String filterEffectiveDate) {
		this.filterEffectiveDate = filterEffectiveDate;
	}
   
}
