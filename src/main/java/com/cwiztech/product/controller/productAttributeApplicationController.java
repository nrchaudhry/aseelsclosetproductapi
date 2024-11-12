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

import com.cwiztech.product.model.ProductAttributeApplication;
import com.cwiztech.product.repository.productAttributeApplicationRepository;
import com.cwiztech.services.ServiceCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productattributeapplication")

public class productAttributeApplicationController {
	
	private static final Logger log = LoggerFactory.getLogger(productAttributeApplicationController.class);

	@Autowired
	private productAttributeApplicationRepository productattributeapplicationrepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattributeapplication", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductAttributeApplication> productattributeapplications = productattributeapplicationrepository.findActive();
		return new ResponseEntity(getAPIResponse(productattributeapplications, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattributeapplication/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductAttributeApplication> productattributeapplications = productattributeapplicationrepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productattributeapplications, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattributeapplication/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductAttributeApplication productattributeapplication = productattributeapplicationrepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productattributeapplication , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattributeapplication/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productattributeapplication_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductattributeapplications = jsonObj.getJSONArray("productattributeapplications");
		for (int i=0; i<jsonproductattributeapplications.length(); i++) {
			productattributeapplication_IDS.add((Integer) jsonproductattributeapplications.get(i));
		}
		List<ProductAttributeApplication> productattributeapplications = new ArrayList<ProductAttributeApplication>();
		if (jsonproductattributeapplications.length()>0)
			productattributeapplications = productattributeapplicationrepository.findByIDs(productattributeapplication_IDS);
		
		return new ResponseEntity(getAPIResponse(productattributeapplications, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattributeapplication/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productattributeapplication_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductattributeapplications = jsonObj.getJSONArray("productattributeapplications");
		for (int i=0; i<jsonproductattributeapplications.length(); i++) {
			productattributeapplication_IDS.add((Integer) jsonproductattributeapplications.get(i));
		}
		List<ProductAttributeApplication> productattributeapplications = new ArrayList<ProductAttributeApplication>();
		if (jsonproductattributeapplications.length()>0)
			productattributeapplications = productattributeapplicationrepository.findByNotInIDs(productattributeapplication_IDS);
		
		return new ResponseEntity(getAPIResponse(productattributeapplications, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattributeapplication", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productattributeapplication/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productattributeapplication", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

			
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductAttributeApplications, JSONObject jsonProductAttributeApplication, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductAttributeApplication> productattributeapplications = new ArrayList<ProductAttributeApplication>();
		if (jsonProductAttributeApplication != null) {
			jsonProductAttributeApplications = new JSONArray();
			jsonProductAttributeApplications.put(jsonProductAttributeApplication);
		}
		log.info(jsonProductAttributeApplications.toString());
		
		for (int a=0; a<jsonProductAttributeApplications.length(); a++) {
			JSONObject jsonObj = jsonProductAttributeApplications.getJSONObject(a);
			ProductAttributeApplication productattributeapplication = new ProductAttributeApplication();
			long productattributeapplicationid = 0;

			if (jsonObj.has("productattributeapplication_ID")) {
				productattributeapplicationid = jsonObj.getLong("productattributeapplication_ID");
				if (productattributeapplicationid != 0) {
					productattributeapplication = productattributeapplicationrepository.findOne(productattributeapplicationid);
					
					if (productattributeapplication == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductAttributeApplication Data!", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}

				
			if (productattributeapplicationid == 0) {
				if (!jsonObj.has("application_ID") || jsonObj.isNull("application_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "application_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productattribute_ID") || jsonObj.isNull("productattribute_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productattribute_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
			}
			
			if (jsonObj.has("application_ID") && !jsonObj.isNull("application_ID"))
			    productattributeapplication.setAPPLICATION_ID(jsonObj.getLong("application_ID"));
		
		    if (jsonObj.has("productattribute_ID") && !jsonObj.isNull("productattribute_ID"))
			    productattributeapplication.setPRODUCTATTRIBUTE_ID(jsonObj.getLong("productattribute_ID"));
		
		    if (productattributeapplicationid == 0)
				productattributeapplication.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				productattributeapplication.setISACTIVE(jsonObj.getString("isactive"));

			productattributeapplication.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productattributeapplication.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productattributeapplication.setMODIFIED_WHEN(dateFormat1.format(date));
			productattributeapplications.add(productattributeapplication);
		}
		
		for (int a=0; a<productattributeapplications.size(); a++) {
			ProductAttributeApplication productattributeapplication = productattributeapplications.get(a);
			productattributeapplication = productattributeapplicationrepository.saveAndFlush(productattributeapplication);
			productattributeapplications.get(a).setPRODUCTATTRIBUTEAPPLICATION_ID(productattributeapplication.getPRODUCTATTRIBUTEAPPLICATION_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonProductAttributeApplication != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productattributeapplications.get(0) , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productattributeapplications, null , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattributeapplication/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductAttributeApplication productattributeapplication = productattributeapplicationrepository.findOne(id);
		productattributeapplicationrepository.delete(productattributeapplication);
		
		return new ResponseEntity(getAPIResponse(null, productattributeapplication , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productattributeapplication/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject productattributeapplication = new JSONObject();
		productattributeapplication.put("id", id);
		productattributeapplication.put("isactive", "N");
		
		return insertupdateAll(null, productattributeapplication, apiRequest);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/advancedsearch", method = RequestMethod.POST)
	public ResponseEntity getByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		return ByAdvancedSearch(data, true, headToken, LimitGrant);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/advancedsearch/all", method = RequestMethod.POST)
	public ResponseEntity getAllByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		return ByAdvancedSearch(data, false, headToken, LimitGrant);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity ByAdvancedSearch(String data, boolean active, String headToken, String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productattributeapplication/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONArray applicationObject = new JSONArray();
		JSONObject jsonObj = new JSONObject(data);
		
		   JSONArray searchObject = new JSONArray();
	        List<Integer> application_IDS = new ArrayList<Integer>(); 
	        List<Integer> productattribute_IDS = new ArrayList<Integer>(); 

	        application_IDS.add((int) 0);
	        productattribute_IDS.add((int) 0);
	        
		long application_ID = 0 , productattribute_ID = 0;
		
     boolean isWithDetail = true;
     if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
         isWithDetail = jsonObj.getBoolean("iswithdetail");
     }
     jsonObj.put("iswithdetail", false);
		
     if (jsonObj.has("application_ID") && !jsonObj.isNull("application_ID") && jsonObj.getLong("application_ID") != 0) {
         application_ID = jsonObj.getLong("application_ID");
         application_IDS.add((int) application_ID);
     } else if (jsonObj.has("application") && !jsonObj.isNull("application") && jsonObj.getLong("application") != 0) {
         if (active == true) {
             searchObject = new JSONArray(ServiceCall.POST("application/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
         } else {
             searchObject = new JSONArray(ServiceCall.POST("application/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
         }

         application_ID = searchObject.length();
         for (int i=0; i<searchObject.length(); i++) {
             application_IDS.add((int) searchObject.getJSONObject(i).getLong("application_ID"));
         }
     }
		
     if (jsonObj.has("productattribute_ID") && !jsonObj.isNull("productattribute_ID") && jsonObj.getLong("productattribute_ID") != 0) {
         productattribute_ID = jsonObj.getLong("productattribute_ID");
         productattribute_IDS.add((int) productattribute_ID);
     } else if (jsonObj.has("productattribute") && !jsonObj.isNull("productattribute") && jsonObj.getLong("productattribute") != 0) {
         if (active == true) {
             searchObject = new JSONArray(ServiceCall.POST("productattribute/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
         } else {
             searchObject = new JSONArray(ServiceCall.POST("productattribute/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
         }

         productattribute_ID = searchObject.length();
         for (int i=0; i<searchObject.length(); i++) {
             productattribute_IDS.add((int) searchObject.getJSONObject(i).getLong("productattribute_ID"));
         }
     }
	
		if (jsonObj.has("productattribute_ID") && !jsonObj.isNull("productattribute_ID"))
			productattribute_ID = jsonObj.getLong("productattribute_ID");
		
		List<ProductAttributeApplication> productattributeapplications = new ArrayList<ProductAttributeApplication>();
		for (int i=0; i<applicationObject.length(); i++) {
			JSONObject objapplication = applicationObject.getJSONObject(i);
			application_ID = objapplication.getLong("application_ID");
			
			List<ProductAttributeApplication> productattributeapplication = ((active == true)
					? productattributeapplicationrepository.findByAdvancedSearch(application_ID,application_IDS,productattribute_ID,productattribute_IDS)
					: productattributeapplicationrepository.findAllByAdvancedSearch(application_ID,application_IDS,productattribute_ID,productattribute_IDS));
			for (int k=0; k<productattributeapplication.size(); k++) {
				boolean found = false;
				
				for (int j=0; j<productattributeapplications.size(); j++) {
					if (productattributeapplication.get(k).getAPPLICATION_ID() == productattributeapplications.get(j).getAPPLICATION_ID()) {
						found = true;
						break;
					}
				}
				
				if (found == false) {
					productattributeapplications.add(productattributeapplication.get(k));
				}
			}		
		}
				
		return new ResponseEntity(getAPIResponse(productattributeapplications, null , null, null, null, apiRequest, false, isWithDetail).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	
	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductAttributeApplication.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductAttributeApplication> productattributeapplications, ProductAttributeApplication productattributeapplication , JSONArray jsonProductAttributeApplications, JSONObject jsonProductAttributeApplication, String message, APIRequestDataLog apiRequest, boolean isTableLog,boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productattributeapplicationID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductAttributeApplication", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productattributeapplication != null && isWithDetail == true) {
				JSONObject application = new JSONObject(ServiceCall.GET("application/"+productattributeapplication.getAPPLICATION_ID(), apiRequest.getREQUEST_OUTPUT(), false));
				productattributeapplication.setAPPLICATION_DETAIL(application.toString());
				
				JSONObject productattribute = new JSONObject(ServiceCall.GET("productattribute/"+productattributeapplication.getPRODUCTATTRIBUTE_ID(), apiRequest.getREQUEST_OUTPUT(), false));
				productattributeapplication.setPRODUCTATTRIBUTE_DETAIL(productattribute.toString());
				
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productattributeapplication));
				productattributeapplicationID = productattributeapplication.getPRODUCTATTRIBUTEAPPLICATION_ID();
			} else if(productattributeapplications != null && isWithDetail == true){
				if (productattributeapplications.size()>0) {
					List<Integer> applicationList = new ArrayList<Integer>();
					List<Integer> productattributeList = new ArrayList<Integer>();
					for (int i=0; i<productattributeapplications.size(); i++) {
						if(productattributeapplications.get(i).getAPPLICATION_ID() != null)
						applicationList.add(Integer.parseInt(productattributeapplications.get(i).getAPPLICATION_ID().toString()));
						if(productattributeapplications.get(i).getPRODUCTATTRIBUTE_ID() != null)
						productattributeList.add(Integer.parseInt(productattributeapplications.get(i).getPRODUCTATTRIBUTE_ID().toString()));
					}
					JSONArray logisticsObject = new JSONArray(ServiceCall.POST("application/ids", "{applications: "+applicationList+"}", apiRequest.getREQUEST_OUTPUT(), false));
					JSONArray productattributeObject = new JSONArray(ServiceCall.POST("productattribute/ids", "{productattributes: "+productattributeList+"}", apiRequest.getREQUEST_OUTPUT(), false));
					for (int i=0; i<productattributeapplications.size(); i++) {
						for (int j=0; j<logisticsObject.length(); j++) {
							JSONObject application = logisticsObject.getJSONObject(j);
							if(productattributeapplications.get(i).getAPPLICATION_ID() == application.getLong("application_ID") ) {
								productattributeapplications.get(i).setAPPLICATION_DETAIL(application.toString());
							}
						}		
						for (int j=0; j<productattributeObject.length(); j++) {
							JSONObject productattribute = productattributeObject.getJSONObject(j);
							if(productattributeapplications.get(i).getPRODUCTATTRIBUTE_ID() == productattribute.getLong("productattribute_ID") ) {
								productattributeapplications.get(i).setPRODUCTATTRIBUTE_DETAIL(productattribute.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productattributeapplications));
			}else if (jsonProductAttributeApplications != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductAttributeApplications.toString());
			
			} else if (jsonProductAttributeApplication != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductAttributeApplication.toString());
			
		} else if (jsonProductAttributeApplications != null){
			apiRequest.setREQUEST_OUTPUT(jsonProductAttributeApplications.toString());
		} else if (jsonProductAttributeApplication != null){
			apiRequest.setREQUEST_OUTPUT(jsonProductAttributeApplication.toString());
		}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productattributeapplicationID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		return apiRequest;
	}

}