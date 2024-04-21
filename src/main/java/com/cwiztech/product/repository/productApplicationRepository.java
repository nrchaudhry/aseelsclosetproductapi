package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwiztech.product.model.ProductApplication;


public interface productApplicationRepository extends JpaRepository<ProductApplication, Long>{
	
	@Query(value = "select * from TBLPRODUCTAPPLICATION where ISACTIVE='Y'" , nativeQuery = true)
	public List<ProductApplication> findActive();

	@Query(value = "select * from TBLPRODUCTAPPLICATION "
			+ "where APPLICATION_ID like CASE WHEN ?1=0 THEN APPLICATION_ID ELSE ?1 END or APPLICATION_ID is NULL "
			+ "and PRODUCT_ID like CASE WHEN ?2=0 THEN PRODUCT_ID ELSE ?2 END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductApplication> findByAdvancedSearch(Long application_ID, Long product_ID);

	@Query(value = "select * from TBLPRODUCTAPPLICATION  "
			+ "where APPLICATION_ID like CASE WHEN ?1=0 THEN APPLICATION_ID ELSE ?1 END or APPLICATION_ID is NULL "
			+ "and PRODUCT_ID like CASE WHEN ?2=0 THEN PRODUCT_ID ELSE ?2 END "
			, nativeQuery = true)
	List<ProductApplication> findAllByAdvancedSearch(Long application_ID, Long product_ID);
	
	@Query(value = "select * from TBLPRODUCTAPPLICATION "
			+ "where PRODUCTAPPLICATION_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductApplication> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTAPPLICATION "
			+ "where PRODUCTAPPLICATION_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductApplication> findByNotInIDs(@Param("ids") List<Integer> ids);

}

