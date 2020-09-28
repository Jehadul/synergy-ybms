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

import com.nazdaq.ybms.model.FiscalYear;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class FiscalYearController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addFiscalYearForm", method = RequestMethod.GET)
	public ModelAndView addFiscalYearForm (@ModelAttribute("command") FiscalYear fiscalYearBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<FiscalYear> fiscalYearList = (List<FiscalYear>)(Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {			
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		model.put("fiscalYears", fiscalYearBean);
		model.put("fiscalYearList", fiscalYearList);
		return new ModelAndView("addFiscalYearForm", model);
	}
	
	//saveTeamLeader
	@RequestMapping(value = "/saveFiscalYear", method = RequestMethod.POST)
	public ModelAndView saveTeamLeader (@ModelAttribute("command") FiscalYear fiscalYearBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes) throws UnsupportedEncodingException, CharacterCodingException{		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(fiscalYearBean.getId() != null) {
			FiscalYear fiscalYearById = (FiscalYear)commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id", fiscalYearBean.getId().toString());
			if(fiscalYearById != null) {		
				fiscalYearById.setName(convertToUTF8(fiscalYearBean.getName()));
				fiscalYearById.setRemarks(fiscalYearBean.getRemarks());
				
				fiscalYearById.setFromYear(fiscalYearBean.getFromYear());
				fiscalYearById.setToYear(fiscalYearBean.getToYear());
				
				commonService.saveOrUpdateModelObjectToDB(fiscalYearById);
				attributes.addFlashAttribute("successMsg", "Fiscal Year Updated Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		} else {
			FiscalYear fiscalYearByName = (FiscalYear)commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "name", fiscalYearBean.getName().trim());
			if(fiscalYearByName == null) {
				fiscalYearBean.setName(convertToUTF8(fiscalYearBean.getName()));
				commonService.saveOrUpdateModelObjectToDB(fiscalYearBean);
				attributes.addFlashAttribute("successMsg", "Fiscal Year Saved Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		}
		
		return new ModelAndView("redirect:/addFiscalYearForm");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editFiscalYear", method = RequestMethod.GET)
	public ModelAndView editFiscalYearForm (@ModelAttribute("command") FiscalYear fiscalYearBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();
		
		FiscalYear fiscalYearById = (FiscalYear)commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id", fiscalYearBean.getId().toString());
		List<FiscalYear> fiscalYearList = (List<FiscalYear>)(Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		
		fiscalYearById.setName(convertFromUTF8(fiscalYearById.getName()));
		model.put("fiscalYears", fiscalYearById);
		model.put("fiscalYearList", fiscalYearList);
		model.put("edit", true);
		return new ModelAndView("addFiscalYearForm", model);
	}

}
