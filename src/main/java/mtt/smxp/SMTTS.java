package mtt.smxp;

import java.io.IOException;

import org.smpp.pdu.PDUException;
import org.smpp.pdu.Request;
import org.smpp.pdu.Response;
import org.smpp.smscsim.PDUProcessor;
import org.smpp.smscsim.PDUProcessorFactory;
import org.smpp.smscsim.SMSCListener;

public class SMTTS {

	public class Listener implements SMSCListener{

		public Listener(int httpPort, boolean b) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public long getAcceptTimeout() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setAcceptTimeout(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setPDUProcessorFactory(PDUProcessorFactory arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void start() throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void stop() throws IOException {
			// TODO Auto-generated method stub
			
		}}
	
	public class PduProcessor extends PDUProcessor{

		@Override
		public void clientRequest(Request arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void clientResponse(Response arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void serverRequest(Request arg0) throws IOException,
				PDUException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void serverResponse(Response arg0) throws IOException,
				PDUException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void stop() {
			// TODO Auto-generated method stub
			
		}

		
		}
	
	
}
