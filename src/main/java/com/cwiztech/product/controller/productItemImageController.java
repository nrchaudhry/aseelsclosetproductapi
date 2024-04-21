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
import com.cwiztech.product.model.ProductItemImage;
import com.cwiztech.product.model.ProductItemPriceLevel;
import com.cwiztech.product.repository.productItemImageRepository;
import com.cwiztech.services.ProductService;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/productitemimage")

public class productItemImageController {
	
	private static final Logger log = LoggerFactory.getLogger(productItemImageController.class);

	@Autowired
	private productItemImageRepository productitemimagerepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemimage", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemImage> productitemimages = productitemimagerepository.findActive();
		return new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemimage/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<ProductItemImage> productitemimages = productitemimagerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemimage/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItemImage productitemimage = productitemimagerepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productitemimage , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemimage/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitemimage_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemimages = jsonObj.getJSONArray("productitemimages");
		for (int i=0; i<jsonproductitemimages.length(); i++) {
			productitemimage_IDS.add((Integer) jsonproductitemimages.get(i));
		}
		List<ProductItemImage> productitemimages = new ArrayList<ProductItemImage>();
		if (jsonproductitemimages.length()>0)
			productitemimages = productitemimagerepository.findByIDs(productitemimage_IDS);
		
		return new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemimage/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> productitemimage_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemimages = jsonObj.getJSONArray("productitemimages");
		for (int i=0; i<jsonproductitemimages.length(); i++) {
			productitemimage_IDS.add((Integer) jsonproductitemimages.get(i));
		}
		List<ProductItemImage> productitemimages = new ArrayList<ProductItemImage>();
		if (jsonproductitemimages.length()>0)
			productitemimages = productitemimagerepository.findByNotInIDs(productitemimage_IDS);
		
		return new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemimage", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/productitemimage/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/productitemimage", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductItemImages, JSONObject jsonProductItemImage, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<ProductItemImage> productitemimages = new ArrayList<ProductItemImage>();
		if (jsonProductItemImage != null) {
			jsonProductItemImages = new JSONArray();
			jsonProductItemImages.put(jsonProductItemImage);
		}
		log.info(jsonProductItemImages.toString());
		
		for (int a=0; a<jsonProductItemImages.length(); a++) {
			JSONObject jsonObj = jsonProductItemImages.getJSONObject(a);
			ProductItemImage productitemimage = new ProductItemImage();
			long productitemimageid = 0;

			if (jsonObj.has("productitemimage_ID")) {
				productitemimageid = jsonObj.getLong("productitemimage_ID");
				if (productitemimageid != 0) {
					productitemimage = productitemimagerepository.findOne(productitemimageid);
					
					if (productitemimage == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductItemImage Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (productitemimageid == 0) {
				if (!jsonObj.has("productitem_ID") || jsonObj.isNull("productitem_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productitemimage_PATH") || jsonObj.isNull("productitemimage_PATH"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitemimage_PATH is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
			}
			
			if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID"))
			    productitemimage.setPRODUCTITEM_ID(jsonObj.getLong("productitem_ID"));
		
		    if (jsonObj.has("productitemimage_PATH") && !jsonObj.isNull("productitemimage_PATH"))
			    productitemimage.setPRODUCTITEMIMAGE_PATH(jsonObj.getString("productitemimage_PATH"));
			
		    if (productitemimageid == 0)
				productitemimage.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				productitemimage.setISACTIVE(jsonObj.getString("isactive"));

			productitemimage.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			productitemimage.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			productitemimage.setMODIFIED_WHEN(dateFormat1.format(date));
			productitemimages.add(productitemimage);
		}
		
		for (int a=0; a<productitemimages.size(); a++) {
			ProductItemImage productitemimage = productitemimages.get(a);
			productitemimage = productitemimagerepository.saveAndFlush(productitemimage);
			productitemimages.get(a).setPRODUCTITEMIMAGE_ID(productitemimage.getPRODUCTITEMIMAGE_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonProductItemImage != null)
			responseentity = new ResponseEntity(getAPIResponse(null, productitemimages.get(0) , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemimage/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		ProductItemImage productitemimage = productitemimagerepository.findOne(id);
		productitemimagerepository.delete(productitemimage);
		
		return new ResponseEntity(getAPIResponse(null, productitemimage , null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/productitemimage/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject productitemimage = new JSONObject();
		productitemimage.put("id", id);
		productitemimage.put("isactive", "N");
		
		return insertupdateAll(null, productitemimage, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/productitemimage/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		List<ProductItemImage> productitemimages = new ArrayList<ProductItemImage>();
		JSONObject jsonObj = new JSONObject(data);
		long productitem_ID = 0;

		if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID"))
			productitem_ID = jsonObj.getLong("productitem_ID");
		if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID") && productitem_ID == 0 ) {
            JSONArray productsObject;
            if (active == true) {
            	productsObject = new JSONArray(ProductService.POST("productitem/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken));
            } else {
            	productsObject = new JSONArray(ProductService.POST("productitem/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken));
            }

            for (int i=0; i<productsObject.length(); i++) {
                List<ProductItemImage> productitemimage = new ArrayList<ProductItemImage>();
                productitemimage = ((active == true)
                        ? productitemimagerepository.findByAdvancedSearch(productsObject.getJSONObject(i).getLong("productitem_ID"))
                        : productitemimagerepository.findAllByAdvancedSearch(productsObject.getJSONObject(i).getLong("productitem_ID") ));
                for (int j=0; j<productitemimage.size(); j++) {
                	productitemimages.add(productitemimage.get(j));
                }
            }
        }

		if(productitem_ID != 0){
			List<ProductItemImage> productitemimage = ((active == true)
				? productitemimagerepository.findByAdvancedSearch(productitem_ID)
				: productitemimagerepository.findAllByAdvancedSearch(productitem_ID));
		  for (int i=0; i<productitemimage.size(); i++) {
	            boolean found = false;
	            
	            for (int j=0; j<productitemimages.size(); j++) {
	                if (productitemimage.get(i).getPRODUCTITEMIMAGE_ID() == productitemimages.get(j).getPRODUCTITEMIMAGE_ID()) {
	                    found = true;
	                    break;
	                }
	            }
	            
	            if (found == false) {
	            	productitemimages.add(productitemimage.get(i));
	            }
	        }
		}
		return new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductItemImage.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<ProductItemImage> productitemimages, ProductItemImage productitemimage , JSONArray jsonProductItemImages, JSONObject jsonProductItemImage, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productitemimageID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "ProductItemImage", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (productitemimage != null) {
				JSONObject productitem = new JSONObject(ProductService.GET("productitem/"+productitemimage.getPRODUCTITEM_ID(), apiRequest.getREQUEST_OUTPUT()));
				productitemimage.setPRODUCTITEM_DETAIL(productitem.toString());
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitemimage));
				productitemimageID = productitemimage.getPRODUCTITEMIMAGE_ID();
			} else if(productitemimages != null){
				if (productitemimages.size()>0) {
					List<Integer> productitemList = new ArrayList<Integer>();
					for (int i=0; i<productitemimages.size(); i++) {
						productitemList.add(Integer.parseInt(productitemimages.get(i).getPRODUCTITEM_ID().toString()));
					}
					JSONArray productitemObject = new JSONArray(ProductService.POST("productitem/ids", "{items: "+productitemList+"}", apiRequest.getREQUEST_OUTPUT()));
					
					for (int i=0; i<productitemimages.size(); i++) {
						for (int j=0; j<productitemObject.length(); j++) {
							JSONObject productitem = productitemObject.getJSONObject(j);
							if(productitemimages.get(i).getPRODUCTITEM_ID() == productitem.getLong("productitem_ID") ) {
								productitemimages.get(i).setPRODUCTITEM_DETAIL(productitem.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(productitemimages));
			}else if (jsonProductItemImages != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemImages.toString());
			
			} else if (jsonProductItemImage != null){
				apiRequest.setREQUEST_OUTPUT(jsonProductItemImage.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productitemimageID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}
}
