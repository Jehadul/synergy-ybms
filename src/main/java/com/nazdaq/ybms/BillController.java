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

import com.nazdaq.ybms.model.Bill;
import com.nazdaq.ybms.model.Budget;
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
public class BillController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBillForm", method = RequestMethod.GET)
	public ModelAndView addBillForm (@ModelAttribute("command") Bill billBean, BindingResult result, Principal principal){		
		
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
		
		
		model.put("bill", billBean);
		model.put("fiscalYear", fiscalYear);
		model.put("pointList", pointList);
		model.put("budgetSourceList", budgetSourceList);
		model.put("budgetCodeList", budgetCodeList);
		model.put("goodsTypeList", goodsTypeList);
		
		return new ModelAndView("addBillForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveBill", method = RequestMethod.POST)
	public ModelAndView saveBill (@ModelAttribute("command") Bill billBean, BindingResult result, Principal principal, 
			RedirectAttributes attributes){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Point point = (Point)commonService.getAnObjectByAnyUniqueColumn("Point", "id", billBean.getPointId().toString());
		BudgetSource budgetSource = (BudgetSource)commonService.getAnObjectByAnyUniqueColumn("BudgetSource", "id", billBean.getBudgetSourceId().toString());
		BudgetCode budgetCode = (BudgetCode)commonService.getAnObjectByAnyUniqueColumn("BudgetCode", "id", billBean.getBudgetCodeId().toString());
		
		GoodsType goodsType = (GoodsType)commonService.getAnObjectByAnyUniqueColumn("GoodsType", "id", billBean.getGoodsTypeId().toString());
		
		FiscalYear fiscalYear =  (FiscalYear)commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "name", getCurrentFiscalYear());
		
		if(billBean.getId() != null) {
			Bill billDb = (Bill)commonService.getAnObjectByAnyUniqueColumn("Bill", "id", billBean.getId().toString());
			
			billDb.setPoint(point);
			billDb.setBudgetCode(budgetCode);
			billDb.setBudgetSource(budgetSource);
			
			billDb.setGoodsType(goodsType);
			
			billDb.setModifiedBy(principal.getName());
			billDb.setModifiedDate(new Date());
			billDb.setBillAmount(billBean.getBillAmount());
			billDb.setRemarks(billBean.getRemarks());
			commonService.saveOrUpdateModelObjectToDB(billDb);
			attributes.addFlashAttribute("successMsg", "Bill Updated Successfully!!");
			
		} else {
			Integer lastBillId = (Integer)commonService.getMaxValueByObjectAndColumn("Bill", "id");
			Bill bill = null;
			if(lastBillId != null) {
				bill = (Bill)commonService.getAnObjectByAnyUniqueColumn("Bill", "id", lastBillId.toString());
			}
			String billNo = this.getReferenceNo(bill);
			billBean.setCreatedBy(principal.getName());
			billBean.setCreatedDate(new Date());
			billBean.setBillId(billNo);	
			
			billBean.setGoodsType(goodsType);
			
			billBean.setPoint(point);
			billBean.setBudgetCode(budgetCode);
			billBean.setBudgetSource(budgetSource);
			billBean.setFiscalYear(fiscalYear);
			List<Bill> billList = (List<Bill>)(Object)
					commonService.getObjectListByFourColumnWithoutLike("Bill", "billDate", billBean.getBillDate(), 
							"point.id", billBean.getPointId().toString(), "budgetSource.id", billBean.getBudgetSourceId().toString(), 
							"budgetCode.id", billBean.getBudgetCodeId().toString());
			if(billList != null && billList.size() > 0) {
				attributes.addFlashAttribute("successMsg", "Bill Already Exist.. Please check the Bill List!!");
			}else {
				commonService.saveOrUpdateModelObjectToDB(billBean);
				attributes.addFlashAttribute("successMsg", "Bill Saved Successfully!!");
			}
			
		}
		
		if(billBean.getGoToList() != null) {
			return new ModelAndView("redirect:/billList");
		} else {
			return new ModelAndView("redirect:/addBillForm");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/billList", method = RequestMethod.GET)
	public ModelAndView billList (Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Map <String, Object> model = new HashMap<String, Object>();	
		List<Bill> billList = (List<Bill>)(Object) commonService.getObjectListByAnyColumn("Bill", "fiscalYear.name", getCurrentFiscalYear());
		
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
		
		for (Bill bill : billList) {
			for (Point point : pointList) {	
				if(bill.getPoint().getId().toString().equals(point.getId().toString())){
					bill.setPoint(point);
					break;
				}
			}
			
			for (BudgetCode budgetCode : budgetCodeList) {
				if(bill.getBudgetCode().getId().toString().equals(budgetCode.getId().toString())){
					bill.setBudgetCode(budgetCode);
					break;
				}
			}
			
			for (BudgetSource budgetSource : budgetSourceList) {
				if(bill.getBudgetSource().getId().toString().equals(budgetSource.getId().toString())){
					bill.setBudgetSource(budgetSource);
					break;
				}
			}
			
		}
		
		model.put("billList", billList);
		
		return new ModelAndView("billList", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editBill", method = RequestMethod.GET)
	public ModelAndView editBillForm (@ModelAttribute("command") Bill billBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Bill billDb = (Bill)commonService.getAnObjectByAnyUniqueColumn("Bill", "id", billBean.getId().toString());
		
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
		
		model.put("bill", billDb);
		model.put("edit", true);
		model.put("fiscalYear", fiscalYear);
		model.put("pointList", pointList);
		model.put("budgetSourceList", budgetSourceList);
		model.put("budgetCodeList", budgetCodeList);
		model.put("goodsTypeList", goodsTypeList);
		
		return new ModelAndView("addBillForm", model);
	}
	
	private String getReferenceNo(Bill bill){
		String refNo = "001";
		if(bill != null){
			Integer ref = 1;
			
			if(bill.getBillId() != null) {
				 ref = Integer.parseInt(bill.getBillId())+1;
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
