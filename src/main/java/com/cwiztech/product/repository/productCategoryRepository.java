package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductCategory;
import com.cwiztech.product.model.ProductItem;

public interface productCategoryRepository extends JpaRepository<ProductCategory, Long>{
	
	@Query(value = "select * from TBLPRODUCTCATEGORY where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductCategory> findActive();
	
	@Query(value = "select * from TBLPRODUCTCATEGORY where NETSUITE_ID=?1 and PRODUCTCATEGORYPARENT_ID<>0", nativeQuery = true)
	ProductCategory findByNetSuiteID(Long id);

	@Query(value = "select * from TBLPRODUCTCATEGORY where PRODUCTCATEGORY_CODE=?1", nativeQuery = true)
	public ProductCategory findByCode(String code);
	
	@Query(value = "select * from TBLPRODUCTCATEGORY where ISACTIVE='Y' and PRODUCTCATEGORYPARENT_ID=?1 order by PRODUCTCATEGORYORDER_NO", nativeQuery = true)
	public List<ProductCategory> findActiveByParent(Long pid);

	@Query(value = "select distinct a.* from TBLPRODUCTCATEGORY as a "
			+ "left outer join TBLPRODUCT as b on a.PRODUCTCATEGORY_ID=b.PRODUCTCATEGORY_ID "
			+ "left outer join TBLPRODUCTAPPLICATION as c on b.PRODUCT_ID=c.PRODUCT_ID "
			+ "where APPLICATION_ID LIKE CASE WHEN ?1 = 0 THEN APPLICATION_ID ELSE ?1 END " 
			+ "and (PRODUCTCATEGORY_NAME LIKE ?2 "
			+ "OR PRODUCTCATEGORY_DESC LIKE ?2) "
			+ "and a.ISACTIVE='Y' order by PRODUCTCATEGORYORDER_NO", nativeQuery = true)
	public List<ProductCategory> findBySearch(Long applicationID, String search);

	@Query(value = "select distinct a.* from TBLPRODUCTCATEGORY as a "
			+ "left outer join TBLPRODUCT as b on a.PRODUCTCATEGORY_ID=b.PRODUCTCATEGORY_ID "
			+ "left outer join TBLPRODUCTAPPLICATION as c on b.PRODUCT_ID=c.PRODUCT_ID "
			+ "where APPLICATION_ID LIKE CASE WHEN ?1 = 0 THEN APPLICATION_ID ELSE ?1 END " 
			+ "and (PRODUCTCATEGORY_NAME LIKE ?2 "
			+ "OR PRODUCTCATEGORY_DESC LIKE ?2) "
			+ "order by PRODUCTCATEGORYORDER_NO", nativeQuery = true)
	public List<ProductCategory> findAllBySearch(Long applicationID, String search);
	
	@Query(value = "select distinct a.* from TBLPRODUCTCATEGORY as a "
				+ "where PRODUCTCATEGORYPARENT_ID LIKE CASE WHEN ?1 = 0 THEN PRODUCTCATEGORYPARENT_ID ELSE ?1 END and PRODUCTCATEGORYPARENT_ID is NOT NULL " 
				+ "and a.ISACTIVE='Y' order by PRODUCTCATEGORYORDER_NO", nativeQuery = true)
	List<ProductCategory> findByAdvancedSearch(Long productcategoryID);

	@Query(value = "select distinct a.* from TBLPRODUCTCATEGORY as a "
			    + "where PRODUCTCATEGORYPARENT_ID LIKE CASE WHEN ?1 = 0 THEN PRODUCTCATEGORYPARENT_ID ELSE ?1 END and PRODUCTCATEGORYPARENT_ID is NOT NULL " 
			    + "order by PRODUCTCATEGORYORDER_NO", nativeQuery = true)
	List<ProductCategory> findAllByAdvancedSearch(Long productcategoryID);
	
	@Query(value = "select * from TBLPRODUCTCATEGORY "
			+ "where PRODUCTCATEGORY_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductCategory> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTCATEGORY "
			+ "where PRODUCTCATEGORY_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductCategory> findByNotInIDs(@Param("ids") List<Integer> ids);


}
