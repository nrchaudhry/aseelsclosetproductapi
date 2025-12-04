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
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cwiztech.product.model.Product;
import com.cwiztech.product.repository.productRepository;
import com.cwiztech.log.apiRequestLog;
import com.cwiztech.services.ServiceCall;
import com.cwiztech.services.SageService;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class productSageController {
	private static final Logger log = LoggerFactory.getLogger(productSageController.class);
	private static String sageService;

	public productSageController(Environment env) {
		productSageController.sageService = env.getRequiredProperty("file_path.SAGESERVICE");
	}

	@Autowired
	private productRepository productrepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/sendtosage", method = RequestMethod.GET)
	public ResponseEntity SendToSage(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/product/sendtosage", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);


		JSONArray objProducts = new JSONArray();

		JSONArray ledgeraccounts = new JSONArray(ServiceCall.GET("ledgeraccount/all", apiRequest.getString("access_TOKEN"), false));

		List<Product> products = productrepository.findByNullSageID();
		for (int i=0; i<products.size(); i++) {
			JSONObject objProduct = new JSONObject();
			JSONObject objProductDetail = new JSONObject();	

			String saleledgeraccount = "", purchaseledgeraccount = "";

			for (int j=0; j<ledgeraccounts.length(); j++) {
				if (ledgeraccounts.getJSONObject(j).getLong("ledgeraccount_ID") == products.get(i).getPURCHASELEDGERACCOUNT_ID()) {
					purchaseledgeraccount = ledgeraccounts.getJSONObject(j).getString("sage_ID");
				}
				if (ledgeraccounts.getJSONObject(j).getLong("ledgeraccount_ID") == products.get(i).getSALELEDGERACCOUNT_ID()) {
					saleledgeraccount = ledgeraccounts.getJSONObject(j).getString("sage_ID");
				}
			}

			objProductDetail.put("catalog_item_type_id", "STOCK_ITEM");	
			objProductDetail.put("item_code", products.get(i).getPRODUCT_CODE());
			objProductDetail.put("description", products.get(i).getPRODUCT_NAME());	
			objProductDetail.put("weight", products.get(i).getPRODUCT_WEIGHT());
			objProductDetail.put("cost_price", products.get(i).getPURCHASE_PRICE());
			objProductDetail.put("sales_ledger_account_id", saleledgeraccount);
			objProductDetail.put("purchase_ledger_account_id", purchaseledgeraccount);
			if (products.get(i).getTAXCODE_ID() == 1) {
				objProductDetail.put("sales_tax_rate_id", "GB_STANDARD");
				objProductDetail.put("purchase_tax_rate_id", "GB_STANDARD");
			} else {
				objProductDetail.put("sales_tax_rate_id", "GB_ZERO");
				objProductDetail.put("purchase_tax_rate_id", "GB_ZERO");
			}

			JSONArray productitems = new JSONArray(ServiceCall.POST("productitem/advancedsearch", "{product_ID: "+products.get(i).getPRODUCT_ID()+"}", apiRequest.getString("access_TOKEN"), false));
			if (productitems.length()>0) {
				List<Object> salesprices = new ArrayList<Object>();

				JSONObject productitem = productitems.getJSONObject(0);
				JSONArray productitempricelevels = new JSONArray(ServiceCall.POST("productitempricelevel/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getString("access_TOKEN"), false));
				for (int j=0; j<productitempricelevels.length(); j++) {
					JSONObject productitempricelevel = new JSONObject();
					if (sageService.compareTo("BFSSAGE") == 0)
						productitempricelevel.put("product_sales_price_type_id", "c0a3306394684a82b2abb0a9af244b55");
					else
						productitempricelevel.put("product_sales_price_type_id", "264fdb5bd99e45c389e5e322f87d6780");

					productitempricelevel.put("price", productitempricelevels.getJSONObject(j).getDouble("productitem_UNITPRICE"));
					productitempricelevel.put("price_includes_tax", "false");

					salesprices.add(productitempricelevel);
				}
				objProductDetail.put("sales_prices", salesprices);


				//				JSONArray productiteminventories = new JSONArray(ServiceCall.POST("productiteminventory/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
				//		        for (int j=0; j<productitempricelevels.length(); j++) {
				//		        }

				JSONArray productitemattributevalues = new JSONArray(ServiceCall.POST("productitemattributevalue/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getString("access_TOKEN"), false));
				for (int j=0; j<productitemattributevalues.length(); j++) {
					JSONObject productitemattributevalue = productitemattributevalues.getJSONObject(j);
					JSONObject jsonproductattribute = new JSONObject(productitemattributevalue.getString("productattribute_DETAIL"));
					JSONObject jsonattribute = new JSONObject(jsonproductattribute.getString("attribute_DETAIL"));

					if (jsonattribute.getString("attribute_KEY").equals("taxcode")) {
						if (productitemattributevalues.getJSONObject(j).getLong("productattributevalue_ID")==1) {
							objProductDetail.put("sales_tax_rate_id", "GB_STANDARD");
							objProductDetail.put("purchase_tax_rate_id", "GB_STANDARD");
						} else {
							objProductDetail.put("sales_tax_rate_id", "GB_ZERO");
							objProductDetail.put("purchase_tax_rate_id", "GB_ZERO");
						}
					}

					if (!productitemattributevalue.isNull("productattributeitem_ID"))
						productitem.put(jsonattribute.getString("attribute_KEY"), productitemattributevalues.getJSONObject(j).getLong("productattributevalue_ID"));
					else if (!productitemattributevalues.getJSONObject(j).isNull("productattributeitem_VALUE"))
						productitem.put(jsonattribute.getString("attribute_KEY"), productitemattributevalues.getJSONObject(j).getString("productattributeitem_VALUE"));
				}
			}


			objProduct.put("stock_item", objProductDetail);
			JSONObject apiRequestResponse = SageService.POST("stock_items", objProduct.toString(), headToken);

			JSONObject product = new JSONObject();
			product.put("product_ID", products.get(i).getPRODUCT_ID());
			if (apiRequestResponse.has("id"))
				product.put("sage_ID", apiRequestResponse.getString("id"));
			else
				product.put("sage_ID", apiRequestResponse.toString());

			product = new JSONObject(ServiceCall.POST("product", product.toString(), apiRequest.getString("access_TOKEN"), false));

			objProducts.put(objProduct);

		}

		return new ResponseEntity(getAPIResponse(null, null , objProducts, null, null, apiRequest, false), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getfromsage/{page}", method = RequestMethod.GET)
	public ResponseEntity GetFromSage(@PathVariable Long page, @RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/product/getfromsage", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);

		boolean lastPage = false;

		JSONArray ledgeraccounts = new JSONArray(ServiceCall.GET("ledgeraccount", apiRequest.getString("access_TOKEN"), false));	
		JSONArray taxcodes = new JSONArray(ServiceCall.GET("taxcode", apiRequest.getString("access_TOKEN"), false));	

		JSONObject response = SageService.GET("products?page=" + (page-1) + "&items_per_page=100", headToken);

		log.info("response sage: "+response);

		while (lastPage == false) {
			JSONArray jsonProducts = response.getJSONArray("$items");

			for (int i=0; i<jsonProducts.length(); i++) {
				JSONObject jsonProduct = jsonProducts.getJSONObject(i);
				long productitem_id = 0;

				JSONObject responseProduct = SageService.GET("stock_items/" + jsonProduct.getString("id"), headToken);
				if (responseProduct.getBoolean("active") == false)
					break;
				
				Product product = productrepository.findBySageID(jsonProduct.getString("id"));
				//				Product product = productrepository.findBySageID("186b84b02cf5444b979cac29b585451a");
				JSONObject objProduct = new JSONObject();
				JSONObject objProductItem = new JSONObject();
				JSONObject objProductItemPrice = new JSONObject();
				JSONObject objProductItemInventory = new JSONObject();
				if (product != null) {
					objProduct.put("product_ID", product.getPRODUCT_ID());
					JSONArray productitems = new JSONArray(ServiceCall.POST("productitem/advancedsearch", objProduct.toString(), apiRequest.getString("access_TOKEN"), false));
					if (productitems.length() > 0) {
						productitem_id = productitems.getJSONObject(productitems.length()-1).getLong("productitem_ID");
						objProductItem.put("productitem_ID", productitem_id);

						JSONArray productitemprices = new JSONArray(ServiceCall.POST("productitempricelevel/advancedsearch", objProductItem.toString(), apiRequest.getString("access_TOKEN"), false));
						JSONArray productiteminventory = new JSONArray(ServiceCall.POST("productiteminventory/advancedsearch", objProductItem.toString(), apiRequest.getString("access_TOKEN"), false));
					} else {
						break;
					}
				} else {
					objProduct.put("productcategory_ID", 0);
					if (responseProduct.has("item_code") && !responseProduct.isNull("item_code")) {
						objProduct.put("product_CODE", responseProduct.getString("item_code"));
					} else {
						responseProduct.put("product_CODE", "AAA");
					}
					if (responseProduct.has("description") && !responseProduct.isNull("description")) {
						objProduct.put("product_NAME", responseProduct.getString("description"));
						objProductItem.put("productitem_NAME", responseProduct.getString("description"));
					} else {
						objProduct.put("product_NAME","AAA");
						objProductItem.put("productitem_NAME","AAA");
					}
					objProductItem.put("application_ID", 1);

				}
				//				JSONObject responseProduct = new JSONObject(SageService.GET("contacts/186b84b02cf5444b979cac29b585451a?show_balance=true&show_overdue_balance=true", headToken));

				objProduct.put("sage_ID", responseProduct.getString("id"));

				for (int j=0; j<taxcodes.length(); j++) {
					JSONObject taxcode = ledgeraccounts.getJSONObject(j);

					if (responseProduct.has("sales_tax_rate") && !responseProduct.isNull("sales_tax_rate") && !taxcode.isNull("sage_ID") && taxcode.getString("sage_ID").compareTo(responseProduct.getJSONObject("sales_tax_rate").getString("id")) == 0) {
						objProduct.put("taxcode_ID", taxcode.getLong("ledgeraccount_ID"));
						break;
					}
				}

				for (int j=0; j<ledgeraccounts.length(); j++) {
					JSONObject ledgeraccount = ledgeraccounts.getJSONObject(j);

					if (responseProduct.has("purchase_ledger_account") && !responseProduct.isNull("purchase_ledger_account") && !ledgeraccount.isNull("sage_ID") && ledgeraccount.getString("sage_ID").compareTo(responseProduct.getJSONObject("purchase_ledger_account").getString("id")) == 0) {
						objProduct.put("purchaseledgeraccount_ID", ledgeraccount.getLong("ledgeraccount_ID"));
					}

					if (responseProduct.has("sales_ledger_account") && !responseProduct.isNull("sales_ledger_account") && !ledgeraccount.isNull("sage_ID") && ledgeraccount.getString("sage_ID").compareTo(responseProduct.getJSONObject("sales_ledger_account").getString("id")) == 0) {
						objProduct.put("saleledgeraccount_ID", ledgeraccount.getLong("ledgeraccount_ID"));
					}
				}

				if (responseProduct.has("cost_price") && !responseProduct.isNull("cost_price")) {
					objProduct.put("purchase_PRICE", responseProduct.getDouble("cost_price"));
				}

				if (responseProduct.has("weight") && !responseProduct.isNull("weight")) {
					objProduct.put("product_WEIGHT", responseProduct.getDouble("weight"));
				}

				if (responseProduct.has("notes") && !responseProduct.isNull("notes")) {
					objProduct.put("product_DESC", responseProduct.getString("notes"));
					objProductItem.put("productitem_DESC", responseProduct.getString("notes"));
				}

				log.info("objProduct: "+objProduct);
				log.info("page: "+page);

				JSONObject products = new JSONObject(ServiceCall.POST("product", objProduct.toString(), apiRequest.getString("access_TOKEN"), false));

				objProductItem.put("product_ID", products.getLong("product_ID"));
				JSONObject productitems = new JSONObject(ServiceCall.POST("productitem", objProductItem.toString(), apiRequest.getString("access_TOKEN"), false));

				JSONArray salesprices = responseProduct.getJSONArray("sales_prices");
				objProductItemPrice.put("productitem_ID", productitems.getLong("productitem_ID"));
				objProductItemPrice.put("pricelevel_CODE", "2");
				objProductItemPrice.put("currency_CODE", "GB");
				objProductItemPrice.put("productitem_QUANTITY", 1);
				objProductItemPrice.put("productitem_UNITPRICE", salesprices.getJSONObject(0).getString("price"));
				ServiceCall.POST("productitempricelevel", objProductItemPrice.toString(), apiRequest.getString("access_TOKEN"), false);

				objProductItemInventory.put("productitem_ID", productitems.getLong("productitem_ID"));
				objProductItemInventory.put("productlocation_CODE", "1");
				objProductItemInventory.put("quantity_ONHAND", responseProduct.getDouble("quantity_in_stock"));
				objProductItemInventory.put("quantity_AVAILABLE", responseProduct.getDouble("quantity_in_stock"));
				ServiceCall.POST("productiteminventory", objProductItemInventory.toString(), apiRequest.getString("access_TOKEN"), false);
			}


			if (response.isNull("$next"))
				lastPage = true;
			response = SageService.GET("stock_items?page=" + page + "&items_per_page=100", headToken);
			page = page + 1;
		}

		return new ResponseEntity(getAPIResponse(null, null , null, null, "Product Updated", apiRequest, false), HttpStatus.OK);
	}

	String getAPIResponse(List<Product> products, Product product , JSONArray jsonProducts, JSONObject jsonProduct, String message, JSONObject apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String rtnAPIResponse="Invalid Resonse";

		if (message != null) {
			rtnAPIResponse = apiRequestLog.apiRequestErrorLog(apiRequest, "Product", message).toString();
		} else {
			if (product != null) {
				JSONObject productcategory = new JSONObject(ServiceCall.GET("productcategory/"+product.getPRODUCTCATEGORY_ID(), apiRequest.getString("access_TOKEN"), false));
				product.setPRODUCTCATEGORY_DETAIL(productcategory.toString());

				rtnAPIResponse = mapper.writeValueAsString(product);
				apiRequestLog.apiRequestSaveLog(apiRequest, rtnAPIResponse, "Success");

			} else if (products != null) {
				if (products.size()>0) {
					List<Integer> productcategoryList = new ArrayList<Integer>();
					for (int i=0; i<products.size(); i++) {
						productcategoryList.add(Integer.parseInt(products.get(i).getPRODUCTCATEGORY_ID().toString()));
					}
					JSONArray productcategoryObject = new JSONArray(ServiceCall.POST("productcategory/ids", "{productcategories: "+productcategoryList+"}", apiRequest.getString("access_TOKEN"), false));

					for (int i=0; i<products.size(); i++) {
						for (int j=0; j<productcategoryObject.length(); j++) {
							JSONObject productcategory = productcategoryObject.getJSONObject(j);
							if (products.get(i).getPRODUCTCATEGORY_ID() == productcategory.getLong("productcategory_ID") ) {
								products.get(i).setPRODUCTCATEGORY_DETAIL(productcategory.toString());
							}
						}
					}
				}
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updatetosage", method = RequestMethod.GET)
	public ResponseEntity updateToSage(@RequestHeader(value = "Authorization") String headToken, @RequestHeader(value = "LimitGrant") String LimitGrant) throws JsonProcessingException, JSONException, ParseException, InterruptedException {
		JSONObject apiRequest = AccessToken.checkToken("GET", "/product/sendtosage", null, null, headToken);
		if (apiRequest.has("error")) return new ResponseEntity(apiRequest.toString(), HttpStatus.OK);


		JSONArray objProducts = new JSONArray();

		JSONArray ledgeraccounts = new JSONArray(ServiceCall.GET("ledgeraccount/all", apiRequest.getString("access_TOKEN"), false));

		List<Product> products = productrepository.findBySage();
		for (int i=0; i<products.size(); i++) {
			JSONObject objProduct = new JSONObject();
			JSONObject objProductDetail = new JSONObject();	

			String saleledgeraccount = "", purchaseledgeraccount = "";

			for (int j=0; j<ledgeraccounts.length(); j++) {
				if (ledgeraccounts.getJSONObject(j).getLong("ledgeraccount_ID") == products.get(i).getPURCHASELEDGERACCOUNT_ID()) {
					purchaseledgeraccount = ledgeraccounts.getJSONObject(j).getString("sage_ID");
				}
				if (ledgeraccounts.getJSONObject(j).getLong("ledgeraccount_ID") == products.get(i).getSALELEDGERACCOUNT_ID()) {
					saleledgeraccount = ledgeraccounts.getJSONObject(j).getString("sage_ID");
				}
			}

//			objProductDetail.put("catalog_item_type_id", "STOCK_ITEM");	
			objProductDetail.put("item_code", products.get(i).getPRODUCT_CODE());
//			objProductDetail.put("description", products.get(i).getPRODUCT_NAME());	
//			objProductDetail.put("weight", products.get(i).getPRODUCT_WEIGHT());
//			objProductDetail.put("cost_price", products.get(i).getPURCHASE_PRICE());
//			objProductDetail.put("sales_ledger_account_id", saleledgeraccount);
//			objProductDetail.put("purchase_ledger_account_id", purchaseledgeraccount);
//			if (products.get(i).getTAXCODE_ID() == 1) {
//				objProductDetail.put("sales_tax_rate_id", "GB_STANDARD");
//				objProductDetail.put("purchase_tax_rate_id", "GB_STANDARD");
//			} else {
//				objProductDetail.put("sales_tax_rate_id", "GB_ZERO");
//				objProductDetail.put("purchase_tax_rate_id", "GB_ZERO");
//			}
//
//			JSONArray productitems = new JSONArray(ServiceCall.POST("productitem/advancedsearch", "{product_ID: "+products.get(i).getPRODUCT_ID()+"}", apiRequest.getString("access_TOKEN"), false));
//			if (productitems.length()>0) {
//				List<Object> salesprices = new ArrayList<Object>();
//
//				JSONObject productitem = productitems.getJSONObject(0);
//				JSONArray productitempricelevels = new JSONArray(ServiceCall.POST("productitempricelevel/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getString("access_TOKEN"), false));
//				for (int j=0; j<productitempricelevels.length(); j++) {
//					JSONObject productitempricelevel = new JSONObject();
//					if (sageService.compareTo("BFSSAGE") == 0)
//						productitempricelevel.put("product_sales_price_type_id", "c0a3306394684a82b2abb0a9af244b55");
//					else
//						productitempricelevel.put("product_sales_price_type_id", "264fdb5bd99e45c389e5e322f87d6780");
//
//					productitempricelevel.put("price", productitempricelevels.getJSONObject(j).getDouble("productitem_UNITPRICE"));
//					productitempricelevel.put("price_includes_tax", "false");
//
//					salesprices.add(productitempricelevel);
//				}
//				objProductDetail.put("sales_prices", salesprices);
//
//
//				//				JSONArray productiteminventories = new JSONArray(ServiceCall.POST("productiteminventory/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getREQUEST_OUTPUT()));
//				//		        for (int j=0; j<productitempricelevels.length(); j++) {
//				//		        }
//
//				JSONArray productitemattributevalues = new JSONArray(ServiceCall.POST("productitemattributevalue/advancedsearch", "{productitem_ID: "+productitem.getLong("productitem_ID")+"}", apiRequest.getString("access_TOKEN"), false));
//				for (int j=0; j<productitemattributevalues.length(); j++) {
//					JSONObject productitemattributevalue = productitemattributevalues.getJSONObject(j);
//					JSONObject jsonproductattribute = new JSONObject(productitemattributevalue.getString("productattribute_DETAIL"));
//					JSONObject jsonattribute = new JSONObject(jsonproductattribute.getString("attribute_DETAIL"));
//
//					if (jsonattribute.getString("attribute_KEY").equals("taxcode")) {
//						if (productitemattributevalues.getJSONObject(j).getLong("productattributevalue_ID")==1) {
//							objProductDetail.put("sales_tax_rate_id", "GB_STANDARD");
//							objProductDetail.put("purchase_tax_rate_id", "GB_STANDARD");
//						} else {
//							objProductDetail.put("sales_tax_rate_id", "GB_ZERO");
//							objProductDetail.put("purchase_tax_rate_id", "GB_ZERO");
//						}
//					}
//
//					if (!productitemattributevalue.isNull("productattributeitem_ID"))
//						productitem.put(jsonattribute.getString("attribute_KEY"), productitemattributevalues.getJSONObject(j).getLong("productattributevalue_ID"));
//					else if (!productitemattributevalues.getJSONObject(j).isNull("productattributeitem_VALUE"))
//						productitem.put(jsonattribute.getString("attribute_KEY"), productitemattributevalues.getJSONObject(j).getString("productattributeitem_VALUE"));
//				}
//			}


			objProduct.put("stock_item", objProductDetail);
			JSONObject apiRequestResponse = SageService.PUT("stock_items/"+products.get(i).getSAGE_ID(), objProduct.toString(), headToken);

//			JSONObject product = new JSONObject();
//			product.put("product_ID", products.get(i).getPRODUCT_ID());
//			if (apiRequestResponse.has("id"))
//				product.put("sage_ID", apiRequestResponse.getString("id"));
//			else
//				product.put("sage_ID", apiRequestResponse.toString());
//
//			product = new JSONObject(ServiceCall.POST("product", product.toString(), apiRequest.getString("access_TOKEN"), false));

			objProducts.put(apiRequestResponse);

		}

		return new ResponseEntity(getAPIResponse(null, null , objProducts, null, null, apiRequest, false), HttpStatus.OK);
	}

}
