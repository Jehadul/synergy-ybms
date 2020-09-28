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
import com.nazdaq.ybms.model.ColorCode;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class ColorCodeController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addColorCodeForm", method = RequestMethod.GET)
	public ModelAndView addColorCodeForm (@ModelAttribute("command") ColorCode colorCodeBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<ColorCode> colorCodeList = (List<ColorCode>)(Object) commonService.getAllObjectList("ColorCode");
		for (ColorCode colorCode : colorCodeList) {			
			colorCode.setName(convertFromUTF8(colorCode.getName()));
		}
		model.put("colorCode", colorCodeBean);
		model.put("colorCodeList", colorCodeList);
		return new ModelAndView("addColorCodeForm", model);
	}
	
	//saveTeamLeader
	@RequestMapping(value = "/saveColorCode", method = RequestMethod.POST)
	public ModelAndView saveColorCode (@ModelAttribute("command") ColorCode colorCodeBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes) throws UnsupportedEncodingException, CharacterCodingException{		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(colorCodeBean.getId() != null) {
			ColorCode colorCodeById = (ColorCode)commonService.getAnObjectByAnyUniqueColumn("ColorCode", "id", colorCodeBean.getId().toString());
			if(colorCodeById != null) {		
				colorCodeById.setName(convertToUTF8(colorCodeBean.getName()));
				colorCodeById.setRemarks(colorCodeBean.getRemarks());
				colorCodeById.setHexaCode(colorCodeBean.getHexaCode());
				commonService.saveOrUpdateModelObjectToDB(colorCodeById);
				attributes.addFlashAttribute("successMsg", "Color Code Updated Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		} else {
			ColorCode colorCodeByName = (ColorCode)commonService.getAnObjectByAnyUniqueColumn("ColorCode", "name", colorCodeBean.getName().trim());
			if(colorCodeByName == null) {
				colorCodeBean.setName(convertToUTF8(colorCodeBean.getName()));
				commonService.saveOrUpdateModelObjectToDB(colorCodeBean);
				attributes.addFlashAttribute("successMsg", "Color Code Saved Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		}
		
		return new ModelAndView("redirect:/addColorCodeForm");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editColorCode", method = RequestMethod.GET)
	public ModelAndView editColorCodeForm (@ModelAttribute("command") ColorCode colorCodeBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();
		
		ColorCode colorCodeById = (ColorCode)commonService.getAnObjectByAnyUniqueColumn("ColorCode", "id", colorCodeBean.getId().toString());
		List<ColorCode> colorCodeList = (List<ColorCode>)(Object) commonService.getAllObjectList("ColorCode");
		for (ColorCode colorCode : colorCodeList) {
			colorCode.setName(convertFromUTF8(colorCode.getName()));
		}
		
		colorCodeById.setName(convertFromUTF8(colorCodeById.getName()));
		model.put("colorCode", colorCodeById);
		model.put("colorCodeList", colorCodeList);
		model.put("edit", true);
		return new ModelAndView("addColorCodeForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteColorCode", method = RequestMethod.GET)
	public ModelAndView deleteColorCode (@ModelAttribute("command") ColorCode colorCodeBean, BindingResult result, Principal principal, RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(colorCodeBean.getId() != null) {			
			List<Budget> budgetList = (List<Budget>)(Object)commonService.getObjectListByAnyColumn("Budget", "colorCode.id", colorCodeBean.getId().toString());
			if(budgetList.size() > 0) {
				attributes.addFlashAttribute("successMsg", "This Color Code Already Used for Budget!!");
			} else {
				commonService.deleteAnObjectById("ColorCode", colorCodeBean.getId());
				attributes.addFlashAttribute("successMsg", "Color Code Deleted Successfully!!");
			}
		} else {
			attributes.addFlashAttribute("successMsg", "Please Try Again!!");
		}
		
		return new ModelAndView("redirect:/addColorCodeForm");
	}
}
