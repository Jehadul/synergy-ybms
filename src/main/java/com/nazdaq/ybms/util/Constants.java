package com.nazdaq.ybms.util;

import java.nio.charset.Charset;

public interface Constants {
	//status
	public static final String SAVED = "1"; 
	public static final String DELETED = "2";
	public static final String SEND_TO_CANDIDATE = "3";	
	public static final String SUBMITTED = "4"; 
	public static final String APPROVED_APP_LETTER = "5"; 
	public static final String DECLINE = "6";
	
	public static final String QUES_SET_ONE_SEND = "7"; 	 
	public static final String JOINED = "16";
	
	public static final String QUES_SET_ONE_ANS_SUBMIT = "8";
	public static final String QUES_SET_TWO_SEND = "9";
	public static final String QUES_SET_TWO_ANS_SUBMIT = "10";
	public static final String QUES_SET_THREE_SEND = "11";
	public static final String QUES_SET_THREE_ANS_SUBMIT = "12";
	public static final String RECRUIT_PROCESS_COMPLETED = "13";
	
	//NEW PROCESS BY SANDIP DAS
	public static final String CANDIDATE_ACCEPTED_AL = "14";
	public static final String CANDIDATE_REJECTED_AL = "15";
	
	//SMS & Email
	public static final String encoding = "UTF-8";
    public static final String ampersand = "&";
    public static final String equal = "=";
    public static final String APPROVAL = "APPROVAL";
	public static final String REJECT = "REJECT";
	public static final String NOTIFICATION = "NOTIFICATION";
	
	//role
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
	
	//System User
	public static final String SYSTEM_USER = "System";
	
	//Company Keyword
	public static final String SYNERGY = "SY";
	public static final String LEXICON = "LXN";
	public static final String NAZDAQ = "NZ";
	public static final String DCIMCH = "DCIMCH";
	public static final String NORTH_EGG = "NEL";
	public static final String MEDITECH = "MT";
	public static final String ARDENT_SYSTEM = "AS";
	public static final String CREATIVE_TRADE = "CT";
	public static final String NPPL = "NPPL";
	public static final String KASIR_UDDIN_HOSPITAL = "KUMH";
	public static final String APPLE_CERAMICS = "ACPL";
	
	public static final String EQUIPMENTS = "EQUIPMENTS";
	public static final String REAGENT = "REAGENT";
	public static final String REPAIR = "REPAIR";
	public static final String FURNITURE = "FURNITURE";
	public static final String COMPUTER = "COMPUTER";
	public static final String BOOKS = "BOOKS";
	
	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
	
	
}
