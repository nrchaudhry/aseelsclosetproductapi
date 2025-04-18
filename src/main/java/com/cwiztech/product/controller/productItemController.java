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
import com.cwiztech.product.model.ProductItem;
import com.cwiztech.product.repository.productItemInventoryRepository;
import com.cwiztech.product.repository.productItemPriceLevelRepository;
import com.cwiztech.product.repository.productItemRepository;
import com.cwiztech.services.ServiceCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productitem")
public class productItemController {
	private static final Logger log = LoggerFactory.getLogger(productItemController.class);

	@Autowired
	private productItemRepository productitemrepository;

	@Autowired
	private productItemInventoryRepository productiteminventoryrepository;

    @Autowired
    private productItemPriceLevelRepository productitempricelevelrepository;

	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;

	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitem", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItem> productitems = productitemrepository.findActive();
		return new ResponseEntity(getAPIResponse(productitems, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitem/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItem> productitems = productitemrepository.findAll();

		return new ResponseEntity(getAPIResponse(productitems, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitem/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItem productitem = productitemrepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, productitem , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
	public ResponseEntity getOneDetail(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitem/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject objProductItem = new JSONObject();
		ProductItem productitem = productitemrepository.findOne(id);
		if (productitem != null) {
			JSONObject product = new JSONObject(ServiceCall.GET("product/"+productitem.getPRODUCT_ID(), apiRequest.getREQUEST_OUTPUT(), false));
			productitem.setPRODUCT_DETAIL(product.toString());

			ObjectMapper mapper = new ObjectMapper();

			objProductItem = new JSONObject(mapper.writeValueAsString(productitem));
            objProductItem.put("purchase_PRICE", product.getLong("purchase_PRICE"));
            objProductItem.put("weight", product.getDouble("product_WEIGHT"));

            JSONObject taxcode = new JSONObject(product.getString("taxcode_DETAIL"));
            objProductItem.put("taxcode_ID", taxcode.getLong("taxcode_ID"));
            objProductItem.put("taxcode", taxcode.getString("taxcode_TITLE"));
            objProductItem.put("vat", taxcode.getLong("taxcode_PERCENTAGE"));


			JSONArray productiteminventory = new JSONArray(ServiceCall.POST("productiteminventory/advancedsearch", "{productitem_ID: "+ productitem.getPRODUCTITEM_ID() + ", iswithdetail: false}", apiRequest.getREQUEST_OUTPUT(), false));
			JSONArray productitempricelevel = new JSONArray(ServiceCall.POST("productitempricelevel/advancedsearch", "{productitem_ID: "+ productitem.getPRODUCTITEM_ID() + ", iswithdetail: false}", apiRequest.getREQUEST_OUTPUT(), false));
			JSONArray productitemimage = new JSONArray(ServiceCall.POST("productitemimage/advancedsearch", "{productitem_ID: "+ productitem.getPRODUCTITEM_ID() + ", iswithdetail: false}", apiRequest.getREQUEST_OUTPUT(), false));
            objProductItem.put("productiteminventory", productiteminventory.toString());
            objProductItem.put("productitempricelevel", productitempricelevel.toString());
            objProductItem.put("productitemimage", productitemimage.toString());
				
			JSONArray productitemattributevalues = new JSONArray(ServiceCall.POST("productitemattributevalue/advancedsearch", "{productitem_ID: "+ productitem.getPRODUCTITEM_ID() + ", iswithdetail: false}", apiRequest.getREQUEST_OUTPUT(), false));
            for (int j=0; j<productitemattributevalues.length(); j++) {
				if (productitem.getPRODUCTITEM_ID()==productitemattributevalues.getJSONObject(j).getLong("productitem_ID")) {
					JSONObject jsonproductattribute = new JSONObject(productitemattributevalues.getJSONObject(j).getString("productattribute_DETAIL"));
					JSONObject jsonattribute = new JSONObject(jsonproductattribute.getString("attribute_DETAIL"));
					if (!productitemattributevalues.getJSONObject(j).isNull("productattributeitem_ID"))
						objProductItem.put(jsonattribute.getString("attribute_KEY"), productitemattributevalues.getJSONObject(j).getLong("productattributevalue_ID"));
					else if (!productitemattributevalues.getJSONObject(j).isNull("productattributeitem_VALUE"))
						objProductItem.put(jsonattribute.getString("attribute_KEY"), productitemattributevalues.getJSONObject(j).getString("productattributeitem_VALUE"));
					if (jsonattribute.getString("attribute_KEY").equals("taxcode")) {
						if (productitemattributevalues.getJSONObject(j).getLong("productattributevalue_ID")==1) {
							objProductItem.put("taxcode", "VAT:S");
							objProductItem.put("vat", 20);
						} else {
							objProductItem.put("taxcode", "VAT:Z");
							objProductItem.put("vat", 0);
						}
					}
				}
			}
		}
		
		return new ResponseEntity(getAPIResponse(null, null, null, objProductItem, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitem/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitem_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitems = jsonObj.getJSONArray("items");
		for (int i=0; i<jsonproductitems.length(); i++) {
			productitem_IDS.add((Integer) jsonproductitems.get(i));
		}
		List<ProductItem> productitems = new ArrayList<ProductItem>();
		if (jsonproductitems.length()>0)
			productitems = productitemrepository.findByIDs(productitem_IDS);

		return new ResponseEntity(getAPIResponse(productitems, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids/detail", method = RequestMethod.POST)
	public ResponseEntity getByIDsWithDetail(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitem/ids/detail", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> productitem_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitems = jsonObj.getJSONArray("items");
		for (int i=0; i<jsonproductitems.length(); i++) {
			productitem_IDS.add((Integer) jsonproductitems.get(i));
		}
		List<ProductItem> productitems = new ArrayList<ProductItem>();
		if (jsonproductitems.length()>0)
			productitems = productitemrepository.findByIDs(productitem_IDS);

		if (productitems.size()>0) {
//			List<Integer> applicationList = new ArrayList<Integer>();
//			for (int i=0; i<productitems.size(); i++) {
//				applicationList.add(Integer.parseInt(productitems.get(i).getAPPLICATION_ID().toString()));
//			}
//			JSONArray logisticsObject = new JSONArray(ServiceCall.POST("application/ids", "{applications: "+applicationList+"}", apiRequest.getREQUEST_OUTPUT(), false));

			List<Integer> productList = new ArrayList<Integer>();
			for (int i=0; i<productitems.size(); i++) {
				productList.add(Integer.parseInt(productitems.get(i).getPRODUCT_ID().toString()));
			}
			JSONArray productObject = new JSONArray(ServiceCall.POST("product/ids", "{products: "+productList+"}", apiRequest.getREQUEST_OUTPUT(), false));

			for (int i=0; i<productitems.size(); i++) {
//				for (int j=0; j<logisticsObject.length(); j++) {
//					JSONObject application = logisticsObject.getJSONObject(j);
//					if(productitems.get(i).getAPPLICATION_ID() == application.getLong("application_ID") ) {
//						productitems.get(i).setAPPLICATION_DETAIL(application.toString());
//					}
//				}

				for (int j=0; j<productObject.length(); j++) {
					JSONObject product = productObject.getJSONObject(j);
					if(productitems.get(i).getPRODUCT_ID() == product.getLong("product_ID") ) {
						productitems.get(i).setPRODUCT_DETAIL(product.toString());
					}
				}
			}
		}

		jsonproductitems = new JSONArray();
		JSONArray productitemattributevalues = new JSONArray(ServiceCall.POST("productitemattributevalue/productitem/ids", jsonObj.toString().replace("//", ""), apiRequest.getREQUEST_OUTPUT(), false));
		for (int i=0; i<productitems.size(); i++) {
			JSONObject objProductItem = new JSONObject(mapper.writeValueAsString(productitems.get(i)));
			
            JSONObject product = new JSONObject(productitems.get(i).getPRODUCT_DETAIL());
            objProductItem.put("purchase_PRICE", product.getLong("purchase_PRICE"));
            objProductItem.put("weight", product.getDouble("product_WEIGHT"));
            JSONObject taxcode = new JSONObject(product.getString("taxcode_DETAIL"));
            objProductItem.put("taxcode_ID", taxcode.getLong("taxcode_ID"));
            objProductItem.put("taxcode", taxcode.getString("taxcode_TITLE"));
            objProductItem.put("vat", taxcode.getLong("taxcode_PERCENTAGE"));

            for (int j=0; j<productitemattributevalues.length(); j++) {
				if (productitems.get(i).getPRODUCTITEM_ID()==productitemattributevalues.getJSONObject(j).getLong("productitem_ID")) {
					JSONObject jsonproductattribute = new JSONObject(productitemattributevalues.getJSONObject(j).getString("productattribute_DETAIL"));
					JSONObject jsonattribute = new JSONObject(jsonproductattribute.getString("attribute_DETAIL"));
					if (!productitemattributevalues.getJSONObject(j).isNull("productattributeitem_ID"))
						objProductItem.put(jsonattribute.getString("attribute_KEY"), productitemattributevalues.getJSONObject(j).getLong("productattributevalue_ID"));
					else if (!productitemattributevalues.getJSONObject(j).isNull("productattributeitem_VALUE"))
						objProductItem.put(jsonattribute.getString("attribute_KEY"), productitemattributevalues.getJSONObject(j).getString("productattributeitem_VALUE"));
					if (jsonattribute.getString("attribute_KEY").equals("taxcode")) {
						if (productitemattributevalues.getJSONObject(j).getLong("productattributevalue_ID")==1) {
							objProductItem.put("taxcode", "VAT:S");
							objProductItem.put("vat", 20);
						} else {
							objProductItem.put("taxcode", "VAT:Z");
							objProductItem.put("vat", 0);
						}
					}
				}
			}
			jsonproductitems.put(objProductItem);
		}

		return new ResponseEntity(getAPIResponse(null, null , jsonproductitems, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitem/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitem_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitems = jsonObj.getJSONArray("items");
		for (int i=0; i<jsonproductitems.length(); i++) {
			productitem_IDS.add((Integer) jsonproductitems.get(i));
		}
		List<ProductItem> productitems = new ArrayList<ProductItem>();
		if (jsonproductitems.length()>0)
			productitems = productitemrepository.findByNotInIDs(productitem_IDS);

		return new ResponseEntity(getAPIResponse(productitems, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitem", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productitem/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productitem", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductItems, JSONObject jsonProductItem, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductItem> productitems = new ArrayList<ProductItem>();
		if (jsonProductItem != null) {
			jsonProductItems = new JSONArray();
			jsonProductItems.put(jsonProductItem);
		}
		log.info(jsonProductItems.toString());

		for (int a=0; a<jsonProductItems.length(); a++) {
			JSONObject jsonObj = jsonProductItems.getJSONObject(a);
			ProductItem productitem = new ProductItem();
			long productitemid = 0;

			if (jsonObj.has("productitem_ID")) {
				productitemid = jsonObj.getLong("productitem_ID");
				if (productitemid != 0) {
					productitem = productitemrepository.findOne(productitemid);

					if (productitem == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductItem Data!", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}


			if (productitemid == 0) {
				if (!jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

				if (!jsonObj.has("application_ID") || jsonObj.isNull("application_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "application_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

				if (!jsonObj.has("productitem_NAME") || jsonObj.isNull("productitem_NAME"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_NAME is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			}
			if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID"))
				productitem.setPRODUCT_ID(jsonObj.getLong("product_ID"));

			if (jsonObj.has("application_ID") && !jsonObj.isNull("application_ID"))
				productitem.setAPPLICATION_ID(jsonObj.getLong("application_ID"));

			if (jsonObj.has("productitem_NAME") && !jsonObj.isNull("productitem_NAME"))
				productitem.setPRODUCTITEM_NAME(jsonObj.getString("productitem_NAME"));

			if (jsonObj.has("productitem_DESC") && !jsonObj.isNull("productitem_DESC"))
				productitem.setPRODUCTITEM_DESC(jsonObj.getString("productitem_DESC"));

			if (jsonObj.has("productitemimage_URL") && !jsonObj.isNull("productitemimage_URL"))
				productitem.setPRODUCTITEMIMAGE_URL(jsonObj.getString("productitemimage_URL"));

			if (jsonObj.has("productitemicon_URL") && !jsonObj.isNull("productitemicon_URL"))
				productitem.setPRODUCTITEMICON_URL(jsonObj.getString("productitemicon_URL"));

			if (jsonObj.has("deactive_AUTO"))
				productitem.setDEACTIVE_AUTO(jsonObj.getString("deactive_AUTO"));
			else if (productitemid == 0)
				productitem.setDEACTIVE_AUTO("N");

			if (productitemid == 0)
				productitem.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				productitem.setISACTIVE(jsonObj.getString("isactive"));

			productitem.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productitem.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productitem.setMODIFIED_WHEN(dateFormat1.format(date));
			productitems.add(productitem);
		}

		for (int a=0; a<productitems.size(); a++) {
			ProductItem productitem = productitems.get(a);
			productitem = productitemrepository.saveAndFlush(productitem);
			productitems.get(a).setPRODUCTITEM_ID(productitem.getPRODUCTITEM_ID());

			productiteminventoryrepository.update(productitem.getPRODUCTITEM_ID(), productitem.getISACTIVE());
			productitempricelevelrepository.update(productitem.getPRODUCTITEM_ID(), productitem.getISACTIVE());
		}

		ResponseEntity responseentity;
		if (jsonProductItem != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productitems.get(0) , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productitems, null , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitem/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItem productitem = productitemrepository.findOne(id);
		productitemrepository.delete(productitem);

		return new ResponseEntity(getAPIResponse(null, productitem , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitem/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject productitem = new JSONObject();
		productitem.put("id", id);
		productitem.put("isactive", "N");

		return insertupdateAll(null, productitem, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productitem/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductItem> productitems = ((active == true)
				? productitemrepository.findBySearch("%" + jsonObj.getString("search") + "%")
						: productitemrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		return new ResponseEntity(getAPIResponse(productitems, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productitem/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItem> productitems = new ArrayList<ProductItem>();
		JSONObject jsonObj = new JSONObject(data);
		JSONArray searchObject = new JSONArray();
		List<Integer> application_IDS = new ArrayList<Integer>(); 
		List<Integer> product_IDS = new ArrayList<Integer>(); 

		application_IDS.add((int) 0);
		product_IDS.add((int) 0);

		long application_ID = 0 , product_ID = 0;

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
				searchObject = new JSONArray(ServiceCall.POST("application/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, true));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("application/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, true));
			}

			application_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				application_IDS.add((int) searchObject.getJSONObject(i).getLong("application_ID"));
			}
		}

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

		if (application_ID != 0 || product_ID != 0) {
			productitems = ((active == true)
					? productitemrepository.findByAdvancedSearch(product_ID, product_IDS, application_ID, application_IDS)
							: productitemrepository.findAllByAdvancedSearch(product_ID, product_IDS, application_ID, application_IDS));
		}

		return new ResponseEntity(getAPIResponse(productitems, null , null, null, null, apiRequest, false, isWithDetail).getREQUEST_OUTPUT(), HttpStatus.OK);
	}


	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItem.getDatabaseTableID());
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

	APIRequestDataLog getAPIResponse(List<ProductItem> productitems, ProductItem productitem , JSONArray jsonProductItems, JSONObject jsonProductItem, String message, APIRequestDataLog apiRequest, boolean isTableLog,boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productitemID = 0;

		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductItem", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productitem != null && isWithDetail == true) {
				JSONObject application = new JSONObject(ServiceCall.GET("application/"+productitem.getAPPLICATION_ID(), apiRequest.getREQUEST_OUTPUT(), true));
				productitem.setAPPLICATION_DETAIL(application.toString());
				JSONObject product = new JSONObject(ServiceCall.GET("product/"+productitem.getPRODUCT_ID(), apiRequest.getREQUEST_OUTPUT(), false));
				productitem.setPRODUCT_DETAIL(product.toString());
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitem));
				productitemID = productitem.getPRODUCTITEM_ID();
			} else if(productitems != null && isWithDetail == true){
				if (productitems.size()>0) {
					List<Integer> applicationList = new ArrayList<Integer>();
					List<Integer> productList = new ArrayList<Integer>();
					for (int i=0; i<productitems.size(); i++) {
						if(productitems.get(i).getAPPLICATION_ID() != null)
							applicationList.add(Integer.parseInt(productitems.get(i).getAPPLICATION_ID().toString()));
						if(productitems.get(i).getPRODUCT_ID() != null)
							productList.add(Integer.parseInt(productitems.get(i).getPRODUCT_ID().toString()));
					}
					JSONArray logisticsObject = new JSONArray(ServiceCall.POST("application/ids", "{applications: "+applicationList+"}", apiRequest.getREQUEST_OUTPUT(), true));
					JSONArray productObject = new JSONArray(ServiceCall.POST("product/ids", "{products: "+productList+"}", apiRequest.getREQUEST_OUTPUT(), false));

					for (int i=0; i<productitems.size(); i++) {
						for (int j=0; j<logisticsObject.length(); j++) {
							JSONObject application = logisticsObject.getJSONObject(j);
							if(productitems.get(i).getAPPLICATION_ID() == application.getLong("application_ID") ) {
								productitems.get(i).setAPPLICATION_DETAIL(application.toString());
							}
						}	
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if(productitems.get(i).getPRODUCT_ID() == product.getLong("product_ID") ) {
								productitems.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitems));

			} else if (productitem != null && isWithDetail == false) {
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitem));

			} else if (productitems != null && isWithDetail == false) {
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitems));

			} else if (jsonProductItems != null) {
				apiRequest.setREQUEST_OUTPUT(jsonProductItems.toString());

			} else if (jsonProductItem != null) {
				apiRequest.setREQUEST_OUTPUT(jsonProductItem.toString());
			}

			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}

		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productitemID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));

		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);

		return apiRequest;
	}


	//	@RequestMapping(value = "/getfromnetsuite/{id}", method = RequestMethod.GET)
	//	public ResponseEntity getNetSuiteProducts(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
	//			throws InterruptedException, IOException, JSONException, ParseException {
	//		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
	//		String rtn, workstation = null;
	//		APIRequestDataLog apiRequest;
	//		
	//		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//		Date date = new Date();
	//
	//		log.info("GET: /productitem/getfromnetsuite/"+id);
	//
	//		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItem.getDatabaseTableID());
	//
	//		if (checkTokenResponse.has("error")) {
	//			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0,
	//					"/product/getfromnetsuite/"+id, null, workstation);
	//			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
	//			apirequestdatalogRepository.saveAndFlush(apiRequest);
	//			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
	//		}
	//
	//		ObjectMapper mapper = new ObjectMapper();
	//		Long requestUser = checkTokenResponse.getLong("user_ID");
	//		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, 
	//				"/product/getfromnetsuite/"+id, null, workstation);
	//	
	//		int start = 0, counter = 1;
	//		List<ProductItem> products = new ArrayList<ProductItem>();
	//		JSONArray objproductitems = new JSONArray();
	//		
	//		while (start == 0 || objproductitems.length() > 0) {
	//			JSONArray params = new JSONArray();
	//			JSONObject obj = new JSONObject();
	//			obj.put("key", "start");
	//			obj.put("value", "" + start);
	//			params.put(obj);
	//
	//			int end = start + 100;
	//			obj = new JSONObject();
	//			obj.put("key", "end");
	//			obj.put("value", "" + end);
	//			params.put(obj);
	//			
	//			obj = new JSONObject();
	//			obj.put("key", "id");
	//			obj.put("value", "" + id);
	//			params.put(obj);
	//
	//			int getDataCount = 1;
	//			while (getDataCount <= 20) {
	//				try {
	//					if (id==0) {
	//						JSONObject customersFromNetSuite = new JSONObject(NetSuiteAPI.GET(692, 1, params));
	//						objproductitems = customersFromNetSuite.getJSONArray("msg");
	//					} else if (id>0){
	//						JSONObject customersFromNetSuite = new JSONObject(NetSuiteAPI.GET(685, 1, params));
	//						JSONObject objproduct = customersFromNetSuite.getJSONObject("msg");
	//						objproductitems.put(objproduct);
	//						id = (long) -1;
	//					} else {
	//						objproductitems = new JSONArray();
	//					}
	//					getDataCount = 21;
	//				} catch (Exception e) {
	//					log.info(e.getMessage());
	//					Thread.sleep(5000);
	//					getDataCount = getDataCount + 1;
	//				}
	//			}
	//
	//			for (int i = 0; i < objproductitems.length(); i++) {
	//				JSONObject objProductItem = objproductitems.getJSONObject(i);
	//				JSONObject productFromNetSuite = objProductItem.getJSONObject("fields");
	//
	//				log.info(objProductItem.getString("id"));
	//				log.info(productFromNetSuite.getString("itemid"));
	//
	//				List<ProductItem> productitem = productitemrepository
	//						.findByCode(productFromNetSuite.getString("itemid"));
	//				if (productitem.size() == 0) {
	//					Product product = new Product();
	//					product.setPRODUCTCATEGORY_ID(productcategoryrepository.findOne((long) 71));
	//					product.setPRODUCT_CODE(productFromNetSuite.getString("itemid"));
	//					product.setPRODUCT_NAME(productFromNetSuite.getString("displayname"));
	//					product.setPRODUCT_DESC(productFromNetSuite.getString("modifiableitemid"));
	//					product.setISACTIVE("Y");
	//					product.setMODIFIED_BY(requestUser);
	//					product.setMODIFIED_WORKSTATION(workstation);
	//					product.setMODIFIED_WHEN(dateFormat1.format(date));
	//					product = productrepository.saveAndFlush(product);
	//
	//					ProductItem productitemnew = new ProductItem();
	//					productitemnew.setPRODUCT_ID(product);
	//					productitemnew.setAPPLICATION_ID((long) 1);
	//					productitemnew.setPRODUCTITEM_NAME(productFromNetSuite.getString("displayname"));
	//					productitemnew.setPRODUCTITEM_DESC(productFromNetSuite.getString("modifiableitemid"));
	//					productitemnew.setISACTIVE("Y");
	//					productitemnew.setMODIFIED_BY(requestUser);
	//					productitemnew.setMODIFIED_WORKSTATION(workstation);
	//					productitemnew.setMODIFIED_WHEN(dateFormat1.format(date));
	//					productitemnew = productitemrepository.saveAndFlush(productitemnew);
	//
	//					productitem.add(productitemnew);
	//				}
	//
	//				Iterator<?> keys = productFromNetSuite.keys();
	//				while (keys.hasNext()) {
	//					String key = (String) keys.next();
	//					String key1 = key;
	//					key = key.replace("_", "");
	//					
	//					if (key.compareTo("isinactive")==0) {
	//						if (productFromNetSuite.getString(key1).compareTo("F")==0) {
	//							productitem.get(0).setISACTIVE("Y");
	//						} else {
	//							productitem.get(0).setISACTIVE("N");
	//						}
	//						productitemrepository.saveAndFlush(productitem.get(0));
	//					}
	//					
	//					ProductAttribute productattribute = productattributerepository.findByKey(key);
	//					if (productattribute == null) {
	//						productattribute = new ProductAttribute();
	//						productattribute
	//								.setPRODUCTATTRIBUTECATEGORY_ID(productattributecategoryrepository.findOne((long) 18));
	//						productattribute.setPRODUCTATTRIBUTEORDER_NO((long) 1);
	//						productattribute.setPRODUCTCATEGORY_ID(productcategoryrepository.findOne((long) 0));
	//						productattribute.setPRODUCTATTRIBUTE_NAME("Unknown");
	//						productattribute.setPRODUCTATTRIBUTE_KEY(key);
	//						productattribute.setDATATYPE_ID(lookuprepository.findOne((long) 1));
	//						productattribute.setISREQUIRED("N");
	//						productattribute.setISACTIVE("Y");
	//						productattribute.setMODIFIED_BY(requestUser);
	//						productattribute.setMODIFIED_WORKSTATION(workstation);
	//						productattribute.setMODIFIED_WHEN(dateFormat1.format(date));
	//						productattribute = productattributerepository.saveAndFlush(productattribute);
	//					}
	//
	//					List<ProductItemAttributeValue> productitemattributevalues = productitemattributevaluerepository
	//							.findByAdvancedSearch(productitem.get(0).getPRODUCTITEM_ID(), (long) 0, (long) 0, (long) 0,
	//									productattribute.getPRODUCTATTRIBUTE_ID(), (long) 0, "", (long) 0, (long) 0, "", (long) 0, "");
	//					if (productitemattributevalues.size() > 0) {
	//						productitemattributevalues.get(0)
	//								.setPRODUCTATTRIBUTEITEM_VALUE(productFromNetSuite.getString(key1));
	//						productitemattributevalues.get(0).setISACTIVE("Y");
	//						productitemattributevalues.get(0).setMODIFIED_BY(requestUser);
	//						productitemattributevalues.get(0).setMODIFIED_WORKSTATION(workstation);
	//						productitemattributevalues.get(0).setMODIFIED_WHEN(dateFormat1.format(date));
	//						productitemattributevaluerepository.saveAndFlush(productitemattributevalues.get(0));
	//					} else {
	//						ProductItemAttributeValue productitemattributevalue = new ProductItemAttributeValue();
	//						productitemattributevalue.setPRODUCTITEM_ID(productitem.get(0));
	//						productitemattributevalue.setPRODUCTATTRIBUTE_ID(productattribute);
	//						productitemattributevalue.setPRODUCTATTRIBUTEITEM_VALUE(productFromNetSuite.getString(key1));
	//						productitemattributevalue.setISACTIVE("Y");
	//						productitemattributevalue.setMODIFIED_BY(requestUser);
	//						productitemattributevalue.setMODIFIED_WORKSTATION(workstation);
	//						productitemattributevalue.setMODIFIED_WHEN(dateFormat1.format(date));
	//						productitemattributevalue = productitemattributevaluerepository
	//								.saveAndFlush(productitemattributevalue);
	//					}
	//				}
	//
	//				JSONObject productitemsublist = objProductItem.getJSONObject("sublists");
	//
	//				JSONObject productitemprices = productitemsublist.getJSONObject("price1");
	//				int lineNo = 1;
	//				while (productitemprices.has("line " + lineNo)) {
	//					JSONObject productitemprice = productitemprices.getJSONObject("line " + lineNo);
	//					log.info("Line : " + lineNo + ", Price level: " + productitemprice.getString("pricelevelname"));
	//					lineNo = lineNo + 1;
	//
	//					if (!productitemprice.isNull("price[1]")) {
	//						log.info("Price: " + productitemprice.get("price[1]"));
	//						List<ProductItemPriceLevel> productitempricelevels = productitempricelevelrepository
	//								.findByAdvancedSearch((long) 0, productitem.get(0).getPRODUCTITEM_ID(), (long) 0,
	//										(long) 0, (long) 0, (long) 0, "", (long) 1,
	//										productitemprice.getString("pricelevel"), (long) 0, (long) 0);
	//
	//						ProductItemPriceLevel productitempricelevel = new ProductItemPriceLevel();
	//						if (productitempricelevels.size() > 0)
	//							productitempricelevel = productitempricelevels.get(0);
	//						
	//						Lookup currency = lookuprepository.findByCode("CURRENCY",
	//								productitemprice.getString("currency"));
	//						if (currency == null) {
	//							currency = new Lookup();
	//							currency.setENTITYNAME("CURRENCY");
	//							currency.setCODE(productitemprice.getString("currency"));
	//							currency.setDESCRIPTION(productitemprice.getString("currency"));
	//							currency.setISACTIVE("Y");
	//							currency.setMODIFIED_BY(requestUser);
	//							currency.setMODIFIED_WORKSTATION(workstation);
	//							currency.setMODIFIED_WHEN(dateFormat1.format(date));
	//							currency = lookuprepository.saveAndFlush(currency);
	//						}
	//						productitempricelevel.setCURRENCY_ID(currency);
	//						productitempricelevel.setPRODUCTITEM_ID(productitem.get(0));
	//						Lookup pricelevel = lookuprepository.findByCode("PRICELEVEL",
	//								productitemprice.getString("pricelevel"));
	//						if (pricelevel == null) {
	//							pricelevel = new Lookup();
	//							pricelevel.setENTITYNAME("PRICELEVEL");
	//							pricelevel.setCODE(productitemprice.getString("pricelevel"));
	//							pricelevel.setDESCRIPTION(productitemprice.getString("pricelevelname"));
	//							pricelevel.setISACTIVE("Y");
	//							pricelevel.setMODIFIED_BY(requestUser);
	//							pricelevel.setMODIFIED_WORKSTATION(workstation);
	//							pricelevel.setMODIFIED_WHEN(dateFormat1.format(date));
	//							pricelevel = lookuprepository.saveAndFlush(currency);
	//						}
	//						productitempricelevel.setPRICELEVEL_ID(pricelevel);
	//						productitempricelevel.setPRODUCTITEM_QUANTITY((long) 1);
	//						productitempricelevel.setPRODUCTITEM_UNITPRICE(
	//								Double.parseDouble(productitemprice.getString("price[1]")));
	//						productitempricelevel.setISACTIVE("Y");
	//						productitempricelevel.setMODIFIED_BY(requestUser);
	//						productitempricelevel.setMODIFIED_WORKSTATION(workstation);
	//						productitempricelevel.setMODIFIED_WHEN(dateFormat1.format(date));
	//						productitempricelevel = productitempricelevelrepository.saveAndFlush(productitempricelevel);
	//					}
	//				}
	//
	//				JSONObject objproductiteminventories = productitemsublist.getJSONObject("locations");
	//				lineNo = 1;
	//				while (objproductiteminventories.has("line " + lineNo)) {
	//					JSONObject objproductiteminventory = objproductiteminventories.getJSONObject("line " + lineNo);
	//					lineNo = lineNo + 1;
	//
	//					if (objproductiteminventory.getString("location").compareTo("2") == 0) {
	//						List<ProductItemInventory> productiteminventories = productiteminventoryrepository
	//								.findByAdvancedSearch((long) 0, (long) 0, (long) 0, "", (long) 0, "",
	//										productitem.get(0).getPRODUCTITEM_ID(), (long) 0, (long) 0, "");
	//
	//						if (productiteminventories.size() > 0) {
	//
	//							productiteminventories.get(0).setPRODUCTITEM_ID(productitem.get(0));
	//
	//							if (objproductiteminventory.has("locationid")
	//									&& !objproductiteminventory.isNull("locationid"))
	//								productiteminventories.get(0).setLOCATION_ID(
	//										Long.parseLong(objproductiteminventory.getString("locationid")));
	//
	//							if (objproductiteminventory.has("quantityonhand")
	//									&& !objproductiteminventory.isNull("quantityonhand"))
	//								productiteminventories.get(0).setQUANTITY_ONHAND(
	//										Double.parseDouble(objproductiteminventory.getString("quantityonhand")));
	//
	//							if (objproductiteminventory.has("quantityonorder")
	//									&& !objproductiteminventory.isNull("quantityonorder"))
	//								productiteminventories.get(0).setQUANTITY_ONORDER(
	//										Long.parseLong(objproductiteminventory.getString("quantityonorder")));
	//
	//							if (objproductiteminventory.has("quantitycommitted")
	//									&& !objproductiteminventory.isNull("quantitycommitted"))
	//								productiteminventories.get(0).setQUANTITY_COMMITTED(
	//										Long.parseLong(objproductiteminventory.getString("quantitycommitted")));
	//
	//							if (objproductiteminventory.has("quantityavailable")
	//									&& !objproductiteminventory.isNull("quantityavailable"))
	//								productiteminventories.get(0).setQUANTITY_AVAILABLE(
	//										Double.parseDouble(objproductiteminventory.getString("quantityavailable")));
	//
	//							if (objproductiteminventory.has("quantitybackordered")
	//									&& !objproductiteminventory.isNull("quantitybackordered"))
	//								productiteminventories.get(0).setQUANTITY_BACKORDERED(
	//										Long.parseLong(objproductiteminventory.getString("quantitybackordered")));
	//
	//							if (objproductiteminventory.has("quantityintransit")
	//									&& !objproductiteminventory.isNull("quantityintransit"))
	//								productiteminventories.get(0).setQUANTITY_INTRANSIT(
	//										Long.parseLong(objproductiteminventory.getString("quantityintransit")));
	//
	//							if (objproductiteminventory.has("qtyintransitexternal")
	//									&& !objproductiteminventory.isNull("qtyintransitexternal"))
	//								productiteminventories.get(0).setQUANTITYEXTERNAL_INTRANSIT(
	//										Long.parseLong(objproductiteminventory.getString("qtyintransitexternal")));
	//
	//							if (objproductiteminventory.has("quantityonhandbase")
	//									&& !objproductiteminventory.isNull("quantityonhandbase"))
	//								productiteminventories.get(0).setQUANTITYBASEUNIT_ONHAND(
	//										Double.parseDouble(objproductiteminventory.getString("quantityonhandbase")));
	//
	//							if (objproductiteminventory.has("quantityavailablebase")
	//									&& !objproductiteminventory.isNull("quantityavailablebase"))
	//								productiteminventories.get(0).setQUANTITYBASEUNIT_AVAILABLE(
	//										Double.parseDouble(objproductiteminventory.getString("quantityavailablebase")));
	//
	//							if (objproductiteminventory.has("onhandvaluemli")
	//									&& !objproductiteminventory.isNull("onhandvaluemli"))
	//								productiteminventories.get(0).setVALUE(
	//										Double.parseDouble(objproductiteminventory.getString("onhandvaluemli")));
	//
	//							if (objproductiteminventory.has("averagecostmli")
	//									&& !objproductiteminventory.isNull("averagecostmli"))
	//								productiteminventories.get(0).setAVERAGE_COST(
	//										Double.parseDouble(objproductiteminventory.getString("averagecostmli")));
	//
	//							if (objproductiteminventory.has("lastpurchasepricemli")
	//									&& !objproductiteminventory.isNull("lastpurchasepricemli"))
	//								productiteminventories.get(0).setLASTPURCHASE_PRICE(
	//										Double.parseDouble(objproductiteminventory.getString("lastpurchasepricemli")));
	//
	//							if (objproductiteminventory.has("reorderpoint")
	//									&& !objproductiteminventory.isNull("reorderpoint"))
	//								productiteminventories.get(0).setREORDER_POINT(
	//										Long.parseLong(objproductiteminventory.getString("reorderpoint")));
	//
	//							if (objproductiteminventory.has("isautolocassignmentallowed")
	//									&& !objproductiteminventory.isNull("onhandvaluemli"))
	//								productiteminventories.get(0).setAUTOLOCATIONASSIGNMENT_ALLOWED(
	//										objproductiteminventory.getString("isautolocassignmentallowed"));
	//
	//							if (objproductiteminventory.has("isautolocassignmentsuspended")
	//									&& !objproductiteminventory.isNull("onhandvaluemli"))
	//								productiteminventories.get(0).setAUTOLOCATIONASSIGNMENT_SUSPENDED(
	//										objproductiteminventory.getString("isautolocassignmentsuspended"));
	//
	//							if (objproductiteminventory.has("preferredstocklevel")
	//									&& !objproductiteminventory.isNull("preferredstocklevel"))
	//								productiteminventories.get(0).setPREFEREDSTOCK_LEVEL(
	//										Long.parseLong(objproductiteminventory.getString("preferredstocklevel")));
	//
	//							if (objproductiteminventory.has("leadtime") && !objproductiteminventory.isNull("leadtime"))
	//								productiteminventories.get(0).setPURCHASELEAD_TIME(
	//										Long.parseLong(objproductiteminventory.getString("leadtime")));
	//
	//							if (objproductiteminventory.has("safetystocklevel")
	//									&& !objproductiteminventory.isNull("safetystocklevel"))
	//								productiteminventories.get(0).setSTAFTYSTOCK_LEVEL(
	//										Long.parseLong(objproductiteminventory.getString("safetystocklevel")));
	//
	//							if (objproductiteminventory.has("atpleadtime")
	//									&& !objproductiteminventory.isNull("atpleadtime"))
	//								productiteminventories.get(0).setATPLEAD_TIME(
	//										Long.parseLong(objproductiteminventory.getString("atpleadtime")));
	//
	//							if (objproductiteminventory.has("defaultreturncost")
	//									&& !objproductiteminventory.isNull("defaultreturncost"))
	//								productiteminventories.get(0).setDEFAULTRETURN_COST(
	//										Double.parseDouble(objproductiteminventory.getString("defaultreturncost")));
	//
	//							if (objproductiteminventory.has("lastinvtcountdate")
	//									&& !objproductiteminventory.isNull("lastinvtcountdate"))
	//								productiteminventories.get(0)
	//										.setLASTCOUNT_DATE(dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
	//												.parse(objproductiteminventory.getString("lastinvtcountdate"))));
	//
	//							if (objproductiteminventory.has("nextinvtcountdate")
	//									&& !objproductiteminventory.isNull("nextinvtcountdate"))
	//								productiteminventories.get(0)
	//										.setNECTCOUNT_DATE(dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
	//												.parse(objproductiteminventory.getString("nextinvtcountdate"))));
	//
	//							if (objproductiteminventory.has("invtcountinterval")
	//									&& !objproductiteminventory.isNull("invtcountinterval"))
	//								productiteminventories.get(0).setCOUNT_INTERVAL(
	//										Long.parseLong(objproductiteminventory.getString("invtcountinterval")));
	//
	//							if (objproductiteminventory.has("invtclassification")
	//									&& !objproductiteminventory.isNull("invtclassification"))
	//								productiteminventories.get(0).setINVENTORYCLASSIFICTION_ID(
	//										Long.parseLong(objproductiteminventory.getString("invtclassification")));
	//
	//							productiteminventories.get(0).setISACTIVE("Y");
	//							productiteminventories.get(0).setMODIFIED_BY(requestUser);
	//							productiteminventories.get(0).setMODIFIED_WORKSTATION(workstation);
	//							productiteminventories.get(0).setMODIFIED_WHEN(dateFormat1.format(date));
	//							productiteminventoryrepository.saveAndFlush(productiteminventories.get(0));
	//
	//						} else {
	//
	//							ProductItemInventory productiteminventory = new ProductItemInventory();
	//
	//							productiteminventory.setPRODUCTITEM_ID(productitem.get(0));
	//
	//							if (objproductiteminventory.has("locationid")
	//									&& !objproductiteminventory.isNull("locationid"))
	//								productiteminventory.setLOCATION_ID(
	//										Long.parseLong(objproductiteminventory.getString("locationid")));
	//
	//							if (objproductiteminventory.has("quantityonhand")
	//									&& !objproductiteminventory.isNull("quantityonhand"))
	//								productiteminventory.setQUANTITY_ONHAND(
	//										Double.parseDouble(objproductiteminventory.getString("quantityonhand")));
	//
	//							if (objproductiteminventory.has("quantityonorder")
	//									&& !objproductiteminventory.isNull("quantityonorder"))
	//								productiteminventory.setQUANTITY_ONORDER(
	//										Long.parseLong(objproductiteminventory.getString("quantityonorder")));
	//
	//							if (objproductiteminventory.has("quantitycommitted")
	//									&& !objproductiteminventory.isNull("quantitycommitted"))
	//								productiteminventory.setQUANTITY_COMMITTED(
	//										Long.parseLong(objproductiteminventory.getString("quantitycommitted")));
	//
	//							if (objproductiteminventory.has("quantityavailable")
	//									&& !objproductiteminventory.isNull("quantityavailable"))
	//								productiteminventory.setQUANTITY_AVAILABLE(
	//										Double.parseDouble(objproductiteminventory.getString("quantityavailable")));
	//
	//							if (objproductiteminventory.has("quantitybackordered")
	//									&& !objproductiteminventory.isNull("quantitybackordered"))
	//								productiteminventory.setQUANTITY_BACKORDERED(
	//										Long.parseLong(objproductiteminventory.getString("quantitybackordered")));
	//
	//							if (objproductiteminventory.has("quantityintransit")
	//									&& !objproductiteminventory.isNull("quantityintransit"))
	//								productiteminventory.setQUANTITY_INTRANSIT(
	//										Long.parseLong(objproductiteminventory.getString("quantityintransit")));
	//
	//							if (objproductiteminventory.has("qtyintransitexternal")
	//									&& !objproductiteminventory.isNull("qtyintransitexternal"))
	//								productiteminventory.setQUANTITYEXTERNAL_INTRANSIT(
	//										Long.parseLong(objproductiteminventory.getString("qtyintransitexternal")));
	//
	//							if (objproductiteminventory.has("quantityonhandbase")
	//									&& !objproductiteminventory.isNull("quantityonhandbase"))
	//								productiteminventory.setQUANTITYBASEUNIT_ONHAND(
	//										Double.parseDouble(objproductiteminventory.getString("quantityonhandbase")));
	//
	//							if (objproductiteminventory.has("quantityavailablebase")
	//									&& !objproductiteminventory.isNull("quantityavailablebase"))
	//								productiteminventory.setQUANTITYBASEUNIT_AVAILABLE(
	//										Double.parseDouble(objproductiteminventory.getString("quantityavailablebase")));
	//
	//							if (objproductiteminventory.has("onhandvaluemli")
	//									&& !objproductiteminventory.isNull("onhandvaluemli"))
	//								productiteminventory.setVALUE(
	//										Double.parseDouble(objproductiteminventory.getString("onhandvaluemli")));
	//
	//							if (objproductiteminventory.has("averagecostmli")
	//									&& !objproductiteminventory.isNull("averagecostmli"))
	//								productiteminventory.setAVERAGE_COST(
	//										Double.parseDouble(objproductiteminventory.getString("averagecostmli")));
	//
	//							if (objproductiteminventory.has("lastpurchasepricemli")
	//									&& !objproductiteminventory.isNull("lastpurchasepricemli"))
	//								productiteminventory.setLASTPURCHASE_PRICE(
	//										Double.parseDouble(objproductiteminventory.getString("lastpurchasepricemli")));
	//
	//							if (objproductiteminventory.has("reorderpoint")
	//									&& !objproductiteminventory.isNull("reorderpoint"))
	//								productiteminventory.setREORDER_POINT(
	//										Long.parseLong(objproductiteminventory.getString("reorderpoint")));
	//
	//							if (objproductiteminventory.has("isautolocassignmentallowed")
	//									&& !objproductiteminventory.isNull("isautolocassignmentallowed"))
	//								productiteminventory.setAUTOLOCATIONASSIGNMENT_ALLOWED(
	//										objproductiteminventory.getString("isautolocassignmentallowed"));
	//
	//							if (objproductiteminventory.has("isautolocassignmentsuspended")
	//									&& !objproductiteminventory.isNull("isautolocassignmentsuspended"))
	//								productiteminventory.setAUTOLOCATIONASSIGNMENT_SUSPENDED(
	//										objproductiteminventory.getString("isautolocassignmentsuspended"));
	//
	//							if (objproductiteminventory.has("preferredstocklevel")
	//									&& !objproductiteminventory.isNull("preferredstocklevel"))
	//								productiteminventory.setPREFEREDSTOCK_LEVEL(
	//										Long.parseLong(objproductiteminventory.getString("preferredstocklevel")));
	//
	//							if (objproductiteminventory.has("leadtime") && !objproductiteminventory.isNull("leadtime"))
	//								productiteminventory.setPURCHASELEAD_TIME(
	//										Long.parseLong(objproductiteminventory.getString("leadtime")));
	//
	//							if (objproductiteminventory.has("safetystocklevel")
	//									&& !objproductiteminventory.isNull("safetystocklevel"))
	//								productiteminventory.setSTAFTYSTOCK_LEVEL(
	//										Long.parseLong(objproductiteminventory.getString("safetystocklevel")));
	//
	//							if (objproductiteminventory.has("atpleadtime")
	//									&& !objproductiteminventory.isNull("atpleadtime"))
	//								productiteminventory.setATPLEAD_TIME(
	//										Long.parseLong(objproductiteminventory.getString("atpleadtime")));
	//
	//							if (objproductiteminventory.has("defaultreturncost")
	//									&& !objproductiteminventory.isNull("defaultreturncost"))
	//								productiteminventory.setDEFAULTRETURN_COST(
	//										Double.parseDouble(objproductiteminventory.getString("defaultreturncost")));
	//
	//							if (objproductiteminventory.has("lastinvtcountdate")
	//									&& !objproductiteminventory.isNull("lastinvtcountdate"))
	//								productiteminventory
	//										.setLASTCOUNT_DATE(dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
	//												.parse(objproductiteminventory.getString("lastinvtcountdate"))));
	//
	//							if (objproductiteminventory.has("nextinvtcountdate")
	//									&& !objproductiteminventory.isNull("nextinvtcountdate"))
	//								productiteminventory
	//										.setNECTCOUNT_DATE(dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
	//												.parse(objproductiteminventory.getString("nextinvtcountdate"))));
	//
	//							if (objproductiteminventory.has("invtcountinterval")
	//									&& !objproductiteminventory.isNull("invtcountinterval"))
	//								productiteminventory.setCOUNT_INTERVAL(
	//										Long.parseLong(objproductiteminventory.getString("invtcountinterval")));
	//
	//							if (objproductiteminventory.has("invtclassification")
	//									&& !objproductiteminventory.isNull("invtclassification"))
	//								productiteminventory.setINVENTORYCLASSIFICTION_ID(
	//										Long.parseLong(objproductiteminventory.getString("invtclassification")));
	//
	//							productiteminventory.setISACTIVE("Y");
	//							productiteminventory.setMODIFIED_BY(requestUser);
	//							productiteminventory.setMODIFIED_WORKSTATION(workstation);
	//							productiteminventory.setMODIFIED_WHEN(dateFormat1.format(date));
	//							productiteminventory = productiteminventoryrepository.saveAndFlush(productiteminventory);
	//						}
	//					}
	//				}
	//
	//				counter = counter + 1;
	//			}
	//
	//			start = start + 100;
	//		}
	//
	//		rtn = mapper.writeValueAsString(products);
	//
	//		apiRequest.setREQUEST_OUTPUT(rtn);
	//		apiRequest.setREQUEST_STATUS("Success");
	//		apirequestdatalogRepository.saveAndFlush(apiRequest);
	//
	//		log.info("Output: " + rtn);
	//		log.info("--------------------------------------------------------");
	//
	//		return new ResponseEntity(rtn, HttpStatus.OK);
	//	}

	//	@RequestMapping(value = "/getfromnetsuite/{id}", method = RequestMethod.GET)
	//	public ResponseEntity getNetSuiteProductByID(@PathVariable Long id,
	//			@RequestHeader(value = "Authorization") String headToken)
	//			throws InterruptedException, IOException, JSONException, ParseException {
	//		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
	//		String rtn, workstation = null;
	//		APIRequestDataLog apiRequest;
	//		
	//		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//		Date date = new Date();
	//
	//		log.info("GET: /productitem/getfromnetsuite/" + id);
	//
	//		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItem.getDatabaseTableID());
	//
	//		if (checkTokenResponse.has("error")) {
	//			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0,
	//					"/product/getfromnetsuite/" + id, null, workstation);
	//			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
	//			apirequestdatalogRepository.saveAndFlush(apiRequest);
	//			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
	//		}
	//
	//		ObjectMapper mapper = new ObjectMapper();
	//		Long requestUser = checkTokenResponse.getLong("user_ID");
	//		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, 
	//				"/product/getfromnetsuite/" + id, null, workstation);
	//	
	//		int start = 0, counter = 1;
	//		List<ProductItem> products = new ArrayList<ProductItem>();
	//		JSONObject objproductitems = new JSONObject();
	//	
	//		JSONArray params = new JSONArray();
	//		JSONObject obj = new JSONObject();
	//		obj.put("key", "id");
	//		obj.put("value", "" + id);
	//		params.put(obj);
	//
	//		int getDataCount = 1;
	//		while (getDataCount <= 20) {
	//			try {
	//				JSONObject productsFromNetSuite = new JSONObject(NetSuiteAPI.GET(685, 1, params));
	//				objproductitems = productsFromNetSuite.getJSONObject("msg");
	//				getDataCount = 21;
	//			} catch (Exception e) {
	//				log.info(e.getMessage());
	//				Thread.sleep(5000);
	//				getDataCount = getDataCount + 1;
	//			}
	//		}
	//
	//		if (objproductitems.has("fields")) {
	//			JSONObject productFromNetSuite = objproductitems.getJSONObject("fields");
	//
	//			log.info(objproductitems.getString("id"));
	//			log.info(productFromNetSuite.getString("itemid"));
	//
	//			List<ProductItem> productitem = productitemrepository.findByCode(productFromNetSuite.getString("itemid"));
	//			if (productitem.size() == 0) {
	//
	//			} else {
	//				Iterator<?> keys = productFromNetSuite.keys();
	//				while (keys.hasNext()) {
	//					String key = (String) keys.next();
	//					String key1 = key;
	//					key = key.replace("_", "");
	//					ProductAttribute productattribute = productattributerepository.findByKey(key);
	//					if (productattribute == null) {
	//						productattribute = new ProductAttribute();
	//						productattribute
	//								.setPRODUCTATTRIBUTECATEGORY_ID(productattributecategoryrepository.findOne((long) 18));
	//						productattribute.setPRODUCTATTRIBUTEORDER_NO((long) 1);
	//						productattribute.setPRODUCTCATEGORY_ID(productcategoryrepository.findOne((long) 0));
	//						productattribute.setPRODUCTATTRIBUTE_NAME("Unknown");
	//						productattribute.setPRODUCTATTRIBUTE_KEY(key);
	//						productattribute.setDATATYPE_ID(lookuprepository.findOne((long) 1));
	//						productattribute.setISREQUIRED("N");
	//						productattribute.setISACTIVE("Y");
	//						productattribute.setMODIFIED_BY(requestUser);
	//						productattribute.setMODIFIED_WORKSTATION(workstation);
	//						productattribute.setMODIFIED_WHEN(dateFormat1.format(date));
	//						productattribute = productattributerepository.saveAndFlush(productattribute);
	//					}
	//
	//					List<ProductItemAttributeValue> productitemattributevalues = productitemattributevaluerepository
	//							.findByAdvancedSearch(productitem.get(0).getPRODUCTITEM_ID(), (long) 0, (long) 0, (long) 0,
	//									productattribute.getPRODUCTATTRIBUTE_ID(), (long) 0, "", (long) 0, (long) 0, "", (long) 0, "");
	//					if (productitemattributevalues.size() > 0) {
	//						productitemattributevalues.get(0)
	//								.setPRODUCTATTRIBUTEITEM_VALUE(productFromNetSuite.getString(key1));
	//						productitemattributevalues.get(0).setISACTIVE("Y");
	//						productitemattributevalues.get(0).setMODIFIED_BY(requestUser);
	//						productitemattributevalues.get(0).setMODIFIED_WORKSTATION(workstation);
	//						productitemattributevalues.get(0).setMODIFIED_WHEN(dateFormat1.format(date));
	//						productitemattributevaluerepository.saveAndFlush(productitemattributevalues.get(0));
	//					} else {
	//						ProductItemAttributeValue productitemattributevalue = new ProductItemAttributeValue();
	//						productitemattributevalue.setPRODUCTITEM_ID(productitem.get(0));
	//						productitemattributevalue.setPRODUCTATTRIBUTE_ID(productattribute);
	//						productitemattributevalue.setPRODUCTATTRIBUTEITEM_VALUE(productFromNetSuite.getString(key1));
	//						productitemattributevalue.setISACTIVE("Y");
	//						productitemattributevalue.setMODIFIED_BY(requestUser);
	//						productitemattributevalue.setMODIFIED_WORKSTATION(workstation);
	//						productitemattributevalue.setMODIFIED_WHEN(dateFormat1.format(date));
	//						productitemattributevalue = productitemattributevaluerepository
	//								.saveAndFlush(productitemattributevalue);
	//					}
	//				}
	//			}
	//
	//			JSONObject productitemsublist = objproductitems.getJSONObject("sublists");
	//
	//			JSONObject productitemprices = productitemsublist.getJSONObject("price1");
	//			int lineNo = 1;
	//			while (productitemprices.has("line " + lineNo)) {
	//				JSONObject productitemprice = productitemprices.getJSONObject("line " + lineNo);
	//				log.info("Line : " + lineNo + ", Price level: " + productitemprice.getString("pricelevelname"));
	//				lineNo = lineNo + 1;
	//
	//				if (!productitemprice.isNull("price[1]")) {
	//					log.info("Price: " + productitemprice.get("price[1]"));
	//					List<ProductItemPriceLevel> productitempricelevels = productitempricelevelrepository
	//							.findByAdvancedSearch((long) 0, productitem.get(0).getPRODUCTITEM_ID(), (long) 0, (long) 0,
	//									(long) 0, (long) 0, "", (long) 1, productitemprice.getString("pricelevel"),
	//									(long) 0, (long) 0);
	//
	//					if (productitempricelevels.size() > 0) {
	//
	//					} else {
	//
	//						ProductItemPriceLevel productitempricelevel = new ProductItemPriceLevel();
	//						Lookup currency = lookuprepository.findByCode("CURRENCY",
	//								productitemprice.getString("currency"));
	//						if (currency == null) {
	//							currency = new Lookup();
	//							currency.setENTITYNAME("CURRENCY");
	//							currency.setCODE(productitemprice.getString("currency"));
	//							currency.setDESCRIPTION(productitemprice.getString("currency"));
	//							currency.setISACTIVE("Y");
	//							currency.setMODIFIED_BY(requestUser);
	//							currency.setMODIFIED_WORKSTATION(workstation);
	//							currency.setMODIFIED_WHEN(dateFormat1.format(date));
	//							currency = lookuprepository.saveAndFlush(currency);
	//						}
	//						productitempricelevel.setCURRENCY_ID(currency);
	//						productitempricelevel.setPRODUCTITEM_ID(productitem.get(0));
	//						Lookup pricelevel = lookuprepository.findByCode("PRICELEVEL",
	//								productitemprice.getString("pricelevel"));
	//						if (pricelevel == null) {
	//							pricelevel = new Lookup();
	//							pricelevel.setENTITYNAME("PRICELEVEL");
	//							pricelevel.setCODE(productitemprice.getString("pricelevel"));
	//							pricelevel.setDESCRIPTION(productitemprice.getString("pricelevelname"));
	//							pricelevel.setISACTIVE("Y");
	//							pricelevel.setMODIFIED_BY(requestUser);
	//							pricelevel.setMODIFIED_WORKSTATION(workstation);
	//							pricelevel.setMODIFIED_WHEN(dateFormat1.format(date));
	//							pricelevel = lookuprepository.saveAndFlush(currency);
	//						}
	//						productitempricelevel.setPRICELEVEL_ID(pricelevel);
	//						productitempricelevel.setPRODUCTITEM_QUANTITY((long) 1);
	//						productitempricelevel
	//								.setPRODUCTITEM_UNITPRICE(Double.parseDouble(productitemprice.getString("price[1]")));
	//						productitempricelevel.setISACTIVE("Y");
	//						productitempricelevel.setMODIFIED_BY(requestUser);
	//						productitempricelevel.setMODIFIED_WORKSTATION(workstation);
	//						productitempricelevel.setMODIFIED_WHEN(dateFormat1.format(date));
	//						productitempricelevel = productitempricelevelrepository.saveAndFlush(productitempricelevel);
	//					}
	//				}
	//			}
	//
	//			JSONObject objproductiteminventories = productitemsublist.getJSONObject("locations");
	//			lineNo = 1;
	//			while (objproductiteminventories.has("line " + lineNo)) {
	//				JSONObject objproductiteminventory = objproductiteminventories.getJSONObject("line " + lineNo);
	//				lineNo = lineNo + 1;
	//
	//				if (objproductiteminventory.getString("location").compareTo("2") == 0) {
	//					List<ProductItemInventory> productiteminventories = productiteminventoryrepository
	//							.findByAdvancedSearch((long) 0, (long) 0, (long) 0, "", (long) 0, "",
	//									productitem.get(0).getPRODUCTITEM_ID());
	//
	//					if (productiteminventories.size() > 0) {
	//
	//						productiteminventories.get(0).setPRODUCTITEM_ID(productitem.get(0));
	//
	//						if (objproductiteminventory.has("locationid") && !objproductiteminventory.isNull("locationid"))
	//							productiteminventories.get(0)
	//									.setLOCATION_ID(Long.parseLong(objproductiteminventory.getString("locationid")));
	//
	//						if (objproductiteminventory.has("quantityonhand")
	//								&& !objproductiteminventory.isNull("quantityonhand"))
	//							productiteminventories.get(0).setQUANTITY_ONHAND(
	//									Double.parseDouble(objproductiteminventory.getString("quantityonhand")));
	//
	//						if (objproductiteminventory.has("quantityonorder")
	//								&& !objproductiteminventory.isNull("quantityonorder"))
	//							productiteminventories.get(0).setQUANTITY_ONORDER(
	//									Long.parseLong(objproductiteminventory.getString("quantityonorder")));
	//
	//						if (objproductiteminventory.has("quantitycommitted")
	//								&& !objproductiteminventory.isNull("quantitycommitted"))
	//							productiteminventories.get(0).setQUANTITY_COMMITTED(
	//									Long.parseLong(objproductiteminventory.getString("quantitycommitted")));
	//
	//						if (objproductiteminventory.has("quantityavailable")
	//								&& !objproductiteminventory.isNull("quantityavailable"))
	//							productiteminventories.get(0).setQUANTITY_AVAILABLE(
	//									Double.parseDouble(objproductiteminventory.getString("quantityavailable")));
	//
	//						if (objproductiteminventory.has("quantitybackordered")
	//								&& !objproductiteminventory.isNull("quantitybackordered"))
	//							productiteminventories.get(0).setQUANTITY_BACKORDERED(
	//									Long.parseLong(objproductiteminventory.getString("quantitybackordered")));
	//
	//						if (objproductiteminventory.has("quantityintransit")
	//								&& !objproductiteminventory.isNull("quantityintransit"))
	//							productiteminventories.get(0).setQUANTITY_INTRANSIT(
	//									Long.parseLong(objproductiteminventory.getString("quantityintransit")));
	//
	//						if (objproductiteminventory.has("qtyintransitexternal")
	//								&& !objproductiteminventory.isNull("qtyintransitexternal"))
	//							productiteminventories.get(0).setQUANTITYEXTERNAL_INTRANSIT(
	//									Long.parseLong(objproductiteminventory.getString("qtyintransitexternal")));
	//
	//						if (objproductiteminventory.has("quantityonhandbase")
	//								&& !objproductiteminventory.isNull("quantityonhandbase"))
	//							productiteminventories.get(0).setQUANTITYBASEUNIT_ONHAND(
	//									Double.parseDouble(objproductiteminventory.getString("quantityonhandbase")));
	//
	//						if (objproductiteminventory.has("quantityavailablebase")
	//								&& !objproductiteminventory.isNull("quantityavailablebase"))
	//							productiteminventories.get(0).setQUANTITYBASEUNIT_AVAILABLE(
	//									Double.parseDouble(objproductiteminventory.getString("quantityavailablebase")));
	//
	//						if (objproductiteminventory.has("onhandvaluemli")
	//								&& !objproductiteminventory.isNull("onhandvaluemli"))
	//							productiteminventories.get(0)
	//									.setVALUE(Double.parseDouble(objproductiteminventory.getString("onhandvaluemli")));
	//
	//						if (objproductiteminventory.has("averagecostmli")
	//								&& !objproductiteminventory.isNull("averagecostmli"))
	//							productiteminventories.get(0).setAVERAGE_COST(
	//									Double.parseDouble(objproductiteminventory.getString("averagecostmli")));
	//
	//						if (objproductiteminventory.has("lastpurchasepricemli")
	//								&& !objproductiteminventory.isNull("lastpurchasepricemli"))
	//							productiteminventories.get(0).setLASTPURCHASE_PRICE(
	//									Double.parseDouble(objproductiteminventory.getString("lastpurchasepricemli")));
	//
	//						if (objproductiteminventory.has("reorderpoint")
	//								&& !objproductiteminventory.isNull("reorderpoint"))
	//							productiteminventories.get(0).setREORDER_POINT(
	//									Long.parseLong(objproductiteminventory.getString("reorderpoint")));
	//
	//						if (objproductiteminventory.has("isautolocassignmentallowed")
	//								&& !objproductiteminventory.isNull("onhandvaluemli"))
	//							productiteminventories.get(0).setAUTOLOCATIONASSIGNMENT_ALLOWED(
	//									objproductiteminventory.getString("isautolocassignmentallowed"));
	//
	//						if (objproductiteminventory.has("isautolocassignmentsuspended")
	//								&& !objproductiteminventory.isNull("onhandvaluemli"))
	//							productiteminventories.get(0).setAUTOLOCATIONASSIGNMENT_SUSPENDED(
	//									objproductiteminventory.getString("isautolocassignmentsuspended"));
	//
	//						if (objproductiteminventory.has("preferredstocklevel")
	//								&& !objproductiteminventory.isNull("preferredstocklevel"))
	//							productiteminventories.get(0).setPREFEREDSTOCK_LEVEL(
	//									Long.parseLong(objproductiteminventory.getString("preferredstocklevel")));
	//
	//						if (objproductiteminventory.has("leadtime") && !objproductiteminventory.isNull("leadtime"))
	//							productiteminventories.get(0).setPURCHASELEAD_TIME(
	//									Long.parseLong(objproductiteminventory.getString("leadtime")));
	//
	//						if (objproductiteminventory.has("safetystocklevel")
	//								&& !objproductiteminventory.isNull("safetystocklevel"))
	//							productiteminventories.get(0).setSTAFTYSTOCK_LEVEL(
	//									Long.parseLong(objproductiteminventory.getString("safetystocklevel")));
	//
	//						if (objproductiteminventory.has("atpleadtime")
	//								&& !objproductiteminventory.isNull("atpleadtime"))
	//							productiteminventories.get(0)
	//									.setATPLEAD_TIME(Long.parseLong(objproductiteminventory.getString("atpleadtime")));
	//
	//						if (objproductiteminventory.has("defaultreturncost")
	//								&& !objproductiteminventory.isNull("defaultreturncost"))
	//							productiteminventories.get(0).setDEFAULTRETURN_COST(
	//									Double.parseDouble(objproductiteminventory.getString("defaultreturncost")));
	//
	//						if (objproductiteminventory.has("lastinvtcountdate")
	//								&& !objproductiteminventory.isNull("lastinvtcountdate"))
	//							productiteminventories.get(0)
	//									.setLASTCOUNT_DATE(dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
	//											.parse(objproductiteminventory.getString("lastinvtcountdate"))));
	//
	//						if (objproductiteminventory.has("nextinvtcountdate")
	//								&& !objproductiteminventory.isNull("nextinvtcountdate"))
	//							productiteminventories.get(0)
	//									.setNECTCOUNT_DATE(dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
	//											.parse(objproductiteminventory.getString("nextinvtcountdate"))));
	//
	//						if (objproductiteminventory.has("invtcountinterval")
	//								&& !objproductiteminventory.isNull("invtcountinterval"))
	//							productiteminventories.get(0).setCOUNT_INTERVAL(
	//									Long.parseLong(objproductiteminventory.getString("invtcountinterval")));
	//
	//						if (objproductiteminventory.has("invtclassification")
	//								&& !objproductiteminventory.isNull("invtclassification"))
	//							productiteminventories.get(0).setINVENTORYCLASSIFICTION_ID(
	//									Long.parseLong(objproductiteminventory.getString("invtclassification")));
	//
	//						productiteminventories.get(0).setISACTIVE("Y");
	//						productiteminventories.get(0).setMODIFIED_BY(requestUser);
	//						productiteminventories.get(0).setMODIFIED_WORKSTATION(workstation);
	//						productiteminventories.get(0).setMODIFIED_WHEN(dateFormat1.format(date));
	//						productiteminventoryrepository.saveAndFlush(productiteminventories.get(0));
	//
	//					} else {
	//
	//						ProductItemInventory productiteminventory = new ProductItemInventory();
	//
	//						productiteminventory.setPRODUCTITEM_ID(productitem.get(0));
	//
	//						if (objproductiteminventory.has("locationid") && !objproductiteminventory.isNull("locationid"))
	//							productiteminventory
	//									.setLOCATION_ID(Long.parseLong(objproductiteminventory.getString("locationid")));
	//
	//						if (objproductiteminventory.has("quantityonhand")
	//								&& !objproductiteminventory.isNull("quantityonhand"))
	//							productiteminventory.setQUANTITY_ONHAND(
	//									Double.parseDouble(objproductiteminventory.getString("quantityonhand")));
	//
	//						if (objproductiteminventory.has("quantityonorder")
	//								&& !objproductiteminventory.isNull("quantityonorder"))
	//							productiteminventory.setQUANTITY_ONORDER(
	//									Long.parseLong(objproductiteminventory.getString("quantityonorder")));
	//
	//						if (objproductiteminventory.has("quantitycommitted")
	//								&& !objproductiteminventory.isNull("quantitycommitted"))
	//							productiteminventory.setQUANTITY_COMMITTED(
	//									Long.parseLong(objproductiteminventory.getString("quantitycommitted")));
	//
	//						if (objproductiteminventory.has("quantityavailable")
	//								&& !objproductiteminventory.isNull("quantityavailable"))
	//							productiteminventory.setQUANTITY_AVAILABLE(
	//									Double.parseDouble(objproductiteminventory.getString("quantityavailable")));
	//
	//						if (objproductiteminventory.has("quantitybackordered")
	//								&& !objproductiteminventory.isNull("quantitybackordered"))
	//							productiteminventory.setQUANTITY_BACKORDERED(
	//									Long.parseLong(objproductiteminventory.getString("quantitybackordered")));
	//
	//						if (objproductiteminventory.has("quantityintransit")
	//								&& !objproductiteminventory.isNull("quantityintransit"))
	//							productiteminventory.setQUANTITY_INTRANSIT(
	//									Long.parseLong(objproductiteminventory.getString("quantityintransit")));
	//
	//						if (objproductiteminventory.has("qtyintransitexternal")
	//								&& !objproductiteminventory.isNull("qtyintransitexternal"))
	//							productiteminventory.setQUANTITYEXTERNAL_INTRANSIT(
	//									Long.parseLong(objproductiteminventory.getString("qtyintransitexternal")));
	//
	//						if (objproductiteminventory.has("quantityonhandbase")
	//								&& !objproductiteminventory.isNull("quantityonhandbase"))
	//							productiteminventory.setQUANTITYBASEUNIT_ONHAND(
	//									Double.parseDouble(objproductiteminventory.getString("quantityonhandbase")));
	//
	//						if (objproductiteminventory.has("quantityavailablebase")
	//								&& !objproductiteminventory.isNull("quantityavailablebase"))
	//							productiteminventory.setQUANTITYBASEUNIT_AVAILABLE(
	//									Double.parseDouble(objproductiteminventory.getString("quantityavailablebase")));
	//
	//						if (objproductiteminventory.has("onhandvaluemli")
	//								&& !objproductiteminventory.isNull("onhandvaluemli"))
	//							productiteminventory
	//									.setVALUE(Double.parseDouble(objproductiteminventory.getString("onhandvaluemli")));
	//
	//						if (objproductiteminventory.has("averagecostmli")
	//								&& !objproductiteminventory.isNull("averagecostmli"))
	//							productiteminventory.setAVERAGE_COST(
	//									Double.parseDouble(objproductiteminventory.getString("averagecostmli")));
	//
	//						if (objproductiteminventory.has("lastpurchasepricemli")
	//								&& !objproductiteminventory.isNull("lastpurchasepricemli"))
	//							productiteminventory.setLASTPURCHASE_PRICE(
	//									Double.parseDouble(objproductiteminventory.getString("lastpurchasepricemli")));
	//
	//						if (objproductiteminventory.has("reorderpoint")
	//								&& !objproductiteminventory.isNull("reorderpoint"))
	//							productiteminventory.setREORDER_POINT(
	//									Long.parseLong(objproductiteminventory.getString("reorderpoint")));
	//
	//						if (objproductiteminventory.has("isautolocassignmentallowed")
	//								&& !objproductiteminventory.isNull("isautolocassignmentallowed"))
	//							productiteminventory.setAUTOLOCATIONASSIGNMENT_ALLOWED(
	//									objproductiteminventory.getString("isautolocassignmentallowed"));
	//
	//						if (objproductiteminventory.has("isautolocassignmentsuspended")
	//								&& !objproductiteminventory.isNull("isautolocassignmentsuspended"))
	//							productiteminventory.setAUTOLOCATIONASSIGNMENT_SUSPENDED(
	//									objproductiteminventory.getString("isautolocassignmentsuspended"));
	//
	//						if (objproductiteminventory.has("preferredstocklevel")
	//								&& !objproductiteminventory.isNull("preferredstocklevel"))
	//							productiteminventory.setPREFEREDSTOCK_LEVEL(
	//									Long.parseLong(objproductiteminventory.getString("preferredstocklevel")));
	//
	//						if (objproductiteminventory.has("leadtime") && !objproductiteminventory.isNull("leadtime"))
	//							productiteminventory.setPURCHASELEAD_TIME(
	//									Long.parseLong(objproductiteminventory.getString("leadtime")));
	//
	//						if (objproductiteminventory.has("safetystocklevel")
	//								&& !objproductiteminventory.isNull("safetystocklevel"))
	//							productiteminventory.setSTAFTYSTOCK_LEVEL(
	//									Long.parseLong(objproductiteminventory.getString("safetystocklevel")));
	//
	//						if (objproductiteminventory.has("atpleadtime")
	//								&& !objproductiteminventory.isNull("atpleadtime"))
	//							productiteminventory
	//									.setATPLEAD_TIME(Long.parseLong(objproductiteminventory.getString("atpleadtime")));
	//
	//						if (objproductiteminventory.has("defaultreturncost")
	//								&& !objproductiteminventory.isNull("defaultreturncost"))
	//							productiteminventory.setDEFAULTRETURN_COST(
	//									Double.parseDouble(objproductiteminventory.getString("defaultreturncost")));
	//
	//						if (objproductiteminventory.has("lastinvtcountdate")
	//								&& !objproductiteminventory.isNull("lastinvtcountdate"))
	//							productiteminventory.setLASTCOUNT_DATE(dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
	//									.parse(objproductiteminventory.getString("lastinvtcountdate"))));
	//
	//						if (objproductiteminventory.has("nextinvtcountdate")
	//								&& !objproductiteminventory.isNull("nextinvtcountdate"))
	//							productiteminventory.setNECTCOUNT_DATE(dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
	//									.parse(objproductiteminventory.getString("nextinvtcountdate"))));
	//
	//						if (objproductiteminventory.has("invtcountinterval")
	//								&& !objproductiteminventory.isNull("invtcountinterval"))
	//							productiteminventory.setCOUNT_INTERVAL(
	//									Long.parseLong(objproductiteminventory.getString("invtcountinterval")));
	//
	//						if (objproductiteminventory.has("invtclassification")
	//								&& !objproductiteminventory.isNull("invtclassification"))
	//							productiteminventory.setINVENTORYCLASSIFICTION_ID(
	//									Long.parseLong(objproductiteminventory.getString("invtclassification")));
	//
	//						productiteminventory.setISACTIVE("Y");
	//						productiteminventory.setMODIFIED_BY(requestUser);
	//						productiteminventory.setMODIFIED_WORKSTATION(workstation);
	//						productiteminventory.setMODIFIED_WHEN(dateFormat1.format(date));
	//						productiteminventory = productiteminventoryrepository.saveAndFlush(productiteminventory);
	//					}
	//				}
	//			}
	//		}
	//		rtn = mapper.writeValueAsString(products);
	//
	//		apiRequest.setREQUEST_OUTPUT(rtn);
	//		apiRequest.setREQUEST_STATUS("Success");
	//		apirequestdatalogRepository.saveAndFlush(apiRequest);
	//
	//		log.info("Output: " + rtn);
	//		log.info("--------------------------------------------------------");
	//
	//		return new ResponseEntity(rtn, HttpStatus.OK);
	//	}
	//
};
