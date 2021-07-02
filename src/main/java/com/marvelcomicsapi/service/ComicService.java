package com.marvelcomicsapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marvelcomicsapi.entity.Comic;
import com.marvelcomicsapi.entity.User;
import com.marvelcomicsapi.entity.UserComic;
import com.marvelcomicsapi.repository.ComicRepository;
import com.marvelcomicsapi.repository.UserRepository;
import com.marvelcomicsapi.service.exception.*;

@Service
public class ComicService {

	@Autowired
	private ComicRepository comicRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserComicService userComicService;
	
	public UserComic save(Comic dto) {
		Optional<Comic> comic = this.comicRepository.findById(dto.getId());
		Optional<User> user = this.userRepository.findById(dto.getUserId());
		
		if(user.isEmpty())
			throw new NotFoundException("User with id " + dto.getUserId() + " not found");
		
		if(comic.isEmpty()) {
			Comic comicToSave = this.fromDto(dto);
			comicRepository.save(comicToSave);		
		}
		
		UserComic userComic = new UserComic();
		userComic.setComic_id(dto.getId());
		userComic.setUser_id(dto.getUserId());
		
		return this.userComicService.save(userComic);
	
	}
	
	
	public Comic fromDto(Comic comic) {
		return new Comic(comic);
	}
}
