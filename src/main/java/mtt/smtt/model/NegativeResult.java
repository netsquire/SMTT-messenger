package mtt.smtt.model;

import mtt.smtt.model.Error;
import mtt.smtt.jsonrpc2.JsonResponse;

public class NegativeResult extends JsonResponse { 

	Error error;
	
	public NegativeResult(Error _error){
		this.error = _error;
	}

	public NegativeResult(String _string) {
		this();
		error.setMessage(_string);
	}

	public NegativeResult() {
		error = new Error();
	}

	public NegativeResult(String message, int code) {
		this(message);
		error.setCode(code);
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
}
