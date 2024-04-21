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
import com.cwiztech.product.model.ProductAttributeValue;
import com.cwiztech.product.repository.productAttributeValueRepository;
import com.cwiztech.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productattributevalue")

public class productAttributeValueController {
	
	private static final Logger log = LoggerFactory.getLogger(productAttributeValueController.class);

	@Autowired
	private productAttributeValueRepository productattributevaluerepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattributevalue", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductAttributeValue> productattributevalues = productattributevaluerepository.findActive();
		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattributevalue/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductAttributeValue> productattributevalues = productattributevaluerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattributevalue/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductAttributeValue productattributevalue = productattributevaluerepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productattributevalue, null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattributevalue/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productattributevalue_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductattributevalues = jsonObj.getJSONArray("productattributevalues");
		for (int i=0; i<jsonproductattributevalues.length(); i++) {
			productattributevalue_IDS.add((Integer) jsonproductattributevalues.get(i));
		}
		List<ProductAttributeValue> productattributevalues = new ArrayList<ProductAttributeValue>();
		if (jsonproductattributevalues.length()>0)
			productattributevalues = productattributevaluerepository.findByIDs(productattributevalue_IDS);
		
		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattributevalue/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productattributevalue_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductattributevalues = jsonObj.getJSONArray("productattributevalues");
		for (int i=0; i<jsonproductattributevalues.length(); i++) {
			productattributevalue_IDS.add((Integer) jsonproductattributevalues.get(i));
		}
		List<ProductAttributeValue> productattributevalues = new ArrayList<ProductAttributeValue>();
		if (jsonproductattributevalues.length()>0)
			productattributevalues = productattributevaluerepository.findByNotInIDs(productattributevalue_IDS);
		
		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattributevalue", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/productattributevalue/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productattributevalue", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductAttributeValues, JSONObject jsonProductAttributeValue, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductAttributeValue> productattributevalues = new ArrayList<ProductAttributeValue>();
		if (jsonProductAttributeValue != null) {
			jsonProductAttributeValues = new JSONArray();
			jsonProductAttributeValues.put(jsonProductAttributeValue);
		}
		log.info(jsonProductAttributeValues.toString());
		
		for (int a=0; a<jsonProductAttributeValues.length(); a++) {
			JSONObject jsonObj = jsonProductAttributeValues.getJSONObject(a);
			ProductAttributeValue productattributevalue = new ProductAttributeValue();
			long productattributevalueid = 0;

			if (jsonObj.has("productattributevalue_ID")) {
				productattributevalueid = jsonObj.getLong("productattributevalue_ID");
				if (productattributevalueid != 0) {
					productattributevalue = productattributevaluerepository.findOne(productattributevalueid);
					
					if (productattributevalue == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductAttributeValue Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (productattributevalueid == 0) {
				if (!jsonObj.has("productattribute_ID") || jsonObj.isNull("productattribute_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productattribute_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			}
			if (jsonObj.has("productattribute_ID") && !jsonObj.isNull("productattribute_ID"))
			    productattributevalue.setPRODUCTATTRIBUTE_ID(jsonObj.getLong("productattribute_ID"));
		
		    if (jsonObj.has("productattribute_VALUE") && !jsonObj.isNull("productattribute_VALUE"))
			    productattributevalue.setPRODUCTATTRIBUTE_VALUE(jsonObj.getString("productattribute_VALUE"));
		
		    if (jsonObj.has("productattributevalueparent_ID") && !jsonObj.isNull("productattributevalueparent_ID"))
			    productattributevalue.setPRODUCTATTRIBUTEVALUEPARENT_ID(jsonObj.getLong("productattributevalueparent_ID"));
			
		    if (productattributevalueid == 0)
				productattributevalue.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				productattributevalue.setISACTIVE(jsonObj.getString("isactive"));

			productattributevalue.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productattributevalue.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productattributevalue.setMODIFIED_WHEN(dateFormat1.format(date));
			productattributevalues.add(productattributevalue);
		}
		
		for (int a=0; a<productattributevalues.size(); a++) {
			ProductAttributeValue productattributevalue = productattributevalues.get(a);
			productattributevalue = productattributevaluerepository.saveAndFlush(productattributevalue);
			productattributevalues.get(a).setPRODUCTATTRIBUTEVALUE_ID(productattributevalue.getPRODUCTATTRIBUTEVALUE_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonProductAttributeValue != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productattributevalues.get(0) , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattributevalue/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductAttributeValue productattributevalue = productattributevaluerepository.findOne(id);
		productattributevaluerepository.delete(productattributevalue);
		
		return new ResponseEntity(getAPIResponse(null, productattributevalue , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattributevalue/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject productattributevalue = new JSONObject();
		productattributevalue.put("id", id);
		productattributevalue.put("isactive", "N");
		
		return insertupdateAll(null, productattributevalue, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productattributevalue/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductAttributeValue> productattributevalues = ((active == true)
				? productattributevaluerepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: productattributevaluerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productattributevalue/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		List<ProductAttributeValue> productattributevalues= new ArrayList<ProductAttributeValue>();
		JSONObject jsonObj = new JSONObject(data);
		long productattribute_ID = 0, productattributevalueparent_ID = 0;

		if (jsonObj.has("productattribute_ID") && !jsonObj.isNull("productattribute_ID"))
			productattribute_ID = jsonObj.getLong("productattribute_ID");
		if (jsonObj.has("productcategory_ID") && !jsonObj.isNull("productcategory_ID") && productattribute_ID == 0 || jsonObj.has("attributecategory_ID") && !jsonObj.isNull("attributecategory_ID") && productattribute_ID == 0 
				|| jsonObj.has("attribute_ID") && !jsonObj.isNull("attribute_ID") && productattribute_ID == 0 
				|| jsonObj.has("product_ID") && !jsonObj.isNull("product_ID") && productattribute_ID == 0) {
            JSONArray productattributevaluesObject;
            if (active == true) {
            	productattributevaluesObject = new JSONArray(ProductService.POST("product/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken));
            } else {
            	productattributevaluesObject = new JSONArray(ProductService.POST("product/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken));
            }
            for (int i=0; i<productattributevaluesObject.length(); i++) {
                List<ProductAttributeValue> productattributevalue = new ArrayList<ProductAttributeValue>();
                productattributevalue = ((active == true)
                        ? productattributevaluerepository.findByAdvancedSearch( productattributevaluesObject.getJSONObject(i).getLong("productattribute_ID"), (long) 0)
                        : productattributevaluerepository.findAllByAdvancedSearch(productattributevaluesObject.getJSONObject(i).getLong("productattribute_ID"), (long) 0));
                for (int j=0; j<productattributevalue.size(); j++) {
                	productattributevalues.add(productattributevalue.get(j));
                }
            }
		}
		
		if (jsonObj.has("productattributevalueparent_ID") && !jsonObj.isNull("productattributevalueparent_ID"))
			productattributevalueparent_ID = jsonObj.getLong("productattributevalueparent_ID");
		
		if(productattribute_ID != 0 || productattributevalueparent_ID != 0){
			List<ProductAttributeValue> productattributevalue = ((active == true)
				? productattributevaluerepository.findByAdvancedSearch(productattribute_ID,productattributevalueparent_ID)
				: productattributevaluerepository.findAllByAdvancedSearch(productattribute_ID,productattributevalueparent_ID));
		
		 for (int i=0; i<productattributevalue.size(); i++) {
	            boolean found = false;
	            
	            for (int j=0; j<productattributevalues.size(); j++) {
	                if (productattributevalue.get(i).getPRODUCTATTRIBUTEVALUE_ID() == productattributevalues.get(j).getPRODUCTATTRIBUTEVALUE_ID()) {
	                    found = true;
	                    break;
	                }
	            }
	            
	            if (found == false) {
	            	productattributevalues.add(productattributevalue.get(i));
	            }
	        }
		}
		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductAttributeValue.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductAttributeValue> productattributevalues, ProductAttributeValue productattributevalue , JSONArray jsonProductAttributeValues, JSONObject jsonProductAttributeValue, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productattributevalueID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductAttributeValue", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productattributevalue != null) {
				JSONObject productattribute = new JSONObject(ProductService.GET("productattribute/"+productattributevalue.getPRODUCTATTRIBUTE_ID(), apiRequest.getREQUEST_OUTPUT()));
				productattributevalue.setPRODUCTATTRIBUTE_DETAIL(productattribute.toString());
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productattributevalue));
				productattributevalueID = productattributevalue.getPRODUCTATTRIBUTEVALUE_ID();
			} else if(productattributevalues != null){
				if (productattributevalues.size()>0) {
					List<Integer> productattributeList = new ArrayList<Integer>();
					for (int i=0; i<productattributevalues.size(); i++) {
						productattributeList.add(Integer.parseInt(productattributevalues.get(i).getPRODUCTATTRIBUTE_ID().toString()));
					}
					JSONArray productattributeObject = new JSONArray(ProductService.POST("productattribute/ids", "{productattributes: "+productattributeList+"}", apiRequest.getREQUEST_OUTPUT()));
					
					for (int i=0; i<productattributevalues.size(); i++) {
						for (int j=0; j<productattributeObject.length(); j++) {
							JSONObject productattribute = productattributeObject.getJSONObject(j);
							if(productattributevalues.get(i).getPRODUCTATTRIBUTE_ID() == productattribute.getLong("productattribute_ID") ) {
								productattributevalues.get(i).setPRODUCTATTRIBUTE_DETAIL(productattribute.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productattributevalues));
			}else if (jsonProductAttributeValues != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductAttributeValues.toString());
			
			} else if (jsonProductAttributeValue != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductAttributeValue.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productattributevalueID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}

}