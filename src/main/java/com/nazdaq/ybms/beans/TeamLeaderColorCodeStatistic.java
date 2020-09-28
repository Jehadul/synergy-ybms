package com.nazdaq.ybms.beans;

public class TeamLeaderColorCodeStatistic {
	private Integer teamLeaderId;
	private String teamLeadername;
	private Double totalGreenAmount;
	private Double totalBlueAmount;
	private Double totalRedAmount;
	private Integer slNO;
	
	
	public TeamLeaderColorCodeStatistic() {
		super();
	}

	public TeamLeaderColorCodeStatistic(Integer slNo,Integer teamLeaderId, String teamLeadername, Double totalGreenAmount,
			Double totalBlueAmount, Double totalRedAmount) {
		super();
		this.slNO=slNo;
		this.teamLeaderId = teamLeaderId;
		this.teamLeadername = teamLeadername;
		this.totalGreenAmount = totalGreenAmount;
		this.totalBlueAmount = totalBlueAmount;
		this.totalRedAmount = totalRedAmount;
		
	}

	public Integer getSlNO() {
		return slNO;
	}

	public void setSlNO(Integer slNO) {
		this.slNO = slNO;
	}

	public Integer getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(Integer teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}

	public String getTeamLeadername() {
		return teamLeadername;
	}

	public void setTeamLeadername(String teamLeadername) {
		this.teamLeadername = teamLeadername;
	}

	public Double getTotalGreenAmount() {
		return totalGreenAmount;
	}

	public void setTotalGreenAmount(Double totalGreenAmount) {
		this.totalGreenAmount = totalGreenAmount;
	}

	public Double getTotalBlueAmount() {
		return totalBlueAmount;
	}

	public void setTotalBlueAmount(Double totalBlueAmount) {
		this.totalBlueAmount = totalBlueAmount;
	}

	public Double getTotalRedAmount() {
		return totalRedAmount;
	}

	public void setTotalRedAmount(Double totalRedAmount) {
		this.totalRedAmount = totalRedAmount;
	}

	
	
}
