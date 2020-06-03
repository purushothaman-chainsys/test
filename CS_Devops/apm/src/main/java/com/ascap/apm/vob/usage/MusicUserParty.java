package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

/**
 * 
 * @author Manoj_Puli
 *
 */
public class MusicUserParty extends BaseVOB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4988608193687995988L;
	
	private String partyId;
	private String callLetter;
	private String callLetterSuffix;
	private String licenseType;
	private String musicUserTypeCode;
	private String musicUserTypeDes;
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MusicUserParty [partyId=");
		builder.append(partyId);
		builder.append(", callLetter=");
		builder.append(callLetter);
		builder.append(", callLetterSuffix=");
		builder.append(callLetterSuffix);
		builder.append(", licenseType=");
		builder.append(licenseType);
		builder.append(", musicUserTypeCode=");
		builder.append(musicUserTypeCode);
		builder.append(", musicUserTypeDes=");
		builder.append(musicUserTypeDes);
		builder.append("]");
		return builder.toString();
	}


	public String getPartyId() {
		return partyId;
	}


	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}


	public String getCallLetter() {
		return callLetter;
	}


	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}


	public String getCallLetterSuffix() {
		return callLetterSuffix;
	}


	public void setCallLetterSuffix(String callLetterSuffix) {
		this.callLetterSuffix = callLetterSuffix;
	}


	public String getLicenseType() {
		return licenseType;
	}


	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}


	public String getMusicUserTypeCode() {
		return musicUserTypeCode;
	}


	public void setMusicUserTypeCode(String musicUserTypeCode) {
		this.musicUserTypeCode = musicUserTypeCode;
	}


	public String getMusicUserTypeDes() {
		return musicUserTypeDes;
	}


	public void setMusicUserTypeDes(String musicUserTypeDes) {
		this.musicUserTypeDes = musicUserTypeDes;
	}

}
