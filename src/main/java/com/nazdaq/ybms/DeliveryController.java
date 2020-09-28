package com.nazdaq.ybms;

import java.security.Principal;
import java.util.Calendar;
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

import com.nazdaq.ybms.model.Delivery;
import com.nazdaq.ybms.model.Bill;
import com.nazdaq.ybms.model.BudgetCode;
import com.nazdaq.ybms.model.BudgetSource;
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
public class DeliveryController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDeliveryForm", method = RequestMethod.GET)
	public ModelAndView addDeliveryForm (@ModelAttribute("command") Delivery deliveryBean, BindingResult result, Principal principal){		
		
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
		
		List<GoodsType> goodsTypeList = (List<GoodsType>)(Object) commonService.getAllObjectList("GoodsType");
				
		String fiscalYear = getCurrentFiscalYear();
		
		
		model.put("delivery", deliveryBean);
		model.put("fiscalYear", fiscalYear);
		model.put("pointList", pointList);
		model.put("budgetSourceList", budgetSourceList);
		model.put("budgetCodeList", budgetCodeList);
		model.put("goodsTypeList", goodsTypeList);
		
		return new ModelAndView("addDeliveryForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveDelivery", method = RequestMethod.POST)
	public ModelAndView saveDelivery (@ModelAttribute("command") Delivery deliveryBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		BudgetSource budgetSource = null;
		BudgetCode budgetCode = null;
		
		Point point = (Point)commonService.getAnObjectByAnyUniqueColumn("Point", "id", deliveryBean.getPointId().toString());
		
		if(deliveryBean.getBudgetSourceId() != null) {
			budgetSource = (BudgetSource)commonService.getAnObjectByAnyUniqueColumn("BudgetSource", "id", deliveryBean.getBudgetSourceId().toString());
		}
		
		if(deliveryBean.getBudgetCodeId() != null) {
			budgetCode = (BudgetCode)commonService.getAnObjectByAnyUniqueColumn("BudgetCode", "id", deliveryBean.getBudgetCodeId().toString());
		}
		
		GoodsType goodsType = (GoodsType)commonService.getAnObjectByAnyUniqueColumn("GoodsType", "id", deliveryBean.getGoodsTypeId().toString());
		
		FiscalYear fiscalYear =  (FiscalYear)commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "name", getCurrentFiscalYear());
		
		if(deliveryBean.getId() != null) {
			Delivery deliveryDb = (Delivery)commonService.getAnObjectByAnyUniqueColumn("Delivery", "id", deliveryBean.getId().toString());
			
			deliveryDb.setPoint(point);
			deliveryDb.setGoodsType(goodsType);
			
			if(deliveryBean.getAgainstBudget().equals("1")) {
				deliveryDb.setBudgetCode(budgetCode);
				deliveryDb.setBudgetSource(budgetSource);
			}
			
			if(deliveryBean.getAgainstBudget().equals("0")) {
				if(budgetCode != null && budgetSource != null) {
					deliveryDb.setBudgetCode(budgetCode);
					deliveryDb.setBudgetSource(budgetSource);
					deliveryDb.setAdjustBudget("1");
					deliveryDb.setFiscalYearBgt(fiscalYear);
				} else {
					deliveryDb.setBudgetCode(budgetCode);
					deliveryDb.setBudgetSource(budgetSource);
					deliveryDb.setAdjustBudget("0");
					deliveryDb.setFiscalYearBgt(null);
				}
				
			}
			
			deliveryDb.setModifiedBy(principal.getName());
			deliveryDb.setModifiedDate(new Date());
			deliveryDb.setDlvAmount(deliveryBean.getDlvAmount());
			deliveryDb.setRemarks(deliveryBean.getRemarks());
			commonService.saveOrUpdateModelObjectToDB(deliveryDb);
			attributes.addFlashAttribute("successMsg", "Delivery Updated Successfully!!");
			
		} else {
			Integer lastDeliveryId = (Integer)commonService.getMaxValueByObjectAndColumn("Delivery", "id");
			Delivery delivery = null;
			if(lastDeliveryId != null) {
				delivery = (Delivery)commonService.getAnObjectByAnyUniqueColumn("Delivery", "id", lastDeliveryId.toString());
			}
			String dlvNo = this.getReferenceNo(delivery);
			deliveryBean.setCreatedBy(principal.getName());
			deliveryBean.setCreatedDate(new Date());
			deliveryBean.setDeliveryId(dlvNo);	
			
			deliveryBean.setPoint(point);
			deliveryBean.setGoodsType(goodsType);
			deliveryBean.setFiscalYearDlv(fiscalYear);			
			deliveryBean.setBudgetCode(budgetCode);
			deliveryBean.setBudgetSource(budgetSource);
			if(deliveryBean.getAgainstBudget().equals("1")) {
				deliveryBean.setAdjustBudget(null);
				deliveryBean.setFiscalYearBgt(fiscalYear);
			} else {
				deliveryBean.setAdjustBudget("0");
			}
			
			if(deliveryBean.getAgainstBudget().equals("1") && budgetCode == null && budgetSource == null) {
				attributes.addFlashAttribute("successMsg", "Please Try Again!!");
			} else {
				
				List<Delivery> deliveryList = (List<Delivery>)(Object)commonService.getObjectListByTwoColumn("Delivery", "dlvDate", deliveryBean.getDlvDate(), 
						"point.id", deliveryBean.getPointId().toString());
				if(deliveryList != null && deliveryList.size() > 0) {
					attributes.addFlashAttribute("successMsg", "Delivery Already Exist... Please check the All Delivery List!!");
				} else {
					commonService.saveOrUpdateModelObjectToDB(deliveryBean);
					attributes.addFlashAttribute("successMsg", "Delivery Saved Successfully!!");
				}
				
			}
		}
		
		if(deliveryBean.getGoToList() != null) {
			return new ModelAndView("redirect:/allDeliveryList");
		} else {
			return new ModelAndView("redirect:/addDeliveryForm");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/allDeliveryList", method = RequestMethod.GET)
	public ModelAndView allDeliveryList (Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();	
		List<Delivery> deliveryList = (List<Delivery>)(Object) commonService.getObjectListByTwoColumnInAndClauseAndOneColumnInOrClause("Delivery", "adjustBudget", "0", "againstBudget", "0", "fiscalYearDlv.name", getCurrentFiscalYear());
		
		List<Point> pointList = (List<Point>)(Object) commonService.getAllObjectList("Point");
		List<BudgetCode> budgetCodeList = (List<BudgetCode>)(Object) commonService.getAllObjectList("BudgetCode");
		
		List<BudgetSource> budgetSourceList = (List<BudgetSource>)(Object) commonService.getAllObjectList("BudgetSource");
		
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}
		
		for (BudgetCode budgetCode : budgetCodeList) {
			budgetCode.setName(convertFromUTF8(budgetCode.getName()));
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
		
		for (Delivery delivery : deliveryList) {
			for (Point point : pointList) {	
				if(delivery.getPoint().getId().toString().equals(point.getId().toString())){
					delivery.setPoint(point);
					break;
				}
			}
			
			for (BudgetCode budgetCode : budgetCodeList) {
				if(delivery.getBudgetCode().getId().toString().equals(budgetCode.getId().toString())){
					delivery.setBudgetCode(budgetCode);
					break;
				}
			}
			
			for (BudgetSource budgetSource : budgetSourceList) {
				if(delivery.getBudgetSource().getId().toString().equals(budgetSource.getId().toString())){
					delivery.setBudgetSource(budgetSource);
					break;
				}
			}
			
		}
		
		model.put("deliveryList", deliveryList);
		
		return new ModelAndView("allDeliveryList", model);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/adjustDeliveryList", method = RequestMethod.GET)
	public ModelAndView adjustDeliveryList (Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();	
		List<Delivery> deliveryList = (List<Delivery>)(Object) commonService.getObjectListByTwoColumn("Delivery", "adjustBudget", "1", "fiscalYearBgt.name", getCurrentFiscalYear());	
		
		List<Point> pointList = (List<Point>)(Object) commonService.getAllObjectList("Point");
		List<BudgetCode> budgetCodeList = (List<BudgetCode>)(Object) commonService.getAllObjectList("BudgetCode");
		
		List<BudgetSource> budgetSourceList = (List<BudgetSource>)(Object) commonService.getAllObjectList("BudgetSource");
		
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}
		
		for (BudgetCode budgetCode : budgetCodeList) {
			budgetCode.setName(convertFromUTF8(budgetCode.getName()));
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
		
		for (Delivery delivery : deliveryList) {
			for (Point point : pointList) {	
				if(delivery.getPoint().getId().toString().equals(point.getId().toString())){
					delivery.setPoint(point);
					break;
				}
			}
			
			for (BudgetCode budgetCode : budgetCodeList) {
				if(delivery.getBudgetCode().getId().toString().equals(budgetCode.getId().toString())){
					delivery.setBudgetCode(budgetCode);
					break;
				}
			}
			
			for (BudgetSource budgetSource : budgetSourceList) {
				if(delivery.getBudgetSource().getId().toString().equals(budgetSource.getId().toString())){
					delivery.setBudgetSource(budgetSource);
					break;
				}
			}
			
		}
		
		model.put("deliveryList", deliveryList);
		
		return new ModelAndView("adjustDeliveryList", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/abDeliveryList", method = RequestMethod.GET)
	public ModelAndView abDeliveryList (Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();	
		List<Delivery> deliveryList = (List<Delivery>)(Object) commonService.getObjectListByTwoColumn("Delivery", "againstBudget", "1", "fiscalYearDlv.name", getCurrentFiscalYear());	
		
		List<Point> pointList = (List<Point>)(Object) commonService.getAllObjectList("Point");
		List<BudgetCode> budgetCodeList = (List<BudgetCode>)(Object) commonService.getAllObjectList("BudgetCode");
		
		List<BudgetSource> budgetSourceList = (List<BudgetSource>)(Object) commonService.getAllObjectList("BudgetSource");
		
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}
		
		for (BudgetCode budgetCode : budgetCodeList) {
			budgetCode.setName(convertFromUTF8(budgetCode.getName()));
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
		
		for (Delivery delivery : deliveryList) {
			for (Point point : pointList) {	
				if(delivery.getPoint().getId().toString().equals(point.getId().toString())){
					delivery.setPoint(point);
					break;
				}
			}
			
			for (BudgetCode budgetCode : budgetCodeList) {
				if(delivery.getBudgetCode().getId().toString().equals(budgetCode.getId().toString())){
					delivery.setBudgetCode(budgetCode);
					break;
				}
			}
			
			for (BudgetSource budgetSource : budgetSourceList) {
				if(delivery.getBudgetSource().getId().toString().equals(budgetSource.getId().toString())){
					delivery.setBudgetSource(budgetSource);
					break;
				}
			}
			
		}
		
		model.put("deliveryList", deliveryList);
		
		return new ModelAndView("abDeliveryList", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/wbDeliveryList", method = RequestMethod.GET)
	public ModelAndView wbDeliveryList (Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();	
		List<Delivery> deliveryList = (List<Delivery>)(Object) commonService.getObjectListByTwoColumn("Delivery", "againstBudget", "0", "fiscalYearDlv.name", getCurrentFiscalYear());	
		
		List<Point> pointList = (List<Point>)(Object) commonService.getAllObjectList("Point");
		List<BudgetCode> budgetCodeList = (List<BudgetCode>)(Object) commonService.getAllObjectList("BudgetCode");
		
		List<BudgetSource> budgetSourceList = (List<BudgetSource>)(Object) commonService.getAllObjectList("BudgetSource");
		
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(convertFromUTF8(budgetSource.getName()));
		}
		
		for (BudgetCode budgetCode : budgetCodeList) {
			budgetCode.setName(convertFromUTF8(budgetCode.getName()));
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
		
		for (Delivery delivery : deliveryList) {
			for (Point point : pointList) {	
				if(delivery.getPoint().getId().toString().equals(point.getId().toString())){
					delivery.setPoint(point);
					break;
				}
			}
			
			for (BudgetCode budgetCode : budgetCodeList) {
				if(delivery.getBudgetCode().getId().toString().equals(budgetCode.getId().toString())){
					delivery.setBudgetCode(budgetCode);
					break;
				}
			}
			
			for (BudgetSource budgetSource : budgetSourceList) {
				if(delivery.getBudgetSource().getId().toString().equals(budgetSource.getId().toString())){
					delivery.setBudgetSource(budgetSource);
					break;
				}
			}
			
		}
		
		model.put("deliveryList", deliveryList);
		
		return new ModelAndView("wbDeliveryList", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editDelivery", method = RequestMethod.GET)
	public ModelAndView editBillForm (@ModelAttribute("command") Delivery deliveryBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Delivery deliveryDb = (Delivery)commonService.getAnObjectByAnyUniqueColumn("Delivery", "id", deliveryBean.getId().toString());
		
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
		
		List<GoodsType> goodsTypeList = (List<GoodsType>)(Object) commonService.getAllObjectList("GoodsType");
		
		String fiscalYear = getCurrentFiscalYear();		
		
		model.put("delivery", deliveryDb);
		model.put("edit", true);
		model.put("fiscalYear", fiscalYear);
		model.put("pointList", pointList);
		model.put("budgetSourceList", budgetSourceList);
		model.put("budgetCodeList", budgetCodeList);
		model.put("goodsTypeList", goodsTypeList);
		
		return new ModelAndView("addDeliveryForm", model);
	}
	
	private String getReferenceNo(Delivery delivery){
		String refNo = "001";
		if(delivery != null){
			Integer ref = 1;
			
			if(delivery.getDeliveryId() != null) {
				 ref = Integer.parseInt(delivery.getDeliveryId())+1;
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
}
