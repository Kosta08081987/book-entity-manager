package telran.java40.book.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
@EqualsAndHashCode(of = {"publisherName"})
public class Publisher implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -977785343953225696L;

	@Id
	String publisherName;
	
	@OneToMany(mappedBy = "publisher")
	Set<Book> books;

	public Publisher(String publisherName) {
		this.publisherName = publisherName;
	}
	
	
}
