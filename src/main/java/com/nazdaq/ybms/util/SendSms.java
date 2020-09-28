package com.nazdaq.ybms.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class SendSms implements Constants{
	
	
	
    Properties prop = new Properties();
	InputStream input = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SendSms sms = new SendSms();
		//sms.sendSmsTo("+8801750249953", "Taleb");
		
		sms.sendSmsTo("+8801711480713", "Taleb");
	}
	
	public void sendSmsTo(String mobileNo, String message){
		String queryString = "";
        
        try {     
        	
        	input = this.getClass().getResourceAsStream("/sms_config.properties");
        	prop.load(input);
        	
        	// sms config path
        	String smsConfigPathUrl = prop.getProperty("sms.config.path.url");
        	
        	String smsConfigPathUser = prop.getProperty("sms.config.path.user");
        	
        	String smsConfigPathPassword = prop.getProperty("sms.config.path.password");
        	
        	String smsConfigPathSender = prop.getProperty("sms.config.path.sender");
        	
        	String smsConfigPathTo = prop.getProperty("sms.config.path.to");
        	
        	String smsConfigPathMessage = prop.getProperty("sms.config.path.message");
        	
        	//sms config path value
        	
        	String smsConfigUser = prop.getProperty("sms.config.username");
        	
        	String smsConfigPassword = prop.getProperty("sms.config.password");
        	
        	String smsConfigSender = prop.getProperty("sms.config.sender");
        	
        	
        	// Build URL encoded query string        	     		
	        queryString = smsConfigPathUser + equal + URLEncoder.encode(smsConfigUser, encoding)
	  			  	+ ampersand + smsConfigPathPassword + equal + URLEncoder.encode(smsConfigPassword, encoding)
	  			  	+ ampersand + smsConfigPathTo + equal + URLEncoder.encode(mobileNo, encoding)
	  			  	+ ampersand + smsConfigPathSender + equal + URLEncoder.encode(smsConfigSender, encoding)
	  			  	+ ampersand + smsConfigPathMessage +equal + URLEncoder.encode(message, encoding);	
			
        	
			// Send request to the API servers over HTTPS
	        URL url = new URL(smsConfigPathUrl);	        
	        URLConnection conn = url.openConnection();
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setDoOutput(true);	 
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
	        //wr.write(queryString);
	        wr.write(queryString);
	        wr.flush();
	       
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	  	        String result = rd.readLine();
	  	        wr.close();
	  	        rd.close();
	  	        
	  	      

	  	        if(result == null) {
	  	          System.out.println("No response received");
	  	        }
	  	        else if(result.startsWith("id:")) {
	  	          System.out.println("Message sent successfully");
	  	        } 
	  	        else {
	  	          System.out.println("Error - " + result);
	  	        }
	  	      //System.exit(0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	
	//added by taleb
	public String getFormatedMobileNo(String mobile){
		String mobileNo = "";
		String [] mobileNos = mobile.split(",");
		mobileNo = mobileNos[0];
		
		if(mobileNo.length() == 10){
			return "+880" + mobileNo;
		} else if(mobileNo.length() == 11){
			return "+88" + mobileNo;
		}else if(mobileNo.length() == 13){
			return "+" + mobileNo;
		} else if(mobileNo.length() == 14){
			return mobileNo;
		} else {
			return mobileNo = "";
		}
	}

}
