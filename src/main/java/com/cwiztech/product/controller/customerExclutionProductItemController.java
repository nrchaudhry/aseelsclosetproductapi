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

import com.cwiztech.services.ServiceCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;
import com.cwiztech.log.apiRequestLog;
import com.cwiztech.product.model.CustomerExclutionProductItem;
import com.cwiztech.product.repository.customerExclutionProductItemRepository;

@RestController
@CrossOrigin
@RequestMapping("/customerexclutionproductitem")
public class customerExclutionProductItemController {
	private static final Logger log = LoggerFactory.getLogger(customerExclutionProductItemController.class);

	@Autowired
	private customerExclutionProductItemRepository customerexclutionproductitemrepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/customerexclutionproductitem", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<CustomerExclutionProductItem> customerexclutionproductitems = customerexclutionproductitemrepository.findActive();
		return new ResponseEntity(getAPIResponse(customerexclutionproductitems, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/customerexclutionproductitem/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<CustomerExclutionProductItem> customerexclutionproductitems = customerexclutionproductitemrepository.findAll();
		
		return new ResponseEntity(getAPIResponse(customerexclutionproductitems, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/customerexclutionproductitem/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		CustomerExclutionProductItem customerexclutionproductitem = customerexclutionproductitemrepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, customerexclutionproductitem , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/customerexclutionproductitem/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<Integer> customerexclutionproductitem_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsoncustomerexclutionproductitems = jsonObj.getJSONArray("customerexclutionproductitems");
		for (int i=0; i<jsoncustomerexclutionproductitems.length(); i++) {
			customerexclutionproductitem_IDS.add((Integer) jsoncustomerexclutionproductitems.get(i));
		}
		List<CustomerExclutionProductItem> customerexclutionproductitems = new ArrayList<CustomerExclutionProductItem>();
		if (jsoncustomerexclutionproductitems.length()>0)
			customerexclutionproductitems = customerexclutionproductitemrepository.findByIDs(customerexclutionproductitem_IDS);
		
		return new ResponseEntity(getAPIResponse(customerexclutionproductitems, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/customerexclutionproductitem/notin/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<Integer> customerexclutionproductitem_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsoncustomerexclutionproductitems = jsonObj.getJSONArray("customerexclutionproductitems");
		for (int i=0; i<jsoncustomerexclutionproductitems.length(); i++) {
			customerexclutionproductitem_IDS.add((Integer) jsoncustomerexclutionproductitems.get(i));
		}
		List<CustomerExclutionProductItem> customerexclutionproductitems = new ArrayList<CustomerExclutionProductItem>();
		if (jsoncustomerexclutionproductitems.length()>0)
			customerexclutionproductitems = customerexclutionproductitemrepository.findByNotInIDs(customerexclutionproductitem_IDS);
		
		return new ResponseEntity(getAPIResponse(customerexclutionproductitems, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/customerexclutionproductitem", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/customerexclutionproductitem/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);
		
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/customerexclutionproductitem", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

			
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonCustomerExclutionProductItems, JSONObject jsonCustomerExclutionProductItem, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<CustomerExclutionProductItem> customerexclutionproductitems = new ArrayList<CustomerExclutionProductItem>();
		if (jsonCustomerExclutionProductItem != null) {
			jsonCustomerExclutionProductItems = new JSONArray();
			jsonCustomerExclutionProductItems.put(jsonCustomerExclutionProductItem);
		}
		log.info(jsonCustomerExclutionProductItems.toString());
		
		for (int a=0; a<jsonCustomerExclutionProductItems.length(); a++) {
			JSONObject jsonObj = jsonCustomerExclutionProductItems.getJSONObject(a);
			CustomerExclutionProductItem customerexclutionproductitem = new CustomerExclutionProductItem();
			long customerexclutionproductitemid = 0;

			if (jsonObj.has("customerexclutionproductitem_ID")) {
				customerexclutionproductitemid = jsonObj.getLong("customerexclutionproductitem_ID");
				if (customerexclutionproductitemid != 0) {
					customerexclutionproductitem = customerexclutionproductitemrepository.findOne(customerexclutionproductitemid);
					
					if (customerexclutionproductitem == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid CustomerExclutionProductItem Data!", apiRequest, true), HttpStatus.BAD_REQUEST);
				}
			}

				
			if (customerexclutionproductitemid == 0) {
				if (!jsonObj.has("customer_ID") || jsonObj.isNull("customer_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "customer_ID is missing", apiRequest, true), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productitem_ID") || jsonObj.isNull("productitem_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_ID is missing", apiRequest, true), HttpStatus.BAD_REQUEST);
				
			}
			if (jsonObj.has("customer_ID") && !jsonObj.isNull("customer_ID")) 			
			customerexclutionproductitem.setCUSTOMER_ID(jsonObj.getLong("customer_ID"));

		    if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID")) 			
			customerexclutionproductitem.setPRODUCTITEM_ID(jsonObj.getLong("productitem_ID"));

		    if (customerexclutionproductitemid == 0)
				customerexclutionproductitem.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				customerexclutionproductitem.setISACTIVE(jsonObj.getString("isactive"));

			customerexclutionproductitem.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			customerexclutionproductitem.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			customerexclutionproductitem.setMODIFIED_WHEN(dateFormat1.format(date));
			customerexclutionproductitems.add(customerexclutionproductitem);
		}
		
		for (int a=0; a<customerexclutionproductitems.size(); a++) {
			CustomerExclutionProductItem customerexclutionproductitem = customerexclutionproductitems.get(a);
			customerexclutionproductitem = customerexclutionproductitemrepository.saveAndFlush(customerexclutionproductitem);
			customerexclutionproductitems.get(a).setCUSTOMEREXCLUSIONPRODUCTITEM_ID(customerexclutionproductitem.getCUSTOMEREXCLUSIONPRODUCTITEM_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonCustomerExclutionProductItem != null)
			responseentity = new ResponseEntity(getAPIResponse(null, customerexclutionproductitems.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(customerexclutionproductitems, null , null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/customerexclutionproductitem/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		CustomerExclutionProductItem customerexclutionproductitem = customerexclutionproductitemrepository.findOne(id);
		customerexclutionproductitemrepository.delete(customerexclutionproductitem);
		
		return new ResponseEntity(getAPIResponse(null, customerexclutionproductitem , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/customerexclutionproductitem/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);
		
		JSONObject customerexclutionproductitem = new JSONObject();
		customerexclutionproductitem.put("id", id);
		customerexclutionproductitem.put("isactive", "N");
		
		return insertupdateAll(null, customerexclutionproductitem, apiRequest);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/customerexclutionproductitem/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		
		
		List<CustomerExclutionProductItem> customerexclutionproductitems = ((active == true)
				? customerexclutionproductitemrepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: customerexclutionproductitemrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(customerexclutionproductitems, null , null, null, null, apiRequest, true), HttpStatus.OK);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/customerexclutionproductitem/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);
		
		List<CustomerExclutionProductItem> customerexclutionproductitems = new ArrayList<CustomerExclutionProductItem>();
		JSONObject jsonObj = new JSONObject(data);
		
		   JSONArray searchObject = new JSONArray();
	        List<Integer> customer_IDS = new ArrayList<Integer>(); 
	        List<Integer> productitem_IDS = new ArrayList<Integer>(); 

	        customer_IDS.add((int) 0);
	        productitem_IDS.add((int) 0);
	        
		long customer_ID = 0 , productitem_ID = 0;
		
        boolean isWithDetail = true;
        if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
            isWithDetail = jsonObj.getBoolean("iswithdetail");
        }
        jsonObj.put("iswithdetail", false);
		
        if (jsonObj.has("customer_ID") && !jsonObj.isNull("customer_ID") && jsonObj.getLong("customer_ID") != 0) {
            customer_ID = jsonObj.getLong("customer_ID");
            customer_IDS.add((int) customer_ID);
        } else if (jsonObj.has("customer") && !jsonObj.isNull("customer") && jsonObj.getLong("customer") != 0) {
            if (active == true) {
                searchObject = new JSONArray(ServiceCall.POST("customer/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
            } else {
                searchObject = new JSONArray(ServiceCall.POST("customer/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
            }

            customer_ID = searchObject.length();
            for (int i=0; i<searchObject.length(); i++) {
                customer_IDS.add((int) searchObject.getJSONObject(i).getLong("customer_ID"));
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
		
		if (customer_ID != 0 || customer_ID != 0) {
		 customerexclutionproductitems = ((active == true)
				? customerexclutionproductitemrepository.findByAdvancedSearch(customer_ID, customer_IDS, productitem_ID, productitem_IDS)
				: customerexclutionproductitemrepository.findAllByAdvancedSearch(customer_ID, customer_IDS, productitem_ID, productitem_IDS));
		}
		return new ResponseEntity(getAPIResponse(customerexclutionproductitems, null, null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}
	
	String getAPIResponse(List<CustomerExclutionProductItem> customerexclutionproductitems, CustomerExclutionProductItem customerexclutionproductitem , JSONArray jsonCustomerExclutionProductItems, JSONObject jsonCustomerExclutionProductItem, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";
		
		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "CustomerExclutionProductItem", message).toString();
		} else {
			if (customerexclutionproductitem != null && isWithDetail == true) {
				JSONObject customer = new JSONObject(ServiceCall.GET("customer/"+customerexclutionproductitem.getCUSTOMER_ID(), apiRequest.getString("access_TOKEN"), false));
				customerexclutionproductitem.setCUSTOMER_DETAIL(customer.toString());
				JSONObject productitem = new JSONObject(ServiceCall.GET("productitem/"+customerexclutionproductitem.getPRODUCTITEM_ID(), apiRequest.getString("access_TOKEN"), false));
				customerexclutionproductitem.setPRODUCTITEM_DETAIL(productitem.toString());
				rtnAPIResponse = mapper.writeValueAsString(customer);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (customerexclutionproductitems != null && isWithDetail == true) {
				if (customerexclutionproductitems.size()>0) {
					List<Integer> customerList = new ArrayList<Integer>();
					for (int i=0; i<customerexclutionproductitems.size(); i++) {
						customerList.add(Integer.parseInt(customerexclutionproductitems.get(i).getCUSTOMER_ID().toString()));
					}
					JSONArray logisticsObject = new JSONArray(ServiceCall.POST("customer/ids", "{customers: "+customerList+"}", apiRequest.getString("access_TOKEN"), false));
					
					List<Integer> productitemList = new ArrayList<Integer>();
					for (int i=0; i<customerexclutionproductitems.size(); i++) {
						productitemList.add(Integer.parseInt(customerexclutionproductitems.get(i).getPRODUCTITEM_ID().toString()));
					}
					JSONArray productitemObject = new JSONArray(ServiceCall.POST("productitem/ids", "{items: "+productitemList+"}", apiRequest.getString("access_TOKEN"), false));
					
					for (int i=0; i<customerexclutionproductitems.size(); i++) {
						for (int j=0; j<logisticsObject.length(); j++) {
							JSONObject customer = logisticsObject.getJSONObject(j);
							if (customerexclutionproductitems.get(i).getCUSTOMER_ID() == customer.getLong("customer_ID") ) {
								customerexclutionproductitems.get(i).setCUSTOMER_DETAIL(customer.toString());
							}
						}
						for (int j=0; j<productitemObject.length(); j++) {
							JSONObject productitem = productitemObject.getJSONObject(j);
							if (customerexclutionproductitems.get(i).getPRODUCTITEM_ID() == productitem.getLong("productitem_ID") ) {
								customerexclutionproductitems.get(i).setPRODUCTITEM_DETAIL(productitem.toString());
							}
						}
					}
				}
				rtnAPIResponse = mapper.writeValueAsString(customerexclutionproductitems);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonCustomerExclutionProductItems != null) {
				rtnAPIResponse = jsonCustomerExclutionProductItems.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			
			} else if (jsonCustomerExclutionProductItem != null) {
				rtnAPIResponse = jsonCustomerExclutionProductItem.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}
		}
		
		return rtnAPIResponse;
	}
}
