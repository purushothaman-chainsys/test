package com.ascap.apm.vob.reports.rest;

public class Logon {
	
	private String userName;
	private String password;
	private String auth;
	private String logonToken;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Logon [userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append("***********");
		builder.append(", auth=");
		builder.append(auth);
		builder.append(", logonToken=");
		builder.append(logonToken);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the auth
	 */
	public String getAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}

	/**
	 * @return the logonToken
	 */
	public String getLogonToken() {
		return logonToken;
	}

	/**
	 * @param logonToken the logonToken to set
	 */
	public void setLogonToken(String logonToken) {
		this.logonToken = logonToken;
	}
}
