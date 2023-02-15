package com.evault.eb.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import com.evault.eb.exception.NoPropertyFileFoundException;
import com.evault.eb.exception.NoSuchPropertyFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Cacheable
public class ReadPropertiesFile {

    private static final Logger log = LogManager.getLogger(ReadPropertiesFile.class);
    // This file is added for reading the external property file added for fileNet
    // mapping and connection
    private final Properties configProp = new Properties();

    private ReadPropertiesFile() {
        // Private constructor to restrict new instances
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            // in = new FileInputStream("/opt/IBM/properties/filenet.properties");
            log.info("Reading all properties from the file");
            configProp.load(in);
        } catch (FileNotFoundException e1) {
            log.info("error in reading property file : " + e1.getLocalizedMessage());
            throw new NoPropertyFileFoundException("Error in reading property file :" + e1.getLocalizedMessage());
        } catch (IOException e) {
            log.info(e.getMessage());
//            throw new IOException("Something went wrong ");
        }
    }

    private static class LazyHolder {
        private static final ReadPropertiesFile INSTANCE = new ReadPropertiesFile();
    }

    public static ReadPropertiesFile getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getProperty(String key) {
        String property = configProp.getProperty(key);
        if (property == null) {
            throw new NoSuchPropertyFoundException("No Such Property Found.");
        }
        return property;
    }

    public Set<String> getAllPropertyNames() {
        return configProp.stringPropertyNames();
    }

    public boolean containsKey(String key) {
        return configProp.containsKey(key);
    }
}
