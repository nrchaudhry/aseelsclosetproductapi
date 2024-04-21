package com.cwiztech.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLPRODUCTITEMMOVEMENT")
public class ProductItemMovement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTITEMMOVEMENT_ID;
	
	@Column(name = "PRODUCTITEMMOVEMENT_DATE")
	private String PRODUCTITEMMOVEMENT_DATE;
	
	@Column(name = "PRODUCT_ID")
	private Long PRODUCT_ID;
	
	@Transient
	private String PRODUCT_DETAIL;
	
	@Column(name = "PRODUCTITEM_QUANTITY")
	private Long PRODUCTITEM_QUANTITY;
	
	@Column(name = "PRODUCTITEMMOVEMENT_QUANTITY")
	private Long PRODUCTITEMMOVEMENT_QUANTITY;
	
	@Column(name = "EMPLOYEE_ID")
	private Long EMPLOYEE_ID;
	
	@Transient
	private String EMPLOYEE_DETAIL;
	
	@Column(name = "ASSIGNTOEMPLOYEE_DATETIME")
	private String ASSIGNTOEMPLOYEE_DATETIME;
	
	@Column(name = "ISPRODUCTITEMMOVED")
	private String  ISPRODUCTITEMMOVED;
	
	@Column(name = "PRODUCTITEMMOVEMENT_DATETIME")
	private String  PRODUCTITEMMOVEMENT_DATETIME;

	@Column(name = "ISACTIVE")
	private String ISACTIVE;

	@JsonIgnore
	@Column(name = "MODIFIED_BY")
	private Long MODIFIED_BY;

	@JsonIgnore 
	@Column(name = "MODIFIED_WHEN")
	private String MODIFIED_WHEN;

	@JsonIgnore 
	@Column(name = "MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;

	public long getPRODUCTITEMMOVEMENT_ID() {
		return PRODUCTITEMMOVEMENT_ID;
	}

	public void setPRODUCTITEMMOVEMENT_ID(long pRODUCTITEMMOVEMENT_ID) {
		PRODUCTITEMMOVEMENT_ID = pRODUCTITEMMOVEMENT_ID;
	}

	public String getPRODUCTITEMMOVEMENT_DATE() {
		return PRODUCTITEMMOVEMENT_DATE;
	}

	public void setPRODUCTITEMMOVEMENT_DATE(String pRODUCTITEMMOVEMENT_DATE) {
		PRODUCTITEMMOVEMENT_DATE = pRODUCTITEMMOVEMENT_DATE;
	}

	public Long getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(Long pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}

	public String getPRODUCT_DETAIL() {
		return PRODUCT_DETAIL;
	}

	public void setPRODUCT_DETAIL(String pRODUCT_DETAIL) {
		PRODUCT_DETAIL = pRODUCT_DETAIL;
	}

	public Long getPRODUCTITEMMOVEMENT_QUANTITY() {
		return PRODUCTITEMMOVEMENT_QUANTITY;
	}

	public void setPRODUCTITEMMOVEMENT_QUANTITY(Long pRODUCTITEMMOVEMENT_QUANTITY) {
		PRODUCTITEMMOVEMENT_QUANTITY = pRODUCTITEMMOVEMENT_QUANTITY;
	}

	public Long getEMPLOYEE_ID() {
		return EMPLOYEE_ID;
	}

	public void setEMPLOYEE_ID(Long eMPLOYEE_ID) {
		EMPLOYEE_ID = eMPLOYEE_ID;
	}

	public String getEMPLOYEE_DETAIL() {
		return EMPLOYEE_DETAIL;
	}

	public void setEMPLOYEE_DETAIL(String eMPLOYEE_DETAIL) {
		EMPLOYEE_DETAIL = eMPLOYEE_DETAIL;
	}

	public String getASSIGNTOEMPLOYEE_DATETIME() {
		return ASSIGNTOEMPLOYEE_DATETIME;
	}
	public void setASSIGNTOEMPLOYEE_DATETIME(String aSSIGNTOEMPLOYEE_DATETIME) {
		ASSIGNTOEMPLOYEE_DATETIME = aSSIGNTOEMPLOYEE_DATETIME;
	}

	public String getISPRODUCTITEMMOVED() {
		return ISPRODUCTITEMMOVED;
	}

	public void setISPRODUCTITEMMOVED(String iSPRODUCTITEMMOVED) {
		ISPRODUCTITEMMOVED = iSPRODUCTITEMMOVED;
	}

	public String getPRODUCTITEMMOVEMENT_DATETIME() {
		return PRODUCTITEMMOVEMENT_DATETIME;
	}

	public void setPRODUCTITEMMOVEMENT_DATETIME(String pRODUCTITEMMOVEMENT_DATETIME) {
		PRODUCTITEMMOVEMENT_DATETIME = pRODUCTITEMMOVEMENT_DATETIME;
	}

	public Long getPRODUCTITEM_QUANTITY() {
		return PRODUCTITEM_QUANTITY;
	}

	public void setPRODUCTITEM_QUANTITY(Long pRODUCTITEM_QUANTITY) {
		PRODUCTITEM_QUANTITY = pRODUCTITEM_QUANTITY;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	@JsonIgnore
	public Long getMODIFIED_BY() {
		return MODIFIED_BY;
	}

	public void setMODIFIED_BY(Long mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}

	@JsonIgnore
	public String getMODIFIED_WHEN() {
		return MODIFIED_WHEN;
	}

	public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
		MODIFIED_WHEN = mODIFIED_WHEN;
	}

	@JsonIgnore
	public String getMODIFIED_WORKSTATION() {
		return MODIFIED_WORKSTATION;
	}

	public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
		MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
	}

	public static long getDatabaseTableID() {
		return (long) 3;
	}

}
