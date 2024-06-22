package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.AttributeCategory;

public interface attributeCategoryRepository extends JpaRepository<AttributeCategory, Long>{
	@Query(value = "select * from TBLATTRIBUTECATEGORY where ISACTIVE='Y'", nativeQuery = true)
	public List<AttributeCategory> findActive();
	
	@Query(value = "select * from TBLATTRIBUTECATEGORY "
			+ "where ATTRIBUTECATEGORYORDER_NO like ?1 or ATTRIBUTECATEGORY_NAME like ?1 or ISTABS like ?1 "
			+ "and ISACTIVE='Y'"
			, nativeQuery = true)
	public List<AttributeCategory> findBySearch(String search);

	@Query(value = "select * from TBLATTRIBUTECATEGORY "
			+ "where ATTRIBUTECATEGORYORDER_NO like ?1 or ATTRIBUTECATEGORY_NAME like ?1 or ISTABS like ?1 "
			, nativeQuery = true)
	public List<AttributeCategory> findAllBySearch(String search);

	@Query(value = "select * from TBLATTRIBUTECATEGORY  "
            + "where CASE WHEN :ATTRIBUTECATEGORYPARENT_ID = 0 THEN ATTRIBUTECATEGORYPARENT_ID=ATTRIBUTECATEGORYPARENT_ID ELSE ATTRIBUTECATEGORYPARENT_ID IN (:ATTRIBUTECATEGORYPARENT_IDS) END "
			+ "and ISACTIVE='Y' order by ATTRIBUTECATEGORYORDER_NO", nativeQuery = true)
	List<AttributeCategory> findByAdvancedSearch(
    @Param("ATTRIBUTECATEGORYPARENT_ID") Long ATTRIBUTECATEGORYPARENT_ID, @Param("ATTRIBUTECATEGORYPARENT_IDS") List<Integer> ATTRIBUTECATEGORYPARENT_IDS); 

	@Query(value = "select distinct * from TBLATTRIBUTECATEGORY "
			+ "where CASE WHEN :ATTRIBUTECATEGORYPARENT_ID = 0 THEN ATTRIBUTECATEGORYPARENT_ID=ATTRIBUTECATEGORYPARENT_ID ELSE ATTRIBUTECATEGORYPARENT_ID IN (:ATTRIBUTECATEGORYPARENT_IDS) END "
			, nativeQuery = true)
	List<AttributeCategory> findAllByAdvancedSearch(
    @Param("ATTRIBUTECATEGORYPARENT_ID") Long ATTRIBUTECATEGORYPARENT_ID, @Param("ATTRIBUTECATEGORYPARENT_IDS") List<Integer> ATTRIBUTECATEGORYPARENT_IDS); 

	@Query(value = "select * from TBLATTRIBUTECATEGORY "
			+ "where ATTRIBUTECATEGORY_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<AttributeCategory> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLATTRIBUTECATEGORY "
			+ "where ATTRIBUTECATEGORY_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<AttributeCategory> findByNotInIDs(@Param("ids") List<Integer> ids);

	
}
