package com.marvelcomicsapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marvelcomicsapi.entity.User;
import com.marvelcomicsapi.objects.response.UserResponse;
import com.marvelcomicsapi.repository.UserRepository;
import com.marvelcomicsapi.service.exception.DuplicatedEntryException;
import com.marvelcomicsapi.service.exception.NotFoundException;
import com.marvelcomicsapi.service.function.*;

@Service
public class UserService {
 
	@Autowired
	private UserRepository userRepository;

	public User save(User dto) throws DuplicatedEntryException {
				
		Optional<User> userCpf = this.userRepository.findByCpf(dto.getCpf());	
		if(!userCpf.isEmpty())
			throw new DuplicatedEntryException("Cpf " + dto.getCpf() + " já cadastrado");
			
		Optional<User> userEmail = this.userRepository.findByEmail(dto.getEmail());	
		if(!userEmail.isEmpty())
			throw new DuplicatedEntryException("Email " + dto.getEmail() + " já cadastrado");
			
		dto.setIdUser(null);
		User userToSave = this.fromDto(dto);

		return this.userRepository.save(userToSave);	
	}
		
	public UserResponse findOne(Integer idUser) {
		Optional<User> user = this.userRepository.findById(idUser);
		if(user.isEmpty())
			throw new NotFoundException("Usuário com id " + idUser + " não foi encontrado");
			
		User userToResponse = user.get();
		
		DiscountDay discountDay = new DiscountDay();
		
		return discountDay.DiscountDayActive(userToResponse);
		
	}
		
	public User fromDto(User user) {
		return new User(user);
	}
	
}


