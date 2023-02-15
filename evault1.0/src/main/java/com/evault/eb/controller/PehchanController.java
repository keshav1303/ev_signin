package com.evault.eb.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evault.eb.dto.Pehchan;
import com.evault.eb.utils.DbUtils;
import com.evault.eb.utils.Messages;
import com.evault.eb.utils.ReadPropertiesFile;
import com.evault.eb.utils.Utility;
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
    @PostMapping("/getDoc")
    public ResponseEntity<String> getDoc(@RequestBody Pehchan pehchan) throws JsonMappingException, JsonProcessingException, JSONException {
        String url = pehchanService.getUrl(pehchan);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

}


