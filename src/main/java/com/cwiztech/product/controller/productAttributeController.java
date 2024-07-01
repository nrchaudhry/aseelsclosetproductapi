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
import org.springframework.web.bind.annotation.RequestBody;
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

import com.cwiztech.product.model.ProductAttribute;
import com.cwiztech.product.repository.productAttributeRepository;
import com.cwiztech.services.ServiceCall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productattribute")

public class productAttributeController {
	
	private static final Logger log = LoggerFactory.getLogger(productAttributeController.class);

	@Autowired
	private productAttributeRepository productattributerepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattribute", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductAttribute> productattributes = productattributerepository.findActive();
		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattribute/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductAttribute> productattributes = productattributerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattribute/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductAttribute productattribute = productattributerepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productattribute , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattribute/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productattribute_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductattributes = jsonObj.getJSONArray("productattributes");
		for (int i=0; i<jsonproductattributes.length(); i++) {
			productattribute_IDS.add((Integer) jsonproductattributes.get(i));
		}
		List<ProductAttribute> productattributes = new ArrayList<ProductAttribute>();
		if (jsonproductattributes.length()>0)
			productattributes = productattributerepository.findByIDs(productattribute_IDS);
		
		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattribute/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productattribute_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductattributes = jsonObj.getJSONArray("productattributes");
		for (int i=0; i<jsonproductattributes.length(); i++) {
			productattribute_IDS.add((Integer) jsonproductattributes.get(i));
		}
		List<ProductAttribute> productattributes = new ArrayList<ProductAttribute>();
		if (jsonproductattributes.length()>0)
			productattributes = productattributerepository.findByNotInIDs(productattribute_IDS);
		
		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattribute", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/productattribute/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productattribute", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductAttributes, JSONObject jsonProductAttribute, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductAttribute> productattributes = new ArrayList<ProductAttribute>();
		if (jsonProductAttribute != null) {
			jsonProductAttributes = new JSONArray();
			jsonProductAttributes.put(jsonProductAttribute);
		}
		log.info(jsonProductAttributes.toString());
		
		for (int a=0; a<jsonProductAttributes.length(); a++) {
			JSONObject jsonObj = jsonProductAttributes.getJSONObject(a);
			ProductAttribute productattribute = new ProductAttribute();
			long productattributeid = 0;

			if (jsonObj.has("productattribute_ID")) {
				productattributeid = jsonObj.getLong("productattribute_ID");
				if (productattributeid != 0) {
					productattribute = productattributerepository.findOne(productattributeid);
					
					if (productattribute == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductAttribute Data!", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (productattributeid == 0) {
				if (!jsonObj.has("attribute_ID") || jsonObj.isNull("attribute_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attribute_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("attributeorder_NO") || jsonObj.isNull("attributeorder_NO"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attributeorder_NO is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
				if (!jsonObj.has("attributecategory_ID") || jsonObj.isNull("attributecategory_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attributecategory_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productcategory_ID") || jsonObj.isNull("productcategory_ID") || !jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productcategory_ID/product_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("isrequired") || jsonObj.isNull("isrequired"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "isrequired is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			}
			
			if (jsonObj.has("attribute_ID") && !jsonObj.isNull("attribute_ID"))
				productattribute.setATTRIBUTE_ID(jsonObj.getLong("attribute_ID"));
			
			if (jsonObj.has("attributeorder_NO") && !jsonObj.isNull("attributeorder_NO"))
				productattribute.setATTRIBUTEORDER_NO(jsonObj.getLong("attributeorder_NO"));
			
			if (jsonObj.has("attributecategory_ID") && !jsonObj.isNull("attributecategory_ID"))
				productattribute.setATTRIBUTECATEGORY_ID(jsonObj.getLong("attributecategory_ID"));
			
			if (jsonObj.has("productcategory_ID") && !jsonObj.isNull("productcategory_ID"))
				productattribute.setPRODUCTCATEGORY_ID(jsonObj.getLong("productcategory_ID"));
			
			if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID"))
				productattribute.setPRODUCT_ID(jsonObj.getLong("product_ID"));
			
			if (jsonObj.has("isrequired") && !jsonObj.isNull("isrequired"))
				productattribute.setISREQUIRED(jsonObj.getString("isrequired"));
			
			if (jsonObj.has("showinlist") && !jsonObj.isNull("showinlist"))
				productattribute.setSHOWINLIST(jsonObj.getString("showinlist"));
		
			if (productattributeid == 0)
				productattribute.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				productattribute.setISACTIVE(jsonObj.getString("isactive"));

			productattribute.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productattribute.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productattribute.setMODIFIED_WHEN(dateFormat1.format(date));
			productattributes.add(productattribute);
		}
		
		for (int a=0; a<productattributes.size(); a++) {
			ProductAttribute productattribute = productattributes.get(a);
			productattribute = productattributerepository.saveAndFlush(productattribute);
			productattributes.get(a).setPRODUCTATTRIBUTE_ID(productattribute.getPRODUCTATTRIBUTE_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonProductAttribute != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productattributes.get(0) , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattribute/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductAttribute productattribute = productattributerepository.findOne(id);
		productattributerepository.delete(productattribute);
		
		return new ResponseEntity(getAPIResponse(null, productattribute , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattribute/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject productattribute = new JSONObject();
		productattribute.put("id", id);
		productattribute.put("isactive", "N");
		
		return insertupdateAll(null, productattribute, apiRequest);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity getBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		return BySearch(data, true, headToken);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search/all", method = RequestMethod.POST)
	public ResponseEntity getAllBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		return BySearch(data, false, headToken);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity BySearch(String data, boolean active, String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattribute/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductAttribute> productattributes = ((active == true)
				? productattributerepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: productattributerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/advancedsearch", method = RequestMethod.POST)
	public ResponseEntity getByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		return ByAdvancedSearch(data, true, headToken);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/advancedsearch/all", method = RequestMethod.POST)
	public ResponseEntity getAllByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		return ByAdvancedSearch(data, false, headToken);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity ByAdvancedSearch(String data, boolean active, String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattribute/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
        List<ProductAttribute> productattributes = new ArrayList<ProductAttribute>();
		JSONObject jsonObj = new JSONObject(data);
		
		   JSONArray searchObject = new JSONArray();
	        List<Integer> attributecategory_IDS = new ArrayList<Integer>(); 
	        List<Integer> productcategory_IDS = new ArrayList<Integer>(); 
	        List<Integer> attribute_IDS = new ArrayList<Integer>(); 
	        List<Integer> product_IDS = new ArrayList<Integer>(); 

	        attribute_IDS.add((int) 0);
	        product_IDS.add((int) 0);
	        attributecategory_IDS.add((int) 0);
	        productcategory_IDS.add((int) 0);
	        
		long attributecategory_ID = 0 , productcategory_ID = 0, attribute_ID = 0 , product_ID = 0;

		
       boolean isWithDetail = true;
       if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
           isWithDetail = jsonObj.getBoolean("iswithdetail");
       }
       jsonObj.put("iswithdetail", false);
		
       if (jsonObj.has("attributecategory_ID") && !jsonObj.isNull("attributecategory_ID") && jsonObj.getLong("attributecategory_ID") != 0) {
           attributecategory_ID = jsonObj.getLong("attributecategory_ID");
           attributecategory_IDS.add((int) attributecategory_ID);
       } else if (jsonObj.has("attributecategory") && !jsonObj.isNull("attributecategory") && jsonObj.getLong("attributecategory") != 0) {
           if (active == true) {
               searchObject = new JSONArray(ServiceCall.POST("attributecategory/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
           } else {
               searchObject = new JSONArray(ServiceCall.POST("attributecategory/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
           }

           attributecategory_ID = searchObject.length();
           for (int i=0; i<searchObject.length(); i++) {
               attributecategory_IDS.add((int) searchObject.getJSONObject(i).getLong("attributecategory_ID"));
           }
       }
		
       if (jsonObj.has("productcategory_ID") && !jsonObj.isNull("productcategory_ID") && jsonObj.getLong("productcategory_ID") != 0) {
           productcategory_ID = jsonObj.getLong("productcategory_ID");
           productcategory_IDS.add((int) productcategory_ID);
       } else if (jsonObj.has("productcategory") && !jsonObj.isNull("productcategory") && jsonObj.getLong("productcategory") != 0) {
           if (active == true) {
               searchObject = new JSONArray(ServiceCall.POST("productcategory/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
           } else {
               searchObject = new JSONArray(ServiceCall.POST("productcategory/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
           }

           productcategory_ID = searchObject.length();
           for (int i=0; i<searchObject.length(); i++) {
               productcategory_IDS.add((int) searchObject.getJSONObject(i).getLong("productcategory_ID"));
           }
       }
		
       if (jsonObj.has("attribute_ID") && !jsonObj.isNull("attribute_ID") && jsonObj.getLong("attribute_ID") != 0) {
           attribute_ID = jsonObj.getLong("attribute_ID");
           attribute_IDS.add((int) attribute_ID);
       } else if (jsonObj.has("attribute") && !jsonObj.isNull("attribute") && jsonObj.getLong("attribute") != 0) {
           if (active == true) {
               searchObject = new JSONArray(ServiceCall.POST("attribute/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
           } else {
               searchObject = new JSONArray(ServiceCall.POST("attribute/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
           }

           attribute_ID = searchObject.length();
           for (int i=0; i<searchObject.length(); i++) {
               attribute_IDS.add((int) searchObject.getJSONObject(i).getLong("attribute_ID"));
           }
       }
		
       if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID") && jsonObj.getLong("product_ID") != 0) {
           product_ID = jsonObj.getLong("product_ID");
           product_IDS.add((int) product_ID);
       } else if (jsonObj.has("product") && !jsonObj.isNull("product") && jsonObj.getLong("product") != 0) {
           if (active == true) {
               searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
           } else {
               searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
           }

           product_ID = searchObject.length();
           for (int i=0; i<searchObject.length(); i++) {
               product_IDS.add((int) searchObject.getJSONObject(i).getLong("product_ID"));
           }
       }
	
		List<ProductAttribute> productattribute = ((active == true)
				? productattributerepository.findByAdvancedSearch(productcategory_ID,productcategory_IDS, product_ID,product_IDS, attributecategory_ID,attributecategory_IDS, attribute_ID, attribute_IDS)
				: productattributerepository.findAllByAdvancedSearch(productcategory_ID,productcategory_IDS, product_ID,product_IDS, attributecategory_ID,attributecategory_IDS, attribute_ID, attribute_IDS));		
		
		
//		if (jsonObj.has("withattributevalue")) {
//			JSONArray productattributewithvalue = new JSONArray();
//			for (int i=0; i<productattribute.size(); i++) {
//				JSONObject obj = new JSONObject();
//				JSONObject jsonproduct = new JSONObject(ServiceCall.GET("product/"+productattribute.get(i).getPRODUCT_ID(), apiRequest.getREQUEST_OUTPUT()));
//				JSONObject jsonproductcategory = new JSONObject(ServiceCall.GET("productcategory/"+productattribute.get(i).getPRODUCTCATEGORY_ID(), apiRequest.getREQUEST_OUTPUT()));
//				obj.put("productattribute_ID", productattribute.get(i).getPRODUCTATTRIBUTE_ID());
//				obj.put("product_ID", productattribute.get(i).getPRODUCT_ID());
//				obj.put("product_NAME", jsonproduct.getString("product_NAME").replace("\"", ""));
//				obj.put("product_DESC", jsonproduct.getString("product_DESC").replace("\"", ""));
//				obj.put("productcategory_ID", productattribute.get(i).getPRODUCTCATEGORY_ID());
//				obj.put("productcategory_NAME", jsonproductcategory.getString("productcategory_NAME").replace("\"", ""));
//				obj.put("productcategory_DESC", jsonproductcategory.getString("productcategory_DESC").replace("\"", ""));
//				obj.put("productattribute_NAME", productattribute.get(i).getPRODUCTATTRIBUTE_NAME());
//				obj.put("productattribute_DESC", productattribute.get(i).getPRODUCTATTRIBUTE_DESC());
//				obj.put("datatype_CODE", productattribute.get(i).getDATATYPE_ID().getCODE());
//				obj.put("datatype_ENTITYSTATUS", productattribute.get(i).getDATATYPE_ID().getENTITY_STATUS());
//
//				if (productattribute.get(i).getDATATYPE_ID().getCODE().compareTo("4") == 0 || productattribute.get(i).getDATATYPE_ID().getCODE().compareTo("6") == 0) {
//					List<ProductAttributeValue> productattributevalue = productattributevaluerepository.findByAdvancedSearch(productattribute.get(i).getPRODUCTATTRIBUTE_ID(), (long) 0);
//					JSONArray productattributevalues = new JSONArray();
//					for (int j=0; j<productattributevalue.size(); j++) {
//						JSONObject objattributevalues = new JSONObject();
//						objattributevalues.put("productattributevalue_ID", productattributevalue.get(j).getPRODUCTATTRIBUTEVALUE_ID());
//						objattributevalues.put("productattributevalueparent_ID", productattributevalue.get(j).getPRODUCTATTRIBUTEVALUEPARENT_ID());
//						objattributevalues.put("productattribute_VALUE", productattributevalue.get(j).getPRODUCTATTRIBUTE_VALUE());
//						productattributevalues.put(objattributevalues);
//					}
//					obj.put("productattribute_VALUE", productattributevalues);
//				}
//				productattributewithvalue.put(obj);
//			}
//			rtn = productattributewithvalue.toString();
//		}
//		else{
//			rtn = mapper.writeValueAsString(productattribute);
//		}

		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, false, isWithDetail).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductAttribute.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductAttribute> productattributes, ProductAttribute productattribute , JSONArray jsonProductAttributes, JSONObject jsonProductAttribute, String message, APIRequestDataLog apiRequest, boolean isTableLog,boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productattributeID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductAttribute", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productattribute != null && isWithDetail == true) {
				if (productattribute.getPRODUCT_ID() != null) {
					JSONObject product = new JSONObject(ServiceCall.GET("product/"+productattribute.getPRODUCT_ID(), apiRequest.getREQUEST_OUTPUT(), false));
					productattribute.setPRODUCT_DETAIL(product.toString());
				}

				if (productattribute.getPRODUCTCATEGORY_ID() != null) {
					JSONObject productcategory = new JSONObject(ServiceCall.GET("productcategory/"+productattribute.getPRODUCTCATEGORY_ID(), apiRequest.getREQUEST_OUTPUT(), false));
					productattribute.setPRODUCTCATEGORY_DETAIL(productcategory.toString());
				}
				JSONObject attribute = new JSONObject(ServiceCall.GET("attribute/"+productattribute.getATTRIBUTE_ID(), apiRequest.getREQUEST_OUTPUT(), false));
				productattribute.setATTRIBUTE_DETAIL(attribute.toString());
				
				JSONObject attributecategory = new JSONObject(ServiceCall.GET("attributecategory/"+productattribute.getATTRIBUTECATEGORY_ID(), apiRequest.getREQUEST_OUTPUT(), false));
				productattribute.setATTRIBUTECATEGORY_DETAIL(attributecategory.toString());
				
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productattribute));
				productattributeID = productattribute.getPRODUCTATTRIBUTE_ID();
			} else if(productattributes != null && isWithDetail == true){
				if (productattributes.size()>0) {
					List<Integer> productList = new ArrayList<Integer>();
					List<Integer> productcategoryList = new ArrayList<Integer>();
					List<Integer> attributeList = new ArrayList<Integer>();
					List<Integer> attributecategoryList = new ArrayList<Integer>();
					for (int i=0; i<productattributes.size(); i++) {
						if (productattributes.get(i).getPRODUCT_ID() != null)
							productList.add(Integer.parseInt(productattributes.get(i).getPRODUCT_ID().toString()));
						if (productattributes.get(i).getPRODUCTCATEGORY_ID() != null)
							productcategoryList.add(Integer.parseInt(productattributes.get(i).getPRODUCTCATEGORY_ID().toString()));
						if (productattributes.get(i).getATTRIBUTE_ID() != null)
						    attributeList.add(Integer.parseInt(productattributes.get(i).getATTRIBUTE_ID().toString()));
						if (productattributes.get(i).getATTRIBUTECATEGORY_ID() != null)
						    attributecategoryList.add(Integer.parseInt(productattributes.get(i).getATTRIBUTECATEGORY_ID().toString()));
					}
					JSONArray productObject = new JSONArray(ServiceCall.POST("product/ids", "{products: "+productList+"}", apiRequest.getREQUEST_OUTPUT(), false));
					JSONArray productcategoryObject = new JSONArray(ServiceCall.POST("productcategory/ids", "{productcategories: "+productcategoryList+"}", apiRequest.getREQUEST_OUTPUT(), false));
					JSONArray attributeObject = new JSONArray(ServiceCall.POST("attribute/ids", "{attributes: "+attributeList+"}", apiRequest.getREQUEST_OUTPUT(), false));
					JSONArray attributecategoryObject = new JSONArray(ServiceCall.POST("attributecategory/ids", "{attributecategories: "+attributecategoryList+"}", apiRequest.getREQUEST_OUTPUT(), false));
					
					for (int i=0; i<productattributes.size(); i++) {
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if(productattributes.get(i).getPRODUCT_ID() == product.getLong("product_ID") ) {
								productattributes.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
						for (int j=0; j<productcategoryObject.length(); j++) {
							JSONObject productcategory = productcategoryObject.getJSONObject(j);
							if(productattributes.get(i).getPRODUCTCATEGORY_ID() == productcategory.getLong("productcategory_ID") ) {
								productattributes.get(i).setPRODUCTCATEGORY_DETAIL(productcategory.toString());
							}
						}

						for (int j=0; j<attributeObject.length(); j++) {
							JSONObject attribute = attributeObject.getJSONObject(j);
							if(productattributes.get(i).getATTRIBUTE_ID() == attribute.getLong("attribute_ID") ) {
								productattributes.get(i).setATTRIBUTE_DETAIL(attribute.toString());
							}
						}

						for (int j=0; j<attributecategoryObject.length(); j++) {
							JSONObject attributecategory = attributecategoryObject.getJSONObject(j);
							if(productattributes.get(i).getATTRIBUTECATEGORY_ID() == attributecategory.getLong("attributecategory_ID") ) {
								productattributes.get(i).setATTRIBUTECATEGORY_DETAIL(attributecategory.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productattributes));
			}else if (jsonProductAttributes != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductAttributes.toString());
			
			} else if (jsonProductAttribute != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductAttribute.toString());
			
		} else if (jsonProductAttributes != null){
			apiRequest.setREQUEST_OUTPUT(jsonProductAttributes.toString());
		} else if (jsonProductAttribute != null){
			apiRequest.setREQUEST_OUTPUT(jsonProductAttribute.toString());
		}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productattributeID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}
}

	
	


