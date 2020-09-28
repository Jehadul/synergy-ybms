package com.nazdaq.ybms.beans;

public class DeliveryRepoBean {
private String deliveryId;
private String pointName;
private Double bgtEquipment;
private Double dlvEquipment;
private Double bgtFurniture;
private Double dlvFurniture;
private Double bgtComputer;
private Double dlvComputer;
private Double bgtRepair;
private Double dlvRepair;
private Double bgtReagent;
private Double dlvReagent;
private Double bgtBook;
private Double dlvBook;
private Double bgtAmount;
private Double dlvAmount;
private String remarks;

public DeliveryRepoBean() {
	super();
}
public DeliveryRepoBean(String deliveryId, String pointName, Double bgtEquipment, Double dlvEquipment, Double bgtFurniture,
		Double dlvFurniture, Double bgtComputer, Double dlvComputer, Double bgtRepair, Double dlvRepair,
		Double bgtReagent, Double dlvReagent, Double bgtBook, Double dlvBook, Double bgtAmount, Double dlvAmount,
		String remarks) {
	super();
	this.deliveryId = deliveryId;
	this.pointName = pointName;
	this.bgtEquipment = bgtEquipment;
	this.dlvEquipment = dlvEquipment;
	this.bgtFurniture = bgtFurniture;
	this.dlvFurniture = dlvFurniture;
	this.bgtComputer = bgtComputer;
	this.dlvComputer = dlvComputer;
	this.bgtRepair = bgtRepair;
	this.dlvRepair = dlvRepair;
	this.bgtReagent = bgtReagent;
	this.dlvReagent = dlvReagent;
	this.bgtBook = bgtBook;
	this.dlvBook = dlvBook;
	this.bgtAmount = bgtAmount;
	this.dlvAmount = dlvAmount;
	this.remarks = remarks;
}
public String getDeliveryId() {
	return deliveryId;
}
public void setDeliveryId(String deliveryId) {
	this.deliveryId = deliveryId;
}
public String getPointName() {
	return pointName;
}
public void setPointName(String pointName) {
	this.pointName = pointName;
}
public Double getBgtEquipment() {
	return bgtEquipment;
}
public void setBgtEquipment(Double bgtEquipment) {
	this.bgtEquipment = bgtEquipment;
}
public Double getDlvEquipment() {
	return dlvEquipment;
}
public void setDlvEquipment(Double dlvEquipment) {
	this.dlvEquipment = dlvEquipment;
}
public Double getBgtFurniture() {
	return bgtFurniture;
}
public void setBgtFurniture(Double bgtFurniture) {
	this.bgtFurniture = bgtFurniture;
}
public Double getDlvFurniture() {
	return dlvFurniture;
}
public void setDlvFurniture(Double dlvFurniture) {
	this.dlvFurniture = dlvFurniture;
}
public Double getBgtComputer() {
	return bgtComputer;
}
public void setBgtComputer(Double bgtComputer) {
	this.bgtComputer = bgtComputer;
}
public Double getDlvComputer() {
	return dlvComputer;
}
public void setDlvComputer(Double dlvComputer) {
	this.dlvComputer = dlvComputer;
}
public Double getBgtRepair() {
	return bgtRepair;
}
public void setBgtRepair(Double bgtRepair) {
	this.bgtRepair = bgtRepair;
}
public Double getDlvRepair() {
	return dlvRepair;
}
public void setDlvRepair(Double dlvRepair) {
	this.dlvRepair = dlvRepair;
}
public Double getBgtReagent() {
	return bgtReagent;
}
public void setBgtReagent(Double bgtReagent) {
	this.bgtReagent = bgtReagent;
}
public Double getDlvReagent() {
	return dlvReagent;
}
public void setDlvReagent(Double dlvReagent) {
	this.dlvReagent = dlvReagent;
}
public Double getBgtBook() {
	return bgtBook;
}
public void setBgtBook(Double bgtBook) {
	this.bgtBook = bgtBook;
}
public Double getDlvBook() {
	return dlvBook;
}
public void setDlvBook(Double dlvBook) {
	this.dlvBook = dlvBook;
}
public Double getBgtAmount() {
	return bgtAmount;
}
public void setBgtAmount(Double bgtAmount) {
	this.bgtAmount = bgtAmount;
}
public Double getDlvAmount() {
	return dlvAmount;
}
public void setDlvAmount(Double dlvAmount) {
	this.dlvAmount = dlvAmount;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}

}
