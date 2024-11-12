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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.cwiztech.services.ServiceCall;
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
	public ResponseEntity SendToSage(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException {
		APIRequestDataLog apiRequest = checkToken("GET", "/product/sendtosage", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);


		JSONArray objProducts = new JSONArray();
		
		List<Product> products = productrepository.findByNullSageID();
        for (int i=0; i<products.size(); i++) {
    		JSONObject objProduct = new JSONObject();
    		JSONObject objProductDetail = new JSONObject();	

    		objProductDetail.put("catalog_item_type_id", "STOCK_ITEM");	
    		objProductDetail.put("item_code", products.get(i).getPRODUCT_CODE());
    		objProductDetail.put("description", products.get(i).getPRODUCT_NAME());	
    		objProductDetail.put("sales_ledger_account_id", "819fd52d842f11ed84fa0252b90cda0d");
    		objProductDetail.put("purchase_ledger_account_id", "81a0cf2c842f11ed84fa0252b90cda0d");
			if (products.get(i).getTAXCODE_ID() == 1) {
	    		objProductDetail.put("sales_tax_rate_id", "GB_STANDARD");
	    		objProductDetail.put("purchase_tax_rate_id", "GB_STANDARD");
			} else {
	    		objProductDetail.put("sales_tax_rate_id", "GB_ZERO");
	    		objProductDetail.put("purchase_tax_rate_id", "GB_ZERO");
			}

    		JSONArray productitems = new JSONArray(ServiceCall.POST("productitem/advancedsearch", "{product_ID: "+products.get(i).getPRODUCT_ID()+"}", apiRequest.getREQUEST_OUTPUT(), false));
	        if (productitems.length()>0) {
	    		List<Object> salesprices = new ArrayList<Object>();

	    		JSONObject productitem = productitems.getJSONObject(0);
				JSONArray productitempricelevels = new JSONArray(ServiceCall.POST("productitempricelevel/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getREQUEST_OUTPUT(), false));
		        for (int j=0; j<productitempricelevels.length(); j++) {
		    		JSONObject productitempricelevel = new JSONObject();	
		    		productitempricelevel.put("product_sales_price_type_id", "264fdb5bd99e45c389e5e322f87d6780");
		    		productitempricelevel.put("price", productitempricelevels.getJSONObject(j).getDouble("productitem_UNITPRICE"));
		    		productitempricelevel.put("price_includes_tax", "true");

		    		salesprices.add(productitempricelevel);
		        }
		        objProductDetail.put("sales_prices", salesprices);
				
		        
//				JSONArray productiteminventories = new JSONArray(ServiceCall.POST("productiteminventory/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
//		        for (int j=0; j<productitempricelevels.length(); j++) {
//		        }

				JSONArray productitemattributevalues = new JSONArray(ServiceCall.POST("productitemattributevalue/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getREQUEST_OUTPUT(), false));
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
			APIRequestDataLog apiRequestResponse = SageService.POST("stock_items", objProduct.toString(), headToken, apiRequest.getDATABASETABLE_ID());
			JSONObject response = new JSONObject(apiRequestResponse.getREQUEST_OUTPUT());

			JSONObject product = new JSONObject();
			product.put("product_ID", products.get(i).getPRODUCT_ID());
			product.put("sage_ID", response.getString("id"));
			product = new JSONObject(ServiceCall.POST("product", product.toString(), apiRequest.getREQUEST_OUTPUT(), false));

			objProducts.put(objProduct);
			
        }
		
		return new ResponseEntity(getAPIResponse(null, null , objProducts, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getfromsage/{page}", method = RequestMethod.GET)
	public ResponseEntity GetFromSage(@PathVariable Long page, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException {
		APIRequestDataLog apiRequest = checkToken("GET", "/product/getfromsage", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		boolean lastPage = false;

		JSONObject response = new JSONObject(new JSONObject(SageService.GET("products?page=" + (page-1) + "&items_per_page=100", headToken, apiRequest.getDATABASETABLE_ID())).getString("REQUEST_OUTPUT"));

		log.info("response sage: "+response);
		
		while (lastPage == false) {
			JSONArray jsonProducts = response.getJSONArray("$items");

			for (int i=0; i<jsonProducts.length(); i++) {
				JSONObject jsonProduct = jsonProducts.getJSONObject(i);
				long productitem_id = 0;

				Product product = productrepository.findBySageID(jsonProduct.getString("id"));
//				Product product = productrepository.findBySageID("186b84b02cf5444b979cac29b585451a");
				JSONObject objProduct = new JSONObject();
				JSONObject objProductItem = new JSONObject();
				JSONObject objProductItemPrice = new JSONObject();
				JSONObject objProductItemInventory = new JSONObject();
				if (product != null) {
					objProduct.put("product_ID", product.getPRODUCT_ID());
					JSONArray productitems = new JSONArray(ServiceCall.POST("productitem/advancedsearch", objProduct.toString(), apiRequest.getREQUEST_OUTPUT(), false));
					if (productitems.length() > 0) {
						productitem_id = productitems.getJSONObject(productitems.length()-1).getLong("productitem_ID");
						objProductItem.put("productitem_ID", productitem_id);

						JSONArray productitemprices = new JSONArray(ServiceCall.POST("productitempricelevel/advancedsearch", objProductItem.toString(), apiRequest.getREQUEST_OUTPUT(), false));
						JSONArray productiteminventory = new JSONArray(ServiceCall.POST("productiteminventory/advancedsearch", objProductItem.toString(), apiRequest.getREQUEST_OUTPUT(), false));
					}
				}
				JSONObject responseProduct = new JSONObject(new JSONObject(SageService.GET("stock_items/" + jsonProduct.getString("id"), headToken, apiRequest.getDATABASETABLE_ID())).getString("REQUEST_OUTPUT"));
//				JSONObject responseProduct = new JSONObject(SageService.GET("contacts/186b84b02cf5444b979cac29b585451a?show_balance=true&show_overdue_balance=true", headToken));

				objProduct.put("sage_ID", responseProduct.getString("id"));
				objProduct.put("productcategory_ID", 1);
				if (responseProduct.has("item_code") && !responseProduct.isNull("item_code")) {
					objProduct.put("product_CODE", responseProduct.getString("item_code"));
				} else {
					responseProduct.put("product_CODE", "AAA");
				}
				if (responseProduct.has("description") && !responseProduct.isNull("description")) {
					objProduct.put("product_NAME", responseProduct.getString("description"));
					objProductItem.put("productitem_NAME", responseProduct.getString("description"));
				} else {
					objProduct.put("product_NAME","AAA");
					objProductItem.put("productitem_NAME","AAA");
				}

				if (responseProduct.has("sales_tax_rate") && !responseProduct.isNull("sales_tax_rate")) {
					if (responseProduct.getJSONObject("sales_tax_rate").getString("id").compareTo("GB_STANDARD") == 0) {
						objProduct.put("taxcode_ID", 1);
					} else {
						objProduct.put("taxcode_ID", 2);
					}
				}

				if (responseProduct.has("cost_price") && !responseProduct.isNull("cost_price")) {
					objProduct.put("purchase_PRICE", responseProduct.getDouble("cost_price"));
				}

				if (responseProduct.has("weight") && !responseProduct.isNull("weight")) {
					objProduct.put("product_WEIGHT", responseProduct.getDouble("weight"));
				}

				if (responseProduct.has("notes") && !responseProduct.isNull("notes")) {
					objProduct.put("product_DESC", responseProduct.getString("notes"));
					objProductItem.put("productitem_DESC", responseProduct.getString("notes"));
				}
				objProductItem.put("application_ID", 1);

				log.info("objProduct: "+objProduct);
				log.info("page: "+page);

				JSONObject products = new JSONObject(ServiceCall.POST("product", objProduct.toString(), apiRequest.getREQUEST_OUTPUT(), false));

				objProductItem.put("product_ID", products.getLong("product_ID"));
				JSONObject productitems = new JSONObject(ServiceCall.POST("productitem", objProductItem.toString(), apiRequest.getREQUEST_OUTPUT(), false));

				JSONArray salesprices = responseProduct.getJSONArray("sales_prices");
				objProductItemPrice.put("productitem_ID", productitems.getLong("productitem_ID"));
				objProductItemPrice.put("pricelevel_CODE", "2");
				objProductItemPrice.put("currency_CODE", "GB");
				objProductItemPrice.put("productitem_QUANTITY", 1);
				objProductItemPrice.put("productitem_UNITPRICE", salesprices.getJSONObject(0).getString("price"));
				ServiceCall.POST("productitempricelevel", objProductItemPrice.toString(), apiRequest.getREQUEST_OUTPUT(), false);

				objProductItemInventory.put("productitem_ID", productitems.getLong("productitem_ID"));
				objProductItemInventory.put("productlocation_CODE", "1");
				objProductItemInventory.put("quantity_ONHAND", responseProduct.getDouble("quantity_in_stock"));
				objProductItemInventory.put("quantity_AVAILABLE", responseProduct.getDouble("quantity_in_stock"));
				ServiceCall.POST("productiteminventory", objProductItemInventory.toString(), apiRequest.getREQUEST_OUTPUT(), false);
			}


			if (response.isNull("$next"))
				lastPage = true;
			response = new JSONObject(new JSONObject(SageService.GET("stock_items?page=" + page + "&items_per_page=100", headToken, apiRequest.getDATABASETABLE_ID())).getString("REQUEST_OUTPUT"));
			page = page + 1;
		}

		return new ResponseEntity(getAPIResponse(null, null , null, null, "Product Updated", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
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

		if (checkTokenResponse.has("employee_ID") && !checkTokenResponse.isNull("employee_ID"))
			apiRequest.setRESPONSE_DATETIME(""+checkTokenResponse.getLong("employee_ID"));

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
				JSONObject productcategory = new JSONObject(ServiceCall.GET("productcategory/"+product.getPRODUCTCATEGORY_ID(), apiRequest.getREQUEST_OUTPUT(), false));
				product.setPRODUCTCATEGORY_DETAIL(productcategory.toString());
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(product));
				productID = product.getPRODUCT_ID();
			} else if(products != null){
				if (products.size()>0) {
					List<Integer> productcategoryList = new ArrayList<Integer>();
					for (int i=0; i<products.size(); i++) {
						productcategoryList.add(Integer.parseInt(products.get(i).getPRODUCTCATEGORY_ID().toString()));
					}
					JSONArray productcategoryObject = new JSONArray(ServiceCall.POST("productcategory/ids", "{productcategories: "+productcategoryList+"}", apiRequest.getREQUEST_OUTPUT(), false));
					
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
		
		return apiRequest;
	}

}
