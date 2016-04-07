package com.careydevelopment.mozmetrics.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.mozmetrics.util.LocalHostHelper;

@Controller
public class BasicMetricsController {
	
	private static final Logger LOGGER = Logger.getLogger(BasicMetricsController.class);
 
    @RequestMapping("/basicmetrics")
    public String basicmetrics(Model model) {
    	model.addAttribute("localhost",LocalHostHelper.getLocalHostPrefix());
    	
        return "basicmetrics";
    }

}
