package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductImage;
import com.cwiztech.product.model.ProductItem;

public interface productImageRepository extends JpaRepository<ProductImage, Long>{
	
	@Query(value = "select * from TBLPRODUCTIMAGE where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductImage>findActive();
	
	@Query(value = "select * from TBLPRODUCTIMAGE  as a " 
			+ "where a.PRODUCT_ID like CASE WHEN ?1=0 THEN a.PRODUCT_ID ELSE ?1 END "
			+ "and a.ISACTIVE='Y'", nativeQuery = true)
	List<ProductImage> findByAdvancedSearch(Long productID);

	@Query(value = "select * from TBLPRODUCTIMAGE as a "
			+ "where a.PRODUCT_ID like CASE WHEN ?1=0 THEN a.PRODUCT_ID ELSE ?1 END "
			, nativeQuery = true)
	List<ProductImage> findAllByAdvancedSearch(Long productID);
	
	@Query(value = "select * from TBLPRODUCTIMAGE "
			+ "where PRODUCTIMAGE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductImage> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTIMAGE "
			+ "where PRODUCTIMAGE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductImage> findByNotInIDs(@Param("ids") List<Integer> ids);

}
