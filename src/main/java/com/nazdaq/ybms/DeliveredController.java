package com.nazdaq.ybms;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.nazdaq.ybms.beans.DeliveredBean;
import com.nazdaq.ybms.model.Budget;
import com.nazdaq.ybms.model.Delivered;
import com.nazdaq.ybms.model.DeliveredHistory;
import com.nazdaq.ybms.model.FiscalYear;
import com.nazdaq.ybms.model.Point;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.UTF8Converter;

@Controller
@PropertySource("classpath:common.properties")
public class DeliveredController extends UTF8Converter implements Constants{
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${cc.email.addresss}")
	String ccEmailAddresss;
	
	@Value("${common.email.address}")
	String commonEmailAddress;
	
	//addDeliveredForm
	@RequestMapping(value = "/addDeliveredForm", method = RequestMethod.GET)
	public ModelAndView addDeliveredForm (@ModelAttribute("command") Delivered deliveredBean, BindingResult result, Principal principal){		
		
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		return new ModelAndView("addDeliveredForm");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/getDeliveredList" }, method = RequestMethod.POST)
	private @ResponseBody String getDeliveredList(@RequestBody String jesonString, Principal principal)
			throws JsonGenerationException, JsonMappingException, Exception {
		String toJson = "";
		Gson gson = new Gson();
		Delivered deliveredBean = gson.fromJson(jesonString, Delivered.class);
		List<Delivered> deliveredListLeatest = new ArrayList<Delivered>();
		
		List<Delivered> finalDeliveredList = new ArrayList<Delivered>();
		List<Point> pointListNew = new ArrayList<Point>();
		List<Point> pointList = (List<Point>)(Object)commonService.getAllObjectList("Point");
		
		FiscalYear fiscalYear = (FiscalYear)commonService.getAnObjectByAnyUniqueColumn("FiscalYear", " name", getCurrentFiscalYear());
		
		List<Delivered> deliveredList = (List<Delivered>)(Object)
				commonService.getObjectListByAnyColumn("Delivered", "fiscalYear.name", getCurrentFiscalYear());
		
		if(deliveredList != null && deliveredList.size() > 0) {			
			if(deliveredList.size() < pointList.size()) {
				boolean flag = false;
				for (Point point : pointList) {
					for (Delivered delivered : deliveredList) {
						if(point.getId().toString().equals(delivered.getPoint().getId().toString())) {
							flag = true;
							break;
						} else {
							flag = false;
						}
					}
					
					if(!flag) {
						pointListNew.add(point);
					}
				}
				
				Delivered delivered = null, deliveredDb = null;
				DeliveredHistory deliveredHistory = null;
				for (Point point : pointListNew) {
					Integer lastDeliveredId = (Integer)commonService.getMaxValueByObjectAndColumn("Delivered", "id");
					if(lastDeliveredId != null) {
						deliveredDb = (Delivered)commonService.getAnObjectByAnyUniqueColumn("Delivered", "id", lastDeliveredId.toString());
					}
					String deliveryId = getReferenceNo(deliveredDb);
					delivered = new Delivered();
					delivered.setPoint(point);
					delivered.setFiscalYear(fiscalYear);
					delivered.setCreatedBy(principal.getName());
					delivered.setCreatedDate(new Date());
					
					delivered.setDlvEquipments(0.0);
					delivered.setDlvFurniture(0.0);
					delivered.setDlvComputer(0.0);
					delivered.setDlvReagent(0.0);
					delivered.setDlvRepair(0.0);
					delivered.setDlvBooks(0.0);
					
					delivered.setDlvAmount(0.0);
					delivered.setId(null);
					delivered.setDeliveryId(deliveryId);
					commonService.saveOrUpdateModelObjectToDB(delivered);
					//history
					deliveredHistory = new DeliveredHistory(null, delivered.getDeliveryId(), delivered.getPoint().getTeamLeader().getName(), delivered.getPoint().getPointType().getName(), delivered.getPoint(), delivered.getFiscalYear(), delivered.getDlvAmount(), 
							delivered.getDlvEquipments(), delivered.getDlvFurniture(), delivered.getDlvComputer(), delivered.getDlvRepair(), delivered.getDlvReagent(),
							delivered.getDlvBooks(), principal.getName(), new Date(), delivered.getRemarks());
					commonService.saveOrUpdateModelObjectToDB(deliveredHistory);
				}
				
				deliveredListLeatest = (List<Delivered>)(Object)
						commonService.getObjectListByAnyColumn("Delivered", "fiscalYear.name", getCurrentFiscalYear());
			} else {
				deliveredListLeatest.addAll(deliveredList);
			}
		} else {
			
			// old_dlv for carry forword start
			List<Budget> budgetListOld = (List<Budget>)(Object) 
					commonService.getObjectListByAnyColumn("Budget", "fiscalYear.name", getPreviousFiscalYear());
			
			List<Delivered> deliveredListOldAll = (List<Delivered>)(Object) 
					commonService.getObjectListByAnyColumn("Delivered", "fiscalYear.name", getPreviousFiscalYear());
			List<Delivered> deliveredListOld = new ArrayList<Delivered>();
			
			for (Delivered delivered : deliveredListOldAll) {
				Double bgtAmount = 0.0, bgtEquipments = 0.0, bgtFurniture = 0.0, bgtComputer = 0.0, bgtRepair = 0.0, bgtReagent = 0.0, bgtBooks = 0.0;
				for (Budget budget : budgetListOld) {
					if(delivered.getPoint().getId().toString().equals(budget.getPoint().getId().toString())) {
						bgtAmount+=budget.getBgtAmount();
						
						if(budget.getGoodsType().getName().toUpperCase().equals(EQUIPMENTS)) {
							bgtEquipments+=budget.getBgtAmount();
						}
						if(budget.getGoodsType().getName().toUpperCase().equals(REAGENT)) {
							bgtReagent+=budget.getBgtAmount();						
						}
						if(budget.getGoodsType().getName().toUpperCase().equals(FURNITURE)) {
							bgtFurniture+=budget.getBgtAmount();
						}
						if(budget.getGoodsType().getName().toUpperCase().equals(COMPUTER)) {
							bgtComputer+=budget.getBgtAmount();
						}
						if(budget.getGoodsType().getName().toUpperCase().equals(REPAIR)) {
							bgtRepair+=budget.getBgtAmount();
						}
						
						if(budget.getGoodsType().getName().toUpperCase().equals(BOOKS)) {
							bgtBooks+=budget.getBgtAmount();
						}
					}
				}
				
				delivered.setBgtAmount(bgtAmount);
				
				delivered.setBgtEquipments(bgtEquipments);
				delivered.setBgtFurniture(bgtFurniture);
				delivered.setBgtComputer(bgtComputer);
				delivered.setBgtReagent(bgtReagent);
				delivered.setBgtRepair(bgtRepair);
				delivered.setBgtBooks(bgtBooks);
				
				if(delivered.getDlvAmount() > (bgtAmount*100)) {
					deliveredListOld.add(delivered);
				}
			}
			//old_dlv for carry forword end
			
			Delivered delivered = null, deliveredDb = null;
			DeliveredHistory deliveredHistory = null;
			
			for (Point point : pointList) {
				Double advDlvAmount = 0.0, dlvEquipments = 0.0, dlvFurniture = 0.0, dlvComputer = 0.0, dlvRepair = 0.0, dlvReagent = 0.0, dlvBooks = 0.0;
				for (Delivered deliveredOld : deliveredListOld) {
					if(point.getId().toString().equals(deliveredOld.getPoint().getId().toString())) {
						advDlvAmount = (deliveredOld.getDlvAmount()-(deliveredOld.getBgtAmount()*100));		
						dlvEquipments = (deliveredOld.getDlvEquipments()-(deliveredOld.getBgtEquipments()*100));
						dlvFurniture = (deliveredOld.getDlvFurniture()-(deliveredOld.getBgtFurniture()*100));
						dlvComputer = (deliveredOld.getDlvComputer()-(deliveredOld.getBgtComputer()*100));
						dlvRepair = (deliveredOld.getDlvRepair()-(deliveredOld.getBgtRepair()*100));
						dlvReagent = (deliveredOld.getDlvReagent()-(deliveredOld.getBgtReagent()*100));
						dlvBooks = (deliveredOld.getDlvBooks()-(deliveredOld.getBgtBooks()*100));
						break;
					}
				}
				
				Integer lastDeliveredId = (Integer)commonService.getMaxValueByObjectAndColumn("Delivered", "id");
				if(lastDeliveredId != null) {
					deliveredDb = (Delivered)commonService.getAnObjectByAnyUniqueColumn("Delivered", "id", lastDeliveredId.toString());
				}
				String deliveryId = getReferenceNo(deliveredDb);
				delivered = new Delivered();
				delivered.setPoint(point);
				delivered.setFiscalYear(fiscalYear);
				delivered.setCreatedBy(principal.getName());
				delivered.setCreatedDate(new Date());
				delivered.setDlvAmount(advDlvAmount);
				
				delivered.setDlvEquipments(dlvEquipments);
				delivered.setDlvFurniture(dlvFurniture);
				delivered.setDlvComputer(dlvComputer);
				delivered.setDlvReagent(dlvReagent);
				delivered.setDlvRepair(dlvRepair);
				delivered.setDlvBooks(dlvBooks);
				
				delivered.setId(null);
				delivered.setDeliveryId(deliveryId);
				commonService.saveOrUpdateModelObjectToDB(delivered);
				
				
				// history
				deliveredHistory = new DeliveredHistory(null, delivered.getDeliveryId(), delivered.getPoint().getTeamLeader().getName(), delivered.getPoint().getPointType().getName(), delivered.getPoint(), delivered.getFiscalYear(), delivered.getDlvAmount(), 
						delivered.getDlvEquipments(), delivered.getDlvFurniture(), delivered.getDlvComputer(), delivered.getDlvRepair(), delivered.getDlvReagent(),
						delivered.getDlvBooks(), principal.getName(), new Date(), delivered.getRemarks());
				commonService.saveOrUpdateModelObjectToDB(deliveredHistory);
			}
			
			deliveredListLeatest = (List<Delivered>)(Object)
					commonService.getObjectListByAnyColumn("Delivered", "fiscalYear.name", getCurrentFiscalYear());
			
		}
		
		List<Budget> budgetList = (List<Budget>)(Object) 
				commonService.getObjectListByAnyColumn("Budget", "fiscalYear.name", getCurrentFiscalYear());
		
		
		
		for (Delivered delivered : deliveredListLeatest) {
			Double bgtAmount = 0.0, bgtEquipments = 0.0, bgtFurniture = 0.0, bgtComputer = 0.0, bgtRepair = 0.0, bgtReagent = 0.0, bgtBooks = 0.0;
			for (Budget budget : budgetList) {
				if(delivered.getPoint().getId().toString().equals(budget.getPoint().getId().toString())) {
					bgtAmount+=budget.getBgtAmount();
					
					if(budget.getGoodsType().getName().toUpperCase().equals(EQUIPMENTS)) {
						bgtEquipments+=budget.getBgtAmount();
					}
					if(budget.getGoodsType().getName().toUpperCase().equals(REAGENT)) {
						bgtReagent+=budget.getBgtAmount();						
					}
					if(budget.getGoodsType().getName().toUpperCase().equals(FURNITURE)) {
						bgtFurniture+=budget.getBgtAmount();
					}
					if(budget.getGoodsType().getName().toUpperCase().equals(COMPUTER)) {
						bgtComputer+=budget.getBgtAmount();
					}
					if(budget.getGoodsType().getName().toUpperCase().equals(REPAIR)) {
						bgtRepair+=budget.getBgtAmount();
					}
					
					if(budget.getGoodsType().getName().toUpperCase().equals(BOOKS)) {
						bgtBooks+=budget.getBgtAmount();
					}
				}
			}
			delivered.setBgtEquipments(bgtEquipments);
			delivered.setBgtFurniture(bgtFurniture);
			delivered.setBgtComputer(bgtComputer);
			delivered.setBgtReagent(bgtReagent);
			delivered.setBgtRepair(bgtRepair);
			delivered.setBgtBooks(bgtBooks);
			
			delivered.setBgtAmount(bgtAmount);			
		}
		
		
		/*for (Delivered delivered : deliveredListLeatest) {
			for (Delivered deliveredOld : deliveredListOld) {
				if(delivered.getPoint().getId().toString().equals(deliveredOld.getPoint().getId().toString())) {
					delivered.setDlvAmount(deliveredOld.getDlvAmount()-deliveredOld.getBgtAmount());
					commonService.saveOrUpdateModelObjectToDB(delivered);
					break;
				}
			}
		}*/
		
		//finalDeliveredList.addAll(deliveredListOld);
		finalDeliveredList.addAll(deliveredListLeatest);
		
		/*for (Point point : pointList) {
			point.setName(convertFromUTF8(point.getName()));
		}*/
		
		int i = 1;
		for (Delivered delivered : finalDeliveredList) {
			delivered.setRecid(delivered.getId());
			delivered.setSlNo(i);
			
			/*for (Point point : pointList) {
				if(delivered.getPoint().getId().toString().equals(point.getId().toString())) {
					delivered.setPoint(point);
					break;
				}
			}*/
			
			i++;
		}
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		toJson = ow.writeValueAsString(finalDeliveredList);
		return toJson;
	}
	
	@RequestMapping(value = {"/saveDeliveredList"}, method = RequestMethod.POST)
	private @ResponseBody String saveDeliveredList(@RequestBody String jesonString, Principal principal) 
			throws JsonGenerationException, JsonMappingException, Exception {
		String toJson = "";
		String result = "";
		Gson gson = new Gson();
		DeliveredHistory deliveredHistory = null;
		
		DeliveredBean deliveredBean = gson.fromJson(jesonString, DeliveredBean.class);
		List<Delivered> deliveredList = deliveredBean.getDeliveredList();
		
		Date now = new Date();
		String loginUser = principal.getName();
		if(deliveredList.size() > 0) {
			for (Delivered delivered : deliveredList) {
				Delivered deliveredDb = (Delivered)commonService.getAnObjectByAnyUniqueColumn("Delivered", "id", delivered.getRecid().toString());
				/*Double bgtAmount = 0.0;
				List<Budget> budgetList = (List<Budget>)(Object)
						commonService.getObjectListByTwoColumn("Budget", "fiscalYear.id", deliveredDb.getFiscalYear().getId().toString(), 
						"point.id", deliveredDb.getPoint().getId().toString());
				for (Budget budget : budgetList) {
					bgtAmount+=budget.getBgtAmount();
				}*/
				
				if(deliveredDb != null) {
					Double totalDlvAmount = 0.0;
					
					if(delivered.getRemarks() != null) {
						deliveredDb.setRemarks(delivered.getRemarks());
					}
					deliveredDb.setModifiedBy(principal.getName());
					deliveredDb.setModifiedDate(now);
					
					if(delivered.getDlvEquipments() != null) {
						totalDlvAmount +=delivered.getDlvEquipments();
						deliveredDb.setDlvEquipments(delivered.getDlvEquipments());
					} else {
						totalDlvAmount +=deliveredDb.getDlvEquipments();
						deliveredDb.setDlvEquipments(deliveredDb.getDlvEquipments());
					}
					
					if(delivered.getDlvFurniture() != null) {
						totalDlvAmount +=delivered.getDlvFurniture();	
						deliveredDb.setDlvFurniture(delivered.getDlvFurniture());
					} else {
						totalDlvAmount +=deliveredDb.getDlvFurniture();
						deliveredDb.setDlvFurniture(deliveredDb.getDlvFurniture());
					}
					
					if(delivered.getDlvComputer() != null) {
						totalDlvAmount +=delivered.getDlvComputer();
						deliveredDb.setDlvComputer(delivered.getDlvComputer());
					} else {
						totalDlvAmount +=deliveredDb.getDlvComputer();
						deliveredDb.setDlvComputer(deliveredDb.getDlvComputer());
					}
					
					if(delivered.getDlvReagent() != null) {
						totalDlvAmount +=delivered.getDlvReagent();
						deliveredDb.setDlvReagent(delivered.getDlvReagent());
					} else {
						totalDlvAmount +=deliveredDb.getDlvReagent();
						deliveredDb.setDlvReagent(deliveredDb.getDlvReagent());
					}
					
					if(delivered.getDlvRepair() != null) {
						totalDlvAmount +=delivered.getDlvRepair();
						deliveredDb.setDlvRepair(delivered.getDlvRepair());
					} else {
						totalDlvAmount +=deliveredDb.getDlvRepair();
						deliveredDb.setDlvRepair(deliveredDb.getDlvRepair());
					}
					
					if(delivered.getDlvBooks() != null) {
						totalDlvAmount +=delivered.getDlvBooks();
						deliveredDb.setDlvBooks(delivered.getDlvBooks());
					} else {
						totalDlvAmount +=deliveredDb.getDlvBooks();
						deliveredDb.setDlvBooks(deliveredDb.getDlvBooks());
					}
					
					/*if(delivered.getDlvAmount() != null) {
						deliveredDb.setDlvAmount(delivered.getDlvAmount());
						commonService.saveOrUpdateModelObjectToDB(deliveredDb);
						result = "success";
						
						// history
						deliveredHistory = new DeliveredHistory(null, deliveredDb.getDeliveryId(), deliveredDb.getPoint(), deliveredDb.getFiscalYear(), deliveredDb.getDlvAmount(), principal.getName(), new Date(), deliveredDb.getRemarks());
						commonService.saveOrUpdateModelObjectToDB(deliveredHistory);						
						
					}else {
						commonService.saveOrUpdateModelObjectToDB(deliveredDb);
						result = "success";
						
						// history
						deliveredHistory = new DeliveredHistory(null, deliveredDb.getDeliveryId(), deliveredDb.getPoint(), deliveredDb.getFiscalYear(), deliveredDb.getDlvAmount(), principal.getName(), new Date(), deliveredDb.getRemarks());
						commonService.saveOrUpdateModelObjectToDB(deliveredHistory);
					}*/
					
					deliveredDb.setDlvAmount(totalDlvAmount);
					commonService.saveOrUpdateModelObjectToDB(deliveredDb);
					
					// history
					//deliveredHistory = new DeliveredHistory(null, deliveredDb.getDeliveryId(), deliveredDb.getPoint(), deliveredDb.getFiscalYear(), deliveredDb.getDlvAmount(), principal.getName(), new Date(), deliveredDb.getRemarks());
					deliveredHistory = new DeliveredHistory(null, deliveredDb.getDeliveryId(), deliveredDb.getPoint().getTeamLeader().getName(), deliveredDb.getPoint().getPointType().getName(), deliveredDb.getPoint(), deliveredDb.getFiscalYear(), deliveredDb.getDlvAmount(), 
							deliveredDb.getDlvEquipments(), deliveredDb.getDlvFurniture(), deliveredDb.getDlvComputer(), deliveredDb.getDlvRepair(), deliveredDb.getDlvReagent(),
							deliveredDb.getDlvBooks(), principal.getName(), new Date(), deliveredDb.getRemarks());
							
					commonService.saveOrUpdateModelObjectToDB(deliveredHistory);
					
					result = "success";
				} else {
					result = "failed";
				}
			}
			
		}
		
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		toJson = ow.writeValueAsString(result);
		return toJson;
	}	
	
	private String getReferenceNo(Delivered delivered){
		String refNo = "001";
		if(delivered != null){
			Integer ref = 1;
			
			if(delivered.getDeliveryId() != null) {
				 ref = Integer.parseInt(delivered.getDeliveryId())+1;
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
	// methos to Dev Item delete
			@RequestMapping(value = "/deliveryHistory", method = RequestMethod.GET)
			public ModelAndView deliveryHistory(RedirectAttributes redirectAttributes,
					Principal principal,ModelMap model) {
				if (principal == null) {
					return new ModelAndView("redirect:/login");
				}
				List<DeliveredHistory> deliveredHistories=(List<DeliveredHistory>) (Object)commonService.getAllObjectList("DeliveredHistory");
				for (DeliveredHistory deliveredHistory : deliveredHistories) {
					deliveredHistory.getPoint().setName(convertFromUTF8(deliveredHistory.getPoint().getName()));;
				}
				
				model.put("deliveredHistories", deliveredHistories);
				return new ModelAndView("deliveryHistoryForm");
			}
	
}
