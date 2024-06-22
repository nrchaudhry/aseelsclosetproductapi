package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItemApplication;

public interface productItemApplicationRepository extends JpaRepository<ProductItemApplication, Long>{
	
	@Query(value = "select * from TBLPRODUCTITEMAPPLICATION where ISACTIVE='Y'" , nativeQuery = true)
	public List<ProductItemApplication> findActive();

    @Query(value = "select a.* from TBLPRODUCTITEMAPPLICATION as a "
            + "where CASE WHEN :APPLICATION_ID = 0 THEN APPLICATION_ID=APPLICATION_ID ELSE APPLICATION_ID IN (:APPLICATION_IDS) END "
            + "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
			+ "and a.ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemApplication> findByAdvancedSearch(
		    @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS,
		    @Param("APPLICATION_ID") Long APPLICATION_ID, @Param("APPLICATION_IDS") List<Integer> APPLICATION_IDS);

	@Query(value = "select a.* from TBLPRODUCTITEMAPPLICATION as a "
			+ "where CASE WHEN :APPLICATION_ID = 0 THEN APPLICATION_ID=APPLICATION_ID ELSE APPLICATION_ID IN (:APPLICATION_IDS) END "
			+ "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
			, nativeQuery = true)
	List<ProductItemApplication> findAllByAdvancedSearch(
    @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS,
    @Param("APPLICATION_ID") Long APPLICATION_ID, @Param("APPLICATION_IDS") List<Integer> APPLICATION_IDS);
	
	@Query(value = "select * from TBLPRODUCTITEMAPPLICATION "
			+ "where PRODUCTITEMAPPLICATION_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemApplication> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMAPPLICATION "
			+ "where PRODUCTITEMAPPLICATION_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemApplication> findByNotInIDs(@Param("ids") List<Integer> ids);
}

