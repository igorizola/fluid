package no.example.fluid.entity;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("BOOKS")
public class Book {

	@Id
	private Long id;
	
	@NotBlank
	private String title;
	@NotBlank
	private String author;
	private String isbn;
	private Double price;
	
	@Column("YEAR_PUBLISHED")
	private Integer year;

	public Book() {
	}

	public Book(String title, String author, String isbn, Double price, Integer year) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.price = price;
		this.year = year;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", isbn='" + isbn + '\'' +
				", price=" + price +
				", year=" + year +
				'}';
	}
}
