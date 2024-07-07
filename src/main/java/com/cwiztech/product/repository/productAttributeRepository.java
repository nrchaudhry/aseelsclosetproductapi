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
            + "where (CASE WHEN :ATTRIBUTE_ID = 0 THEN ATTRIBUTE_ID=ATTRIBUTE_ID ELSE ATTRIBUTE_ID IN (:ATTRIBUTE_IDS) END or ATTRIBUTE_ID is NULL) "
            + "and (CASE WHEN :PRODUCTCATEGORY_ID = 0 THEN PRODUCTCATEGORY_ID=PRODUCTCATEGORY_ID ELSE PRODUCTCATEGORY_ID IN (:PRODUCTCATEGORY_IDS) END or PRODUCTCATEGORY_ID is NULL) "
            + "and (CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END or PRODUCT_ID is NULL) "
            + "and (CASE WHEN :ATTRIBUTECATEGORY_ID = 0 THEN ATTRIBUTECATEGORY_ID=ATTRIBUTECATEGORY_ID ELSE ATTRIBUTECATEGORY_ID IN (:ATTRIBUTECATEGORY_IDS) END or ATTRIBUTECATEGORY_ID is NULL) "
			+ "and ISACTIVE='Y' order by ATTRIBUTEORDER_NO ", nativeQuery = true)
    public List<ProductAttribute> findByAdvancedSearch(
		    @Param("ATTRIBUTE_ID") Long ATTRIBUTE_ID, @Param("ATTRIBUTE_IDS") List<Integer> ATTRIBUTE_IDS,
		    @Param("PRODUCTCATEGORY_ID") Long PRODUCTCATEGORY_ID, @Param("PRODUCTCATEGORY_IDS") List<Integer> PRODUCTCATEGORY_IDS,
    		@Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS,
    		@Param("ATTRIBUTECATEGORY_ID") Long ATTRIBUTECATEGORY_ID, @Param("ATTRIBUTECATEGORY_IDS") List<Integer> ATTRIBUTECATEGORY_IDS);

	@Query(value = "select * from TBLPRODUCTATTRIBUTE "
            + "where (CASE WHEN :ATTRIBUTE_ID = 0 THEN ATTRIBUTE_ID=ATTRIBUTE_ID ELSE ATTRIBUTE_ID IN (:ATTRIBUTE_IDS) END or ATTRIBUTE_ID is NULL) "
            + "and (CASE WHEN :PRODUCTCATEGORY_ID = 0 THEN PRODUCTCATEGORY_ID=PRODUCTCATEGORY_ID ELSE PRODUCTCATEGORY_ID IN (:PRODUCTCATEGORY_IDS) END or PRODUCTCATEGORY_ID is NULL) "
            + "and (CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END or PRODUCT_ID is NULL) "
            + "and (CASE WHEN :ATTRIBUTECATEGORY_ID = 0 THEN ATTRIBUTECATEGORY_ID=ATTRIBUTECATEGORY_ID ELSE ATTRIBUTECATEGORY_ID IN (:ATTRIBUTECATEGORY_IDS) END or ATTRIBUTECATEGORY_ID is NULL) "
			, nativeQuery = true)
	public List<ProductAttribute> findAllByAdvancedSearch(
		    @Param("ATTRIBUTE_ID") Long ATTRIBUTE_ID, @Param("ATTRIBUTE_IDS") List<Integer> ATTRIBUTE_IDS,
		    @Param("PRODUCTCATEGORY_ID") Long PRODUCTCATEGORY_ID, @Param("PRODUCTCATEGORY_IDS") List<Integer> PRODUCTCATEGORY_IDS,
    		@Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS,
    		@Param("ATTRIBUTECATEGORY_ID") Long ATTRIBUTECATEGORY_ID, @Param("ATTRIBUTECATEGORY_IDS") List<Integer> ATTRIBUTECATEGORY_IDS);

	@Query(value = "select * from TBLPRODUCTATTRIBUTE "
			+ "where PRODUCTATTRIBUTE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductAttribute> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTATTRIBUTE "
			+ "where PRODUCTATTRIBUTE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductAttribute> findByNotInIDs(@Param("ids") List<Integer> ids);
}



