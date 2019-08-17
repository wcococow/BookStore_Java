package com.rest.service;
import lombok.NoArgsConstructor;
import com.rest.model.Author;
import com.rest.model.Publication;

import com.rest.repository.AuthorRepository;
import com.rest.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.*;
import com.rest.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@NoArgsConstructor
public class PublicationServiceImp extends PublicationService{
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublicationRepository publicationRepository;
	
	@Override
	public Page<Publication> getAll( Pageable pageable )
	{
		return publicationRepository.findAll( pageable );
	}
	@Override
	public Publication add( Publication o )
	{
		Author author = new Author();
		author.setPublication( o );
		
		return publicationRepository.save( o );
	}
	
	@Override
	public Publication update( Publication o, int id ) throws ResourceNotFoundException
	{
		Publication publication = checkPublicationId( id );
	
		Author author = new Author();
		author.setPublication( o );
		

		return publicationRepository.save( publication );
	}
	
	@Override
	public Publication getById( int id ) throws ResourceNotFoundException
	{
		return checkPublicationId( id );
	}
	
	@Override
	public Publication deleteById( int id ) throws ResourceNotFoundException
	{
		Publication publication = checkPublicationId( id );
		publicationRepository.deleteById( id );
		return publication;
	}
	
	
	private Author checkAuthorId( int id )
	{
		if ( !authorRepository.findById( id ).isPresent() )
			throw new ResourceNotFoundException( " Author id = " + id + " not found" );
		else
			return authorRepository.findById( id ).get();
	}
	
	private Publication checkPublicationId( int id )
	{
		if ( !publicationRepository.findById( id ).isPresent() )
			throw new ResourceNotFoundException( " Publication id=" + id + " not found" );
		else
			return publicationRepository.findById( id ).get();
	}
		
	
	
	
}
