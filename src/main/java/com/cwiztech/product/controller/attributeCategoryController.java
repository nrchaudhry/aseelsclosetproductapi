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


import com.cwiztech.product.model.AttributeCategory;
import com.cwiztech.product.repository.attributeCategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/attributecategory")

public class attributeCategoryController {
	
	private static final Logger log = LoggerFactory.getLogger(attributeCategoryController.class);

	@Autowired
	private attributeCategoryRepository attributecategoryrepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/attributecategory", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<AttributeCategory> attributecategories = attributecategoryrepository.findActive();
		return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/attributecategory/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<AttributeCategory> attributecategories = attributecategoryrepository.findAll();
		
		return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/attributecategory/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		AttributeCategory attributecategory = attributecategoryrepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, attributecategory , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/attributecategory/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> attributecategory_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonattributecategories = jsonObj.getJSONArray("attributecategories");
		for (int i=0; i<jsonattributecategories.length(); i++) {
			attributecategory_IDS.add((Integer) jsonattributecategories.get(i));
		}
		List<AttributeCategory> attributecategories = new ArrayList<AttributeCategory>();
		if (jsonattributecategories.length()>0)
			attributecategories = attributecategoryrepository.findByIDs(attributecategory_IDS);
		
		return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/attributecategory/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> attributecategory_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonattributecategories = jsonObj.getJSONArray("attributecategories");
		for (int i=0; i<jsonattributecategories.length(); i++) {
			attributecategory_IDS.add((Integer) jsonattributecategories.get(i));
		}
		List<AttributeCategory> attributecategories = new ArrayList<AttributeCategory>();
		if (jsonattributecategories.length()>0)
			attributecategories = attributecategoryrepository.findByNotInIDs(attributecategory_IDS);
		
		return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/attributecategory", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/attributecategory/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/attributecategory", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonAttributecategories, JSONObject jsonAttributeCategory, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<AttributeCategory> attributecategories = new ArrayList<AttributeCategory>();
		if (jsonAttributeCategory != null) {
			jsonAttributecategories = new JSONArray();
			jsonAttributecategories.put(jsonAttributeCategory);
		}
		log.info(jsonAttributecategories.toString());
		
		for (int a=0; a<jsonAttributecategories.length(); a++) {
			JSONObject jsonObj = jsonAttributecategories.getJSONObject(a);
			AttributeCategory attributecategory = new AttributeCategory();
			long attributecategoryid = 0;

			if (jsonObj.has("attributecategory_ID")) {
				attributecategoryid = jsonObj.getLong("attributecategory_ID");
				if (attributecategoryid != 0) {
					attributecategory = attributecategoryrepository.findOne(attributecategoryid);
					
					if (attributecategory == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid AttributeCategory Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (attributecategoryid == 0) {
				if (!jsonObj.has("attributecategory_NAME") || jsonObj.isNull("attributecategory_NAME"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attributecategory_NAME is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
				if (!jsonObj.has("attributecategoryorder_NO") || jsonObj.isNull("attributecategoryorder_NO"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attributecategoryorder_NO is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			}
			
			if (jsonObj.has("attributecategoryparent_ID") && !jsonObj.isNull("attributecategoryparent_ID"))
			    attributecategory.setATTRIBUTECATEGORYPARENT_ID(attributecategoryrepository.findOne(jsonObj.getLong("attributecategoryparent_ID")));
		
			if (jsonObj.has("attributecategoryorder_NO") && !jsonObj.isNull("attributecategoryorder_NO"))
				attributecategory.setATTRIBUTECATEGORYORDER_NO(jsonObj.getLong("attributecategoryorder_NO"));
			
		    if (jsonObj.has("attributecategory_NAME") && !jsonObj.isNull("attributecategory_NAME"))
			    attributecategory.setATTRIBUTECATEGORY_NAME(jsonObj.getString("attributecategory_NAME"));
		
		    if (jsonObj.has("istabs") && !jsonObj.isNull("istabs"))
			    attributecategory.setISTABS(jsonObj.getString("istabs"));
			
		    if (attributecategoryid == 0)
				attributecategory.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				attributecategory.setISACTIVE(jsonObj.getString("isactive"));

			attributecategory.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			attributecategory.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			attributecategory.setMODIFIED_WHEN(dateFormat1.format(date));
			attributecategories.add(attributecategory);
		}
		
		for (int a=0; a<attributecategories.size(); a++) {
			AttributeCategory attributecategory = attributecategories.get(a);
			attributecategory = attributecategoryrepository.saveAndFlush(attributecategory);
			attributecategories.get(a).setATTRIBUTECATEGORY_ID(attributecategory.getATTRIBUTECATEGORY_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonAttributeCategory != null)
			responseentity = new ResponseEntity(getAPIResponse(null, attributecategories.get(0) , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/attributecategory/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		AttributeCategory attributecategory = attributecategoryrepository.findOne(id);
		attributecategoryrepository.delete(attributecategory);
		
		return new ResponseEntity(getAPIResponse(null, attributecategory , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/attributecategory/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject attributecategory = new JSONObject();
		attributecategory.put("id", id);
		attributecategory.put("isactive", "N");
		
		return insertupdateAll(null, attributecategory, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/attributecategory/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<AttributeCategory> attributecategories = ((active == true)
				? attributecategoryrepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: attributecategoryrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/attributecategory/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		List<AttributeCategory> attributecategories = new ArrayList<AttributeCategory>();
		JSONObject jsonObj = new JSONObject(data);

		long category_ID = 0 ;

		if (jsonObj.has("category_ID"))
			category_ID = jsonObj.getLong("category_ID");

		if(category_ID != 0 ){
		attributecategories = ((active == true)
				? attributecategoryrepository.findByAdvancedSearch(category_ID)
				: attributecategoryrepository.findAllByAdvancedSearch(category_ID));

	}
		return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);

}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(AttributeCategory.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<AttributeCategory> attributecategories, AttributeCategory attributecategory , JSONArray jsonAttributecategories, JSONObject jsonAttributeCategory, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long attributecategoryID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "AttributeCategory", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (attributecategory != null) {
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(attributecategory));
				attributecategoryID = attributecategory.getATTRIBUTECATEGORY_ID();
			} else if(attributecategories != null){
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(attributecategories));
			}else if (jsonAttributecategories != null){
				apiRequest.setREQUEST_OUTPUT(jsonAttributecategories.toString());
			
			} else if (jsonAttributeCategory != null){
				apiRequest.setREQUEST_OUTPUT(jsonAttributeCategory.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(attributecategoryID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}

}