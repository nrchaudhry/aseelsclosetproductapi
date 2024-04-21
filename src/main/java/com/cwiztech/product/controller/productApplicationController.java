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
import com.cwiztech.product.model.Product;
import com.cwiztech.product.model.ProductApplication;
import com.cwiztech.product.repository.productApplicationRepository;
import com.cwiztech.services.ProductService;
import com.cwiztech.services.UserLoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productapplication")

public class productApplicationController {
	
	private static final Logger log = LoggerFactory.getLogger(productApplicationController.class);

	@Autowired
	private productApplicationRepository productapplicationrepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productapplication", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductApplication> productapplications = productapplicationrepository.findActive();
		return new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productapplication/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductApplication> productapplications = productapplicationrepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productapplication/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductApplication productapplication = productapplicationrepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productapplication , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productapplication/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productapplication_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductapplications = jsonObj.getJSONArray("productapplications");
		for (int i=0; i<jsonproductapplications.length(); i++) {
			productapplication_IDS.add((Integer) jsonproductapplications.get(i));
		}
		List<ProductApplication> productapplications = new ArrayList<ProductApplication>();
		if (jsonproductapplications.length()>0)
			productapplications = productapplicationrepository.findByIDs(productapplication_IDS);
		
		return new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productapplication/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productapplication_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductapplications = jsonObj.getJSONArray("productapplications");
		for (int i=0; i<jsonproductapplications.length(); i++) {
			productapplication_IDS.add((Integer) jsonproductapplications.get(i));
		}
		List<ProductApplication> productapplications = new ArrayList<ProductApplication>();
		if (jsonproductapplications.length()>0)
			productapplications = productapplicationrepository.findByNotInIDs(productapplication_IDS);
		
		return new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productapplication", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productapplication/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productapplication", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

			
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductApplications, JSONObject jsonProductApplication, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductApplication> productapplications = new ArrayList<ProductApplication>();
		if (jsonProductApplication != null) {
			jsonProductApplications = new JSONArray();
			jsonProductApplications.put(jsonProductApplication);
		}
		log.info(jsonProductApplications.toString());
		
		for (int a=0; a<jsonProductApplications.length(); a++) {
			JSONObject jsonObj = jsonProductApplications.getJSONObject(a);
			ProductApplication productapplication = new ProductApplication();
			long productapplicationid = 0;

			if (jsonObj.has("productapplication_ID")) {
				productapplicationid = jsonObj.getLong("productapplication_ID");
				if (productapplicationid != 0) {
					productapplication = productapplicationrepository.findOne(productapplicationid);
					
					if (productapplication == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductApplication Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}

				
			if (productapplicationid == 0) {
				if (!jsonObj.has("application_ID") || jsonObj.isNull("application_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "application_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
			}
			if (jsonObj.has("application_ID") && !jsonObj.isNull("application_ID"))
			    productapplication.setAPPLICATION_ID(jsonObj.getLong("application_ID"));
		
		    if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID"))
			    productapplication.setPRODUCT_ID(jsonObj.getLong("product_ID"));
			
		    if (productapplicationid == 0)
				productapplication.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				productapplication.setISACTIVE(jsonObj.getString("isactive"));

			productapplication.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productapplication.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productapplication.setMODIFIED_WHEN(dateFormat1.format(date));
			productapplications.add(productapplication);
		}
		
		for (int a=0; a<productapplications.size(); a++) {
			ProductApplication productapplication = productapplications.get(a);
			productapplication = productapplicationrepository.saveAndFlush(productapplication);
			productapplications.get(a).setPRODUCTAPPLICATION_ID(productapplication.getPRODUCTAPPLICATION_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonProductApplication != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productapplications.get(0) , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productapplication/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductApplication productapplication = productapplicationrepository.findOne(id);
		productapplicationrepository.delete(productapplication);
		
		return new ResponseEntity(getAPIResponse(null, productapplication , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productapplication/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject productapplication = new JSONObject();
		productapplication.put("id", id);
		productapplication.put("isactive", "N");
		
		return insertupdateAll(null, productapplication, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productapplication/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);
		long application_ID = 0;
		long product_ID = 0;
		List<ProductApplication> productapplications = new ArrayList<ProductApplication>();

		if (jsonObj.has("application_ID") && !jsonObj.isNull("application_ID"))
			application_ID = jsonObj.getLong("application_ID");
		
		
		if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID"))
			product_ID = jsonObj.getLong("product_ID");
		if (jsonObj.has("productcategory_ID") && !jsonObj.isNull("productcategory_ID") && product_ID == 0) {
            JSONArray productsObject;
            if (active == true) {
            	productsObject = new JSONArray(ProductService.POST("product/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken));
            } else {
            	productsObject = new JSONArray(ProductService.POST("product/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken));
            }
            for (int i=0; i<productsObject.length(); i++) {
                List<ProductApplication> productapplication = new ArrayList<ProductApplication>();
                productapplication = ((active == true)
                        ? productapplicationrepository.findByAdvancedSearch((long) 0,  productsObject.getJSONObject(i).getLong("product_ID"))
                        : productapplicationrepository.findAllByAdvancedSearch((long) 0, productsObject.getJSONObject(i).getLong("product_ID")));
                for (int j=0; j<productapplication.size(); j++) {
                    productapplications.add(productapplication.get(j));
                }
            }
		}
		
		if(application_ID !=0 || product_ID !=0){
			List<ProductApplication> productapplication = ((active == true)
				? productapplicationrepository.findByAdvancedSearch(application_ID, product_ID)
				: productapplicationrepository.findAllByAdvancedSearch(application_ID, product_ID));
	    for (int i=0; i<productapplication.size(); i++) {
            boolean found = false;
            
            for (int j=0; j<productapplications.size(); j++) {
                if (productapplication.get(i).getPRODUCTAPPLICATION_ID() == productapplications.get(j).getPRODUCTAPPLICATION_ID()) {
                    found = true;
                    break;
                }
            }
            
            if (found == false) {
                productapplications.add(productapplication.get(i));
            }
        }
	}
	
		return new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
}
	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductApplication.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductApplication> productapplications, ProductApplication productapplication , JSONArray jsonProductApplications, JSONObject jsonProductApplication, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productapplicationID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductApplication", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productapplication != null) {
				JSONObject application = new JSONObject(UserLoginService.GET("application/"+productapplication.getAPPLICATION_ID(), apiRequest.getREQUEST_OUTPUT()));
				productapplication.setAPPLICATION_DETAIL(application.toString());
				JSONObject product = new JSONObject(ProductService.GET("product/"+productapplication.getPRODUCT_ID(), apiRequest.getREQUEST_OUTPUT()));
				productapplication.setPRODUCT_DETAIL(product.toString());
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productapplication));
				productapplicationID = productapplication.getPRODUCTAPPLICATION_ID();
			} else if(productapplications != null){
				if (productapplications.size()>0) {
					List<Integer> applicationList = new ArrayList<Integer>();
					List<Integer> productList = new ArrayList<Integer>();
					for (int i=0; i<productapplications.size(); i++) {
						if(productapplications.get(i).getAPPLICATION_ID() != null)
						applicationList.add(Integer.parseInt(productapplications.get(i).getAPPLICATION_ID().toString()));
						if(productapplications.get(i).getPRODUCT_ID() != null)
						productList.add(Integer.parseInt(productapplications.get(i).getPRODUCT_ID().toString()));

					}
					
					JSONArray logisticsObject = new JSONArray(UserLoginService.POST("application/ids", "{applications: "+applicationList+"}", apiRequest.getREQUEST_OUTPUT()));
					JSONArray productObject = new JSONArray(ProductService.POST("product/ids", "{products: "+productList+"}", apiRequest.getREQUEST_OUTPUT()));
					
					for (int i=0; i<productapplications.size(); i++) {
						for (int j=0; j<logisticsObject.length(); j++) {
							JSONObject application = logisticsObject.getJSONObject(j);
							if(productapplications.get(i).getAPPLICATION_ID() == application.getLong("application_ID") ) {
								productapplications.get(i).setAPPLICATION_DETAIL(application.toString());
							}
						}	
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if(productapplications.get(i).getPRODUCT_ID() == product.getLong("product_ID") ) {
								productapplications.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productapplications));
			}else if (jsonProductApplications != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductApplications.toString());
			
			} else if (jsonProductApplication != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductApplication.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productapplicationID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}
}