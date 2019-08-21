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
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.Persistence;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.ArrayList;

@Service
@NoArgsConstructor
public class AuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublicationRepository publicationRepository;
	
	
	public Page<Author> getAll( Pageable pageable )
	{
		return authorRepository.findAll( pageable );
	}
	
	public Author add( Author o )
	{
		return authorRepository.save( o );
	}
	
	
	public Author update( Author o, int id )
	{
		Author author = checkAuthorId( id );
		author.setName( o.getName() );
		author.setEmailId( o.getEmailId() );
		return authorRepository.save( author );
	}
	
	
	public Author getById( int id )
	{
		return checkAuthorId( id );
	}
	
	
	public Author deleteById( int id )
	{
		Author author = checkAuthorId( id );
		//remove all publications by this author
		author.getPublication().forEach(pub -> {
			pub.getAuthor().remove(author);
		});
		authorRepository.deleteById( id );
		return author;
	}
	
	

	
	private Author checkAuthorId( int id )
	{
		if ( !authorRepository.findById( id ).isPresent() )
			throw new ResourceNotFoundException( " Author id = " + id + " not found" );
		else
			return authorRepository.findById( id ).get();
	}
		
	
	
	public List<Author> findByName( String name )
	{
		return authorRepository.findByName( name );
	}
	
}
