package com.developers.spring.datajpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class DevelopersController {
	@GetMapping("/developers")
    String home() {
      return "Hello World1";
    }
}
