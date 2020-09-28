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
@Table(name = "delivered_history")
public class DeliveredHistory implements Serializable, Comparable<DeliveredHistory> {
	private static final long serialVersionUID = 8721885972801648681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "delivery_id")
	private String deliveryId;

	@Column(name = "team_leader")
	private String teamLeader;

	@Column(name = "point_type")
	private String pointType;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "point_id", nullable = true)
	private Point point;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fiscal_year_id", nullable = true)
	private FiscalYear fiscalYear;

	@Column(name = "dlv_amount")
	private Double dlvAmount;

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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public DeliveredHistory() {

	}

	public DeliveredHistory(Integer id, String deliveryId, String teamLeader, String pointType, Point point,
			FiscalYear fiscalYear, Double dlvAmount, Double dlvEquipments, Double dlvFurniture, Double dlvComputer,
			Double dlvRepair, Double dlvReagent, Double dlvBooks, String createdBy, Date createdDate, String remarks) {
		super();
		this.id = id;
		this.deliveryId = deliveryId;
		this.teamLeader = teamLeader;
		this.pointType = pointType;
		this.point = point;
		this.fiscalYear = fiscalYear;
		this.dlvAmount = dlvAmount;
		this.dlvEquipments = dlvEquipments;
		this.dlvFurniture = dlvFurniture;
		this.dlvComputer = dlvComputer;
		this.dlvRepair = dlvRepair;
		this.dlvReagent = dlvReagent;
		this.dlvBooks = dlvBooks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.remarks = remarks;
	}

	@Override
	public int compareTo(DeliveredHistory o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
