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
import com.cwiztech.product.model.Product;
import com.cwiztech.product.repository.productRepository;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class productController {
	private static final Logger log = LoggerFactory.getLogger(productController.class);

	@Autowired
	private productRepository productrepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/product", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Product> products = productrepository.findActive();
		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/product/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Product> products = productrepository.findAll();

		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseEntity getWithDetail(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("GET", "/product/detail", null, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        ObjectMapper mapper = new ObjectMapper();
        List<Product> products = productrepository.findActive();
        JSONArray jsonProducts = new JSONArray(mapper.writeValueAsString(products));
        
        JSONArray productattributevalues = new JSONArray(ServiceCall.GET("productattributevalue", headToken, false));
        
        for (int i=0; i<jsonProducts.length(); i++) {
            jsonProducts.getJSONObject(i).put("unitprice", jsonProducts.getJSONObject(i).getDouble("purchase_PRICE"));
           for (int j=0; j<productattributevalues.length(); j++) {
               JSONObject productattributevalue = productattributevalues.getJSONObject(j);
               JSONObject productattribute = new JSONObject(productattributevalue.getString("productattribute_DETAIL"));
               JSONObject attribute = new JSONObject(productattribute.getString("attribute_DETAIL"));

               if (jsonProducts.getJSONObject(i).getLong("product_ID") == productattributevalue.getLong("product_ID") && productattributevalue.isNull("attributevalue_ID")) {
                   jsonProducts.getJSONObject(i).put(attribute.getString("attribute_KEY"), productattributevalue.getString("productattribute_VALUE"));
               } else if (jsonProducts.getJSONObject(i).getLong("product_ID") == productattributevalue.getLong("product_ID") && !productattributevalue.isNull("attributevalue_ID")) {
                   if (attribute.getString("attribute_KEY").equals("taxcode")) {
                       if (productattributevalue.getLong("productattributevalue_ID")==1) {
                           jsonProducts.getJSONObject(i).put("taxcode_TITLE", "VAT:S");
                           jsonProducts.getJSONObject(i).put("vat", 20);
                       } else {
                           jsonProducts.getJSONObject(i).put("taxcode_TITLE", "VAT:Z");
                           jsonProducts.getJSONObject(i).put("vat", 0);
                       }
                   }
                   
                   jsonProducts.getJSONObject(i).put(attribute.getString("attribute_KEY"), productattributevalue.getLong("attributevalue_ID"));
               }
           }
        }
        return new ResponseEntity(getAPIResponse(null, null , jsonProducts, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/product/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		Product product = productrepository.findOne(id);
		
		return new ResponseEntity(getAPIResponse(null, product , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/product/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> product_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproducts = jsonObj.getJSONArray("products");
		for (int i=0; i<jsonproducts.length(); i++) {
			product_IDS.add((Integer) jsonproducts.get(i));
		}
		List<Product> products = new ArrayList<Product>();
		if (jsonproducts.length()>0)
			products = productrepository.findByIDs(product_IDS);
		
		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/ids/detail", method = RequestMethod.POST)
    public ResponseEntity getByIDsWithDetail(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
            throws JsonProcessingException, JSONException, ParseException {
        APIRequestDataLog apiRequest = checkToken("POST", "/product/ids", data, null, headToken);
        if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

        JSONArray jsonProducts = new JSONArray();
        List<Integer> product_IDS = new ArrayList<Integer>(); 
        JSONObject jsonObj = new JSONObject(data);
        JSONArray jsonproducts = jsonObj.getJSONArray("products");
        for (int i=0; i<jsonproducts.length(); i++) {
            product_IDS.add((Integer) jsonproducts.get(i));
        }
        
        if (jsonproducts.length()>0) {
            List<Product> products = new ArrayList<Product>();
            products = productrepository.findByIDs(product_IDS);
            ObjectMapper mapper = new ObjectMapper();
            jsonProducts = new JSONArray(mapper.writeValueAsString(products));
            
            JSONArray productattributevalues = new JSONArray(ServiceCall.GET("productattributevalue", headToken, false));
            
            for (int i=0; i<jsonProducts.length(); i++) {
                jsonProducts.getJSONObject(i).put("unitprice", jsonProducts.getJSONObject(i).getDouble("purchase_PRICE"));
               for (int j=0; j<productattributevalues.length(); j++) {
                   JSONObject productattributevalue = productattributevalues.getJSONObject(j);
                   JSONObject productattribute = new JSONObject(productattributevalue.getString("productattribute_DETAIL"));
                   JSONObject attribute = new JSONObject(productattribute.getString("attribute_DETAIL"));

                   if (jsonProducts.getJSONObject(i).getLong("product_ID") == productattributevalue.getLong("product_ID") && productattributevalue.isNull("attributevalue_ID")) {
                       jsonProducts.getJSONObject(i).put(attribute.getString("attribute_KEY"), productattributevalue.getString("productattribute_VALUE"));
                   } else if (jsonProducts.getJSONObject(i).getLong("product_ID") == productattributevalue.getLong("product_ID") && !productattributevalue.isNull("attributevalue_ID")) {
                       if (attribute.getString("attribute_KEY").equals("taxcode")) {
                           if (productattributevalue.getLong("productattributevalue_ID")==1) {
                               jsonProducts.getJSONObject(i).put("taxcode_TITLE", "VAT:S");
                               jsonProducts.getJSONObject(i).put("vat", 20);
                           } else {
                               jsonProducts.getJSONObject(i).put("taxcode_TITLE", "VAT:Z");
                               jsonProducts.getJSONObject(i).put("vat", 0);
                           }
                       }
                       
                       jsonProducts.getJSONObject(i).put(attribute.getString("attribute_KEY"), productattributevalue.getLong("attributevalue_ID"));
                   }
               }
            }
        }
        return new ResponseEntity(getAPIResponse(null, null , jsonProducts, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/product/notin/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> product_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproducts = jsonObj.getJSONArray("products");
		for (int i=0; i<jsonproducts.length(); i++) {
			product_IDS.add((Integer) jsonproducts.get(i));
		}
		List<Product> products = new ArrayList<Product>();
		if (jsonproducts.length()>0)
			products = productrepository.findByNotInIDs(product_IDS);
		
		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/product", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		
		APIRequestDataLog apiRequest = checkToken("PUT", "/product/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/product", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public ResponseEntity insertupdateAll(JSONArray jsonProducts, JSONObject jsonProduct, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<Product> products = new ArrayList<Product>();
		if (jsonProduct != null) {
			jsonProducts = new JSONArray();
			jsonProducts.put(jsonProduct);
		}
		log.info(jsonProducts.toString());
		
		for (int a=0; a<jsonProducts.length(); a++) {
			JSONObject jsonObj = jsonProducts.getJSONObject(a);
			Product product = new Product();
			long productid = 0;

			if (jsonObj.has("product_ID")) {
				productid = jsonObj.getLong("product_ID");
				if (productid != 0) {
					product = productrepository.findOne(productid);
					
					if (product == null)
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid Product Data!", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}
			
			if (productid == 0) {
				if (!jsonObj.has("product_CODE") || jsonObj.isNull("product_CODE"))
				    return new ResponseEntity(getAPIResponse(null, null , null, null, "product_CODE is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("product_NAME") || jsonObj.isNull("product_NAME"))
				    return new ResponseEntity(getAPIResponse(null, null , null, null, "product_NAME is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("productcategory_ID") || jsonObj.isNull("productcategory_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productcategory_ID is missing", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
//				String new_code = null;
//	    	    String productcategory_CODE = productrepository.GenerateNewCode(jsonObj.getLong("productcategory_ID"));
//	    	    if(productcategory_CODE == null){
//					JSONObject productcategory = new JSONObject(ServiceCall.GET("productcategory/"+jsonObj.getLong("productcategory_ID"), apiRequest.getREQUEST_OUTPUT()));
//	    	    	new_code = productcategory.getString("productcategory_CODE") + "0001";
//	    	    	}
//		     	else{
//		     		String last_4letters = productcategory_CODE.substring(productcategory_CODE.length() - 4);
//		     		int z=Integer.parseInt(last_4letters) + 1;
//		     		String first_letters = productcategory_CODE.substring(0,productcategory_CODE.length() - 4);
//		         	
//		     		if(z<10){new_code =  first_letters +"000"+ String.valueOf(z);}
//		         	else if(z>9 && z<100){new_code =  first_letters +"00"+ String.valueOf(z);}
//		         	else if(z>=100 && z<1000){new_code =  first_letters +"0"+ String.valueOf(z);}
//		         	else{new_code =  first_letters + String.valueOf(z);}
//		     	}
//		     		product.setPRODUCT_CODE(new_code);
			}
			if (jsonObj.has("productcategory_ID") && !jsonObj.isNull("productcategory_ID")) {
				JSONObject productcategory = new JSONObject(ServiceCall.GET("productcategory/"+jsonObj.getLong("productcategory_ID"), apiRequest.getREQUEST_OUTPUT(), false));
			    if (productcategory == null) 
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productcategory_ID doesn't exist!", apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				product.setPRODUCTCATEGORY_ID(jsonObj.getLong("productcategory_ID"));
			}
		
		    if (jsonObj.has("quickbook_ID")  && !jsonObj.isNull("quickbook_ID"))
			    product.setQUICKBOOK_ID(jsonObj.getString("quickbook_ID"));
		
		    if (jsonObj.has("sage_ID")  && !jsonObj.isNull("sage_ID"))
			    product.setSAGE_ID(jsonObj.getString("sage_ID"));
		
		    if (jsonObj.has("product_CODE")  && !jsonObj.isNull("product_CODE"))
		        product.setPRODUCT_CODE(jsonObj.getString("product_CODE"));
		
		    if (jsonObj.has("product_NAME") && !jsonObj.isNull("product_NAME"))
			    product.setPRODUCT_NAME(jsonObj.getString("product_NAME"));
		
		    if (jsonObj.has("product_DESC") && !jsonObj.isNull("product_DESC"))
			    product.setPRODUCT_DESC(jsonObj.getString("product_DESC"));
			
            if (jsonObj.has("producticon_URL") && !jsonObj.isNull("producticon_URL"))
                product.setPRODUCTICON_URL(jsonObj.getString("producticon_URL"));
            
            if (jsonObj.has("purchase_PRICE") && !jsonObj.isNull("purchase_PRICE"))
                product.setPURCHASE_PRICE(jsonObj.getDouble("purchase_PRICE"));
            else if (productid == 0)
            	product.setPURCHASE_PRICE(0.0);
            	
		    if (productid == 0)
				product.setISACTIVE("Y");
		    else if (jsonObj.has("isactive"))
				product.setISACTIVE(jsonObj.getString("isactive"));

			product.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			product.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			product.setMODIFIED_WHEN(dateFormat1.format(date));
			products.add(product);
		}
		
		for (int a=0; a<products.size(); a++) {
			Product product = products.get(a);
			product = productrepository.saveAndFlush(product);
			products.get(a).setPRODUCT_ID(product.getPRODUCT_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonProduct != null)
			responseentity = new ResponseEntity(getAPIResponse(null, products.get(0) , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(products, null, null , null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/product/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		Product product = productrepository.findOne(id);
		productrepository.delete(product);
		
		return new ResponseEntity(getAPIResponse(null, product , null, null, null, apiRequest, true, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/product/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject product = new JSONObject();
		product.put("id", id);
		product.put("isactive", "N");
		
		return insertupdateAll(null, product, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/product/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);
		long application_ID = 0;

		if (jsonObj.has("application_ID"))
			application_ID = jsonObj.getLong("application_ID");

		List<Product> products = ((active == true)
				? productrepository.findBySearch(application_ID,"%" + jsonObj.getString("search") + "%")
				: productrepository.findAllBySearch(application_ID,"%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, false, true).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/product/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		List<Product> products = new ArrayList<Product>();
		JSONObject jsonObj = new JSONObject(data);
		   JSONArray searchObject = new JSONArray();
	        List<Integer> productcategory_IDS = new ArrayList<Integer>(); 
	        productcategory_IDS.add((int) 0);
	        
		long productcategory_ID = 0;
		
       boolean isWithDetail = true;
       if (jsonObj.has("iswithdetail") && !jsonObj.isNull("iswithdetail")) {
           isWithDetail = jsonObj.getBoolean("iswithdetail");
       }
       jsonObj.put("iswithdetail", false);
		
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
		
		if(productcategory_ID != 0){
		products = ((active == true)
				? productrepository.findByAdvancedSearch(productcategory_ID, productcategory_IDS)
				: productrepository.findAllByAdvancedSearch(productcategory_ID, productcategory_IDS));
		}
		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, false, isWithDetail).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findOne(Product.getDatabaseTableID());
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
	
	APIRequestDataLog getAPIResponse(List<Product> products, Product product , JSONArray jsonProducts, JSONObject jsonProduct, String message, APIRequestDataLog apiRequest, boolean isTableLog,boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long productID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Product", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (product != null && isWithDetail == true) {
				JSONObject productcategory = new JSONObject(ServiceCall.GET("productcategory/"+product.getPRODUCTCATEGORY_ID(), apiRequest.getREQUEST_OUTPUT(), false));
				product.setPRODUCTCATEGORY_DETAIL(productcategory.toString());
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(product));
				productID = product.getPRODUCT_ID();
			} else if(products != null && isWithDetail == true){
				if (products.size()>0) {
					List<Integer> productcategoryList = new ArrayList<Integer>();
					for (int i=0; i<products.size(); i++) {
						productcategoryList.add(Integer.parseInt(products.get(i).getPRODUCTCATEGORY_ID().toString()));
					}
					JSONArray productcategoryObject = new JSONArray(ServiceCall.POST("productcategory/ids", "{productcategories: "+productcategoryList+"}", apiRequest.getREQUEST_OUTPUT(), false));
					
					for (int i=0; i<products.size(); i++) {
						for (int j=0; j<productcategoryObject.length(); j++) {
							JSONObject productcategory = productcategoryObject.getJSONObject(j);
							if(products.get(i).getPRODUCTCATEGORY_ID() == productcategory.getLong("productcategory_ID") ) {
								products.get(i).setPRODUCTCATEGORY_DETAIL(productcategory.toString());
							}
						}
					}
				}
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(products));
				
			} else if (product != null && isWithDetail == false) {
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(product));
			
			} else if (products != null && isWithDetail == false){
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(products));
				
			} else if (jsonProducts != null){
				apiRequest.setREQUEST_OUTPUT(jsonProducts.toString());
				
			} else if (jsonProduct != null){
				apiRequest.setREQUEST_OUTPUT(jsonProduct.toString());
			}
			apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(productID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		if (apiRequest.getREQUEST_OUTPUT().contains("bearer"))
			apiRequest.setREQUEST_OUTPUT(null);
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}
	
};
