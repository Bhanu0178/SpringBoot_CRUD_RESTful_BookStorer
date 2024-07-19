package org.bhanu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto 
{
	private Integer id;
	private String title;
	private String author;
	private Double price;
	private Integer stock;
}
