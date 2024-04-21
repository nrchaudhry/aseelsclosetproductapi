package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItem;
import com.cwiztech.product.model.ProductItemMovement;

public interface productItemMovementRepository extends JpaRepository<ProductItemMovement, Long> {

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemMovement> findActive();

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT as a "
			+ "inner join TBLPRODUCT as b on a.PRODUCT_ID=b.PRODUCT_ID "
			+ "where PRODUCT_CODE like ?1 "
			+ "and a.ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemMovement> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT as a "
			+ "inner join TBLPRODUCT as b on a.PRODUCT_ID=b.PRODUCT_ID "
			+ "where PRODUCT_CODE like ?1 "
			+ "", nativeQuery = true)
	public List<ProductItemMovement> findAllBySearch(String search);

	
	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT as a "
			+ "where a.PRODUCT_ID LIKE CASE WHEN ?1 = 0 THEN a.PRODUCT_ID ELSE ?1 END "
			+ "and a.PRODUCTITEMMOVEMENT_DATE LIKE CASE WHEN ?2= 0 THEN a.PRODUCTITEMMOVEMENT_DATE ELSE ?3 END "
            + "and (a.ASSIGNTOEMPLOYEE_DATETIME LIKE CASE WHEN ?4 = 0 THEN a.ASSIGNTOEMPLOYEE_DATETIME ELSE ?5 END or a.ASSIGNTOEMPLOYEE_DATETIME is NULL) "
            + "and (a.EMPLOYEE_ID LIKE CASE WHEN ?6 = 0 THEN a.EMPLOYEE_ID ELSE ?6 END or a.EMPLOYEE_ID is NULL) "
            + "and (a.PRODUCTITEMMOVEMENT_DATETIME LIKE CASE WHEN ?7 = 0 THEN a.PRODUCTITEMMOVEMENT_DATETIME ELSE ?8 END or a.PRODUCTITEMMOVEMENT_DATETIME is NULL) "
            + "and a.ISACTIVE='Y'", nativeQuery = true)
	List<ProductItemMovement> findByAdvancedSearch(Long pid,Long pimdid, String productmovementdate,Long ated,String assigntoemployeedate,
			Long eid,Long pimdtid,String productmovementdatetime);
	
	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT as a " 
			+ "where a.PRODUCT_ID LIKE CASE WHEN ?1 = 0 THEN a.PRODUCT_ID ELSE ?1 END "
			+ "and a.PRODUCTITEMMOVEMENT_DATE LIKE CASE WHEN ?2= 0 THEN a.PRODUCTITEMMOVEMENT_DATE ELSE ?3 END "
            + "and (a.ASSIGNTOEMPLOYEE_DATETIME LIKE CASE WHEN ?4 = 0 THEN a.ASSIGNTOEMPLOYEE_DATETIME ELSE ?5 END or a.ASSIGNTOEMPLOYEE_DATETIME is NULL) "
            + "and (a.EMPLOYEE_ID LIKE CASE WHEN ?6 = 0 THEN a.EMPLOYEE_ID ELSE ?6 END or a.EMPLOYEE_ID is NULL) "
            + "and (a.PRODUCTITEMMOVEMENT_DATETIME LIKE CASE WHEN ?7 = 0 THEN a.PRODUCTITEMMOVEMENT_DATETIME ELSE ?8 END or a.PRODUCTITEMMOVEMENT_DATETIME is NULL) "
		    + "", nativeQuery = true)
	List<ProductItemMovement> findAllByAdvancedSearch(Long product_ID,Long pimdid, String productmovementdate,Long ated,String assigntoemployeedate,
			Long eid,Long pimdtid,String productmovementdatetime);
	

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT "
			+ "where PRODUCTITEMMOVEMENT_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemMovement> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT "
			+ "where PRODUCTITEMMOVEMENT_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemMovement> findByNotInIDs(@Param("ids") List<Integer> ids);

}
