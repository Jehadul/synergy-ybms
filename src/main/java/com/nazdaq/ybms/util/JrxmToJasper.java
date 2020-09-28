package com.nazdaq.ybms.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;


/**
 * @author RAFIQUL
 *
 */
public class JrxmToJasper {
	public static void main(String[] args) throws JRException {
		// TODO Auto-generated method stub
		
        JasperCompileManager.compileReportToFile(
        		"D:\\gitproject\\ybms\\src\\main\\resources\\report\\bmsRpoOneRest.jrxml", 
        		"D:\\gitproject\\ybms\\src\\main\\resources\\report\\bmsRpoOneRest.jasper");
     }

}
