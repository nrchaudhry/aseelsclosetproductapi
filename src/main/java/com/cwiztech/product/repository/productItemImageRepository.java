package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItem;
import com.cwiztech.product.model.ProductItemImage;

public interface productItemImageRepository extends JpaRepository<ProductItemImage, Long>{
	
	@Query(value = "select * from TBLPRODUCTITEMIMAGE where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemImage>findActive();
	
	@Query(value = "select * from TBLPRODUCTITEMIMAGE as a " 
			+ "where a.PRODUCTITEM_ID like CASE WHEN ?1=0 THEN a.PRODUCTITEM_ID ELSE ?1 END "
			+ "and a.ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemImage> findByAdvancedSearch(Long productitemID);

	@Query(value = "select * from TBLPRODUCTITEMIMAGE as a "
			+ "where a.PRODUCTITEM_ID like CASE WHEN ?1=0 THEN a.PRODUCTITEM_ID ELSE ?1 END "
			, nativeQuery = true)
	List<ProductItemImage> findAllByAdvancedSearch(Long productitemID);

	@Query(value = "select * from TBLPRODUCTITEMIMAGE "
			+ "where PRODUCTITEMIMAGE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemImage> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMIMAGE "
			+ "where PRODUCTITEMIMAGE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemImage> findByNotInIDs(@Param("ids") List<Integer> ids);
}
