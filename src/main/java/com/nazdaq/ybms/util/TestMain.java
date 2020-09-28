package com.nazdaq.ybms.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class TestMain {
	public static void main(String[] args) throws ParseException {
		
        System.out.printf(TestMain.getPreviousFiscalYear());
     }
	
	private static String getPreviousFiscalYear() {
		Date d = new Date();
		String res;
		Integer month = (d.getMonth()+1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		
		Integer year = cal.get(Calendar.YEAR);
		if(month > 6) {
			res = (year-1)+"-"+(year);
		} else {
			res = (year-2)+"-"+(year-1);
		}
		return res;
	}
	

}
