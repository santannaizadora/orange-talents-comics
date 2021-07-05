package com.marvelcomicsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marvelcomicsapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
}