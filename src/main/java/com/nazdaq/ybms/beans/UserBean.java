package com.nazdaq.ybms.beans;

import java.sql.Timestamp;

public class UserBean {
	private Integer id;
	private String uName;
	private String pass;
	private String email;
	private String status;
	private String fName;
	private String lName;
	private String desc;
	private Timestamp insertDate;
	private Timestamp updateDate;
	private String mobile;
	private String auth;
	private String value1;
	private Short empId;

	private String insertedBy;

	public Short getEmpId() {
		return empId;
	}

	public void setEmpId(Short empId) {
		this.empId = empId;
	}

	public Timestamp getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}

	public UserBean(Integer id, String uName, String pass, String email, String status, String fName, String lName,
			Timestamp insertDate, String mobile, String auth) {
		super();
		this.id = id;
		this.uName = uName;
		this.pass = pass;
		this.email = email;
		this.status = status;
		this.fName = fName;
		this.lName = lName;
		this.insertDate = insertDate;
		this.mobile = mobile;
		this.auth = auth;
	}

	public UserBean() {

	}

}
