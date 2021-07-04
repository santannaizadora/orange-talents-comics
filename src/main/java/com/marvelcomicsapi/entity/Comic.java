package com.marvelcomicsapi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Comic implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer idComic;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Float price;
	
	@Column(nullable = true)
	private String author;
	
	@Column(nullable = true)
	private String isbn;
	
	@Column(nullable = true)
	private String description;
	
	@OneToMany(mappedBy = "comic", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<UserComic> userComics = new ArrayList<>();
	
	public Comic() {
		
	}

	public Comic(Comic comic) {
		this.idComic = comic.getIdComic();
		this.title = comic.getTitle();
		this.price = comic.getPrice();
		this.author = comic.getAuthor();
		this.isbn = comic.getIsbn();
		this.description = comic.getDescription();
	}

	public Integer getIdComic() {
		return idComic;
	} 

	public void setIdComic(Integer idComic) {
		this.idComic = idComic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
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

	
}
