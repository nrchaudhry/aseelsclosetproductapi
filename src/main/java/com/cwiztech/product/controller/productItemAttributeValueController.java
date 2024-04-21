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
import com.cwiztech.product.model.ProductItemAttributeValue;
import com.cwiztech.product.repository.productItemAttributeValueRepository;
import com.cwiztech.services.ProductService;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/productitemattributevalue")

public class productItemAttributeValueController {
	
	private static final Logger log = LoggerFactory.getLogger(productItemAttributeValueController.class);

	@Autowired
	private productItemAttributeValueRepository productitemattributevaluerepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemattributevalue", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemAttributeValue> productitemattributevalues = productitemattributevaluerepository.findActive();
		return new ResponseEntity(getAPIResponse(productitemattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemattributevalue/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemAttributeValue> productitemattributevalues = productitemattributevaluerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productitemattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemattributevalue/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItemAttributeValue productitemattributevalue = productitemattributevaluerepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productitemattributevalue , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemattributevalue/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitemattributevalue_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemattributevalues = jsonObj.getJSONArray("productitemattributevalues");
		for (int i=0; i<jsonproductitemattributevalues.length(); i++) {
			productitemattributevalue_IDS.add((Integer) jsonproductitemattributevalues.get(i));
		}
		List<ProductItemAttributeValue> productitemattributevalues = new ArrayList<ProductItemAttributeValue>();
		if (jsonproductitemattributevalues.length()>0)
			productitemattributevalues = productitemattributevaluerepository.findByIDs(productitemattributevalue_IDS);
		
		return new ResponseEntity(getAPIResponse(productitemattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/productitem/ids", method = RequestMethod.POST)
	public ResponseEntity getByProductItemIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemattributevalue/productitem/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitem_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitems = jsonObj.getJSONArray("items");
		for (int i=0; i<jsonproductitems.length(); i++) {
			productitem_IDS.add((Integer) jsonproductitems.get(i));
		}
		List<ProductItemAttributeValue> productitemattributevalues = new ArrayList<ProductItemAttributeValue>();
		if (jsonproductitems.length()>0)
			productitemattributevalues = productitemattributevaluerepository.findByProductItemIDs(productitem_IDS);
				
		return new ResponseEntity(getAPIResponse(productitemattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemattributevalue/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitemattributevalue_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemattributevalues = jsonObj.getJSONArray("productitemattributevalues");
		for (int i=0; i<jsonproductitemattributevalues.length(); i++) {
			productitemattributevalue_IDS.add((Integer) jsonproductitemattributevalues.get(i));
		}
		List<ProductItemAttributeValue> productitemattributevalues = new ArrayList<ProductItemAttributeValue>();
		if (jsonproductitemattributevalues.length()>0)
			productitemattributevalues = productitemattributevaluerepository.findByNotInIDs(productitemattributevalue_IDS);
		
		return new ResponseEntity(getAPIResponse(productitemattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemattributevalue", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/productitemattributevalue/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productitemattributevalue", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductItemAttributeValues, JSONObject jsonProductItemAttributeValue, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductItemAttributeValue> productitemattributevalues = new ArrayList<ProductItemAttributeValue>();
		if (jsonProductItemAttributeValue != null) {
			jsonProductItemAttributeValues = new JSONArray();
			jsonProductItemAttributeValues.put(jsonProductItemAttributeValue);
		}
		log.info(jsonProductItemAttributeValues.toString());
		
		for (int a=0; a<jsonProductItemAttributeValues.length(); a++) {
			JSONObject jsonObj = jsonProductItemAttributeValues.getJSONObject(a);
			ProductItemAttributeValue productitemattributevalue = new ProductItemAttributeValue();
			long productitemattributevalueid = 0;

			if (jsonObj.has("productitemattributevalue_ID")) {
				productitemattributevalueid = jsonObj.getLong("productitemattributevalue_ID");
				if (productitemattributevalueid != 0) {
					productitemattributevalue = productitemattributevaluerepository.findOne(productitemattributevalueid);
					
					if (productitemattributevalue == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductItemAttributeValue Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (productitemattributevalueid == 0) {
				if (!jsonObj.has("productitem_ID") || jsonObj.isNull("productitem_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productattribute_ID") || jsonObj.isNull("productattribute_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productattribute_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
			}
			if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID"))
		    	productitemattributevalue.setPRODUCTITEM_ID(jsonObj.getLong("productitem_ID"));
		
		    if (jsonObj.has("productattribute_ID") && !jsonObj.isNull("productattribute_ID"))
		    	productitemattributevalue.setPRODUCTATTRIBUTE_ID(jsonObj.getLong("productattribute_ID"));
		
		    if (jsonObj.has("productattributevalue_ID") && !jsonObj.isNull("productattributevalue_ID"))
		    	productitemattributevalue.setPRODUCTATTRIBUTEVALUE_ID(jsonObj.getLong("productattributevalue_ID"));
		
		    if (jsonObj.has("productattributeitem_VALUE") && !jsonObj.isNull("productattributeitem_VALUE"))
			    productitemattributevalue.setPRODUCTATTRIBUTEITEM_VALUE(jsonObj.getString("productattributeitem_VALUE"));
			
		    if (productitemattributevalueid == 0)
				productitemattributevalue.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				productitemattributevalue.setISACTIVE(jsonObj.getString("isactive"));

			productitemattributevalue.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productitemattributevalue.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productitemattributevalue.setMODIFIED_WHEN(dateFormat1.format(date));
			productitemattributevalues.add(productitemattributevalue);
		}
		
		for (int a=0; a<productitemattributevalues.size(); a++) {
			ProductItemAttributeValue productitemattributevalue = productitemattributevalues.get(a);
			productitemattributevalue = productitemattributevaluerepository.saveAndFlush(productitemattributevalue);
			productitemattributevalues.get(a).setPRODUCTITEMATTRIBUTEVALUE_ID(productitemattributevalue.getPRODUCTITEMATTRIBUTEVALUE_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonProductItemAttributeValue != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productitemattributevalues.get(0) , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productitemattributevalues, null , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemattributevalue/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItemAttributeValue productitemattributevalue = productitemattributevaluerepository.findOne(id);
		productitemattributevaluerepository.delete(productitemattributevalue);
		
		return new ResponseEntity(getAPIResponse(null, productitemattributevalue , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemattributevalue/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject productitemattributevalue = new JSONObject();
		productitemattributevalue.put("id", id);
		productitemattributevalue.put("isactive", "N");
		
		return insertupdateAll(null, productitemattributevalue, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemattributevalue/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductItemAttributeValue> productitemattributevalues = ((active == true)
				? productitemattributevaluerepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: productitemattributevaluerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(productitemattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemattributevalue/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray productattributes = new JSONArray();
		long productitem_ID = 0;
		long productattribute_ID = 0;

		if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID")) {
			productitem_ID = jsonObj.getLong("productitem_ID");
			JSONObject productitem = new JSONObject(ProductService.GET("productitem/"+productitem_ID, apiRequest.getREQUEST_OUTPUT()));
			jsonObj.put("product_ID", productitem.getLong("product_ID"));
			productattributes = new JSONArray(ProductService.POST("productattribute/advancedsearch", jsonObj.toString(), apiRequest.getREQUEST_OUTPUT()));
		}
		
		if (jsonObj.has("productattribute_ID") && !jsonObj.isNull("productattribute_ID"))
			productattribute_ID = jsonObj.getLong("productattribute_ID");

		List<ProductItemAttributeValue> productitemattributevalues = ((active == true)
				? productitemattributevaluerepository.findByAdvancedSearch(productitem_ID, productattribute_ID)
				: productitemattributevaluerepository.findAllByAdvancedSearch(productitem_ID, productattribute_ID));

		for (int i=0; i<productattributes.length(); i++) {
			boolean found = false;
			for (int j=0; j<productitemattributevalues.size(); j++) {
				if (productattributes.getJSONObject(i).getLong("productattribute_ID") == productitemattributevalues.get(j).getPRODUCTATTRIBUTE_ID())
					found = true;
			}
			
			if (found == false) {
				ProductItemAttributeValue productitemattributevalue = new ProductItemAttributeValue(); 
				productitemattributevalue.setPRODUCTITEM_ID(productitem_ID);
				productitemattributevalue.setPRODUCTATTRIBUTE_ID(productattributes.getJSONObject(i).getLong("productattribute_ID"));
				productitemattributevalues.add(productitemattributevalue);
			}
		}
		
		
//		JSONObject jsonproductitemattributevalue = new JSONObject();
//		JSONArray productattributewithvalue = new JSONArray();
//		if (jsonObj.has("withattributevalue")) {
//			List<ProductAttribute> productattribute = ((active == true)
//					? productattributerepository.findByAdvancedSearch(productcategory_ID, product_ID,productattributecategory_ID,datatype_ID)
//					: productattributerepository.findAllByAdvancedSearch(productcategory_ID, product_ID,productattributecategory_ID,datatype_ID));
//			for (int i=0; i<productattribute.size(); i++) {
//				JSONObject obj = new JSONObject();
//				obj.put("productattribute_ID", productattribute.get(i).getPRODUCTATTRIBUTE_ID());
//				if (productattribute.get(i).getPRODUCT_ID()!=null) {
//					JSONObject jsonproduct = new JSONObject(ProductService.GET("product/"+productattribute.get(i).getPRODUCT_ID(), apiRequest.getREQUEST_OUTPUT()));
//					obj.put("product_ID", productattribute.get(i).getPRODUCT_ID());
//					obj.put("product_NAME", jsonproduct.getString("product_NAME").replace("\"", ""));
//					obj.put("product_DESC", jsonproduct.getString("product_DESC").replace("\"", ""));
//				} 
//				if (productattribute.get(i).getPRODUCTCATEGORY_ID()!=null) {
//					JSONObject jsonproductcategory = new JSONObject(ProductService.GET("productcategory/"+productattribute.get(i).getPRODUCTCATEGORY_ID(), apiRequest.getREQUEST_OUTPUT()));
//					obj.put("productcategory_ID", productattribute.get(i).getPRODUCTCATEGORY_ID());
//					obj.put("productcategory_NAME", jsonproductcategory.getString("productcategory_NAME").replace("\"", ""));
//					obj.put("productcategory_DESC", jsonproductcategory.getString("productcategory_DESC").replace("\"", ""));
//				} 
//				obj.put("productattribute_NAME", productattribute.get(i).getPRODUCTATTRIBUTE_NAME());
//				obj.put("productattribute_DESC", productattribute.get(i).getPRODUCTATTRIBUTE_DESC());
//				obj.put("datatype_CODE", productattribute.get(i).getDATATYPE_ID().getCODE());
//				obj.put("datatype_ENTITYSTATUS", productattribute.get(i).getDATATYPE_ID().getENTITY_STATUS());
//				
//				if (productattribute.get(i).getDATATYPE_ID().getCODE().compareTo("4") == 0 || productattribute.get(i).getDATATYPE_ID().getCODE().compareTo("6") == 0) {
//					List<ProductAttributeValue> productattributevalues = productattributevaluerepository.findByAdvancedSearch(productattribute.get(i).getPRODUCTATTRIBUTE_ID(), (long) 0);
//					JSONArray objproductattributevalues = new JSONArray();
//					for (int j=0; j<productattributevalues.size(); j++) {
//						JSONObject objattributevalues = new JSONObject();
//						objattributevalues.put("productattributevalue_ID", productattributevalues.get(j).getPRODUCTATTRIBUTEVALUE_ID());
//						objattributevalues.put("productattributevalueparent_ID", productattributevalues.get(j).getPRODUCTATTRIBUTEVALUEPARENT_ID());
//						objattributevalues.put("productattribute_VALUE", productattributevalues.get(j).getPRODUCTATTRIBUTE_VALUE());
//						objproductattributevalues.put(objattributevalues);
//					}
//					obj.put("productattribute_VALUE", objproductattributevalues);
//				}
//				
//				JSONObject objattributevalues = new JSONObject();
//				objattributevalues.put("productitemattributevalue_ID", 0);
//				objattributevalues.put("productattribute_VALUE", "");
//				
//				for (int j=0; j<productitemattributevalue.size(); j++) {
//					if (productattribute.get(i).getPRODUCTATTRIBUTE_ID()==productitemattributevalue.get(j).getPRODUCTATTRIBUTE_ID()) {
//						objattributevalues.put("productitem_ID", productitemattributevalue.get(j).getPRODUCTITEM_ID());
//						objattributevalues.put("productitemattributevalue_ID", productitemattributevalue.get(j).getPRODUCTITEMATTRIBUTEVALUE_ID());
//						if (productitemattributevalue.get(j).getPRODUCTATTRIBUTEVALUE_ID()==null)
//							objattributevalues.put("productattributeitem_VALUE", productitemattributevalue.get(j).getPRODUCTATTRIBUTEITEM_VALUE());
//						else
//						    jsonproductitemattributevalue = new JSONObject(ProductService.GET("productitemattributevalue/"+productitemattributevalue.get(j).getPRODUCTATTRIBUTEVALUE_ID(), apiRequest.getREQUEST_OUTPUT()));
//							objattributevalues.put("productattribute_VALUE", jsonproductitemattributevalue.getString("productattribute_VALUE"));
//						break;
//					}
//				}
//				obj.put("productitemattributevalue", objattributevalues);
//
//				productattributewithvalue.put(obj);
//			}
//			rtn = productattributewithvalue.toString();
//		}
//		else{
//			rtn = mapper.writeValueAsString(productitemattributevalue);
//		}

		return new ResponseEntity(getAPIResponse(productitemattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItemAttributeValue.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductItemAttributeValue> productitemattributevalues, ProductItemAttributeValue productitemattributevalue , JSONArray jsonProductItemAttributeValues, JSONObject jsonProductItemAttributeValue, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productitemattributevalueID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductItemAttributeValue", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productitemattributevalue != null) {
				JSONObject productitem = new JSONObject(ProductService.GET("productitem/"+productitemattributevalue.getPRODUCTITEM_ID(), apiRequest.getREQUEST_OUTPUT()));
				productitemattributevalue.setPRODUCTITEM_DETAIL(productitem.toString());
				
				JSONObject productattribute = new JSONObject(ProductService.GET("productattribute/"+productitemattributevalue.getPRODUCTATTRIBUTE_ID(), apiRequest.getREQUEST_OUTPUT()));
				productitemattributevalue.setPRODUCTATTRIBUTE_DETAIL(productattribute.toString());
				
//				if (productitemattributevalue.getPRODUCTATTRIBUTEVALUE_ID() != null) {
//					JSONObject productattributevalue = new JSONObject(ProductService.GET("productattributevalue/"+productitemattributevalue.getPRODUCTATTRIBUTEVALUE_ID(), apiRequest.getREQUEST_OUTPUT()));
//					productitemattributevalue.setPRODUCTATTRIBUTEVALUE_DETAIL(productattributevalue.toString());
//				}
				
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitemattributevalue));
				productitemattributevalueID = productitemattributevalue.getPRODUCTITEMATTRIBUTEVALUE_ID();
			} else if(productitemattributevalues != null){
				if (productitemattributevalues.size()>0) {
					List<Integer> productitemList = new ArrayList<Integer>();
					List<Integer> productattributevalueList = new ArrayList<Integer>();
					List<Integer> productattributeList = new ArrayList<Integer>();
					for (int i=0; i<productitemattributevalues.size(); i++) {
						if (productitemattributevalues.get(i).getPRODUCTATTRIBUTEVALUE_ID() != null)
							productattributevalueList.add(Integer.parseInt(productitemattributevalues.get(i).getPRODUCTATTRIBUTEVALUE_ID().toString()));
						if (productitemattributevalues.get(i).getPRODUCTITEM_ID() != null)			
							productitemList.add(Integer.parseInt(productitemattributevalues.get(i).getPRODUCTITEM_ID().toString()));
						if (productitemattributevalues.get(i).getPRODUCTATTRIBUTE_ID() != null)
							productattributeList.add(Integer.parseInt(productitemattributevalues.get(i).getPRODUCTATTRIBUTE_ID().toString()));

					}
					JSONArray productitemObject = new JSONArray(ProductService.POST("productitem/ids", "{items: "+productitemList+"}", apiRequest.getREQUEST_OUTPUT()));
					JSONArray productattributeObject = new JSONArray(ProductService.POST("productattribute/ids", "{productattributes: "+productattributeList+"}", apiRequest.getREQUEST_OUTPUT()));
//					JSONArray productattributevalueObject = new JSONArray(ProductService.POST("productattributevalue/ids", "{productattributevalues: "+productattributevalueList+"}", apiRequest.getREQUEST_OUTPUT()));
					
					for (int i=0; i<productitemattributevalues.size(); i++) {
						for (int j=0; j<productitemObject.length(); j++) {
							JSONObject productitem = productitemObject.getJSONObject(j);
							if(productitemattributevalues.get(i).getPRODUCTITEM_ID() == productitem.getLong("productitem_ID") ) {
								productitemattributevalues.get(i).setPRODUCTITEM_DETAIL(productitem.toString());
							}
						}
						for (int j=0; j<productattributeObject.length(); j++) {
							JSONObject productattribute = productattributeObject.getJSONObject(j);
							if(productitemattributevalues.get(i).getPRODUCTATTRIBUTE_ID() == productattribute.getLong("productattribute_ID") ) {
								productitemattributevalues.get(i).setPRODUCTATTRIBUTE_DETAIL(productattribute.toString());
							}
						}
//						for (int j=0; j<productattributevalueObject.length(); j++) {
//							JSONObject productattributevalue = productattributevalueObject.getJSONObject(j);
//							if(productitemattributevalues.get(i).getPRODUCTATTRIBUTEVALUE_ID() == productattributevalue.getLong("productattributevalue_ID") ) {
//								productitemattributevalues.get(i).setPRODUCTATTRIBUTEVALUE_DETAIL(productattributevalue.toString());
//							}
//						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitemattributevalues));
			}else if (jsonProductItemAttributeValues != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemAttributeValues.toString());
			
			} else if (jsonProductItemAttributeValue != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemAttributeValue.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productitemattributevalueID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}
}