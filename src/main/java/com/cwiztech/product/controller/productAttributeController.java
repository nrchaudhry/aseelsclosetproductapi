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
import com.cwiztech.product.model.ProductAttribute;
import com.cwiztech.product.repository.productAttributeRepository;
import com.cwiztech.services.ServiceCall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productattribute")

public class productAttributeController {

	private static final Logger log = LoggerFactory.getLogger(productAttributeController.class);

	@Autowired
	private productAttributeRepository productattributerepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productattribute", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductAttribute> productattributes = productattributerepository.findActive();
		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productattribute/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductAttribute> productattributes = productattributerepository.findAll();

		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productattribute/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductAttribute productattribute = productattributerepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, productattribute , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productattribute/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productattribute_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductattributes = jsonObj.getJSONArray("productattributes");
		for (int i=0; i<jsonproductattributes.length(); i++) {
			productattribute_IDS.add((Integer) jsonproductattributes.get(i));
		}
		List<ProductAttribute> productattributes = new ArrayList<ProductAttribute>();
		if (jsonproductattributes.length()>0)
			productattributes = productattributerepository.findByIDs(productattribute_IDS);

		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productattribute/notin/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productattribute_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductattributes = jsonObj.getJSONArray("productattributes");
		for (int i=0; i<jsonproductattributes.length(); i++) {
			productattribute_IDS.add((Integer) jsonproductattributes.get(i));
		}
		List<ProductAttribute> productattributes = new ArrayList<ProductAttribute>();
		if (jsonproductattributes.length()>0)
			productattributes = productattributerepository.findByNotInIDs(productattribute_IDS);

		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productattribute", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {

		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productattribute/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productattribute", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductAttributes, JSONObject jsonProductAttribute, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductAttribute> productattributes = new ArrayList<ProductAttribute>();
		if (jsonProductAttribute != null) {
			jsonProductAttributes = new JSONArray();
			jsonProductAttributes.put(jsonProductAttribute);
		}
		log.info(jsonProductAttributes.toString());

		for (int a=0; a<jsonProductAttributes.length(); a++) {
			JSONObject jsonObj = jsonProductAttributes.getJSONObject(a);
			ProductAttribute productattribute = new ProductAttribute();
			long productattributeid = 0;

			if (jsonObj.has("productattribute_ID")) {
				productattributeid = jsonObj.getLong("productattribute_ID");
				if (productattributeid != 0) {
					productattribute = productattributerepository.findOne(productattributeid);

					if (productattribute == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductAttribute Data!", apiRequest, true), HttpStatus.OK);
				}
			}

			if (productattributeid == 0) {
				if (!jsonObj.has("attribute_ID") || jsonObj.isNull("attribute_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attribute_ID is missing", apiRequest, true), HttpStatus.OK);

				if (!jsonObj.has("attributeorder_NO") || jsonObj.isNull("attributeorder_NO"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attributeorder_NO is missing", apiRequest, true), HttpStatus.OK);

				if (!jsonObj.has("attributecategory_ID") || jsonObj.isNull("attributecategory_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attributecategory_ID is missing", apiRequest, true), HttpStatus.OK);

				if (!jsonObj.has("productcategory_ID") || jsonObj.isNull("productcategory_ID") || !jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productcategory_ID/product_ID is missing", apiRequest, true), HttpStatus.OK);

				if (!jsonObj.has("isrequired") || jsonObj.isNull("isrequired"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "isrequired is missing", apiRequest, true), HttpStatus.OK);
			}

			if (jsonObj.has("attribute_ID") && !jsonObj.isNull("attribute_ID"))
				productattribute.setATTRIBUTE_ID(jsonObj.getLong("attribute_ID"));

			if (jsonObj.has("attributeorder_NO") && !jsonObj.isNull("attributeorder_NO"))
				productattribute.setATTRIBUTEORDER_NO(jsonObj.getLong("attributeorder_NO"));

			if (jsonObj.has("attributecategory_ID") && !jsonObj.isNull("attributecategory_ID"))
				productattribute.setATTRIBUTECATEGORY_ID(jsonObj.getLong("attributecategory_ID"));

			if (jsonObj.has("productcategory_ID") && !jsonObj.isNull("productcategory_ID"))
				productattribute.setPRODUCTCATEGORY_ID(jsonObj.getLong("productcategory_ID"));

			if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID"))
				productattribute.setPRODUCT_ID(jsonObj.getLong("product_ID"));

			if (jsonObj.has("isrequired") && !jsonObj.isNull("isrequired"))
				productattribute.setISREQUIRED(jsonObj.getString("isrequired"));

			if (jsonObj.has("showinlist") && !jsonObj.isNull("showinlist"))
				productattribute.setSHOWINLIST(jsonObj.getString("showinlist"));

			if (productattributeid == 0)
				productattribute.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				productattribute.setISACTIVE(jsonObj.getString("isactive"));

			productattribute.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			productattribute.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			productattribute.setMODIFIED_WHEN(dateFormat1.format(date));
			productattributes.add(productattribute);
		}

		for (int a=0; a<productattributes.size(); a++) {
			ProductAttribute productattribute = productattributes.get(a);
			productattribute = productattributerepository.saveAndFlush(productattribute);
			productattributes.get(a).setPRODUCTATTRIBUTE_ID(productattribute.getPRODUCTATTRIBUTE_ID());
		}

		ResponseEntity responseentity;
		if (jsonProductAttribute != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productattributes.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productattribute/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductAttribute productattribute = productattributerepository.findOne(id);
		productattributerepository.delete(productattribute);

		return new ResponseEntity(getAPIResponse(null, productattribute , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productattribute/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject productattribute = new JSONObject();
		productattribute.put("id", id);
		productattribute.put("isactive", "N");

		return insertupdateAll(null, productattribute, apiRequest);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productattribute/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);

		List<ProductAttribute> productattributes = ((active == true)
				? productattributerepository.findBySearch("%" + jsonObj.getString("search") + "%")
						: productattributerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productattribute/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductAttribute> productattributes = new ArrayList<ProductAttribute>();
		JSONObject jsonObj = new JSONObject(data);

		JSONArray searchObject = new JSONArray();
		List<Integer> attributecategory_IDS = new ArrayList<Integer>(); 
		List<Integer> productcategory_IDS = new ArrayList<Integer>(); 
		List<Integer> attribute_IDS = new ArrayList<Integer>(); 
		List<Integer> product_IDS = new ArrayList<Integer>(); 

		attribute_IDS.add((int) 0);
		product_IDS.add((int) 0);
		attributecategory_IDS.add((int) 0);
		productcategory_IDS.add((int) 0);

		long attributecategory_ID = 0 , productcategory_ID = 0, attribute_ID = 0 , product_ID = 0;


		boolean isWithDetail = true;
		if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
			isWithDetail = jsonObj.getBoolean("iswithdetail");
		}
		jsonObj.put("iswithdetail", false);

		if (jsonObj.has("attributecategory_ID") && !jsonObj.isNull("attributecategory_ID") && jsonObj.getLong("attributecategory_ID") != 0) {
			attributecategory_ID = jsonObj.getLong("attributecategory_ID");
			attributecategory_IDS.add((int) attributecategory_ID);
		} else if (jsonObj.has("attributecategory") && !jsonObj.isNull("attributecategory") && jsonObj.getLong("attributecategory") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("attributecategory/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("attributecategory/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
			}

			attributecategory_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				attributecategory_IDS.add((int) searchObject.getJSONObject(i).getLong("attributecategory_ID"));
			}
		}

		if (jsonObj.has("productcategory_ID") && !jsonObj.isNull("productcategory_ID") && jsonObj.getLong("productcategory_ID") != 0) {
			productcategory_ID = jsonObj.getLong("productcategory_ID");
			productcategory_IDS.add((int) productcategory_ID);
		} else if (jsonObj.has("productcategory") && !jsonObj.isNull("productcategory") && jsonObj.getLong("productcategory") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("productcategory/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("productcategory/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
			}

			productcategory_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				productcategory_IDS.add((int) searchObject.getJSONObject(i).getLong("productcategory_ID"));
			}
		}

		if (jsonObj.has("attribute_ID") && !jsonObj.isNull("attribute_ID") && jsonObj.getLong("attribute_ID") != 0) {
			attribute_ID = jsonObj.getLong("attribute_ID");
			attribute_IDS.add((int) attribute_ID);
		} else if (jsonObj.has("attribute") && !jsonObj.isNull("attribute") && jsonObj.getLong("attribute") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("attribute/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("attribute/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
			}

			attribute_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				attribute_IDS.add((int) searchObject.getJSONObject(i).getLong("attribute_ID"));
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

		productattributes = ((active == true)
				? productattributerepository.findByAdvancedSearch(productcategory_ID,productcategory_IDS, product_ID,product_IDS, attributecategory_ID,attributecategory_IDS, attribute_ID, attribute_IDS)
						: productattributerepository.findAllByAdvancedSearch(productcategory_ID,productcategory_IDS, product_ID,product_IDS, attributecategory_ID,attributecategory_IDS, attribute_ID, attribute_IDS));		


		//		if (jsonObj.has("withattributevalue")) {
		//			JSONArray productattributewithvalue = new JSONArray();
		//			for (int i=0; i<productattribute.size(); i++) {
		//				JSONObject obj = new JSONObject();
		//				JSONObject jsonproduct = new JSONObject(ServiceCall.GET("product/"+productattribute.get(i).getPRODUCT_ID(), apiRequest.getREQUEST_OUTPUT()));
		//				JSONObject jsonproductcategory = new JSONObject(ServiceCall.GET("productcategory/"+productattribute.get(i).getPRODUCTCATEGORY_ID(), apiRequest.getREQUEST_OUTPUT()));
		//				obj.put("productattribute_ID", productattribute.get(i).getPRODUCTATTRIBUTE_ID());
		//				obj.put("product_ID", productattribute.get(i).getPRODUCT_ID());
		//				obj.put("product_NAME", jsonproduct.getString("product_NAME").replace("\"", ""));
		//				obj.put("product_DESC", jsonproduct.getString("product_DESC").replace("\"", ""));
		//				obj.put("productcategory_ID", productattribute.get(i).getPRODUCTCATEGORY_ID());
		//				obj.put("productcategory_NAME", jsonproductcategory.getString("productcategory_NAME").replace("\"", ""));
		//				obj.put("productcategory_DESC", jsonproductcategory.getString("productcategory_DESC").replace("\"", ""));
		//				obj.put("productattribute_NAME", productattribute.get(i).getPRODUCTATTRIBUTE_NAME());
		//				obj.put("productattribute_DESC", productattribute.get(i).getPRODUCTATTRIBUTE_DESC());
		//				obj.put("datatype_CODE", productattribute.get(i).getDATATYPE_ID().getCODE());
		//				obj.put("datatype_ENTITYSTATUS", productattribute.get(i).getDATATYPE_ID().getENTITY_STATUS());
		//
		//				if (productattribute.get(i).getDATATYPE_ID().getCODE().compareTo("4") == 0 || productattribute.get(i).getDATATYPE_ID().getCODE().compareTo("6") == 0) {
		//					List<ProductAttributeValue> productattributevalue = productattributevaluerepository.findByAdvancedSearch(productattribute.get(i).getPRODUCTATTRIBUTE_ID(), (long) 0);
		//					JSONArray productattributevalues = new JSONArray();
		//					for (int j=0; j<productattributevalue.size(); j++) {
		//						JSONObject objattributevalues = new JSONObject();
		//						objattributevalues.put("productattributevalue_ID", productattributevalue.get(j).getPRODUCTATTRIBUTEVALUE_ID());
		//						objattributevalues.put("productattributevalueparent_ID", productattributevalue.get(j).getPRODUCTATTRIBUTEVALUEPARENT_ID());
		//						objattributevalues.put("productattribute_VALUE", productattributevalue.get(j).getPRODUCTATTRIBUTE_VALUE());
		//						productattributevalues.put(objattributevalues);
		//					}
		//					obj.put("productattribute_VALUE", productattributevalues);
		//				}
		//				productattributewithvalue.put(obj);
		//			}
		//			rtn = productattributewithvalue.toString();
		//		}
		//		else {
		//			rtn = mapper.writeValueAsString(productattribute);
		//		}

		return new ResponseEntity(getAPIResponse(productattributes, null , null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}

	String getAPIResponse(List<ProductAttribute> productattributes, ProductAttribute productattribute , JSONArray jsonProductAttributes, JSONObject jsonProductAttribute, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "ProductAttribute", message).toString();
		} else {
			if ((productattributes != null || productattribute != null) && isWithDetail == true) {
				if (productattribute != null)
					productattributes = new ArrayList<ProductAttribute>();
					productattributes.add(productattribute);

				if (productattributes.size()>0) {
					List<Integer> attributeList = new ArrayList<Integer>();
					List<Integer> attributecategoryList = new ArrayList<Integer>();
					List<Integer> productList = new ArrayList<Integer>();
					List<Integer> productcategoryList = new ArrayList<Integer>();					

					for (int i=0; i<productattributes.size(); i++) {
						if (productattributes.get(i).getATTRIBUTE_ID() != null) {
							attributeList .add(Integer.parseInt(productattributes.get(i).getATTRIBUTE_ID().toString()));
						}

						if (productattributes.get(i).getATTRIBUTECATEGORY_ID() != null) {
							attributecategoryList.add(Integer.parseInt(productattributes.get(i).getATTRIBUTECATEGORY_ID().toString()));
						}

						if (productattributes.get(i).getPRODUCT_ID() != null) {
							productList.add(Integer.parseInt(productattributes.get(i).getPRODUCT_ID().toString()));
						}

						if (productattributes.get(i).getPRODUCTCATEGORY_ID() != null) {
							productcategoryList.add(Integer.parseInt(productattributes.get(i).getPRODUCTCATEGORY_ID().toString()));
						}
					}

					CompletableFuture<JSONArray> attributeFuture = CompletableFuture.supplyAsync(() -> {
						if (attributeList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("attribute/ids", "{attributes: "+attributeList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});

					CompletableFuture<JSONArray> attributecategoryFuture = CompletableFuture.supplyAsync(() -> {
						if (attributecategoryList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("attributecategory/ids", "{attributecategories: "+attributecategoryList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});

					CompletableFuture<JSONArray>productFuture = CompletableFuture.supplyAsync(() -> {
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

					CompletableFuture<JSONArray>productcategoryFuture = CompletableFuture.supplyAsync(() -> {
						if (productcategoryList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("productcategory/ids", "{productcategories: "+productcategoryList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});

					// Wait until all futures complete
					CompletableFuture<Void> allDone =
							CompletableFuture.allOf(attributeFuture,attributecategoryFuture,productFuture,productcategoryFuture);

					// Block until all are done
					allDone.join();

					JSONArray attributeObject = new JSONArray(attributeFuture.toString());
					JSONArray attributecategoryObject = new JSONArray(attributecategoryFuture.toString());
					JSONArray productObject = new JSONArray(productFuture.toString());
					JSONArray productcategoryObject = new JSONArray(productcategoryFuture.toString());

					for (int i=0; i<productattributes.size(); i++) {
						for (int j=0; j<attributeObject.length(); j++) {
							JSONObject attribute = attributeObject.getJSONObject(j);
							if (productattributes.get(i).getATTRIBUTE_ID() != null && productattributes.get(i).getATTRIBUTE_ID() == attribute.getLong("ATTRIBUTE_ID")) {
								productattributes.get(i).setATTRIBUTE_DETAIL(attribute.toString());
							}
						}
						for (int j=0; j<attributecategoryObject.length(); j++) {
							JSONObject attributecategory = attributecategoryObject.getJSONObject(j);
							if (productattributes.get(i).getATTRIBUTECATEGORY_ID() != null && productattributes.get(i).getATTRIBUTECATEGORY_ID() == attributecategory.getLong("ATTRIBUTECATEGORY_ID")) {
								productattributes.get(i).setATTRIBUTECATEGORY_DETAIL(attributecategory.toString());
							}
						}
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if (productattributes.get(i).getPRODUCT_ID() != null && productattributes.get(i).getPRODUCT_ID() == product.getLong("PRODUCT_ID")) {
								productattributes.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
						for (int j=0; j<productcategoryObject.length(); j++) {
							JSONObject productcategory = productcategoryObject.getJSONObject(j);
							if (productattributes.get(i).getPRODUCTCATEGORY_ID() != null && productattributes.get(i).getPRODUCTCATEGORY_ID() == productcategory.getLong("PRODUCTCATEGORY_ID")) {
								productattributes.get(i).setPRODUCTCATEGORY_DETAIL(productcategory.toString());
							}
						}
					}
					if (productattribute != null)
						rtnAPIResponse = mapper.writeValueAsString(productattributes.get(0));
					else	
						rtnAPIResponse = mapper.writeValueAsString(productattributes);
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

				} else if (productattribute != null && isWithDetail == false) {
					rtnAPIResponse = mapper.writeValueAsString(productattribute);
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

				} else if (productattributes != null && isWithDetail == false) {
					rtnAPIResponse = mapper.writeValueAsString(productattributes);
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

				} else if (jsonProductAttributes != null) {
					rtnAPIResponse = jsonProductAttributes.toString();
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

				} else if (jsonProductAttribute != null) {
					rtnAPIResponse = jsonProductAttribute.toString();
					apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
				}
			}
		}
		return rtnAPIResponse;
	}
}	


