package com.cwiztech.product.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
import com.cwiztech.product.model.Product;
import com.cwiztech.product.model.ProductItemMovement;
import com.cwiztech.product.repository.productItemMovementRepository;
import com.cwiztech.log.apiRequestLog;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productitemmovement")
public class productItemMovementController {
private static final Logger log = LoggerFactory.getLogger(productItemMovementController.class);

	@Autowired
	private productItemMovementRepository productitemmovementrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemmovement", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductItemMovement> productitemmovements = productitemmovementrepository.findActive();
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemmovement/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductItemMovement> productitemmovements = productitemmovementrepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemmovement/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductItemMovement productitemmovement = productitemmovementrepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productitemmovement , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemmovement/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productitemmovement_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemmovements = jsonObj.getJSONArray("productitemmovements");
		for (int i=0; i<jsonproductitemmovements.length(); i++) {
			productitemmovement_IDS.add((Integer) jsonproductitemmovements.get(i));
		}
		List<ProductItemMovement> productitemmovements = new ArrayList<ProductItemMovement>();
		if (jsonproductitemmovements.length()>0)
			productitemmovements = productitemmovementrepository.findByIDs(productitemmovement_IDS);
		
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemmovement/notin/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productitemmovement_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemmovements = jsonObj.getJSONArray("productitemmovements");
		for (int i=0; i<jsonproductitemmovements.length(); i++) {
			productitemmovement_IDS.add((Integer) jsonproductitemmovements.get(i));
		}
		List<ProductItemMovement> productitemmovements = new ArrayList<ProductItemMovement>();
		if (jsonproductitemmovements.length()>0)
			productitemmovements = productitemmovementrepository.findByNotInIDs(productitemmovement_IDS);
		
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemmovement", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productitemmovement/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productitemmovement", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductItemMovements, JSONObject jsonProductItemMovement, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
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
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductItemMovement Data!", apiRequest, true), HttpStatus.OK);
				}
			}
			
			if (productitemmovementid == 0) {
				if (!jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_ID is missing", apiRequest, true), HttpStatus.OK);
				
				if (!jsonObj.has("productitemmovement_DATE") || jsonObj.isNull("productitemmovement_DATE"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitemmovement_DATE is missing", apiRequest, true), HttpStatus.OK);
					
				if (!jsonObj.has("productitem_QUANTITY") || jsonObj.isNull("productitem_QUANTITY"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_QUANTITY is missing", apiRequest, true), HttpStatus.OK);
				
				if (!jsonObj.has("productitemmovement_QUANTITY") || jsonObj.isNull("productitemmovement_QUANTITY"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitemmovement_QUANTITY is missing", apiRequest, true), HttpStatus.OK);
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

			productitemmovement.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			productitemmovement.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
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
			responseentity = new ResponseEntity(getAPIResponse(null, productitemmovements.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemmovement/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductItemMovement productitemmovement = productitemmovementrepository.findOne(id);
		productitemmovementrepository.delete(productitemmovement);
		
		return new ResponseEntity(getAPIResponse(null, productitemmovement , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemmovement/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		JSONObject productitemmovement = new JSONObject();
		productitemmovement.put("id", id);
		productitemmovement.put("isactive", "N");
		
		return insertupdateAll(null, productitemmovement, apiRequest);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity getBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		return BySearch(data, true, headToken, LimitGrant);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search/all", method = RequestMethod.POST)
	public ResponseEntity getAllBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		return BySearch(data, false, headToken, LimitGrant);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity BySearch(String data, boolean active, String headToken, String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemmovement/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductItemMovement> productitemmovements = ((active == true)
				? productitemmovementrepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: productitemmovementrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, true), HttpStatus.OK);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemmovement/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		List<ProductItemMovement> productitemmovements = new ArrayList<ProductItemMovement>();
		JSONObject jsonObj = new JSONObject(data);
		   JSONArray searchObject = new JSONArray();
	        List<Integer> employee_IDS = new ArrayList<Integer>(); 
	        List<Integer> product_IDS = new ArrayList<Integer>(); 

	        employee_IDS.add((int) 0);
	        product_IDS.add((int) 0);
	        
		long employee_ID = 0 , product_ID = 0;
		
       boolean isWithDetail = true;
       if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
           isWithDetail = jsonObj.getBoolean("iswithdetail");
       }
       jsonObj.put("iswithdetail", false);
		
       if (jsonObj.has("employee_ID") && !jsonObj.isNull("employee_ID") && jsonObj.getLong("employee_ID") != 0) {
           employee_ID = jsonObj.getLong("employee_ID");
           employee_IDS.add((int) employee_ID);
       } else if (jsonObj.has("employee") && !jsonObj.isNull("employee") && jsonObj.getLong("employee") != 0) {
           if (active == true) {
               searchObject = new JSONArray(ServiceCall.POST("employee/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
           } else {
               searchObject = new JSONArray(ServiceCall.POST("employee/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
           }

           employee_ID = searchObject.length();
           for (int i=0; i<searchObject.length(); i++) {
               employee_IDS.add((int) searchObject.getJSONObject(i).getLong("employee_ID"));
           }
       }
		
       if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID") && jsonObj.getLong("product_ID") != 0) {
           product_ID = jsonObj.getLong("product_ID");
           product_IDS.add((int) product_ID);
       } else if (jsonObj.has("product") && !jsonObj.isNull("product") && jsonObj.getLong("product") != 0) {
           if (active == true) {
               searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
           } else {
               searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
           }

           product_ID = searchObject.length();
           for (int i=0; i<searchObject.length(); i++) {
               product_IDS.add((int) searchObject.getJSONObject(i).getLong("product_ID"));
           }
       }
       
	if (product_ID != 0 || employee_ID != 0 ) {
		List<ProductItemMovement> productitemmovement = ((active == true)
				? productitemmovementrepository.findByAdvancedSearch(product_ID,product_IDS,employee_ID,employee_IDS)
				: productitemmovementrepository.findAllByAdvancedSearch(product_ID,product_IDS,employee_ID,employee_IDS));

	}
		return new ResponseEntity(getAPIResponse(productitemmovements, null , null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}

	String getAPIResponse(List<ProductItemMovement> productitemmovements, ProductItemMovement productitemmovement , JSONArray jsonProductItemMovements, JSONObject jsonProductItemMovement, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";
		
		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "ProductItemMovement", message).toString();
		} else {
			if ((productitemmovements != null || productitemmovement != null) && isWithDetail == true) {
				if (productitemmovement != null) {
					productitemmovements = new ArrayList<ProductItemMovement>();
					productitemmovements.add(productitemmovement);
				}
				if (productitemmovements.size()>0) {
					List<Integer> productList = new ArrayList<Integer>();
					List<Integer> employeeList = new ArrayList<Integer>();

					for (int i=0; i<productitemmovements.size(); i++) {
						if (productitemmovements.get(i).getPRODUCT_ID() != null) {
							productList.add(Integer.parseInt(productitemmovements.get(i).getPRODUCT_ID().toString()));
						}

						if (productitemmovements.get(i).getEMPLOYEE_ID() != null) {
							employeeList.add(Integer.parseInt(productitemmovements.get(i).getEMPLOYEE_ID().toString()));
						}
					}

					CompletableFuture<JSONArray> productFuture = CompletableFuture.supplyAsync(() -> {
						if (productList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("product/ids", "{products: "+productList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});

					CompletableFuture<JSONArray> employeeFuture = CompletableFuture.supplyAsync(() -> {
						if (employeeList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("employee/ids", "{employees: "+employeeList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});

					// Wait until all futures complete
					CompletableFuture<Void> allDone =
							CompletableFuture.allOf(productFuture,employeeFuture );

					// Block until all are done
					allDone.join();

					JSONArray productObject = new JSONArray(productFuture.toString());
					JSONArray employeeObject = new JSONArray(employeeFuture.toString());

					for (int i=0; i<productitemmovements.size(); i++) {
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if (productitemmovements.get(i).getPRODUCT_ID() != null && productitemmovements.get(i).getPRODUCT_ID() == product.getLong("product_ID")) {
								productitemmovements.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
						for (int j=0; j<employeeObject.length(); j++) {
							JSONObject employee = employeeObject.getJSONObject(j);
							if (productitemmovements.get(i).getEMPLOYEE_ID() != null && productitemmovements.get(i).getEMPLOYEE_ID() == employee.getLong("EMPLOYEE_ID")) {
								productitemmovements.get(i).setEMPLOYEE_DETAIL(employee.toString());
							}
						}
					}
				}

				if (productitemmovement != null)
					rtnAPIResponse = mapper.writeValueAsString(productitemmovements.get(0));
				else	
					rtnAPIResponse = mapper.writeValueAsString(productitemmovements);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (productitemmovement != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(productitemmovement);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (productitemmovements != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(productitemmovements);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonProductItemMovements != null) {
				rtnAPIResponse = jsonProductItemMovements.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonProductItemMovement != null) {
				rtnAPIResponse = jsonProductItemMovements.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}
		}

		return rtnAPIResponse;
	}
}

		
