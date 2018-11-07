package com.bluedotinnovation.application;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bluedotinnovation.common.BDCommon;

/**
 * @author Bluedot Innovation
 * Copyright (c) 2016 Bluedot Innovation. All rights reserved.
 * Add Application Zones Sync Enabled client demonstrates adding an application to your Bluedot backend 
 * using Apache HTTP client and JSON Simple libraries for which the zones sync will be available using Firebase push.
 */

public class AddApplicationZonesSyncEnabled extends BDCommon {
    
	private static String bdRestUrl = "https://api.bluedotinnovation.com/1/applications";
	
    /**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static void main(String[] args) throws IOException, ParseException, KeyManagementException, NoSuchAlgorithmException {
		
		String bdCustomerApiKey    = "CUSTOMER_API_KEY_HERE"; //This key is generated by Bluedot Point Access UI when your account is created.

		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(getSSLContextFactory()).build();
		
		String applicationWithZonesSync = 
			"{" +
				"\"security\": {" +
					"\"customerApiKey\":" +"\"" +bdCustomerApiKey + "\"" +
				"}," +
				"\"content\": {"
					+"\"application\" : {"+
						"\"name\" : \"New Application with Zones sync enabled\"," +
						"\"packageName\": \"com.bluedot.zonesSync.enabled \"," +
						"\"nextRuleUpdateIntervalFormatted\": \"00:10\"," +
						/*The Web API key from Firebase project settings. 
						 * Using this key our backend will be able to communicate with Firebase to deliver the 
						 * zones that need to be synced to your application.*/
						"\"firebaseApiKey\": \"gDZyt1Bj_Y6S8BKyQfKH6uZ9wc475hrRlGA_6lS\"" +
					"}"+
				"}"+
			"}";
					
	    JSONObject bdApplicationJSONObject;	    
	    JSONParser parser       = new JSONParser();
	    bdApplicationJSONObject = (JSONObject)  parser.parse(applicationWithZonesSync);
		  
		HttpPost postRequest = new HttpPost(bdRestUrl);
		postRequest.addHeader("content-type", "application/json");
		postRequest.setEntity(new StringEntity(bdApplicationJSONObject.toJSONString(), Charset.defaultCharset()));
	 
	    HttpResponse response = httpRestClient.execute(postRequest);
	    
        if (response.getStatusLine().getStatusCode() == 200) {
        	System.out.println("Application with Zones Sync enabled was successfully created");
        	InputStream inputStream = response.getEntity().getContent();
        	byte[] bytes            = readStream(inputStream);
        	String resultString     = new String(bytes); //json result
        	JSONObject jsonResult   = (JSONObject)  parser.parse(resultString);
        	System.out.println("apiKey for your application is : " + jsonResult.get("apiKey"));
        } else {
        	InputStream inputStream = response.getEntity().getContent();
        	byte[] bytes            = readStream(inputStream);
        	String resultString     = new String(bytes); //json error result
        	System.out.println(resultString);
        }			
	}
}
