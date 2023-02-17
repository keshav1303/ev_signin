package com.evault.eb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.evault.eb.controller.PehchanController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbUtils {

    private static final Logger logger = LogManager.getLogger(DbUtils.class);

    DataSource _ds = null;
    Context _ctx = null;
    public static String deptUserName;
    public static String deptPassword;
    public static String deptObjectStore;

    public Connection getDB2Connection() throws NamingException, SQLException, ClassNotFoundException {
      Connection connection = null;
        this._ctx = new InitialContext();
        logger.info("context---" + this._ctx);
        this._ds = (DataSource) this._ctx.lookup("jdbc/DIGILOC");
        logger.info("Datasource----" + this._ds);
        try {
            connection = this._ds.getConnection();
        } catch (SQLException ex) {
            logger.info("Exception  : " + ex.getMessage());
        }
        return connection;
    }

}
