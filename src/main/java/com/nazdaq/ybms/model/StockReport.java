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
@Table(name = "stock_report")
public class StockReport implements Serializable, Comparable<StockReport> {

	private static final long serialVersionUID = 8721885972801648681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fiscal_year_id", nullable = true)
	private FiscalYear fiscalYear;

	@Column(name = "warehouse")
	private Double warehouse = 0.0;

	@Column(name = "local_currency")
	private Double localCurrency = 0.0;

	@Column(name = "work_order")
	private Double workOrder = 0.0;

	@Column(name = "dlv_at_point")
	private Double dlvAtPoint = 0.0;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FiscalYear getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(FiscalYear fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public Double getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Double warehouse) {
		this.warehouse = warehouse;
	}

	public Double getLocalCurrency() {
		return localCurrency;
	}

	public void setLocalCurrency(Double localCurrency) {
		this.localCurrency = localCurrency;
	}

	public Double getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(Double workOrder) {
		this.workOrder = workOrder;
	}

	public Double getDlvAtPoint() {
		return dlvAtPoint;
	}

	public void setDlvAtPoint(Double dlvAtPoint) {
		this.dlvAtPoint = dlvAtPoint;
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

	@Override
	public int compareTo(StockReport o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
