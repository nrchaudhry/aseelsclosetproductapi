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
public class EmployeeService {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String employeeService;

	public EmployeeService(Environment env) {
		EmployeeService.employeeService = env.getRequiredProperty("file_path.EMPLOYEESERVICE");
	}

	public static String GET(String URI, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
		String appPath = AccessToken.findApplicationDetail(employeeService, headers);

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
		String appPath = AccessToken.findApplicationDetail(employeeService, headers);

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
		String appPath = AccessToken.findApplicationDetail(employeeService, headers);

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
		String appPath = AccessToken.findApplicationDetail(employeeService, headers);

		log.info("DELETE: " + appPath + URI);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.DELETE, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}
}

//if(calling.getEMPLOYEE_ID() != null) {
	//JSONObject employee = new JSONObject(EmployeeService.GET("employee/"+calling.getEMPLOYEE_ID(), apiRequest.getREQUEST_OUTPUT()));
	//calling.setEMPLOYEE_DETAIL(employee.toString());
//}





//if (callings.size()>0) {
//	List<Integer> employeeList = new ArrayList<Integer>();
//	for (int i=0; i<callings.size(); i++) {
//		employeeList.add(Integer.parseInt(callings.get(i).getEMPLOYEE_ID().toString()));
//	}
//	JSONArray employeeObject = new JSONArray(EmployeeService.POST("employee/ids", "{employees: "+employeeList+"}", apiRequest.getREQUEST_OUTPUT()));
	
//	for (int i=0; i<callings.size(); i++) {
//		for (int j=0; j<employeeObject.length(); j++) {
//			JSONObject employee = employeeObject.getJSONObject(j);
//			if(callings.get(i).getEMPLOYEE_ID() == employee.getLong("employee_ID") ) {
//				callings.get(i).setEMPLOYEE_DETAIL(employee.toString());
//			}
//		}
//	}
//}


