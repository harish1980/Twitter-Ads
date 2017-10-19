package com.manthan.twitter.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * This is Dummy Data File Creator for Twitter ADs API.
 * 
 * @author rharish
 *
 */
public class DummyFileUtil {

	private static final String inputFilePath = "D:\\Adaptors_Working\\POC\\Social Media\\TwitterAudienceAPI\\Harish100k.csv";
	//DEVICE_ID,EMAIL,HANDLE,PHONE_NUMBER,TWITTER_ID,TWITTER_COOKIE_ID
	private static final String EMAIL_TYPE = "EMAIL";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		createDummyTwitterDataFile(inputFilePath, EMAIL_TYPE);
	}
	
	public static void createDummyTwitterDataFile(String path, String type) throws Exception{
		File twitterDataFile = new File(path);
//		if(twitterDataFile.exists()){
//			twitterDataFile.delete();
//		}
//		twitterDataFile.createNewFile();
		for(int i=0;i<100000;i++){
			FileUtils.write(twitterDataFile,
					sha256(RandomStringUtils.randomAlphabetic(10).toLowerCase()
							+ "@gmail.com")
							+ "\n", StandardCharsets.UTF_8, true);
		}
	}
	
	
	private static String sha256(String message) {
	    try {
	      MessageDigest digest = MessageDigest.getInstance("SHA-256");
	      byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
	      return toHex(hash);
	    } catch(Exception e){
	      e.printStackTrace();
	      return null;
	    }
	  }

	private static String toHex(byte[] bytes) {
	    StringBuilder sb = new StringBuilder();
	    for (byte b : bytes) {
	        sb.append(String.format("%1$02x", b));
	    }
	    return sb.toString();
	  }
	//63939d46383ddf9717b9829c8e3c215b84c6f11ceedf9d451e030804df5df5fc
	//1d58ef924fc8aefc0a5cef89fd16f776f93ded16599e4fbd1402ef28cd4d530c

}
