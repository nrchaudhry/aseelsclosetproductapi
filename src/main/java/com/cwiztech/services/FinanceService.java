package com.cwiztech.services;

import java.text.ParseException;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class FinanceService {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String financeService;

	public FinanceService(Environment env) {
		FinanceService.financeService = env.getRequiredProperty("file_path.FINANCESERVICE");
	}

	public static String GET(String URI, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
		String appPath = AccessToken.findApplicationDetail(financeService, headers);

		log.info("GET: " + appPath + URI);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.GET, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String POST(String URI, String body, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
		String appPath = AccessToken.findApplicationDetail(financeService, headers);

		log.info("POST: " + appPath + URI);
		log.info("Body: " + body);

		HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.POST, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String PUT(String URI, String body, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
		String appPath = AccessToken.findApplicationDetail(financeService, headers);

		log.info("PUT: " + appPath + URI);
		log.info("Body: " + body);

		HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.PUT, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String DELETE(String URI, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
		String appPath = AccessToken.findApplicationDetail(financeService, headers);

		log.info("DELETE: " + appPath + URI);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.DELETE, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}
}

//if(calling.getFINANCE_ID() != null) {
	//JSONObject finance = new JSONObject(FinanceService.GET("finance/"+calling.getFINANCE_ID(), apiRequest.getREQUEST_OUTPUT()));
	//calling.setFINANCE_DETAIL(finance.toString());
//}





//if (callings.size()>0) {
//	List<Integer> financeList = new ArrayList<Integer>();
//	for (int i=0; i<callings.size(); i++) {
//		financeList.add(Integer.parseInt(callings.get(i).getFINANCE_ID().toString()));
//	}
//	JSONArray financeObject = new JSONArray(FinanceService.POST("finance/ids", "{finances: "+financeList+"}", apiRequest.getREQUEST_OUTPUT()));
	
//	for (int i=0; i<callings.size(); i++) {
//		for (int j=0; j<financeObject.length(); j++) {
//			JSONObject finance = financeObject.getJSONObject(j);
//			if(callings.get(i).getFINANCE_ID() == finance.getLong("finance_ID") ) {
//				callings.get(i).setFINANCE_DETAIL(finance.toString());
//			}
//		}
//	}
//}


