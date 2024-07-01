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
import com.cwiztech.product.model.AttributeValue;
import com.cwiztech.product.repository.attributeValueRepository;
import com.cwiztech.services.ServiceCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/attributevalue")

public class attributeValueController {
	
	private static final Logger log = LoggerFactory.getLogger(attributeValueController.class);

	@Autowired
	private attributeValueRepository attributevaluerepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/attributevalue", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<AttributeValue> attributevalues = attributevaluerepository.findActive();
		return new ResponseEntity(getAPIResponse(attributevalues, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/attributevalue/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<AttributeValue> attributevalues = attributevaluerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(attributevalues, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/attributevalue/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		AttributeValue attributevalue = attributevaluerepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, attributevalue, null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/attributevalue/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> attributevalue_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonattributevalues = jsonObj.getJSONArray("attributevalues");
		for (int i=0; i<jsonattributevalues.length(); i++) {
			attributevalue_IDS.add((Integer) jsonattributevalues.get(i));
		}
		List<AttributeValue> attributevalues = new ArrayList<AttributeValue>();
		if (jsonattributevalues.length()>0)
			attributevalues = attributevaluerepository.findByIDs(attributevalue_IDS);
		
		return new ResponseEntity(getAPIResponse(attributevalues, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/attributevalue/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> attributevalue_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonattributevalues = jsonObj.getJSONArray("attributevalues");
		for (int i=0; i<jsonattributevalues.length(); i++) {
			attributevalue_IDS.add((Integer) jsonattributevalues.get(i));
		}
		List<AttributeValue> attributevalues = new ArrayList<AttributeValue>();
		if (jsonattributevalues.length()>0)
			attributevalues = attributevaluerepository.findByNotInIDs(attributevalue_IDS);
		
		return new ResponseEntity(getAPIResponse(attributevalues, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/attributevalue", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/attributevalue/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/attributevalue", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonAttributeValues, JSONObject jsonAttributeValue, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<AttributeValue> attributevalues = new ArrayList<AttributeValue>();
		if (jsonAttributeValue != null) {
			jsonAttributeValues = new JSONArray();
			jsonAttributeValues.put(jsonAttributeValue);
		}
		log.info(jsonAttributeValues.toString());
		
		for (int a=0; a<jsonAttributeValues.length(); a++) {
			JSONObject jsonObj = jsonAttributeValues.getJSONObject(a);
			AttributeValue attributevalue = new AttributeValue();
			long attributevalueid = 0;

			if (jsonObj.has("attributevalue_ID")) {
				attributevalueid = jsonObj.getLong("attributevalue_ID");
				if (attributevalueid != 0) {
					attributevalue = attributevaluerepository.findOne(attributevalueid);
					
					if (attributevalue == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid AttributeValue Data!", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (attributevalueid == 0) {
				if (!jsonObj.has("attribute_ID") || jsonObj.isNull("attribute_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attribute_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			}
			if (jsonObj.has("attribute_ID") && !jsonObj.isNull("attribute_ID"))
			    attributevalue.setATTRIBUTE_ID(jsonObj.getLong("attribute_ID"));
		
		    if (jsonObj.has("attribute_VALUE") && !jsonObj.isNull("attribute_VALUE"))
			    attributevalue.setATTRIBUTE_VALUE(jsonObj.getString("attribute_VALUE"));
		
		    if (jsonObj.has("attributevalueparent_ID") && !jsonObj.isNull("attributevalueparent_ID"))
			    attributevalue.setATTRIBUTEVALUEPARENT_ID(jsonObj.getLong("attributevalueparent_ID"));
			
		    if (attributevalueid == 0)
				attributevalue.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				attributevalue.setISACTIVE(jsonObj.getString("isactive"));

			attributevalue.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			attributevalue.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			attributevalue.setMODIFIED_WHEN(dateFormat1.format(date));
			attributevalues.add(attributevalue);
		}
		
		for (int a=0; a<attributevalues.size(); a++) {
			AttributeValue attributevalue = attributevalues.get(a);
			attributevalue = attributevaluerepository.saveAndFlush(attributevalue);
			attributevalues.get(a).setATTRIBUTEVALUE_ID(attributevalue.getATTRIBUTEVALUE_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonAttributeValue != null)
			responseentity = new ResponseEntity(getAPIResponse(null, attributevalues.get(0) , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(attributevalues, null , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/attributevalue/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		AttributeValue attributevalue = attributevaluerepository.findOne(id);
		attributevaluerepository.delete(attributevalue);
		
		return new ResponseEntity(getAPIResponse(null, attributevalue , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/attributevalue/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject attributevalue = new JSONObject();
		attributevalue.put("id", id);
		attributevalue.put("isactive", "N");
		
		return insertupdateAll(null, attributevalue, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/attributevalue/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<AttributeValue> attributevalues = ((active == true)
				? attributevaluerepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: attributevaluerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(attributevalues, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/attributevalue/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		List<AttributeValue> attributevalues= new ArrayList<AttributeValue>();
		JSONObject jsonObj = new JSONObject(data);
		long attribute_ID = 0, attributevalueparent_ID = 0;
		
		   JSONArray searchObject = new JSONArray();
	        List<Integer> attribute_IDS = new ArrayList<Integer>(); 
	        List<Integer> attributevalueparent_IDS = new ArrayList<Integer>(); 

	        attribute_IDS.add((int) 0);
	        attributevalueparent_IDS.add((int) 0);

	        		
       boolean isWithDetail = true;
       if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
           isWithDetail = jsonObj.getBoolean("iswithdetail");
       }
       jsonObj.put("iswithdetail", false);
		
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
		
		if (jsonObj.has("attributevalueparent_ID") && !jsonObj.isNull("attributevalueparent_ID"))
			attributevalueparent_ID = jsonObj.getLong("attributevalueparent_ID");
		
		if(attribute_ID != 0 || attributevalueparent_ID != 0){
			List<AttributeValue> attributevalue = ((active == true)
				? attributevaluerepository.findByAdvancedSearch(attribute_ID,attribute_IDS, attributevalueparent_ID, attributevalueparent_IDS)
				: attributevaluerepository.findAllByAdvancedSearch(attribute_ID,attribute_IDS, attributevalueparent_ID, attributevalueparent_IDS));
		
		 for (int i=0; i<attributevalue.size(); i++) {
	            boolean found = false;
	            
	            for (int j=0; j<attributevalues.size(); j++) {
	                if (attributevalue.get(i).getATTRIBUTEVALUE_ID() == attributevalues.get(j).getATTRIBUTEVALUE_ID()) {
	                    found = true;
	                    break;
	                }
	            }
	            
	            if (found == false) {
	            	attributevalues.add(attributevalue.get(i));
	            }
	        }
		}
		return new ResponseEntity(getAPIResponse(attributevalues, null , null, null, null, apiRequest, false, isWithDetail).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(AttributeValue.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<AttributeValue> attributevalues, AttributeValue attributevalue , JSONArray jsonAttributeValues, JSONObject jsonAttributeValue, String message, APIRequestDataLog apiRequest, boolean isTableLog, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long attributevalueID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "AttributeValue", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (attributevalue != null && isWithDetail == true) {
				JSONObject attribute = new JSONObject(ServiceCall.GET("attribute/"+attributevalue.getATTRIBUTE_ID(), apiRequest.getREQUEST_OUTPUT(), false));
				attributevalue.setATTRIBUTE_DETAIL(attribute.toString());
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(attributevalue));
				attributevalueID = attributevalue.getATTRIBUTEVALUE_ID();
			} else if(attributevalues != null && isWithDetail == true){
				if (attributevalues.size()>0) {
					List<Integer> attributeList = new ArrayList<Integer>();
					for (int i=0; i<attributevalues.size(); i++) {
						attributeList.add(Integer.parseInt(attributevalues.get(i).getATTRIBUTE_ID().toString()));
					}
					JSONArray attributeObject = new JSONArray(ServiceCall.POST("attribute/ids", "{attributes: "+attributeList+"}", apiRequest.getREQUEST_OUTPUT(), false));
					
					for (int i=0; i<attributevalues.size(); i++) {
						for (int j=0; j<attributeObject.length(); j++) {
							JSONObject attribute = attributeObject.getJSONObject(j);
							if(attributevalues.get(i).getATTRIBUTE_ID() == attribute.getLong("attribute_ID") ) {
								attributevalues.get(i).setATTRIBUTE_DETAIL(attribute.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(attributevalues));
			}else if (jsonAttributeValues != null){
				apiRequest.setREQUEST_OUTPUT(jsonAttributeValues.toString());
			
			} else if (jsonAttributeValue != null){
				apiRequest.setREQUEST_OUTPUT(jsonAttributeValue.toString());
			
		} else if (jsonAttributeValues != null){
			apiRequest.setREQUEST_OUTPUT(jsonAttributeValues.toString());
		} else if (jsonAttributeValue != null){
			apiRequest.setREQUEST_OUTPUT(jsonAttributeValue.toString());
		}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(attributevalueID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}

}