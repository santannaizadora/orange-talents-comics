package com.marvelcomicsapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Comic implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Double price;
	
	@Column(nullable = true)
	private String author;
	
	@Column(nullable = true)
	private String isbn;
	
	@Column(nullable = true)
	private String description;
	
	private Integer userId;

	
	public Comic() {
		
	}

	public Comic(Comic comic) {
		this.id = comic.getId();
		this.title = comic.getTitle();
		this.price = comic.getPrice();
		this.author = comic.getAuthor();
		this.isbn = comic.getIsbn();
		this.description = comic.getDescription();
		this.userId = comic.getUserId();
	}

	public Integer getId() {
		return id;
	} 

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
}
