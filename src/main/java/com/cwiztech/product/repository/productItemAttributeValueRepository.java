package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItem;
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
			+ "where PRODUCTITEM_ID like CASE WHEN ?1=0 THEN  PRODUCTITEM_ID ELSE ?1 END "
			+ "and PRODUCTATTRIBUTE_ID like CASE WHEN ?2=0 THEN PRODUCTATTRIBUTE_ID ELSE ?2 END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemAttributeValue> findByAdvancedSearch(Long productitemID, Long productattributeID);

	@Query(value = "select * from TBLPRODUCTITEMATTRIBUTEVALUE "
			+ "where PRODUCTITEM_ID like CASE WHEN ?1=0 THEN  PRODUCTITEM_ID ELSE ?1 END "
			+ "and PRODUCTATTRIBUTE_ID like CASE WHEN ?2=0 THEN PRODUCTATTRIBUTE_ID ELSE ?2 END "
			+ "", nativeQuery = true)
	List<ProductItemAttributeValue> findAllByAdvancedSearch(Long productitemID, Long productattributeID);

	@Query(value = "select a.* from TBLPRODUCTITEMATTRIBUTEVALUE as a "
			+ "inner join TBLPRODUCTATTRIBUTE as b on a.PRODUCTATTRIBUTE_ID=b.PRODUCTATTRIBUTE_ID " 
			+ "where PRODUCTATTRIBUTE_KEY in ('lastpurchaseprice', 'taxschedule', 'custitemproducthold', 'baseunit') "
			+ "and a.ISACTIVE='Y' order by PRODUCTATTRIBUTEORDER_NO", nativeQuery = true)
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
