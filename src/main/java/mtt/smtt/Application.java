package mtt.smtt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.smpp.Connection;
import org.smpp.Receiver;
import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.ValueNotSetException;
import org.smpp.smscsim.DeliveryInfoSender;
import org.smpp.smscsim.PDUProcessor;
import org.smpp.smscsim.PDUProcessorGroup;
import org.smpp.smscsim.SMSCListener;
import org.smpp.smscsim.SMSCListenerImpl;
import org.smpp.smscsim.SMSCSession;
import org.smpp.smscsim.SMSCSessionImpl;
import org.smpp.smscsim.ShortMessageStore;
import org.smpp.smscsim.SimulatorPDUProcessorFactory;
import org.smpp.smscsim.util.Table;

import com.google.gson.Gson;

import mtt.smtt.control.ServiceLine;
import mtt.smtt.jsonrpc2.JsonRequest;
import mtt.smtt.model.WorkOrder;
import mtt.reactor.Reactor;
import mtt.smxp.NcPduProcessor;
import mtt.smxp.NcPduProcessorFactory;
import mtt.smxp.Postal;
import mtt.smxp.SMSC;
import mtt.smxp.SMTTS;
import mtt.smxp.SmppGate;
import mtt.smtt.templates.TemplateEngine;

public class Application {

	final static Logger logger = Logger.getLogger(Application.class);
	private static String propFileName = "config/nc.properties";
	private static AppConfiguration configuration = new AppConfiguration(propFileName);
	private static int httpPort = 8080; 
	private static int smscPort = 2775; 
		
	private static AtomicInteger aId = new AtomicInteger(0);
	private static String salt; 
	private static Map<String, WorkOrder> infoTable; // for storing statuses
	private static Reactor reactor1;
	private static Reactor reactor2;
	private static ServiceLine serviceLine; // -> WorkFlow
	private static List<String> validMethods; 	
	private static Gson gson; // = new Gson();
	private static Boolean scheduled = false;
	private static TemplateEngine templateEngine;
	private static Postal postal; // = new Postal();
	private static SMSC smsPoint; // = new SMSC();
	SMSCListener listener;
	Receiver receiver;
	//static Type jsonRequestType = new TypeToken<JsonRequest>() {}.getType();

	SMTTS root;
	public Application() throws FileNotFoundException, IOException, ValueNotSetException, TimeoutException, PDUException, WrongSessionStateException, InterruptedException{		
		// +++ Monitoring		
		// +++ Logging		
		
		if (configuration == null) {
			System.out.println("Configuration is zero again");
			}
		
		root = new SMTTS();
		postal   = new Postal();
		smsPoint = new SMSC();
		//smsPoint.mainFunc("Дериватив, презерватив, предотвратив", "79587154240");
		
		SmppGate smppGate = new SmppGate();
		smppGate.smscPing("Launch+Ping> " + System.currentTimeMillis());
		gson = new Gson();
		infoTable = new ConcurrentHashMap<>();
		new WebHandle().up();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ValueNotSetException, TimeoutException, PDUException, WrongSessionStateException, InterruptedException {				
		System.out.println("Start Application");		
		System.out.println("SMTP Host: " + getConfigurationProperty("SmtpHost"));
		new Application();	
	}

	public SMTTS getRoot() {
		return root;
	}

	public void setRoot(SMTTS fabric) {
		this.root = fabric;
	}

	public static String getConfigurationProperty(String key){
		AppConfiguration conf = getConfiguration();
		if (conf == null) {
			System.out.println("Configuration is zero");
		}
		return conf.getProperty(key);
	}

	public static List<String> getValidMethods() {
		return validMethods;
	}

	public static void setValidMethods(List<String> validMethods) {
		Application.validMethods = validMethods;
	}
	

	public static Reactor getService(String serviceName){
		// service is Reactor 
		return serviceLine.get(serviceName);
	}
	

	public static Map<String, Reactor> getServiceLine() {
		return serviceLine;
	}


	public static void setServiceLine(ServiceLine serviceLine) {
		Application.serviceLine = serviceLine;
	}


	public static void setReactor1(Reactor reactor1) {
		Application.reactor1 = reactor1;
	}


	public static void setReactor2(Reactor reactor2) {
		Application.reactor2 = reactor2;
	}

	
	public String getStatus(String id){
		return infoTable.get(id).getStatus();
	}

	public void setStatus(String id, String status){
		infoTable.get(id).setStatus(status);
	}


	public /* static */ Postal getPostal() {
		return postal;
	}

	@SuppressWarnings("static-access")
	public /* static */ void setPostal(Postal postal) {
		this.postal = postal;
	}

	public static SMSC getSmsPoint() {
		return smsPoint;
	}


	@SuppressWarnings("static-access")
	public /* static */ void setSmsPoint(SMSC smsc) {
		this.smsPoint = smsc;
	}


	public static TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public static void setTemplateEngine(TemplateEngine templateEngine) {
		Application.templateEngine = templateEngine;
	}

	

	private static int getAId() {
		return aId.incrementAndGet();
	}
	
	public static String getSalt() {
		return salt;
	}

	public static synchronized String getUniqueId(){
		return new StringBuffer(getAId() + salt).toString();
	}
	
	public static Map<String, WorkOrder> getInfoTable() {
		return infoTable;
	}

	

	public static Boolean getScheduled() {
		return scheduled;
	}

	public static void setScheduled(Boolean scheduled) {
		Application.scheduled = scheduled;
	}

	public static Gson getGson() {
		return gson;
	}

	public static void setGson(Gson gson) {
		Application.gson = gson;
	}

	/*
	public static PropertiesManagement getProperties() {
		return properties;
	}
	public static void setProperties(PropertiesManagement properties) {
		Application.properties = properties;
	}
	*/

	public static Reactor getReactor1() {
		return reactor1;
	}

	public static Reactor getReactor2() {
		return reactor2;
	}
	
	public static String getMethod(String queryString) {			
		JsonRequest jsonRequest = (JsonRequest) getGson().fromJson(queryString, getConfiguration().getJsonRequestType());
		return jsonRequest.getMethod();
	}
	
	public static String getId(String queryString) {
		JsonRequest jsonRequest = (JsonRequest) getGson().fromJson(queryString, getConfiguration().getJsonRequestType());
		return jsonRequest.getId();
	}
	

	public static int getHttpPort() {
		return httpPort;
	}


	public static void setHttpPort(int httpPort) {
		Application.httpPort = httpPort;
	}

	public static AppConfiguration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(AppConfiguration configuration) {
		Application.configuration = configuration;
	}

	
	
}

