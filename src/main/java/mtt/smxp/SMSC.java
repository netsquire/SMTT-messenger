package mtt.smxp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URLEncoder;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import mtt.note.smxp.SMSC;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class SMSC {	
	
	private final String USER_AGENT = "Mozilla/5.0";

	private String sendHttpsGet (String url) throws ClientProtocolException, IOException {        
    	CloseableHttpClient client = HttpClientBuilder.create().build();
    	CookieHandler.setDefault(new CookieManager());
    	HttpGet request = new HttpGet(url);
    	request.setHeader("Connection", "keep-alive");
	   	request.setHeader("Accept-Language", "en-US,en;q=0.5");
    	request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    	request.setHeader("User-Agent", USER_AGENT);	
    	
    	System.out.println("\n Requested URL: " + url);
    	System.out.println("Request: " + request.toString());
    	
    	HttpResponse response = client.execute(request);
    	
    	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));         	
    	StringBuffer taskId = new StringBuffer();
    	String line = "";
    	while ((line = rd.readLine()) != null) {
    		taskId.append(line);
    	}
    	return taskId.toString();
	}
	 
	private String createUrl(String host, Map<String, String> props){
		String separator = "";
		StringBuffer sb = new StringBuffer(host);
		sb.append("?");
		Iterator<Entry<String, String>> it = props.entrySet().iterator();
		while (it.hasNext()){
			Entry<String, String> en = it.next();
			sb.append(separator);
			if(separator.equals("")){
				separator = "&"; 
				}
			sb.append(en.getKey());
			sb.append("=");
			sb.append(en.getValue());
			}
		return sb.toString();
	}
	
	public String checkStatus(String taskId) throws ClientProtocolException, IOException{		
		String status = "unknown";
		Map<String, String> params = new HashMap<String, String>();		
		params.put("login", "0FFCeK6L");
		params.put("password", "leVjicPE");
		params.put("id", taskId);
		params.put("operation", "status");
		String host = "https://httpsms.mtt.ru/send";
		String url = createUrl(host, params);
		status = sendHttpsGet(url);
		return status ;
	}
	
	public synchronized String pushSMS(String text, String msisdn) {		
		String host = "https://httpsms.mtt.ru/send";
		String url = "";
		Map<String, String> params = new HashMap<String, String>();				
		params.put("login", "0FFCeK6L");
		params.put("password", "leVjicPE");
		params.put("msisdn", msisdn );
		params.put("shortcode", "Test97");
		params.put("operation", "send");		

		String ret = "";
		try {
			text = URLEncoder.encode(text, "UTF-8");			
			params.put("text", text);		
			url = createUrl(host, params);
			ret = sendHttpsGet(url);
			System.out.println("task ID: [" + ret + "]");
		} catch (IOException e1) {	e1.printStackTrace();}
		return ret;
	}
	
	public String  mainFunc(String text, String msisdn) throws ClientProtocolException, IOException, InterruptedException{
		String taskId = pushSMS(text + "(pushSMS, old way)", msisdn);
		System.out.println("task ID: [" + taskId + "]");
		
		String status = checkStatus(taskId);
		System.out.println("Delivering Status (1): '" + status + "'");

		Thread.sleep(5000);
		status = checkStatus(taskId);
		System.out.println("Delivering Status (2): '" + status + "'");
		return taskId;
	}

	// ========================================================
	
	
	
	@SuppressWarnings("unused")
	private void print_https_cert(HttpsURLConnection con){		 
	    if(con!=null){	 
			try {
				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("Headers : ");
				Map<String, List<String>> headers = con.getHeaderFields();
				Iterator<String> hit = headers.keySet().iterator();
				while(hit.hasNext()) {
					String key = hit.next();
					System.out.println(" --> " + key + " : " + headers.get(key));
				}

				Certificate[] certs = con.getServerCertificates();
				for (Certificate cert : certs) {
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : " 	  + cert.getPublicKey().getFormat());
				}
			} catch (SSLPeerUnverifiedException e) { e.printStackTrace();
		} catch (IOException e){ e.printStackTrace(); }
	     }
	   }

	
	@SuppressWarnings("unused")
	private void listHeaders(HttpGet httpget) {
		System.out.println("Headers ");
		Header[] headers = httpget.getHeaders("");
		for(Header h : headers){
			System.out.println("Header: " + h);
		}
	}


}
