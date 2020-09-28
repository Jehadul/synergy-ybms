package com.nazdaq.ybms.util;

public class UTF8Converter implements Constants {
	 // convert from internal Java String format -> UTF-8
    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    
    public static String convertFromUTF8(String s) {
    	String text="";
    	if(s!=null) {
    		byte[] byteText = s.getBytes(ISO_8859_1); 
    		String originalString = new String(byteText, UTF_8);
    		text=originalString;
    	}
    	
        return text;
    }
}
