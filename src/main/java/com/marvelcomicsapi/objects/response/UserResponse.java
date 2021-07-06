package com.marvelcomicsapi.objects.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserResponse {

	private Integer idUser;
	private String name;
	private String email;
	private String cpf;
	private Date birthDate;
	private List<UserComicResponse> userComics = new ArrayList<>();

	public UserResponse() {
		
	}

	public UserResponse(Integer idUser, String name, String email, String cpf, Date birthDate) {
		this.idUser = idUser;
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.birthDate = birthDate;
	}

	public UserResponse(UserResponse user) {
		this.idUser = user.getIdUser();
		this.name = user.getName();
		this.email = user.getEmail();
		this.cpf = user.getCpf();
		this.birthDate = user.getBirthDate();
		this.userComics = user.getUserComics();
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}		

	public List<UserComicResponse> getUserComics() {
		return userComics;
	}

	public void setUserComics(List<UserComicResponse> userComics) {
		this.userComics = userComics;
	}
	
	public void addUserComic(UserComicResponse userComic) {
		this.userComics.add(userComic);
	}

}
