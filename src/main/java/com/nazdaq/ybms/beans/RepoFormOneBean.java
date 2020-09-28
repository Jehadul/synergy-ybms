package com.nazdaq.ybms.beans;

public class RepoFormOneBean {
	private Integer id;
	private String budgetSource;
	private String fromYear;
	private String toYear;
	private String pointName;
	private String budgetCode;
	private String budgetAmount;
	private String billAmount;
	private String billPendingAmount;
	private String teamLeaderName;
	private String fiscalYear;
	private String slNO;

	

	public RepoFormOneBean() {
	}

	public RepoFormOneBean(String budgetSource, String fromYear, String toYear, String pointName, String budgetCode,
			String budgetAmount, String billAmount, String billPendingAmount, String teamLeaderName,
			String fiscalYear) {
		super();
		this.budgetSource = budgetSource;
		this.fromYear = fromYear;
		this.toYear = toYear;
		this.pointName = pointName;
		this.budgetCode = budgetCode;
		this.budgetAmount = budgetAmount;
		this.billAmount = billAmount;
		this.billPendingAmount = billPendingAmount;
		this.teamLeaderName = teamLeaderName;
		this.fiscalYear = fiscalYear;
	}
	public String getSlNO() {
		return slNO;
	}

	public void setSlNO(String slNO) {
		this.slNO = slNO;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBudgetSource() {
		return budgetSource;
	}

	public void setBudgetSource(String budgetSource) {
		this.budgetSource = budgetSource;
	}

	public String getFromYear() {
		return fromYear;
	}

	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getBudgetCode() {
		return budgetCode;
	}

	public void setBudgetCode(String budgetCode) {
		this.budgetCode = budgetCode;
	}

	public String getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(String budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public String getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}

	public String getBillPendingAmount() {
		return billPendingAmount;
	}

	public void setBillPendingAmount(String billPendingAmount) {
		this.billPendingAmount = billPendingAmount;
	}

	public String getTeamLeaderName() {
		return teamLeaderName;
	}

	public void setTeamLeaderName(String teamLeaderName) {
		this.teamLeaderName = teamLeaderName;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

}
