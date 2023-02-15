package com.evault.eb.utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import org.springframework.stereotype.Component;

@Component
public class Proper {
	private static com.evault.eb.utils.Proper inst;
	  
	  public static String url;
	  
	  public static String stanza;
	  
	  public static String ip;
	  
	  public static String port;
	  
	  public static String service;
	  
	  public static String prototype;

	private static String PehchanDateOfDeath;
	  
	  public static String DEATH = PehchanDateOfDeath;

	private static String PehchanStillBirth;
	  public static String STILLBIRTH = PehchanStillBirth;

	private static String PehchanDateOfBirth;
	  public String BIRTH=PehchanDateOfBirth;

	private static String PehchanMarriage;
	  public String MARRIAGE = PehchanMarriage;
	  
	  private final Properties configProp = new Properties();
	  public Proper() {
		    InputStream in = getClass().getClassLoader().getResourceAsStream("filenet.properties");
		    try {
		      System.out.println("Reading all properties from the file");
		      this.configProp.load(in);
		    } catch (FileNotFoundException e1) {
		      e1.printStackTrace();
		      System.out.println("error in reading property file : " + e1.getLocalizedMessage());
		    } catch (IOException e) {
		      e.printStackTrace();
		    } 
		  }
		  
//		  public static com.evault.eb.Utils.Proper getInstance() {
//		    return LazyHolder.access$100();
//		  }
		  
		  public String getProperty(String key) {
			    return this.configProp.getProperty(key);
			  }
	  
	  
	  
	  public Properties getPropInst() {
		  Properties props = new Properties();
	    if (inst == null)
	      try {
	    	  
//	        props.load(new FileInputStream("C:\\Users\\HP\\Desktop\\Propertise file 31-33\\dcmigration\\properties_API_10.68.251.129"));
	        ip = props.getProperty("ip");
	        port = props.getProperty("port");
	        stanza = null;
	        prototype = props.getProperty("prototype");
	        service = props.getProperty("service");
	        url = props.getProperty("corbalocurl");
	        inst = new com.evault.eb.utils.Proper();
	      } catch (Exception ex) {
	    	  System.out.println("LogFile.LoggerException "+ ex);
	      }  
	    return props;
	  }

}
