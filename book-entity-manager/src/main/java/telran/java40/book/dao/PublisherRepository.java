package telran.java40.book.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import telran.java40.book.model.Book;
import telran.java40.book.model.Publisher;



public interface PublisherRepository{
	
//	@Query("select distinct p.publisherName from Book b join b.publisher p join b.authors a where a.name=?1")//query language JPQL number of variables from method properties begins from 1 but not from 0
	List<String> findPublisherNameByAuthorName(String authorName);
//	
//	Stream<Publisher> findDistinctByBooksAuthorsName(String authorName);

	Optional<Publisher> findById(String publisher);

	Publisher save(Publisher publisher);

	List<Book> findByPublisherPublisherName(String name);
}
