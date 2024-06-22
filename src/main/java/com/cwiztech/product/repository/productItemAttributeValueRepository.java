package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItemAttributeValue;

public interface productItemAttributeValueRepository extends JpaRepository<ProductItemAttributeValue, Long>{
	@Query(value = "select * from TBLPRODUCTITEMATTRIBUTEVALUE where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemAttributeValue> findActive();
	
	@Query(value = "select * from TBLPRODUCTITEMATTRIBUTEVALUE "
			+ "where PRODUCTATTRIBUTE_VALUE like ?1  and ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemAttributeValue> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTITEMATTRIBUTEVALUE "
			+ "where PRODUCTATTRIBUTE_VALUE like ?1 ", nativeQuery = true)
	public List<ProductItemAttributeValue> findAllBySearch(String search);

	@Query(value = "select * from TBLPRODUCTITEMATTRIBUTEVALUE "
            + "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
            + "and (CASE WHEN :PRODUCTATTRIBUTE_ID = 0 THEN PRODUCTATTRIBUTE_ID=PRODUCTATTRIBUTE_ID ELSE PRODUCTATTRIBUTE_ID IN (:PRODUCTATTRIBUTE_IDS) END or PRODUCTATTRIBUTE_ID is NULL) "
            + "and (CASE WHEN :PRODUCTATTRIBUTEVALUE_ID = 0 THEN PRODUCTATTRIBUTEVALUE_ID=PRODUCTATTRIBUTEVALUE_ID ELSE PRODUCTATTRIBUTEVALUE_ID IN (:PRODUCTATTRIBUTEVALUE_IDS) END or PRODUCTATTRIBUTEVALUE_ID is NULL) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemAttributeValue> findByAdvancedSearch(
		    @Param("PRODUCTATTRIBUTE_ID") Long PRODUCTATTRIBUTE_ID, @Param("PRODUCTATTRIBUTE_IDS") List<Integer> PRODUCTATTRIBUTE_IDS,
		    @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS,
		    @Param("PRODUCTATTRIBUTEVALUE_ID") Long PRODUCTATTRIBUTEVALUE_ID, @Param("PRODUCTATTRIBUTEVALUE_IDS") List<Integer> PRODUCTATTRIBUTEVALUE_IDS); 

	@Query(value = "select * from TBLPRODUCTITEMATTRIBUTEVALUE "
            + "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
            + "and (CASE WHEN :PRODUCTATTRIBUTE_ID = 0 THEN PRODUCTATTRIBUTE_ID=PRODUCTATTRIBUTE_ID ELSE PRODUCTATTRIBUTE_ID IN (:PRODUCTATTRIBUTE_IDS) END or PRODUCTATTRIBUTE_ID is NULL) "
            + "and (CASE WHEN :PRODUCTATTRIBUTEVALUE_ID = 0 THEN PRODUCTATTRIBUTEVALUE_ID=PRODUCTATTRIBUTEVALUE_ID ELSE PRODUCTATTRIBUTEVALUE_ID IN (:PRODUCTATTRIBUTEVALUE_IDS) END or PRODUCTATTRIBUTEVALUE_ID is NULL) "
			+ "", nativeQuery = true)
	List<ProductItemAttributeValue> findAllByAdvancedSearch(
		    @Param("PRODUCTATTRIBUTE_ID") Long PRODUCTATTRIBUTE_ID, @Param("PRODUCTATTRIBUTE_IDS") List<Integer> PRODUCTATTRIBUTE_IDS,
		    @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS,
			@Param("PRODUCTATTRIBUTEVALUE_ID") Long PRODUCTATTRIBUTEVALUE_ID, @Param("PRODUCTATTRIBUTEVALUE_IDS") List<Integer> PRODUCTATTRIBUTEVALUE_IDS); 

	@Query(value = "select* from TBLPRODUCTITEMATTRIBUTEVALUE "
			+ "inner join TBLPRODUCTATTRIBUTE as b onPRODUCTATTRIBUTE_ID=b.PRODUCTATTRIBUTE_ID " 
			+ "where PRODUCTATTRIBUTE_KEY in ('lastpurchaseprice', 'taxschedule', 'custitemproducthold', 'baseunit') "
			+ "andISACTIVE='Y' order by PRODUCTATTRIBUTEORDER_NO", nativeQuery = true)
	List<ProductItemAttributeValue> findCustomAttributeValue();
	
	@Query(value = "select * from TBLPRODUCTITEMATTRIBUTEVALUE "
			+ "where PRODUCTITEMATTRIBUTEVALUE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemAttributeValue> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMATTRIBUTEVALUE "
			+ "where PRODUCTITEM_ID in (:ids) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemAttributeValue> findByProductItemIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMATTRIBUTEVALUE "
			+ "where PRODUCTITEMATTRIBUTEVALUE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemAttributeValue> findByNotInIDs(@Param("ids") List<Integer> ids);

}
