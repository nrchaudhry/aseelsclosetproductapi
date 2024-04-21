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
@Table(name = "TBLPRODUCTATTRIBUTE")
public class ProductAttribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTATTRIBUTE_ID;
	
	@Column(name = "ATTRIBUTE_ID")
	private Long ATTRIBUTE_ID;
	
	@Transient
	private String ATTRIBUTE_DETAIL;

	@Column(name = "ATTRIBUTEORDER_NO")
	private Long ATTRIBUTEORDER_NO;
	
	@Column(name = "ATTRIBUTECATEGORY_ID")
	private Long ATTRIBUTECATEGORY_ID;
	
	@Transient
	private String ATTRIBUTECATEGORY_DETAIL;
	
	@Column(name = "PRODUCTCATEGORY_ID")
	private Long PRODUCTCATEGORY_ID;
	
	@Transient
	private String PRODUCTCATEGORY_DETAIL;
	
	@Column(name = "PRODUCT_ID")
	private Long PRODUCT_ID;
	
	@Transient
	private String PRODUCT_DETAIL;
	
	@Column(name = "ISREQUIRED")
	private String ISREQUIRED;
	
	@Column(name = "SHOWINLIST")
	private String SHOWINLIST;
	
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

	public long getPRODUCTATTRIBUTE_ID() {
		return PRODUCTATTRIBUTE_ID;
	}

	public void setPRODUCTATTRIBUTE_ID(long pRODUCTATTRIBUTE_ID) {
		PRODUCTATTRIBUTE_ID = pRODUCTATTRIBUTE_ID;
	}

	public Long getATTRIBUTE_ID() {
		return ATTRIBUTE_ID;
	}

	public void setATTRIBUTE_ID(Long aTTRIBUTE_ID) {
		ATTRIBUTE_ID = aTTRIBUTE_ID;
	}

	public String getATTRIBUTE_DETAIL() {
		return ATTRIBUTE_DETAIL;
	}

	public void setATTRIBUTE_DETAIL(String aTTRIBUTE_DETAIL) {
		ATTRIBUTE_DETAIL = aTTRIBUTE_DETAIL;
	}

	public Long getATTRIBUTEORDER_NO() {
		return ATTRIBUTEORDER_NO;
	}

	public void setATTRIBUTEORDER_NO(Long aTTRIBUTEORDER_NO) {
		ATTRIBUTEORDER_NO = aTTRIBUTEORDER_NO;
	}

	public Long getATTRIBUTECATEGORY_ID() {
		return ATTRIBUTECATEGORY_ID;
	}

	public void setATTRIBUTECATEGORY_ID(Long aTTRIBUTECATEGORY_ID) {
		ATTRIBUTECATEGORY_ID = aTTRIBUTECATEGORY_ID;
	}

	public String getATTRIBUTECATEGORY_DETAIL() {
		return ATTRIBUTECATEGORY_DETAIL;
	}

	public void setATTRIBUTECATEGORY_DETAIL(String aTTRIBUTECATEGORY_DETAIL) {
		ATTRIBUTECATEGORY_DETAIL = aTTRIBUTECATEGORY_DETAIL;
	}

	public Long getPRODUCTCATEGORY_ID() {
		return PRODUCTCATEGORY_ID;
	}

	public void setPRODUCTCATEGORY_ID(Long pRODUCTCATEGORY_ID) {
		PRODUCTCATEGORY_ID = pRODUCTCATEGORY_ID;
	}

	public String getPRODUCTCATEGORY_DETAIL() {
		return PRODUCTCATEGORY_DETAIL;
	}

	public void setPRODUCTCATEGORY_DETAIL(String pRODUCTCATEGORY_DETAIL) {
		PRODUCTCATEGORY_DETAIL = pRODUCTCATEGORY_DETAIL;
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

	public String getISREQUIRED() {
		return ISREQUIRED;
	}

	public void setISREQUIRED(String iSREQUIRED) {
		ISREQUIRED = iSREQUIRED;
	}

	public String getSHOWINLIST() {
		return SHOWINLIST;
	}

	public void setSHOWINLIST(String sHOWINLIST) {
		SHOWINLIST = sHOWINLIST;
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
		return (long) 6;
	}

	
	
	
}
