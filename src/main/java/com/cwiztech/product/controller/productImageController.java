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

import com.cwiztech.product.model.ProductImage;
import com.cwiztech.product.repository.productImageRepository;
import com.cwiztech.log.apiRequestLog;
import com.cwiztech.services.ServiceCall;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productimage", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductImage> productimages = productimagerepository.findActive();
		return new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productimage/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductImage> productimages = productimagerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productimage/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductImage productimage = productimagerepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productimage , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productimage/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productimage_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductimages = jsonObj.getJSONArray("productimages");
		for (int i=0; i<jsonproductimages.length(); i++) {
			productimage_IDS.add((Integer) jsonproductimages.get(i));
		}
		List<ProductImage> productimages = new ArrayList<ProductImage>();
		if (jsonproductimages.length()>0)
			productimages = productimagerepository.findByIDs(productimage_IDS);
		
		return new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productimage/notin/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productimage_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductimages = jsonObj.getJSONArray("productimages");
		for (int i=0; i<jsonproductimages.length(); i++) {
			productimage_IDS.add((Integer) jsonproductimages.get(i));
		}
		List<ProductImage> productimages = new ArrayList<ProductImage>();
		if (jsonproductimages.length()>0)
			productimages = productimagerepository.findByNotInIDs(productimage_IDS);
		
		return new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productimage", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productimage/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productimage", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductImages, JSONObject jsonProductImage, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
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
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductImage Data!", apiRequest, true), HttpStatus.OK);
				}
			}
			
			if (productimageid == 0) {
				if (!jsonObj.has("product_ID") || jsonObj.isNull("product_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_ID is missing", apiRequest, true), HttpStatus.OK);
				
				if (!jsonObj.has("productimage_PATH") || jsonObj.isNull("productimage_PATH"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productimage_PATH is missing", apiRequest, true), HttpStatus.OK);
					
			}
			
			if (jsonObj.has("product_ID") && !jsonObj.isNull("product_ID"))
			    productimage.setPRODUCT_ID(jsonObj.getLong("product_ID"));
		
		    if (jsonObj.has("productimage_PATH") && !jsonObj.isNull("productimage_PATH"))
			    productimage.setPRODUCTIMAGE_PATH(jsonObj.getString("productimage_PATH"));
			
		    if (productimageid == 0)
				productimage.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				productimage.setISACTIVE(jsonObj.getString("isactive"));

			productimage.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			productimage.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
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
			responseentity = new ResponseEntity(getAPIResponse(null, productimages.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productimage/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductImage productimage = productimagerepository.findOne(id);
		productimagerepository.delete(productimage);
		
		return new ResponseEntity(getAPIResponse(null, productimage , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productimage/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		JSONObject productimage = new JSONObject();
		productimage.put("id", id);
		productimage.put("isactive", "N");
		
		return insertupdateAll(null, productimage, apiRequest);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productimage/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
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
               searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
           } else {
               searchObject = new JSONArray(ServiceCall.POST("product/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
           }

           product_ID = searchObject.length();
           for (int i=0; i<searchObject.length(); i++) {
               product_IDS.add((int) searchObject.getJSONObject(i).getLong("product_ID"));
           }
       }

		if (product_ID != 0) {
		productimages = ((active == true)
				? productimagerepository.findByAdvancedSearch(product_ID, product_IDS)
				: productimagerepository.findAllByAdvancedSearch(product_ID, product_IDS));
		}
		return new ResponseEntity(getAPIResponse(productimages, null , null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}

	String getAPIResponse(List<ProductImage> productimages, ProductImage productimage , JSONArray jsonProductImages, JSONObject jsonProductImage, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";
		
		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "ProductImage", message).toString();
		} else  {
			if ((productimages != null || productimage != null) && isWithDetail == true) {
				if (productimage != null) {
					productimages = new ArrayList<ProductImage>();
					productimages.add(productimage);
				}
				if (productimages.size()>0) {
					List<Integer> productList = new ArrayList<Integer>();

					for (int i=0; i<productimages.size(); i++) {
						if (productimages.get(i).getPRODUCT_ID() != null) {
							productList.add(Integer.parseInt(productimages.get(i).getPRODUCT_ID().toString()));
						}
					} 
					CompletableFuture<JSONArray> productFuture = CompletableFuture.supplyAsync(() -> {
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

					// Wait until all futures complete
					CompletableFuture<Void> allDone =
							CompletableFuture.allOf(productFuture);

					// Block until all are done
					allDone.join();
  
					JSONArray productObject =  new JSONArray(productFuture.toString());

					for (int i=0; i<productimages.size(); i++) {
						for (int j=0; j<productObject.length(); j++) {
							JSONObject product = productObject.getJSONObject(j);
							if (productimages.get(i).getPRODUCT_ID() != null && productimages.get(i).getPRODUCT_ID() == product.getLong("getPRODUCT_ID") ) {
								productimages.get(i).setPRODUCT_DETAIL(product.toString());
							}
						}
					}
				}
				if (productimage != null)
					rtnAPIResponse = mapper.writeValueAsString(productimages.get(0));
				else {
					rtnAPIResponse = mapper.writeValueAsString(productimages);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");}

				} else if (productimage != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(productimage);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (productimages != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(productimages);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonProductImages != null) {
				rtnAPIResponse = jsonProductImages.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonProductImage != null) {
				rtnAPIResponse = mapper.writeValueAsString(jsonProductImage.toString());
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}
		}
		
		return rtnAPIResponse;
	}
}


		
		
		