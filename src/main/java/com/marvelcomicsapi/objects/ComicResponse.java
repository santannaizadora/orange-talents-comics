package com.marvelcomicsapi.objects;

import java.util.ArrayList;
import java.util.List;

public class ComicResponse {

	private Integer idComic;
	private String title;
	private Float price;
	private String author;
	private String isbn;
	private String description;
	private String discountDay;
	private Boolean discountActive;

	public ComicResponse() {
		
	}

	public ComicResponse(Integer idComic, String title, Float price, String author, String isbn, 
			String description, String discountDay, Boolean discountActive) {
		this.idComic = idComic;
		this.title = title;
		this.price = price;
		this.author = author;
		this.isbn = isbn;
		this.description = description;
		this.discountDay = discountDay;
		this.discountActive = discountActive;
	}

	public ComicResponse(ComicResponse comic) {
		this.idComic = comic.getIdComic();
		this.title = comic.getTitle();
		this.price = comic.getPrice();
		this.author = comic.getAuthor();
		this.isbn = comic.getIsbn();
		this.description = comic.getDescription();
		this.discountDay = comic.getDiscountDay();
		this.discountActive = comic.getDiscountActive();
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

	public String getDiscountDay() {
		return discountDay;
	}

	public void setDiscountDay(String discountDay) {
		this.discountDay = discountDay;
	}

	public Boolean getDiscountActive() {
		return discountActive;
	}

	public void setDiscountActive(Boolean discountActive) {
		this.discountActive = discountActive;
	}
}
