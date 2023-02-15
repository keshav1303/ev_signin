//package com.evault.eb.logs;
//
//public class LogFile {
//	
//
//  private static com.evault.eb.logs.LogFile instance;
//  
//  public static String LoggerException = "Exception Message:  ";
//  
//  public static String LoggerInfo = "Info Message:  ";
//  
//  public static String WarningInfo = "Warning Message:  ";
//  
//  public static String ErrorInfo = "Error Message:  ";
//  
//  public static com.evault.eb.logs.LogFile getInst() {
//    if (instance == null) {
//      instance = new com.evault.eb.logs.LogFile();
//      PropertyConfigurator.configure("/opt/IBM/properties/globalgenericserviceslog4j.properties");
//    } 
//    return instance;
//  }
//}
//
//
//
