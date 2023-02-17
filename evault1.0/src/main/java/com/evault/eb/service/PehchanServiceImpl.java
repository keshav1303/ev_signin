package com.evault.eb.service;

import com.evault.eb.constants.AppConstant;
import com.evault.eb.dto.Pehchan;
import com.evault.eb.entity.AppPehchanDocView;
import com.evault.eb.exception.*;
import com.evault.eb.utils.Messages;
import com.evault.eb.utils.ReadPropertiesFile;
import com.evault.eb.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filenet.search.DAO.GetDocumentDAO;
//import com.filenet.search.DAO.GetDocumentDAO;
//import com.filenet.search.impl.GetDocumentImpl;
import com.filenet.search.impl.GetDocumentImpl;

//import com.filenet.search.DAO.GetDocumentDAO;
//import com.filenet.search.impl.GetDocumentImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PehchanServiceImpl implements PehchanService {
    private static final Logger logger = LogManager.getLogger(PehchanServiceImpl.class);

    @Autowired
    private AppPehchanDocViewService appPehchanDocViewService;

    public String getResponse(Pehchan pehchan) throws JsonMappingException, JsonProcessingException,InvalidEventTypeException, JSONException, InvalidRegistrationNumberException, InvalidYearException, InvalidEventTypeException, InvalidDocIdException {
        String jsonGetDocumentResult = null;
        AppPehchanDocView appPehchanDocView = new AppPehchanDocView();
        // Generate any random incident id
        String incId = UUID.randomUUID().toString();
        appPehchanDocView.setIncidentId(incId);
        appPehchanDocView.setRegistrationNo(pehchan.getRegistration());
        appPehchanDocView.setYear(pehchan.getYear());
        appPehchanDocView.setEvent(pehchan.getEvent());
        appPehchanDocView.setFetchTimeStamp(System.currentTimeMillis() / 1000);
        appPehchanDocView.setOs(AppConstant.OS);
        appPehchanDocView.setDataRequired(AppConstant.DOCVIEWURL);
        //This will always on contain event coming from request 
        appPehchanDocView.setOldEventValue(pehchan.getEvent());
        appPehchanDocViewService.insertViewDocument(appPehchanDocView);

        // check event is null or not 
        validateEventDocTypeData(appPehchanDocView);
        
        // Checking eventDocType is present on properties file or not as well as check event is empty or not null
        String eventDocType = ReadPropertiesFile.getInstance().checkEventInPropertiesFile(appPehchanDocView);
        appPehchanDocView.setEvent(eventDocType);
        appPehchanDocView.setDocType(eventDocType);
        
        // Check registration number is null or empty
        validateRegistrationData(appPehchanDocView);
        // Check year is null or empty
        validateYearData(appPehchanDocView);
        // Generate officeId
        generateOfficeId(appPehchanDocView);

        // Generate Registration Number
        String newRegistrationNumber = appPehchanDocView.getRegistrationNo() + "/" + appPehchanDocView.getYear() + "/" + appPehchanDocView.getOfficeId() + "/" + pehchan.getEvent();
        appPehchanDocView.setNewRegistrationNumber(newRegistrationNumber);
        logger.info("Registration Number : " + newRegistrationNumber);
        // Calling FileNet Service
        fetchFilenetDocDetails(appPehchanDocView);

        // generate Url
        String url = AppConstant.URL + appPehchanDocView.getDocId() + AppConstant.ENDURL;
        logger.info("Url : " + url);
        jsonGetDocumentResult = Utility.getJsonResponse(HttpStatus.OK.toString(), "", Messages.MESSAGE_CODE_MS_112, incId, url, Messages.MESSAGE_VALUE_MS_112);
       appPehchanDocView.setResponseCode(Messages.MESSAGE_CODE_MS_112);
        appPehchanDocViewService.updateViewDocument(appPehchanDocView);
        return jsonGetDocumentResult;
    }

    private void fetchFilenetDocDetails(AppPehchanDocView appPehchanDocView) throws JSONException, JsonProcessingException {
        GetDocumentDAO getDocumentDao = new GetDocumentImpl();
        String docDetails = getDocumentDao.getDocument(AppConstant.PEHCHAN, appPehchanDocView.getEvent(), AppConstant.REGISTRATIONNUMBER, appPehchanDocView.getNewRegistrationNumber());
        if (docDetails == null || docDetails.isEmpty()) {
            logger.error("FileNot Found : ");
            appPehchanDocView.setResponseCode(Messages.MESSAGE_VALUE_IE_129);
            appPehchanDocViewService.updateViewDocument(appPehchanDocView);
            throw new DocumentNotFoundException(Utility.getJsonResponseFlr(HttpStatus.BAD_REQUEST.toString(), "", Messages.MESSAGE_CODE_IE_129, appPehchanDocView.getIncidentId(), appPehchanDocView.getDocId(), Messages.MESSAGE_VALUE_IE_129));
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> o = mapper.readValue(docDetails, Map.class);
        logger.info(o);
        if (o.containsKey("code") && (o.get("code") != null && o.get("code").toString().equals("104"))) {
            appPehchanDocViewService.updateViewDocument(appPehchanDocView);
            throw new DocumentNotFoundException(Utility.getJsonResponseFlr(HttpStatus.BAD_REQUEST.toString(), "", Messages.MESSAGE_CODE_IE_129, appPehchanDocView.getIncidentId(), "", Messages.MESSAGE_VALUE_IE_129));
        }
        // Check documentId is contained or not
        checkDocumentId(o, appPehchanDocView);
        // Check code is contained or not
        checkCode(o, appPehchanDocView);
    }
    
    private void validateEventDocTypeData(AppPehchanDocView appPehchanDocView) throws JSONException,InvalidEventTypeException {
        if (appPehchanDocView.getEvent() == null || appPehchanDocView.getEvent().isEmpty()) {
            logger.error("eventDocType : " + appPehchanDocView.getEvent());
            // update incident logs
            appPehchanDocView.setResponseCode(Messages.MESSAGE_CODE_IE_130);
            appPehchanDocViewService.updateViewDocument(appPehchanDocView);
            throw new InvalidEventTypeException(Utility.getJsonResponse(HttpStatus.BAD_REQUEST.toString(), Messages.MESSAGE_FALSE, Messages.MESSAGE_CODE_IE_130, "", "", Messages.MESSAGE_VALUE_IE_130));
        }
        logger.info("eventDocType : " + appPehchanDocView.getEvent());
    }


    private void checkCode(Map<String, Object> o, AppPehchanDocView appPehchanDocView) throws JSONException {
        String documentId =null;
        if (!o.containsKey("code")) {
            logger.error("code Not Found: ");
            // update incident logs
            appPehchanDocView.setResponseCode(Messages.MESSAGE_CODE_IE_132);
            appPehchanDocViewService.updateViewDocument(appPehchanDocView);
            throw new InvalidCodeException(Utility.getJsonResponseFlr(HttpStatus.BAD_REQUEST.toString(), "", Messages.MESSAGE_CODE_IE_132, appPehchanDocView.getIncidentId(), documentId, Messages.MESSAGE_VALUE_IE_132));
        }
        logger.info("Code : " + o.get("code").toString());
    }

    private void checkDocumentId(Map<String, Object> o, AppPehchanDocView appPehchanDocView) throws JSONException, InvalidDocIdException {
        String documentId = null;
        if (!o.containsKey("DocumentId") || o.get("DocumentId") == null) {
            logger.error("DocumentId Not Found: ");
            // update incident logs
            appPehchanDocView.setResponseCode(Messages.MESSAGE_CODE_IE_129);
            appPehchanDocViewService.updateViewDocument(appPehchanDocView);
            throw new InvalidDocIdException(Utility.getJsonResponseFlr(HttpStatus.BAD_REQUEST.toString(), "", Messages.MESSAGE_CODE_IE_129, appPehchanDocView.getIncidentId(), documentId, Messages.MESSAGE_VALUE_IE_129));
        }
        documentId = o.get("DocumentId").toString();
        logger.info("DocumentId : " + documentId);
        appPehchanDocView.setDocId(documentId);
    }

    private void validateYearData(AppPehchanDocView appPehchanDocView) throws JSONException, InvalidYearException {
        if (appPehchanDocView.getYear() == null || appPehchanDocView.getYear().isEmpty()) {
            logger.error("year : " + appPehchanDocView.getYear());
            // update incident logs
            appPehchanDocView.setResponseCode(Messages.MESSAGE_CODE_MS_131);
            appPehchanDocViewService.updateViewDocument(appPehchanDocView);
            throw new InvalidYearException(Utility.getJsonResponse(HttpStatus.BAD_REQUEST.toString(), Messages.MESSAGE_FALSE, Messages.MESSAGE_CODE_MS_131, "", "", Messages.MESSAGE_VALUE_MS_131));
        }
        logger.info("year : " + appPehchanDocView.getYear());
    }

    public void validateRegistrationData(AppPehchanDocView appPehchanDocView) throws JSONException, InvalidRegistrationNumberException {
        if (appPehchanDocView.getRegistrationNo() == null || appPehchanDocView.getRegistrationNo().isEmpty()) {
            logger.error("registration : " + appPehchanDocView.getRegistrationNo());
            // update incident logs
            appPehchanDocView.setResponseCode(Messages.MESSAGE_CODE_MS_110);
            appPehchanDocViewService.updateViewDocument(appPehchanDocView);
            throw new InvalidRegistrationNumberException(Utility.getJsonResponse(HttpStatus.BAD_REQUEST.toString(), Messages.MESSAGE_FALSE, Messages.MESSAGE_CODE_MS_110, "", "", Messages.MESSAGE_VALUE_MS_111));
        }
        logger.info("registration : " + appPehchanDocView.getRegistrationNo());
    }

    public void generateOfficeId(AppPehchanDocView appPehchanDocView) {
        String reg = new String(appPehchanDocView.getRegistrationNo());
        logger.info("The extracted substring  is : " + reg);
        String s1 = reg.substring(1, 6);
        String s2 = reg.substring(11, 15);
        String officeId = (s1 + s2);
        logger.info("Office Id : " + officeId);
        appPehchanDocView.setOfficeId(officeId);
    }

}

