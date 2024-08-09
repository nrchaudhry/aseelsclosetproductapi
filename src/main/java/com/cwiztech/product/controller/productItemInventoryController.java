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
import com.cwiztech.product.model.ProductItemInventory;
import com.cwiztech.product.repository.productItemInventoryRepository;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/productiteminventory")
public class productItemInventoryController {

	private static final Logger log = LoggerFactory.getLogger(productItemInventoryController.class);

	@Autowired
	private productItemInventoryRepository productiteminventoryrepository;

	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;

	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productiteminventory", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemInventory> productiteminventories = productiteminventoryrepository.findActive();
		return new ResponseEntity(getAPIResponse(productiteminventories, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productiteminventory/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemInventory> productiteminventories = productiteminventoryrepository.findAll();

		return new ResponseEntity(getAPIResponse(productiteminventories, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productiteminventory/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItemInventory productiteminventory = productiteminventoryrepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, productiteminventory , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productiteminventory/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productiteminventory_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductiteminventories = jsonObj.getJSONArray("productiteminventories");
		for (int i=0; i<jsonproductiteminventories.length(); i++) {
			productiteminventory_IDS.add((Integer) jsonproductiteminventories.get(i));
		}
		List<ProductItemInventory> productiteminventories = new ArrayList<ProductItemInventory>();
		if (jsonproductiteminventories.length()>0)
			productiteminventories = productiteminventoryrepository.findByIDs(productiteminventory_IDS);

		return new ResponseEntity(getAPIResponse(productiteminventories, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productiteminventory/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productiteminventory_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductiteminventories = jsonObj.getJSONArray("productiteminventories");
		for (int i=0; i<jsonproductiteminventories.length(); i++) {
			productiteminventory_IDS.add((Integer) jsonproductiteminventories.get(i));
		}
		List<ProductItemInventory> productiteminventories = new ArrayList<ProductItemInventory>();
		if (jsonproductiteminventories.length()>0)
			productiteminventories = productiteminventoryrepository.findByNotInIDs(productiteminventory_IDS);

		return new ResponseEntity(getAPIResponse(productiteminventories, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productiteminventory", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {

		APIRequestDataLog apiRequest = checkToken("PUT", "/productiteminventory/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productiteminventory", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductItemInventories, JSONObject jsonProductItemInventory, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductItemInventory> productiteminventories = new ArrayList<ProductItemInventory>();
		if (jsonProductItemInventory != null) {
			jsonProductItemInventories = new JSONArray();
			jsonProductItemInventories.put(jsonProductItemInventory);
		}
		log.info(jsonProductItemInventories.toString());

		for (int a=0; a<jsonProductItemInventories.length(); a++) {
			JSONObject jsonObj = jsonProductItemInventories.getJSONObject(a);
			ProductItemInventory productiteminventory = new ProductItemInventory();
			long productiteminventoryid = 0;

			if (jsonObj.has("productiteminventory_ID")) {
				productiteminventoryid = jsonObj.getLong("productiteminventory_ID");
				if (productiteminventoryid != 0) {
					productiteminventory = productiteminventoryrepository.findOne(productiteminventoryid);

					if (productiteminventory == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductItemInventory Data!", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}

			if (productiteminventoryid == 0) {
				if (!jsonObj.has("productitem_ID") || jsonObj.isNull("productitem_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

				if ((!jsonObj.has("productlocation_ID") || jsonObj.isNull("productlocation_ID")) && (!jsonObj.has("productlocation_CODE") || jsonObj.isNull("productlocation_CODE"))) 
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productlocation_ID/productlocation_CODE is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			}
			if (jsonObj.has("productitem_ID")  && !jsonObj.isNull("productitem_ID"))
				productiteminventory.setPRODUCTITEM_ID(jsonObj.getLong("productitem_ID"));

			if (jsonObj.has("productlocation_ID") && !jsonObj.isNull("productlocation_ID"))
				productiteminventory.setPRODUCTLOCATION_ID(jsonObj.getLong("productlocation_ID"));

			if (jsonObj.has("quantity_ONHAND") && !jsonObj.isNull("quantity_ONHAND"))
				productiteminventory.setQUANTITY_ONHAND(jsonObj.getDouble("quantity_ONHAND"));
			else if (productiteminventoryid == 0)
				productiteminventory.setQUANTITY_ONHAND(0.0);

			if (jsonObj.has("quantity_ONORDER") && !jsonObj.isNull("quantity_ONORDER"))
				productiteminventory.setQUANTITY_ONORDER(jsonObj.getLong("quantity_ONORDER"));
			else if (productiteminventoryid == 0)
				productiteminventory.setQUANTITY_ONORDER((long) 0);

			if (jsonObj.has("quantity_COMMITTED") && !jsonObj.isNull("quantity_COMMITTED"))
				productiteminventory.setQUANTITY_COMMITTED(jsonObj.getLong("quantity_COMMITTED"));
			else if (productiteminventoryid == 0)
				productiteminventory.setQUANTITY_COMMITTED((long) 0);

			if (jsonObj.has("quantity_AVAILABLE") && !jsonObj.isNull("quantity_AVAILABLE"))
				productiteminventory.setQUANTITY_AVAILABLE(jsonObj.getDouble("quantity_AVAILABLE"));
			else if (productiteminventoryid == 0)
				productiteminventory.setQUANTITY_AVAILABLE(productiteminventory.getQUANTITY_ONHAND());

			if (jsonObj.has("quantity_BACKORDERED") && !jsonObj.isNull("quantity_BACKORDERED"))
				productiteminventory.setQUANTITY_BACKORDERED(jsonObj.getLong("quantity_BACKORDERED"));
			else if (productiteminventoryid == 0)
				productiteminventory.setQUANTITY_BACKORDERED((long) 0);

			if (jsonObj.has("quantity_INTRANSIT") && !jsonObj.isNull("quantity_INTRANSIT"))
				productiteminventory.setQUANTITY_INTRANSIT(jsonObj.getLong("quantity_INTRANSIT"));
			else if (productiteminventoryid == 0)
				productiteminventory.setQUANTITY_INTRANSIT((long) 0);

			if (jsonObj.has("quantityexternal_INTRANSIT") && !jsonObj.isNull("quantityexternal_INTRANSIT"))
				productiteminventory.setQUANTITYEXTERNAL_INTRANSIT(jsonObj.getLong("quantityexternal_INTRANSIT"));
			else if (productiteminventoryid == 0)
				productiteminventory.setQUANTITYEXTERNAL_INTRANSIT((long) 0);

			if (jsonObj.has("quantitybaseunit_ONHAND") && !jsonObj.isNull("quantitybaseunit_ONHAND"))
				productiteminventory.setQUANTITYBASEUNIT_ONHAND(jsonObj.getDouble("quantitybaseunit_ONHAND"));
			else if (productiteminventoryid == 0)
				productiteminventory.setQUANTITYBASEUNIT_ONHAND(0.0);

			if (jsonObj.has("quantitybaseunit_AVAILABLE") && !jsonObj.isNull("quantitybaseunit_AVAILABLE"))
				productiteminventory.setQUANTITYBASEUNIT_AVAILABLE(jsonObj.getDouble("quantitybaseunit_AVAILABLE"));
			else if (productiteminventoryid == 0)
				productiteminventory.setQUANTITYBASEUNIT_AVAILABLE(0.0);

			if (jsonObj.has("value") && !jsonObj.isNull("value"))
				productiteminventory.setVALUE(jsonObj.getDouble("value"));

			if (jsonObj.has("average_COST") && !jsonObj.isNull("average_COST"))
				productiteminventory.setAVERAGE_COST(jsonObj.getDouble("average_COST"));
			else if (productiteminventoryid == 0)
				productiteminventory.setAVERAGE_COST(0.0);

			if (jsonObj.has("lastpurchase_PRICE") && !jsonObj.isNull("lastpurchase_PRICE"))
				productiteminventory.setLASTPURCHASE_PRICE(jsonObj.getDouble("lastpurchase_PRICE"));
			else if (productiteminventoryid == 0)
				productiteminventory.setLASTPURCHASE_PRICE(0.0);

			if (jsonObj.has("reorder_POINT") && !jsonObj.isNull("reorder_POINT"))
				productiteminventory.setREORDER_POINT(jsonObj.getLong("reorder_POINT"));

			if (jsonObj.has("autolocationassignment_ALLOWED") && !jsonObj.isNull("autolocationassignment_ALLOWED"))
				productiteminventory.setAUTOLOCATIONASSIGNMENT_ALLOWED(jsonObj.getString("autolocationassignment_ALLOWED"));

			if (jsonObj.has("autolocationassignment_SUSPENDED") && !jsonObj.isNull("autolocationassignment_SUSPENDED"))
				productiteminventory.setAUTOLOCATIONASSIGNMENT_SUSPENDED(jsonObj.getString("autolocationassignment_SUSPENDED"));

			if (jsonObj.has("preferedstock_LEVEL") && !jsonObj.isNull("preferedstock_LEVEL"))
				productiteminventory.setPREFEREDSTOCK_LEVEL(jsonObj.getLong("preferedstock_LEVEL"));

			if (jsonObj.has("purchaselead_TIME") && !jsonObj.isNull("purchaselead_TIME"))
				productiteminventory.setPURCHASELEAD_TIME(jsonObj.getLong("purchaselead_TIME"));

			if (jsonObj.has("staftystock_LEVEL") && !jsonObj.isNull("staftystock_LEVEL"))
				productiteminventory.setSTAFTYSTOCK_LEVEL(jsonObj.getLong("staftystock_LEVEL"));

			if (jsonObj.has("atplead_TIME") && !jsonObj.isNull("atplead_TIME"))
				productiteminventory.setATPLEAD_TIME(jsonObj.getLong("atplead_TIME"));

			if (jsonObj.has("defaultreturn_COST") && !jsonObj.isNull("defaultreturn_COST"))
				productiteminventory.setDEFAULTRETURN_COST(jsonObj.getDouble("defaultreturn_COST"));

			if (jsonObj.has("lastcount_DATE") && !jsonObj.isNull("lastcount_DATE"))
				productiteminventory.setLASTCOUNT_DATE(jsonObj.getString("lastcount_DATE"));

			if (jsonObj.has("nectcount_DATE") && !jsonObj.isNull("nectcount_DATE"))
				productiteminventory.setNECTCOUNT_DATE(jsonObj.getString("nectcount_DATE"));

			if (jsonObj.has("count_INTERVAL") && !jsonObj.isNull("count_INTERVAL"))
				productiteminventory.setCOUNT_INTERVAL(jsonObj.getLong("count_INTERVAL"));

			if (jsonObj.has("inventoryclassifiction_ID") && !jsonObj.isNull("inventoryclassifiction_ID"))
				productiteminventory.setINVENTORYCLASSIFICTION_ID(jsonObj.getLong("inventoryclassifiction_ID"));

			if (productiteminventoryid == 0)
				productiteminventory.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				productiteminventory.setISACTIVE(jsonObj.getString("isactive"));

			productiteminventory.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productiteminventory.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productiteminventory.setMODIFIED_WHEN(dateFormat1.format(date));
			productiteminventories.add(productiteminventory);
		}

		for (int a=0; a<productiteminventories.size(); a++) {
			ProductItemInventory productiteminventory = productiteminventories.get(a);
			productiteminventory = productiteminventoryrepository.saveAndFlush(productiteminventory);
			productiteminventories.get(a).setPRODUCTITEMINVENTORY_ID(productiteminventory.getPRODUCTITEMINVENTORY_ID());
		}

		ResponseEntity responseentity;
		if (jsonProductItemInventory != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productiteminventories.get(0) , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productiteminventories, null , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productiteminventory/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItemInventory productiteminventory = productiteminventoryrepository.findOne(id);
		productiteminventoryrepository.delete(productiteminventory);

		return new ResponseEntity(getAPIResponse(null, productiteminventory , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productiteminventory/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject productiteminventory = new JSONObject();
		productiteminventory.put("id", id);
		productiteminventory.put("isactive", "N");

		return insertupdateAll(null, productiteminventory, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productiteminventory/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductItemInventory> productiteminventories = ((active == true)
				? productiteminventoryrepository.findBySearch("%" + jsonObj.getString("search") + "%")
						: productiteminventoryrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		return new ResponseEntity(getAPIResponse(productiteminventories, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productiteminventory/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemInventory> productiteminventories = new ArrayList<ProductItemInventory>();
		JSONObject jsonObj = new JSONObject(data);
		JSONArray searchObject = new JSONArray();
	
		long productitem_ID = 0 , productlocation_ID = 0, inventoryclassification_ID = 0;
		List<Integer> productitem_IDS = new ArrayList<Integer>(); 
		productitem_IDS.add((int) 0);

		boolean isWithDetail = true;
		if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
			isWithDetail = jsonObj.getBoolean("iswithdetail");
		}
		jsonObj.put("iswithdetail", false);

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

		if (jsonObj.has("productlocation_ID") && !jsonObj.isNull("productlocation_ID"))
			productlocation_ID = jsonObj.getLong("productlocation_ID");
		else if (jsonObj.has("productlocation_CODE") && !jsonObj.isNull("productlocation_CODE")) {
			JSONObject productlocation = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'PRODUCTLOCATION', code: "+jsonObj.getString("productlocation_CODE")+"}", apiRequest.getREQUEST_OUTPUT(), true));
			if (productlocation != null)
				productlocation_ID = productlocation.getLong("id");
		} 

		if (jsonObj.has("inventoryclassification_ID") && !jsonObj.isNull("inventoryclassification_ID"))
			inventoryclassification_ID = jsonObj.getLong("inventoryclassification_ID");
		else if (jsonObj.has("inventoryclassification_CODE") && !jsonObj.isNull("inventoryclassification_CODE")) {
			JSONObject inventoryclassification = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'INVENTORYCLASSIFICTION', code: "+jsonObj.getString("inventoryclassification_CODE")+"}", apiRequest.getREQUEST_OUTPUT(), true));
			if (inventoryclassification != null)
				inventoryclassification_ID = inventoryclassification.getLong("id");
		} 

		if (productitem_ID != 0 || productlocation_ID != 0 || inventoryclassification_ID != 0) {
			productiteminventories = ((active == true)
					? productiteminventoryrepository.findByAdvancedSearch(productitem_ID, productitem_IDS, productlocation_ID, inventoryclassification_ID)
							: productiteminventoryrepository.findAllByAdvancedSearch(productitem_ID, productitem_IDS, productlocation_ID, inventoryclassification_ID));
		}
		return new ResponseEntity(getAPIResponse(productiteminventories, null, null, null, null, apiRequest, false, isWithDetail).getREQUEST_OUTPUT(), HttpStatus.OK);
	}       

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItemInventory.getDatabaseTableID());
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

		if (checkTokenResponse.has("employee_ID") && !checkTokenResponse.isNull("employee_ID"))
			apiRequest.setRESPONSE_DATETIME(""+checkTokenResponse.getLong("employee_ID"));

		return apiRequest;
	}

	APIRequestDataLog getAPIResponse(List<ProductItemInventory> productiteminventories, ProductItemInventory productiteminventory , JSONArray jsonProductItemInventories, JSONObject jsonProductItemInventory, String message, APIRequestDataLog apiRequest, boolean isTableLog,boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productiteminventoryID = 0;

		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductItemInventory", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productiteminventory != null && isWithDetail == true) {

				JSONObject productitem = new JSONObject(ServiceCall.GET("productitem/"+productiteminventory.getPRODUCTITEM_ID(), apiRequest.getREQUEST_OUTPUT(), false));
				productiteminventory.setPRODUCTITEM_DETAIL(productitem.toString());

				if(productiteminventory.getPRODUCTLOCATION_ID() != null) {
					JSONObject productlocation = new JSONObject(ServiceCall.GET("lookup/"+productiteminventory.getPRODUCTLOCATION_ID(), apiRequest.getREQUEST_OUTPUT(), true));
					productiteminventory.setPRODUCTLOCATION_DETAIL(productlocation.toString());
				}
				if(productiteminventory.getINVENTORYCLASSIFICTION_ID() != null) {
					JSONObject inventoryclassification = new JSONObject(ServiceCall.GET("lookup/"+productiteminventory.getINVENTORYCLASSIFICTION_ID(), apiRequest.getREQUEST_OUTPUT(), true));
					productiteminventory.setINVENTORYCLASSIFICTION_DETAIL(inventoryclassification.toString());
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productiteminventory));
				productiteminventoryID = productiteminventory.getPRODUCTITEMINVENTORY_ID();
			} else if(productiteminventories != null && isWithDetail == true){	
				if (productiteminventories.size()>0) {
					List<Integer> productitemList = new ArrayList<Integer>();
					List<Integer> lookupList = new ArrayList<Integer>();
					for (int i=0; i<productiteminventories.size(); i++) {
						if(productiteminventories.get(i).getPRODUCTITEM_ID() != null)
							productitemList.add(Integer.parseInt(productiteminventories.get(i).getPRODUCTITEM_ID().toString()));
						if(productiteminventories.get(i).getPRODUCTLOCATION_ID() != null)
							lookupList.add(Integer.parseInt(productiteminventories.get(i).getPRODUCTLOCATION_ID().toString()));
						if(productiteminventories.get(i).getINVENTORYCLASSIFICTION_ID() != null)
							lookupList.add(Integer.parseInt(productiteminventories.get(i).getINVENTORYCLASSIFICTION_ID().toString()));
					}
					JSONArray productitemObject = new JSONArray(ServiceCall.POST("productitem/ids", "{items: "+productitemList+"}", apiRequest.getREQUEST_OUTPUT(), false));
					JSONArray lookupObject = new JSONArray(ServiceCall.POST("lookup/ids", "{lookups: "+ lookupList+"}", apiRequest.getREQUEST_OUTPUT(), true));

					for (int i=0; i<productiteminventories.size(); i++) {
						for (int j=0; j<productitemObject.length(); j++) {
							JSONObject productitem = productitemObject.getJSONObject(j);
							if(productiteminventories.get(i).getPRODUCTITEM_ID() == productitem.getLong("productitem_ID") ) {
								productiteminventories.get(i).setPRODUCTITEM_DETAIL(productitem.toString());
							}
						}

						for (int j=0; j<lookupObject.length(); j++) {
							JSONObject lookup = lookupObject.getJSONObject(j);
							if(productiteminventories.get(i).getPRODUCTLOCATION_ID() != null && productiteminventories.get(i).getPRODUCTLOCATION_ID() == lookup.getLong("id") ) {
								productiteminventories.get(i).setPRODUCTLOCATION_DETAIL(lookup.toString());
							}
							if(productiteminventories.get(i).getINVENTORYCLASSIFICTION_ID() != null && productiteminventories.get(i).getINVENTORYCLASSIFICTION_ID() == lookup.getLong("id") ) {
								productiteminventories.get(i).setINVENTORYCLASSIFICTION_DETAIL(lookup.toString());
							}
						}
					}	
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productiteminventories));
			
			} else if(productiteminventory != null && isWithDetail == false){	
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productiteminventory));

			} else if(productiteminventories != null && isWithDetail == false){	
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productiteminventories));

			} else if (jsonProductItemInventories != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemInventories.toString());

			} else if (jsonProductItemInventory != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemInventory.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}

		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productiteminventoryID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));

		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);

		return apiRequest;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/commitment" ,method = RequestMethod.POST)
	public ResponseEntity Commitment(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productiteminventory/commitment", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		long saleorderdetailstatusID1 = 0, saleorderdetailstatusID2 = 0;
		JSONArray saleorderdetailstatus = new JSONArray(ServiceCall.POST("lookup/entity", "{entityname: 'SALEORDERDETAILSTATUS'}", headToken, true));
		for (int i=0; i<saleorderdetailstatus.length(); i++) {
			if (saleorderdetailstatus.getJSONObject(i).getString("code").compareTo("F") == 0) {
				saleorderdetailstatusID1 = saleorderdetailstatus.getJSONObject(i).getLong("id");
			} else if (saleorderdetailstatus.getJSONObject(i).getString("code").compareTo("PF") == 0) {
				saleorderdetailstatusID2 = saleorderdetailstatus.getJSONObject(i).getLong("id");
			} 
		}

		JSONArray jsonPAV = new JSONArray(data);
		JSONArray rtnArray = new JSONArray();

		for (int i = 0; i < jsonPAV.length(); i++) {
			JSONObject orderdetail = jsonPAV.getJSONObject(i);
			JSONObject rtnobj = new JSONObject();
			long quantity = orderdetail.getLong("productitem_QUANTITY");

			ProductItemInventory productiteminventory = productiteminventoryrepository.findByProductItemId(orderdetail.getLong("productitem_ID"));

			if(quantity > 0 && productiteminventory.getQUANTITY_AVAILABLE() > 0 && productiteminventory.getQUANTITY_AVAILABLE() < (double) quantity) {
				quantity = (long) (productiteminventory.getQUANTITY_AVAILABLE() - 0);
			} else if (quantity > 0 && productiteminventory.getQUANTITY_AVAILABLE() <= 0) {
				quantity = 0;
			}

			productiteminventory.setQUANTITY_AVAILABLE(productiteminventory.getQUANTITY_AVAILABLE() -  quantity);
			productiteminventory.setQUANTITY_COMMITTED(productiteminventory.getQUANTITY_COMMITTED() +  quantity);
			productiteminventory = productiteminventoryrepository.saveAndFlush(productiteminventory);

			rtnobj.put("saleorder_ID", orderdetail.getLong("saleorder_ID"));
			rtnobj.put("productitem_ID", orderdetail.getLong("productitem_ID"));
			rtnobj.put("productitem_COMMITTED", quantity);
			if (quantity == orderdetail.getLong("productitem_QUANTITY"))
				rtnobj.put("saleorderdetailstatus_ID", saleorderdetailstatusID1);
			else
				rtnobj.put("saleorderdetailstatus_ID", saleorderdetailstatusID2);

			rtnArray.put(rtnobj);
		}

		return new ResponseEntity(getAPIResponse(null, null, rtnArray, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/fulfillment" ,method = RequestMethod.POST)
	public ResponseEntity Fulfillment(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productiteminventory/fulfillment", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONArray jsonPAV = new JSONArray(data);
		JSONArray rtnArray = new JSONArray();
		JSONObject saleorderdetailstatusID = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'SALEORDERDETAILSTATUS', code: 'B'}", headToken, true));

		for (int i = 0; i < jsonPAV.length(); i++) {
			JSONObject orderdetail = jsonPAV.getJSONObject(i);
			JSONObject rtnobj = new JSONObject();

			long commitment = orderdetail.getLong("committed");
			long backordered = orderdetail.getLong("back_ORDERED");

			ProductItemInventory productiteminventory = productiteminventoryrepository.findByProductItemId(orderdetail.getLong("productitem_ID"));
			productiteminventory.setQUANTITY_ONHAND(productiteminventory.getQUANTITY_ONHAND() - commitment + backordered);
			productiteminventory.setQUANTITY_AVAILABLE(productiteminventory.getQUANTITY_AVAILABLE() +  backordered);
			productiteminventory.setQUANTITY_COMMITTED(productiteminventory.getQUANTITY_COMMITTED() - commitment - backordered);
			productiteminventory = productiteminventoryrepository.saveAndFlush(productiteminventory);

			rtnobj.put("saleorderdetail_ID", orderdetail.getLong("saleorderdetail_ID"));
			rtnobj.put("productitem_FULFILLED", commitment - backordered);
			rtnobj.put("saleorderdetailstatus_ID", saleorderdetailstatusID.getLong("id"));
			rtnArray.put(rtnobj);
		}

		return new ResponseEntity(getAPIResponse(null, null, rtnArray, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/received" ,method = RequestMethod.POST)
	public ResponseEntity Received(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productiteminventory/received", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONArray jsonPAV = new JSONArray(data);
		List<ProductItemInventory> productiteminventorylist = new ArrayList<ProductItemInventory>();

		for (int i = 0; i < jsonPAV.length(); i++) {
			JSONObject productitem = jsonPAV.getJSONObject(i);		
			long productitem_id = 0;
			if (productitem.has("productitem_ID"))
				productitem_id = productitem.getLong("productitem_ID");
			else if (productitem.has("product_ID")) {
				JSONObject product = new JSONObject();
				product.put("product_ID", productitem.getLong("product_ID"));
				JSONArray productitems = new JSONArray(ServiceCall.POST("productitem/advancedsearch", product.toString(), apiRequest.getREQUEST_OUTPUT(), false));
				if (productitems.length() > 0) {
					productitem_id = productitems.getJSONObject(productitems.length()-1).getLong("productitem_ID");
				}
			}
			
			long quantity_received = productitem.getLong("quantity_RECEIVED");
			if(quantity_received != 0){
				ProductItemInventory productiteminventory = productiteminventoryrepository.findByProductItemId(productitem_id);
				if(productiteminventory != null)
				{
					double quantity_onhand = productiteminventory.getQUANTITY_ONHAND() + (double) quantity_received;
					productiteminventory.setQUANTITY_ONHAND(quantity_onhand);
					double quantity_available = productiteminventory.getQUANTITY_AVAILABLE() + (double) quantity_received;
					productiteminventory.setQUANTITY_AVAILABLE(quantity_available);
					productiteminventory = productiteminventoryrepository.saveAndFlush(productiteminventory);
					productiteminventorylist.add(productiteminventory);
				}
			}
		}

		return new ResponseEntity(getAPIResponse(productiteminventorylist, null, null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}	
}
