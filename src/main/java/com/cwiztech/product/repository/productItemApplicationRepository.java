package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItem;
import com.cwiztech.product.model.ProductItemApplication;

public interface productItemApplicationRepository extends JpaRepository<ProductItemApplication, Long>{
	
	@Query(value = "select * from TBLPRODUCTITEMAPPLICATION where ISACTIVE='Y'" , nativeQuery = true)
	public List<ProductItemApplication> findActive();

	@Query(value = "select a.* from TBLPRODUCTITEMAPPLICATION as a "
			+ "where APPLICATION_ID like CASE WHEN ?1=0 THEN APPLICATION_ID ELSE ?1 END "
			+ "and PRODUCTITEM_ID like CASE WHEN ?2=0 THEN PRODUCTITEM_ID ELSE ?2 END "
			+ "and a.ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemApplication> findByAdvancedSearch(Long application_ID, Long productitem_ID);

	@Query(value = "select a.* from TBLPRODUCTITEMAPPLICATION as a "
			+ "where APPLICATION_ID like CASE WHEN ?1=0 THEN APPLICATION_ID ELSE ?1 END "
			+ "and PRODUCTITEM_ID like CASE WHEN ?2=0 THEN PRODUCTITEM_ID ELSE ?2 END "
			, nativeQuery = true)
	List<ProductItemApplication> findAllByAdvancedSearch(Long application_ID, Long productitem_ID );

	@Query(value = "select * from TBLPRODUCTITEMAPPLICATION "
			+ "where PRODUCTITEMAPPLICATION_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemApplication> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMAPPLICATION "
			+ "where PRODUCTITEMAPPLICATION_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemApplication> findByNotInIDs(@Param("ids") List<Integer> ids);
}

