package com.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import java.util.stream.*;
import com.rest.model.*;
import com.rest.repository.*;
import com.rest.service.*;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublicationRepository publicationRepository;
	
	private PublicationService publicationService; 
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}
		
		@Override
		public void run(String... args) throws Exception {
			// Cleanup the tables
			authorRepository.deleteAllInBatch();
			publicationRepository.deleteAllInBatch();
			
			//create authors
			Author auth1 = new Author(1,"Mike Joe","mikejoe@gmail.com");
			Author auth2 = new Author(2,"Bruce Lee","brucelee@gmail.com");
			Author auth3 = new Author(3,"Mike Will","Mikew@gmail.com");
			Author auth4 = new Author(4,"Kate Lee","wcococow@gmail.com");

			
			authorRepository.save(auth1);
			authorRepository.save(auth2);
			authorRepository.save(auth3);
			authorRepository.save(auth4);

			//Create publications
			Publication pub1 = new Publication(1,"book","my way",2001,"NA","NA","Drama");
			Publication pub2 = new Publication(2,"book","data science",2011,"NA","NA","Science");
			Publication pub3 = new Publication(3,"magazine","vogue",2019,"printed","NA","NA");
			Publication pub4 = new Publication(4,"book","old man",2019,"NA","NA","Drama");
			Publication pub5 = new Publication(5,"book","young lady",2019,"NA","NA","Novel");
			Publication pub6 = new Publication(6,"magazine","dog lover",2019,"printed","NA","NA");
			Publication pub7 = new Publication(7,"comics","batman",2002,"NA","batman","NA");
			Publication pub8 = new Publication(8,"comics","superman",2001,"NA","superman","NA");
			
			publicationRepository.save(pub1);
			publicationRepository.save(pub2);
			publicationRepository.save(pub3);
			publicationRepository.save(pub4);
			publicationRepository.save(pub5);
			publicationRepository.save(pub6);
			publicationRepository.save(pub7);
			publicationRepository.save(pub8);
			
			pub1.setAuthor(auth1);
			pub1.setAuthor(auth2);
			pub2.setAuthor(auth3);
			pub3.setAuthor(auth3);
			pub6.setAuthor(auth3);
			
			pub4.setAuthor(auth4);
			pub5.setAuthor(auth4);
			pub7.setAuthor(auth4);
			pub8.setAuthor(auth4);
			
		
	
			publicationRepository.save(pub1);
			publicationRepository.save(pub2);
			publicationRepository.save(pub3);
			publicationRepository.save(pub4);
			publicationRepository.save(pub5);
			publicationRepository.save(pub6);
			publicationRepository.save(pub7);
			publicationRepository.save(pub8);
			
		
		
			
		
			
			authorRepository.save(auth1);
			authorRepository.save(auth2);
			authorRepository.save(auth3);
			authorRepository.save(auth4);
			
		

		}
		
    
}