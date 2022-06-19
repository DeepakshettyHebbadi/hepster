package com.hepster.book.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hepster.book.domain.Book;
import com.hepster.book.dto.BookDTO;
import com.hepster.book.repository.BookRepository;
import com.hepster.book.service.impl.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	BookRepository bookRepository;

	@PostMapping()
	public ResponseEntity<Void> saveBook(@RequestBody BookDTO bookDto) {

		bookService.saveBook(bookDto);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<Book> getBookByUuid(@PathVariable(value = "uuid") UUID uuid) {

		if (bookService.getBook(uuid) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(bookService.getBook(uuid), HttpStatus.OK);

	}

	@GetMapping()
	public ResponseEntity<List<Book>> getActiveBooks() {

		return new ResponseEntity<>(bookService.getActiveBooks(), HttpStatus.OK);

	}

	@PatchMapping("/{uuid}")
	public ResponseEntity<Book> updateBookByUuid(@PathVariable(value = "uuid") UUID uuid,
			@RequestBody Map<String, Object> bookUpdateRequest) {

		if (bookService.updateBook(uuid, bookUpdateRequest) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
