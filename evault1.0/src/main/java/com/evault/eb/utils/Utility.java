package com.evault.eb.utils;


import org.json.JSONException;

import org.springframework.stereotype.Component;


import org.json.JSONObject;
@Component
public class Utility {

	  public static String getJsonResponse(String status, String docExists, String responseCode, String incidentID, String Url, String messages) throws JSONException {
	    JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("Status", status);
	    jsonResponse.put("isDocumentExist", "True");
	    jsonResponse.put("ResponseCode", responseCode);
	    jsonResponse.put("TransactionId", incidentID);
	    jsonResponse.put("URL", Url);
	    jsonResponse.put("SuccessMessage", messages);
	    return jsonResponse.toString();
	  }
	  
	  public static JSONObject getJsonResponseExc(String status, String docExists, String responseCode, String incidentID, String docID, String messages) throws JSONException {
	    JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("Status", status);
	    jsonResponse.put("isDocumentExist", "False");
	    jsonResponse.put("ResponseCode", responseCode);
	    jsonResponse.put("IncidentId", incidentID);
	    jsonResponse.put("ErrorMessage", messages);
	    return jsonResponse;
	  }
	  
	  public static String getJsonResponseFlr(String status, String docExists, String responseCode, String incidentID, String docID, String messages) throws JSONException {
		    JSONObject jsonResponse = new JSONObject();
		    jsonResponse.put("Status", status);
		    jsonResponse.put("isDocumentExist", "False");
		    jsonResponse.put("ResponseCode", responseCode);
		    jsonResponse.put("IncidentId", incidentID);
		    jsonResponse.put("ErrorMessage", messages);
		    return jsonResponse.toString();
		  }
	  
	  public static String getMessage(String message, String strDynamicValue1, String strDynamicValue2) {
	    message = message.replaceAll("#ClassName#", strDynamicValue1);
	    message = message.replaceAll("", strDynamicValue2);
	    return message;
	  }
	  
	

}
