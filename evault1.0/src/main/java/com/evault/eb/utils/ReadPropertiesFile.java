package com.evault.eb.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import com.evault.eb.entity.AppPehchanDocView;
import com.evault.eb.exception.InvalidEventTypeException;
import com.evault.eb.exception.NoPropertyFileFoundException;
import com.evault.eb.exception.NoSuchPropertyFoundException;
import com.evault.eb.service.AppPehchanDocViewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Cacheable
public class ReadPropertiesFile {

    private static final Logger logger = LogManager.getLogger(ReadPropertiesFile.class);

    @Autowired
    private AppPehchanDocViewService appPehchanDocViewService;
    private final Properties configProp = new Properties();

    private ReadPropertiesFile() {
        // Private constructor to restrict new instances
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            // in = new FileInputStream("/opt/IBM/properties/filenet.properties");
            logger.info("Reading all properties from the file");
            configProp.load(in);
        } catch (Exception e1) {
            logger.info("error in reading property file : " + e1.getLocalizedMessage());
            throw new NoPropertyFileFoundException("Error in reading property file :" + e1.getLocalizedMessage());
        }
    }

    private static class LazyHolder {
        private static final ReadPropertiesFile INSTANCE = new ReadPropertiesFile();
    }

    public static ReadPropertiesFile getInstance() throws JSONException , InvalidEventTypeException {
        return LazyHolder.INSTANCE;
    }

    public String checkEventInPropertiesFile(AppPehchanDocView appPehchanDocView) throws JSONException ,InvalidEventTypeException{
   
        String property = configProp.getProperty(appPehchanDocView.getEvent());
        if (property == null) {
            logger.info("error in reading property file : ");
            appPehchanDocView.setResponseCode(Messages.MESSAGE_CODE_IE_133);
            appPehchanDocViewService.updateViewDocument(appPehchanDocView);
            throw new NoSuchPropertyFoundException(Utility.getJsonResponseFlr(HttpStatus.BAD_REQUEST.toString(), "", Messages.MESSAGE_CODE_IE_133, appPehchanDocView.getIncidentId(), "", Messages.MESSAGE_VALUE_IE_133));
        }
        return property;
    }



}
