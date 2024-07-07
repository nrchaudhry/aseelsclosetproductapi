package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductAttribute;
import com.cwiztech.product.model.ProductAttributeValue;

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

	@Query(value = "select * from TBLPRODUCTATTRIBUTEVALUE " 
            + "where CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
            + "where CASE WHEN :PRODUCTATTRIBUTE_ID = 0 THEN PRODUCTATTRIBUTE_ID=PRODUCTATTRIBUTE_ID ELSE PRODUCTATTRIBUTE_ID IN (:PRODUCTATTRIBUTE_IDS) END "
            + "where CASE WHEN :ATTRIBUTEVALUE_ID = 0 THEN ATTRIBUTEVALUE_ID=ATTRIBUTEVALUE_ID ELSE ATTRIBUTEVALUE_ID IN (:ATTRIBUTEVALUE_IDS) END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductAttributeValue> findByAdvancedSearch(
            @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS,
		    @Param("PRODUCTATTRIBUTE_ID") Long PRODUCTATTRIBUTE_ID, @Param("PRODUCTATTRIBUTE_IDS") List<Integer> PRODUCTATTRIBUTE_IDS,
            @Param("ATTRIBUTEVALUE_ID") Long ATTRIBUTEVALUE_ID, @Param("ATTRIBUTEVALUE_IDS") List<Integer> ATTRIBUTEVALUE_IDS);
    
	@Query(value = "select * from TBLPRODUCTATTRIBUTEVALUE   "
            + "where CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
			+ "where CASE WHEN :PRODUCTATTRIBUTE_ID = 0 THEN PRODUCTATTRIBUTE_ID=PRODUCTATTRIBUTE_ID ELSE PRODUCTATTRIBUTE_ID IN (:PRODUCTATTRIBUTE_IDS) END "
			+ "where CASE WHEN :ATTRIBUTEVALUE_ID = 0 THEN ATTRIBUTEVALUE_ID=ATTRIBUTEVALUE_ID ELSE ATTRIBUTEVALUE_ID IN (:ATTRIBUTEVALUE_IDS) END "
			, nativeQuery = true)
	List<ProductAttributeValue> findAllByAdvancedSearch(
    		@Param("PRODUCT_IDS") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS,
		    @Param("ATTRIBUTEVALUE_IDS") Long ATTRIBUTEVALUE_ID, @Param("ATTRIBUTEVALUE_IDS") List<Integer> ATTRIBUTEVALUE_IDS,
		    @Param("PRODUCTATTRIBUTE_IDS") Long PRODUCTATTRIBUTE_ID, @Param("PRODUCTATTRIBUTE_IDS") List<Integer> PRODUCTATTRIBUTE_IDS); 

}

