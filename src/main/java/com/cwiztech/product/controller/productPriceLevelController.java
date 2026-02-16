package com.cwiztech.product.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

import com.cwiztech.product.model.Product;
import com.cwiztech.product.repository.productRepository;
import com.cwiztech.log.apiRequestLog;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import com.cwiztech.product.model.ProductPriceLevel;
import com.cwiztech.product.repository.productPriceLevelRepository;
import com.cwiztech.log.apiRequestLog;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/productpricelevel")
public class productPriceLevelController {
	private static final Logger log = LoggerFactory.getLogger(productPriceLevelController.class);
	@Autowired
	private productPriceLevelRepository productpricelevelrepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productpricelevel", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductPriceLevel> productpricelevels = productpricelevelrepository.findActive();
		return new ResponseEntity(getAPIResponse(productpricelevels, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productpricelevel/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductPriceLevel> productpricelevels = productpricelevelrepository.findAll();

		return new ResponseEntity(getAPIResponse(productpricelevels, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productpricelevel/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductPriceLevel productpricelevel = productpricelevelrepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, productpricelevel , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productpricelevel/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productpricelevel_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductpricelevels = jsonObj.getJSONArray("productpricelevels");
		for (int i=0; i<jsonproductpricelevels.length(); i++) {
			productpricelevel_IDS.add((Integer) jsonproductpricelevels.get(i));
		}
		List<ProductPriceLevel> productpricelevels = new ArrayList<ProductPriceLevel>();
		if (jsonproductpricelevels.length()>0)
			productpricelevels = productpricelevelrepository.findByIDs(productpricelevel_IDS);

		return new ResponseEntity(getAPIResponse(productpricelevels, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productpricelevel/notin/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productpricelevel_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductpricelevels = jsonObj.getJSONArray("productpricelevels");
		for (int i=0; i<jsonproductpricelevels.length(); i++) {
			productpricelevel_IDS.add((Integer) jsonproductpricelevels.get(i));
		}
		List<ProductPriceLevel> productpricelevels = new ArrayList<ProductPriceLevel>();
		if (jsonproductpricelevels.length()>0)
			productpricelevels = productpricelevelrepository.findByNotInIDs(productpricelevel_IDS);

		return new ResponseEntity(getAPIResponse(productpricelevels, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productpricelevel", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {

		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productpricelevel/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productpricelevel", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductPriceLevels, JSONObject jsonProductPriceLevel, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductPriceLevel> productpricelevels = new ArrayList<ProductPriceLevel>();
		if (jsonProductPriceLevel != null) {
			jsonProductPriceLevels = new JSONArray();
			jsonProductPriceLevels.put(jsonProductPriceLevel);
		}
		log.info(jsonProductPriceLevels.toString());

		for (int a=0; a<jsonProductPriceLevels.length(); a++) {
			JSONObject jsonObj = jsonProductPriceLevels.getJSONObject(a);
			ProductPriceLevel productpricelevel = new ProductPriceLevel();
			long productpricelevelid = 0;

			if (jsonObj.has("productpricelevel_ID")) {
				productpricelevelid = jsonObj.getLong("productpricelevel_ID");
				if (productpricelevelid != 0) {
					productpricelevel = productpricelevelrepository.findOne(productpricelevelid);

					if (productpricelevel == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductPriceLevel Data!", apiRequest, true), HttpStatus.OK);
				}
			}

			if (productpricelevelid == 0) {
				if (!jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_ID is missing", apiRequest, true), HttpStatus.OK);

				if (!jsonObj.has("product_QUANTITY") || jsonObj.isNull("product_QUANTITY"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_QUANTITY is missing", apiRequest, true), HttpStatus.OK);

				if (!jsonObj.has("product_UNITPRICE") || jsonObj.isNull("product_UNITPRICE"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_UNITPRICE is missing", apiRequest, true), HttpStatus.OK);
			}

			if (jsonObj.has("currency_ID") && !jsonObj.isNull("currency_ID")) 			
				productpricelevel.setCURRENCY_ID(jsonObj.getLong("currency_ID"));
			else
				productpricelevel.setCURRENCY_ID((long) 91);

			if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID")) 
				productpricelevel.setPRODUCT_ID(jsonObj.getLong("product_ID"));

			if (jsonObj.has("pricelevel_ID") && !jsonObj.isNull("pricelevel_ID")) 
				productpricelevel.setPRICELEVEL_ID(jsonObj.getLong("pricelevel_ID"));
			else
				productpricelevel.setPRICELEVEL_ID((long) 148);

			if (jsonObj.has("product_QUANTITY")  && !jsonObj.isNull("product_QUANTITY"))
				productpricelevel.setPRODUCT_QUANTITY(jsonObj.getLong("product_QUANTITY"));

			if (jsonObj.has("product_UNITPRICE") && !jsonObj.isNull("product_UNITPRICE"))
				productpricelevel.setPRODUCT_UNITPRICE(BigDecimal.valueOf(jsonObj.getDouble("product_UNITPRICE")));

			if (productpricelevelid == 0)
				productpricelevel.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				productpricelevel.setISACTIVE(jsonObj.getString("isactive"));

			productpricelevel.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			productpricelevel.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			productpricelevel.setMODIFIED_WHEN(dateFormat1.format(date));
			productpricelevels.add(productpricelevel);
		}

		for (int a=0; a<productpricelevels.size(); a++) {
			ProductPriceLevel productpricelevel = productpricelevels.get(a);
			productpricelevel = productpricelevelrepository.saveAndFlush(productpricelevel);
			productpricelevels.get(a).setPRODUCTPRICELEVEL_ID(productpricelevel.getPRODUCTPRICELEVEL_ID());
		}

		ResponseEntity responseentity;
		if (jsonProductPriceLevel != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productpricelevels.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productpricelevels, null , null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productpricelevel/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductPriceLevel productpricelevel = productpricelevelrepository.findOne(id);
		productpricelevelrepository.delete(productpricelevel);

		return new ResponseEntity(getAPIResponse(null, productpricelevel , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productpricelevel/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject productpricelevel = new JSONObject();
		productpricelevel.put("id", id);
		productpricelevel.put("isactive", "N");

		return insertupdateAll(null, productpricelevel, apiRequest);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity getBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		return BySearch(data, true, headToken, LimitGrant);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/search/all", method = RequestMethod.POST)
	public ResponseEntity getAllBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		return BySearch(data, false, headToken, LimitGrant);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity BySearch(String data, boolean active, String headToken, String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productpricelevel/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductPriceLevel> productpricelevels = ((active == true)
				? productpricelevelrepository.findBySearch("%" + jsonObj.getString("search") + "%")
						: productpricelevelrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		return new ResponseEntity(getAPIResponse(productpricelevels, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/advancedsearch", method = RequestMethod.POST)
	public ResponseEntity getByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		return ByAdvancedSearch(data, true, headToken, LimitGrant);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/advancedsearch/all", method = RequestMethod.POST)
	public ResponseEntity getAllByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		return ByAdvancedSearch(data, false, headToken, LimitGrant);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity ByAdvancedSearch(String data, boolean active, String headToken, String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productpricelevel/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductPriceLevel> productpricelevels = new ArrayList<ProductPriceLevel>();
		JSONObject jsonObj = new JSONObject(data);
		JSONArray searchObject = new JSONArray();
		List<Integer> currency_IDS = new ArrayList<Integer>(); 
		List<Integer> product_IDS = new ArrayList<Integer>(); 
		List<Integer> pricelevel_IDS = new ArrayList<Integer>(); 

		currency_IDS.add((int) 0);
		product_IDS.add((int) 0);
		pricelevel_IDS.add((int) 0);
		long currency_ID = 0, product_ID=0, pricelevel_ID=0;

		boolean isWithDetail = true;
		if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
			isWithDetail = jsonObj.getBoolean("iswithdetail");
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

		if (jsonObj.has("currency_ID") && !jsonObj.isNull("currency_ID"))
			currency_ID = jsonObj.getLong("currency_ID");
		else if (jsonObj.has("currency_CODE") && !jsonObj.isNull("currency_CODE")) {
			JSONObject currency = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'CURRENCY', code: "+jsonObj.getString("currency_CODE")+"}", apiRequest.getString("access_TOKEN"), true));
			if (currency != null)
				currency_ID = currency.getLong("id");
		}

		if (jsonObj.has("pricelevel_ID") && !jsonObj.isNull("pricelevel_ID"))
			pricelevel_ID = jsonObj.getLong("pricelevel_ID");
		else if (jsonObj.has("pricelevel_CODE") && !jsonObj.isNull("pricelevel_CODE")) {
			JSONObject pricelevel = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'PRICELEVEL', code: "+jsonObj.getString("pricelevel_CODE")+"}", apiRequest.getString("access_TOKEN"), true));
			if (pricelevel != null)
				pricelevel_ID = pricelevel.getLong("id");
		}

		if (currency_ID != 0 || product_ID != 0 || pricelevel_ID != 0) {
			productpricelevels = ((active == true)
					? productpricelevelrepository.findByAdvancedSearch(product_ID, product_IDS, pricelevel_ID, currency_ID)
							: productpricelevelrepository.findAllByAdvancedSearch(product_ID, product_IDS, pricelevel_ID, currency_ID));
		}
		return new ResponseEntity(getAPIResponse(productpricelevels, null , null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}

	String getAPIResponse(List<ProductPriceLevel> productpricelevels, ProductPriceLevel productpricelevel , JSONArray jsonProductPriceLevels, JSONObject jsonProductPriceLevel, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException, InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "ProductPriceLevel", message).toString();
		} else {
			if (productpricelevel != null && isWithDetail == true) {
				JSONObject product = new JSONObject(ServiceCall.GET("product/"+productpricelevel.getPRODUCT_ID(), apiRequest.getString("access_TOKEN"), false));
				productpricelevel.setPRODUCT_DETAIL(product.toString());

				List<Integer> lookupList = new ArrayList<Integer>();
				if (productpricelevel.getCURRENCY_ID() != null)
					lookupList.add(Integer.parseInt(productpricelevel.getCURRENCY_ID().toString()));
				if (productpricelevel.getPRICELEVEL_ID() != null)
					lookupList.add(Integer.parseInt(productpricelevel.getPRICELEVEL_ID().toString()));

				JSONArray lookupObject = new JSONArray(ServiceCall.POST("lookup/ids", "{lookups: "+lookupList+"}", apiRequest.getString("access_TOKEN"), true));	
				for (int j=0; j<lookupObject.length(); j++) {
					JSONObject lookup = lookupObject.getJSONObject(j);
					if (productpricelevel.getCURRENCY_ID() != null && productpricelevel.getCURRENCY_ID() == lookup.getLong("id") ) {
						productpricelevel.setCURRENCY_DETAIL(lookup.toString());
					}
					if (productpricelevel.getPRICELEVEL_ID() != null && productpricelevel.getPRICELEVEL_ID() == lookup.getLong("id") ) {
						productpricelevel.setPRICELEVEL_DETAIL(lookup.toString());
					}
				}
				rtnAPIResponse = mapper.writeValueAsString(productpricelevel);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (productpricelevels != null && isWithDetail == true) {
				if (productpricelevels.size()>0) {
					List<Integer> productList = new ArrayList<Integer>();
					List<Integer> lookupList = new ArrayList<Integer>();
					for (int i=0; i<productpricelevels.size(); i++) {
						if (productpricelevels.get(i).getPRODUCT_ID() != null)
							productList.add(Integer.parseInt(productpricelevels.get(i).getPRODUCT_ID().toString()));
						if (productpricelevels.get(i).getPRICELEVEL_ID() != null)
							lookupList.add(Integer.parseInt(productpricelevels.get(i).getPRICELEVEL_ID().toString()));
						if (productpricelevels.get(i).getCURRENCY_ID() != null)
							lookupList.add(Integer.parseInt(productpricelevels.get(i).getCURRENCY_ID().toString()));
					}
					JSONArray productObject = new JSONArray(ServiceCall.POST("product/ids", "{products: "+productList+"}", apiRequest.getString("access_TOKEN"), false));
					JSONArray lookupObject = new JSONArray(ServiceCall.POST("lookup/ids", "{lookups: "+lookupList+"}", apiRequest.getString("access_TOKEN"), true));	

					for (int i=0; i<productpricelevels.size(); i++) {
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if (productpricelevels.get(i).getPRODUCT_ID() == product.getLong("product_ID") ) {
								productpricelevels.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
						for (int j=0; j<lookupObject.length(); j++) {
							JSONObject lookup = lookupObject.getJSONObject(j);
							if (productpricelevels.get(i).getCURRENCY_ID() != null && productpricelevels.get(i).getCURRENCY_ID() == lookup.getLong("id") ) {
								productpricelevels.get(i).setCURRENCY_DETAIL(lookup.toString());
							}
							if (productpricelevels.get(i).getCURRENCY_ID() != null && productpricelevels.get(i).getPRICELEVEL_ID() == lookup.getLong("id") ) {
								productpricelevels.get(i).setPRICELEVEL_DETAIL(lookup.toString());
							}
						}
					}
				}
				
				rtnAPIResponse = mapper.writeValueAsString(productpricelevels);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
				
			} else if (productpricelevel != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(productpricelevel);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (productpricelevels != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(productpricelevels);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
				
			} else if (jsonProductPriceLevels != null) {
				rtnAPIResponse = jsonProductPriceLevels.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
				
			} else if (jsonProductPriceLevel != null) {
				rtnAPIResponse = jsonProductPriceLevel.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}
		}

		return rtnAPIResponse;
	}

}