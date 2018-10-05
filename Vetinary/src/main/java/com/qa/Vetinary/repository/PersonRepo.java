package com.qa.Vetinary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.Vetinary.model.PersonModel;

@Repository
public interface PersonRepo extends JpaRepository<PersonModel,Long>{

}
