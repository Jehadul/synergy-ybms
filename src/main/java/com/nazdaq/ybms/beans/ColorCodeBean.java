package com.nazdaq.ybms.beans;

public class ColorCodeBean {
private Integer colorCodeId;
private Integer teamLeaderId;
private String colorName;
private String teamLeaderName;
private Double amount;

public ColorCodeBean() {
	super();
}


public ColorCodeBean(Integer colorCodeId, Integer teamLeaderId, String colorName, String teamLeaderName,
		Double amount) {
	super();
	this.colorCodeId = colorCodeId;
	this.teamLeaderId = teamLeaderId;
	this.colorName = colorName;
	this.teamLeaderName = teamLeaderName;
	this.amount = amount;
}


public Integer getColorCodeId() {
	return colorCodeId;
}
public void setColorCodeId(Integer colorCodeId) {
	this.colorCodeId = colorCodeId;
}

public Integer getTeamLeaderId() {
	return teamLeaderId;
}


public void setTeamLeaderId(Integer teamLeaderId) {
	this.teamLeaderId = teamLeaderId;
}


public String getColorName() {
	return colorName;
}
public void setColorName(String colorName) {
	this.colorName = colorName;
}
public String getTeamLeaderName() {
	return teamLeaderName;
}
public void setTeamLeaderName(String teamLeaderName) {
	this.teamLeaderName = teamLeaderName;
}
public Double getAmount() {
	return amount;
}
public void setAmount(Double amount) {
	this.amount = amount;
}

}
