package com.marvelcomicsapi.objects;

import java.util.Date;

public class UserComicResponse {

	private Integer idUserComic;
	private Date date;
	private UserResponse user;
	private ComicResponse comic;
	
	public UserComicResponse() {
			
	}	
	
	public UserComicResponse(Integer idUserComic, Date date) {
		this.idUserComic = idUserComic;
		this.date = date;
	}	

	public UserComicResponse(UserComicResponse userComic) {
		this.idUserComic = userComic.getIdUserComic();
		this.date = userComic.getDate();
		this.user = userComic.getUser();
		this.comic = userComic.getComic();
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

	public ComicResponse getComic() {
		return comic;
	}

	public void setComic(ComicResponse comic) {
		this.comic = comic;
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

}
