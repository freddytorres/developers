package com.developers.spring.datajpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.developers.spring.datajpa.model.Developers;
import com.developers.spring.datajpa.repository.DevelopersRepository;

@Controller
public class DevelopersController {
	@Autowired
	DevelopersRepository developersRepository;

	@RequestMapping(value = "/developers", method = RequestMethod.GET)
	@ResponseBody
	public List<Developers> getDevelopers() {
		return developersRepository.findAll();
	}

}
