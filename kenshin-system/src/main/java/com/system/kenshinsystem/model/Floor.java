package com.system.kenshinsystem.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Floor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	private Double area;
	
	@ManyToOne
	@JoinColumn(name = "building_id")
	private Building building;
	
	@OneToMany(mappedBy = "floor",cascade = CascadeType.PERSIST)
	@JsonIgnore
	private List<Tenant> tanants;
	
	@OneToMany(mappedBy = "floor",cascade = CascadeType.PERSIST)
	@JsonIgnore
	private List<Readings> readings;
	
	

}
