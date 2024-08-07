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
import com.cwiztech.product.model.ProductItemPriceLevel;
import com.cwiztech.product.repository.productItemInventoryRepository;
import com.cwiztech.product.repository.productItemPriceLevelRepository;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/productitempricelevel")
public class productItemPriceLevelController {
    private static final Logger log = LoggerFactory.getLogger(productItemPriceLevelController.class);
    @Autowired
    private productItemPriceLevelRepository productitempricelevelrepository;

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
        APIRequestDataLog apiRequest = checkToken("GET", "/productitempricelevel", null, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        List<ProductItemPriceLevel> productitempricelevels = productitempricelevelrepository.findActive();
        return new ResponseEntity(getAPIResponse(productitempricelevels, null , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("GET", "/productitempricelevel/all", null, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        List<ProductItemPriceLevel> productitempricelevels = productitempricelevelrepository.findAll();

        return new ResponseEntity(getAPIResponse(productitempricelevels, null , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("GET", "/productitempricelevel/"+id, null, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        ProductItemPriceLevel productitempricelevel = productitempricelevelrepository.findOne(id);

        return new ResponseEntity(getAPIResponse(null, productitempricelevel , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/ids", method = RequestMethod.POST)
    public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
            throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("POST", "/productitempricelevel/ids", data, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        List<Integer> productitempricelevel_IDS = new ArrayList<Integer>(); 
        JSONObject jsonObj = new JSONObject(data);
        JSONArray jsonproductitempricelevels = jsonObj.getJSONArray("productitempricelevels");
        for (int i=0; i<jsonproductitempricelevels.length(); i++) {
            productitempricelevel_IDS.add((Integer) jsonproductitempricelevels.get(i));
        }
        List<ProductItemPriceLevel> productitempricelevels = new ArrayList<ProductItemPriceLevel>();
        if (jsonproductitempricelevels.length()>0)
            productitempricelevels = productitempricelevelrepository.findByIDs(productitempricelevel_IDS);

        return new ResponseEntity(getAPIResponse(productitempricelevels, null , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
    public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
            throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("POST", "/productitempricelevel/notin/ids", data, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        List<Integer> productitempricelevel_IDS = new ArrayList<Integer>(); 
        JSONObject jsonObj = new JSONObject(data);
        JSONArray jsonproductitempricelevels = jsonObj.getJSONArray("productitempricelevels");
        for (int i=0; i<jsonproductitempricelevels.length(); i++) {
            productitempricelevel_IDS.add((Integer) jsonproductitempricelevels.get(i));
        }
        List<ProductItemPriceLevel> productitempricelevels = new ArrayList<ProductItemPriceLevel>();
        if (jsonproductitempricelevels.length()>0)
            productitempricelevels = productitempricelevelrepository.findByNotInIDs(productitempricelevel_IDS);

        return new ResponseEntity(getAPIResponse(productitempricelevels, null , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
            throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("POST", "/productitempricelevel", data, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        return insertupdateAll(null, new JSONObject(data), apiRequest);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
            throws JsonProcessingException, JSONException, ParseException {

        APIRequestDataLog apiRequest = checkToken("PUT", "/productitempricelevel/"+id, data, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
        JSONObject jsonObj = new JSONObject(data);
        jsonObj.put("id", id);

        return insertupdateAll(null, jsonObj, apiRequest);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
            throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("PUT", "/productitempricelevel", data, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        return insertupdateAll(new JSONArray(data), null, apiRequest);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ResponseEntity insertupdateAll(JSONArray jsonProductItemPriceLevels, JSONObject jsonProductItemPriceLevel, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        List<ProductItemPriceLevel> productitempricelevels = new ArrayList<ProductItemPriceLevel>();
        if (jsonProductItemPriceLevel != null) {
            jsonProductItemPriceLevels = new JSONArray();
            jsonProductItemPriceLevels.put(jsonProductItemPriceLevel);
        }
        log.info(jsonProductItemPriceLevels.toString());

        for (int a=0; a<jsonProductItemPriceLevels.length(); a++) {
            JSONObject jsonObj = jsonProductItemPriceLevels.getJSONObject(a);
            ProductItemPriceLevel productitempricelevel = new ProductItemPriceLevel();
            long productitempricelevelid = 0;

            if (jsonObj.has("productitempricelevel_ID")) {
                productitempricelevelid = jsonObj.getLong("productitempricelevel_ID");
                if (productitempricelevelid != 0) {
                    productitempricelevel = productitempricelevelrepository.findOne(productitempricelevelid);

                    if (productitempricelevel == null)
                        return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductItemPriceLevel Data!", apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
                }
            }

            if (productitempricelevelid == 0) {
                if (!jsonObj.has("productitem_ID") || jsonObj.isNull("productitem_ID"))
                    return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_ID is missing", apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

                if (!jsonObj.has("productitem_QUANTITY") || jsonObj.isNull("productitem_QUANTITY"))
                    return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_QUANTITY is missing", apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

                if (!jsonObj.has("productitem_UNITPRICE") || jsonObj.isNull("productitem_UNITPRICE"))
                    return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_UNITPRICE is missing", apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
            }
            if (jsonObj.has("currency_ID") && !jsonObj.isNull("currency_ID")) 			
                productitempricelevel.setCURRENCY_ID(jsonObj.getLong("currency_ID"));
            else
                productitempricelevel.setCURRENCY_ID((long) 91);

            if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID")) 
                productitempricelevel.setPRODUCTITEM_ID(jsonObj.getLong("productitem_ID"));

            if (jsonObj.has("pricelevel_ID") && !jsonObj.isNull("pricelevel_ID")) 
                productitempricelevel.setPRICELEVEL_ID(jsonObj.getLong("pricelevel_ID"));
            else
                productitempricelevel.setPRICELEVEL_ID((long) 148);

            if (jsonObj.has("productitem_QUANTITY")  && !jsonObj.isNull("productitem_QUANTITY"))
                productitempricelevel.setPRODUCTITEM_QUANTITY(jsonObj.getLong("productitem_QUANTITY"));

            if (jsonObj.has("productitem_UNITPRICE") && !jsonObj.isNull("productitem_UNITPRICE"))
                productitempricelevel.setPRODUCTITEM_UNITPRICE(jsonObj.getDouble("productitem_UNITPRICE"));

            if (productitempricelevelid == 0)
                productitempricelevel.setISACTIVE("Y");
            else if (jsonObj.has("isactive"))
                productitempricelevel.setISACTIVE(jsonObj.getString("isactive"));

            productitempricelevel.setMODIFIED_BY(apiRequest.getREQUEST_ID());
            productitempricelevel.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
            productitempricelevel.setMODIFIED_WHEN(dateFormat1.format(date));
            productitempricelevels.add(productitempricelevel);
        }

        for (int a=0; a<productitempricelevels.size(); a++) {
            ProductItemPriceLevel productitempricelevel = productitempricelevels.get(a);
            productitempricelevel = productitempricelevelrepository.saveAndFlush(productitempricelevel);
            productitempricelevels.get(a).setPRODUCTITEMPRICELEVEL_ID(productitempricelevel.getPRODUCTITEMPRICELEVEL_ID());
        }

        ResponseEntity responseentity;
        if (jsonProductItemPriceLevel != null)
            responseentity = new ResponseEntity(getAPIResponse(null, productitempricelevels.get(0) , null, null, null, apiRequest, true,true).getREQUEST_OUTPUT(), HttpStatus.OK);
        else
            responseentity = new ResponseEntity(getAPIResponse(productitempricelevels, null , null, null, null, apiRequest, true,true).getREQUEST_OUTPUT(), HttpStatus.OK);
        return responseentity;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("GET", "/productitempricelevel/"+id, null, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        ProductItemPriceLevel productitempricelevel = productitempricelevelrepository.findOne(id);
        productitempricelevelrepository.delete(productitempricelevel);

        return new ResponseEntity(getAPIResponse(null, productitempricelevel , null, null, null, apiRequest, true,true).getREQUEST_OUTPUT(), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("GET", "/productitempricelevel/"+id, null, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        JSONObject productitempricelevel = new JSONObject();
        productitempricelevel.put("id", id);
        productitempricelevel.put("isactive", "N");

        return insertupdateAll(null, productitempricelevel, apiRequest);
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
        APIRequestDataLog apiRequest = checkToken("POST", "/productitempricelevel/search" + ((active == true) ? "" : "/all"), data, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        JSONObject jsonObj = new JSONObject(data);

        List<ProductItemPriceLevel> productitempricelevels = ((active == true)
                ? productitempricelevelrepository.findBySearch("%" + jsonObj.getString("search") + "%")
                        : productitempricelevelrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

        return new ResponseEntity(getAPIResponse(productitempricelevels, null , null, null, null, apiRequest, false,true).getREQUEST_OUTPUT(), HttpStatus.OK);
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
        APIRequestDataLog apiRequest = checkToken("POST", "/productitempricelevel/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        JSONObject jsonObj = new JSONObject(data);
        List<ProductItemPriceLevel> productitempricelevels = new ArrayList<ProductItemPriceLevel>();
        JSONArray searchObject = new JSONArray();

        long currency_ID = 0, productitem_ID=0, pricelevel_ID=0;
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
        
		if (jsonObj.has("pricelevel_ID") && !jsonObj.isNull("pricelevel_ID"))
			pricelevel_ID = jsonObj.getLong("pricelevel_ID");
		else if (jsonObj.has("pricelevel_CODE") && !jsonObj.isNull("pricelevel_CODE")) {
			JSONObject pricelevel = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'PRICELEVEL', code: "+jsonObj.getString("pricelevel_CODE")+"}", apiRequest.getREQUEST_OUTPUT(), true));
			if (pricelevel != null)
				pricelevel_ID = pricelevel.getLong("id");
		} 

		if (jsonObj.has("currency_ID") && !jsonObj.isNull("currency_ID"))
			currency_ID = jsonObj.getLong("currency_ID");
		else if (jsonObj.has("currency_CODE") && !jsonObj.isNull("currency_CODE")) {
			JSONObject currency = new JSONObject(ServiceCall.POST("lookup/bycode", "{entityname: 'CURRENCY', code: "+jsonObj.getString("currency_CODE")+"}", apiRequest.getREQUEST_OUTPUT(), true));
			if (currency != null)
				currency_ID = currency.getLong("id");
		} 

        if (productitem_ID != 0 || pricelevel_ID != 0 || currency_ID != 0) {
            productitempricelevels = ((active == true)
                    ? productitempricelevelrepository.findByAdvancedSearch(productitem_ID, productitem_IDS, pricelevel_ID, currency_ID)
                            : productitempricelevelrepository.findAllByAdvancedSearch(productitem_ID, productitem_IDS, pricelevel_ID, currency_ID));
        }
        return new ResponseEntity(getAPIResponse(productitempricelevels, null , null, null, null, apiRequest, false,isWithDetail).getREQUEST_OUTPUT(), HttpStatus.OK);
    }

    public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
        JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
        DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItemPriceLevel.getDatabaseTableID());
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

    APIRequestDataLog getAPIResponse(List<ProductItemPriceLevel> productitempricelevels, ProductItemPriceLevel productitempricelevel , JSONArray jsonProductItemPriceLevels, JSONObject jsonProductItemPriceLevel, String message, APIRequestDataLog apiRequest, boolean isTableLog,boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        long productitempricelevelID = 0;

        if (message != null) {
            apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductItemPriceLevel", message);
            apirequestdatalogRepository.saveAndFlush(apiRequest);
        } else {
            if (productitempricelevel != null && isWithDetail == true) {
                JSONObject productitem = new JSONObject(ServiceCall.GET("productitem/"+productitempricelevel.getPRODUCTITEM_ID(), apiRequest.getREQUEST_OUTPUT(), false));
                productitempricelevel.setPRODUCTITEM_DETAIL(productitem.toString());

                if(productitempricelevel.getCURRENCY_ID() != null) {
                    JSONObject currency = new JSONObject(ServiceCall.GET("lookup/"+productitempricelevel.getCURRENCY_ID(), apiRequest.getREQUEST_OUTPUT(), true));
                    productitempricelevel.setCURRENCY_DETAIL(currency.toString());
                }
                if(productitempricelevel.getPRICELEVEL_ID() != null) {
                    JSONObject priceLevel = new JSONObject(ServiceCall.GET("lookup/"+productitempricelevel.getPRICELEVEL_ID(), apiRequest.getREQUEST_OUTPUT(), true));
                    productitempricelevel.setPRICELEVEL_DETAIL(priceLevel.toString());
                }

                apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitempricelevel));
                productitempricelevelID = productitempricelevel.getPRODUCTITEMPRICELEVEL_ID();
            } else if(productitempricelevels != null && isWithDetail == true){
                if (productitempricelevels.size()>0) {
                    List<Integer> productitemList = new ArrayList<Integer>();
                    List<Integer> lookupList = new ArrayList<Integer>();
                    for (int i=0; i<productitempricelevels.size(); i++) {
                        productitemList.add(Integer.parseInt(productitempricelevels.get(i).getPRODUCTITEM_ID().toString()));
                        if(productitempricelevels.get(i).getCURRENCY_ID() != null)
                            lookupList.add(Integer.parseInt(productitempricelevels.get(i).getCURRENCY_ID().toString()));
                        if(productitempricelevels.get(i).getPRICELEVEL_ID() != null)
                            lookupList.add(Integer.parseInt(productitempricelevels.get(i).getPRICELEVEL_ID().toString()));
                    }
                    JSONArray productitemObject = new JSONArray(ServiceCall.POST("productitem/ids", "{items: "+productitemList+"}", apiRequest.getREQUEST_OUTPUT(), false));
                    JSONArray lookupObject = new JSONArray(ServiceCall.POST("lookup/ids", "{lookups: "+ lookupList+"}", apiRequest.getREQUEST_OUTPUT(), true));

                    for (int i=0; i<productitempricelevels.size(); i++) {
                        for (int j=0; j<productitemObject.length(); j++) {
                            JSONObject productitem = productitemObject.getJSONObject(j);
                            if(productitempricelevels.get(i).getPRODUCTITEM_ID() == productitem.getLong("productitem_ID") ) {
                                productitempricelevels.get(i).setPRODUCTITEM_DETAIL(productitem.toString());
                            }
                        }

                        for (int j=0; j<lookupObject.length(); j++) {
                            JSONObject lookup = lookupObject.getJSONObject(j);
                            if(productitempricelevels.get(i).getCURRENCY_ID() != null && productitempricelevels.get(i).getCURRENCY_ID() == lookup.getLong("id") ) {
                                productitempricelevels.get(i).setCURRENCY_DETAIL(lookup.toString());
                            }
                            if(productitempricelevels.get(i).getPRICELEVEL_ID() != null && productitempricelevels.get(i).getPRICELEVEL_ID() == lookup.getLong("id") ) {
                                productitempricelevels.get(i).setPRICELEVEL_DETAIL(lookup.toString());
                            }
                        }
                    }
                }
                apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitempricelevels));

            } else if (productitempricelevel != null && isWithDetail == false) {
                apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitempricelevel));

            } else if (productitempricelevels != null && isWithDetail == false) {
                apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitempricelevels));

            } else if (jsonProductItemPriceLevels != null) {
                apiRequest.setREQUEST_OUTPUT(jsonProductItemPriceLevels.toString());

            } else if (jsonProductItemPriceLevel != null) {
                apiRequest.setREQUEST_OUTPUT(jsonProductItemPriceLevel.toString());
            }
            apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
            apiRequest.setREQUEST_STATUS("Success");
            apirequestdatalogRepository.saveAndFlush(apiRequest);
        }

        if (isTableLog)
            tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productitempricelevelID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));

        if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
            apiRequest.setREQUEST_OUTPUT(null);

        log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
        log.info("--------------------------------------------------------");

        return apiRequest;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/bycustomer", method = RequestMethod.POST)
    public ResponseEntity getAllByCustomer(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("POST", "/productitempricelevel/bycustomer", data, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        JSONArray objProductItems = new JSONArray();

        JSONObject jsonObj = new JSONObject(data);
        String pricelevel_CODE="";

        if (jsonObj.has("pricelevel_CODE")) {
            pricelevel_CODE = jsonObj.getString("pricelevel_CODE");
        }

        JSONArray productitems = new JSONArray(ServiceCall.GET("productitem", headToken, false));
        List<ProductItemInventory> productitemiventory = productiteminventoryrepository.findActive();
        JSONArray productitemattributevalues = new JSONArray(ServiceCall.GET("productitemattributevalue", headToken, false));
        JSONArray pricelevels = new JSONArray(ServiceCall.POST("lookup/entity/all", "{entityname: 'PRICELEVEL'}", headToken, true));
        List<ProductItemPriceLevel> productitempricelevel = productitempricelevelrepository.findActive();

        for (int i=0; i<productitems.length(); i++) {

            JSONObject objProductItem = new JSONObject();
            JSONArray objProductItemPrices = new JSONArray();
            List<Integer> foundList = new ArrayList<Integer>();

            for (int j=0; j<productitempricelevel.size(); j++) {
                if (productitems.getJSONObject(i).getLong("productitem_ID")==productitempricelevel.get(j).getPRODUCTITEM_ID()) {
                    JSONObject objProductItemPrice = new JSONObject();

                    for (int k=0; k<pricelevels.length(); k++) {
                        if (productitempricelevel.get(j).getPRICELEVEL_ID() == pricelevels.getJSONObject(k).getLong("id")) {
                            objProductItemPrice.put("pricelevel", pricelevels.getJSONObject(k).toString());
                        }
                    }
                    objProductItemPrice.put("unitprice", productitempricelevel.get(j).getPRODUCTITEM_UNITPRICE());
                    if (pricelevel_CODE.compareTo("0") != 0) {
                        for (int k=0; k<pricelevels.length(); k++) {
                            if (productitempricelevel.get(j).getPRICELEVEL_ID() == pricelevels.getJSONObject(k).getLong("id")) {
                                if (pricelevel_CODE.compareTo(pricelevels.getJSONObject(k).getString("code"))==0)
                                    objProductItemPrice.put("isselected", "Y");
                                else
                                    objProductItemPrice.put("isselected", "N");
                            }
                        }
                    }                   
                    objProductItemPrices.put(objProductItemPrice);
                    foundList.add(j);
                }
            }

            for (int j=0; j<foundList.size(); j++) {
                productitempricelevel.remove(foundList.get(j));
            }
            JSONObject jsonproduct = new JSONObject(productitems.getJSONObject(i).getString("product_DETAIL"));
            JSONObject jsonproductcategory = new JSONObject(jsonproduct.getString("productcategory_DETAIL"));

            objProductItem.put("productitem_ID", productitems.getJSONObject(i).getLong("productitem_ID"));
            objProductItem.put("productitem_CODE", jsonproduct.getString("product_CODE"));
            objProductItem.put("productitem_NAME", productitems.getJSONObject(i).getString("productitem_NAME"));
            if (!productitems.getJSONObject(i).isNull("productitem_DESC"))
                objProductItem.put("productitem_DESC", productitems.getJSONObject(i).getString("productitem_DESC"));
            objProductItem.put("productcategory_ID", jsonproduct.getLong("productcategory_ID"));
            objProductItem.put("productcategory_NAME", jsonproductcategory.getString("productcategory_NAME").replace("\"", ""));
            objProductItem.put("productcategoryorder_NO", jsonproductcategory.getLong("productcategoryorder_NO"));
            JSONObject product = new JSONObject(productitems.getJSONObject(i).getString("product_DETAIL"));
            objProductItem.put("purchase_PRICE", product.getLong("purchase_PRICE"));
            for (int j=0; j<productitemiventory.size(); j++) {
                if (productitems.getJSONObject(i).getLong("productitem_ID")==productitemiventory.get(j).getPRODUCTITEM_ID()) {
                    objProductItem.put("quantityonhand", productitemiventory.get(j).getQUANTITY_ONHAND());
                    objProductItem.put("quantityavailable", productitemiventory.get(j).getQUANTITY_AVAILABLE());
                    break;
                }
            }

            foundList = new ArrayList<Integer>();
            for (int j=0; j<productitemattributevalues.length(); j++) {
                if (productitems.getJSONObject(i).getLong("productitem_ID")==productitemattributevalues.getJSONObject(j).getLong("productitem_ID")) {
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
                    foundList.add(j);
                }
            }

            for (int j=0; j<foundList.size(); j++) {
                productitempricelevel.remove(foundList.get(j));
            }

            objProductItem.put("productitemprices", objProductItemPrices);
            objProductItems.put(objProductItem);
        }

        return new ResponseEntity(getAPIResponse(null, null , objProductItems, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/changeprice" ,method = RequestMethod.POST)
    public ResponseEntity ChangePrice(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
            throws JsonProcessingException, JSONException, ParseException {
        JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
        String rtn = null, workstation = null;
        APIRequestDataLog apiRequest;

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        log.info("POST: /productitempricelevel/changeprice");
        log.info("Input: " + data);

        DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItemPriceLevel.getDatabaseTableID());

        if (checkTokenResponse.has("error")) {
            apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/productitempricelevel/changeprice", data, workstation);
            apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
            apirequestdatalogRepository.saveAndFlush(apiRequest);
            return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
        }

        ObjectMapper mapper = new ObjectMapper();
        Long requestUser = checkTokenResponse.getLong("user_ID");
        apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/productitempricelevel/changeprice", data, workstation);

        JSONArray jsonPAV = new JSONArray(data);
        List<ProductItemPriceLevel> productitempricelevellist = new ArrayList<ProductItemPriceLevel>();

        List<Integer> currency_IDS = new ArrayList<Integer>(); 
        currency_IDS.add((int) 0);

        for (int i = 0; i < jsonPAV.length(); i++) {
            JSONObject productitem = jsonPAV.getJSONObject(i);      
            long productitem_ID = productitem.getLong("productitem_ID");
            long pricelevel_ID = productitem.getLong("pricelevel_ID");
            double productitem_unitprice = productitem.getLong("productitem_UNITPRICE");
            List<Integer> productitem_IDS = new ArrayList<Integer>(); 

            productitem_IDS.add((int) 0);

            List<ProductItemPriceLevel> productitempricelevel = productitempricelevelrepository.findByAdvancedSearch(productitem_ID, productitem_IDS, pricelevel_ID, (long) 0);

            if(!(productitempricelevel.size() == 0)){
                for (int k=0; k<productitempricelevel.size(); k++){
                    ProductItemPriceLevel pricelevel = productitempricelevel.get(k);
                    pricelevel.setISACTIVE("N");
                    pricelevel.setMODIFIED_BY(requestUser);
                    pricelevel.setMODIFIED_WORKSTATION(workstation);
                    pricelevel.setMODIFIED_WHEN(dateFormat1.format(date));
                    pricelevel = productitempricelevelrepository.saveAndFlush(pricelevel);
                    ProductItemPriceLevel newproductprice = new ProductItemPriceLevel();
                    newproductprice.setPRODUCTITEM_ID(productitem_ID);
                    newproductprice.setPRODUCTITEM_QUANTITY((long) 1);
                    newproductprice.setPRODUCTITEM_UNITPRICE(productitem_unitprice);
                    newproductprice.setCURRENCY_ID((long) 91);
                    newproductprice.setPRICELEVEL_ID((long) 148);
                    newproductprice.setISACTIVE("Y");
                    newproductprice.setMODIFIED_BY(requestUser);
                    newproductprice.setMODIFIED_WORKSTATION(workstation);
                    newproductprice.setMODIFIED_WHEN(dateFormat1.format(date));
                    newproductprice = productitempricelevelrepository.saveAndFlush(newproductprice);
                    productitempricelevellist.add(newproductprice);
                }
            }
            else{
                ProductItemPriceLevel newproductprice = new ProductItemPriceLevel();
                newproductprice.setPRODUCTITEM_ID(productitem_ID);
                newproductprice.setPRODUCTITEM_QUANTITY((long) 1);
                newproductprice.setPRODUCTITEM_UNITPRICE(productitem_unitprice);
                newproductprice.setCURRENCY_ID((long) 91);
                newproductprice.setPRICELEVEL_ID((long) 148);
                newproductprice.setISACTIVE("Y");
                newproductprice.setMODIFIED_BY(requestUser);
                newproductprice.setMODIFIED_WORKSTATION(workstation);
                newproductprice.setMODIFIED_WHEN(dateFormat1.format(date));
                newproductprice = productitempricelevelrepository.saveAndFlush(newproductprice);
                productitempricelevellist.add(newproductprice);
            }

        }

        rtn = mapper.writeValueAsString(productitempricelevellist);
        apiRequest.setREQUEST_OUTPUT(rtn);
        apiRequest.setREQUEST_STATUS("Success");
        apirequestdatalogRepository.saveAndFlush(apiRequest);

        log.info("Output: " + rtn);
        log.info("--------------------------------------------------------");

        return new ResponseEntity(rtn, HttpStatus.OK);
    }   
}

