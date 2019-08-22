package com.rest.service;
import lombok.NoArgsConstructor;
import com.rest.model.Author;
import com.rest.model.Publication;
import java.util.stream.*;
import com.rest.repository.AuthorRepository;
import com.rest.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.*;
import com.rest.exception.ResourceNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.Predicate;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;


@Service
@NoArgsConstructor
public class PublicationService {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublicationRepository publicationRepository;
	
	
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Publications");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
	
	
	public Page<Publication> getAll( Pageable pageable )
	{
		return publicationRepository.findAll( pageable );
	}
	
	public Publication add( Publication o )
	{
		if (o.getAuthor() != null){
			List<Integer> authorsIds = o.getAuthor().stream()
									.map(Author::getId)
									.collect(Collectors.toList());
			//fetch authors by id
			authorsIds.forEach(i -> {
				Author author = checkAuthorId(i);
				//update each author set the updated publication
				o.setAuthor(author);
				author.setPublication(o);
			});
		
		}
		
		return publicationRepository.save( o );
	}
	
	
	public Publication update( Publication o, int id ) throws ResourceNotFoundException
	{
		Publication publication = checkPublicationId( id );
		if ( o.getCatagory() != null )
			publication.setCatagory( o.getCatagory() ); 
		if ( o.getTitle() != null )
			publication.setTitle( o.getTitle() );
		if ( o.getYear() != 0 )
			publication.setYear( o.getYear() ); 
		if ( o.getType() != null )
			publication.setType( o.getType() );
		if ( o.getHero() != null )
			publication.setHero( o.getHero() );
		if ( o.getGenre() != null )
			publication.setGenre( o.getGenre() );
		
		if ( o.getAuthor() != null ){
			List<Integer> authorsIds = o.getAuthor().stream()
									.map(Author::getId)
									.collect(Collectors.toList());
			//fetch authors by id
			authorsIds.forEach(i -> {
				Author author = checkAuthorId(i);
				//update each author set the updated publication
				o.setAuthor(author);
				author.setPublication(publication);
			});			
			
		}
		
		return publicationRepository.save( publication );
	}
	
	
	public Publication getById( int id ) throws ResourceNotFoundException
	{
		return checkPublicationId( id );
	}
	
	
	public Publication deleteById( int id ) throws ResourceNotFoundException
	{
		Publication publication = checkPublicationId( id );
			//remove all authors by this publication
			publication.getAuthor().forEach(auth -> {
				auth.getPublication().remove(publication);
			});
		
		publicationRepository.deleteById( id );
		return publication;
	}
	
	
	
	public List<Publication> findByAuthorsNameAndYear(String name, Integer year){
		
		List<Publication> publications = publicationRepository.findByAuthorsNameAndYear(name , year);
		
		return publications;
		
	}
	
	public List<Publication> findByAuthorsNameAndCatagory(String name, String catagory){
		
		List<Publication> publications = publicationRepository.findByAuthorsNameAndCatagory(name , catagory);
		
		return publications;
		
	}
		
	public List<Publication> search(String name,String catagory,String title,int year,String type,String hero,String genre)
	{
		
		
		Publication filter = new Publication();
		
		filter.setCatagory(catagory);
		filter.setTitle(title);
		filter.setYear(year);
		filter.setType(type);
		filter.setHero(hero);
		filter.setGenre(genre);
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Publication> cq = cb.createQuery(Publication.class);
		Root<Publication> root = cq.from(Publication.class);
		Join<Publication,Author> pa = root.join("authors",JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<>();
		
		
		predicates.add(cb.equal(pa.get("name"),name ));
				
				
		if (filter.getCatagory() != null) {
					predicates.add(cb.like(cb.lower(pa.get("catagory")), 
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
		
		
		cq.select(root).where(predicates.toArray(new Predicate[]{}));				
				
		
		return entityManager.createQuery(cq).getResultList();
		
		
		
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
