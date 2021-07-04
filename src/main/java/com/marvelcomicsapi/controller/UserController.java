package com.marvelcomicsapi.controller;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marvelcomicsapi.entity.User;
import com.marvelcomicsapi.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> users = this.userService.findAll();

		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping("/{idUser}")
	public ResponseEntity<User> findOne(@PathVariable(value = "idUser")Integer idUser) {
		User user = this.userService.findOne(idUser);
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody User dto) {
		
		User user = this.userService.save(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idUser}").buildAndExpand(user.getIdUser()).toUri();

		return ResponseEntity.created(uri).build();
	}
	
}