package com.nazdaq.ybms.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "color_code")
public class ColorCode implements Serializable, Comparable<ColorCode> {

	private static final long serialVersionUID = 8721885972801648681L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "hexa_code")
	private String hexaCode;
	
	@Column(name = "remarks")
	private String remarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public String getHexaCode() {
		return hexaCode;
	}

	public void setHexaCode(String hexaCode) {
		this.hexaCode = hexaCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public int compareTo(ColorCode o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
