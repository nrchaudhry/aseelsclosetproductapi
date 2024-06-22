package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItemPriceChange;

public interface productItemPriceChangeRepository extends JpaRepository<ProductItemPriceChange, Long>{
	@Query(value = "select * from TBLPRODUCTITEMPRICECHANGE where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemPriceChange> findActive();
	
	@Query(value = "select * from TBLPRODUCTITEMPRICECHANGE "
			+ "where PRODUCTITEMPRICECHANGE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemPriceChange> findByIDs(@Param("ids") List<Integer> ids);
	
	@Query(value = "select * from TBLPRODUCTITEMPRICECHANGE "
			+ "where PRODUCTITEMPRICECHANGE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemPriceChange> findByNotInIDs(@Param("ids") List<Integer> ids);
	
	@Query(value = "select * from TBLPRODUCTITEMPRICECHANGE "
			+ "where PRODUCTITEMPRICECHANGE_ID=?1 ", nativeQuery = true)
	public ProductItemPriceChange findOne(Long id);
	
	@Query(value = "select * from TBLPRODUCTITEMPRICECHANGE "
			+ "where PRODUCTITEM_PURCHASEPRICE like ?1 or  PRODUCTITEM_LASTPURCHASEPRICE like ?1 and ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemPriceChange> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTITEMPRICECHANGE "
			+ "where PRODUCTITEM_PURCHASEPRICE like ?1 or  PRODUCTITEM_LASTPURCHASEPRICE like ?1 ", nativeQuery = true)
	public List<ProductItemPriceChange> findAllBySearch(String search);
	
	@Query(value = "select * from TBLPRODUCTITEMPRICECHANGE " 
            + "where CASE WHEN :CURRENCY_ID = 0 THEN CURRENCY_ID=CURRENCY_ID ELSE CURRENCY_ID IN (:CURRENCY_IDS) END "
            + "and CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
		    + "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemPriceChange> findByAdvancedSearch(
		    @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS,
		    @Param("CURRENCY_ID") Long PRICELEVEL_ID, @Param("CURRENCY_IDS") List<Integer> PRICELEVEL_IDS);
	
	@Query(value = "select * from TBLPRODUCTITEMPRICECHANGE " 
			+ "where CASE WHEN :CURRENCY_ID = 0 THEN CURRENCY_ID=CURRENCY_ID ELSE CURRENCY_ID IN (:CURRENCY_IDS) END "
			+ "and CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
		    + "", nativeQuery = true)
	List<ProductItemPriceChange> findAllByAdvancedSearch(
            @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS,
            @Param("CURRENCY_ID") Long PRICELEVEL_ID, @Param("CURRENCY_IDS") List<Integer> PRICELEVEL_IDS);

}
