package telran.java40.book.dao;

import java.util.List;
import java.util.Optional;

import telran.java40.book.model.Author;
import telran.java40.book.model.Book;

public interface AuthorRepository {

	Optional<Author> findById(String authorName);

	Author delete(Author author);

	Author save(Author author);

	List<Book> findByAuthorsName(String name);

}
