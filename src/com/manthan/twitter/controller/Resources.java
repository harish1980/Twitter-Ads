package com.manthan.twitter.controller;

/**
 * This interface contains List of Resource URLs of Twitter API.
 * 
 * @author rharish
 *
 */
public interface Resources {

	String FETCH_ACCOUNT_URL = "https://ads-api.twitter.com/2/accounts";
	String CREATE_TAILORED_LIST = "https://ads-api.twitter.com/2/accounts/:account_id/tailored_audiences";
	String FETCH_TAILORED_LIST = "https://ads-api.twitter.com/2/accounts/:account_id/tailored_audiences";
	String DELETE_TAILORED_LIST = "https://ads-api.twitter.com/2/accounts/:account_id/tailored_audiences/:id";
	
	String ADD_TO_TAILORED_LIST = "https://ads-api.twitter.com/2/accounts/:account_id/tailored_audience_changes";
	String STATUS_OF_TAILORED_LIST = "https://ads-api.twitter.com/2/accounts/:account_id/tailored_audience_changes/:id";
	
	String TON_BASE_URL = "https://ton.twitter.com";
	String UPLOAD_SINGLE_CHUNK_TO_TON = TON_BASE_URL+"/1.1/ton/bucket/ta_partner";
	String UPLOAD_CHUNKED_TO_TON = TON_BASE_URL+"/1.1/ton/bucket/ta_partner?resumable=true";
}
