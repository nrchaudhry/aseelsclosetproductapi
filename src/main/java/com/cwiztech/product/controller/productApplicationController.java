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

import com.cwiztech.log.apiRequestLog;
import com.cwiztech.product.model.ProductApplication;
import com.cwiztech.product.repository.productApplicationRepository;
import com.cwiztech.services.ServiceCall;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productapplication", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<ProductApplication> productapplications = productapplicationrepository.findActive();
		return new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productapplication/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<ProductApplication> productapplications = productapplicationrepository.findAll();

		return new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productapplication/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		ProductApplication productapplication = productapplicationrepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, productapplication , null, null, null, apiRequest, true), HttpStatus.OK);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productapplication/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<Integer> productapplication_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductapplications = jsonObj.getJSONArray("productapplications");
		for (int i=0; i<jsonproductapplications.length(); i++) {
			productapplication_IDS.add((Integer) jsonproductapplications.get(i));
		}
		List<ProductApplication> productapplications = new ArrayList<ProductApplication>();
		if (jsonproductapplications.length()>0)
			productapplications = productapplicationrepository.findByIDs(productapplication_IDS);

		return new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productapplication/notin/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<Integer> productapplication_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductapplications = jsonObj.getJSONArray("productapplications");
		for (int i=0; i<jsonproductapplications.length(); i++) {
			productapplication_IDS.add((Integer) jsonproductapplications.get(i));
		}
		List<ProductApplication> productapplications = new ArrayList<ProductApplication>();
		if (jsonproductapplications.length()>0)
			productapplications = productapplicationrepository.findByNotInIDs(productapplication_IDS);

		return new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productapplication", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productapplication/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productapplication", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductApplications, JSONObject jsonProductApplication, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
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
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductApplication Data!", apiRequest, true), HttpStatus.BAD_REQUEST);
				}
			}


			if (productapplicationid == 0) {
				if (!jsonObj.has("application_ID") || jsonObj.isNull("application_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "application_ID is missing", apiRequest, true), HttpStatus.BAD_REQUEST);

				if (!jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_ID is missing", apiRequest, true), HttpStatus.BAD_REQUEST);

			}
			if (jsonObj.has("application_ID") && !jsonObj.isNull("application_ID"))
				productapplication.setAPPLICATION_ID(jsonObj.getLong("application_ID"));

			if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID"))
				productapplication.setPRODUCT_ID(jsonObj.getLong("product_ID"));

			if (productapplicationid == 0)
				productapplication.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				productapplication.setISACTIVE(jsonObj.getString("isactive"));

			productapplication.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			productapplication.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
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
			responseentity = new ResponseEntity(getAPIResponse(null, productapplications.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productapplications, null , null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productapplication/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		ProductApplication productapplication = productapplicationrepository.findOne(id);
		productapplicationrepository.delete(productapplication);

		return new ResponseEntity(getAPIResponse(null, productapplication , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productapplication/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		JSONObject productapplication = new JSONObject();
		productapplication.put("id", id);
		productapplication.put("isactive", "N");

		return insertupdateAll(null, productapplication, apiRequest);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productapplication/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<ProductApplication> productapplications = new ArrayList<ProductApplication>();
		JSONObject jsonObj = new JSONObject(data);

		JSONArray searchObject = new JSONArray();
		List<Integer> product_IDS = new ArrayList<Integer>(); 
		List<Integer> application_IDS = new ArrayList<Integer>(); 

		product_IDS.add((int) 0);
		application_IDS.add((int) 0);

		long product_ID = 0 , application_ID = 0;

		boolean isWithDetail = true;
		if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
		}
		jsonObj.put("iswithdetail", false);

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

		if (product_ID != 0 || product_ID != 0) {
			productapplications = ((active == true)
					? productapplicationrepository.findByAdvancedSearch(product_ID, product_IDS, application_ID, application_IDS)
							: productapplicationrepository.findAllByAdvancedSearch(product_ID, product_IDS, application_ID, application_IDS));
		}

		return new ResponseEntity(getAPIResponse(productapplications, null, null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}

	String getAPIResponse(List<ProductApplication> productapplications, ProductApplication productapplication , JSONArray jsonProductApplications, JSONObject jsonProductApplication, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "ProductApplication", message).toString();
		} else {
			if (productapplication != null && isWithDetail == true) {
				if (productapplication.getAPPLICATION_ID() != null) {
					JSONObject application = new JSONObject(ServiceCall.GET("application/"+productapplication.getAPPLICATION_ID(), apiRequest.getString("access_TOKEN"), true));
					productapplication.setAPPLICATION_DETAIL(application.toString());
				}
				if (productapplication.getPRODUCT_ID() != null) {
					JSONObject product = new JSONObject(ServiceCall.GET("product/"+productapplication.getPRODUCT_ID(), apiRequest.getString("access_TOKEN"), false));
					productapplication.setPRODUCT_DETAIL(product.toString());
				}

				rtnAPIResponse = mapper.writeValueAsString(productapplication);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (productapplications != null && isWithDetail == true) {
				if (productapplications.size()>0) {
					List<Integer> applicationList = new ArrayList<Integer>();
					List<Integer> productList = new ArrayList<Integer>();

					for (int i=0; i<productapplications.size(); i++) {
						if (productapplications.get(i).getAPPLICATION_ID() != null) {
							applicationList.add(Integer.parseInt(productapplications.get(i).getAPPLICATION_ID().toString()));
						}
						if (productapplications.get(i).getPRODUCT_ID() != null) {
							productList.add(Integer.parseInt(productapplications.get(i).getPRODUCT_ID().toString()));
						}
					}

					JSONArray applicationObject = new JSONArray(ServiceCall.POST("application/ids", "{applications: "+applicationList+"}", apiRequest.getString("access_TOKEN"), true));
					JSONArray productObject = new JSONArray(ServiceCall.POST("product/ids", "{products: "+productList+"}", apiRequest.getString("access_TOKEN"), false));

					for (int i=0; i<productapplications.size(); i++) {
						for (int j=0; j<applicationObject.length(); j++) {
							JSONObject application = applicationObject.getJSONObject(j);
							if (productapplications.get(i).getAPPLICATION_ID() != null && productapplications.get(i).getAPPLICATION_ID() == application.getLong("application_ID")) {
								productapplications.get(i).setAPPLICATION_DETAIL(application.toString());
							}
						}
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if (productapplications.get(i).getPRODUCT_ID() != null && productapplications.get(i).getPRODUCT_ID() == product.getLong("product_ID")) {
								productapplications.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}

					}
				}
			}
		}
		return rtnAPIResponse;
	}
}
