package com.qa.Vetinary.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.Vetinary.exceptions.ResourceNotFoundException;
import com.qa.Vetinary.model.InjuryModel;
import com.qa.Vetinary.model.PetModel;
import com.qa.Vetinary.repository.InjuryRepo;
import com.qa.Vetinary.repository.PetRepo;

@RestController
@RequestMapping("/api")
public class InjuryController {
	
	@Autowired
	private PetRepo petRepo;
	
	@Autowired
	private InjuryRepo injuryRepo;
	
	@GetMapping("/person/{personId}/pets/{petId}/injuries")
	public Set<InjuryModel> getAllInjuriesByPetIdAndPersonId(@PathVariable (value = "personId") Long personId, @PathVariable (value = "petId") Long petId, Pageable pageable){
		Page<PetModel> a = petRepo.findByPersonId(personId,  pageable);
		List<PetModel> b = a.getContent();
		PetModel c = b.get(0);
		return c.getInjuries();
		
	}
	
	
	
	
	@PostMapping("person/{personId}/pets/{petId}/injuries")
	public InjuryModel addNewInjury(@PathVariable (value = "personId") Long personId, @PathVariable (value = "petId") Long petId, Pageable pageable,
			@Valid @RequestBody InjuryModel injury) {
		return petRepo.findAll().map(PetModel -> {
			Set<PetModel> temp = injury.getPets();
			temp.add(PetModel);
			injury.setPets(temp);
			return injuryRepo.save(injury);
		}).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", injury));
	}
}
