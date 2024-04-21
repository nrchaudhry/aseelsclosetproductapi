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
			+ "where DATATYPE_ID like CASE WHEN ?1=0 THEN DATATYPE_ID ELSE ?1 END "
			+ "and ISACTIVE='Y' order by ATTRIBUTEORDER_NO ", nativeQuery = true)
	List<Attribute> findByAdvancedSearch(Long datatypeID);

	@Query(value = "select * from TBLATTRIBUTE "
			+ "where DATATYPE_ID like CASE WHEN ?1=0 THEN DATATYPE_ID ELSE ?1 END order by ATTRIBUTEORDER_NO"
			, nativeQuery = true)
	List<Attribute> findAllByAdvancedSearch(Long datatypeID);

	@Query(value = "select * from TBLATTRIBUTE where ATTRIBUTE_ID in (:ids) ", nativeQuery = true)
	public List<Attribute> findByIDs(@Param("ids") List<Integer> ids);

}



