package com.qa.Vetinary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.Vetinary.repository.InjuryRepo;
import com.qa.Vetinary.repository.PetRepo;

@RestController
@RequestMapping("/api")
public class InjuryController {
	
	@Autowired
	private PetRepo petRepo;
	
	@Autowired
	private InjuryRepo injuryRepo;
	
	
}
