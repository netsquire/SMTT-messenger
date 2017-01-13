package mtt.smtt.model;

import mtt.smtt.model.Result;

public class Result {	//"result": {"taskId":123456, "status":"ok" }
	
	String code;
	String status;
	String taskId = "000";
	
	public Result(String code, String status,	String taskId) {
		this.code = code;
		this.status = status;
		this.taskId = taskId;
	}

	public Result positive(String code, String status) {
		this.code = code;
		this.status = status;
		return this;
	}

	public Result negative(String error, String code, String status) {
		this.code = code;
		this.status = status;
		return this;
	}
	
	public Result() {}
	
	public Result(String taskId) {
		this.taskId = taskId;
	}
	
	public Result(int taskId) {
		this.taskId = taskId + "";
	}
	
	public Result(String _string, int _code) {
		this();
		positive(_code+"", _string);
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	//public static void main(String[] args) {}

}
