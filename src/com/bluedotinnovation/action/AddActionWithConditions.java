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
 * Add Action with conditions REST client demonstrating an action with conditions to an existing zone using JSON simple and Apache HTTP client libraries
 */
public class AddActionWithConditions extends BDCommon
{
	private static String bdCustomerApiKey     = "ca4c8d11-6942-11e4-ba4b-a0481cdc3311"; //This key is generated by Bluedot Access UI when you register
	private static String bdApplicationApiKey  = "f5223f4c-a0f3-47be-ba59-080b6290a9d4"; //This apiKey is generated when you create an application
    private static String bdRestUrl           = "https://api.bluedotinnovation.com/1/actions";
	private static String bdZoneId            = "dfafb7fd-d2f7-42ec-afd5-d5c7851c0396"; //This is the id of the zone being updated. This can be fetched by calling zones/getAll API
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static void main(String[] args) throws  IOException, ParseException, KeyManagementException, NoSuchAlgorithmException
	{
		
		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(new SSLSocketFactory(getSSLContext())).build();
		
		HttpPost postRequest = new HttpPost(bdRestUrl);
  
	    JSONParser parser    = new JSONParser();
	    JSONObject bdMessageActionWithCondtionsJSONObject = (JSONObject) parser.parse(getJsonMessageActionWithConditions()); //action with conditions json
				    		    
		postRequest.addHeader("content-type", "application/json");

		postRequest.setEntity(new StringEntity(bdMessageActionWithCondtionsJSONObject.toJSONString(), Charset.defaultCharset()));
	 
	    HttpResponse response = httpRestClient.execute(postRequest);
	    	    	    
        if (response.getStatusLine().getStatusCode() == 200)
        {
        	System.out.println("Message action and conditions were added to your zone successfully");
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

    /**
     * Example of Url Action.
     * @return
     */
	private static String getJsonURLActionWithConditions()
	{
		String urlActionWithCondtionsJson =
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
	                            "\"conditions\": {" +
	                                "\"percentageCrossed\":"+
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
	                                        "\"period\": \"pm\" "+ 
	                                    "}" +
	                                "}]" +
	                            "}" +
	                        "}" +
	                    "]" +
	                  "}" +
	                "}" +
	            "}" +
	        "}";
		
		return urlActionWithCondtionsJson;
	}

    /**
     * Example of Message Action.
     * @return
     */
	private static String getJsonMessageActionWithConditions()
	{
		String messageActionWithCondtionsJson =
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
	                        "\"name\": \"Welcome to MCG\"," +
                            "\"title\": \"MCG Welcome Message\"," +
                            "\"message\": \"Welcome to MCG\"" +
	                            "\"conditions\": {" +
	                                "\"percentageCrossed\":"+
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
	                                        "\"period\": \"pm\" "+ 
	                                    "}" +
	                                "}]" +
	                            "}" +
	                        "}" +
	                    "]" +
	                  "}" +
	                "}" +
	            "}" +
	        "}";
		
		return messageActionWithCondtionsJson;
	}

    /**
     * Example of Vibration Action.
     * @return
     */
	private static String getJsonVibrationActionWithConditions()
	{
		String vibrationActionWithCondtionsJson =
			 "{" +
	            "\"security\": {" +
	                "\"apiKey\":" + "\"" + bdApplicationApiKey +"\"," +
	                "\"customerApiKey\":" +"\"" + bdCustomerApiKey + "\""+
	            "}," +
	            "\"content\": {" +
	                "\"zone\": {" +
	                    "\"zoneId\":"+ "\"" + bdZoneId +"\"," +
	            	    "\"actions\": {" +
	        	        "\"vibrationActions\": [" +
	                        "{" +
	                        "\"name\": \"A vibration action\"" +
	                            "\"conditions\": {" +
	                                "\"percentageCrossed\":"+
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
	                                        "\"period\": \"pm\" "+ 
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
