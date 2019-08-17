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
	@PathVariable(value = "authorId") int authorId) {
		
		Author author = authorService.getById(authorId);
		return ResponseEntity.ok().body(author);
		
	}
	
	@PostMapping("/authors")
	public Author createAuthor(@Valid @RequestBody Author author){
		return authorService.add(author);
		
		
	}
	
	@PutMapping("authors/{id}")
	public ResponseEntity<Author> updateAuthor(@PathVariable(value = "authorId") int authorId,
		@Valid @RequestBody Author author){
		return ResponseEntity.ok(authorService.update(author,authorId));
	}
	
	
	@DeleteMapping("/authors/{id}")
	public Map<String,Boolean> deleteAuthor(
		@PathVariable(value = "authorId") int authorId){
		Author author = authorService.deleteById(authorId);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/{id}/books")
	public ResponseEntity<List<Publication>>getAuthorPublicationById(
	@PathVariable(value = "authorId") int authorId){
		
		return ResponseEntity.ok(authorService.getPublicationsById(authorId));
	
	}
	
	@GetMapping("/authors/search")
	public List<Publication> search(@RequestParam(value = "authorId", required = true) int authorId,
								@RequestParam(value = "catagory", required = false) String catagory,
                                @RequestParam(value = "title", required = false) String title,
                                @RequestParam(value = "year", required = false) int year,
								@RequestParam(value = "type", required = false) String type,
								@RequestParam(value = "hero", required = false) String hero,
                                @RequestParam(value = "genre", required = false) String genre){
												  
								return authorService.search(authorId,catagory,title,year,type,hero,genre); 				  
												  
	
								}
}