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
@Table(name = "TBLPRODUCTITEM")
public class ProductItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTITEM_ID;

	@Column(name = "PRODUCT_ID")
	private Long PRODUCT_ID ;

	@Transient
	private String PRODUCT_DETAIL;

	@Column(name = "APPLICATION_ID")
	private Long APPLICATION_ID ;

	@Transient
	private String APPLICATION_DETAIL;

	@Column(name = "PRODUCTITEM_NAME")
	private String PRODUCTITEM_NAME ;

	@Column(name = "PRODUCTITEM_DESC")
	private String  PRODUCTITEM_DESC;

	@Column(name = "PRODUCTITEMIMAGE_URL")
	private String  PRODUCTITEMIMAGE_URL;

	@Column(name = "PRODUCTITEMICON_URL")
	private String  PRODUCTITEMICON_URL;

	@Column(name = "DEACTIVE_AUTO")
	private String DEACTIVE_AUTO;

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

	public long getPRODUCTITEM_ID() {
		return PRODUCTITEM_ID;
	}

	public void setPRODUCTITEM_ID(long pRODUCTITEM_ID) {
		PRODUCTITEM_ID = pRODUCTITEM_ID;
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

	public Long getAPPLICATION_ID() {
		return APPLICATION_ID;
	}

	public void setAPPLICATION_ID(Long aPPLICATION_ID) {
		APPLICATION_ID = aPPLICATION_ID;
	}

	public String getAPPLICATION_DETAIL() {
		return APPLICATION_DETAIL;
	}

	public void setAPPLICATION_DETAIL(String aPPLICATION_DETAIL) {
		APPLICATION_DETAIL = aPPLICATION_DETAIL;
	}

	public String getPRODUCTITEM_NAME() {
		return PRODUCTITEM_NAME;
	}

	public void setPRODUCTITEM_NAME(String pRODUCTITEM_NAME) {
		PRODUCTITEM_NAME = pRODUCTITEM_NAME;
	}

	public String getPRODUCTITEM_DESC() {
		return PRODUCTITEM_DESC;
	}

	public void setPRODUCTITEM_DESC(String pRODUCTITEM_DESC) {
		PRODUCTITEM_DESC = pRODUCTITEM_DESC;
	}

	public String getPRODUCTITEMIMAGE_URL() {
		return PRODUCTITEMIMAGE_URL;
	}

	public void setPRODUCTITEMIMAGE_URL(String pRODUCTITEMIMAGE_URL) {
		PRODUCTITEMIMAGE_URL = pRODUCTITEMIMAGE_URL;
	}

	public String getPRODUCTITEMICON_URL() {
		return PRODUCTITEMICON_URL;
	}

	public void setPRODUCTITEMICON_URL(String pRODUCTITEMICON_URL) {
		PRODUCTITEMICON_URL = pRODUCTITEMICON_URL;
	}

	public String getDEACTIVE_AUTO() {
		return DEACTIVE_AUTO;
	}

	public void setDEACTIVE_AUTO(String dEACTIVE_AUTO) {
		DEACTIVE_AUTO = dEACTIVE_AUTO;
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
		return (long) 7;
	}


}
