package com.qa.Vetinary.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.Vetinary.exceptions.ResourceNotFoundException;
import com.qa.Vetinary.model.PetModel;
import com.qa.Vetinary.repository.PersonRepo;
import com.qa.Vetinary.repository.PetRepo;

@RestController
@RequestMapping("/api")
public class PetController {
	@Autowired
	private PetRepo petRepo;
	
	@Autowired
	private PersonRepo personRepo;
	
	@GetMapping("/person/{personId}/pets")
	public Page<PetModel> getAllOrdersByPersonId(@PathVariable (value = "personId") Long personId, Pageable pageable){
		return petRepo.findByPersonId(personId,  pageable);
	}
	
	@PostMapping("person/{personId}/pets")
	public PetModel createComment(@PathVariable (value = "personId") Long personId,
			@Valid @RequestBody PetModel pet) {
		return personRepo.findById(personId).map(PersonModel -> {
			pet.setPerson(PersonModel);
			return petRepo.save(pet);
		}).orElseThrow(() -> new ResourceNotFoundException("Person", "id", pet));
	}
	
	@PutMapping("person/{personId}/pets/{petId}")
	public PetModel updatePet(@PathVariable (value = "personId") Long personId,
			@PathVariable (value = "petId") Long petId,
			@Valid @RequestBody PetModel petRequest) {
		
		if(!personRepo.existsById(personId)) {
			throw new ResourceNotFoundException("Person", "Id", personId);
		}
		
		
		return petRepo.findById(petId).map(pet -> {
			pet.setName(petRequest.getName());
			return petRepo.save(pet);
		}).orElseThrow(() -> new ResourceNotFoundException("Pet Id", "id", petRequest));
	}
	
	
	
	
	@DeleteMapping("/person/{personId}/pets/{petId}")
	public ResponseEntity<?> deleteComment(@PathVariable (value = "personId") Long personId,
			@PathVariable (value = "petId") Long petId){
		if(!personRepo.existsById(personId)) {
			throw new ResourceNotFoundException("Person", "Id", personId);
		}
		
		return petRepo.findById(petId).map(order -> {
			petRepo.delete(order);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Pet Id", petId.toString(), null));
		
	}
	
	
}
