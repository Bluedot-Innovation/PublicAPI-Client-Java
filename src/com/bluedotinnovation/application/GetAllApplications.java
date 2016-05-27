package com.bluedotinnovation.application;

import java.io.BufferedReader;
import java.io.IOException;
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
 * Get Applications java client demonstrates listing or all you applications from Bluedot backend using Apache HTTP client and JSON Simple libraries.
 */
public class GetAllApplications extends BDCommon {

	public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		
		String bdCustomerApiKey 	= "7cd1ea80-d40e-11e4-84cb-b8ca3a6b879d";
		String bdRestUrl            = "https://api.bluedotinnovation.com/1/applications?customerApiKey=" + bdCustomerApiKey;

		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(getSSLContextFactory()).build();
		HttpGet request       = new HttpGet(bdRestUrl);
		HttpResponse response = httpRestClient.execute(request);
		BufferedReader rd     = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		JSONParser parser          = new JSONParser();
		String bdApplicationJSON = "";
		while ((bdApplicationJSON = rd.readLine()) != null) {
			Object object = parser.parse(bdApplicationJSON);
			JSONArray bdApplicationJsonArray = (JSONArray) object;
			for (Object applicationObject : bdApplicationJsonArray){
			    JSONObject jsonObject =  (JSONObject) applicationObject;
			    System.out.println("App name : " + jsonObject.get("name"));
			    System.out.println("App id: " + jsonObject.get("_id"));
			    System.out.println("App apiKey: " + jsonObject.get("apiKey"));
			    System.out.println("App packageName: " + jsonObject.get("packageName"));
			    System.out.println("App Ruleset download interval: " + jsonObject.get("nextRuleUpdateIntervalFormatted"));
			    System.out.println("---------\n");
			}
		}
	}
}
