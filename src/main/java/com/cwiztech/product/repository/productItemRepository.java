package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItem;

public interface productItemRepository extends JpaRepository<ProductItem, Long>{
    @Query(value = "select * from TBLPRODUCTITEM where ISACTIVE='Y'", nativeQuery = true)
    public List<ProductItem> findActive();

    @Query(value = "select * from TBLPRODUCTITEM as a "
            + "inner join TBLPRODUCT as b on PRODUCT_ID=b.PRODUCT_ID "
            + "where NETSUITE_ID=?1 "
            + "", nativeQuery = true)
    List<ProductItem> findByNetSuiteID(Long id);

    @Query(value = "select * from TBLPRODUCTITEM as a "
            + "inner join TBLPRODUCT as b on PRODUCT_ID=b.PRODUCT_ID "
            + "where NETSUITE_ID in (:ids) "
            + "", nativeQuery = true)
    List<ProductItem> findByNetSuiteIDs(@Param("ids") List<Integer> ids);

    @Query(value = "select * from TBLPRODUCTITEM "
            + "where PRODUCTITEM_ID in (:ids) "
            + "", nativeQuery = true)
    public List<ProductItem> findByIDs(@Param("ids") List<Integer> ids);

    @Query(value = "select * from TBLPRODUCTITEM "
            + "where PRODUCTITEM_ID not in (:ids) "
            + "", nativeQuery = true)
    public List<ProductItem> findByNotInIDs(@Param("ids") List<Integer> ids);

    @Query(value = "select * from TBLPRODUCTITEM  as a "
            + "inner join TBLPRODUCT as b on PRODUCT_ID=b.PRODUCT_ID "
            + "where PRODUCT_CODE=?1 "
            + "", nativeQuery = true)
    public List<ProductItem> findByCode(String code);

    @Query(value = "select * from TBLPRODUCTITEM  as a "
            + "inner join TBLPRODUCT as b on PRODUCT_ID=b.PRODUCT_ID "
            + "where PRODUCT_NEWCODE=?1 "
            + "", nativeQuery = true)
    public List<ProductItem> findByNewCode(String code);

    @Query(value = "select * from TBLPRODUCTITEM  as a "
            + "inner join TBLPRODUCT as b on PRODUCT_ID=b.PRODUCT_ID "
            + "where PRODUCT_CODE in (:codes) "
            + "", nativeQuery = true)
    public List<ProductItem> findByCodes(@Param("codes") List<String> codes);

    @Query(value = "select * from TBLPRODUCTITEM  as a "
            + "inner join TBLPRODUCT as b on PRODUCT_ID=b.PRODUCT_ID "
            + "where PRODUCT_NEWCODE in (:codes) "
            + "", nativeQuery = true)
    public List<ProductItem> findByNewCodes(@Param("codes") List<String> codes);

    @Query(value = "select * from TBLPRODUCTITEM "
            + "where PRODUCTITEM_NAME like ?1 or  PRODUCTITEM_DESC like ?1 and ISACTIVE='Y'", nativeQuery = true)
    public List<ProductItem> findBySearch(String search);

    @Query(value = "select * from TBLPRODUCTITEM "
            + "where PRODUCTITEM_NAME like ?1 or  PRODUCTITEM_DESC like ?1 ", nativeQuery = true)
    public List<ProductItem> findAllBySearch(String search);

    @Query(value = "select * from TBLPRODUCTITEM "
            + "where CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
            + "and CASE WHEN :APPLICATION_ID = 0 THEN APPLICATION_ID=APPLICATION_ID ELSE APPLICATION_ID IN (:APPLICATION_IDS) END "
            + "and ISACTIVE='Y'", nativeQuery = true)
    List<ProductItem> findByAdvancedSearch(
            @Param("APPLICATION_ID") Long APPLICATION_ID, @Param("APPLICATION_IDS") List<Integer> APPLICATION_IDS,
            @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS); 

    @Query(value = "select * from TBLPRODUCTITEM " 
            + "where CASE WHEN :PRODUCT_ID = 0 THEN PRODUCT_ID=PRODUCT_ID ELSE PRODUCT_ID IN (:PRODUCT_IDS) END "
            + "and CASE WHEN :APPLICATION_ID = 0 THEN APPLICATION_ID=APPLICATION_ID ELSE APPLICATION_ID IN (:APPLICATION_IDS) END "
            + "", nativeQuery = true)
    List<ProductItem> findAllByAdvancedSearch(
            @Param("APPLICATION_ID") Long APPLICATION_ID, @Param("APPLICATION_IDS") List<Integer> APPLICATION_IDS,
            @Param("PRODUCT_ID") Long PRODUCT_ID, @Param("PRODUCT_IDS") List<Integer> PRODUCT_IDS); 

}
