package com.cwiztech.services;

import java.text.ParseException;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class SageService {
    private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
    private static String sageService;
    private static String clientID = "0d1d7691-5b14-4610-8ab5-b87478a10e2a/b9ccc1b3-cc20-4f34-ba35-c25993680a7b";
    private static String clientSecret = "j*5ll~CCn$}]+kP-]n!M";
    private static String grantType = "refresh_token";

    public SageService(Environment env) {
        SageService.sageService = env.getRequiredProperty("file_path.SAGESERVICE");
    }

    public static String GET(String URI, String accessToken)
            throws JsonProcessingException, JSONException, ParseException, InterruptedException {
        String rtnAPIResponse="Invalid Resonse";
        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
        String appPath = AccessToken.findApplicationDetail(sageService, headers);
        headers = AccessToken.getHttpHeader(generateToken(AccessToken.applicationID, AccessToken.oauthservicePath, AccessToken.refreshToken, accessToken));

        log.info("GET: " + appPath + URI);

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.GET, entity, String.class);
        rtnAPIResponse=response.getBody().toString();

        log.info("Response: " + rtnAPIResponse);
        log.info("----------------------------------------------------------------------------------");
        return rtnAPIResponse;
    }

    public static String POST(String URI, String body, String accessToken)
            throws JsonProcessingException, JSONException, ParseException, InterruptedException {
        String rtnAPIResponse="Invalid Resonse";
        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
        String appPath = AccessToken.findApplicationDetail(sageService, headers);
        headers = AccessToken.getHttpHeader(generateToken(AccessToken.applicationID, AccessToken.oauthservicePath, AccessToken.refreshToken, accessToken));

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
        String appPath = AccessToken.findApplicationDetail(sageService, headers);

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
        String appPath = AccessToken.findApplicationDetail(sageService, headers);

        log.info("DELETE: " + appPath + URI);

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.DELETE, entity, String.class);
        rtnAPIResponse=response.getBody().toString();

        log.info("Response: " + rtnAPIResponse);
        log.info("----------------------------------------------------------------------------------");
        return rtnAPIResponse;
    }

    public static String generateToken(long applicationID, String oauthservicePath, String refreshToken, String accessToken)
            throws JsonProcessingException, JSONException, ParseException, InterruptedException {
        String rtnAPIResponse="Invalid Resonse";
        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        log.info("Generate Token");

        HttpHeaders headers = new HttpHeaders();

        JSONObject body = new JSONObject();
        body.put("client_id", clientID);
        body.put("client_secret", clientSecret);
        body.put("grant_type", grantType);
        body.put("refresh_token", refreshToken);
        
        headers.add("Content-type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);

        log.info("oauthservicePath: " + oauthservicePath);
        log.info("Body: " + body.toString());
        log.info("entity: " + entity.toString());

        
        int getDataCount = 1;
        while (getDataCount <= 20) {
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(oauthservicePath, entity, String.class);
                rtnAPIResponse = response.getBody().toString();
                getDataCount = 21;
            } catch (Exception e) {
                log.info(e.getMessage());
                Thread.sleep(5000);
                getDataCount = getDataCount + 1;
            }
        }
        
        body = new JSONObject(rtnAPIResponse);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("application_ID", applicationID);
        jsonObj.put("authorization_CODE", body.getString("refresh_token"));
        
        UserLoginService.POST("application", jsonObj.toString(), accessToken);

        log.info("Response: " + rtnAPIResponse);
        log.info("----------------------------------------------------------------------------------");
        return body.getString("access_token");
    }
}




