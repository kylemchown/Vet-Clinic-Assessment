package com.example.DatabaseConnection.repository;

import com.example.DatabaseConnection.model.MySpringBootDataModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySpringBootRepository extends JpaRepository<MySpringBootDataModel,Long> {

}
