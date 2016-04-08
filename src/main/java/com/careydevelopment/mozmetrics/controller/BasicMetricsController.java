package com.careydevelopment.mozmetrics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.mozmetrics.util.LocalHostHelper;

@Controller
public class BasicMetricsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BasicMetricsController.class);
 
    @RequestMapping("/basicmetrics")
    public String basicmetrics(Model model) {
    	model.addAttribute("localhost",LocalHostHelper.getLocalHostPrefix());
    	model.addAttribute("toolsActive", "active");
    	
        return "basicmetrics";
    }

}
