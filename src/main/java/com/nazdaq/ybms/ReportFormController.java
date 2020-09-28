package com.nazdaq.ybms;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nazdaq.ybms.beans.ColorCodeBean;
import com.nazdaq.ybms.beans.CommonBean;
import com.nazdaq.ybms.beans.TeamLeaderColorCodeStatistic;
import com.nazdaq.ybms.model.Budget;
import com.nazdaq.ybms.model.BudgetSource;
import com.nazdaq.ybms.model.ColorCode;
import com.nazdaq.ybms.model.FiscalYear;
import com.nazdaq.ybms.model.Point;
import com.nazdaq.ybms.model.TeamLeader;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.CommonConverter;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
public class ReportFormController extends UTF8Converter implements Constants {

	@Autowired
	private CommonService commonService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alAltmntReportForm", method = RequestMethod.GET)
	public ModelAndView alAltmntReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("alAltmntReportForm", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/dlvReports", method = RequestMethod.GET)
	public ModelAndView dlvReports(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("dlvReportForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alAltmntReportFormRest", method = RequestMethod.GET)
	public ModelAndView alAltmntReportFormRest(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("alAltmntReportFormRest", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alAltmntTLReportForm", method = RequestMethod.GET)
	public ModelAndView alAltmntTLReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}

		model.put("fiscalYearList", fiscalYearList);
		model.put("teamLeaderList", teamLeaderList);

		return new ModelAndView("alAltmntTLReportForm", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alAltmntTLReportFormRest", method = RequestMethod.GET)
	public ModelAndView alAltmntTLReportFormRest(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}

		model.put("fiscalYearList", fiscalYearList);
		model.put("teamLeaderList", teamLeaderList);

		return new ModelAndView("alAltmntTLReportFormRest", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/teamLeaderPointList", method = RequestMethod.GET)
	public ModelAndView teamLeaderPointList(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}

		model.put("fiscalYearList", fiscalYearList);
		model.put("teamLeaderList", teamLeaderList);

		return new ModelAndView("teamLeaderPointList", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/altmntColorTeamLeaderWise", method = RequestMethod.GET)
	public ModelAndView altmntColorTeamLeaderWise(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}
		List<ColorCode>colorCodeList=(List<ColorCode>) (Object)commonService.getAllObjectList("ColorCode");

		model.put("fiscalYearList", fiscalYearList);
		model.put("teamLeaderList", teamLeaderList);
		model.put("colorCodeList", colorCodeList);

		return new ModelAndView("altmntColorTeamLeaderWise", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/altmntSrcReportForm", method = RequestMethod.GET)
	public ModelAndView altmntSrcReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<BudgetSource> budgetSourceList = (List<BudgetSource>) (Object) commonService
				.getAllObjectList("BudgetSource");
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}

		model.put("budgetSourceList", budgetSourceList);
		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("altmntSrcReportForm", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/altmntSrcReportFormRest", method = RequestMethod.GET)
	public ModelAndView altmntSrcReportFormRest(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<BudgetSource> budgetSourceList = (List<BudgetSource>) (Object) commonService
				.getAllObjectList("BudgetSource");
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}

		model.put("budgetSourceList", budgetSourceList);
		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("altmntSrcReportFormRest", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alAltmntPntReportForm", method = RequestMethod.GET)
	public ModelAndView alAltmntPntReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<Point> pointList = (List<Point>) (Object) commonService.getAllObjectList("Point");
		for (Point point : pointList) {
			point.setName(convertFromUTF8(point.getName()));
		}

		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}

		model.put("pointList", pointList);
		model.put("teamLeaderList", teamLeaderList);
		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("alAltmntPntReportForm", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alAltmntPntReportFormRest", method = RequestMethod.GET)
	public ModelAndView alAltmntPntReportFormRest(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<Point> pointList = (List<Point>) (Object) commonService.getAllObjectList("Point");
		for (Point point : pointList) {
			point.setName(convertFromUTF8(point.getName()));
		}

		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}

		model.put("pointList", pointList);
		model.put("teamLeaderList", teamLeaderList);
		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("alAltmntPntReportFormRest", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alAltmntSrcReportForm", method = RequestMethod.GET)
	public ModelAndView alAltmntSrcReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<BudgetSource> budgetSourceList = (List<BudgetSource>) (Object) commonService
				.getAllObjectList("BudgetSource");
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}

		model.put("budgetSourceList", budgetSourceList);

		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("alAltmntSrcReportForm", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alAltmntSrcReportFormRest", method = RequestMethod.GET)
	public ModelAndView alAltmntSrcReportFormRest(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<BudgetSource> budgetSourceList = (List<BudgetSource>) (Object) commonService
				.getAllObjectList("BudgetSource");
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}

		model.put("budgetSourceList", budgetSourceList);

		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("alAltmntSrcReportFormRest", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rstAltmntReportForm", method = RequestMethod.GET)
	public ModelAndView rstAltmntReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("rstAltmntReportForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rstAltmntTLReportForm", method = RequestMethod.GET)
	public ModelAndView rstAltmntTLReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}

		model.put("fiscalYearList", fiscalYearList);
		model.put("teamLeaderList", teamLeaderList);

		return new ModelAndView("rstAltmntTLReportForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rstAltmntSrcReportForm", method = RequestMethod.GET)
	public ModelAndView rstAltmntSrcReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<BudgetSource> budgetSourceList = (List<BudgetSource>) (Object) commonService
				.getAllObjectList("BudgetSource");
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}

		model.put("budgetSourceList", budgetSourceList);

		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("rstAltmntSrcReportForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/altmntTLReportForm", method = RequestMethod.GET)
	public ModelAndView altmntTLReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}

		model.put("fiscalYearList", fiscalYearList);
		model.put("teamLeaderList", teamLeaderList);

		return new ModelAndView("altmntTLReportForm", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/altmntTLReportFormRest", method = RequestMethod.GET)
	public ModelAndView altmntTLReportFormRest(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}

		model.put("fiscalYearList", fiscalYearList);
		model.put("teamLeaderList", teamLeaderList);

		return new ModelAndView("altmntTLReportFormRest", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rstAltmntSrcSmryReportForm", method = RequestMethod.GET)
	public ModelAndView rstAltmntSrcSmryReportForm(@ModelAttribute("command") CommonBean commonBean,
			BindingResult result, Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<BudgetSource> budgetSourceList = (List<BudgetSource>) (Object) commonService
				.getAllObjectList("BudgetSource");
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}

		model.put("budgetSourceList", budgetSourceList);

		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("rstAltmntSrcSmryReportForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/altmntTLSmryReportForm", method = RequestMethod.GET)
	public ModelAndView altmntTLSmryReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}

		model.put("fiscalYearList", fiscalYearList);
		model.put("teamLeaderList", teamLeaderList);

		return new ModelAndView("altmntTLSmryReportForm", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pointVSGoodDeliversForm", method = RequestMethod.GET)
	public ModelAndView pointVSGoodDeliversForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		List<Point> pointList = (List<Point>) (Object) commonService.getAllObjectList("Point");
		for (Point point : pointList) {
			point.setName(convertFromUTF8(point.getName()));
		}

		model.put("fiscalYearList", fiscalYearList);
		model.put("pointList", pointList);

		return new ModelAndView("pointVSGoodDeliversForm", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/altmntColorReportForm", method = RequestMethod.GET)
	public ModelAndView altmntColorReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<ColorCode> colorCodeList = (List<ColorCode>) (Object) commonService.getAllObjectList("ColorCode");
		for (ColorCode colorCode : colorCodeList) {
			colorCode.setName(convertFromUTF8(colorCode.getName()));
		}
		model.put("colorCodeList", colorCodeList);

		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("altmntColorReportForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/altmntClrAndTLReportForm", method = RequestMethod.GET)
	public ModelAndView altmntClrAndTLReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}

		List<ColorCode> colorCodeList = (List<ColorCode>) (Object) commonService.getAllObjectList("ColorCode");
		for (ColorCode colorCode : colorCodeList) {
			colorCode.setName(convertFromUTF8(colorCode.getName()));
		}
		model.put("colorCodeList", colorCodeList);

		model.put("fiscalYearList", fiscalYearList);
		model.put("teamLeaderList", teamLeaderList);

		return new ModelAndView("altmntClrAndTLReportForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rstAltmntPointReportForm", method = RequestMethod.GET)
	public ModelAndView rstAltmntPointReportForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<Point> pointList = (List<Point>) (Object) commonService.getAllObjectList("Point");
		for (Point point : pointList) {
			point.setName(convertFromUTF8(point.getName()));
		}
		model.put("pointList", pointList);

		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("rstAltmntPointReportForm", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rstAltmntPointReportFormRest", method = RequestMethod.GET)
	public ModelAndView rstAltmntPointReportFormRest(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		List<Point> pointList = (List<Point>) (Object) commonService.getAllObjectList("Point");
		for (Point point : pointList) {
			point.setName(convertFromUTF8(point.getName()));
		}
		model.put("pointList", pointList);

		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("rstAltmntPointReportFormRest", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/altmntColorReports", method = RequestMethod.GET)
	public ModelAndView altmntColorReports(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("altmntColorReports", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alAltmntVSGoodDeliversForm", method = RequestMethod.GET)
	public ModelAndView alAltmntVSGoodDeliversForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}
		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));
		}
		model.put("fiscalYearList", fiscalYearList);
		model.put("teamLeaderList", teamLeaderList);

		return new ModelAndView("alAltmntVSGoodDeliversForm", model);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/generateReportsColorCodeWise", method = RequestMethod.POST)
	public ModelAndView generateReportsColorCodeWise(@ModelAttribute("command") CommonBean commonBean,
			BindingResult result, Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();

		List<TeamLeader> teamLeaderList = (List<TeamLeader>) (Object) commonService.getAllObjectList("TeamLeader");
		List<Budget> budgetList = (List<Budget>) (Object) commonService.getAllObjectList("Budget");
		List<ColorCodeBean> colorCodeBeanList = new ArrayList<ColorCodeBean>();

		Double totalGreen = 0.0;
		Double totalBlue = 0.0;
		Double totalRed = 0.0;
		List<TeamLeaderColorCodeStatistic> teamLeaderColorCodeStatisticList = new ArrayList<>();
		int i=1;
		for (TeamLeader teamLeader : teamLeaderList) {
			Double localGreen = 0.0;
			Double localBlue = 0.0;
			Double localRed = 0.0;
			for (Budget budget : budgetList) {
				if (teamLeader.getId().equals(budget.getPoint().getTeamLeader().getId())) {
					ColorCodeBean colorCodeBean = new ColorCodeBean(budget.getColorCode().getId(),
							budget.getPoint().getTeamLeader().getId(), budget.getColorCode().getName(),
							convertFromUTF8(budget.getPoint().getTeamLeader().getName()), budget.getBgtAmount());
					colorCodeBeanList.add(colorCodeBean);

					if(budget.getColorCode().getId()==1) {
						totalGreen+=budget.getBgtAmount();
					}
					if(budget.getColorCode().getId()==2) {
						totalRed+=budget.getBgtAmount();
					}
					if(budget.getColorCode().getId()==3) {
						totalBlue+=budget.getBgtAmount();
					}
				}
				

			}

			for (ColorCodeBean colorCodeBean : colorCodeBeanList) {
				

				if (teamLeader.getId() == colorCodeBean.getTeamLeaderId() && colorCodeBean.getColorCodeId() == 1) {
					localGreen += colorCodeBean.getAmount();
				}
				if (teamLeader.getId() == colorCodeBean.getTeamLeaderId() && colorCodeBean.getColorCodeId() == 2) {
					localRed += colorCodeBean.getAmount();
				}
				if (teamLeader.getId() == colorCodeBean.getTeamLeaderId() && colorCodeBean.getColorCodeId() == 3) {
					localBlue += colorCodeBean.getAmount();
				}
			}
			TeamLeaderColorCodeStatistic codeStatistic = new TeamLeaderColorCodeStatistic(i,teamLeader.getId(),
					convertFromUTF8(teamLeader.getName()), localGreen, localBlue, localRed);
			teamLeaderColorCodeStatisticList.add(codeStatistic);
			i++;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");  
	    Date date = new Date();  
	   
		Map<String, Object> modelMal = new HashMap<>();
		modelMal.put("codeStatisticList", teamLeaderColorCodeStatisticList);
		modelMal.put("totalGreen", totalGreen);
		modelMal.put("totalBlue", totalBlue);
		modelMal.put("totalRed", totalRed);
		modelMal.put("size", teamLeaderColorCodeStatisticList.size());
		modelMal.put("totalAmount", totalBlue+totalGreen+totalRed);
		
		modelMal.put("curentDate",CommonConverter.convertDateformate( formatter.format(date)));
		return new ModelAndView("altmntColorReportsForm", modelMal);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/stockReportsForm", method = RequestMethod.GET)
	public ModelAndView stockReportsForm(@ModelAttribute("command") CommonBean commonBean, BindingResult result,
			Principal principal) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FiscalYear> fiscalYearList = (List<FiscalYear>) (Object) commonService.getAllObjectList("FiscalYear");
		for (FiscalYear fiscalYear : fiscalYearList) {
			fiscalYear.setName(convertFromUTF8(fiscalYear.getName()));
		}

		model.put("fiscalYearList", fiscalYearList);

		return new ModelAndView("stockReportsForm", model);
	}
	

}
