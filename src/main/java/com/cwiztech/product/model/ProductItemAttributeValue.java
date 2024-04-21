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
@Table(name = "TBLPRODUCTITEMATTRIBUTEVALUE")

public class ProductItemAttributeValue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTITEMATTRIBUTEVALUE_ID;
	
	@Column(name = "PRODUCTITEM_ID")
	private Long PRODUCTITEM_ID;
	
	@Transient
	private String PRODUCTITEM_DETAIL;
	
	@Column(name = "PRODUCTATTRIBUTE_ID")
	private Long PRODUCTATTRIBUTE_ID;
	
	@Transient
	private String PRODUCTATTRIBUTE_DETAIL;
	
	@Column(name = "PRODUCTATTRIBUTEVALUE_ID")
	private Long PRODUCTATTRIBUTEVALUE_ID;
	
	@Transient
	private String PRODUCTATTRIBUTEVALUE_DETAIL;

	@Column(name = "PRODUCTATTRIBUTEITEM_VALUE")
	private String  PRODUCTATTRIBUTEITEM_VALUE;
	
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
	
	public long getPRODUCTITEMATTRIBUTEVALUE_ID() {
		return PRODUCTITEMATTRIBUTEVALUE_ID;
	}

	public void setPRODUCTITEMATTRIBUTEVALUE_ID(long pRODUCTITEMATTRIBUTEVALUE_ID) {
		PRODUCTITEMATTRIBUTEVALUE_ID = pRODUCTITEMATTRIBUTEVALUE_ID;
	}

	public Long getPRODUCTITEM_ID() {
		return PRODUCTITEM_ID;
	}

	public void setPRODUCTITEM_ID(Long pRODUCTITEM_ID) {
		PRODUCTITEM_ID = pRODUCTITEM_ID;
	}

	public String getPRODUCTITEM_DETAIL() {
		return PRODUCTITEM_DETAIL;
	}

	public void setPRODUCTITEM_DETAIL(String pRODUCTITEM_DETAIL) {
		PRODUCTITEM_DETAIL = pRODUCTITEM_DETAIL;
	}

	public Long getPRODUCTATTRIBUTE_ID() {
		return PRODUCTATTRIBUTE_ID;
	}

	public void setPRODUCTATTRIBUTE_ID(Long pRODUCTATTRIBUTE_ID) {
		PRODUCTATTRIBUTE_ID = pRODUCTATTRIBUTE_ID;
	}

	public String getPRODUCTATTRIBUTE_DETAIL() {
		return PRODUCTATTRIBUTE_DETAIL;
	}

	public void setPRODUCTATTRIBUTE_DETAIL(String pRODUCTATTRIBUTE_DETAIL) {
		PRODUCTATTRIBUTE_DETAIL = pRODUCTATTRIBUTE_DETAIL;
	}

	public Long getPRODUCTATTRIBUTEVALUE_ID() {
		return PRODUCTATTRIBUTEVALUE_ID;
	}

	public void setPRODUCTATTRIBUTEVALUE_ID(Long pRODUCTATTRIBUTEVALUE_ID) {
		PRODUCTATTRIBUTEVALUE_ID = pRODUCTATTRIBUTEVALUE_ID;
	}

	public String getPRODUCTATTRIBUTEVALUE_DETAIL() {
		return PRODUCTATTRIBUTEVALUE_DETAIL;
	}

	public void setPRODUCTATTRIBUTEVALUE_DETAIL(String pRODUCTATTRIBUTEVALUE_DETAIL) {
		PRODUCTATTRIBUTEVALUE_DETAIL = pRODUCTATTRIBUTEVALUE_DETAIL;
	}

	public String getPRODUCTATTRIBUTEITEM_VALUE() {
		return PRODUCTATTRIBUTEITEM_VALUE;
	}

	public void setPRODUCTATTRIBUTEITEM_VALUE(String pRODUCTATTRIBUTEITEM_VALUE) {
		PRODUCTATTRIBUTEITEM_VALUE = pRODUCTATTRIBUTEITEM_VALUE;
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
		return (long) 8;
	}

	
}
