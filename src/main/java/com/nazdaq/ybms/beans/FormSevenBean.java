package com.nazdaq.ybms.beans;

public class FormSevenBean {
	private Integer id;
	private String pointName;
	private String pointType;
	private String budgetAmount;
	private String deliveryAmount;
	private String teamLeaderName;
	private String slNO;

	public FormSevenBean(Integer id, String pointName, String pointType, String budgetAmount, String deliveryAmount,
			String teamLeaderName, String slNO) {
		super();
		this.id = id;
		this.pointName = pointName;
		this.pointType = pointType;
		this.budgetAmount = budgetAmount;
		this.deliveryAmount = deliveryAmount;
		this.teamLeaderName = teamLeaderName;
		this.slNO = slNO;
	}

	public FormSevenBean() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public String getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(String budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public String getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(String deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public String getTeamLeaderName() {
		return teamLeaderName;
	}

	public void setTeamLeaderName(String teamLeaderName) {
		this.teamLeaderName = teamLeaderName;
	}

	public String getSlNO() {
		return slNO;
	}

	public void setSlNO(String slNO) {
		this.slNO = slNO;
	}
	
}
