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

import com.cwiztech.product.model.Product;
import com.cwiztech.product.repository.productRepository;
import com.cwiztech.log.apiRequestLog;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/product", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Product> products = productrepository.findActive();
		
		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/product/all", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Product> products = productrepository.findAll();

		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity getWithDetail(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/product/detail", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONArray jsonProducts = new JSONArray(ServiceCall.GET("product", headToken, false));
		JSONArray productattributevalues = new JSONArray(ServiceCall.GET("productattributevalue", headToken, false));

		for (int i=0; i<jsonProducts.length(); i++) {
			jsonProducts.getJSONObject(i).put("unitprice", jsonProducts.getJSONObject(i).getDouble("purchase_PRICE"));
            JSONObject taxcode = new JSONObject(jsonProducts.getJSONObject(i).getString("taxcode_DETAIL"));
            jsonProducts.getJSONObject(i).put("taxcode_ID", taxcode.getLong("taxcode_ID"));
            jsonProducts.getJSONObject(i).put("taxcode", taxcode.getString("taxcode_TITLE"));
            jsonProducts.getJSONObject(i).put("vat", taxcode.getLong("taxcode_PERCENTAGE"));
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
		return new ResponseEntity(getAPIResponse(null, null , jsonProducts, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/product/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		Product product = productrepository.findOne(id);

		return new ResponseEntity(getAPIResponse(null, product , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/product/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> product_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproducts = jsonObj.getJSONArray("products");
		for (int i=0; i<jsonproducts.length(); i++) {
			product_IDS.add((Integer) jsonproducts.get(i));
		}
		List<Product> products = new ArrayList<Product>();
		if (jsonproducts.length()>0)
			products = productrepository.findByIDs(product_IDS);

		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids/detail", method = RequestMethod.POST)
	public ResponseEntity getByIDsWithDetail(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/product/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

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
		return new ResponseEntity(getAPIResponse(null, null , jsonProducts, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/product/notin/ids", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Integer> product_IDS = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonproducts = jsonObj.getJSONArray("products");
		for (int i=0; i<jsonproducts.length(); i++) {
			product_IDS.add((Integer) jsonproducts.get(i));
		}
		List<Product> products = new ArrayList<Product>();
		if (jsonproducts.length()>0)
			products = productrepository.findByNotInIDs(product_IDS);

		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("POST", "/product", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {

		JSONObject apiRequest = AccessToken.checkToken("PUT", "/product/"+id, data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);

		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("PUT", "/product", data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public ResponseEntity insertupdateAll(JSONArray jsonProducts, JSONObject jsonProduct, JSONObject apiRequest) throws JsonProcessingException, JSONException, ParseException {
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
						return new ResponseEntity(getAPIResponse(null, null , null, null, "Invalid Product Data!", apiRequest, true), HttpStatus.OK);
				}
			}

			if (productid == 0) {
//				if (!jsonObj.has("product_CODE") || jsonObj.isNull("product_CODE"))
//					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_CODE is missing", apiRequest, true), HttpStatus.OK);

				if (!jsonObj.has("product_NAME") || jsonObj.isNull("product_NAME"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "product_NAME is missing", apiRequest, true), HttpStatus.OK);

				if (!jsonObj.has("productcategory_ID") || jsonObj.isNull("productcategory_ID"))
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productcategory_ID is missing", apiRequest, true), HttpStatus.OK);

				String new_code = null;
	    	    String productcategory_CODE = productrepository.GenerateNewCode(jsonObj.getLong("productcategory_ID"));
	    	    if (productcategory_CODE == null) {
					JSONObject productcategory = new JSONObject(ServiceCall.GET("productcategory/"+jsonObj.getLong("productcategory_ID"), apiRequest.getString("access_TOKEN"), false));
	    	    	new_code = productcategory.getString("productcategory_CODE") + "001";
    	    	} else {
		     		String last_4letters = productcategory_CODE.substring(productcategory_CODE.length() - 3);
		     		int z=Integer.parseInt(last_4letters) + 1;
		     		String first_letters = productcategory_CODE.substring(0,productcategory_CODE.length() - 3);
		         	
		     		if (z<10) {new_code =  first_letters +"000"+ String.valueOf(z);}
		         	else if (z>9 && z<100) {new_code =  first_letters +"00"+ String.valueOf(z);}
		         	else if (z>=100 && z<1000) {new_code =  first_letters +"0"+ String.valueOf(z);}
		         	else {new_code =  first_letters + String.valueOf(z);}
		     	}
	     		
	    	    product.setPRODUCT_CODE(new_code);
			}
			if (jsonObj.has("productcategory_ID") && !jsonObj.isNull("productcategory_ID")) {
				JSONObject productcategory = new JSONObject(ServiceCall.GET("productcategory/"+jsonObj.getLong("productcategory_ID"), apiRequest.getString("access_TOKEN"), false));
				if (productcategory == null) 
					return new ResponseEntity(getAPIResponse(null, null , null, null, "productcategory_ID doesn't exist!", apiRequest, true), HttpStatus.OK);
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

			if (jsonObj.has("productimage_URL") && !jsonObj.isNull("productimage_URL"))
				product.setPRODUCTIMAGE_URL(jsonObj.getString("productimage_URL"));

			if (jsonObj.has("producticon_URL") && !jsonObj.isNull("producticon_URL"))
				product.setPRODUCTICON_URL(jsonObj.getString("producticon_URL"));

			if (jsonObj.has("taxcode_ID") && !jsonObj.isNull("taxcode_ID"))
				product.setTAXCODE_ID(jsonObj.getLong("taxcode_ID"));
			else if (productid == 0)
				product.setTAXCODE_ID((long) 2);

			if (jsonObj.has("saleledgeraccount_ID") && !jsonObj.isNull("saleledgeraccount_ID"))
				product.setSALELEDGERACCOUNT_ID(jsonObj.getLong("saleledgeraccount_ID"));

			if (jsonObj.has("purchaseledgeraccount_ID") && !jsonObj.isNull("purchaseledgeraccount_ID"))
				product.setPURCHASELEDGERACCOUNT_ID(jsonObj.getLong("purchaseledgeraccount_ID"));

			if (jsonObj.has("purchase_PRICE") && !jsonObj.isNull("purchase_PRICE"))
				product.setPURCHASE_PRICE(jsonObj.getDouble("purchase_PRICE"));
			else if (productid == 0)
				product.setPURCHASE_PRICE(0.0);

			if (jsonObj.has("product_WEIGHT")  && !jsonObj.isNull("product_WEIGHT"))
				product.setPRODUCT_WEIGHT(jsonObj.getDouble("product_WEIGHT"));
			else if (productid == 0)
				product.setPRODUCT_WEIGHT(0.0);

			if (productid == 0)
				product.setISACTIVE("Y");
			else if (jsonObj.has("isactive"))
				product.setISACTIVE(jsonObj.getString("isactive"));

			product.setMODIFIED_BY(apiRequest.getLong("request_ID"));
			product.setMODIFIED_WORKSTATION(apiRequest.getString("log_WORKSTATION"));
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
			responseentity = new ResponseEntity(getAPIResponse(null, products.get(0) , null, null, null, apiRequest, true), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(products, null, null , null, null, apiRequest, true), HttpStatus.OK);
		return responseentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/product/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		Product product = productrepository.findOne(id);
		productrepository.delete(product);

		return new ResponseEntity(getAPIResponse(null, product , null, null, null, apiRequest, true), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/product/"+id, null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject product = new JSONObject();
		product.put("id", id);
		product.put("isactive", "N");

		return insertupdateAll(null, product, apiRequest);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/product/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		JSONObject jsonObj = new JSONObject(data);
		long application_ID = 0;

		if (jsonObj.has("application_ID"))
			application_ID = jsonObj.getLong("application_ID");

		List<Product> products = ((active == true)
				? productrepository.findBySearch(application_ID,"%" + jsonObj.getString("search") + "%")
						: productrepository.findAllBySearch(application_ID,"%" + jsonObj.getString("search") + "%"));

		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, true), HttpStatus.OK);
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
		JSONObject apiRequest = AccessToken.checkToken("POST", "/product/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		List<Product> products = new ArrayList<Product>();
		JSONObject jsonObj = new JSONObject(data);

		JSONArray searchObject = new JSONArray();
		List<Integer> productcategory_IDS = new ArrayList<Integer>(); 
		List<Integer> saleledgeraccount_IDS = new ArrayList<Integer>();
		List<Integer> purchaseledgeraccount_IDS = new ArrayList<Integer>();

		productcategory_IDS.add((int) 0);
		saleledgeraccount_IDS.add((int) 0);
		purchaseledgeraccount_IDS.add((int) 0);

		long productcategory_ID = 0, saleledgeraccount_ID = 0, purchaseledgeraccount_ID = 0;

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

		if (jsonObj.has("saleledgeraccount_ID") && !jsonObj.isNull("saleledgeraccount_ID") && jsonObj.getLong("saleledgeraccount_ID") != 0) {
			saleledgeraccount_ID = jsonObj.getLong("saleledgeraccount_ID");
			saleledgeraccount_IDS.add((int) saleledgeraccount_ID);
		} else if (jsonObj.has("saleledgeraccount") && !jsonObj.isNull("saleledgeraccount") && jsonObj.getLong("saleledgeraccount") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("ledgeraccount/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("ledgeraccount/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
			}

			saleledgeraccount_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				saleledgeraccount_IDS.add((int) searchObject.getJSONObject(i).getLong("ledgeraccount_ID"));
			}
		}

		if (jsonObj.has("purchaseledgeraccount_ID") && !jsonObj.isNull("purchaseledgeraccount_ID") && jsonObj.getLong("purchaseledgeraccount_ID") != 0) {
			purchaseledgeraccount_ID = jsonObj.getLong("purchaseledgeraccount_ID");
			purchaseledgeraccount_IDS.add((int) purchaseledgeraccount_ID);
		} else if (jsonObj.has("purchaseledgeraccount") && !jsonObj.isNull("purchaseledgeraccount") && jsonObj.getLong("purchaseledgeraccount") != 0) {
			if (active == true) {
				searchObject = new JSONArray(ServiceCall.POST("ledgeraccount/advancedsearch", jsonObj.toString().replace("\"", "'"), headToken, false));
			} else {
				searchObject = new JSONArray(ServiceCall.POST("ledgeraccount/advancedsearch/all", jsonObj.toString().replace("\"", "'"), headToken, false));
			}

			purchaseledgeraccount_ID = searchObject.length();
			for (int i=0; i<searchObject.length(); i++) {
				purchaseledgeraccount_IDS.add((int) searchObject.getJSONObject(i).getLong("ledgeraccount_ID"));
			}
		}

		if (productcategory_ID != 0 || saleledgeraccount_ID != 0 || purchaseledgeraccount_ID != 0) {
			products = ((active == true)
					? productrepository.findByAdvancedSearch(productcategory_ID, productcategory_IDS, saleledgeraccount_ID, saleledgeraccount_IDS, purchaseledgeraccount_ID, purchaseledgeraccount_IDS)
							: productrepository.findAllByAdvancedSearch(productcategory_ID, productcategory_IDS, saleledgeraccount_ID, saleledgeraccount_IDS, purchaseledgeraccount_ID, purchaseledgeraccount_IDS));
		}
		return new ResponseEntity(getAPIResponse(products, null , null, null, null, apiRequest, isWithDetail), HttpStatus.OK);
	}

	String getAPIResponse(List<Product> products, Product product , JSONArray jsonProducts, JSONObject jsonProduct, String message, JSONObject apiRequest, boolean isWithDetail) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "Product", message).toString();
		} else {
			if (product != null && isWithDetail == true) {
				List<Integer> ledgeraccountList = new ArrayList<Integer>();

				if (product.getPRODUCTCATEGORY_ID() != null) {
					JSONObject productcategory = new JSONObject(ServiceCall.GET("productcategory/"+product.getPRODUCTCATEGORY_ID(), apiRequest.getString("access_TOKEN"), false));
					product.setPRODUCTCATEGORY_DETAIL(productcategory.toString());
				}

				if (product.getTAXCODE_ID() != null) {
					JSONObject taxcode = new JSONObject(ServiceCall.GET("taxcode/"+product.getTAXCODE_ID(), apiRequest.getString("access_TOKEN"), false));
					product.setTAXCODE_DETAIL(taxcode.toString());
				}

				if (product.getSALELEDGERACCOUNT_ID() != null) {
					ledgeraccountList.add(Integer.parseInt(product.getSALELEDGERACCOUNT_ID().toString()));
				}

				if (product.getPURCHASELEDGERACCOUNT_ID() != null) {
					ledgeraccountList.add(Integer.parseInt(product.getPURCHASELEDGERACCOUNT_ID().toString()));
				}

				JSONArray ledgeraccountObject = new JSONArray(ServiceCall.POST("ledgeraccount/ids", "{ledgeraccounts: "+ledgeraccountList+"}", apiRequest.getString("access_TOKEN"), false));
				for (int j=0; j<ledgeraccountObject.length(); j++) {
					JSONObject ledgeraccount = ledgeraccountObject.getJSONObject(j);

					if (product.getSALELEDGERACCOUNT_ID() != null && product.getSALELEDGERACCOUNT_ID() == ledgeraccount.getLong("ledgeraccount_ID")) {
						product.setSALELEDGERACCOUNT_DETAIL(ledgeraccount.toString());
					}

					if (product.getPURCHASELEDGERACCOUNT_ID() != null && product.getPURCHASELEDGERACCOUNT_ID() == ledgeraccount.getLong("ledgeraccount_ID")) {
						product.setPURCHASELEDGERACCOUNT_DETAIL(ledgeraccount.toString());
					}
				}

				rtnAPIResponse = mapper.writeValueAsString(product);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (products != null && isWithDetail == true) {
				if (products.size()>0) {
					List<Integer> productcategoryList = new ArrayList<Integer>();
					List<Integer> taxcodeList = new ArrayList<Integer>();
				List<Integer> ledgeraccountList = new ArrayList<Integer>();

					for (int i=0; i<products.size(); i++) {
						if (products.get(i).getPRODUCTCATEGORY_ID() != null) {
							productcategoryList.add(Integer.parseInt(products.get(i).getPRODUCTCATEGORY_ID().toString()));
						}

						if (products.get(i).getTAXCODE_ID() != null) {
							taxcodeList.add(Integer.parseInt(products.get(i).getTAXCODE_ID().toString()));
						}

						if (products.get(i).getSALELEDGERACCOUNT_ID() != null) {
							ledgeraccountList.add(Integer.parseInt(products.get(i).getSALELEDGERACCOUNT_ID().toString()));
						}
		
						if (products.get(i).getPURCHASELEDGERACCOUNT_ID() != null) {
							ledgeraccountList.add(Integer.parseInt(products.get(i).getPURCHASELEDGERACCOUNT_ID().toString()));
						}
					}

					JSONArray productcategoryObject = new JSONArray(ServiceCall.POST("productcategory/ids", "{productcategories: "+productcategoryList+"}", apiRequest.getString("access_TOKEN"), false));
					JSONArray taxcodeObject = new JSONArray(ServiceCall.POST("taxcode/ids", "{taxcodes: "+taxcodeList+"}", apiRequest.getString("access_TOKEN"), false));
					JSONArray ledgeraccountObject = new JSONArray(ServiceCall.POST("ledgeraccount/ids", "{ledgeraccounts: "+ledgeraccountList+"}", apiRequest.getString("access_TOKEN"), false));

					for (int i=0; i<products.size(); i++) {
						for (int j=0; j<productcategoryObject.length(); j++) {
							JSONObject productcategory = productcategoryObject.getJSONObject(j);
							if (products.get(i).getPRODUCTCATEGORY_ID() != null && products.get(i).getPRODUCTCATEGORY_ID() == productcategory.getLong("productcategory_ID")) {
								products.get(i).setPRODUCTCATEGORY_DETAIL(productcategory.toString());
							}
						}
						for (int j=0; j<taxcodeObject.length(); j++) {
							JSONObject taxcode = taxcodeObject.getJSONObject(j);
							if (products.get(i).getTAXCODE_ID() != null && products.get(i).getTAXCODE_ID() == taxcode.getLong("taxcode_ID")) {
								products.get(i).setTAXCODE_DETAIL(taxcode.toString());
							}
						}
						for (int j=0; j<ledgeraccountObject.length(); j++) {
							JSONObject ledgeraccount = ledgeraccountObject.getJSONObject(j);
		
							if (products.get(i).getSALELEDGERACCOUNT_ID() != null && products.get(i).getSALELEDGERACCOUNT_ID() == ledgeraccount.getLong("ledgeraccount_ID")) {
								products.get(i).setSALELEDGERACCOUNT_DETAIL(ledgeraccount.toString());
							}
		
							if (products.get(i).getPURCHASELEDGERACCOUNT_ID() != null && products.get(i).getPURCHASELEDGERACCOUNT_ID() == ledgeraccount.getLong("ledgeraccount_ID")) {
								products.get(i).setPURCHASELEDGERACCOUNT_DETAIL(ledgeraccount.toString());
							}
						}
					}
				}
				
				rtnAPIResponse = mapper.writeValueAsString(products);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (product != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(product);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (products != null && isWithDetail == false) {
				rtnAPIResponse = mapper.writeValueAsString(products);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonProducts != null) {
				rtnAPIResponse = jsonProducts.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (jsonProduct != null) {
				rtnAPIResponse = jsonProduct.toString();
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");
			}
		}

		return rtnAPIResponse;
	}
};
