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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.product.model.ProductItemMovement;
import com.cwiztech.product.repository.productItemMovementRepository;
import com.cwiztech.services.EmployeeService;
import com.cwiztech.services.ProductService;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productitemmovement")
public class productItemMovementController {
private static final Logger log = LoggerFactory.getLogger(productItemMovementController.class);

	@Autowired
	private productItemMovementRepository productitemmovementrepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;

	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemmovement", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemMovement> productitemmovements = productitemmovementrepository.findActive();
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemmovement/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemMovement> productitemmovements = productitemmovementrepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemmovement/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItemMovement productitemmovement = productitemmovementrepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productitemmovement , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemmovement/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitemmovement_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemmovements = jsonObj.getJSONArray("productitemmovements");
		for (int i=0; i<jsonproductitemmovements.length(); i++) {
			productitemmovement_IDS.add((Integer) jsonproductitemmovements.get(i));
		}
		List<ProductItemMovement> productitemmovements = new ArrayList<ProductItemMovement>();
		if (jsonproductitemmovements.length()>0)
			productitemmovements = productitemmovementrepository.findByIDs(productitemmovement_IDS);
		
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemmovement/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitemmovement_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemmovements = jsonObj.getJSONArray("productitemmovements");
		for (int i=0; i<jsonproductitemmovements.length(); i++) {
			productitemmovement_IDS.add((Integer) jsonproductitemmovements.get(i));
		}
		List<ProductItemMovement> productitemmovements = new ArrayList<ProductItemMovement>();
		if (jsonproductitemmovements.length()>0)
			productitemmovements = productitemmovementrepository.findByNotInIDs(productitemmovement_IDS);
		
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemmovement", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/productitemmovement/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productitemmovement", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductItemMovements, JSONObject jsonProductItemMovement, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductItemMovement> productitemmovements = new ArrayList<ProductItemMovement>();
		if (jsonProductItemMovement != null) {
			jsonProductItemMovements = new JSONArray();
			jsonProductItemMovements.put(jsonProductItemMovement);
		}
		log.info(jsonProductItemMovements.toString());
		
		for (int a=0; a<jsonProductItemMovements.length(); a++) {
			JSONObject jsonObj = jsonProductItemMovements.getJSONObject(a);
			ProductItemMovement productitemmovement = new ProductItemMovement();
			long productitemmovementid = 0;

			if (jsonObj.has("productitemmovement_ID")) {
				productitemmovementid = jsonObj.getLong("productitemmovement_ID");
				if (productitemmovementid != 0) {
					productitemmovement = productitemmovementrepository.findOne(productitemmovementid);
					
					if (productitemmovement == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductItemMovement Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (productitemmovementid == 0) {
				if (!jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productitemmovement_DATE") || jsonObj.isNull("productitemmovement_DATE"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitemmovement_DATE is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
				if (!jsonObj.has("productitem_QUANTITY") || jsonObj.isNull("productitem_QUANTITY"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_QUANTITY is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productitemmovement_QUANTITY") || jsonObj.isNull("productitemmovement_QUANTITY"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitemmovement_QUANTITY is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			}
			
			if (jsonObj.has("productitemmovement_DATE") && !jsonObj.isNull("productitemmovement_DATE")) 
			productitemmovement.setPRODUCTITEMMOVEMENT_DATE(jsonObj.getString("productitemmovement_DATE"));
		
			if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID")) 
			productitemmovement.setPRODUCT_ID(jsonObj.getLong("product_ID"));
			
			if (jsonObj.has("productitem_QUANTITY") && !jsonObj.isNull("productitem_QUANTITY")) 
				productitemmovement.setPRODUCTITEM_QUANTITY(jsonObj.getLong("productitem_QUANTITY"));
		
			if (jsonObj.has("productitemmovement_QUANTITY") && !jsonObj.isNull("productitemmovement_QUANTITY")) 
			productitemmovement.setPRODUCTITEMMOVEMENT_QUANTITY(jsonObj.getLong("productitemmovement_QUANTITY"));
		
			if (jsonObj.has("employee_ID")  && !jsonObj.isNull("employee_ID"))
			productitemmovement.setEMPLOYEE_ID(jsonObj.getLong("employee_ID"));
		
			if (jsonObj.has("assigntoemployee_DATETIME") && !jsonObj.isNull("assigntoemployee_DATETIME"))
		    productitemmovement.setASSIGNTOEMPLOYEE_DATETIME(jsonObj.getString("assigntoemployee_DATETIME"));
			
			if (jsonObj.has("isproductitemmoved")  && !jsonObj.isNull("isproductitemmoved"))
			productitemmovement.setISPRODUCTITEMMOVED(jsonObj.getString("isproductitemmoved"));
		
		    if (jsonObj.has("productitemmovement_DATETIME") && !jsonObj.isNull("productitemmovement_DATETIME"))
			productitemmovement.setPRODUCTITEMMOVEMENT_DATETIME(jsonObj.getString("productitemmovement_DATETIME"));
			
		    if (productitemmovementid == 0)
				productitemmovement.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				productitemmovement.setISACTIVE(jsonObj.getString("isactive"));

			productitemmovement.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productitemmovement.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productitemmovement.setMODIFIED_WHEN(dateFormat1.format(date));
			productitemmovements.add(productitemmovement);
		}
		
		for (int a=0; a<productitemmovements.size(); a++) {
			ProductItemMovement productitemmovement = productitemmovements.get(a);
			productitemmovement = productitemmovementrepository.saveAndFlush(productitemmovement);
			productitemmovements.get(a).setPRODUCTITEMMOVEMENT_ID(productitemmovement.getPRODUCTITEMMOVEMENT_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonProductItemMovement != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productitemmovements.get(0) , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemmovement/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItemMovement productitemmovement = productitemmovementrepository.findOne(id);
		productitemmovementrepository.delete(productitemmovement);
		
		return new ResponseEntity(getAPIResponse(null, productitemmovement , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemmovement/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject productitemmovement = new JSONObject();
		productitemmovement.put("id", id);
		productitemmovement.put("isactive", "N");
		
		return insertupdateAll(null, productitemmovement, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemmovement/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductItemMovement> productitemmovements = ((active == true)
				? productitemmovementrepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: productitemmovementrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemmovement/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		List<ProductItemMovement> productitemmovements = new ArrayList<ProductItemMovement>();
		JSONObject jsonObj = new JSONObject(data);
		long product_ID = 0, productitemmovementdate=0 ,employee_ID=0, assigntoemployeedatetime=0 ,productitemmovementdatetime=0,productbrand_ID=0;
		String productitemmovement_DATE="";
		String assigntoemployee_DATETIME="";
		String productitemmovement_DATETIME="";
		
		if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID"))
			product_ID = jsonObj.getLong("product_ID");
		if (jsonObj.has("productcategory_ID") && !jsonObj.isNull("productcategory_ID") && product_ID == 0 ) {
            JSONArray productcategorysObject;
            if (active == true) {
            	productcategorysObject = new JSONArray(ProductService.POST("product/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken));
            } else {
            	productcategorysObject = new JSONArray(ProductService.POST("product/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken));
            }

            for (int i=0; i<productcategorysObject.length(); i++) {
                List<ProductItemMovement> productitemmovement = new ArrayList<ProductItemMovement>();
                productitemmovement = ((active == true)
                        ? productitemmovementrepository.findByAdvancedSearch(productcategorysObject.getJSONObject(i).getLong("product_ID") , (long) 0, "",  (long) 0,"", (long) 0, (long) 0,"" )
                        : productitemmovementrepository.findAllByAdvancedSearch(productcategorysObject.getJSONObject(i).getLong("product_ID") , (long) 0,"",  (long) 0,"", (long) 0, (long) 0,"" ));
                for (int j=0; j<productitemmovement.size(); j++) {
                	productitemmovements.add(productitemmovement.get(j));
                }
            }
        }
		
		if (jsonObj.has("productitemmovement_DATE")) {
			productitemmovementdate = 1;
			productitemmovement_DATE = jsonObj.getString("productitemmovement_DATE");
		}
		
		if (jsonObj.has("assigntoemployee_DATETIME")) {
			assigntoemployeedatetime = 1;
			assigntoemployee_DATETIME = jsonObj.getString("assigntoemployee_DATETIME");		
		}
		
		if (jsonObj.has("employee_ID") && !jsonObj.isNull("employee_ID")) {
			employee_ID = jsonObj.getLong("employee_ID");
		}
		
		if (jsonObj.has("productitemmovement_DATETIME")) {
			productitemmovementdatetime = 1;
			productitemmovement_DATETIME = jsonObj.getString("productitemmovement_DATETIME");
		}

	if(product_ID != 0 || productitemmovementdatetime != 0 || employee_ID != 0 || assigntoemployeedatetime != 0 || productitemmovementdate != 0){
		List<ProductItemMovement> productitemmovement = ((active == true)
				? productitemmovementrepository.findByAdvancedSearch(product_ID,productitemmovementdate, productitemmovement_DATE, 
						assigntoemployeedatetime, assigntoemployee_DATETIME,employee_ID,productitemmovementdatetime,productitemmovement_DATETIME)
				: productitemmovementrepository.findAllByAdvancedSearch(product_ID,productitemmovementdate, productitemmovement_DATE, 
						assigntoemployeedatetime, assigntoemployee_DATETIME,employee_ID,productitemmovementdatetime,productitemmovement_DATETIME));
	for (int i=0; i<productitemmovement.size(); i++) {
        boolean found = false;
        
        for (int j=0; j<productitemmovements.size(); j++) {
            if (productitemmovement.get(i).getPRODUCTITEMMOVEMENT_ID() == productitemmovements.get(j).getPRODUCTITEMMOVEMENT_ID()) {
                found = true;
                break;
            }
        }
        
        if (found == false) {
        	productitemmovements.add(productitemmovement.get(i));
        }
    }
	}
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItemMovement.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductItemMovement> productitemmovements, ProductItemMovement productitemmovement , JSONArray jsonProductItemMovements, JSONObject jsonProductItemMovement, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productitemmovementID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductItemMovement", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productitemmovement != null) {
				JSONObject product = new JSONObject(ProductService.GET("product/"+productitemmovement.getPRODUCT_ID(), apiRequest.getREQUEST_OUTPUT()));
				productitemmovement.setPRODUCT_DETAIL(product.toString());
				if(productitemmovement.getEMPLOYEE_ID() != null) {
					JSONObject employee = new JSONObject(EmployeeService.GET("employee/"+productitemmovement.getEMPLOYEE_ID(), apiRequest.getREQUEST_OUTPUT()));
					productitemmovement.setEMPLOYEE_DETAIL(employee.toString());
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitemmovement));
				productitemmovementID = productitemmovement.getPRODUCTITEMMOVEMENT_ID();
			} else if(productitemmovements != null){
				if (productitemmovements.size()>0) {
					List<Integer> productList = new ArrayList<Integer>();
					List<Integer> employeeList = new ArrayList<Integer>();
					for (int i=0; i<productitemmovements.size(); i++) {
						if(productitemmovements.get(i).getPRODUCT_ID() != null) 
						  productList.add(Integer.parseInt(productitemmovements.get(i).getPRODUCT_ID().toString()));
						if(productitemmovements.get(i).getEMPLOYEE_ID() != null) 
						  employeeList.add(Integer.parseInt(productitemmovements.get(i).getEMPLOYEE_ID().toString()));
					}
					JSONArray productObject = new JSONArray(ProductService.POST("product/ids", "{products: "+productList+"}", apiRequest.getREQUEST_OUTPUT()));
					JSONArray employeeObject = new JSONArray(EmployeeService.POST("employee/ids", "{employees: "+employeeList+"}", apiRequest.getREQUEST_OUTPUT()));	
					for (int i=0; i<productitemmovements.size(); i++) {
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if(productitemmovements.get(i).getPRODUCT_ID() == product.getLong("product_ID") ) {
								productitemmovements.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
						for (int j=0; j<employeeObject.length(); j++) {
							JSONObject employee = employeeObject.getJSONObject(j);
							if(productitemmovements.get(i).getEMPLOYEE_ID() == employee.getLong("employee_ID") ) {
								productitemmovements.get(i).setEMPLOYEE_DETAIL(employee.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitemmovements));
			}else if (jsonProductItemMovements != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemMovements.toString());
			
			} else if (jsonProductItemMovement != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemMovement.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productitemmovementID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity getProductQuantity(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {

		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;

		log.info("POST: productitemmovement/refresh");
		log.info("Input: " + data);
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItemMovement.getDatabaseTableID());

	if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/productitemmovement/refresh", data, workstation);
		apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
		apirequestdatalogRepository.saveAndFlush(apiRequest);
		return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
	}
		
		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/productitemmovement/refresh", data, workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		JSONObject jsonObj = new JSONObject(data);
		long netsuite_ID=0, saleorder_ID = 0,product_ID=0,saleordertype_ID=0,shop_ID = 0, transportroute_ID = -1, saleorderstatus_ID=0,
				routestatus_ID=0,saleorderdate=0,saleorderdeliverydate=0,productcategory_ID=0,productbrand_ID=0,producttype_ID=0,
				saleorderstatus=0, saleordertype=0, routestatus=0;
		String saleorder_DATE="", saleorderdelivery_DATE="", saleorderstatus_CODE="", saleordertype_CODE="", routestatus_CODE="";
		
		if (jsonObj.has("netsuite_ID"))
			netsuite_ID = jsonObj.getLong("netsuite_ID");
		
		if (jsonObj.has("saleorder_ID"))
			saleorder_ID = jsonObj.getLong("saleorder_ID");
		
		if (jsonObj.has("product_ID"))
			product_ID = jsonObj.getLong("product_ID");
		
		if (jsonObj.has("saleordertype_ID"))
			saleordertype_ID = jsonObj.getLong("saleordertype_ID");
		if (jsonObj.has("saleordertype_CODE")) {
			saleordertype = 1;
			saleordertype_CODE = jsonObj.getString("saleordertype_CODE");
		}

		if (jsonObj.has("shop_ID"))
			shop_ID = jsonObj.getLong("shop_ID");
		
		if (jsonObj.has("transportroute_ID"))
			transportroute_ID = jsonObj.getLong("transportroute_ID");
		
		if (jsonObj.has("saleorderstatus_ID"))
			saleorderstatus_ID = jsonObj.getLong("saleorderstatus_ID");
		if (jsonObj.has("saleorderstatus_CODE")) {
			saleorderstatus = 1;
			saleorderstatus_CODE = jsonObj.getString("saleorderstatus_CODE");
		}
		
		if (jsonObj.has("routestatus_ID"))
			routestatus_ID = jsonObj.getLong("routestatus_ID");
		if (jsonObj.has("routestatus_CODE")) {
			routestatus = 1;
			routestatus_CODE = jsonObj.getString("routestatus_CODE");
		}

		if (jsonObj.has("saleorder_DATE")) {
			saleorderdate = 1;
			saleorder_DATE = jsonObj.getString("saleorder_DATE");
		}
		
		if (jsonObj.has("saleorderdelivery_DATE")) {
			saleorderdeliverydate = 1;
			saleorderdelivery_DATE = jsonObj.getString("saleorderdelivery_DATE");
		}

		if (jsonObj.has("productcategory_ID"))
			productcategory_ID = jsonObj.getLong("productcategory_ID");
		
		if (jsonObj.has("productbrand_ID"))
			productbrand_ID = jsonObj.getLong("productbrand_ID");
		
		if (jsonObj.has("producttype_ID"))
			producttype_ID = jsonObj.getLong("producttype_ID");
		
//		List<Object[]> productquantity = orderdetailrepository.findProdutQuantityByAdvancedSearch(netsuite_ID,saleorder_ID,product_ID,saleordertype_ID, shop_ID, transportroute_ID, saleorderstatus_ID, routestatus_ID,saleorderdate, saleorder_DATE,saleorderdeliverydate,saleorderdelivery_DATE,productcategory_ID,productbrand_ID,producttype_ID, saleorderstatus, saleorderstatus_CODE, saleordertype, saleordertype_CODE, routestatus, routestatus_CODE);
		List<Object[]> productquantity = null;
		
		for (int i=0; i<productquantity.size(); i++) {
			Object[] obj = productquantity.get(i);
			JSONObject productquantityObject = new JSONObject();
			long productID = (int) obj[0];
			long productQTY = (int) obj[2];
			List<ProductItemMovement> productitemsmovement = productitemmovementrepository.findByAdvancedSearch(productID, (long) 1, (String) obj[1], 
							(long) 0, "", (long) 0, (long) 0, "");
			
			if (productitemsmovement.size()>0) {
				ProductItemMovement productitemmovement = productitemsmovement.get(0);
				productitemmovement.setPRODUCTITEM_QUANTITY(productQTY);
				productitemmovement.setMODIFIED_BY(requestUser);
				productitemmovement.setMODIFIED_WORKSTATION(workstation);
				productitemmovement.setMODIFIED_WHEN(dateFormat1.format(date));
				productitemmovement = productitemmovementrepository.saveAndFlush(productitemmovement);
			} else {
				ProductItemMovement productitemmovement = new ProductItemMovement();
				productitemmovement.setPRODUCTITEMMOVEMENT_DATE( (String) obj[1]);
				productitemmovement.setPRODUCT_ID(productID);
				productitemmovement.setPRODUCTITEM_QUANTITY(productQTY);
				productitemmovement.setISACTIVE("Y");
				productitemmovement.setMODIFIED_BY(requestUser);
				productitemmovement.setMODIFIED_WORKSTATION(workstation);
				productitemmovement.setMODIFIED_WHEN(dateFormat1.format(date));
				productitemmovement = productitemmovementrepository.saveAndFlush(productitemmovement);
			}
		}

		List<ProductItemMovement> productitemmovement = productitemmovementrepository.findByAdvancedSearch((long) 0, saleorderdeliverydate, saleorderdelivery_DATE, 
				(long) 0, "", (long) 0, (long) 0, "");
		rtn = mapper.writeValueAsString(productitemmovement);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);

	}
}
