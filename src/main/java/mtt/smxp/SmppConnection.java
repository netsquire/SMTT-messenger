package mtt.smxp;

import mtt.smtt.Application;

import org.smpp.TCPIPConnection;

public class SmppConnection extends TCPIPConnection{

	static String smscHost = "95.169.99.106" ; //Application.getConfiguration().getProperty("SmppHost");
	static int smscPort = 2775; //Integer.parseInt( Application.getConfiguration().getProperty("SmppPort"));
	
	public SmppConnection() {
		super(smscHost, smscPort);
		//new TCPIPConnection("95.169.99.106", 2775);
	}

}
