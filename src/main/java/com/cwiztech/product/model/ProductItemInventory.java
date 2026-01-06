package com.cwiztech.product.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLPRODUCTITEMINVENTORY")
public class ProductItemInventory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTITEMINVENTORY_ID;
	
	@Column(name = "PRODUCTITEM_ID")
	private Long PRODUCTITEM_ID;
	
	@Transient
	private String PRODUCTITEM_DETAIL;
	
	@Column(name = "PRODUCTLOCATION_ID")
	private Long PRODUCTLOCATION_ID;
	
	@Transient
	private String PRODUCTLOCATION_DETAIL;
	
	@Column(name = "QUANTITY_ONHAND")
	private BigDecimal QUANTITY_ONHAND ;
	
	
	@Column(name = "QUANTITY_ONORDER")
	private Long QUANTITY_ONORDER ;
	
	@Column(name = "QUANTITY_COMMITTED")
	private Long QUANTITY_COMMITTED ;
	

	@Column(name = "QUANTITY_AVAILABLE")
	private  BigDecimal QUANTITY_AVAILABLE ;
	
	@Column(name = "QUANTITY_BACKORDERED")
	private Long QUANTITY_BACKORDERED ;
	
	
	@Column(name = "QUANTITY_INTRANSIT")
	private Long QUANTITY_INTRANSIT ;
	
	@Column(name = "QUANTITYEXTERNAL_INTRANSIT")
	private Long QUANTITYEXTERNAL_INTRANSIT ;
	
	@JoinColumn(name = "QUANTITYBASEUNIT_ONHAND")
	private  BigDecimal QUANTITYBASEUNIT_ONHAND ;
	
	@Column(name = "QUANTITYBASEUNIT_AVAILABLE")
	private  BigDecimal QUANTITYBASEUNIT_AVAILABLE ;
	
	
	@Column(name = "VALUE")
	private  BigDecimal VALUE ;
	
	@Column(name = "AVERAGE_COST")
	private  BigDecimal AVERAGE_COST ;
	

	@Column(name = "LASTPURCHASE_PRICE")
	private  BigDecimal LASTPURCHASE_PRICE ;
	
	@Column(name = "REORDER_POINT")
	private Long REORDER_POINT ;
	
	
	@Column(name = "AUTOLOCATIONASSIGNMENT_ALLOWED")
	private String AUTOLOCATIONASSIGNMENT_ALLOWED ;
	
	@Column(name = "AUTOLOCATIONASSIGNMENT_SUSPENDED")
	private String AUTOLOCATIONASSIGNMENT_SUSPENDED ;
	
	@Column(name = "PREFEREDSTOCK_LEVEL")
	private Long PREFEREDSTOCK_LEVEL ;
	
	@Column(name = "PURCHASELEAD_TIME")
	private Long PURCHASELEAD_TIME ;
	
	@Column(name = "STAFTYSTOCK_LEVEL")
	private Long STAFTYSTOCK_LEVEL ;
	
	@Column(name = "ATPLEAD_TIME")
	private Long ATPLEAD_TIME ;
	
	@Column(name = "DEFAULTRETURN_COST")
	private  BigDecimal DEFAULTRETURN_COST ;
	
	@Column(name = "LASTCOUNT_DATE")
	private String LASTCOUNT_DATE ;
	
	@Column(name = "NECTCOUNT_DATE")
	private String NECTCOUNT_DATE ;

	@Column(name = "COUNT_INTERVAL")
	private Long COUNT_INTERVAL ;
	
	@Column(name = "INVENTORYCLASSIFICTION_ID")
	private Long INVENTORYCLASSIFICTION_ID ;
	
	@Transient
	private String INVENTORYCLASSIFICTION_DETAIL;
	
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

	
	public long getPRODUCTITEMINVENTORY_ID() {
		return PRODUCTITEMINVENTORY_ID;
	}

	public void setPRODUCTITEMINVENTORY_ID(long pRODUCTITEMINVENTORY_ID) {
		PRODUCTITEMINVENTORY_ID = pRODUCTITEMINVENTORY_ID;
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

	public Long getPRODUCTLOCATION_ID() {
		return PRODUCTLOCATION_ID;
	}

	public void setPRODUCTLOCATION_ID(Long pRODUCTLOCATION_ID) {
		PRODUCTLOCATION_ID = pRODUCTLOCATION_ID;
	}

	public String getPRODUCTLOCATION_DETAIL() {
		return PRODUCTLOCATION_DETAIL;
	}

	public void setPRODUCTLOCATION_DETAIL(String pRODUCTLOCATION_DETAIL) {
		PRODUCTLOCATION_DETAIL = pRODUCTLOCATION_DETAIL;
	}

	public BigDecimal getQUANTITY_ONHAND() {
		return QUANTITY_ONHAND;
	}

	public void setQUANTITY_ONHAND(BigDecimal qUANTITY_ONHAND) {
		QUANTITY_ONHAND = qUANTITY_ONHAND;
	}

	public Long getQUANTITY_ONORDER() {
		return QUANTITY_ONORDER;
	}

	public void setQUANTITY_ONORDER(Long qUANTITY_ONORDER) {
		QUANTITY_ONORDER = qUANTITY_ONORDER;
	}

	public Long getQUANTITY_COMMITTED() {
		return QUANTITY_COMMITTED;
	}

	public void setQUANTITY_COMMITTED(Long qUANTITY_COMMITTED) {
		QUANTITY_COMMITTED = qUANTITY_COMMITTED;
	}

	public BigDecimal getQUANTITY_AVAILABLE() {
		return QUANTITY_AVAILABLE;
	}

	public void setQUANTITY_AVAILABLE(BigDecimal qUANTITY_AVAILABLE) {
		QUANTITY_AVAILABLE = qUANTITY_AVAILABLE;
	}

	public Long getQUANTITY_BACKORDERED() {
		return QUANTITY_BACKORDERED;
	}

	public void setQUANTITY_BACKORDERED(Long qUANTITY_BACKORDERED) {
		QUANTITY_BACKORDERED = qUANTITY_BACKORDERED;
	}

	public Long getQUANTITY_INTRANSIT() {
		return QUANTITY_INTRANSIT;
	}

	public void setQUANTITY_INTRANSIT(Long qUANTITY_INTRANSIT) {
		QUANTITY_INTRANSIT = qUANTITY_INTRANSIT;
	}

	public Long getQUANTITYEXTERNAL_INTRANSIT() {
		return QUANTITYEXTERNAL_INTRANSIT;
	}

	public void setQUANTITYEXTERNAL_INTRANSIT(Long qUANTITYEXTERNAL_INTRANSIT) {
		QUANTITYEXTERNAL_INTRANSIT = qUANTITYEXTERNAL_INTRANSIT;
	}

	public BigDecimal getQUANTITYBASEUNIT_ONHAND() {
		return QUANTITYBASEUNIT_ONHAND;
	}

	public void setQUANTITYBASEUNIT_ONHAND(BigDecimal qUANTITYBASEUNIT_ONHAND) {
		QUANTITYBASEUNIT_ONHAND = qUANTITYBASEUNIT_ONHAND;
	}

	public BigDecimal getQUANTITYBASEUNIT_AVAILABLE() {
		return QUANTITYBASEUNIT_AVAILABLE;
	}

	public void setQUANTITYBASEUNIT_AVAILABLE(BigDecimal qUANTITYBASEUNIT_AVAILABLE) {
		QUANTITYBASEUNIT_AVAILABLE = qUANTITYBASEUNIT_AVAILABLE;
	}

	public BigDecimal getVALUE() {
		return VALUE;
	}

	public void setVALUE(BigDecimal vALUE) {
		VALUE = vALUE;
	}

	public BigDecimal getAVERAGE_COST() {
		return AVERAGE_COST;
	}

	public void setAVERAGE_COST(BigDecimal aVERAGE_COST) {
		AVERAGE_COST = aVERAGE_COST;
	}

	public BigDecimal getLASTPURCHASE_PRICE() {
		return LASTPURCHASE_PRICE;
	}

	public void setLASTPURCHASE_PRICE(BigDecimal lASTPURCHASE_PRICE) {
		LASTPURCHASE_PRICE = lASTPURCHASE_PRICE;
	}

	public Long getREORDER_POINT() {
		return REORDER_POINT;
	}

	public void setREORDER_POINT(Long rEORDER_POINT) {
		REORDER_POINT = rEORDER_POINT;
	}

	public String getAUTOLOCATIONASSIGNMENT_ALLOWED() {
		return AUTOLOCATIONASSIGNMENT_ALLOWED;
	}

	public void setAUTOLOCATIONASSIGNMENT_ALLOWED(String aUTOLOCATIONASSIGNMENT_ALLOWED) {
		AUTOLOCATIONASSIGNMENT_ALLOWED = aUTOLOCATIONASSIGNMENT_ALLOWED;
	}

	public String getAUTOLOCATIONASSIGNMENT_SUSPENDED() {
		return AUTOLOCATIONASSIGNMENT_SUSPENDED;
	}

	public void setAUTOLOCATIONASSIGNMENT_SUSPENDED(String aUTOLOCATIONASSIGNMENT_SUSPENDED) {
		AUTOLOCATIONASSIGNMENT_SUSPENDED = aUTOLOCATIONASSIGNMENT_SUSPENDED;
	}

	public Long getPREFEREDSTOCK_LEVEL() {
		return PREFEREDSTOCK_LEVEL;
	}

	public void setPREFEREDSTOCK_LEVEL(Long pREFEREDSTOCK_LEVEL) {
		PREFEREDSTOCK_LEVEL = pREFEREDSTOCK_LEVEL;
	}

	public Long getPURCHASELEAD_TIME() {
		return PURCHASELEAD_TIME;
	}

	public void setPURCHASELEAD_TIME(Long pURCHASELEAD_TIME) {
		PURCHASELEAD_TIME = pURCHASELEAD_TIME;
	}

	public Long getSTAFTYSTOCK_LEVEL() {
		return STAFTYSTOCK_LEVEL;
	}

	public void setSTAFTYSTOCK_LEVEL(Long sTAFTYSTOCK_LEVEL) {
		STAFTYSTOCK_LEVEL = sTAFTYSTOCK_LEVEL;
	}

	public Long getATPLEAD_TIME() {
		return ATPLEAD_TIME;
	}

	public void setATPLEAD_TIME(Long aTPLEAD_TIME) {
		ATPLEAD_TIME = aTPLEAD_TIME;
	}

	public BigDecimal getDEFAULTRETURN_COST() {
		return DEFAULTRETURN_COST;
	}

	public void setDEFAULTRETURN_COST(BigDecimal dEFAULTRETURN_COST) {
		DEFAULTRETURN_COST = dEFAULTRETURN_COST;
	}

	public String getLASTCOUNT_DATE() {
		return LASTCOUNT_DATE;
	}

	public void setLASTCOUNT_DATE(String lASTCOUNT_DATE) {
		LASTCOUNT_DATE = lASTCOUNT_DATE;
	}

	public String getNECTCOUNT_DATE() {
		return NECTCOUNT_DATE;
	}

	public void setNECTCOUNT_DATE(String nECTCOUNT_DATE) {
		NECTCOUNT_DATE = nECTCOUNT_DATE;
	}

	public Long getCOUNT_INTERVAL() {
		return COUNT_INTERVAL;
	}

	public void setCOUNT_INTERVAL(Long cOUNT_INTERVAL) {
		COUNT_INTERVAL = cOUNT_INTERVAL;
	}

	public Long getINVENTORYCLASSIFICTION_ID() {
		return INVENTORYCLASSIFICTION_ID;
	}

	public void setINVENTORYCLASSIFICTION_ID(Long iNVENTORYCLASSIFICTION_ID) {
		INVENTORYCLASSIFICTION_ID = iNVENTORYCLASSIFICTION_ID;
	}

	public String getINVENTORYCLASSIFICTION_DETAIL() {
		return INVENTORYCLASSIFICTION_DETAIL;
	}

	public void setINVENTORYCLASSIFICTION_DETAIL(String iNVENTORYCLASSIFICTION_DETAIL) {
		INVENTORYCLASSIFICTION_DETAIL = iNVENTORYCLASSIFICTION_DETAIL;
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

































