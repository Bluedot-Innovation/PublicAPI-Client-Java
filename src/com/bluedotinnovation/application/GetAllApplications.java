
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
 * Get Applications java client demonstrates listing or all you applications from Bluedot backend using Apache HTTP client and JSON Simple libraries.
 */
public class GetAllApplications extends BDCommon
{

	public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException 
	{
		String bdCustomerApiKey = "7cd1ea80-d40e-11e4-84cb-b8ca3a6b879d";
		String bdRestUrl            = "https://api.bluedotinnovation.com/1/applications?customerApiKey=" + bdCustomerApiKey;

		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(new SSLSocketFactory(getSSLContext())).build();
		HttpGet request       = new HttpGet(bdRestUrl);
		HttpResponse response = httpRestClient.execute(request);
		BufferedReader rd     = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		String line           = "";
		while ((line = rd.readLine()) != null) 
		{
		     System.out.println(line);
		}
	}

}
