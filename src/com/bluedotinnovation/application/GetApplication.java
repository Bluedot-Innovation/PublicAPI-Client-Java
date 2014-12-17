/**
 * 
 */
package com.bluedotinnovation.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author Bluedot Innovation
 * Get Application java client demonstrates listing an application for given apiKey from Bluedot backend using Apache HTTP client and JSON Simple libraries.
 */


public class GetApplication {

	public static void main(String[] args) throws IOException 
	{
		String apiKey     = "d3161e80-38d1-11e4-b039-bc305bf60831";
		String customerId = "bc199c80-5441-11e4-b7bb-a0481cdc3311";
		String url        = "http://localhost:3033/1/application/get?apiKey=" +apiKey + "&customerId=" + customerId;

		HttpClient client     = new DefaultHttpClient();
		HttpGet request       = new HttpGet(url);
		HttpResponse response = client.execute(request);
		BufferedReader rd     = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		String line           = "";
		while ((line = rd.readLine()) != null) 
		{
		     System.out.println(line);
		}
	}

}
