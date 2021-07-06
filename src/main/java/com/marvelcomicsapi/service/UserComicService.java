package com.marvelcomicsapi.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marvelcomicsapi.entity.Comic;
import com.marvelcomicsapi.entity.User;
import com.marvelcomicsapi.entity.UserComic;
import com.marvelcomicsapi.objects.request.UserComicRequest;
import com.marvelcomicsapi.repository.ComicRepository;
import com.marvelcomicsapi.repository.UserComicRepository;
import com.marvelcomicsapi.repository.UserRepository;
import com.marvelcomicsapi.service.exception.NotFoundException;

@Service
public class UserComicService {
	
	@Autowired
	private UserComicRepository userComicRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ComicRepository comicRepository;

	@Autowired
	private ComicService comicService;
	
	public UserComic save(UserComicRequest dto) {
		Optional<User> user = this.userRepository.findById(dto.getIdUser());
		
		if(user.isEmpty())
			throw new NotFoundException("User with id " + dto.getIdUser() + " not found");	

		Optional<Comic> comic = this.comicRepository.findById(dto.getIdComic());
		
		if (comic.isEmpty()) {
			comic = this.comicService.save(dto.getIdComic());
		}
		
		UserComic userComic = new UserComic();
		Date date = new Date();
		
		userComic.setIdUserComic(null);
		userComic.setDate(date);
		userComic.setUser(user.get());
		userComic.setComic(comic.get());
		
		return this.userComicRepository.save(userComic);
		
	}
	
	public UserComic fromDto(UserComic userComic) {
		return new UserComic(userComic);
	}

}
