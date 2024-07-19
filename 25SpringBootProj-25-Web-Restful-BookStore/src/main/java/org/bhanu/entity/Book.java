package org.bhanu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BookStore")
@Getter
@Setter
public class Book 
{
	@Id
	private Integer id;
	
	@Column(length = 50)
	private String title;
	
	@Column(length = 50)
	private String author;
	
	@Column
	private Double price;
	
	@Column
	private Integer stock;
	
}
