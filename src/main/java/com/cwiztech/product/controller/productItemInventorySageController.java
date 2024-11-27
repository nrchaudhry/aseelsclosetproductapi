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
import com.cwiztech.product.model.ProductItemInventory;
import com.cwiztech.product.repository.productItemInventoryRepository;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.services.SageService;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/productiteminventory")
public class productItemInventorySageController {
	private static final Logger log = LoggerFactory.getLogger(productItemInventorySageController.class);

	@Autowired
	private productItemInventoryRepository productiteminventoryrepository;

	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;

	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/sendtosage", method = RequestMethod.GET)
	public ResponseEntity SendToSage(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productiteminventory/sendtosage", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);


		JSONArray objProductItemInventorys = new JSONArray();
		
//		List<ProductItemInventory> productiteminventories = productiteminventoryrepository.findActive();
//        for (int i=0; i<productiteminventories.size(); i++) {
//    		JSONObject objProductItemInventory = new JSONObject();
//    		JSONObject objProductItemInventoryDetail = new JSONObject();	
//
//    		objProductItemInventoryDetail.put("catalog_item_type_id", "STOCK_ITEM");	
//    		objProductItemInventoryDetail.put("item_code", productiteminventories.get(i).getPRODUCT_CODE());
//    		objProductItemInventoryDetail.put("description", productiteminventories.get(i).getPRODUCT_DESC());	
//    		objProductItemInventoryDetail.put("sales_ledger_account_id", "819fd52d842f11ed84fa0252b90cda0d");
//    		objProductItemInventoryDetail.put("purchase_ledger_account_id", "81a0cf2c842f11ed84fa0252b90cda0d");
//
//    		JSONArray productiteminventoryitems = new JSONArray(ServiceCall.POST("productiteminventoryitem/advancedsearch", "{productiteminventory_ID: "+productiteminventories.get(i).getPRODUCT_ID()+"}", apiRequest.getREQUEST_OUTPUT()));
//	        if (productiteminventoryitems.length()>0) {
//	    		List<Object> salesprices = new ArrayList<Object>();
//
//	    		JSONObject productiteminventoryitem = productiteminventoryitems.getJSONObject(0);
//				JSONArray productiteminventoryitempricelevels = new JSONArray(ServiceCall.POST("productiteminventoryitempricelevel/advancedsearch", "{productiteminventoryitem_ID: "+productiteminventoryitem.getLong("productiteminventoryitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
//		        for (int j=0; j<productiteminventoryitempricelevels.length(); j++) {
//		    		JSONObject productiteminventoryitempricelevel = new JSONObject();	
//		    		productiteminventoryitempricelevel.put("productiteminventory_sales_price_type_id", "264fdb5bd99e45c389e5e322f87d6780");
//		    		productiteminventoryitempricelevel.put("price", productiteminventoryitempricelevels.getJSONObject(j).getDouble("productiteminventoryitem_UNITPRICE"));
//		    		productiteminventoryitempricelevel.put("price_includes_tax", "true");
//
//		    		salesprices.add(productiteminventoryitempricelevel);
//		        }
//		        objProductItemInventoryDetail.put("sales_prices", salesprices);
//				
//		        
////				JSONArray productiteminventoryiteminventories = new JSONArray(ServiceCall.POST("productiteminventoryiteminventory/advancedsearch", "{productiteminventoryitem_ID: "+productiteminventoryitem.getLong("productiteminventoryitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
////		        for (int j=0; j<productiteminventoryitempricelevels.length(); j++) {
////		        }
//
//				JSONArray productiteminventoryitemattributevalues = new JSONArray(ServiceCall.POST("productiteminventoryitemattributevalue/advancedsearch", "{productiteminventoryitem_ID: "+productiteminventoryitem.getLong("productiteminventoryitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
//		        for (int j=0; j<productiteminventoryitemattributevalues.length(); j++) {
//		        	JSONObject productiteminventoryitemattributevalue = productiteminventoryitemattributevalues.getJSONObject(j);
//					JSONObject jsonproductiteminventoryattribute = new JSONObject(productiteminventoryitemattributevalue.getString("productiteminventoryattribute_DETAIL"));
//					JSONObject jsonattribute = new JSONObject(jsonproductiteminventoryattribute.getString("attribute_DETAIL"));
//
//					if (jsonattribute.getString("attribute_KEY").equals("taxcode")) {
//						if (productiteminventoryitemattributevalues.getJSONObject(j).getLong("productiteminventoryattributevalue_ID")==1) {
//				    		objProductItemInventoryDetail.put("sales_tax_rate_id", "GB_STANDARD");
//				    		objProductItemInventoryDetail.put("purchase_tax_rate_id", "GB_STANDARD");
//						} else {
//				    		objProductItemInventoryDetail.put("sales_tax_rate_id", "GB_ZERO");
//				    		objProductItemInventoryDetail.put("purchase_tax_rate_id", "GB_ZERO");
//						}
//					}
//
//					if (!productiteminventoryitemattributevalue.isNull("productiteminventoryattributeitem_ID"))
//						productiteminventoryitem.put(jsonattribute.getString("attribute_KEY"), productiteminventoryitemattributevalues.getJSONObject(j).getLong("productiteminventoryattributevalue_ID"));
//					else if (!productiteminventoryitemattributevalues.getJSONObject(j).isNull("productiteminventoryattributeitem_VALUE"))
//						productiteminventoryitem.put(jsonattribute.getString("attribute_KEY"), productiteminventoryitemattributevalues.getJSONObject(j).getString("productiteminventoryattributeitem_VALUE"));
//				}
//			}
//
//
//			objProductItemInventory.put("stock_item", objProductItemInventoryDetail);
//			JSONObject response = new JSONObject(SageService.POST("stock_items", objProductItemInventory.toString(), headToken));
//			
//			JSONObject productiteminventory = new JSONObject();
//			productiteminventory.put("productiteminventory_ID", productiteminventories.get(i).getPRODUCT_ID());
//			productiteminventory.put("sage_ID", response.getString("id"));
//			productiteminventory = new JSONObject(ServiceCall.POST("productiteminventory", productiteminventory.toString(), apiRequest.getREQUEST_OUTPUT()));
//
//			objProductItemInventorys.put(objProductItemInventory);
//			
//        }
		
		return new ResponseEntity(getAPIResponse(null, null , objProductItemInventorys, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/updatestock", method = RequestMethod.GET)
    public ResponseEntity UpdateStock(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException {
        APIRequestDataLog apiRequest = checkToken("GET", "/productiteminventory/updatestock", null, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);


        JSONArray objProductItemInventorys = new JSONArray();
        
//        List<ProductItemInventoryItemInventory> productiteminventories = productiteminventoryiteminventoryrepository.findByNullSageID();
//        for (int i=0; i<productiteminventories.size(); i++) {
//            JSONObject objProductItemInventory = new JSONObject();
//            JSONObject objProductItemInventoryDetail = new JSONObject(); 
//
//            objProductItemInventoryDetail.put("catalog_item_type_id", "STOCK_ITEM"); 
//            objProductItemInventoryDetail.put("item_code", productiteminventories.get(i).getPRODUCT_CODE());
//            objProductItemInventoryDetail.put("description", productiteminventories.get(i).getPRODUCT_DESC()); 
//            objProductItemInventoryDetail.put("sales_ledger_account_id", "819fd52d842f11ed84fa0252b90cda0d");
//            objProductItemInventoryDetail.put("purchase_ledger_account_id", "81a0cf2c842f11ed84fa0252b90cda0d");
//
//            JSONArray productiteminventoryitems = new JSONArray(ServiceCall.POST("productiteminventoryitem/advancedsearch", "{productiteminventory_ID: "+productiteminventories.get(i).getPRODUCT_ID()+"}", apiRequest.getREQUEST_OUTPUT()));
//            if (productiteminventoryitems.length()>0) {
//                List<Object> salesprices = new ArrayList<Object>();
//
//                JSONObject productiteminventoryitem = productiteminventoryitems.getJSONObject(0);
//                JSONArray productiteminventoryitempricelevels = new JSONArray(ServiceCall.POST("productiteminventoryitempricelevel/advancedsearch", "{productiteminventoryitem_ID: "+productiteminventoryitem.getLong("productiteminventoryitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
//                for (int j=0; j<productiteminventoryitempricelevels.length(); j++) {
//                    JSONObject productiteminventoryitempricelevel = new JSONObject();    
//                    productiteminventoryitempricelevel.put("productiteminventory_sales_price_type_id", "264fdb5bd99e45c389e5e322f87d6780");
//                    productiteminventoryitempricelevel.put("price", productiteminventoryitempricelevels.getJSONObject(j).getDouble("productiteminventoryitem_UNITPRICE"));
//                    productiteminventoryitempricelevel.put("price_includes_tax", "true");
//
//                    salesprices.add(productiteminventoryitempricelevel);
//                }
//                objProductItemInventoryDetail.put("sales_prices", salesprices);
//                
//                
////              JSONArray productiteminventoryiteminventories = new JSONArray(ServiceCall.POST("productiteminventoryiteminventory/advancedsearch", "{productiteminventoryitem_ID: "+productiteminventoryitem.getLong("productiteminventoryitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
////              for (int j=0; j<productiteminventoryitempricelevels.length(); j++) {
////              }
//
//                JSONArray productiteminventoryitemattributevalues = new JSONArray(ServiceCall.POST("productiteminventoryitemattributevalue/advancedsearch", "{productiteminventoryitem_ID: "+productiteminventoryitem.getLong("productiteminventoryitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
//                for (int j=0; j<productiteminventoryitemattributevalues.length(); j++) {
//                    JSONObject productiteminventoryitemattributevalue = productiteminventoryitemattributevalues.getJSONObject(j);
//                    JSONObject jsonproductiteminventoryattribute = new JSONObject(productiteminventoryitemattributevalue.getString("productiteminventoryattribute_DETAIL"));
//                    JSONObject jsonattribute = new JSONObject(jsonproductiteminventoryattribute.getString("attribute_DETAIL"));
//
//                    if (jsonattribute.getString("attribute_KEY").equals("taxcode")) {
//                        if (productiteminventoryitemattributevalues.getJSONObject(j).getLong("productiteminventoryattributevalue_ID")==1) {
//                            objProductItemInventoryDetail.put("sales_tax_rate_id", "GB_STANDARD");
//                            objProductItemInventoryDetail.put("purchase_tax_rate_id", "GB_STANDARD");
//                        } else {
//                            objProductItemInventoryDetail.put("sales_tax_rate_id", "GB_ZERO");
//                            objProductItemInventoryDetail.put("purchase_tax_rate_id", "GB_ZERO");
//                        }
//                    }
//
//                    if (!productiteminventoryitemattributevalue.isNull("productiteminventoryattributeitem_ID"))
//                        productiteminventoryitem.put(jsonattribute.getString("attribute_KEY"), productiteminventoryitemattributevalues.getJSONObject(j).getLong("productiteminventoryattributevalue_ID"));
//                    else if (!productiteminventoryitemattributevalues.getJSONObject(j).isNull("productiteminventoryattributeitem_VALUE"))
//                        productiteminventoryitem.put(jsonattribute.getString("attribute_KEY"), productiteminventoryitemattributevalues.getJSONObject(j).getString("productiteminventoryattributeitem_VALUE"));
//                }
//            }
//
//
//            objProductItemInventory.put("stock_item", objProductItemInventoryDetail);
//            JSONObject response = new JSONObject(SageService.POST("stock_items", objProductItemInventory.toString(), headToken));
//            
//            JSONObject productiteminventory = new JSONObject();
//            productiteminventory.put("productiteminventory_ID", productiteminventories.get(i).getPRODUCT_ID());
//            productiteminventory.put("sage_ID", response.getString("id"));
//            productiteminventory = new JSONObject(ServiceCall.POST("productiteminventory", productiteminventory.toString(), apiRequest.getREQUEST_OUTPUT()));
//
//            objProductItemInventorys.put(objProductItemInventory);
//            
//        }
        
        return new ResponseEntity(getAPIResponse(null, null , objProductItemInventorys, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
    }

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItemInventory.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductItemInventory> productiteminventories, ProductItemInventory productiteminventory , JSONArray jsonProductItemInventorys, JSONObject jsonProductItemInventory, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productiteminventoryID = 0;
		
//		if (message != null) {
//			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductItemInventory", message);
//			apirequestdatalogRepository.saveAndFlush(apiRequest);
//		} else {
//			if (productiteminventory != null) {
//				JSONObject productiteminventorycategory = new JSONObject(ServiceCall.GET("productiteminventorycategory/"+productiteminventory.getPRODUCTCATEGORY_ID(), apiRequest.getREQUEST_OUTPUT()));
//				productiteminventory.setPRODUCTCATEGORY_DETAIL(productiteminventorycategory.toString());
//				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productiteminventory));
//				productiteminventoryID = productiteminventory.getPRODUCT_ID();
//			} else if(productiteminventories != null){
//				if (productiteminventories.size()>0) {
//					List<Integer> productiteminventorycategoryList = new ArrayList<Integer>();
//					for (int i=0; i<productiteminventories.size(); i++) {
//						productiteminventorycategoryList.add(Integer.parseInt(productiteminventories.get(i).getPRODUCTCATEGORY_ID().toString()));
//					}
//					JSONArray productiteminventorycategoryObject = new JSONArray(ServiceCall.POST("productiteminventorycategory/ids", "{productiteminventorycategories: "+productiteminventorycategoryList+"}", apiRequest.getREQUEST_OUTPUT()));
//					
//					for (int i=0; i<productiteminventories.size(); i++) {
//						for (int j=0; j<productiteminventorycategoryObject.length(); j++) {
//							JSONObject productiteminventorycategory = productiteminventorycategoryObject.getJSONObject(j);
//							if(productiteminventories.get(i).getPRODUCTCATEGORY_ID() == productiteminventorycategory.getLong("productiteminventorycategory_ID") ) {
//								productiteminventories.get(i).setPRODUCTCATEGORY_DETAIL(productiteminventorycategory.toString());
//							}
//						}
//					}
//				}
//				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productiteminventories));
//			} else if (jsonProductItemInventorys != null){
//				apiRequest.setREQUEST_OUTPUT(jsonProductItemInventorys.toString());
//			
//			} else if (jsonProductItemInventory != null){
//				apiRequest.setREQUEST_OUTPUT(jsonProductItemInventory.toString());
//			} else {
//				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productiteminventory));
//			}
//			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
//			apiRequest.setREQUEST_STATUS("Success");
//			apirequestdatalogRepository.saveAndFlush(apiRequest);
//		}
//		
//		if (isTableLog)
//			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productiteminventoryID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
//		
//		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
//			apiRequest.setREQUEST_OUTPUT(null);
//		
//		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
//		log.info("--------------------------------------------------------");

		return apiRequest;
	}

}
