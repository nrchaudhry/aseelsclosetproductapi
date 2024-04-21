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

	@Query(value = "select a.* from TBLPRODUCTITEMINVENTORY as a "
			+ "where (a.LOCATION_ID like CASE WHEN ?1=0 THEN a.LOCATION_ID ELSE ?1 END or a.LOCATION_ID is NULL) "
			+ "and (a.INVENTORYCLASSIFICTION_ID LIKE CASE WHEN ?2= 0 THEN a.INVENTORYCLASSIFICTION_ID ELSE ?2 END or a.INVENTORYCLASSIFICTION_ID is NULL) "
			+ "and (a.LASTCOUNT_DATE LIKE CASE WHEN ?3= 0 THEN a.LASTCOUNT_DATE ELSE ?4 END or a.LASTCOUNT_DATE is NULL) "
			+ "and (a.NECTCOUNT_DATE LIKE CASE WHEN ?5= 0 THEN a.NECTCOUNT_DATE ELSE ?6 END or a.NECTCOUNT_DATE is NULL) "
			+ "and a.PRODUCTITEM_ID like CASE WHEN ?7=0 THEN a.PRODUCTITEM_ID ELSE ?7 END "
			+ "and a.ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemInventory> findByAdvancedSearch(Long lid,Long icid, Long lcd, String lastcountdate, Long ncd, String nectcountdate, Long pitid);

	@Query(value = "select a.* from TBLPRODUCTITEMINVENTORY  as a "
			+ "where (a.LOCATION_ID like CASE WHEN ?1=0 THEN a.LOCATION_ID ELSE ?1 END or a.LOCATION_ID is NULL) "
			+ "and (a.INVENTORYCLASSIFICTION_ID LIKE CASE WHEN ?2= 0 THEN a.INVENTORYCLASSIFICTION_ID ELSE ?2 END or a.INVENTORYCLASSIFICTION_ID is NULL) "
			+ "and (a.LASTCOUNT_DATE LIKE CASE WHEN ?3= 0 THEN a.LASTCOUNT_DATE ELSE ?4 END or a.LASTCOUNT_DATE is NULL) "
			+ "and (a.NECTCOUNT_DATE LIKE CASE WHEN ?5= 0 THEN a.NECTCOUNT_DATE ELSE ?6 END or a.NECTCOUNT_DATE is NULL) "
			+ "and a.PRODUCTITEM_ID like CASE WHEN ?7=0 THEN a.PRODUCTITEM_ID ELSE ?7 END "
			+ "", nativeQuery = true)
	List<ProductItemInventory> findAllByAdvancedSearch(Long lid,Long icid, Long lcd, String lastcountdate, Long ncd, String nectcountdate, Long pitid);

	@Query(value = "select * from TBLPRODUCTITEMINVENTORY "
			+ "where PRODUCTITEMINVENTORY_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemInventory> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMINVENTORY "
			+ "where PRODUCTITEMINVENTORY_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemInventory> findByNotInIDs(@Param("ids") List<Integer> ids);	

}

