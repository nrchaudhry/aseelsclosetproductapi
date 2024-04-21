package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItem;
import com.cwiztech.product.model.ProductPriceLevel;

public interface productPriceLevelRepository extends JpaRepository<ProductPriceLevel, Long> {
	@Query(value = "select * from TBLPRODUCTPRICELEVEL where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductPriceLevel> findActive();

	@Query(value = "select * from TBLPRODUCTPRICELEVEL where NETSUITE_ID=?1", nativeQuery = true)
	public ProductPriceLevel findByNetSuiteID(Long id);

	@Query(value = "select * from TBLPRODUCTPRICELEVEL where PRODUCT_CODE=?1", nativeQuery = true)
	public ProductPriceLevel findByCode(String code);
	
	@Query(value = "select * from TBLPRODUCTPRICELEVEL "
			+ "where PRODUCTPRICELEVEL_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductPriceLevel> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTPRICELEVEL "
			+ "where PRODUCTPRICELEVEL_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductPriceLevel> findByNotInIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTPRICELEVEL as a "
			+ "inner join TBLPRODUCTPRICELEVELCATEGORY as b on a.PRODUCTCATEGORY_ID=b.PRODUCTCATEGORY_ID "
			+ "where PRODUCT_CODE like ?1 "
			+ "and a.ISACTIVE='Y'", nativeQuery = true)
	public List<ProductPriceLevel> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTPRICELEVEL as a "
			+ "inner join TBLPRODUCTPRICELEVELCATEGORY as b on a.PRODUCT_ID=b.PRODUCT_ID "
			+ "where PRODUCT_CODE like ?1 "
			+ "", nativeQuery = true)
	public List<ProductPriceLevel> findAllBySearch(String search);

	@Query(value = "select * from TBLPRODUCTPRICELEVEL as a " 
			+ "where a.CURRENCY_ID LIKE CASE WHEN ?1 = 0 THEN a.CURRENCY_ID ELSE ?1 END or a.CURRENCY_ID is NULL "
			+ "and a.PRODUCT_ID LIKE CASE WHEN ?2 = 0 THEN a.PRODUCT_ID ELSE ?2 END "
			+ "and a.PRICELEVEL_ID LIKE CASE WHEN ?3 = 0 THEN a.PRICELEVEL_ID ELSE ?3 END or a.PRICELEVEL_ID is NULL "
	        + "and a.ISACTIVE='Y'", nativeQuery = true)
	List<ProductPriceLevel> findByAdvancedSearch(Long cid, Long pid, Long plid);
	
	@Query(value = "select * from TBLPRODUCTPRICELEVEL as a "
			+ "where a.CURRENCY_ID LIKE CASE WHEN ?1 = 0 THEN a.CURRENCY_ID ELSE ?1 END or a.CURRENCY_ID is NULL "
			+ "and a.PRODUCT_ID LIKE CASE WHEN ?2 = 0 THEN a.PRODUCT_ID ELSE ?2 END "
			+ "and a.PRICELEVEL_ID LIKE CASE WHEN ?3 = 0 THEN a.PRICELEVEL_ID ELSE ?3 END or a.PRICELEVEL_ID is NULL "
		    + "", nativeQuery = true)
	List<ProductPriceLevel> findAllByAdvancedSearch(Long cid, Long pid, Long plid);
}
