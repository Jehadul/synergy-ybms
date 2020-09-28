package com.nazdaq.ybms.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.nazdaq.ybms.model.ReportColumnTitle;
import com.nazdaq.ybms.service.CommonService;

public class CommonConverter implements Constants{
	
	private static  String[] banglaDigits = {};
	//private static final String[] banglaDigits = {'০','১','২','৩','৪','৫','৬','৭','৮','৯'};
	
	private static final char[] englishDigits = {'0','1','2','3','4','5','6','7','8','9'};
	 
	public  static final String  getDigitBanglaFromEnglish(String number,ReportColumnTitle reportColumnTitle){
			banglaDigits=reportColumnTitle.getTitleBn().toString().split(",");
	    if(number==null)
	         return new String("");
	    StringBuilder builder = new StringBuilder();
	    try{
	         for(int i =0;i<number.length();i++){
	              if(Character.isDigit(number.charAt(i))){
	                  if(((int)(number.charAt(i))-48)<=9){
	                      builder.append(banglaDigits[(int)(number.charAt(i))-48]);
	                  }else{
	                      builder.append(number.charAt(i));
	                  }
	              }else{
	                   builder.append(number.charAt(i));
	              }
	          }
	}catch(Exception e){
	         //logger.debug("getDigitBanglaFromEnglish: ",e);
	         return new String("");
	       }
	     return builder.toString().replace("'", "");
	}
	public  static final String  getDigitBanglaFromEnglishnew(Double numberInt,ReportColumnTitle reportColumnTitle){
		banglaDigits=reportColumnTitle.getTitleBn().toString().split(",");
		String number="0.0";
		if(numberInt!=null) {
			number=numberInt.toString();
		}
    if(number==null)
         return new String("");
    StringBuilder builder = new StringBuilder();
    try{
         for(int i =0;i<number.length();i++){
              if(Character.isDigit(number.charAt(i))){
                  if(((int)(number.charAt(i))-48)<=9){
                      builder.append(banglaDigits[(int)(number.charAt(i))-48]);
                  }else{
                      builder.append(number.charAt(i));
                  }
              }else{
                   builder.append(number.charAt(i));
              }
          }
}catch(Exception e){
         //logger.debug("getDigitBanglaFromEnglish: ",e);
         return new String("");
       }
     return builder.toString().replace("'", "");
}
	public  static final String convertDateformate(String dateFOrmate) {
		String dateReturn="";
		String[] MonthS = {"Jan", "Feb", "Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		String[] dateArry=dateFOrmate.split("-");
		for (int i = 0; i < MonthS.length; i++) {
			if((Integer.parseInt(dateArry[1].toString())-1)==i) {
				dateReturn=dateArry[0]+"-"+MonthS[i]+"-"+dateArry[2];
			}
		}
		
		return dateReturn;
	}
}
