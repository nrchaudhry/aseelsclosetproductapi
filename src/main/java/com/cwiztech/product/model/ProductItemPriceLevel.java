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
@Table(name = "TBLPRODUCTITEMPRICELEVEL")
public class ProductItemPriceLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTITEMPRICELEVEL_ID;
	
	@Column(name = "CURRENCY_ID")
	private Long CURRENCY_ID;
	
	@Transient
	private String CURRENCY_DETAIL;
	
	@Column(name = "PRODUCTITEM_ID")
	private Long PRODUCTITEM_ID;
	
	@Transient
	private String PRODUCTITEM_DETAIL;
	
	@Column(name = "PRICELEVEL_ID")
	private Long PRICELEVEL_ID;
	
	@Transient
	private String PRICELEVEL_DETAIL;
	
	@Column(name = "PRODUCTITEM_QUANTITY")
	private Long PRODUCTITEM_QUANTITY;
	
	@Column(name = "PRODUCTITEM_UNITPRICE")
	private Double PRODUCTITEM_UNITPRICE;
	
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
	
	public long getPRODUCTITEMPRICELEVEL_ID() {
		return PRODUCTITEMPRICELEVEL_ID;
	}

	public void setPRODUCTITEMPRICELEVEL_ID(long pRODUCTITEMPRICELEVEL_ID) {
		PRODUCTITEMPRICELEVEL_ID = pRODUCTITEMPRICELEVEL_ID;
	}

	public Long getCURRENCY_ID() {
		return CURRENCY_ID;
	}

	public void setCURRENCY_ID(Long cURRENCY_ID) {
		CURRENCY_ID = cURRENCY_ID;
	}

	public String getCURRENCY_DETAIL() {
		return CURRENCY_DETAIL;
	}

	public void setCURRENCY_DETAIL(String cURRENCY_DETAIL) {
		CURRENCY_DETAIL = cURRENCY_DETAIL;
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


	public Long getPRICELEVEL_ID() {
		return PRICELEVEL_ID;
	}

	public void setPRICELEVEL_ID(Long pRICELEVEL_ID) {
		PRICELEVEL_ID = pRICELEVEL_ID;
	}

	public String getPRICELEVEL_DETAIL() {
		return PRICELEVEL_DETAIL;
	}

	public void setPRICELEVEL_DETAIL(String pRICELEVEL_DETAIL) {
		PRICELEVEL_DETAIL = pRICELEVEL_DETAIL;
	}

	public Long getPRODUCTITEM_QUANTITY() {
		return PRODUCTITEM_QUANTITY;
	}

	public void setPRODUCTITEM_QUANTITY(Long pRODUCTITEM_QUANTITY) {
		PRODUCTITEM_QUANTITY = pRODUCTITEM_QUANTITY;
	}

	public Double getPRODUCTITEM_UNITPRICE() {
		return PRODUCTITEM_UNITPRICE;
	}

	public void setPRODUCTITEM_UNITPRICE(Double pRODUCTITEM_UNITPRICE) {
		PRODUCTITEM_UNITPRICE = pRODUCTITEM_UNITPRICE;
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
		return (long) 16;
	}
	
	
}
