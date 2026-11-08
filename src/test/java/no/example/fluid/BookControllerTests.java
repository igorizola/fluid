package no.example.fluid;

import no.example.fluid.entity.Book;
import no.example.fluid.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private BookRepository bookRepository;

	@BeforeEach
	void clearBooks() {
		bookRepository.deleteAll();
	}

	@Test
	void createIgnoresClientIdAndPersistsMappedColumns() {
		ResponseEntity<Book> response = restTemplate.postForEntity("/api/books",
				json("{\"id\":999999,\"title\":\"Title\",\"author\":\"Author\",\"isbn\":\"isbn-1\",\"year\":2024}"),
				Book.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertNotEquals(Long.valueOf(999999L), response.getBody().getId());
		assertEquals(2024, bookRepository.findById(response.getBody().getId()).orElseThrow().getYear());
	}

	@Test
	void requiredFieldsAreValidatedForCreateAndUpdate() {
		ResponseEntity<String> create = restTemplate.postForEntity("/api/books", json("{}"), String.class);
		ResponseEntity<String> update = restTemplate.exchange("/api/books/1",
				HttpMethod.PUT, json("{\"title\":\"\",\"author\":null}"), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, create.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, update.getStatusCode());
	}

	@Test
	void missingBooksReturnNotFoundAndDuplicateIsbnReturnsConflict() {
		Book first = createBook("isbn-1");
		Book second = createBook("isbn-2");

		ResponseEntity<String> update = restTemplate.exchange("/api/books/" + second.getId(),
				HttpMethod.PUT,
				json("{\"title\":\"Updated\",\"author\":\"Author\",\"isbn\":\"isbn-1\"}"), String.class);
		ResponseEntity<String> missingUpdate = restTemplate.exchange("/api/books/999999",
				HttpMethod.PUT,
				json("{\"title\":\"Title\",\"author\":\"Author\"}"), String.class);
		ResponseEntity<String> missingDelete = restTemplate.exchange("/api/books/999999",
				HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

		assertNotNull(first.getId());
		assertEquals(HttpStatus.CONFLICT, update.getStatusCode());
		assertEquals(HttpStatus.NOT_FOUND, missingUpdate.getStatusCode());
		assertEquals(HttpStatus.NOT_FOUND, missingDelete.getStatusCode());
	}

	private Book createBook(String isbn) {
		return restTemplate.postForEntity("/api/books",
				json("{\"title\":\"Title\",\"author\":\"Author\",\"isbn\":\"" + isbn + "\"}"),
				Book.class).getBody();
	}

	private HttpEntity<String> json(String body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(body, headers);
	}
}
