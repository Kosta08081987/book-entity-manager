package telran.java40.book.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import telran.java40.book.model.Book;
@Repository
public class BookRepositoryImpl implements BookRepository {

	@PersistenceContext
	EntityManager em;
	
		@Override
	public boolean existsById(Long isbn) {
		Book book = em.find(Book.class, isbn);
		return book != null;
	}

	@Override
	public Book save(Book book) {
		em.persist(book);
		return book;
	}

	@Override
	public Optional<Book> findById(Long isbn) {
		Book book = em.find(Book.class, isbn);
		return Optional.ofNullable(book);
	}

	@Override
	public Book deleteBook(Book book) {
		em.remove(book);
		em.flush();
		return book;
	}

}
