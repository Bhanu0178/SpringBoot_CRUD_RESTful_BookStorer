package org.bhanu.service;

import java.util.List;

import org.bhanu.entity.Book;
import org.bhanu.exception.BookNotFoundException;

public interface IBookService {
	Book add(Book book);

	List<Book> getAll() throws BookNotFoundException;

	Book getById(Integer id);

	List<Book> getByTitle(String title);

	List<Book> getByAuthor(String author);

	void removeById(Integer id);
	
	Book modifyById(Integer id, Book book);

}
