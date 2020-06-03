/*
 * Copyright(c) 2013 Chain-Sys Corporation Inc. Duplication or distribution of
 * this code in part or in whole by any media without the express written
 * permission of Chain-Sys Corporation or its agents is strictly prohibited.
 *
 * REVISION DATE NAME DESCRIPTION 511.101 Oct 30, 2016 MDR Initial Code
 */
package com.ascap.apm.controller.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import com.ascap.apm.common.helpers.LogHelper;

/**
 * The Class LinkedinAuthenticator.
 */
public final class ApplicationAuthenticator {

	/** The Constant CLIENT_ID. */
	public static final String CLIENT_ID = "l5qcc88v0rkftkcur53dj91q5";

	/** The Constant CLIENT_SECRET. */
	public static final String CLIENT_SECRET = "m16r194e1oab97kuc51tlni2b6490p9uhlt0mce34q5tf7soh98";

	/** The Constant REDIRECT_URI. */
	public static final String REDIRECT_URI = "http://localhost:8181/apm/authenticateLogon";

	/** The Constant SCOPE. */
	public static final String SCOPE = "r_basicprofile";
	
	public static final String DOMAIN_URL = "https://ea3-employees-sbx-ascap-temp.auth.us-east-1.amazoncognito.com";

	/** The access token. */
	private String accessToken = "";

	/** The state token. */
	private String stateToken = "";

	private LogHelper log = new LogHelper(this.getClass().getName());
	
	/**
	 * Instantiates a new amazon authenticator.
	 */
	public ApplicationAuthenticator() {
		generateStateToken();
	}

	/**
	 * Gets the face book auth url.
	 *
	 * @return the face book auth url
	 */
	public String getAmazonAuthUrl() {
		String lnLoginUrl = "";
		try {
			lnLoginUrl = DOMAIN_URL + "/oauth2/authorize?client_id=" + CLIENT_ID
					+ "&scope=openid&response_type=code&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
					+ "&state=" + stateToken;
		} catch (UnsupportedEncodingException e) {
			log.debug(e);
		}
		return lnLoginUrl;
	}

	/**
	 * Generates a secure state token.
	 *
	 * @return the string
	 */
	public void generateStateToken() {
		SecureRandom sr1 = new SecureRandom();
		stateToken = "az" + sr1.nextInt();
	}

	/**
	 * Accessor for state token.
	 *
	 * @return the state token
	 */
	public String getStateToken() {
		return stateToken;
	}

	/**
	 * Gets the access token URL.
	 *
	 * @param code
	 *            the code
	 * @return the access token URL
	 */
	private String getAccessTokenURL() {
		return DOMAIN_URL + "/oauth2/token";
	}

	private String getTokenAuthorization() {
		return Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());
	}
	
	/**
	 * Gets the access token.
	 *
	 * @param code
	 *            the code
	 * @return the access token
	 */
	public String getAccessToken(String code) throws IOException {
		if ("".equals(accessToken)) {
			String params = "client_id=" + CLIENT_ID + "&redirect_uri="
					+ REDIRECT_URI + "&code=" + code + "&grant_type=authorization_code";


			byte[] paramArr = params.getBytes();
			int paramLength = paramArr.length;

			URL lnTokenURL;
			HttpURLConnection lnConnection;
			BufferedReader in = null;
			StringBuilder b = null;
			try {
				lnTokenURL = new URL(getAccessTokenURL());
				lnConnection = (HttpURLConnection) lnTokenURL.openConnection();
				lnConnection.setDoOutput(true);
				lnConnection.setDoInput(true);
				lnConnection.setRequestMethod("POST");
				lnConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				lnConnection.addRequestProperty("Content-Length", String.valueOf(paramLength));
				String authorization = "Basic " + getTokenAuthorization();
				lnConnection.addRequestProperty("Authorization", authorization);
				lnConnection.setUseCaches(false);
				try (DataOutputStream wr = new DataOutputStream(lnConnection.getOutputStream())) {
					wr.write(paramArr);
				}
				
				in = new BufferedReader(new InputStreamReader(lnConnection.getInputStream()));
				String inputLine;
				b = new StringBuilder();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				accessToken = b.toString();
			} catch (IOException e) {
				throw new IOException("Unable to connect with Cognito " + e);
			}
		}
		return accessToken;
	}

	/**
	 * Gets the user info.
	 *
	 * @param accessToken
	 *            the access token
	 * @return the user info
	 */
	public String getUserInfo(String accessToken) throws IOException {
		String urlString = DOMAIN_URL + "/oauth2/userInfo";
		URL lnTokenURL;
		String userInfo = null;
		HttpsURLConnection lnConnection;
		StringBuilder b = null;
		BufferedReader in = null;
		try {
			lnTokenURL = new URL(urlString);
			lnConnection = (HttpsURLConnection) lnTokenURL.openConnection();
			lnConnection.setDoOutput(true);
			lnConnection.setDoInput(true);
			lnConnection.setRequestMethod("GET");
			lnConnection.setRequestProperty("Authorization", " Bearer "+accessToken);
			lnConnection.setUseCaches(false);
			in = new BufferedReader(new InputStreamReader(lnConnection.getInputStream()));
			String inputLine;
			b = new StringBuilder();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			userInfo = b.toString();
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.debug(e);
				}
			}
		}
		return userInfo;
	}

}
