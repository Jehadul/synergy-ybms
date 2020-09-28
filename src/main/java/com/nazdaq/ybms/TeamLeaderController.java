package com.nazdaq.ybms;

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
import com.nazdaq.ybms.model.Point;
import com.nazdaq.ybms.model.TeamLeader;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class TeamLeaderController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTeamLeaderForm", method = RequestMethod.GET)
	public ModelAndView addTeamLeaderForm (@ModelAttribute("command") TeamLeader teamLeaderBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<TeamLeader> teamLeaderList = (List<TeamLeader>)(Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}
		model.put("teamLeader", teamLeaderBean);
		model.put("teamLeaderList", teamLeaderList);
		return new ModelAndView("addTeamLeaderForm", model);
	}
	
	//saveTeamLeader
	@RequestMapping(value = "/saveTeamLeader", method = RequestMethod.POST)
	public ModelAndView saveTeamLeader (@ModelAttribute("command") TeamLeader teamLeaderBean, BindingResult result, Principal principal, RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(teamLeaderBean.getId() != null) {
			TeamLeader teamLeaderById = (TeamLeader)commonService.getAnObjectByAnyUniqueColumn("TeamLeader", "id", teamLeaderBean.getId().toString());
			//TeamLeader teamLeaderByName = (TeamLeader)commonService.getAnObjectByAnyUniqueColumn("TeamLeader", "name", teamLeaderBean.getName().trim());
			if(teamLeaderById != null) {
				teamLeaderById.setModifiedBy(principal.getName());
				teamLeaderById.setModifiedDate(new Date());
				teamLeaderById.setName(convertToUTF8(teamLeaderBean.getName()));				
				teamLeaderById.setDesignation(teamLeaderBean.getDesignation());
				teamLeaderById.setEmailAddress(teamLeaderBean.getEmailAddress());
				teamLeaderById.setMobileNo(teamLeaderBean.getMobileNo());
				teamLeaderById.setRemarks(teamLeaderBean.getRemarks());
				commonService.saveOrUpdateModelObjectToDB(teamLeaderById);
				attributes.addFlashAttribute("successMsg", "Team Leader Updated Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		} else {
			TeamLeader teamLeaderByName = (TeamLeader)commonService.getAnObjectByAnyUniqueColumn("TeamLeader", "name", teamLeaderBean.getName().trim());
			if(teamLeaderByName == null) {
				teamLeaderBean.setName(convertToUTF8(teamLeaderBean.getName()));
				teamLeaderBean.setCreatedBy(principal.getName());
				teamLeaderBean.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(teamLeaderBean);
				attributes.addFlashAttribute("successMsg", "Team Leader Saved Successfully!!");
			} else {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			}
			
		}
		
		return new ModelAndView("redirect:/addTeamLeaderForm");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editTeamLeader", method = RequestMethod.GET)
	public ModelAndView editTeamLeaderForm (@ModelAttribute("command") TeamLeader teamLeaderBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<TeamLeader> teamLeaderList = (List<TeamLeader>)(Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}
		TeamLeader teamLeaderById = (TeamLeader)commonService.getAnObjectByAnyUniqueColumn("TeamLeader", "id", teamLeaderBean.getId().toString());
		teamLeaderById.setName(convertFromUTF8(teamLeaderById.getName()));
		model.put("teamLeader", teamLeaderById);
		model.put("teamLeaderList", teamLeaderList);
		model.put("edit", true);
		return new ModelAndView("addTeamLeaderForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteTeamLeader", method = RequestMethod.GET)
	public ModelAndView deleteTeamLeader (@ModelAttribute("command") TeamLeader teamLeaderBean, BindingResult result, Principal principal, RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(teamLeaderBean.getId() != null) {			
			List<Point> pointList = (List<Point>)(Object)commonService.getObjectListByAnyColumn("Point", "teamLeader.id", teamLeaderBean.getId().toString());
			if(pointList.size() > 0) {
				attributes.addFlashAttribute("successMsg", "This Team Leader Already Used for Point!!");
			} else {
				commonService.deleteAnObjectById("TeamLeader", teamLeaderBean.getId());
				attributes.addFlashAttribute("successMsg", "Team Leader Deleted Successfully!!");
			}
		} else {
			attributes.addFlashAttribute("successMsg", "Please Try Again!!");
		}
		
		return new ModelAndView("redirect:/addTeamLeaderForm");
	}

}
