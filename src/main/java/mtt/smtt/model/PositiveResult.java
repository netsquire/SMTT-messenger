package mtt.smtt.model;

import mtt.smtt.model.Result;
import mtt.smtt.jsonrpc2.JsonResponse;

public class PositiveResult extends JsonResponse {

	Result result = new Result("Success", 200);
	//String taskId = "000";
	
	public PositiveResult(Result _result){
		this.result = _result;
	}

	public PositiveResult(String string) {
		super();
		result.setStatus(string);
	}

	public PositiveResult(int _code) {
		super();
		result.setCode(_code+"");
	}

	
	public PositiveResult(String _taskId, String _string) {
		this(_string);
		//this.taskId = _taskId;
		this.result.setTaskId(_taskId);
	}
	
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
