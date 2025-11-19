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

import com.cwiztech.product.model.ProductItemApplication;
import com.cwiztech.product.repository.productItemApplicationRepository;
import com.cwiztech.log.apiRequestLog;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/productitemapplication")

public class productItemApplicationController {

	private static final Logger log = LoggerFactory.getLogger(productItemApplicationController.class);

	@Autowired
	private productItemApplicationRepository productitemapplicationrepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemapplication", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductItemApplication> productitemapplications = productitemapplicationrepository.findActive();
		return new ResponseEntity(getAPIResponse(productitemapplications, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemapplication/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductItemApplication> productitemapplications = productitemapplicationrepository.findAll();

		return new ResponseEntity(getAPIResponse(productitemapplications, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemapplication/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductItemApplication productitemapplication = productitemapplicationrepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, productitemapplication , null, null, null, apiRequest, true), HttpStatus.OK);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemapplication/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productitemapplication_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemapplications = jsonObj.getJSONArray("productitemapplications");
		for (int i=0; i<jsonproductitemapplications.length(); i++) {
			productitemapplication_IDS.add((Integer) jsonproductitemapplications.get(i));
		}
		List<ProductItemApplication> productitemapplications = new ArrayList<ProductItemApplication>();
		if (jsonproductitemapplications.length()>0)
			productitemapplications = productitemapplicationrepository.findByIDs(productitemapplication_IDS);

		return new ResponseEntity(getAPIResponse(productitemapplications, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemapplication/notin/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productitemapplication_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemapplications = jsonObj.getJSONArray("productitemapplications");
		for (int i=0; i<jsonproductitemapplications.length(); i++) {
			productitemapplication_IDS.add((Integer) jsonproductitemapplications.get(i));
		}
		List<ProductItemApplication> productitemapplications = new ArrayList<ProductItemApplication>();
		if (jsonproductitemapplications.length()>0)
			productitemapplications = productitemapplicationrepository.findByNotInIDs(productitemapplication_IDS);

		return new ResponseEntity(getAPIResponse(productitemapplications, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemapplication", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productitemapplication/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productitemapplication", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductItemApplications, JSONObject jsonProductItemApplication, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductItemApplication> productitemapplications = new ArrayList<ProductItemApplication>();
		if (jsonProductItemApplication != null) {
			jsonProductItemApplications = new JSONArray();
			jsonProductItemApplications.put(jsonProductItemApplication);
		}
		log.info(jsonProductItemApplications.toString());

		for (int a=0; a<jsonProductItemApplications.length(); a++) {
			JSONObject jsonObj = jsonProductItemApplications.getJSONObject(a);
			ProductItemApplication productitemapplication = new ProductItemApplication();
			long productitemapplicationid = 0;

			if (jsonObj.has("productitemapplication_ID")) {
				productitemapplicationid = jsonObj.getLong("productitemapplication_ID");
				if (productitemapplicationid != 0) {
					productitemapplication = productitemapplicationrepository.findOne(productitemapplicationid);

					if (productitemapplication == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductItemApplication Data!", apiRequest, true), HttpStatus.OK);
				}
			}


			if (productitemapplicationid == 0) {
				if (!jsonObj.has("application_ID") || jsonObj.isNull("application_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "application_ID is missing", apiRequest, true), HttpStatus.OK);

				if (!jsonObj.has("productitem_ID") || jsonObj.isNull("productitem_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_ID is missing", apiRequest, true), HttpStatus.OK);

			}
			if (jsonObj.has("application_ID") && !jsonObj.isNull("application_ID"))
				productitemapplication.setAPPLICATION_ID(jsonObj.getLong("application_ID"));

			if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID"))
				productitemapplication.setPRODUCTITEM_ID(jsonObj.getLong("productitem_ID"));

			if (productitemapplicationid == 0)
				productitemapplication.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				productitemapplication.setISACTIVE(jsonObj.getString("isactive"));

			productitemapplication.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			productitemapplication.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			productitemapplication.setMODIFIED_WHEN(dateFormat1.format(date));
			productitemapplications.add(productitemapplication);
		}

		for (int a=0; a<productitemapplications.size(); a++) {
			ProductItemApplication productitemapplication = productitemapplications.get(a);
			productitemapplication = productitemapplicationrepository.saveAndFlush(productitemapplication);
			productitemapplications.get(a).setPRODUCTITEMAPPLICATION_ID(productitemapplication.getPRODUCTITEMAPPLICATION_ID());
		}

		ResponseEntity responseentity;
		if (jsonProductItemApplication != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productitemapplications.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productitemapplications, null , null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemapplication/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductItemApplication productitemapplication = productitemapplicationrepository.findOne(id);
		productitemapplicationrepository.delete(productitemapplication);

		return new ResponseEntity(getAPIResponse(null, productitemapplication , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemapplication/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject productitemapplication = new JSONObject();
		productitemapplication.put("id", id);
		productitemapplication.put("isactive", "N");

		return insertupdateAll(null, productitemapplication, apiRequest);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemapplication/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductItemApplication> productitemapplications = new ArrayList<ProductItemApplication>();
		JSONObject jsonObj = new JSONObject(data);
		JSONArray searchObject = new JSONArray();
		List<Integer> application_IDS = new ArrayList<Integer>(); 
		List<Integer> productitem_IDS = new ArrayList<Integer>(); 

		application_IDS.add((int) 0);
		productitem_IDS.add((int) 0);

		long application_ID = 0 , productitem_ID = 0;

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

		if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID") && jsonObj.getLong("productitem_ID") != 0) {
			productitem_ID = jsonObj.getLong("productitem_ID");
			productitem_IDS.add((int) productitem_ID);
		} else if (jsonObj.has("productitem") && !jsonObj.isNull("productitem") && jsonObj.getLong("productitem") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("productitem/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("productitem/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
			}

			productitem_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				productitem_IDS.add((int) searchObject.getJSONObject(i).getLong("productitem_ID"));
			}
		}

		if (application_ID !=0 || productitem_ID !=0) {
			List<ProductItemApplication> productitemapplication = ((active == true)
					? productitemapplicationrepository.findByAdvancedSearch(application_ID,application_IDS,productitem_ID,productitem_IDS)
							: productitemapplicationrepository.findAllByAdvancedSearch(application_ID,application_IDS,productitem_ID,productitem_IDS));

		}
		return new ResponseEntity(getAPIResponse(productitemapplications, null , null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}

	String getAPIResponse(List<ProductItemApplication> productitemapplications, ProductItemApplication productitemapplication , JSONArray jsonProductItemApplications, JSONObject jsonProductItemApplication, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "ProductItemApplication", message).toString();
		} else {
			if (productitemapplication != null && isWithDetail == true) {
				if (productitemapplication.getAPPLICATION_ID() != null) {
					JSONObject application = new JSONObject(ServiceCall.GET("application/"+productitemapplication.getAPPLICATION_ID(), apiRequest.getString("access_TOKEN"), false));
					productitemapplication.setAPPLICATION_DETAIL(application.toString());

				}
				if (productitemapplication.getPRODUCTITEM_ID() != null) {
					JSONObject productitem = new JSONObject(ServiceCall.GET("productitem/"+productitemapplication.getPRODUCTITEM_ID(), apiRequest.getString("access_TOKEN"), false));
					productitemapplication.setPRODUCTITEM_DETAIL(productitem.toString());

					rtnAPIResponse = mapper.writeValueAsString(productitemapplication);
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

				} else if (productitemapplications != null && isWithDetail == true) {
					if (productitemapplications.size()>0) {
						List<Integer> productitemList = new ArrayList<Integer>();
						List<Integer> applicationList = new ArrayList<Integer>();
						for (int i=0; i<productitemapplications.size(); i++) {
							if (productitemapplications.get(i).getPRODUCTITEM_ID() != null)
								productitemList.add(Integer.parseInt(productitemapplications.get(i).getPRODUCTITEM_ID().toString()));
							if (productitemapplications.get(i).getAPPLICATION_ID() != null)
								productitemList.add(Integer.parseInt(productitemapplications.get(i).getAPPLICATION_ID().toString()));
						}
						JSONArray logisticsObject = new JSONArray(ServiceCall.POST("application/ids", "{applications: "+applicationList+"}", apiRequest.getString("access_TOKEN"), false));
						JSONArray productitemObject = new JSONArray(ServiceCall.POST("productitem/ids", "{items: "+productitemList+"}", apiRequest.getString("access_TOKEN"), false));

						for (int i=0; i<productitemapplications.size(); i++) {
							for (int j=0; j<logisticsObject.length(); j++) {
								JSONObject application = logisticsObject.getJSONObject(j);
								if (productitemapplications.get(i).getAPPLICATION_ID() == application.getLong("application_ID") ) {
									productitemapplications.get(i).setAPPLICATION_DETAIL(application.toString());
								}
							}
							for (int j=0; j<productitemObject.length(); j++) {
								JSONObject productitem = productitemObject.getJSONObject(j);
								if (productitemapplications.get(i).getPRODUCTITEM_ID() == productitem.getLong("productitem_ID") ) {
									productitemapplications.get(i).setPRODUCTITEM_DETAIL(productitem.toString());
								}
							}
						}
					}

					rtnAPIResponse = mapper.writeValueAsString(productitemapplications);
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

				} else if (productitemapplication != null && isWithDetail == true) {
					rtnAPIResponse = mapper.writeValueAsString(productitemapplication);
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

				} else if (productitemapplications != null && isWithDetail == true) {
					rtnAPIResponse = mapper.writeValueAsString(productitemapplications);
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

				} else if (jsonProductItemApplications != null) {
					rtnAPIResponse = jsonProductItemApplications.toString();
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

				} else if (jsonProductItemApplication != null) {
					rtnAPIResponse = jsonProductItemApplication.toString();
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
				}
			}
		}
		return rtnAPIResponse;
	}

}
