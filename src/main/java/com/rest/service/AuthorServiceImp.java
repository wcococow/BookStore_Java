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
public class AuthorServiceImp extends AuthorService{
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublicationRepository publicationRepository;
	
	@Override
	public Page<Author> getAll( Pageable pageable )
	{
		return authorRepository.findAll( pageable );
	}
	@Override
	public Author add( Author o )
	{
		return authorRepository.save( o );
	}
	
	@Override
	public Author update( Author o, int id )
	{
		Author author = checkAuthorId( id );
		author.setName( o.getName() );
		author.setEmailId( o.getEmailId() );
		return authorRepository.save( author );
	}
	
	@Override
	public Author getById( int id )
	{
		return checkAuthorId( id );
	}
	
	@Override
	public Author deleteById( int id )
	{
		Author author = checkAuthorId( id );
		authorRepository.deleteById( id );
		return author;
	}
	
	@Override
	public List<Publication> getPublicationsById( int id )
	{
		return checkAuthorId( id ).getPublication();
	}
	
	public List<Publication> search(int id,String catagory,String title,int year,String type,String hero,String genre)
	{
		Author author = checkAuthorId( id );
		Publication filter = new Publication();
		filter.setCatagory(catagory);
		filter.setTitle(title);
		filter.setYear(year);
		filter.setType(type);
		filter.setHero(hero);
		filter.setGenre(genre);
		List<Publication> publications = publicationRepository.findAll(new Specification<Publication>(){
			@Override
			public Predicate toPredicate(Root<Publication> root, CriteriaQuery< ?> query, CriteriaBuilder cb)
			{
				List<Predicate> predicates = new ArrayList<>();
				
				if (filter.getCatagory() != null) {
						predicates.add(cb.like(cb.lower(root.get("catagory")), 
                                                    "%" + filter.getCatagory().toLowerCase() + "%"));
				}
				if (filter.getTitle() != null) {
				
						predicates.add(cb.like(cb.lower(root.get("title")), 
                                                    "%" + filter.getTitle().toLowerCase() + "%"));
				}
				if (filter.getYear() != 0) {
				
						predicates.add(cb.equal(root.get("year"), filter.getYear()));
				}
				if (filter.getType() != null) {
				
						predicates.add(cb.like(cb.lower(root.get("type")), 
                                                    "%" + filter.getType().toLowerCase() + "%"));
				}
				if (filter.getHero() != null) {
				
						predicates.add(cb.like(cb.lower(root.get("hero")), 
                                                    "%" + filter.getHero().toLowerCase() + "%"));
				}
				if (filter.getGenre() != null) {
				
						predicates.add(cb.like(cb.lower(root.get("genre")), 
                                                    "%" + filter.getGenre().toLowerCase() + "%"));
				}
			
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		});
		return publications;
	}
	
	private Author checkAuthorId( int id )
	{
		if ( !authorRepository.findById( id ).isPresent() )
			throw new ResourceNotFoundException( " Author id = " + id + " not found" );
		else
			return authorRepository.findById( id ).get();
	}
		
	
	
	
}
