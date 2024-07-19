package org.bhanu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bhanu.dto.BookDto;
import org.bhanu.entity.Book;
import org.bhanu.exception.BookNotFoundException;
import org.bhanu.service.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class BookController 
{
	
	@Autowired
	private IBookService bookService;
	
	@Autowired
	private ModelMapper modelMapper;			//To map DTO to Entity
	
	@PostMapping(path = "/add")		//Id and Title should be different from another Id and Title
	public ResponseEntity<?> add(@RequestBody BookDto dto) {
		Book addBook = modelMapper.map(dto, Book.class);
		Random random = new Random();
		Integer ranNum = random.nextInt(100000, 200000);
		addBook.setId(ranNum);
		try {
			Book book = bookService.add(addBook);
			return new ResponseEntity<>("Record Added with Id " + book.getId(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}//try/catch
	}
	
	@GetMapping(value = "/books")
	public ResponseEntity<?> books() {
		try {
			List<Book> books = bookService.getAll();
			List<BookDto> books1 = new ArrayList<>();
			for(Book book : books) {
				books1.add(modelMapper.map(book, BookDto.class));
			}
			return new ResponseEntity<>(books1, HttpStatus.OK);
		} catch (BookNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}//try/catch
	}
	
	@GetMapping(value = "/getbyid/{id}")
	public ResponseEntity<?> bookById(@PathVariable Integer id) {
		try {
			Book book = bookService.getById(id);
			BookDto dto = modelMapper.map(book, BookDto.class);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch(IllegalArgumentException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}//try/catch
	}
	
	@GetMapping(path = "/getbytitle")
	public ResponseEntity<?> bookByTitle(@RequestParam String title) {
		try {
			List<Book> books = bookService.getByTitle(title);
			List<BookDto> dto = new ArrayList<>();
			for(Book book : books) {
				dto.add(modelMapper.map(book, BookDto.class));
			}
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch(IllegalArgumentException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}//try/catch
	}
	
	@GetMapping(value = "/getbyauthor/{author}")
	public ResponseEntity<?> bookByAuthor(@PathVariable String author) {
		try {
			List<Book> books = bookService.getByAuthor(author);
			List<BookDto> dto = new ArrayList<>();
			for(Book book : books) {
				dto.add(modelMapper.map(book, BookDto.class));
			}
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch(IllegalArgumentException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}//try/catch
	}
	
	@DeleteMapping(path = "/deletebyid/{id}")
	public ResponseEntity<?> deleteBookById(@PathVariable Integer id) {
		try {
			bookService.removeById(id);
			return new ResponseEntity<>("Book Removed with Id " + id, HttpStatus.OK);
		} catch(IllegalArgumentException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}//try/catch
	}
	
	@PutMapping(value = "/updatebyid/{id}")
	public ResponseEntity<?> updateBookById(@PathVariable Integer id, @RequestBody BookDto dto){
		try {
			Book book = modelMapper.map(dto, Book.class);
			Book book1 = bookService.modifyById(id, book);
			return new ResponseEntity<>(book1, HttpStatus.OK);
		} catch(IllegalArgumentException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}//try/catch
	}
}
