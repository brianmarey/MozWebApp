package com.careydevelopment.mozmetrics.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.mozmetrics.authenticator.Authenticator;
import com.careydevelopment.mozmetrics.response.UrlResponse;
import com.careydevelopment.mozmetrics.service.URLMetricsService;
import com.google.gson.Gson;

@RestController
public class FetchBasicMetricsController {
	
	private static final Logger LOGGER = Logger.getLogger(FetchBasicMetricsController.class);
 
    @RequestMapping("/fetchBasicMetrics")
    public String fetchBasicMetrics(@RequestParam(value="domain", required=true) String domain, Model model) {
		Properties props = new Properties();
		
		try {
			File file = new File("/etc/tomcat8/resources/moz.properties");
			FileInputStream inStream = new FileInputStream (file);
			props.load(inStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Add your accessID here
		String accessID = props.getProperty("moz.accessId");
		
		//Add your secretKey here
		String secretKey = props.getProperty("moz.secretKey");
		
		Authenticator authenticator = new Authenticator();
		authenticator.setAccessID(accessID);
		authenticator.setSecretKey(secretKey);
		
		LOGGER.info("running service");
		
		URLMetricsService urlMetricsService = new URLMetricsService(authenticator);
		String response = urlMetricsService.getUrlMetrics(domain);
		LOGGER.info(response);
		/*Gson gson = new Gson();
		UrlResponse res = gson.fromJson(response, UrlResponse.class);
		LOGGER.info(res);
		  */  	
        return response;
    }

}
