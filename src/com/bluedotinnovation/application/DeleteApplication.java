package com.bluedotinnovation.application;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.bluedotinnovation.common.BDCommon;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Bluedot Innovation
 * Delete Application REST client demonstrates deleting an application using Apache HTTP client libraries
 */
public class DeleteApplication extends BDCommon {
	
    public static void main(String[] args) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException {
    	
        String bdCustomerApiKey = "7cd1ea80-d40e-11e4-84cb-b8ca3a6b879d";
        String bdApplicationId  = "7acadbf2-4424-4d3b-b4ec-98fad0a6618b";
        String bdRestUrl        = "https://api.bluedotinnovation.com/1/applications?customerApiKey="+bdCustomerApiKey+"&applicationId=" + bdApplicationId;
        
        CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(getSSLContextFactory()).build();
        HttpDelete request    = new HttpDelete(bdRestUrl);
        HttpResponse response = httpRestClient.execute(request);
        JSONParser parser     = new JSONParser();

        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("Application has been deleted successfully");
            InputStream inputStream = response.getEntity().getContent();
            byte[] bytes            = readStream(inputStream);
            String resultString     = new String(bytes); //json result
            JSONObject jsonResult   = (JSONObject)  parser.parse(resultString);
            System.out.println(jsonResult);
        } else {
            InputStream inputStream = response.getEntity().getContent();
            byte[] bytes            = readStream(inputStream);
            String resultString     = new String(bytes); //json error result
            System.out.println(resultString);
        }
    }
}
