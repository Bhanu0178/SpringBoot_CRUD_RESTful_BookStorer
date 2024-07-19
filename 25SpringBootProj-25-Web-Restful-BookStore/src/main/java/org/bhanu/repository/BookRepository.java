package org.bhanu.repository;

import java.util.List;

import org.bhanu.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository			//DAO		=>Components talks to DB
public interface BookRepository extends JpaRepository<Book, Integer> 
{
	boolean existsByIdAndTitle(Integer id, String title);
	
	List<Book> findByTitle(String title);
	
	List<Book> findByAuthor(String author);
	
}
