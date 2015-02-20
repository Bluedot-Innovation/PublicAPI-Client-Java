package com.bluedotinnovation.checkin;

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
 * 
 * CheckInActivity client demonstrates fetching checkIn activities for a given zone.
 * 100 CheckIn activities are returned per call, increment the pagenumber parameter to get records corresponding to the page
 * This api also accepts a date range as parameter(optional) where records within the start date and the end date range are returned
 */

public class BDCheckinActivityClient 
{
    private static String bdCustomerApiKey    = "bc199c80-5441-11e4-b7bb-a0481cdc3311"; //This key is generated by Bluedot Access UI when you register
    private static String bdZoneId            = "24d9a245-2087-421b-9972-2af2ee0970f1"; //This is the id of the zone being updated. This can be fetched by calling zones/getAll API
    private static String startDate           = "11/9/2014";
    private static String endDate             = "14/9/2014";
    private static String bdRestBaseUrl       = "https://api.bluedotinnovation.com/1/checkinactivities";

	public static void main(String[] args) throws IOException, ParseException
	{
		getCheckInActivitiesByZoneAndDateRange();
		getAllCheckInActivitesByZone();
	}

    public static void getCheckInActivitiesByZoneAndDateRange() throws IOException, ParseException
    {
		String bdRestUrl           = bdRestBaseUrl + "?simpleCheckInZoneId=" + bdZoneId + "&customerApiKey=" + bdCustomerApiKey + "&startdate=" + startDate + "&enddate=" + endDate + "&pagenumber=0";
		HttpClient httpRestClient  = new DefaultHttpClient();
		HttpGet request            = new HttpGet(bdRestUrl);
		HttpResponse httpResponse  = httpRestClient.execute(request);
		BufferedReader rd          = new BufferedReader (new InputStreamReader
				                    (httpResponse.getEntity().getContent()));
		JSONParser parser          = new JSONParser();
		String bdChecInRecords;
		
		while ((bdChecInRecords = rd.readLine()) != null) 
		{
		     Object object                = parser.parse(bdChecInRecords);
		     JSONArray bdCheckInJsonArry  = (JSONArray) object;
		     
		     for (Object checkInObject : bdCheckInJsonArry) 
		     {
		    	 JSONObject jsonObject        = (JSONObject) checkInObject;
		    	 
			     /*Print checkIn records returned*/			     
			     System.out.println("_id : " + jsonObject.get("_id").toString());
			     System.out.println("creationTime : " +  jsonObject.get("creationTime"));
			     
			     JSONObject notificationRoot     = (JSONObject) jsonObject.get("notification");
			     
			     JSONObject notificationData     = (JSONObject) notificationRoot.get("d");
			     
			     System.out.println("zoneId :  " + notificationData.get("zoneId"));
			     
			     System.out.println("zoneName :  " + notificationData.get("zoneName"));
			     System.out.println("speed : " + notificationData.get("speed"));
			     System.out.println("deviceType : " + notificationData.get("deviceType"));
			     System.out.println("bluedotId : " + notificationData.get("bluedotId")); // This is a fully randomised id and the real application id or device id is never stored in the db
			     System.out.println("---------\n");
			}
		    
		}
    }
	
    public static void getAllCheckInActivitesByZone() throws IOException, ParseException
    {
    	String bdRestUrl           = bdRestBaseUrl + "?simpleCheckInZoneId=" + bdZoneId + "&customerApiKey=" + bdCustomerApiKey + "&pagenumber=0";
		HttpClient httpRestClient  = new DefaultHttpClient();
		HttpGet request            = new HttpGet(bdRestUrl);
		HttpResponse httpResponse  = httpRestClient.execute(request);
		BufferedReader rd          = new BufferedReader (new InputStreamReader
				                    (httpResponse.getEntity().getContent()));
		JSONParser parser          = new JSONParser();
		String bdChecInRecords;
		
		while ((bdChecInRecords = rd.readLine()) != null) 
		{
		     Object object                = parser.parse(bdChecInRecords);
		     JSONArray bdCheckInJsonArry  = (JSONArray) object;
		     
		     for (Object checkInObject : bdCheckInJsonArry) 
		     {
		    	 JSONObject jsonObject = (JSONObject) checkInObject;
		    	 
			     /*Print checkIn records returned*/			     
			     System.out.println("_id : " + jsonObject.get("_id").toString());
			     System.out.println("creationTime : " + jsonObject.get("creationTime"));
			     
			     JSONObject notificationRoot = (JSONObject) jsonObject.get("notification");
			     
			     JSONObject notificationData = (JSONObject) notificationRoot.get("d");
			     
			     System.out.println("zoneId : " + notificationData.get("zoneId"));
			     
			     System.out.println("zoneName : " + notificationData.get("zoneName"));
			     System.out.println("speed : " + notificationData.get("speed"));
			     System.out.println("deviceType : " +  notificationData.get("deviceType"));
			     System.out.println("bluedotId : " +  notificationData.get("bluedotId")); // This is a fully randomised id and the real application id or device id is never stored in the db
			     System.out.println("---------\n");
			}		    
		}
    }
}
