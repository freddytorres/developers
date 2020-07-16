package com.developers.spring.datajpa.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.developers.spring.datajpa.exception.ResourceNotFoundException;
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

	@RequestMapping(value = "/developers", method = RequestMethod.POST)
	@ResponseBody
	public Developers createDevelopers(@Valid @RequestBody Developers developers) {
		return developersRepository.save(developers);
	}

	@RequestMapping(value = "/developers/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Developers> updateDevelopers(@PathVariable(value = "id") Long developerId,
			@Valid @RequestBody Developers developersDetails) throws ResourceNotFoundException {
		Developers developers = developersRepository.findById(developerId)
				.orElseThrow(() -> new ResourceNotFoundException("Developers no se encuentra : " + developerId));
		developers.setCedula(developersDetails.getCedula());
		developers.setNombre(developersDetails.getNombre());
		developers.setApellido(developersDetails.getApellido());
		final Developers updatedDevelopers = developersRepository.save(developers);
		return ResponseEntity.ok(updatedDevelopers);
	}

}
