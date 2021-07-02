package com.marvelcomicsapi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

@Entity
public class User implements Serializable{
 
		private static final long serialVersionUID = 1L;
 
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
	
		@Column(nullable = false)
		private String name;
	
		@Column(nullable = false, unique = true)
		@Email(message = "email invalido")
		private String email;
	
		@Column(nullable = false, unique = true)
		@CPF(message = "cpf invalido")
		private String cpf;
	
		@Column(nullable = false)
		private Date birthDate;
		
		@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
		private List<UserComic> user_comic = new ArrayList<>();

		public User() {
		
		}
 
		public User(User user) {
			this.id = user.getId();
			this.name = user.getName();
			this.email = user.getEmail();
			this.cpf = user.getCpf();
			this.birthDate = user.getBirthDate();
		}
 
		public Integer getId() {
			return id;
		}
 
		public void setId(Integer id) {
			this.id = id;
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

 
}