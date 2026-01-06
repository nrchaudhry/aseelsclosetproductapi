package com.cwiztech.product.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLPRODUCTPRICELEVEL")
public class ProductPriceLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTPRICELEVEL_ID;
	
	@Column(name = "CURRENCY_ID")
	private Long CURRENCY_ID;
	
	@Transient
	private String CURRENCY_DETAIL;
	
	@Column(name = "PRODUCT_ID")
	private Long PRODUCT_ID;
	
	@Transient
	private String PRODUCT_DETAIL;
	
	@Column(name = "PRICELEVEL_ID")
	private Long PRICELEVEL_ID;
	
	@Transient
	private String PRICELEVEL_DETAIL;
	
	@Column(name = "PRODUCT_QUANTITY")
	private Long PRODUCT_QUANTITY;
	
	@Column(name = "PRODUCT_UNITPRICE")
	private  BigDecimal PRODUCT_UNITPRICE;
	
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


	public long getPRODUCTPRICELEVEL_ID() {
		return PRODUCTPRICELEVEL_ID;
	}


	public void setPRODUCTPRICELEVEL_ID(long pRODUCTPRICELEVEL_ID) {
		PRODUCTPRICELEVEL_ID = pRODUCTPRICELEVEL_ID;
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

	public Long getPRODUCT_QUANTITY() {
		return PRODUCT_QUANTITY;
	}

	public void setPRODUCT_QUANTITY(Long pRODUCT_QUANTITY) {
		PRODUCT_QUANTITY = pRODUCT_QUANTITY;
	}
	
	public BigDecimal getPRODUCT_UNITPRICE() {
		return PRODUCT_UNITPRICE;
	}


	public void setPRODUCT_UNITPRICE(BigDecimal pRODUCT_UNITPRICE) {
		PRODUCT_UNITPRICE = pRODUCT_UNITPRICE;
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
