package mtt.smxp;

import java.io.FileNotFoundException;
import java.io.IOException;

import mtt.smtt.Application;

import org.smpp.Connection;
import org.smpp.Data;
import org.smpp.Receiver;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.TimeoutException;
import org.smpp.Transmitter;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.Address;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.EnquireLink;
import org.smpp.pdu.EnquireLinkResp;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.Response;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.ValueNotSetException;
import org.smpp.smscsim.DeliveryInfoSender;
import org.smpp.smscsim.PDUProcessor;
import org.smpp.smscsim.PDUProcessorGroup;
import org.smpp.smscsim.SMSCListenerImpl;
import org.smpp.smscsim.SMSCSession;
import org.smpp.smscsim.SMSCSessionImpl;
import org.smpp.smscsim.ShortMessageStore;
import org.smpp.smscsim.SimulatorPDUProcessorFactory;
import org.smpp.smscsim.util.Table;

public class SmppGate {

	Connection connection;
	//Transmitter transmitter = new Transmitter(connection);
	Session session;
	BindRequest breq;	
	Receiver receiver;

	Response resp = null;
	boolean isConnected = true;
	
	/*
	String systemType = "";
	String serviceType = "";
	Address destAddress = new Address();
	String scheduleDeliveryTime = "";
	String validityPeriod = "";
	String shortMessage = "";
	int numberOfDestination = 1;
	String messageId = "";
	*/	
	//byte priorityFlag = 0;
	//byte registeredDelivery = 0;
	//byte replaceIfPresentFlag = 0;
	//byte smDefaultMsgId = 0;
	Address sourceAddress = new Address();
	String sender = "Test97";
	byte esmClass = 0;
	byte protocolId = 0;
	byte dataCoding = 8;
	
	public SmppGate() throws FileNotFoundException, IOException, ValueNotSetException, TimeoutException, PDUException, WrongSessionStateException{
		System.out.print("Starting SMSC listener... ");

		ShortMessageStore messageStore = new ShortMessageStore();
		PDUProcessorGroup processors = new PDUProcessorGroup();			
		DeliveryInfoSender deliveryInfoSender = new DeliveryInfoSender();
		deliveryInfoSender.start();		
		String usersFileName = "config/users.txt";
		Table users = new Table(usersFileName);		
		NcPduProcessorFactory factory = new NcPduProcessorFactory(processors, messageStore, deliveryInfoSender, users);
		factory.setDisplayInfo(true);
		
		connection = new SmppConnection();
		session = new Session(connection);
		String port = Application.getConfiguration().getProperty("SmppPort");
		//System.out.println("SMPP port (gate): " + port);
		int smscPort = /* 2775; */ Integer.parseInt( port );
		SMSCListenerImpl smscListener = new SMSCListenerImpl( smscPort, true);
		smscListener.setPDUProcessorFactory(factory);
		smscListener.start();

		breq = new BindTransmitter();
		//breq.setSystemId("Test97");	

		System.out.println(" Bind Transmitter System Id (gate): " + breq.getSystemId());		
		breq.setSystemId("0FFCeK6L");		
		breq.setPassword("leVjicPE");		
		//params.put("login", "0FFCeK6L");
		//params.put("password", "leVjicPE");		

		try {
			resp = session.bind(breq);
			//session.unbind();
		} catch (java.net.ConnectException e) {
			// e.printStackTrace();
			isConnected = false;
			System.out.println("Check SMSC connection!");
		}
		
		//smscPing("Hello, SMPP world!");
		System.out.println("started.");
	}
	
	/*
	public void setTentacles(Transmitter transmitter, Connection connection, Receiver receiver) {
		this.transmitter = transmitter;
		this.connection = connection;
		this.receiver = receiver;
	}	
	*/
	
	public synchronized void smscPing(String shortMessage) 
			throws 
				ValueNotSetException, TimeoutException, PDUException, WrongSessionStateException, IOException {
		/*
		Response resp = null;
		boolean isConnected = true;
		try {
			resp = session.bind(breq);
		} catch (java.net.ConnectException e) {
			// e.printStackTrace();
			isConnected = false;
			System.out.println("Check SMSC connection!");
		}
		*/
		
		//resp = session.bind(breq);
		//if (isConnected) {
			//if (resp.getCommandStatus() == Data.ESME_ROK) {
				
				SubmitSM msg = new SubmitSM();
				byte senderTon =  (byte) 0; // 0
				byte senderNpi =  (byte) 0; // 0
				
				if(sender != null) {
					if(sender.startsWith("+")) {
						sender = sender.substring(1);
						senderTon = 1;
						senderNpi = 1;
					}
					if(!sender.matches("\\d+")) {
						senderTon = 5;
						senderNpi = 0;
					}
						
					if(senderTon == 5) {
						msg.setSourceAddr(new Address(senderTon, senderNpi, sender, 11));
					} else {
						msg.setSourceAddr(new Address(senderTon, senderNpi, sender));
					}
				} else {
					msg.setSourceAddr(sourceAddress);
				}								
				msg.setSourceAddr( senderTon, senderNpi, "Test97");
				System.out.println("Message source address = "	+ msg.getSourceAddr().toString());
								
				msg.setDestAddr("79587154240");
				//int dataCoding = Integer.parseInt(Application.getConfigurationProperty("datacoding"));
				msg.setDataCoding((byte) dataCoding);
				msg.setShortMessage("[" + shortMessage + " (new way > через  SMPP)] - " + System.currentTimeMillis(), Data.ENC_UTF16);
				msg.setEsmClass(esmClass);
				msg.setProtocolId(protocolId);
				
				System.out.println("Message source address = "	+ msg.getSourceAddr().toString());
				//System.out.println("Message OriginalRequest CommandStatus = "	+ resp.getOriginalRequest().getCommandStatus());
				resp = session.submit(msg);
				if (resp.getCommandStatus() == Data.ESME_ROK) {
					System.out.println("Message submitted. Status="	+ resp.getCommandStatus());
				} else {
					System.out.println("Message submission failed. Status="	+ resp.getCommandStatus());
				}
				//session.unbind();
	/*		
	} else {
				System.out.println("Couldn't bind. Status="	+ resp.getCommandStatus());
			}
		}
		*/
	}

	private void enquireLink() {
		try {
			EnquireLink request = new EnquireLink();
			EnquireLinkResp response;
			System.out.println("Enquire Link request " + request.debugString());
			response = session.enquireLink(request);
			System.out.println("Enquire Link response " + response.debugString());
		} catch (Exception e) {
			System.out.println("Enquire Link operation failed. " + e);
		}
	}
	
}
