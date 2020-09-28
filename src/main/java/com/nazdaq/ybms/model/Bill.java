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
@Table(name = "bill")
public class Bill implements Serializable, Comparable<Bill> {

	private static final long serialVersionUID = 8721885972801648681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "bill_id")
	private String billId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "point_id", nullable = true)
	private Point point;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fiscal_year_id", nullable = true)
	private FiscalYear fiscalYear;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "bgt_src_id", nullable = true)
	private BudgetSource budgetSource;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "bgt_code_id", nullable = true)
	private BudgetCode budgetCode;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "goods_type_id", nullable = true)
	private GoodsType goodsType;

	@Column(name = "bill_amount")
	private Double billAmount;

	@Column(name = "bill_date")
	private String billDate;

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

	@Transient
	private Integer pointId;

	@Transient
	private Integer fiscalYearId;

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

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
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

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Integer getFiscalYearId() {
		return fiscalYearId;
	}

	public void setFiscalYearId(Integer fiscalYearId) {
		this.fiscalYearId = fiscalYearId;
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

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	@Override
	public int compareTo(Bill o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
