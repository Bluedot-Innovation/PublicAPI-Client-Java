/**
 * 
 */
package com.bluedotinnovation.beacon;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bluedotinnovation.common.BDCommon;

public class AddBeacon extends BDCommon{

	private static String bdCustomerApiKey    = "7cd1ea80-d40e-11e4-84cb-b8cb3a6b879d"; //This key is generated by Bluedot Access UI when you register
	private static String bdApplicationApiKey = "afc346a0-de5e-11e4-af33-b8cb3a6b879d"; //This apiKey is generated when you create an application
	
	private static String bdRestUrl           = "https://api.bluedotinnovation.com/1/beacons";
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws ParseException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static void main(String[] args) throws ParseException, ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException{
		
		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(new SSLSocketFactory(getSSLContext())).build();
		HttpPost postRequest = new HttpPost(bdRestUrl);
		
		JSONParser parser    = new JSONParser();
		JSONObject bdBothBeaconJSONObject = (JSONObject) parser.parse(getJsonBeacon()); //add an Both type beacon
		
		postRequest.addHeader("content-type", "application/json");

		postRequest.setEntity(new StringEntity(bdBothBeaconJSONObject.toJSONString()));
		
		HttpResponse response = httpRestClient.execute(postRequest);
		
		if (response.getStatusLine().getStatusCode() == 200)
        {
        	System.out.println("Beacon was successfully created");
        	InputStream inputStream = response.getEntity().getContent();
        	byte[] bytes            = readStream(inputStream);
        	String resultString     = new String(bytes); //json result
        	JSONObject jsonResult   = (JSONObject)  parser.parse(resultString);
        	System.out.println(jsonResult);
        }
        else 
        {
        	InputStream inputStream = response.getEntity().getContent();
        	byte[] bytes            = readStream(inputStream);
        	String resultString     = new String(bytes); //json error result
        	System.out.println(resultString);
        }
	}

	private static String getJsonBeacon() {
		
		String beaconJson =  
				"{" +
		           "\"security\": {" +
		            "\"apiKey\":"+ "\"" + bdApplicationApiKey +"\","+
		            "\"customerApiKey\":" + "\"" + bdCustomerApiKey + "\" "+
		        "}," + 
		        "\"content\": {" +
		            "\"beacon\": {" +
		               "\"name\": \"Bluedot building\"," +
					   "\"proximityUUID\": \"f7826da6-4fa2-4e98-8024-bc4b71e0894e\"," +
					   "\"longitude\": \"123.34455\"," +
					   "\"latitude\": \"47.777888\"," +
					   "\"type\":  \"Both\"," +
					   "\"major\": 12," +
					   "\"minor\": 13," +
					   "\"macAddress\": \"01:17:C5:31:84:21\"," +
					   "\"txPower\": -77," +
					   "\"description\": \"Sample description\"," +
		            "}"+
		        "}"+
		     "}";
				
		return beaconJson;
	}

}