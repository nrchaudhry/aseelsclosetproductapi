package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	@Query(value = "select * from TBLPRODUCTPRICELEVEL "
			+ "inner join TBLPRODUCTPRICELEVELCATEGORY as b on PRODUCTCATEGORY_ID=b.PRODUCTCATEGORY_ID "
			+ "where PRODUCT_CODE like ?1 "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<ProductPriceLevel> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTPRICELEVEL "
			+ "inner join TBLPRODUCTPRICELEVELCATEGORY as b on PRODUCT_ID=b.PRODUCT_ID "
			+ "where PRODUCT_CODE like ?1 "
			+ "", nativeQuery = true)
	public List<ProductPriceLevel> findAllBySearch(String search);

	@Query(value = "select * from TBLPRODUCTPRICELEVEL " 
            + "where CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
			+ "and (PRICELEVEL_ID LIKE CASE WHEN :PRICELEVEL_ID = 0 Then PRICELEVEL_ID ELSE :PRICELEVEL_ID END or PRICELEVEL_ID is NULL) "
			+ "and (CURRENCY_ID LIKE CASE WHEN :CURRENCY_ID = 0 Then CURRENCY_ID ELSE :CURRENCY_ID END or CURRENCY_ID is NULL) "
	        + "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductPriceLevel> findByAdvancedSearch(
		    @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS,
		    @Param("PRICELEVEL_ID") Long PRICELEVEL_ID,
		    @Param("CURRENCY_ID") Long CURRENCY_ID);
	
	@Query(value = "select * from TBLPRODUCTPRICELEVEL "
            + "where CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
			+ "and (PRICELEVEL_ID LIKE CASE WHEN :PRICELEVEL_ID = 0 Then PRICELEVEL_ID ELSE :PRICELEVEL_ID END or PRICELEVEL_ID is NULL) "
			+ "and (CURRENCY_ID LIKE CASE WHEN :CURRENCY_ID = 0 Then CURRENCY_ID ELSE :CURRENCY_ID END or CURRENCY_ID is NULL) "
		    + "", nativeQuery = true)
	List<ProductPriceLevel> findAllByAdvancedSearch(
		    @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS,
		    @Param("PRICELEVEL_ID") Long PRICELEVEL_ID,
		    @Param("CURRENCY_ID") Long CURRENCY_ID);
}
