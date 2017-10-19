package com.manthan.twitter.info.bo;

/**
 * This class holds Twitter ADs API Credential Information.
 * 
 * @author rharish
 *
 */
public class CredentialsBo {

	private String consumerKey;
	private String consumerKeySecret;
	private String accessToken;
	private String accessTokenSecret;
	
	/**
	 * This is constructor.
	 * 
	 * @param consumerKey the Consumer Key
	 * @param consumerKeySecret the Consumer Secret
	 * @param accessToken the Access Token
	 * @param accessTokenSecret the Access Token Secret
	 */
	public CredentialsBo(String consumerKey, String consumerKeySecret, String accessToken, String accessTokenSecret) {
		this.consumerKey = consumerKey;
		this.consumerKeySecret = consumerKeySecret;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
	}

	/**
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * @param consumerKey the consumerKey to set
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * @return the consumerKeySecret
	 */
	public String getConsumerKeySecret() {
		return consumerKeySecret;
	}

	/**
	 * @param consumerKeySecret the consumerKeySecret to set
	 */
	public void setConsumerKeySecret(String consumerKeySecret) {
		this.consumerKeySecret = consumerKeySecret;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the accessTokenSecret
	 */
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	/**
	 * @param accessTokenSecret the accessTokenSecret to set
	 */
	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}
	
}
