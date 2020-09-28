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
@Table(name = "delivered")
public class Delivered implements Serializable, Comparable<Delivered> {

	private static final long serialVersionUID = 8721885972801648681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "delivery_id")
	private String deliveryId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "point_id", nullable = true)
	private Point point;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fiscal_year_id", nullable = true)
	private FiscalYear fiscalYear;

	@Column(name = "dlv_equipments")
	private Double dlvEquipments;

	@Column(name = "dlv_furniture")
	private Double dlvFurniture;

	@Column(name = "dlv_computer")
	private Double dlvComputer;

	@Column(name = "dlv_repair")
	private Double dlvRepair;

	@Column(name = "dlv_reagent")
	private Double dlvReagent;

	@Column(name = "dlv_books")
	private Double dlvBooks;

	@Column(name = "dlv_amount")
	private Double dlvAmount;

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
	private String remarks = null;

	@Transient
	private Integer pointId;

	@Transient
	private Double bgtEquipments = null;

	@Transient
	private Double bgtFurniture = null;

	@Transient
	private Double bgtComputer = null;

	@Transient
	private Double bgtRepair = null;

	@Transient
	private Double bgtReagent = null;

	@Transient
	private Double bgtBooks = null;

	@Transient
	private Double bgtAmount = null;

	@Transient
	private Integer fiscalYearId;

	@Transient
	private Integer recid;

	@Transient
	private Integer slNo;

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

	public FiscalYear getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(FiscalYear fiscalYear) {
		this.fiscalYear = fiscalYear;
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

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Double getBgtAmount() {
		return bgtAmount;
	}

	public void setBgtAmount(Double bgtAmount) {
		this.bgtAmount = bgtAmount;
	}

	public Integer getFiscalYearId() {
		return fiscalYearId;
	}

	public void setFiscalYearId(Integer fiscalYearId) {
		this.fiscalYearId = fiscalYearId;
	}

	public Integer getRecid() {
		return recid;
	}

	public void setRecid(Integer recid) {
		this.recid = recid;
	}

	public Integer getSlNo() {
		return slNo;
	}

	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}

	public Double getDlvEquipments() {
		return dlvEquipments;
	}

	public void setDlvEquipments(Double dlvEquipments) {
		this.dlvEquipments = dlvEquipments;
	}

	public Double getDlvFurniture() {
		return dlvFurniture;
	}

	public void setDlvFurniture(Double dlvFurniture) {
		this.dlvFurniture = dlvFurniture;
	}

	public Double getDlvComputer() {
		return dlvComputer;
	}

	public void setDlvComputer(Double dlvComputer) {
		this.dlvComputer = dlvComputer;
	}

	public Double getDlvRepair() {
		return dlvRepair;
	}

	public void setDlvRepair(Double dlvRepair) {
		this.dlvRepair = dlvRepair;
	}

	public Double getDlvReagent() {
		return dlvReagent;
	}

	public void setDlvReagent(Double dlvReagent) {
		this.dlvReagent = dlvReagent;
	}

	public Double getDlvBooks() {
		return dlvBooks;
	}

	public void setDlvBooks(Double dlvBooks) {
		this.dlvBooks = dlvBooks;
	}

	public Double getBgtEquipments() {
		return bgtEquipments;
	}

	public void setBgtEquipments(Double bgtEquipments) {
		this.bgtEquipments = bgtEquipments;
	}

	public Double getBgtFurniture() {
		return bgtFurniture;
	}

	public void setBgtFurniture(Double bgtFurniture) {
		this.bgtFurniture = bgtFurniture;
	}

	public Double getBgtComputer() {
		return bgtComputer;
	}

	public void setBgtComputer(Double bgtComputer) {
		this.bgtComputer = bgtComputer;
	}

	public Double getBgtRepair() {
		return bgtRepair;
	}

	public void setBgtRepair(Double bgtRepair) {
		this.bgtRepair = bgtRepair;
	}

	public Double getBgtReagent() {
		return bgtReagent;
	}

	public void setBgtReagent(Double bgtReagent) {
		this.bgtReagent = bgtReagent;
	}

	public Double getBgtBooks() {
		return bgtBooks;
	}

	public void setBgtBooks(Double bgtBooks) {
		this.bgtBooks = bgtBooks;
	}

	@Override
	public int compareTo(Delivered o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
