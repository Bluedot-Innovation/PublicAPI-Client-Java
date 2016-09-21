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
 * Add URL Action client demonstrates adding a URL action to an existing zone using JSON simple and Apache HTTP client libraries.
 */

public class AddURLAction extends BDCommon {

	private static String bdCustomerApiKey    = "ca4c8d11-6942-11e4-ba4b-a0481cdc3311"; //This key is generated by Bluedot Point Access UI when your account is created.
	private static String bdApplicationApiKey = "f5223f4c-a0f3-47be-ba59-080b6290a9d4"; //This apiKey is generated when you create an application
	private static String bdZoneId            = "dfafb7fd-d2f7-42ec-afd5-d5c7851c0396"; //This is the id of the zone being updated. This can be fetched by calling GET Zones API
    private static String bdRestUrl           = "https://api.bluedotinnovation.com/1/actions";
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static void main(String[] args) throws IOException, ParseException, KeyManagementException, NoSuchAlgorithmException {
		
		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(getSSLContextFactory()).build();
		  
	    JSONParser parser    = new JSONParser();
	    JSONObject bdUrlActionJSONObject = (JSONObject) parser.parse(getJsonUrlAction()); //url action json

	    HttpPost postRequest = new HttpPost(bdRestUrl);
		postRequest.addHeader("content-type", "application/json");
		postRequest.setEntity(new StringEntity(bdUrlActionJSONObject.toJSONString(), Charset.defaultCharset()));
	 
	    HttpResponse response = httpRestClient.execute(postRequest);
	    	    	    
        if (response.getStatusLine().getStatusCode() == 200) {
        	System.out.println("URL action was added to your zone successfully");
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
	
	
	private static String getJsonUrlAction() {
		
		String urlActionJson =
			 "{" +
	            "\"security\": {" +
	                "\"apiKey\":" + "\"" + bdApplicationApiKey +"\"," +
	                "\"customerApiKey\":" +"\"" + bdCustomerApiKey + "\""+
	            "}," +
	            "\"content\": {" +
	                "\"zone\": {" +
	                    "\"zoneId\":"+ "\"" + bdZoneId +"\"," +
	                    "\"actions\": {" +
	                        "\"urlActions\": [" +
	                            "{" +
	                                "\"name\": \"Bluedot URL\"," +
	                                "\"url\": \"http://www.bluedotinnovation.com\"" +
	                            "}" +
	                        "]" +
	                    "}" +
	                "}" +
	            "}" +
	        "}";		
		return urlActionJson;
	}
}
