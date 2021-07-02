package com.marvelcomicsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marvelcomicsapi.entity.UserComic;

@Repository
public interface UserComicRepository extends JpaRepository<UserComic, Integer> {

}
