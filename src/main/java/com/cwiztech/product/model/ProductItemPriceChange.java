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
@Table(name = "TBLPRODUCTITEMPRICECHANGE")
public class ProductItemPriceChange {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTITEMPRICECHANGE_ID;
	
	@Column(name = "PRODUCTITEM_ID")
	private Long PRODUCTITEM_ID;
	
	@Transient
	private String PRODUCTITEM_DETAIL;
	
	@Column(name = "CURRENCY_ID")
	private Long CURRENCY_ID;
	
	@Transient
	private String CURRENCY_DETAIL;
	
	@Column(name = "PRODUCTITEM_PURCHASEPRICE")
	private Double PRODUCTITEM_PURCHASEPRICE;
	
	@Column(name = "PRODUCTITEM_LASTPURCHASEPRICE")
	private Double PRODUCTITEM_LASTPURCHASEPRICE;
	
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

	
	
	public long getPRODUCTITEMPRICECHANGE_ID() {
		return PRODUCTITEMPRICECHANGE_ID;
	}

	public void setPRODUCTITEMPRICECHANGE_ID(long pRODUCTITEMPRICECHANGE_ID) {
		PRODUCTITEMPRICECHANGE_ID = pRODUCTITEMPRICECHANGE_ID;
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

	public Double getPRODUCTITEM_PURCHASEPRICE() {
		return PRODUCTITEM_PURCHASEPRICE;
	}

	public void setPRODUCTITEM_PURCHASEPRICE(Double pRODUCTITEM_PURCHASEPRICE) {
		PRODUCTITEM_PURCHASEPRICE = pRODUCTITEM_PURCHASEPRICE;
	}

	public Double getPRODUCTITEM_LASTPURCHASEPRICE() {
		return PRODUCTITEM_LASTPURCHASEPRICE;
	}

	public void setPRODUCTITEM_LASTPURCHASEPRICE(Double pRODUCTITEM_LASTPURCHASEPRICE) {
		PRODUCTITEM_LASTPURCHASEPRICE = pRODUCTITEM_LASTPURCHASEPRICE;
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
		return (long) 17;
	}

	

}
