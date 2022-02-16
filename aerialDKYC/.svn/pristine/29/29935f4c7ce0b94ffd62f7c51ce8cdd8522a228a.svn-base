package com.edios.cdf.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class WebLogging {
	
    public SimpleDateFormat gDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public SimpleDateFormat gDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected static ResourceBundle gBundle = null;
    //Log Variables*************************************************************
    private static  int gLogFileSize=0;
	/*Configurable File Size in XML need to be implemented */
    public static final String LOG_TYPE_WARNING = "WARNING";
    public static final String LOG_TYPE_FATAL = "FATAL";
    public static final String LOG_TYPE_INFORMATIVE= "INFO";
    public static final String LOG_TYPE_DEBUG= "DEBUG";
    protected String gLogType = LOG_TYPE_DEBUG;
    protected String LOG_FOLDER_ARRAY[] = {"Debuglog", "Errorlog"};
    protected String gLogPath = "";
    private File gDebugLogFile = null;
    private File gErrorLogFile = null;
    protected String gDebugLogInfo = "";
    protected String gErrorLogInfo = "";
    public String entityName="AL";
    protected String gErrorFileLocation = "";
    protected String gDebugFileLocation = "";
    private String gDebugLastDateForlog=gDateFormatter.format(new Date());
    private String gErrorLastDateForlog=gDateFormatter.format(new Date());
    protected String gUserID = "";
      

    public WebLogging(String entityName,String userID,String AppConfigPath) throws Exception
	{
		String errorMessage="";
		this.gUserID = userID;
		if(userID.equalsIgnoreCase("") )
		{
			errorMessage="User  ID  can't be blank while creating new logging instance";
			 throw new Exception(errorMessage);
		}
		else if(AppConfigPath.equalsIgnoreCase(""))
		{
			errorMessage="AppConfiguration file can not be  blank while creating new logging instance";
			 throw new Exception(errorMessage);
		}
		if(entityName.equals(""))
		{
			errorMessage="EntityName can not be  blank while creating new logging instance";
			 throw new Exception(errorMessage);
		}
		this.entityName=entityName;
		gBundle=ResourceBundle.getBundle(AppConfigPath);
		errorMessage=readLogParametersFromPropertyFile();
		 if(errorMessage.equalsIgnoreCase(""))
		 {
			 initializeLogFiles();
		 }  
		 else
			 throw new Exception(errorMessage);
	}
    	   
    public boolean checkFileSize(File fileName)
    {   
    	try
    	{
    	
		    	if(gDebugLogInfo.equalsIgnoreCase("Yes"))
		    	{
		    		if(fileName.length()/(1024*1024)>gLogFileSize)
		    			return true; 
			    	 
		    		else 
		    			return false; 
			    	
		    	}
    		
    	}
    	catch(Exception errorException)
    	{
    		System.out.println("Exception occured in checkFileSize() function :"+errorException.getMessage());
    	}    	
    	return false;
    }
    private String setFileCounter(int logFileCounter)
    {	
    	Integer objFileCounter=new Integer(logFileCounter);
    	
    	if(objFileCounter<10)
    	  return "00"+objFileCounter.toString();
    	else
    	{
    		return objFileCounter>99 ? objFileCounter.toString():"0"+objFileCounter.toString();
    	}
   
    }
    private File getLogFile(String folderPath)
    {
    	int logFileCounter = 1;
        File logFileName = null;
        File latestLogFileName = null;
       
        try
        {   
        	
        	folderPath= new File(folderPath+"/"+gDateFormatter.format(new Date())).getPath();
        	if(!(new File(folderPath).isDirectory()))
          	{ 
        		new File(folderPath).mkdirs();
          	}
			
			logFileName = new File(folderPath + "/" + gDateFormatter.format(new Date()) +"_" + gUserID + ".TXT");
            latestLogFileName = logFileName;
            
	            if(gLogFileSize>0)
	            {
		            while(latestLogFileName.exists())
		            {
		            	if(checkFileSize(latestLogFileName))
		            	{
							latestLogFileName = new File(logFileName.getPath().replaceAll(".TXT", "_Part_"+setFileCounter(logFileCounter)+".TXT"));
		            		logFileCounter++;
		            	
		            	}
		            	else 
		            	
		            		break;
		            	
		            }
	          }
        }
        catch (Exception errorException) 
        {
        	System.out.println("Exception occured in getLogFile() function :"+errorException.getMessage());
        }
        return latestLogFileName;
    }
    
    
    public void checkFileSizeAndSetFileName()
    {   
    	try
    	{
		    	if(gDebugLogInfo.equalsIgnoreCase("Yes"))
		    	{
		    		if(gDebugLogFile.length()/(1024*1024)>gLogFileSize)
			    	{
			    		gDebugLogFile=getLogFile(gDebugFileLocation);		    		
			    	}	    		
		    	}	
	
    	}
    	catch(Exception errorException)
    	{
    		System.out.println("Exception occured in checkFileSizeAndSetFileName() function :"+errorException.getMessage());
    	}    	
    }
    
    
    public void logDebugInfo(String debugMessage, String logType) 
    {   
    	boolean logflag;
        try
        {      
        	logflag=false;
            if (gDebugLogInfo.equalsIgnoreCase("Yes")) 
            {
            	if(gLogType.equalsIgnoreCase(LOG_TYPE_DEBUG))
            	{
            		logflag=true;
            	}
            	else if(gLogType.equalsIgnoreCase(LOG_TYPE_INFORMATIVE) && (!logType.equalsIgnoreCase(LOG_TYPE_DEBUG)))			
            	{
            		logflag=true;
            	}
            	else if(gLogType.equalsIgnoreCase(LOG_TYPE_WARNING)&&!(logType.equalsIgnoreCase(LOG_TYPE_INFORMATIVE)||logType.equalsIgnoreCase(LOG_TYPE_DEBUG)))
            	{
            		
            		logflag=true;
            	}
            	else if(gLogType.equalsIgnoreCase(LOG_TYPE_FATAL)&&!(logType.equalsIgnoreCase(LOG_TYPE_INFORMATIVE)||logType.equalsIgnoreCase(LOG_TYPE_WARNING)||logType.equalsIgnoreCase(LOG_TYPE_DEBUG)))
            	{
            		
            		logflag=true;
            	}
            
        		if(logflag)
        		{
        			
        				if(gLogFileSize>0) 
        					
        					checkFileSizeAndSetFileName();
        				
		            	if(!gDateFormatter.format(new Date()).equalsIgnoreCase(gDebugLastDateForlog))
		                {  
		               	    gDebugLastDateForlog=gDateFormatter.format(new Date()); 
		               	    gDebugLogFile=getLogFile(gDebugFileLocation);                    
		                }
		                FileWriter debugMessageStream = new FileWriter(gDebugLogFile, true);
		                BufferedWriter debugMessageBufferWriter = new BufferedWriter(debugMessageStream);
		                debugMessageBufferWriter.write(gDateFormat.format(new Date()) + "-["+logType.toUpperCase()+"] -> " + debugMessage);
		                debugMessageBufferWriter.newLine();
		                debugMessageBufferWriter.close();
            	}
            }
        } catch (Exception errorException) 
        {
        	System.out.println("Exception occured in logDebugInfo() Function while printing log:"+errorException.getMessage());
        }
    }
 
    public void logErrorInfo(String errorMessage) 
    {           
        try 
        {
            if(gErrorLogInfo.equalsIgnoreCase("Yes")) 
            {
            	logDebugInfo(errorMessage,LOG_TYPE_FATAL);
            	
            	if(!gDateFormatter.format(new Date()).equalsIgnoreCase(gErrorLastDateForlog))
                {  
                    gErrorLastDateForlog=gDateFormatter.format(new Date()); 
               	    gErrorLogFile=getLogFile(gErrorFileLocation);                    
                }
                FileWriter ErrorMessageStream = new FileWriter(gErrorLogFile, true);
                BufferedWriter ErrorMessageBufferWriter = new BufferedWriter(ErrorMessageStream);
                ErrorMessageBufferWriter.write(gDateFormat.format(new Date()) + " -> " + errorMessage);
                ErrorMessageBufferWriter.newLine();
                ErrorMessageBufferWriter.close();
            }
        } catch (IOException errorException)
        {
            logDebugInfo("In logErrorInfo() Function : Exception thrown : " + errorException.getMessage(),LOG_TYPE_FATAL);
        }  
    }
    private void initializeLogFiles()
    {
    	if(gDebugLogInfo.equalsIgnoreCase("YES")){
        gDebugLogFile = getLogFile(gDebugFileLocation);
    	}
    	if(gErrorLogInfo.equalsIgnoreCase("YES")){
        gErrorLogFile=getLogFile(gErrorFileLocation);
        }
    	if(gLogType.equalsIgnoreCase("INFO")){
        logDebugInfo("Debug and Error Log files intialize......",LOG_TYPE_INFORMATIVE);
    	}
    	else if(gLogType.equalsIgnoreCase("FATAL")){
    		logDebugInfo("Debug and Error Log files intialize......",LOG_TYPE_FATAL);
    		
    	}
    	else if(gLogType.equalsIgnoreCase("WARNING")){
    		logDebugInfo("Debug and Error Log files intialize......",LOG_TYPE_WARNING);
    	}
    } 
    

    private String readLogParametersFromPropertyFile() {
    	   
        String errorMessage = "";
        
        try {
        	
        	  gDebugLogInfo = gBundle.getString("DebugLoggingInFile");
              gErrorLogInfo =  gBundle.getString("ErrorLoggingInFile");
              gLogPath =  gBundle.getString("LoggingPath");
              gLogFileSize= gBundle.getString("MaxLogFileSizeInMB").length()>0 ? Integer.parseInt(gBundle.getString("MaxLogFileSizeInMB")):0;
              gLogType= ((gBundle.getString("DebugLoggingType")).length()>0) ? gBundle.getString("DebugLoggingType"):gLogType;

            if (gDebugLogInfo.equals("") || gErrorLogInfo.equals("") || gLogPath.equals("")) {	
                errorMessage = "One or more log parameters are either blank or invalid in property file";
                return errorMessage;
            }

            for (int countFolder = 0; countFolder < LOG_FOLDER_ARRAY.length; countFolder++) {
                if (!new File(gLogPath + "/" + LOG_FOLDER_ARRAY[countFolder]).isDirectory()) {
                	if (new File(gLogPath + "/" + entityName + "/" + LOG_FOLDER_ARRAY[countFolder]).mkdirs()) {
                        System.out.println("Folder created on this location: " + new File(gLogPath + "/" + entityName + "/" + LOG_FOLDER_ARRAY[countFolder]).getAbsolutePath());
                    }
                }
               
            }
            gDebugFileLocation = new File(gLogPath + "/" + entityName + "/" + LOG_FOLDER_ARRAY[0]).getPath();
            gErrorFileLocation = new File(gLogPath + "/" + entityName + "/" + LOG_FOLDER_ARRAY[1]).getPath();
   
            return "";
        } catch (Exception errorException) {
            errorMessage = "Exception occurred in readLogParametersFromPropertyFile() function; Exception Message : " + errorException.getMessage();
            return errorMessage;
        }
        
    }
  
}

