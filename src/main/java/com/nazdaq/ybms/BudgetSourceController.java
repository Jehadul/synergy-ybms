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

import com.nazdaq.ybms.model.Budget;
import com.nazdaq.ybms.model.BudgetSource;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class BudgetSourceController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBudgetSourceForm", method = RequestMethod.GET)
	public ModelAndView addBudgetSourceForm (@ModelAttribute("command") BudgetSource budgetSourceBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<BudgetSource> budgetSourceList = (List<BudgetSource>)(Object) commonService.getAllObjectList("BudgetSource");
		for (BudgetSource budgetSource : budgetSourceList) {			
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}
		model.put("budgetSource", budgetSourceBean);
		model.put("budgetSourceList", budgetSourceList);
		return new ModelAndView("addBudgetSourceForm", model);
	}
	
	//saveTeamLeader
	@RequestMapping(value = "/saveBudgetSource", method = RequestMethod.POST)
	public ModelAndView saveBudgetSource (@ModelAttribute("command") BudgetSource budgetSourceBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes) throws UnsupportedEncodingException, CharacterCodingException{		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(budgetSourceBean.getId() != null) {
			BudgetSource budgetSourceById = (BudgetSource)commonService.getAnObjectByAnyUniqueColumn("BudgetSource", "id", budgetSourceBean.getId().toString());
			if(budgetSourceById != null) {		
				budgetSourceById.setName(convertToUTF8(budgetSourceBean.getName()));
				budgetSourceById.setRemarks(budgetSourceBean.getRemarks());
				commonService.saveOrUpdateModelObjectToDB(budgetSourceById);
				attributes.addFlashAttribute("successMsg", "Budget Source Updated Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		} else {
			BudgetSource budgetSourceByName = (BudgetSource)commonService.getAnObjectByAnyUniqueColumn("BudgetSource", "name", budgetSourceBean.getName().trim());
			if(budgetSourceByName == null) {
				budgetSourceBean.setName(convertToUTF8(budgetSourceBean.getName()));
				commonService.saveOrUpdateModelObjectToDB(budgetSourceBean);
				attributes.addFlashAttribute("successMsg", "Budget Source Saved Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		}
		
		return new ModelAndView("redirect:/addBudgetSourceForm");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editBudgetSource", method = RequestMethod.GET)
	public ModelAndView editBudgetSourceForm (@ModelAttribute("command") BudgetSource budgetSourceBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();
		
		BudgetSource budgetSourceById = (BudgetSource)commonService.getAnObjectByAnyUniqueColumn("BudgetSource", "id", budgetSourceBean.getId().toString());
		List<BudgetSource> budgetSourceList = (List<BudgetSource>)(Object) commonService.getAllObjectList("BudgetSource");
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}
		
		budgetSourceById.setName(convertFromUTF8(budgetSourceById.getName()));
		model.put("budgetSource", budgetSourceById);
		model.put("budgetSourceList", budgetSourceList);
		model.put("edit", true);
		return new ModelAndView("addBudgetSourceForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBudgetSource", method = RequestMethod.GET)
	public ModelAndView deleteBudgetSource (@ModelAttribute("command") BudgetSource budgetSourceBean, BindingResult result, Principal principal, RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(budgetSourceBean.getId() != null) {			
			List<Budget> budgetList = (List<Budget>)(Object)commonService.getObjectListByAnyColumn("Budget", "budgetSource.id", budgetSourceBean.getId().toString());
			if(budgetList.size() > 0) {
				attributes.addFlashAttribute("successMsg", "This Budget Source Already Used for Budget!!");
			} else {
				commonService.deleteAnObjectById("BudgetSource", budgetSourceBean.getId());
				attributes.addFlashAttribute("successMsg", "Budget Source Deleted Successfully!!");
			}
		} else {
			attributes.addFlashAttribute("successMsg", "Please Try Again!!");
		}
		
		return new ModelAndView("redirect:/addBudgetSourceForm");
	}
	
}
