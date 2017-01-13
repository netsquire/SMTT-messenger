package mtt.smtt.jsonrpc2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class JsonrpcClient {

	private static final String USER_AGENT = "Mozilla/5.0";	 
    //private static final String  GET_URL = "http://localhost:8080/entry-point/jsonrpc2";
    private static final String POST_URL = "http://localhost:8080/entry-point/jsonrpc2";
 
	/* 
    @SuppressWarnings("unused")
	private static void sendGET() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(GET_URL);
        httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
 
        System.out.println("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
 
        String inputLine;
        StringBuffer response = new StringBuffer();
 
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
 
        // print result
        System.out.println(response.toString());
        httpClient.close();
    }
 */

    private static void sendPOST() throws IOException {
 
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(POST_URL);
        httpPost.addHeader("User-Agent", USER_AGENT);
 
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("method", "directsms"));
        urlParameters.add(new BasicNameValuePair("jsonrpc", "2.0"));
        urlParameters.add(new BasicNameValuePair("params", "{\"to\": \"79587154240\", \"text\": \"Direct sms sending - 137\"}"));
        urlParameters.add(new BasicNameValuePair("id", "26"));
 
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        System.out.println("POST params" + postParams.toString());
        
        httpPost.setEntity(postParams);
        System.out.println("HTTP POST: [" + httpPost.toString() + "]");
        
        
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        System.out.println("POST Response Status: " + httpResponse.getStatusLine().getStatusCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
 
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // print result
        System.out.println("RESPONSE: " + response.toString());
        
        httpClient.close(); 
    }
 
	
    public static void main(String[] args) throws IOException {        
        sendPOST();
        System.out.println("POST DONE");
    }
}
