package com.nazdaq.ybms;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.security.Principal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nazdaq.ybms.model.Budget;
import com.nazdaq.ybms.model.Point;
import com.nazdaq.ybms.model.PointType;
import com.nazdaq.ybms.model.TeamLeader;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class PointController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPointForm", method = RequestMethod.GET)
	public ModelAndView addPointForm (@ModelAttribute("command") Point pointBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<Point> pointList = (List<Point>)(Object) commonService.getAllObjectList("Point");
		
		List<PointType> pointTypeList = (List<PointType>)(Object) commonService.getAllObjectList("PointType");
		List<TeamLeader> teamLeaderList = (List<TeamLeader>)(Object) commonService.getAllObjectList("TeamLeader");
		
		for (PointType pointType : pointTypeList) {
			pointType.setName(convertFromUTF8(pointType.getName()));
		}
		
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}
		
		
		for (Point point : pointList) {			
			point.setName(convertFromUTF8(point.getName()));
			
			for (PointType pointType : pointTypeList) {
				if(point.getPointType().getId().toString().equals(pointType.getId().toString())) {
					point.getPointType().setName(pointType.getName());
					break;
				}
			}
			
			for (TeamLeader teamLeader : teamLeaderList) {
				if(point.getTeamLeader().getId().toString().equals(teamLeader.getId().toString())) {
					point.getTeamLeader().setName(teamLeader.getName());
					break;
				}
			}
			
		}
		
		
		
		model.put("point", pointBean);
		model.put("pointList", pointList);
		
		model.put("pointTypeList", pointTypeList);
		model.put("teamLeaderList", teamLeaderList);
		return new ModelAndView("addPointForm", model);
	}
	
	//saveTeamLeader
	@RequestMapping(value = "/savePoint", method = RequestMethod.POST)
	public ModelAndView saveTeamLeader (@ModelAttribute("command") Point pointBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes) throws UnsupportedEncodingException, CharacterCodingException{		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PointType pointType = (PointType) commonService.getAnObjectByAnyUniqueColumn("PointType", "id", pointBean.getPointTypeId().toString());
		TeamLeader teamLeader = (TeamLeader) commonService.getAnObjectByAnyUniqueColumn("TeamLeader", "id", pointBean.getTeamLeaderId().toString());
		
		if(pointBean.getId() != null) {
			Point pointById = (Point)commonService.getAnObjectByAnyUniqueColumn("Point", "id", pointBean.getId().toString());
			if(pointById != null) {		
				pointById.setName(convertToUTF8(pointBean.getName()));
				pointById.setRemarks(pointBean.getRemarks());
				pointById.setNameEn(pointBean.getNameEn());
				pointById.setAddress(pointBean.getAddress());
				pointById.setEmailAddress(pointBean.getEmailAddress());
				pointById.setKeyword(pointBean.getKeyword());
				pointById.setMobileNo(pointBean.getMobileNo());
				
				
				pointById.setPointType(pointType);
				pointById.setTeamLeader(teamLeader);
				
				pointById.setModifiedBy(principal.getName());
				pointById.setModifiedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(pointById);
				attributes.addFlashAttribute("successMsg", "Point Updated Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		} else {
			Point pointByName = (Point)commonService.getAnObjectByAnyUniqueColumn("Point", "name", pointBean.getName().trim());
			if(pointByName == null) {
				pointBean.setName(convertToUTF8(pointBean.getName()));
				
				pointBean.setPointType(pointType);
				pointBean.setTeamLeader(teamLeader);
				
				pointBean.setCreatedBy(principal.getName());
				pointBean.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(pointBean);
				attributes.addFlashAttribute("successMsg", "Point Saved Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		}
		
		return new ModelAndView("redirect:/addPointForm");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editPoint", method = RequestMethod.GET)
	public ModelAndView editPointForm (@ModelAttribute("command") Point pointBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();
		
		Point pointById = (Point)commonService.getAnObjectByAnyUniqueColumn("Point", "id", pointBean.getId().toString());
		List<Point> pointList = (List<Point>)(Object) commonService.getAllObjectList("Point");
		
		List<PointType> pointTypeList = (List<PointType>)(Object) commonService.getAllObjectList("PointType");
		List<TeamLeader> teamLeaderList = (List<TeamLeader>)(Object) commonService.getAllObjectList("TeamLeader");
		
		for (PointType pointType : pointTypeList) {
			pointType.setName(convertFromUTF8(pointType.getName()));
		}
		
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}
		
		for (Point point : pointList) {			
			point.setName(convertFromUTF8(point.getName()));
			
			for (PointType pointType : pointTypeList) {
				if(point.getPointType().getId().toString().equals(pointType.getId().toString())) {
					point.getPointType().setName(pointType.getName());
					break;
				}
			}
			
			for (TeamLeader teamLeader : teamLeaderList) {
				if(point.getTeamLeader().getId().toString().equals(teamLeader.getId().toString())) {
					point.getTeamLeader().setName(teamLeader.getName());
					break;
				}
			}
			
		}
		
		pointById.setName(convertFromUTF8(pointById.getName()));
		model.put("point", pointById);
		model.put("pointList", pointList);
		
		model.put("pointTypeList", pointTypeList);
		model.put("teamLeaderList", teamLeaderList);
		
		model.put("edit", true);
		return new ModelAndView("addPointForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePoint", method = RequestMethod.GET)
	public ModelAndView deletePoint (@ModelAttribute("command") Point pointBean, BindingResult result, Principal principal, RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(pointBean.getId() != null) {			
			List<Budget> budgetList = (List<Budget>)(Object)commonService.getObjectListByAnyColumn("Budget", "point.id", pointBean.getId().toString());
			if(budgetList.size() > 0) {
				attributes.addFlashAttribute("successMsg", "This Point Already Used for Budget!!");
			} else {
				commonService.deleteAnObjectById("Point", pointBean.getId());
				attributes.addFlashAttribute("successMsg", "Point Deleted Successfully!!");
			}
		} else {
			attributes.addFlashAttribute("successMsg", "Please Try Again!!");
		}
		
		return new ModelAndView("redirect:/addPointForm");
	}
}
