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
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;


@Service
@NoArgsConstructor
public class PublicationServiceImp extends PublicationService{
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublicationRepository publicationRepository;
	
	
	private EntityManager entitiyManager;
	private EntityManagerFactory entityManagerFactory;
	
	@Override
	public Page<Publication> getAll( Pageable pageable )
	{
		return publicationRepository.findAll( pageable );
	}
	@Override
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
	
	@Override
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
	
	@Override
	public Publication getById( int id ) throws ResourceNotFoundException
	{
		return checkPublicationId( id );
	}
	
	@Override
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
	
	@Override
/*	public List<Publication> searchPublicationsByAuthorAndYear()
	{
		/*CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
		CriteriaQuery<Publication> cq = cb.createQuery(Publication.class);
		Root<Publication> root = cq.from(Publication.class);
		Join<Object,Object> publication = root.join(Publication_.Authors);
		
		ParameterExpression<Integer> year = cb.parameter(Integer.class);
		ParameterExpression<String> name = cb.parameter(String.class);
		cq.where(
		cb.like(publication.get(Publication_.year), year),
		cb.like(publication.get(Author.name), name)
		);
		
		
		TypedQuery<Publication> q = entitiyManager.createQuery(cq);
		q.setParameter(name, author);
		q.setParameter(year, pubyear);
		List<Publication> pubs = q.getResultList();
		return pubs;
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		List<Publication> pubs = em.createQuery(
			"SELECT b FROM publication b INNER JOIN author_publication pa ON pa.publication_id = b.id INNER JOIN author a ON a.id = pa.author_id WHERE a.id = 3 and b.catagory='book'",
			Publication.class).getResultList();
			em.getTransaction().commit();
			em.close();
		return pubs;	
	
	}
	*/
		
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
		
		
		entitiyManager.getTransaction().begin();
		CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
		CriteriaQuery<Publication> cq = cb.createQuery(Publication.class);
		Root<Author> root = cq.from(Author.class);
		Join<Author,Publication> p = root.join("publications",JoinType.LEFT);
		cq.where(cb.equal(p.get("catagory"), catagory));
		TypedQuery<Publication> tq = entitiyManager.createQuery(cq);
		List<Publication> pubs = tq.getResultList();
		
		pubs.forEach(System.out::println);
		entitiyManager.getTransaction().commit();
		entitiyManager.close();
		return pubs;
		
		
		
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
