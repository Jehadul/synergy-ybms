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

import com.nazdaq.ybms.model.Point;
import com.nazdaq.ybms.model.PointType;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class PointTypeController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPointTypeForm", method = RequestMethod.GET)
	public ModelAndView addPointTypeForm (@ModelAttribute("command") PointType pointTypeBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<PointType> pointTypeList = (List<PointType>)(Object) commonService.getAllObjectList("PointType");
		for (PointType pointType : pointTypeList) {			
			pointType.setName(convertFromUTF8(pointType.getName()));
		}
		model.put("pointType", pointTypeBean);
		model.put("pointTypeList", pointTypeList);
		return new ModelAndView("addPointTypeForm", model);
	}
	
	//saveTeamLeader
	@RequestMapping(value = "/savePointType", method = RequestMethod.POST)
	public ModelAndView saveTeamLeader (@ModelAttribute("command") PointType pointTypeBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes) throws UnsupportedEncodingException, CharacterCodingException{		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(pointTypeBean.getId() != null) {
			PointType pointTypeById = (PointType)commonService.getAnObjectByAnyUniqueColumn("PointType", "id", pointTypeBean.getId().toString());
			if(pointTypeById != null) {		
				pointTypeById.setName(convertToUTF8(pointTypeBean.getName()));
				pointTypeById.setRemarks(pointTypeBean.getRemarks());
				commonService.saveOrUpdateModelObjectToDB(pointTypeById);
				attributes.addFlashAttribute("successMsg", "Point Type Updated Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		} else {
			PointType pointTypeByName = (PointType)commonService.getAnObjectByAnyUniqueColumn("PointType", "name", pointTypeBean.getName().trim());
			if(pointTypeByName == null) {
				pointTypeBean.setName(convertToUTF8(pointTypeBean.getName()));
				commonService.saveOrUpdateModelObjectToDB(pointTypeBean);
				attributes.addFlashAttribute("successMsg", "Point Type Saved Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		}
		
		return new ModelAndView("redirect:/addPointTypeForm");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editPointType", method = RequestMethod.GET)
	public ModelAndView editPointTypeForm (@ModelAttribute("command") PointType pointTypeBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();
		
		PointType pointTypeById = (PointType)commonService.getAnObjectByAnyUniqueColumn("PointType", "id", pointTypeBean.getId().toString());
		List<PointType> pointTypeList = (List<PointType>)(Object) commonService.getAllObjectList("PointType");
		for (PointType pointType : pointTypeList) {
			pointType.setName(convertFromUTF8(pointType.getName()));
		}
		
		pointTypeById.setName(convertFromUTF8(pointTypeById.getName()));
		model.put("pointType", pointTypeById);
		model.put("pointTypeList", pointTypeList);
		model.put("edit", true);
		return new ModelAndView("addPointTypeForm", model);
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePointType", method = RequestMethod.GET)
	public ModelAndView deletePointType (@ModelAttribute("command") PointType pointTypeBean, BindingResult result, Principal principal, RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(pointTypeBean.getId() != null) {			
			List<Point> pointList = (List<Point>)(Object)commonService.getObjectListByAnyColumn("Point", "pointType.id", pointTypeBean.getId().toString());
			if(pointList.size() > 0) {
				attributes.addFlashAttribute("successMsg", "This Point Type Already Used for Point!!");
			} else {
				commonService.deleteAnObjectById("PointType", pointTypeBean.getId());
				attributes.addFlashAttribute("successMsg", "Point Type Deleted Successfully!!");
			}
		} else {
			attributes.addFlashAttribute("successMsg", "Please Try Again!!");
		}
		
		return new ModelAndView("redirect:/addPointTypeForm");
	}
   
}
