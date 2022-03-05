package telran.java40.book.dao;

import java.util.List;
import java.util.Optional;

import telran.java40.book.model.Book;


public interface BookRepository{
//	List<Book> findByAuthorsName(String name);
	
//	List<Book> findByPublisherPublisherName(String name);

	boolean existsById(Long isbn);

	Book save(Book book);

	Optional<Book> findById(Long isbn);

	Book deleteBook(Book book);
}
