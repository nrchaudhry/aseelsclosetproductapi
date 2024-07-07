package com.cwiztech.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TBLPRODUCTCATEGORY")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class ProductCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTCATEGORY_ID;
	
	@Column(name = "NETSUITE_ID")
	private Long NETSUITE_ID;
	
	@Column(name = "QUICKBOOK_ID")
	private String QUICKBOOK_ID;

	@OneToOne
	@JoinColumn(name = "PRODUCTCATEGORYPARENT_ID")
	private ProductCategory PRODUCTCATEGORYPARENT_ID;
	
	@Column(name = "PRODUCTCATEGORYORDER_NO")
	private Long  PRODUCTCATEGORYORDER_NO;
	
	@Column(name = "PRODUCTCATEGORY_CODE")
	private String PRODUCTCATEGORY_CODE ;
	
	@Column(name = "PRODUCTCATEGORY_NAME")
	private String PRODUCTCATEGORY_NAME ;
	
	@Column(name = "PRODUCTCATEGORY_DESC")
	private String PRODUCTCATEGORY_DESC ;
	
    @Column(name = "PRODUCTCATEGORYICON_URL")
    private String PRODUCTCATEGORYICON_URL ;
    
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


	public String getQUICKBOOK_ID() {
		return QUICKBOOK_ID;
	}

	public void setQUICKBOOK_ID(String qUICKBOOK_ID) {
		QUICKBOOK_ID = qUICKBOOK_ID;
	}

	public long getPRODUCTCATEGORY_ID() {
		return PRODUCTCATEGORY_ID;
	}

	public void setPRODUCTCATEGORY_ID(long pRODUCTCATEGORY_ID) {
		PRODUCTCATEGORY_ID = pRODUCTCATEGORY_ID;
	}
	
	public Long getNETSUITE_ID() {
		return NETSUITE_ID;
	}

	public void setNETSUITE_ID(Long nETSUITE_ID) {
		NETSUITE_ID = nETSUITE_ID;
	}

	public ProductCategory getPRODUCTCATEGORYPARENT_ID() {
		return PRODUCTCATEGORYPARENT_ID;
	}

	public void setPRODUCTCATEGORYPARENT_ID(ProductCategory pRODUCTCATEGORYPARENT_ID) {
		PRODUCTCATEGORYPARENT_ID = pRODUCTCATEGORYPARENT_ID;
	}

	public Long getPRODUCTCATEGORYORDER_NO() {
		return PRODUCTCATEGORYORDER_NO;
	}

	public void setPRODUCTCATEGORYORDER_NO(Long pRODUCTCATEGORYORDER_NO) {
		PRODUCTCATEGORYORDER_NO = pRODUCTCATEGORYORDER_NO;
	}

	public String getPRODUCTCATEGORY_CODE() {
		return PRODUCTCATEGORY_CODE;
	}

	public void setPRODUCTCATEGORY_CODE(String pRODUCTCATEGORY_CODE) {
		PRODUCTCATEGORY_CODE = pRODUCTCATEGORY_CODE;
	}

	public String getPRODUCTCATEGORY_NAME() {
		return PRODUCTCATEGORY_NAME;
	}

	public void setPRODUCTCATEGORY_NAME(String pRODUCTCATEGORY_NAME) {
		PRODUCTCATEGORY_NAME = pRODUCTCATEGORY_NAME;
	}

	public String getPRODUCTCATEGORY_DESC() {
		return PRODUCTCATEGORY_DESC;
	}

	public void setPRODUCTCATEGORY_DESC(String pRODUCTCATEGORY_DESC) {
		PRODUCTCATEGORY_DESC = pRODUCTCATEGORY_DESC;
	}

	public String getPRODUCTCATEGORYICON_URL() {
        return PRODUCTCATEGORYICON_URL;
    }

    public void setPRODUCTCATEGORYICON_URL(String pRODUCTCATEGORYICON_URL) {
        PRODUCTCATEGORYICON_URL = pRODUCTCATEGORYICON_URL;
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
