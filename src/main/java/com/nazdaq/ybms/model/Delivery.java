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
import javax.persistence.Transient;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "delivery")
public class Delivery implements Serializable, Comparable<Delivery> {

	private static final long serialVersionUID = 8721885972801648681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "delivery_by")
	private String deliveryId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "point_id", nullable = true)
	private Point point;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fiscal_year_bgt_id", nullable = true)
	private FiscalYear fiscalYearBgt;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fiscal_year_dlv_id", nullable = true)
	private FiscalYear fiscalYearDlv;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "bgt_src_id", nullable = true)
	private BudgetSource budgetSource;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "bgt_code_id", nullable = true)
	private BudgetCode budgetCode;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "goods_type_id", nullable = true)
	private GoodsType goodsType;

	@Column(name = "dlv_amount")
	private Double dlvAmount;

	@Column(name = "dlv_date")
	private String dlvDate;

	// common
	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	@Type(type = "timestamp")
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "is_against_budget")
	private String againstBudget;

	@Column(name = "adjust_budget")
	private String adjustBudget;

	@Transient
	private Integer pointId;

	@Transient
	private Integer fiscalYearDlvId;

	@Transient
	private Integer fiscalYearBgtId;

	@Transient
	private Integer budgetSourceId;

	@Transient
	private Integer budgetCodeId;

	@Transient
	private Integer goodsTypeId;

	@Transient
	private String goToList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public FiscalYear getFiscalYearBgt() {
		return fiscalYearBgt;
	}

	public void setFiscalYearBgt(FiscalYear fiscalYearBgt) {
		this.fiscalYearBgt = fiscalYearBgt;
	}

	public FiscalYear getFiscalYearDlv() {
		return fiscalYearDlv;
	}

	public void setFiscalYearDlv(FiscalYear fiscalYearDlv) {
		this.fiscalYearDlv = fiscalYearDlv;
	}

	public BudgetSource getBudgetSource() {
		return budgetSource;
	}

	public void setBudgetSource(BudgetSource budgetSource) {
		this.budgetSource = budgetSource;
	}

	public BudgetCode getBudgetCode() {
		return budgetCode;
	}

	public void setBudgetCode(BudgetCode budgetCode) {
		this.budgetCode = budgetCode;
	}

	public Double getDlvAmount() {
		return dlvAmount;
	}

	public void setDlvAmount(Double dlvAmount) {
		this.dlvAmount = dlvAmount;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAgainstBudget() {
		return againstBudget;
	}

	public void setAgainstBudget(String againstBudget) {
		this.againstBudget = againstBudget;
	}

	public String getAdjustBudget() {
		return adjustBudget;
	}

	public void setAdjustBudget(String adjustBudget) {
		this.adjustBudget = adjustBudget;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Integer getFiscalYearDlvId() {
		return fiscalYearDlvId;
	}

	public void setFiscalYearDlvId(Integer fiscalYearDlvId) {
		this.fiscalYearDlvId = fiscalYearDlvId;
	}

	public Integer getFiscalYearBgtId() {
		return fiscalYearBgtId;
	}

	public void setFiscalYearBgtId(Integer fiscalYearBgtId) {
		this.fiscalYearBgtId = fiscalYearBgtId;
	}

	public Integer getBudgetSourceId() {
		return budgetSourceId;
	}

	public void setBudgetSourceId(Integer budgetSourceId) {
		this.budgetSourceId = budgetSourceId;
	}

	public Integer getBudgetCodeId() {
		return budgetCodeId;
	}

	public void setBudgetCodeId(Integer budgetCodeId) {
		this.budgetCodeId = budgetCodeId;
	}

	public String getGoToList() {
		return goToList;
	}

	public void setGoToList(String goToList) {
		this.goToList = goToList;
	}

	public GoodsType getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}

	public Integer getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(Integer goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getDlvDate() {
		return dlvDate;
	}

	public void setDlvDate(String dlvDate) {
		this.dlvDate = dlvDate;
	}

	@Override
	public int compareTo(Delivery o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
