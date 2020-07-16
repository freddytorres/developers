package com.developers.spring.datajpa.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.developers.spring.datajpa.model.Tecnologia;
import com.developers.spring.datajpa.repository.TecnologiaRepository;

@Controller
public class TecnologiaController {

	@Autowired
	TecnologiaRepository tecnologiaRepository;

	@RequestMapping(value = "/tecnologia", method = RequestMethod.GET)
	@ResponseBody
	public List<Tecnologia> getTecnologia() {
		return tecnologiaRepository.findAll();
	}
	
	@RequestMapping(value = "/tecnologia", method = RequestMethod.POST)
	@ResponseBody
	public Tecnologia createTecnologia(@Valid @RequestBody Tecnologia tecnologia) {
		return tecnologiaRepository.save(tecnologia);
	}
}
