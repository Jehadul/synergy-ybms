package com.nazdaq.ybms;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import com.nazdaq.ybms.model.BudgetCode;
import com.nazdaq.ybms.model.BudgetHistory;
import com.nazdaq.ybms.model.BudgetSource;
import com.nazdaq.ybms.model.ColorCode;
import com.nazdaq.ybms.model.FiscalYear;
import com.nazdaq.ybms.model.GoodsType;
import com.nazdaq.ybms.model.Point;
import com.nazdaq.ybms.model.PointType;
import com.nazdaq.ybms.model.TeamLeader;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class BudgetController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBudgetForm", method = RequestMethod.GET)
	public ModelAndView addBudgetForm (@ModelAttribute("command") Budget budgetBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<Point> pointList = (List<Point>)(Object) commonService.getAllObjectList("Point");
		for (Point point : pointList) {			
			point.setName(convertFromUTF8(point.getName()));
			
			PointType pointType = point.getPointType();
			pointType.setName(convertFromUTF8(pointType.getName()));
			TeamLeader teamLeader = point.getTeamLeader();
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));			
			point.setPointType(pointType);
			point.setTeamLeader(teamLeader);
		}
		
		List<BudgetSource> budgetSourceList = (List<BudgetSource>)(Object) commonService.getAllObjectList("BudgetSource");
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}
		
		List<BudgetCode> budgetCodeList = (List<BudgetCode>)(Object) commonService.getAllObjectList("BudgetCode");
		for (BudgetCode budgetCode : budgetCodeList) {
			budgetCode.setName(convertFromUTF8(budgetCode.getName()));
		}
		
		List<ColorCode> colorCodeList = (List<ColorCode>)(Object) commonService.getAllObjectList("ColorCode");
		for (ColorCode colorCode : colorCodeList) {
			colorCode.setName(convertFromUTF8(colorCode.getName()));
		}
		Collections.sort(colorCodeList);
		
		List<GoodsType> goodsTypeList = (List<GoodsType>)(Object) commonService.getAllObjectList("GoodsType");
		
		String fiscalYear = getCurrentFiscalYear();
		
		
		model.put("budget", budgetBean);
		model.put("fiscalYear", fiscalYear);
		model.put("pointList", pointList);
		model.put("budgetSourceList", budgetSourceList);
		model.put("budgetCodeList", budgetCodeList);
		model.put("colorCodeList", colorCodeList);
		model.put("goodsTypeList", goodsTypeList);
		
		return new ModelAndView("addBudgetForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveBudget", method = RequestMethod.POST)
	public ModelAndView saveBudget (@ModelAttribute("command") Budget budgetBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Point point = (Point)commonService.getAnObjectByAnyUniqueColumn("Point", "id", budgetBean.getPointId().toString());
		BudgetSource budgetSource = (BudgetSource)commonService.getAnObjectByAnyUniqueColumn("BudgetSource", "id", budgetBean.getBudgetSourceId().toString());
		BudgetCode budgetCode = (BudgetCode)commonService.getAnObjectByAnyUniqueColumn("BudgetCode", "id", budgetBean.getBudgetCodeId().toString());
		ColorCode colorCode = (ColorCode)commonService.getAnObjectByAnyUniqueColumn("ColorCode", "id", budgetBean.getColorCodeId().toString());
		
		GoodsType goodsType = (GoodsType)commonService.getAnObjectByAnyUniqueColumn("GoodsType", "id", budgetBean.getGoodsTypeId().toString());
		
		FiscalYear fiscalYear =  (FiscalYear)commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "name", getCurrentFiscalYear());
		
		if(budgetBean.getId() != null) {
			Budget budgetDb = (Budget)commonService.getAnObjectByAnyUniqueColumn("Budget", "id", budgetBean.getId().toString());
			
			budgetDb.setPoint(point);
			budgetDb.setBudgetCode(budgetCode);
			budgetDb.setBudgetSource(budgetSource);
			budgetDb.setColorCode(colorCode);
			budgetDb.setGoodsType(goodsType);
			
			budgetDb.setModifiedBy(principal.getName());
			budgetDb.setModifiedDate(new Date());
			budgetDb.setBgtAmount(budgetBean.getBgtAmount());
			budgetDb.setBillAmount(budgetBean.getBillAmount());
			budgetDb.setRemarks(budgetBean.getRemarks());
			if(budgetDb.getBillAmount() > budgetDb.getBgtAmount()) {
				attributes.addFlashAttribute("successMsg", "Bill Amount cannot greater than Budget Amount.. Please Try Again!!");
			} else {
				commonService.saveOrUpdateModelObjectToDB(budgetDb);
				attributes.addFlashAttribute("successMsg", "Budget Updated Successfully!!");
				//history
				BudgetHistory budgetHistory = new BudgetHistory(null, budgetDb.getBudgetId(), budgetDb.getPoint(), budgetDb.getFiscalYear(), 
						budgetSource.getName(), budgetCode.getName(), colorCode, goodsType.getName(), point.getPointType().getName(), point.getTeamLeader().getName(), 
						budgetDb.getBgtAmount(), budgetDb.getBillAmount(), principal.getName(), new Date(), budgetDb.getRemarks());
				/*BudgetHistory budgetHistory = new BudgetHistory(null, budgetDb.getBudgetId(), budgetDb.getPoint(), budgetDb.getFiscalYear(), 
						budgetDb.getBudgetSource(), budgetDb.getBudgetCode(), budgetDb.getColorCode(), budgetDb.getGoodsType(), budgetDb.getBgtAmount(), 
						budgetDb.getBillAmount(), principal.getName(), new Date(), budgetDb.getRemarks());*/
				commonService.saveOrUpdateModelObjectToDB(budgetHistory);
			}
			
			
		} else {
			Integer lastBudgetId = (Integer)commonService.getMaxValueByObjectAndColumn("Budget", "id");
			Budget budget = null;
			if(lastBudgetId != null) {
				budget = (Budget)commonService.getAnObjectByAnyUniqueColumn("Budget", "id", lastBudgetId.toString());
			}
			String budgetNo = this.getReferenceNo(budget);
			budgetBean.setCreatedBy(principal.getName());
			budgetBean.setCreatedDate(new Date());
			budgetBean.setBudgetId(budgetNo);	
			
			budgetBean.setPoint(point);
			budgetBean.setBudgetCode(budgetCode);
			budgetBean.setBudgetSource(budgetSource);
			budgetBean.setColorCode(colorCode);
			budgetBean.setFiscalYear(fiscalYear);
			budgetBean.setGoodsType(goodsType);
			
			List<Budget> BudgetList = (List<Budget>)(Object)commonService.getObjectListByFourColumnWithoutLike("Budget", "smarokNo", budgetBean.getSmarokNo(), 
					"point.id", budgetBean.getPointId().toString(), "fiscalYear.id", fiscalYear.getId().toString(), 
					"budgetCode.id", budgetBean.getBudgetCodeId().toString());
			if(BudgetList != null && BudgetList.size() > 0) {
				attributes.addFlashAttribute("successMsg", "Budget Allready Exist... Please check the Budget List!!");
			} else {
				if(budgetBean.getBillAmount() > budgetBean.getBgtAmount()) {
					attributes.addFlashAttribute("successMsg", "Bill Amount cannot greater than Budget Amount.. Please Try Again!!");
				} else {
					commonService.saveOrUpdateModelObjectToDB(budgetBean);
					attributes.addFlashAttribute("successMsg", "Budget Saved Successfully!!");
					//history
					BudgetHistory budgetHistory = new BudgetHistory(null, budgetBean.getBudgetId(), budgetBean.getPoint(), budgetBean.getFiscalYear(), 
							budgetSource.getName(), budgetCode.getName(), colorCode, goodsType.getName(), point.getPointType().getName(), point.getTeamLeader().getName(), 
							budgetBean.getBgtAmount(), budgetBean.getBillAmount(), principal.getName(), new Date(), budgetBean.getRemarks());
					/*BudgetHistory budgetHistory = new BudgetHistory(null, budgetBean.getBudgetId(), budgetBean.getPoint(), budgetBean.getFiscalYear(), 
							budgetBean.getBudgetSource(), budgetBean.getBudgetCode(), budgetBean.getColorCode(), budgetBean.getGoodsType(), 
							budgetBean.getBgtAmount(), budgetBean.getBillAmount(), principal.getName(), new Date(), budgetBean.getRemarks());*/
					commonService.saveOrUpdateModelObjectToDB(budgetHistory);
				}
				
			}
			
		}
		
		if(budgetBean.getGoToList() != null) {
			return new ModelAndView("redirect:/budgetList");
		} else {
			return new ModelAndView("redirect:/addBudgetForm");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budgetList", method = RequestMethod.GET)
	public ModelAndView budgetList (Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();	
		List<Budget> budgetList = new ArrayList<Budget>();
		//currenct session
		List<Budget> budgetListCY = (List<Budget>)(Object) commonService.getObjectListByAnyColumn("Budget", "fiscalYear.name", getCurrentFiscalYear());
		//due budget
		//FiscalYear fiscalYear = (FiscalYear)commonService.getAnObjectByAnyUniqueColumn("FiscalYear", " name", getCurrentFiscalYear());
		//String queryString = "FROM Budget b WHERE fiscalYear.id != '" + fiscalYear.getId().toString() + "' and b.bgtAmount != b.billAmount";
		String queryString = "FROM Budget b WHERE fiscalYear.name = '" + getPreviousFiscalYear() + "' and b.bgtAmount != b.billAmount";
		List<Budget> budgetListDue = (List<Budget>)(Object) commonService.getListBySqlQuery(queryString);
				
		budgetList.addAll(budgetListDue);
		budgetList.addAll(budgetListCY);
		
		List<Point> pointList = (List<Point>)(Object) commonService.getAllObjectList("Point");
		List<BudgetCode> budgetCodeList = (List<BudgetCode>)(Object) commonService.getAllObjectList("BudgetCode");
		
		List<BudgetSource> budgetSourceList = (List<BudgetSource>)(Object) commonService.getAllObjectList("BudgetSource");
		
		for (BudgetCode budgetCode : budgetCodeList) {
			budgetCode.setName(convertFromUTF8(budgetCode.getName()));
		}
		
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}
		
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
		
		for (Budget budget : budgetList) {
			for (Point point : pointList) {	
				if(budget.getPoint().getId().toString().equals(point.getId().toString())){
					budget.setPoint(point);
					break;
				}
			}
			
			for (BudgetCode budgetCode : budgetCodeList) {
				if(budget.getBudgetCode().getId().toString().equals(budgetCode.getId().toString())){
					budget.setBudgetCode(budgetCode);
					break;
				}
			}
			
			for (BudgetSource budgetSource : budgetSourceList) {
				if(budget.getBudgetSource().getId().toString().equals(budgetSource.getId().toString())){
					budget.setBudgetSource(budgetSource);
					break;
				}
			}
			
		}
		
		model.put("budgetList", budgetList);
		//model.put("fiscalYear", getCurrentFiscalYear());
		
		return new ModelAndView("budgetList", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editBudget", method = RequestMethod.GET)
	public ModelAndView editBudgetForm (@ModelAttribute("command") Budget budgetBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Budget budgetDb = (Budget)commonService.getAnObjectByAnyUniqueColumn("Budget", "id", budgetBean.getId().toString());
		
		Map <String, Object> model = new HashMap<String, Object>();		
		List<Point> pointList = (List<Point>)(Object) commonService.getAllObjectList("Point");
		for (Point point : pointList) {			
			point.setName(convertFromUTF8(point.getName()));
			
			PointType pointType = point.getPointType();
			pointType.setName(convertFromUTF8(pointType.getName()));
			TeamLeader teamLeader = point.getTeamLeader();
			teamLeader.setName(convertFromUTF8(teamLeader.getName()));			
			point.setPointType(pointType);
			point.setTeamLeader(teamLeader);
		}
		
		List<BudgetSource> budgetSourceList = (List<BudgetSource>)(Object) commonService.getAllObjectList("BudgetSource");
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}
		
		List<BudgetCode> budgetCodeList = (List<BudgetCode>)(Object) commonService.getAllObjectList("BudgetCode");
		for (BudgetCode budgetCode : budgetCodeList) {
			budgetCode.setName(convertFromUTF8(budgetCode.getName()));
		}
		
		List<ColorCode> colorCodeList = (List<ColorCode>)(Object) commonService.getAllObjectList("ColorCode");
		for (ColorCode colorCode : colorCodeList) {
			colorCode.setName(convertFromUTF8(colorCode.getName()));
		}
		Collections.sort(colorCodeList);
		
		List<GoodsType> goodsTypeList = (List<GoodsType>)(Object) commonService.getAllObjectList("GoodsType");
		
		String fiscalYear = getCurrentFiscalYear();
		
		
		model.put("budget", budgetDb);
		model.put("edit", true);
		model.put("fiscalYear", fiscalYear);
		model.put("pointList", pointList);
		model.put("budgetSourceList", budgetSourceList);
		model.put("budgetCodeList", budgetCodeList);
		model.put("colorCodeList", colorCodeList);
		
		model.put("goodsTypeList", goodsTypeList);
		
		return new ModelAndView("addBudgetForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBudget", method = RequestMethod.GET)
	public ModelAndView deleteBudget (@ModelAttribute("command") Budget budgetBean, BindingResult result, Principal principal, RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		if(budgetBean.getId() != null) {		
			Budget budget = (Budget) commonService.getAnObjectByAnyUniqueColumn("Budget", "id", budgetBean.getId().toString());
			commonService.deleteAnObjectListByAnyColumn("BudgetHistory", "budgetId", budget.getBudgetId());
			commonService.deleteAnObjectById("Budget", budgetBean.getId());
			attributes.addFlashAttribute("successMsg", "Budget Deleted Successfully!!");
		} else {
			attributes.addFlashAttribute("successMsg", "Please Try Again!!");
		}
		
		return new ModelAndView("redirect:/budgetList");
	}
	
	private String getReferenceNo(Budget budget){
		String refNo = "001";
		if(budget != null){
			Integer ref = 1;
			
			if(budget.getBudgetId() != null) {
				 ref = Integer.parseInt(budget.getBudgetId())+1;
			} 
			
			if(ref.toString().length() == 1) {
				refNo = "00"+ref;
			} else if (ref.toString().length() == 2) {
				refNo = "0"+ref;
			} else {
				refNo = ref.toString();
			}
		}
		return refNo;
	}
	
	private String getFormatedMobileNo(String mobile){
		String mobileNo = "";
		String [] mobileNos = mobile.split(",");
		mobileNo = mobileNos[0];
		
		if(mobileNo.length() == 10){
			return "+880" + mobileNo;
		} else if(mobileNo.length() == 11){
			return "+88" + mobileNo;
		}else if(mobileNo.length() == 13){
			return "+" + mobileNo;
		} else if(mobileNo.length() == 14){
			return mobileNo;
		} else {
			return mobileNo = "";
		}
	}
	
	private static String getCurrentFiscalYear() {
		Date d = new Date();
		String res;
		Integer month = (d.getMonth()+1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		
		Integer year = cal.get(Calendar.YEAR);
		if(month > 6) {
			res = year+"-"+(year+1);
		} else {
			res = (year-1)+"-"+(year);
		}
		return res;
	}
	
	private static String getPreviousFiscalYear() {
		Date d = new Date();
		String res;
		Integer month = (d.getMonth()+1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		
		Integer year = cal.get(Calendar.YEAR);
		if(month > 6) {
			res = (year-1)+"-"+(year);
		} else {
			res = (year-2)+"-"+(year-1);
		}
		return res;
	}
}
