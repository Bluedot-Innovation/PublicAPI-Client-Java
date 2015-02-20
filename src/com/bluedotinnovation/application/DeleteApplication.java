package com.bluedotinnovation.application;

import java.io.IOException;
import java.io.InputStream;

import com.bluedotinnovation.common.BDCommon;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Bluedot Innovation
 * Delete Application REST client demonstrates deleting an application using Apache HTTP client libraries
 */
public class DeleteApplication extends BDCommon{
    public static void main(String[] args) throws ParseException, IOException{
        String customerApiKey = "0cbfd210-9544-11e4-b884-402cf464abb8";
        String applicationId  = "7b45e5b1-4464-43ae-902b-34fcb63d8a7b";
        String url            = "https://api.bluedotinnovation.com/1/applications?customerApiKey="+customerApiKey+"&applicationId=" + applicationId;
        HttpClient client     = new DefaultHttpClient();
        HttpDelete request    = new HttpDelete(url);
        HttpResponse response = client.execute(request);
        JSONParser parser     = new JSONParser();

        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("Application has been deleted successfully");
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
