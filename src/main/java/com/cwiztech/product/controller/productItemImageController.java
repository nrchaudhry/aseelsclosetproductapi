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

import com.cwiztech.product.model.Product;
import com.cwiztech.product.model.ProductItemImage;
import com.cwiztech.product.model.ProductItemPriceLevel;
import com.cwiztech.product.repository.productItemImageRepository;
import com.cwiztech.log.apiRequestLog;
import com.cwiztech.services.ServiceCall;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemimage", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductItemImage> productitemimages = productitemimagerepository.findActive();
		return new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemimage/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<ProductItemImage> productitemimages = productitemimagerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemimage/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductItemImage productitemimage = productitemimagerepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, productitemimage , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemimage/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productitemimage_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemimages = jsonObj.getJSONArray("productitemimages");
		for (int i=0; i<jsonproductitemimages.length(); i++) {
			productitemimage_IDS.add((Integer) jsonproductitemimages.get(i));
		}
		List<ProductItemImage> productitemimages = new ArrayList<ProductItemImage>();
		if (jsonproductitemimages.length()>0)
			productitemimages = productitemimagerepository.findByIDs(productitemimage_IDS);
		
		return new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemimage/notin/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> productitemimage_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproductitemimages = jsonObj.getJSONArray("productitemimages");
		for (int i=0; i<jsonproductitemimages.length(); i++) {
			productitemimage_IDS.add((Integer) jsonproductitemimages.get(i));
		}
		List<ProductItemImage> productitemimages = new ArrayList<ProductItemImage>();
		if (jsonproductitemimages.length()>0)
			productitemimages = productitemimagerepository.findByNotInIDs(productitemimage_IDS);
		
		return new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemimage", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productitemimage/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/productitemimage", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonProductItemImages, JSONObject jsonProductItemImage, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
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
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid ProductItemImage Data!", apiRequest, true), HttpStatus.OK);
				}
			}
			
			if (productitemimageid == 0) {
				if (!jsonObj.has("productitem_ID") || jsonObj.isNull("productitem_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitem_ID is missing", apiRequest, true), HttpStatus.OK);
				
				if (!jsonObj.has("productitemimage_PATH") || jsonObj.isNull("productitemimage_PATH"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productitemimage_PATH is missing", apiRequest, true), HttpStatus.OK);
					
			}
			
			if (jsonObj.has("productitem_ID") && !jsonObj.isNull("productitem_ID"))
			    productitemimage.setPRODUCTITEM_ID(jsonObj.getLong("productitem_ID"));
		
		    if (jsonObj.has("productitemimage_PATH") && !jsonObj.isNull("productitemimage_PATH"))
			    productitemimage.setPRODUCTITEMIMAGE_PATH(jsonObj.getString("productitemimage_PATH"));
			
		    if (productitemimageid == 0)
				productitemimage.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				productitemimage.setISACTIVE(jsonObj.getString("isactive"));

			productitemimage.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			productitemimage.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
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
			responseentity = new ResponseEntity(getAPIResponse(null, productitemimages.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemimage/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		ProductItemImage productitemimage = productitemimagerepository.findOne(id);
		productitemimagerepository.delete(productitemimage);
		
		return new ResponseEntity(getAPIResponse(null, productitemimage , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/productitemimage/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		JSONObject productitemimage = new JSONObject();
		productitemimage.put("id", id);
		productitemimage.put("isactive", "N");
		
		return insertupdateAll(null, productitemimage, apiRequest);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/productitemimage/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		
		List<ProductItemImage> productitemimages = new ArrayList<ProductItemImage>();
		JSONObject jsonObj = new JSONObject(data);
		   JSONArray searchObject = new JSONArray();
        List<Integer> productitem_IDS = new ArrayList<Integer>(); 

        productitem_IDS.add((int) 0);
        
	long productitem_ID = 0;
	
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

		if (productitem_ID != 0) {
			productitemimages = ((active == true)
				? productitemimagerepository.findByAdvancedSearch(productitem_ID,productitem_IDS)
				: productitemimagerepository.findAllByAdvancedSearch(productitem_ID,productitem_IDS));

		}
		return new ResponseEntity(getAPIResponse(productitemimages, null , null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}

	String getAPIResponse(List<ProductItemImage> productitemimages, ProductItemImage productitemimage, JSONArray jsonProductItemImages, JSONObject jsonProductItemImage, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";
		
		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "ProductItemImage", message).toString();
		} else {
			if ((productitemimages != null || productitemimage != null) && isWithDetail == true) {
				if (productitemimage != null) {
					productitemimages = new ArrayList<ProductItemImage>();
					productitemimages.add(productitemimage);
				}
				if (productitemimages.size()>0) {
					List<Integer> productitemList = new ArrayList<Integer>();

					for (int i=0; i<productitemimages.size(); i++) {
						if (productitemimages.get(i).getPRODUCTITEM_ID() != null) {
							productitemList.add(Integer.parseInt(productitemimages.get(i).getPRODUCTITEM_ID().toString()));
						}
					}

					CompletableFuture<JSONArray> productitemFuture = CompletableFuture.supplyAsync(() -> {
						if (productitemList.size() <= 0) {
							return new JSONArray();
						}

						try {
							return new JSONArray(ServiceCall.POST("productitem/ids", "{productitems: "+productitemList+"}", apiRequest.getString("access_TOKEN"), true));
						} catch (JSONException | JsonProcessingException | ParseException e) {
							e.printStackTrace();
							return new JSONArray();
						}
					});

					// Wait until all futures complete
					CompletableFuture<Void> allDone =
							CompletableFuture.allOf(productitemFuture);

					// Block until all are done
					allDone.join();

					JSONArray productitemObject = new JSONArray(productitemFuture.toString());
		
					for (int i=0; i<productitemimages.size(); i++) {
						for (int j=0; j<productitemObject.length(); j++) {
							JSONObject productitem = productitemObject.getJSONObject(j);
							if (productitemimages.get(i).getPRODUCTITEM_ID() != null && productitemimages.get(i).getPRODUCTITEM_ID() == productitem.getLong("PRODUCTITEM_ID")) {
								productitemimages.get(i).setPRODUCTITEM_DETAIL(productitem.toString());
							}
						}
					}
				}

				if (productitemimage != null)
					rtnAPIResponse = mapper.writeValueAsString(productitemimages.get(0));
				else	
					rtnAPIResponse = mapper.writeValueAsString(productitemimages);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (productitemimage != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(productitemimage);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (productitemimages != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(productitemimages);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonProductItemImages != null) {
				rtnAPIResponse = jsonProductItemImages.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonProductItemImage != null) {
				rtnAPIResponse = jsonProductItemImage.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}
		}

		return rtnAPIResponse;
	}
}

		
