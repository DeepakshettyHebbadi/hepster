package com.hepster.book.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hepster.book.domain.Book;
import com.hepster.book.dto.BookDTO;
import com.hepster.book.repository.BookRepository;
import com.hepster.book.service.impl.BookService;

@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	BookService bookService;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	BookRepository bookRepository;

	private final String BOOK_TITLE_1 = "Effective Java";
	private final String AUTHOR_TITLE_1 = "Joshua Bloch";
	private final double PRICE_1 = 23.56;

	private final String BOOK_TITLE_2 = "Head First Kotlin";
	private final String AUTHOR_TITLE_2 = "David Griffiths and Dawn Griffiths";
	private final double PRICE_2 = 27.43;

	private final String BOOK_TITLE_3 = "Head First Object-Oriented Analysis and Design";
	private final String AUTHOR_TITLE_3 = "Brett McLaughlin";
	private final double PRICE_3 = 237.56;

	private final Book BOOK_1 = new Book();
	private final Book BOOK_2 = new Book();
	private final Book BOOK_3 = new Book();

	@BeforeEach
	public void setup() {

		BOOK_1.setActive(true);
		BOOK_1.setBookId(UUID.randomUUID());
		BOOK_1.setTitle(BOOK_TITLE_1);
		BOOK_1.setAuthor(AUTHOR_TITLE_1);
		BOOK_1.setPrice(PRICE_1);

		BOOK_2.setActive(true);
		BOOK_2.setBookId(UUID.randomUUID());
		BOOK_2.setTitle(BOOK_TITLE_2);
		BOOK_2.setAuthor(AUTHOR_TITLE_2);
		BOOK_2.setPrice(PRICE_2);

		BOOK_3.setActive(true);
		BOOK_3.setBookId(UUID.randomUUID());
		BOOK_3.setTitle(BOOK_TITLE_3);
		BOOK_3.setAuthor(AUTHOR_TITLE_3);
		BOOK_3.setPrice(PRICE_3);

	}

	@Test
	public void getActiveBooks() throws Exception {

		List<Book> books = new ArrayList<Book>();

		books = Arrays.asList(BOOK_1, BOOK_2, BOOK_3);

		Mockito.when(bookService.getActiveBooks()).thenReturn(books);

		mockMvc.perform(MockMvcRequestBuilders.get("/book").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[1].title").value(BOOK_TITLE_2));

	}

	@Test
	public void getBookByBookId() throws Exception {

		UUID uuid = UUID.randomUUID();

		Mockito.when(bookService.getBook(uuid)).thenReturn(BOOK_1);

		mockMvc.perform(MockMvcRequestBuilders.get("/book/" + uuid).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.title").value(BOOK_TITLE_1));

	}

	@Test
	public void getBookwhenBookIDIsNullReturnNotFound() throws Exception {

		UUID uuid = UUID.randomUUID();

		Mockito.when(bookService.getBook(uuid)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.get("/book/" + uuid).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	@Test
	public void saveBook() throws Exception {
		BookDTO bookdto = new BookDTO();
		bookdto.setActive(true);
		bookdto.setBookId(UUID.randomUUID());
		bookdto.setTitle(BOOK_TITLE_1);
		bookdto.setAuthor(AUTHOR_TITLE_1);
		bookdto.setPrice(PRICE_1);

		mockMvc.perform(MockMvcRequestBuilders.post("/book").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(bookdto)))
				.andExpect(status().isCreated());

	}

	@Test
	public void updateBook() throws Exception {

		UUID uuid = UUID.randomUUID();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("price", PRICE_1);
		map.put("title", BOOK_TITLE_1);

		Mockito.when(bookService.updateBook(uuid, map)).thenReturn(BOOK_1);

		mockMvc.perform(MockMvcRequestBuilders.patch("/book/" + uuid).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(map)))
				.andExpect(status().isOk());

	}

	@Test
	public void updateBookwhenUUIDnotfoundReturn404() throws Exception {

		UUID uuid = UUID.randomUUID();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("price", PRICE_1);
		map.put("title", BOOK_TITLE_1);

		Mockito.when(bookService.updateBook(uuid, map)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.patch("/book/" + uuid).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(map)))
				.andExpect(status().isNotFound());

	}

}
