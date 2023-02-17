package com.evault.eb.service;

import org.json.JSONException;

import com.evault.eb.dto.Pehchan;
import com.evault.eb.exception.InvalidEventTypeException;
import com.evault.eb.exception.InvalidRegistrationNumberException;
import com.evault.eb.exception.InvalidYearException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface PehchanService {

	String getResponse(Pehchan pehchan) throws JsonMappingException, JsonProcessingException, JSONException, InvalidRegistrationNumberException, InvalidYearException,InvalidEventTypeException;

}
