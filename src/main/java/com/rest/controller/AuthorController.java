package com.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import com.rest.model.Author;
import com.rest.model.Publication;
import com.rest.repository.AuthorRepository;
import com.rest.service.AuthorService;


@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AuthorController {
	
	
	@Autowired
	private AuthorService authorService;
	
	
	@GetMapping("/authors")
	public Page<Author> getAllAuthors(Pageable pageable){
		
		return authorService.getAll( pageable);
	}
	
	@GetMapping(value="/authors/{id}")
	public ResponseEntity<Author> getAuthorById(
	@PathVariable(value = "id") int id) {
		
		Author author = authorService.getById(id);
		return ResponseEntity.ok().body(author);
		
	}
	
	@PostMapping("/authors")
	public Author createAuthor(@Valid @RequestBody Author author){
		return authorService.add(author);
		
		
	}
	
	@PutMapping("authors/{id}")
	public ResponseEntity<Author> updateAuthor(@PathVariable(value = "id") int id,
		@Valid @RequestBody Author author){
		return ResponseEntity.ok(authorService.update(author,id));
	}
	
	
	@DeleteMapping("/authors/{id}")
	public Map<String,Boolean> deleteAuthor(@PathVariable(value = "id") int id){
		Author author = authorService.deleteById(id);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/authors/name/{name}")
	public ResponseEntity<Author>  getAuthorByName(
	@PathVariable(value = "name") String name){
		
		return ResponseEntity.ok(authorService.findByName(name));
	
	}
}
	
	