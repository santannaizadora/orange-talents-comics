package com.marvelcomicsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marvelcomicsapi.entity.Comic;
import com.marvelcomicsapi.service.ComicService;

@RestController
@RequestMapping("/comics")
public class ComicController {
	
	@Autowired
	private ComicService comicService;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Comic dto) {
		
		//return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
		//Comic comic = this.comicService.save(dto);
		
		//URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comic.getId()).toUri();

		//return ResponseEntity.created(uri).build();
		return new ResponseEntity<>(comicService.save(dto), HttpStatus.CREATED);
		
	}
	
	
}
