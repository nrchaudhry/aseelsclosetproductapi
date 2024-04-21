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
@Table(name = "TBLPRODUCTITEMIMAGE")

public class ProductItemImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTITEMIMAGE_ID;
	
	@Column(name = "PRODUCTITEM_ID")
	private Long PRODUCTITEM_ID;
	
	@Transient
	private String PRODUCTITEM_DETAIL;
	
	@Column(name = "PRODUCTITEMIMAGE_PATH")
	private String PRODUCTITEMIMAGE_PATH ;
	
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

		
	public long getPRODUCTITEMIMAGE_ID() {
		return PRODUCTITEMIMAGE_ID;
	}

	public void setPRODUCTITEMIMAGE_ID(long pRODUCTITEMIMAGE_ID) {
		PRODUCTITEMIMAGE_ID = pRODUCTITEMIMAGE_ID;
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

	public String getPRODUCTITEMIMAGE_PATH() {
		return PRODUCTITEMIMAGE_PATH;
	}

	public void setPRODUCTITEMIMAGE_PATH(String pRODUCTITEMIMAGE_PATH) {
		PRODUCTITEMIMAGE_PATH = pRODUCTITEMIMAGE_PATH;
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
		return (long) 11;
	}

	
}
