package com.bluedotinnovation.action;

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
 * Delete Ation REST client demonstrates deleting an action using Apache HTTP client libraries
 */
public class DeleteAction extends BDCommon{
    public static void main(String[] args) throws ParseException, IOException{
        String customerApiKey = "0cbfd210-9544-11e4-b884-402cf464abb8";
        String zoneId         = "d6c3b688-cf2e-4aac-b76b-b29f371f448e";
        String actionId       = "8613a1ed-e1bd-4017-9bac-37100ebb4e00";
        String url            = "https://api.bluedotinnovation.com/1/actions?customerApiKey="+customerApiKey+"&zoneId=" + zoneId + "&actionId=" + actionId;
        HttpClient client     = new DefaultHttpClient();
        HttpDelete request    = new HttpDelete(url);
        HttpResponse response = client.execute(request);
        JSONParser parser     = new JSONParser();

        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("Action has been deleted successfully");
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
