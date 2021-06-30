package com.marvelcomicsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marvelcomicsapi.entity.Comic;
import com.marvelcomicsapi.repository.ComicRepository;

@Service
public class ComicService {

	@Autowired
	private ComicRepository comicRepository;
	
	public Comic save(Comic dto) {
		
		dto.setPrice(3.99);
		Comic comicToSave = this.fromDto(dto);
		System.out.print(comicToSave.toString());
		return this.comicRepository.save(comicToSave);
	
	}
	
	
	public Comic fromDto(Comic comic) {
		return new Comic(comic);
	}
}
