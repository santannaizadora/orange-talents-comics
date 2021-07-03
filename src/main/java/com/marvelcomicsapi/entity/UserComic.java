package com.marvelcomicsapi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "userComic")
public class UserComic implements Serializable{
	 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUserComic;
	
	@Column(nullable = false)
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="idUser", insertable = false, updatable = false)
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="idComic", insertable = false, updatable = false)
	@JsonIgnore
	private Comic comic;

	private Integer idUser;
	private Integer idComic;
	
	public UserComic() {
		
	}	

	public UserComic(UserComic userComic) {
		this.idUserComic = userComic.getIdUserComic();
		this.date = userComic.getDate();
		this.user = userComic.getUser();
		this.comic = userComic.getComic();
		this.idUser = userComic.getIdUser();
		this.idComic = userComic.getIdComic();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comic getComic() {
		return comic;
	}

	public void setComic(Comic comic) {
		this.comic = comic;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIdUserComic() {
		return idUserComic;
	}

	public void setIdUserComic(Integer idUserComic) {
		this.idUserComic = idUserComic;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getIdComic() {
		return idComic;
	}

	public void setIdComic(Integer idComic) {
		this.idComic = idComic;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

}
