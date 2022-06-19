package com.hepster.book.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

	private UUID bookId;
	private String title;
	private String author;
	private Double price;
	private boolean active;

	

}
