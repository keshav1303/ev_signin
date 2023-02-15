package com.evault.eb.service;

import com.evault.eb.Controller.PehchanController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.evault.eb.Utils.DbUtils;

@Service
public class PehchanService {
	private static final Logger logger = LogManager.getLogger(PehchanService.class);
	
	public String getOfficeId(String registration, String year, String event) {

		    String reg = new String(registration);

		    logger.info("The extracted substring  is : " + reg);
		   String s1 =reg.substring(1, 6);
		   String s2 =reg.substring(11, 15);
		   String OfficeId =  (s1+s2);
		    logger.info(s1);
		    logger.info(s2);
		    logger.info(OfficeId);
               
		    DbUtils dbustils = new DbUtils();
	
            
		if (event.equalsIgnoreCase("BIRTH") || event.equalsIgnoreCase("DEATH") || event.equalsIgnoreCase("MARRIAGE")) {
            if (event.equalsIgnoreCase("BIRTH")) {
                event = "PehchanDateOfBirth";
            } else if (event.equalsIgnoreCase("DEATH")) {
                event = "PehchanDateOfDeath";
            } else if (event.equalsIgnoreCase("MARRIAGE")) {
                event = "PehchanMarriage";
            }
            else
            {
            	return "class not found";
            }

}
		return OfficeId;

	}
}
    

//if (Registration != null && !(Registration.isEmpty()) && year != null && !(year.isEmpty())
//&& event != null && !(event.isEmpty())) {
//boolean insertRecord = dbustils.insertRecord(Registration, year, event.toUpperCase());
//if (insertRecord) {
//Registration = Registration.trim();
//year = year.trim();
//event = event.trim();
//
//event=.getPro("event")l
