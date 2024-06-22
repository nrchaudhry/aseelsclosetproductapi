package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductImage;

public interface productImageRepository extends JpaRepository<ProductImage, Long>{
	@Query(value = "select * from TBLPRODUCTIMAGE where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductImage>findActive();
	
	@Query(value = "select * from TBLPRODUCTIMAGE  " 
            + "where CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
			+ "and a.ISACTIVE='Y'", nativeQuery = true)
	List<ProductImage> findByAdvancedSearch(
    @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS); 

	@Query(value = "select * from TBLPRODUCTIMAGE "
			+ "where CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
			, nativeQuery = true)
	List<ProductImage> findAllByAdvancedSearch(
    @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS); 

	@Query(value = "select * from TBLPRODUCTIMAGE "
			+ "where PRODUCTIMAGE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductImage> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTIMAGE "
			+ "where PRODUCTIMAGE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductImage> findByNotInIDs(@Param("ids") List<Integer> ids);

}
