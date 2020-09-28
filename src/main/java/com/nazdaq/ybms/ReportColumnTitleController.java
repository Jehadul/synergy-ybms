package com.nazdaq.ybms;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.security.Principal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nazdaq.ybms.model.ReportColumnTitle;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class ReportColumnTitleController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addReportColumnTitleForm", method = RequestMethod.GET)
	public ModelAndView addReportColumnTitleForm (@ModelAttribute("command") ReportColumnTitle reportColumnTitleBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<ReportColumnTitle> reportColumnTitleList = (List<ReportColumnTitle>)(Object) commonService.getAllObjectList("ReportColumnTitle");
		for (ReportColumnTitle reportColumnTitle : reportColumnTitleList) {			
			reportColumnTitle.setTitleBn(convertFromUTF8(reportColumnTitle.getTitleBn()));
		}
		model.put("reportColumnTitle", reportColumnTitleBean);
		model.put("reportColumnTitleList", reportColumnTitleList);
		return new ModelAndView("addReportColumnTitleForm", model);
	}
	
	//saveReportColumnTitle
	@RequestMapping(value = "/saveReportColumnTitle", method = RequestMethod.POST)
	public ModelAndView saveColorCode (@ModelAttribute("command") ReportColumnTitle reportColumnTitleBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes) throws UnsupportedEncodingException, CharacterCodingException{		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(reportColumnTitleBean.getId() != null) {
			ReportColumnTitle reportColumnTitleById = (ReportColumnTitle)commonService.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "id", reportColumnTitleBean.getId().toString());
			if(reportColumnTitleById != null) {		
				reportColumnTitleById.setTitleBn(convertToUTF8(reportColumnTitleBean.getTitleBn()));
				reportColumnTitleById.setRemarks(reportColumnTitleBean.getRemarks());
				reportColumnTitleById.setTitleEn(reportColumnTitleBean.getTitleEn());
				
				commonService.saveOrUpdateModelObjectToDB(reportColumnTitleById);
				attributes.addFlashAttribute("successMsg", "Report Column Title Updated Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		} else {
			ReportColumnTitle colorCodeByKeyword = (ReportColumnTitle)commonService.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "keyword", reportColumnTitleBean.getKeyword().trim());
			if(colorCodeByKeyword == null) {
				reportColumnTitleBean.setTitleBn(convertToUTF8(reportColumnTitleBean.getTitleBn()));
				commonService.saveOrUpdateModelObjectToDB(reportColumnTitleBean);
				attributes.addFlashAttribute("successMsg", "Report Column Title Saved Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		}
		
		return new ModelAndView("redirect:/addReportColumnTitleForm");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editReportColumnTitle", method = RequestMethod.GET)
	public ModelAndView editColorCodeForm (@ModelAttribute("command") ReportColumnTitle reportColumnTitleBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();
		
		ReportColumnTitle reportColumnTitleById = (ReportColumnTitle)commonService.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "id", reportColumnTitleBean.getId().toString());
		List<ReportColumnTitle> reportColumnTitleList = (List<ReportColumnTitle>)(Object) commonService.getAllObjectList("ReportColumnTitle");
		for (ReportColumnTitle reportColumnTitle : reportColumnTitleList) {			
			reportColumnTitle.setTitleBn(convertFromUTF8(reportColumnTitle.getTitleBn()));
		}
		
		reportColumnTitleById.setTitleBn(convertFromUTF8(reportColumnTitleById.getTitleBn()));
		model.put("reportColumnTitle", reportColumnTitleById);
		model.put("reportColumnTitleList", reportColumnTitleList);
		model.put("edit", true);
		return new ModelAndView("addReportColumnTitleForm", model);
	}
}
