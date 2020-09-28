package com.nazdaq.ybms;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nazdaq.ybms.model.Bill;
import com.nazdaq.ybms.model.Delivered;
import com.nazdaq.ybms.model.FiscalYear;
import com.nazdaq.ybms.model.StockReport;
import com.nazdaq.ybms.model.StockReportHistory;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class StockReportController extends UTF8Converter implements Constants{
			
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addStockReportForm", method = RequestMethod.GET)
	public ModelAndView addStockReportForm (@ModelAttribute("command") StockReport stockReportBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();	
		String fiscalYear = getCurrentFiscalYear();
		FiscalYear fisYear = (FiscalYear)commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "name", fiscalYear);
		StockReport stockReportDb = (StockReport)commonService.getAnObjectByAnyUniqueColumn("StockReport", "fiscalYear.name", fiscalYear);
		List<Delivered> deliveredList = (List<Delivered>)(Object)commonService.getObjectListByAnyColumn("Delivered", "fiscalYear.name", fiscalYear);
		Double dlvAmount = 0.0;
		if(deliveredList != null && deliveredList.size() > 0) {
			for (Delivered delivered : deliveredList) {
				dlvAmount+=delivered.getDlvAmount();
			}
		}
		
		if(stockReportDb == null) {
			stockReportBean.setDlvAtPoint(dlvAmount);
			stockReportBean.setCreatedBy(principal.getName());
			stockReportBean.setCreatedDate(new Date());
			stockReportBean.setFiscalYear(fisYear);
			commonService.saveOrUpdateModelObjectToDB(stockReportBean);
			
			//history
			StockReportHistory srh = new StockReportHistory(null, fisYear, 0.0, 0.0, 0.0, dlvAmount, principal.getName(), new Date());
			commonService.saveOrUpdateModelObjectToDB(srh);
			
			model.put("stockReport", stockReportBean);
		} else {
			if(stockReportDb.getDlvAtPoint() != dlvAmount) {
				stockReportDb.setDlvAtPoint(dlvAmount);						
				commonService.saveOrUpdateModelObjectToDB(stockReportDb);
				model.put("stockReport", stockReportDb);
				//history
				StockReportHistory srh = new StockReportHistory(null, fisYear, stockReportDb.getWarehouse(), stockReportDb.getLocalCurrency(), stockReportDb.getWorkOrder(), dlvAmount, principal.getName(), new Date());
				commonService.saveOrUpdateModelObjectToDB(srh);
			}			
		}
		
		return new ModelAndView("addStockReportForm", model);
	}
	
	//updateStockReport
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateStockReport", method = RequestMethod.POST)
	public ModelAndView updateStockReport (@ModelAttribute("command") StockReport stockReportBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		String fiscalYear = getCurrentFiscalYear();
		
		if(stockReportBean.getId() != null) {
			StockReport stockReportDb = (StockReport)commonService.getAnObjectByAnyUniqueColumn("StockReport", "id", stockReportBean.getId().toString());
			if(stockReportDb != null) {
				List<Delivered> deliveredList = (List<Delivered>)(Object)commonService.getObjectListByAnyColumn("Delivered", "fiscalYear.name", fiscalYear);
				Double dlvAmount = 0.0;
				if(deliveredList != null && deliveredList.size() > 0) {
					for (Delivered delivered : deliveredList) {
						dlvAmount+=delivered.getDlvAmount();
					}
				}
				
				stockReportDb.setModifiedBy(principal.getName());
				stockReportDb.setModifiedDate(new Date());
				
				stockReportDb.setWarehouse(stockReportBean.getWarehouse());
				stockReportDb.setLocalCurrency(stockReportBean.getLocalCurrency());
				stockReportDb.setWorkOrder(stockReportBean.getWorkOrder());
				
				commonService.saveOrUpdateModelObjectToDB(stockReportDb);
				//history
				StockReportHistory srh = new StockReportHistory(null, stockReportDb.getFiscalYear(), stockReportDb.getWarehouse(), 
						stockReportDb.getLocalCurrency(), stockReportDb.getWorkOrder(), dlvAmount, principal.getName(), new Date());
				commonService.saveOrUpdateModelObjectToDB(srh);
			}
		}
		
		return new ModelAndView("redirect:/addStockReportForm");
	}
	
	private static String getCurrentFiscalYear() {
		Date d = new Date();
		String res;
		Integer month = (d.getMonth()+1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		
		Integer year = cal.get(Calendar.YEAR);
		if(month > 6) {
			res = year+"-"+(year+1);
		} else {
			res = (year-1)+"-"+(year);
		}
		return res;
	}

}
