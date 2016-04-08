package com.careydevelopment.mozmetrics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.mozmetrics.util.LocalHostHelper;

@Controller
public class LinksMetricsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LinksMetricsController.class);
 
    @RequestMapping("/linksmetrics")
    public String linksmetrics(Model model) {
    	model.addAttribute("localhost",LocalHostHelper.getLocalHostPrefix());
    	
        return "linksmetrics";
    }

}
