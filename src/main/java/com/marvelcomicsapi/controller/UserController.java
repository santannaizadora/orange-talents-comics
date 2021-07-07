package com.marvelcomicsapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marvelcomicsapi.entity.User;
import com.marvelcomicsapi.objects.response.UserResponse;
import com.marvelcomicsapi.service.UserService;
import com.marvelcomicsapi.service.exception.DuplicatedEntryException;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{idUser}")
	public ResponseEntity<UserResponse> findOne(@PathVariable(value = "idUser")Integer idUser) {
		UserResponse user = this.userService.findOne(idUser);
		
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping
	public ResponseEntity<User> save(@RequestBody User dto) throws DuplicatedEntryException {
		
		return new ResponseEntity<User>(userService.save(dto), HttpStatus.CREATED);
		
	}
	
}