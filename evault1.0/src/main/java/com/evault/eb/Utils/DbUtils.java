package com.evault.eb.Utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

@Component
public class DbUtils {

    DataSource _ds = null;

    Context _ctx = null;

    public static String deptUserName;

    public static String deptPassword;

    public static String deptObjectStore;
    
    public Connection getDB2Connection() throws NamingException {
        Connection connection = null;
        this._ctx = new InitialContext();
        System.out.println("context---" + this._ctx);
        this._ds = (DataSource)this._ctx.lookup("jdbc/DIGILOC");
        System.out.println("Datasource----" + this._ds);
        try {
            connection = this._ds.getConnection();
        } catch (SQLException ex) {
            System.out.println("Exception  : " + ex.getMessage());
        }
        return connection;
    }
      
   
      
    
      
      public boolean globalInputsForviewDocument(String incidentID, String os, String RegistrationNumber, String DOCID, String DocType, String Version,String ResponseCode,long FetchTimestamp,String DataRequired) {
        boolean flag = false;
        Connection connection = null;
        Statement stmt = null;
        try {
        	connection=getDB2Connection();
          String insertsql = "insert into APP_PEHCHAN_DOCVIEW(INCIDENT_ID,OS,REGISTRATIONNUMBER,DOCID,DOCTYPE,VERSION,RESPONSECODE,FETCHTIMESTAMP,DATAREQUIRED)values('" + incidentID + "','" + os + "','" + RegistrationNumber + "','" + DOCID + "','" + DocType + "', '" + Version + "', '" + ResponseCode + "', '" + FetchTimestamp + "', '" + DataRequired + "')";
          stmt = connection.createStatement();
          stmt.execute(insertsql);
          System.out.println("*****Inserted data here****");
          flag = true;
        } catch (SQLException e) {
          System.out.println("SQLException : globalInputsForPushLogs :" + e.getMessage());
        } catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
          try {
            if (stmt != null)
              stmt.close(); 
          } catch (Exception ex) {
            System.out.println("Exception : globalInputsForPushLogs finally block:" + ex.getMessage());
          } 
        } 
        return flag;
      }
      
//      public boolean globalUpdateResponseDBUpdate(String incidentID, String DocID, String ResponseCode, String FinalResponse, Connection connection) {
//        boolean flag = false;
//        Statement stmt = null;
//        try {
//          stmt = connection.createStatement();
//          String query = "update APP_GENERIC_GLOBAL_UPDATE set DOCID='" + DocID + "',RESPONSECODE='" + ResponseCode + "',FINALRESPONSE='" + FinalResponse + "' WHERE INCIDENT_ID = ('" + incidentID + "')";
//          stmt.executeUpdate(query);
//          flag = true;
//        } catch (SQLException e) {
//          System.out.println("SQLException : globalPushResponseDBUpdateLogs :" + e.getMessage());
//        } finally {
//          try {
//            if (stmt != null)
//              stmt.close(); 
//          } catch (Exception ex) {
//            System.out.println("Exception : globalPushResponseDBUpdateLogs finally block:" + ex.getMessage());
//          } 
//        } 
//        return flag;
//      }
//      
//      public String selectDBUserDetail(String deptid, Connection connection) {
//        String flag = "FALSE";
//        Statement statement = null;
//        String objectStore = "";
//        try {
//          String queryString = "Select * from db2inst1.GENERICSERVICES_DEPTDETAIL where DEPTKEY='" + deptid + "'";
//          statement = connection.createStatement();
//          ResultSet resultSet = statement.executeQuery(queryString);
//          while (resultSet.next()) {
//            deptUserName = resultSet.getString("USERNAME");
//            deptPassword = resultSet.getString("PASSWORD");
//            objectStore = resultSet.getString("OS");
//            deptObjectStrore = objectStore;
//            deptObjectStrore = objectStore;
//            flag = objectStore;
//          } 
//        } catch (Exception e) {
//          System.out.println("Exception in selectDBUserDetail" + e.getMessage());
//        } finally {
//          try {
//            if (statement != null)
//              statement.close(); 
//          } catch (Exception ex) {
//            System.out.println("Exception : selectDBUserDetail finally block:" + ex.getMessage());
//          } 
//        } 
//        return flag;
//      }
      
      
//      public boolean insertRecord(String INCIDENTID, String REGISTRATIONNUMBER,String DOCTYPE,  String DOCID, String RESPONSECODE,long FETCHTIMESTAMP) {
//        boolean result = false;
//        CallableStatement proc = null;
//        Connection connection = null;
//        System.out.println("insertRecord : ");
//        int index = 0;
//        try {
//          System.out.println("query ");
//          proc = connection.prepareCall("CALL DB2INST1.InsertRecords(?,?,?,?,?,?,?,?,?)");
//          proc.setNString(++index, INCIDENTID);
//          proc.setString(++index, REGISTRATIONNUMBER);
//          proc.setString(++index, DOCTYPE);
//          proc.setString(++index, DOCID);
//          proc.setString(++index, RESPONSECODE);
//          proc.setLong(++index, FETCHTIMESTAMP);
//          proc.execute();
//          result = true;
//          System.out.println("*****Inserted data here****    " + result);
//        } catch (SQLException e) {
//          System.out.println("SQLException : globalInputsForPushLogs :" + e.getMessage());
//        } catch (Exception e) {
//          System.out.println("   " + e.getLocalizedMessage());
//        } finally {
//          try {
//            if (proc != null)
//              proc.close(); 
//          } catch (Exception ex) {
//            System.out.println("Error in  insert    1 " + ex.getMessage());
//            System.out.println("Exception :  insert finally block:  2" + ex.getMessage());
//          } 
//        } 
//        return result;
//      }
//      
//      public void updateRecord(String INCIDENTID,  String RESPONSECODE,	Connection connection) {
//        CallableStatement proc = null;
//        System.out.println("insertRecord : ");
//        int index = 0;
//        try {
//          proc = connection.prepareCall("CALL DB2INST1.EvaultUserStoreUpdateRecords(?,?,?)");
//          proc.setString(++index, INCIDENTID);
//          proc.setString(++index, RESPONSECODE);
////          proc.setString(++index, response);
//          proc.execute();
//        } catch (SQLException e) {
//          System.out.println("Error in update   3  " + e.getMessage());
//          System.out.println("SQLException : update 4:" + e.getMessage());
//        } finally {
//          try {
//            if (proc != null)
//              proc.close(); 
//          } catch (Exception ex) {
//            System.out.println("Error in update  22   " + ex.getMessage());
//            System.out.println("Exception : update finally block 33:" + ex.getMessage());
//          } 
//        } 
//      }
//      
      
}
