package com.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rest.model.Author;


public interface AuthorRepository extends JpaRepository<Author,Integer>{

	
	
}