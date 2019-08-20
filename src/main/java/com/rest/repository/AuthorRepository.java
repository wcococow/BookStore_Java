package com.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rest.model.Author;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Integer>{

	List<Author> findByname(String name);
	
}