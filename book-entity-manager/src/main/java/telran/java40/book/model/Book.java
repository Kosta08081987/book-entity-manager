package telran.java40.book.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"isbn"})
public class Book implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175434838288027543L;
	@Id
	long isbn;
	String title;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "BOOK_AUTHORS",
	   joinColumns = @JoinColumn(name = "BOOKS_ISBN"),
	   inverseJoinColumns = @JoinColumn(name = "AUTHORS_NAME"))
	Set<Author> authors;
	@ManyToOne
	Publisher publisher;
	
}
