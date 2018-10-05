package com.qa.Vetinary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.Vetinary.model.InjuryModel;

@Repository
public interface InjuryRepo extends JpaRepository<InjuryModel, Long> {

}
