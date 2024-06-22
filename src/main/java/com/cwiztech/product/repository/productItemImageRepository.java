package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItemImage;

public interface productItemImageRepository extends JpaRepository<ProductItemImage, Long>{
	
	@Query(value = "select * from TBLPRODUCTITEMIMAGE where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemImage>findActive();
	
	@Query(value = "select * from TBLPRODUCTITEMIMAGE " 
            + "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemImage> findByAdvancedSearch(
    @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS); 

	@Query(value = "select * from TBLPRODUCTITEMIMAGE "
			+ "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
			, nativeQuery = true)
	List<ProductItemImage> findAllByAdvancedSearch(
    @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS); 

	@Query(value = "select * from TBLPRODUCTITEMIMAGE "
			+ "where PRODUCTITEMIMAGE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemImage> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMIMAGE "
			+ "where PRODUCTITEMIMAGE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemImage> findByNotInIDs(@Param("ids") List<Integer> ids);
}
