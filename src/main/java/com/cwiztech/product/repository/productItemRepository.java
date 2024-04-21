package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.Product;
import com.cwiztech.product.model.ProductItem;
import com.cwiztech.product.model.ProductItemAttributeValue;

public interface productItemRepository extends JpaRepository<ProductItem, Long>{
	
	@Query(value = "select * from TBLPRODUCTITEM where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItem> findActive();
	
	@Query(value = "select a.* from TBLPRODUCTITEM as a "
			+ "inner join TBLPRODUCT as b on a.PRODUCT_ID=b.PRODUCT_ID "
			+ "where NETSUITE_ID=?1 "
			+ "", nativeQuery = true)
	List<ProductItem> findByNetSuiteID(Long id);

	@Query(value = "select a.* from TBLPRODUCTITEM as a "
			+ "inner join TBLPRODUCT as b on a.PRODUCT_ID=b.PRODUCT_ID "
			+ "where NETSUITE_ID in (:ids) "
			+ "", nativeQuery = true)
	List<ProductItem> findByNetSuiteIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEM "
			+ "where PRODUCTITEM_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItem> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEM "
			+ "where PRODUCTITEM_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItem> findByNotInIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select a.* from TBLPRODUCTITEM  as a "
			+ "inner join TBLPRODUCT as b on a.PRODUCT_ID=b.PRODUCT_ID "
			+ "where PRODUCT_CODE=?1 "
			+ "", nativeQuery = true)
	public List<ProductItem> findByCode(String code);

	@Query(value = "select a.* from TBLPRODUCTITEM  as a "
			+ "inner join TBLPRODUCT as b on a.PRODUCT_ID=b.PRODUCT_ID "
			+ "where PRODUCT_NEWCODE=?1 "
			+ "", nativeQuery = true)
	public List<ProductItem> findByNewCode(String code);

	@Query(value = "select a.* from TBLPRODUCTITEM  as a "
			+ "inner join TBLPRODUCT as b on a.PRODUCT_ID=b.PRODUCT_ID "
			+ "where PRODUCT_CODE in (:codes) "
			+ "", nativeQuery = true)
	public List<ProductItem> findByCodes(@Param("codes") List<String> codes);

	@Query(value = "select a.* from TBLPRODUCTITEM  as a "
			+ "inner join TBLPRODUCT as b on a.PRODUCT_ID=b.PRODUCT_ID "
			+ "where PRODUCT_NEWCODE in (:codes) "
			+ "", nativeQuery = true)
	public List<ProductItem> findByNewCodes(@Param("codes") List<String> codes);

	@Query(value = "select * from TBLPRODUCTITEM "
			+ "where PRODUCTITEM_NAME like ?1 or  PRODUCTITEM_DESC like ?1 and ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItem> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTITEM "
			+ "where PRODUCTITEM_NAME like ?1 or  PRODUCTITEM_DESC like ?1 ", nativeQuery = true)
	public List<ProductItem> findAllBySearch(String search);
	
	@Query(value = "select a.* from TBLPRODUCTITEM  as a "
			+ "where APPLICATION_ID like CASE WHEN ?2=0 THEN APPLICATION_ID ELSE ?2 END "
			+ "and a.PRODUCT_ID like CASE WHEN ?1=0 THEN a.PRODUCT_ID ELSE ?1 END "
			+ "and a.ISACTIVE='Y'", nativeQuery = true)
	List<ProductItem> findByAdvancedSearch(Long productID,Long application_ID);

	@Query(value = "select a.* from TBLPRODUCTITEM  as a " 
			+ "where APPLICATION_ID like CASE WHEN ?2=0 THEN APPLICATION_ID ELSE ?2 END "
			+ "and a.PRODUCT_ID like CASE WHEN ?1=0 THEN a.PRODUCT_ID ELSE ?1 END "
			+ "", nativeQuery = true)
	List<ProductItem> findAllByAdvancedSearch(Long productID, Long application_ID);

}
