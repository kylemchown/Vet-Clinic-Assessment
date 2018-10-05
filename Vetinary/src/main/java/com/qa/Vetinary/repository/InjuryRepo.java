package com.qa.Vetinary.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.Vetinary.model.InjuryModel;
import com.qa.Vetinary.model.PetModel;

@Repository
public interface InjuryRepo extends JpaRepository<InjuryModel, Long> {
	Page<InjuryModel> findByPets(Set<PetModel> pets, Pageable pageable);
}
