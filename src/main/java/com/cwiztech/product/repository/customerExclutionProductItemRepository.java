package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.CustomerExclutionProductItem;


public interface customerExclutionProductItemRepository extends JpaRepository<CustomerExclutionProductItem, Long>{
	@Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM where ISACTIVE='Y'", nativeQuery = true)
	public List<CustomerExclutionProductItem> findActive();
	
	@Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM as a "
			+ "inner join TBLCUSTOMER as b on a.CUSTOMER_ID=b.CUSTOMER_ID "
			+ "inner join TBLPRODUCTITEM as c on a.PRODUCTITEM_ID=c.PRODUCTITEM_ID "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	public List<CustomerExclutionProductItem> findBySearch(String search);

	@Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM as a "
			+ "inner join TBLCUSTOMER as b on a.CUSTOMER_ID=b.CUSTOMER_ID "
			+ "inner join TBLPRODUCTITEM as c on a.PRODUCTITEM_ID=c.PRODUCTITEM_ID "
			, nativeQuery = true)
	public List<CustomerExclutionProductItem> findAllBySearch(String search);
	
    @Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM "
            + "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
            + "where CASE WHEN :CUSTOMER_ID = 0 THEN CUSTOMER_ID=CUSTOMER_ID ELSE CUSTOMER_ID IN (:CUSTOMER_IDS) END "
            + "and ISACTIVE='Y'", nativeQuery = true)
    public List<CustomerExclutionProductItem> findByAdvancedSearch(
    @Param("CUSTOMER_ID") Long CUSTOMER_ID, @Param("CUSTOMER_IDS") List<Integer> CUSTOMER_IDS,
    @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS); 

	@Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM "
			+ "where CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
			+ "where CASE WHEN :CUSTOMER_ID = 0 THEN CUSTOMER_ID=CUSTOMER_ID ELSE CUSTOMER_ID IN (:CUSTOMER_IDS) END "
					, nativeQuery = true)
	public List<CustomerExclutionProductItem> findAllByAdvancedSearch(
	@Param("CUSTOMER_ID") Long CUSTOMER_ID, @Param("CUSTOMER_IDS") List<Integer> CUSTOMER_IDS,
	@Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS); 
	
    @Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM "
            + "where CUSTOMEREXCLUTIONPRODUCTITEM_ID in (:ids) "
            + "", nativeQuery = true)
    public List<CustomerExclutionProductItem> findByIDs(@Param("ids") List<Integer> ids);
    	
	@Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM "
			+ "where CUSTOMEREXCLUTIONPRODUCTITEM_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<CustomerExclutionProductItem> findByNotInIDs(@Param("ids") List<Integer> ids);
	
}
