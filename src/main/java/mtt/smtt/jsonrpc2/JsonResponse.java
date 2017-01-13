package mtt.smtt.jsonrpc2;

import mtt.smtt.jsonrpc2.JsonBasic;

public class JsonResponse extends JsonBasic {
	
	String id;
	//String taskId;
	
	public JsonResponse(){}

	/*
	public JsonResponse(String _taskId) {
		this.taskId = _taskId;
	}
	*/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	} 
	*/
	
}
