package com.qa.Vetinary.controller;

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

import com.qa.Vetinary.exceptions.ResourceNotFoundException;
import com.qa.Vetinary.model.PersonModel;
import com.qa.Vetinary.repository.PersonRepo;

@RestController
@RequestMapping("/api")
public class PersonController {
	
	@Autowired
	PersonRepo myRepo;
	
	/*Parameters - PersonModel PM
	 * This method creates a new person in a database and then returns the model if successful
	*/
	@PostMapping("/person")
	public PersonModel createPerson(@Valid @RequestBody PersonModel PM) {
		return myRepo.save(PM);
	}
	
	/*Parameters - Long personId
	 * This method returns the information for the person with id [personId]
	 * 
	 * */
	@GetMapping("person/{id}")
	public PersonModel getPersonbyID(@PathVariable(value = "id")Long personID) {
		return myRepo.findById(personID).orElseThrow(()-> new ResourceNotFoundException("PersonModel", "id", personID));
	}
	
	
	/*Parameters - n/a
	 * This method returns all people
	 *
	 */
	@GetMapping("/person")
	public List<PersonModel> getAllPeople(){
		return myRepo.findAll();
		}
	
	/*Parameters - Long personId, personModel personDetails
	 * This method updates the information of the person with id [personId]
	 *
	 */
		@PutMapping("/person/{id}")
		public PersonModel updatePerson(@PathVariable(value = "id") Long personID,
				@Valid @RequestBody PersonModel personDetails) {
			PersonModel PM = myRepo.findById(personID).orElseThrow(()->new ResourceNotFoundException("Person", "id", personID));
		
			PM.setName(personDetails.getName());

			PersonModel updateData = myRepo.save(PM);
			
			return updateData;
		}
		
		/*Parameters - Long personId
		 * This method deletes the person with id [personId]
		 *
		 */
		@DeleteMapping("/person/{id}")
		public ResponseEntity<?> deletePerson(@PathVariable(value = "id")Long personID){
			PersonModel PM = myRepo.findById(personID).orElseThrow(()->new ResourceNotFoundException("Person", "id", personID));
		
			myRepo.delete(PM);
			return ResponseEntity.ok().build();
		}

}
