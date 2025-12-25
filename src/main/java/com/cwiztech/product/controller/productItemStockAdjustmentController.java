package com.cwiztech.product.controller;

import java.io.IOException;
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

import com.cwiztech.log.apiRequestLog;
import com.cwiztech.product.model.ProductItemStockAdjustment;
import com.cwiztech.product.repository.productItemStockAdjustmentRepository;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;

@RestController
@CrossOrigin
@RequestMapping("/ProductItemStockAdjustment")
public class productItemStockAdjustmentController {                                                                        
	private static final Logger log = LoggerFactory.getLogger(productItemStockAdjustmentController.class);

	@Autowired
	private productItemStockAdjustmentRepository productitemstockadjustmentrepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/ProductItemStockAdjustment", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductItemStockAdjustment> productitemstockadjustments = productitemstockadjustmentrepository .findActive();

		return new ResponseEntity(getAPIResponse(productitemstockadjustments, null, null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/ProductItemStockAdjustment/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductItemStockAdjustment> productitemstockadjustments = productitemstockadjustmentrepository.findAll();

		return new ResponseEntity(getAPIResponse(productitemstockadjustments, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/ProductItemStockAdjustment/" + id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductItemStockAdjustment productitemstockadjustment = productitemstockadjustmentrepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, productitemstockadjustment, null, null, null, apiRequest, true), HttpStatus.OK);
	}

	// will give id's in body in form of array and it'll show data of that id's
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {

		JSONObject apiRequest = AccessToken.checkToken("POST", "/ProductItemStockAdjustment/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<Integer> ProductItemStockAdjustment_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonProductItemStockAdjustments = jsonObj.getJSONArray("ProductItemStockAdjustments");
		for (int i=0; i<jsonProductItemStockAdjustments.length(); i++) {
			ProductItemStockAdjustment_IDS.add((Integer) jsonProductItemStockAdjustments.get(i));
		}

		List<ProductItemStockAdjustment> ProductItemStockAdjustments = new ArrayList<ProductItemStockAdjustment>();   
		if (jsonProductItemStockAdjustments.length()>0)
			ProductItemStockAdjustments = productitemstockadjustmentrepository.findByIDs(ProductItemStockAdjustment_IDS);

		return new ResponseEntity(getAPIResponse(ProductItemStockAdjustments, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/ProductItemStockAdjustment", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( method = RequestMethod.PUT)
	public ResponseEntity updateAll(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/ProductItemStockAdjustment", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {

		JSONObject apiRequest = AccessToken.checkToken("PUT", "/ProductItemStockAdjustment/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("ProductItemStockAdjustment_ID", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductItemStockAdjustments, JSONObject jsonProductItemStockAdjustment, JSONObject apiRequest) throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductItemStockAdjustment> ProductItemStockAdjustments = new ArrayList<ProductItemStockAdjustment>();
		if (jsonProductItemStockAdjustment!= null) {
			jsonProductItemStockAdjustments = new JSONArray();
			jsonProductItemStockAdjustments.put(jsonProductItemStockAdjustment);
		}

		for (int i=0; i<jsonProductItemStockAdjustment.length(); i++) {
			JSONObject jsonObj = jsonProductItemStockAdjustments.getJSONObject(i);
			ProductItemStockAdjustment ProductItemStockAdjustment = new  ProductItemStockAdjustment();
			long id=0; 

			if (jsonObj.has("productitemstockadjustment_ID")) {
				id = jsonObj.getLong("productitemstockadjustment_ID");
				if (id!=0) {
					ProductItemStockAdjustment = productitemstockadjustmentrepository.findOne(id);
				}
			}

			if (id == 0) {
				if (!jsonObj.has("productitemstockadjustment_ID") && jsonObj.isNull("productitemstockadjustment_ID")) {
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitemstockadjustment_ID are missing", apiRequest, true), HttpStatus.OK);
				}
			}

			if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID"))
				ProductItemStockAdjustment.setPRODUCTITEM_ID(jsonObj.getLong("productitem_ID"));

			if (jsonObj.has("quantity_ADJUST") && !jsonObj.isNull("quantity_ADJUST"))
				ProductItemStockAdjustment.setQUANTITY_ADJUST(jsonObj.getDouble("quantity_ADJUST"));

			if (jsonObj.has("purchase_PRICE") && !jsonObj.isNull("purchase_PRICE"))
				ProductItemStockAdjustment.setPURCHASE_PRICE(jsonObj.getDouble("purchase_PRICE"));

			if (id == 0)
				ProductItemStockAdjustment.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				ProductItemStockAdjustment.setISACTIVE(jsonObj.getString("isactive"));

			ProductItemStockAdjustment.setMODIFIED_BY(apiRequest.getString("request_ID"));
			ProductItemStockAdjustment.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			ProductItemStockAdjustment.setMODIFIED_WHEN(dateFormat1.format(date));

			ProductItemStockAdjustment = productitemstockadjustmentrepository.saveAndFlush(ProductItemStockAdjustment);
			ProductItemStockAdjustments.add(ProductItemStockAdjustment);

		}

		ResponseEntity responseentity;
		if (jsonProductItemStockAdjustment != null)
			responseentity = new ResponseEntity(getAPIResponse(null, ProductItemStockAdjustments.get(0), null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(ProductItemStockAdjustments, null, null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}

	// we delete the id that we enter  in api 
	// first we find & then we delete
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/ProductItemStockAdjustment/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		ProductItemStockAdjustment ProductItemStockAdjustment = productitemstockadjustmentrepository.findOne(id);
		productitemstockadjustmentrepository.delete(ProductItemStockAdjustment);

		return new ResponseEntity(getAPIResponse(null, ProductItemStockAdjustment, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
	}

	//update the list  to remove the given id and make it non-active
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JSONException, ParseException, InterruptedException, ExecutionException, ApiException, InterruptedException, IOException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/ProductItemStockAdjustment/remove/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject ProductItemStockAdjustment = new JSONObject();
		ProductItemStockAdjustment.put("ProductItemStockAdjustment_ID", id);
		ProductItemStockAdjustment.put("isactive", "N");

		return insertupdateAll(null, ProductItemStockAdjustment, apiRequest);
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
	public ResponseEntity BySearch(String data, boolean active, String headToken, String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException, ExecutionException, InterruptedException, ExecutionException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/ProductItemStockAdjustment/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductItemStockAdjustment> ProductItemStockAdjustments = ((active == true)
				? productitemstockadjustmentrepository.findBySearch("%" + jsonObj.getString("search") + "%")
						: productitemstockadjustmentrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		return new ResponseEntity(getAPIResponse(ProductItemStockAdjustments, null, null, null, null, apiRequest, true).toString(), HttpStatus.OK);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/ProductItemStockAdjustment/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductItemStockAdjustment> ProductItemStockAdjustments = new ArrayList<ProductItemStockAdjustment>();
		JSONObject jsonObj = new JSONObject(data);
		JSONArray searchObject = new JSONArray();

		boolean isWithDetail = true;
		if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
			isWithDetail = jsonObj.getBoolean("iswithdetail");
		}
		jsonObj.put("iswithdetail", false);

		long productitem_ID=0;
		List<Integer> productitem_IDS = new ArrayList<Integer>(); 

		productitem_IDS.add((int) 0);

		if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID") && jsonObj.getLong("productitem_ID") != 0) {
			productitem_ID = jsonObj.getLong("productitem_ID");
			productitem_IDS.add((int) productitem_ID);

		} else if (jsonObj.has("productitem") && !jsonObj.isNull("productitem") && jsonObj.getLong("productitem") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("productitem/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("productitem/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
			}

			productitem_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				productitem_IDS.add((int) searchObject.getJSONObject(i).getLong("promotion_ID"));
			}
		}

		if (productitem_ID != 0) {
			ProductItemStockAdjustments = ((active == true)
					? productitemstockadjustmentrepository.findByAdvancedSearch(productitem_ID, productitem_IDS)
							: productitemstockadjustmentrepository.findAllByAdvancedSearch(productitem_ID, productitem_IDS));
		}
		
		return new ResponseEntity(getAPIResponse(ProductItemStockAdjustments, null, null, null, null, apiRequest, isWithDetail).toString(), HttpStatus.OK);
	}

	//getAPI response Function
	String getAPIResponse(List<ProductItemStockAdjustment> productitemstockadjustments, ProductItemStockAdjustment productitemstockadjustment, JSONArray Jsonproductitemstockadjustments, JSONObject Jsonproductitemstockadjustment, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException, InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "ProductItemStockAdjustment", message).toString();
		} else  {
			if (productitemstockadjustment != null && isWithDetail == true) {
				if (isWithDetail == true) {
					if (productitemstockadjustment != null)
						productitemstockadjustments.add(productitemstockadjustment);

					if (productitemstockadjustments.size()>0) {
						List<Integer> productitemList = new ArrayList<Integer>();

						for (int i=0; i<productitemstockadjustments.size(); i++) {
							if (productitemstockadjustments.get(i).getPRODUCTITEM_ID() != null) {
								productitemList.add(Integer.parseInt(productitemstockadjustments.get(i).getPRODUCTITEM_ID().toString()));
							}
						}

						CompletableFuture<JSONObject> productitemFuture = CompletableFuture.supplyAsync(() -> {
							if (productitemstockadjustment.getPRODUCTITEM_ID() == null) {
								return new JSONObject();
							}

							try {
								return new JSONObject(ServiceCall.GET("productitem/"+productitemstockadjustment.getPRODUCTITEM_ID(), apiRequest.getString("access_TOKEN"), true));
							} catch (JSONException | JsonProcessingException | ParseException e) {
								e.printStackTrace();
								return new JSONObject();
							}
						});

						// Wait until all futures complete
						CompletableFuture<Void> allDone =
								CompletableFuture.allOf(productitemFuture);

						// Block until all are done
						allDone.join();

						productitemstockadjustment.setPRODUCTITEM_DETAIL(productitemFuture.get().toString());


						JSONObject personObject = productitemFuture.get();

						for (int  i=0; i<productitemstockadjustments.size(); i++) {
							for (int j=0; j<personObject.length(); j++) {
								JSONObject person = personObject.getJSONObject(j);
								if (productitemstockadjustments.get(i).getPRODUCTITEM_ID() != null && productitemstockadjustments.get(i).getPRODUCTITEM_ID() == person.getLong("person_ID") ) {
									productitemstockadjustments.get(i).setPRODUCTITEM_DETAIL(person.toString());
								}
							}


							if (productitemstockadjustments != null && isWithDetail == true) {
								if (productitemstockadjustments.size()>0) {
								}

								rtnAPIResponse = mapper.writeValueAsString(productitemstockadjustments);
								apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

							} else if (productitemstockadjustment != null && isWithDetail == false) {
								rtnAPIResponse = mapper.writeValueAsString(productitemstockadjustment);
								apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

							} else if (productitemstockadjustments != null && isWithDetail == false) {
								rtnAPIResponse = mapper.writeValueAsString(productitemstockadjustments);
								apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

							} else if (Jsonproductitemstockadjustments != null) {
								rtnAPIResponse = Jsonproductitemstockadjustments.toString();
								apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

							} else if (Jsonproductitemstockadjustment != null) {
								rtnAPIResponse = mapper.writeValueAsString(Jsonproductitemstockadjustment.toString());
								apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
							}

							return rtnAPIResponse;
						}
					}
				}
			}
		}
	}
}





