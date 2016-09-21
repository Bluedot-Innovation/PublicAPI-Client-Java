package com.bluedotinnovation.action;

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
 * Update Message Action client demonstrates updating an existing message action to an existing zone using JSON simple and Apache HTTP client libraries.
 */
public class UpdateMessageAction extends BDCommon {
	
	private static String bdCustomerApiKey    = "ca4c8d11-6942-11e4-ba4b-a0481cdc3311"; //This key is generated by Bluedot Point Access UI when your account is created.
	private static String bdApplicationApiKey = "f5223f4c-a0f3-47be-ba59-080b6290a9d4"; //This apiKey is generated when you create an application
	private static String bdZoneId            = "dfafb7fd-d2f7-42ec-afd5-d5c7851c0396"; //This is the id of the zone being updated. This can be fetched by calling GET Zones API
	private static String bdActionId          = "26111887-4b82-42fd-a316-3ef1826208d5"; //This is the id of the action being updated. This can be fetch by calling GET Zones
    private static String bdRestUrl           = "https://api.bluedotinnovation.com/1/actions";
	
	public static void main(String[] args) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException {
		
		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(getSSLContextFactory()).build();
  
	    JSONParser parser    = new JSONParser();
	    JSONObject bdMessageActionJSONObject = (JSONObject) parser.parse(getJsonMessageAction()); //message action json
				    		    
		HttpPost postRequest = new HttpPost(bdRestUrl);
		postRequest.addHeader("content-type", "application/json");
		postRequest.setEntity(new StringEntity(bdMessageActionJSONObject.toJSONString(), Charset.defaultCharset()));
	 
	    HttpResponse response = httpRestClient.execute(postRequest);
	    	    	    
        if (response.getStatusLine().getStatusCode() == 200) {
        	System.out.println("Message action was added to your zone successfully");
        	InputStream inputStream = response.getEntity().getContent();
        	byte[] bytes            = readStream(inputStream);
        	String resultString     = new String(bytes); //json result
        	JSONObject jsonResult   = (JSONObject)  parser.parse(resultString);
        	System.out.println(jsonResult);
        } else {
        	InputStream inputStream = response.getEntity().getContent();
        	byte[] bytes            = readStream(inputStream);
        	String resultString     = new String(bytes); //json error result
        	System.out.println(resultString);
        }			
	}
	
	private static String getJsonMessageAction() {
		
		String messageActionJson =
			 "{" +
	            "\"security\": {" +
	                "\"apiKey\":" + "\"" + bdApplicationApiKey +"\"," +
	                "\"customerApiKey\":" +"\"" + bdCustomerApiKey + "\""+
	            "}," +
	            "\"content\": {" +
	                "\"zone\": {" +
	                    "\"zoneId\":"+ "\"" + bdZoneId +"\"," +
	                    "\"actions\": {" +
	                        "\"messageActions\": [" +
	                            "{" +
	                              "\"actionId\":"+ "\"" + bdActionId +"\"," +
	                                "\"title\": \"MCG Welcome Message Updated\"," +
	                                "\"message\": \"Welcome to MCG Updated Message\"" +
	                            "}" +
	                        "]" +
	                    "}" +
	                "}" +
	            "}" +
	        "}";		
		return messageActionJson;
	}
}
