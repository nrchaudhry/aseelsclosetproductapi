package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductCategory;

public interface productCategoryRepository extends JpaRepository<ProductCategory, Long>{
	
	@Query(value = "select * from TBLPRODUCTCATEGORY where ISACTIVE='Y' order by PRODUCTCATEGORYORDER_NO", nativeQuery = true)
	public List<ProductCategory> findActive();
	
	@Query(value = "select * from TBLPRODUCTCATEGORY where NETSUITE_ID=?1 and PRODUCTCATEGORYPARENT_ID<>0", nativeQuery = true)
	ProductCategory findByNetSuiteID(Long id);

	@Query(value = "select * from TBLPRODUCTCATEGORY where PRODUCTCATEGORY_CODE=?1", nativeQuery = true)
	public ProductCategory findByCode(String code);
	
	@Query(value = "select * from TBLPRODUCTCATEGORY where ISACTIVE='Y' and PRODUCTCATEGORYPARENT_ID=?1 order by PRODUCTCATEGORYORDER_NO", nativeQuery = true)
	public List<ProductCategory> findActiveByParent(Long pid);

	@Query(value = "select distinct * from TBLPRODUCTCATEGORY "
			+ "where PRODUCTCATEGORY_NAME LIKE ?1 OR PRODUCTCATEGORY_DESC LIKE ?1 "
			+ "and ISACTIVE='Y' order by PRODUCTCATEGORYORDER_NO", nativeQuery = true)
	public List<ProductCategory> findBySearch(String search);

    @Query(value = "select distinct * from TBLPRODUCTCATEGORY "
            + "where PRODUCTCATEGORY_NAME LIKE ?1 OR PRODUCTCATEGORY_DESC LIKE ?1 "
			+ "order by PRODUCTCATEGORYORDER_NO", nativeQuery = true)
	public List<ProductCategory> findAllBySearch(String search);
	
    @Query(value = "select * from TBLPRODUCTCATEGORY  "
            + "where CASE WHEN :PRODUCTCATEGORYPARENT_ID = 0 THEN PRODUCTCATEGORYPARENT_ID=PRODUCTCATEGORYPARENT_ID ELSE PRODUCTCATEGORYPARENT_ID IN (:PRODUCTCATEGORYPARENT_IDS) END "
            + "and ISACTIVE='Y' order by PRODUCTCATEGORYORDER_NO", nativeQuery = true)
    List<ProductCategory> findByAdvancedSearch(
    @Param("PRODUCTCATEGORYPARENT_ID") Long PRODUCTCATEGORYPARENT_ID, @Param("PRODUCTCATEGORYPARENT_IDS") List<Integer> PRODUCTCATEGORYPARENT_IDS); 

    @Query(value = "select distinct * from TBLPRODUCTCATEGORY "
            + "where CASE WHEN :PRODUCTCATEGORYPARENT_ID = 0 THEN PRODUCTCATEGORYPARENT_ID=PRODUCTCATEGORYPARENT_ID ELSE PRODUCTCATEGORYPARENT_ID IN (:PRODUCTCATEGORYPARENT_IDS) END "
            , nativeQuery = true)
    List<ProductCategory> findAllByAdvancedSearch(
    @Param("PRODUCTCATEGORYPARENT_ID") Long PRODUCTCATEGORYPARENT_ID, @Param("PRODUCTCATEGORYPARENT_IDS") List<Integer> PRODUCTCATEGORYPARENT_IDS); 
	
	@Query(value = "select * from TBLPRODUCTCATEGORY "
			+ "where PRODUCTCATEGORY_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductCategory> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTCATEGORY "
			+ "where PRODUCTCATEGORY_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductCategory> findByNotInIDs(@Param("ids") List<Integer> ids);


}
