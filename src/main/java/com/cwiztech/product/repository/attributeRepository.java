package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.Attribute;

public interface attributeRepository extends JpaRepository<Attribute, Long>{
	
	@Query(value = "select * from TBLATTRIBUTE where ISACTIVE='Y'", nativeQuery = true)
	public List<Attribute> findActive();
	
	@Query(value = "select * from TBLATTRIBUTE where ATTRIBUTE_KEY=?1 and ISACTIVE='Y'", nativeQuery = true)
	public Attribute findByKey(String key);
	
	@Query(value = "select * from TBLATTRIBUTE "
			+ "where (ATTRIBUTE_NAME like ?1 or ATTRIBUTE_KEY like ?1 or ATTRIBUTE_DESCRIPTION like ?1) and ISACTIVE='Y' order by ATTRIBUTEORDER_NO", nativeQuery = true)
	public List<Attribute> findBySearch(String search);

	@Query(value = "select * from TBLATTRIBUTE "
			+ "where ATTRIBUTE_NAME like ?1 or ATTRIBUTE_KEY like ?1 or ATTRIBUTE_DESCRIPTION like ?1 order by ATTRIBUTEORDER_NO", nativeQuery = true)
	public List<Attribute> findAllBySearch(String search);

    @Query(value = "select * from TBLATTRIBUTE "
            + "where CASE WHEN :DATATYPE_ID = 0 THEN DATATYPE_ID=DATATYPE_ID ELSE DATATYPE_ID IN (:DATATYPE_IDS) END "
			+ "and ISACTIVE='Y' order by ATTRIBUTEORDER_NO ", nativeQuery = true)
	List<Attribute> findByAdvancedSearch(
    @Param("DATATYPE_ID") Long DATATYPE_ID, @Param("DATATYPE_IDS") List<Integer> DATATYPE_IDS); 

	@Query(value = "select * from TBLATTRIBUTE "
			+ "where CASE WHEN :DATATYPE_ID = 0 THEN DATATYPE_ID=DATATYPE_ID ELSE DATATYPE_ID IN (:DATATYPE_IDS) END "
			, nativeQuery = true)
	List<Attribute> findAllByAdvancedSearch(
    @Param("DATATYPE_ID") Long DATATYPE_ID, @Param("DATATYPE_IDS") List<Integer> DATATYPE_IDS); 

	@Query(value = "select * from TBLATTRIBUTE where ATTRIBUTE_ID in (:ids) ", nativeQuery = true)
	public List<Attribute> findByIDs(@Param("ids") List<Integer> ids);

}



