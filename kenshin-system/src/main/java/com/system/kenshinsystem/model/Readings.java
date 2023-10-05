package com.system.kenshinsystem.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Readings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true)
	private Double lightingReading;
	
	@Column(nullable = true)
	private Double powerReading;
	
	@Column(nullable = true)
	private Double waterReading;
	
	@Column(nullable = true)
	private Double gasReading;
	
	@Column(nullable = true)
	private Double lightingReadingBeforeChange;
	
	@Column(nullable = true)
	private Double powerReadingBeforeChange;
	
	@Column(nullable = true)
	private Double waterReadingBeforeChange;
	
	@Column(nullable = true)
	private Double gasReadingBeforeChange;
	
	@Column(nullable = true, length = 5000)
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "floor_id")
	private Floor floor;
	
	@ManyToOne
	@JoinColumn(name = "reading_date_id")
	private ReadingDate readingDate;
	
	@OneToMany(mappedBy = "reading", cascade = CascadeType.PERSIST)
	private List<ImageURL> imageURL;

}
