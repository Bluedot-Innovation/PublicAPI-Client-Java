package com.bluedotinnovation.fence;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.bluedotinnovation.common.BDCommon;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Bluedot Innovation
 * Copyright (c) 2016 Bluedot Innovation. All rights reserved.
 * Delete Fence client demonstrates deleting a fence using Apache HTTP client libraries
 */

public class DeleteFence extends BDCommon {
    public static void main(String[] args) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException {
        
    	String bdCustomerApiKey 	= "CUSTOMER_API_KEY_HERE"; //This key is generated by Bluedot Point Access UI when your account is created.
        String bdApplicationApiKey 	= "APPLICATION_API_KEY_HERE"; //This apiKey is generated when you create an application
        String bdZoneId         	= "ZONE_ID_HERE"; //This is the id of the zone being updated. This can be fetched by calling GET Zones API
        String bdFenceId        	= "FENCE_ID_HERE"; //This is the id of the fence being deleted. This can be fetched by calling GET Zones API
        String bdRestUrl            = "https://api.bluedotinnovation.com/1/fences?customerApiKey="+bdCustomerApiKey+ "&apiKey=" + bdApplicationApiKey +
        		"&zoneId=" + bdZoneId + "&fenceId=" + bdFenceId;
        
        CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(getSSLContextFactory()).build();
        HttpDelete request    = new HttpDelete(bdRestUrl);
        HttpResponse response = httpRestClient.execute(request);
        JSONParser parser     = new JSONParser();

        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("Fence has been deleted successfully");
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
}
