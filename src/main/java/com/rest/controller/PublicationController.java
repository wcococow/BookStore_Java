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
import com.rest.repository.PublicationRepository;
import com.rest.service.PublicationService;


@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class PublicationController {
	
	
	@Autowired
	private PublicationService publicationService;
	
	
	@GetMapping("/publications")
	public Page<Publication> getAllPublications(Pageable pageable){
		
		return publicationService.getAll( pageable);
	}
	
	@GetMapping(value="/publications/{id}")
	public ResponseEntity<Publication> getPublicationById(
	@PathVariable(value = "id") int id) {
		
		Publication publication = publicationService.getById(id);
		return ResponseEntity.ok().body(publication);
		
	}
	
	@PostMapping("/publications")
	public Publication createPublication(@Valid @RequestBody Publication publication){
		return publicationService.add(publication);
		
		
	}
	
	@PutMapping("publications/{id}")
	public ResponseEntity<Publication> updatePublication(@PathVariable(value = "id") int id,
		@Valid @RequestBody Publication publication){
		return ResponseEntity.ok(publicationService.update(publication,id));
	}
	
	
	@DeleteMapping("/publications/{id}")
	public Map<String,Boolean> deletePublication(@PathVariable(value = "id") int id){
		Publication publication = publicationService.deleteById(id);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/publications/search")
	public List<Publication> search(@RequestParam(value = "id", required = true) int id,
								@RequestParam(value = "catagory", required = false) String catagory,
                                @RequestParam(value = "title", required = false) String title,
                                @RequestParam(value = "year", required = false) int year,
								@RequestParam(value = "type", required = false) String type,
								@RequestParam(value = "hero", required = false) String hero,
                                @RequestParam(value = "genre", required = false) String genre){
												  
								return publicationService.search(id,catagory,title,year,type,hero,genre); 				  
												  
	
								}

}