package com.nazdaq.ybms.beans;

public class ColoBeanB {
	private String pointName;
	private String budgetCode;
	private Double budgetAmount;
	private Double billAmount;
	private String pointType;
	private String fiscalYear;
	private String teamLeaderName;
	private String colorCode;
	public ColoBeanB(String pointName, String budgetCode, Double budgetAmount, Double billAmount, String pointType,
			String fiscalYear, String teamLeaderName, String colorCode) {
		super();
		this.pointName = pointName;
		this.budgetCode = budgetCode;
		this.budgetAmount = budgetAmount;
		this.billAmount = billAmount;
		this.pointType = pointType;
		this.fiscalYear = fiscalYear;
		this.teamLeaderName = teamLeaderName;
		this.colorCode = colorCode;
	}
	public ColoBeanB() {
		super();
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
	public Double getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(Double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	public Double getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getTeamLeaderName() {
		return teamLeaderName;
	}
	public void setTeamLeaderName(String teamLeaderName) {
		this.teamLeaderName = teamLeaderName;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	
}
