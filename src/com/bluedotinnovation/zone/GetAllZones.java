/**
 * 
 */
package com.bluedotinnovation.zone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Bluedot Innovation
 * Get zones client demonstrates the listing of zone names and ids for a given customer
 */
public class GetAllZones 
{

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException
	{
		String bdCustomerApiKey    = "ca4c8d11-6942-11e4-ba4b-a0481cdc3311"; //This key is generated by Bluedot Access UI when you register
		String bdRestUrl           = "https://api.bluedotinnovation.com/1/zones?customerApiKey=" +bdCustomerApiKey;  //If the application apiKey is not passed the service will return all zones as json arrays grouped by the application, for the given customer
		HttpClient httpRestClient  = new DefaultHttpClient();
		HttpGet request            = new HttpGet(bdRestUrl);
		HttpResponse httpResponse  = httpRestClient.execute(request);
		BufferedReader rd          = new BufferedReader (new InputStreamReader
				                    (httpResponse.getEntity().getContent()));
		JSONParser parser          = new JSONParser();
		String bdZonesJson;
		
		while ((bdZonesJson = rd.readLine()) != null) 
		{
		     Object object                    = parser.parse(bdZonesJson);
		     JSONArray bdZonesJsonArry        = (JSONArray) object;
		     JSONArray jsonObjectArray        = (JSONArray) bdZonesJsonArry.get(0);
		     JSONObject applicationObject     = (JSONObject) jsonObjectArray.get(0);
		     /*Zone name and id of the first zone from the array returned*/
		     System.out.println("Zone name : " + applicationObject.get("name") + " Zone id: " + applicationObject.get("_id"));
		}

	}

}
