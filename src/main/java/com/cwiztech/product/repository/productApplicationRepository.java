package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwiztech.product.model.ProductApplication;


public interface productApplicationRepository extends JpaRepository<ProductApplication, Long>{
	
	@Query(value = "select * from TBLPRODUCTAPPLICATION where ISACTIVE='Y'" , nativeQuery = true)
	public List<ProductApplication> findActive();

    @Query(value = "select * from TBLPRODUCTAPPLICATION  "
            + "where CASE WHEN :APPLICATION_ID = 0 THEN APPLICATION_ID=APPLICATION_ID ELSE APPLICATION_ID IN (:APPLICATION_IDS) END "
            + "and CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductApplication> findByAdvancedSearch(
		    @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS,
		    @Param("APPLICATION_ID") Long APPLICATION_ID, @Param("APPLICATION_IDS") List<Integer> APPLICATION_IDS); 

	@Query(value = "select * from TBLPRODUCTAPPLICATION  "
			+ "where CASE WHEN :APPLICATION_ID = 0 THEN APPLICATION_ID=APPLICATION_ID ELSE APPLICATION_ID IN (:APPLICATION_IDS) END "
			+ "and CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
			, nativeQuery = true)
	List<ProductApplication> findAllByAdvancedSearch(
		    @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS,
		    @Param("APPLICATION_ID") Long APPLICATION_ID, @Param("APPLICATION_IDS") List<Integer> APPLICATION_IDS); 
	
	@Query(value = "select * from TBLPRODUCTAPPLICATION "
			+ "where PRODUCTAPPLICATION_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductApplication> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTAPPLICATION "
			+ "where PRODUCTAPPLICATION_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductApplication> findByNotInIDs(@Param("ids") List<Integer> ids);

}

