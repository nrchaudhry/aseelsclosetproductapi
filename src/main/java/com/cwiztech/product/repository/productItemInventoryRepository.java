package com.cwiztech.product.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItemInventory;


public interface productItemInventoryRepository extends JpaRepository<ProductItemInventory, Long>{
	
	@Query(value = "select * from TBLPRODUCTITEMINVENTORY where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemInventory> findActive();
	
	@Query(value = "select * from TBLPRODUCTITEMINVENTORY where PRODUCTITEM_ID=?1", nativeQuery = true)
	public ProductItemInventory findByProductItemId(Long piid);
	
	@Query(value = "select * from TBLPRODUCTITEMINVENTORY "
			+ "where PRODUCTITEM_NAME like ?1 or  PRODUCTITEM_DESC like ?1 and ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemInventory> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTITEMINVENTORY "
			+ "where PRODUCTITEM_NAME like ?1 or  PRODUCTITEM_DESC like ?1 ", nativeQuery = true)
	public List<ProductItemInventory> findAllBySearch(String search);

	@Query(value = "select * from TBLPRODUCTITEMINVENTORY "
            + "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
			+ "and (PRODUCTLOCATION_ID LIKE CASE WHEN :PRODUCTLOCATION_ID = 0 Then PRODUCTLOCATION_ID ELSE :PRODUCTLOCATION_ID END or PRODUCTLOCATION_ID is NULL) "
			+ "and (INVENTORYCLASSIFICTION_ID LIKE CASE WHEN :INVENTORYCLASSIFICTION_ID = 0 Then INVENTORYCLASSIFICTION_ID ELSE :INVENTORYCLASSIFICTION_ID END or INVENTORYCLASSIFICTION_ID is NULL) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	
	List<ProductItemInventory> findByAdvancedSearch(
	   @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS,
       @Param("PRODUCTLOCATION_ID") Long PRODUCTLOCATION_ID,
       @Param("INVENTORYCLASSIFICTION_ID") Long INVENTORYCLASSIFICTION_ID);

	@Query(value = "select * from TBLPRODUCTITEMINVENTORY  "
			+ "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
			+ "and (PRODUCTLOCATION_ID LIKE CASE WHEN :PRODUCTLOCATION_ID = 0 Then PRODUCTLOCATION_ID ELSE :PRODUCTLOCATION_ID END or PRODUCTLOCATION_ID is NULL) "
			+ "and (INVENTORYCLASSIFICTION_ID LIKE CASE WHEN :INVENTORYCLASSIFICTION_ID = 0 Then INVENTORYCLASSIFICTION_ID ELSE :INVENTORYCLASSIFICTION_ID END or INVENTORYCLASSIFICTION_ID is NULL) "
			+ "", nativeQuery = true)
	List<ProductItemInventory> findAllByAdvancedSearch(
			   @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS,
		       @Param("PRODUCTLOCATION_ID") Long PRODUCTLOCATION_ID,
		       @Param("INVENTORYCLASSIFICTION_ID") Long INVENTORYCLASSIFICTION_ID);

	@Query(value = "select * from TBLPRODUCTITEMINVENTORY "
			+ "where PRODUCTITEMINVENTORY_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemInventory> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMINVENTORY "
			+ "where PRODUCTITEMINVENTORY_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemInventory> findByNotInIDs(@Param("ids") List<Integer> ids);	

	@Modifying
	@Transactional
	@Query(value = "update TBLPRODUCTITEMINVENTORY set ISACTIVE=:active "
			+ "where PRODUCTITEM_ID=:itemid "
			+ "", nativeQuery = true)
	public void update(@Param("itemid") long itemid, @Param("active") String active);	
}

