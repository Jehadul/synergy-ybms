package com.nazdaq.ybms;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nazdaq.ybms.beans.ColoBeanB;
import com.nazdaq.ybms.beans.CommonBean;
import com.nazdaq.ybms.beans.CommonBeanAdvance;
import com.nazdaq.ybms.beans.DeliveryRepoBean;
import com.nazdaq.ybms.beans.DeliveryRepoBeanBan;
import com.nazdaq.ybms.beans.FormSevenBean;
import com.nazdaq.ybms.beans.RepoFormOneBean;
import com.nazdaq.ybms.model.Bill;
import com.nazdaq.ybms.model.Budget;
import com.nazdaq.ybms.model.Delivered;
import com.nazdaq.ybms.model.Delivery;
import com.nazdaq.ybms.model.FiscalYear;
import com.nazdaq.ybms.model.ReportColumnTitle;
import com.nazdaq.ybms.model.StockReport;
import com.nazdaq.ybms.model.TeamLeader;
import com.nazdaq.ybms.service.CommonService;
import com.nazdaq.ybms.util.Constants;
import com.nazdaq.ybms.util.CommonConverter;
import com.nazdaq.ybms.util.UTF8Converter;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

@Controller
public class ReportController extends UTF8Converter implements Constants {

	@Autowired
	private CommonService commonService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downLoadAll", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadAll(HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {

		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();
		List<CommonBeanAdvance> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {

			if (!(code.containsKey(budget.getBudgetCode().getId())
					&& code.get(budget.getBudgetCode().getId()) == budget.getPoint().getId())) {
				code.put(budget.getBudgetCode().getId(), budget.getPoint().getId());
				CommonBeanAdvance commonBean = new CommonBeanAdvance();
				commonBean.setCodeId(budget.getBudgetCode().getId());
				commonBean.setPointId(budget.getPoint().getId());
				commonBean.setTeamLeaderId(budget.getPoint().getTeamLeader().getId());
				commonBeans.add(commonBean);
			}

		}
		Integer sl = 1;
		for (CommonBeanAdvance commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()
						&& commonBean.getTeamLeaderId() == budget.getPoint().getTeamLeader().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill += budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(convertFromUTF8(budget.getBudgetSource().getName()));
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(
						CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(), reportColumnTitleArr));
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(
						CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(), reportColumnTitleArr));
			if (budgetAmount > 0.0) {
				Double pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean.setBillPendingAmount(
							CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(), reportColumnTitleArr));
				}

			}
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}
		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoOne.jasper");
		RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("repoFormOneBean", repoFormOneBean);
		jRdataSource = new JRBeanCollectionDataSource(repoFormOneBeans, false);

		params.put("datasource", jRdataSource);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		ReportColumnTitle reportColumnTitle = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "budget_key");
		String fiscalYearStr = convertFromUTF8(reportColumnTitle.getTitleBn()) + " "
				+ CommonConverter.getDigitBanglaFromEnglish(fiscalYear.getFromYear(), reportColumnTitleArr) + "-"
				+ CommonConverter.getDigitBanglaFromEnglish(fiscalYear.getToYear(), reportColumnTitleArr);
		params.put("fiscalYear", fiscalYearStr);

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "inline; filename=allAllotments.xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "downLoadAllRest", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadAllRest(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();
		List<CommonBeanAdvance> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {

			if ((budget.getBgtAmount() - budget.getBillAmount() > 0.0)
					&& !(code.containsKey(budget.getBudgetCode().getId())
							&& code.get(budget.getBudgetCode().getId()) == budget.getPoint().getId())) {
				code.put(budget.getBudgetCode().getId(), budget.getPoint().getId());
				CommonBeanAdvance commonBean = new CommonBeanAdvance();
				commonBean.setCodeId(budget.getBudgetCode().getId());
				commonBean.setPointId(budget.getPoint().getId());
				commonBean.setTeamLeaderId(budget.getPoint().getTeamLeader().getId());
				commonBeans.add(commonBean);
			}

		}
		Integer sl = 1;
		for (CommonBeanAdvance commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()
						&& commonBean.getTeamLeaderId() == budget.getPoint().getTeamLeader().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill += budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(convertFromUTF8(budget.getBudgetSource().getName()));
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(
						CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(), reportColumnTitleArr));
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(
						CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(), reportColumnTitleArr));
			if (budgetAmount > 0.0) {
				Double pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean.setBillPendingAmount(
							CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(), reportColumnTitleArr));
				}

			}
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}
		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoOneRest.jasper");
		RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("repoFormOneBean", repoFormOneBean);
		jRdataSource = new JRBeanCollectionDataSource(repoFormOneBeans, false);

		params.put("datasource", jRdataSource);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		ReportColumnTitle reportColumnTitle = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "budget_key");
		String fiscalYearStr = convertFromUTF8(reportColumnTitle.getTitleBn()) + " "
				+ CommonConverter.getDigitBanglaFromEnglish(fiscalYear.getFromYear(), reportColumnTitleArr) + "-"
				+ CommonConverter.getDigitBanglaFromEnglish(fiscalYear.getToYear(), reportColumnTitleArr);
		params.put("fiscalYear", fiscalYearStr);

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "inline; filename=restOfAllAllotments.xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downLoadAllAltByPonit", method = RequestMethod.POST)
	@ResponseBody
	public void downLoadAllAltByPonit(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if (budget.getPoint().getId().toString().equals(request.getParameter("pointId"))
					&& !(code.containsKey(budget.getBudgetCode().getId())
							&& code.get(budget.getBudgetCode().getId()) == budget.getPoint().getId())) {
				code.put(budget.getBudgetCode().getId(), budget.getPoint().getId());
				CommonBean commonBean = new CommonBean();
				commonBean.setCodeId(budget.getBudgetCode().getId());
				commonBean.setPointId(budget.getPoint().getId());

				commonBeans.add(commonBean);

			}

		}
		Double totalBudgetAmount = 0.0;
		Double totalBillAmount = 0.0;
		Double totalPendingAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill += budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(convertFromUTF8(budget.getBudgetSource().getName()));
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(totalBill.toString());
			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(
						CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(), reportColumnTitleArr));
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(
						CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(), reportColumnTitleArr));
			Double pendingam = 0.0;
			if (budgetAmount > 0.0) {
				pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean.setBillPendingAmount(
							CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(), reportColumnTitleArr));
				}

			}
			totalBillAmount += totalBill;
			totalBudgetAmount += budgetAmount;
			totalPendingAmount += pendingam;
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoOnePointWise.jasper");
		RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("repoFormOneBean", repoFormOneBean);
		jRdataSource = new JRBeanCollectionDataSource(repoFormOneBeans, false);

		params.put("datasource", jRdataSource);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		String fiscalYearStr = "All Allotments " + fiscalYear.getFromYear() + "-" + fiscalYear.getToYear();
		params.put("fiscalYear", fiscalYearStr);
		String totalBudgetStr = "0.0";
		String totalBillStr = "0.0";
		String totalPendingStr = "0.0";

		if (totalBillAmount != null && totalBillAmount > 0.0) {
			totalBillStr = totalBillAmount.toString();
		}

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		if (totalPendingAmount != null && totalPendingAmount > 0.0) {
			totalPendingStr = totalPendingAmount.toString();
		}

		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		params.put("totalBill", CommonConverter.getDigitBanglaFromEnglish(totalBillStr, reportColumnTitleArr));
		params.put("totalPending", CommonConverter.getDigitBanglaFromEnglish(totalPendingStr, reportColumnTitleArr));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition",
				"inline; filename=allAllotmentsPointWise_" + request.getParameter("pointId") + ".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downLoadAllAltByPonitRest", method = RequestMethod.POST)
	@ResponseBody
	public void downLoadAllAltByPonitRest(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if ((budget.getBgtAmount() - budget.getBillAmount() > 0.0)
					&& budget.getPoint().getId().toString().equals(request.getParameter("pointId"))
					&& !(code.containsKey(budget.getBudgetCode().getId())
							&& code.get(budget.getBudgetCode().getId()) == budget.getPoint().getId())) {
				code.put(budget.getBudgetCode().getId(), budget.getPoint().getId());
				CommonBean commonBean = new CommonBean();
				commonBean.setCodeId(budget.getBudgetCode().getId());
				commonBean.setPointId(budget.getPoint().getId());

				commonBeans.add(commonBean);

			}

		}
		Double totalBudgetAmount = 0.0;
		Double totalBillAmount = 0.0;
		Double totalPendingAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill += budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(convertFromUTF8(budget.getBudgetSource().getName()));
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(totalBill.toString());
			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(
						CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(), reportColumnTitleArr));
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(
						CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(), reportColumnTitleArr));
			Double pendingam = 0.0;
			if (budgetAmount > 0.0) {
				pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean.setBillPendingAmount(
							CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(), reportColumnTitleArr));
				}

			}
			totalBillAmount += totalBill;
			totalBudgetAmount += budgetAmount;
			totalPendingAmount += pendingam;
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoOnePointWiseRest.jasper");
		RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("repoFormOneBean", repoFormOneBean);
		jRdataSource = new JRBeanCollectionDataSource(repoFormOneBeans, false);

		params.put("datasource", jRdataSource);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		String fiscalYearStr = "All Allotments " + fiscalYear.getFromYear() + "-" + fiscalYear.getToYear();
		params.put("fiscalYear", fiscalYearStr);
		String totalBudgetStr = "0.0";
		String totalBillStr = "0.0";
		String totalPendingStr = "0.0";

		if (totalBillAmount != null && totalBillAmount > 0.0) {
			totalBillStr = totalBillAmount.toString();
		}

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		if (totalPendingAmount != null && totalPendingAmount > 0.0) {
			totalPendingStr = totalPendingAmount.toString();
		}

		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		params.put("totalBill", CommonConverter.getDigitBanglaFromEnglish(totalBillStr, reportColumnTitleArr));
		params.put("totalPending", CommonConverter.getDigitBanglaFromEnglish(totalPendingStr, reportColumnTitleArr));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition",
				"inline; filename=restOfAllAllotmentsPointWise_" + request.getParameter("pointId") + ".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downLoadAllAltBySource", method = RequestMethod.POST)
	@ResponseBody
	public void downLoadAllAltBySource(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if (budget.getBudgetSource().getId().toString().equals(request.getParameter("budgetSourceId"))
					&& !(code.containsKey(budget.getBudgetCode().getId())
							&& code.get(budget.getBudgetCode().getId()) == budget.getPoint().getId())) {
				code.put(budget.getBudgetCode().getId(), budget.getPoint().getId());
				CommonBean commonBean = new CommonBean();
				commonBean.setCodeId(budget.getBudgetCode().getId());
				commonBean.setPointId(budget.getPoint().getId());

				commonBeans.add(commonBean);

			}

		}
		Double totalBudgetAmount = 0.0;
		Double totalBillAmount = 0.0;
		Double totalPendingAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill += budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(convertFromUTF8(budget.getBudgetSource().getName()));
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(totalBill.toString());
			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(
						CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(), reportColumnTitleArr));
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(
						CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(), reportColumnTitleArr));
			Double pendingam = 0.0;
			if (budgetAmount > 0.0) {
				pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean.setBillPendingAmount(
							CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(), reportColumnTitleArr));
				}

			}
			totalBillAmount += totalBill;
			totalBudgetAmount += budgetAmount;
			totalPendingAmount += pendingam;
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoOneSourceWise.jasper");
		RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("repoFormOneBean", repoFormOneBean);
		jRdataSource = new JRBeanCollectionDataSource(repoFormOneBeans, false);

		params.put("datasource", jRdataSource);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		String fiscalYearStr = "All Allotments " + fiscalYear.getFromYear() + "-" + fiscalYear.getToYear();
		params.put("fiscalYear", fiscalYearStr);
		String totalBudgetStr = "0.0";
		String totalBillStr = "0.0";
		String totalPendingStr = "0.0";

		if (totalBillAmount != null && totalBillAmount > 0.0) {
			totalBillStr = totalBillAmount.toString();
		}

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		if (totalPendingAmount != null && totalPendingAmount > 0.0) {
			totalPendingStr = totalPendingAmount.toString();
		}

		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		params.put("totalBill", CommonConverter.getDigitBanglaFromEnglish(totalBillStr, reportColumnTitleArr));
		params.put("totalPending", CommonConverter.getDigitBanglaFromEnglish(totalPendingStr, reportColumnTitleArr));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition",
				"inline; filename=allAllotmentsSourceWise_" + request.getParameter("budgetSourceId") + ".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downLoadAllAltBySourceRest", method = RequestMethod.POST)
	@ResponseBody
	public void downLoadAllAltBySourceRest(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if ((budget.getBgtAmount() - budget.getBillAmount() > 0.0)
					&& budget.getBudgetSource().getId().toString().equals(request.getParameter("budgetSourceId"))
					&& !(code.containsKey(budget.getBudgetCode().getId())
							&& code.get(budget.getBudgetCode().getId()) == budget.getPoint().getId())) {
				code.put(budget.getBudgetCode().getId(), budget.getPoint().getId());
				CommonBean commonBean = new CommonBean();
				commonBean.setCodeId(budget.getBudgetCode().getId());
				commonBean.setPointId(budget.getPoint().getId());

				commonBeans.add(commonBean);

			}

		}
		Double totalBudgetAmount = 0.0;
		Double totalBillAmount = 0.0;
		Double totalPendingAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill += budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(convertFromUTF8(budget.getBudgetSource().getName()));
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(totalBill.toString());
			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(
						CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(), reportColumnTitleArr));
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(
						CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(), reportColumnTitleArr));
			Double pendingam = 0.0;
			if (budgetAmount > 0.0) {
				pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean.setBillPendingAmount(
							CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(), reportColumnTitleArr));
				}

			}
			totalBillAmount += totalBill;
			totalBudgetAmount += budgetAmount;
			totalPendingAmount += pendingam;
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoOneSourceWiseRest.jasper");
		RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("repoFormOneBean", repoFormOneBean);
		jRdataSource = new JRBeanCollectionDataSource(repoFormOneBeans, false);

		params.put("datasource", jRdataSource);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		String fiscalYearStr = "All Allotments " + fiscalYear.getFromYear() + "-" + fiscalYear.getToYear();
		params.put("fiscalYear", fiscalYearStr);
		String totalBudgetStr = "0.0";
		String totalBillStr = "0.0";
		String totalPendingStr = "0.0";

		if (totalBillAmount != null && totalBillAmount > 0.0) {
			totalBillStr = totalBillAmount.toString();
		}

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		if (totalPendingAmount != null && totalPendingAmount > 0.0) {
			totalPendingStr = totalPendingAmount.toString();
		}

		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		params.put("totalBill", CommonConverter.getDigitBanglaFromEnglish(totalBillStr, reportColumnTitleArr));
		params.put("totalPending", CommonConverter.getDigitBanglaFromEnglish(totalPendingStr, reportColumnTitleArr));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition",
				"inline; filename=restOfAllAllotmentsSourceWise_" + request.getParameter("budgetSourceId") + ".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downLoadAllAltByTeamLd", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadAllAltByTeamLd(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if (budget.getPoint().getTeamLeader().getId().toString().equals(request.getParameter("teamLeaderId"))) {
				if (!(code.containsKey(budget.getBudgetCode().getId())
						&& code.get(budget.getBudgetCode().getId()) == budget.getPoint().getId())) {
					code.put(budget.getBudgetCode().getId(), budget.getPoint().getId());
					CommonBean commonBean = new CommonBean();
					commonBean.setCodeId(budget.getBudgetCode().getId());
					commonBean.setPointId(budget.getPoint().getId());

					commonBeans.add(commonBean);
				}

			}

		}
		Double totalBudgetAmount = 0.0;
		Double totalBillAmount = 0.0;
		Double totalPendingAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill += budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(convertFromUTF8(budget.getBudgetSource().getName()));
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(totalBill.toString());
			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(
						CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(), reportColumnTitleArr));
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(
						CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(), reportColumnTitleArr));
			Double pendingam = 0.0;
			if (budgetAmount > 0.0) {
				pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean.setBillPendingAmount(
							CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(), reportColumnTitleArr));
				}

			}
			totalBillAmount += totalBill;
			totalBudgetAmount += budgetAmount;
			totalPendingAmount += pendingam;
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoOneTemLed.jasper");
		RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("repoFormOneBean", repoFormOneBean);
		jRdataSource = new JRBeanCollectionDataSource(repoFormOneBeans, false);

		params.put("datasource", jRdataSource);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		String fiscalYearStr = "All Allotments " + fiscalYear.getFromYear() + "-" + fiscalYear.getToYear();
		params.put("fiscalYear", fiscalYearStr);
		String totalBudgetStr = "0.0";
		String totalBillStr = "0.0";
		String totalPendingStr = "0.0";

		if (totalBillAmount != null && totalBillAmount > 0.0) {
			totalBillStr = totalBillAmount.toString();
		}

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		if (totalPendingAmount != null && totalPendingAmount > 0.0) {
			totalPendingStr = totalPendingAmount.toString();
		}

		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		params.put("totalBill", CommonConverter.getDigitBanglaFromEnglish(totalBillStr, reportColumnTitleArr));
		params.put("totalPending", CommonConverter.getDigitBanglaFromEnglish(totalPendingStr, reportColumnTitleArr));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition",
				"inline; filename=allAllotmentsTeam" + request.getParameter("teamLeaderId") + ".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downLoadAllAltByTeamLdRest", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadAllAltByTeamLdRest(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if ((budget.getBgtAmount() - budget.getBillAmount() > 0.0) && budget.getPoint().getTeamLeader().getId()
					.toString().equals(request.getParameter("teamLeaderId"))) {
				if (!(code.containsKey(budget.getBudgetCode().getId())
						&& code.get(budget.getBudgetCode().getId()) == budget.getPoint().getId())) {
					code.put(budget.getBudgetCode().getId(), budget.getPoint().getId());
					CommonBean commonBean = new CommonBean();
					commonBean.setCodeId(budget.getBudgetCode().getId());
					commonBean.setPointId(budget.getPoint().getId());

					commonBeans.add(commonBean);
				}

			}

		}
		Double totalBudgetAmount = 0.0;
		Double totalBillAmount = 0.0;
		Double totalPendingAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill += budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(convertFromUTF8(budget.getBudgetSource().getName()));
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(totalBill.toString());
			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(
						CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(), reportColumnTitleArr));
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(
						CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(), reportColumnTitleArr));
			Double pendingam = 0.0;
			if (budgetAmount > 0.0) {
				pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean.setBillPendingAmount(
							CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(), reportColumnTitleArr));
				}

			}
			totalBillAmount += totalBill;
			totalBudgetAmount += budgetAmount;
			totalPendingAmount += pendingam;
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoOneTemLedRest.jasper");
		RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("repoFormOneBean", repoFormOneBean);
		jRdataSource = new JRBeanCollectionDataSource(repoFormOneBeans, false);

		params.put("datasource", jRdataSource);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		String fiscalYearStr = "All Allotments " + fiscalYear.getFromYear() + "-" + fiscalYear.getToYear();
		params.put("fiscalYear", fiscalYearStr);
		String totalBudgetStr = "0.0";
		String totalBillStr = "0.0";
		String totalPendingStr = "0.0";

		if (totalBillAmount != null && totalBillAmount > 0.0) {
			totalBillStr = totalBillAmount.toString();
		}

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		if (totalPendingAmount != null && totalPendingAmount > 0.0) {
			totalPendingStr = totalPendingAmount.toString();
		}

		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		params.put("totalBill", CommonConverter.getDigitBanglaFromEnglish(totalBillStr, reportColumnTitleArr));
		params.put("totalPending", CommonConverter.getDigitBanglaFromEnglish(totalPendingStr, reportColumnTitleArr));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition",
				"inline; filename=restOfAllAllotmentsTeam" + request.getParameter("teamLeaderId") + ".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downLoadAllAltByTeamLdAndPoint", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadAllAltByTeamLdAndPoint(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if (budget.getPoint().getId().toString().equals(request.getParameter("pointId")) && budget.getPoint()
					.getTeamLeader().getId().toString().equals(request.getParameter("teamLeaderId"))) {
				if (!code.containsKey(budget.getPoint().getId())) {
					code.put(budget.getPoint().getId(), budget.getPoint().getId());
					CommonBean commonBean = new CommonBean();
					commonBean.setCodeId(budget.getBudgetCode().getId());
					commonBean.setPointId(budget.getPoint().getId());
					commonBeans.add(commonBean);
				}
			}

		}
		Double totalBudgetAmount = 0.0;
		Double totalBillAmount = 0.0;
		Double totalPendingAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill += budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(convertFromUTF8(budget.getBudgetSource().getName()));
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(totalBill.toString());
			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(
						CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(), reportColumnTitleArr));
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(
						CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(), reportColumnTitleArr));
			Double pendingam = 0.0;
			if (budgetAmount > 0.0) {
				pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean.setBillPendingAmount(
							CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(), reportColumnTitleArr));
				}

			}
			totalBillAmount += totalBill;
			totalBudgetAmount += budgetAmount;
			totalPendingAmount += pendingam;
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoTwoTemPoint.jasper");
		RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("repoFormOneBean", repoFormOneBean);
		jRdataSource = new JRBeanCollectionDataSource(repoFormOneBeans, false);

		params.put("datasource", jRdataSource);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		String fiscalYearStr = "All Allotments-" + fiscalYear.getFromYear() + "-" + fiscalYear.getToYear();
		params.put("fiscalYear", fiscalYearStr);
		String totalBudgetStr = "0.0";
		String totalBillStr = "0.0";
		String totalPendingStr = "0.0";

		if (totalBillAmount != null && totalBillAmount > 0.0) {
			totalBillStr = totalBillAmount.toString();
		}

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		if (totalPendingAmount != null && totalPendingAmount > 0.0) {
			totalPendingStr = totalPendingAmount.toString();
		}

		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		params.put("totalBill", CommonConverter.getDigitBanglaFromEnglish(totalBillStr, reportColumnTitleArr));
		params.put("totalPending", CommonConverter.getDigitBanglaFromEnglish(totalPendingStr, reportColumnTitleArr));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "inline; filename=allAllotmentsPonitWise_PointID_"
				+ request.getParameter("pointId") + "_tmldID_" + request.getParameter("teamLeaderId") + ".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downLoadAllAltByTeamLdAndPointRest", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadAllAltByTeamLdAndPointRest(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<RepoFormOneBean> repoFormOneBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if ((budget.getBgtAmount() - budget.getBillAmount() > 0.0)
					&& budget.getPoint().getId().toString().equals(request.getParameter("pointId")) && budget.getPoint()
							.getTeamLeader().getId().toString().equals(request.getParameter("teamLeaderId"))) {
				if (!code.containsKey(budget.getPoint().getId())) {
					code.put(budget.getPoint().getId(), budget.getPoint().getId());
					CommonBean commonBean = new CommonBean();
					commonBean.setCodeId(budget.getBudgetCode().getId());
					commonBean.setPointId(budget.getPoint().getId());
					commonBeans.add(commonBean);
				}
			}

		}
		Double totalBudgetAmount = 0.0;
		Double totalBillAmount = 0.0;
		Double totalPendingAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {
			Double budgetAmount = 0.0;
			Double totalBill = 0.0;
			RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
			repoFormOneBean.setId(1);
			repoFormOneBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getCodeId() == budget.getBudgetCode().getId()
						&& commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();
					totalBill += budget.getBillAmount();
					repoFormOneBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					repoFormOneBean.setBudgetCode(convertFromUTF8(budget.getBudgetCode().getName()));
					repoFormOneBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					repoFormOneBean.setBudgetSource(convertFromUTF8(budget.getBudgetSource().getName()));
					repoFormOneBean.setToYear(budget.getFiscalYear().getToYear());
					repoFormOneBean.setFromYear(budget.getFiscalYear().getFromYear());

				}
			}

			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(totalBill.toString());
			if (budgetAmount != null && budgetAmount > 0.0)
				repoFormOneBean.setBudgetAmount(
						CommonConverter.getDigitBanglaFromEnglish(budgetAmount.toString(), reportColumnTitleArr));
			if (totalBill != null && totalBill > 0.0)
				repoFormOneBean.setBillAmount(
						CommonConverter.getDigitBanglaFromEnglish(totalBill.toString(), reportColumnTitleArr));
			Double pendingam = 0.0;
			if (budgetAmount > 0.0) {
				pendingam = budgetAmount - totalBill;
				if (pendingam != null && pendingam > 0.0) {
					repoFormOneBean.setBillPendingAmount(
							CommonConverter.getDigitBanglaFromEnglish(pendingam.toString(), reportColumnTitleArr));
				}

			}
			totalBillAmount += totalBill;
			totalBudgetAmount += budgetAmount;
			totalPendingAmount += pendingam;
			repoFormOneBeans.add(repoFormOneBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoTwoTemPointRest.jasper");
		RepoFormOneBean repoFormOneBean = new RepoFormOneBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("repoFormOneBean", repoFormOneBean);
		jRdataSource = new JRBeanCollectionDataSource(repoFormOneBeans, false);

		params.put("datasource", jRdataSource);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		String fiscalYearStr = "All Allotments-" + fiscalYear.getFromYear() + "-" + fiscalYear.getToYear();
		params.put("fiscalYear", fiscalYearStr);
		String totalBudgetStr = "0.0";
		String totalBillStr = "0.0";
		String totalPendingStr = "0.0";

		if (totalBillAmount != null && totalBillAmount > 0.0) {
			totalBillStr = totalBillAmount.toString();
		}

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		if (totalPendingAmount != null && totalPendingAmount > 0.0) {
			totalPendingStr = totalPendingAmount.toString();
		}

		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		params.put("totalBill", CommonConverter.getDigitBanglaFromEnglish(totalBillStr, reportColumnTitleArr));
		params.put("totalPending", CommonConverter.getDigitBanglaFromEnglish(totalPendingStr, reportColumnTitleArr));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "inline; filename=restOfAllAllotmentsPonitWise_PointID_"
				+ request.getParameter("pointId") + "_tmldID_" + request.getParameter("teamLeaderId") + ".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repoFormSeven", method = RequestMethod.POST)
	@ResponseBody
	public void repoFormSeven(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<FormSevenBean> formSevenBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if (budget.getPoint().getTeamLeader().getId().toString().equals(request.getParameter("teamLeaderId"))) {

				if (!code.containsKey(budget.getPoint().getId())) {
					code.put(budget.getPoint().getId(), budget.getPoint().getId());
					CommonBean commonBean = new CommonBean();
					commonBean.setPointId(budget.getPoint().getId());
					commonBeans.add(commonBean);
				}

			}

		}

		Double totalBudgetAmount = 0.0;
		Double totalDeliveryAmount = 0.0;
		Double budgetAmount = 0.0;
		Double deliveryAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {

			FormSevenBean formSevenBean = new FormSevenBean();
			formSevenBean.setId(1);
			formSevenBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();

				}
			}
			for (Budget budget : buList) {
				if (commonBean.getPointId() == budget.getPoint().getId()) {
					formSevenBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					formSevenBean.setPointType(convertFromUTF8(budget.getPoint().getPointType().getName()));
					formSevenBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					break;
				}
			}

			List<Delivery> deliveries = (List<Delivery>) (Object) commonService.getObjectListByTwoColumn("Delivery",
					"fiscal_year_dlv_id", request.getParameter("fiscalYearId"), "point_id",
					commonBean.getPointId().toString());
			for (Delivery delivery : deliveries) {
				deliveryAmount += delivery.getDlvAmount();
			}
			totalBudgetAmount += budgetAmount;
			totalDeliveryAmount += deliveryAmount;
			String budgetStr = "";
			String deliveryStr = "";
			if (budgetAmount != null & budgetAmount > 0.0)
				budgetStr = budgetAmount.toString();

			if (deliveryAmount != null & deliveryAmount > 0.0)
				deliveryStr = deliveryAmount.toString();
			formSevenBean.setBudgetAmount(
					CommonConverter.getDigitBanglaFromEnglish(budgetStr, reportColumnTitleArr).toString().trim());
			formSevenBean.setDeliveryAmount(
					CommonConverter.getDigitBanglaFromEnglish(deliveryStr, reportColumnTitleArr).toString().trim());
			formSevenBeans.add(formSevenBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoFormSeven.jasper");
		FormSevenBean formSevenBean = new FormSevenBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("formSevenBean", formSevenBean);
		jRdataSource = new JRBeanCollectionDataSource(formSevenBeans, false);

		params.put("datasource", jRdataSource);

		String totalBudgetStr = "0.0";
		String totalDeliveryStr = "0.0";

		if (totalDeliveryAmount != null && totalDeliveryAmount > 0.0) {
			totalDeliveryStr = totalDeliveryAmount.toString();
		}

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		params.put("totalDelivered", CommonConverter.getDigitBanglaFromEnglish(totalDeliveryStr, reportColumnTitleArr));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition",
				"inline; filename=allAllowtmentsVSgoods_tmldID_" + request.getParameter("teamLeaderId") + ".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repoPointVSGoods", method = RequestMethod.POST)
	@ResponseBody
	public void repoPointVSGoods(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<FormSevenBean> formSevenBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if (budget.getPoint().getId().toString().equals(request.getParameter("pointId"))) {

				if (!code.containsKey(budget.getPoint().getId())) {
					code.put(budget.getPoint().getId(), budget.getPoint().getId());
					CommonBean commonBean = new CommonBean();
					commonBean.setPointId(budget.getPoint().getId());
					commonBeans.add(commonBean);
				}

			}

		}

		Double totalBudgetAmount = 0.0;
		Double totalDeliveryAmount = 0.0;
		Double budgetAmount = 0.0;
		Double deliveryAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {

			FormSevenBean formSevenBean = new FormSevenBean();
			formSevenBean.setId(1);
			formSevenBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();

				}
			}
			for (Budget budget : buList) {
				if (commonBean.getPointId() == budget.getPoint().getId()) {
					formSevenBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					formSevenBean.setPointType(convertFromUTF8(budget.getPoint().getPointType().getName()));
					formSevenBean.setTeamLeaderName(convertFromUTF8(budget.getPoint().getTeamLeader().getName()));
					break;
				}
			}

			List<Delivery> deliveries = (List<Delivery>) (Object) commonService.getObjectListByTwoColumn("Delivery",
					"fiscal_year_dlv_id", request.getParameter("fiscalYearId"), "point_id",
					commonBean.getPointId().toString());
			for (Delivery delivery : deliveries) {
				deliveryAmount += delivery.getDlvAmount();
			}
			totalBudgetAmount += budgetAmount;
			totalDeliveryAmount += deliveryAmount;
			String budgetStr = "";
			String deliveryStr = "";
			if (budgetAmount != null & budgetAmount > 0.0)
				budgetStr = budgetAmount.toString();

			if (deliveryAmount != null & deliveryAmount > 0.0)
				deliveryStr = deliveryAmount.toString();
			formSevenBean.setBudgetAmount(
					CommonConverter.getDigitBanglaFromEnglish(budgetStr, reportColumnTitleArr).toString().trim());
			formSevenBean.setDeliveryAmount(
					CommonConverter.getDigitBanglaFromEnglish(deliveryStr, reportColumnTitleArr).toString().trim());
			formSevenBeans.add(formSevenBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/repoPointVSGoods.jasper");
		FormSevenBean formSevenBean = new FormSevenBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("formSevenBean", formSevenBean);
		jRdataSource = new JRBeanCollectionDataSource(formSevenBeans, false);

		params.put("datasource", jRdataSource);

		String totalBudgetStr = "0.0";
		String totalDeliveryStr = "0.0";

		if (totalDeliveryAmount != null && totalDeliveryAmount > 0.0) {
			totalDeliveryStr = totalDeliveryAmount.toString();
		}

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		params.put("totalDelivered", CommonConverter.getDigitBanglaFromEnglish(totalDeliveryStr, reportColumnTitleArr));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition",
				"inline; filename=PointVSgoods_tmldID_" + request.getParameter("teamLeaderId") + ".xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repoTeamLeaderPoits", method = RequestMethod.GET)
	@ResponseBody
	public void repoTeamLeaderPoits(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<FormSevenBean> formSevenBeans = new ArrayList<>();

		List<CommonBean> commonBeans = new ArrayList<>();
		ReportColumnTitle reportColumnTitleArr = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");

		Map<Integer, Integer> code = new HashMap<>();
		for (Budget budget : buList) {
			if (budget.getPoint().getTeamLeader().getId().toString().equals(request.getParameter("teamLeaderId"))) {
				if (!code.containsKey(budget.getPoint().getId())) {
					code.put(budget.getPoint().getId(), budget.getPoint().getId());
					CommonBean commonBean = new CommonBean();
					commonBean.setPointId(budget.getPoint().getId());
					commonBeans.add(commonBean);
				}

			}

		}

		Double totalBudgetAmount = 0.0;
		Double totalDeliveryAmount = 0.0;
		Double budgetAmount = 0.0;
		Double deliveryAmount = 0.0;
		Integer sl = 1;
		for (CommonBean commonBean : commonBeans) {

			FormSevenBean formSevenBean = new FormSevenBean();
			formSevenBean.setId(1);
			formSevenBean.setSlNO(CommonConverter.getDigitBanglaFromEnglish(sl.toString(), reportColumnTitleArr));
			for (Budget budget : buList) {
				if (commonBean.getPointId() == budget.getPoint().getId()) {
					budgetAmount += budget.getBgtAmount();

				}
			}
			for (Budget budget : buList) {
				if (commonBean.getPointId() == budget.getPoint().getId()) {
					formSevenBean.setPointName(convertFromUTF8(budget.getPoint().getName()));
					formSevenBean.setPointType(convertFromUTF8(budget.getPoint().getPointType().getName()));

					break;
				}
			}

			totalBudgetAmount += budgetAmount;

			String budgetStr = "";

			if (budgetAmount != null & budgetAmount > 0.0)
				budgetStr = budgetAmount.toString();

			formSevenBean.setBudgetAmount(
					CommonConverter.getDigitBanglaFromEnglish(budgetStr, reportColumnTitleArr).toString().trim());

			formSevenBeans.add(formSevenBean);
			sl++;
		}

		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/bmsRpoTeamLdPointList.jasper");
		FormSevenBean formSevenBean = new FormSevenBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("formSevenBean", formSevenBean);
		jRdataSource = new JRBeanCollectionDataSource(formSevenBeans, false);

		params.put("datasource", jRdataSource);

		String totalBudgetStr = "0.0";

		if (totalBudgetAmount != null && totalBudgetAmount > 0.0) {
			totalBudgetStr = totalBudgetAmount.toString();
		}

		ReportColumnTitle reportColumnTitle = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "inistitute_key");
		params.put("totalBudget", CommonConverter.getDigitBanglaFromEnglish(totalBudgetStr, reportColumnTitleArr));
		TeamLeader teamLeader = (TeamLeader) commonService.getAnObjectByAnyUniqueColumn("TeamLeader", "id",
				request.getParameter("teamLeaderId"));
		params.put("teamLeaderName",
				convertFromUTF8(teamLeader.getName()) + convertFromUTF8(reportColumnTitle.getTitleBn()));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "inline; filename=teamLeaderPointList.xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/stackReports", method = RequestMethod.GET)
	@ResponseBody
	public void stackReports(HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {
		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<StockReport> stockReports = new ArrayList<>();
		StockReport stockReportBn = (StockReport) commonService.getAnObjectByAnyUniqueColumn("StockReport",
				"fiscal_year_id", request.getParameter("fiscalYearId"));
		stockReports.add(stockReportBn);
		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/stockReport.jasper");
		StockReport stockReport = new StockReport();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("stockReport", stockReport);
		jRdataSource = new JRBeanCollectionDataSource(stockReports, false);

		params.put("datasource", jRdataSource);

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "inline; filename=stockReports.xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getReportColTeamLd", method = RequestMethod.GET)
	@ResponseBody
	public void getReportColTeamLd(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		List<Budget> buList = (List<Budget>) (Object) commonService.getObjectListByAnyColumn("Budget", "fiscal_year_id",
				request.getParameter("fiscalYearId"));

		List<ColoBeanB> coloBeanBss = new ArrayList<>();
		List<Budget> budgets = (List<Budget>) (Object) commonService.getObjectListByTwoColumn("Budget",
				"fiscal_year_id", request.getParameter("fiscalYearId").trim(), "color_code_id",
				request.getParameter("colorCodeId").trim());
		for (Budget budget : budgets) {
			if (budget.getPoint().getTeamLeader().getId().toString()
					.equals(request.getParameter("teamLeaderId").trim())) {
				ColoBeanB coloBeanBObj = new ColoBeanB(convertFromUTF8(budget.getPoint().getName()),
						convertFromUTF8(budget.getBudgetCode().getName()), budget.getBgtAmount(),
						budget.getBillAmount(), convertFromUTF8(budget.getPoint().getPointType().getName()),
						budget.getFiscalYear().getName(), convertFromUTF8(budget.getPoint().getTeamLeader().getName()),
						budget.getColorCode().getHexaCode());
				coloBeanBss.add(coloBeanBObj);
			}
		}
		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		jasperStream = this.getClass().getResourceAsStream("/report/reportColorAndTeamLeader.jasper");
		ColoBeanB coloBeanB = new ColoBeanB();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("coloBeanB", coloBeanB);
		jRdataSource = new JRBeanCollectionDataSource(coloBeanBss, false);
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		params.put("datasource", jRdataSource);
		params.put("fiscalyear", "All Allotments-" + fiscalYear.getFromYear() + "-"
				+ fiscalYear.getToYear().replaceFirst("20", "") + "");

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "inline; filename=reportColorAndTeamLeader.xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downLoadDlvReport", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadDlvReport(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		

		List<DeliveryRepoBean> deliveryRepoBeans = new ArrayList<>();
		List<DeliveryRepoBeanBan> deliveryRepoBeansBan = new ArrayList<>();
		List<Budget> budgetList = (List<Budget>)(Object) 
				commonService.getObjectListByAnyColumn("Budget", "fiscalYear.id", request.getParameter("fiscalYearId"));
		List<Delivered> delivereds = (List<Delivered>) (Object) commonService.getObjectListByAnyColumn("Delivered", "fiscal_year_id",
				request.getParameter("fiscalYearId"));
		ReportColumnTitle reportColumnTitle = (ReportColumnTitle) commonService
				.getAnObjectByAnyUniqueColumn("ReportColumnTitle", "clmn_keyword", "banglaDigit_key");
		Integer slno=1;
		for (Delivered delivered : delivereds) {
			
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
			if(request.getParameter("version").trim().equals("ban")) {
				delivered.setBgtEquipments(bgtEquipments);
				delivered.setBgtFurniture(bgtFurniture);
				delivered.setBgtComputer(bgtComputer);
				delivered.setBgtReagent(bgtReagent);
				delivered.setBgtRepair(bgtRepair);
				delivered.setBgtBooks(bgtBooks);
				
				delivered.setBgtAmount(bgtAmount);		
				
				DeliveryRepoBeanBan deliveryRepoBeanBan=new DeliveryRepoBeanBan();
				deliveryRepoBeanBan.setSlNo(CommonConverter.getDigitBanglaFromEnglish(slno.toString(), reportColumnTitle));
				deliveryRepoBeanBan.setDeliveryId(CommonConverter.getDigitBanglaFromEnglish(delivered.getDeliveryId(), reportColumnTitle).replace(".0", ""));
				deliveryRepoBeanBan.setDlvAmount(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getDlvAmount(), reportColumnTitle));
				deliveryRepoBeanBan.setBgtAmount(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getBgtAmount(), reportColumnTitle));
				deliveryRepoBeanBan.setBgtBook(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getBgtBooks(), reportColumnTitle));
				deliveryRepoBeanBan.setDlvBook(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getDlvBooks(), reportColumnTitle));
				deliveryRepoBeanBan.setBgtComputer(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getBgtComputer(), reportColumnTitle));
				deliveryRepoBeanBan.setDlvComputer(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getDlvComputer(), reportColumnTitle));
				deliveryRepoBeanBan.setBgtEquipment(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getBgtEquipments(), reportColumnTitle));
				deliveryRepoBeanBan.setDlvEquipment(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getDlvEquipments(), reportColumnTitle));
				deliveryRepoBeanBan.setBgtFurniture(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getBgtFurniture(), reportColumnTitle));
				deliveryRepoBeanBan.setDlvFurniture(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getDlvFurniture(), reportColumnTitle));
				deliveryRepoBeanBan.setBgtReagent(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getBgtReagent(), reportColumnTitle));
				deliveryRepoBeanBan.setDlvReagent(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getDlvReagent(), reportColumnTitle));
				deliveryRepoBeanBan.setBgtRepair(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getBgtRepair(), reportColumnTitle));
				deliveryRepoBeanBan.setDlvRepair(CommonConverter.getDigitBanglaFromEnglishnew(delivered.getDlvRepair(), reportColumnTitle));
				
				deliveryRepoBeanBan.setRemarks(convertFromUTF8(delivered.getRemarks()));
				deliveryRepoBeanBan.setPointName(convertFromUTF8(delivered.getPoint().getName()));
				deliveryRepoBeansBan.add(deliveryRepoBeanBan);
			}else {
				delivered.setBgtEquipments(bgtEquipments);
				delivered.setBgtFurniture(bgtFurniture);
				delivered.setBgtComputer(bgtComputer);
				delivered.setBgtReagent(bgtReagent);
				delivered.setBgtRepair(bgtRepair);
				delivered.setBgtBooks(bgtBooks);
				
				delivered.setBgtAmount(bgtAmount);		
				DeliveryRepoBean deliveryRepoBeanObj=new DeliveryRepoBean();
				deliveryRepoBeanObj.setDeliveryId(delivered.getDeliveryId());
				deliveryRepoBeanObj.setDlvAmount(delivered.getDlvAmount());
				deliveryRepoBeanObj.setBgtAmount(delivered.getBgtAmount());
				deliveryRepoBeanObj.setBgtBook(delivered.getBgtBooks());
				deliveryRepoBeanObj.setDlvBook(delivered.getDlvBooks());
				deliveryRepoBeanObj.setBgtComputer(delivered.getBgtComputer());
				deliveryRepoBeanObj.setDlvComputer(delivered.getDlvComputer());
				deliveryRepoBeanObj.setBgtEquipment(delivered.getBgtEquipments());
				deliveryRepoBeanObj.setDlvEquipment(delivered.getDlvEquipments());
				deliveryRepoBeanObj.setBgtFurniture(delivered.getBgtFurniture());
				deliveryRepoBeanObj.setDlvFurniture(delivered.getDlvFurniture());
				deliveryRepoBeanObj.setBgtReagent(delivered.getBgtReagent());
				deliveryRepoBeanObj.setDlvReagent(delivered.getDlvReagent());
				deliveryRepoBeanObj.setBgtRepair(delivered.getBgtRepair());
				deliveryRepoBeanObj.setDlvRepair(delivered.getDlvRepair());
				deliveryRepoBeanObj.setRemarks(delivered.getRemarks());
				deliveryRepoBeanObj.setPointName(delivered.getPoint().getNameEn());
				deliveryRepoBeans.add(deliveryRepoBeanObj);
			}
			
			slno++;
		}
		JRDataSource jRdataSource = null;
		InputStream jasperStream = null;
		if(request.getParameter("version").trim().equals("ban")) {
			jasperStream = this.getClass().getResourceAsStream("/report/deliveryListReportBan.jasper");
		}else {
			jasperStream = this.getClass().getResourceAsStream("/report/deliveryListReport.jasper");
		}
		
		DeliveryRepoBean deliveryRepoBean = new DeliveryRepoBean();
		DeliveryRepoBeanBan deliveryRepoBeanBan=new DeliveryRepoBeanBan();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		if(request.getParameter("version").trim().equals("ban")) {
			datasourcemap.put("deliveryRepoBeanBan", deliveryRepoBeanBan);
			jRdataSource = new JRBeanCollectionDataSource(deliveryRepoBeansBan, false);
		}else {
			datasourcemap.put("deliveryRepoBean", deliveryRepoBean);
			jRdataSource = new JRBeanCollectionDataSource(deliveryRepoBeans, false);
		}
		
		FiscalYear fiscalYear = (FiscalYear) commonService.getAnObjectByAnyUniqueColumn("FiscalYear", "id",
				request.getParameter("fiscalYearId"));
		params.put("datasource", jRdataSource);
		params.put("fiscalYear", "Delivery Report-" + fiscalYear.getFromYear() + "-"
				+ fiscalYear.getToYear().replaceFirst("20", "") + "");

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		// export to xls
		response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "inline; filename=deliveryListReport.xlsx");
		ServletOutputStream outputStream = response.getOutputStream();
		net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.flush();
		outputStream.close();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}
	@SuppressWarnings("null")
	private String dateFormat(String date) throws ParseException {
		String responseDate = "";
		if (date != null && date.length() > 0) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date result = df.parse(date);

			DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
			responseDate = df1.format(result);
		}
		return responseDate;
	}

	private double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		double tmp = Math.floor(value);
		return (double) tmp / factor;
	}

	public String getBdCurrencyFormat(String amount) {
		StringBuilder stringBuilder = new StringBuilder();
		char amountArray[] = amount.toCharArray();
		int a = 0, b = 0;
		for (int i = amountArray.length - 1; i >= 0; i--) {
			if (a < 3) {
				stringBuilder.append(amountArray[i]);
				a++;
			} else if (b < 2) {
				if (b == 0) {
					stringBuilder.append(",");
					stringBuilder.append(amountArray[i]);
					b++;
				} else {
					stringBuilder.append(amountArray[i]);
					b = 0;
				}
			}
		}
		return stringBuilder.reverse().toString();
	}

}
