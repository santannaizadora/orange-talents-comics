package com.marvelcomicsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marvelcomicsapi.entity.UserComic;
import com.marvelcomicsapi.service.UserComicService;

@RestController
@RequestMapping("/comics")
public class UserComicController {
	
	@Autowired
	private UserComicService userComicService;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody UserComic dto) {
		
		
		return new ResponseEntity<>(userComicService.save(dto), HttpStatus.CREATED);
		
	}
	
	
}