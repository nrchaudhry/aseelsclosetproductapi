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
import com.cwiztech.product.model.ProductImage;
import com.cwiztech.product.repository.productImageRepository;
import com.cwiztech.services.ProductService;
import com.cwiztech.services.ProductService;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/productimage")

public class productImageController {
	
	private static final Logger log = LoggerFactory.getLogger(productImageController.class);

	@Autowired
	private productImageRepository productimagerepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productimage", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductImage> productimages = productimagerepository.findActive();
		return new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productimage/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductImage> productimages = productimagerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productimage/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductImage productimage = productimagerepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productimage , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productimage/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productimage_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductimages = jsonObj.getJSONArray("productimages");
		for (int i=0; i<jsonproductimages.length(); i++) {
			productimage_IDS.add((Integer) jsonproductimages.get(i));
		}
		List<ProductImage> productimages = new ArrayList<ProductImage>();
		if (jsonproductimages.length()>0)
			productimages = productimagerepository.findByIDs(productimage_IDS);
		
		return new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productimage/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productimage_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductimages = jsonObj.getJSONArray("productimages");
		for (int i=0; i<jsonproductimages.length(); i++) {
			productimage_IDS.add((Integer) jsonproductimages.get(i));
		}
		List<ProductImage> productimages = new ArrayList<ProductImage>();
		if (jsonproductimages.length()>0)
			productimages = productimagerepository.findByNotInIDs(productimage_IDS);
		
		return new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productimage", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/productimage/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productimage", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductImages, JSONObject jsonProductImage, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductImage> productimages = new ArrayList<ProductImage>();
		if (jsonProductImage != null) {
			jsonProductImages = new JSONArray();
			jsonProductImages.put(jsonProductImage);
		}
		log.info(jsonProductImages.toString());
		
		for (int a=0; a<jsonProductImages.length(); a++) {
			JSONObject jsonObj = jsonProductImages.getJSONObject(a);
			ProductImage productimage = new ProductImage();
			long productimageid = 0;

			if (jsonObj.has("productimage_ID")) {
				productimageid = jsonObj.getLong("productimage_ID");
				if (productimageid != 0) {
					productimage = productimagerepository.findOne(productimageid);
					
					if (productimage == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductImage Data!", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (productimageid == 0) {
				if (!jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productimage_PATH") || jsonObj.isNull("productimage_PATH"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productimage_PATH is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
			}
			
			if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID"))
			    productimage.setPRODUCT_ID(jsonObj.getLong("product_ID"));
		
		    if (jsonObj.has("productimage_PATH") && !jsonObj.isNull("productimage_PATH"))
			    productimage.setPRODUCTIMAGE_PATH(jsonObj.getString("productimage_PATH"));
			
		    if (productimageid == 0)
				productimage.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				productimage.setISACTIVE(jsonObj.getString("isactive"));

			productimage.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productimage.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productimage.setMODIFIED_WHEN(dateFormat1.format(date));
			productimages.add(productimage);
		}
		
		for (int a=0; a<productimages.size(); a++) {
			ProductImage productimage = productimages.get(a);
			productimage = productimagerepository.saveAndFlush(productimage);
			productimages.get(a).setPRODUCTIMAGE_ID(productimage.getPRODUCTIMAGE_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonProductImage != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productimages.get(0) , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productimage/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductImage productimage = productimagerepository.findOne(id);
		productimagerepository.delete(productimage);
		
		return new ResponseEntity(getAPIResponse(null, productimage , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productimage/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject productimage = new JSONObject();
		productimage.put("id", id);
		productimage.put("isactive", "N");
		
		return insertupdateAll(null, productimage, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productimage/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		List<ProductImage> productimages = new ArrayList<ProductImage>();
		JSONObject jsonObj = new JSONObject(data);
		   JSONArray searchObject = new JSONArray();
	        List<Integer> product_IDS = new ArrayList<Integer>(); 

	        product_IDS.add((int) 0);
	        
		long product_ID = 0;
		
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
               searchObject = new JSONArray(ProductService.POST("product/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken));
           } else {
               searchObject = new JSONArray(ProductService.POST("product/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken));
           }

           product_ID = searchObject.length();
           for (int i=0; i<searchObject.length(); i++) {
               product_IDS.add((int) searchObject.getJSONObject(i).getLong("product_ID"));
           }
       }

		if(product_ID != 0){
		productimages = ((active == true)
				? productimagerepository.findByAdvancedSearch(product_ID, product_IDS)
				: productimagerepository.findAllByAdvancedSearch(product_ID, product_IDS));
		}
		return new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, false, isWithDetail).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductImage.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductImage> productimages, ProductImage productimage , JSONArray jsonProductImages, JSONObject jsonProductImage, String message, APIRequestDataLog apiRequest, boolean isTableLog,boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productimageID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductImage", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productimage != null && isWithDetail == true) {
				JSONObject product = new JSONObject(ProductService.GET("product/"+productimage.getPRODUCT_ID(), apiRequest.getREQUEST_OUTPUT()));
				productimage.setPRODUCT_DETAIL(product.toString());
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productimage));
				productimageID = productimage.getPRODUCTIMAGE_ID();
			} else if(productimages != null && isWithDetail == true){
				if (productimages.size()>0) {
					List<Integer> productList = new ArrayList<Integer>();
					for (int i=0; i<productimages.size(); i++) {
						productList.add(Integer.parseInt(productimages.get(i).getPRODUCT_ID().toString()));
					}
					JSONArray productObject = new JSONArray(ProductService.POST("product/ids", "{products: "+productList+"}", apiRequest.getREQUEST_OUTPUT()));
					
					for (int i=0; i<productimages.size(); i++) {
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if(productimages.get(i).getPRODUCT_ID() == product.getLong("product_ID") ) {
								productimages.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productimages));
			}else if (jsonProductImages != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductImages.toString());
			
			} else if (jsonProductImage != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductImage.toString());
			}
			else if (jsonProductImages != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductImages.toString());
			} else if (jsonProductImage != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductImage.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productimageID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}

}