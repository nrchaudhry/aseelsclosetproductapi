package com.cwiztech.product.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.product.model.Product;
import com.cwiztech.product.repository.productRepository;
import com.cwiztech.services.ProductService;
import com.cwiztech.services.SageService;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class productSageController {
	private static final Logger log = LoggerFactory.getLogger(productSageController.class);

	@Autowired
	private productRepository productrepository;

	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;

	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/sendtosage", method = RequestMethod.GET)
	public ResponseEntity SendToSage(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException, InterruptedException {
		APIRequestDataLog apiRequest = checkToken("GET", "/product/sendtosage", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);


		JSONArray objProducts = new JSONArray();
		
		List<Product> products = productrepository.findByNullSageID();
        for (int i=0; i<products.size(); i++) {
    		JSONObject objProduct = new JSONObject();
    		JSONObject objProductDetail = new JSONObject();	

    		objProductDetail.put("catalog_item_type_id", "STOCK_ITEM");	
    		objProductDetail.put("item_code", products.get(i).getPRODUCT_CODE());
    		objProductDetail.put("description", products.get(i).getPRODUCT_DESC());	
    		objProductDetail.put("sales_ledger_account_id", "819fd52d842f11ed84fa0252b90cda0d");
    		objProductDetail.put("purchase_ledger_account_id", "81a0cf2c842f11ed84fa0252b90cda0d");

    		JSONArray productitems = new JSONArray(ProductService.POST("productitem/advancedsearch", "{product_ID: "+products.get(i).getPRODUCT_ID()+"}", apiRequest.getREQUEST_OUTPUT()));
	        if (productitems.length()>0) {
	    		List<Object> salesprices = new ArrayList<Object>();

	    		JSONObject productitem = productitems.getJSONObject(0);
				JSONArray productitempricelevels = new JSONArray(ProductService.POST("productitempricelevel/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
		        for (int j=0; j<productitempricelevels.length(); j++) {
		    		JSONObject productitempricelevel = new JSONObject();	
		    		productitempricelevel.put("product_sales_price_type_id", "264fdb5bd99e45c389e5e322f87d6780");
		    		productitempricelevel.put("price", productitempricelevels.getJSONObject(j).getDouble("productitem_UNITPRICE"));
		    		productitempricelevel.put("price_includes_tax", "true");

		    		salesprices.add(productitempricelevel);
		        }
		        objProductDetail.put("sales_prices", salesprices);
				
		        
//				JSONArray productiteminventories = new JSONArray(ProductService.POST("productiteminventory/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
//		        for (int j=0; j<productitempricelevels.length(); j++) {
//		        }

				JSONArray productitemattributevalues = new JSONArray(ProductService.POST("productitemattributevalue/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
		        for (int j=0; j<productitemattributevalues.length(); j++) {
		        	JSONObject productitemattributevalue = productitemattributevalues.getJSONObject(j);
					JSONObject jsonproductattribute = new JSONObject(productitemattributevalue.getString("productattribute_DETAIL"));
					JSONObject jsonattribute = new JSONObject(jsonproductattribute.getString("attribute_DETAIL"));

					if (jsonattribute.getString("attribute_KEY").equals("taxcode")) {
						if (productitemattributevalues.getJSONObject(j).getLong("productattributevalue_ID")==1) {
				    		objProductDetail.put("sales_tax_rate_id", "GB_STANDARD");
				    		objProductDetail.put("purchase_tax_rate_id", "GB_STANDARD");
						} else {
				    		objProductDetail.put("sales_tax_rate_id", "GB_ZERO");
				    		objProductDetail.put("purchase_tax_rate_id", "GB_ZERO");
						}
					}

					if (!productitemattributevalue.isNull("productattributeitem_ID"))
						productitem.put(jsonattribute.getString("attribute_KEY"), productitemattributevalues.getJSONObject(j).getLong("productattributevalue_ID"));
					else if (!productitemattributevalues.getJSONObject(j).isNull("productattributeitem_VALUE"))
						productitem.put(jsonattribute.getString("attribute_KEY"), productitemattributevalues.getJSONObject(j).getString("productattributeitem_VALUE"));
				}
			}


			objProduct.put("stock_item", objProductDetail);
			JSONObject response = new JSONObject(SageService.POST("stock_items", objProduct.toString(), headToken));
			
			JSONObject product = new JSONObject();
			product.put("product_ID", products.get(i).getPRODUCT_ID());
			product.put("sage_ID", response.getString("id"));
			product = new JSONObject(ProductService.POST("product", product.toString(), apiRequest.getREQUEST_OUTPUT()));

			objProducts.put(objProduct);
			
        }
		
		return new ResponseEntity(getAPIResponse(null, null , objProducts, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(Product.getDatabaseTableID());
		APIRequestDataLog apiRequest;
		
		log.info(requestType + ": " + requestURI);
		if (requestBody != null)
			log.info("Input: " + requestBody);

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog(requestType, databaseTableID, (long) 0, requestURI, requestBody, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest;
		}
		
		Long requestUser = (long) 0;
		if (accessToken != null && accessToken != "")
			requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog(requestType, databaseTableID, requestUser, requestURI, requestBody, workstation);
		apiRequest.setREQUEST_OUTPUT(accessToken);

		return apiRequest;
	}
	
	APIRequestDataLog getAPIResponse(List<Product> products, Product product , JSONArray jsonProducts, JSONObject jsonProduct, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Product", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (product != null) {
				JSONObject productcategory = new JSONObject(ProductService.GET("productcategory/"+product.getPRODUCTCATEGORY_ID(), apiRequest.getREQUEST_OUTPUT()));
				product.setPRODUCTCATEGORY_DETAIL(productcategory.toString());
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(product));
				productID = product.getPRODUCT_ID();
			} else if(products != null){
				if (products.size()>0) {
					List<Integer> productcategoryList = new ArrayList<Integer>();
					for (int i=0; i<products.size(); i++) {
						productcategoryList.add(Integer.parseInt(products.get(i).getPRODUCTCATEGORY_ID().toString()));
					}
					JSONArray productcategoryObject = new JSONArray(ProductService.POST("productcategory/ids", "{productcategories: "+productcategoryList+"}", apiRequest.getREQUEST_OUTPUT()));
					
					for (int i=0; i<products.size(); i++) {
						for (int j=0; j<productcategoryObject.length(); j++) {
							JSONObject productcategory = productcategoryObject.getJSONObject(j);
							if(products.get(i).getPRODUCTCATEGORY_ID() == productcategory.getLong("productcategory_ID") ) {
								products.get(i).setPRODUCTCATEGORY_DETAIL(productcategory.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(products));
			}else if (jsonProducts != null){
				apiRequest.setREQUEST_OUTPUT(jsonProducts.toString());
			
			} else if (jsonProduct != null){
				apiRequest.setREQUEST_OUTPUT(jsonProduct.toString());
			} else {
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(product));
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}

}
