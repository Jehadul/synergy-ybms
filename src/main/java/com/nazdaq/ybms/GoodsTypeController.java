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
import com.nazdaq.ybms.model.GoodsType;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class GoodsTypeController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addGoodsTypeForm", method = RequestMethod.GET)
	public ModelAndView addGoodsTypeForm (@ModelAttribute("command") GoodsType goodsTypeBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<GoodsType> goodsTypeList = (List<GoodsType>)(Object) commonService.getAllObjectList("GoodsType");
		/*for (GoodsType goodsType : goodsTypeList) {			
			goodsType.setName(convertFromUTF8(goodsType.getName()));
		}*/
		model.put("goodsType", goodsTypeBean);
		model.put("goodsTypeList", goodsTypeList);
		return new ModelAndView("addGoodsTypeForm", model);
	}
	
	//saveTeamLeader
	@RequestMapping(value = "/saveGoodsType", method = RequestMethod.POST)
	public ModelAndView saveGoodsType (@ModelAttribute("command") GoodsType goodsTypeBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes) throws UnsupportedEncodingException, CharacterCodingException{		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(goodsTypeBean.getId() != null) {
			GoodsType goodsTypeById = (GoodsType)commonService.getAnObjectByAnyUniqueColumn("GoodsType", "id", goodsTypeBean.getId().toString());
			if(goodsTypeById != null) {		
				//goodsTypeById.setName(convertToUTF8(goodsTypeBean.getName()));
				goodsTypeById.setName(goodsTypeBean.getName());
				goodsTypeById.setRemarks(goodsTypeBean.getRemarks());
				commonService.saveOrUpdateModelObjectToDB(goodsTypeById);
				attributes.addFlashAttribute("successMsg", "Goods Type Updated Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		} else {
			GoodsType goodsTypeByName = (GoodsType)commonService.getAnObjectByAnyUniqueColumn("GoodsType", "name", goodsTypeBean.getName().trim());
			if(goodsTypeByName == null) {
				//goodsTypeBean.setName(convertToUTF8(goodsTypeBean.getName()));
				commonService.saveOrUpdateModelObjectToDB(goodsTypeBean);
				attributes.addFlashAttribute("successMsg", "Goods Type Saved Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		}
		
		return new ModelAndView("redirect:/addGoodsTypeForm");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editGoodsType", method = RequestMethod.GET)
	public ModelAndView editGoodsTypeForm (@ModelAttribute("command") GoodsType goodsTypeBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();
		
		GoodsType goodsTypeById = (GoodsType)commonService.getAnObjectByAnyUniqueColumn("GoodsType", "id", goodsTypeBean.getId().toString());
		List<GoodsType> goodsTypeList = (List<GoodsType>)(Object) commonService.getAllObjectList("GoodsType");
		/*for (GoodsType goodsType : goodsTypeList) {			
			goodsType.setName(convertFromUTF8(goodsType.getName()));
		}*/
		//goodsTypeById.setName(convertFromUTF8(goodsTypeById.getName()));
		
		model.put("goodsType", goodsTypeById);
		model.put("goodsTypeList", goodsTypeList);
		model.put("edit", true);
		return new ModelAndView("addGoodsTypeForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteGoodsType", method = RequestMethod.GET)
	public ModelAndView deleteGoodsType (@ModelAttribute("command") GoodsType goodsTypeBean, BindingResult result, Principal principal, RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(goodsTypeBean.getId() != null) {			
			List<Budget> budgetList = (List<Budget>)(Object)commonService.getObjectListByAnyColumn("Budget", "goodsType.id", goodsTypeBean.getId().toString());
			if(budgetList.size() > 0) {
				attributes.addFlashAttribute("successMsg", "This Goods Type Already Used for Budget!!");
			} else {
				commonService.deleteAnObjectById("GoodsType", goodsTypeBean.getId());
				attributes.addFlashAttribute("successMsg", "Goods Type Deleted Successfully!!");
			}
		} else {
			attributes.addFlashAttribute("successMsg", "Please Try Again!!");
		}
		
		return new ModelAndView("redirect:/addGoodsTypeForm");
	}
}
