package com.marvelcomicsapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marvelcomicsapi.entity.User;
import com.marvelcomicsapi.repository.UserRepository;

@Service
public class UserService {
 
		@Autowired
		private UserRepository userRepository;
	
		public User save(User dto) {
			dto.setId(null);
			User userToSave = this.fromDto(dto);
 
			return this.userRepository.save(userToSave);
		}
		
		public User fromDto(User user) {
			return new User(user.getId(), user.getName(), user.getEmail(), user.getCpf(), user.getBirthDate());
		}
	
}


