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
import com.cwiztech.product.model.ProductCategory;
import com.cwiztech.product.repository.productCategoryRepository;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/productcategory")

public class productCategoryController {
	
	private static final Logger log = LoggerFactory.getLogger(productCategoryController.class);

	@Autowired
	private productCategoryRepository productcategoryrepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productcategory", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductCategory> productcategories = productcategoryrepository.findActive();
		return new ResponseEntity(getAPIResponse(productcategories, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productcategory/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductCategory> productcategories = productcategoryrepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productcategories, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productcategory/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductCategory productcategory = productcategoryrepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productcategory , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productcategory/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productcategory_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductcategories = jsonObj.getJSONArray("productcategories");
		for (int i=0; i<jsonproductcategories.length(); i++) {
			productcategory_IDS.add((Integer) jsonproductcategories.get(i));
		}
		List<ProductCategory> productcategories = new ArrayList<ProductCategory>();
		if (jsonproductcategories.length()>0)
			productcategories = productcategoryrepository.findByIDs(productcategory_IDS);
		
		return new ResponseEntity(getAPIResponse(productcategories, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productcategory/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productcategory_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductcategories = jsonObj.getJSONArray("productcategories");
		for (int i=0; i<jsonproductcategories.length(); i++) {
			productcategory_IDS.add((Integer) jsonproductcategories.get(i));
		}
		List<ProductCategory> productcategories = new ArrayList<ProductCategory>();
		if (jsonproductcategories.length()>0)
			productcategories = productcategoryrepository.findByNotInIDs(productcategory_IDS);
		
		return new ResponseEntity(getAPIResponse(productcategories, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productcategory", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/productcategory/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productcategory", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductcategories, JSONObject jsonProductCategory, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductCategory> productcategories = new ArrayList<ProductCategory>();
		if (jsonProductCategory != null) {
			jsonProductcategories = new JSONArray();
			jsonProductcategories.put(jsonProductCategory);
		}
		log.info(jsonProductcategories.toString());
		
		for (int a=0; a<jsonProductcategories.length(); a++) {
			JSONObject jsonObj = jsonProductcategories.getJSONObject(a);
			ProductCategory productcategory = new ProductCategory();
			long productcategoryid = 0;

			if (jsonObj.has("productcategory_ID")) {
				productcategoryid = jsonObj.getLong("productcategory_ID");
				if (productcategoryid != 0) {
					productcategory = productcategoryrepository.findOne(productcategoryid);
					
					if (productcategory == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductCategory Data!", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (productcategoryid == 0) {
				if (!jsonObj.has("productcategory_NAME") || jsonObj.isNull("productcategory_NAME"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productcategory_NAME is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productcategoryorder_NO") || jsonObj.isNull("productcategoryorder_NO"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productcategoryorder_NO is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			}
			
			if (jsonObj.has("productcategoryparent_ID") && !jsonObj.isNull("productcategoryparent_ID"))
			productcategory.setPRODUCTCATEGORYPARENT_ID(productcategoryrepository.findOne(jsonObj.getLong("productcategoryparent_ID")));

		    if (jsonObj.has("productcategory_CODE")  && !jsonObj.isNull("productcategory_CODE"))
			productcategory.setPRODUCTCATEGORY_CODE(jsonObj.getString("productcategory_CODE"));
		
		    if (jsonObj.has("quickbook_ID")  && !jsonObj.isNull("quickbook_ID"))
			productcategory.setQUICKBOOK_ID(jsonObj.getString("quickbook_ID"));
		
		    if (jsonObj.has("productcategoryorder_NO") && !jsonObj.isNull("productcategoryorder_NO"))
			productcategory.setPRODUCTCATEGORYORDER_NO(jsonObj.getLong("productcategoryorder_NO"));

		    if (jsonObj.has("productcategory_NAME") && !jsonObj.isNull("productcategory_NAME"))
			productcategory.setPRODUCTCATEGORY_NAME(jsonObj.getString("productcategory_NAME"));
		
		    if (jsonObj.has("productcategory_DESC") && !jsonObj.isNull("productcategory_DESC"))
			productcategory.setPRODUCTCATEGORY_DESC(jsonObj.getString("productcategory_DESC"));
			
            if (jsonObj.has("productcategoryicon_URL") && !jsonObj.isNull("productcategoryicon_URL"))
                productcategory.setPRODUCTCATEGORYICON_URL(jsonObj.getString("productcategoryicon_URL"));
            
		    if (productcategoryid == 0)
				productcategory.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				productcategory.setISACTIVE(jsonObj.getString("isactive"));

			productcategory.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productcategory.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productcategory.setMODIFIED_WHEN(dateFormat1.format(date));
			productcategories.add(productcategory);
		}
		
		for (int a=0; a<productcategories.size(); a++) {
			ProductCategory productcategory = productcategories.get(a);
			productcategory = productcategoryrepository.saveAndFlush(productcategory);
			productcategories.get(a).setPRODUCTCATEGORY_ID(productcategory.getPRODUCTCATEGORY_ID());
		}
		
		List<Integer> productcategory_IDS = new ArrayList<Integer>(); 
		for (int a=0; a<productcategories.size(); a++) {
			productcategory_IDS.add((int) productcategories.get(a).getPRODUCTCATEGORY_ID());
		}
		productcategories = productcategoryrepository.findByIDs(productcategory_IDS);
		
		ResponseEntity responseentity;
		if (jsonProductCategory != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productcategories.get(0) , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productcategories, null , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productcategory/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductCategory productcategory = productcategoryrepository.findOne(id);
		productcategoryrepository.delete(productcategory);
		
		return new ResponseEntity(getAPIResponse(null, productcategory , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productcategory/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject productcategory = new JSONObject();
		productcategory.put("id", id);
		productcategory.put("isactive", "N");
		
		return insertupdateAll(null, productcategory, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productcategory/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductCategory> productcategories = ((active == true)
				? productcategoryrepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: productcategoryrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(productcategories, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productcategory/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
        List<ProductCategory> productcategories = new ArrayList<ProductCategory>();
        JSONObject jsonObj = new JSONObject(data);

        JSONArray searchObject = new JSONArray();
        List<Integer> productcategoryparent_IDS = new ArrayList<Integer>(); 

        productcategoryparent_IDS.add((int) 0);

        long productcategoryparent_ID = 0 ;

        boolean isWithDetail = true;
        if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
            isWithDetail = jsonObj.getBoolean("iswithdetail");
        }
        jsonObj.put("iswithdetail", false);

        if (jsonObj.has("productcategoryparent_ID") && !jsonObj.isNull("productcategoryparent_ID") && jsonObj.getLong("productcategoryparent_ID") != 0) {
            productcategoryparent_ID = jsonObj.getLong("productcategoryparent_ID");
            productcategoryparent_IDS.add((int) productcategoryparent_ID);
        } else if (jsonObj.has("productcategoryparent") && !jsonObj.isNull("productcategoryparent") && jsonObj.getLong("productcategoryparent") != 0) {
            if (active == true) {
                searchObject = new JSONArray(ServiceCall.POST("productcategoryparent/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
            } else {
                searchObject = new JSONArray(ServiceCall.POST("productcategoryparent/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
            }

            productcategoryparent_ID = searchObject.length();
            for (int i=0; i<searchObject.length(); i++) {
                productcategoryparent_IDS.add((int) searchObject.getJSONObject(i).getLong("productcategoryparent_ID"));
            }
        }

        if(productcategoryparent_ID != 0 ){
            productcategories = ((active == true)
                    ? productcategoryrepository.findByAdvancedSearch(productcategoryparent_ID, productcategoryparent_IDS)
                            : productcategoryrepository.findAllByAdvancedSearch(productcategoryparent_ID, productcategoryparent_IDS));

        }
 		return new ResponseEntity(getAPIResponse(productcategories, null , null, null, null, apiRequest, false, isWithDetail).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductCategory.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductCategory> productcategories, ProductCategory productcategory , JSONArray jsonProductcategories, JSONObject jsonProductCategory, String message, APIRequestDataLog apiRequest, boolean isTableLog, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productcategoryID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductCategory", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productcategory != null) {
//				if(productcategory.getPRODUCTCATEGORYPARENT_ID() != null) {
//					JSONObject productcategoryp = new JSONObject(ServiceCall.GET("productcategory/"+productcategory.getPRODUCTCATEGORYPARENT_ID(), apiRequest.getREQUEST_OUTPUT()));
//					productcategory.setPRODUCTCATEGORYPARENT_DETAIL(productcategoryp.toString());
//				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productcategory));
				productcategoryID = productcategory.getPRODUCTCATEGORY_ID();
			} else if(productcategories != null){
//				if (productcategories.size()>0) {
//					List<Integer> productcategorypList = new ArrayList<Integer>();
//					for (int i=0; i<productcategories.size(); i++) {
//						productcategorypList.add(Integer.parseInt(productcategories.get(i).getPRODUCTCATEGORYPARENT_ID().toString()));
//					}
//					JSONArray productcategorypObject = new JSONArray(ServiceCall.POST("productcategory/ids", "{productcategories: "+productcategorypList+"}", apiRequest.getREQUEST_OUTPUT()));
//
//					for (int i=0; i<productcategories.size(); i++) {
//						for (int j=0; j<productcategorypObject.length(); j++) {
//							JSONObject productcategoryp = productcategorypObject.getJSONObject(j);
//							if(productcategories.get(i).getPRODUCTCATEGORYPARENT_ID() == productcategoryp.getLong("productcategoryparent_ID") ) {
//								productcategories.get(i).setPRODUCTCATEGORYPARENT_DETAIL(productcategoryp.toString());
//							}
//						}
//					}
					apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productcategories));
//				}
			}else if (jsonProductcategories != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductcategories.toString());
			
			} else if (jsonProductCategory != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductCategory.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productcategoryID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}

	@RequestMapping(value = "/web", method = RequestMethod.GET)
	public String getActiveWeb(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		Long requestUser = (long) 0;
		JSONArray productcategories = new JSONArray();

		log.info("GET: /productcategory/web");

		List<ProductCategory >  productcategory = productcategoryrepository.findActive();
		String rtn, workstation = null;

        
        for (int i=0; i<productcategory.size(); i++) {
        	if (productcategory.get(i).getPRODUCTCATEGORYPARENT_ID()==null) {
        		JSONObject objproductcategory = new JSONObject();
        		objproductcategory.put("productcategory_ID", productcategory.get(i).getPRODUCTCATEGORY_ID());
        		objproductcategory.put("productcategoryorder_NO", productcategory.get(i).getPRODUCTCATEGORYORDER_NO());
        		objproductcategory.put("productcategory_NAME", productcategory.get(i).getPRODUCTCATEGORY_NAME());
        		objproductcategory.put("productcategory_DESC", productcategory.get(i).getPRODUCTCATEGORY_DESC());
        		objproductcategory.put("sublist", getProductcategoriesubList(productcategory.get(i).getPRODUCTCATEGORY_ID()));
        		
        		productcategories.put(objproductcategory);
        	}        	
        }
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductCategory.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/productcategory", null, workstation);

		rtn = productcategories.toString();

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	public JSONArray getProductcategoriesubList(Long productcategory_ID) {
		JSONArray objSubList = new JSONArray();
		List<ProductCategory >  productcategory = productcategoryrepository.findActiveByParent(productcategory_ID);
        if (productcategory.size()==0) {
        	return objSubList;
        } else {
			for (int i=0; i<productcategory.size(); i++) {
	    		JSONObject objproductcategory = new JSONObject();
	    		objproductcategory.put("productcategory_ID", productcategory.get(i).getPRODUCTCATEGORY_ID());
	    		objproductcategory.put("productcategoryorder_NO", productcategory.get(i).getPRODUCTCATEGORYORDER_NO());
	    		objproductcategory.put("productcategory_NAME", productcategory.get(i).getPRODUCTCATEGORY_NAME());
	    		objproductcategory.put("productcategory_DESC", productcategory.get(i).getPRODUCTCATEGORY_DESC());
	    		objproductcategory.put("sublist", getProductcategoriesubList(productcategory.get(i).getPRODUCTCATEGORY_ID()));
	    		
	    		objSubList.put(objproductcategory);
			}
        }
		return objSubList;
	}
}