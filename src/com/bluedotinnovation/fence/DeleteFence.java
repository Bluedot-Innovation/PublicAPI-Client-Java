package com.bluedotinnovation.fence;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.bluedotinnovation.common.BDCommon;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Bluedot Innovation
 * Delete Fence REST client demonstrates deleting a fence using Apache HTTP client libraries
 */
public class DeleteFence extends BDCommon{
    public static void main(String[] args) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException{
        String customerApiKey = "0cbfd210-9544-11e4-b884-402cf464abb8";
        String zoneId         = "d6c3b688-cf2e-4aac-b76b-b29f371f448e";
        String fenceId        = "2cf924f0-f9a0-4a5a-abf6-4fc18a22db56";
        String url            = "https://api.bluedotinnovation.com/1/fences?customerApiKey="+customerApiKey+"&zoneId=" + zoneId + "&fenceId=" + fenceId;
        CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(new SSLSocketFactory(getSSLContext())).build();
        HttpDelete request    = new HttpDelete(url);
        HttpResponse response = httpRestClient.execute(request);
        JSONParser parser     = new JSONParser();

        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("Fence has been deleted successfully");
            InputStream inputStream = response.getEntity().getContent();
            byte[] bytes            = readStream(inputStream);
            String resultString     = new String(bytes); //json result
            JSONObject jsonResult   = (JSONObject)  parser.parse(resultString);
            System.out.println(jsonResult);
        }else{
            InputStream inputStream = response.getEntity().getContent();
            byte[] bytes            = readStream(inputStream);
            String resultString     = new String(bytes); //json error result
            System.out.println(resultString);
        }
    }
}
