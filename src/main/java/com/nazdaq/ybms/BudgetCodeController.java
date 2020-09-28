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
import com.nazdaq.ybms.model.BudgetCode;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class BudgetCodeController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBudgetCodeForm", method = RequestMethod.GET)
	public ModelAndView addBudgetCodeForm (@ModelAttribute("command") BudgetCode budgetCodeBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<BudgetCode> budgetCodeList = (List<BudgetCode>)(Object) commonService.getAllObjectList("BudgetCode");
		for (BudgetCode budgetCode : budgetCodeList) {			
			budgetCode.setName(convertFromUTF8(budgetCode.getName()));
		}
		model.put("budgetCode", budgetCodeBean);
		model.put("budgetCodeList", budgetCodeList);
		return new ModelAndView("addBudgetCodeForm", model);
	}
	
	//saveTeamLeader
	@RequestMapping(value = "/saveBudgetCode", method = RequestMethod.POST)
	public ModelAndView saveBudgetCode (@ModelAttribute("command") BudgetCode budgetCodeBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes) throws UnsupportedEncodingException, CharacterCodingException{		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(budgetCodeBean.getId() != null) {
			BudgetCode budgetCodeById = (BudgetCode)commonService.getAnObjectByAnyUniqueColumn("BudgetCode", "id", budgetCodeBean.getId().toString());
			if(budgetCodeById != null) {		
				budgetCodeById.setName(convertToUTF8(budgetCodeBean.getName()));
				budgetCodeById.setRemarks(budgetCodeBean.getRemarks());
				commonService.saveOrUpdateModelObjectToDB(budgetCodeById);
				attributes.addFlashAttribute("successMsg", "Budget Code Updated Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		} else {
			BudgetCode budgetCodeByName = (BudgetCode)commonService.getAnObjectByAnyUniqueColumn("BudgetCode", "name", budgetCodeBean.getName().trim());
			if(budgetCodeByName == null) {
				budgetCodeBean.setName(convertToUTF8(budgetCodeBean.getName()));
				commonService.saveOrUpdateModelObjectToDB(budgetCodeBean);
				attributes.addFlashAttribute("successMsg", "Budget Code Saved Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		}
		
		return new ModelAndView("redirect:/addBudgetCodeForm");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editBudgetCode", method = RequestMethod.GET)
	public ModelAndView editBudgetCodeForm (@ModelAttribute("command") BudgetCode budgetCodeBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();
		
		BudgetCode budgetCodeById = (BudgetCode)commonService.getAnObjectByAnyUniqueColumn("BudgetCode", "id", budgetCodeBean.getId().toString());
		List<BudgetCode> budgetCodeList = (List<BudgetCode>)(Object) commonService.getAllObjectList("BudgetCode");
		for (BudgetCode budgetCode : budgetCodeList) {
			budgetCode.setName(convertFromUTF8(budgetCode.getName()));
		}
		
		budgetCodeById.setName(convertFromUTF8(budgetCodeById.getName()));
		model.put("budgetCode", budgetCodeById);
		model.put("budgetCodeList", budgetCodeList);
		model.put("edit", true);
		return new ModelAndView("addBudgetCodeForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBudgetCode", method = RequestMethod.GET)
	public ModelAndView deleteBudgetCode (@ModelAttribute("command") BudgetCode budgetCodeBean, BindingResult result, Principal principal, RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(budgetCodeBean.getId() != null) {			
			List<Budget> budgetList = (List<Budget>)(Object)commonService.getObjectListByAnyColumn("Budget", "budgetCode.id", budgetCodeBean.getId().toString());
			if(budgetList.size() > 0) {
				attributes.addFlashAttribute("successMsg", "This Budget Code Already Used for Budget!!");
			} else {
				commonService.deleteAnObjectById("BudgetCode", budgetCodeBean.getId());
				attributes.addFlashAttribute("successMsg", "Budget Code Deleted Successfully!!");
			}
		} else {
			attributes.addFlashAttribute("successMsg", "Please Try Again!!");
		}
		
		return new ModelAndView("redirect:/addBudgetCodeForm");
	}
}
