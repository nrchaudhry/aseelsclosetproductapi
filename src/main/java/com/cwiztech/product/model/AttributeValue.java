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
@Table(name = "TBLATTRIBUTEVALUE")

public class AttributeValue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ATTRIBUTEVALUE_ID;
	
	@Column(name = "ATTRIBUTEVALUEPARENT_ID")
	private Long ATTRIBUTEVALUEPARENT_ID ;
	
	@Column(name = "ATTRIBUTE_ID")
	private Long ATTRIBUTE_ID ;

	@Transient
	private String ATTRIBUTE_DETAIL;
	
	@Column(name = "ATTRIBUTE_VALUE")
	private String ATTRIBUTE_VALUE;
	
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
	
	public long getATTRIBUTEVALUE_ID() {
		return ATTRIBUTEVALUE_ID;
	}
	
	public Long getATTRIBUTEVALUEPARENT_ID() {
		return ATTRIBUTEVALUEPARENT_ID;
	}

	public void setATTRIBUTEVALUEPARENT_ID(Long pRODUCTATTRIBUTEVALUEPARENT_ID) {
		ATTRIBUTEVALUEPARENT_ID = pRODUCTATTRIBUTEVALUEPARENT_ID;
	}

	public void setATTRIBUTEVALUE_ID(long pRODUCTATTRIBUTEVALUE_ID) {
		ATTRIBUTEVALUE_ID = pRODUCTATTRIBUTEVALUE_ID;
	}

	public Long getATTRIBUTE_ID() {
		return ATTRIBUTE_ID;
	}

	public void setATTRIBUTE_ID(Long pRODUCTATTRIBUTE_ID) {
		ATTRIBUTE_ID = pRODUCTATTRIBUTE_ID;
	}

	public String getATTRIBUTE_DETAIL() {
		return ATTRIBUTE_DETAIL;
	}

	public void setATTRIBUTE_DETAIL(String pRODUCTATTRIBUTE_DETAIL) {
		ATTRIBUTE_DETAIL = pRODUCTATTRIBUTE_DETAIL;
	}

	public String getATTRIBUTE_VALUE() {
		return ATTRIBUTE_VALUE;
	}

	public void setATTRIBUTE_VALUE(String pRODUCTATTRIBUTE_VALUE) {
		ATTRIBUTE_VALUE = pRODUCTATTRIBUTE_VALUE;
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
		return (long) 12;
	}
}
