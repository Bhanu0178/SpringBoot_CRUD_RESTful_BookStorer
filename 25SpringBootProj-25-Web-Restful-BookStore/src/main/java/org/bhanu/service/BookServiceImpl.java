package org.bhanu.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bhanu.entity.Book;
import org.bhanu.exception.BookNotFoundException;
import org.bhanu.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	private BookRepository bookRepository;

	public Book add(Book book) {
		if (!bookRepository.existsById(book.getId())) {
			if (!(bookRepository.existsByIdAndTitle(book.getId(), book.getTitle())))
				return bookRepository.save(book);
		}
		throw new IllegalArgumentException("Already Book avialable with Id " + book.getId());
	}// add

	public List<Book> getAll() throws BookNotFoundException {
		List<Book> books = bookRepository.findAll();
		if (!books.isEmpty())
			return books.stream().distinct().sorted((b1, b2) -> b1.getId() - b2.getId()).collect(Collectors.toList());
		else throw new BookNotFoundException("Books Not Available!");
	}// getAll

	public Book getById(Integer id) {
		Optional<Book> book = bookRepository.findById(id);
		return book.orElseThrow(() -> new IllegalArgumentException("Book Not Found with Id " + id));
	}// getById

	public List<Book> getByTitle(String title) {
		List<Book> books = bookRepository.findByTitle(title);
		if (!books.isEmpty()) return books;
		else	throw new IllegalArgumentException("Book Not Found with Title " + title);
	}// getByTitle

	public List<Book> getByAuthor(String author) {
		List<Book> books = bookRepository.findByAuthor(author);
		if (!books.isEmpty()) return books;
		else throw new IllegalArgumentException("Book Not Found with Author " + author);
	}// getByAuthor
	
	public void removeById(Integer id) {
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent()) bookRepository.deleteById(id); 
		else throw new IllegalArgumentException("Book Not Found with Id " + id);
	}//removeById
	
	public Book modifyById(Integer id, Book book) {
		return bookRepository.findById(id).map((book1)->{
			book1.setTitle(book.getTitle());
			book1.setAuthor(book.getAuthor());
			book1.setPrice(book.getPrice());
			book1.setStock(book.getStock());
			return bookRepository.save(book1);
		}).orElseThrow(()->new IllegalArgumentException("Book Not Found with Id " + id));
	}//modifyById
}
