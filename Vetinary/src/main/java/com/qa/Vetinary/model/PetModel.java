package com.qa.Vetinary.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Pets")
public class PetModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String type;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "person_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private PersonModel person;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
		@JoinTable(name = "pet_injuries",
		joinColumns = { @JoinColumn(name = "pet_id")},
		inverseJoinColumns = {@JoinColumn(name = "injury_id") })
		private Set<InjuryModel> injuries = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PersonModel getPerson() {
		return person;
	}

	public void setPerson(PersonModel person) {
		this.person = person;
	}

	public Set<InjuryModel> getInjuries() {
		return injuries;
	}

	public void setInjuries(Set<InjuryModel> injuries) {
		this.injuries = injuries;
	}
	
	
	
	
}
