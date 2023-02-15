package com.evault.eb.service;

import com.evault.eb.dto.Pehchan;
import com.evault.eb.exception.SomethingWentWrongException;
import com.evault.eb.utils.Messages;
import com.evault.eb.utils.ReadPropertiesFile;
import com.evault.eb.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.evault.eb.utils.DbUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PehchanService {
    private static final Logger logger = LogManager.getLogger(PehchanService.class);


    public String getUrl(Pehchan pehchan) {
        String jsonGetDocumentResult = null;
        try {
            String registrationNoData = getRegistrationData(pehchan);
            String yearData = getYearData(pehchan);
            String eventDocTypeData = getEventDocTypeData(pehchan);
            String officeId = getOfficeId(registrationNoData,eventDocTypeData);
            String officeIdData = getOfficeIdData(officeId);
            String eventDocType = ReadPropertiesFile.getInstance().getProperty(eventDocTypeData);
            String incId = UUID.randomUUID().toString();
            String eventDocTypeInfo = getEventDocTypeInfo(eventDocType, incId);
            String registrationNumber = registrationNoData + "/" + yearData + "/" + officeIdData + "/" + eventDocTypeData;
            GetDocumentDAO getDocumentDao = new GetDocumentImpl();
            String docDetails = getDocumentDao.getDocument("PEHCHAN", eventDocType, "RegistrationNumber", registrationNumber);
            logger.info("DocDetails : " + docDetails);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> o = mapper.readValue(docDetails, Map.class);
            String docId = getDocumentId(o, incId);
            String code = getCode(o, incId);
            DbUtils dbUtils = new DbUtils();
            boolean globalInputsForviewDocument = dbUtils.globalInputsForviewDocument(incId, "", registrationNoData, docId, eventDocType, "", code, System.currentTimeMillis() / 1000, "");
            // generate Url
            String url = "https://evault.rajasthan.gov.in/DocViewerNew/preview.jsp?id=" + docId + "&type=PEHCHAN";
            logger.info("Url : " + url);
            jsonGetDocumentResult = Utility.getJsonResponse("200", "", "MS-111", incId, url, Messages.MESSAGE_VALUE_MS_112);
            return jsonGetDocumentResult;
        } catch (Exception e) {
            throw new SomethingWentWrongException("Something went wrong");
        }
    }

    private String getCode(Map<String, Object> o, String incId) throws JSONException {
        if (!o.containsKey("code")) {
            logger.error("Code : " + o.get("code").toString());
            return Utility.getJsonResponseFlr("400", "", "IE-129", incId, "", Messages.MESSAGE_VALUE_IE_129);
        }
        logger.info("Code : " + o.get("code").toString());
        return o.get("code").toString();
    }

    private String getDocumentId(Map<String, Object> o, String incId) throws JSONException {
        if (!o.containsKey("DocumentId")) {
            logger.error("DocumentId : " + o.get("DocumentId").toString());
            return Utility.getJsonResponseFlr("400", "", "IE-129", incId, "", Messages.MESSAGE_VALUE_IE_129);
        }
        logger.info("DocumentId : " + o.get("DocumentId").toString());
        return o.get("DocumentId").toString();
    }

    private String getEventDocTypeInfo(String eventDocType, String incId) throws JSONException {
        if (eventDocType != null && !eventDocType.isEmpty()) {
            logger.error("eventDocType : " + eventDocType);
            return Utility.getJsonResponseFlr("400", "", "IE-130", incId, "", Messages.MESSAGE_VALUE_IE_130);
        }
        logger.info("eventDocType : " + eventDocType);
        return eventDocType;
    }

    private String getOfficeIdData(String officeId) throws JSONException {
        if (officeId == null) {
            logger.error("officeId : " + officeId);
            return Utility.getJsonResponse("", "False", "MS-112", "", "", "");
        }
        logger.info("officeId : " + officeId);
        return officeId;
    }

    private String getEventDocTypeData(Pehchan pehchan) throws JSONException {
        if (pehchan.getEventDocType() == null && pehchan.getEventDocType().isEmpty()) {
            logger.error("eventDocType : " + pehchan.getEventDocType());
            return Utility.getJsonResponse("", "False", "MS-112", "", "", "");
        }
        logger.info("eventDocType : " + pehchan.getEventDocType());
        return pehchan.getEventDocType();
    }

    private String getYearData(Pehchan pehchan) throws JSONException {
        if (pehchan.getYear() == null && pehchan.getYear().isEmpty()) {
            logger.error("year : " + pehchan.getYear());
            return Utility.getJsonResponse("", "False", "MS-112", "", "", "");
        }
        logger.info("year : " + pehchan.getYear());
        return pehchan.getYear();
    }

    public String getRegistrationData(Pehchan pehchan) throws JSONException {
        if (pehchan.getRegistration() == null && pehchan.getRegistration().isEmpty()) {
            logger.error("registration : " + pehchan.getRegistration());
            return Utility.getJsonResponse("", "False", "MS-112", "", "", "");
        }
        logger.info("registration : " + pehchan.getRegistration());
        return pehchan.getRegistration();

    }

    public String getOfficeId(String registration,String event) {
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

