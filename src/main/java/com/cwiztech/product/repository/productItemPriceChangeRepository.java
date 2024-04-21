package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItem;
import com.cwiztech.product.model.ProductItemPriceChange;
import com.cwiztech.product.model.ProductItemPriceLevel;

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
	
	@Query(value = "select a.* from TBLPRODUCTITEMPRICELEVEL as a " 
			+ "and a.CURRENCY_ID LIKE CASE WHEN ?1 = 0 THEN a.CURRENCY_ID ELSE ?1 END or a.CURRENCY_ID is NULL "
			+ "and a.PRODUCTITEM_ID LIKE CASE WHEN ?2 = 0 THEN a.PRODUCTITEM_ID ELSE ?2 END "
		    + "and a.ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemPriceChange> findByAdvancedSearch(Long cid, Long piid);
	
	@Query(value = "select a.* from TBLPRODUCTITEMPRICELEVEL as a " 
			+ "and a.CURRENCY_ID LIKE CASE WHEN ?1 = 0 THEN a.CURRENCY_ID ELSE ?1 END or a.CURRENCY_ID is NULL "
			+ "and a.PRODUCTITEM_ID LIKE CASE WHEN ?2 = 0 THEN a.PRODUCTITEM_ID ELSE ?2 END "
		    + "", nativeQuery = true)
	List<ProductItemPriceChange> findAllByAdvancedSearch(Long cid, Long piid);

}
