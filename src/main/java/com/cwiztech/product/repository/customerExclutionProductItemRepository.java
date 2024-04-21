package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwiztech.product.model.CustomerExclutionProductItem;


public interface customerExclutionProductItemRepository extends JpaRepository<CustomerExclutionProductItem, Long>{
	@Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM where ISACTIVE in ('Y','N')", nativeQuery = true)
	public List<CustomerExclutionProductItem> findAll();
	
	@Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM where ISACTIVE='Y'", nativeQuery = true)
	public List<CustomerExclutionProductItem> findActive();
	
	@Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM "
			+ "where CUSTOMEREXCLUTIONPRODUCTITEM_ID=?1 ", nativeQuery = true)
	public CustomerExclutionProductItem findOne(Long id);
	
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
			+ "where CUSTOMEREXCLUTIONPRODUCTITEM_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<CustomerExclutionProductItem> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLCUSTOMEREXCLUTIONPRODUCTITEM "
			+ "where CUSTOMEREXCLUTIONPRODUCTITEM_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<CustomerExclutionProductItem> findByNotInIDs(@Param("ids") List<Integer> ids);
	
}
