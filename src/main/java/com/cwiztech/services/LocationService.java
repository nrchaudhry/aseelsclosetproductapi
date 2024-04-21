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
public class LocationService {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String locationService;

	public LocationService(Environment env) {
		LocationService.locationService = env.getRequiredProperty("file_path.LOCATIONSERVICE");
	}

	public static String GET(String URI, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
		String appPath = AccessToken.findApplicationDetail(locationService, headers);

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
		String appPath = AccessToken.findApplicationDetail(locationService, headers);

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
		String appPath = AccessToken.findApplicationDetail(locationService, headers);

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
		String appPath = AccessToken.findApplicationDetail(locationService, headers);

		log.info("DELETE: " + appPath + URI);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.DELETE, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}
}

//if(calling.getLOCATION_ID() != null) {
	//JSONObject location = new JSONObject(LocationService.GET("location/"+calling.getLOCATION_ID(), apiRequest.getREQUEST_OUTPUT()));
	//calling.setLOCATION_DETAIL(location.toString());
//}





//if (callings.size()>0) {
//	List<Integer> locationList = new ArrayList<Integer>();
//	for (int i=0; i<callings.size(); i++) {
//		locationList.add(Integer.parseInt(callings.get(i).getLOCATION_ID().toString()));
//	}
//	JSONArray locationObject = new JSONArray(LocationService.POST("location/ids", "{locations: "+locationList+"}", apiRequest.getREQUEST_OUTPUT()));
	
//	for (int i=0; i<callings.size(); i++) {
//		for (int j=0; j<locationObject.length(); j++) {
//			JSONObject location = locationObject.getJSONObject(j);
//			if(callings.get(i).getLOCATION_ID() == location.getLong("location_ID") ) {
//				callings.get(i).setLOCATION_DETAIL(location.toString());
//			}
//		}
//	}
//}


