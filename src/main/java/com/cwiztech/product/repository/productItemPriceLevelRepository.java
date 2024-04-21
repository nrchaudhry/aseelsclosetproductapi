package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItemPriceLevel;

public interface productItemPriceLevelRepository  extends JpaRepository<ProductItemPriceLevel, Long> {
	@Query(value = "select * from TBLPRODUCTITEMPRICELEVEL where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemPriceLevel> findActive();

	@Query(value = "select * from TBLPRODUCTITEMPRICELEVEL where NETSUITE_ID=?1", nativeQuery = true)
	public ProductItemPriceLevel findByNetSuiteID(Long id);

	@Query(value = "select * from TBLPRODUCTITEMPRICELEVEL where PRODUCT_CODE=?1", nativeQuery = true)
	public ProductItemPriceLevel findByCode(String code);
	
	@Query(value = "select * from TBLPRODUCTITEMPRICELEVEL "
			+ "where PRODUCTITEMPRICELEVEL_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemPriceLevel> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMPRICELEVEL "
			+ "where PRODUCTITEMPRICELEVEL_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemPriceLevel> findByNotInIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMPRICELEVEL "
			+ "inner join TBLPRODUCTITEMPRICELEVELCATEGORY as b on PRODUCTCATEGORY_ID=PRODUCTCATEGORY_ID "
			+ "where PRODUCT_CODE like ?1 "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemPriceLevel> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTITEMPRICELEVEL "
			+ "inner join TBLPRODUCTITEMPRICELEVELCATEGORY as b on PRODUCT_ID=PRODUCT_ID "
			+ "where PRODUCT_CODE like ?1 "
			+ "", nativeQuery = true)
	public List<ProductItemPriceLevel> findAllBySearch(String search);

	@Query(value = "select * from TBLPRODUCTITEMPRICELEVEL " 
			+ "where (CURRENCY_ID LIKE CASE WHEN ?1 = 0 THEN CURRENCY_ID ELSE ?1 END or CURRENCY_ID is NULL) "
			+ "and PRODUCTITEM_ID LIKE CASE WHEN ?2 = 0 THEN PRODUCTITEM_ID ELSE ?2 END "
			+ "and (PRICELEVEL_ID LIKE CASE WHEN ?3 = 0 THEN PRICELEVEL_ID ELSE ?3 END or PRICELEVEL_ID is NULL) "
		    + "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemPriceLevel> findByAdvancedSearch(Long cid, Long piid, Long plid);
	
	@Query(value = "select * from TBLPRODUCTITEMPRICELEVEL " 
			+ "where (CURRENCY_ID LIKE CASE WHEN ?1 = 0 THEN CURRENCY_ID ELSE ?1 END or CURRENCY_ID is NULL) "
			+ "and PRODUCTITEM_ID LIKE CASE WHEN ?2 = 0 THEN PRODUCTITEM_ID ELSE ?2 END "
			+ "and (PRICELEVEL_ID LIKE CASE WHEN ?3 = 0 THEN PRICELEVEL_ID ELSE ?3 END or PRICELEVEL_ID is NULL) "
		    + "", nativeQuery = true)
	List<ProductItemPriceLevel> findAllByAdvancedSearch(Long cid, Long piid, Long plid);

}
