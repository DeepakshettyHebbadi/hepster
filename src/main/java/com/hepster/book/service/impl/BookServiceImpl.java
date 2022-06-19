package com.hepster.book.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.hepster.book.domain.Book;
import com.hepster.book.dto.BookDTO;
import com.hepster.book.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	/**
	 * Create book
	 */
	@Override
	public void saveBook(BookDTO bookDto) {
		Book book = new Book();
		book.setBookId(bookDto.getBookId());
		book.setTitle(bookDto.getTitle());
		book.setPrice(bookDto.getPrice());
		book.setAuthor(bookDto.getAuthor());
		book.setActive(bookDto.isActive());
		bookRepository.save(book);

	}

	/**
	 * Get book from uuid
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public Book getBook(UUID bookId) {
		Book book = bookRepository.findByBookId(bookId);

		return book;

	}

	/**
	 * Get all the Active Books
	 */
	@Override
	public List<Book> getActiveBooks() {

		List<Book> activeBooks = bookRepository.findByActive(true);

		return activeBooks;
	}

	/**
	 * Update bookby UUID
	 * 
	 */
	@Override
	public Book updateBook(UUID uuid, Map<String, Object> request) {
		Book book = bookRepository.findByBookId(uuid);
		if (book == null) {
			return null;
		}

		request.forEach((key, value) -> {

			Field field = ReflectionUtils.findField(Book.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, book, value);

		});

		Book bookRequest = bookRepository.save(book);
		return bookRequest;

	}

}
