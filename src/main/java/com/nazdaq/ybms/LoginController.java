package com.nazdaq.ybms;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nazdaq.ybms.beans.CommonBean;
import com.nazdaq.ybms.beans.RepoFormOneBean;
import com.nazdaq.ybms.model.Bill;
import com.nazdaq.ybms.model.Budget;
import com.nazdaq.ybms.model.BudgetCode;
import com.nazdaq.ybms.model.BudgetSource;
import com.nazdaq.ybms.model.Delivered;
import com.nazdaq.ybms.model.Delivery;
import com.nazdaq.ybms.model.FiscalYear;
import com.nazdaq.ybms.model.Point;
import com.nazdaq.ybms.model.PointType;
import com.nazdaq.ybms.model.ReportColumnTitle;
import com.nazdaq.ybms.model.TeamLeader;
import com.nazdaq.ybms.model.User;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.service.UserService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.CommonConverter;
import com.nazdaq.ybms.util.UTF8Converter;



@Controller
public class LoginController extends SavedRequestAwareAuthenticationSuccessHandler implements Constants{
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonService commonService;
	
	
	@RequestMapping(value="/success", method = RequestMethod.GET)
	public String success(ModelMap model) {
	return "success"; 
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/","/index"}, method = RequestMethod.GET)
	public ModelAndView printWelcome1(ModelMap model, Principal principal, HttpSession session, HttpServletRequest request) throws ParseException {
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> auth = authentication.getAuthorities();
		String roleName = "";
		for (GrantedAuthority ga : auth) {
			roleName = ga.getAuthority();
			break;
		}*/
	
		
		String roleName = 	commonService.getAuthRoleName();
		
		String pageLocation=null;
		User user=null;
		String name = principal.getName();
		user=userService.getUser(name);			
		
		session.setAttribute("userr", name);
		session.setAttribute("uid", 1);
		session.setAttribute("userrId", session.getAttribute("userr"));
		session.setAttribute("fiscalYear", getCurrentFiscalYear());
		session.setAttribute("roleName", roleName);
		
		model.addAttribute("userName", session.getAttribute("userr"));
		model.addAttribute("userId", session.getAttribute("userrId"));
		model.addAttribute("fiscalYear", getCurrentFiscalYear());
		
		
		Map<String, Object> modelStr = new HashMap<String, Object>();
				
		if(request.isUserInRole(ROLE_ADMIN) || request.isUserInRole(ROLE_SUPER_ADMIN)){
			Double totalBudget = 0.0, totalDelivery = 0.0, totalBill = 0.0;
			//List<Budget> budgetList = (List<Budget>)(Object) commonService.getObjectListByAnyColumn("Budget", "fiscalYear.name", getCurrentFiscalYear());
			
			List<Budget> budgetList = commonService
					.getObjectListByAnyColumn("Budget", "fiscalYear.name", getCurrentFiscalYear())
					.stream()
				    .map(e -> (Budget) e)
				    .collect(Collectors.toList());
			
			if(budgetList != null) {
				for (Budget budget : budgetList) {
					totalBudget+=budget.getBgtAmount();
					totalBill+=budget.getBillAmount() != null?budget.getBillAmount():0.0;					
				}
			}
			
			/*List<Bill> billList = (List<Bill>)(Object) commonService.getObjectListByAnyColumn("Bill", "fiscalYear.name", getCurrentFiscalYear());
			
			if(billList != null) {
				for (Bill bill : billList) {
					totalBill+=bill.getBillAmount();
				}
			}*/
			
			//List<Delivered> deliveryList = (List<Delivered>)(Object) commonService.getObjectListByAnyColumn("Delivered", "fiscalYear.name", getCurrentFiscalYear());
			List<Delivered> deliveryList = commonService.
					getObjectListByAnyColumn("Delivered", "fiscalYear.name", getCurrentFiscalYear()).stream()
				    .map(e -> (Delivered) e)
				    .collect(Collectors.toList());
			
			if(deliveryList != null) {
				for (Delivered delivery : deliveryList) {
					totalDelivery+=delivery.getDlvAmount();
				}
			}
			
			List<RepoFormOneBean> allAllotment =  repoFormOneBeans ();
			
			modelStr.put("totalBudget", totalBudget);
			modelStr.put("totalDelivery", totalDelivery);
			modelStr.put("totalBill", totalBill);
			
			//modelStr.put("allAllotment", allAllotment);
			modelStr.put("budgetList", budgetList());
			pageLocation="index";	
		} else {
			pageLocation="index";					
		}
		
		//model.put("otMonth", otMonth);		
		return new ModelAndView(pageLocation, modelStr);
	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Principal principal) {
		if(principal != null) {
			return "redirect:/index";
		}
 		return "login";
	}
 
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(Model model) {
 
		model.addAttribute("error", "true");
		return "login";
 
	}
 
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) {
		session.invalidate();
 		return "login";
 	}
	
	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	protected String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	private Integer getMonthKey(String month) {
		Map<String, Integer> months = new HashMap<String, Integer>();
		months.put("January", 1);
		months.put("February", 2);
		months.put("March", 3);
		months.put("April", 4);
		months.put("May", 5);
		months.put("June", 6);
		months.put("July", 7);
		months.put("August", 8);
		months.put("September", 9);
		months.put("October", 10);
		months.put("November", 11);
		months.put("December", 12);
		
		return months.get(month);
	}
	
	private String getMonthName(Integer key) {
		Map<Integer, String> months = new HashMap<Integer, String>();
		months.put(1, "January");
		months.put(2, "February");
		months.put(3, "March");
		months.put(4, "April");
		months.put(5, "May");
		months.put(6, "June");
		months.put(7, "July");
		months.put(8, "August");
		months.put(9, "September");
		months.put(10, "October");
		months.put(11, "November");
		months.put(12, "December");		
		return months.get(key);
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
	
	@SuppressWarnings("unchecked")
	private List<RepoFormOneBean> repoFormOneBeans (){
		
		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscalYear.name", getCurrentFiscalYear());
		
		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();
		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitle=(ReportColumnTitle) commonService.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");
				
		Map<Integer, Integer> codeUniq = new HashMap<>();
		for (Budget budget : buList) {
			if(!codeUniq.containsKey(budget.getBudgetCode().getId())) {
				codeUniq.put(budget.getBudgetCode().getId(), budget.getBudgetCode().getId());
				CommonBean commonBean = new CommonBean();
				commonBean.setCodeId(budget.getBudgetCode().getId());
				commonBean.setPointId(budget.getPoint().getId());
				commonBeans.add(commonBean);
			}
			

		}
		Integer sl=1;
		for (CommonBean commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(),reportColumnTitle));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill+=budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(UTF8Converter.convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(UTF8Converter.convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(UTF8Converter.convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(UTF8Converter.convertFromUTF8(budget.getBudgetSource().getName()));
					//repoFormOneBean.setBudgetSource(budget.getBudgetSource().getName());
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(totalBill.toString());
			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(),reportColumnTitle));
			if (totalBill != null && totalBill > 0.0) {
				if(CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(),reportColumnTitle) != null) {
					repoFormOneBean.setBillAmount(CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(),reportColumnTitle));
				} else {
					repoFormOneBean.setBillAmount("0.0");
				}
			} else {
				repoFormOneBean.setBillAmount("0.0");
			}
				
			if (budgetAmount > 0.0) {
				Double pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean
							.setBillPendingAmount(CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(),reportColumnTitle));
				}

			}
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}
		
		return repoFormOneBeans;
	}
	
	public List<Budget> budgetList (){	
		
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
			budgetCode.setName(UTF8Converter.convertFromUTF8(budgetCode.getName()));
		}
		
		for (BudgetSource budgetSource : budgetSourceList) {
			budgetSource.setName(UTF8Converter.convertFromUTF8(budgetSource.getName()));
		}
		
		List<PointType> pointTypeList = (List<PointType>)(Object) commonService.getAllObjectList("PointType");
		List<TeamLeader> teamLeaderList = (List<TeamLeader>)(Object) commonService.getAllObjectList("TeamLeader");
		
		for (PointType pointType : pointTypeList) {
			pointType.setName(UTF8Converter.convertFromUTF8(pointType.getName()));
		}
		
		for (TeamLeader teamLeader : teamLeaderList) {
			teamLeader.setName(UTF8Converter.convertFromUTF8(teamLeader.getName()));
		}
		
		for (Point point : pointList) {			
			point.setName(UTF8Converter.convertFromUTF8(point.getName()));
			
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
		
			
		return budgetList;
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
