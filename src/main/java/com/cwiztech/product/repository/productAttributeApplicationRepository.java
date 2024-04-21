package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductAttributeApplication;
import com.cwiztech.product.model.ProductItem;

public interface productAttributeApplicationRepository extends JpaRepository<ProductAttributeApplication, Long>{
	
	@Query(value = "select * from TBLPRODUCTATTRIBUTEAPPLICATION where ISACTIVE='Y'" , nativeQuery = true)
	public List<ProductAttributeApplication> findActive();

	@Query(value = "select * from TBLPRODUCTATTRIBUTEAPPLICATION "
			+ "where APPLICATION_ID like CASE WHEN ?1=0 THEN APPLICATION_ID ELSE ?1 END "
			+ "and PRODUCTATTRIBUTE_ID like CASE WHEN ?2=0 THEN PRODUCTATTRIBUTE_ID ELSE ?2 END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductAttributeApplication> findByAdvancedSearch(Long application_ID, Long productattribute_ID);

	@Query(value = "select * from TBLPRODUCTATTRIBUTEAPPLICATION "
			+ "where APPLICATION_ID like CASE WHEN ?1=0 THEN APPLICATION_ID ELSE ?1 END "
			+ "and PRODUCTATTRIBUTE_ID like CASE WHEN ?2=0 THEN PRODUCTATTRIBUTE_ID ELSE ?2 END "
			, nativeQuery = true)
	List<ProductAttributeApplication> findAllByAdvancedSearch(Long application_ID, Long productattribute_ID);
	
	@Query(value = "select * from TBLPRODUCTATTRIBUTEAPPLICATION "
			+ "where PRODUCTATTRIBUTEAPPLICATION_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductAttributeApplication> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTATTRIBUTEAPPLICATION "
			+ "where PRODUCTATTRIBUTEAPPLICATION_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductAttributeApplication> findByNotInIDs(@Param("ids") List<Integer> ids);

}

