package com.marvelcomicsapi.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marvelcomicsapi.entity.User;
import com.marvelcomicsapi.entity.UserComic;
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
	private ComicService comicService;
	
	public UserComic save(UserComic dto) {
		Optional<User> user = this.userRepository.findById(dto.getIdUser());
		
		if(user.isEmpty())
			throw new NotFoundException("User with id " + dto.getIdUser() + " not found");	
		
		this.comicService.save(dto.getIdComic());	
		
		Date date = new Date();
		dto.setIdUserComic(null);
		dto.setDate(date);
		
		return this.userComicRepository.save(dto);
		
	}
	
	public UserComic fromDto(UserComic userComic) {
		return new UserComic(userComic);
	}

}
