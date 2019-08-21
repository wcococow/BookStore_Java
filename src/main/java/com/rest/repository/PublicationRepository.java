package com.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import com.rest.model.Publication;

public interface PublicationRepository extends JpaRepository<Publication,Integer>,
														JpaSpecificationExecutor {
															
	public List<Publication> findByAuthorsNameAndYear(String name, Integer year);	
	public List<Publication> findByAuthorsNameAndCatagory(String name, String catagory);
	
    	
}