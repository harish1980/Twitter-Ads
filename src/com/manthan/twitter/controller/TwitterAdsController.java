package com.manthan.twitter.controller;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manthan.twitter.info.bo.AccountBo;
import com.manthan.twitter.info.bo.CredentialsBo;
import com.manthan.twitter.info.bo.TailoredListBo;
import com.manthan.twitter.util.DateUtil;

public class TwitterAdsController {
	
	/**
	 * Post Associating the TON File with Audience List ID.
	 * Only surest way to check if Audience is processed is to poll.
	 * Poll till we get Error - Tailored Audience has no changes.
	 *  
	 * @param credentialsBo the Twitter Credentials
	 * @param accountBo the Account Information
	 * @throws Exception
	 */
	public static void checkTailoredAudienceStatus(CredentialsBo credentialsBo, AccountBo accountBo, String tailoredAudienceId) throws Exception{
		String url = Resources.STATUS_OF_TAILORED_LIST;
		url = url.replace(":account_id", accountBo.getId()).replace(":id", tailoredAudienceId);
		boolean isComplete = true;
		while(isComplete){
			try{
				invokeGetTwitterAdApi(credentialsBo, url);
			}catch(Exception e){
				//Only on Success We get 404 as Exception
				//Poll this them
				break;
			}
		}
	}
	
	/**
	 * This method will upload Data to a Tailored List.
	 * If <64 MB then single upload else
	 * Multi Chuncked upload into TON 
	 * 
	 * @param credentialsBo the Twitter Credentials
	 * @param accountBo the Account Information
	 * @param listID the Tailored List ID
	 * @param inputFilePath the File Path
	 * @throws Exception
	 */
	public static void addToTailoredAudience(CredentialsBo credentialsBo,
			AccountBo accountBo, String listID, String inputFilePath) throws Exception{
		//Upload Single Chunk File <64MB to TON System
		String filePathInTON = "";
		if(new File(inputFilePath).length() < 64 * 1024 * 1024){
			filePathInTON = uploadSingleChunkToTON(credentialsBo, inputFilePath);
		}else{
			//MULTI PART FILE UPLOAD
			filePathInTON = uploadMultiChunkToTON(credentialsBo, inputFilePath);
		}
		String url = Resources.ADD_TO_TAILORED_LIST;
		url = url.replace(":account_id", accountBo.getId());
		url = url + "?tailored_audience_id="+listID+"&operation=ADD&input_file_path="+URLEncoder.encode(filePathInTON, StandardCharsets.UTF_8.name());
		invokePostTwitterAdApi(credentialsBo, url);
		
	}
	
	/**
	 * This method Deletes Twitter Tailored List via Ads API.
	 * 
	 * @param credentialsBo the Twitter Credentials
	 * @param accountBo the Account Information
	 * @param listId the Tailored Audience ID to Delete
	 * @throws Exception
	 */
	public static void deleteTailoredAudience(CredentialsBo credentialsBo,
			AccountBo accountBo, String listId)
			throws Exception {
		String url = Resources.DELETE_TAILORED_LIST;
		url = url.replace(":account_id", accountBo.getId());
		url = url.replace(":id", listId);
		invokeDeleteTwitterAdApi(credentialsBo, url);
	}

	/**
	 * This method will fetch Tailored Lists.
	 * 
	 * @param credentialsBo the Twitter Credentials
	 * @param accountBo the Account Information
	 * @return List of Tailored Information
	 * @throws Exception
	 */
	public static List<TailoredListBo> fetchTailoredLists(CredentialsBo credentialsBo,
			AccountBo accountBo) throws Exception{
		String url = Resources.FETCH_TAILORED_LIST;
		url = url.replace(":account_id", accountBo.getId());
		Type tailoredListType = new TypeToken<ArrayList<TailoredListBo>>(){}.getType();
		List<TailoredListBo> tailoredList = new ArrayList<TailoredListBo>();
		invokePaginationTwitterAdApi(credentialsBo, url, tailoredListType, tailoredList);
		return tailoredList;
		
	}

	/**
	 * This method creates Twitter Tailored List via Ads API.
	 * 
	 * @param credentialsBo the Twitter Credentials
	 * @param accountBo the Account Information
	 * @param listName the List Name
	 * @param listType the List Type
	 * @return Tailored List Details
	 * @throws Exception
	 */
	public static TailoredListBo createTailoredAudience(CredentialsBo credentialsBo,
			AccountBo accountBo, String listName, String listType)
			throws Exception {
		String url = Resources.CREATE_TAILORED_LIST;
		url = url.replace(":account_id", accountBo.getId());
		url = url + "?name="+URLEncoder.encode(listName, StandardCharsets.UTF_8.name())+"&list_type="+listType;
		JSONObject jsonObj = new JSONObject(invokePostTwitterAdApi(credentialsBo, url));
		return new Gson().fromJson(jsonObj.getJSONObject("data").toString(), TailoredListBo.class);
	}
	
	/**
	 * This will fetch Account Details from Twitter.
	 * This will fetch Account ID required for Subsequent API Calls for ADs API.
	 * 
	 * @param credentialsBo the Twitter Credentails
	 * @return Twitter Account Information
	 * @throws Exception
	 */
	public static AccountBo fetchAccountDetails(CredentialsBo credentialsBo) throws Exception{
        JSONObject jsonObj = new JSONObject(invokeGetTwitterAdApi(credentialsBo, Resources.FETCH_ACCOUNT_URL));
        return new Gson().fromJson(jsonObj.getJSONArray("data").get(0).toString(), AccountBo.class);
	}
	
	/**
	 * This is generic Pagination on Twitter AD API
	 * 
	 * @param credentialsBo the Twitter Credentials
	 * @param url the GET FULL URL with Cursor
	 * @return response in JSON
	 * @throws Exception
	 */
	public static void invokePaginationTwitterAdApi(CredentialsBo credentialsBo, String url, Type T, List<? extends Object> parentList) throws Exception{

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		OAuthConsumer oAuthConsumer = createOAuthConsumer(credentialsBo);
		oAuthConsumer.sign(httpGet);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		String responseString = IOUtils.toString(httpResponse.getEntity().getContent());
		if(statusCode!=HttpStatus.SC_OK){
			throw new Exception("Invalid Response from Twitter:"+responseString);
		}
		JSONObject jsonObj = new JSONObject(responseString);
		parentList.addAll(new Gson().fromJson(jsonObj.getJSONArray("data").toString(), T));
		if(jsonObj.isNull("next_cursor")){
			return;
		}else{
			String nextCursor = StringUtils.trimToNull(jsonObj.getString("next_cursor"));
			//Add Cursor to URL
			url = url +"&cursor="+nextCursor;
			invokePaginationTwitterAdApi(credentialsBo, url, T, parentList);
		}
	}
	
	/**
	 * This is generic DELETE on Twitter AD API
	 * 
	 * @param credentialsBo the Twitter Credentials
	 * @param url the DELETE FULL URL
	 * @return response in JSON
	 * @throws Exception
	 */
	public static String invokeDeleteTwitterAdApi(CredentialsBo credentialsBo, String url) throws Exception{

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpDelete httpDelete = new HttpDelete(url);
		OAuthConsumer oAuthConsumer = createOAuthConsumer(credentialsBo);
		oAuthConsumer.sign(httpDelete);
		HttpResponse httpResponse = httpClient.execute(httpDelete);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		String responseString = IOUtils.toString(httpResponse.getEntity().getContent());
		if(statusCode!=HttpStatus.SC_OK){
			throw new Exception("Invalid Response from Twitter:"+responseString);
		}
		return responseString;
	}
	
	/**
	 * This method will upload Single File <64MB to TON API.
	 * Returns the PATH of file in TON Bucket
	 *  
	 * @param credentialsBo the Twitter Account Credentials
	 * @param fileToUploadToTON the File to upload to TON
	 * @return Returns the PATH of file in TON Bucket
	 * @throws Exception
	 */
	public static String uploadSingleChunkToTON(CredentialsBo credentialsBo, String fileToUploadToTON) throws Exception{
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(Resources.UPLOAD_SINGLE_CHUNK_TO_TON);
		OAuthConsumer oAuthConsumer = createOAuthConsumer(credentialsBo);
		oAuthConsumer.sign(httpPost);
		FileEntity fileEntity = new FileEntity(new File(fileToUploadToTON), ContentType.TEXT_PLAIN);
		fileEntity.setChunked(false);
		httpPost.setEntity(fileEntity);
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-Type", ContentType.TEXT_PLAIN.getMimeType());
		//Set Expiration as 7 Days ahead
		httpPost.addHeader("x-ton-expires", DateUtil.getTONExpirationDate());
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		String responseString = IOUtils.toString(httpResponse.getEntity().getContent());
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode != HttpStatus.SC_CREATED){
			throw new Exception("Invalid Response from Twitter:"+responseString);
		}
		//From Response Header - Location contains the Actual Path in TON
		Header location = 
				Arrays.asList(httpResponse.getAllHeaders()).stream().filter(e-> "location".equalsIgnoreCase(e.getName())).findAny().orElse(null);
		if(null==location){
			throw new Exception("No Location Information from TON Twitter");
		}
		httpPost.releaseConnection();
		return location.getValue();

	}
	
	/**
	 * This method performs Resumable Chuncked File Uploads to TON End Point.
	 * 
	 * @param credentialsBo the Credentials
	 * @param inputFile the File to upload To Twitter
	 * @param tonFileLocation the TON File Path
	 * @param xTonMaxChunkSize the Chunk Size
	 * @throws Exception
	 */
	public static void resumeUploadToTON(CredentialsBo credentialsBo,
			File inputFile, String tonFileLocation, int xTonMaxChunkSize)
			throws Exception {
		HttpClient httpClient = HttpClientBuilder.create().build();
		String url = Resources.TON_BASE_URL+tonFileLocation;
		long fileLength = inputFile.length();
		OAuthConsumer oAuthConsumer = createOAuthConsumer(credentialsBo);
		FileInputStream fis = new FileInputStream(inputFile);
	    byte[] buffer = new byte[xTonMaxChunkSize]; 
	    int chunkCounter = 0;
	    String contentRange = "";
	    int read = 0;
	    int beginIndex = 0;
	    int endIndex = 0;
	    while( ( read = fis.read(buffer, 0, xTonMaxChunkSize) ) >= 0 ){
			HttpPut httpPut = new HttpPut(url);
			oAuthConsumer.sign(httpPut);
	        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(buffer, ContentType.TEXT_PLAIN);
	        byteArrayEntity.setChunked(false);
	        byteArrayEntity.setContentType(ContentType.TEXT_PLAIN.getMimeType());
			httpPut.addHeader("Content-Type", ContentType.TEXT_PLAIN.getMimeType());
		    httpPut.setEntity(byteArrayEntity);
		    endIndex = endIndex + read;
		    contentRange = "bytes "+beginIndex+"-"+(endIndex-1)+"/"+fileLength;
		    beginIndex = endIndex;
		    System.out.println("Correct One - "+"bytes "+ chunkCounter*xTonMaxChunkSize+"-"+Math.min((((chunkCounter+1)*xTonMaxChunkSize)-1),fileLength-1)+"/"+fileLength);
		    System.out.println("Match -       "+contentRange);
		    httpPut.addHeader("Content-Range", contentRange);
		    chunkCounter++;
		    HttpResponse httpResponse = httpClient.execute(httpPut);
		    int statusCode = httpResponse.getStatusLine().getStatusCode();
		    //Chunck Uploads gives 308
		    if(!(statusCode == 308 || statusCode == HttpStatus.SC_CREATED)){
				throw new Exception("Invalid Response from Twitter:");
			}
			httpPut.releaseConnection();
	    }
	    fis.close();
	}

	public static String uploadMultiChunkToTON(CredentialsBo credentialsBo, String inputFilePath) throws Exception{
		//Step 1 - Initiate Bulk Chuncked Upload - /1.1/ton/bucket/{bucket}?resumable=true
		//Step 2 - Get Location
		//Step 3 - Chucked File Post
		//Step 4 - Return TON File Location path
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(Resources.UPLOAD_CHUNKED_TO_TON);
		OAuthConsumer oAuthConsumer = createOAuthConsumer(credentialsBo);
		oAuthConsumer.sign(httpPost);
		httpPost.addHeader("Content-Type", ContentType.TEXT_PLAIN.getMimeType());
		httpPost.addHeader("x-ton-content-type", ContentType.TEXT_PLAIN.getMimeType());
		httpPost.addHeader("x-ton-content-length", new File(inputFilePath).length()+"");
		//Set Expiration as 7 Days ahead
		httpPost.addHeader("x-ton-expires", DateUtil.getTONExpirationDate());
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		String responseString = IOUtils.toString(httpResponse.getEntity().getContent());
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode != HttpStatus.SC_CREATED){
			throw new Exception("Invalid Response from Twitter:"+responseString);
		}
		//From Response Header - Location contains the Actual Path in TON
		Header location = 
				Arrays.asList(httpResponse.getAllHeaders()).stream().filter(e-> "location".equalsIgnoreCase(e.getName())).findAny().orElse(null);
		Header xTonMaxChunkSize = 
				Arrays.asList(httpResponse.getAllHeaders()).stream().filter(e-> "x-ton-max-chunk-size".equalsIgnoreCase(e.getName())).findAny().orElse(null);
		Header xTonMinChunkSize = 
				Arrays.asList(httpResponse.getAllHeaders()).stream().filter(e-> "x-ton-min-chunk-size".equalsIgnoreCase(e.getName())).findAny().orElse(null);
		if(null==location || null==xTonMaxChunkSize || null==xTonMinChunkSize){
			throw new Exception("No Valid Resumeable Information from TON Twitter");
		}
		httpPost.releaseConnection();
		
		resumeUploadToTON(credentialsBo, new File(inputFilePath), location.getValue(), Integer.parseInt(xTonMaxChunkSize.getValue()));
		
		return location.getValue();

	}

	/**
	 * This is generic POST on Twitter AD API
	 * 
	 * @param credentialsBo the Twitter Credentials
	 * @param url the POST FULL URL
	 * @return response in JSON
	 * @throws Exception
	 */
	public static String invokePostTwitterAdApi(CredentialsBo credentialsBo, String url) throws Exception{

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		OAuthConsumer oAuthConsumer = createOAuthConsumer(credentialsBo);
		oAuthConsumer.sign(httpPost);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		String responseString = IOUtils.toString(httpResponse.getEntity().getContent());
		if(statusCode!=HttpStatus.SC_CREATED){
			throw new Exception("Invalid Response from Twitter:"+responseString);
		}
		httpPost.releaseConnection();
		return responseString;
	}

	/**
	 * This is generic GET on Twitter AD API
	 * 
	 * @param credentialsBo the Twitter Credentials
	 * @param url the GET FULL URL
	 * @return response in JSON
	 * @throws Exception
	 */
	public static String invokeGetTwitterAdApi(CredentialsBo credentialsBo, String url) throws Exception{

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		OAuthConsumer oAuthConsumer = createOAuthConsumer(credentialsBo);
		oAuthConsumer.sign(httpGet);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		String responseString = IOUtils.toString(httpResponse.getEntity().getContent());
		if(statusCode!=HttpStatus.SC_OK){
			throw new Exception("Invalid Response from Twitter:"+responseString);
		}
		httpGet.releaseConnection();
		return responseString;
	}
	
	/**
	 * Creates the OAuth Consumer to Sign outbound Twitter HTTP/S Requests.
	 * 
	 * @param credentialsBo the input Credentials
	 * 
	 * @return OAuth Consumer
	 */
	private static OAuthConsumer createOAuthConsumer(CredentialsBo credentialsBo){
		OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(credentialsBo.getConsumerKey(),
				credentialsBo.getConsumerKeySecret());
		oAuthConsumer.setTokenWithSecret(credentialsBo.getAccessToken(), credentialsBo.getAccessTokenSecret());
		return oAuthConsumer;
	}
}
