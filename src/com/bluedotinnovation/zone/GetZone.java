package com.bluedotinnovation.zone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bluedotinnovation.common.BDCommon;

/**
 * @author Bluedot Innovation
 * Copyright (c) 2016 Bluedot Innovation. All rights reserved.
 * Get Zone client demonstrates the listing of zone details for a given customer's zoneId using Apache HTTP client and JSON Simple libraries.
 */
public class GetZone extends BDCommon {

	public static void main(String[] args) throws IOException, ParseException, KeyManagementException, NoSuchAlgorithmException
	{
		String bdCustomerApiKey    = "76e1ae30-c616-11e5-a7c0-b8ca3a6b879d"; //This key is generated by Bluedot Access UI when you register
		String bdApplicationApiKey = "dee11930-ebff-11e5-8e27-bc305bf60831"; //This apiKey is generated when you create an application
		String bdZoneId 		   = "67f99448-a646-43c9-a6ae-0d823d65edbd"; //This is the id of the zone being retrieved
		String bdRestUrl           = "https://api.bluedotinnovation.com/1/zones?customerApiKey=" +bdCustomerApiKey + "&apiKey=" + bdApplicationApiKey + "&zoneId=" + bdZoneId; 
		
		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(getSSLContextFactory()).build();
			
		HttpGet request            = new HttpGet(bdRestUrl);
		HttpResponse httpResponse  = httpRestClient.execute(request);
		
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			BufferedReader rd          = new BufferedReader (new InputStreamReader (httpResponse.getEntity().getContent()));
			JSONParser parser          = new JSONParser();
			String bdZonesJson		   = "";
			while ((bdZonesJson = rd.readLine()) != null) {
				JSONObject jsonZoneObject = (JSONObject) parser.parse(bdZonesJson);
				System.out.println("Zone name : " + jsonZoneObject.get("zoneName"));
				System.out.println("Zone id: " + jsonZoneObject.get("zoneId"));
			}
		} else {
			InputStream inputStream = httpResponse.getEntity().getContent();
        	byte[] bytes            = readStream(inputStream);
        	String resultString     = new String(bytes); //json error result
        	System.out.println(resultString);
		}
	}

}
