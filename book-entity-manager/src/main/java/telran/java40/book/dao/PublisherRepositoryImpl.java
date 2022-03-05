package telran.java40.book.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import telran.java40.book.model.Book;
import telran.java40.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<String> findPublisherNameByAuthorName(String authorName) {
		TypedQuery<String> query = em.createQuery("select distinct p.publisherName from Book b join b.publisher p join b.authors a where a.name=?1",String.class);
		query.setParameter(1, authorName);
		return query.getResultList();
	}

	@Override
	public List<Book> findByPublisherPublisherName(String name) {
		TypedQuery<Book> query = em.createQuery("select b from Publisher p join p.books b where p.publisherName=?1",Book.class);
		query.setParameter(1, name);
		return query.getResultList();
		}
	
//	@Override
//	public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {
//		TypedQuery<String> query = em.createQuery("select distinct b.authors from Book b join b.authors",String.class);
//		query.setParameter(1, authorName);
//		return null;
//	}

	@Override
	public Optional<Publisher> findById(String name) {
		Publisher publisher = em.find(Publisher.class, name);
		return Optional.ofNullable(publisher);
	}

	@Override
	public Publisher save(Publisher publisher) {
		em.persist(publisher);
		return publisher;
	}

}
