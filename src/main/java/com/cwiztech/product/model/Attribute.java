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
@Table(name = "TBLATTRIBUTE")
public class Attribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ATTRIBUTE_ID;
	
	@Column(name = "ATTRIBUTE_NAME")
	private String ATTRIBUTE_NAME;
	
	@Column(name = "ATTRIBUTE_KEY")
	private String ATTRIBUTE_KEY;
	
	@Column(name = "ATTRIBUTENETSUITE_KEY")
	private String ATTRIBUTENETSUITE_KEY;
	
	@Column(name = "ATTRIBUTE_DESCRIPTION")
	private String ATTRIBUTE_DESCRIPTION;
	
	@Column(name = "DATATYPE_ID")
	private Long DATATYPE_ID;
	
	@Transient
	private String DATATYPE_DETAIL;
	
	@Column(name = "INPUT_PATTERN")
	private String INPUT_PATTERN;
	
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

	public long getATTRIBUTE_ID() {
		return ATTRIBUTE_ID;
	}

	public void setATTRIBUTE_ID(long aTTRIBUTE_ID) {
		ATTRIBUTE_ID = aTTRIBUTE_ID;
	}

	public String getATTRIBUTE_NAME() {
		return ATTRIBUTE_NAME;
	}

	public void setATTRIBUTE_NAME(String aTTRIBUTE_NAME) {
		ATTRIBUTE_NAME = aTTRIBUTE_NAME;
	}

	public String getATTRIBUTE_KEY() {
		return ATTRIBUTE_KEY;
	}

	public void setATTRIBUTE_KEY(String aTTRIBUTE_KEY) {
		ATTRIBUTE_KEY = aTTRIBUTE_KEY;
	}

	public String getATTRIBUTENETSUITE_KEY() {
		return ATTRIBUTENETSUITE_KEY;
	}

	public void setATTRIBUTENETSUITE_KEY(String aTTRIBUTENETSUITE_KEY) {
		ATTRIBUTENETSUITE_KEY = aTTRIBUTENETSUITE_KEY;
	}

	public String getATTRIBUTE_DESCRIPTION() {
		return ATTRIBUTE_DESCRIPTION;
	}

	public Long getDATATYPE_ID() {
		return DATATYPE_ID;
	}

	public void setDATATYPE_ID(Long dATATYPE_ID) {
		DATATYPE_ID = dATATYPE_ID;
	}

	public String getDATATYPE_DETAIL() {
		return DATATYPE_DETAIL;
	}

	public void setDATATYPE_DETAIL(String dATATYPE_DETAIL) {
		DATATYPE_DETAIL = dATATYPE_DETAIL;
	}

	public void setATTRIBUTE_DESCRIPTION(String aTTRIBUTE_DESCRIPTION) {
		ATTRIBUTE_DESCRIPTION = aTTRIBUTE_DESCRIPTION;
	}

	public String getINPUT_PATTERN() {
		return INPUT_PATTERN;
	}

	public void setINPUT_PATTERN(String iNPUT_PATTERN) {
		INPUT_PATTERN = iNPUT_PATTERN;
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
