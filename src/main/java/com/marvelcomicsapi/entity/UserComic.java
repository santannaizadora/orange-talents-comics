package com.marvelcomicsapi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_comics")
public class UserComic implements Serializable{
	 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Date date;
	
	@Column(nullable = false)
	private Integer comic_id;
	
	@Column(nullable = false)
	private Integer user_id;
	
	

	public UserComic() {
		
	}	

	public UserComic(UserComic userComic) {
		this.id = userComic.getId();
		this.date = userComic.getDate();
		this.comic_id = userComic.getComic_id();
		this.user_id = userComic.getUser_id();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getComic_id() {
		return comic_id;
	}

	public void setComic_id(Integer comic_id) {
		this.comic_id = comic_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}	

}
