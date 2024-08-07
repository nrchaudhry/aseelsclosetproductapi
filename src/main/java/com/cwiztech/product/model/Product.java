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
@Table(name = "TBLPRODUCT")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCT_ID;
	
	@Column(name = "NETSUITE_ID")
	private Long NETSUITE_ID;
	
	@Column(name = "QUICKBOOK_ID")
	private String QUICKBOOK_ID;
	
	@Column(name = "SAGE_ID")
	private String SAGE_ID;
	
	@Column(name = "PRODUCTCATEGORY_ID")
	private Long PRODUCTCATEGORY_ID;
	
	@Transient
	private String PRODUCTCATEGORY_DETAIL;
	
	@Column(name = "PRODUCT_CODE")
	private String PRODUCT_CODE;
	
	@Column(name = "PRODUCT_NAME")
	private String PRODUCT_NAME;
	
	@Column(name = "PRODUCT_DESC")
	private String PRODUCT_DESC;
	
    @Column(name = "PRODUCTICON_URL")
    private String PRODUCTICON_URL;
    
    @Column(name = "TAXCODE_ID")
    private Long TAXCODE_ID;
    
	@Transient
	private String TAXCODE_DETAIL;
	
    @Column(name = "PURCHASE_PRICE")
    private Double PURCHASE_PRICE;
    
	@Column(name = "PRODUCT_WEIGHT")
	private Double PRODUCT_WEIGHT;
	
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

	public long getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(long pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}

	public Long getNETSUITE_ID() {
		return NETSUITE_ID;
	}

	public void setNETSUITE_ID(Long nETSUITE_ID) {
		NETSUITE_ID = nETSUITE_ID;
	}

	public String getQUICKBOOK_ID() {
		return QUICKBOOK_ID;
	}

	public void setQUICKBOOK_ID(String qUICKBOOK_ID) {
		QUICKBOOK_ID = qUICKBOOK_ID;
	}

	public String getSAGE_ID() {
		return SAGE_ID;
	}

	public void setSAGE_ID(String sAGE_ID) {
		SAGE_ID = sAGE_ID;
	}

	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}

	public void setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
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

	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}

	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}

	public String getPRODUCT_DESC() {
		return PRODUCT_DESC;
	}

	public void setPRODUCT_DESC(String pRODUCT_DESC) {
		PRODUCT_DESC = pRODUCT_DESC;
	}

	public String getPRODUCTICON_URL() {
        return PRODUCTICON_URL;
    }

    public void setPRODUCTICON_URL(String pRODUCTICON_URL) {
        PRODUCTICON_URL = pRODUCTICON_URL;
    }

    public Long getTAXCODE_ID() {
		return TAXCODE_ID;
	}

	public void setTAXCODE_ID(Long tAXCODE_ID) {
		TAXCODE_ID = tAXCODE_ID;
	}

	public String getTAXCODE_DETAIL() {
		return TAXCODE_DETAIL;
	}

	public void setTAXCODE_DETAIL(String tAXCODE_DETAIL) {
		TAXCODE_DETAIL = tAXCODE_DETAIL;
	}

	public Double getPURCHASE_PRICE() {
		return PURCHASE_PRICE;
	}

	public void setPURCHASE_PRICE(Double pURCHASE_PRICE) {
		PURCHASE_PRICE = pURCHASE_PRICE;
	}

	public Double getPRODUCT_WEIGHT() {
		return PRODUCT_WEIGHT;
	}

	public void setPRODUCT_WEIGHT(Double pRODUCT_WEIGHT) {
		PRODUCT_WEIGHT = pRODUCT_WEIGHT;
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
		return (long) 4;
	}

	

}
