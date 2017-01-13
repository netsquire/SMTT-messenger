package mtt.smtt;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import mtt.smtt.Application;
import mtt.smtt.jsonrpc2.JsonResponse;
import mtt.smtt.model.NegativeResult;
import mtt.smtt.model.PositiveResult;
import mtt.smtt.model.WorkOrder;

import com.google.gson.JsonObject;
 
@Path("/entry-point")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EntryPoint {
 
    public EntryPoint() {}

	@GET
    @Path("status/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String statusSms(@PathParam("taskId") String taskId) {
    	System.out.println("\n-- Request for SMS delivering status --" );
    	JsonObject answer = new JsonObject();
    	String status = "0000";
    	String ret = "zero";
    	try {
			status = Application.getInfoTable().get(taskId).getStatus();
		} catch (NullPointerException npe) {
			ret = " Status: empty. Check condition!";
			System.out.println(ret);
		}
		System.out.println(" (" + taskId + ") -> [ " + status + " ]");
		answer.addProperty(taskId, status);		
        ret = Application.getGson().toJson(answer);
        System.out.println(" RETURN (Status SMS) Status: " + ret);
        return ret;
    }
			
	// POST(JSON-RPC2) IN, JSON-RPC2 OUT
	// --> {"jsonrpc": "2.0", "method": "directsms", "params": {"to": "765688367", "text": "bla-bla-bla-sms"}, "id": 4}
	// <-- {"jsonrpc": "2.0", "result": {"taskId":123456, "status":"ok" }, "id": 4}	
	@POST
	@Path("jsonrpc2")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String workJsonRpc2 (String queryString)	{		
		String orderId = Application.getUniqueId();
		@SuppressWarnings("unused")
		Boolean result = true;
		
		String method = Application.getMethod(queryString);		
		String id = Application.getId(queryString);		
		JsonResponse jsonResponse = null; 
		System.out.println(" Valid methods: " + Application.getValidMethods()); 
		System.out.println(" Required method: " + method);
		if(Application.getValidMethods().contains(method)) {					
			mtt.smtt.model.WorkOrder workOrder = new mtt.smtt.model.WorkOrder(method, orderId, queryString);
			try {	
				Application.getInfoTable().put(orderId, workOrder);
				jsonResponse = new PositiveResult(orderId, "Loaded into reactor"); 
				Application.getService("init").load(workOrder);
			} catch (InterruptedException e) {	
				result = false;
				jsonResponse = new NegativeResult("Error while loading into reactor");
				e.printStackTrace();	
				}				
		}
		else {
			result = false;
			jsonResponse = new NegativeResult("No such valid method.", 666);
		}
		jsonResponse.setId(id);
		String ret = Application.getGson().toJson(jsonResponse);
		return ret;
	}

}