package com.nazdaq.ybms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "budget_history")
public class BudgetHistory implements Serializable, Comparable<BudgetHistory> {
	private static final long serialVersionUID = 8721885972801648681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "budget_id")
	private String budgetId;

	@ManyToOne
	@JoinColumn(name = "point_id", nullable = true)
	private Point point;

	@ManyToOne
	@JoinColumn(name = "fiscal_year_id", nullable = true)
	private FiscalYear fiscalYear;

	@Column(name = "budget_source")
	private String budgetSource;

	@Column(name = "budget_code")
	private String budgetCode;

	@ManyToOne
	@JoinColumn(name = "color_code_id", nullable = true)
	private ColorCode colorCode;

	@Column(name = "goods_type")
	private String goodsType;

	@Column(name = "point_type")
	private String pointType;

	@Column(name = "team_leader")
	private String teamLeader;

	@Column(name = "bgt_amount")
	private Double bgtAmount;

	@Column(name = "bill_amount")
	private Double billAmount = 0.0;

	// common
	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	@Type(type = "timestamp")
	private Date createdDate;

	@Column(name = "remarks")
	private String remarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public FiscalYear getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(FiscalYear fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public String getBudgetSource() {
		return budgetSource;
	}

	public void setBudgetSource(String budgetSource) {
		this.budgetSource = budgetSource;
	}

	public String getBudgetCode() {
		return budgetCode;
	}

	public void setBudgetCode(String budgetCode) {
		this.budgetCode = budgetCode;
	}

	public ColorCode getColorCode() {
		return colorCode;
	}

	public void setColorCode(ColorCode colorCode) {
		this.colorCode = colorCode;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public String getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	public Double getBgtAmount() {
		return bgtAmount;
	}

	public void setBgtAmount(Double bgtAmount) {
		this.bgtAmount = bgtAmount;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BudgetHistory() {

	}

	public BudgetHistory(Integer id, String budgetId, Point point, FiscalYear fiscalYear, String budgetSource,
			String budgetCode, ColorCode colorCode, String goodsType, String pointType, String teamLeader,
			Double bgtAmount, Double billAmount, String createdBy, Date createdDate, String remarks) {
		super();
		this.id = id;
		this.budgetId = budgetId;
		this.point = point;
		this.fiscalYear = fiscalYear;
		this.budgetSource = budgetSource;
		this.budgetCode = budgetCode;
		this.colorCode = colorCode;
		this.goodsType = goodsType;
		this.pointType = pointType;
		this.teamLeader = teamLeader;
		this.bgtAmount = bgtAmount;
		this.billAmount = billAmount;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.remarks = remarks;
	}

	@Override
	public int compareTo(BudgetHistory o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
