package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductAttributeApplication;

public interface productAttributeApplicationRepository extends JpaRepository<ProductAttributeApplication, Long>{
	@Query(value = "select * from TBLPRODUCTATTRIBUTEAPPLICATION where ISACTIVE='Y'" , nativeQuery = true)
	public List<ProductAttributeApplication> findActive();

    @Query(value = "select * from TBLPRODUCTATTRIBUTEAPPLICATION "
            + "where PRODUCTATTRIBUTEAPPLICATION_ID in (:ids) "
            + "", nativeQuery = true)
    public List<ProductAttributeApplication> findByIDs(@Param("ids") List<Integer> ids);

    @Query(value = "select * from TBLPRODUCTATTRIBUTEAPPLICATION "
            + "where PRODUCTATTRIBUTEAPPLICATION_ID not in (:ids) "
            + "", nativeQuery = true)
    public List<ProductAttributeApplication> findByNotInIDs(@Param("ids") List<Integer> ids);

    @Query(value = "select * from TBLPRODUCTATTRIBUTEAPPLICATION "
            + "where CASE WHEN :APPLICATION_ID = 0 THEN APPLICATION_ID=APPLICATION_ID ELSE APPLICATION_ID IN (:APPLICATION_IDS) END "
            + "and CASE WHEN :PRODUCTATTRIBUTE_ID = 0 THEN PRODUCTATTRIBUTE_ID=PRODUCTATTRIBUTE_ID ELSE PRODUCTATTRIBUTE_ID IN (:PRODUCTATTRIBUTE_IDS) END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<ProductAttributeApplication> findByAdvancedSearch(
		    @Param("PRODUCTATTRIBUTE_ID") Long PRODUCTATTRIBUTE_ID, @Param("PRODUCTATTRIBUTE_IDS") List<Integer> PRODUCTATTRIBUTE_IDS,
		    @Param("APPLICATION_ID") Long APPLICATION_ID, @Param("APPLICATION_IDS") List<Integer> APPLICATION_IDS);

    @Query(value = "select * from TBLPRODUCTATTRIBUTEAPPLICATION "
            + "where CASE WHEN :APPLICATION_ID = 0 THEN APPLICATION_ID=APPLICATION_ID ELSE APPLICATION_ID IN (:APPLICATION_IDS) END "
            + "and CASE WHEN :PRODUCTATTRIBUTE_ID = 0 THEN PRODUCTATTRIBUTE_ID=PRODUCTATTRIBUTE_ID ELSE PRODUCTATTRIBUTE_ID IN (:PRODUCTATTRIBUTE_IDS) END "
			, nativeQuery = true)
	List<ProductAttributeApplication> findAllByAdvancedSearch(
		    @Param("PRODUCTATTRIBUTE_ID") Long PRODUCTATTRIBUTE_ID, @Param("PRODUCTATTRIBUTE_IDS") List<Integer> PRODUCTATTRIBUTE_IDS,
		    @Param("APPLICATION_ID") Long APPLICATION_ID, @Param("APPLICATION_IDS") List<Integer> APPLICATION_IDS);
	
}

