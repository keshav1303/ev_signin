package com.evault.eb.controller;
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
import com.evault.eb.exception.InvalidEventTypeException;
import com.evault.eb.exception.InvalidRegistrationNumberException;
import com.evault.eb.service.PehchanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
@RestController
@RequestMapping("/pehchan")
public class PehchanController {

    private static final Logger logger = LogManager.getLogger(PehchanController.class);
    @Autowired
    private PehchanService pehchanService;

    @PostMapping("/getDoc")
    public ResponseEntity<String> getDoc(@RequestBody Pehchan pehchan) throws JsonMappingException, JsonProcessingException, JSONException, InvalidRegistrationNumberException,InvalidEventTypeException {
        String response = pehchanService.getResponse(pehchan);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


