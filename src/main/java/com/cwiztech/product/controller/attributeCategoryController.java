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
import com.cwiztech.product.model.AttributeCategory;
import com.cwiztech.product.repository.attributeCategoryRepository;
import com.cwiztech.services.ServiceCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cwiztech.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/attributecategory")

public class attributeCategoryController {

    private static final Logger log = LoggerFactory.getLogger(attributeCategoryController.class);

    @Autowired
    private attributeCategoryRepository attributecategoryrepository;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
        JSONObject apiRequest = AccessToken.checkToken("GET", "/attributecategory", null, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        List<AttributeCategory> attributecategories = attributecategoryrepository.findActive();
        return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, true), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
        JSONObject apiRequest = AccessToken.checkToken("GET", "/attributecategory/all", null, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        List<AttributeCategory> attributecategories = attributecategoryrepository.findAll();

        return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, true), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
        JSONObject apiRequest = AccessToken.checkToken("GET", "/attributecategory/"+id, null, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        AttributeCategory attributecategory = attributecategoryrepository.findOne(id);

        return new ResponseEntity(getAPIResponse(null, attributecategory , null, null, null, apiRequest, true), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/ids", method = RequestMethod.POST)
    public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
            throws JsonProcessingException, JSONException, ParseException {
        JSONObject apiRequest = AccessToken.checkToken("POST", "/attributecategory/ids", data, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        List<Integer> attributecategory_IDS = new ArrayList<Integer>(); 
        JSONObject jsonObj = new JSONObject(data);
        JSONArray jsonattributecategories = jsonObj.getJSONArray("attributecategories");
        for (int i=0; i<jsonattributecategories.length(); i++) {
            attributecategory_IDS.add((Integer) jsonattributecategories.get(i));
        }
        List<AttributeCategory> attributecategories = new ArrayList<AttributeCategory>();
        if (jsonattributecategories.length()>0)
            attributecategories = attributecategoryrepository.findByIDs(attributecategory_IDS);

        return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, true), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
    public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
            throws JsonProcessingException, JSONException, ParseException {
        JSONObject apiRequest = AccessToken.checkToken("POST", "/attributecategory/notin/ids", data, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        List<Integer> attributecategory_IDS = new ArrayList<Integer>(); 
        JSONObject jsonObj = new JSONObject(data);
        JSONArray jsonattributecategories = jsonObj.getJSONArray("attributecategories");
        for (int i=0; i<jsonattributecategories.length(); i++) {
            attributecategory_IDS.add((Integer) jsonattributecategories.get(i));
        }
        List<AttributeCategory> attributecategories = new ArrayList<AttributeCategory>();
        if (jsonattributecategories.length()>0)
            attributecategories = attributecategoryrepository.findByNotInIDs(attributecategory_IDS);

        return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, true), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
            throws JsonProcessingException, JSONException, ParseException {
        JSONObject apiRequest = AccessToken.checkToken("POST", "/attributecategory", data, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        return insertupdateAll(null, new JSONObject(data), apiRequest);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
            throws JsonProcessingException, JSONException, ParseException {

        JSONObject apiRequest = AccessToken.checkToken("PUT", "/attributecategory/"+id, data, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
        JSONObject jsonObj = new JSONObject(data);
        jsonObj.put("id", id);

        return insertupdateAll(null, jsonObj, apiRequest);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
            throws JsonProcessingException, JSONException, ParseException {
        JSONObject apiRequest = AccessToken.checkToken("PUT", "/attributecategory", data, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        return insertupdateAll(new JSONArray(data), null, apiRequest);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ResponseEntity insertupdateAll(JSONArray jsonAttributecategories, JSONObject jsonAttributeCategory, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        List<AttributeCategory> attributecategories = new ArrayList<AttributeCategory>();
        if (jsonAttributeCategory != null) {
            jsonAttributecategories = new JSONArray();
            jsonAttributecategories.put(jsonAttributeCategory);
        }
        log.info(jsonAttributecategories.toString());

        for (int a=0; a<jsonAttributecategories.length(); a++) {
            JSONObject jsonObj = jsonAttributecategories.getJSONObject(a);
            AttributeCategory attributecategory = new AttributeCategory();
            long attributecategoryid = 0;

            if (jsonObj.has("attributecategory_ID")) {
                attributecategoryid = jsonObj.getLong("attributecategory_ID");
                if (attributecategoryid != 0) {
                    attributecategory = attributecategoryrepository.findOne(attributecategoryid);

                    if (attributecategory == null)
                        return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid AttributeCategory Data!", apiRequest, true), HttpStatus.OK);
                }
            }

            if (attributecategoryid == 0) {
                if (!jsonObj.has("attributecategory_NAME") || jsonObj.isNull("attributecategory_NAME"))
                    return new ResponseEntity(getAPIResponse(null, null , null, null, "attributecategory_NAME is missing", apiRequest, true), HttpStatus.OK);

                if (!jsonObj.has("attributecategoryorder_NO") || jsonObj.isNull("attributecategoryorder_NO"))
                    return new ResponseEntity(getAPIResponse(null, null , null, null, "attributecategoryorder_NO is missing", apiRequest, true), HttpStatus.OK);
            }

            if (jsonObj.has("attributecategoryparent_ID") && !jsonObj.isNull("attributecategoryparent_ID"))
                attributecategory.setATTRIBUTECATEGORYPARENT_ID(attributecategoryrepository.findOne(jsonObj.getLong("attributecategoryparent_ID")));

            if (jsonObj.has("attributecategoryorder_NO") && !jsonObj.isNull("attributecategoryorder_NO"))
                attributecategory.setATTRIBUTECATEGORYORDER_NO(jsonObj.getLong("attributecategoryorder_NO"));

            if (jsonObj.has("attributecategory_NAME") && !jsonObj.isNull("attributecategory_NAME"))
                attributecategory.setATTRIBUTECATEGORY_NAME(jsonObj.getString("attributecategory_NAME"));

            if (jsonObj.has("istabs") && !jsonObj.isNull("istabs"))
                attributecategory.setISTABS(jsonObj.getString("istabs"));

            if (attributecategoryid == 0)
                attributecategory.setISACTIVE("Y");
            else if (jsonObj.has("isactive"))
                attributecategory.setISACTIVE(jsonObj.getString("isactive"));

            attributecategory.setMODIFIED_BY(apiRequest.getLong("request_ID"));
            attributecategory.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
            attributecategory.setMODIFIED_WHEN(dateFormat1.format(date));
            attributecategories.add(attributecategory);
        }

        for (int a=0; a<attributecategories.size(); a++) {
            AttributeCategory attributecategory = attributecategories.get(a);
            attributecategory = attributecategoryrepository.saveAndFlush(attributecategory);
            attributecategories.get(a).setATTRIBUTECATEGORY_ID(attributecategory.getATTRIBUTECATEGORY_ID());
        }

        ResponseEntity responseentity;
        if (jsonAttributeCategory != null)
            responseentity = new ResponseEntity(getAPIResponse(null, attributecategories.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
        else
            responseentity = new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, true), HttpStatus.OK);
        return responseentity;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
        JSONObject apiRequest = AccessToken.checkToken("GET", "/attributecategory/"+id, null, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        AttributeCategory attributecategory = attributecategoryrepository.findOne(id);
        attributecategoryrepository.delete(attributecategory);

        return new ResponseEntity(getAPIResponse(null, attributecategory , null, null, null, apiRequest, true), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
        JSONObject apiRequest = AccessToken.checkToken("GET", "/attributecategory/"+id, null, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        JSONObject attributecategory = new JSONObject();
        attributecategory.put("id", id);
        attributecategory.put("isactive", "N");

        return insertupdateAll(null, attributecategory, apiRequest);
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
        JSONObject apiRequest = AccessToken.checkToken("POST", "/attributecategory/search" + ((active == true) ? "" : "/all"), data, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        JSONObject jsonObj = new JSONObject(data);

        List<AttributeCategory> attributecategories = ((active == true)
                ? attributecategoryrepository.findBySearch("%" + jsonObj.getString("search") + "%")
                        : attributecategoryrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

        return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, true), HttpStatus.OK);
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
        JSONObject apiRequest = AccessToken.checkToken("POST", "/attributecategory/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
        if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

        List<AttributeCategory> attributecategories = new ArrayList<AttributeCategory>();
        JSONObject jsonObj = new JSONObject(data);

        JSONArray searchObject = new JSONArray();
        List<Integer> attributecategoryparent_IDS = new ArrayList<Integer>(); 

        attributecategoryparent_IDS.add((int) 0);

        long attributecategoryparent_ID = 0 ;

        boolean isWithDetail = true;
        if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
            isWithDetail = jsonObj.getBoolean("iswithdetail");
        }
        jsonObj.put("iswithdetail", false);

        if (jsonObj.has("attributecategoryparent_ID") && !jsonObj.isNull("attributecategoryparent_ID") && jsonObj.getLong("attributecategoryparent_ID") != 0) {
            attributecategoryparent_ID = jsonObj.getLong("attributecategoryparent_ID");
            attributecategoryparent_IDS.add((int) attributecategoryparent_ID);
        } else if (jsonObj.has("attributecategoryparent") && !jsonObj.isNull("attributecategoryparent") && jsonObj.getLong("attributecategoryparent") != 0) {
            if (active == true) {
                searchObject = new JSONArray(ServiceCall.POST("attributecategoryparent/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
            } else {
                searchObject = new JSONArray(ServiceCall.POST("attributecategoryparent/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
            }

            attributecategoryparent_ID = searchObject.length();
            for (int i=0; i<searchObject.length(); i++) {
                attributecategoryparent_IDS.add((int) searchObject.getJSONObject(i).getLong("attributecategoryparent_ID"));
            }
        }

        if (attributecategoryparent_ID != 0 ) {
            attributecategories = ((active == true)
                    ? attributecategoryrepository.findByAdvancedSearch(attributecategoryparent_ID, attributecategoryparent_IDS)
                            : attributecategoryrepository.findAllByAdvancedSearch(attributecategoryparent_ID, attributecategoryparent_IDS));

        }
        return new ResponseEntity(getAPIResponse(attributecategories, null , null, null, null, apiRequest, isWithDetail), HttpStatus.OK);

    }

    String getAPIResponse(List<AttributeCategory> attributecategories, AttributeCategory attributecategory , JSONArray jsonAttributecategories, JSONObject jsonAttributeCategory, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

        if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "AttributeCategory", message).toString();
        } else {
            if (attributecategory != null) {
				rtnAPIResponse = mapper.writeValueAsString(attributecategory);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

            } else if (attributecategories != null ) {
                				rtnAPIResponse = mapper.writeValueAsString(attributecategory);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

            } else if (jsonAttributecategories != null) {
                rtnAPIResponse = jsonAttributecategories.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

            } else if (jsonAttributeCategory != null) {
                rtnAPIResponse = jsonAttributeCategory.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
            }
        }

        return rtnAPIResponse;
    }

}