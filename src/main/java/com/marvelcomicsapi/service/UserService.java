package com.marvelcomicsapi.service;


import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.marvelcomicsapi.entity.User;
import com.marvelcomicsapi.repository.UserRepository;
import com.marvelcomicsapi.service.exception.NotFoundException;

@Service
public class UserService {
 
		@Autowired
		private UserRepository userRepository;
	
		public User save(User dto) {
			
			try {
				dto.setId(null);
				User userToSave = this.fromDto(dto);
 
				return this.userRepository.save(userToSave);
			
			} catch(JpaSystemException jpae){
				System.out.print(jpae);
				return new User();
			}
			
		}
		
		public List<User> findAll() {
			return this.userRepository.findAll();
		}
		
		public User findOne(Integer id) {
			Optional<User> user = this.userRepository.findById(id);
			
			return user.orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
		}
		
		public User fromDto(User user) {
			return new User(user);
		}
	
}


