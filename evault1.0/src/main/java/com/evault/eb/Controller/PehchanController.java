package com.evault.eb.Controller;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.sql.Connection;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evault.eb.DTO.Pehchan;
import com.evault.eb.Utils.DbUtils;
import com.evault.eb.Utils.Messages;
import com.evault.eb.Utils.Proper;
import com.evault.eb.Utils.ReadPropertiesFile;
import com.evault.eb.Utils.Utility;
import com.evault.eb.service.PehchanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filenet.search.DAO.GetDocumentDAO;
import com.filenet.search.impl.GetDocumentImpl;

@RestController
@RequestMapping("/pehchan")
public class PehchanController {
	private static final Logger logger = LogManager.getLogger(PehchanController.class);
	@Autowired
	private PehchanService pehchanService;
	@Autowired
	private Messages message;

	
//	public String getPahchanDocInfo(String Registration,String year,String DocType);

	@PostMapping("/getDoc")
    public ResponseEntity<String> getDoc(@RequestBody Pehchan pehchan) throws JsonMappingException, JsonProcessingException, JSONException{
		String jsonGetDocumentResult = null;
    	String event = pehchan.getEventDocType();
    	String Registration = pehchan.getRegistration();
    	String year = pehchan.getYear();
    	 logger.info("event : "+event);
    	 logger.info("Registration : "+Registration);
    	 logger.info("year : "+year);
		 try {
			 if (pehchan.getRegistration() == null && pehchan.getRegistration().isEmpty()) {
				 jsonGetDocumentResult = Utility.getJsonResponse("", "False", "MS-112", "", "", "");
				 return ResponseEntity.ok(jsonGetDocumentResult);
			 }
			 if (pehchan.getYear() == null && pehchan.getYear().isEmpty()) {

				 return null;
			 }
			 if (pehchan.getEventDocType() == null && pehchan.getEventDocType().isEmpty()) {
				 return null;
			 }

			 String officeId = pehchanService.getOfficeId(Registration, year, event);
			 if (officeId == null && officeId.isEmpty()) {
				 return null;
			 }

			 String eventDocType = ReadPropertiesFile.getInstance().getProperty(event);
			 String incId = UUID.randomUUID().toString();

			 if (eventDocType != null && !eventDocType.isEmpty()) {
				 logger.info("eventDocType : " + eventDocType);
				 String RegistrationNumber = Registration + "/" + year + "/" + officeId + "/" + event;
				 GetDocumentDAO getDocumentDao = new GetDocumentImpl();
				 String DocDetails = getDocumentDao.getDocument("PEHCHAN", eventDocType, "RegistrationNumber", RegistrationNumber);
				 System.out.println();
				 logger.info("DocDetails : " + DocDetails);
				 Map<String, Object> DocIdDetails = new HashMap<String, Object>();
				 ObjectMapper mapper = new ObjectMapper();
				 Map<String, Object> o = mapper.readValue(DocDetails, Map.class);
				 System.out.println("O : " + 0);
				 if (!o.containsKey("DocumentId")) {
					 jsonGetDocumentResult = Utility.getJsonResponseFlr("400", "", "IE-129", incId, "", Messages.MESSAGE_VALUE_IE_129);
					 return ResponseEntity.ok(jsonGetDocumentResult);
				 }
				 String DocId = o.get("DocumentId").toString();
				 String code = o.get("code").toString();
				 logger.info("get" + o.get("DocumentId"));


				 DbUtils dbUtils = new DbUtils();
				 boolean globalInputsForviewDocument = dbUtils.globalInputsForviewDocument(incId, "", Registration, DocId, eventDocType, "", code, System.currentTimeMillis() / 1000, "");
//    				

				 String Url = "https://evault.rajasthan.gov.in/DocViewerNew/preview.jsp?id=" + DocId + "&type=PEHCHAN";
				 logger.info(Url);

				 jsonGetDocumentResult = Utility.getJsonResponse("200", "", "MS-111", incId, Url, Messages.MESSAGE_VALUE_MS_112);
				 return ResponseEntity.ok(jsonGetDocumentResult);


			 } else {
				 jsonGetDocumentResult = Utility.getJsonResponseFlr("400", "", "IE-130", incId, "", Messages.MESSAGE_VALUE_IE_130);
				 return ResponseEntity.ok(jsonGetDocumentResult);
			 }
		 }
		 catch (Exception e){
				 System.err.println("Exception: ");
				 System.out.println("Exception: "+ e.getMessage().getClass().getName());
				 e.printStackTrace();
			 }
		 return R
		 }
    	
    	
	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
//    	 if(proper.getProperty("BIRTH").equals(event)){
//    		 eventDocType= proper.BIRTH;
//    	 }
//    	 else if(proper.getProperty("STILLBIRTH").equals(event)){
//    		 eventDocType= proper.STILLBIRTH;
//    	 }
//    	 else if(proper.getProperty("DEATH").equals(event)){
//    		 eventDocType= proper.DEATH;
//    	 }
//    	 else if(proper.getProperty("MARRIAGE").equals(event)){
//    		 eventDocType= proper.MARRIAGE;
//    	 }
//    	 else {
//    		return message.MESSAGE_CODE_IE_130; 
//    	 }
//    	 
        
    }
    




//    @RequestMapping("/{someID}")
//    public @ResponseBody int getAttr(@PathVariable(value="someID") String id, 
//                                     @RequestParam String someAttr) {
//    }
//    
//    String offset = getPehchanApi();
//
//    String fileNetURL = "";
//       
//    String docID = fileNetMethode(fileNetURL);
//
//    String newURL =  sffsfdf+docID+pehchan;
//
//    update datalogs = calltoData;
//
//    return newURL;
//
//    }
//
//    String getPahchanApi(){
//
//    Return "offSet"
//    }



