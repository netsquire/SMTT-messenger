package mtt.smtt.model;

import javax.jms.Message;
import javax.jms.ObjectMessage;

public class ProcessingResults {
	
	private Message message;
	private Boolean success;
	
	public ProcessingResults(ObjectMessage outMessage, boolean b) {
		this.message = outMessage;
		this.success = b;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
}
