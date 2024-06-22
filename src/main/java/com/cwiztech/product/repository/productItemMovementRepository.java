package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItemMovement;

public interface productItemMovementRepository extends JpaRepository<ProductItemMovement, Long> {

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT where ISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemMovement> findActive();

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT "
			+ "inner join TBLPRODUCT as b onPRODUCT_ID=b.PRODUCT_ID "
			+ "where PRODUCT_CODE like ?1 "
			+ "andISACTIVE='Y'", nativeQuery = true)
	public List<ProductItemMovement> findBySearch(String search);

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT "
			+ "inner join TBLPRODUCT as b onPRODUCT_ID=b.PRODUCT_ID "
			+ "where PRODUCT_CODE like ?1 "
			+ "", nativeQuery = true)
	public List<ProductItemMovement> findAllBySearch(String search);
	
	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT "
            + "where CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
            + "and CASE WHEN :EMPLOYEE_ID = 0 THEN EMPLOYEE_ID=EMPLOYEE_ID ELSE EMPLOYEE_ID IN (:EMPLOYEE_IDS) END "
            + "andISACTIVE='Y'", nativeQuery = true)
	List<ProductItemMovement> findByAdvancedSearch(   
			@Param("EMPLOYEE_ID") Long EMPLOYEE_ID, @Param("EMPLOYEE_IDS") List<Integer> EMPLOYEE_IDS,
		    @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS); 

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT " 
			+ "where CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
			+ "and CASE WHEN :EMPLOYEE_ID = 0 THEN EMPLOYEE_ID=EMPLOYEE_ID ELSE EMPLOYEE_ID IN (:EMPLOYEE_IDS) END "
		    + "", nativeQuery = true)
	List<ProductItemMovement> findAllByAdvancedSearch(   
			@Param("EMPLOYEE_ID") Long EMPLOYEE_ID, @Param("EMPLOYEE_IDS") List<Integer> EMPLOYEE_IDS,
		    @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS); 

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT "
			+ "where PRODUCTITEMMOVEMENT_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemMovement> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLPRODUCTITEMMOVEMENT "
			+ "where PRODUCTITEMMOVEMENT_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<ProductItemMovement> findByNotInIDs(@Param("ids") List<Integer> ids);

}
