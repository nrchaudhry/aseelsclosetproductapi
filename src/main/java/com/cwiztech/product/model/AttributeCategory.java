package com.cwiztech.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLATTRIBUTECATEGORY")
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class AttributeCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ATTRIBUTECATEGORY_ID;
	
	@OneToOne
	@JoinColumn(name = "ATTRIBUTECATEGORYPARENT_ID")
	private AttributeCategory ATTRIBUTECATEGORYPARENT_ID;
	
	@Column(name = "ATTRIBUTECATEGORYORDER_NO")
	private Long ATTRIBUTECATEGORYORDER_NO;
	
	@Column(name = "ATTRIBUTECATEGORY_NAME")
	private String ATTRIBUTECATEGORY_NAME;
	
	@Column(name = "ISTABS")
	private String ISTABS;
	
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

	public long getATTRIBUTECATEGORY_ID() {
		return ATTRIBUTECATEGORY_ID;
	}

	public void setATTRIBUTECATEGORY_ID(long pRODUCTATTRIBUTECATEGORY_ID) {
		ATTRIBUTECATEGORY_ID = pRODUCTATTRIBUTECATEGORY_ID;
	}

	public AttributeCategory getATTRIBUTECATEGORYPARENT_ID() {
		return ATTRIBUTECATEGORYPARENT_ID;
	}

	public void setATTRIBUTECATEGORYPARENT_ID(AttributeCategory aTTRIBUTECATEGORYPARENT_ID) {
		ATTRIBUTECATEGORYPARENT_ID = aTTRIBUTECATEGORYPARENT_ID;
	}

	public Long getATTRIBUTECATEGORYORDER_NO() {
		return ATTRIBUTECATEGORYORDER_NO;
	}

	public void setATTRIBUTECATEGORYORDER_NO(Long aTTRIBUTECATEGORYORDER_NO) {
		ATTRIBUTECATEGORYORDER_NO = aTTRIBUTECATEGORYORDER_NO;
	}

	public String getATTRIBUTECATEGORY_NAME() {
		return ATTRIBUTECATEGORY_NAME;
	}

	public void setATTRIBUTECATEGORY_NAME(String aTTRIBUTECATEGORY_NAME) {
		ATTRIBUTECATEGORY_NAME = aTTRIBUTECATEGORY_NAME;
	}

	public String getISTABS() {
		return ISTABS;
	}

	public void setISTABS(String iSTABS) {
		ISTABS = iSTABS;
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
		return (long) 5;
	}
	

}
