package com.marvelcomicsapi.objects;

import java.sql.Date;

public class UserComicRequest {

	private Integer idUserComic;
	private Date date;
	private Integer idUser;
	private Integer idComic;
		
	public UserComicRequest() {
			
	}	

	public UserComicRequest(UserComicRequest userComic) {
		this.idUserComic = userComic.getIdUserComic();
		this.date = userComic.getDate();
		this.idUser = userComic.getIdUser();
		this.idComic = userComic.getIdComic();
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
