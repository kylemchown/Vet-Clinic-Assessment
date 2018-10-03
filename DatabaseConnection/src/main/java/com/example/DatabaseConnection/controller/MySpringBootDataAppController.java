package com.example.DatabaseConnection.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DatabaseConnection.exception.ResourceNotFoundException;
import com.example.DatabaseConnection.model.MySpringBootDataModel;
import com.example.DatabaseConnection.repository.MySpringBootRepository;


@RestController
@RequestMapping("/api")
public class MySpringBootDataAppController {
	
	@Autowired
	MySpringBootRepository myRepository;
	
	//Method to create a person
	@PostMapping("/person")
	public MySpringBootDataModel createPerson(@Valid @RequestBody MySpringBootDataModel mSDM) {
		return myRepository.save(mSDM);
	}
	
	//Method to get a person
	@GetMapping("person/{id}")
	public MySpringBootDataModel getPersonbyID(@PathVariable(value = "id")Long personID) {
		return myRepository.findById(personID).orElseThrow(()-> new ResourceNotFoundException("MySpringBootDataModel", "id", personID));
	}
	
	
	//Method to get all people
	@GetMapping("/person")
	public List<MySpringBootDataModel> getAllPeople(){
		return myRepository.findAll();
		}
	
	
	//Method to update a person
	@PutMapping("/person/{id}")
	public MySpringBootDataModel updatePerson(@PathVariable(value = "id") Long personID,
			@Valid @RequestBody MySpringBootDataModel personDetails) {
		MySpringBootDataModel mSDM = myRepository.findById(personID).orElseThrow(()->new ResourceNotFoundException("Person", "id", personID));
	
		mSDM.setName(personDetails.getName());
		mSDM.setAddress(personDetails.getAddress());
		mSDM.setAge(personDetails.getAge());
		
		MySpringBootDataModel updateData = myRepository.save(mSDM);
		
		return updateData;
	}
	
	
	//Method to remove a person
	@DeleteMapping("/person/{id}")
	public ResponseEntity<?> deletePerson(@PathVariable(value = "id")Long personID){
		MySpringBootDataModel mSDM = myRepository.findById(personID).orElseThrow(()->new ResourceNotFoundException("Person", "id", personID));
	
		myRepository.delete(mSDM);
		return ResponseEntity.ok().build();
	}
	
}
