package com.qa.Vetinary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.Vetinary.model.PetModel;

@Repository
public interface PetRepo extends JpaRepository<PetModel,Long> {
	Page<PetModel> findByPersonId(Long personId, Pageable pageable);
}
