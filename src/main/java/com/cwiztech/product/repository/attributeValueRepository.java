package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.AttributeValue;

public interface attributeValueRepository extends JpaRepository<AttributeValue, Long>{
	
	@Query(value = "select * from TBLATTRIBUTEVALUE where ISACTIVE='Y'", nativeQuery = true)
	public List<AttributeValue> findActive();
	
	@Query(value = "select * from TBLATTRIBUTEVALUE "
			+ "where ATTRIBUTEVALUE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<AttributeValue> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLATTRIBUTEVALUE "
			+ "where ATTRIBUTEVALUE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<AttributeValue> findByNotInIDs(@Param("ids") List<Integer> ids);
	
	@Query(value = "select * from TBLATTRIBUTEVALUE "
			+ "where ATTRIBUTE_VALUE like ?1 and ISACTIVE='Y'", nativeQuery = true)
	public List<AttributeValue> findBySearch(String search);

	@Query(value = "select * from TBLATTRIBUTEVALUE "
			+ "where ATTRIBUTE_VALUE like ?1 ", nativeQuery = true)
	public List<AttributeValue> findAllBySearch(String search);

	@Query(value = "select a.* from TBLATTRIBUTEVALUE as a " 
			+ "where CASE WHEN :ATTRIBUTE_ID = 0 THEN ATTRIBUTE_ID=ATTRIBUTE_ID ELSE ATTRIBUTE_ID IN (:ATTRIBUTE_IDS) END "
			+ "where CASE WHEN :ATTRIBUTEVALUEPARENT_ID = 0 THEN ATTRIBUTEVALUEPARENT_ID=ATTRIBUTEVALUEPARENT_ID ELSE ATTRIBUTEVALUEPARENT_ID IN (:ATTRIBUTEVALUEPARENT_IDS) END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<AttributeValue> findByAdvancedSearch(
    @Param("ATTRIBUTE_ID") Long ATTRIBUTE_ID, @Param("ATTRIBUTE_IDS") List<Integer> ATTRIBUTE_IDS,
    @Param("ATTRIBUTEVALUEPARENT_IDS") Long ATTRIBUTEVALUEPARENT_ID, @Param("ATTRIBUTEVALUEPARENT_IDS") List<Integer> ATTRIBUTEVALUEPARENT_IDS);

    @Query(value = "select a.* from TBLATTRIBUTEVALUE as a " 
            + "where CASE WHEN :ATTRIBUTE_ID = 0 THEN ATTRIBUTE_ID=ATTRIBUTE_ID ELSE ATTRIBUTE_ID IN (:ATTRIBUTE_IDS) END "
            + "where CASE WHEN :ATTRIBUTEVALUEPARENT_ID = 0 THEN ATTRIBUTEVALUEPARENT_ID=ATTRIBUTEVALUEPARENT_ID ELSE ATTRIBUTEVALUEPARENT_ID IN (:ATTRIBUTEVALUEPARENT_IDS) END "
			, nativeQuery = true)
	List<AttributeValue> findAllByAdvancedSearch(
    @Param("ATTRIBUTE_ID") Long ATTRIBUTE_ID, @Param("ATTRIBUTE_IDS") List<Integer> ATTRIBUTE_IDS,
    @Param("ATTRIBUTEVALUEPARENT_IDS") Long ATTRIBUTEVALUEPARENT_ID, @Param("ATTRIBUTEVALUEPARENT_IDS") List<Integer> ATTRIBUTEVALUEPARENT_IDS);
	

}
