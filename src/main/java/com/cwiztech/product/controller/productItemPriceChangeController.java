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
import com.cwiztech.product.model.ProductItemPriceChange;
import com.cwiztech.product.repository.productItemPriceChangeRepository;
import com.cwiztech.services.ServiceCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productitempricechange")
public class productItemPriceChangeController {
	private static final Logger log = LoggerFactory.getLogger(productItemPriceChangeController.class);
	
	@Autowired
	private productItemPriceChangeRepository productitempricechangerepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;

	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitempricechange", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemPriceChange> productitempricechanges = productitempricechangerepository.findActive();
		return new ResponseEntity(getAPIResponse(productitempricechanges, null , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitempricechange/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemPriceChange> productitempricechanges = productitempricechangerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productitempricechanges, null , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitempricechange/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItemPriceChange productitempricechange = productitempricechangerepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productitempricechange , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitempricechange/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitempricechange_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitempricechanges = jsonObj.getJSONArray("productitempricechanges");
		for (int i=0; i<jsonproductitempricechanges.length(); i++) {
			productitempricechange_IDS.add((Integer) jsonproductitempricechanges.get(i));
		}
		List<ProductItemPriceChange> productitempricechanges = new ArrayList<ProductItemPriceChange>();
		if (jsonproductitempricechanges.length()>0)
			productitempricechanges = productitempricechangerepository.findByIDs(productitempricechange_IDS);
		
		return new ResponseEntity(getAPIResponse(productitempricechanges, null , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitempricechange/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitempricechange_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitempricechanges = jsonObj.getJSONArray("productitempricechanges");
		for (int i=0; i<jsonproductitempricechanges.length(); i++) {
			productitempricechange_IDS.add((Integer) jsonproductitempricechanges.get(i));
		}
		List<ProductItemPriceChange> productitempricechanges = new ArrayList<ProductItemPriceChange>();
		if (jsonproductitempricechanges.length()>0)
			productitempricechanges = productitempricechangerepository.findByNotInIDs(productitempricechange_IDS);
		
		return new ResponseEntity(getAPIResponse(productitempricechanges, null , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitempricechange", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/productitempricechange/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productitempricechange", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductItemPriceChanges, JSONObject jsonProductItemPriceChange, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductItemPriceChange> productitempricechanges = new ArrayList<ProductItemPriceChange>();
		if (jsonProductItemPriceChange != null) {
			jsonProductItemPriceChanges = new JSONArray();
			jsonProductItemPriceChanges.put(jsonProductItemPriceChange);
		}
		log.info(jsonProductItemPriceChanges.toString());
		
		for (int a=0; a<jsonProductItemPriceChanges.length(); a++) {
			JSONObject jsonObj = jsonProductItemPriceChanges.getJSONObject(a);
			ProductItemPriceChange productitempricechange = new ProductItemPriceChange();
			long productitempricechangeid = 0;

			if (jsonObj.has("productitempricechange_ID")) {
				productitempricechangeid = jsonObj.getLong("productitempricechange_ID");
				if (productitempricechangeid != 0) {
					productitempricechange = productitempricechangerepository.findOne(productitempricechangeid);
					
					if (productitempricechange == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductItemPriceChange Data!", apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (productitempricechangeid == 0) {
				if (!jsonObj.has("productitem_ID") || jsonObj.isNull("productitem_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_ID is missing", apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productitem_PURCHASEPRICE") || jsonObj.isNull("productitem_PURCHASEPRICE"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_PURCHASEPRICE is missing", apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
				if (!jsonObj.has("productitem_LASTPURCHASEPRICE") || jsonObj.isNull("productitem_LASTPURCHASEPRICE"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_LASTPURCHASEPRICE is missing", apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			}
			
		if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID"))
			productitempricechange.setPRODUCTITEM_ID(jsonObj.getLong("productitem_ID"));

		if (jsonObj.has("currency_ID") && !jsonObj.isNull("currency_ID"))
			productitempricechange.setCURRENCY_ID(jsonObj.getLong("currency_ID"));
		else
			productitempricechange.setCURRENCY_ID((long) 91);

		if (jsonObj.has("productitem_PURCHASEPRICE") && !jsonObj.isNull("productitem_PURCHASEPRICE"))
			productitempricechange.setPRODUCTITEM_PURCHASEPRICE(jsonObj.getDouble("productitem_PURCHASEPRICE"));

		if (jsonObj.has("productitem_LASTPURCHASEPRICE") && !jsonObj.isNull("productitem_LASTPURCHASEPRICE"))
			productitempricechange.setPRODUCTITEM_LASTPURCHASEPRICE(jsonObj.getDouble("productitem_LASTPURCHASEPRICE"));
		
		if (productitempricechangeid == 0)
			productitempricechange.setISACTIVE("Y");
		else if (jsonObj.has("isactive"))
			productitempricechange.setISACTIVE(jsonObj.getString("isactive"));

			productitempricechange.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productitempricechange.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productitempricechange.setMODIFIED_WHEN(dateFormat1.format(date));
			productitempricechanges.add(productitempricechange);
		}
		
		for (int a=0; a<productitempricechanges.size(); a++) {
			ProductItemPriceChange productitempricechange = productitempricechanges.get(a);
			productitempricechange = productitempricechangerepository.saveAndFlush(productitempricechange);
			productitempricechanges.get(a).setPRODUCTITEMPRICECHANGE_ID(productitempricechange.getPRODUCTITEMPRICECHANGE_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonProductItemPriceChange != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productitempricechanges.get(0) , null, null, null, apiRequest, true,true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productitempricechanges, null , null, null, null, apiRequest, true,true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitempricechange/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItemPriceChange productitempricechange = productitempricechangerepository.findOne(id);
		productitempricechangerepository.delete(productitempricechange);
		
		return new ResponseEntity(getAPIResponse(null, productitempricechange , null, null, null, apiRequest, true,true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitempricechange/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject productitempricechange = new JSONObject();
		productitempricechange.put("id", id);
		productitempricechange.put("isactive", "N");
		
		return insertupdateAll(null, productitempricechange, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productitempricechange/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductItemPriceChange> productitempricechanges = ((active == true)
				? productitempricechangerepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: productitempricechangerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(productitempricechanges, null , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productitempricechange/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
	
		List<ProductItemPriceChange> productitempricechanges = new ArrayList<ProductItemPriceChange>();
		JSONObject jsonObj = new JSONObject(data);
		JSONArray searchObject = new JSONArray();
        List<Integer> currency_IDS = new ArrayList<Integer>(); 
        List<Integer> productitem_IDS = new ArrayList<Integer>(); 

        currency_IDS.add((int) 0);
        productitem_IDS.add((int) 0);
		long currency_ID = 0, productitem_ID=0;
		
		
		boolean isWithDetail = true;
        if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
            isWithDetail = jsonObj.getBoolean("iswithdetail");
        }
        jsonObj.put("iswithdetail", false);
		
        if (jsonObj.has("currency_ID") && !jsonObj.isNull("currency_ID") && jsonObj.getLong("currency_ID") != 0) {
            currency_ID = jsonObj.getLong("currency_ID");
            currency_IDS.add((int) currency_ID);
        } else if (jsonObj.has("currency") && !jsonObj.isNull("currency") && jsonObj.getLong("currency") != 0) {
            if (active == true) {
                searchObject = new JSONArray(ServiceCall.POST("currency/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
            } else {
                searchObject = new JSONArray(ServiceCall.POST("currency/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
            }

            currency_ID = searchObject.length();
            for (int i=0; i<searchObject.length(); i++) {
                currency_IDS.add((int) searchObject.getJSONObject(i).getLong("currency_ID"));
            }
        }
		
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
                productitem_IDS.add((int) searchObject.getJSONObject(i).getLong("productitem_ID"));
            }
        }

        if(currency_ID != 0 || productitem_ID != 0){
        	List<ProductItemPriceChange> productitempricechange = ((active == true)
		        ? productitempricechangerepository.findByAdvancedSearch(currency_ID,currency_IDS,productitem_ID,productitem_IDS)
		        : productitempricechangerepository.findAllByAdvancedSearch(currency_ID,currency_IDS,productitem_ID,productitem_IDS));
       	
        }
		return new ResponseEntity(getAPIResponse(productitempricechanges, null , null, null, null, apiRequest, false,isWithDetail).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItemPriceChange.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductItemPriceChange> productitempricechanges, ProductItemPriceChange productitempricechange , JSONArray jsonProductItemPriceChanges, JSONObject jsonProductItemPriceChange, String message, APIRequestDataLog apiRequest, boolean isTableLog,boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productitempricechangeID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductItemPriceChange", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productitempricechange != null && isWithDetail == true) {
				JSONObject productitem = new JSONObject(ServiceCall.GET("productitem/"+productitempricechange.getPRODUCTITEM_ID(), apiRequest.getREQUEST_OUTPUT(), true));
				productitempricechange.setPRODUCTITEM_DETAIL(productitem.toString());
				
				if(productitempricechange.getCURRENCY_ID() != null) {
				JSONObject currency = new JSONObject(ServiceCall.GET("lookup/"+productitempricechange.getCURRENCY_ID(), apiRequest.getREQUEST_OUTPUT(), true));
				productitempricechange.setCURRENCY_DETAIL(currency.toString());
			}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitempricechange));
				productitempricechangeID = productitempricechange.getPRODUCTITEMPRICECHANGE_ID();
			} else if(productitempricechanges != null && isWithDetail == true){
				if (productitempricechanges.size()>0) {
					List<Integer> productitemList = new ArrayList<Integer>();
					List<Integer> currencyList = new ArrayList<Integer>();
					for (int i=0; i<productitempricechanges.size(); i++) {
						if(productitempricechanges.get(i).getPRODUCTITEM_ID() != null)
						   productitemList.add(Integer.parseInt(productitempricechanges.get(i).getPRODUCTITEM_ID().toString()));
						if(productitempricechanges.get(i).getCURRENCY_ID() != null)
						   currencyList.add(Integer.parseInt(productitempricechanges.get(i).getCURRENCY_ID().toString()));
					}
					JSONArray productitemObject = new JSONArray(ServiceCall.POST("productitem/ids", "{items: "+productitemList+"}", apiRequest.getREQUEST_OUTPUT(), true));
					JSONArray currencyObject = new JSONArray(ServiceCall.POST("lookup/ids", "{lookups: "+currencyList+"}", apiRequest.getREQUEST_OUTPUT(), true));		
					for (int i=0; i<productitempricechanges.size(); i++) {
						for (int j=0; j<productitemObject.length(); j++) {
							JSONObject productitem = productitemObject.getJSONObject(j);
							if(productitempricechanges.get(i).getPRODUCTITEM_ID() == productitem.getLong("productitem_ID") ) {
								productitempricechanges.get(i).setPRODUCTITEM_DETAIL(productitem.toString());
							}
						}
						for (int j=0; j<currencyObject.length(); j++) {
							JSONObject currency = currencyObject.getJSONObject(j);
							if(productitempricechanges.get(i).getCURRENCY_ID() != null && productitempricechanges.get(i).getCURRENCY_ID() == currency.getLong("id") ) {
								productitempricechanges.get(i).setCURRENCY_DETAIL(currency.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitempricechanges));
			}else if (jsonProductItemPriceChanges != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemPriceChanges.toString());
			
			} else if (jsonProductItemPriceChange != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemPriceChange.toString());
			}
			else if (jsonProductItemPriceChanges != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemPriceChanges.toString());
			
			} else if (jsonProductItemPriceChange != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemPriceChange.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productitempricechangeID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		return apiRequest;
	}
}
