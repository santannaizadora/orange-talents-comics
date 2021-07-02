package com.marvelcomicsapi.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marvelcomicsapi.entity.UserComic;
import com.marvelcomicsapi.repository.UserComicRepository;

@Service
public class UserComicService {
	
	@Autowired
	private UserComicRepository userComicRepository;
	
	public UserComic save( UserComic dto) {
		
		Date date = new Date();
		dto.setId(null);
		dto.setDate(date);
		
		return this.userComicRepository.save(dto);		
		
	}
	
	public UserComic fromDto(UserComic userComic) {
		return new UserComic(userComic);
	}

}
