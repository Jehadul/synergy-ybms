package com.nazdaq.ybms.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.AttributedCharacterIterator;

public class Test {

	public static void main(String[] args) {
		
		try {
		     GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("SolaimanLipi.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		Test test=new Test();
	
	}
	private  void gameLevel() {
		  try {
		     InputStream fnt_stream = getClass().getResourceAsStream("resources/SolaimanLipi.ttf");
		     Font myFont = Font.createFont(Font.TRUETYPE_FONT, fnt_stream);
		     Font SolaimanLipi = new Font("SolaimanLipi", Font.BOLD, 40);

		    
		  } catch (Exception ex) {
		     System.out.println(ex);
		  }
}
}