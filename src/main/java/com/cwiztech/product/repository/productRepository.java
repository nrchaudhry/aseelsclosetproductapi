package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.Product;

public interface productRepository extends JpaRepository<Product, Long>{
	@Query(value = "select * from TBLPRODUCT where ISACTIVE='Y'", nativeQuery = true)
	public List<Product> findActive();
	
	@Query(value = "select * from TBLPRODUCT where NETSUITE_ID=?1", nativeQuery = true)
	Product findByNetSuiteID(Long id);
	
	@Query(value = "select * from TBLPRODUCT where NETSUITE_ID is NULL and ISACTIVE='Y'", nativeQuery = true)
	public List<Product> findByNullNetSuiteID();
	
	@Query(value = "select * from TBLPRODUCT where QUICKBOOK_ID=?1", nativeQuery = true)
	public Product findByQuickBookID(String id);
	
	@Query(value = "select * from TBLPRODUCT where QUICKBOOK_ID is NULL and ISACTIVE='Y'", nativeQuery = true)
	public List<Product> findByNullQuickBookID();
	
	@Query(value = "select * from TBLPRODUCT where SAGE_ID=?1", nativeQuery = true)
	public Product findBySageID(String id);
	
	@Query(value = "select * from TBLPRODUCT where SAGE_ID is NULL and ISACTIVE='Y'", nativeQuery = true)
	public List<Product> findByNullSageID();
	
	@Query(value = "SELECT PRODUCT_CODE FROM TBLPRODUCT where PRODUCTCATEGORY_ID =?1 order by PRODUCT_CODE desc limit 1", nativeQuery = true)
	public String GenerateNewCode(Long pid);

	@Query(value = "select * from TBLPRODUCT where PRODUCT_CODE=?1", nativeQuery = true)
	public Product findByCode(String code);
	
	@Query(value = "select * from TBLPRODUCT where PRODUCT_NEWCODE=?1", nativeQuery = true)
	public Product findByNewCode(String code);
	
	@Query(value = "select distinct * from TBLPRODUCT "
			+ "left outer join TBLPRODUCTAPPLICATION as b on PRODUCT_ID=b.PRODUCT_ID "
			+ "where APPLICATION_ID LIKE CASE WHEN ?1 = 0 THEN APPLICATION_ID ELSE ?1 END " 
			+ "and (PRODUCT_NAME LIKE ?2 "
			+ "OR PRODUCT_DESC LIKE ?2) "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<Product> findBySearch(Long applicationID, String search);

	@Query(value = "select distinct * from TBLPRODUCT "
			+ "left outer join TBLPRODUCTAPPLICATION as b on PRODUCT_ID=b.PRODUCT_ID "
			+ "where APPLICATION_ID LIKE CASE WHEN ?1 = 0 THEN APPLICATION_ID ELSE ?1 END " 
			+ "and (PRODUCT_NAME LIKE ?2 "
			+ "OR PRODUCT_DESC LIKE ?2) ", nativeQuery = true)
	public List<Product> findAllBySearch(Long applicationID, String search);

	@Query(value = "select * from TBLPRODUCT "
			+ "where (CASE WHEN :PRODUCTCATEGORY_ID = 0 THEN PRODUCTCATEGORY_ID=PRODUCTCATEGORY_ID ELSE PRODUCTCATEGORY_ID IN (:PRODUCTCATEGORY_IDS) END or PRODUCTCATEGORY_ID is NULL) "
			+ " and ISACTIVE='Y' order by PRODUCT_NAME", nativeQuery = true)
	
	List<Product> findByAdvancedSearch(
			@Param("PRODUCTCATEGORY_ID") Long PRODUCTCATEGORY_ID, @Param("PRODUCTCATEGORY_IDS") List<Integer> PRODUCTCATEGORY_IDS);  

	@Query(value = "select distinct * from TBLPRODUCT "
			+ "where (CASE WHEN :PRODUCTCATEGORY_ID = 0 THEN PRODUCTCATEGORY_ID=PRODUCTCATEGORY_ID ELSE PRODUCTCATEGORY_ID IN (:PRODUCTCATEGORY_IDS) END or PRODUCTCATEGORY_ID is NULL) "
			+ "order by PRODUCT_NAME", nativeQuery = true)
	List<Product> findAllByAdvancedSearch(
    @Param("PRODUCTCATEGORY_ID") Long PRODUCTCATEGORY_ID, @Param("PRODUCTCATEGORY_IDS") List<Integer> PRODUCTCATEGORY_IDS); 

	@Query(value = "select * from TBLPRODUCT "
			+ "where PRODUCT_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<Product> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCT "
			+ "where PRODUCT_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<Product> findByNotInIDs(@Param("ids") List<Integer> ids);

}
