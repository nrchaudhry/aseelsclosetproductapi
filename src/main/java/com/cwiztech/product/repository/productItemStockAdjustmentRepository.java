package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItemStockAdjustment;

public interface productItemStockAdjustmentRepository extends JpaRepository<ProductItemStockAdjustment, Long> {
	@Query(value = "select * from tblproductitemstockadjustment where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemStockAdjustment> findActive();

	@Query(value = "select * from tblproductitemstockadjustment "
			+ "where PRODUCTITEMSTOCKADJUSTMENT_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemStockAdjustment> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from tblproductitemstockadjustment "
			+ "where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemStockAdjustment> findBySearch(String search);

	@Query(value = "select * from tblproductitemstockadjustment "
			+ "", nativeQuery = true)
	public List<ProductItemStockAdjustment> findAllBySearch(String search);

	@Query(value = "select * from tblproductitemstockadjustment " 
			+ "where (CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END or PRODUCTITEM_ID is NULL) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemStockAdjustment> findByAdvancedSearch(
			@Param("PRODUCTITEM_ID") Long productitem_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS);

	@Query(value = "select * from tblproductitemstockadjustment " 
			+ "where (CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END or PRODUCTITEM_ID is NULL) "
			+ "", nativeQuery = true)
	public List<ProductItemStockAdjustment> findAllByAdvancedSearch(   
			@Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS);

}


