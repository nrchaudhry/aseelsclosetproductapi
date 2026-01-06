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
@Table(name ="tblproductitemstockadjustment")
public class ProductItemStockAdjustment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTITEMSTOCKADJUSTMENT_ID;

	@Column(name ="PRODUCTITEM_ID")
	private Long PRODUCTITEM_ID;

	@Transient
	private String PRODUCTITEM_DETAIL;

	@Column(name ="QUANTITY_ADJUST")
	private  BigDecimal QUANTITY_ADJUST;

	@Column(name ="PURCHASE_PRICE")
	private  BigDecimal PURCHASE_PRICE;

	@Column(name ="ISACTIVE")
	private String ISACTIVE;

	@JsonIgnore
	@Column(name ="MODIFIED_BY")
	private String MODIFIED_BY;

	@JsonIgnore
	@Column(name ="MODIFIED_WHEN")
	private String MODIFIED_WHEN;

	@JsonIgnore
	@Column(name ="MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;

	public long getPRODUCTITEMSTOCKADJUSTMENT_ID() {
		return PRODUCTITEMSTOCKADJUSTMENT_ID;
	}

	public void setPRODUCTITEMSTOCKADJUSTMENT_ID(long pRODUCTITEMSTOCKADJUSTMENT_ID) {
		PRODUCTITEMSTOCKADJUSTMENT_ID = pRODUCTITEMSTOCKADJUSTMENT_ID;
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

	public BigDecimal getQUANTITY_ADJUST() {
		return QUANTITY_ADJUST;
	}

	public void setQUANTITY_ADJUST(BigDecimal qUANTITY_ADJUST) {
		QUANTITY_ADJUST = qUANTITY_ADJUST;
	}

	public BigDecimal getPURCHASE_PRICE() {
		return PURCHASE_PRICE;
	}

	public void setPURCHASE_PRICE(BigDecimal pURCHASE_PRICE) {
		PURCHASE_PRICE = pURCHASE_PRICE;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	@JsonIgnore
	public String getMODIFIED_BY() {
		return MODIFIED_BY;
	}

	public void setMODIFIED_BY(String mODIFIED_BY) {
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


}
