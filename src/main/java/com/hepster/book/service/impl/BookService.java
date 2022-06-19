package com.hepster.book.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hepster.book.domain.Book;
import com.hepster.book.dto.BookDTO;

public interface BookService {
	public void saveBook(BookDTO bookDto);

	public Book getBook(UUID bookId);

	public List<Book> getActiveBooks();

	public Book updateBook(UUID uuid,Map<String, Object> request);

}
