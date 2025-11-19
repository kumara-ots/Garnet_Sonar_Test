package com.fuso.enterprise.ots.srv.server.util;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class SmsApi {
	
	private final static Logger logger = LoggerFactory.getLogger(SmsApi.class);
	
	public static String callSms(String email , String contactNumber, String message) {
		
		logger.info("INSEIDE SMS CLASS");
		
		try {
			try {
				 RestTemplate restTemplate = new RestTemplate();
				 String url = "https://iotserver.ortusolis.in:3150/sendMessageToNumber";
				 JSONObject requestObj=new JSONObject();
				 requestObj.put("SourceEmail", email);
				 requestObj.put("ToMobileNumber", contactNumber);
				 requestObj.put("Message", message);
				 
				 HttpHeaders headers = new HttpHeaders();
				 headers.setContentType(MediaType.APPLICATION_JSON);
				 HttpEntity<String> entity = new HttpEntity<String>(requestObj.toString(),headers);
				 System.out.println("Before calling  sms server");
				 String answer = restTemplate.postForObject(url, entity, String.class);
				 logger.info("SMS RESULT"+answer);
				 return null;
			 }catch(Exception e) {
				 System.out.println(e);
				 logger.info("SMS ERROR"+e);
				 return null;
			 }
		}catch(Exception e) {
			 System.out.println(e);
			 return null;
		 }
		
	  }
}
