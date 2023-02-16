package com.evault.eb.service;

import com.evault.eb.dto.Pehchan;
import com.evault.eb.exception.*;
import com.evault.eb.utils.Messages;
import com.evault.eb.utils.ReadPropertiesFile;
import com.evault.eb.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.evault.eb.utils.DbUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PehchanService {
    private static final Logger logger = LogManager.getLogger(PehchanService.class);


    public String getUrl(Pehchan pehchan) throws JsonMappingException, JsonProcessingException, JSONException, InvalidRegistrationNumberException, InvalidYearException {
        String jsonGetDocumentResult = null;

        // Generate any random incident id
        String incId = UUID.randomUUID().toString();
        // Check registration number is null or empty
        String registrationNoData = getRegistrationData(pehchan,incId);
        // Check year is null or empty
        String yearData = getYearData(pehchan,incId);
        // Check event is null or empty
        String eventDocTypeData = getEventDocTypeData(pehchan,incId);
        // Generate officeId
        String officeId = generateOfficeId(registrationNoData, eventDocTypeData);
        // Check officeId is null
        String officeIdData = getOfficeIdData(officeId,incId);
        // Checking eventDocType is present on properties file or not
        String eventDocType = ReadPropertiesFile.getInstance().getProperty(eventDocTypeData);
        // After check from property file checking eventDocType is null or empty
        String eventDocTypeInfo = getEventDocTypeInfo(eventDocType, incId);
        //
        String registrationNumber = registrationNoData + "/" + yearData + "/" + officeIdData + "/" + eventDocTypeData;
        // Calling FileNet Service
        GetDocumentDAO getDocumentDao = new GetDocumentImpl();
        String docDetails = getDocumentDao.getDocument("PEHCHAN", eventDocTypeInfo, "RegistrationNumber", registrationNumber);
        logger.info("DocDetails : " + docDetails);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> o = mapper.readValue(docDetails, Map.class);
        // Check documentId is contained or not
        String docId = getDocumentId(o, incId);
        // Check code is contained or not
        String code = getCode(o, incId);

        DbUtils dbUtils = new DbUtils();
        boolean globalInputsForviewDocument = dbUtils.globalInputsForviewDocument(incId, "", registrationNoData, docId, eventDocType, "", code, System.currentTimeMillis() / 1000, "");
        // generate Url
        String url = "https://evault.rajasthan.gov.in/DocViewerNew/preview.jsp?id=" + docId + "&type=PEHCHAN";
        logger.info("Url : " + url);
        jsonGetDocumentResult = Utility.getJsonResponse(HttpStatus.OK.toString(), "", Messages.MESSAGE_CODE_MS_112, incId, url, Messages.MESSAGE_VALUE_MS_112);
        return jsonGetDocumentResult;
    }

    private String getCode(Map<String, Object> o, String incId) throws JSONException {
        if (!o.containsKey("code")) {
            logger.error("Code : " + o.get("code").toString());
            throw new InvalidCodeException(Utility.getJsonResponseFlr(HttpStatus.BAD_REQUEST.toString(), "", Messages.MESSAGE_CODE_IE_132, incId, "", Messages.MESSAGE_VALUE_IE_132));
        }
        logger.info("Code : " + o.get("code").toString());
        return o.get("code").toString();
    }

    private String getDocumentId(Map<String, Object> o, String incId) throws JSONException {
        if (!o.containsKey("DocumentId")) {
            logger.error("DocumentId : " + o.get("DocumentId").toString());
            throw new InvalidDocIdException(Utility.getJsonResponseFlr(HttpStatus.BAD_REQUEST.toString(), "", Messages.MESSAGE_CODE_IE_129, incId, "", Messages.MESSAGE_VALUE_IE_129));
        }
        logger.info("DocumentId : " + o.get("DocumentId").toString());
        return o.get("DocumentId").toString();
    }

    private String getEventDocTypeInfo(String eventDocType, String incId) throws JSONException {
        if (eventDocType != null && !eventDocType.isEmpty()) {
            logger.error("eventDocType : " + eventDocType);
            throw new InvalidOfficeIdExecption(Utility.getJsonResponseFlr(HttpStatus.BAD_REQUEST.toString(), "", Messages.MESSAGE_CODE_IE_130, incId, "", Messages.MESSAGE_VALUE_IE_130));

        }
        logger.info("eventDocType : " + eventDocType);
        return eventDocType;
    }

    private String getOfficeIdData(String officeId, String incId) throws JSONException {
        if (officeId == null) {
            logger.error("officeId : " + officeId);
            throw new InvalidOfficeIdExecption(Utility.getJsonResponse(HttpStatus.BAD_REQUEST.toString(),  Messages.MESSAGE_FALSE,Messages.MESSAGE_CODE_IE_126, "", "", Messages.MESSAGE_VALUE_IE_126));
        }
        logger.info("officeId : " + officeId);
        return officeId;
    }

    private String getEventDocTypeData(Pehchan pehchan, String incId) throws JSONException {
        if (pehchan.getEventDocType() == null && pehchan.getEventDocType().isEmpty()) {
            logger.error("eventDocType : " + pehchan.getEventDocType());
            throw new InvalidEventTypeException(Utility.getJsonResponse(HttpStatus.BAD_REQUEST.toString(),  Messages.MESSAGE_FALSE, Messages.MESSAGE_CODE_MS_131, "", "", Messages.MESSAGE_VALUE_MS_131));
        }
        logger.info("eventDocType : " + pehchan.getEventDocType());
        return pehchan.getEventDocType();
    }

    private String getYearData(Pehchan pehchan, String incId) throws JSONException, InvalidYearException {
        if (pehchan.getYear() == null && pehchan.getYear().isEmpty()) {
            logger.error("year : " + pehchan.getYear());
            throw new InvalidYearException(Utility.getJsonResponse(HttpStatus.BAD_REQUEST.toString(), Messages.MESSAGE_FALSE, Messages.MESSAGE_CODE_IE_130, "", "",  Messages.MESSAGE_VALUE_MS_130));
        }
        logger.info("year : " + pehchan.getYear());
        return pehchan.getYear();
    }

    public String getRegistrationData(Pehchan pehchan, String incId) throws JSONException, InvalidRegistrationNumberException {
        if (pehchan.getRegistration() == null && pehchan.getRegistration().isEmpty()) {
            logger.error("registration : " + pehchan.getRegistration());
            throw new InvalidRegistrationNumberException(Utility.getJsonResponse(HttpStatus.BAD_REQUEST.toString(), Messages.MESSAGE_FALSE, Messages.MESSAGE_CODE_MS_111, "", "", Messages.MESSAGE_VALUE_MS_111));
        }
        logger.info("registration : " + pehchan.getRegistration());
        return pehchan.getRegistration();

    }

    public String generateOfficeId(String registration, String event) {
        String reg = new String(registration);
        logger.info("The extracted substring  is : " + reg);
        String s1 = reg.substring(1, 6);
        String s2 = reg.substring(11, 15);
        String officeId = (s1 + s2);
        logger.info(s1);
        logger.info(s2);
        logger.info("Office Id : " + officeId);
        DbUtils dbustils = new DbUtils();
        if (event.equalsIgnoreCase("BIRTH") || event.equalsIgnoreCase("DEATH") || event.equalsIgnoreCase("MARRIAGE")) {
            if (event.equalsIgnoreCase("BIRTH")) {
                event = "PehchanDateOfBirth";
            } else if (event.equalsIgnoreCase("DEATH")) {
                event = "PehchanDateOfDeath";
            } else if (event.equalsIgnoreCase("MARRIAGE")) {
                event = "PehchanMarriage";
            } else {
                return "class not found";
            }
        }
        return officeId;
    }


}

