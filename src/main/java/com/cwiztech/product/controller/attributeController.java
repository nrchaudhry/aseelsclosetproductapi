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
import com.cwiztech.product.model.Attribute;
import com.cwiztech.product.repository.attributeRepository;
import com.cwiztech.services.ServiceCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/attribute")

public class attributeController {
	
	private static final Logger log = LoggerFactory.getLogger(attributeController.class);

	@Autowired
	private attributeRepository attributerepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/attribute", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<Attribute> attributes = attributerepository.findActive();
		return new ResponseEntity(getAPIResponse(attributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/attribute/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<Attribute> attributes = attributerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(attributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/attribute/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		Attribute attribute = attributerepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, attribute , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/attribute/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		List<Integer> attribute_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonattributes = jsonObj.getJSONArray("attributes");
		for (int i=0; i<jsonattributes.length(); i++) {
			attribute_IDS.add((Integer) jsonattributes.get(i));
		}
		List<Attribute> attributes = new ArrayList<Attribute>();
		if (jsonattributes.length()>0)
			attributes = attributerepository.findByIDs(attribute_IDS);
		
		return new ResponseEntity(getAPIResponse(attributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/attribute", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/attribute/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/attribute", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonAttributes, JSONObject jsonAttribute, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<Attribute> attributes = new ArrayList<Attribute>();
		if (jsonAttribute != null) {
			jsonAttributes = new JSONArray();
			jsonAttributes.put(jsonAttribute);
		}
		log.info(jsonAttributes.toString());
		
		for (int a=0; a<jsonAttributes.length(); a++) {
			JSONObject jsonObj = jsonAttributes.getJSONObject(a);
			Attribute attribute = new Attribute();
			long attributeid = 0;

			if (jsonObj.has("attribute_ID")) {
				attributeid = jsonObj.getLong("attribute_ID");
				if (attributeid != 0) {
					attribute = attributerepository.findOne(attributeid);
					
					if (attribute == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid Attribute Data!", apiRequest, true), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (attributeid == 0) {
				if (!jsonObj.has("attribute_NAME") || jsonObj.isNull("attribute_NAME"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attribute_NAME is missing", apiRequest, true), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("attribute_KEY") || jsonObj.isNull("attribute_KEY"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "attribute_KEY is missing", apiRequest, true), HttpStatus.BAD_REQUEST);
					
				if (!jsonObj.has("datatype_ID") || jsonObj.isNull("datatype_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "datatype_ID is missing", apiRequest, true), HttpStatus.BAD_REQUEST);
			}

		if (jsonObj.has("attribute_NAME") && !jsonObj.isNull("attribute_NAME"))
			attribute.setATTRIBUTE_NAME(jsonObj.getString("attribute_NAME"));
		
		if (jsonObj.has("attribute_KEY") && !jsonObj.isNull("attribute_KEY"))
			attribute.setATTRIBUTE_KEY(jsonObj.getString("attribute_KEY"));
		
		if (jsonObj.has("attribute_DESCRIPTION") && !jsonObj.isNull("attribute_DESCRIPTION"))
			attribute.setATTRIBUTE_DESCRIPTION(jsonObj.getString("attribute_DESCRIPTION"));
		
		if (jsonObj.has("datatype_ID") && !jsonObj.isNull("datatype_ID"))
			attribute.setDATATYPE_ID(jsonObj.getLong("datatype_ID"));
		 else if (jsonObj.has("datatype_CODE") && !jsonObj.isNull("datatype_CODE")) {
	  			JSONObject datatype = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'DATATYPE', code: "+jsonObj.getString("datatype_CODE")+"}", apiRequest.getString("access_TOKEN"), true));
	  			if (datatype != null)
	  				attribute.setDATATYPE_ID(datatype.getLong("id"));
		 }
		
		if (jsonObj.has("input_PATTERN") && !jsonObj.isNull("input_PATTERN"))
			attribute.setINPUT_PATTERN(jsonObj.getString("input_PATTERN"));	
		
		if (attributeid == 0)
			attribute.setISACTIVE("Y");
		else if (jsonObj.has("isactive"))
			attribute.setISACTIVE(jsonObj.getString("isactive"));

			attribute.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			attribute.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
			attribute.setMODIFIED_WHEN(dateFormat1.format(date));
			attributes.add(attribute);
		}
		
		for (int a=0; a<attributes.size(); a++) {
			Attribute attribute = attributes.get(a);
			attribute = attributerepository.saveAndFlush(attribute);
			attributes.get(a).setATTRIBUTE_ID(attribute.getATTRIBUTE_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonAttribute != null)
			responseentity = new ResponseEntity(getAPIResponse(null, attributes.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(attributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/attribute/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		Attribute attribute = attributerepository.findOne(id);
		attributerepository.delete(attribute);
		
		return new ResponseEntity(getAPIResponse(null, attribute , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/attribute/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);
		
		JSONObject attribute = new JSONObject();
		attribute.put("id", id);
		attribute.put("isactive", "N");
		
		return insertupdateAll(null, attribute, apiRequest);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/attribute/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<Attribute> attributes = ((active == true)
				? attributerepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: attributerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(attributes, null , null, null, null, apiRequest, true), HttpStatus.OK);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/attribute/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.BAD_REQUEST);
		
		String rtn = null;
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObj = new JSONObject(data);
		
		   JSONArray searchObject = new JSONArray();
	        List<Integer> datatype_IDS = new ArrayList<Integer>(); 

	        datatype_IDS.add((int) 0);
	        
		long datatype_ID = 0;
		
       boolean isWithDetail = true;
       if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
           isWithDetail = jsonObj.getBoolean("iswithdetail");
       }
       jsonObj.put("iswithdetail", false);
		
       if (jsonObj.has("datatype_ID") && !jsonObj.isNull("datatype_ID") && jsonObj.getLong("datatype_ID") != 0) {
           datatype_ID = jsonObj.getLong("datatype_ID");
           datatype_IDS.add((int) datatype_ID);
       } else if (jsonObj.has("datatype") && !jsonObj.isNull("datatype") && jsonObj.getLong("datatype") != 0) {
           if (active == true) {
               searchObject = new JSONArray(ServiceCall.POST("datatype/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
           } else {
               searchObject = new JSONArray(ServiceCall.POST("datatype/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
           }

           datatype_ID = searchObject.length();
           for (int i=0; i<searchObject.length(); i++) {
               datatype_IDS.add((int) searchObject.getJSONObject(i).getLong("datatype_ID"));
           }
       }

		 else if (jsonObj.has("datatype_CODE") && !jsonObj.isNull("datatype_CODE")) {
	  			JSONObject datatype = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'DATATYPE', code: "+jsonObj.getString("datatype_CODE")+"}", apiRequest.getString("access_TOKEN"), true));
	  			if (datatype != null)
	  				datatype_ID = datatype.getLong("id");
		 }
		List<Attribute> attribute = ((active == true)
				? attributerepository.findByAdvancedSearch(datatype_ID, datatype_IDS)
				: attributerepository.findAllByAdvancedSearch(datatype_ID, datatype_IDS));
		
		rtn = mapper.writeValueAsString(attribute);

		return new ResponseEntity(rtn, HttpStatus.OK);
	}

	String getAPIResponse(List<Attribute> attributes, Attribute attribute , JSONArray jsonAttributes, JSONObject jsonAttribute, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "Attribute", message).toString();
		} else {
			if (attribute != null && isWithDetail == true) {
				if (attribute.getDATATYPE_ID() != null) {
				JSONObject datatype = new JSONObject(ServiceCall.GET("lookup/"+attribute.getDATATYPE_ID(), apiRequest.getString("access_TOKEN"), true));
				attribute.setDATATYPE_DETAIL(datatype.toString());
			   }
				rtnAPIResponse = mapper.writeValueAsString(attribute);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (attributes != null && isWithDetail == true) {
				if (attributes.size()>0) {
				List<Integer> datatypeList = new ArrayList<Integer>();
				for (int i=0; i<attributes.size(); i++) {
					datatypeList.add(Integer.parseInt(attributes.get(i).getDATATYPE_ID().toString()));
				}
				JSONArray datatypeObject = new JSONArray(ServiceCall.POST("lookup/ids", "{lookups: "+datatypeList+"}", apiRequest.getString("access_TOKEN"), true));

				for (int i=0; i<attributes.size(); i++) {
					for (int j=0; j<datatypeObject.length(); j++) {
						JSONObject datatype = datatypeObject.getJSONObject(j);
						if (attributes.get(i).getDATATYPE_ID() != null && attributes.get(i).getDATATYPE_ID() == datatype.getLong("id") ) {
							attributes.get(i).setDATATYPE_DETAIL(datatype.toString());
						}
					}		
			    }
		    }
			rtnAPIResponse = mapper.writeValueAsString(attributes);
			apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonAttributes != null) {
				rtnAPIResponse = jsonAttributes.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
		
			} else if (jsonAttribute != null) {
				rtnAPIResponse = jsonAttribute.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}		
		}
		
		return rtnAPIResponse;
	}
}

	
	


