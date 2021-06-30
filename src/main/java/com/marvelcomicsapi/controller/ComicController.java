package com.marvelcomicsapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marvelcomicsapi.entity.Comic;
import com.marvelcomicsapi.service.ComicService;

@RestController
@RequestMapping("/comics")
public class ComicController {
	
	@Autowired
	private ComicService comicService;
	
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody Comic dto) {
		
		
		Comic comic = this.comicService.save(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comic.getId()).toUri();

		return ResponseEntity.created(uri).build();
		
	}
	
	
}
