package no.example.fluid.service;

import no.example.fluid.entity.Book;
import no.example.fluid.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> getAllBooks() {
		return (List<Book>) bookRepository.findAll();
	}

	public Optional<Book> getBookById(Long id) {
		return bookRepository.findById(id);
	}

	public Book createBook(Book book) {
		return bookRepository.save(book);
	}

	public Book updateBook(Long id, Book bookDetails) {
		return bookRepository.findById(id)
				.map(book -> {
					book.setTitle(bookDetails.getTitle());
					book.setAuthor(bookDetails.getAuthor());
					book.setIsbn(bookDetails.getIsbn());
					book.setPrice(bookDetails.getPrice());
					book.setYear(bookDetails.getYear());
					return bookRepository.save(book);
				})
				.orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
	}

	public void deleteBook(Long id) {
		if (!bookRepository.existsById(id)) {
			throw new RuntimeException("Book not found with id: " + id);
		}
		bookRepository.deleteById(id);
	}
}
