package com.marvelcomicsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marvelcomicsapi.entity.Comic;

@Repository
public interface ComicRepository  extends JpaRepository<Comic, Integer> {

}
