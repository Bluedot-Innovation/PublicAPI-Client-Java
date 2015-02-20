
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
 * Get Applications java client demonstrates listing or all you applications from Bluedot backend using Apache HTTP client and JSON Simple libraries.
 */
public class GetAllApplications 
{

	public static void main(String[] args) throws IOException 
	{
		String customerApiKey = "ca4c8d11-6942-11e4-ba4b-a0481cdc3311";
		String url            = "https://api.bluedotinnovation.com/1/applications?customerApiKey=" + customerApiKey;

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
