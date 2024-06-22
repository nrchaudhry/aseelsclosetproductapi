package com.cwiztech.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.product.model.ProductItemPriceLevel;

public interface productItemPriceLevelRepository  extends JpaRepository<ProductItemPriceLevel, Long> {
    @Query(value = "select * from TBLPRODUCTITEMPRICELEVEL where ISACTIVE='Y'", nativeQuery = true)
    public List<ProductItemPriceLevel> findActive();

    @Query(value = "select * from TBLPRODUCTITEMPRICELEVEL where NETSUITE_ID=?1", nativeQuery = true)
    public ProductItemPriceLevel findByNetSuiteID(Long id);

    @Query(value = "select * from TBLPRODUCTITEMPRICELEVEL where PRODUCT_CODE=?1", nativeQuery = true)
    public ProductItemPriceLevel findByCode(String code);

    @Query(value = "select * from TBLPRODUCTITEMPRICELEVEL "
            + "where PRODUCTITEMPRICELEVEL_ID in (:ids) "
            + "", nativeQuery = true)
    public List<ProductItemPriceLevel> findByIDs(@Param("ids") List<Integer> ids);

    @Query(value = "select * from TBLPRODUCTITEMPRICELEVEL "
            + "where PRODUCTITEMPRICELEVEL_ID not in (:ids) "
            + "", nativeQuery = true)
    public List<ProductItemPriceLevel> findByNotInIDs(@Param("ids") List<Integer> ids);

    @Query(value = "select * from TBLPRODUCTITEMPRICELEVEL "
            + "inner join TBLPRODUCTITEMPRICELEVELCATEGORY as b on PRODUCTCATEGORY_ID=PRODUCTCATEGORY_ID "
            + "where PRODUCT_CODE like ?1 "
            + "and ISACTIVE='Y'", nativeQuery = true)
    public List<ProductItemPriceLevel> findBySearch(String search);

    @Query(value = "select * from TBLPRODUCTITEMPRICELEVEL "
            + "inner join TBLPRODUCTITEMPRICELEVELCATEGORY as b on PRODUCT_ID=PRODUCT_ID "
            + "where PRODUCT_CODE like ?1 "
            + "", nativeQuery = true)
    public List<ProductItemPriceLevel> findAllBySearch(String search);

    @Query(value = "select * from TBLPRODUCTITEMPRICELEVEL " 
            + "where CASE WHEN :CURRENCY_ID = 0 THEN CURRENCY_ID=CURRENCY_ID ELSE CURRENCY_ID IN (:CURRENCY_IDS) END "
            + "and CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
            + "and CASE WHEN :PRICELEVEL_ID = 0 THEN PRICELEVEL_ID=PRICELEVEL_ID ELSE PRICELEVEL_ID IN (:PRICELEVEL_IDS) END "
            + "and ISACTIVE='Y'", nativeQuery = true)
    List<ProductItemPriceLevel> findByAdvancedSearch(
            @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS,
            @Param("PRICELEVEL_ID") Long PRICELEVEL_ID, @Param("PRICELEVEL_IDS") List<Integer> PRICELEVEL_IDS,
            @Param("CURRENCY_ID") Long CURRENCY_ID, @Param("CURRENCY_IDS") List<Integer> CURRENCY_IDS);

    @Query(value = "select * from TBLPRODUCTITEMPRICELEVEL " 
            + "where CASE WHEN :CURRENCY_ID = 0 THEN CURRENCY_ID=CURRENCY_ID ELSE CURRENCY_ID IN (:CURRENCY_IDS) END "
            + "and CASE WHEN :PRODUCTITEM_ID = 0 THEN PRODUCTITEM_ID=PRODUCTITEM_ID ELSE PRODUCTITEM_ID IN (:PRODUCTITEM_IDS) END "
            + "and CASE WHEN :PRICELEVEL_ID = 0 THEN PRICELEVEL_ID=PRICELEVEL_ID ELSE PRICELEVEL_ID IN (:PRICELEVEL_IDS) END "
            + "", nativeQuery = true)
    List<ProductItemPriceLevel> findAllByAdvancedSearch(
            @Param("PRODUCTITEM_ID") Long PRODUCTITEM_ID, @Param("PRODUCTITEM_IDS") List<Integer> PRODUCTITEM_IDS,
            @Param("PRICELEVEL_ID") Long PRICELEVEL_ID, @Param("PRICELEVEL_IDS") List<Integer> PRICELEVEL_IDS,
            @Param("CURRENCY_ID") Long CURRENCY_ID, @Param("CURRENCY_IDS") List<Integer> CURRENCY_IDS);

}
