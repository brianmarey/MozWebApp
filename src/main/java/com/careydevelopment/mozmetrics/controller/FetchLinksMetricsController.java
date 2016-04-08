package com.careydevelopment.mozmetrics.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.mozmetrics.authenticator.Authenticator;
import com.careydevelopment.mozmetrics.service.LinksService;
import com.careydevelopment.mozmetrics.url.UrlReaderException;
import com.careydevelopment.mozmetrics.util.LinksConstants;

@RestController
public class FetchLinksMetricsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FetchLinksMetricsController.class);
 
    @RequestMapping("/fetchLinksMetrics")
    public String fetchLinksMetrics(@RequestParam(value="domain", required=true) String domain, Model model) {
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
		
		String response = "";
		
		try {
			LinksService linksService = new LinksService();
			linksService.setAuthenticator(authenticator);
			response = linksService.getLinks(domain, LinksConstants.LINKS_SCOPE_PAGE_TO_PAGE, 
					null, LinksConstants.LINKS_SORT_PAGE_AUTHORITY, LinksConstants.LINKS_COL_URL, 0, 140);
			LOGGER.info(response);
		} catch (UrlReaderException ue) {
			ue.printStackTrace();
			response = "error";
		}
		
        return response;
    }

}
