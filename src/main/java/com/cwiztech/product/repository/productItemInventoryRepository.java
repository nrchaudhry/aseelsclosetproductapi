package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
            + "and CASE WHEN :LOCATION_ID = 0 THEN LOCATION_ID=LOCATION_ID ELSE LOCATION_ID IN (:LOCATION_IDS) END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemInventory> findByAdvancedSearch(
	   @Param("LOCATION_ID") Long LOCATION_ID, @Param("LOCATION_IDS") List<Integer> LOCATION_IDS,
	    @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS); 

	@Query(value = "select * from TBLPRODUCTITEMINVENTORY  "
			+ "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
			+ "and CASE WHEN :LOCATION_ID = 0 THEN LOCATION_ID=LOCATION_ID ELSE LOCATION_ID IN (:LOCATION_IDS) END "
			+ "", nativeQuery = true)
	List<ProductItemInventory> findAllByAdvancedSearch(
	   @Param("LOCATION_ID") Long LOCATION_ID, @Param("LOCATION_IDS") List<Integer> LOCATION_IDS,
	    @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS); 

	@Query(value = "select * from TBLPRODUCTITEMINVENTORY "
			+ "where PRODUCTITEMINVENTORY_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemInventory> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMINVENTORY "
			+ "where PRODUCTITEMINVENTORY_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemInventory> findByNotInIDs(@Param("ids") List<Integer> ids);	

}

