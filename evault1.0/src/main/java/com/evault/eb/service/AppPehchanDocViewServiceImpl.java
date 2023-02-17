package com.evault.eb.service;

import com.evault.eb.constants.ErrorMessage;
import com.evault.eb.entity.AppPehchanDocView;
import com.evault.eb.exception.InvalidRegistrationNumberException;
import com.evault.eb.exception.SQLInsertDataException;
import com.evault.eb.exception.SQLUpdateDataException;
import com.evault.eb.utils.DbUtils;
import com.evault.eb.utils.Messages;
import com.evault.eb.utils.Utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.sql.*;
import java.time.LocalDateTime;

@Service
public class AppPehchanDocViewServiceImpl implements AppPehchanDocViewService {

    private static final Logger logger = LogManager.getLogger(AppPehchanDocViewServiceImpl.class);
    @Autowired
    private DbUtils dbUtils;

    @Value("${spring.queries.insert-query}")
    String status_query;

    @Value("${spring.queries.update-query}")
    String update_query;

    public boolean insertViewDocument(AppPehchanDocView appPehchanDocView) throws JSONException {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = dbUtils.getDB2Connection();
            System.out.println(status_query);
            stmt = connection.prepareStatement(status_query);
            stmt.setString(1, appPehchanDocView.getIncidentId());
            stmt.setString(2, appPehchanDocView.getOs());
            stmt.setString(3, appPehchanDocView.getRegistrationNo());
            stmt.setString(4, appPehchanDocView.getOldEventValue());
            stmt.setTimestamp(5, getCurrentTimeStamp());
            stmt.setString(6, appPehchanDocView.getYear());
            int i = stmt.executeUpdate();
            logger.info("*****Inserted data here****");
            flag = true;
        } catch (Exception e) {
        	e.printStackTrace();
            logger.info("SQLException : insertViewDocument :" + e.getMessage());
            throw new SQLInsertDataException(Utility.getJsonResponse(HttpStatus.BAD_REQUEST.toString(), Messages.MESSAGE_FALSE, Messages.MESSAGE_CODE_MS_109, "", "", ErrorMessage.SQL_INSERT_ERROR));
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                logger.info("Exception : insertViewDocument finally block:" + ex.getMessage());
            }

        }
        return flag;
    }
    
    private java.sql.Timestamp getCurrentTimeStamp() {

    	java.util.Date today = new java.util.Date();
    	return new java.sql.Timestamp(today.getTime());

    }

    @Override
    public boolean updateViewDocument(AppPehchanDocView appPehchanDocView) throws JSONException {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = dbUtils.getDB2Connection();
            stmt = connection.prepareStatement(update_query);
            stmt.setString(1, appPehchanDocView.getDocType());
            stmt.setString(2, appPehchanDocView.getVersion());
            stmt.setString(3, appPehchanDocView.getDocId());
            stmt.setString(4, appPehchanDocView.getResponseCode());
            stmt.setString(5, appPehchanDocView.getDataRequired());
            stmt.setString(6, appPehchanDocView.getIncidentId());
            int i = stmt.executeUpdate();
            logger.info("*****update data here****");
            flag = true;
        } catch (Exception e) {
            logger.info("SQLException : updateViewDocument :" + e.getMessage());
            throw new SQLUpdateDataException(Utility.getJsonResponse(HttpStatus.BAD_REQUEST.toString(), Messages.MESSAGE_FALSE, Messages.MESSAGE_CODE_MS_109, "", "", ErrorMessage.SQL_UPDATE_ERROR));
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                logger.info("Exception : updateViewDocument finally block:" + ex.getMessage());
            }
        }
        return flag;
    }


}
