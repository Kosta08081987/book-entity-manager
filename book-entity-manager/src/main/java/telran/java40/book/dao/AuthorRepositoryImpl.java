package telran.java40.book.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import telran.java40.book.model.Author;
import telran.java40.book.model.Book;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public Optional<Author> findById(String authorName) {
		Author author = em.find(Author.class, authorName);
		return Optional.ofNullable(author);
	}

	@Override
	public List<Book> findByAuthorsName(String name) {
		TypedQuery<Book> query = em.createQuery("select b from Author a join a.books b where a.name=?1",Book.class);
		query.setParameter(1, name);
		return query.getResultList();
	}
	
	@Override
	public Author delete(Author author) {
		Author authorRes = em.find(Author.class,author.getName());
		em.remove(authorRes);
		return authorRes;
	}

	@Override
	public Author save(Author author) {
		em.persist(author);
		em.flush();
		return author;
	}

}
