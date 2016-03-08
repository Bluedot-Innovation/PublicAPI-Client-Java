/**
 * 
 */
package com.bluedotinnovation.action;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bluedotinnovation.common.BDCommon;

/**
 * @author Bluedot Innovation
 * Add a vibration action with all conditions
 * 
 */

public class AddVibrationActionWithConditions extends BDCommon
{

	 private static String bdCustomerApiKey    = "a6598740-75f5-11e4-86ca-a0481cdc3311"; //This key is generated by Bluedot Access UI when you register
     private static String bdApplicationApiKey = "5a9b1b78-3dd7-4f4d-8608-82408f3baf4c"; //This apiKey is generated when you create an application
     private static String bdRestUrl           = "https://api.bluedotinnovation.com/1/actions";
     private static String bdZoneId            = "b80e50eb-b9f9-4ed5-966e-4b2e39cc0549"; //This is the id of the zone being updated. This can be fetched by calling zones/getAll API

     
	public static void main(String[] args) throws IOException, ParseException, KeyManagementException, NoSuchAlgorithmException
	{		
		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(new SSLSocketFactory(getSSLContext())).build();
			
		HttpPost postRequest = new HttpPost(bdRestUrl);
  
	    JSONParser parser    = new JSONParser();
	    JSONObject bdVibrationActionJSONObject = (JSONObject) parser.parse(getJsonVibrationActionWithConditions()); //Vibration action with conditions json
				    		    
		postRequest.addHeader("content-type", "application/json");

		postRequest.setEntity(new StringEntity(bdVibrationActionJSONObject.toJSONString(), Charset.defaultCharset()));
	 
	    HttpResponse response = httpRestClient.execute(postRequest);
	    	    	    
        if (response.getStatusLine().getStatusCode() == 200)
        {
        	System.out.println("Vibration Action with conditions was added successfully");
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
	

    private static String getJsonVibrationActionWithConditions()
    {
        String vibrationActionWithCondtionsJson =
             "{" +
                "\"security\": {" +
                    "\"apiKey\":" + "\"" + bdApplicationApiKey + "\"," +
                    "\"customerApiKey\":" + "\"" + bdCustomerApiKey + "\"" +
                "}," +
                "\"content\": {" +
                    "\"zone\": {" +
                        "\"zoneId\":" + "\"" + bdZoneId + "\"," +
                        "\"actions\": {" +
                        "\"vibrationActions\": [" +
                            "{" +
                            "\"name\": \"A vibration action\"" +
                                "\"conditions\": {" +
                                    "\"percentageCrossed\":" +
                                        "[" +
                                            "{" +
                                                "\"percentage\": 50," +
                                                "\"timeoutPeriod\": \"00:05\"" +
                                            "}" +
                                        "]," +
                                    "\"dateRange\": [" +
                                        "{" +
                                            "\"start\": \"01/03/2014\"," +
                                            "\"end\": \"14/12/2014\"" +
                                        "}" +
                                    "]," +
                                    "\"timeActive\": [{" +
                                        "\"from\": {" +
                                            "\"time\": \"06:01\"," +
                                            "\"period\": \"am\" " +
                                        "}," +
                                        "\"to\": {" +
                                            "\"time\": \"11:00\"," +
                                            "\"period\": \"pm\" " +
                                        "}" +
                                    "}]" +
                                "}" +
                            "}" +
                        "]" +
                      "}" +
                    "}" +
                "}" +
            "}";

        return vibrationActionWithCondtionsJson;
    }

}
