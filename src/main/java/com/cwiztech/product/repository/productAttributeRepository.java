package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductAttribute;

public interface productAttributeRepository extends JpaRepository<ProductAttribute, Long>{
	
	@Query(value = "select * from TBLPRODUCTATTRIBUTE where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductAttribute> findActive();
	
	@Query(value = "select * from TBLPRODUCTATTRIBUTE where PRODUCTATTRIBUTE_KEY=?1 and ISACTIVE='Y'", nativeQuery = true)
	public ProductAttribute findByKey(String key);
	
	@Query(value = "select * from TBLPRODUCTATTRIBUTE "
			+ "where (PRODUCTATTRIBUTE_NAME like ?1 or  PRODUCTATTRIBUTE_DESC like ?1) and ISACTIVE='Y' order by PRODUCTATTRIBUTEORDER_NO", nativeQuery = true)
	public List<ProductAttribute> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTATTRIBUTE "
			+ "where PRODUCTATTRIBUTE_NAME like ?1 or  PRODUCTATTRIBUTE_DESC like ?1 order by PRODUCTATTRIBUTEORDER_NO", nativeQuery = true)
	public List<ProductAttribute> findAllBySearch(String search);

	@Query(value = "select * from TBLPRODUCTATTRIBUTE " 
			+ "where (PRODUCTCATEGORY_ID like CASE WHEN ?1=0 THEN PRODUCTCATEGORY_ID ELSE ?1 END or PRODUCTCATEGORY_ID is NULL) "
			+ "and (PRODUCT_ID like CASE WHEN ?2=0 THEN PRODUCT_ID ELSE ?2 END or PRODUCT_ID is NULL) "
			+ "and ATTRIBUTECATEGORY_ID like CASE WHEN ?3=0 THEN ATTRIBUTECATEGORY_ID ELSE ?3 END "
			+ "and ATTRIBUTE_ID like CASE WHEN ?4=0 THEN ATTRIBUTE_ID ELSE ?4 END  "
			+ "and ISACTIVE='Y' order by ATTRIBUTEORDER_NO ", nativeQuery = true)
	List<ProductAttribute> findByAdvancedSearch(Long productcategoryID, Long productID, Long productattributecategoryID, Long attributeID);

	@Query(value = "select * from TBLPRODUCTATTRIBUTE "
			+ "where (PRODUCTCATEGORY_ID like CASE WHEN ?1=0 THEN PRODUCTCATEGORY_ID ELSE ?1 END or PRODUCTCATEGORY_ID is NULL) "
			+ "and (PRODUCT_ID like CASE WHEN ?2=0 THEN PRODUCT_ID ELSE ?2 END or PRODUCT_ID is NULL) "
			+ "and ATTRIBUTECATEGORY_ID like CASE WHEN ?3=0 THEN ATTRIBUTECATEGORY_ID ELSE ?3 END "
			+ "and ATTRIBUTE_ID like CASE WHEN ?4=0 THEN ATTRIBUTE_ID ELSE ?4 END order by ATTRIBUTEORDER_NO"
			, nativeQuery = true)
	List<ProductAttribute> findAllByAdvancedSearch(Long productcategoryID, Long productID, Long productattributecategoryID, Long attributeID);

	@Query(value = "select * from TBLPRODUCTATTRIBUTE "
			+ "where PRODUCTATTRIBUTE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductAttribute> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTATTRIBUTE "
			+ "where PRODUCTATTRIBUTE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductAttribute> findByNotInIDs(@Param("ids") List<Integer> ids);
}



