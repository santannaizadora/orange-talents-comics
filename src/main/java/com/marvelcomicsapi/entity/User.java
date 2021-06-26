package com.marvelcomicsapi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

@Entity
public class User implements Serializable {
 
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
	
		public User() {
		
		}
 
		public User(Integer id, String name, String email, String cpf, Date birthDate) {
			this.id = id;
			this.name = name;
			this.email = email;
			this.cpf = cpf;
			this.birthDate = birthDate;
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