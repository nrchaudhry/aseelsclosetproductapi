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

import com.cwiztech.log.apiRequestLog;
import com.cwiztech.product.model.ProductAttributeValue;
import com.cwiztech.product.repository.productAttributeValueRepository;
import com.cwiztech.services.ServiceCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productattributevalue")

public class productAttributeValueController {

	private static final Logger log = LoggerFactory.getLogger(productAttributeValueController.class);

	@Autowired
	private productAttributeValueRepository productattributevaluerepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productattributevalue", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductAttributeValue> productattributevalues = productattributevaluerepository.findActive();
		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productattributevalue/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductAttributeValue> productattributevalues = productattributevaluerepository.findAll();

		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productattributevalue/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductAttributeValue productattributevalue = productattributevaluerepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, productattributevalue, null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productattributevalue/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productattributevalue_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductattributevalues = jsonObj.getJSONArray("productattributevalues");
		for (int i=0; i<jsonproductattributevalues.length(); i++) {
			productattributevalue_IDS.add((Integer) jsonproductattributevalues.get(i));
		}
		List<ProductAttributeValue> productattributevalues = new ArrayList<ProductAttributeValue>();
		if (jsonproductattributevalues.length()>0)
			productattributevalues = productattributevaluerepository.findByIDs(productattributevalue_IDS);

		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productattributevalue/notin/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productattributevalue_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductattributevalues = jsonObj.getJSONArray("productattributevalues");
		for (int i=0; i<jsonproductattributevalues.length(); i++) {
			productattributevalue_IDS.add((Integer) jsonproductattributevalues.get(i));
		}
		List<ProductAttributeValue> productattributevalues = new ArrayList<ProductAttributeValue>();
		if (jsonproductattributevalues.length()>0)
			productattributevalues = productattributevaluerepository.findByNotInIDs(productattributevalue_IDS);

		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productattributevalue", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {

		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productattributevalue/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productattributevalue", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductAttributeValues, JSONObject jsonProductAttributeValue, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductAttributeValue> productattributevalues = new ArrayList<ProductAttributeValue>();
		if (jsonProductAttributeValue != null) {
			jsonProductAttributeValues = new JSONArray();
			jsonProductAttributeValues.put(jsonProductAttributeValue);
		}
		log.info(jsonProductAttributeValues.toString());

		for (int a=0; a<jsonProductAttributeValues.length(); a++) {
			JSONObject jsonObj = jsonProductAttributeValues.getJSONObject(a);
			ProductAttributeValue productattributevalue = new ProductAttributeValue();
			long productattributevalueid = 0;

			if (jsonObj.has("productattributevalue_ID")) {
				productattributevalueid = jsonObj.getLong("productattributevalue_ID");
				if (productattributevalueid != 0) {
					productattributevalue = productattributevaluerepository.findOne(productattributevalueid);

					if (productattributevalue == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductAttributeValue Data!", apiRequest, true), HttpStatus.OK);
				}
			}

			if (productattributevalueid == 0) {
				if (!jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_ID is missing", apiRequest, true), HttpStatus.OK);

				if (!jsonObj.has("productattribute_ID") || jsonObj.isNull("productattribute_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productattribute_ID is missing", apiRequest, true), HttpStatus.OK);
			}

			productattributevalue.setPRODUCT_ID(jsonObj.getLong("product_ID"));
			productattributevalue.setPRODUCTATTRIBUTE_ID(jsonObj.getLong("productattribute_ID"));

			if (jsonObj.has("productattribute_VALUE") && !jsonObj.isNull("productattribute_VALUE"))
				productattributevalue.setPRODUCTATTRIBUTE_VALUE(jsonObj.getString("productattribute_VALUE"));

			if (jsonObj.has("attributevalue_ID") && !jsonObj.isNull("attributevalue_ID"))
				productattributevalue.setATTRIBUTEVALUE_ID(jsonObj.getLong("attributevalue_ID"));

			if (productattributevalueid == 0)
				productattributevalue.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				productattributevalue.setISACTIVE(jsonObj.getString("isactive"));

			productattributevalue.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			productattributevalue.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			productattributevalue.setMODIFIED_WHEN(dateFormat1.format(date));
			productattributevalues.add(productattributevalue);
		}

		for (int a=0; a<productattributevalues.size(); a++) {
			ProductAttributeValue productattributevalue = productattributevalues.get(a);
			productattributevalue = productattributevaluerepository.saveAndFlush(productattributevalue);
			productattributevalues.get(a).setPRODUCTATTRIBUTEVALUE_ID(productattributevalue.getPRODUCTATTRIBUTEVALUE_ID());
		}

		ResponseEntity responseentity;
		if (jsonProductAttributeValue != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productattributevalues.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productattributevalue/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductAttributeValue productattributevalue = productattributevaluerepository.findOne(id);
		productattributevaluerepository.delete(productattributevalue);

		return new ResponseEntity(getAPIResponse(null, productattributevalue , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productattributevalue/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject productattributevalue = new JSONObject();
		productattributevalue.put("id", id);
		productattributevalue.put("isactive", "N");

		return insertupdateAll(null, productattributevalue, apiRequest);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productattributevalue/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductAttributeValue> productattributevalues = ((active == true)
				? productattributevaluerepository.findBySearch("%" + jsonObj.getString("search") + "%")
						: productattributevaluerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, true), HttpStatus.OK);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productattributevalue/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductAttributeValue> productattributevalues= new ArrayList<ProductAttributeValue>();
		JSONObject jsonObj = new JSONObject(data);

		JSONArray searchObject = new JSONArray();
		List<Integer> product_IDS = new ArrayList<Integer>(); 
		List<Integer> productattribute_IDS = new ArrayList<Integer>(); 
		List<Integer> attributevalue_IDS = new ArrayList<Integer>(); 

		product_IDS.add((int) 0);
		productattribute_IDS.add((int) 0);
		attributevalue_IDS.add((int) 0);

		long product_ID = 0, productattribute_ID = 0 , attributevalue_ID = 0;

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

		if (jsonObj.has("attributevalue_ID") && !jsonObj.isNull("attributevalue_ID") && jsonObj.getLong("attributevalue_ID") != 0) {
			attributevalue_ID = jsonObj.getLong("attributevalue_ID");
			attributevalue_IDS.add((int) attributevalue_ID);
		} else if (jsonObj.has("attributevalue") && !jsonObj.isNull("attributevalue") && jsonObj.getLong("attributevalue") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("attributevalue/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("attributevalue/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
			}

			attributevalue_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				attributevalue_IDS.add((int) searchObject.getJSONObject(i).getLong("attributevalue_ID"));
			}
		}

		if (product_ID != 0 || productattribute_ID != 0 || attributevalue_ID != 0) {
			productattributevalues = ((active == true)

					? productattributevaluerepository.findByAdvancedSearch( product_ID, product_IDS, productattribute_ID,productattribute_IDS,attributevalue_ID,attributevalue_IDS)
							: productattributevaluerepository.findAllByAdvancedSearch( product_ID, product_IDS, productattribute_ID,productattribute_IDS,attributevalue_ID,attributevalue_IDS));

		}
		return new ResponseEntity(getAPIResponse(productattributevalues, null , null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}

	String getAPIResponse(List<ProductAttributeValue> productattributevalues, ProductAttributeValue productattributevalue , JSONArray jsonProductAttributeValues, JSONObject jsonProductAttributeValue, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "ProductAttributeValue", message).toString();
		} else {
			if ((productattributevalues != null || productattributevalue != null) && isWithDetail == true) {
				if (productattributevalue != null) {
					productattributevalues = new ArrayList<ProductAttributeValue>();
					productattributevalues.add(productattributevalue);
				}
				if (productattributevalues.size()>0) {
					List<Integer> productList = new ArrayList<Integer>();
					List<Integer> productattributeList = new ArrayList<Integer>();
					List<Integer> attributevalueList = new ArrayList<Integer>();

					for (int i=0; i<productattributevalues.size(); i++) {
						if (productattributevalues.get(i).getPRODUCT_ID() != null) {
							productList.add(Integer.parseInt(productattributevalues.get(i).getPRODUCT_ID().toString()));
						}

						if (productattributevalues.get(i).getPRODUCTATTRIBUTE_ID() != null) {
							productattributeList.add(Integer.parseInt(productattributevalues.get(i).getPRODUCTATTRIBUTE_ID().toString()));
						}

						if (productattributevalues.get(i).getATTRIBUTEVALUE_ID() != null) {
							attributevalueList.add(Integer.parseInt(productattributevalues.get(i).getATTRIBUTEVALUE_ID().toString()));
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

					CompletableFuture<JSONArray> productattributeFuture = CompletableFuture.supplyAsync(() -> {
						if (productattributeList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("productattribute/ids", "{productattributes: "+productattributeList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});

					CompletableFuture<JSONArray> attributevalueFuture = CompletableFuture.supplyAsync(() -> {
						if (attributevalueList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("attributevalue/ids", "{attributevalues: "+attributevalueList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});

					// Wait until all futures complete
					CompletableFuture<Void> allDone =
							CompletableFuture.allOf(productFuture, productattributeFuture, attributevalueFuture);

					// Block until all are done
					allDone.join();

					JSONArray productObject = new JSONArray(productFuture.toString());
					JSONArray productattributeObject = new JSONArray(productattributeFuture.toString());
					JSONArray attributevalueObject = new JSONArray(attributevalueFuture.toString());

					for (int i=0; i<productattributevalues.size(); i++) {
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if (productattributevalues.get(i).getPRODUCT_ID() != null && productattributevalues.get(i).getPRODUCT_ID() == product.getLong("PRODUCT_ID")) {
								productattributevalues.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
						for (int j=0; j<productattributeObject.length(); j++) {
							JSONObject productattribute = productattributeObject.getJSONObject(j);
							if (productattributevalues.get(i).getPRODUCTATTRIBUTE_ID() != null && productattributevalues .get(i).getPRODUCTATTRIBUTE_ID() == productattribute.getLong("getPRODUCTATTRIBUTE_ID")) {
								productattributevalues.get(i).setPRODUCTATTRIBUTE_DETAIL(productattribute.toString());
							}
						}
						for (int j=0; j<attributevalueObject.length(); j++) {
							JSONObject attributevalue = attributevalueObject.getJSONObject(j);

							if (productattributevalues.get(i).getATTRIBUTEVALUE_ID() != null && productattributevalues.get(i).getATTRIBUTEVALUE_ID() == attributevalue.getLong("ATTRIBUTEVALUE_ID")) {
								productattributevalues.get(i).setATTRIBUTEVALUE_DETAIL(attributevalue.toString());
							}
						}
					}
				}

				if (productattributevalue != null)
					rtnAPIResponse = mapper.writeValueAsString(productattributevalues.get(0));
				else	
					rtnAPIResponse = mapper.writeValueAsString(productattributevalues);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (productattributevalue != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(productattributevalue);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (productattributevalues != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(productattributevalues);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonProductAttributeValues != null) {
				rtnAPIResponse = jsonProductAttributeValues.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonProductAttributeValue != null) {
				rtnAPIResponse = jsonProductAttributeValue.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}
		}

		return rtnAPIResponse;
	}
}
