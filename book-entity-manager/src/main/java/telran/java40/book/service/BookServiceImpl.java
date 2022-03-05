package telran.java40.book.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.java40.book.dao.AuthorRepository;
import telran.java40.book.dao.BookRepository;
import telran.java40.book.dao.PublisherRepository;
import telran.java40.book.dto.AuthorDto;
import telran.java40.book.dto.BookDto;
import telran.java40.book.exceptions.AuthorNotFoundException;
import telran.java40.book.exceptions.BookNotFoundException;
import telran.java40.book.exceptions.PublisherNotFoundException;
import telran.java40.book.model.Author;
import telran.java40.book.model.Book;
import telran.java40.book.model.Publisher;


@Service
public class BookServiceImpl implements BookService {

	BookRepository bookRepository;
	AuthorRepository authorRepository;
	PublisherRepository publisherRepository;
	ModelMapper modelMapper;
	
	@Autowired
	public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
			PublisherRepository publisherRepository, ModelMapper modelMapper) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.publisherRepository = publisherRepository;
		this.modelMapper = modelMapper;
	}


	@Override
	@Transactional
	// controls trasactional of method and keep connection opened 
	public boolean addBook(BookDto bookDto) {
		if(bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		//Publishers
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
													.orElseGet(() -> publisherRepository.save(new Publisher(bookDto.getPublisher())));
		//Authors
		Set<Author> authors = bookDto.getAuthors().stream().map(ad -> authorRepository.findById(ad.getName()).orElseGet(() -> authorRepository.save(new Author(ad.getName(),ad.getBirthDate())))).collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(),bookDto.getTitle(),authors,publisher);
		bookRepository.save(book);
		return true;
	}


	@Override
	public BookDto findBookByIsbn(Long isbn) {
		Book book = bookRepository.findById(isbn).orElse(null);
		if(book == null) {
			throw new BookNotFoundException(isbn);
		}
		
		return modelMapper.map(book,BookDto.class);
	}


	@Override
	@Transactional
	public BookDto removeBook(Long isbn) {
		Book book = bookRepository.findById(isbn).orElse(null);
		if(book == null) {
			throw new BookNotFoundException(isbn);
		}
		bookRepository.deleteBook(book);
		return modelMapper.map(book,BookDto.class);
	}


	@Override
	@Transactional
	public BookDto updateBook(Long isbn, String title) {
		Book book = bookRepository.findById(isbn).orElse(null);
		if(book == null) {
			throw new BookNotFoundException(isbn);
		}
		book.setTitle(title);
		return modelMapper.map(book,BookDto.class);
	}


	@Override
	@Transactional(readOnly = true)
	public Iterable<BookDto> findBooksByAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElse(null);
		if(author == null) {
			throw new AuthorNotFoundException(authorName);
		}
		return authorRepository.findByAuthorsName(authorName).stream().map(b -> modelMapper.map(b,BookDto.class)).collect(Collectors.toList());
	}


	@Override
	@Transactional(readOnly = true)
	public Iterable<BookDto> findBooksByPublisher(String publisher) {
		Publisher publisher1 = publisherRepository.findById(publisher).orElse(null);
		if(publisher1 == null) {
			throw new PublisherNotFoundException(publisher);
		}
		return publisherRepository.findByPublisherPublisherName(publisher).stream().map(b -> modelMapper.map(b,BookDto.class)).collect(Collectors.toList());
	}


	@Override
	@Transactional
	public Iterable<AuthorDto> findBookAuthors(Long isbn) {
		Book book = bookRepository.findById(isbn).orElse(null);
		if(book == null) {
			throw new BookNotFoundException(isbn);
		}
		
		return book.getAuthors().stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toList());
	}


	@Override
	@Transactional(readOnly = true)
	public List<String> findPublishersByAuthor(String authorsName) {

		return publisherRepository.findPublisherNameByAuthorName(authorsName);
	}


	@Override
	@Transactional
	public AuthorDto removeAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElse(null);
		if(author == null) {
			throw new AuthorNotFoundException(authorName);
		}
		authorRepository.findByAuthorsName(authorName).stream().forEach(b -> bookRepository.deleteBook(b));
		authorRepository.delete(author);
		return modelMapper.map(author, AuthorDto.class);
	}
	
}
