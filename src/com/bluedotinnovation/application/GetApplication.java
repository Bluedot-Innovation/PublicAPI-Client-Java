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
		String applicationId     = "a4e5b264-d231-436b-b30e-4065bd517f02";
		String customerApiKey = "86577370-7b91-11e4-bcb7-a0481cdc3311";
		String url        = "https://api.bluedotinnovation.com/1/applications?apiKey=" +customerApiKey + "&applicationId=" + applicationId;

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
