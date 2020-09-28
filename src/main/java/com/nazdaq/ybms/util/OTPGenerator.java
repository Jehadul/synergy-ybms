package com.nazdaq.ybms.util;

import java.util.Random;

/**
 * @author Abu.taleb
 *
 */

public class OTPGenerator {
	public static void main(String [] a) {
		OTPGenerator otpg = new OTPGenerator();
		System.out.println(otpg.getGeneratedPassword(6));
	}
	
	public String getGeneratedPassword(Integer passLength) {
		String numar = new String("0123456789ABCDEFGHIJKLMNOP");
   		
		Random random = new Random();
		Integer[] result = new Integer[passLength];
		for(int i = 0; i<passLength; i++) {
			result[i] = random.nextInt(numar.length());
		}
			
		String password = "";
		for(int i = 0; i <result.length; i++) {
			password = password + numar.substring(result[i], result[i]+1);
		}
		
		return password;
	}
}
