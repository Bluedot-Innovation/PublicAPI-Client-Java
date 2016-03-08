/**
 * 
 */
package com.bluedotinnovation.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.bluedotinnovation.common.BDCommon;

/**
 * @author Bluedot Innovation
 * Get Application java client demonstrates listing an application for given apiKey from Bluedot backend using Apache HTTP client and JSON Simple libraries.
 */


public class GetApplication extends BDCommon
{

	public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException 
	{
		String applicaitonId     = "a4e5b264-d231-436b-b30e-4065bd517f02";
		String customerApiKey = "86577370-7b91-11e4-bcb7-a0481cdc3311";
		String url        = "https://api.bluedotinnovation.com/1/applications?customerApiKey=" +customerApiKey + "&applicaitonId=" + applicaitonId;

		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(new SSLSocketFactory(getSSLContext())).build();
		HttpGet request       = new HttpGet(url);
		HttpResponse response = httpRestClient.execute(request);
		BufferedReader rd     = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		String line           = "";
		while ((line = rd.readLine()) != null) 
		{
		     System.out.println(line);
		}
	}

}
