package com.developers.spring.datajpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class DevelopersController {	
	//@RequestMapping("/developers")
	//@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@RequestMapping(value = "/developers", method = RequestMethod.GET)
	
    @ResponseBody
    String home() {
      return "Hello World1";
    }
    
}
