package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductAttributeValue;
import com.cwiztech.product.model.ProductItem;

public interface productAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long>{
	
	@Query(value = "select * from TBLPRODUCTATTRIBUTEVALUE where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductAttributeValue> findActive();
	
	@Query(value = "select * from TBLPRODUCTATTRIBUTEVALUE "
			+ "where PRODUCTATTRIBUTEVALUE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductAttributeValue> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTATTRIBUTEVALUE "
			+ "where PRODUCTATTRIBUTEVALUE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductAttributeValue> findByNotInIDs(@Param("ids") List<Integer> ids);
	
	@Query(value = "select * from TBLPRODUCTATTRIBUTEVALUE "
			+ "where PRODUCTATTRIBUTE_VALUE like ?1 and ISACTIVE='Y'", nativeQuery = true)
	public List<ProductAttributeValue> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTATTRIBUTEVALUE "
			+ "where PRODUCTATTRIBUTE_VALUE like ?1 ", nativeQuery = true)
	public List<ProductAttributeValue> findAllBySearch(String search);

	@Query(value = "select a.* from TBLPRODUCTATTRIBUTEVALUE as a " 
			+ "where PRODUCTATTRIBUTE_ID like CASE WHEN ?1=0 THEN PRODUCTATTRIBUTE_ID ELSE ?1 END "
			+ "and (PRODUCTATTRIBUTEVALUEPARENT_ID like CASE WHEN ?2=0 THEN PRODUCTATTRIBUTEVALUEPARENT_ID ELSE ?2 END or PRODUCTATTRIBUTEVALUEPARENT_ID is NULL) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductAttributeValue> findByAdvancedSearch(Long productattributeID,Long productattributevalueparentID);

	@Query(value = "select a.* from TBLPRODUCTATTRIBUTEVALUE  as a "
			+ "where PRODUCTATTRIBUTE_ID like CASE WHEN ?1=0 THEN PRODUCTATTRIBUTE_ID ELSE ?1 END "
			+ "and (PRODUCTATTRIBUTEVALUEPARENT_ID like CASE WHEN ?2=0 THEN PRODUCTATTRIBUTEVALUEPARENT_ID ELSE ?2 END or PRODUCTATTRIBUTEVALUEPARENT_ID is NULL) "
			, nativeQuery = true)
	List<ProductAttributeValue> findAllByAdvancedSearch(Long productattributeID,Long productattributevalueparentID);

}
